package tv.zodiac.dev;


import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * We are Headend (on localhost): chain of requests: Headend(localhost) -> AMS -> STB -> AMS -> localhost
 */
class testMiddle_Reminders_edge_middle_API extends API_Middle {

    private API_Middle Middle = new API_Middle();

    @Test
    void testGetStbReminder() throws IOException {
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
    void testGetAllReminder() throws IOException {
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
    void testDelete_multiple_reminders() throws IOException {
        ArrayList actual;
        actual = Middle.Delete_multiple_reminders(charterapi_a, mac,1, 1);
        assertEquals(expected201, actual.get(0));
        assertEquals("", actual.get(1));

        actual = Middle.Delete_multiple_reminders(charterapi_b, mac,1, 1);
        assertEquals(expected201, actual.get(0));
        assertEquals("", actual.get(1));

        actual = Middle.Delete_multiple_reminders(charterapi_c, mac,1, 1);
        assertEquals(expected201, actual.get(0));
        assertEquals("", actual.get(1));

        actual = Middle.Delete_multiple_reminders(charterapi_d, mac,1, 1);
        assertEquals(expected201, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    void testDelete_multiple_reminders__Not_Found() throws IOException {
        ArrayList actual = Middle.Delete_multiple_reminders(charterapi, mac,0, 0);
        assertEquals(expected404, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    void testSchedule_reminder() throws IOException {
        ArrayList actual = Middle.Schedule_reminder(charterapi, mac,0);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    void testSchedule_reminder_ERROR_SCHEDULING_REMINDER() throws IOException {
        ArrayList actual = Middle.Schedule_reminder(charterapi, mac,0);
        assertEquals(expected200, actual.get(0));
        assertEquals("ERROR_SCHEDULING_REMINDER", actual.get(1));
    }

}
