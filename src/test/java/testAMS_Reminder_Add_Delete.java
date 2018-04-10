import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * We are as Middle: chain of requests: localhost -> AMS -> box -> AMS -> localhost (Middle)
 */
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class testAMS_Reminder_Add_Delete extends API {

    private API_AMS AMS = new API_AMS();

    @Test
    public void testAdd_Delete() throws IOException {
        int count_reminders = 1;
        int reminderChannelNumber = reminderChannelNumber();
        Random random = new Random();
        Random random2 = new Random();
        long reminderScheduleId = Math.abs(random.nextLong());
        long reminderId = Math.abs(random2.nextLong());

        ArrayList actual = AMS.Request(macaddress, Operation.add, count_reminders, reminderProgramStart(), reminderChannelNumber,
                reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(macaddress, Operation.delete, count_reminders, reminderProgramStart(), reminderChannelNumber,
                reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testAdd_Delete48() throws IOException {
        int count_reminders = 48;
        int reminderChannelNumber = reminderChannelNumber();
        Random random = new Random();
        Random random2 = new Random();
        long reminderScheduleId = Math.abs(random.nextLong());
        long reminderId = Math.abs(random2.nextLong());

        ArrayList actual;
        actual = AMS.Request(macaddress, Operation.add, count_reminders, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(macaddress, Operation.delete, count_reminders, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testAdd_Delete288() throws IOException {
        int count_reminders = 288;
        Random random = new Random();
        Random random2 = new Random();
        long reminderScheduleId = Math.abs(random.nextLong());
        long reminderId = Math.abs(random2.nextLong());

        ArrayList actual;
        actual = AMS.Request(macaddress, Operation.add, count_reminders, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(macaddress, Operation.delete, count_reminders, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testAdd_Delete720() throws IOException {
        int count_reminders = 720;
        Random random = new Random();
        Random random2 = new Random();
        long reminderScheduleId = Math.abs(random.nextLong());
        long reminderId = Math.abs(random2.nextLong());

        ArrayList actual;
        actual = AMS.Request(macaddress, Operation.add, count_reminders, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(macaddress, Operation.delete, count_reminders, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

}
