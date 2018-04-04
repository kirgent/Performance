import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * We are as Middle: chain of requests: localhost -> AMS -> box -> AMS -> localhost (Middle)
 */
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
    public void testGetStbReminder() throws IOException {
        ArrayList actual = Middle.GetStbReminder(charterapi, macaddress);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testGetAllReminder_a() throws IOException {
        ArrayList actual = Middle.GetAllReminder(charterapi_a, macaddress, 0);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testGetAllReminder_b() throws IOException {
        ArrayList actual = Middle.GetAllReminder(charterapi_b, macaddress, 0);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testGetAllReminder_c() throws IOException {
        ArrayList actual = Middle.GetAllReminder(charterapi_c, macaddress, 0);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testGetAllReminder_d() throws IOException {
        ArrayList actual = Middle.GetAllReminder(charterapi_d, macaddress, 0);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testDelete_multiple_reminders() throws IOException {
        ArrayList actual = Middle.Delete_multiple_reminders(charterapi, macaddress,12345, 12345);
        assertEquals(expected201, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testDelete_multiple_reminders__Not_Found() throws IOException {
        ArrayList actual = Middle.Delete_multiple_reminders(charterapi, macaddress,0, 0);
        assertEquals(expected404, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testSchedule_reminder() throws IOException {
        ArrayList actual = Middle.Schedule_reminder(charterapi, macaddress,0);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testSchedule_reminder_ERROR_SCHEDULING_REMINDER() throws IOException {
        ArrayList actual = Middle.Schedule_reminder(charterapi, macaddress,0);
        assertEquals(expected200, actual.get(0));
        assertEquals("ERROR_SCHEDULING_REMINDER", actual.get(1));
    }

}
