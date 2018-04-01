import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class testReminder_Delete extends API {

    @Test
    public void testDelete() throws IOException {
        starttime();
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.delete, count_reminders,
                reminderProgramStart(), reminderChannelNumber,
                reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        finishtime();
        System.out.println("[DBG] " + (finish-start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testDelete_reminderChannelNumber_empty() throws IOException {
        starttime();
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.delete, count_reminders,
                reminderProgramStart(), reminderChannelNumber_empty, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testDelete_reminderChannelNumber_negative() throws IOException {
        starttime();
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.delete, count_reminders,
                reminderProgramStart(), -1, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testDelete_reminderChannelNumber_MAX_VALUE() throws IOException {
        starttime();
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.delete, count_reminders,
                reminderProgramStart(), Integer.MAX_VALUE, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testDelete_reminderChannelNumber_MIN_VALUE() throws IOException {
        starttime();
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.delete, count_reminders,
                reminderProgramStart(), Integer.MIN_VALUE, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testDelete_reminderProgramStart_PAST() throws IOException {
        starttime();
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.delete, count_reminders,
                "0000-00-00", reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testDelete_reminderProgramStart_empty() throws IOException {
        starttime();
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.delete, count_reminders,
                "", reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testDelete_reminderProgramStart_wrong() throws IOException {
        starttime();
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.delete, count_reminders,
                "YYYY-mm-dd", reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testDelete_reminderProgramId_empty() throws IOException {
        starttime();
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.delete, count_reminders,
                reminderProgramStart(), reminderChannelNumber, "",
                reminderOffset, reminderScheduleId, reminderId);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testDelete_reminderProgramId_wrong() throws IOException {
        starttime();
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.delete, count_reminders,
                reminderProgramStart(), reminderChannelNumber, "EP#@$%#$%@#$^$#%^#$%^",
                reminderOffset, reminderScheduleId, reminderId);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testDelete_reminderOffset_empty() throws IOException {
        starttime();
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.delete, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset_empty, reminderScheduleId, reminderId);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testDelete_reminderOffset_MAX_VALUE() throws IOException {
        starttime();
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.delete, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                Integer.MAX_VALUE, reminderScheduleId, reminderId);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testDelete_reminderOffset_MIN_VALUE() throws IOException {
        starttime();
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.delete, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                Integer.MIN_VALUE, reminderScheduleId, reminderId);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testDelete_reminderOffset_negative() throws IOException {
        starttime();
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.delete, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                -1, reminderScheduleId, reminderId);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testDelete_reminderScheduleId_empty() throws IOException {
        starttime();
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.delete, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId_empty, reminderId);
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
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset, Integer.MAX_VALUE, reminderId);
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
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset, Integer.MIN_VALUE, reminderId);
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
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset, -1, reminderId);
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
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId_empty);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testDelete_reminderId_MAX_VALUE() throws IOException {
        starttime();
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.delete, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, Integer.MAX_VALUE);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testDelete_reminderId_MIN_VALUE() throws IOException {
        starttime();
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.delete, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, Integer.MIN_VALUE);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testDelete_reminderId_negative() throws IOException {
        starttime();
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.delete, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, -1);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    /** 4 - reminder is unknown. Applies to "Reminders Delete" request (Request ID=1) and "Reminders Modify" request (Request ID=2)
     * @throws IOException - TBD
     */
    @Test
    public void testDelete_statusCode4() throws IOException {
        starttime();
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.delete, count_reminders,
                reminderProgramStart(), reminderChannelNumber,
                reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
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
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
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
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        finishtime();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("", actual.get(2));
    }

}
