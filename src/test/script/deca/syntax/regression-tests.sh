#! /bin/sh
cd "$(dirname "$0")"|| exit 1

exitnum=0

cd valid
./regression-tests.sh
exitnum=$(($exitnum + $?))

cd ..
cd invalid
./regression-tests.sh
exitnum=$(($exitnum + $?))
cd ..

exit $exitnum
