import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class testAMS_Reminder_Delete extends API {

    private API_AMS AMS = new API_AMS();

    @Test
    public void testDelete() throws IOException {
        starttime();
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.delete, count_reminders,
                "", 0, "", 0, reminderScheduleId, reminderId);
        finishtime();
        System.out.println("[DBG] " + (finish-start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testDelete_reminderScheduleId_empty() throws IOException {
        starttime();
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.delete, count_reminders,
                "", 0, "", 0, reminderScheduleId_null, reminderId);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testDelete_reminderScheduleId_MAX_VALUE() throws IOException {
        starttime();
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.delete, count_reminders,
                "", 0, "", 0, Integer.MAX_VALUE, reminderId);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testDelete_reminderScheduleId_MIN_VALUE() throws IOException {
        starttime();
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.delete, count_reminders,
                "", 0, "", 0, Integer.MIN_VALUE, reminderId);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testDelete_reminderScheduleId_negative() throws IOException {
        starttime();
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.delete, count_reminders,
                "", 0, "", 0, -1, reminderId);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testDelete_reminderId_empty() throws IOException {
        starttime();
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.delete, count_reminders,
                "", 0, "", 0, reminderScheduleId, reminderId_null);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("REM-008 Reminders parsing error: incorrect reminderId", actual.get(2));
    }

    @Test
    public void testDelete_reminderId_MAX_VALUE() throws IOException {
        starttime();
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.delete, count_reminders,
                "", 0, "", 0, reminderScheduleId, Integer.MAX_VALUE);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("REM-008 Reminders parsing error: incorrect reminderId", actual.get(2));
    }

    @Test
    public void testDelete_reminderId_MIN_VALUE() throws IOException {
        starttime();
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.delete, count_reminders,
                "", 0, "", 0, reminderScheduleId, Integer.MIN_VALUE);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("REM-008 Reminders parsing error: incorrect reminderId", actual.get(2));
    }

    @Test
    public void testDelete_reminderId_negative() throws IOException {
        starttime();
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.delete, count_reminders,
                "", 0, "", 0, reminderScheduleId, -1);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("REM-008 Reminders parsing error: incorrect reminderId", actual.get(2));
    }

    /** 4 - reminder is unknown. Applies to "Reminders Delete" request (Request ID=1) and "Reminders Modify" request (Request ID=2)
     * @throws IOException - TBD
     */
    @Test
    public void testDelete_statusCode4_v1() throws IOException {
        starttime();
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.delete, count_reminders,
                "", 0, "", 0, 0, reminderId);
        finishtime();
        System.out.println("[DBG] " + (finish-start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("4", actual.get(2));
    }

    /** 4 - reminder is unknown. Applies to "Reminders Delete" request (Request ID=1) and "Reminders Modify" request (Request ID=2)
     * @throws IOException - TBD
     */
    @Test
    public void testDelete_statusCode4_v2() throws IOException {
        starttime();
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.delete, count_reminders,
                "", 0, "", 0, reminderScheduleId, 0);
        finishtime();
        System.out.println("[DBG] " + (finish-start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("4", actual.get(2));
    }

    /** 4 - reminder is unknown. Applies to "Reminders Delete" request (Request ID=1) and "Reminders Modify" request (Request ID=2)
     * @throws IOException - TBD
     */
    @Test
    public void testDelete_statusCode4_v3() throws IOException {
        starttime();
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.delete, count_reminders,
                "", 0, "", 0, 0, 0);
        finishtime();
        System.out.println("[DBG] " + (finish-start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("4", actual.get(2));
    }


    /**
     * @throws IOException - TBD
     */
    @Test
    public void testDelete_macaddress_empty() throws IOException {
        starttime();
        ArrayList actual = AMS.Request(ams_ip, "", Operation.delete, count_reminders,
                "", 0, "", 0, reminderScheduleId, reminderId);
        finishtime();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("REM-008 Reminders parsing error: wrong deviceId", actual.get(2));
    }

    @Test
    public void testDelete_macaddress_wrong() throws IOException {
        starttime();
        ArrayList actual = AMS.Request(ams_ip, macaddress_wrong, Operation.delete, count_reminders,
                "", 0, "", 0, reminderScheduleId, reminderId);
        finishtime();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("", actual.get(2));
    }

}
