#!/bin/sh

set -eu

seq 1 4 | parallel 'ab -n 10 -p empty http://localhost:7080/bank/merge_accounts/{}/' | grep 'Time taken' | tail -n 1

