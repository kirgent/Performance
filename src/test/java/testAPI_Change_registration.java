import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class testAPI_Change_registration extends API {

    @Test
    public void testChange_registration_via_charterapi_() throws IOException {
        starttime();
        ArrayList actual = api.Change_registration(macaddress, charterapi_, ams_ip);
        finishtime();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testChange_registration_via_charterapi_b() throws IOException, InterruptedException {
        starttime();
        ArrayList actual = api.Change_registration(macaddress, charterapi_b, ams_ip);
        finishtime();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testChange_registration_via_charterapi_c() throws IOException, InterruptedException {
        starttime();
        ArrayList actual = api.Change_registration(macaddress, charterapi_c, ams_ip);
        finishtime();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testChange_registration_via_charterapi_d() throws IOException, InterruptedException {
        starttime();
        ArrayList actual = api.Change_registration(macaddress, charterapi_d, ams_ip);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testChange_registration_to_invalid_ams127_0_0_1() throws IOException, InterruptedException {
        starttime();
        ArrayList actual = api.Change_registration(macaddress, charterapi, "127.0.0.1");
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

}
