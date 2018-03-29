import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Deprecated
public class testReminder_OldAPI extends API {

    //@RepeatedTest(1)
    @Test
    public void testAdd_Delete() throws IOException, InterruptedException {
        starttime();
        int count_reminders = 1;
        ArrayList actual = api.Request(ams_ip, macaddress, Operation.add, count_reminders,
                get_date(), reminderChannelNumber, reminderOffset);
        System.out.println("[DBG] return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));

        actual = api.Request(ams_ip, macaddress, Operation.delete, count_reminders,
                get_date(), reminderChannelNumber, reminderOffset);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    //@RepeatedTest(3)
    @Test
    public void testAdd() throws IOException, InterruptedException {
        starttime();
        ArrayList actual = api.Request(ams_ip, macaddress, Operation.add, count_reminders,
                get_date(), reminderChannelNumber, reminderOffset);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testAdd_400_Bad_Request() throws IOException, InterruptedException {
        starttime();
        ArrayList actual = api.Request(ams_ip, macaddress, Operation.add, count_reminders,
                "YYYY-MM-DD", reminderChannelNumber, reminderOffset);
        finishtime();
        System.out.println("[DBG] " + (finish-start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("REM-008 Reminders parsing error: incorrect message format", actual.get(2));
    }

    @Test
    public void testAdd_REM_ST_001_Box_is_not_registered() throws IOException, InterruptedException {
        starttime();
        ArrayList actual = api.Request("172.30.81.0", macaddress, Operation.add, count_reminders,
                get_date(), reminderChannelNumber, reminderOffset);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected500, actual.get(0));
        assertEquals(expected500t, actual.get(1));
        assertEquals("REM-ST-001 Box is not registered", actual.get(2));
    }

    /** 2 - reminder is set for time in the past
     * @throws IOException - TBD
     * @throws InterruptedException - TBD
     */
    @Test
    public void testAdd_statusCode2() throws IOException, InterruptedException {
        starttime();
        ArrayList actual = api.Request(ams_ip, macaddress, Operation.add, count_reminders,
                reminderProgramStart_for_statuscode2, reminderChannelNumber, reminderOffset);
        finishtime();
        System.out.println("[DBG] " + (finish-start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("2", actual.get(2));
    }

    /** 3 - reminder is set for unknown channel
     * @throws IOException - TBD
     * @throws InterruptedException - TBD
     */
    @Test
    public void testAdd_statusCode3() throws IOException, InterruptedException {
        starttime();
        ArrayList actual = api.Request(ams_ip, macaddress, Operation.add, count_reminders,
                get_date(), reminderChannelNumber_for_statuscode3, reminderOffset);
        finishtime();
        System.out.println("[DBG] " + (finish-start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("3", actual.get(2));
    }

    /** 4 - reminder is unknown, applies to reminder deletion attempts
     * @throws IOException - TBD
     * @throws InterruptedException - TBD
     */
    @Test
    public void testAdd_statusCode4() throws IOException, InterruptedException {
        starttime();
        ArrayList actual = api.Request(ams_ip, macaddress, Operation.add, count_reminders,
                get_date(), reminderChannelNumber, reminderOffset);
        finishtime();
        System.out.println("[DBG] " + (finish-start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("4", actual.get(2));
    }

    @Test
    public void testDelete() throws IOException, InterruptedException {
        starttime();
        ArrayList actual = api.Request(ams_ip, macaddress, Operation.delete, count_reminders,
                get_date(), reminderChannelNumber, reminderOffset);
        finishtime();
        System.out.println("[DBG] " + (finish-start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testDelete_400_Bad_Request() throws IOException, InterruptedException {
        starttime();
        ArrayList actual = api.Request(ams_ip, macaddress, Operation.delete, count_reminders,
                "YYYY-MM-DD", reminderChannelNumber, reminderOffset);
        finishtime();
        System.out.println("[DBG] " + (finish-start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("REM-008 Reminders parsing error: incorrect message format", actual.get(2));
    }

    @Test
    public void testDelete_REM_ST_001_Box_is_not_registered() throws IOException, InterruptedException {
        starttime();
        ArrayList actual = api.Request("172.30.81.0", macaddress, Operation.delete, count_reminders,
                get_date(), reminderChannelNumber, reminderOffset);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected500, actual.get(0));
        assertEquals(expected500t, actual.get(1));
        assertEquals("REM-ST-001 Box is not registered", actual.get(2));
    }

    @Test
    public void testPurge() throws IOException, InterruptedException {
        starttime();
        ArrayList actual = api.Request(ams_ip, macaddress, Operation.purge, false);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));

    }

    @Test
    public void testPurge_macaddress_empty() throws IOException, InterruptedException {
        starttime();
        ArrayList actual = api.Request(ams_ip, "", Operation.purge, false);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("REM-008 Reminders parsing error: wrong deviceId", actual.get(2));
    }

    @Test
    public void testPurge_macaddress_wrong() throws IOException, InterruptedException {
        starttime();
        ArrayList actual = api.Request(ams_ip, macaddress_wrong, Operation.purge, false);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected500, actual.get(0));
        assertEquals(expected500t, actual.get(1));
        assertEquals("REM-ST-001 Box is not registered", actual.get(2));
    }

    @Test
    public void testPurge_REM_ST_01_Box_is_not_registered() throws IOException, InterruptedException {
        starttime();
        ArrayList actual = api.Request("172.30.81.0", macaddress, Operation.purge, false);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected500, actual.get(0));
        assertEquals(expected500t, actual.get(1));
        assertEquals("REM-ST-001 Box is not registered", actual.get(2));
    }

    @Test
    @Deprecated
    public void testPurge_500_Internal_Server_Error() throws IOException, InterruptedException {
        starttime();
        ArrayList actual = api.Request(ams_ip, macaddress, Operation.purge, false);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected500, actual.get(0));
        assertEquals(expected500t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    @Deprecated
    public void testPurge_504_Server_data_timeout() throws IOException, InterruptedException {
        starttime();
        ArrayList actual = api.Request(ams_ip, macaddress, Operation.purge, false);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected504, actual.get(0));
        assertEquals(expected504t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    /** 2 - reminder is set for time in the past
     * @throws IOException - TBD
     * @throws InterruptedException - TBD
     */
    @Test
    public void testDelete_statusCode2() throws IOException, InterruptedException {
        starttime();
        ArrayList actual = api.Request(ams_ip, macaddress, Operation.delete, count_reminders,
                reminderProgramStart_for_statuscode2, reminderChannelNumber, reminderOffset);
        finishtime();
        System.out.println("[DBG] " + (finish-start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("2", actual.get(2));
    }

    /** 3 - reminder is set for unknown channel
     * @throws IOException - TBD
     * @throws InterruptedException - TBD
     */
    @Test
    public void testDelete_statusCode3() throws IOException, InterruptedException {
        starttime();
        ArrayList actual = api.Request(ams_ip, macaddress, Operation.delete, count_reminders,
                get_date(), reminderChannelNumber_for_statuscode3, reminderOffset);
        finishtime();
        System.out.println("[DBG] " + (finish-start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("3", actual.get(2));
    }

    /** 4 - reminder is unknown, applies to reminder deletion attempts
     * @throws IOException - TBD
     * @throws InterruptedException - TBD
     */
    @Test
    public void testDelete_statusCode4() throws IOException, InterruptedException {
        starttime();
        ArrayList actual = api.Request(ams_ip, macaddress, Operation.delete, count_reminders,
                get_date(), reminderChannelNumber, reminderOffset);
        finishtime();
        System.out.println("[DBG] " + (finish-start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("4", actual.get(2));
    }

    @Test
    public void testOperation_wrong_400_Bad_Request() throws IOException, InterruptedException {
        starttime();
        ArrayList actual = api.Request(ams_ip, macaddress, Operation.blablabla, count_reminders,
                get_date(), reminderChannelNumber, reminderOffset);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("REM-008 Reminders parsing error: wrong operation", actual.get(2));
    }

}
