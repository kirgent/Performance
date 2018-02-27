import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class testMiddle {

    private int expected = 200;
    private Middle request = new Middle();
    private String macaddress="6CB56BBA882C";
    private String ams_ip="172.30.81.4";
    private int count_reminders=48;
    private int reminderOffset=0;
    private int reminderOffset_new=10;

    @Test
    public void testChange_registration_to_valid_ams() throws IOException, InterruptedException {
        System.out.println("[DBG] testChange_registration_to_valid_ams:");
        int actual = request.Change_registration(macaddress, ams_ip);
        System.out.println("[DBG] return code: " + actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testChange_registration_to_invalid_ams127_0_0_1() throws IOException, InterruptedException {
        String ams_ip="127.0.0.1";
        System.out.println("[DBG] testChange_registration_to_invalid_ams127_0_0_1:");
        int actual = request.Change_registration(macaddress, ams_ip);
        System.out.println("[DBG] return code: " + actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testCheck_registration() throws IOException, InterruptedException {
        System.out.println("[DBG] testCheck_registration:");
        int actual = request.Check_registration(macaddress);
        System.out.println("[DBG] return code: " + actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testAdd() throws IOException, InterruptedException {
        System.out.println("[DBG] testAdd:");
        int actual = request.Add(macaddress, count_reminders, reminderOffset);
        System.out.println("[DBG] return code: " + actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testDelete() throws IOException {
        System.out.println("[DBG] testDelete:");
        int actual = request.Delete(macaddress, count_reminders, reminderOffset);
        System.out.println("[DBG] return code: " + actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testModify_operation() throws IOException {
        System.out.println("[DBG] testModify_operation:");
        int actual = request.Modify(macaddress, count_reminders, reminderOffset, reminderOffset_new);
        System.out.println("[DBG] return code: " + actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testModify_reminderOffset() throws IOException {
        System.out.println("[DBG] testModify_reminderOffset:");
        int actual = request.Modify(macaddress, count_reminders, reminderOffset, reminderOffset_new);
        System.out.println("[DBG] return code: " + actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testModify_reminderOffset_new__invalid() throws IOException {
        int reminderOffset_new = -1;
        System.out.println("[DBG] testModify_reminderOffset_new__invalid:");
        int actual = request.Modify(macaddress, count_reminders, reminderOffset, reminderOffset_new);
        System.out.println("[DBG] return code: " + actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testModify_reminderOffset_new__long() throws IOException {
        int reminderOffset_new = 32276;
        System.out.println("[DBG] testModify_reminderOffset_new__long:");
        int actual = request.Modify(macaddress, count_reminders, reminderOffset, reminderOffset_new);
        System.out.println("[DBG] return code: " + actual);
        assertEquals(expected, actual);
    }

    @Test
//    @After
    public void testPurge() throws IOException {
        System.out.println("[DBG] testPurge:");
        int actual = request.Purge(macaddress);
        System.out.println("[DBG] return code: " + actual);
        assertEquals(expected, actual);
    }
}
