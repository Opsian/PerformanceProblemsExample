#!/bin/sh

QUERY="$1"

curl "http://localhost:7080/house/contains/$QUERY" | jq -C . | less -R

