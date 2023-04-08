#!/bin/bash

table_value_search() {
  regex=$1; col=$2
  cat | grep -E "$regex" | awk -v col="$col" '{print $col}' | xargs
}

lscpu | table_value_search "^CPU\(s\)" 2