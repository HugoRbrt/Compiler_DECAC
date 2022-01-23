#! /bin/sh

# Author : gl49, Teimur
# Initial version : 13/01/2022

# This program is able to compile execute and compare the result of
# a deca compilation to ARM
# for the codegen stage of the project.
# Associated results will be found in ../../tmp
# under the suffixes .ARMrestmp

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

test_arm() {
    # $1 = first argument : name of files without extension

    # We dont treat overflow (arithmetic not stack) for ARM
    if echo "$1" | grep -q -e "OV" -e "Overflow"
        then
            return
    fi

    $DECAC -a "$TESTPATH"/$1.deca 2>&1

    # Checks if a new result file was created.
    # echo "$TMP/$1.listmp"
    if [ ! -f $TESTPATH/$1.s ]
        then
           echo "${RED}[NO NEW .s file] : $1 ${NC}"
           exitnum=$(($exitnum + 1))
           return
        else
            arm-linux-gnueabihf-as -march="armv6+fp"  -mfloat-abi=soft  -mfpu=softvfp -g $TESTPATH/$1.s -o $TESTPATH/$1.o || exit 1
            arm-linux-gnueabihf-gcc  -mfloat-abi=hard -g $TESTPATH/$1.o -o $TESTPATH/$1 -lc -static || exit 1
            ./$TESTPATH/$1 >  $TMP/$1.ARMrestmp
    fi


    if [ ! -f $RESPATH/$1.ARMres ]
        then
            echo "${RED}[NO OLD .ARMres file] : $1 ${NC}"
            exitnum=$(($exitnum + 1))
            return
    fi


    DIFF=$(diff $TMP/$1.ARMrestmp $RESPATH/$1.ARMres)

    if [ "$DIFF" != "" ]
        then
            echo "${RED}[UNEXPECTED OUTUT] : $1 ${NC}"
            exitnum=$(($exitnum + 1))
        else
            echo "${GREEN}[EXPECTED OUTPUT] : $1 ${NC}"
            rm $TMP/$1.ARMrestmp
            rm $TESTPATH/$1.s
            rm $TESTPATH/$1.o
            rm $TESTPATH/$1
    fi

}

echo "Start of "$1" generated ARM code tests for "$2""

# Looping on all the targeted paths.
for cas_de_test in "$TESTPATH"/*.deca
do
# we get the filename without its extension for further use
    file=$(basename "$cas_de_test" ".deca")
    test_arm "$file"
done

exit $exitnum
