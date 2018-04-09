import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * We are as Middle: chain of requests: localhost -> AMS -> box -> AMS -> localhost (Middle)
 */
public class testAMS_Reminder_Purge extends API {

    private API_AMS AMS = new API_AMS();

    @Test
    //@After
    public void testPurge() throws IOException {
        ArrayList actual = AMS.Request(macaddress, Operation.purge, true);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testPurge_macaddress_empty() throws IOException {
        ArrayList actual = AMS.Request("", Operation.purge, true);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: wrong deviceId", actual.get(1));
    }

    @Test
    public void testPurge_macaddress_wrong() throws IOException {
        ArrayList actual = AMS.Request(macaddress_wrong, Operation.purge, true);
        assertEquals(expected400, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testPurge_REM_ST_01_Box_is_not_registered() throws IOException {
        ArrayList actual = AMS.Request(macaddress, Operation.purge, true);
        assertEquals(expected500, actual.get(0));
        assertEquals("REM-ST-001 Box is not registered", actual.get(1));
    }

    @Test
    public void testPurge_400_Bad_Request() throws IOException {
        ArrayList actual = AMS.Request(macaddress, Operation.blablabla, true);
        assertEquals(expected400, actual.get(0));
        assertEquals("Incorrect request", actual.get(1));
    }

    @Test
    @Deprecated
    public void testPurge_500_Internal_Server_Error() throws IOException {
        ArrayList actual = AMS.Request(macaddress, Operation.purge, true);
        assertEquals(expected500, actual.get(0));
        assertEquals("REM-002 Reminders Service error: Timeout detected by BoxResponseTracker", actual.get(1));
    }

    @Test
    @Deprecated
    public void testPurge_504_Server_data_timeout() throws IOException {
        ArrayList actual = AMS.Request(macaddress, Operation.purge, true);
        assertEquals(expected504, actual.get(0));
        assertEquals("", actual.get(1));
    }

}
