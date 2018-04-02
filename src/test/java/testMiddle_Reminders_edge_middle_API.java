import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class testMiddle_Reminders_edge_middle_API extends API_AMS{

    private API_Middle Middle = new API_Middle();

    //todo
    //Delete multiple reminders by collection<reminderScheduleId and reminderId>

    //Request:
    //POST  /deleteMultipleReminders

    //Middle Request Body:
    //macAddress and List<ReminderIdentifier>

    //List<ReminderIdentifier> reminderIdentifiers

    //ReminderIdentifier:
    //reminderScheduleId	RequestObject	Long	Y	reminder Schedule Id
    //reminderId            RequestObject   Long    Y   reminderId

    //Sample Request Body:
    //{"macAddress":"STB12345",
    //[{"reminderScheduleId":1222,"reminderId":34843},
    //{"reminderScheduleId":1223,"reminderId":34841},
    //{"reminderScheduleId":1224,"reminderId":34842}]}

    //Sample Middle cURL (local):
    //curl -X POST http://<ipAddress>:<port>/remindersmiddle/v1/reminders/deleteMultipleReminders -d '{"macAddress":"STB12345",[{"reminderScheduleId":1222,"reminderId":34843},{"reminderScheduleId":1223,"reminderId":34841},{"reminderScheduleId":1224,"reminderId":34842}]}'

    //Response:
    //Successful Http Response code: 201 Created (with no LOCATION Header)
    //Failure response code: Not equal to 201 (for example: 500, 401)

    @Test
    public void testGetAllReminder() throws IOException {
        starttime();
        ArrayList actual = Middle.GetAllReminder(macaddress, charterapi_b, 12345);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testDelete_multiple_reminders () throws IOException {
        starttime();
        ArrayList actual = Middle.Delete_multiple_reminders(macaddress, charterapi_c,1, 2);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    public void testSchedule_a_reminder() throws IOException {
        starttime();
        ArrayList actual = Middle.Schedule_a_reminder(macaddress, charterapi_b,12345);
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

}
