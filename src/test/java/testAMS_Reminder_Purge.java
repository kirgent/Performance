import org.junit.jupiter.api.RepeatedTest;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * We are as Middle: chain of requests: localhost -> AMS -> box -> AMS -> localhost (Middle)
 */
class testAMS_Reminder_Purge extends API {

    private API_AMS AMS = new API_AMS();

    @RepeatedTest(1)
    void testPurge() throws IOException {
        ArrayList actual = AMS.Request(mac, Operation.purge);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @RepeatedTest(1)
    void testPurge_mac_empty() throws IOException {
        ArrayList actual = AMS.Request("", Operation.purge);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: wrong deviceId", actual.get(1));
    }

    @RepeatedTest(1)
    void testPurge_mac_wrong() throws IOException {
        ArrayList actual = AMS.Request(mac_wrong, Operation.purge);
        assertEquals(expected200, actual.get(0));
        assertEquals("REM-ST-001 Box is not registered", actual.get(1));
    }

    @RepeatedTest(1)
    void testblablabla_jsonAdd__400_Bad_Request() throws IOException {
        ArrayList actual = AMS.Request(mac, Operation.blablabla, count, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("Incorrect request: blablabla", actual.get(1));
    }

    @RepeatedTest(1)
    void testblablabla_jsonDelete__400_Bad_Request() throws IOException {
        ArrayList actual = AMS.Request(mac, Operation.blablabla, count, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("Incorrect request: blablabla", actual.get(1));
    }

    @RepeatedTest(1)
    void testblablabla_jsonPurge__400_Bad_Request() throws IOException {
        ArrayList actual = AMS.Request(mac, Operation.blablabla);
        assertEquals(expected400, actual.get(0));
        assertEquals("Incorrect request: blablabla", actual.get(1));
    }

}
