import org.junit.jupiter.api.RepeatedTest;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class testAMS_Reminder_Add_Delete extends API {

    private API_AMS AMS = new API_AMS();

    @RepeatedTest(1)
    void testAdd_Delete() throws IOException {
        starttime();
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.add, 10,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        System.out.println("[DBG] return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));

        actual = AMS.Request(ams_ip, macaddress, Operation.delete, 10,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @RepeatedTest(10)
    void testAdd_Delete48() throws IOException {
        starttime();
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.add, 48,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        System.out.println("[DBG] return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));

        actual = AMS.Request(ams_ip, macaddress, Operation.delete, 48,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @RepeatedTest(10)
    void testAdd_Delete288() throws IOException {
        starttime();
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.add, 288,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        System.out.println("[DBG] return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));

        actual = AMS.Request(ams_ip, macaddress, Operation.delete, 288,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @RepeatedTest(10)
    void testAdd_Delete720() throws IOException {
        starttime();
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.add, 720,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        System.out.println("[DBG] return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));

        actual = AMS.Request(ams_ip, macaddress, Operation.delete, 720,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

}
