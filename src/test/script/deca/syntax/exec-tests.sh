#! /bin/sh
cd "$(dirname "$0")"|| exit 1
cd valid
./exec-tests.sh
cd ..
cd invalid
./exec-tests.sh
cd ..
