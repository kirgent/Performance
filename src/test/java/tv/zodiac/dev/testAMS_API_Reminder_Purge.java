package tv.zodiac.dev;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * We are Headend (on localhost): chain of requests: Headend(localhost) -> AMS -> STB -> AMS -> localhost
 */
class testAMS_API_Reminder_Purge extends API {

    private NEWAPI_AMS AMS = new NEWAPI_AMS();

    @Test
    void testPurge() throws IOException {
        ArrayList actual = AMS.request(mac, Operation.purge);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    void testPurge_macaddress_empty() throws IOException {
        ArrayList actual = AMS.request("", Operation.purge);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: wrong deviceId", actual.get(1));
    }

    @Test
    void testPurge_macaddress_wrong() throws IOException {
        ArrayList actual = AMS.request(mac_wrong, Operation.purge);
        assertEquals(expected200, actual.get(0));
        assertEquals("REM-ST-001 Box is not registered", actual.get(1));
    }

    @Test
    void testPurge_macaddress_wrong_unknown_MAC() throws IOException {
        ArrayList actual = AMS.request(mac_wrong, Operation.purge);
        assertEquals(expected200, actual.get(0));
        assertEquals("unknown MAC", actual.get(1));
    }

    @Test
    void testblablabla_jsonAdd__400_Bad_request() throws IOException {
        ArrayList actual = AMS.request(mac, Operation.blablabla, count, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("Incorrect request: blablabla", actual.get(1));
    }

    @Test
    void testblablabla_jsonDelete__400_Bad_request() throws IOException {
        //todo
        int count = 1;
        ArrayList actual = AMS.request(mac, Operation.blablabla, count, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("Incorrect request: blablabla", actual.get(1));
    }

    @Test
    void testblablabla_jsonPurge__400_Bad_request() throws IOException {
        ArrayList actual = AMS.request(mac, Operation.blablabla);
        assertEquals(expected400, actual.get(0));
        assertEquals("Incorrect request: blablabla", actual.get(1));
    }

}
