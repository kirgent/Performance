package tv.zodiac.dev;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.IOException;
import java.util.ArrayList;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

/**
 * We are localhost (Charter Headend). Full chain of requests: localhost -> AMS -> STB -> AMS -> localhost
 */
class testMiddle_Change_registration extends API_Middle {
    private API_Middle Middle = new API_Middle();
    private int timeout = 20000;

    @ParameterizedTest
    @CsvFileSource(resources = "/reminders_macaddress_registrations.csv", numLinesToSkip = 1)
    void testChange_registration(String charterapi, String macaddress, String ams_ip) throws IOException {
        final ArrayList[] actual = new ArrayList[1];
        assertTimeoutPreemptively(ofMillis(timeout), () -> {
            actual[0] = Middle.Change_registration(charterapi, macaddress, ams_ip);
        });
        assertEquals(expected200, actual[0].get(0));
        assertEquals("", actual[0].get(1));
    }

}
