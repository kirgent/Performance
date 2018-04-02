import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

class testAMS_Settings extends API {

    private API_AMS AMS = new API_AMS();

    @Test
    @Disabled
    void testSettings_Channel_Filters() throws IOException {
        //String json = "{"settings":{"groups":
        // [{"id":"STB000005FE680A",
        // "type":"device-stb",
        // "options": [{"name":"Channel Filters","value":["Premiums"]}]
        // }]
        // }}"
        starttime();
        ArrayList actual = AMS.Change_settings(ams_ip, macaddress, "Channel Filters", "Premiums");
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    void testSettings_Audio_Output_to_Other() throws IOException {
        starttime();
        ArrayList actual = AMS.Change_settings(ams_ip, macaddress, "Audio Output", "Other");
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    void testSettings_Audio_Output_to_Dolby_Digital() throws IOException {
        starttime();
        ArrayList actual = AMS.Change_settings(ams_ip, macaddress, "Audio Output", "Dolby Digital");
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    void testSettings_Audio_Output_to_HDMI() throws IOException {
        starttime();
        ArrayList actual = AMS.Change_settings(ams_ip, macaddress, "Audio Output", "HDMI");
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    void testSettings_negative_STB_MAC_not_found() throws IOException {
        starttime();
        ArrayList actual = AMS.Change_settings(ams_ip, macaddress_wrong, "Audio Output", "HDMI");
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected404, actual.get(0));
        assertEquals(expected404t, actual.get(1));
        assertEquals("STB MAC not found: " + macaddress_wrong, actual.get(2));
    }

    @Test
    void testSettings_Audio_Output_to_value_empty() throws IOException {
        starttime();
        ArrayList actual = AMS.Change_settings(ams_ip, macaddress, "Audio Output", "");
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("incorrect value", actual.get(2));
    }

    @Test
    void testSettings_Audio_Output_to_value_wrong() throws IOException {
        starttime();
        ArrayList actual = AMS.Change_settings(ams_ip, macaddress, "Audio Output", "blablabla");
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("incorrect value", actual.get(2));
    }

    @Test
    void testSettings_negative_SET_025_Unsupported_data_type_Not_a_JSON_Object() throws IOException {
        starttime();
        ArrayList actual = AMS.Change_settings(ams_ip, macaddress, "Audio Output", "");
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected500, actual.get(0));
        assertEquals(expected500t, actual.get(1));
        assertEquals("SET-025 Unsupported data type: Not a JSON Object:", actual.get(2));
    }

    @Test
    void testSettings_Turn_Reminders_to_Off() throws IOException {
        starttime();
        ArrayList actual = AMS.Change_settings(ams_ip, macaddress, "Turn On/Off Reminders", "Off");
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    void testSettings_Turn_Reminders_to_On() throws IOException {
        starttime();
        ArrayList actual = AMS.Change_settings(ams_ip, macaddress, "Turn On/Off Reminders", "On");
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    void testSettings_Turn_Reminders_to_empty_value() throws IOException {
        starttime();
        ArrayList actual = AMS.Change_settings(ams_ip, macaddress, "Turn On/Off Reminders", "");
        finishtime();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("incorrect value", actual.get(2));
    }

}
