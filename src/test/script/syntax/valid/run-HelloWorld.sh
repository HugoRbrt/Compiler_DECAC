#! /bin/sh

# Auteur : gl49, Paul
# Version initiale : 06/01/2022

# Test minimaliste de la syntaxe et sauvegarde des résultats.
# On lance test_synt sur tous les fichiers du dossier de tests
# dont le suffixe est syntax/valid/HelloWorld
# Les résultats associés sont mis dans results/syntax/valid/HelloWorld
cd "$(dirname "$0")"/../../../../../ || exit 1

PATH=./src/test/script/launchers:"$PATH"
TESTPATH=./src/test/deca/syntax/valid/HelloWorld
LISPATH=./src/test/results/syntax/valid
TMP=./src/test/tmp

RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m'
# we will use two repositories : one with already saved
# files and another with the newly created results


# function to verify if the test is valid, if so it is stored in a
# temporary result repository
test_synt_valid () {
    # $1 = first argument : name of files without extension

    # echo $(test_synt $TESTPATH/$1.deca)  
    if test_synt $TESTPATH/$1.deca 2>&1 | head -n 1 | grep -q "$1.deca"
        then # abnormal success
            echo "${RED}[KO] : $1 ${NC}"
            exit 1
        else # normal success : we store the new result in a tmp file
            test_synt $TESTPATH/$1.deca 1> $TMP/$1.listmp 2>> $TMP/$1.listmp
            echo "${GREEN}[OK] : $1 ${NC}"
    fi
}

# function to verify that there was no regression between old results
# and new ones
no_regression_test () {
    if [ diff $LISPATH/$1.lis $TMP/$1.listmp ]
        then
            echo "${RED}[REGRESSION] : $1 ${NC}"
            exit 1
        else
            echo "${GREEN}[NO-REGRESSION] : $1 ${NC}"
    fi
}

# looping on all the targeted paths
for cas_de_test in "$TESTPATH"/*.deca
do	
    file=$(basename "$cas_de_test" ".deca")
    test_synt_valid "$file"
done
