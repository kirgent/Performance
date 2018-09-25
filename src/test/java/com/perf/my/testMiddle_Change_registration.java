package com.perf.my;

import org.apache.http.HttpStatus;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.ArrayList;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

/**
 * We are localhost (Charter Headend). Full chain of requests: localhost -> AMS -> STB -> AMS -> localhost
 */
class testMiddle_Change_registration extends API_middle {
    private API_middle Middle = new API_middle();
    private int timeout = 20000;

    @ParameterizedTest
    @CsvFileSource(resources = "/reminders_macaddress_registration.csv", numLinesToSkip = 1)
    void testChange_registration(String ams_ip, String mac, String charterapi) {
        final ArrayList[] actual = new ArrayList[1];
        assertTimeoutPreemptively(ofMillis(timeout), () -> {
            actual[0] = Middle.changeRegistration(ams_ip, mac, charterapi);
        });
        assertEquals(HttpStatus.SC_OK, actual[0].get(0));
        assertEquals("", actual[0].get(1));
    }

}
