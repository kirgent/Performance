import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class testReminder_Add extends testAPI {

    //@RepeatedTest(1)
    @Test
    void testAdd_48() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Add", false, 48,
                reminderProgramStart_by_default, reminderChannelNumber_by_default, reminderProgramId_by_default,
                reminderOffset_by_default, reminderScheduleId_by_default, reminderId_by_default);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    void testAdd_288() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Add", false, 288,
                reminderProgramStart_by_default, reminderChannelNumber_by_default, reminderProgramId_by_default,
                reminderOffset_by_default, reminderScheduleId_by_default, reminderId_by_default);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    void testAdd_720() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Add", false, 720,
                reminderProgramStart_by_default, reminderChannelNumber_by_default, reminderProgramId_by_default,
                reminderOffset_by_default, reminderScheduleId_by_default, reminderId_by_default);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    /**2 - reminder is set for time in the past
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    void testAdd_statusCode2() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Add", false, count_reminders_by_default,
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
    void testAdd_statusCode3() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Add", false, count_reminders_by_default,
                reminderProgramStart_by_default, reminderChannelNumber_for_statuscode3, reminderProgramId_by_default,
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
    void testAdd_statusCode4() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Add", false, count_reminders_by_default,
                reminderProgramStart_by_default, reminderChannelNumber_by_default, reminderProgramId_by_default,
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
    void testAdd_statusCode5() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Add", false, count_reminders_by_default,
                reminderProgramStart_by_default, reminderChannelNumber_by_default, reminderProgramId_by_default,
                reminderOffset_by_default, reminderScheduleId_by_default, reminderId_by_default);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("5", actual.get(2));
    }

    @Test
    void testAdd_negative_with_empty_macaddress() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, "", "Add", false, count_reminders_by_default,
                reminderProgramStart_by_default, reminderChannelNumber_by_default, reminderProgramId_by_default,
                reminderOffset_by_default, reminderScheduleId_by_default, reminderId_by_default);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    void testAdd_negative_REM_ST_001_Box_is_not_registered() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation("172.30.81.0", macaddress[0], "Add", false, count_reminders_by_default,
                reminderProgramStart_by_default, reminderChannelNumber_by_default, reminderProgramId_by_default,
                reminderOffset_by_default, reminderScheduleId_by_default, reminderId_by_default);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, " + "return code: " + actual);
        assertEquals(expected500, actual.get(0));
        assertEquals(expected500t, actual.get(1));
        assertEquals("REM-ST-001 Box is not registered", actual.get(2));
    }

}
