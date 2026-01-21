package pl.edu.agh.kt;

import net.floodlightcontroller.routing.IRoutingService;
import net.floodlightcontroller.routing.Route;
import net.floodlightcontroller.routing.RouteId;
import net.floodlightcontroller.topology.NodePortTuple;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.projectfloodlight.openflow.types.DatapathId;
import org.projectfloodlight.openflow.types.OFPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Routing {
	private IRoutingService routingService;
	private LinkedList<RouteConfig> routesConfig;
	protected static final Logger logger = LoggerFactory
			.getLogger(Routing.class);

	public Routing(IRoutingService routingService) {
		super();
		this.routingService = routingService;
		DatapathId[] switches = new DatapathId[] {
				DatapathId.of("00:00:00:00:00:01"),
				DatapathId.of("00:00:00:00:00:02"),
				DatapathId.of("00:00:00:00:00:03"),
				DatapathId.of("00:00:00:00:00:04"),
				DatapathId.of("00:00:00:00:00:05"),
				DatapathId.of("00:00:00:00:00:06") };
		
		this.routesConfig = new LinkedList<>();

		// path1 l1 -> l2 : sciezka przez s1
		List<NodePortTuple> path1 = new ArrayList<>();
		path1.add(new NodePortTuple(switches[2], OFPort.of(1)));
		path1.add(new NodePortTuple(switches[0], OFPort.of(1)));
		path1.add(new NodePortTuple(switches[0], OFPort.of(2)));
		path1.add(new NodePortTuple(switches[3], OFPort.of(1)));
		routesConfig.add(new RouteConfig(switches[2], switches[3], path1));

		// path2 l1 -> l3 : sciezka przez s1
		List<NodePortTuple> path2 = new ArrayList<>();
		path2.add(new NodePortTuple(switches[2], OFPort.of(1)));
		path2.add(new NodePortTuple(switches[0], OFPort.of(1)));
		path2.add(new NodePortTuple(switches[0], OFPort.of(3)));
		path2.add(new NodePortTuple(switches[4], OFPort.of(1)));
		routesConfig.add(new RouteConfig(switches[2], switches[4], path2));

		// path3 l1 -> l4 : sciezka przez s1
		List<NodePortTuple> path3 = new ArrayList<>();
		path3.add(new NodePortTuple(switches[2], OFPort.of(1)));
		path3.add(new NodePortTuple(switches[0], OFPort.of(1)));
		path3.add(new NodePortTuple(switches[0], OFPort.of(4)));
		path3.add(new NodePortTuple(switches[5], OFPort.of(1)));
		routesConfig.add(new RouteConfig(switches[2], switches[5], path3));

		// path4 l2 -> l1 : sciezka przez s1
		List<NodePortTuple> path4 = new ArrayList<>();
		path4.add(new NodePortTuple(switches[3], OFPort.of(1)));
		path4.add(new NodePortTuple(switches[0], OFPort.of(2)));
		path4.add(new NodePortTuple(switches[0], OFPort.of(1)));
		path4.add(new NodePortTuple(switches[2], OFPort.of(1)));
		routesConfig.add(new RouteConfig(switches[3], switches[2], path4));

		// path5 l2 -> l3 : sciezka przez s1
		List<NodePortTuple> path5 = new ArrayList<>();
		path5.add(new NodePortTuple(switches[3], OFPort.of(1)));
		path5.add(new NodePortTuple(switches[0], OFPort.of(2)));
		path5.add(new NodePortTuple(switches[0], OFPort.of(3)));
		path5.add(new NodePortTuple(switches[4], OFPort.of(1)));
		routesConfig.add(new RouteConfig(switches[3], switches[4], path5));

		// path6 l2 -> l4 : sciezka przez s1
		List<NodePortTuple> path6 = new ArrayList<>();
		path6.add(new NodePortTuple(switches[3], OFPort.of(1)));
		path6.add(new NodePortTuple(switches[0], OFPort.of(2)));
		path6.add(new NodePortTuple(switches[0], OFPort.of(4)));
		path6.add(new NodePortTuple(switches[5], OFPort.of(1)));
		routesConfig.add(new RouteConfig(switches[3], switches[5], path6));

		// path7 l3 -> l1 : sciezka przez s1
		List<NodePortTuple> path7 = new ArrayList<>();
		path7.add(new NodePortTuple(switches[4], OFPort.of(1)));
		path7.add(new NodePortTuple(switches[0], OFPort.of(3)));
		path7.add(new NodePortTuple(switches[0], OFPort.of(1)));
		path7.add(new NodePortTuple(switches[2], OFPort.of(1)));
		routesConfig.add(new RouteConfig(switches[4], switches[2], path7));

		// path8 l3 -> l2 : sciezka przez s1
		List<NodePortTuple> path8 = new ArrayList<>();
		path8.add(new NodePortTuple(switches[4], OFPort.of(1)));
		path8.add(new NodePortTuple(switches[0], OFPort.of(3)));
		path8.add(new NodePortTuple(switches[0], OFPort.of(2)));
		path8.add(new NodePortTuple(switches[3], OFPort.of(1)));
		routesConfig.add(new RouteConfig(switches[4], switches[3], path8));

		// path9 l3 -> l4 : sciezka przez s1
		List<NodePortTuple> path9 = new ArrayList<>();
		path9.add(new NodePortTuple(switches[4], OFPort.of(1)));
		path9.add(new NodePortTuple(switches[0], OFPort.of(3)));
		path9.add(new NodePortTuple(switches[0], OFPort.of(4)));
		path9.add(new NodePortTuple(switches[5], OFPort.of(1)));
		routesConfig.add(new RouteConfig(switches[4], switches[5], path9));

		// path10 l4 -> l1 : sciezka przez s1
		List<NodePortTuple> path10 = new ArrayList<>();
		path10.add(new NodePortTuple(switches[5], OFPort.of(1)));
		path10.add(new NodePortTuple(switches[0], OFPort.of(4)));
		path10.add(new NodePortTuple(switches[0], OFPort.of(1)));
		path10.add(new NodePortTuple(switches[2], OFPort.of(1)));
		routesConfig.add(new RouteConfig(switches[5], switches[2], path10));

		// path11 l4 -> l2 : sciezka przez s1
		List<NodePortTuple> path11 = new ArrayList<>();
		path11.add(new NodePortTuple(switches[5], OFPort.of(1)));
		path11.add(new NodePortTuple(switches[0], OFPort.of(4)));
		path11.add(new NodePortTuple(switches[0], OFPort.of(2)));
		path11.add(new NodePortTuple(switches[3], OFPort.of(1)));
		routesConfig.add(new RouteConfig(switches[5], switches[3], path11));

		// path12 l4 -> l3 : sciezka przez s1
		List<NodePortTuple> path12 = new ArrayList<>();
		path12.add(new NodePortTuple(switches[5], OFPort.of(1)));
		path12.add(new NodePortTuple(switches[0], OFPort.of(4)));
		path12.add(new NodePortTuple(switches[0], OFPort.of(3)));
		path12.add(new NodePortTuple(switches[4], OFPort.of(1)));
		routesConfig.add(new RouteConfig(switches[5], switches[4], path12));

//		path13 l1 -> l2 : sciezka przez s2
		List<NodePortTuple> path13 = new ArrayList<>();
		path13.add(new NodePortTuple(switches[2], OFPort.of(2)));
		path13.add(new NodePortTuple(switches[1], OFPort.of(1)));
		path13.add(new NodePortTuple(switches[1], OFPort.of(2)));
		path13.add(new NodePortTuple(switches[3], OFPort.of(2)));
		routesConfig.add(new RouteConfig(switches[2], switches[3], path13));

		// path14 l1 -> l3 : sciezka przez s2
		List<NodePortTuple> path14 = new ArrayList<>();
		path14.add(new NodePortTuple(switches[2], OFPort.of(2)));
		path14.add(new NodePortTuple(switches[1], OFPort.of(1)));
		path14.add(new NodePortTuple(switches[1], OFPort.of(3)));
		path14.add(new NodePortTuple(switches[4], OFPort.of(2)));
		routesConfig.add(new RouteConfig(switches[2], switches[4], path14));

		// path15 l1 -> l4 : sciezka przez s2
		List<NodePortTuple> path15 = new ArrayList<>();
		path15.add(new NodePortTuple(switches[2], OFPort.of(2)));
		path15.add(new NodePortTuple(switches[1], OFPort.of(1)));
		path15.add(new NodePortTuple(switches[1], OFPort.of(4)));
		path15.add(new NodePortTuple(switches[5], OFPort.of(2)));
		routesConfig.add(new RouteConfig(switches[2], switches[5], path15));

		// path16 l2 -> l1 : sciezka przez s2
		List<NodePortTuple> path16 = new ArrayList<>();
		path16.add(new NodePortTuple(switches[3], OFPort.of(2)));
		path16.add(new NodePortTuple(switches[1], OFPort.of(2)));
		path16.add(new NodePortTuple(switches[1], OFPort.of(1)));
		path16.add(new NodePortTuple(switches[2], OFPort.of(2)));
		routesConfig.add(new RouteConfig(switches[3], switches[2], path16));

		// path17 l2 -> l3 : sciezka przez s2
		List<NodePortTuple> path17 = new ArrayList<>();
		path17.add(new NodePortTuple(switches[3], OFPort.of(2)));
		path17.add(new NodePortTuple(switches[1], OFPort.of(2)));
		path17.add(new NodePortTuple(switches[1], OFPort.of(3)));
		path17.add(new NodePortTuple(switches[4], OFPort.of(2)));
		routesConfig.add(new RouteConfig(switches[3], switches[4], path17));

		// path18 l2 -> l4 : sciezka przez s2
		List<NodePortTuple> path18 = new ArrayList<>();
		path18.add(new NodePortTuple(switches[3], OFPort.of(2)));
		path18.add(new NodePortTuple(switches[1], OFPort.of(2)));
		path18.add(new NodePortTuple(switches[1], OFPort.of(4)));
		path18.add(new NodePortTuple(switches[5], OFPort.of(2)));
		routesConfig.add(new RouteConfig(switches[3], switches[5], path18));

		// path19 l3 -> l1 : sciezka przez s2
		List<NodePortTuple> path19 = new ArrayList<>();
		path19.add(new NodePortTuple(switches[4], OFPort.of(2)));
		path19.add(new NodePortTuple(switches[1], OFPort.of(3)));
		path19.add(new NodePortTuple(switches[1], OFPort.of(1)));
		path19.add(new NodePortTuple(switches[2], OFPort.of(2)));
		routesConfig.add(new RouteConfig(switches[4], switches[2], path19));

		// path20 l3 -> l2 : sciezka przez s2
		List<NodePortTuple> path20 = new ArrayList<>();
		path20.add(new NodePortTuple(switches[4], OFPort.of(2)));
		path20.add(new NodePortTuple(switches[1], OFPort.of(3)));
		path20.add(new NodePortTuple(switches[1], OFPort.of(2)));
		path20.add(new NodePortTuple(switches[3], OFPort.of(2)));
		routesConfig.add(new RouteConfig(switches[4], switches[3], path20));

		// path21 l3 -> l4 : sciezka przez s2
		List<NodePortTuple> path21 = new ArrayList<>();
		path21.add(new NodePortTuple(switches[4], OFPort.of(2)));
		path21.add(new NodePortTuple(switches[1], OFPort.of(3)));
		path21.add(new NodePortTuple(switches[1], OFPort.of(4)));
		path21.add(new NodePortTuple(switches[5], OFPort.of(2)));
		routesConfig.add(new RouteConfig(switches[4], switches[5], path21));

		// path22 l4 -> l1 : sciezka przez s2
		List<NodePortTuple> path22 = new ArrayList<>();
		path22.add(new NodePortTuple(switches[5], OFPort.of(2)));
		path22.add(new NodePortTuple(switches[1], OFPort.of(4)));
		path22.add(new NodePortTuple(switches[1], OFPort.of(1)));
		path22.add(new NodePortTuple(switches[2], OFPort.of(2)));
		routesConfig.add(new RouteConfig(switches[5], switches[2], path22));

		// path23 l4 -> l2 : sciezka przez s2
		List<NodePortTuple> path23 = new ArrayList<>();
		path23.add(new NodePortTuple(switches[5], OFPort.of(2)));
		path23.add(new NodePortTuple(switches[1], OFPort.of(4)));
		path23.add(new NodePortTuple(switches[1], OFPort.of(2)));
		path23.add(new NodePortTuple(switches[3], OFPort.of(2)));
		routesConfig.add(new RouteConfig(switches[5], switches[3], path23));

		// path24 l4 -> l3 : sciezka przez s2
		List<NodePortTuple> path24 = new ArrayList<>();
		path24.add(new NodePortTuple(switches[5], OFPort.of(2)));
		path24.add(new NodePortTuple(switches[1], OFPort.of(4)));
		path24.add(new NodePortTuple(switches[1], OFPort.of(3)));
		path24.add(new NodePortTuple(switches[4], OFPort.of(2)));
		routesConfig.add(new RouteConfig(switches[5], switches[4], path24));

		// path25 l1 -> l2 : sciezka przez s1 l3 s2
		List<NodePortTuple> path25 = new ArrayList<>();
		path25.add(new NodePortTuple(switches[2], OFPort.of(1)));
		path25.add(new NodePortTuple(switches[0], OFPort.of(1)));
		path25.add(new NodePortTuple(switches[0], OFPort.of(3)));
		path25.add(new NodePortTuple(switches[4], OFPort.of(1)));
		path25.add(new NodePortTuple(switches[4], OFPort.of(2)));
		path25.add(new NodePortTuple(switches[1], OFPort.of(3)));
		path25.add(new NodePortTuple(switches[1], OFPort.of(2)));
		path25.add(new NodePortTuple(switches[3], OFPort.of(2)));
		routesConfig.add(new RouteConfig(switches[2], switches[3], path25));

		// path26 l1 -> l3 : sciezka przez s1 l4 s2
		List<NodePortTuple> path26 = new ArrayList<>();
		path26.add(new NodePortTuple(switches[2], OFPort.of(1)));
		path26.add(new NodePortTuple(switches[0], OFPort.of(1)));
		path26.add(new NodePortTuple(switches[0], OFPort.of(4)));
		path26.add(new NodePortTuple(switches[5], OFPort.of(1)));
		path26.add(new NodePortTuple(switches[5], OFPort.of(2)));
		path26.add(new NodePortTuple(switches[1], OFPort.of(4)));
		path26.add(new NodePortTuple(switches[1], OFPort.of(3)));
		path26.add(new NodePortTuple(switches[4], OFPort.of(2)));
		routesConfig.add(new RouteConfig(switches[2], switches[4], path26));

		// path27 l1 -> l4 : sciezka przez s1 l2 s2
		List<NodePortTuple> path27 = new ArrayList<>();
		path27.add(new NodePortTuple(switches[2], OFPort.of(1)));
		path27.add(new NodePortTuple(switches[0], OFPort.of(1)));
		path27.add(new NodePortTuple(switches[0], OFPort.of(2)));
		path27.add(new NodePortTuple(switches[3], OFPort.of(1)));
		path27.add(new NodePortTuple(switches[3], OFPort.of(2)));
		path27.add(new NodePortTuple(switches[1], OFPort.of(2)));
		path27.add(new NodePortTuple(switches[1], OFPort.of(4)));
		path27.add(new NodePortTuple(switches[5], OFPort.of(2)));
		routesConfig.add(new RouteConfig(switches[2], switches[5], path27));

		// path28 l2 -> l1 : sciezka przez s1 l3 s2
		List<NodePortTuple> path28 = new ArrayList<>();
		path28.add(new NodePortTuple(switches[3], OFPort.of(1)));
		path28.add(new NodePortTuple(switches[0], OFPort.of(2)));
		path28.add(new NodePortTuple(switches[0], OFPort.of(3)));
		path28.add(new NodePortTuple(switches[4], OFPort.of(1)));
		path28.add(new NodePortTuple(switches[4], OFPort.of(2)));
		path28.add(new NodePortTuple(switches[1], OFPort.of(3)));
		path28.add(new NodePortTuple(switches[1], OFPort.of(1)));
		path28.add(new NodePortTuple(switches[2], OFPort.of(2)));
		routesConfig.add(new RouteConfig(switches[3], switches[2], path28));

		// path29 l2 -> l3 : sciezka przez s1 l4 s2
		List<NodePortTuple> path29 = new ArrayList<>();
		path29.add(new NodePortTuple(switches[3], OFPort.of(1)));
		path29.add(new NodePortTuple(switches[0], OFPort.of(2)));
		path29.add(new NodePortTuple(switches[0], OFPort.of(4)));
		path29.add(new NodePortTuple(switches[5], OFPort.of(1)));
		path29.add(new NodePortTuple(switches[5], OFPort.of(2)));
		path29.add(new NodePortTuple(switches[1], OFPort.of(4)));
		path29.add(new NodePortTuple(switches[1], OFPort.of(3)));
		path29.add(new NodePortTuple(switches[4], OFPort.of(2)));
		routesConfig.add(new RouteConfig(switches[3], switches[4], path29));

		// path30 l2 -> l4 : sciezka przez s1 l1 s2
		List<NodePortTuple> path30 = new ArrayList<>();
		path30.add(new NodePortTuple(switches[3], OFPort.of(1)));
		path30.add(new NodePortTuple(switches[0], OFPort.of(2)));
		path30.add(new NodePortTuple(switches[0], OFPort.of(1)));
		path30.add(new NodePortTuple(switches[2], OFPort.of(1)));
		path30.add(new NodePortTuple(switches[2], OFPort.of(2)));
		path30.add(new NodePortTuple(switches[1], OFPort.of(1)));
		path30.add(new NodePortTuple(switches[1], OFPort.of(4)));
		path30.add(new NodePortTuple(switches[5], OFPort.of(2)));
		routesConfig.add(new RouteConfig(switches[3], switches[5], path30));

		// path31 l3 -> l1 : sciezka przez s1 l2 s2
		List<NodePortTuple> path31 = new ArrayList<>();
		path31.add(new NodePortTuple(switches[4], OFPort.of(1)));
		path31.add(new NodePortTuple(switches[0], OFPort.of(3)));
		path31.add(new NodePortTuple(switches[0], OFPort.of(2)));
		path31.add(new NodePortTuple(switches[3], OFPort.of(1)));
		path31.add(new NodePortTuple(switches[3], OFPort.of(2)));
		path31.add(new NodePortTuple(switches[1], OFPort.of(2)));
		path31.add(new NodePortTuple(switches[1], OFPort.of(1)));
		path31.add(new NodePortTuple(switches[2], OFPort.of(2)));
		routesConfig.add(new RouteConfig(switches[4], switches[2], path31));

		// path32 l3 -> l2 : sciezka przez s1 l4 s2
		List<NodePortTuple> path32 = new ArrayList<>();
		path32.add(new NodePortTuple(switches[4], OFPort.of(1)));
		path32.add(new NodePortTuple(switches[0], OFPort.of(3)));
		path32.add(new NodePortTuple(switches[0], OFPort.of(4)));
		path32.add(new NodePortTuple(switches[5], OFPort.of(1)));
		path32.add(new NodePortTuple(switches[5], OFPort.of(2)));
		path32.add(new NodePortTuple(switches[1], OFPort.of(4)));
		path32.add(new NodePortTuple(switches[1], OFPort.of(2)));
		path32.add(new NodePortTuple(switches[3], OFPort.of(2)));
		routesConfig.add(new RouteConfig(switches[4], switches[3], path32));

		// path33 l3 -> l4 : sciezka przez s1 l1 s2
		List<NodePortTuple> path33 = new ArrayList<>();
		path33.add(new NodePortTuple(switches[4], OFPort.of(1)));
		path33.add(new NodePortTuple(switches[0], OFPort.of(3)));
		path33.add(new NodePortTuple(switches[0], OFPort.of(1)));
		path33.add(new NodePortTuple(switches[2], OFPort.of(1)));
		path33.add(new NodePortTuple(switches[2], OFPort.of(2)));
		path33.add(new NodePortTuple(switches[1], OFPort.of(1)));
		path33.add(new NodePortTuple(switches[1], OFPort.of(4)));
		path33.add(new NodePortTuple(switches[5], OFPort.of(2)));
		routesConfig.add(new RouteConfig(switches[4], switches[5], path33));

		// path34 l4 -> l1 : sciezka przez s1 l2 s2
		List<NodePortTuple> path34 = new ArrayList<>();
		path34.add(new NodePortTuple(switches[5], OFPort.of(1)));
		path34.add(new NodePortTuple(switches[0], OFPort.of(4)));
		path34.add(new NodePortTuple(switches[0], OFPort.of(2)));
		path34.add(new NodePortTuple(switches[3], OFPort.of(1)));
		path34.add(new NodePortTuple(switches[3], OFPort.of(2)));
		path34.add(new NodePortTuple(switches[1], OFPort.of(2)));
		path34.add(new NodePortTuple(switches[1], OFPort.of(1)));
		path34.add(new NodePortTuple(switches[2], OFPort.of(2)));
		routesConfig.add(new RouteConfig(switches[5], switches[2], path34));

		// path35 l4 -> l2 : sciezka przez s1 l3 s2
		List<NodePortTuple> path35 = new ArrayList<>();
		path35.add(new NodePortTuple(switches[5], OFPort.of(1)));
		path35.add(new NodePortTuple(switches[0], OFPort.of(4)));
		path35.add(new NodePortTuple(switches[0], OFPort.of(3)));
		path35.add(new NodePortTuple(switches[4], OFPort.of(1)));
		path35.add(new NodePortTuple(switches[4], OFPort.of(2)));
		path35.add(new NodePortTuple(switches[1], OFPort.of(3)));
		path35.add(new NodePortTuple(switches[1], OFPort.of(2)));
		path35.add(new NodePortTuple(switches[3], OFPort.of(2)));
		routesConfig.add(new RouteConfig(switches[5], switches[3], path35));

		// path36 l4 -> l3 : sciezka przez s1 l1 s2
		List<NodePortTuple> path36 = new ArrayList<>();
		path36.add(new NodePortTuple(switches[5], OFPort.of(1)));
		path36.add(new NodePortTuple(switches[0], OFPort.of(4)));
		path36.add(new NodePortTuple(switches[0], OFPort.of(1)));
		path36.add(new NodePortTuple(switches[2], OFPort.of(1)));
		path36.add(new NodePortTuple(switches[2], OFPort.of(2)));
		path36.add(new NodePortTuple(switches[1], OFPort.of(1)));
		path36.add(new NodePortTuple(switches[1], OFPort.of(3)));
		path36.add(new NodePortTuple(switches[4], OFPort.of(2)));
		routesConfig.add(new RouteConfig(switches[5], switches[4], path36));
		
		// path37 s1 -> l1
		List<NodePortTuple> path37 = new ArrayList<>();
		path37.add(new NodePortTuple(switches[0], OFPort.of(1)));
		path37.add(new NodePortTuple(switches[2], OFPort.of(1)));
		routesConfig.add(new RouteConfig(switches[0], switches[2], path37));
		
		// path38 s1 -> l2
		List<NodePortTuple> path38 = new ArrayList<>();
		path38.add(new NodePortTuple(switches[0], OFPort.of(2)));
		path38.add(new NodePortTuple(switches[3], OFPort.of(1)));
		routesConfig.add(new RouteConfig(switches[0], switches[3], path38));
		
		// path39 s1 -> l3
		List<NodePortTuple> path39 = new ArrayList<>();
		path39.add(new NodePortTuple(switches[0], OFPort.of(3)));
		path39.add(new NodePortTuple(switches[4], OFPort.of(1)));
		routesConfig.add(new RouteConfig(switches[0], switches[4], path39));
				
		// path40 s1 -> l4
		List<NodePortTuple> path40 = new ArrayList<>();
		path40.add(new NodePortTuple(switches[0], OFPort.of(4)));
		path40.add(new NodePortTuple(switches[5], OFPort.of(1)));
		routesConfig.add(new RouteConfig(switches[0], switches[5], path40));
		
		// path41 s2 -> l1
		List<NodePortTuple> path41 = new ArrayList<>();
		path41.add(new NodePortTuple(switches[1], OFPort.of(1)));
		path41.add(new NodePortTuple(switches[2], OFPort.of(2)));
		routesConfig.add(new RouteConfig(switches[1], switches[2], path41));
		
		// path42 s2 -> l2
		List<NodePortTuple> path42 = new ArrayList<>();
		path42.add(new NodePortTuple(switches[1], OFPort.of(2)));
		path42.add(new NodePortTuple(switches[3], OFPort.of(2)));
		routesConfig.add(new RouteConfig(switches[1], switches[3], path42));
		
		// path43 s2 -> l3
		List<NodePortTuple> path43 = new ArrayList<>();
		path43.add(new NodePortTuple(switches[1], OFPort.of(3)));
		path43.add(new NodePortTuple(switches[4], OFPort.of(2)));
		routesConfig.add(new RouteConfig(switches[1], switches[4], path43));
				
		// path44 s2 -> l4
		List<NodePortTuple> path44 = new ArrayList<>();
		path44.add(new NodePortTuple(switches[1], OFPort.of(4)));
		path44.add(new NodePortTuple(switches[5], OFPort.of(2)));
		routesConfig.add(new RouteConfig(switches[1], switches[5], path44));
		
		// path45 l1 -> h1
		List<NodePortTuple> path45 = new ArrayList<>();
		path45.add(new NodePortTuple(switches[2], OFPort.of(3)));
		routesConfig.add(new RouteConfig(switches[2], switches[2], path45));
		
		// path46 l2 -> h2
		List<NodePortTuple> path46 = new ArrayList<>();
		path46.add(new NodePortTuple(switches[3], OFPort.of(3)));
		routesConfig.add(new RouteConfig(switches[3], switches[3], path46));
		
		// path47 l3 -> h3
		List<NodePortTuple> path47 = new ArrayList<>();
		path47.add(new NodePortTuple(switches[4], OFPort.of(3)));
		routesConfig.add(new RouteConfig(switches[4], switches[4], path47));
		
		// path48 l4 -> h4
		List<NodePortTuple> path48 = new ArrayList<>();
		path48.add(new NodePortTuple(switches[5], OFPort.of(3)));
		routesConfig.add(new RouteConfig(switches[5], switches[5], path48));
	}

	public ArrayList<Route> checkRoutes(DatapathId src, DatapathId dst) {
		ArrayList<Route> allRoutes = new ArrayList<>();
		for (RouteConfig routeConfig : routesConfig) {
			if (routeConfig.src.equals(src) && routeConfig.dst.equals(dst)) {
				List<NodePortTuple> path = new ArrayList<>(routeConfig.paths);

				RouteId routeId = new RouteId(src, dst);
				Route route = new Route(routeId, path);
				allRoutes.add(route);
			}
		}

		return allRoutes;
	}

	private static class RouteConfig {
		DatapathId src;
		DatapathId dst;
		List<NodePortTuple> paths;

		RouteConfig(DatapathId src, DatapathId dst, List<NodePortTuple> paths) {
			this.src = src;
			this.dst = dst;
			this.paths = paths;
		}
	}
}