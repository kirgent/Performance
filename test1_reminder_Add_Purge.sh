#!/bin/bash
java -ea -Dfile.encoding=UTF-8 -classpath \
./target/test-classes:\
./target/classes:\
./libs/* \
com.intellij.rt.execution.junit.JUnitStarter \
-ideVersion5 \
-junit5 \
tv.zodiac.dev.testAMS_newAPI_Performance,\
test1_Add_Purge\
\(java.lang.String,java.lang.String,java.lang.String,int,int,int\)