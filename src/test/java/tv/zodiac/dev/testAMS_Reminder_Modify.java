package tv.zodiac.dev;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * We are Headend (on localhost): chain of requests: Headend(localhost) -> AMS -> STB -> AMS -> localhost
 */
class testAMS_Reminder_Modify extends API {
    //"reminderChannelNumber": <new value for the DCN the reminder is set to>,
    //"reminderProgramStart": "<new value for the date/time of program the reminder is set to>",
    //"reminderProgramId": "<new value for the TMS Program ID the reminder is set to>",
    //"reminderOffset": <new value for the number of minutes before the program start the reminder should be shown>
    //"reminderScheduleId": "<series or Individual program reminder schedule reference ID>",
    //"reminderId": "<episode or Individual program reminder reference ID of a particular schedule>",

    private API_AMS AMS = new API_AMS();
    final private int countrepeat = 10;
    final private int count = 2;

    @RepeatedTest(1)
    void testModify() throws IOException {
        ArrayList actual;
        actual = AMS.Request(mac, Operation.add, count, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    /** 4 - reminder is unknown. Applies to "Reminders Delete" request (Request ID=1) and "Reminders Modify" request (Request ID=2)
     * @throws IOException - TBD
     */
    @Test
    void testModify__statusCode4() throws IOException {
        //todo!!!
        int count = 1;
        ArrayList actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("4", actual.get(1));
    }

    @Test
    void testModify_macaddress_empty() throws IOException {
        ArrayList actual = AMS.Request("", Operation.modify, 1, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: wrong deviceId", actual.get(1));
    }

    @Test
    void testModify_macaddress_wrong() throws IOException {
        ArrayList actual = AMS.Request(mac_wrong, Operation.modify, 1, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("REM-ST-001 Box is not registered", actual.get(1));
    }

    @Test
    void testModify_count_is_0() throws IOException {
        ArrayList actual = AMS.Request(mac, Operation.modify, 0, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: wrong number of reminders", actual.get(1));
    }

    @Test
    void testModify_reminderChannelNumber_0__statusCode3() throws IOException {
        //todo!!!
        int count = 1;
        ArrayList actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart, 0, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("3", actual.get(1));
    }

    @Test
    void testModify_reminderChannelNumber_empty() throws IOException {
        //todo!!!
        int count = 1;
        ArrayList actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart, -1, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected500, actual.get(0));
        assertEquals("name cannot be null", actual.get(1));
    }

    @Test
    void testModify_reminderChannelNumber_LONG_MAX_VALUE() throws IOException {
        //todo
        int count = 1;
        ArrayList actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart, Long.MAX_VALUE, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected500, actual.get(0));
        assertEquals("name cannot be null", actual.get(1));
    }

    /** 4 - reminder is unknown. Applies to "Reminders Delete" request (Request ID=1) and "Reminders Modify" request (Request ID=2)
     * @throws IOException - TBD
     */
    @Test
    void testModify_reminderChannelNumber_MAX_VALUE__statusCode4() throws IOException {
        //todo
        int count = 1;
        ArrayList actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart, Integer.MAX_VALUE, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("4", actual.get(1));
    }

    @Test
    void testModify_reminderChannelNumber_MIN_VALUE() throws IOException {
        //todo!!!
        int count = 1;
        ArrayList actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart, Integer.MIN_VALUE, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: invalid channel number", actual.get(1));
    }

    /** 4 - reminder is unknown. Applies to "Reminders Delete" request (Request ID=1) and "Reminders Modify" request (Request ID=2)
     * @throws IOException - TBD
     */
    @Test
    void testModify_reminderId_0__statusCode4() throws IOException {
        //todo
        int count = 1;
        ArrayList actual;
        actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, 0);
        assertEquals(expected200, actual.get(0));
        assertEquals("4", actual.get(1));
    }

    @Test
    void testModify_reminderId_empty() throws IOException {
        //todo
        int count = 1;
        ArrayList actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, -1);
        assertEquals(expected500, actual.get(0));
        assertEquals("name cannot be null", actual.get(1));
    }

    @RepeatedTest(countrepeat)
    @Deprecated
    void testModify_reminderId_MAX_VALUE() throws IOException {
        ArrayList actual;
        actual = AMS.Request(mac, Operation.add, count, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, Long.MAX_VALUE);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset(), reminderScheduleId, Long.MAX_VALUE);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
        testPurge();
    }

