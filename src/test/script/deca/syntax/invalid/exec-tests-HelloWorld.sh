#! /bin/sh

# Author : gl49, Paul
# Initial version : 06/01/2022

# This program is able to execute all the invalid syntax tests
# for the HelloWorld step.
# Associated results will be found in ../../tmp
# under the suffixes .listmp
cd "$(dirname "$0")"/../../../../../../ || exit 1

# We change the paths to execute the tests from the project root.
PATH=src/test/script/launchers:"$PATH"
TESTPATH=src/test/deca/syntax/invalid/HelloWorld
TMP=src/test/tmp

# Coloring for the script.
RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m'

# Function to verify if the test is invalid. If so, it will be stored in a
# temporary result repository.

test_synt_invalid () {
    # $1 = first argument : name of files without extension

    # echo $(test_synt $TESTPATH/$1.deca)
    if test_synt $TESTPATH/$1.deca 2>&1 | grep -q -e "$1.deca:[0-9][0-9]*:"
        then # normal fail
            test_synt $TESTPATH/$1.deca 1> $TMP/$1.listmp 2>> $TMP/$1.listmp
            echo "${GREEN}[KO] : $1 ${NC}"
        else # unexpected success
            echo "${RED}[OK] : $1 ${NC}"
	    exit 1
    fi
}


# looping on all the targeted paths
for cas_de_test in "$TESTPATH"/*.deca
do	
    file=$(basename "$cas_de_test" ".deca")
    test_synt_invalid "$file"
done
