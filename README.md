# Hash-Based Load Balancer in SDN

## Project Overview
This project presents the implementation of a simple **hash-based load balancer** in a **Software-Defined Networking (SDN)** environment.  
Network traffic originating from client hosts is distributed among multiple servers based on a **hash of the source IP address**.  
This approach ensures deterministic server selection and reduces controller overhead once flow rules are installed.

The network is emulated using **Mininet**, while forwarding decisions and load balancing logic are implemented as a custom application running on the **Floodlight SDN controller**.

---

## Load Balancing Principle
The load balancing algorithm is executed during Packet-In processing in the SDN controller.

1. A packet without a matching flow entry triggers a Packet-In event and is sent to the controller.
2. The controller extracts basic flow attributes from the packet:
   source and destination IP addresses, transport protocol, source and destination ports (for TCP/UDP), and an HTTP indicator.
3. A hash value is computed from the extracted attributes to uniquely identify the flow.
4. The controller determines all available paths between the source and destination switches.
5. One path is selected by mapping the hash value to the set of available paths using a modulo operation.
6. OpenFlow rules are installed along the selected path so that subsequent packets of the same flow are forwarded directly in the data plane.


---

## Network Topology
<img width="1131" height="861" alt="Network topology diagram" src="https://github.com/user-attachments/assets/62f6a4d0-b01b-4c0e-b472-2ba2f47d9482" />

The topology consists of six OpenFlow switches:
- Switches: s1, s2
- Edge switches: l1, l2, l3, l4

Each edge switch is connected to both spine switches and to a single host:
- h1 ↔ l1
- h2 ↔ l2
- h3 ↔ l3
- h4 ↔ l4

## Running the Topology
To start the Mininet topology and connect it to the Floodlight controller, run the following command:
```bash
sudo mn --custom topology_setup.py --topo mytopo --controller=remote,ip=<controller_ip>,port=6653

```
---

## Host Configuration
Host-to-switch mapping and IP addressing are defined in a JSON configuration file loaded by the controller via REST API.

Example configuration:

```json
{
  "hostList": [
    {
      "name": "h1",
      "sw": 3,
      "port": 3,
      "ip": "10.0.0.1"
    },
    {
      "name": "h2",
      "sw": 4,
      "port": 3,
      "ip": "10.0.0.2"
    },
    {
      "name": "h3",
      "sw": 5,
      "port": 3,
      "ip": "10.0.0.3"
    },
    {
      "name": "h4",
      "sw": 6,
      "port": 3,
      "ip": "10.0.0.4"
    }
  ]
}

