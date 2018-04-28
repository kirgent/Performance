package tv.zodiac.dev;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

class testAMS_Reminder_Add_Modify_Delete_Purge__average extends API{

    private API_AMS AMS = new API_AMS();
    final private int countrepeat = 100;
    final private int count = 100;

    @Test
    void test2_Add_Modify___average() throws IOException {
        for (int i = 1; i <= countrepeat; i++) {
            long reminderChannelNumber = reminderChannelNumber();
            long reminderOffset = reminderOffset();
            long reminderOffset_new = reminderOffset();
            long reminderScheduleId = reminderScheduleId();
            long reminderId = reminderId();
            ArrayList actual;
            actual = AMS.Request(mac, API.Operation.add, count, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
            if(actual.get(1).equals("")) {
                AMS.Request(mac, API.Operation.modify, count, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset_new, reminderScheduleId, reminderId);
            }
        }
        //System.out.println("[DBG] average add time: " + get_average_time(list_add_time));
        //System.out.println("[DBG] average modify time: " + get_average_time(list_modify_time));
    }

    @Test
    void test3_Add_Modify_Delete__average() throws IOException {
        for (int i = 1; i <= countrepeat; i++) {
            long reminderChannelNumber = reminderChannelNumber();
            long reminderOffset = reminderOffset();
            long reminderOffset_new = reminderOffset();
            long reminderScheduleId = reminderScheduleId();
            long reminderId = reminderId();
            ArrayList actual;
            actual = AMS.Request(mac, Operation.add, count, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
            if (actual.get(1).equals("")) {
                AMS.Request(mac, Operation.modify, count, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset_new, reminderScheduleId, reminderId);
                AMS.Request(mac, Operation.delete, count, reminderScheduleId, reminderId);
            }
        }
    }

    @Test
    void test4_Add_Delete__average() throws IOException, InterruptedException {
        for (int i = 1; i <= countrepeat; i++) {
            long reminderScheduleId = reminderScheduleId();
            long reminderId = reminderId();
            ArrayList actual;
            actual = AMS.Request(mac, Operation.add, count, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
            if (actual.get(1).equals("")) {
                AMS.Request(mac, Operation.delete, count, reminderScheduleId, reminderId);
            }
        }
    }

    @AfterEach
    void testPurge() throws IOException {
        ArrayList actual = AMS.Request(mac, Operation.purge);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

}
