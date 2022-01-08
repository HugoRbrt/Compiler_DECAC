#! /bin/sh
cd valid
./exec-tests.sh
cd ..
cd invalid
./exec-tests.sh
cd ..
