#! /bin/sh

cd "$(dirname "$0")"/../../.. || exit 1
./Launch-test.sh context valid provided

exit $?
