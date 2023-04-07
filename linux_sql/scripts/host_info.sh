#!/bin/bash

lscpu_out=$(lscpu)
vmstat_out=$(vmstat -s)

#Hardware info
hostname=$(hostname -f)
cpu_number=$(echo "$lscpu_out" | sed -nr "s/^CPU\(s\):\s*(\d*)/\1/p")
cpu_architecture=$(echo "$lscpu_out" | sed -nr "s/^Architecture:\s*(\w*)/\1/p")
cpu_model=$(echo "$lscpu_out" | sed -nr "s/^Model name:\s*(\w*)/\1/p")
cpu_mhz=$(echo "$lscpu_out" | sed -nr "s/^CPU MHz:\s*(\w*)/\1/p")
l2_cache=$(echo "$lscpu_out" | sed -nr "s/^L2 cache:\s*(\w*)/\1/p")
total_mem=$(echo "$vmstat_out" | sed -nr "s/(\d*) K total memory/\1/p")
timestamp=$(date +"%Y-%m-%d %H:%M:%S")



