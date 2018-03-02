import junit.extensions.RepeatedTest;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class testMiddle_Add extends testMiddle {

    public testMiddle_Add() throws IOException {
    }

    @Test
    public void testAdd_48rems() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        int actual = old_api.Operation("Add", macaddress, 1);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual);
    }

    @Test
    public void testAdd_288rems() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        int actual = old_api.Operation("Add", macaddress, 288);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual);
    }

    @Test
    public void testAdd_720rems() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        int actual = old_api.Operation("Add", macaddress, 720);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual);
    }

    @Ignore
    @Test
    public void testAdd_without_macaddress() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        int actual = old_api.Operation("Add","", count_reminders_by_default);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual);
    }

    @Test
    public void testAdd_with_empty_macaddress() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        int actual = old_api.Operation("Add","", count_reminders_by_default);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual);
    }

    @Test
    public void testAdd_48rems_via_operation() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        int actual = old_api.Operation("Add" ,macaddress, 48);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual);
    }

}
