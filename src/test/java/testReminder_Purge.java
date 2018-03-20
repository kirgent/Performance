import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class testReminder_Purge extends API {

    @Test
    void testPurgeNew() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Purge", true, 0, "", 0, "", 0, 0, 0);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    void testPurgeNew_negative_with_empty_macaddress() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, "", "Purge", true, 0, "", 0, "", 0, 0, 0);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    void testPurgeNew_negative_REM_ST_01_Box_is_not_registered() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation("172.30.81.0", macaddress[0], "Purge", true, 0, "", 0, "", 0, 0, 0);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected500, actual.get(0));
        assertEquals(expected500t, actual.get(1));
        assertEquals("REM-ST-001 Box is not registered", actual.get(2));
    }

    @Test
    void testPurgeNew_negative_400_Bad_Request() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Purge", true, 0, "", 0, "", 0, 0, 0);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    void testPurgeNew_negative_500_Internal_Server_Error() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Purge", true, 0, "", 0, "", 0, 0, 0);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected500, actual.get(0));
        assertEquals(expected500t, actual.get(1));
        assertEquals("REM-002 Reminders Service error: REM-012", actual.get(2));
    }

    @Test
    void testPurgeNew_negative_504_Server_data_timeout() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Purge", true, 0, "", 0, "", 0, 0, 0);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected504, actual.get(0));
        assertEquals(expected504t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    void testPurge() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Purge", false, 0, "", 0, "", 0, 0, 0);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));

    }

    @Test
    void testPurge_negative_with_empty_macaddress() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, "", "Purge", false, 0, "", 0, "", 0, 0, 0);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    void testPurge_negative_REM_ST_01_Box_is_not_registered() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation("172.30.81.0", macaddress[0], "Purge", false, 0, "", 0, "", 0, 0, 0);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected500, actual.get(0));
        assertEquals(expected500t, actual.get(1));
        assertEquals("REM-ST-001 Box is not registered", actual.get(2));
    }

    @Test
    void testPurge_negative_504_Server_data_timeout() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Purge", false, 0, "", 0, "", 0, 0, 0);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected504, actual.get(0));
        assertEquals(expected504t, actual.get(1));
        assertEquals("", actual.get(2));
    }

}
