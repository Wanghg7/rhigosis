#!/bin/bash

cat <(echo "digraph {") <(cat "$1" |
grep "::=" |
sed 's/;.*$/}/' |
sed -E 's/^ */	/' |
sed 's/ *::= */->{/' |
sed -E 's/ +/,/g') <(echo "}")

