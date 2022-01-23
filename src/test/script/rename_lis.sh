#!/bin/sh

# Author : Teimur
# Initial version : 11/1/2022
# Script to replace all files with *.listmp to *lis to validate

cd "$(dirname "$0")" || exit 1

for f in ../tmp/*.listmp; do
    mv -- "$f" "${f%.listmp}.lis"
done
