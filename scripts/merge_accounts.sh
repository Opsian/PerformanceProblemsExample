#!/bin/sh

set -eu

seq 1 10 | parallel 'ab -n 10 -p empty http://localhost:7080/bank/merge_accounts/{}/'

