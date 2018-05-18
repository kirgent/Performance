package tv.zodiac.dev;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * We are Headend (on localhost): chain of requests: Headend(localhost) -> AMS -> STB -> AMS -> localhost
 */
class testAMS_Settings extends API {

    private NEWAPI_AMS AMS = new NEWAPI_AMS();

    @Test
    @Disabled
    void testSettings_Channel_Filters() throws IOException {
        //String json = "{"settings":{"groups":
        // [{"id":"STB000005FE680A",
        // "type":"device-stb",
        // "options": [{"name":"Channel Filters","value":["Premiums"]}]
        // }]
        // }}"
        ArrayList actual = AMS.Change_settings(mac, "Channel Filters", "Premiums");
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    void testSettings_Audio_Output_to_Other() throws IOException {
        ArrayList actual = AMS.Change_settings(mac, "Audio Output", "Other");
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    void testSettings_Audio_Output_to_Dolby_Digital() throws IOException {
        ArrayList actual = AMS.Change_settings(mac, "Audio Output", "Dolby Digital");
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    void testSettings_Audio_Output_to_HDMI() throws IOException {
        ArrayList actual = AMS.Change_settings(mac, "Audio Output", "HDMI");
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    void testSettings_negative_STB_MAC_not_found() throws IOException {
        ArrayList actual = AMS.Change_settings(mac_wrong, "Audio Output", "HDMI");
        assertEquals(expected404, actual.get(0));
        assertEquals("STB MAC not found: " + mac_wrong, actual.get(1));
    }

    @Test
    void testSettings_Audio_Output_to_value_empty() throws IOException {
        ArrayList actual = AMS.Change_settings(mac, "Audio Output", "");
        assertEquals(expected200, actual.get(0));
        assertEquals("incorrect value", actual.get(1));
    }

    @Test
    void testSettings_Audio_Output_to_value_wrong() throws IOException {
        ArrayList actual = AMS.Change_settings(mac, "Audio Output", "blablabla");
        assertEquals(expected200, actual.get(0));
        assertEquals("incorrect value", actual.get(1));
    }

    @Test
    void testSettings_negative_SET_025_Unsupported_data_type_Not_a_JSON_Object() throws IOException {
        ArrayList actual = AMS.Change_settings(mac, "Audio Output", "");
        assertEquals(expected500, actual.get(0));
        assertEquals("SET-025 Unsupported data type: Not a JSON Object:", actual.get(1));
    }

    @Test
    void testSettings_Turn_Reminders_to_Off() throws IOException {
        ArrayList actual = AMS.Change_settings(mac, "Turn On/Off Reminders", "Off");
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    void testSettings_Turn_Reminders_to_On() throws IOException {
        ArrayList actual = AMS.Change_settings(mac, "Turn On/Off Reminders", "On");
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    void testSettings_Turn_Reminders_to_empty_value() throws IOException {
        ArrayList actual = AMS.Change_settings(mac, "Turn On/Off Reminders", "");
        assertEquals(expected200, actual.get(0));
        assertEquals("incorrect value", actual.get(1));
    }

    @Test
    void testSettings_Guide_Narration_to_On() throws IOException {
        ArrayList actual = AMS.Change_settings(mac, "Guide Narration", "On");
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    void testSettings_Guide_Narration_to_Off() throws IOException {
        ArrayList actual = AMS.Change_settings(mac, "Guide Narration", "Off");
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    void testSettings_PC_to_On() throws IOException {
        ArrayList actual = AMS.Change_settings(mac, "Enable/Disable Parental Controls", "On");
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

    @Test
    void testSettings_PC_to_Off() throws IOException {
        ArrayList actual = AMS.Change_settings(mac, "Enable/Disable Parental Controls", "Off");
        assertEquals(expected200, actual.get(0));
        assertEquals("", actual.get(1));
    }

}
