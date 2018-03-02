import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class testMiddle_Modify extends testMiddle{

    public testMiddle_Modify() throws IOException {
    }

    @Test
    public void testModify_reminderChannelNumber() throws IOException, InterruptedException {
        //"reminderChannelNumber": <new value for the DCN the reminder is set to>,
        //"reminderProgramStart": "<new value for the date/time of program the reminder is set to>",
        //"reminderProgramId": "<new value for the TMS Program ID the reminder is set to>",
        //"reminderOffset": <new value for the number of minutes before the program start the reminder should be shown>
        //"reminderScheduleId": "<series or Individual program reminder schedule reference ID>",
        //"reminderId": "<episode or Individual program reminder reference ID of a particular schedule>",
        long start = System.currentTimeMillis();
        int actual = new_api.Operation("Modify", macaddress, 48, reminderChannelNumber_by_default, reminderProgramStart_by_default, reminderProgramId_by_default, reminderOffset_by_default, reminderScheduleId_by_default, reminderId_by_default);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual);
    }

    @Test
    public void testModify_reminderProgramId() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        int actual = new_api.Operation("Modify", macaddress, 48, reminderChannelNumber_by_default, reminderProgramStart_by_default, reminderProgramId_by_default, reminderOffset_by_default, reminderScheduleId_by_default, reminderId_by_default);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual);
    }

    @Test
    public void testModify_reminderProgramStart() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        int actual = new_api.Operation("Modify", macaddress, 48, reminderChannelNumber_by_default, reminderProgramStart_by_default, reminderProgramId_by_default, reminderOffset_by_default, reminderScheduleId_by_default, reminderId_by_default);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual);
    }

}
