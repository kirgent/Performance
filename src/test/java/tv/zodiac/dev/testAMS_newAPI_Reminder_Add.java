package tv.zodiac.dev;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * We are localhost (Charter Headend). Full chain of requests: localhost -> AMS -> STB -> AMS -> localhost
 */
class testAMS_newAPI_Reminder_Add extends API_common {

    private NewAPI_AMS AMS = new NewAPI_AMS();
    final private int count_iterations = 10;
    final private int count_reminders = 2;

    /** common test for Adding
     * @throws IOException - TBD
     */
    @RepeatedTest(count_iterations)
    void testAdd() throws IOException {
        ArrayList actual = AMS.request(ams_ip, mac, Operation.add, count_reminders, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
        testPurge();
    }

    @Test
    void testAdd_count_is_0() throws IOException {
        ArrayList actual = AMS.request(ams_ip, mac, Operation.add, 0, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: wrong number of reminders", actual.get(1));
    }

    @Test
    void testAdd_macaddress_empty() throws IOException {
        ArrayList actual = AMS.request(ams_ip, "", Operation.add, count_reminders, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: wrong deviceId", actual.get(1));
    }

    @Test
    void testAdd_macaddress_wrong__unknown_MAC() throws IOException {
        ArrayList actual = AMS.request(ams_ip, mac_wrong, Operation.add, count_reminders, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("unknown MAC", actual.get(1));
    }

    @Test
    @Deprecated
    void testAdd_macaddress_wrong__Box_is_not_registered() throws IOException {
        ArrayList actual = AMS.request(ams_ip, mac_wrong, Operation.add, count_reminders, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("REM-ST-001 Box is not registered", actual.get(1));
    }

    /** 3 - reminder is set for unknown channel. \"Reminders Add\" request (request ID=0)
     * @throws IOException - TBD
     */
    @Test
    void testAdd_reminderChannelNumber_0__statusCode3() throws IOException {
        ArrayList actual = AMS.request(ams_ip, mac, Operation.add, count_reminders, reminderProgramStart, 0, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("3", actual.get(1));
    }

    @Test
    void testAdd_reminderChannelNumber_empty() throws IOException {
        ArrayList actual = AMS.request(ams_ip, mac, Operation.add, count_reminders, reminderProgramStart, -1, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected500, actual.get(0));
        assertEquals("name cannot be null", actual.get(1));
    }

    //todo!!!
    @Test
    //todo!!!
    void testAdd_reminderChannelNumber_LONG_MAX_VALUE() throws IOException {
        ArrayList actual = AMS.request(ams_ip, mac, Operation.add, count_reminders, reminderProgramStart, Long.MAX_VALUE, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected500, actual.get(0));
        assertEquals("name cannot be null", actual.get(1));
    }

    /** 3 - reminder is set for unknown channel. \"Reminders Add\" request (request ID=0)
     * @throws IOException - TBD
     */
    @Test
    void testAdd_reminderChannelNumber_MAX_VALUE__statusCode3() throws IOException {
        ArrayList actual = AMS.request(ams_ip, mac, Operation.add, count_reminders, reminderProgramStart, Integer.MAX_VALUE, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("3", actual.get(1));
    }

    @Test
    void testAdd_reminderChannelNumber_MIN_VALUE() throws IOException {
        ArrayList actual = AMS.request(ams_ip, mac, Operation.add, count_reminders, reminderProgramStart, Integer.MIN_VALUE, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: invalid channel number", actual.get(1));
    }

    @Test
    void testAdd_reminderId_empty() throws IOException {
        ArrayList actual = AMS.request(ams_ip, mac, Operation.add, 1, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, -1);
        assertEquals(expected500, actual.get(0));
        assertEquals("name cannot be null", actual.get(1));
    }

    @RepeatedTest(count_iterations)
    void testAdd_reminderId_MAX_VALUE() throws IOException {
        //long max_value = 18446744073709551615L;
        //Long.MAX_VALUE=9223372036854775807
        ArrayList actual = AMS.request(ams_ip, mac, Operation.add, count_reminders, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, Long.MAX_VALUE);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
        testPurge();
    }

    @Test
    void testAdd_reminderId_MIN_VALUE() throws IOException {
        ArrayList actual = AMS.request(ams_ip, mac, Operation.add, count_reminders, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, Long.MIN_VALUE);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: incorrect reminderId", actual.get(1));
    }

    /** 4 - reminder is unknown. Applies to "Reminders Delete" request (request ID=1) and "Reminders Modify" request (request ID=2)
     * @throws IOException - TBD
     */
    @Test
    void testAdd_reminderId_0__statusCode4() throws IOException {
        ArrayList actual = AMS.request(ams_ip, mac, Operation.add, count_reminders, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, 0);
        //discussed with Natalia Tarabutina:
        assertEquals(expected200, actual.get(0));
        assertEquals("4", actual.get(1));
    }

    @Test
    void testAdd_reminderOffset_empty() throws IOException {
        ArrayList actual = AMS.request(ams_ip, mac, Operation.add, count_reminders, reminderProgramStart, reminderChannelNumber, reminderProgramId, -1, reminderScheduleId, reminderId);
        assertEquals(expected500, actual.get(0));
        assertEquals("name cannot be null", actual.get(1));
    }

    @Test
    void testAdd_reminderOffset_LONG_MAX_VALUE() throws IOException {
        ArrayList actual = AMS.request(ams_ip, mac, Operation.add, count_reminders, reminderProgramStart, reminderChannelNumber, reminderProgramId, Long.MAX_VALUE, reminderScheduleId, reminderId);
        assertEquals(expected500, actual.get(0));
        assertEquals("name cannot be null", actual.get(1));
    }

    @RepeatedTest(count_iterations)
    void testAdd_reminderOffset_MAX_VALUE() throws IOException {
        ArrayList actual = AMS.request(ams_ip, mac, Operation.add, count_reminders, reminderProgramStart, reminderChannelNumber, reminderProgramId, Integer.MAX_VALUE, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
        //testPurge();
    }

    @Test
    void testAdd_reminderOffset_MIN_VALUE() throws IOException {
        ArrayList actual = AMS.request(ams_ip, mac, Operation.add, count_reminders, reminderProgramStart, reminderChannelNumber, reminderProgramId, Integer.MIN_VALUE, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: invalid offset", actual.get(1));
    }

    @Test
    void testAdd_reminderOffset_wrong() throws IOException {
        ArrayList actual = AMS.request(ams_ip, mac, Operation.add, count_reminders, reminderProgramStart, reminderChannelNumber, reminderProgramId, -2, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: missing offset", actual.get(1));
    }

    /** reminderProgramId is optional and AMS is removed his from json
     * @throws IOException - TBD
     */
    @RepeatedTest(count_iterations)
    void testAdd_reminderProgramId_empty() throws IOException {
        ArrayList actual = AMS.request(ams_ip, mac, Operation.add, count_reminders, reminderProgramStart, reminderChannelNumber, "", reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
        //assertEquals("REM-008 Reminders parsing error: wrong programId", actual.get(1));
        testPurge();
    }

    /** reminderProgramId is optional and AMS is removed his from json
     * @throws IOException - TBD
     */
    @RepeatedTest(count_iterations)
    void testAdd_reminderProgramId_wrong() throws IOException {
        ArrayList actual = AMS.request(ams_ip, mac, Operation.add, count_reminders, reminderProgramStart, reminderChannelNumber, "EP#@$%#$%@#$^$#%^#$%^", reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
        testPurge();
    }

    @Test
    void testAdd_reminderProgramStart_empty() throws IOException {
        ArrayList actual = AMS.request(ams_ip, mac, Operation.add, count_reminders, "", reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: missing program start", actual.get(1));
    }

    /** 2 - reminder is set for time in the past. Applies to "Reminders Add" request (request ID=0)
     * @throws IOException - TBD
     */
    @Test
    void testAdd_reminderProgramStart_PAST__statusCode2() throws IOException {
        ArrayList actual = AMS.request(ams_ip, mac, Operation.add, count_reminders, "2000-01-01", reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("2", actual.get(1));
    }

    @Test
    void testAdd_reminderProgramStart_text() throws IOException {
        ArrayList actual = AMS.request(ams_ip, mac, Operation.add, count_reminders, "yyyy-mm-dd", reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected500, actual.get(0));
        assertEquals("name cannot be null", actual.get(1));
    }

    @Test
    void testAdd_reminderProgramStart_wrong() throws IOException {
        ArrayList actual = AMS.request(ams_ip, mac, Operation.add, count_reminders, "0000-00-00", reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: invalid program start", actual.get(1));
    }

    /** 4 - reminder is unknown. Applies to "Reminders Delete" request (request ID=1) and "Reminders Modify" request (request ID=2)
     * @throws IOException - TBD
     */
    @Test
    void testAdd_reminderScheduleId_0__statusCode4() throws IOException {
        ArrayList actual = AMS.request(ams_ip, mac, Operation.add, count_reminders, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, 0, reminderId);
        //discussed with Natalia Tarabutina:
        assertEquals(expected200, actual.get(0));
        assertEquals("4", actual.get(1));
    }

    /**4 - reminder is unknown. Applies to "Reminders Delete" request (request ID=1) and "Reminders Modify" request (request ID=2)
     * 5 - reminder with provided pair of identifiers (reminderScheduleId and reminderId) is already set (for Add Reminder request)
     * @throws IOException - TBD
     */
    @Test
    void testAdd_reminderScheduleId_reminderId_0__statusCode4() throws IOException {
        ArrayList actual = AMS.request(ams_ip, mac, Operation.add, count_reminders, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, 0, 0);
        //discussed with Natalia Tarabutina:
        assertEquals(expected200, actual.get(0));
        assertEquals("45", actual.get(1));
    }

    @Test
    void testAdd_reminderScheduleId_empty() throws IOException {
        ArrayList actual = AMS.request(ams_ip, mac, Operation.add, count_reminders, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, -1, reminderId);
        assertEquals(expected500, actual.get(0));
        assertEquals("name cannot be null", actual.get(1));
    }

    @Test
    void testAdd_reminderScheduleId_reminderId_empty() throws IOException {
        ArrayList actual = AMS.request(ams_ip, mac, Operation.add, count_reminders, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, -1, -1);
        assertEquals(expected500, actual.get(0));
        assertEquals("name cannot be null", actual.get(1));
    }

    @RepeatedTest(count_iterations)
    void testAdd_reminderScheduleId_MAX_VALUE() throws IOException {
        ArrayList actual = AMS.request(ams_ip, mac, Operation.add, count_reminders, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, Long.MAX_VALUE, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
        testPurge();
    }

    @RepeatedTest(count_iterations)
    void testAdd_reminderScheduleId_reminderId_MAX_VALUE() throws IOException {
        int count_reminders = 1;
        ArrayList actual = AMS.request(ams_ip, mac, Operation.add, count_reminders, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, Long.MAX_VALUE, Long.MAX_VALUE);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
        testPurge();
    }

    @Test
    void testAdd_reminderScheduleId_MIN_VALUE() throws IOException {
        ArrayList actual = AMS.request(ams_ip, mac, Operation.add, count_reminders, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, Long.MIN_VALUE, reminderId);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: incorrect reminderScheduleId", actual.get(1));
    }

    @Test
    void testAdd_reminderScheduleId_reminderId_MIN_VALUE() throws IOException {
        ArrayList actual = AMS.request(ams_ip, mac, Operation.add, count_reminders, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, Long.MIN_VALUE, Long.MIN_VALUE);
        assertEquals(expected400, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: incorrect reminderScheduleId", actual.get(1));
    }

    /**5 - reminder with provided pair of identifiers (reminderScheduleId and reminderId) is already set (for Add Reminder request)
     * @throws IOException - TBD
     */
    @RepeatedTest(count_iterations)
    void testAdd_statusCode5() throws IOException {
        //todo count=1 MUST BE !!! otherwise random will be used
        int count_reminders = 1;
        ArrayList actual;
        actual = AMS.request(ams_ip, mac, Operation.add, count_reminders, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.request(ams_ip, mac, Operation.add, count_reminders, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(expected200, actual.get(0));
        assertEquals("5", actual.get(1));
        testPurge();
    }

    @Disabled
    private void testPurge() throws IOException {
        ArrayList actual = AMS.request(ams_ip, mac, Operation.purge);
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }
}
