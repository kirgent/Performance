import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class testAPI_Check_registration extends API {

    @Test
    public void testCheck_registration_via_charterapi_() throws IOException {
        starttime();
        ArrayList actual = api.Check_registration(macaddress, charterapi_);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testCheck_registration_via_charterapi_b() throws IOException {
        starttime();
        ArrayList actual = api.Check_registration(macaddress, charterapi_b);
        finishtime();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testCheck_registration_via_charterapi_c() throws IOException {
        starttime();
        ArrayList actual = api.Check_registration(macaddress, charterapi_c);
        finishtime();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testCheck_registration_via_charterapi_d() throws IOException {
        starttime();
        ArrayList actual = api.Check_registration(macaddress, charterapi_d);
        finishtime();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }


}
