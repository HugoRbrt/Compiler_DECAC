#! /bin/sh
cd "$(dirname "$0")"|| exit 1

exitnum=0

./regression-tests-HelloWorld.sh
exitnum=$(($exitnum + $?))

./regression-tests-SansObjet.sh
exitnum=$(($exitnum + $?))

exit $exitnum