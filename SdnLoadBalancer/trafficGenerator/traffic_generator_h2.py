import random
import time
from scapy.all import send, IP, ICMP, TCP, UDP, Raw

def generate_icmp(src_ip, dst_ip):
    send(IP(src=src_ip, dst=dst_ip) / ICMP())

def generate_tcp(src_ip, dst_ip):
    send(IP(src=src_ip, dst=dst_ip) / TCP(dport=random.randint(1, 65535), sport=random.randint(1024, 65535)))

def generate_udp(src_ip, dst_ip):
    send(IP(src=src_ip, dst=dst_ip) / UDP(dport=random.randint(1, 65535), sport=random.randint(1024, 65535)) / Raw(load="Test payload"))

def generate_http(src_ip, dst_ip):
    http_request = "GET / HTTP/1.1\r\nHost: {}\r\n\r\n".format(dst_ip)
    send(IP(src=src_ip, dst=dst_ip) / TCP(dport=80, sport=random.randint(1024, 65535), flags="S") / Raw(load=http_request))

def send_random_packet():
    dst_ip_list = ["10.0.0.1", "10.0.0.3", "10.0.0.4"]
    dst_ip = random.choice(dst_ip_list)
    src_ip = "10.0.0.2"
    
    packet_type = random.choice(['ICMP', 'TCP', 'UDP', 'HTTP'])

    if packet_type == 'ICMP':
        generate_icmp(src_ip, dst_ip)
    elif packet_type == 'TCP':
        generate_tcp(src_ip, dst_ip)
    elif packet_type == 'UDP':
        generate_udp(src_ip, dst_ip)
    elif packet_type == 'HTTP':
        generate_http(src_ip, dst_ip)
    print(src_ip  +  " sending " + packet_type + " packet to " + dst_ip)

def send_packet():
    while(True):
        send_random_packet()
        time.sleep(5)

if __name__ == "__main__":
    send_packet()