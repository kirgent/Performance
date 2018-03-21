import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class testAPI_Check_registration extends API {

    @Test
    public void testCheck_registration_via_charterapi_() throws IOException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Check_registration(macaddress[0], charterapi_);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testCheck_registration_via_charterapi_b() throws IOException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Check_registration(macaddress[0], charterapi_b);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testCheck_registration_via_charterapi_c() throws IOException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Check_registration(macaddress[0], charterapi_c);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testCheck_registration_via_charterapi_d() throws IOException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Check_registration(macaddress[0], charterapi_d);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }


}
