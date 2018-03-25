import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class testReminder_Modify extends API {

    @Test
    public void testModify() throws IOException, InterruptedException {
        //"reminderChannelNumber": <new value for the DCN the reminder is set to>,
        //"reminderProgramStart": "<new value for the date/time of program the reminder is set to>",
        //"reminderProgramId": "<new value for the TMS Program ID the reminder is set to>",
        //"reminderOffset": <new value for the number of minutes before the program start the reminder should be shown>
        //"reminderScheduleId": "<series or Individual program reminder schedule reference ID>",
        //"reminderId": "<episode or Individual program reminder reference ID of a particular schedule>",
        starttime();
        ArrayList actual = api.Request(ams_ip, macaddress, Operation.modify, true, count_reminders,
                get_date(), reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        finishtime();
        System.out.println("[DBG] " + (finish-start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testModify_reminderChannelNumber_empty() throws IOException, InterruptedException {
        starttime();
        ArrayList actual = api.Request(ams_ip, macaddress, Operation.modify, true, count_reminders,
                get_date(), reminderChannelNumber_empty, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testModify_reminderChannelNumber_negative() throws IOException, InterruptedException {
        starttime();
        ArrayList actual = api.Request(ams_ip, macaddress, Operation.modify, true, count_reminders,
                get_date(), -1, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testModify_reminderChannelNumber_MAX_VALUE() throws IOException, InterruptedException {
        starttime();
        ArrayList actual = api.Request(ams_ip, macaddress, Operation.modify, true, count_reminders,
                get_date(), Integer.MAX_VALUE, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testModify_reminderChannelNumber_MIN_VALUE() throws IOException, InterruptedException {
        starttime();
        ArrayList actual = api.Request(ams_ip, macaddress, Operation.modify, true, count_reminders,
                get_date(), Integer.MIN_VALUE, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testModify_reminderProgramStart_PAST() throws IOException, InterruptedException {
        starttime();
        ArrayList actual = api.Request(ams_ip, macaddress, Operation.modify, true, count_reminders,
                "0000-00-00", reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testModify_reminderProgramStart_empty() throws IOException, InterruptedException {
        starttime();
        ArrayList actual = api.Request(ams_ip, macaddress, Operation.modify, true, count_reminders,
                "", reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testModify_reminderProgramStart_wrong() throws IOException, InterruptedException {
        starttime();
        ArrayList actual = api.Request(ams_ip, macaddress, Operation.modify, true, count_reminders,
                "YYYY-mm-dd", reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testModify_reminderProgramId_empty() throws IOException, InterruptedException {
        starttime();
        ArrayList actual = api.Request(ams_ip, macaddress, Operation.modify, true, count_reminders,
                get_date(), reminderChannelNumber, "",
                reminderOffset, reminderScheduleId, reminderId);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testModify_reminderProgramId_wrong() throws IOException, InterruptedException {
        starttime();
        ArrayList actual = api.Request(ams_ip, macaddress, Operation.modify, true, count_reminders,
                get_date(), reminderChannelNumber, "EP#@$%#$%@#$^$#%^#$%^",
                reminderOffset, reminderScheduleId, reminderId);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testModify_reminderOffset_empty() throws IOException, InterruptedException {
        starttime();
        ArrayList actual = api.Request(ams_ip, macaddress, Operation.modify, true, count_reminders,
                get_date(), reminderChannelNumber, reminderProgramId,
                reminderOffset_empty, reminderScheduleId, reminderId);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testModify_reminderOffset_MAX_VALUE() throws IOException, InterruptedException {
        starttime();
        ArrayList actual = api.Request(ams_ip, macaddress, Operation.modify, true, count_reminders,
                get_date(), reminderChannelNumber, reminderProgramId,
                Integer.MAX_VALUE, reminderScheduleId, reminderId);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testModify_reminderOffset_MIN_VALUE() throws IOException, InterruptedException {
        starttime();
        ArrayList actual = api.Request(ams_ip, macaddress, Operation.modify, true, count_reminders,
                get_date(), reminderChannelNumber, reminderProgramId,
                Integer.MIN_VALUE, reminderScheduleId, reminderId);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testModify_reminderOffset_negative() throws IOException, InterruptedException {
        starttime();
        ArrayList actual = api.Request(ams_ip, macaddress, Operation.modify, true, count_reminders,
                get_date(), reminderChannelNumber, reminderProgramId,
                -1, reminderScheduleId, reminderId);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testModify_reminderScheduleId_empty() throws IOException, InterruptedException {
        starttime();
        ArrayList actual = api.Request(ams_ip, macaddress, Operation.modify, true, count_reminders,
                get_date(), reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId_empty, reminderId);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testModify_reminderScheduleId_MAX_VALUE() throws IOException, InterruptedException {
        starttime();
        ArrayList actual = api.Request(ams_ip, macaddress, Operation.modify, true, count_reminders,
                get_date(), reminderChannelNumber, reminderProgramId,
                reminderOffset, Integer.MAX_VALUE, reminderId);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testModify_reminderScheduleId_MIN_VALUE() throws IOException, InterruptedException {
        starttime();
        ArrayList actual = api.Request(ams_ip, macaddress, Operation.modify, true, count_reminders,
                get_date(), reminderChannelNumber, reminderProgramId,
                reminderOffset, Integer.MIN_VALUE, reminderId);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testModify_reminderScheduleId_negative() throws IOException, InterruptedException {
        starttime();
        ArrayList actual = api.Request(ams_ip, macaddress, Operation.modify, true, count_reminders,
                get_date(), reminderChannelNumber, reminderProgramId,
                reminderOffset, -1, reminderId);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testModify_reminderId_empty() throws IOException, InterruptedException {
        starttime();
        ArrayList actual = api.Request(ams_ip, macaddress, Operation.modify, true, count_reminders,
                get_date(), reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId_empty);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testModify_reminderId_MAX_VALUE() throws IOException, InterruptedException {
        starttime();
        ArrayList actual = api.Request(ams_ip, macaddress, Operation.modify, true, count_reminders,
                get_date(), reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, Integer.MAX_VALUE);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testModify_reminderId_MIN_VALUE() throws IOException, InterruptedException {
        starttime();
        ArrayList actual = api.Request(ams_ip, macaddress, Operation.modify, true, count_reminders,
                get_date(), reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, Integer.MIN_VALUE);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testModify_reminderId_negative() throws IOException, InterruptedException {
        starttime();
        ArrayList actual = api.Request(ams_ip, macaddress, Operation.modify, true, count_reminders,
                get_date(), reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, -1);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    /** 2 - reminder is set for time in the past
     * @throws IOException - TBD
     * @throws InterruptedException - TBD
     */
    @Test
    public void testModify_statusCode2() throws IOException, InterruptedException {
        starttime();
        ArrayList actual = api.Request(ams_ip, macaddress, Operation.modify, true, count_reminders,
                reminderProgramStart_for_statuscode2, reminderChannelNumber,
                reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        finishtime();
        System.out.println("[DBG] " + (finish-start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("2", actual.get(2));
    }

    /** 3 - reminder is set for unknown channel
     * @throws IOException - TBD
     * @throws InterruptedException - TBD
     */
    @Test
    public void testModify_statusCode3() throws IOException, InterruptedException {
        starttime();
        ArrayList actual = api.Request(ams_ip, macaddress, Operation.modify, true, count_reminders,
                reminderProgramStart_for_statuscode2, reminderChannelNumber_for_statuscode3,
                reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        finishtime();
        System.out.println("[DBG] " + (finish-start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("3", actual.get(2));
    }

    /** 4 - reminder is unknown, applies to reminder deletion attempts
     * @throws IOException - TBD
     * @throws InterruptedException - TBD
     */
    @Test
    public void testModify_statusCode4() throws IOException, InterruptedException {
        starttime();
        ArrayList actual = api.Request(ams_ip, macaddress, Operation.modify, true, count_reminders,
                get_date(), reminderChannelNumber,
                reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        finishtime();
        System.out.println("[DBG] " + (finish-start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("4", actual.get(2));
    }

    /** 5 - reminder with provided pair of identifiers (reminderScheduleId and reminderId) is already set (for Add Reminder request)
     * @throws IOException - TBD
     * @throws InterruptedException - TBD
     */
    @Test
    public void testModify_statusCode5() throws IOException, InterruptedException {
        starttime();
        ArrayList actual = api.Request(ams_ip, macaddress, Operation.modify, true, count_reminders,
                get_date(), reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        finishtime();
        System.out.println("[DBG] " + (finish-start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("5", actual.get(2));
    }

    @Test
    public void testModify_macaddress_empty() throws IOException, InterruptedException {
        starttime();
        ArrayList actual = api.Request(ams_ip, "", Operation.modify, true, count_reminders,
                get_date(), reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        finishtime();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testModify_macaddress_wrong() throws IOException, InterruptedException {
        starttime();
        ArrayList actual = api.Request(ams_ip, macaddress_wrong, Operation.modify, true, count_reminders,
                get_date(), reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        finishtime();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testModify_REM_ST_001_Box_is_not_registered() throws IOException, InterruptedException {
        starttime();
        ArrayList actual = api.Request(ams_ip, macaddress, Operation.modify, true, count_reminders,
                get_date(), reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected500, actual.get(0));
        assertEquals(expected500t, actual.get(1));
        assertEquals("REM-ST-001 Box is not registered", actual.get(2));
    }

}
