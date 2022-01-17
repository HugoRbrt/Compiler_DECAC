#! /bin/sh
cd "$(dirname "$0")"|| exit 1

exitnum=0

./exec-tests-Objet.sh
exitnum=$(($exitnum + $?))

./regression-tests-Objet.sh
exitnum=$(($exitnum + $?))

exit $exitnum