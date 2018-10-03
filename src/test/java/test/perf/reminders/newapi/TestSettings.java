package test.perf.reminders.newapi;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import test.perf.common.CommonAPI;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * We are localhost (Charter Headend). Full chain of requests: localhost -> AMS -> STB -> AMS -> localhost
 */
class TestSettings extends CommonAPI {

    private NewAPI AMS = new NewAPI();

    @Test
    @Disabled
    void testSettings_Channel_Filters() throws IOException {
        //String json = "{"settings":{"groups":
        // [{"id":"STB000005FE680A",
        // "type":"device-stb",
        // "options": [{"name":"Channel Filters","value":["Premiums"]}]
        // }]
        // }}"
        ArrayList actual = AMS.changeSettings(mac, "Channel Filters", "Premiums");
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    void testSettings_Audio_Output_to_Other() throws IOException {
        ArrayList actual = AMS.changeSettings(mac, "Audio Output", "Other");
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    void testSettings_Audio_Output_to_Dolby_Digital() throws IOException {
        ArrayList actual = AMS.changeSettings(mac, "Audio Output", "Dolby Digital");
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    void testSettings_Audio_Output_to_HDMI() throws IOException {
        ArrayList actual = AMS.changeSettings(mac, "Audio Output", "HDMI");
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    void testSettings_negative_STB_MAC_not_found() throws IOException {
        ArrayList actual = AMS.changeSettings(mac_wrong, "Audio Output", "HDMI");
        assertEquals(HttpStatus.SC_NOT_FOUND, actual.get(0));
        assertEquals("STB MAC not found: " + mac_wrong, actual.get(1));
    }

    @Test
    void testSettings_Audio_Output_to_value_empty() throws IOException {
        ArrayList actual = AMS.changeSettings(mac, "Audio Output", "");
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("incorrect value", actual.get(1));
    }

    @Test
    void testSettings_Audio_Output_to_value_wrong() throws IOException {
        ArrayList actual = AMS.changeSettings(mac, "Audio Output", "blablabla");
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("incorrect value", actual.get(1));
    }

    @Test
    void testSettings_negative_SET_025_Unsupported_data_type_Not_a_JSON_Object() throws IOException {
        ArrayList actual = AMS.changeSettings(mac, "Audio Output", "");
        assertEquals(HttpStatus.SC_INTERNAL_SERVER_ERROR, actual.get(0));
        assertEquals("SET-025 Unsupported data type: Not a JSON Object:", actual.get(1));
    }

    @Test
    void testSettings_Turn_Reminders_to_Off() throws IOException {
        ArrayList actual = AMS.changeSettings(mac, "Turn On/Off Reminders", "Off");
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    void testSettings_Turn_Reminders_to_On() throws IOException {
        ArrayList actual = AMS.changeSettings(mac, "Turn On/Off Reminders", "On");
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    void testSettings_Turn_Reminders_to_empty_value() throws IOException {
        ArrayList actual = AMS.changeSettings(mac, "Turn On/Off Reminders", "");
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("incorrect value", actual.get(1));
    }

    @Test
    void testSettings_Guide_Narration_to_On() throws IOException {
        ArrayList actual = AMS.changeSettings(mac, "Guide Narration", "On");
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    void testSettings_Guide_Narration_to_Off() throws IOException {
        ArrayList actual = AMS.changeSettings(mac, "Guide Narration", "Off");
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    void testSettings_PC_to_On() throws IOException {
        ArrayList actual = AMS.changeSettings(mac, "Enable/Disable Parental Controls", "On");
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    void testSettings_PC_to_Off() throws IOException {
        ArrayList actual = AMS.changeSettings(mac, "Enable/Disable Parental Controls", "Off");
        assertEquals(HttpStatus.SC_OK, actual.get(0));
        assertEquals("", actual.get(1));
    }

}
