import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class testMiddle_Change_registration extends testMiddle {

    public testMiddle_Change_registration() throws IOException {
    }

    @Test(timeout = 20000)
    public void testChange_registration_via_charterapi_() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        int actual = old_api.Change_registration(macaddress, charterapi_, ams_ip_by_default);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual);
    }

    @Test(timeout = 20000)
    public void testChange_registration_via_charterapi_b() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        int actual = old_api.Change_registration(macaddress, charterapi_b, ams_ip_by_default);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual);
    }

    @Test(timeout = 20000)
    public void testChange_registration_via_charterapi_c() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        int actual = old_api.Change_registration(macaddress, charterapi_c, ams_ip_by_default);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual);
    }

    @Test(timeout = 20000)
    public void testChange_registration_via_charterapi_d() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        int actual = old_api.Change_registration(macaddress, charterapi_d, ams_ip_by_default);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual);
    }

    @Ignore
    @Test(timeout = 20000)
    public void testChange_registration_to_invalid_ams127_0_0_1() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        int actual = old_api.Change_registration(macaddress, charterapi_by_default, "127.0.0.1");
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual);
    }

}
