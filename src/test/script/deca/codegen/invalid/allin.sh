#! /bin/sh
cd "$(dirname "$0")"|| exit 1

exitnum=0
# ./exec-tests-HelloWorld.sh
# exitnum=$(($exitnum + 1))

./exec-tests-SansObjet.sh
exitnum=$(($exitnum + $?))

./exec-tests-ARM-SansObjet.sh
exitnum=$(($exitnum + $?))

./exec-tests-Objet.sh
exitnum=$(($exitnum + $?))

exit $exitnum
