import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.RepeatedTest;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

class testAMS_Reminder_Modify extends API {
    //"reminderChannelNumber": <new value for the DCN the reminder is set to>,
    //"reminderProgramStart": "<new value for the date/time of program the reminder is set to>",
    //"reminderProgramId": "<new value for the TMS Program ID the reminder is set to>",
    //"reminderOffset": <new value for the number of minutes before the program start the reminder should be shown>
    //"reminderScheduleId": "<series or Individual program reminder schedule reference ID>",
    //"reminderId": "<episode or Individual program reminder reference ID of a particular schedule>",

    private API_AMS AMS = new API_AMS();

    @RepeatedTest(1)
    void testModify() throws IOException {
        int reminderChannelNumber = 211;

        ArrayList actual;
        //actual = AMS.Request(mac, Operation.add, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        //Assertions.assertEquals(expected200, actual.get(0));
        //Assertions.assertEquals("", actual.get(1));

        actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @RepeatedTest(1)
    void testModify_reminderChannelNumber_empty() throws IOException {
        //todo!!!
        int reminderScheduleId = 0;
        int reminderId = 0;
        ArrayList actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart(), -1, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: missing channel number", actual.get(1));
    }

    @RepeatedTest(1)
    void testModify_reminderChannelNumber_0__statusCode3() throws IOException {
        //todo!!!
        int reminderScheduleId = 0;
        int reminderId = 0;
        ArrayList actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart(), 0, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("3", actual.get(1));
    }

    @RepeatedTest(1)
    void testModify_reminderChannelNumber_MAX_VALUE() throws IOException {
        //todo!!!
        int reminderScheduleId = 0;
        int reminderId = 0;
        ArrayList actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart(), Integer.MAX_VALUE, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @RepeatedTest(1)
    void testModify_reminderChannelNumber_MIN_VALUE() throws IOException {
        //todo!!!
        int reminderScheduleId = 0;
        int reminderId = 0;
        ArrayList actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart(), Integer.MIN_VALUE, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: missing channel number", actual.get(1));
    }

    @RepeatedTest(10)
    void testModify_reminderProgramStart_PAST() throws IOException {
        ArrayList actual;
        actual = AMS.Request(mac, Operation.add, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
        //todo!!!
        actual = AMS.Request(mac, Operation.modify, count, "2000-01-01", reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("2", actual.get(1));
        testPurge();
    }

    @RepeatedTest(10)
    void testModify_reminderProgramStart_wrong() throws IOException {
        ArrayList actual;
        actual = AMS.Request(mac, Operation.add, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
        //todo!!!
        actual = AMS.Request(mac, Operation.modify, count, "0000-0-00", reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: invalid program start", actual.get(1));
        testPurge();
    }

    @RepeatedTest(10)
    void testModify_reminderProgramStart_empty() throws IOException {
        ArrayList actual;
        actual = AMS.Request(mac, Operation.add, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(mac, Operation.modify, count, "", reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
        testPurge();
    }

    @RepeatedTest(10)
    void testModify_reminderProgramStart_text() throws IOException {
        ArrayList actual;
        actual = AMS.Request(mac, Operation.add, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
        //todo!!!
        actual = AMS.Request(mac, Operation.modify, count, "YYYY-mm-dd", reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected500, actual.get(0));
        assertEquals("name cannot be null", actual.get(1));
        testPurge();
    }

    @RepeatedTest(10)
    void testAdd_reminderProgramStart_wrong() throws IOException {
        ArrayList actual;
        actual = AMS.Request(mac, Operation.add, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
        //todo!!!
        actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart_wrong, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        Assertions.assertEquals(expected400, actual.get(0));
        Assertions.assertEquals("REM-008 Reminders parsing error: invalid program start", actual.get(1));
    }

    /** reminderProgramId can be empty, AMS is removed his from json
     * @throws IOException
     */
    @RepeatedTest(1)
    void testModify_reminderProgramId_empty() throws IOException {
        //todo!!!
        int reminderScheduleId = 0;
        int reminderId = 0;
        ArrayList actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart(), reminderChannelNumber, "", reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("", actual.get(1));
    }

    /** reminderProgramId can be empty, AMS is removed his from json
     * @throws IOException
     */
    @RepeatedTest(1)
    void testModify_reminderProgramId_wrong() throws IOException {
        //todo!!!
        int reminderScheduleId = 0;
        int reminderId = 0;
        ArrayList actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart(), reminderChannelNumber, "EP#@$%#$%@#$^$#%^#$%^", reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @RepeatedTest(1)
    void testModify_reminderOffset_empty() throws IOException {
        //todo!!!
        int reminderScheduleId = 123;
        int reminderId = 123;
        ArrayList actual = AMS.Request(mac, Operation.modify, 1, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset_null, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @RepeatedTest(1)
    void testModify_reminderOffset_MAX_VALUE() throws IOException {
        //todo!!!
        ArrayList actual;
        actual = AMS.Request(mac, Operation.add, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, Integer.MAX_VALUE+1000, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("", actual.get(1));
        testPurge();
    }

    @RepeatedTest(1)
    void testModify_reminderOffset_MIN_VALUE() throws IOException {
        //todo!!!
        int reminderScheduleId = 123;
        int reminderId = 123;
        ArrayList actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, Integer.MIN_VALUE, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: missing offset", actual.get(1));
    }

    @RepeatedTest(1)
    void testModify_reminderScheduleId_empty() throws IOException {
        int reminderChannelNumber = 211;
        ArrayList actual;
        actual = AMS.Request(mac, Operation.add, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, -1, reminderId);
        assertEquals(expected500, actual.get(0));
        assertEquals("name cannot be null", actual.get(1));
        testPurge();
    }

    /** 4 - reminder is unknown. Applies to "Reminders Delete" request (Request ID=1) and "Reminders Modify" request (Request ID=2)
     * @throws IOException - TBD
     */
    @RepeatedTest(1)
    void testModify_reminderScheduleId_MAX_VALUE() throws IOException {
        int reminderChannelNumber = 211;
        ArrayList actual;
        actual = AMS.Request(mac, Operation.add, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, Long.MAX_VALUE, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("4", actual.get(1));
        testPurge();
    }

    @RepeatedTest(1)
    void testModify_reminderScheduleId_MIN_VALUE() throws IOException {
        int reminderChannelNumber = 211;
        ArrayList actual;
        actual = AMS.Request(mac, Operation.add, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, Long.MIN_VALUE, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: incorrect reminderScheduleId", actual.get(1));
        testPurge();
    }

    /** 4 - reminder is unknown. Applies to "Reminders Delete" request (Request ID=1) and "Reminders Modify" request (Request ID=2)
     * @throws IOException - TBD
     */
    @RepeatedTest(1)
    void testModify_reminderScheduleId_0__statusCode4() throws IOException {
        int reminderChannelNumber = 211;
        ArrayList actual;
        actual = AMS.Request(mac, Operation.add, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, 0, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("4", actual.get(1));
        testPurge();
    }

    @RepeatedTest(1)
    void testModify_reminderId_empty() throws IOException {
        int reminderChannelNumber = 211;
        ArrayList actual;
        actual = AMS.Request(mac, Operation.add, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, -1);
        assertEquals(expected500, actual.get(0));
        assertEquals("name cannot be null", actual.get(1));
        testPurge();
    }

    @RepeatedTest(1)
    void testModify_reminderId_MAX_VALUE() throws IOException {
        int reminderChannelNumber = 211;
        ArrayList actual;
        actual = AMS.Request(mac, Operation.add, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, Long.MAX_VALUE);
        assertEquals(expected200, actual.get(0));
        assertEquals("4", actual.get(1));
        testPurge();
    }

    @RepeatedTest(1)
    void testModify_reminderId_MIN_VALUE() throws IOException {
        int reminderChannelNumber = 211;
        ArrayList actual;
        actual = AMS.Request(mac, Operation.add, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, Long.MIN_VALUE);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: incorrect reminderId", actual.get(1));
        testPurge();
    }

    /** 4 - reminder is unknown. Applies to "Reminders Delete" request (Request ID=1) and "Reminders Modify" request (Request ID=2)
     * @throws IOException - TBD
     */
    @RepeatedTest(1)
    void testModify_reminderId_0__statusCode4() throws IOException {
        int reminderChannelNumber = 211;
        ArrayList actual;
        actual = AMS.Request(mac, Operation.add, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, 0);
        assertEquals(expected200, actual.get(0));
        assertEquals("4", actual.get(1));
        testPurge();
    }

    @RepeatedTest(1)
    void testModify_mac_empty() throws IOException {
        ArrayList actual = AMS.Request("", Operation.modify, 1, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: wrong deviceId", actual.get(1));
    }

    @RepeatedTest(1)
    void testModify_mac_wrong() throws IOException {
        ArrayList actual = AMS.Request(mac_wrong, Operation.modify, 1, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("REM-ST-001 Box is not registered", actual.get(1));
    }

    @RepeatedTest(1)
    void testModify_reminderProgramId_reminderOffset_empty() throws IOException {
        //todo!!!
        int reminderScheduleId = 0;
        int reminderId = 0;
        ArrayList actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart(), reminderChannelNumber, "", reminderOffset_null, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @RepeatedTest(1)
    void testModify_reminderScheduleId_empty_reminderId_empty() throws IOException {
        int reminderChannelNumber = 211;
        ArrayList actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, -1, -1);
        assertEquals(expected500, actual.get(0));
        assertEquals("name cannot be null", actual.get(1));
    }

    /** 4 - reminder is unknown. Applies to "Reminders Delete" request (Request ID=1) and "Reminders Modify" request (Request ID=2)
     * @throws IOException - TBD
     */
    @RepeatedTest(1)
    void testModify_reminderScheduleId_0_reminderId_0__statusCode4() throws IOException {
        ArrayList actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, 0, 0);
        assertEquals(expected200, actual.get(0));
        assertEquals("4", actual.get(1));
    }

    @RepeatedTest(1)
    void testModify_count_is_0() throws IOException {
        ArrayList actual = AMS.Request(mac, Operation.modify, 0, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: wrong number of reminders", actual.get(1));
    }

    @Disabled
    void testAdd() throws IOException {
        int reminderChannelNumber = 211;
        ArrayList actual = AMS.Request(mac, Operation.add, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        Assertions.assertEquals(expected200, actual.get(0));
        Assertions.assertEquals("", actual.get(1));
    }

    @Disabled
    private void testPurge() throws IOException {
        ArrayList actual = AMS.Request(mac, Operation.purge);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }
    
}
