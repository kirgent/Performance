package test.perf.reminders;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * We are localhost (Charter Headend). Full chain of requests: localhost -> AMS -> STB -> AMS -> localhost
 */
class TestReminderModify extends NewAPI {

    private NewAPI AMS = new NewAPI();
    final private int count_iterations = 10;
    final private int count_reminders = 1;

    @RepeatedTest(1)
    void testModify() throws IOException {
        ArrayList actual;
        actual = AMS.request(amsIp, mac, Operation.ADD, count_reminders,
                reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.request(amsIp, mac, Operation.MODIFY, count_reminders,
                reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("", actual.get(1));
        testPurge();
    }

    @Test
    void testModify__statusCode4() throws IOException {
        //todo!!!
        ArrayList actual = AMS.request(amsIp, mac, Operation.MODIFY, 1,
                reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("4", actual.get(1));
    }

    @Test
    void testModify_macaddress_empty() throws IOException {
        //todo!!!
        ArrayList actual = AMS.request(amsIp, "", Operation.MODIFY, 1,
                reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(HttpStatus.SC_BAD_REQUEST, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: wrong deviceId", actual.get(1));
    }

    @Test
    @Deprecated
    void testModify_macaddress_wrong__Box_is_not_registered() throws IOException {
        //todo!!!
        ArrayList actual = AMS.request(amsIp, mac_wrong, Operation.MODIFY, 1,
                reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("REM-ST-001 Box is not registered", actual.get(1));
    }

    @Test
    void testModify_macaddress_wrong__unknown_MAC() throws IOException {
        //todo!!!
        ArrayList actual = AMS.request(amsIp, mac_wrong, Operation.MODIFY, 1,
                reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("unknown MAC", actual.get(1));
    }

    @Test
    void testModify_count_is_0() throws IOException {
        ArrayList actual = AMS.request(amsIp, mac, Operation.MODIFY, 0,
                reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(HttpStatus.SC_BAD_REQUEST, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: wrong number of reminders", actual.get(1));
    }

    @Test
    void testModify_reminderChannelNumber_0__statusCode3() throws IOException {
        //todo!!!
        ArrayList actual = AMS.request(amsIp, mac, Operation.MODIFY, 1,
                reminderProgramStart, 0, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("3", actual.get(1));
    }

    @Test
    void testModify_reminderChannelNumber_empty() throws IOException {
        //todo!!!
        ArrayList actual = AMS.request(amsIp, mac, Operation.MODIFY, 1,
                reminderProgramStart, -1, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(HttpStatus.SC_BAD_REQUEST, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: incorrect message format", actual.get(1));
    }

    @Test
    void testModify_reminderChannelNumber_LONG_MAX_VALUE() throws IOException {
        //todo!!!
        ArrayList actual = AMS.request(amsIp, mac, Operation.MODIFY, 1,
                reminderProgramStart, Long.MAX_VALUE, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(HttpStatus.SC_BAD_REQUEST, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: incorrect message format", actual.get(1));
    }

    @Test
    void testModify_reminderChannelNumber_MAX_VALUE__statusCode4() throws IOException {
        //todo
        ArrayList actual = AMS.request(amsIp, mac, Operation.MODIFY, 1,
                reminderProgramStart, Integer.MAX_VALUE, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("4", actual.get(1));
    }

    @Test
    void testModify_reminderChannelNumber_MIN_VALUE() throws IOException {
        //todo!!!
        ArrayList actual = AMS.request(amsIp, mac, Operation.MODIFY, 1,
                reminderProgramStart, Integer.MIN_VALUE, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(HttpStatus.SC_BAD_REQUEST, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: invalid channel number", actual.get(1));
    }

    @Test
    void testModify_reminderId_0__statusCode4() throws IOException {
        //todo
        ArrayList actual = AMS.request(amsIp, mac, Operation.MODIFY, 1,
                reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, 0);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("4", actual.get(1));
    }

    @Test
    void testModify_reminderId_empty() throws IOException {
        //todo!!!
        ArrayList actual = AMS.request(amsIp, mac, Operation.MODIFY, 1,
                reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, -1);
        assertEquals(HttpStatus.SC_BAD_REQUEST, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: incorrect message format", actual.get(1));
    }

    @RepeatedTest(count_iterations)
    @Deprecated
        //todo WHY it was deprecated?
    @Disabled
    void testModify_reminderId_MAX_VALUE() throws IOException {
        ArrayList actual;
        actual = AMS.request(amsIp, mac, Operation.ADD, count_reminders,
                reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, Long.MAX_VALUE);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.request(amsIp, mac, Operation.MODIFY, count_reminders,
                reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset(15), reminderScheduleId, Long.MAX_VALUE);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("", actual.get(1));
        testPurge();
    }

    @Test
    void testModify_reminderId_MIN_VALUE__incorrect_reminderId() throws IOException {
        //todo
        ArrayList actual = AMS.request(amsIp, mac, Operation.MODIFY, 1,
                reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, Long.MIN_VALUE);
        assertEquals(HttpStatus.SC_BAD_REQUEST, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: incorrect reminderId", actual.get(1));
    }

    @Test
    void testModify_reminderOffset_empty() throws IOException {
        //todo!!!
        ArrayList actual = AMS.request(amsIp, mac, Operation.MODIFY, 1,
                reminderProgramStart, reminderChannelNumber, reminderProgramId, -1, reminderScheduleId, reminderId);
        assertEquals(HttpStatus.SC_BAD_REQUEST, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: incorrect message format", actual.get(1));
    }

    @Test
    void testModify_reminderOffset_LONG_MAX_VALUE() throws IOException {
        //todo
        ArrayList actual = AMS.request(amsIp, mac, Operation.MODIFY, 1,
                reminderProgramStart, reminderChannelNumber, reminderProgramId, Long.MAX_VALUE, reminderScheduleId, reminderId);
        assertEquals(HttpStatus.SC_BAD_REQUEST, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: incorrect message format", actual.get(1));
    }

    @RepeatedTest(count_iterations)
    void testModify_reminderOffset_MAX_VALUE() throws IOException {
        //todo!!!
        ArrayList actual;
        actual = AMS.request(amsIp, mac, Operation.ADD, count_reminders,
                reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.request(amsIp, mac, Operation.MODIFY, count_reminders,
                reminderProgramStart, reminderChannelNumber, reminderProgramId, Integer.MAX_VALUE, reminderScheduleId, reminderId);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("", actual.get(1));
        testPurge();
    }

    @Test
    void testModify_reminderOffset_MIN_VALUE__invalid_offset() throws IOException {
        //todo!!!
        ArrayList actual = AMS.request(amsIp, mac, Operation.MODIFY, 1,
                reminderProgramStart, reminderChannelNumber, reminderProgramId, Integer.MIN_VALUE, reminderScheduleId, reminderId);
        assertEquals(HttpStatus.SC_BAD_REQUEST, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: invalid offset", actual.get(1));
    }

    /** reminderProgramId can be empty, AMS is removed his from json
     * @throws IOException - TBD
     */
    @RepeatedTest(count_iterations)
    void testModify_reminderProgramId_empty() throws IOException {
        ArrayList actual;
        actual = AMS.request(amsIp, mac, Operation.ADD, count_reminders,
                reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("", actual.get(1));
        //todo!!!
        actual = AMS.request(amsIp, mac, Operation.MODIFY, count_reminders,
                reminderProgramStart, reminderChannelNumber, "", reminderOffset, reminderScheduleId, reminderId);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("", actual.get(1));
        testPurge();
    }

    /** reminderProgramId can be empty, AMS is removed his from json
     * @throws IOException - TBD
     */
    @RepeatedTest(count_iterations)
    void testModify_reminderProgramId_wrong() throws IOException {
        ArrayList actual;
        actual = AMS.request(amsIp, mac, Operation.ADD, count_reminders,
                reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("", actual.get(1));
        //todo!!!
        actual = AMS.request(amsIp, mac, Operation.MODIFY, count_reminders,
                reminderProgramStart, reminderChannelNumber, "EP#@$%#$%@#$^$#%^#$%^", reminderOffset, reminderScheduleId, reminderId);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("", actual.get(1));
        testPurge();
    }

    @RepeatedTest(1)
    void testModify_reminderProgramStart_empty() throws IOException {
        ArrayList actual;
        long reminderScheduleId = reminderScheduleId(Generation.RANDOM);
        long reminderId = reminderId(Generation.RANDOM);
        actual = AMS.request(amsIp, mac, Operation.ADD, count_reminders,
                reminderProgramStart, 5, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.request(amsIp, mac, Operation.MODIFY, count_reminders,
                "-1", 5, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("", actual.get(1));

        testPurge();
    }

    @Test
    void testModify_reminderProgramStart_PAST__statusCode2() throws IOException {
        //todo!!!
        ArrayList actual = AMS.request(amsIp, mac, Operation.MODIFY, 1,
                "2000-01-01", reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("2", actual.get(1));
    }

    @Test
    void testModify_reminderProgramStart_text() throws IOException {
        //todo!!!
        ArrayList actual = AMS.request(amsIp, mac, Operation.MODIFY, 1,
                "YYYY-mm-dd", reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(HttpStatus.SC_BAD_REQUEST, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: incorrect message format", actual.get(1));
    }

    @Test
    void testModify_reminderProgramStart_wrong() throws IOException {
        //todo!!!
        ArrayList actual = AMS.request(amsIp, mac, Operation.MODIFY, 1,
                "0000-00-00", reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(HttpStatus.SC_BAD_REQUEST, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: invalid program start", actual.get(1));
        //testPurge();
    }

    /** 4 - reminder is unknown. Applies to "Reminders Delete" request (request ID=1) and "Reminders Modify" request (request ID=2)
     * @throws IOException - TBD
     */
    @Test
    void testModify_reminderScheduleId_0__statusCode4() throws IOException {
        //todo
        ArrayList actual = AMS.request(amsIp, mac, Operation.MODIFY, 1,
                reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, 0, reminderId);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("4", actual.get(1));
    }

    @Test
    void testModify_reminderScheduleId_empty() throws IOException {
        //todo
        ArrayList actual = AMS.request(amsIp, mac, Operation.MODIFY, 1,
                reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, -1, reminderId);
        assertEquals(HttpStatus.SC_BAD_REQUEST, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: incorrect message format", actual.get(1));
    }

    @RepeatedTest(count_iterations)
    @Deprecated
        //todo WHY it was deprecated?
    void testModify_reminderScheduleId_MAX_VALUE() throws IOException {
        ArrayList actual;
        actual = AMS.request(amsIp, mac, Operation.ADD, count_reminders,
                reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, Long.MAX_VALUE, reminderId);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.request(amsIp, mac, Operation.MODIFY, count_reminders,
                reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, Long.MAX_VALUE, reminderId);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("", actual.get(1));
        testPurge();
    }

    @RepeatedTest(count_iterations)
    @Deprecated
        //todo WHY it was deprecated?
    void testModify_reminderScheduleId_reminderId_MAX_VALUE() throws IOException {
        ArrayList actual;
        actual = AMS.request(amsIp, mac, Operation.ADD, count_reminders,
                reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, Long.MAX_VALUE, Long.MAX_VALUE);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.request(amsIp, mac, Operation.MODIFY, count_reminders,
                reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, Long.MAX_VALUE, Long.MAX_VALUE);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("", actual.get(1));
        testPurge();
    }

    @Test
    void testModify_reminderScheduleId_MIN_VALUE() throws IOException {
        //todo
        ArrayList actual = AMS.request(amsIp, mac, Operation.MODIFY, 1,
                reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, Long.MIN_VALUE, reminderId);
        assertEquals(HttpStatus.SC_BAD_REQUEST, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: incorrect reminderScheduleId", actual.get(1));
    }

    /** 4 - reminder is unknown. Applies to "Reminders Delete" request (request ID=1) and "Reminders Modify" request (request ID=2)
     * @throws IOException - TBD
     */
    @Test
    void testModify_reminderScheduleId_reminderId_0__statusCode4() throws IOException {
        //todo
        ArrayList actual = AMS.request(amsIp, mac, Operation.MODIFY, 1,
                reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, 0, 0);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("4", actual.get(1));
    }

    @Test
    void testModify_reminderScheduleId_reminderId_empty() throws IOException {
        ArrayList actual = AMS.request(amsIp, mac, Operation.MODIFY, count_reminders,
                reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, -1, -1);
        assertEquals(HttpStatus.SC_BAD_REQUEST, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: incorrect message format", actual.get(1));
    }

    @Test
    void testModify_reminderScheduleId_reminderId_MIN_VALUE() throws IOException {
        //todo
        ArrayList actual = AMS.request(amsIp, mac, Operation.MODIFY, 1,
                reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, Long.MIN_VALUE,  Long.MIN_VALUE);
        assertEquals(HttpStatus.SC_BAD_REQUEST, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: incorrect reminderScheduleId", actual.get(1));
    }

    private void testPurge() throws IOException {
        ArrayList actual = AMS.request(amsIp, mac, Operation.PURGE);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("", actual.get(1));
    }

}
