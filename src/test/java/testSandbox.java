import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class testSandbox extends API {

    @Test
    @Disabled
    void testDate() {
        //String[] xxx = {};
        assertEquals ("2018-03-16", get_date());

        assertEquals("2018-03-16", get_date(1, false));
        assertEquals("2018-03-16 2018-03-17", get_date(2, true));
        assertEquals("2018-03-17", get_date(2, false));
        assertEquals("2018-03-16 2018-03-17", get_date(2, true));
    }

    @Test
    @Disabled
    void testTime() {
        assertEquals("02:30", get_time(150, false));
    }





    @Test
    void testOperation_NewAPI_400_Bad_Request() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress, Operation.blablabla, true, count_reminders,
                get_date(), reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    void testOracleDB_Query() throws SQLException, ClassNotFoundException {
        long start = System.currentTimeMillis();
        ArrayList result = api.QueryDB(ams_ip, macaddress);
        long finish = System.currentTimeMillis();

        assertFalse(result.isEmpty());
            System.out.println("[DBG] " + (finish - start) + "ms test, return result: "
                    + result.get(0) + "  "
                    + result.get(1) + "  "
                    + result.get(2) + "  "
                    + result.get(3) + "  "
                    + result.get(4));

            assertNotEquals(0, result.get(0));
            assertNotEquals(0, result.get(1));
            assertNotEquals(0, result.get(2));
            assertNotEquals(0, result.get(3));
            assertNotEquals(0, result.get(4));
    }

    @Test
    @Disabled
    void testCheck_registration_No_amsIp_found_for_macAddress() throws IOException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Check_registration("123456789012", charterapi_);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected500, actual.get(0));
        assertEquals(expected500t, actual.get(1));
        assertEquals("No amsIp found for macAddress", actual.get(2));
    }

    @Test
    void testCheck_registration_b_No_amsIp_found_for_macAddress() throws IOException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Check_registration("123456789012", charterapi_b);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected500, actual.get(0));
        assertEquals(expected500t, actual.get(1));
        assertEquals("No amsIp found for macAddress", actual.get(2));
    }

    @Test
    void testCheck_registration_c_No_amsIp_found_for_macAddress() throws IOException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Check_registration("123456789012", charterapi_c);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected500, actual.get(0));
        assertEquals(expected500t, actual.get(1));
        assertEquals("No amsIp found for macAddress", actual.get(2));
    }

    @Test
    void testCheck_registration_d_No_amsIp_found_for_macAddress() throws IOException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Check_registration("123456789012", charterapi_d);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected500, actual.get(0));
        assertEquals(expected500t, actual.get(1));
        assertEquals("No amsIp found for macAddress", actual.get(2));
    }

}
