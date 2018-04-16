import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * We are as Middle: chain of requests: localhost -> AMS -> box -> AMS -> localhost (Middle)
 */
public class testMiddle_Check_registration extends API_Middle {

    @Rule
    final public Timeout globalTimeout = Timeout.seconds(20);

    private API_Middle Middle = new API_Middle();

    @Test
    @Deprecated
    public void testCheck_registration_via_charterapi_a() throws IOException {
        ArrayList actual = Middle.Check_registration(mac, charterapi_a);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testCheck_registration_via_charterapi_b() throws IOException {
        ArrayList actual = Middle.Check_registration(mac, charterapi_b);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testCheck_registration_via_charterapi_c() throws IOException {
        ArrayList actual = Middle.Check_registration(mac, charterapi_c);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testCheck_registration_via_charterapi_d() throws IOException {
        ArrayList actual = Middle.Check_registration(mac, charterapi_d);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    @Ignore
    public void testCheck_registration_a_No_amsIp_found_for_mac() throws IOException {
        ArrayList actual = Middle.Check_registration("123456789012", charterapi_a);
        assertEquals(expected500, actual.get(0));
        assertEquals("No amsIp found for mac", actual.get(1));
    }

    @Test
    @Ignore
    public void testCheck_registration_b_No_amsIp_found_for_mac() throws IOException {
        ArrayList actual = Middle.Check_registration("123456789012", charterapi_b);
        assertEquals(expected500, actual.get(0));
        assertEquals("No amsIp found for mac", actual.get(1));
    }

    @Test
    @Ignore
    public void testCheck_registration_c_No_amsIp_found_for_mac() throws IOException {
        ArrayList actual = Middle.Check_registration("123456789012", charterapi_c);
        assertEquals(expected500, actual.get(0));
        assertEquals("No amsIp found for mac", actual.get(1));
    }

    @Test
    @Ignore
    public void testCheck_registration_d_No_amsIp_found_for_mac() throws IOException {
        ArrayList actual = Middle.Check_registration("123456789012", charterapi_d);
        assertEquals(expected500, actual.get(0));
        assertEquals("No amsIp found for mac", actual.get(1));
    }

}
