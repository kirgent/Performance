import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class testMiddle_Check_registration extends testMiddle {

    @Test
    public void testCheck_registration_via_charterapi_() throws IOException, InterruptedException {
        System.out.println("[DBG] testCheck_registration_via_charterapi_:");
        //log.info("testCheck_registration_via_charterapi_:");
        long start = System.currentTimeMillis();
        int actual = request.Check_registration(macaddress,charterapi_);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        //log.info((finish-start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual);
    }

    @Test
    public void testCheck_registration_via_charterapi_b() throws IOException, InterruptedException {
        System.out.println("[DBG] testCheck_registration_via_charterapi_b:");
        long start = System.currentTimeMillis();
        int actual = request.Check_registration(macaddress,charterapi_b);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual);
    }

    @Test
    public void testCheck_registration_via_charterapi_c() throws IOException, InterruptedException {
        System.out.println("[DBG] testCheck_registration_via_charterapi_c:");
        long start = System.currentTimeMillis();
        int actual = request.Check_registration(macaddress,charterapi_c);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual);
    }

    @Test
    public void testCheck_registration_via_charterapi_d() throws IOException, InterruptedException {
        System.out.println("[DBG] testCheck_registration_via_charterapi_d:");
        long start = System.currentTimeMillis();
        int actual = request.Check_registration(macaddress,charterapi_d);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual);
    }

}
