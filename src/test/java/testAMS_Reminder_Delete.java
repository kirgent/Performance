import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * We are as Middle: chain of requests: localhost -> AMS -> box -> AMS -> localhost (Middle)
 */
public class testAMS_Reminder_Delete extends API {

    private API_AMS AMS = new API_AMS();

    @Test
    public void testDelete() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.delete, count_reminders,
                "", 0, "", 0, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testDelete_reminderScheduleId_empty() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.delete, count_reminders,
                "", 0, "", 0, reminderScheduleId_null, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testDelete_reminderScheduleId_MAX_VALUE() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.delete, count_reminders,
                "", 0, "", 0, Integer.MAX_VALUE, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testDelete_reminderScheduleId_MIN_VALUE() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.delete, count_reminders,
                "", 0, "", 0, Integer.MIN_VALUE, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testDelete_reminderScheduleId_negative() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.delete, count_reminders,
                "", 0, "", 0, -1, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testDelete_reminderId_empty() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.delete, count_reminders,
                "", 0, "", 0, reminderScheduleId, reminderId_null);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: incorrect reminderId", actual.get(1));
    }

    @Test
    public void testDelete_reminderId_MAX_VALUE() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.delete, count_reminders,
                "", 0, "", 0, reminderScheduleId, Integer.MAX_VALUE);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: incorrect reminderId", actual.get(1));
    }

    @Test
    public void testDelete_reminderId_MIN_VALUE() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.delete, count_reminders,
                "", 0, "", 0, reminderScheduleId, Integer.MIN_VALUE);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: incorrect reminderId", actual.get(1));
    }

    @Test
    public void testDelete_reminderId_negative() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.delete, count_reminders,
                "", 0, "", 0, reminderScheduleId, -1);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: incorrect reminderId", actual.get(1));
    }

    /** 4 - reminder is unknown. Applies to "Reminders Delete" request (Request ID=1) and "Reminders Modify" request (Request ID=2)
     * @throws IOException - TBD
     */
    @Test
    public void testDelete_statusCode4_v1() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.delete, count_reminders,
                "", 0, "", 0, 0, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("4", actual.get(1));
    }

    /** 4 - reminder is unknown. Applies to "Reminders Delete" request (Request ID=1) and "Reminders Modify" request (Request ID=2)
     * @throws IOException - TBD
     */
    @Test
    public void testDelete_statusCode4_v2() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.delete, count_reminders,
                "", 0, "", 0, reminderScheduleId, 0);
        assertEquals(expected200, actual.get(0));
        assertEquals("4", actual.get(1));
    }

    /** 4 - reminder is unknown. Applies to "Reminders Delete" request (Request ID=1) and "Reminders Modify" request (Request ID=2)
     * @throws IOException - TBD
     */
    @Test
    public void testDelete_statusCode4_v3() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.delete, count_reminders,
                "", 0, "", 0, 0, 0);
        assertEquals(expected200, actual.get(0));
        assertEquals("4", actual.get(1));
    }

    /**
     * @throws IOException - TBD
     */
    @Test
    public void testDelete_macaddress_empty() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, "", Operation.delete, count_reminders,
                "", 0, "", 0, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: wrong deviceId", actual.get(1));
    }

    @Test
    public void testDelete_macaddress_wrong() throws IOException {
        ArrayList actual = AMS.Request(ams_ip, macaddress_wrong, Operation.delete, count_reminders,
                "", 0, "", 0, reminderScheduleId, reminderId);
        assertEquals(expected500, actual.get(0));
        assertEquals("REM-ST-001 Box is not registered", actual.get(1));
    }

}
