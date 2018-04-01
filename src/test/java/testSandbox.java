import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class testSandbox extends API {

    private API_Middle Middle = new API_Middle();

    @Test
    @Disabled
    void testDate() {
        assertEquals("2018-03-31", reminderProgramStart());
        assertEquals("2018-03-31", get_date(1, false));
        assertEquals("2018-03-31 2018-04-01", get_date(2, true));
        assertEquals("2018-04-01", get_date(2, false));
    }

    @Test
    @Disabled
    void testTime() {
        assertEquals("00:30", get_time(1, 2));
    }

    @Test
    void testOperation_NewAPI_400_Bad_Request() throws IOException {
        starttime();
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.blablabla, count_reminders,
                reminderProgramStart(), reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    void testOracleDB_Query() throws SQLException, ClassNotFoundException {
        starttime();
        ArrayList result = AMS.QueryDB(ams_ip, macaddress);
        finishtime();

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
        starttime();
        ArrayList actual = Middle.Check_registration("123456789012", charterapi);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected500, actual.get(0));
        assertEquals(expected500t, actual.get(1));
        assertEquals("No amsIp found for macAddress", actual.get(2));
    }

    @Test
    void testCheck_registration_b_No_amsIp_found_for_macAddress() throws IOException {
        starttime();
        ArrayList actual = Middle.Check_registration("123456789012", charterapi_b);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected500, actual.get(0));
        assertEquals(expected500t, actual.get(1));
        assertEquals("No amsIp found for macAddress", actual.get(2));
    }

    @Test
    void testCheck_registration_c_No_amsIp_found_for_macAddress() throws IOException {
        starttime();
        ArrayList actual = Middle.Check_registration("123456789012", charterapi_c);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected500, actual.get(0));
        assertEquals(expected500t, actual.get(1));
        assertEquals("No amsIp found for macAddress", actual.get(2));
    }

    @Test
    void testCheck_registration_d_No_amsIp_found_for_macAddress() throws IOException {
        starttime();
        ArrayList actual = Middle.Check_registration("123456789012", charterapi_d);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected500, actual.get(0));
        assertEquals(expected500t, actual.get(1));
        assertEquals("No amsIp found for macAddress", actual.get(2));
    }

    @Test
    void testCheck_Delete() throws IOException {
        starttime();
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.delete, count_reminders,
                reminderProgramStart(), reminderChannelNumber,
                reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

}
