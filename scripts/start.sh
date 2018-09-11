#!/bin/sh

set -eu

KEY=$1
NAME=$2
OPSIAN_PATH=$3

cd .. && mvn package -DskipTests && cd scripts

java \
-agentpath:"$OPSIAN_PATH,apiKey=$KEY,debugLogPath=debug.log,agentId=$NAME" \
-jar ../target/performance-problems-example-1.3.5.jar server ../example.yml

