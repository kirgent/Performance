package tv.zodiac.dev;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.IOException;
import java.util.ArrayList;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

/**
 * We are localhost (Charter Headend). Full chain of requests: localhost -> AMS -> STB -> AMS -> localhost
 */
class testMiddle_Check_registration extends API_Middle {
    private API_Middle Middle = new API_Middle();
    private int timeout = 20000;

    @ParameterizedTest
    @CsvFileSource(resources = "/reminders_macaddress_registrations.csv", numLinesToSkip = 1)
    void testCheck_registration(String charterapi, String macaddress) throws IOException {
        final ArrayList[] actual = new ArrayList[1];
        assertTimeoutPreemptively(ofMillis(timeout), () -> {
            actual[0] = Middle.Check_registration(charterapi, macaddress);
        });
        assertEquals(expected200, actual[0].get(0));
        assertEquals("", actual[0].get(1));
    }

    @Test
    @Disabled
    void testCheck_registration_a_No_amsIp_found_for_mac() throws IOException {
        ArrayList actual = Middle.Check_registration(charterapi_a, "123456789012");
        assertEquals(expected500, actual.get(0));
        assertEquals("No amsIp found for mac", actual.get(1));
    }

    @Test
    @Disabled
    void testCheck_registration_b_No_amsIp_found_for_mac() throws IOException {
        ArrayList actual = Middle.Check_registration(charterapi_b, "123456789012");
        assertEquals(expected500, actual.get(0));
        assertEquals("No amsIp found for mac", actual.get(1));
    }

    @Test
    @Disabled
    void testCheck_registration_c_No_amsIp_found_for_mac() throws IOException {
        ArrayList actual = Middle.Check_registration(charterapi_c, "123456789012");
        assertEquals(expected500, actual.get(0));
        assertEquals("No amsIp found for mac", actual.get(1));
    }

    @Test
    @Disabled
    void testCheck_registration_d_No_amsIp_found_for_mac() throws IOException {
        ArrayList actual = Middle.Check_registration(charterapi_d, "123456789012");
        assertEquals(expected500, actual.get(0));
        assertEquals("No amsIp found for mac", actual.get(1));
    }

}
