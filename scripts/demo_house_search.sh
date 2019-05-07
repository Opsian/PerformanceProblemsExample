#!/bin/sh

curl http://localhost:7080/house/contains/cardiff | jq -C . | less -R

