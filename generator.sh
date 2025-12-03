#!/bin/bash

HOSTS=("10.0.0.1" "10.0.0.2" "10.0.0.3" "10.0.0.4")
SELF=$1
PORT=5001

while true; do
    DST=${HOSTS[$RANDOM % ${#HOSTS[@]}]}
    if [ "$DST" != "$SELF" ]; then
        iperf -c $DST -t 10 -p $PORT &
        echo "$SELF sending traffic to $DST on port $PORT"
    fi
    sleep 1
done
