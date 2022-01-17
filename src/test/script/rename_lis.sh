#!/bin/sh

# Author : Teimur
# Initial version : 11/1/2022
# Script to replace all files with *.listmp to *lis to validate
# To be executed in the script directory

for f in ../tmp/*.listmp; do
    mv -- "$f" "${f%.listmp}.lis"
done
