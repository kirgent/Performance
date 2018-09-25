package com.perf.my;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * We are localhost (Charter Headend). Full chain of requests: localhost -> AMS -> STB -> AMS -> localhost
 */
class testAMS_newAPI_Reminder_Delete extends API_common {

    private API_new AMS = new API_new();
    final private int count_iterations = 10;
    final private int count_reminders = 2;

    @RepeatedTest(count_iterations)
    void testDelete() throws IOException {
        ArrayList actual;
        actual = AMS.request(ams_ip, mac, Operation.add, count_reminders, reminderProgramStart, reminderChannelNumber(1000), reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.request(ams_ip, mac, Operation.delete, count_reminders, reminderScheduleId, reminderId);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    void testDelete__statusCode3() throws IOException {
        //todo
        int count_reminders = 1;
        ArrayList actual = AMS.request(ams_ip, mac, Operation.delete, count_reminders, reminderScheduleId, reminderId);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("3", actual.get(1));
    }

    @Test
    void testDelete_count_is_0() throws IOException {
        int count_reminders = 0;
        ArrayList actual = AMS.request(ams_ip, mac, Operation.delete, count_reminders, reminderScheduleId, reminderId);
        assertEquals(HttpStatus.SC_BAD_REQUEST, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: wrong number of reminders", actual.get(1));
    }

    @Test
    void testDelete_macaddress_empty() throws IOException {
        //todo
        int count_reminders = 1;
        ArrayList actual = AMS.request(ams_ip, "", Operation.delete, count_reminders, reminderScheduleId, reminderId);
        assertEquals(HttpStatus.SC_BAD_REQUEST, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: wrong deviceId", actual.get(1));
    }

    @Test
    void testDelete_macaddress_wrong__unknown_MAC() throws IOException {
        //todo
        int count_reminders = 1;
        ArrayList actual = AMS.request(ams_ip, mac_wrong, Operation.delete, count_reminders, reminderScheduleId, reminderId);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("unknown MAC", actual.get(1));
    }

    @Test
    @Deprecated
    void testDelete_macaddress_wrong__Box_is_not_registered() throws IOException {
        //todo
        int count_reminders = 1;
        ArrayList actual = AMS.request(ams_ip, mac_wrong, Operation.delete, count_reminders, reminderScheduleId, reminderId);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("REM-ST-001 Box is not registered", actual.get(1));
    }

    @Test
    void testDelete_reminderId_() throws IOException {
        //todo
        int count_reminders = 1;
        ArrayList actual = AMS.request(ams_ip, mac, Operation.delete, count_reminders, reminderScheduleId, 0);
        assertEquals(HttpStatus.SC_BAD_REQUEST, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: incorrect reminderId", actual.get(1));
    }

    @Test
    void testDelete_reminderId_empty() throws IOException {
        //todo
        int count_reminders = 1;
        ArrayList actual = AMS.request(ams_ip, mac, Operation.delete, count_reminders, reminderScheduleId, -1);
        assertEquals(HttpStatus.SC_INTERNAL_SERVER_ERROR, actual.get(0));
        assertEquals("name cannot be null", actual.get(1));
    }

    @RepeatedTest(count_iterations)
    void testDelete_reminderId_MAX_VALUE() throws IOException {
        //todo
        int count_reminders = 1;
        ArrayList actual;
        actual = AMS.request(ams_ip, mac, Operation.add, count_reminders, reminderProgramStart, reminderChannelNumber(1000), reminderProgramId, reminderOffset, reminderScheduleId, Long.MAX_VALUE);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.request(ams_ip, mac, Operation.delete, count_reminders, reminderScheduleId, Long.MAX_VALUE);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    @DisplayName("testDelete - check reminderId for Long.MIN_VALUE")
    void testDelete_reminderId_MIN_VALUE() throws IOException {
        //todo
        int count_reminders = 1;
        ArrayList actual = AMS.request(ams_ip, mac, Operation.delete, count_reminders, reminderScheduleId, Long.MIN_VALUE);
        assertEquals(HttpStatus.SC_BAD_REQUEST, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: incorrect reminderId", actual.get(1));
    }

    @Test
    void testDelete_reminderScheduleId_0() throws IOException {
        //todo
        int count_reminders = 1;
        ArrayList actual = AMS.request(ams_ip, mac, Operation.delete, count_reminders, 0, reminderId);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("4", actual.get(1));
    }

    @Test
    void testDelete_reminderScheduleId_reminderId_0() throws IOException {
        //todo
        int count_reminders = 1;
        ArrayList actual = AMS.request(ams_ip, mac, Operation.delete, count_reminders, 0, 0);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("4", actual.get(1));
    }

    @Test
    void testDelete_reminderScheduleId_empty() throws IOException {
        //todo
        int count_reminders = 1;
        ArrayList actual = AMS.request(ams_ip, mac, Operation.delete, count_reminders, -1, reminderId);
        assertEquals(HttpStatus.SC_INTERNAL_SERVER_ERROR, actual.get(0));
        assertEquals("name cannot be null", actual.get(1));
    }

    /** 4 - reminder is unknown. Applies to "Reminders Delete" request (request ID=1) and "Reminders Modify" request (request ID=2)
     * @throws IOException - TBD
     */
    @Test
    void testDelete_reminderScheduleId_reminderId_empty() throws IOException {
        //todo
        int count_reminders = 1;
        ArrayList actual = AMS.request(ams_ip, mac, Operation.delete, count_reminders, -1, -1);
        assertEquals(HttpStatus.SC_INTERNAL_SERVER_ERROR, actual.get(0));
        assertEquals("name cannot be null", actual.get(1));
    }

    @RepeatedTest(count_iterations)
    void testDelete_reminderScheduleId_MAX_VALUE() throws IOException {
        //todo
        int count_reminders = 1;
        ArrayList actual;
        actual = AMS.request(ams_ip, mac, Operation.add, count_reminders, reminderProgramStart, reminderChannelNumber(1000), reminderProgramId, reminderOffset, Long.MAX_VALUE, reminderId);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.request(ams_ip, mac, Operation.delete, count_reminders, Long.MAX_VALUE, reminderId);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @RepeatedTest(count_iterations)
    void testDelete_reminderScheduleId_reminderId_MAX_VALUE() throws IOException {
        //todo
        int count_reminders = 1;
        ArrayList actual;
        actual = AMS.request(ams_ip, mac, Operation.add, count_reminders, reminderProgramStart, reminderChannelNumber(1000), reminderProgramId, reminderOffset, Long.MAX_VALUE, Long.MAX_VALUE);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("", actual.get(1));

        actual = AMS.request(ams_ip, mac, Operation.delete, count_reminders, Long.MAX_VALUE, Long.MAX_VALUE);
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    void testDelete_reminderScheduleId_MIN_VALUE() throws IOException {
        //todo
        int count_reminders = 1;
        ArrayList actual = AMS.request(ams_ip, mac, Operation.delete, count_reminders, Long.MIN_VALUE, reminderId);
        assertEquals(HttpStatus.SC_BAD_REQUEST, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: incorrect reminderScheduleId", actual.get(1));
    }

    @Test
    void testDelete_reminderScheduleId_reminderId_MIN_VALUE() throws IOException {
        //todo
        int count_reminders = 1;
        ArrayList actual = AMS.request(ams_ip, mac, Operation.delete, count_reminders, Long.MIN_VALUE, Long.MIN_VALUE);
        assertEquals(HttpStatus.SC_BAD_REQUEST, actual.get(0));
        assertEquals("REM-008 Reminders parsing error: incorrect reminderScheduleId", actual.get(1));
    }

}
