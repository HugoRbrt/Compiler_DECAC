#! /bin/sh
cd "$(dirname "$0")"|| exit 1

res=0

cd syntax
./allin.sh
res=$(($res + $?))
cd ..

cd context
./allin.sh
res=$(($res + $?))
cd ..

cd codegen
./allin.sh
res=$(($res + $?))

exit $res