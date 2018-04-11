import org.junit.jupiter.api.RepeatedTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * We are as Middle: chain of requests: localhost -> AMS -> box -> AMS -> localhost (Middle)
 */
class testAMS_Reminder_Add_Modify_Delete extends API {

    private API_AMS AMS = new API_AMS();

    @RepeatedTest(10)
    void testAdd_Modify_Delete() throws IOException {
        int count_reminders = 1;
        int reminderChannelNumber = reminderChannelNumber();
        int reminderOffset_new = 10;
        Random random = new Random();
        Random random2 = new Random();
        long reminderScheduleId = Math.abs(random.nextLong());
        long reminderId = Math.abs(random2.nextLong());

        ArrayList actual;
        actual = AMS.Request(macaddress, Operation.add, count_reminders,
                reminderProgramStart(), reminderChannelNumber,
                reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(macaddress, Operation.modify, count_reminders,
                reminderProgramStart(), reminderChannelNumber,
                reminderProgramId, reminderOffset_new, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(macaddress, Operation.delete, count_reminders, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @RepeatedTest(1)
    void testAdd_Modify_Delete288() throws IOException {
        int count_reminders = 288;
        int reminderChannelNumber = reminderChannelNumber();
        Random random = new Random();
        Random random2 = new Random();
        long reminderScheduleId = Math.abs(random.nextLong());
        long reminderId = Math.abs(random2.nextLong());

        ArrayList actual = AMS.Request(macaddress, Operation.add, count_reminders, reminderProgramStart(),
                reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(macaddress, Operation.modify, count_reminders, reminderProgramStart(),
                reminderChannelNumber, reminderProgramId, reminderOffset_new, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(macaddress, Operation.delete, count_reminders, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @RepeatedTest(1)
    void testAdd_Modify_Delete720() throws IOException {
        int count_reminders = 720;
        int reminderChannelNumber = reminderChannelNumber();
        Random random = new Random();
        Random random2 = new Random();
        long reminderScheduleId = Math.abs(random.nextLong());
        long reminderId = Math.abs(random2.nextLong());

        ArrayList actual = AMS.Request(macaddress, Operation.add, count_reminders, reminderProgramStart(),
                reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(macaddress, Operation.modify, count_reminders, reminderProgramStart(),
                reminderChannelNumber, reminderProgramId, reminderOffset_new, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(macaddress, Operation.delete, count_reminders, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

}
