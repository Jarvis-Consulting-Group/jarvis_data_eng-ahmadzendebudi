#!/bin/bash

vmstat_out=$(vmstat -s --unit M)

memory_free=$(echo "$vmstat_out" | sed -nr "s/(\w*) free memory/\1/p")

echo "$memory_free"