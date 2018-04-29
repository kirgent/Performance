package tv.zodiac.dev;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

/**
 * We are Headend (on localhost): chain of requests: localhost -> AMS -> STB -> AMS -> localhost
 */
class testAMS_Reminder_Add_Modify_Delete_Purge__average extends API{

    private API_AMS AMS = new API_AMS();
    final private int countrepeat = 100;
    final private int count = 1;

    @Test
    @Disabled
    void test1_Add_Purge__average() throws IOException {
        for (int i = 1; i <= countrepeat; i++) {
            long reminderChannelNumber = reminderChannelNumber();
            long reminderOffset = reminderOffset();
            long reminderScheduleId = reminderScheduleId();
            long reminderId = reminderId();
            ArrayList actual;
            actual = AMS.Request(mac, Operation.add, count, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
            if(actual.get(1).equals("")) {
                AMS.Request(mac, Operation.purge);
            }
        }
    }

    @Test
    void test2_Add_Modify_Purge___average() throws IOException {
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
                AMS.Request(mac, Operation.purge);
            }
        }
    }

    @Test
    void test3_Add_Modify_Delete_Purge__average() throws IOException {
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
                AMS.Request(mac, Operation.purge);
            }
        }
    }

    @Test
    void test4_Add_Delete_Purge__average() throws IOException, InterruptedException {
        for (int i = 1; i <= countrepeat; i++) {
            long reminderScheduleId = reminderScheduleId();
            long reminderId = reminderId();
            ArrayList actual;
            actual = AMS.Request(mac, Operation.add, count, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
            if (actual.get(1).equals("")) {
                AMS.Request(mac, Operation.delete, count, reminderScheduleId, reminderId);
                AMS.Request(mac, Operation.purge);
            }
        }
    }

}
