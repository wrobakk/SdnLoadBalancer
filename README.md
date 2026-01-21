# Hash-Based Load Balancer in SDN

## Project Overview
This project presents the implementation of a simple **hash-based load balancer** in a **Software-Defined Networking (SDN)** environment.  
Network traffic originating from client hosts is distributed among multiple servers based on a **hash of the source IP address**.  
This approach ensures deterministic server selection and reduces controller overhead once flow rules are installed.

The network is emulated using **Mininet**, while forwarding decisions and load balancing logic are implemented as a custom application running on the **Floodlight SDN controller**.

---

## Project Goals
The main objectives of the project are:
- Design and implement a load balancing mechanism in SDN
- Apply IP hash–based traffic distribution
- Gain practical experience with:
  - Floodlight controller
  - OpenFlow rule installation
  - SDN-based routing and traffic control
- Analyze load balancing concepts in SDN networks

---

## Load Balancing Principle
The load balancing mechanism operates as follows:

1. A packet from a client host enters the SDN network
2. The controller extracts the source IP address
3. A hash function is applied to the IP address
4. Based on the hash value, one of the available servers is selected
5. OpenFlow rules are installed to forward subsequent packets directly in the data plane

This method provides:
- Deterministic traffic forwarding
- Low computational complexity
- Stable client-to-server mapping

---

## Network Topology
The network topology follows a **leaf–spine architecture**, which is commonly used in data center networks.

<img width="1131" height="861" alt="Network topology diagram" src="https://github.com/user-attachments/assets/62f6a4d0-b01b-4c0e-b472-2ba2f47d9482" />

Topology characteristics:
- Two spine switches forming the network core
- Multiple leaf switches connected to end hosts
- Each leaf switch is connected to all spine switches
- Multiple equal-cost paths enable efficient load distribution

The topology is defined using a custom Mininet script.

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

