package test.perf.common;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * We are localhost (Charter Headend). Full chain of requests: localhost -> AMS -> STB -> AMS -> localhost
 */
class TestMiddle extends MiddleAPI {

    private MiddleAPI Middle = new MiddleAPI();

    @Test
    void testGetStbReminder() throws IOException {
        ArrayList actual = Middle.getStbReminder(charterapiA, mac);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("", actual.get(1));

        actual = Middle.getStbReminder(charterapiB, mac);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("", actual.get(1));

        actual = Middle.getStbReminder(charterapiC, mac);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("", actual.get(1));

        actual = Middle.getStbReminder(charterapiD, mac);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    void testGetAllReminder() throws IOException {
        ArrayList actual = Middle.getAllReminder(charterapiA, mac, 0);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("", actual.get(1));

        actual = Middle.getAllReminder(charterapiB, mac, 0);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("", actual.get(1));

        actual = Middle.getAllReminder(charterapiC, mac, 0);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("", actual.get(1));

        actual = Middle.getAllReminder(charterapiD, mac, 0);
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
    void testDeleteMultipleReminders() throws IOException {
        ArrayList actual;
        actual = Middle.deleteMultipleReminders(charterapiA, mac,1, 1);
        assertEquals(HttpStatus.SC_CREATED, actual.get(0));
        assertEquals("", actual.get(1));

        actual = Middle.deleteMultipleReminders(charterapiB, mac,1, 1);
        assertEquals(HttpStatus.SC_CREATED, actual.get(0));
        assertEquals("", actual.get(1));

        actual = Middle.deleteMultipleReminders(charterapiC, mac,1, 1);
        assertEquals(HttpStatus.SC_CREATED, actual.get(0));
        assertEquals("", actual.get(1));

        actual = Middle.deleteMultipleReminders(charterapiD, mac,1, 1);
        assertEquals(HttpStatus.SC_CREATED, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    void testDeleteMultipleRemindersNotFound() throws IOException {
        ArrayList actual = Middle.deleteMultipleReminders(charterapi, mac,0, 0);
        assertEquals(HttpStatus.SC_NOT_FOUND, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    void testScheduleReminder() throws IOException {
        ArrayList actual = Middle.scheduleReminder(charterapi, mac,0);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    void testScheduleReminderERRORSCHEDULINGREMINDER() throws IOException {
        ArrayList actual = Middle.scheduleReminder(charterapi, mac,0);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("ERROR_SCHEDULING_REMINDER", actual.get(1));
    }

}
