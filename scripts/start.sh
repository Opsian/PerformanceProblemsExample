#!/bin/sh

set -eu

KEY=$1
NAME=$2
OPSIAN_AGENT=$3

# mvn package -DskipTests

# TODO: add application version to make it easier to demo / diff

java \
-agentpath:"${OPSIAN_AGENT}apiKey=$KEY,debugLogPath=debug.log,agentId=$NAME" \
-jar target/performance-problems-example-1.3.5.jar server example.yml

