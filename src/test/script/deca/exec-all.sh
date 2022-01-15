#! /bin/sh
cd "$(dirname "$0")"|| exit 1

res=0

cd syntax
./allin.sh
res=$(($res + $?))

cd context
./allin.sh
res=$(($res + $?))

cd codegen
./allin.sh
res=$(($res + $?))

exit $res