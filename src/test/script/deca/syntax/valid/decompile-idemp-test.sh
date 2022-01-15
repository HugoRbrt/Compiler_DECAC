#! /bin/sh

# Author : gl49, Troy
# Initial version : 12/01/2022

# This program is able to execute all the valid syntax tests
# for the HelloWorld step and compare them with the validated
# one found in ../../../results/deca/context/valid/HelloWorld
# If we find a difference, then we must identify the regression
# source.
cd "$(dirname "$0")"/../../../../../../ || exit 1

# We change the paths to execute the tests from the project root.
PATH=src/test/script/launchers:"$PATH"
TESTPATH=src/test/deca/syntax/valid/SansObjet
TMP=src/test/tmp

exitnum=0

# Coloring for the script.
RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m'

test_decompile () {
    src/main/bin/decac -p $TESTPATH/$1.deca 1> $TMP/$1.deca
    src/main/bin/decac -p $TMP/$1.deca 1> $TMP/$1.decap
    DIFF=$(diff $TMP/$1.deca $TMP/$1.decap)
    if [ "$DIFF" != "" ]
        then
            echo "${RED}[NON IDEMPOTENCE] : $1 ${NC}"
            exitnum=$(($exitnum + 1))
        else
            echo "${GREEN}[IDEMPOTENCE] : $1 ${NC}"
    fi
    rm -f $TMP/$1.deca
    rm -f $TMP/$1.decap
}

for deca_file in "$TESTPATH"/*.deca
do
    file=$(basename "$deca_file" ".deca")
    test_decompile "$file"
done

exit $exitnum