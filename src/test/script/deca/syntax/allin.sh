#! /bin/sh
cd "$(dirname "$0")"|| exit 1

cd valid
./allin.sh
cd ..
cd invalid
./allin.sh
cd ..
