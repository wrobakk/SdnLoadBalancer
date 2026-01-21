package pl.edu.agh.kt;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.projectfloodlight.openflow.protocol.OFMessage;
import org.projectfloodlight.openflow.protocol.OFPacketIn;
import org.projectfloodlight.openflow.protocol.OFType;
import org.projectfloodlight.openflow.types.DatapathId;
import org.projectfloodlight.openflow.types.IPv4Address;
import org.projectfloodlight.openflow.types.IpProtocol;
import org.projectfloodlight.openflow.types.OFPort;

import net.floodlightcontroller.core.FloodlightContext;
import net.floodlightcontroller.core.IOFMessageListener;
import net.floodlightcontroller.core.IOFSwitch;
import net.floodlightcontroller.core.internal.IOFSwitchService;
import net.floodlightcontroller.core.module.FloodlightModuleContext;
import net.floodlightcontroller.core.module.FloodlightModuleException;
import net.floodlightcontroller.core.module.IFloodlightModule;
import net.floodlightcontroller.core.module.IFloodlightService;

import net.floodlightcontroller.core.IFloodlightProviderService;
import net.floodlightcontroller.packet.ARP;
import net.floodlightcontroller.packet.Ethernet;
import net.floodlightcontroller.packet.ICMP;
import net.floodlightcontroller.packet.IPv4;
import net.floodlightcontroller.packet.IPv6;
import net.floodlightcontroller.packet.TCP;
import net.floodlightcontroller.packet.UDP;
import net.floodlightcontroller.restserver.IRestApiService;
import net.floodlightcontroller.routing.IRoutingService;
import net.floodlightcontroller.routing.Route;
import net.floodlightcontroller.topology.ITopologyService;
import net.floodlightcontroller.topology.NodePortTuple;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SdnLabListener implements IFloodlightModule, IOFMessageListener {

    protected IFloodlightProviderService floodlightProvider;
    protected static Logger logger;
    protected ITopologyService topologyService;
    protected IOFSwitchService switchService;
    protected IRoutingService routingService;
    protected static Routing routing;
    protected IRestApiService restApiService;

    @Override
    public String getName() {
        return SdnLabListener.class.getSimpleName();
    }

    @Override
    public boolean isCallbackOrderingPrereq(OFType type, String name) {
        return false;
    }

    @Override
    public boolean isCallbackOrderingPostreq(OFType type, String name) {
        return false;
    }

    @Override
    public net.floodlightcontroller.core.IListener.Command receive(
            IOFSwitch sw, OFMessage msg, FloodlightContext cntx) {

        logger.info("********* NEW PACKET IN *********");

        // Check if the message is of type OFPacketIn
        if (!(msg instanceof OFPacketIn)) {
            logger.error("Received invalid message type: {}", msg.getClass().getSimpleName());
            return Command.STOP;
        }

        OFPacketIn pin = (OFPacketIn) msg;
        Ethernet eth = new Ethernet();

        try {
            eth.deserialize(pin.getData(), 0, pin.getData().length);
        } catch (Exception e) {
            logger.error("Failed to deserialize Ethernet frame", e);
            return Command.STOP;
        }

        IPv4Address srcIp = null;
        IPv4Address dstIp = null;
        int srcPort = 0;
        int dstPort = 0;
        IpProtocol protocol = null;
        boolean isHttp = false;
        
        if(eth.getPayload().getClass().getSimpleName() == "IPv6")
        {
        	return Command.STOP;
        }
        
        logger.info("Type = {}", eth.getPayload().getClass().getSimpleName());

        if (eth.getPayload() instanceof IPv4) {
            IPv4 ipv4 = (IPv4) eth.getPayload();
            srcIp = ipv4.getSourceAddress();
            dstIp = ipv4.getDestinationAddress();
            protocol = ipv4.getProtocol();

            if (ipv4.getPayload() instanceof TCP) {
                TCP tcp = (TCP) ipv4.getPayload();
                srcPort = tcp.getSourcePort().getPort();
                dstPort = tcp.getDestinationPort().getPort();

                // Check for HTTP data in TCP payload
                byte[] tcpPayload = tcp.getPayload().serialize();
                String payloadStr = new String(tcpPayload);

                // Look for typical HTTP methods in the payload
                if (payloadStr.startsWith("GET") || payloadStr.startsWith("POST") || 
                    payloadStr.startsWith("PUT") || payloadStr.startsWith("DELETE") || 
                    payloadStr.startsWith("HEAD") || payloadStr.startsWith("OPTIONS")) {
                    isHttp = true;
                    logger.info("HTTP traffic detected: {}", payloadStr.split("\n")[0]); // Log HTTP method and URI
                }
            } else if (ipv4.getPayload() instanceof UDP) {
                UDP udp = (UDP) ipv4.getPayload();
                srcPort = udp.getSourcePort().getPort();
                dstPort = udp.getDestinationPort().getPort();
            } else if (ipv4.getPayload() instanceof ICMP) {
                logger.info("ICMP packet detected");
            } else {
                logger.debug("Unsupported IPv4 payload type");
                return Command.STOP;
            }
        } else if (eth.getPayload() instanceof ARP) {
            ARP arp = (ARP) eth.getPayload();
            srcIp = arp.getSenderProtocolAddress();
            dstIp = arp.getTargetProtocolAddress();
        } else {
            logger.debug("Unsupported payload type");
            return Command.STOP;
        }

        if (dstIp == null || srcIp == null) {
            logger.debug("Source or Destination IP is null");
            return Command.STOP;
        }

        logger.info("Source IP = {}, Destination IP = {}", srcIp, dstIp);
        logger.info("Source Port = {}, Destination Port = {}", srcPort, dstPort);
        logger.info("Protocol = {}", protocol);
        if (isHttp) {
            logger.info("Detected HTTP traffic between {}:{}", srcIp, srcPort);
            logger.info("and {}:{}",dstIp, dstPort);
        }

        SingleHostInfo info = Flows.hosts.getHostInfo(dstIp);

        if (info == null) {
            logger.debug("Host not found for IP {}", dstIp);
            return Command.STOP;
        }

        int dstSwId = info.getSw();
        if (dstSwId == 0) {
            logger.error("Invalid switch ID for destination host: {}", dstSwId);
            return Command.STOP;
        }

        logger.info("Host sw = {}", dstSwId);
        DatapathId srcSw = sw.getId();
        DatapathId dstSw = DatapathId.of(dstSwId);
        logger.info("Source SW ID: {} Destination SW ID: {}", srcSw, dstSw);

        // Calculate hash from source IP, destination IP, ports, protocol, and HTTP flag
        int hash = Objects.hash(srcIp, dstIp, srcPort, dstPort, protocol, isHttp);
        logger.info("Packet hash = {}", hash);
        logger.info("Source IP: {} Destination IP: {}", srcIp, dstIp);

        // Get all possible routes
        ArrayList<Route> routes = SdnLabListener.getRouting().checkRoutes(srcSw, dstSw);
        if (routes == null || routes.isEmpty()) {
            logger.error("No routes available");
            return Command.STOP;
        }

     // Check if the list of routes is not empty before proceeding
        if (routes == null || routes.isEmpty()) {
            logger.error("No available routes for the selected source and destination switches");
            return Command.STOP;
        }

        // Select route using modulo
        logger.info("------------------------------- ROZMIAR ROUTES={}",routes.size());
        int selectedRouteIndex = Math.abs(hash) % routes.size();
        Route selectedRoute = routes.get(selectedRouteIndex);
        logger.info("Packet hash = {}", selectedRouteIndex);

        // Ensure that the selected route is valid
        if (selectedRoute == null || selectedRoute.getPath() == null || selectedRoute.getPath().isEmpty()) {
            logger.error("Selected route is invalid or empty");
            return Command.STOP;
        }

        logger.info("Selected route index = {}, route = {}", selectedRouteIndex, selectedRoute.getPath());


        List<NodePortTuple> path = selectedRoute.getPath();
        if (path == null || path.isEmpty()) {
            logger.error("Selected route path is empty");
            return Command.STOP;
        }

        DatapathId previousId = dstSw;
        OFPort previousPort = OFPort.of(info.getPort());

        for (int i = path.size() - 1; i >= 0; i--) {
            NodePortTuple npt = path.get(i);
            if (npt != null && npt.getNodeId() != null && npt.getPortId() != null) {
                IOFSwitch s = switchService.getSwitch(npt.getNodeId());
                if (s != null) {
                    logger.info("Switch ID = {}", npt.getNodeId());
                    logger.info("Port IN = {}, Port OUT = {}", npt.getPortId(), previousPort);
                    Flows.simpleAdd(s, pin, cntx, npt.getPortId(), previousPort, false);
                } else {
                    logger.warn("Switch not found for NodeId {}", npt.getNodeId());
                }
            } else {
                logger.warn("Invalid NodePortTuple at index {}", i);
            }
            previousId = npt.getNodeId();
            previousPort = npt.getPortId();
        }

        logger.info("Final Switch ID = {}, Port IN = {}", srcSw, pin.getInPort());
        Flows.simpleAdd(sw, pin, cntx, pin.getInPort(), previousPort, true);

        return Command.STOP;
    }


    @Override
    public Collection<Class<? extends IFloodlightService>> getModuleServices() {
        return null;
    }

    @Override
    public Map<Class<? extends IFloodlightService>, IFloodlightService> getServiceImpls() {
        return null;
    }

    @Override
    public Collection<Class<? extends IFloodlightService>> getModuleDependencies() {
        Collection<Class<? extends IFloodlightService>> l = new ArrayList<>();
        l.add(IFloodlightProviderService.class);
        l.add(IRoutingService.class);
        l.add(IRestApiService.class);
        return l;
    }

    @Override
    public void init(FloodlightModuleContext context)
            throws FloodlightModuleException {
        floodlightProvider = context.getServiceImpl(IFloodlightProviderService.class);
        logger = LoggerFactory.getLogger(SdnLabListener.class);

        restApiService = context.getServiceImpl(IRestApiService.class);
        topologyService = context.getServiceImpl(ITopologyService.class);
        switchService = context.getServiceImpl(IOFSwitchService.class);
        routingService = context.getServiceImpl(IRoutingService.class);
    }

    @Override
    public void startUp(FloodlightModuleContext context)
            throws FloodlightModuleException {
        floodlightProvider.addOFMessageListener(OFType.PACKET_IN, this);

        restApiService.addRestletRoutable(new RestLab());

        switchService.addOFSwitchListener(new SdnLabTopologyListener());
        topologyService.addListener(new SdnLabTopologyListener());
        routing = new Routing(routingService);
        logger.info("*************** START **********************");

    }

    public static Routing getRouting() {
        return routing;
    }
}