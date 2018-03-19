import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeout;

class testAPI_Change_registration extends testAPI {

    //@Test(timeout = 20000)
    @Test
    void testChange_registration_via_charterapi_() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Change_registration(macaddress[0], charterapi_, ams_ip);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
    }

    //@Test(timeout = 20000)
    @Test
    void testChange_registration_via_charterapi_b() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Change_registration(macaddress[0], charterapi_b, ams_ip);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("SUCCESS", actual.get(2));
    }

    //@Test(timeout = 20000)
    @Test
    void testChange_registration_via_charterapi_c() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Change_registration(macaddress[0], charterapi_c, ams_ip);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("SUCCESS", actual.get(2));
    }

    //@Test(timeout = 20000)
    @Test
    void testChange_registration_via_charterapi_d() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Change_registration(macaddress[0], charterapi_d, ams_ip);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("SUCCESS", actual.get(2));
    }

    @Test

    void testChange_registration_to_invalid_ams127_0_0_1() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();

        ArrayList actual = new ArrayList();
        assertTimeout(ofMillis(20000), () -> {
            api.Change_registration(macaddress[0], charterapi_by_default, "127.0.0.1");
        });
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        //assertEquals(expected200, actual.get(0));
        //assertEquals(expected200t, actual.get(1));
        //assertEquals("SUCCESS", actual.get(2));
    }

}
