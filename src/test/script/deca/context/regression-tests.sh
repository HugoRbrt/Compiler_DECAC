#! /bin/sh
cd "$(dirname "$0")"|| exit 1
cd valid
./regression-tests.sh
cd ..
cd invalid
./regression-tests.sh
cd ..
