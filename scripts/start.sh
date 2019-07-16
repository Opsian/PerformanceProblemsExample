#!/bin/sh

set -eu

KEY=$1
NAME=$2
OPSIAN_AGENT=$3

mvn package -DskipTests

java \
-Xmx1024M \
-agentpath:"${OPSIAN_AGENT}apiKey=$KEY,debugLogPath=opsian-debug.log,agentId=$NAME,applicationVersion=$NAME" \
-jar target/performance-problems-example-1.3.5.jar server example.yml

