import org.junit.jupiter.api.RepeatedTest;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class testReminder_Add_Modify_Delete extends API {

    @RepeatedTest(1)
    void testAdd_Modify_Delete48() throws IOException {
        starttime();
        ArrayList actual = AMS.Request(ams_ip, macaddress, Operation.add, 48,
                reminderProgramStart(), reminderChannelNumber,
                reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        System.out.println("[DBG] return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));

        actual = AMS.Request(ams_ip, macaddress, Operation.modify, 48,
                reminderProgramStart(), reminderChannelNumber,
                reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        System.out.println("[DBG] return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));

        actual = AMS.Request(ams_ip, macaddress, Operation.delete, 48,
                reminderProgramStart(), reminderChannelNumber,
                reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

}
