#! /bin/sh
cd "$(dirname "$0")"|| exit 1

exitnum=0

cd valid
./exec-tests-ARM-SansObjet.sh
exitnum=$(($exitnum + $?))

cd ..
cd invalid
./exec-tests-ARM-SansObjet.sh
exitnum=$(($exitnum + $?))

exit $exitnum
