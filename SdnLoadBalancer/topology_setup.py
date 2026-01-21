from mininet.topo import Topo
from mininet.net import Mininet
from mininet.node import Controller
from mininet.cli import CLI

def int2dpid(dpid):
    try:
        dpid = hex(dpid)[2:]
        dpid = '0' * (16 - len(dpid)) + dpid
        return dpid
    except IndexError:
        raise Exception('Unable to derive default datapath ID - '
                         'please either specify a dpid or use a '
                         'canonical switch name such as s23.')
class MyTopo(Topo):
    def __init__(self):
        Topo.__init__(self)
        # Add switches and hosts
        s1 = self.addSwitch('s1', dpid=int2dpid(1))
        s2 = self.addSwitch('s2', dpid=int2dpid(2))
        l1 = self.addSwitch('l1', dpid=int2dpid(3))
        l2 = self.addSwitch('l2', dpid=int2dpid(4))
        l3 = self.addSwitch('l3', dpid=int2dpid(5))
        l4 = self.addSwitch('l4', dpid=int2dpid(6))
        h1 = self.addHost('h1')
        h2 = self.addHost('h2')
        h3 = self.addHost('h3')
        h4 = self.addHost('h4')

        # Add links
        for leaf in [l1, l2, l3, l4]:
            self.addLink(leaf, s1)
            self.addLink(leaf, s2)

        self.addLink(l1, h1)
        self.addLink(l2, h2)
        self.addLink(l3, h3)
        self.addLink(l4, h4)

        self.host_ip_map = {
            h1: '10.0.0.1',
            h2: '10.0.0.2',
            h3: '10.0.0.3',
            h4: '10.0.0.4'
        }

        print("Topology created, waiting for network to start...")

# Create the Mininet network
topos = {'mytopo': (lambda: MyTopo())}