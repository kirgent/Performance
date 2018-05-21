package tv.zodiac.dev;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * We are localhost (Charter Headend). Full chain of requests: localhost -> AMS -> STB -> AMS -> localhost
 */
class testMiddle_Check_registration extends API_Middle {

    private API_Middle Middle = new API_Middle();
    //String mac = boxMoto2145_173;

    @Test
    @Deprecated
    void testCheck_registration_via_charterapi_a() throws IOException {
        ArrayList actual = Middle.Check_registration(mac, charterapi_a);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    void testCheck_registration_via_charterapi_b() throws IOException {
        ArrayList actual = Middle.Check_registration(mac, charterapi_b);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    void testCheck_registration_via_charterapi_c() throws IOException {
        ArrayList actual = Middle.Check_registration(mac, charterapi_c);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    void testCheck_registration_via_charterapi_d() throws IOException {
        ArrayList actual = Middle.Check_registration(mac, charterapi_d);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    @Disabled
    void testCheck_registration_a_No_amsIp_found_for_mac() throws IOException {
        ArrayList actual = Middle.Check_registration("123456789012", charterapi_a);
        assertEquals(expected500, actual.get(0));
        assertEquals("No amsIp found for mac", actual.get(1));
    }

    @Test
    @Disabled
    void testCheck_registration_b_No_amsIp_found_for_mac() throws IOException {
        ArrayList actual = Middle.Check_registration("123456789012", charterapi_b);
        assertEquals(expected500, actual.get(0));
        assertEquals("No amsIp found for mac", actual.get(1));
    }

    @Test
    @Disabled
    void testCheck_registration_c_No_amsIp_found_for_mac() throws IOException {
        ArrayList actual = Middle.Check_registration("123456789012", charterapi_c);
        assertEquals(expected500, actual.get(0));
        assertEquals("No amsIp found for mac", actual.get(1));
    }

    @Test
    @Disabled
    void testCheck_registration_d_No_amsIp_found_for_mac() throws IOException {
        ArrayList actual = Middle.Check_registration("123456789012", charterapi_d);
        assertEquals(expected500, actual.get(0));
        assertEquals("No amsIp found for mac", actual.get(1));
    }

}
