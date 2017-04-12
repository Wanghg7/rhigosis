#!/bin/bash

cat <(echo "strict digraph {layout=neato;overlap=false;splines=true;") <(cat "$1" |
grep "::=" |
sed 's/;.*$/}/' |
sed -E 's/^ */	/' |
sed 's/ *::= */->{/' |
sed -E 's/ +/,/g') <(echo "}")

