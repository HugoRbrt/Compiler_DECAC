#! /bin/sh

# Author : gl49, Paul
# Initial version : 21/01/2022

# This program is meant to check the
# functionality of the code generation when we
# get choose the no-check option 

cd "$(dirname "$0")"/../../../../.. || exit 1

# We change the paths to execute the tests from the project root.
TESTPATH=src/test/deca/codegen/valid/Objet
RESPATH=src/test/results/deca/codegen/valid/Objet
TMP=src/test/tmp
DECAC=src/main/bin/decac

exitnum=0

# Coloring for the script.
RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m'

# Function to verify if the test is valid. If so, it will be stored in a
# temporary result repository.

test_limit() {
    # $1 = first argument : name of files without extension

    $DECAC -n $TESTPATH/$1.deca

    	if [ ! -f $TESTPATH/$1.ass ]
            then
              echo "${RED}[NO NEW .ass file] : $1 ${NC}"
              exitnum=$(($exitnum + 1))
              return
           else
              ima $TESTPATH/$1.ass > $TMP/$1.restmp
        fi


        if [ ! -f $RESPATH/$1.res ]
            then
                echo "${RED}[NO OLD .res file] : $1 ${NC}"
                exitnum=$(($exitnum + 1))
                return
        fi

        DIFF=$(diff $TMP/$1.restmp $RESPATH/$1.res)
        if [ "$DIFF" != "" ]
            then
                echo "${RED}[UNEXPECTED OUTUT] : $1 ${NC}"
                exitnum=$(($exitnum + 1))
            else
                echo "${GREEN}[EXPECTED OUTPUT] : $1 ${NC}"
                rm $TMP/$1.restmp
                rm $TESTPATH/$1.ass
        fi

}

echo "Start of limited registers generated code tests for SansObjet"

# Looping on all the targeted paths.
for cas_de_test in "$TESTPATH"/*.deca
do
# we get the filename without its extension for further use
    file=$(basename "$cas_de_test" ".deca")
    test_limit "$file"
done

exit $exitnum
