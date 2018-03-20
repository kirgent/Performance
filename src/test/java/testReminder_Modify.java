import org.junit.jupiter.api.RepeatedTest;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class testReminder_Modify extends API {

    @RepeatedTest(1)
    void testModify_48() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Modify", true, 48,
                rack_date, rack_channel, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @RepeatedTest(1)
    void testModify_288() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Modify", true, 288,
                rack_date, rack_channel, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @RepeatedTest(1)
    void testModify_720() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Modify", true, 720,
                rack_date, rack_channel, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @RepeatedTest(1)
    void testModify_wrong_reminderChannelNumber() throws IOException, InterruptedException {
        //"reminderChannelNumber": <new value for the DCN the reminder is set to>,
        //"reminderProgramStart": "<new value for the date/time of program the reminder is set to>",
        //"reminderProgramId": "<new value for the TMS Program ID the reminder is set to>",
        //"reminderOffset": <new value for the number of minutes before the program start the reminder should be shown>
        //"reminderScheduleId": "<series or Individual program reminder schedule reference ID>",
        //"reminderId": "<episode or Individual program reminder reference ID of a particular schedule>",
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Modify", true, count_reminders,
                reminderProgramStart, -1, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("-1", actual.get(2));
    }

    @RepeatedTest(1)
    void testModify_wrong_reminderProgramStart() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Modify", true, count_reminders,
                "1980-01-01 00:00", reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("-1", actual.get(2));
    }

    @RepeatedTest(1)
    void testModify_wrong_reminderProgramId() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Modify", true, count_reminders,
                reminderProgramStart, reminderChannelNumber, "",
                reminderOffset, reminderScheduleId, reminderId);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("-1", actual.get(2));
    }

    @RepeatedTest(1)
    void testModify_wrong_reminderOffset() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Modify", true, count_reminders,
                reminderProgramStart, reminderChannelNumber, reminderProgramId,
                -1, reminderScheduleId, reminderId);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("-1", actual.get(2));
    }

    @RepeatedTest(1)
    void testModify_wrong_reminderScheduleId() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Modify", true, count_reminders,
                reminderProgramStart, reminderChannelNumber, reminderProgramId,
                reminderOffset, -1, reminderId);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("-1", actual.get(2));
    }

    @RepeatedTest(1)
    void testModify_wrong_reminderId() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Modify", true, count_reminders,
                reminderProgramStart, reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, -1);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("-1", actual.get(2));
    }

    @RepeatedTest(1)
    void testModify_negative_400_Bad_Request() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Operation(ams_ip, macaddress[0], "Modify", true, count_reminders,
                reminderProgramStart, reminderChannelNumber, reminderProgramId,
                reminderOffset, reminderScheduleId, reminderId);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual.get(0));
        assertEquals(expected400t, actual.get(1));
        assertEquals("", actual.get(2));
    }

}
