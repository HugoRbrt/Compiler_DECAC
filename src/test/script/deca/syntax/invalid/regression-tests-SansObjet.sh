#! /bin/sh

cd "$(dirname "$0")"/../../.. || exit 1
./Regression-test.sh synt invalid SansObjet

exit $?