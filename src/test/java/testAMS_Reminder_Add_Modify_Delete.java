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

    @RepeatedTest(1)
    void testAdd_Modify_Delete() throws IOException {
        int count_reminders = 1;
        Random random = new Random();
        Random random2 = new Random();
        Long reminderScheduleId = Math.abs(random.nextLong());
        Long reminderId = Math.abs(random2.nextLong());

        ArrayList actual = AMS.Request(macaddress, Operation.add, count_reminders, reminderProgramStart(),
                reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(macaddress, Operation.modify, count_reminders, reminderProgramStart(),
                reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(macaddress, Operation.delete, count_reminders, reminderProgramStart(),
                reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @RepeatedTest(1)
    void testAdd_Modify_Delete720() throws IOException {
        int count_reminders = 720;
        Random random = new Random();
        Random random2 = new Random();
        Long reminderScheduleId = Math.abs(random.nextLong());
        Long reminderId = Math.abs(random2.nextLong());

        ArrayList actual = AMS.Request(macaddress, Operation.add, count_reminders, reminderProgramStart(),
                reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(macaddress, Operation.modify, count_reminders, reminderProgramStart(),
                reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(macaddress, Operation.delete, count_reminders, reminderProgramStart(),
                reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

}
