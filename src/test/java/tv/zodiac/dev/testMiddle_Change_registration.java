package tv.zodiac.dev;

import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * We are Headend (on localhost): chain of requests: localhost -> AMS -> STB -> AMS -> localhost
 */
public class testMiddle_Change_registration extends API_Middle {

    private API_Middle Middle = new API_Middle();

    @Test
    public void testChange_registration_via_charterapi_a() throws IOException {
        ArrayList actual = Middle.Change_registration(mac, charterapi_a, ams_ip);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testChange_registration_via_charterapi_b() throws IOException, InterruptedException {
        ArrayList actual = Middle.Change_registration(mac, charterapi_b, ams_ip);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testChange_registration_via_charterapi_c() throws IOException, InterruptedException {
        ArrayList actual = Middle.Change_registration(mac, charterapi_c, ams_ip);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testChange_registration_via_charterapi_d() throws IOException, InterruptedException {
        ArrayList actual = Middle.Change_registration(mac, charterapi_d, ams_ip);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    @Ignore
    public void testChange_registration_to_invalid_ams127_0_0_1() throws IOException, InterruptedException {
        ArrayList actual = Middle.Change_registration(mac, charterapi, "127.0.0.1");
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

}
