package tv.zodiac.dev;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * We are as Middle: chain of requests: localhost -> AMS -> box -> AMS -> localhost (Middle)
 */
class testAMS_Reminder_Purge extends API {

    private API_AMS AMS = new API_AMS();

    @Test
    void testPurge() throws IOException {
        ArrayList actual = AMS.Request(mac, Operation.purge);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    void testPurge_macaddress_empty() throws IOException {
        ArrayList actual = AMS.Request("", Operation.purge);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: wrong deviceId", actual.get(1));
    }

    @Test
    void testPurge_macaddress_wrong() throws IOException {
        ArrayList actual = AMS.Request(mac_wrong, Operation.purge);
        assertEquals(expected200, actual.get(0));
        assertEquals("REM-ST-001 Box is not registered", actual.get(1));
    }

    @Test
    void testPurge_macaddress_wrong_unknown_MAC() throws IOException {
        ArrayList actual = AMS.Request(mac_wrong, Operation.purge);
        assertEquals(expected200, actual.get(0));
        assertEquals("unknown MAC", actual.get(1));
    }

    @Test
    void testblablabla_jsonAdd__400_Bad_Request() throws IOException {
        ArrayList actual = AMS.Request(mac, Operation.blablabla, count, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("Incorrect request: blablabla", actual.get(1));
    }

    @Test
    void testblablabla_jsonDelete__400_Bad_Request() throws IOException {
        //todo
        int count = 1;
        ArrayList actual = AMS.Request(mac, Operation.blablabla, count, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("Incorrect request: blablabla", actual.get(1));
    }

    @Test
    void testblablabla_jsonPurge__400_Bad_Request() throws IOException {
        ArrayList actual = AMS.Request(mac, Operation.blablabla);
        assertEquals(expected400, actual.get(0));
        assertEquals("Incorrect request: blablabla", actual.get(1));
    }

}
