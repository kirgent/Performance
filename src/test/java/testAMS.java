import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;

class testAMS {

    //static Logger log = Logger.getLogger(testAMS.class.getName());

    int expected200 = 200;    String expected200t = "OK";
    int expected400 = 400;    String expected400t = "Bad Request";
    int expected405 = 405;    String expected405t = "Method Not Allowed";
    int expected500 = 500;    String expected500t = "Internal Server Error";


    String[] boxD101 = {"A0722CEEC970", "WB20 D101 ???"};

    String[] boxD102 = {"3438B7EB2E24", "WB20 D102"};

    String[] boxD103 = {"3438B7EB2E28", "WB20 D103"};

    String[] boxD104 = {"3438B7EB2E34", "WB20 D104"};

    String[] boxD105 = {"3438B7EB2E30", "WB20 D105"};

    String[] boxD106 = {"3438B7EB2EC4", "WB20 D106"};

    String[] box_Kirmoto = {"0000007F8214", "Kirmoto"};

    String[] box_Tanya = {"000005FE680A", "Tanya"};

    String[] box_Vitya = {"A0722CEEC934", "Vitya"};

    String[] box_Katya_V = {"0000048D4EB4", "Katya_V"};

    String[] macaddress = boxD104;

    String ams_ip = "172.30.81.4";
    //String ams_ip = "172.30.82.132";
    //String ams_ip = "172.30.112.19";

    //DATE
    String[] rack_date = {"2018-03-15"};
    String[] rack_date_for_statuscode2 = { "2000-01-01" };

    //CHANNEL
    Integer[] rack_channel = {2};
    Integer[] rack_channel_negative = {-1};
    Integer[] rack_channel_all = { 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };
    Integer[] rack_channel_for_statuscode3 = { 9999 };
    //String
    final private String[] rack_channel_statuscode4 = { "1000" };


    final String charterapi_ = "http://spec.partnerapi.engprod-charter.net/api/pub/networksettingsmiddle/ns/settings";
    final String charterapi_b = "http://specb.partnerapi.engprod-charter.net/api/pub/networksettingsmiddle/ns/settings";
    final String charterapi_c = "http://specc.partnerapi.engprod-charter.net/api/pub/networksettingsmiddle/ns/settings";
    final String charterapi_d = "http://specd.partnerapi.engprod-charter.net/api/pub/networksettingsmiddle/ns/settings";
    final String charterapi_by_default = charterapi_b;

    //DEFAULTS
    int count_reminders_by_default = 48;
    String reminderProgramStart_by_default = "2013-03-15 00:00";
    //"reminderProgramStart": "2016-10-15 20:30"
    String reminderProgramId_by_default = "0";
    //"reminderProgramId": "EP002960010113"
    int reminderOffset_by_default = 0;
    //"reminderOffset": 15
    int reminderScheduleId_by_default = 1;
    //"reminderScheduleId": "2"
    int reminderId_by_default = 1;
    //"reminderId": "5"
    //"reminderChannelNumber": 27

    Middle api = new Middle();

    @Test
    void testDate() {
        assertEquals("2018-03-16", get_date(1, false));
        assertEquals("2018-03-16 2018-03-17", get_date(2, true));
        assertEquals("2018-03-17", get_date(2, false));
        assertEquals("2018-03-16 2018-03-17", get_date(2, true));
    }

    @Test
    void testTime() {
        assertEquals("02:30", get_time(140, false));
    }

    private String get_date(int count, Boolean several) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat pattern = new SimpleDateFormat("yyyy-MM-dd");

        String result = "";
        String[] rack_date = new String[count];

        if (several) {
            for (int i = 1; i <= count; i++) {
                calendar.add(Calendar.DAY_OF_YEAR, +1);
                //result += pattern.format(calendar.getTime());
                rack_date[i - 1] = pattern.format(calendar.getTime());
                //System.out.println(rack_date[i]);
                result += pattern.format(calendar.getTime());
                if (i != count) {
                    result += " ";
                }
            }
        } else {
            calendar.add(Calendar.DAY_OF_YEAR, +count);
            result = pattern.format(calendar.getTime());
        }
        System.out.println("generated result: " + result);
        return result;
    }

    private String get_time(int count, Boolean several) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat pattern = new SimpleDateFormat("HH:mm");
        calendar.setTime(new Date(0, 0, 0, 0, 0));

        calendar.add(Calendar.MINUTE, count);
        String result = pattern.format(calendar.getTime());
        System.out.println("generated times: " + result);
        return result;
    }


    //public void testMiddle_Request(){
        //curl -vk -X POST -H "Content-Type: application/json"
        //String charterapiX = "http://specd.partnerapi.engprod-charter.net/api/pub/remindersmiddle/v1/reminders" +
                //"?deviceId=000007444C77&lineupId=CA11-1"
                //-d '{"reminderType":"Individual","deliveryId":"49767-MV000209150000-1488390180000","channelId":"49767","programId":"MV000209150000","channelNumber":662,"startTime":1488390180000,"reminderPresetTime":0}'

    //}



}
