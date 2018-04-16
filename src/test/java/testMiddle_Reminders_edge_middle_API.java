import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * We are as Middle: chain of requests: localhost -> AMS -> box -> AMS -> localhost (Middle)
 */
public class testMiddle_Reminders_edge_middle_API extends API_Middle {

    @Rule
    final public Timeout globalTimeout = Timeout.seconds(600);

    private API_Middle Middle = new API_Middle();

    @Test
    public void testGetStbReminder() throws IOException {
        ArrayList actual = Middle.GetStbReminder(charterapi_a, mac);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = Middle.GetStbReminder(charterapi_b, mac);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = Middle.GetStbReminder(charterapi_c, mac);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = Middle.GetStbReminder(charterapi_d, mac);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testGetAllReminder() throws IOException {
        ArrayList actual = Middle.GetAllReminder(charterapi_a, mac, 0);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = Middle.GetAllReminder(charterapi_b, mac, 0);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = Middle.GetAllReminder(charterapi_c, mac, 0);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = Middle.GetAllReminder(charterapi_d, mac, 0);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    //todo
    //Delete multiple reminders by collection<reminderScheduleId and reminderId>
    //Request:
    //POST  /deleteMultipleReminders
    //Middle Request Body:
    //mac and List<ReminderIdentifier>
    //List<ReminderIdentifier> reminderIdentifiers
    //ReminderIdentifier:
    //reminderScheduleId	RequestObject	Long	Y	reminder Schedule Id
    //reminderId            RequestObject   Long    Y   reminderId
    //Sample Request Body:
    //{"mac":"STB12345",
    //[{"reminderScheduleId":1222,"reminderId":34843},
    //{"reminderScheduleId":1223,"reminderId":34841},
    //{"reminderScheduleId":1224,"reminderId":34842}]}

    //Sample Middle cURL (local):
    //curl -X POST http://<ipAddress>:<port>/remindersmiddle/v1/reminders/deleteMultipleReminders -d '{"mac":"STB12345",[{"reminderScheduleId":1222,"reminderId":34843},{"reminderScheduleId":1223,"reminderId":34841},{"reminderScheduleId":1224,"reminderId":34842}]}'

    //Response:
    //Successful Http Response code: 201 Created (with no LOCATION Header)
    //Failure response code: Not equal to 201 (for example: 500, 401)
    @Test
    public void testDelete_multiple_reminders() throws IOException {
        ArrayList actual = Middle.Delete_multiple_reminders(charterapi, mac,12345, 12345);
        assertEquals(expected201, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testDelete_multiple_reminders__Not_Found() throws IOException {
        ArrayList actual = Middle.Delete_multiple_reminders(charterapi, mac,0, 0);
        assertEquals(expected404, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testSchedule_reminder() throws IOException {
        ArrayList actual = Middle.Schedule_reminder(charterapi, mac,0);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    public void testSchedule_reminder_ERROR_SCHEDULING_REMINDER() throws IOException {
        ArrayList actual = Middle.Schedule_reminder(charterapi, mac,0);
        assertEquals(expected200, actual.get(0));
        assertEquals("ERROR_SCHEDULING_REMINDER", actual.get(1));
    }

}
