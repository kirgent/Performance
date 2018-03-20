import org.junit.jupiter.api.RepeatedTest;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class testReminder_Add_Delete extends API {

    @RepeatedTest(1)
    void testAdd_Delete48() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Add", false, 48,
                reminderProgramStart, reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        System.out.println("[DBG] return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));

        actual = api.Operation(ams_ip, macaddress[0], "Delete", false, 48,
                reminderProgramStart, reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @RepeatedTest(1)
    void testAdd_Modify_Delete48() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Add", false, 48,
                reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        System.out.println("[DBG] return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));

        actual = api.Operation(ams_ip, macaddress[0], "Modify", false, 48,
                reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        System.out.println("[DBG] return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));

        actual = api.Operation(ams_ip, macaddress[0], "Delete", false, 48,
                reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @RepeatedTest(1)
    void testAdd_Delete288() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Add", false, 288,
                reminderProgramStart, reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        System.out.println("[DBG] return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));

        actual = api.Operation(ams_ip, macaddress[0], "Delete", false, 288,
                reminderProgramStart, reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @RepeatedTest(1)
    void testAdd_Delete720() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Add", false, 720,
                reminderProgramStart, reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        System.out.println("[DBG] return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));

        actual = api.Operation(ams_ip, macaddress[0], "Delete", false, 720,
                reminderProgramStart, reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

}
