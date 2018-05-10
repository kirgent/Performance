#!/bin/bash
java -ea -Dfile.encoding=UTF-8 -classpath \
./target/test-classes:\
./target/classes:\
./lib/* \
com.intellij.rt.execution.junit.JUnitStarter -ideVersion5 -junit5 tv.zodiac.dev.testAMS_Reminder_Add_Modify_Delete_Purge__average,test2_Add_Modify_Purge___average