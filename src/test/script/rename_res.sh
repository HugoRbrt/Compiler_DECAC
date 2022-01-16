#!/bin/sh

# Author : Paul
# Initial version : 16/1/2022
# Script to replace all files with *.restmp to *res to validate
# To be executed in the script directory

for f in ../tmp/*.restmp; do
    mv -- "$f" "${f%.restmp}.lis"
done
