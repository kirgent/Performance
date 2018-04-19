import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

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

    @Test
    void testModify() throws IOException {
        int reminderChannelNumber = 305;
        //long reminderScheduleId = 5;
        //long reminderId = 5;

        ArrayList actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    @Deprecated
    void testModify_increase_reminderChannelNumber() throws IOException {
        ArrayList actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart(), reminderChannelNumber+1, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    @Deprecated
    void testModify_increase_reminderOffset() throws IOException {
        int reminderChannelNumber = 305;
        long reminderScheduleId = 5;
        long reminderId = 5;
        ArrayList actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset+1, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    @Deprecated
    void testModify_increase_reminderScheduleId() throws IOException {
        ArrayList actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId+5, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    @Deprecated
    void testModify_increase_reminderId() throws IOException {
        ArrayList actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId+5);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    void testModify_reminderChannelNumber_empty() throws IOException {
        ArrayList actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart(), reminderChannelNumber_empty, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: missing channel number", actual.get(1));
    }

    @Test
    void testModify_reminderChannelNumber_MAX_VALUE() throws IOException {
        ArrayList actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart(), Integer.MAX_VALUE, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    void testModify_reminderChannelNumber_MIN_VALUE() throws IOException {
        ArrayList actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart(), Integer.MIN_VALUE, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: missing channel number", actual.get(1));
    }

    @Test
    void testModify_reminderProgramStart_PAST() throws IOException {
        ArrayList actual = AMS.Request(mac, Operation.modify, count, "0000-00-00", reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: invalid program start", actual.get(1));
    }

    @RepeatedTest(100)
    void testModify_reminderProgramStart_empty() throws IOException {
        ArrayList actual;
        actual = AMS.Request(mac, Operation.add, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(mac, Operation.modify, count, "", reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    void testModify_reminderProgramStart_wrong() throws IOException {
        ArrayList actual = AMS.Request(mac, Operation.modify, count, "YYYY-mm-dd", reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("name cannot be null", actual.get(1));
    }

    /** reminderProgramId can be empty, AMS is removed his from json
     * @throws IOException
     */
    @Test
    void testModify_reminderProgramId_empty() throws IOException {
        ArrayList actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart(), reminderChannelNumber, "", reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("", actual.get(1));
    }

    /** reminderProgramId can be empty, AMS is removed his from json
     * @throws IOException
     */
    @Test
    void testModify_reminderProgramId_wrong() throws IOException {
        ArrayList actual = AMS.Request(mac, Operation.modify, count,
                reminderProgramStart(), reminderChannelNumber,
                "EP#@$%#$%@#$^$#%^#$%^", reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    void testModify_reminderOffset_empty() throws IOException {
        ArrayList actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset_null, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    void testModify_reminderOffset_MAX_VALUE() throws IOException {
        ArrayList actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, Integer.MAX_VALUE, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    void testModify_reminderOffset_MIN_VALUE() throws IOException {
        ArrayList actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, Integer.MIN_VALUE, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: missing offset", actual.get(1));
    }

    @Test
    void testModify_reminderScheduleId_empty() throws IOException {
        ArrayList actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId_null, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("4", actual.get(1));
    }

    @Test
    void testModify_reminderScheduleId_MAX_VALUE() throws IOException {
        long reminderScheduleId = Long.MAX_VALUE;
        ArrayList actual;
        actual = AMS.Request(mac, Operation.add, count, reminderProgramStart(), reminderChannelNumber,reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    void testModify_reminderScheduleId_MIN_VALUE() throws IOException {
        long reminderScheduleId = Long.MIN_VALUE;
        ArrayList actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: incorrect reminderScheduleId", actual.get(1));
    }

    @Test
    void testModify_reminderId_empty() throws IOException {
        ArrayList actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, 0);
        assertEquals(expected400, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    void testModify_reminderId_MAX_VALUE() throws IOException {
        long reminderId = Long.MAX_VALUE;
        ArrayList actual;
        actual = AMS.Request(mac, Operation.add, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    void testModify_reminderId_MIN_VALUE() throws IOException {
        long reminderId = Long.MIN_VALUE;
        ArrayList actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: incorrect reminderId", actual.get(1));
    }

    /** 4 - reminder is unknown. Applies to "Reminders Delete" request (Request ID=1) and "Reminders Modify" request (Request ID=2)
     * @throws IOException - TBD
     */
    @Test
    void testModify_statusCode4() throws IOException {
        ArrayList actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, 0, 0);
        assertEquals(expected200, actual.get(0));
        assertEquals("4", actual.get(1));
    }

    @Test
    void testModify_mac_empty() throws IOException {
        ArrayList actual = AMS.Request("", Operation.modify, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: wrong deviceId", actual.get(1));
    }

    @Test
    void testModify_mac_wrong() throws IOException {
        ArrayList actual = AMS.Request(mac_wrong, Operation.modify, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("REM-ST-001 Box is not registered", actual.get(1));
    }

    @Test
    void testModify_reminderProgramId_reminderOffset_empty() throws IOException {
        ArrayList actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart(), reminderChannelNumber, "", reminderOffset_null, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    void testModify_reminderScheduleId_reminderId_empty() throws IOException {
        ArrayList actual = AMS.Request(mac, Operation.modify, count, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId_null, 0);
        assertEquals(expected400, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    void testModify_count_is_0() throws IOException {
        ArrayList actual = AMS.Request(mac, Operation.modify, 0, reminderProgramStart(), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: wrong number of reminders", actual.get(1));
    }

    @Test
    void testModify_all_wrong() throws IOException {
        ArrayList actual = AMS.Request(mac_wrong, Operation.modify, 0, "", 0, reminderProgramId_empty, 0, 0, 0);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: wrong number of reminders", actual.get(1));
    }

    @AfterEach
    void testPurge() throws IOException {
        ArrayList actual = AMS.Request(mac, Operation.purge);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }
    
}
