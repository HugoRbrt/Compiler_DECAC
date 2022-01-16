#! /bin/sh
cd "$(dirname "$0")"|| exit 1

exitnum=0

cd valid
./exec-tests.sh
exitnum=$(($exitnum + $?))

cd ..
cd invalid
./exec-tests.sh
exitnum=$(($exitnum + $?))
cd ..

exit $exitnum
