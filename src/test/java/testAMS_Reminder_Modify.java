import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class testAMS_Reminder_Modify extends API {

    private API_AMS AMS = new API_AMS();

    @Test
    public void testModify() throws IOException {
        //"reminderChannelNumber": <new value for the DCN the reminder is set to>,
        //"reminderProgramStart": "<new value for the date/time of program the reminder is set to>",
        //"reminderProgramId": "<new value for the TMS Program ID the reminder is set to>",
        //"reminderOffset": <new value for the number of minutes before the program start the reminder should be shown>
        //"reminderScheduleId": "<series or Individual program reminder schedule reference ID>",
        //"reminderId": "<episode or Individual program reminder reference ID of a particular schedule>",
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.modify, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    @Deprecated
    public void testModify_increase_reminderChannelNumber() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.modify, count_reminders,
                reminderProgramStart(), reminderChannelNumber+1, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    @Deprecated
    public void testModify_increase_reminderOffset() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.modify, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset+5, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    @Deprecated
    public void testModify_increase_reminderScheduleId() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.modify, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId+5, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    @Deprecated
    public void testModify_increase_reminderId() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.modify, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId+5);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testModify_reminderChannelNumber_empty() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.modify, count_reminders,
                reminderProgramStart(), reminderChannelNumber_empty, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testModify_reminderChannelNumber_negative() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.modify, count_reminders,
                reminderProgramStart(), -1, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testModify_reminderChannelNumber_MAX_VALUE() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.modify, count_reminders,
                reminderProgramStart(), Integer.MAX_VALUE, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testModify_reminderChannelNumber_MIN_VALUE() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.modify, count_reminders,
                reminderProgramStart(), Integer.MIN_VALUE, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testModify_reminderProgramStart_PAST() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.modify, count_reminders,
                "0000-00-00", reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testModify_reminderProgramStart_empty() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.modify, count_reminders,
                "", reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testModify_reminderProgramStart_wrong() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.modify, count_reminders,
                "YYYY-mm-dd", reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testModify_reminderProgramId_empty() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.modify, count_reminders,
                reminderProgramStart(), reminderChannelNumber, "",
                reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testModify_reminderProgramId_wrong() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.modify, count_reminders,
                reminderProgramStart(), reminderChannelNumber, "EP#@$%#$%@#$^$#%^#$%^",
                reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testModify_reminderOffset_empty() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.modify, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset_null, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testModify_reminderOffset_MAX_VALUE() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.modify, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                Integer.MAX_VALUE, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testModify_reminderOffset_MIN_VALUE() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.modify, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                Integer.MIN_VALUE, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testModify_reminderOffset_negative() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.modify, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                -1, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testModify_reminderScheduleId_empty() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.modify, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId_null, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testModify_reminderScheduleId_MAX_VALUE() throws IOException {
        
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.modify, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset, Integer.MAX_VALUE, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testModify_reminderScheduleId_MIN_VALUE() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.modify, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset, Integer.MIN_VALUE, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testModify_reminderScheduleId_negative() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.modify, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset, -1, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testModify_reminderId_empty() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.modify, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId_null);
        assertEquals(expected400, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testModify_reminderId_MAX_VALUE() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.modify, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, Integer.MAX_VALUE);
        assertEquals(expected400, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testModify_reminderId_MIN_VALUE() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.modify, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, Integer.MIN_VALUE);
        assertEquals(expected400, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testModify_reminderId_negative() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.modify, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, -1);
        assertEquals(expected400, actual.get(0));
        assertEquals("", actual.get(1));
    }

    /** 4 - reminder is unknown. Applies to "Reminders Delete" request (Request ID=1) and "Reminders Modify" request (Request ID=2)
     * @throws IOException - TBD
     */
    @Test
    public void testModify_statusCode4() throws IOException {
        
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.modify, count_reminders,
                reminderProgramStart(), reminderChannelNumber,
                reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("4", actual.get(1));
    }

    @Test
    public void testModify_macaddress_empty() throws IOException {
        
        ArrayList actual = AMS.Request(ams_ip, "", Operation.modify, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: wrong deviceId", actual.get(1));
    }

    @Test
    public void testModify_macaddress_wrong() throws IOException {
        
        ArrayList actual = AMS.Request(ams_ip, macaddress_wrong, Operation.modify, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testModify_REM_ST_001_Box_is_not_registered() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.modify, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected500, actual.get(0));
        assertEquals("REM-ST-001 Box is not registered", actual.get(1));
    }

    @Test
    public void testModify_reminderChannelNumber_reminderProgramStart_empty() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.modify, count_reminders,
                "", reminderChannelNumber_empty, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testModify_reminderProgramId_reminderOffset_empty() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.modify, count_reminders,
                reminderProgramStart(), reminderChannelNumber, "",
                reminderOffset_null, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testModify_reminderScheduleId_reminderId_empty() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.modify, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId_null, reminderId_null);
        assertEquals(expected400, actual.get(0));
        assertEquals("", actual.get(1));
    }
    
}
