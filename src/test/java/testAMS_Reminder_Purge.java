import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class testAMS_Reminder_Purge extends API {

    private API_AMS AMS = new API_AMS();

    @Test
    public void testPurge() throws IOException {
        starttime();
        ArrayList actual = AMS.Request_purge(ams_ip, macaddress, Operation.purge, true);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testPurge_macaddress_empty() throws IOException {
        starttime();
        ArrayList actual = AMS.Request_purge(ams_ip, "", Operation.purge, true);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("REM-008 Reminders parsing error: wrong deviceId", actual.get(2));
    }

    @Test
    public void testPurge_macaddress_wrong() throws IOException {
        starttime();
        ArrayList actual = AMS.Request_purge(ams_ip, macaddress_wrong, Operation.purge, true);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testPurge_REM_ST_01_Box_is_not_registered() throws IOException {
        starttime();
        ArrayList actual = AMS.Request_purge("172.30.81.0", macaddress, Operation.purge, true);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected500, actual.get(0));
        assertEquals(expected500t, actual.get(1));
        assertEquals("REM-ST-001 Box is not registered", actual.get(2));
    }

    @Test
    public void testPurge_400_Bad_Request() throws IOException {
        starttime();
        ArrayList actual = AMS.Request_purge(ams_ip, macaddress, Operation.blablabla, true);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("Incorrect request", actual.get(2));
    }

    @Test
    @Deprecated
    public void testPurge_500_Internal_Server_Error() throws IOException {
        starttime();
        ArrayList actual = AMS.Request_purge(ams_ip, macaddress, Operation.purge, true);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected500, actual.get(0));
        assertEquals(expected500t, actual.get(1));
        assertEquals("REM-002 Reminders Service error: Timeout detected by BoxResponseTracker", actual.get(2));
    }

    @Test
    @Deprecated
    public void testPurge_504_Server_data_timeout() throws IOException {
        starttime();
        ArrayList actual = AMS.Request_purge(ams_ip, macaddress, Operation.purge, true);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected504, actual.get(0));
        assertEquals(expected504t, actual.get(1));
        assertEquals("", actual.get(2));
    }

}
