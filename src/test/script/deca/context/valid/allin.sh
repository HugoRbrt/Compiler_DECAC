#! /bin/sh
cd "$(dirname "$0")"|| exit 1

exitnum=0

./allin-HelloWorld.sh
exitnum=$(($exitnum + $?))

./allin-SansObjet.sh
exitnum=$(($exitnum + $?))

exit $exitnum
