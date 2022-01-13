#! /bin/sh
cd "$(dirname "$0")"|| exit 1
./exec-tests-HelloWorld.sh
./regression-tests-HelloWorld.sh
