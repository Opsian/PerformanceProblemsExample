#!/bin/sh

mvn package -DskipTests

KEY=$1
NAME=$2
OPSIAN_PATH=$3

java \
-agentpath:"$OPSIAN_PATH,apiKey=$KEY,debugLogPath=debug.log,agentId=$NAME" \
-jar ../target/dropwizard-example-1.4.0-SNAPSHOT.jar server ../example.yml

