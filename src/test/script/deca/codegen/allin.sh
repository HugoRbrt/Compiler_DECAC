#! /bin/sh
cd "$(dirname "$0")"|| exit 1

exitnum=0

cd valid
./allin.sh
exitnum=$(($exitnum + $?))

cd ..
cd invalid
./allin.sh
exitnum=$(($exitnum + $?))
cd ..


exit $exitnum
