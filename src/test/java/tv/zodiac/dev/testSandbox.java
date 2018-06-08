package tv.zodiac.dev;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class testSandbox extends API_common {

    private NewAPI_AMS AMS = new NewAPI_AMS();

    @Test
    void test_get_median() throws IOException {
        int[] arr = {10,34,2,56,7,67,88,42};
        ArrayList list = new ArrayList();

        for (int i = 0; i < arr.length; i++) {
            list.add(i,arr[i]);
        }

        //sort_bubble(list);
        //sort_quick(list);
        sort_selection(list);
        //todo
        //sort_insertion(list);

        for (Object aList : list) {
            System.out.print(aList + " ");
        }
        System.out.println();

    }
    @Test
    void testDate() {
        assertEquals("2018-05-19", reminderProgramStart());
        assertEquals("2018-05-19", get_date(1));
        assertEquals("2018-05-19 2018-05-20", get_date_several(2));
        assertEquals("2018-05-20", get_date(2));
    }

    @Test
    void test_get_date_time() throws InterruptedException {
        assertEquals("2018-05-19 00:00", get_date_time(0));
    }

    @Test
    void testOperation_NewAPI_400_Bad_request() throws IOException {
        ArrayList actual = AMS.request(ams_ip, mac, Operation.blablabla, 2, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    void testOracleDB_Query() throws SQLException, ClassNotFoundException {
        ArrayList actual = AMS.QueryDB(ams_ip, mac);
        assertFalse(actual.isEmpty());

        assertEquals(Long.class, actual.get(0).getClass());
        assertNotEquals(0, actual.get(0));

        assertEquals(Long.class, actual.get(1).getClass());
        assertNotEquals(0, actual.get(1));

        assertEquals(Integer.class, actual.get(2).getClass());
        assertEquals(0, actual.get(2));

        assertEquals(String.class, actual.get(3).getClass());
        assertNotEquals(0, actual.get(3));

        assertEquals(String.class, actual.get(4).getClass());
        assertNotEquals(0, actual.get(4));
    }

    @Test
    void testOracleDB_Query_macaddress_empty() throws SQLException, ClassNotFoundException {
        ArrayList result = AMS.QueryDB(ams_ip, "");
        assertTrue(result.isEmpty());
    }

    @Test
    void testOracleDB_Query_macaddress_wrong() throws SQLException, ClassNotFoundException {
        ArrayList result = AMS.QueryDB(ams_ip, mac_wrong);
        assertTrue(result.isEmpty());
    }


}
