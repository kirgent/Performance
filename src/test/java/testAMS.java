import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;

class testAMS {

    //static Logger log = Logger.getLogger(testAMS.class.getName());

    int expected200 = 200;    String expected200t = "OK";
    int expected400 = 400;    String expected400t = "Bad Request";
    int expected405 = 405;    String expected405t = "Method Not Allowed";
    int expected500 = 500;    String expected500t = "Internal Server Error";

    //String notes="kir 1.1"
    //String macaddress = "6CB56BBA882C";

    //String notes = "WB20 D102";
    //String macaddress = "3438B7EB2E24";

    //String notes = "WB20 D103";
    //String macaddress = "3438B7EB2E28";

    String notes = "WB20 D104";
    String macaddress = "3438B7EB2E34";

    //String notes = "WB20 D105";
    //String macaddress = "3438B7EB2E30";

    //String notes = "WB20 D106";
    //String macaddress = "3438B7EB2EC4";

    //String notes = "Kirmoto";
    //String macaddress = "0000007F8214";

    //String notes = "Tanya";
    //String macaddress = "000005FE680A";

    //String notes = "Vitya";
    //String macaddress = "A0722CEEC934";

    //String notes = "Katya_V";
    //String macaddress = "0000048D4EB4";

    String ams_ip = "172.30.81.4";
    //String ams_ip = "172.30.82.132";
    //String ams_ip = "172.30.112.19";

    //DATE
    String[] rack_date = {"2018-03-13"};
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
    //String reminderProgramId_by_default = "EP002960010113";
    String reminderProgramId_by_default = "0";
    int reminderOffset_by_default = 0;
    int reminderScheduleId_by_default = 1;
    int reminderId_by_default = 1;

    //"reminderId": "5"
    //"reminderScheduleId": "2"
    //"reminderOffset": 15
    //"reminderProgramId": "EP002960010113"
    //"reminderProgramStart": "2016-10-15 20:30"
    //"reminderChannelNumber": 27

    Middle api = new Middle();

    @Test
    @Disabled
    void testSunday() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        //calendar.set(2018, Calendar.MONTH, 2, 11, 30, 0);
        calendar.set(2018,Calendar.MONDAY,1,1,1,1);
        calendar.add(Calendar.DAY_OF_YEAR, -2);
        assertEquals(Calendar.FRIDAY, calendar.get(Calendar.DAY_OF_WEEK));
    }

    @Test
    @Disabled
    void testFormat() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        //calendar.set(2018, Calendar.MONTH, 2, 11, 30, 0);
        calendar.set(2018,Calendar.MONDAY,1,1,1,1);
        calendar.add(Calendar.DAY_OF_YEAR, -2);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dateFormat.setLenient(false);
        dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));

    }

    //public void testMiddle_Request(){
        //curl -vk -X POST -H "Content-Type: application/json"
        //String charterapiX = "http://specd.partnerapi.engprod-charter.net/api/pub/remindersmiddle/v1/reminders" +
                //"?deviceId=000007444C77&lineupId=CA11-1"
                //-d '{"reminderType":"Individual","deliveryId":"49767-MV000209150000-1488390180000","channelId":"49767","programId":"MV000209150000","channelNumber":662,"startTime":1488390180000,"reminderPresetTime":0}'

    //}



}
