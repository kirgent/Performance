import org.junit.jupiter.api.RepeatedTest;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class testReminder_Add_Delete extends testAPI {

    @RepeatedTest(1)
    void testAdd_Delete48() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Add", false, 48,
                reminderProgramStart_by_default, reminderChannelNumber_by_default, reminderProgramId_by_default,
                reminderOffset_by_default, reminderScheduleId_by_default, reminderId_by_default);
        System.out.println("[DBG] return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));

        actual = api.Operation(ams_ip, macaddress[0], "Delete", false, 48,
                reminderProgramStart_by_default, reminderChannelNumber_by_default, reminderProgramId_by_default,
                reminderOffset_by_default, reminderScheduleId_by_default, reminderId_by_default);
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
                reminderProgramStart_by_default, reminderChannelNumber_by_default, reminderProgramId_by_default,
                reminderOffset_by_default, reminderScheduleId_by_default, reminderId_by_default);
        System.out.println("[DBG] return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));

        actual = api.Operation(ams_ip, macaddress[0], "Delete", false, 288,
                reminderProgramStart_by_default, reminderChannelNumber_by_default, reminderProgramId_by_default,
                reminderOffset_by_default, reminderScheduleId_by_default, reminderId_by_default);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

}
