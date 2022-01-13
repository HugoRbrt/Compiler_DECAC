#! /bin/sh
cd "$(dirname "$0")"|| exit 1
./regression-tests-HelloWorld.sh
./regression-tests-SansObjet.sh
