import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;

//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class test_Add_Delete extends testAMS{

    //@RepeatedTest(1)
    @Test
    void test48Add() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress,"Add", count_reminders_by_default, count_iterations, rack_date, rack_channel);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    //@RepeatedTest(1)
    @Test
    void test48Delete() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress, "Delete", count_reminders_by_default, count_iterations, rack_date, rack_channel);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    //@RepeatedTest(1)
    //@Disabled
    @Test
    void test288Add() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress,"Add", 288, count_iterations, rack_date, rack_channel);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    //@RepeatedTest(1)
    //@Disabled
    @Test
    void test288Delete() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress, "Delete", 288, count_iterations, rack_date, rack_channel);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
        sleep(1000);
    }

}
