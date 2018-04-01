import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class testCheck_registration extends API {

    private API_Middle Middle = new API_Middle();

    @Test
    public void testCheck_registration_via_charterapi_() throws IOException {
        starttime();
        ArrayList actual = Middle.Check_registration(macaddress, charterapi);
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


}
