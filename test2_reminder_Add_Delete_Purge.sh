#!/bin/bash
java -ea -Dfile.encoding=UTF-8 -classpath \
./target/test-classes:\
./target/classes:\
./lib/* \
com.intellij.rt.execution.junit.JUnitStarter \
-ideVersion5 \
-junit5 \
tv.zodiac.dev.testAMS_newAPI_Performance,\
test2_Add_Delete_Purge\
\(java.lang.String,java.lang.String,java.lang.String,int,int,int,int,int\)
