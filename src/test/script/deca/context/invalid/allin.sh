#! /bin/sh
cd "$(dirname "$0")"|| exit 1

exitnum=0

./allin-SansObjet.sh
exitnum=$(($exitnum + $?))

./allin-Objet.sh
exitnum=$(($exitnum + $?))

exit $exitnum