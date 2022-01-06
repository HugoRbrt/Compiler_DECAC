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
TMP=./src/test/results/tmp

RED='tput setaf 1'
GREEN='tput setaf 2'
NC='tput sgr0'
# we will use two LISPATH repository : one with already saved
# files and another with the newly created results


# function to verify if the test is valid, if so it is stored in a
# temporary result repository
test_synt_valid () {
    # $1 = first argument
    echo '"$1".deca' # log
    if test_synt "$1".deca 2>&1 | head -n 1 | grep -q '"$1".deca'
        then # abnormal success
            echo "${RED}$1 : KO${NC}"
            exit 1
        else # normal success : we store the new result in a tmp repo
            touch "$TMP"/"$1".lis-new
            test_synt "$1".deca 1> "$TMP"/"$1".lis-new 2> "$TMP"/"$1".lis-new
            echo "${GREEN}$1 : OK${NC}"
    fi
}

# function to verify that there was no regression between old results
# and new ones
no_regression_test () {
    if [ diff $LISPATH/$1.lis $TMP/$1.lis-new ]
        then
            echo "${RED}$1 : REGRESSION${NC}"
            exit 1
        else
            echo "${GREEN}$1 : NO-REGRESSION${NC}"
    fi
}

# looping on all the targeted paths
for cas_de_test in "$TESTPATH"/*.deca
do
    test_synt_valid "$cas_de_test"
done
