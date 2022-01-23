#!/bin/sh

# Author : Paul
# Initial version : 16/1/2022
# Usage : ./rename-tmp.sh [res | lis | ass | s]
# Script to replace all files with *.<suffix>tmp to *<suffix> to validate
# To be executed in the script directory

cd "$(dirname "$0")" || exit 1

for f in ../tmp/*."$1"tmp; do
    mv -- "$f" "${f%."$1"tmp}."$1""
done
