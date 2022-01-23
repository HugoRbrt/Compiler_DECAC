#! /bin/sh

# Author : gl49, Teimur
# Initial version : 13/01/2022

# This program is able to execute all the tests
# for teh codegen stage of the project.
# Associated results will be found in ../../tmp
# under the suffixes .restmp

# $1 = first argument : valid or invalid
# $2 = second argument : SansObjet or HelloWorld or Objet

cd "$(dirname "$0")"/../../../../.. || exit 1

# We change the paths to execute the tests from the project root.
TESTPATH=src/test/deca/codegen/"$1"/"$2"
RESPATH=src/test/results/deca/codegen/"$1"/"$2"
TMP=src/test/tmp
DECAC=src/main/bin/decac

exitnum=0

# Coloring for the script.
RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m'

# Function to verify if the test is valid. If so, it will be stored in a
# temporary result repository.

test_codegen() {
    # $1 = first argument : name of files without extension

    $DECAC "$TESTPATH"/$1.deca 2>&1

    # Checks if a new result file was created.
    # echo "$TMP/$1.listmp"
    if [ ! -f $TESTPATH/$1.ass ]
        then
           echo "${RED}[NO NEW .ass file] : $1 ${NC}"
           exitnum=$(($exitnum + 1))
           return
        else
            ima $TESTPATH/$1.ass >  $TMP/$1.restmp
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
            echo "${RED}[UNEXPECTED OUTPUT] : $1 ${NC}"
            exitnum=$(($exitnum + 1))
        else
            echo "${GREEN}[EXPECTED OUTPUT] : $1 ${NC}"
            rm $TMP/$1.restmp
            rm $TESTPATH/$1.ass
    fi

}

echo "Start of "$1" generated code tests for "$2""

# Looping on all the targeted paths.
for cas_de_test in "$TESTPATH"/*.deca
do
# we get the filename without its extension for further use
    file=$(basename "$cas_de_test" ".deca")
    test_codegen "$file"
done

exit $exitnum
