import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class testMiddle_Delete extends testMiddle {

    public testMiddle_Delete() throws IOException {
    }

    @Test
    public void testDelete_48rems() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        int actual = old_api.Operation("Delete", macaddress, 48);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual);
    }

    @Test
    public void testDelete_288rems() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        int actual = old_api.Operation("Delete", macaddress, 288);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual);
    }

    @Test
    public void testDelete_720rems() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        int actual = old_api.Operation("Delete", macaddress, 720);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual);
    }

    @Test
    public void testDelete_with_empty_macaddress() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        int actual = old_api.Operation("Delete", macaddress, count_reminders_by_default);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected400, actual);
    }

    @Ignore
    @Test
    public void testDelete_without_macaddress() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        int actual = old_api.Operation("Delete","", count_reminders_by_default);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected400, actual);
    }

    @Test
    public void testAdd_48rems_via_operation() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        int actual = old_api.Operation("Delete" ,macaddress, 48);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual);
    }

}
