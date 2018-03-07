import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

class testAMS_Check_registration extends testAMS {

    //@Test(timeout = 20000)
    @Test
    void testCheck_registration_via_charterapi_() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        int actual = api.Check_registration(macaddress,charterapi_);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual);
    }

    //@Test(timeout = 20000)
    @Test
    void testCheck_registration_via_charterapi_b() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        int actual = api.Check_registration(macaddress,charterapi_b);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual);
    }

    //@Test(timeout = 20000)
    @Test
    void testCheck_registration_via_charterapi_c() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        int actual = api.Check_registration(macaddress,charterapi_c);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual);
    }

    //@Test(timeout = 20000)
    @Test
    void testCheck_registration_via_charterapi_d() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        int actual = api.Check_registration(macaddress,charterapi_d);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual);
    }

}
