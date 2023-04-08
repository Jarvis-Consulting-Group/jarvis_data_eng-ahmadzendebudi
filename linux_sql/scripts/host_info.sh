#!/bin/bash

psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

if [ $# -ne 5 ]; then
  echo "Illegal number of parameters"
  exit 1
fi

#Hardware specs
lscpu_out=$(lscpu)
vmstat_out=$(vmstat -s --unit K)
hostname=$(hostname -f)
cpu_number=$(echo "$lscpu_out" | grep -E "^CPU\(s\):" | awk -v col="2" '{print $col}' | xargs) 
cpu_architecture=$(echo "$lscpu_out" | grep -E "^Architecture:" | awk -v col="2" '{print $col}'| xargs)
cpu_model=$(echo "$lscpu_out" | sed -nr "s/^Model name:\s*(.*)\s*/\1/p")
cpu_mhz=$(echo "$lscpu_out" | grep -E "^CPU MHz:" | awk -v col="3" '{print $col}'| xargs)
l2_cache=$(echo "$lscpu_out" | grep -E "^L2 cache:" | awk -v col="3" '{print $col}'| sed "s/K//g" | sed "s/M/000/g")
total_mem=$(echo "$vmstat_out" | grep -E "total memory" | awk -v col="1" '{print $col}'| xargs)
timestamp=$(date -u +"%Y-%m-%d %H:%M:%S")

#Insert into host_info psql table
insert_statement="INSERT INTO public.host_info\
(hostname, cpu_number, cpu_architecture, cpu_model, cpu_mhz, l2_cache, timestamp, total_mem) \
VALUES('$hostname', '$cpu_number', '$cpu_architecture', '$cpu_model', '$cpu_mhz', '$l2_cache', '$timestamp', '$total_mem')"

export PGPASSWORD=$psql_password
psql -h "$psql_host" -p "$psql_port" -d "$db_name" -U "$psql_user" -c "$insert_statement"

exit $?