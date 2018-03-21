import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class testReminder_Add extends API {

    //@RepeatedTest(3)
    @Test
    public void testAdd() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Add", false, 1,
                reminderProgramStart, reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testAdd_reminderChannelNumber_empty() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Add", false, 1,
                reminderProgramStart, reminderChannelNumber_empty, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testAdd_reminderChannelNumber_negative() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Add", false, 1,
                reminderProgramStart, -1, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testAdd_reminderChannelNumber_MAX_VALUE() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Add", false, 1,
                reminderProgramStart, Integer.MAX_VALUE, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testAdd_reminderChannelNumber_MIN_VALUE() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Add", false, 1,
                reminderProgramStart, Integer.MIN_VALUE, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testAdd_reminderProgramStart_PAST() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Add", false, 1,
                "0000-00-00", reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testAdd_reminderProgramStart_empty() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Add", false, 1,
                "", reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testAdd_reminderProgramStart_wrong() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Add", false, 1,
                "YYYY-mm-dd", reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testAdd_reminderProgramId_empty() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Add", false, 1,
                reminderProgramStart, reminderChannelNumber, "",
                reminderOffset, reminderScheduleId, reminderId);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testAdd_reminderProgramId_wrong() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Add", false, 1,
                reminderProgramStart, reminderChannelNumber, "EP#@$%#$%@#$^$#%^#$%^",
                reminderOffset, reminderScheduleId, reminderId);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testAdd_reminderOffset_empty() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Add", false, 1,
                reminderProgramStart, reminderChannelNumber, reminderProgramId,
                reminderOffset_empty, reminderScheduleId, reminderId);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testAdd_reminderOffset_MAX_VALUE() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Add", false, 1,
                reminderProgramStart, reminderChannelNumber, reminderProgramId,
                Integer.MAX_VALUE, reminderScheduleId, reminderId);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testAdd_reminderOffset_MIN_VALUE() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Add", false, 1,
                reminderProgramStart, reminderChannelNumber, reminderProgramId,
                Integer.MIN_VALUE, reminderScheduleId, reminderId);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testAdd_reminderOffset_negative() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Add", false, 1,
                reminderProgramStart, reminderChannelNumber, reminderProgramId,
                -1, reminderScheduleId, reminderId);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testAdd_reminderScheduleId_empty() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Add", false, 1,
                reminderProgramStart, reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId_empty, reminderId);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testAdd_reminderScheduleId_MAX_VALUE() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Add", false, 1,
                reminderProgramStart, reminderChannelNumber, reminderProgramId,
                reminderOffset, Integer.MAX_VALUE, reminderId);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testAdd_reminderScheduleId_MIN_VALUE() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Add", false, 1,
                reminderProgramStart, reminderChannelNumber, reminderProgramId,
                reminderOffset, Integer.MIN_VALUE, reminderId);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testAdd_reminderScheduleId_negative() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Add", false, 1,
                reminderProgramStart, reminderChannelNumber, reminderProgramId,
                reminderOffset, -1, reminderId);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testAdd_reminderId_empty() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Add", false, 1,
                reminderProgramStart, reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId_empty);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testAdd_reminderId_MAX_VALUE() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Add", false, 1,
                reminderProgramStart, reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, Integer.MAX_VALUE);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testAdd_reminderId_MIN_VALUE() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Add", false, 1,
                reminderProgramStart, reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, Integer.MIN_VALUE);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testAdd_reminderId_negative() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Add", false, 1,
                reminderProgramStart, reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, -1);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    /**2 - reminder is set for time in the past
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    public void testAdd_statusCode2() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Add", false, 1,
                reminderProgramStart_for_statuscode2, reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
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
    public void testAdd_statusCode3() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Add", false, 1,
                reminderProgramStart, reminderChannelNumber_for_statuscode3, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
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
    public void testAdd_statusCode4() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Add", false, 1,
                reminderProgramStart, reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
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
    public void testAdd_statusCode5() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Add", false, 1,
                reminderProgramStart, reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("5", actual.get(2));
    }

    @Test
    public void testAdd_macaddress_empty() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, "", "Add", false, 1,
                reminderProgramStart, reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("", actual.get(2));
    }

}
