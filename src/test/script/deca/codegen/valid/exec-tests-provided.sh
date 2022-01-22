#! /bin/sh

cd "$(dirname "$0")"/.. || exit 1
./Codegen-test.sh valid provided

exit $?
