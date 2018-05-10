package tv.zodiac.dev;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.RepeatedTest;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * We are Headend (on localhost): chain of requests: Headend(localhost) -> AMS -> STB -> AMS -> localhost
 */
@Deprecated
class testAMS_newAPI_Reminder_Add_Modify_Delete_Purge extends API {

    private NEWAPI_AMS AMS = new NEWAPI_AMS();
    final private int count_iterations = 10;
    final private int count_reminders = 1;

    @RepeatedTest(count_iterations)
    @Disabled
    @Deprecated
    void test1_Add() throws IOException {
        ArrayList actual;
        actual = AMS.request(mac, Operation.add, count_reminders, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
        //System.out.println("[DBG] average add time: " + get_average_time(list_add_time));
    }

    @RepeatedTest(count_iterations)
    @Deprecated
    void test2_Add_Modify() throws IOException {
        ArrayList actual;
        actual = AMS.request(mac, Operation.add, count_reminders, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.request(mac, Operation.modify, count_reminders, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset_new, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @RepeatedTest(count_iterations)
    @Deprecated
    void test3_Add_Modify_Delete() throws IOException {
        ArrayList actual;
        actual = AMS.request(mac, Operation.add, count_reminders, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.request(mac, Operation.modify, count_reminders, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset_new, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.request(mac, Operation.delete, count_reminders, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @RepeatedTest(count_iterations)
    @Deprecated
    void test4_Add_Delete() throws IOException, InterruptedException {
        ArrayList actual;
        actual = AMS.request(mac, Operation.add, count_reminders, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.request(mac, Operation.delete, count_reminders, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @AfterEach
    void testPurge() throws IOException {
        ArrayList actual = AMS.request(mac, Operation.purge);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
        //System.out.println("[DBG] average purge time:" + get_average_time(list_purge_time));
    }
}
