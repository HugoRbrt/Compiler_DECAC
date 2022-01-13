#! /bin/sh
cd "$(dirname "$0")"|| exit 1
./exec-tests-SansObjet.sh
./regression-tests-SansObjet.sh
