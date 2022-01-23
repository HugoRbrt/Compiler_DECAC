#! /bin/sh
echo "Compilation without -P:"
time decac -p ../deca/context/valid/SansObjet/*.deca > /dev/null
echo "Compilation with -P:"
time decac -p -P ../deca/context/valid/SansObjet/*.deca > /dev/null
