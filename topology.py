from mininet.topo import Topo
from mininet.net import Mininet
from mininet.node import Controller
from mininet.cli import CLI

class MyTopo(Topo):
    def __init__(self):
        Topo.__init__(self)
        # Add switches and hosts
        s1 = self.addSwitch('s1')
        s2 = self.addSwitch('s2')

        e1 = self.addSwitch('e1')
        e2 = self.addSwitch('e2')
        e3 = self.addSwitch('e3')
        e4 = self.addSwitch('e4')

        h1 = self.addHost('h1')
        h2 = self.addHost('h2')
        h3 = self.addHost('h3')
        h4 = self.addHost('h4')

        # Links betweens edge and core switches
        for e in [e1, e2, e3, e4]:
            self.addLink(e, s1)
            self.addLink(e, s2)

        # Links betweens edge switches and hosts
        self.addLink(e1, h1)
        self.addLink(e2, h2)
        self.addLink(e3, h3)
        self.addLink(e4, h4)

        print("Waiting to start...")

# Create the Mininet network
topos = {'mytopo': (lambda: MyTopo())}
