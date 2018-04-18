import org.junit.jupiter.api.RepeatedTest;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * We are as Middle: chain of requests: localhost -> AMS -> box -> AMS -> localhost (Middle)
 */
class testAMS_Reminder_Add_Modify_Delete_Purge extends API {

    private API_AMS AMS = new API_AMS();

    @RepeatedTest(100)
    void test1_Add_Purge() throws IOException {
        int count_reminders = 5;

        ArrayList actual;
        actual = AMS.Request(mac, Operation.add, count_reminders, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(mac, Operation.purge, true);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @RepeatedTest(100)
    void test2_Add_Modify() throws IOException {
        int count_reminders = 5;
        int reminderOffset_new = reminderOffset();

        ArrayList actual;
        actual = AMS.Request(mac, Operation.add, count_reminders, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(mac, Operation.modify, count_reminders, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset_new, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @RepeatedTest(100)
    void test3_Add_Modify_Purge() throws IOException {
        int count_reminders = 5;
        int reminderOffset_new = reminderOffset();

        ArrayList actual;
        actual = AMS.Request(mac, Operation.add, count_reminders, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(mac, Operation.modify, count_reminders, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset_new, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(mac, Operation.purge, true);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @RepeatedTest(100)
    void test4_Add_Modify_Delete() throws IOException {
        int count_reminders = 5;
        int reminderOffset_new = reminderOffset();

        ArrayList actual;
        actual = AMS.Request(mac, Operation.add, count_reminders, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(mac, Operation.modify, count_reminders, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset_new, reminderScheduleId_multi, reminderId_multi);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(mac, Operation.delete, count_reminders, reminderScheduleId_multi, reminderId_multi);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @RepeatedTest(100)
    void test5_Add_Modify_Delete_Purge() throws IOException {
        int count_reminders = 5;
        int reminderOffset_new = reminderOffset();

        ArrayList actual;
        actual = AMS.Request(mac, Operation.add, count_reminders, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(mac, Operation.modify, count_reminders, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset_new, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(mac, Operation.delete, count_reminders, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(mac, Operation.purge, true);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @RepeatedTest(100)
    void test6_Add_Delete() throws IOException, InterruptedException {
        int count_reminders = 5;

        ArrayList actual;
        actual = AMS.Request(mac, Operation.add, count_reminders, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(mac, Operation.delete, count_reminders, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @RepeatedTest(100)
    void test7_Add_Delete_Purge() throws IOException, InterruptedException {
        int count_reminders = 5;

        ArrayList actual;
        actual = AMS.Request(mac, Operation.add, count_reminders, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(mac, Operation.delete, count_reminders, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(mac, Operation.purge, true);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

}
