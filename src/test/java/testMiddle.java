import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class testMiddle {
    private int expected = 200;
    //kir
    //private String macaddress_by_default = "6CB56BBA882C";

    //tanya
    private String macaddress_by_default = "000005FE680A";

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

    private String charterapi_ = "http://spec.partnerapi.engprod-charter.net/api/pub/networksettingsmiddle/ns/settings";
    private String charterapi_b = "http://specb.partnerapi.engprod-charter.net/api/pub/networksettingsmiddle/ns/settings";
    private String charterapi_c = "http://specc.partnerapi.engprod-charter.net/api/pub/networksettingsmiddle/ns/settings";
    private String charterapi_d = "http://specd.partnerapi.engprod-charter.net/api/pub/networksettingsmiddle/ns/settings";
    private String charterapi = charterapi_b;

    private String ams_ip = "172.30.81.4";

    private Middle request = new Middle();

    @Test
    public void testChange_registration() throws IOException, InterruptedException {
        System.out.println("[DBG] testChange_registration_to_valid_ams:");
        int actual = request.Change_registration(macaddress_by_default, charterapi, ams_ip);
        System.out.println("[DBG] return code: " + actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testChange_registration_via_charterapi_() throws IOException, InterruptedException {
        System.out.println("[DBG] testChange_registration_via_charterapi_:");
        int actual = request.Change_registration(macaddress_by_default, charterapi_, ams_ip);
        System.out.println("[DBG] return code: " + actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testChange_registration_via_charterapi_b() throws IOException, InterruptedException {
        System.out.println("[DBG] testChange_registration_via_charterapi_b:");
        int actual = request.Change_registration(macaddress_by_default, charterapi_b, ams_ip);
        System.out.println("[DBG] return code: " + actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testChange_registration_via_charterapi_c() throws IOException, InterruptedException {
        System.out.println("[DBG] testChange_registration_via_charterapi_c:");
        int actual = request.Change_registration(macaddress_by_default, charterapi_c, ams_ip);
        System.out.println("[DBG] return code: " + actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testChange_registration_via_charterapi_d() throws IOException, InterruptedException {
        System.out.println("[DBG] testChange_registration_via_charterapi_d:");
        int actual = request.Change_registration(macaddress_by_default, charterapi_d, ams_ip);
        System.out.println("[DBG] return code: " + actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testChange_registration_to_invalid_ams127_0_0_1() throws IOException, InterruptedException {
        System.out.println("[DBG] testChange_registration_to_invalid_ams127_0_0_1:");
        int actual = request.Change_registration(macaddress_by_default, charterapi, "127.0.0.1");
        System.out.println("[DBG] return code: " + actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testCheck_registration_charterapi() throws IOException, InterruptedException {
        System.out.println("[DBG] testCheck_registration:");
        int actual = request.Check_registration(macaddress_by_default,charterapi);
        System.out.println("[DBG] return code: " + actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testCheck_registration_via_charterapi_() throws IOException, InterruptedException {
        System.out.println("[DBG] testCheck_registration_via_charterapi_:");
        int actual = request.Check_registration(macaddress_by_default,charterapi_);
        System.out.println("[DBG] return code: " + actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testCheck_registration_via_charterapi_b() throws IOException, InterruptedException {
        System.out.println("[DBG] testCheck_registration_via_charterapi_b:");
        int actual = request.Check_registration(macaddress_by_default,charterapi_b);
        System.out.println("[DBG] return code: " + actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testCheck_registration_via_charterapi_c() throws IOException, InterruptedException {
        System.out.println("[DBG] testCheck_registration_via_charterapi_c:");
        int actual = request.Check_registration(macaddress_by_default,charterapi_c);
        System.out.println("[DBG] return code: " + actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testCheck_registration_via_charterapi_d() throws IOException, InterruptedException {
        System.out.println("[DBG] testCheck_registration_via_charterapi_d:");
        int actual = request.Check_registration(macaddress_by_default,charterapi_d);
        System.out.println("[DBG] return code: " + actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testAdd_48rems() throws IOException, InterruptedException {
        System.out.println("[DBG] testAdd_48rems:");
        int actual = request.Add(macaddress_by_default, 48, reminderOffset_by_default);
        System.out.println("[DBG] return code: " + actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testAdd_288rems() throws IOException, InterruptedException {
        System.out.println("[DBG] testAdd_288rems:");
        int actual = request.Add(macaddress_by_default, 288, reminderOffset_by_default);
        System.out.println("[DBG] return code: " + actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testAdd_720rems() throws IOException, InterruptedException {
        System.out.println("[DBG] testAdd_720rems:");
        int actual = request.Add(macaddress_by_default, 720, reminderOffset_by_default);
        System.out.println("[DBG] return code: " + actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testDelete_48rems() throws IOException, InterruptedException {
        System.out.println("[DBG] testDelete_48rems:");
        int actual = request.Delete(macaddress_by_default, 48, reminderOffset_by_default);
        System.out.println("[DBG] return code: " + actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testDelete_288rems() throws IOException, InterruptedException {
        System.out.println("[DBG] testDelete_288rems:");
        int actual = request.Delete(macaddress_by_default, 288, reminderOffset_by_default);
        System.out.println("[DBG] return code: " + actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testDelete_720rems() throws IOException, InterruptedException {
        System.out.println("[DBG] testDelete_720rems:");
        int actual = request.Delete(macaddress_by_default, 720, reminderOffset_by_default);
        System.out.println("[DBG] return code: " + actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testModify_reminderChannelNumber() throws IOException {
        //"reminderChannelNumber": <new value for the DCN the reminder is set to>,
        //"reminderProgramStart": "<new value for the date/time of program the reminder is set to>",
        //"reminderProgramId": "<new value for the TMS Program ID the reminder is set to>",
        //"reminderOffset": <new value for the number of minutes before the program start the reminder should be shown>
        //"reminderScheduleId": "<series or Individual program reminder schedule reference ID>",
        //"reminderId": "<episode or Individual program reminder reference ID of a particular schedule>",
        System.out.println("[DBG] testModify_reminderChannelNumber:");
        int actual = request.Modify(macaddress_by_default, 48, reminderChannelNumber_by_default, reminderProgramStart_by_default, reminderProgramId_by_default, reminderOffset_by_default, reminderScheduleId_by_default, reminderId_by_default);
        System.out.println("[DBG] return code: " + actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testModify_reminderProgramId() throws IOException {
        System.out.println("[DBG] testModify_reminderProgramId:");
        int actual = request.Modify(macaddress_by_default, 48, reminderChannelNumber_by_default, reminderProgramStart_by_default, reminderProgramId_by_default, reminderOffset_by_default, reminderScheduleId_by_default, reminderId_by_default);
        System.out.println("[DBG] return code: " + actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testModify_reminderProgramStart() throws IOException {
        System.out.println("[DBG] testModify_reminderProgramStart:");
        int actual = request.Modify(macaddress_by_default, 48, reminderChannelNumber_by_default, reminderProgramStart_by_default, reminderProgramId_by_default, reminderOffset_by_default, reminderScheduleId_by_default, reminderId_by_default);
        System.out.println("[DBG] return code: " + actual);
        assertEquals(expected, actual);
    }

    @Test
    //@After
    public void testPurge() throws IOException {
        System.out.println("[DBG] testPurge:");
        int actual = request.Purge(macaddress_by_default);
        System.out.println("[DBG] return code: " + actual);
        assertEquals(expected, actual);
    }
}
