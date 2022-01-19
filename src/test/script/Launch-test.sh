#! /bin/sh

# Author : gl49, Teimur
# Initial version : 13/01/2022

# This program is able to execute all the invalid tests for
# syntax/ and context/ of the HelloWorld, SansObjet and Objet
# stages of the project.
# Associated results will be found in ../../tmp
# under the suffixes .listmp
# $1 = first argument : lex or synt or context
# $2 = second argument : valid or invalid
# $3 = third argument : SansObjet or HelloWorld or Objet

# go into project root
cd "$(dirname "$0")"/../../.. || exit 1

exitnum=0

if [ "$1" = "synt" ]
    then
        folder="syntax"
elif [ "$1" = "context" ]
    then
        folder="context"
elif [ "$1" = "lex" ]
    then
	folder="syntax"
else
    echo "Usage : ./Launch-test.sh [lex|synt|context] [valid|invalid] [HelloWorld|SansObjet|Objet]"
    exit 1
fi


# We change the paths to execute the tests from the project root.
PATH=src/test/script/launchers:"$PATH"
TESTPATH=src/test/deca/"$folder"/"$2"/"$3"
TMP=src/test/tmp

# Coloring for the script.
RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m'

test_invalid () {
    # $1 = first argument : name of files without extension
    # $2 = lex or synt or context

    test_"$2" $TESTPATH/$1.deca 1> tempor.gl49 2>> tempor.gl49
    # invalid include tests are special so they were manually verified, Large-FloatLiteral was manually tested also
    if echo $TESTPATH/$1.deca 2>&1 | grep -q -e "Include" -e "Large-FloatLiteral"
        then
            test_"$2" $TESTPATH/$1.deca 1> $TMP/$1.listmp 2>> $TMP/$1.listmp
            echo "${GREEN}[KO] : $1 ${NC}"
    # Assertion error
    elif  grep -q -e "AssertionError" < tempor.gl49
        then
            echo "${RED}[KO] : $1 ${NC}"
            exitnum=$(($exitnum + 1))

    elif grep -q -e "$1.deca:[0-9][0-9]*:" < tempor.gl49
        then # normal fail
            cp tempor.gl49 $TMP/$1.listmp
            echo "${GREEN}[KO] : $1 ${NC}"
    else # unexpected success
        echo "${RED}[OK] : $1 ${NC}"
        exitnum=$(($exitnum + 1))
    fi
}

test_valid () {
    # $1 = first argument : name of files without extension
    # $2 = lex or synt or context

    test_"$2" $TESTPATH/$1.deca 1> tempor.gl49 2>> tempor.gl49
    if grep -q -e "$1.deca" -e "Error" -e "Exception"< tempor.gl49
        then 	# unexpected fail
            echo "${RED}[KO] : $1 ${NC}"
            exitnum=$(($exitnum + 1))
        else 	# normal success : we store the new result in a tmp file
            cp tempor.gl49 $TMP/$1.listmp
            echo "${GREEN}[OK] : $1 ${NC}"
    fi
}

echo "Starting "$2" "$1" tests for "$3""

# Looping on all the targeted paths.
for file_to_test in "$TESTPATH"/*.deca
do
    # we get the filename without its extension for further use
    file=$(basename "$file_to_test" ".deca")
    test_"$2" "$file" "$1"
done

rm tempor.gl49
exit $exitnum

