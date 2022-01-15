#! /bin/sh
cd "$(dirname "$0")"|| exit 1

exitnum=0

./exec-tests-HelloWorld.sh
exitnum=$(($exitnum + $?))

./exec-tests-SansObjet.sh
exitnum=$(($exitnum + $?))

exit $exitnum