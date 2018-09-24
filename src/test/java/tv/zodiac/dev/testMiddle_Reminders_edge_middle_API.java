package tv.zodiac.dev;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * We are localhost (Charter Headend). Full chain of requests: localhost -> AMS -> STB -> AMS -> localhost
 */
class testMiddle_Reminders_edge_middle_API extends API_middle {

    private API_middle Middle = new API_middle();

    @Test
    void testGetStbReminder() throws IOException {
        ArrayList actual = Middle.getStbReminder(charterapi_a, mac);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("", actual.get(1));

        actual = Middle.getStbReminder(charterapi_b, mac);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("", actual.get(1));

        actual = Middle.getStbReminder(charterapi_c, mac);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("", actual.get(1));

        actual = Middle.getStbReminder(charterapi_d, mac);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    void testGetAllReminder() throws IOException {
        ArrayList actual = Middle.getAllReminder(charterapi_a, mac, 0);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("", actual.get(1));

        actual = Middle.getAllReminder(charterapi_b, mac, 0);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("", actual.get(1));

        actual = Middle.getAllReminder(charterapi_c, mac, 0);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("", actual.get(1));

        actual = Middle.getAllReminder(charterapi_d, mac, 0);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
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
        actual = Middle.deleteMultipleReminders(charterapi_a, mac,1, 1);
        assertEquals(HttpStatus.SC_CREATED, actual.get(0));
        assertEquals("", actual.get(1));

        actual = Middle.deleteMultipleReminders(charterapi_b, mac,1, 1);
        assertEquals(HttpStatus.SC_CREATED, actual.get(0));
        assertEquals("", actual.get(1));

        actual = Middle.deleteMultipleReminders(charterapi_c, mac,1, 1);
        assertEquals(HttpStatus.SC_CREATED, actual.get(0));
        assertEquals("", actual.get(1));

        actual = Middle.deleteMultipleReminders(charterapi_d, mac,1, 1);
        assertEquals(HttpStatus.SC_CREATED, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    void testDelete_multiple_reminders__Not_Found() throws IOException {
        ArrayList actual = Middle.deleteMultipleReminders(charterapi, mac,0, 0);
        assertEquals(HttpStatus.SC_NOT_FOUND, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    void testSchedule_reminder() throws IOException {
        ArrayList actual = Middle.scheduleReminder(charterapi, mac,0);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    void testSchedule_reminder_ERROR_SCHEDULING_REMINDER() throws IOException {
        ArrayList actual = Middle.scheduleReminder(charterapi, mac,0);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("ERROR_SCHEDULING_REMINDER", actual.get(1));
    }

}
