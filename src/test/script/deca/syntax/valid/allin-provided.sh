#! /bin/sh
cd "$(dirname "$0")"|| exit 1

exitnum=0

./exec-tests-provided.sh
exitnum=$(($exitnum + $?))

./regression-tests-provided.sh
exitnum=$(($exitnum + $?))

exit $exitnum
