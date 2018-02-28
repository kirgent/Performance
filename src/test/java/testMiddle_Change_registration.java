import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class testMiddle_Change_registration {

    private int expected = 200;
    private String macaddress = "6CB56BBA882C";

    private String charterapi_ = "http://spec.partnerapi.engprod-charter.net/api/pub/networksettingsmiddle/ns/settings";
    private String charterapi_b = "http://specb.partnerapi.engprod-charter.net/api/pub/networksettingsmiddle/ns/settings";
    private String charterapi_c = "http://specc.partnerapi.engprod-charter.net/api/pub/networksettingsmiddle/ns/settings";
    private String charterapi_d = "http://specd.partnerapi.engprod-charter.net/api/pub/networksettingsmiddle/ns/settings";
    private String charterapi = charterapi_b;

    private String ams_ip = "172.30.81.4";

    private Middle request = new Middle();

    @Test
    public void testChange_registration2() throws IOException, InterruptedException {
        System.out.println("[DBG] testChange_registration_to_valid_ams:");
        int actual = request.Change_registration(macaddress, charterapi, ams_ip);
        System.out.println("[DBG] return code: " + actual);
        assertEquals(expected, actual);
    }
}
