import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class testMiddle_Change_registration extends API {

    private API_Middle Middle = new API_Middle();

    @Test
    public void testChange_registration_via_charterapi_a() throws IOException {
        starttime();
        ArrayList actual = Middle.Change_registration(macaddress, charterapi_a, ams_ip);
        finishtime();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testChange_registration_via_charterapi_b() throws IOException, InterruptedException {
        starttime();
        ArrayList actual = Middle.Change_registration(macaddress, charterapi_b, ams_ip);
        finishtime();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testChange_registration_via_charterapi_c() throws IOException, InterruptedException {
        starttime();
        ArrayList actual = Middle.Change_registration(macaddress, charterapi_c, ams_ip);
        finishtime();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testChange_registration_via_charterapi_d() throws IOException, InterruptedException {
        starttime();
        ArrayList actual = Middle.Change_registration(macaddress, charterapi_d, ams_ip);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    @Ignore
    public void testChange_registration_to_invalid_ams127_0_0_1() throws IOException, InterruptedException {
        starttime();
        ArrayList actual = Middle.Change_registration(macaddress, charterapi_default, "127.0.0.1");
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

}
