package tv.zodiac.dev;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * We are localhost (Charter Headend). Full chain of requests: localhost -> AMS -> STB -> AMS -> localhost
 */
class testAMS_newAPI_Reminder_Purge extends API_common {

    private API_new AMS = new API_new();

    @Test
    void testPurge() throws IOException {
        ArrayList actual = AMS.request(ams_ip, mac, Operation.purge);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    void testPurge_macaddress_empty() throws IOException {
        ArrayList actual = AMS.request(ams_ip, "", Operation.purge);
        assertEquals(HttpStatus.SC_BAD_REQUEST, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: wrong deviceId", actual.get(1));
    }

    @Test
    @Deprecated
    void testPurge_macaddress_wrong__Box_is_not_registered() throws IOException {
        ArrayList actual = AMS.request(ams_ip, mac_wrong, Operation.purge);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("REM-ST-001 Box is not registered", actual.get(1));
    }

    @Test
    void testPurge_macaddress_wrong__unknown_MAC() throws IOException {
        ArrayList actual = AMS.request(ams_ip, mac_wrong, Operation.purge);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("unknown MAC", actual.get(1));
    }

    @Test
    void testblablabla_jsonAdd__400_Bad_request() throws IOException {
        ArrayList actual = AMS.request(ams_ip, mac, Operation.blablabla, 2, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(HttpStatus.SC_BAD_REQUEST, actual.get(0));
        assertEquals("Incorrect request: blablabla", actual.get(1));
    }

    @Test
    void testblablabla_jsonDelete__400_Bad_request() throws IOException {
        //todo
        int count_reminders = 1;
        ArrayList actual = AMS.request(ams_ip, mac, Operation.blablabla, count_reminders, reminderScheduleId, reminderId);
        assertEquals(HttpStatus.SC_BAD_REQUEST, actual.get(0));
        assertEquals("Incorrect request: blablabla", actual.get(1));
    }

    @Test
    void testblablabla_jsonPurge__400_Bad_request() throws IOException {
        ArrayList actual = AMS.request(ams_ip, mac, Operation.blablabla);
        assertEquals(HttpStatus.SC_BAD_REQUEST, actual.get(0));
        assertEquals("Incorrect request: blablabla", actual.get(1));
    }

}
