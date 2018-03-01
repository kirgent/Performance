import org.junit.Test;
import sun.util.logging.resources.logging;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import static java.util.logging.FileHandler.*;
import static org.junit.Assert.assertEquals;

public class testMiddle {

    static Logger log = Logger.getLogger(testMiddle.class.getName());
    //handlers= FileHandler

    //pattern = application_log.txt
    //limit = 1000000
    //count = 5
    //formatter = java.util.logging.SimpleFormatter

    int expected200 = 200;//200 Ok
    private int expected400 = 400;//400 Bad Request
    private int expected500 = 500;//500 Internal Server Error

    //kir 1.1
    //String macaddress = "6CB56BBA882C";

    //kir 2.0 D104
    String macaddress = "3438B7EB2E34";

    //tanya
    //String macaddress = "000005FE680A";

    private int count_reminders_by_default = 48;
    private int reminderChannelNumber_by_default = 2;
    private String reminderProgramStart_by_default = "2013-03-08 00:00";
    private int reminderProgramId_by_default = 1;
    private int reminderOffset_by_default = 0;
    private int reminderOffset_new_by_default = 10;
    private int reminderScheduleId_by_default = 1;
    private int reminderId_by_default = 1;

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

    Middle_old request = new Middle_old();

    //long timestart(){
    //    return System.currentTimeMillis();
    //}

    @Test
    public void testAdd_48rems() throws IOException, InterruptedException {
        //System.out.println("[DBG] testAdd_48rems:");
        long start = System.currentTimeMillis();
        int actual = request.Add(macaddress, 2);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual);
    }

    @Test
    public void testAdd_288rems() throws IOException, InterruptedException {
        //System.out.println("[DBG] testAdd_288rems:");
        long start = System.currentTimeMillis();
        int actual = request.Add(macaddress, 288);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual);
    }

    @Test
    public void testAdd_720rems() throws IOException, InterruptedException {
        //System.out.println("[DBG] testAdd_720rems:");
        long start = System.currentTimeMillis();
        int actual = request.Add(macaddress, 720);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual);
    }

    @Test
    public void testAdd_48rems_without_macaddress() throws IOException, InterruptedException {
        //System.out.println("[DBG] testAdd_48rems:");
        long start = System.currentTimeMillis();
        int actual = request.Add("", count_reminders_by_default);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual);
    }

    @Test
    public void testAdd_48rems_with_empty_macaddress() throws IOException, InterruptedException {
        //System.out.println("[DBG] testAdd_48rems:");
        long start = System.currentTimeMillis();
        int actual = request.Add("", count_reminders_by_default);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, " + "return code: " + actual);
        assertEquals(expected400, actual);
    }

    @Test
    public void testDelete_48rems() throws IOException, InterruptedException {
        //System.out.println("[DBG] testDelete_48rems:");
        long start = System.currentTimeMillis();
        int actual = request.Delete(macaddress, 48);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, " + "return code: " + actual);
        assertEquals(expected200, actual);
    }

    @Test
    public void testDelete_288rems() throws IOException, InterruptedException {
        //System.out.println("[DBG] testDelete_288rems:");
        long start = System.currentTimeMillis();
        int actual = request.Delete(macaddress, 288);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual);
    }

    @Test
    public void testDelete_720rems() throws IOException, InterruptedException {
        //System.out.println("[DBG] testDelete_720rems:");
        long start = System.currentTimeMillis();
        int actual = request.Delete(macaddress, 720);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual);
    }

    @Test
    public void testDelete_48rems_without_macaddress() throws IOException, InterruptedException {
        //System.out.println("[DBG] testDelete_48rems:");
        long start = System.currentTimeMillis();
        int actual = request.Delete("", 48);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected400, actual);
    }

    @Test
    public void testModify_reminderChannelNumber() throws IOException {
        Middle_new request2 = new Middle_new();
        //"reminderChannelNumber": <new value for the DCN the reminder is set to>,
        //"reminderProgramStart": "<new value for the date/time of program the reminder is set to>",
        //"reminderProgramId": "<new value for the TMS Program ID the reminder is set to>",
        //"reminderOffset": <new value for the number of minutes before the program start the reminder should be shown>
        //"reminderScheduleId": "<series or Individual program reminder schedule reference ID>",
        //"reminderId": "<episode or Individual program reminder reference ID of a particular schedule>",
        System.out.println("[DBG] testModify_reminderChannelNumber:");
        long start = System.currentTimeMillis();
        int actual = request2.Modify(macaddress, 48, reminderChannelNumber_by_default, reminderProgramStart_by_default, reminderProgramId_by_default, reminderOffset_by_default, reminderScheduleId_by_default, reminderId_by_default);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual);
    }

    @Test
    public void testModify_reminderProgramId() throws IOException {
        Middle_new request2 = new Middle_new();
        System.out.println("[DBG] testModify_reminderProgramId:");
        long start = System.currentTimeMillis();
        int actual = request2.Modify(macaddress, 48, reminderChannelNumber_by_default, reminderProgramStart_by_default, reminderProgramId_by_default, reminderOffset_by_default, reminderScheduleId_by_default, reminderId_by_default);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual);
    }

    @Test
    public void testModify_reminderProgramStart() throws IOException {
        Middle_new request2 = new Middle_new();
        System.out.println("[DBG] testModify_reminderProgramStart:");
        long start = System.currentTimeMillis();
        int actual = request2.Modify(macaddress, 48, reminderChannelNumber_by_default, reminderProgramStart_by_default, reminderProgramId_by_default, reminderOffset_by_default, reminderScheduleId_by_default, reminderId_by_default);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual);
    }

    @Test
    //@After
    public void testPurge_REM_ST_01_Box_is_not_registered() throws IOException {
        System.out.println("[DBG] testPurge:");
        long start = System.currentTimeMillis();
        ArrayList actual = request.Purge(macaddress);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected500, actual.get(0));
        assertEquals("REM-ST-001 Box is not registered", "REM-ST-001 Box is not registered", actual.get(1));
    }

    @Test
    //@After
    public void testPurge() throws IOException {
        System.out.println("[DBG] testPurge:");
        long start = System.currentTimeMillis();
        ArrayList actual = request.Purge(macaddress);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish-start) + "ms test, return code: " + actual);
        assertEquals(expected200, actual.get(0));
        assertEquals("SUCCESS", "SUCCESS", actual.get(1));
    }
}
