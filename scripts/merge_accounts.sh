#!/bin/sh

set -eu

ab -c 10 -n 1000 -p empty http://localhost:7080/bank/merge_accounts/1/

