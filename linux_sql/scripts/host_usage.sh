#!/bin/bash

psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

if [ $# -ne 5 ]; then
  echo "Illegal number of arguments"
  exit 1
fi

# Usage info
vmstat_out=$(vmstat --unit K)
hostname=$(hostname -f)
memory_free=$(echo "$vmstat_out" | tail -1 | awk -v col="4" '{print $col}' | xargs)
cpu_idle=$(echo "$vmstat_out" | tail -1 | awk -v col="15" '{print $col}' | xargs)
cpu_kernel=$(echo "$vmstat_out" | tail -1 | awk -v col="14" '{print $col}' | xargs)
disk_io=$(echo "$vmstat_out" | tail -1 | awk -v col="10" '{print $col}' | xargs)
disk_available=$(df -BM | grep "/dev/sda2" | awk -v col="4" '{print $col}' | sed "s/M//g")
timestamp=$(date -u +"%Y-%m-%d %H:%M:%S")

# Required for psql commands
export PGPASSWORD=$psql_password

# Finding the host_id from host_info psql table
host_id_sql="SELECT id from host_info where hostname = '$hostname'"
host_id=$(psql -U "$psql_user" -h "$psql_host" -p "$psql_port" -d "$db_name" -c "$host_id_sql")

if [[ $host_id =~ "(0 rows)" ]]; then
  echo "No entity in host_info table matches hostname: $hostname"
  echo "Please insert host info in the host_info table first"
  exit 1
fi

host_id=$(echo "$host_id" | head -n 3 | tail -n 1 | xargs)

# Inserting usage info into host_usage psql table
insert_statement="INSERT INTO host_usage\
(host_id, memory_free, cpu_idle, cpu_kernel, disk_io, disk_available, timestamp) \
VALUES('$host_id', '$memory_free', '$cpu_idle', '$cpu_kernel', '$disk_io', '$disk_available', '$timestamp')"

psql -U "$psql_user" -h "$psql_host" -p "$psql_port" -d "$db_name" -c "$insert_statement"

exit $?