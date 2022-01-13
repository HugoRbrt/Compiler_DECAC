#! /bin/sh

# Author : gl49, Teimur
# Initial version : 13/01/2022

# This program is able to execute all the invalid syntax/context tests
# for the SansObjet/HelloWorld/object stage of the project.
# Associated results will be found in ../../tmp
# under the suffixes .listmp
# To be Launched from root Projet_GL folder
# $1 = first argument : lex or synt or context
# $2 = first argument : valid or invalid
# $3 = second argument : SansObjet or HelloWorld or Objet

# go into project root
cd "$(dirname "$0")"/../../.. || exit 1


if [ "$1" = "synt" ]
    then
        folder="syntax"
elif [ "$1" = "context" ]
    then
        folder="context"
fi

# We change the paths to execute the tests from the project root.
PATH=src/test/script/launchers:"$PATH"
TESTPATH=src/test/deca/"$folder"/"$2"/"$3"
LISPATH=src/test/results/deca/"$folder"/"$2"/"$3"
TMP=src/test/tmp

# Coloring for the script.
RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m'

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

echo "Starting regression verification on "$2" "$1" tests for "$3""

# Looping on all the targeted paths.
for cas_de_test in "$TESTPATH"/*.deca
do
    # we get the filename without its extension for further use
    file=$(basename "$cas_de_test" ".deca")
    regression_test "$file"
done