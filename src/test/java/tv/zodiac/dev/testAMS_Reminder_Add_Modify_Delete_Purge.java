package tv.zodiac.dev;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.RepeatedTest;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * We are Headend (on localhost): chain of requests: localhost -> AMS -> STB -> AMS -> localhost
 */
class testAMS_Reminder_Add_Modify_Delete_Purge extends API {

    private API_AMS AMS = new API_AMS();
    final private int countrepeat = 10;
    final private int count = 1;

    @RepeatedTest(countrepeat)
    @Disabled
    void test1_Add() throws IOException {
        ArrayList actual;
        actual = AMS.Request(mac, Operation.add, count, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
        //System.out.println("[DBG] average add time: " + get_average_time(list_add_time));
    }

    @RepeatedTest(countrepeat)
    void test2_Add_Modify() throws IOException {
        ArrayList actual;
        actual = AMS.Request(mac, Operation.add, count, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset_new, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @RepeatedTest(countrepeat)
    void test3_Add_Modify_Delete() throws IOException {
        ArrayList actual;
        actual = AMS.Request(mac, Operation.add, count, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset_new, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(mac, Operation.delete, count, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @RepeatedTest(countrepeat)
    void test4_Add_Delete() throws IOException, InterruptedException {
        ArrayList actual;
        actual = AMS.Request(mac, Operation.add, count, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(mac, Operation.delete, count, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @AfterEach
    void testPurge() throws IOException {
        ArrayList actual = AMS.Request(mac, Operation.purge);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
        //System.out.println("[DBG] average purge time:" + get_average_time(list_purge_time));
    }
}
