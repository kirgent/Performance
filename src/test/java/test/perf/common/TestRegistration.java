package test.perf.common;

import java.util.ArrayList;

import org.apache.http.HttpStatus;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;


/**
 * We are localhost (Charter Headend). Full chain of requests: localhost -> AMS -> STB -> AMS -> localhost
 */
class TestRegistration extends MiddleAPI {
    private MiddleAPI Middle = new MiddleAPI();
    private int timeout = 20000;

    @ParameterizedTest
    @CsvFileSource(resources = "/remindersRegistration.csv", numLinesToSkip = 1)
    void testChangeRegistration(String ams_ip, String mac, String charterapi) {
        final ArrayList[] actual = new ArrayList[1];
        assertTimeoutPreemptively(ofMillis(timeout), () -> {
            actual[0] = Middle.changeRegistration(ams_ip, mac, charterapi);
        });
        assertEquals(HttpStatus.SC_OK, actual[0].get(0));
        assertEquals("", actual[0].get(1));
    }


    @ParameterizedTest
    @CsvFileSource(resources = "/remindersRegistration.csv", numLinesToSkip = 1)
    void testCheckRegistration(String ams_ip, String charterapi, String macaddress) {
        final ArrayList[] actual = new ArrayList[1];
        assertTimeoutPreemptively(ofMillis(timeout), () -> {
            actual[0] = Middle.checkRegistration(charterapi, macaddress);
        });
        assertEquals(HttpStatus.SC_OK, actual[0].get(0));
        assertEquals("", actual[0].get(1));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/remindersRegistration.csv", numLinesToSkip = 1)
    void testCheckRegistrationNoAmsIpFoundForMAC(String charterapi) {
        final ArrayList[] actual = new ArrayList[1];
        assertTimeoutPreemptively(ofMillis(timeout), () -> {
            actual[0] = Middle.checkRegistration(charterapi, "123456789012");
        });
        assertEquals(HttpStatus.SC_INTERNAL_SERVER_ERROR, actual[0].get(0));
        assertEquals("No amsIp found for mac", actual[0].get(1));
    }


}
