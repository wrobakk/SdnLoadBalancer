# Hash-Based Load Balancer in SDN

## Project Overview

This project implements a load balancer based on the IP hash algorithm in a Software-Defined Networking (SDN) environment. The network topology is created using Mininet and controlled by the Floodlight controller. The goal of the project is to test how IP hash–based load balancing works in an SDN network.


## Load Balancing Principle
The load balancing algorithm is executed during Packet-In processing in the SDN controller.

1. A packet without a matching flow entry triggers a Packet-In event and is sent to the controller.
2. The controller extracts basic flow attributes from the packet:
   source and destination IP addresses, transport protocol, source and destination ports (for TCP/UDP), and an HTTP indicator.
3. A hash value is computed from the extracted attributes to uniquely identify the flow.
4. The controller determines all available paths between the source and destination switches.
5. One path is selected by mapping the hash value to the set of available paths using a modulo operation.
6. OpenFlow rules are installed along the selected path so that subsequent packets of the same flow are forwarded directly throw this route.


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
```
## REST API – Host Configuration
Host configuration is provided to the controller using a REST API endpoint.  
The configuration is sent as a JSON in the body of an HTTP POST request.

To upload the host configuration, execute the following command:

```bash
curl -X POST -H "Content-Type: application/json" -d @hosts_config.json http://<controller_ip>:8080/sdnlab/hosts

```
## Traffic Generation
Traffic is generated using a Python script based on the Scapy library.

To start traffic generation, execute the traffic generator script from within a Mininet host terminal (e.g., `h1`):

```bash
python path/to/traffic_generator1.py
```
## Flow Table Inspection
To inspect the flow tables installed on a switch, use the following command:

```bash
sudo ovs-ofctl dump-flows <switch_name>
```
## Authors 
- Piotr Kostrzewa
- Wojciech Robak
- Filip Jemioło
-  Piotr Karcz

## Literature 
1. **Nasution, R. A., Siregar, H., & Sihotang, H. T.** [*Analysis of Load Balancing Performance using Round Robin and IP Hash Algorithm on P4.*](https://ieeexplore.ieee.org/document/10052975)
2. **Lin Li, Qiaozhi Xu.** [*Load Balancing Researches in SDN: A Survey.*](https://ieeexplore.ieee.org/document/8076592)
3. **I Putu Adhi Suwandika, Muhammad Arief Nugroho, Maman Abdurahman.** [*Increasing SDN Network Performance Using Load Balancing Scheme on Web Server.*](https://ieeexplore.ieee.org/document/8528803)
4. **Evans Osei Kofi, Emmanuel Ahene.** [*Enhanced Network Load Balancing Technique for Efficient Performance in Software Defined Network.*](https://journals.plos.org/plosone/article?id=10.1371%2Fjournal.pone.0284176)
