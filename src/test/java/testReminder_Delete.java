import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class testReminder_Delete extends testAPI {

    @Test
    void testDelete_48() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Delete", false, 48,
                rack_date, rack_channel, reminderProgramId_by_default, reminderOffset_by_default,
                reminderScheduleId_by_default, reminderId_by_default);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    void testDelete_288() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Delete", false, 288,
                rack_date, rack_channel, reminderProgramId_by_default, reminderOffset_by_default,
                reminderScheduleId_by_default, reminderId_by_default);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    void testDelete_720() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        //ArrayList actual = api.Operation("Delete", macaddress[0], 720, count_iterations, ams_ip);
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Delete", false, 720,
                rack_date, rack_channel, reminderProgramId_by_default, reminderOffset_by_default,
                reminderScheduleId_by_default, reminderId_by_default);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    /** 2 - reminder is set for time in the past
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    void testDelete_statusCode2() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Delete", false, count_reminders_by_default,
                reminderProgramStart_for_statuscode2, reminderChannelNumber_by_default, reminderProgramId_by_default,
                reminderOffset_by_default, reminderScheduleId_by_default, reminderId_by_default);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("2", actual.get(2));
    }

    /**3 - reminder is set for unknown channel
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    void testDelete_statusCode3() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Delete", false, count_reminders_by_default,
                reminderProgramStart_for_statuscode2, reminderChannelNumber_for_statuscode3, reminderProgramId_by_default,
                reminderOffset_by_default, reminderScheduleId_by_default, reminderId_by_default);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("3", actual.get(2));
    }

    /**4 - reminder is unknown, applies to reminder deletion attempts
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    void testDelete_statusCode4() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Delete", false, count_reminders_by_default,
                rack_date, rack_channel, reminderProgramId_by_default,
                reminderOffset_by_default, reminderScheduleId_by_default, reminderId_by_default);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("4", actual.get(2));
    }

    /**5 - reminder with provided pair of identifiers (reminderScheduleId and reminderId) is already set (for Add Reminder request)
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    void testDelete_statusCode5() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Delete", false, count_reminders_by_default,
                rack_date, rack_channel, reminderProgramId_by_default,
                reminderOffset_by_default, reminderScheduleId_by_default, reminderId_by_default);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("5", actual.get(2));
    }

    @Test
    void testDelete_negative_with_empty_macaddress() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, "", "Delete", false, count_reminders_by_default,
                rack_date, rack_channel, reminderProgramId_by_default,
                reminderOffset_by_default, reminderScheduleId_by_default, reminderId_by_default);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    void testDelete_negative_REM_ST_001_Box_is_not_registered() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation("172.30.81.0", macaddress[0], "Delete", false, count_reminders_by_default,
                rack_date, rack_channel, reminderProgramId_by_default,
                reminderOffset_by_default, reminderScheduleId_by_default, reminderId_by_default);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, " + "return code: " + actual);
        assertEquals(expected500, actual.get(0));
        assertEquals(expected500t, actual.get(1));
        assertEquals("REM-ST-001 Box is not registered", actual.get(2));
    }

}
