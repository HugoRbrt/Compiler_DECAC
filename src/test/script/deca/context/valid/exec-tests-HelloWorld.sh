#! /bin/sh

# Author : gl49, Paul,Teimur
# Initial version : 08/01/2022

# This program is able to execute all the valid context tests
# for the HelloWorld step.
# Associated results will be found in ../../tmp
# under the suffixes .listmp
cd "$(dirname "$0")"/../../../../../../ || exit 1

# We change the paths to execute the tests from the project root.
PATH=src/test/script/launchers:"$PATH"
TESTPATH=src/test/deca/context/valid/HelloWorld
TMP=src/test/tmp

# Coloring for the script.
RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m'

# Function to verify if the test is valid. If so, it will be stored in a
# temporary result repository.

test_context_valid () {
    # $1 = first argument : name of files without extension

    # echo $(test_synt $TESTPATH/$1.deca)  
    if test_context $TESTPATH/$1.deca 2>&1 | head -n 1 | grep -q "$1.deca"
        then 	# unexpected fail
            echo "${RED}[KO] : $1 ${NC}"
            exit 1
        else 	# normal success : we store the new result in a tmp file
            test_context $TESTPATH/$1.deca 1> $TMP/$1.listmp 2>> $TMP/$1.listmp
            echo "${GREEN}[OK] : $1 ${NC}"
    fi
}


# Looping on all the targeted paths.
for cas_de_test in "$TESTPATH"/*.deca
do
# we get the filename without its extension for further use
    file=$(basename "$cas_de_test" ".deca")
    test_context_valid "$file"
done
