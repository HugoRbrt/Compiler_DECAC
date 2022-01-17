#! /bin/sh

# Author : gl49, Troy
# Initial version : 12/01/2022

# This program is able to check the idempotence of decompiling
# We decompile a .deca file , then we decompile the result of the first decompilation
# and then we check to see if they have the same output
# this test is run on all the valid SansObjet tests that are created
cd "$(dirname "$0")"/../../.. || exit 1

# We change the paths to execute the tests from the project root.
PATH=src/test/script/launchers:"$PATH"
TESTPATH1=src/test/deca/syntax/valid/SansObjet
TESTPATH2=src/test/deca/context/valid/SansObjet
TESTPATH3=src/test/deca/codegen/valid/SansObjet
TMP=src/test/tmp

exitnum=0

# Coloring for the script.
RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m'

test_decompile () {
    src/main/bin/decac -p $2/$1.deca 1> $TMP/$1.deca
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

for deca_file in "$TESTPATH1"/*.deca
do
    file=$(basename "$deca_file" ".deca")
    test_decompile "$file" "$TESTPATH1"
done

for deca_file in "$TESTPATH2"/*.deca
do
    file=$(basename "$deca_file" ".deca")
    test_decompile "$file" "$TESTPATH2"
done

for deca_file in "$TESTPATH3"/*.deca
do
    file=$(basename "$deca_file" ".deca")
    test_decompile "$file" "$TESTPATH3"
done

exit $exitnum