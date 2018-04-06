import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * We are as Middle: chain of requests: localhost -> AMS -> box -> AMS -> localhost (Middle)
 */
public class testAMS_Reminder_Add extends API {

    private API_AMS AMS = new API_AMS();

    //@RepeatedTest(3)
    @Test
    public void testAdd() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.add, 1,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset, 12345, 12345);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    /** 3 - reminder is set for unknown channel. \"Reminders Add\" request (Request ID=0)
     * @throws IOException
     */
    @Test
    public void testAdd_reminderChannelNumber_empty__statusCode3() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.add, count_reminders,
                reminderProgramStart(), reminderChannelNumber_empty, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("3", actual.get(1));
    }

    @Test
    public void testAdd_reminderChannelNumber_MAX_VALUE() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.add, count_reminders,
                reminderProgramStart(), Integer.MAX_VALUE, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("3", actual.get(1));
    }

    @Test
    public void testAdd_reminderChannelNumber_MIN_VALUE() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.add, count_reminders,
                reminderProgramStart(), Integer.MIN_VALUE, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: missing channel number", actual.get(1));
    }

    /** 2 - reminder is set for time in the past. Applies to "Reminders Add" request (Request ID=0)
     * @throws IOException - TBD
     */
    @Test
    public void testAdd_reminderProgramStart_PAST__statusCode2() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.add, count_reminders,
                "2000-01-01", reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("2", actual.get(1));
    }

    @Test
    public void testAdd_reminderProgramStart_empty() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.add, count_reminders,
                "", reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected500, actual.get(0));
        assertEquals("name cannot be null", actual.get(1));
    }

    @Test
    public void testAdd_reminderProgramStart_wrong2() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.add, count_reminders,
                "yyyy-mm-dd", reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected500, actual.get(0));
        assertEquals("name cannot be null", actual.get(1));
    }

    @Test
    public void testAdd_reminderProgramStart_wrong() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.add, count_reminders,
                "0000-00-00", reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: missing program start", actual.get(1));
    }

    @Test
    public void testAdd_reminderProgramId_empty() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.add, count_reminders,
                reminderProgramStart(), reminderChannelNumber, "",
                reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testAdd_reminderProgramId_wrong() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.add, count_reminders,
                reminderProgramStart(), reminderChannelNumber, "EP#@$%#$%@#$^$#%^#$%^",
                reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testAdd_reminderOffset_empty() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.add, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset_null, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testAdd_reminderOffset_MAX_VALUE() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.add, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                Integer.MAX_VALUE, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testAdd_reminderOffset_MIN_VALUE() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.add, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                Integer.MIN_VALUE, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testAdd_reminderOffset_negative() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.add, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                -1, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testAdd_reminderScheduleId_empty() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.add, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId_null, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testAdd_reminderScheduleId_MAX_VALUE() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.add, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset, Integer.MAX_VALUE, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testAdd_reminderScheduleId_MIN_VALUE() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.add, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset, Integer.MIN_VALUE, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: incorrect reminderScheduleId", actual.get(1));
    }

    @Test
    public void testAdd_reminderId_empty() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.add, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId_null);
        assertEquals(expected400, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testAdd_reminderId_MAX_VALUE() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.add, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, Integer.MAX_VALUE);
        assertEquals(expected400, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testAdd_reminderId_MIN_VALUE() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.add, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, Integer.MIN_VALUE);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: incorrect reminderId", actual.get(1));
    }

    /**5 - reminder with provided pair of identifiers (reminderScheduleId and reminderId) is already set (for Add Reminder request)
     * @throws IOException - TBD
     */
    @Test
    public void testAdd_statusCode5() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.add, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("5", actual.get(1));
    }

    @Test
    @Deprecated
    public void testAdd_REM_ST_001_Box_is_not_registered() throws IOException {
        ArrayList actual = AMS.Request("172.30.81.0", macaddress, Operation.add, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected500, actual.get(0));
        assertEquals("REM-ST-001 Box is not registered", actual.get(1));
    }

    @Test
    public void testAdd_macaddress_empty() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, "", Operation.add, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: wrong deviceId", actual.get(1));
    }

    @Test
    public void testAdd_macaddress_wrong() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress_wrong, Operation.add, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected500, actual.get(0));
        assertEquals("REM-ST-001 Box is not registered", actual.get(1));
    }

}
