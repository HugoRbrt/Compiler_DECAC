#! /bin/sh
cd "$(dirname "$0")"|| exit 1

exitnum=0

./exec-tests-HelloWorld.sh
exitnum=$(($exitnum + $?))

./regression-tests-HelloWorld.sh
exitnum=$(($exitnum + $?))

exit $exitnum
