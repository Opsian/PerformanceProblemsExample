#!/bin/sh

ab -c 4 -n "$1" http://localhost:7080/house/contains/cardiff

