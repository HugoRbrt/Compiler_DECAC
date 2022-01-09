#! /bin/sh

# Author : gl49, Paul
# Initial version : 06/01/2022

# This program is able to execute all the valid syntax tests
# for the HelloWorld step and compare them with the validated
# one found in ../../../results/deca/syntax/valid/HelloWorld
# If we find a difference, then we must identify the regression
# source.
cd "$(dirname "$0")"/../../../../../../ || exit 1

# We change the paths to execute the tests from the project root.
PATH=src/test/script/launchers:"$PATH"
TESTPATH=src/test/deca/syntax/valid/HelloWorld
LISPATH=src/test/results/deca/syntax/valid/HelloWorld
TMP=src/test/tmp

# Coloring for the script.
RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m'

# Function to verify that there was no regression between old results
# and new ones.
regression_test () {
    # $1 = first argument : name of files without extension
    
    # Checks if an old file was there.
    # echo "$LISPATH/$1.lis"
    if [ ! -f $LISPATH/$1.lis ]
        then
            echo "${RED}[NO OLD] : $1 ${NC}"
    fi

    # Checks if a new result file was created.
    # echo "$TMP/$1.listmp"
    if [ ! -f $TMP/$1.listmp ]
        then
           echo "${RED}[NO NEW] : $1 ${NC}"
    fi
		
    DIFF=$(diff $LISPATH/$1.lis $TMP/$1.listmp)
    if [ "$DIFF" != "" ]
        then
            echo "${RED}[REGRESSION] : $1 ${NC}"
        else
            echo "${GREEN}[NO-REGRESSION] : $1 ${NC}"
    fi
}

# Looping on all the targeted paths.
for cas_de_test in "$TESTPATH"/*.deca
do
# we get the filename without its extension for further use
    file=$(basename "$cas_de_test" ".deca")
    regression_test "$file"
done
