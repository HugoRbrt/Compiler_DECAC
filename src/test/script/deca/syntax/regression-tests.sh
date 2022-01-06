#! /bin/sh
cd valid
./regression-tests.sh
cd ..
cd invalid
./regression-tests.sh
cd ..
