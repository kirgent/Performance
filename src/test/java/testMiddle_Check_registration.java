import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class testMiddle_Check_registration extends API {

    private API_Middle Middle = new API_Middle();

    @Test
    public void testCheck_registration_via_charterapi_a() throws IOException {
        starttime();
        ArrayList actual = Middle.Check_registration(macaddress, charterapi_a);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testCheck_registration_via_charterapi_b() throws IOException {
        starttime();
        ArrayList actual = Middle.Check_registration(macaddress, charterapi_b);
        finishtime();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testCheck_registration_via_charterapi_c() throws IOException {
        starttime();
        ArrayList actual = Middle.Check_registration(macaddress, charterapi_c);
        finishtime();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testCheck_registration_via_charterapi_d() throws IOException {
        starttime();
        ArrayList actual = Middle.Check_registration(macaddress, charterapi_d);
        finishtime();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    @Ignore
    public void testCheck_registration_a_No_amsIp_found_for_macAddress() throws IOException {
        starttime();
        ArrayList actual = Middle.Check_registration("123456789012", charterapi_a);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected500, actual.get(0));
        assertEquals(expected500t, actual.get(1));
        assertEquals("No amsIp found for macAddress", actual.get(2));
    }

    @Test
    @Ignore
    public void testCheck_registration_b_No_amsIp_found_for_macAddress() throws IOException {
        starttime();
        ArrayList actual = Middle.Check_registration("123456789012", charterapi_b);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected500, actual.get(0));
        assertEquals(expected500t, actual.get(1));
        assertEquals("No amsIp found for macAddress", actual.get(2));
    }

    @Test
    @Ignore
    public void testCheck_registration_c_No_amsIp_found_for_macAddress() throws IOException {
        starttime();
        ArrayList actual = Middle.Check_registration("123456789012", charterapi_c);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected500, actual.get(0));
        assertEquals(expected500t, actual.get(1));
        assertEquals("No amsIp found for macAddress", actual.get(2));
    }

    @Test
    @Ignore
    public void testCheck_registration_d_No_amsIp_found_for_macAddress() throws IOException {
        starttime();
        ArrayList actual = Middle.Check_registration("123456789012", charterapi_d);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected500, actual.get(0));
        assertEquals(expected500t, actual.get(1));
        assertEquals("No amsIp found for macAddress", actual.get(2));
    }

}
