import org.junit.jupiter.api.RepeatedTest;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * We are as Middle: chain of requests: localhost -> AMS -> box -> AMS -> localhost (Middle)
 */
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class testAMS_Reminder_Add_Delete extends API {

    private API_AMS AMS = new API_AMS();

    @RepeatedTest(100)
    void testAdd_Delete() throws IOException, InterruptedException {
        int count_reminders = 1;
        //int reminderChannelNumber = 305;
        int reminderChannelNumber = reminderChannelNumber();
        long reminderScheduleId = reminderScheduleId();
        long reminderId = reminderId();

        ArrayList actual = AMS.Request(macaddress, Operation.add, count_reminders,
                reminderProgramStart(), reminderChannelNumber,
                reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(macaddress, Operation.delete, count_reminders, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(macaddress, Operation.purge, true);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @RepeatedTest(10)
    void testAdd_Delete48() throws IOException {
        int count_reminders = 48;
        int reminderChannelNumber = reminderChannelNumber();
        long reminderScheduleId = reminderScheduleId();
        long reminderId = reminderId();

        ArrayList actual;
        actual = AMS.Request(macaddress, Operation.add, count_reminders,
                reminderProgramStart(), reminderChannelNumber,
                reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(macaddress, Operation.delete, count_reminders, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @RepeatedTest(10)
    void testAdd_Delete288() throws IOException {
        int count_reminders = 288;
        int reminderChannelNumber = reminderChannelNumber();
        long reminderScheduleId = reminderScheduleId();
        long reminderId = reminderId();

        ArrayList actual;
        actual = AMS.Request(macaddress, Operation.add, count_reminders,
                reminderProgramStart(), reminderChannelNumber,
                reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(macaddress, Operation.delete, count_reminders, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @RepeatedTest(10)
    void testAdd_Delete720() throws IOException {
        int count_reminders = 720;
        int reminderChannelNumber = reminderChannelNumber();
        long reminderScheduleId = reminderScheduleId();
        long reminderId = reminderId();

        ArrayList actual;
        actual = AMS.Request(macaddress, Operation.add, count_reminders,
                reminderProgramStart(), reminderChannelNumber,
                reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(macaddress, Operation.delete, count_reminders, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

}
