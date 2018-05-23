#!/bin/bash
git status
git pull
git status
git commit -a -m "auto commit"
git status
git push
git status
java -ea -Dfile.encoding=UTF-8 -classpath \
./target/test-classes:\
./target/classes:\
./lib/* \
com.intellij.rt.execution.junit.JUnitStarter \
-ideVersion5 \
-junit5 \
tv.zodiac.dev.testAMS_oldAPI_Performance,\
test11_Add_Purge\(java.lang.String,java.lang.String,java.lang.String,int,int,long,long,int\)
