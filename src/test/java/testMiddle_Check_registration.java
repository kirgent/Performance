import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * We are as Middle: chain of requests: localhost -> AMS -> box -> AMS -> localhost (Middle)
 */
public class testMiddle_Check_registration extends API {

    private API_Middle Middle = new API_Middle();

    @Test
    public void testCheck_registration_via_charterapi_a() throws IOException {
        ArrayList actual = Middle.Check_registration(macaddress, charterapi_a);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testCheck_registration_via_charterapi_b() throws IOException {
        ArrayList actual = Middle.Check_registration(macaddress, charterapi_b);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testCheck_registration_via_charterapi_c() throws IOException {
        ArrayList actual = Middle.Check_registration(macaddress, charterapi_c);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testCheck_registration_via_charterapi_d() throws IOException {
        ArrayList actual = Middle.Check_registration(macaddress, charterapi_d);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    @Ignore
    public void testCheck_registration_a_No_amsIp_found_for_macAddress() throws IOException {
        ArrayList actual = Middle.Check_registration("123456789012", charterapi_a);
        assertEquals(expected500, actual.get(0));
        assertEquals("No amsIp found for macAddress", actual.get(1));
    }

    @Test
    @Ignore
    public void testCheck_registration_b_No_amsIp_found_for_macAddress() throws IOException {
        ArrayList actual = Middle.Check_registration("123456789012", charterapi_b);
        assertEquals(expected500, actual.get(0));
        assertEquals("No amsIp found for macAddress", actual.get(1));
    }

    @Test
    @Ignore
    public void testCheck_registration_c_No_amsIp_found_for_macAddress() throws IOException {
        ArrayList actual = Middle.Check_registration("123456789012", charterapi_c);
        assertEquals(expected500, actual.get(0));
        assertEquals("No amsIp found for macAddress", actual.get(1));
    }

    @Test
    @Ignore
    public void testCheck_registration_d_No_amsIp_found_for_macAddress() throws IOException {
        ArrayList actual = Middle.Check_registration("123456789012", charterapi_d);
        assertEquals(expected500, actual.get(0));
        assertEquals("No amsIp found for macAddress", actual.get(1));
    }

}