    @Test
    void testModify_reminderId_MIN_VALUE() throws IOException {
        //todo
        int count = 1;
        ArrayList actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, Long.MIN_VALUE);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: incorrect reminderId", actual.get(1));
    }

    @Test
    void testModify_reminderOffset_empty() throws IOException {
        //todo!!!
        int count = 1;
        ArrayList actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart, reminderChannelNumber, reminderProgramId, -1, reminderScheduleId, reminderId);
        assertEquals(expected500, actual.get(0));
        assertEquals("name cannot be null", actual.get(1));
    }

    @Test
    void testModify_reminderOffset_LONG_MAX_VALUE() throws IOException {
        //todo!!!
        int count = 1;
        ArrayList actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart, reminderChannelNumber, reminderProgramId, Long.MAX_VALUE, reminderScheduleId, reminderId);
        assertEquals(expected500, actual.get(0));
        assertEquals("name cannot be null", actual.get(1));
    }

    @RepeatedTest(countrepeat)
    void testModify_reminderOffset_MAX_VALUE() throws IOException {
        //todo!!!
        int count = 1;
        ArrayList actual;
        actual = AMS.Request(mac, Operation.add, count, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart, reminderChannelNumber, reminderProgramId, Integer.MAX_VALUE, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
        testPurge();
    }

    @Test
    void testModify_reminderOffset_MIN_VALUE() throws IOException {
        //todo!!!
        int count = 1;
        ArrayList actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart, reminderChannelNumber, reminderProgramId, Integer.MIN_VALUE, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: invalid offset", actual.get(1));
    }

    /** reminderProgramId can be empty, AMS is removed his from json
     * @throws IOException - TBD
     */
    @RepeatedTest(countrepeat)
    void testModify_reminderProgramId_empty() throws IOException {
        ArrayList actual;
        actual = AMS.Request(mac, Operation.add, count, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
        //todo!!!
        actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart, reminderChannelNumber, "", reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
        testPurge();
    }

    /** reminderProgramId can be empty, AMS is removed his from json
     * @throws IOException - TBD
     */
    @RepeatedTest(countrepeat)
    void testModify_reminderProgramId_wrong() throws IOException {
        ArrayList actual;
        actual = AMS.Request(mac, Operation.add, count, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
        //todo!!!
        actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart, reminderChannelNumber, "EP#@$%#$%@#$^$#%^#$%^", reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
        testPurge();
    }

    @RepeatedTest(countrepeat)
    void testModify_reminderProgramStart_empty() throws IOException, InterruptedException {
        ArrayList actual;
        actual = AMS.Request(mac, Operation.add, count, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(mac, Operation.modify, count, "", reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        testPurge();
    }

    @Test
    void testModify_reminderProgramStart_PAST__statusCode2() throws IOException {
        //todo!!!
        int count = 1;
        ArrayList actual = AMS.Request(mac, Operation.modify, count, "2000-01-01", reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("2", actual.get(1));
    }

    @Test
    void testModify_reminderProgramStart_text() throws IOException {
        //todo!!!
        int count = 1;
        ArrayList actual = AMS.Request(mac, Operation.modify, count, "YYYY-mm-dd", reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected500, actual.get(0));
        assertEquals("name cannot be null", actual.get(1));
    }

    @Test
    void testModify_reminderProgramStart_wrong() throws IOException {
        //todo!!!
        int count = 1;
        ArrayList actual = AMS.Request(mac, Operation.modify, count, "0000-00-00", reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: invalid program start", actual.get(1));
        //testPurge();
    }

    /** 4 - reminder is unknown. Applies to "Reminders Delete" request (Request ID=1) and "Reminders Modify" request (Request ID=2)
     * @throws IOException - TBD
     */
    @Test
    void testModify_reminderScheduleId_0__statusCode4() throws IOException {
        //todo
        int count = 1;
        ArrayList actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, 0, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("4", actual.get(1));
    }

    /** 4 - reminder is unknown. Applies to "Reminders Delete" request (Request ID=1) and "Reminders Modify" request (Request ID=2)
     * @throws IOException - TBD
     */
    @Test
    void testModify_reminderScheduleId_0_reminderId_0__statusCode4() throws IOException {
        //todo
        int count = 1;
        ArrayList actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, 0, 0);
        assertEquals(expected200, actual.get(0));
        assertEquals("4", actual.get(1));
    }

    @Test
    void testModify_reminderScheduleId_empty() throws IOException {
        int reminderChannelNumber = 211;
        ArrayList actual;
        actual = AMS.Request(mac, Operation.add, count, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, -1, reminderId);
        assertEquals(expected500, actual.get(0));
        assertEquals("name cannot be null", actual.get(1));
        testPurge();
    }

    @Test
    void testModify_reminderScheduleId_empty_reminderId_empty() throws IOException {
        int reminderChannelNumber = 211;
        ArrayList actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, -1, -1);
        assertEquals(expected500, actual.get(0));
        assertEquals("name cannot be null", actual.get(1));
    }

    @RepeatedTest(countrepeat)
    @Deprecated
    void testModify_reminderScheduleId_MAX_VALUE() throws IOException {
        ArrayList actual;
        actual = AMS.Request(mac, Operation.add, count, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, Long.MAX_VALUE, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset(), Long.MAX_VALUE, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
        testPurge();
    }

    @Test
    void testModify_reminderScheduleId_MIN_VALUE() throws IOException {
        int reminderChannelNumber = 211;
        ArrayList actual;
        actual = AMS.Request(mac, Operation.add, count, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, Long.MIN_VALUE, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: incorrect reminderScheduleId", actual.get(1));
        testPurge();
    }

    @Disabled
    private void testPurge() throws IOException {
        ArrayList actual = AMS.Request(mac, Operation.purge);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }
    
}
