import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class testAPI_Change_registration extends API {

    @Test
    public void testChange_registration_via_charterapi_() throws IOException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Change_registration(macaddress[0], charterapi_, ams_ip);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testChange_registration_via_charterapi_b() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Change_registration(macaddress[0], charterapi_b, ams_ip);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testChange_registration_via_charterapi_c() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Change_registration(macaddress[0], charterapi_c, ams_ip);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testChange_registration_via_charterapi_d() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Change_registration(macaddress[0], charterapi_d, ams_ip);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

}
