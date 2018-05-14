#!/bin/bash
java -ea -Dfile.encoding=UTF-8 -classpath \
../../../target/test-classes:\
../../../target/classes:\
./lib/* \
tv.zodiac.dev.Main