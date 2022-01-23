#! /bin/sh

cd "$(dirname "$0")"/.. || exit 1
./ARM-test.sh invalid SansObjet

exit $?