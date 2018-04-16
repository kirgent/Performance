import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * We are as Middle: chain of requests: localhost -> AMS -> box -> AMS -> localhost (Middle)
 */
@Deprecated
public class testAMS_Reminder_OldAPI extends API {

    private API_AMS AMS = new API_AMS();

    //@RepeatedTest(1)
    @Test
    public void testAdd_Delete() throws IOException {
        int count_reminders = 1;
        ArrayList actual = AMS.Request(mac, Operation.add, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderOffset);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.Request(mac, Operation.delete, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderOffset);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    //@RepeatedTest(3)
    @Test
    public void testAdd() throws IOException {
        ArrayList actual = AMS.Request(mac, Operation.add, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderOffset);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testAdd_400_Bad_Request() throws IOException {
        ArrayList actual = AMS.Request(mac, Operation.add, count_reminders,
                "YYYY-MM-DD", reminderChannelNumber, reminderOffset);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: incorrect message format", actual.get(1));
    }

    @Test
    public void testAdd_REM_ST_001_Box_is_not_registered() throws IOException {
        ArrayList actual = AMS.Request(mac, Operation.add, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderOffset);
        assertEquals(expected500, actual.get(0));
        assertEquals("REM-ST-001 Box is not registered", actual.get(1));
    }

    /** 2 - reminder is set for time in the past
     * @throws IOException - TBD
     */
    @Test
    public void testAdd_statusCode2() throws IOException {
        ArrayList actual = AMS.Request(mac, Operation.add, count_reminders,
                reminderProgramStart_past, reminderChannelNumber, reminderOffset);
        assertEquals(expected200, actual.get(0));
        assertEquals("2", actual.get(1));
    }

    /** 3 - reminder is set for unknown channel
     * @throws IOException - TBD
     */
    @Test
    public void testAdd_statusCode3() throws IOException {
        ArrayList actual = AMS.Request(mac, Operation.add, count_reminders,
                reminderProgramStart(), reminderChannelNumber_for_statuscode3, reminderOffset);
        assertEquals(expected200, actual.get(0));
        assertEquals("3", actual.get(1));
    }

    /** 4 - reminder is unknown, applies to reminder deletion attempts
     * @throws IOException - TBD
     */
    @Test
    public void testAdd_statusCode4() throws IOException {
        ArrayList actual = AMS.Request(mac, Operation.add, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderOffset);
        assertEquals(expected200, actual.get(0));
        assertEquals("4", actual.get(1));
    }

    @Test
    public void testDelete() throws IOException {
        ArrayList actual = AMS.Request(mac, Operation.delete, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderOffset);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testDelete_400_Bad_Request() throws IOException {
        ArrayList actual = AMS.Request(mac, Operation.delete, count_reminders,
                "YYYY-MM-DD", reminderChannelNumber, reminderOffset);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: incorrect message format", actual.get(1));
    }

    @Test
    public void testDelete_REM_ST_001_Box_is_not_registered() throws IOException {
        ArrayList actual = AMS.Request(mac, Operation.delete, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderOffset);
        assertEquals(expected500, actual.get(0));
        assertEquals("REM-ST-001 Box is not registered", actual.get(1));
    }

    @Test
    public void testPurge() throws IOException {
        ArrayList actual = AMS.Request(mac, Operation.purge, false);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

    }

    @Test
    public void testPurge_mac_empty() throws IOException {
        ArrayList actual = AMS.Request("", Operation.purge, false);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: wrong deviceId", actual.get(1));
    }

    @Test
    public void testPurge_mac_wrong() throws IOException {
        ArrayList actual = AMS.Request(mac_wrong, Operation.purge, false);
        assertEquals(expected500, actual.get(0));
        assertEquals("REM-ST-001 Box is not registered", actual.get(1));
    }

    @Test
    public void testPurge_REM_ST_01_Box_is_not_registered() throws IOException {
        ArrayList actual = AMS.Request(mac, Operation.purge, false);
        assertEquals(expected500, actual.get(0));
        assertEquals("REM-ST-001 Box is not registered", actual.get(1));
    }

    @Test
    @Deprecated
    public void testPurge_500_Internal_Server_Error() throws IOException {
        ArrayList actual = AMS.Request(mac, Operation.purge, false);
        assertEquals(expected500, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    @Deprecated
    public void testPurge_504_Server_data_timeout() throws IOException {
        ArrayList actual = AMS.Request(mac, Operation.purge, false);
        assertEquals(expected504, actual.get(0));
        assertEquals("", actual.get(1));
    }

    /** 2 - reminder is set for time in the past
     * @throws IOException - TBD
     */
    @Test
    public void testDelete_statusCode2() throws IOException {
        ArrayList actual = AMS.Request(mac, Operation.delete, count_reminders,
                reminderProgramStart_past, reminderChannelNumber, reminderOffset);
        assertEquals(expected200, actual.get(0));
        assertEquals("2", actual.get(1));
    }

    /** 3 - reminder is set for unknown channel
     * @throws IOException - TBD
     */
    @Test
    public void testDelete_statusCode3() throws IOException {
        ArrayList actual = AMS.Request(mac, Operation.delete, count_reminders,
                reminderProgramStart(), reminderChannelNumber_for_statuscode3, reminderOffset);
        assertEquals(expected200, actual.get(0));
        assertEquals("3", actual.get(1));
    }

    /** 4 - reminder is unknown, applies to reminder deletion attempts
     * @throws IOException - TBD
     */
    @Test
    public void testDelete_statusCode4() throws IOException {
        ArrayList actual = AMS.Request(mac, Operation.delete, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderOffset);
        assertEquals(expected200, actual.get(0));
        assertEquals("4", actual.get(1));
    }

    @Test
    public void testOperation_wrong_400_Bad_Request() throws IOException {
        ArrayList actual = AMS.Request(mac, Operation.blablabla, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderOffset);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: wrong operation", actual.get(1));
    }

}
