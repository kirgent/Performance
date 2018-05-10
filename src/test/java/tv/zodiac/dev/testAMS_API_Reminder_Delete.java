package tv.zodiac.dev;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * We are Headend (on localhost): chain of requests: Headend(localhost) -> AMS -> STB -> AMS -> localhost
 */
class testAMS_API_Reminder_Delete extends API {

    private NEWAPI_AMS AMS = new NEWAPI_AMS();
    final private int countrepeat = 10;
    final private int count = 2;

    @RepeatedTest(countrepeat)
    void testDelete() throws IOException {
        ArrayList actual;
        actual = AMS.request(mac, Operation.add, count, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.request(mac, Operation.delete, count, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    void testDelete__statusCode3() throws IOException {
        //todo
        int count = 1;
        ArrayList actual = AMS.request(mac, Operation.delete, count, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("3", actual.get(1));
    }

    @Test
    void testDelete_count_is_0() throws IOException {
        int count = 0;
        ArrayList actual = AMS.request(mac, Operation.delete, count, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: wrong number of reminders", actual.get(1));
    }

    @Test
    void testDelete_macaddress_empty() throws IOException {
        //todo
        int count = 1;
        ArrayList actual = AMS.request("", Operation.delete, count, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: wrong deviceId", actual.get(1));
    }

    @Test
    void testDelete_macaddress_wrong_unknown_MAC() throws IOException {
        //todo
        int count = 1;
        ArrayList actual = AMS.request(mac_wrong, Operation.delete, count, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("unknown MAC", actual.get(1));
    }

    @Test
    void testDelete_macaddress_wrong() throws IOException {
        //todo
        int count = 1;
        ArrayList actual = AMS.request(mac_wrong, Operation.delete, count, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("unknown MAC", actual.get(1));
    }

    @Test
    void testDelete_reminderId_0() throws IOException {
        //todo
        int count = 1;
        ArrayList actual = AMS.request(mac, Operation.delete, count, reminderScheduleId, 0);
        assertEquals(expected200, actual.get(0));
        assertEquals("4", actual.get(1));
    }

    @Test
    void testDelete_reminderId_empty() throws IOException {
        //todo
        int count = 1;
        ArrayList actual = AMS.request(mac, Operation.delete, count, reminderScheduleId, -1);
        assertEquals(expected500, actual.get(0));
        assertEquals("name cannot be null", actual.get(1));
    }

    @RepeatedTest(countrepeat)
    void testDelete_reminderId_MAX_VALUE() throws IOException {
        //todo
        int count = 1;
        ArrayList actual;
        actual = AMS.request(mac, Operation.add, count, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, Long.MAX_VALUE);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.request(mac, Operation.delete, count, reminderScheduleId, Long.MAX_VALUE);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    void testDelete_reminderId_MIN_VALUE() throws IOException {
        //todo
        int count = 1;
        ArrayList actual = AMS.request(mac, Operation.delete, count, reminderScheduleId, Long.MIN_VALUE);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: incorrect reminderId", actual.get(1));
    }

    @Test
    void testDelete_reminderScheduleId_0() throws IOException {
        //todo
        int count = 1;
        ArrayList actual = AMS.request(mac, Operation.delete, count, 0, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("4", actual.get(1));
    }

    @Test
    void testDelete_reminderScheduleId_0_reminderId_0() throws IOException {
        //todo
        int count = 1;
        ArrayList actual = AMS.request(mac, Operation.delete, count, 0, 0);
        assertEquals(expected200, actual.get(0));
        assertEquals("4", actual.get(1));
    }

    @Test
    void testDelete_reminderScheduleId_empty() throws IOException {
        //todo
        int count = 1;
        ArrayList actual = AMS.request(mac, Operation.delete, count, -1, reminderId);
        assertEquals(expected500, actual.get(0));
        assertEquals("name cannot be null", actual.get(1));
    }

    /** 4 - reminder is unknown. Applies to "Reminders Delete" request (request ID=1) and "Reminders Modify" request (request ID=2)
     * @throws IOException - TBD
     */
    @Test
    void testDelete_reminderScheduleId_empty_reminderId_empty() throws IOException {
        //todo
        int count = 1;
        ArrayList actual = AMS.request(mac, Operation.delete, count, -1, -1);
        assertEquals(expected500, actual.get(0));
        assertEquals("name cannot be null", actual.get(1));
    }

    @RepeatedTest(countrepeat)
    void testDelete_reminderScheduleId_MAX_VALUE() throws IOException {
        //todo
        int count = 1;
        ArrayList actual;
        actual = AMS.request(mac, Operation.add, count, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, Long.MAX_VALUE, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.request(mac, Operation.delete, count, Long.MAX_VALUE, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @RepeatedTest(countrepeat)
    void testDelete_reminderScheduleId_MAX_VALUE_reminderId_MAX_VALUE() throws IOException {
        //todo
        int count = 1;
        ArrayList actual;
        actual = AMS.request(mac, Operation.add, count, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, Long.MAX_VALUE, Long.MAX_VALUE);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.request(mac, Operation.delete, count, Long.MAX_VALUE, Long.MAX_VALUE);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    void testDelete_reminderScheduleId_MIN_VALUE() throws IOException {
        //todo
        int count = 1;
        ArrayList actual = AMS.request(mac, Operation.delete, count, Long.MIN_VALUE, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: incorrect reminderScheduleId", actual.get(1));
    }

    @Test
    void testDelete_reminderScheduleId_MIN_VALUE_reminderId_MIN_VALUE() throws IOException {
        //todo
        int count = 1;
        ArrayList actual = AMS.request(mac, Operation.delete, count, Long.MIN_VALUE, Long.MIN_VALUE);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: incorrect reminderScheduleId", actual.get(1));
    }

}
