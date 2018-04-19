import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.RepeatedTest;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * We are as Middle: chain of requests: localhost -> AMS -> box -> AMS -> localhost (Middle)
 */
class testAMS_Reminder_Add_Modify_Delete_Purge extends API {

    private API_AMS AMS = new API_AMS();
    final private int repeatcount = 100;
    final private int count = 100;

    @RepeatedTest(10)
    void test1_Add() throws IOException {
        ArrayList actual;
        actual = AMS.Request(mac, Operation.add, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        testPurge();
    }

    @RepeatedTest(repeatcount)
    void test2_Add_Purge() throws IOException {
        ArrayList actual;
        actual = AMS.Request(mac, Operation.add, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(mac, Operation.purge);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @RepeatedTest(repeatcount)
    void test3_Add_Modify() throws IOException {
        ArrayList actual;
        actual = AMS.Request(mac, Operation.add, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset_new, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @RepeatedTest(repeatcount)
    void test4_Add_Modify_Purge() throws IOException {
        ArrayList actual;
        actual = AMS.Request(mac, Operation.add, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset_new, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(mac, Operation.purge);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @RepeatedTest(100)
    void test4_Add_Modify_Delete() throws IOException {
        //int reminderChannelNumber = 164;
        ArrayList actual;
        actual = AMS.Request(mac, Operation.add, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset_new, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(mac, Operation.delete, count, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @RepeatedTest(repeatcount)
    void test5_Add_Modify_Delete_Purge() throws IOException {
        ArrayList actual;
        actual = AMS.Request(mac, Operation.add, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset_new, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(mac, Operation.delete, count, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(mac, Operation.purge);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @RepeatedTest(repeatcount)
    void test6_Add_Delete() throws IOException, InterruptedException {
        ArrayList actual;
        actual = AMS.Request(mac, Operation.add, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(mac, Operation.delete, count, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @RepeatedTest(repeatcount)
    void test7_Add_Delete_Purge() throws IOException, InterruptedException {
        ArrayList actual;
        actual = AMS.Request(mac, Operation.add, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(mac, Operation.delete, count, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(mac, Operation.purge);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Disabled
    void testPurge() throws IOException {
        ArrayList actual = AMS.Request(mac, Operation.purge);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }
}
