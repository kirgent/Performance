package tv.zodiac.dev;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * We are Headend (on localhost): chain of requests: Headend(localhost) -> AMS -> STB -> AMS -> localhost
 */
class testMiddle_Change_registration extends API_Middle {

    private API_Middle Middle = new API_Middle();

    String mac = boxD105;

    @Test
    void testChange_registration_via_charterapi_a() throws IOException {
        ArrayList actual = Middle.Change_registration(charterapi_a, mac, ams_ip);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    void testChange_registration_via_charterapi_b() throws IOException, InterruptedException {
        ArrayList actual = Middle.Change_registration(charterapi_b, mac, ams_ip);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    void testChange_registration_via_charterapi_c() throws IOException, InterruptedException {
        ArrayList actual = Middle.Change_registration(charterapi_c, mac, ams_ip);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    void testChange_registration_via_charterapi_d() throws IOException, InterruptedException {
        ArrayList actual = Middle.Change_registration(charterapi_d, mac, ams_ip);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

}
