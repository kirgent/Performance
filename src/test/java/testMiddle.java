import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;


import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;

public class testMiddle {

    //static Logger log = Logger.getLogger(testMiddle.class.getName());

    int expected200 = 200;//200 Ok
    int expected400 = 400;//400 Bad Request
    private int expected500 = 500;//500 Internal Server Error

    //kir 1.1
    //String macaddress = "6CB56BBA882C";

    //kir 2.0 D104
    //String macaddress = "3438B7EB2E34";
    //kir moto
    //String macaddress = "0000007F8214";

    //tanya
    //String macaddress = "000005FE680A";

    //Vitya
    String macaddress = "A0722CEEC934";

    int count_reminders_by_default = 48;
    int reminderChannelNumber_by_default = 2;
    String reminderProgramStart_by_default = "2013-03-08 00:00";
    int reminderProgramId_by_default = 1;
    int reminderOffset_by_default = 0;
    private int reminderOffset_new_by_default = 10;
    int reminderScheduleId_by_default = 1;
    int reminderId_by_default = 1;

                          //"reminderChannelNumber": 27
                          //"reminderProgramStart": "2016-10-15 20:30"
                          //"reminderProgramId": "EP002960010113"
                          //"reminderOffset": 15
                          //"reminderScheduleId": "2"
                          //"reminderId": "5"

    String charterapi_ = "http://spec.partnerapi.engprod-charter.net/api/pub/networksettingsmiddle/ns/settings";
    String charterapi_b = "http://specb.partnerapi.engprod-charter.net/api/pub/networksettingsmiddle/ns/settings";
    String charterapi_c = "http://specc.partnerapi.engprod-charter.net/api/pub/networksettingsmiddle/ns/settings";
    String charterapi_d = "http://specd.partnerapi.engprod-charter.net/api/pub/networksettingsmiddle/ns/settings";
    String charterapi_by_default = charterapi_;

    //String ams_ip_by_default = "172.30.81.4";
    String ams_ip_by_default = "172.30.112.19";

    Middle old_api = new Middle();
    Middle_new new_api = new Middle_new();

    public testMiddle() throws IOException {
    }

    @Test
    @Ignore
    public void testSunday() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        //calendar.set(2018, Calendar.MONTH, 2, 11, 30, 0);
        calendar.set(2018,Calendar.MONDAY,1,1,1,1);
        calendar.add(Calendar.DAY_OF_YEAR, -2);
        assertEquals(Calendar.FRIDAY, calendar.get(Calendar.DAY_OF_WEEK));
    }

    @Test
    @Ignore
    public void testFormat() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        //calendar.set(2018, Calendar.MONTH, 2, 11, 30, 0);
        calendar.set(2018,Calendar.MONDAY,1,1,1,1);
        calendar.add(Calendar.DAY_OF_YEAR, -2);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dateFormat.setLenient(false);
        dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));

    }


    @Test
    public void testPurge_REM_ST_01_Box_is_not_registered() throws IOException {
        long start = System.currentTimeMillis();
        ArrayList actual = old_api.Purge(macaddress);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected500, actual.get(0));
        assertEquals("REM-ST-001 Box is not registered", "REM-ST-001 Box is not registered", actual.get(1));
    }

    @Test
    @After
    public void testPurge() throws IOException {
        long start = System.currentTimeMillis();
        ArrayList actual = old_api.Purge(macaddress);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        //assertEquals("SUCCESS", "SUCCESS", actual.get(1));
    }

}
