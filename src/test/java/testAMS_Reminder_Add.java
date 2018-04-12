import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * We are as Middle: chain of requests: localhost -> AMS -> box -> AMS -> localhost (Middle)
 */
public class testAMS_Reminder_Add extends API {
    //"reminderChannelNumber": <new value for the DCN the reminder is set to>,
    //"reminderProgramStart": "<new value for the date/time of program the reminder is set to>",
    //"reminderProgramId": "<new value for the TMS Program ID the reminder is set to>",
    //"reminderOffset": <new value for the number of minutes before the program start the reminder should be shown>
    //"reminderScheduleId": "<series or Individual program reminder schedule reference ID>",
    //"reminderId": "<episode or Individual program reminder reference ID of a particular schedule>",

    private API_AMS AMS = new API_AMS();

    @Test
    public void testAdd() throws IOException {
        int count_reminders = 1;
        int reminderChannelNumber = 305;

        //long reminderScheduleId = 2;
        //long reminderId = 2;

        long reminderScheduleId = reminderScheduleId();
        long reminderId = reminderId();

        //long reminderScheduleId = reminderScheduleId_random;
        //long reminderId = reminderId_random;

        ArrayList actual = AMS.Request(macaddress, Operation.add, count_reminders,
                reminderProgramStart(), reminderChannelNumber,
                reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    /** 3 - reminder is set for unknown channel. \"Reminders Add\" request (Request ID=0)
     * @throws IOException
     */
    @Test
    public void testAdd_reminderChannelNumber_empty__statusCode3() throws IOException {
        ArrayList actual = AMS.Request(macaddress, Operation.add, count_reminders,
                reminderProgramStart(), reminderChannelNumber_empty, reminderProgramId,
                reminderOffset, reminderScheduleId(), reminderId());
        assertEquals(expected200, actual.get(0));
        assertEquals("3", actual.get(1));
    }

    @Test
    public void testAdd_reminderChannelNumber_MAX_VALUE__statusCode3() throws IOException {
        ArrayList actual = AMS.Request(macaddress, Operation.add, count_reminders,
                reminderProgramStart(), Integer.MAX_VALUE, reminderProgramId,
                reminderOffset, reminderScheduleId(), reminderId());
        assertEquals(expected200, actual.get(0));
        assertEquals("3", actual.get(1));
    }

    @Test
    public void testAdd_reminderChannelNumber_MIN_VALUE() throws IOException {
        ArrayList actual = AMS.Request(macaddress, Operation.add, count_reminders,
                reminderProgramStart(), Integer.MIN_VALUE, reminderProgramId,
                reminderOffset, reminderScheduleId(), reminderId());
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: missing channel number", actual.get(1));
    }

    @Test
    //todo https://jira.zodiac.tv/browse/CH0098-134
    public void testAdd_reminderProgramStart_empty() throws IOException {
        ArrayList actual = AMS.Request(macaddress, Operation.add, count_reminders,
                "", reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId(), reminderId());
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: missing program start", actual.get(1));
    }

    /** 2 - reminder is set for time in the past. Applies to "Reminders Add" request (Request ID=0)
     * @throws IOException - TBD
     */
    @Test
    public void testAdd_reminderProgramStart_PAST__statusCode2() throws IOException {
        ArrayList actual = AMS.Request(macaddress, Operation.add, count_reminders,
                "2000-01-01", reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId(), reminderId());
        assertEquals(expected200, actual.get(0));
        assertEquals("2", actual.get(1));
    }

    @Test
    public void testAdd_reminderProgramStart_wrong() throws IOException {
        ArrayList actual = AMS.Request(macaddress, Operation.add, count_reminders,
                reminderProgramStart_wrong, reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId(), reminderId());
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: missing program start", actual.get(1));
    }

    @Test
    public void testAdd_reminderProgramStart_text() throws IOException {
        ArrayList actual = AMS.Request(macaddress, Operation.add, count_reminders,
                reminderProgramStart_text, reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId(), reminderId());
        assertEquals(expected500, actual.get(0));
        assertEquals("name cannot be null", actual.get(1));
    }

    @Test
    public void testAdd_reminderProgramId_empty() throws IOException {
        ArrayList actual = AMS.Request(macaddress, Operation.add, count_reminders,
                reminderProgramStart(), reminderChannelNumber, "",
                reminderOffset, reminderScheduleId(), reminderId());
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: wrong programId", actual.get(1));
    }

    @Test
    public void testAdd_reminderProgramId_wrong() throws IOException {
        ArrayList actual = AMS.Request(macaddress, Operation.add, count_reminders,
                reminderProgramStart(), reminderChannelNumber, "EP#@$%#$%@#$^$#%^#$%^",
                reminderOffset, reminderScheduleId(), reminderId());
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: wrong programId", actual.get(1));
    }

    @Test
    //todo POSSIBLE BUG !!!
    public void testAdd_reminderOffset_MAX_VALUE() throws IOException {
        ArrayList actual = AMS.Request(macaddress, Operation.add, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                Integer.MAX_VALUE, reminderScheduleId(), reminderId());
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: missing offset", actual.get(1));
    }

    @Test
    public void testAdd_reminderOffset_MIN_VALUE() throws IOException {
        ArrayList actual = AMS.Request(macaddress, Operation.add, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                Integer.MIN_VALUE, reminderScheduleId(), reminderId());
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: missing offset", actual.get(1));
    }

    @Test
    public void testAdd_reminderScheduleId_zero() throws IOException {
        ArrayList actual = AMS.Request(macaddress, Operation.add, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset, 0, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: wrong ScheduleId", actual.get(1));
    }

    @Test
    public void testAdd_reminderScheduleId_MAX_VALUE() throws IOException {
        ArrayList actual = AMS.Request(macaddress, Operation.add, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset, Long.MAX_VALUE, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testAdd_reminderScheduleId_MIN_VALUE() throws IOException {
        ArrayList actual = AMS.Request(macaddress, Operation.add, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset, Long.MIN_VALUE, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: incorrect reminderScheduleId", actual.get(1));
    }

    @Test
    public void testAdd_reminderId_zero() throws IOException {
        ArrayList actual = AMS.Request(macaddress, Operation.add, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, 0);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: incorrect reminderId", actual.get(1));
    }

    @Test
    public void testAdd_reminderId_MAX_VALUE() throws IOException {
        ArrayList actual = AMS.Request(macaddress, Operation.add, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, Long.MAX_VALUE);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testAdd_reminderId_MIN_VALUE() throws IOException {
        ArrayList actual = AMS.Request(macaddress, Operation.add, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, Long.MIN_VALUE);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: incorrect reminderId", actual.get(1));
    }

    /**5 - reminder with provided pair of identifiers (reminderScheduleId and reminderId) is already set (for Add Reminder request)
     * @throws IOException - TBD
     */
    @Test
    public void testAdd_statusCode5() throws IOException {
        ArrayList actual = AMS.Request(macaddress, Operation.purge,true);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(macaddress, Operation.add, count_reminders, reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset, 999, 999);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(macaddress, Operation.add, count_reminders, reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset, 999, 999);
        assertEquals(expected200, actual.get(0));
        assertEquals("5", actual.get(1));

    }

    /** sometimes happened. box is down?
     * @throws IOException
     */
    @Test
    @Deprecated
    public void testAdd__REM_002_Reminders_Service_error_Can_not_connect_to_STB_with_stbId() throws IOException {
        ArrayList actual = AMS.Request(macaddress, Operation.add, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId(), reminderId());
        assertEquals(expected500, actual.get(0));
        assertEquals("REM-002 Reminders Service error: Can not connect to STB with stbId=" + macaddress, actual.get(1));
    }

    @Test
    public void testAdd_macaddress_empty() throws IOException {
        ArrayList actual = AMS.Request("", Operation.add, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId(), reminderId());
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: wrong deviceId", actual.get(1));
    }

    @Test
    public void testAdd_macaddress_wrong() throws IOException {
        ArrayList actual = AMS.Request(macaddress_wrong, Operation.add, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId(), reminderId());
        assertEquals(expected500, actual.get(0));
        assertEquals("REM-ST-001 Box is not registered", actual.get(1));
    }

    @Test
    public void testAdd_count_reminders_is_0() throws IOException {
        ArrayList actual = AMS.Request(macaddress, Operation.add, 0,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId(), reminderId());
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: wrong number of reminders", actual.get(1));
    }

}
