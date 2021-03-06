package test.perf.common;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import test.perf.reminders.NewAPI;

import static org.junit.jupiter.api.Assertions.*;

class TestSandbox extends CommonAPI {

    private NewAPI AMS = new NewAPI();

    @Test
    void testSortBubble() throws IOException {
        //int[] arr = {42,1,40,3,7,88,67,10};
        int[] arr = {10,34,2,56,7,67,88,42};
        ArrayList list = new ArrayList();
        for (int i = 0; i < arr.length; i++) {
            list.add(i,arr[i]);
        }
        sortBubble(list);
        for (Object aList : list) {
            System.out.print(aList + " ");
        }
        System.out.println();
    }

    @Test
    void testSortInsertion() throws IOException {
        //int[] arr = {42,1,40,3,7,88,67,10};
        int[] arr = {10,34,2,56,7,67,88,42};
        ArrayList list = new ArrayList();
        for (int i = 0; i < arr.length; i++) {
            list.add(i,arr[i]);
        }
        sortInsertion(list);
        for (Object aList : list) {
            System.out.print(aList + " ");
        }
        System.out.println();
    }

    @Test
    void testSortQuick() throws IOException {
        //int[] arr = {42,1,40,3,7,88,67,10};
        int[] arr = {10,34,2,56,7,67,88,42};
        ArrayList list = new ArrayList();
        for (int i = 0; i < arr.length; i++) {
            list.add(i,arr[i]);
        }
        sortQuick(list);
        for (Object aList : list) {
            System.out.print(aList + " ");
        }
        System.out.println();
    }

    @Test
    void testSortSelection() throws IOException {
        //int[] arr = {42,1,40,3,7,88,67,10};
        int[] arr = {10,34,2,56,7,67,88,42};
        ArrayList list = new ArrayList();
        for (int i = 0; i < arr.length; i++) {
            list.add(i,arr[i]);
        }
        sortSelection(list);
        for (Object aList : list) {
            System.out.print(aList + " ");
        }
        System.out.println();
    }

    @Test
    void testSortMerge() throws IOException {
        //int[] arr = {42,1,40,3,7,88,67,10};
        int[] inputArr = {10,34,2,56,7,67,88,42};
        //ArrayList list = new ArrayList();
        //ArrayList tempMergArr = new ArrayList(list.size());
        //for (int i = 0; i < arr.length; i++) {
        //    list.ADD(i,arr[i]);
        //}
        sortMerge(inputArr);
        /*for (Object aList : list) {
            System.out.print(aList + " ");
        }*/
        for(int i:inputArr){
            System.out.print(i);
            System.out.print(" ");
        }
        System.out.println();
    }


    @Test
    void testDate() {
        assertEquals("2018-05-19", reminderProgramStart());
        assertEquals("2018-05-19", getDate(1));
        assertEquals("2018-05-19 2018-05-20", getDateSeveral(2));
        assertEquals("2018-05-20", getDate(2));
    }

    @Test
    void testGetDateTime() throws InterruptedException {
        assertEquals("2018-05-19 00:00", getDateTime(0));
    }

    @Test
    void testOperation_NewAPI_400_Bad_request() throws IOException {
        ArrayList actual = AMS.request(amsIp, mac, Operation.BLABLABLA, 2, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(HttpStatus.SC_BAD_REQUEST, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    void testOracleDB_Query() throws SQLException, ClassNotFoundException {
        ArrayList actual = AMS.queryDB(amsIp, mac);
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
        ArrayList result = AMS.queryDB(amsIp, "");
        assertTrue(result.isEmpty());
    }

    @Test
    void testOracleDB_Query_macaddress_wrong() throws SQLException, ClassNotFoundException {
        ArrayList result = AMS.queryDB(amsIp, mac_wrong);
        assertTrue(result.isEmpty());
    }

    @Test
    void testReadCsv() throws IOException {
        readCsv();
    }

}
