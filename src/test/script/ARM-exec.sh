# /bin/sh

# Author : gl49, Teimur, Paul
# Initial version : 10/1/2022
# Script to replace the long use of Arm cross compilation
# So that we can execute ARM programs as easily as IMA ones


# Please export this executable in your path to use it easily after that

dir="$(dirname "$1")"
file="$(basename "$1" ".s")"
arm-linux-gnueabihf-as -march="armv6+fp"  -mfloat-abi=soft  -mfpu=softvfp -g $dir/$file.s -o $dir/$file.o || exit 1
arm-linux-gnueabihf-gcc  -mfloat-abi=hard -g $dir/$file.o -o $dir/$file -lc -static || exit 1
./$dir/$file
rm $dir/$file.o
