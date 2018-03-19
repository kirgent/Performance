import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class testSettings_Audio extends testAPI {

    @Test
    @Disabled
    void testChangeSettings_Channel_Filters() throws IOException {
        //String json = "{"settings":{"groups":
        // [{"id":"STB000005FE680A",
        // "type":"device-stb",
        // "options": [{"name":"Channel Filters","value":["Premiums"]}]
        // }]
        // }}"
        long start = System.currentTimeMillis();
        ArrayList actual = api.Change_settings(ams_ip, macaddress[0], "Channel Filters", "Premiums");
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    void testChangeSettings_Audio_Output_to_Other() throws IOException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Change_settings(ams_ip, macaddress[0], "Audio Output", "Other");
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    void testChangeSettings_Audio_Output_to_Dolby_Digital() throws IOException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Change_settings(ams_ip, macaddress[0], "Audio Output", "Dolby Digital");
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    void testChangeSettings_Audio_Output_to_HDMI() throws IOException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Change_settings(ams_ip, macaddress[0], "Audio Output", "HDMI");
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    void testChangeSettings_STB_MAC_not_found() throws IOException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Change_settings(ams_ip, "001122334455", "Audio Output", "HDMI");
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected404, actual.get(0));
        assertEquals(expected404t, actual.get(1));
        assertEquals("STB MAC not found", actual.get(2));
    }

    @Test
    void testChangeSettings_incorrect_value() throws IOException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Change_settings(ams_ip, "001122334455", "Audio Output", "");
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("incorrect value", actual.get(2));
    }

    @Test
    void testChangeSettings_SET_025_Unsupported_data_type_Not_a_JSON_Object() throws IOException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Change_settings(ams_ip, macaddress[0], "Audio Output", "");
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected500, actual.get(0));
        assertEquals(expected500t, actual.get(1));
        assertEquals("SET-025 Unsupported data type: Not a JSON Object:", actual.get(2));
    }

    @Test
    void testChangeSettings_Turn_Off_Reminders() throws IOException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Change_settings(ams_ip, macaddress[0], "Turn On/Off Reminders", "Off");
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

    @Test
    void testChangeSettings_Turn_On_Reminders() throws IOException {
        long start = System.currentTimeMillis();
        ArrayList actual = api.Change_settings(ams_ip, macaddress[0], "Turn On/Off Reminders", "On");
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals(expected200t, actual.get(1));
        assertEquals("", actual.get(2));
    }

}
