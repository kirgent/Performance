import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Rule;
import org.junit.rules.Timeout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

import static java.lang.System.currentTimeMillis;

/**
 * we are as Middle: send requests to AMS and got responses
 * Middle -> AMS -> box -> AMS -> Middle
 */
public class API {

    @Rule
    final public Timeout globalTimeout = Timeout.seconds(20);

    static API api = new API();

    private final static Logger log = Logger.getLogger(API.class.getName());

    enum Operation { add, modify, delete, purge, blablabla, blablablablablablablablablablablablablablabla }

    //static Logger log = Logger.getLogger(testAMS.class.getName());
    //FileHandler txtFile = new FileHandler ("log.log", true);
    //private FileHandler fh = new FileHandler("test_reminder.log");

    //public void testMiddle_Request(){
    //curl -vk -X POST -H "Content-Type: application/json"
    //String charterapiX = "http://specd.partnerapi.engprod-charter.net/api/pub/remindersmiddle/v1/reminders" +
    //"?deviceId=000007444C77&lineupId=CA11-1"
    //-d '{"reminderType":"Individual","deliveryId":"49767-MV000209150000-1488390180000","channelId":"49767","programId":"MV000209150000","channelNumber":662,"startTime":1488390180000,"reminderPresetTime":0}'

    final String charterapi_ = "http://spec.partnerapi.engprod-charter.net/api/pub/networksettingsmiddle/ns/settings";
    final String charterapi_b = "http://specb.partnerapi.engprod-charter.net/api/pub/networksettingsmiddle/ns/settings";
    final String charterapi_c = "http://specc.partnerapi.engprod-charter.net/api/pub/networksettingsmiddle/ns/settings";
    final String charterapi_d = "http://specd.partnerapi.engprod-charter.net/api/pub/networksettingsmiddle/ns/settings";
    final String charterapi = charterapi_b;

    final int expected200 = 200;
    final String expected200t = "OK";

    final int expected400 = 400;
    final String expected400t = "Bad Request";

    final int expected404 = 404;
    final String expected404t = "Not Found";

    final int expected405 = 405;
    final String expected405t = "Method Not Allowed";

    final int expected500 = 500;
    final String expected500t = "Internal Server Error";

    final int expected504 = 504;
    final String expected504t = "Server data timeout";

    final String macaddress_wrong = "123456789012";
    final String boxD101 = "A0722CEEC970"; //WB20 D101 ???
    final String boxD102 = "3438B7EB2E24"; //WB20 D102
    final String boxD103 = "3438B7EB2E28"; //WB20 D103
    final String boxD104 = "3438B7EB2E34"; //WB20 D104
    final String boxD105 = "3438B7EB2E30"; //WB20 D105
    final String boxD106 = "3438B7EB2EC4"; //WB20 D106
    final String box_Kirmoto = "0000007F8214"; //Kirmoto
    final String box_Tanya = "000005FE680A"; //Tanya
    final String box_Vitya = "A0722CEEC934"; //Vitya
    final String box_Katya_V = "0000048D4EB4"; //Katya_V
    String macaddress = boxD102;


    //DATES
    //String reminderProgramStart = "2018-03-24";
    String reminderProgramStart_for_statuscode2 = "2000-01-01";
    //String[] rack_date = {"2018-03-15"};

    //CHANNELS
    int reminderChannelNumber = 6;
    int reminderChannelNumber_empty;
    int reminderChannelNumber_for_statuscode3 = 9999;
    int reminderChannelNumber_for_statuscode4 = 1000;
    /*private Integer[] rack_channel30 = { 2, 3, 4, 5, 6, 7, 8, 9, 12, 13,
            14, 16, 18, 19, 22, 23, 25, 28, 30, 31,
            32, 33, 37, 38, 41, 44, 46, 48, 49, 50 };*/
    Integer[] rack_channel = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11};

    String reminderProgramId = "EP0"; //"reminderProgramId": "EP002960010113"
    //String reminderProgramId = "EP002960010113";
    //String reminderProgramId = "0";

    int reminderOffset = 0;
    int reminderOffset_empty;
    int reminderOffset_wrong = -1;

    int reminderScheduleId = 1;
    int reminderScheduleId_empty;
    int reminderScheduleId_wrong = -1;

    int reminderId = 1;
    int reminderId_empty;
    int reminderId_wrong = -1;

    int count_reminders = 1;

    private static String[] statuscode = {
            "0 - requested operation with the reminder was accomplished successfully. Always returned for \"Reminders Purge\" request (Request ID=3)",
            "1 - empty, TBD",
            "2 - reminder is set for time in the past. Applies to \"Reminders Add\" request (Request ID=0)",
            "3 - reminder is set for unknown channel. \"Reminders Add\" request (Request ID=0)",
            "4 - reminder is unknown. Applies to \"Reminders Delete\" request (Request ID=1) and \"Reminders Modify\" request (Request ID=2)",
            "5 - reminder with provided pair of identifiers (reminderScheduleId and reminderId) is already set \"Reminders Add\" request (Request ID=0)" };

    String ams_ip = "172.30.81.4";
    //String ams_ip = "172.30.82.132";
    //String ams_ip = "172.30.112.19";
    private int ams_port = 8080;

    long finish;
    long start;

    /** Request method for Add/Modify/Delete
     * 6 MANDATORY parameters!
     * @param ams_ip - TBD
     * @param macaddress      - macaddress of the box
     * @param operation       - can be Add / Modify / Delete / Purge
     * @param count_reminders - count of reminders to generate in json {..}
     * @param reminderProgramStart - TBD
     * @param reminderChannelNumber - TBD
     * @param reminderProgramId - TBD
     * @param reminderOffset - TBD
     * @param reminderScheduleId - TBD
     * @param reminderId - TBD
     * @return arrayList
     * @throws IOException -TBD
     */
    ArrayList Request(String ams_ip, String macaddress, Enum<Operation> operation, int count_reminders,
                        String reminderProgramStart, Integer reminderChannelNumber, String reminderProgramId,
                        int reminderOffset, int reminderScheduleId, int reminderId) throws IOException {
        System.out.println(operation + "(newapi=true) for macaddress=" + macaddress + ", ams_ip=" + ams_ip + ", "
                + "count_reminders=" + count_reminders + ", "
                + "reminderProgramStart=" + reminderProgramStart + ", "
                + "reminderChannelNumber=" + reminderChannelNumber + ", "
                + "reminderProgramId=" + reminderProgramId + ", "
                + "reminderOffset=" + reminderOffset + ", "
                + "reminderScheduleId=" + reminderScheduleId + ", "
                + "reminderId=" + reminderId);

        HttpClient client = HttpClients.createDefault();
        HttpPost request = new HttpPost(prepare_url(ams_ip, operation, true));
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        //request.setHeader("Charset", "UTF-8");
        System.out.println("[DBG] Request string: " + request);

        request.setEntity(new StringEntity(generate_json_reminder(macaddress, true, count_reminders, operation,
                reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId)));

        long start = currentTimeMillis();
        HttpResponse response = client.execute(request);
        long finish = currentTimeMillis();
        System.out.print("[DBG] " + (finish - start) + "ms request, ");
        //"[DBG] Response getStatusLine: " + response.getStatusLine());

        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
        StringBuilder body = new StringBuilder();
        for (String line = null; (line = reader.readLine()) != null; ) {
            //body.append(line);
            System.out.println("response body: " + body.append(line).append("\n"));
        }

        ArrayList arrayList = new ArrayList();
        arrayList.add(0, response.getStatusLine().getStatusCode());
        arrayList.add(1, response.getStatusLine().getReasonPhrase());
        arrayList.add(2, check_body_response(body.toString(), macaddress));
        return arrayList;
    }

    /** OLD_API Request method for Add/Delete
     * 3 MANDATORY parameters!
     * @param ams_ip - TBD
     * @param macaddress - TBD
     * @param operation - TBD
     * @param count_reminders - TBD
     * @param reminderProgramStart - TBD
     * @param reminderChannelNumber - TBD
     * @param reminderOffset - TBD
     * @return arraylist
     * @throws IOException - TBD
     */
    ArrayList Request(String ams_ip, String macaddress, Enum<Operation> operation, int count_reminders,
                        String reminderProgramStart, Integer reminderChannelNumber, int reminderOffset) throws IOException {
        System.out.println(operation + " (newapi=false) for macaddress=" + macaddress + ", ams_ip=" + ams_ip + ", "
                + "count_reminders=" + count_reminders + ", "
                + "reminderProgramStart=" + reminderProgramStart + ", "
                + "reminderChannelNumber=" + reminderChannelNumber + ", "
                + "reminderOffset=" + reminderOffset);

        HttpClient client = HttpClients.createDefault();
        HttpPost request = new HttpPost(prepare_url(ams_ip, operation, false));
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        //request.setHeader("Charset", "UTF-8");
        System.out.println("[DBG] Request string: " + request);

        request.setEntity(new StringEntity(generate_json_reminder(macaddress, false, count_reminders, operation, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId)));

        long start = currentTimeMillis();
        HttpResponse response = client.execute(request);
        long finish = currentTimeMillis();
        System.out.print("[DBG] " + (finish - start) + "ms request, ");
        //"[DBG] Response getStatusLine: " + response.getStatusLine());

        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
        StringBuilder body = new StringBuilder();
        for (String line = null; (line = reader.readLine()) != null; ) {
            //body.append(line);
            System.out.println("response body: " + body.append(line).append("\n"));
        }

        ArrayList arrayList = new ArrayList();
        arrayList.add(0, response.getStatusLine().getStatusCode());
        arrayList.add(1, response.getStatusLine().getReasonPhrase());
        arrayList.add(2, check_body_response(body.toString(), macaddress));
        return arrayList;
    }

    /** Request method for Purge (OLD_API / NEW_API)
     * @param ams_ip - TBD
     * @param macaddress - TBD
     * @param operation - TBD
     * @param newapi - TBD
     * @return arrayList
     * @throws IOException - TBD
     */
    ArrayList Request(String ams_ip, String macaddress, Enum<Operation> operation, Boolean newapi) throws IOException {
        System.out.println(operation + " (newapi=" + newapi + ") for macaddress=" + macaddress + ", ams_ip=" + ams_ip);

        HttpClient client = HttpClients.createDefault();
        HttpPost request = new HttpPost(prepare_url(ams_ip, operation, newapi));
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        //request.setHeader("Charset", "UTF-8");
        System.out.println("[DBG] Request string: " + request);

        request.setEntity(new StringEntity(generate_json_reminder_purge(macaddress, newapi)));

        long start = currentTimeMillis();
        HttpResponse response = client.execute(request);
        long finish = currentTimeMillis();
        System.out.print("[DBG] " + (finish - start) + "ms request, ");
        //"[DBG] Response getStatusLine: " + response.getStatusLine());

        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
        StringBuilder body = new StringBuilder();
        for (String line = null; (line = reader.readLine()) != null; ) {
            //body.append(line);
            System.out.println("Response body: " + body.append(line).append("\n"));
        }

        ArrayList arrayList = new ArrayList();
        arrayList.add(0, response.getStatusLine().getStatusCode());
        arrayList.add(1, response.getStatusLine().getReasonPhrase());
        arrayList.add(2, check_body_response(body.toString(), macaddress));
        return arrayList;
    }


    /** 2nd Request method for Add/Modify/Delete
     * RACK : String[] rack_date
     * RACK : Integer[] rack_channel
     * @param ams_ip - TBD
     * @param macaddress - macaddress of the box
     * @param operation - can be Add / Modify / Delete
     * @param count_reminders - count of reminders to generate in json {..}
     * @param rack_date - TBD
     * @param rack_channel - TBD
     * @param reminderProgramId - TBD
     * @param reminderOffset - TBD
     * @param reminderScheduleId - TBD
     * @param reminderId - TBD
     * @return arrayList
     * @throws IOException - TBD
     */
    @Deprecated
    ArrayList Request(String ams_ip, String macaddress, Enum<Operation> operation, int count_reminders,
                        String[] rack_date, Integer[] rack_channel, String reminderProgramId,
                        int reminderOffset, int reminderScheduleId, int reminderId) throws IOException {
        if (Objects.equals(operation, "Purge")) {
            System.out.println(operation + " (newapi=true) for macaddress=" + macaddress + ", ams_ip=" + ams_ip);
        } else {
            System.out.println(operation + " (newapi=true) for macaddress=" + macaddress + ", ams_ip=" + ams_ip + ", "
                    + "count_reminders=" + count_reminders + ", "
                    + "reminderOffset=" + reminderOffset + ", "
                    //+ "rack_data.length=" + rack_date.length + ", "
                    + "data=" + Arrays.asList(rack_date) + ", "
                    //+ "rack_channel.length=" + rack_channel.length + ", "
                    + "channel=" + Arrays.asList(rack_channel));
        }

        HttpClient client = HttpClients.createDefault();
        HttpPost request = new HttpPost(prepare_url(ams_ip, operation, true));
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        System.out.println("[DBG] Request string: " + request);
        //+ "\n[DBG] Request entity: " + request.getEntity());

        ArrayList arrayList = new ArrayList();
        if (Objects.equals(operation, "Purge")) {
            request.setEntity(new StringEntity(generate_json_reminder_purge(macaddress, true)));

            long start = currentTimeMillis();
            HttpResponse response = client.execute(request);
            long finish = currentTimeMillis();
            System.out.println("[DBG] " + (finish - start) + "ms request");
            //"[DBG] Response getStatusLine: " + response.getStatusLine());

            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
            StringBuilder body = new StringBuilder();
            for (String line = null; (line = reader.readLine()) != null; ) {
                body.append(line);
                //System.out.println("[DBG] Response body: " + body.append(line).append("\n"));
            }

            arrayList.add(0, response.getStatusLine().getStatusCode());
            arrayList.add(1, response.getStatusLine().getReasonPhrase());
            arrayList.add(2, check_body_response(body.toString(), macaddress));
        } else {
            for (String aRack_date : rack_date) {
                for (int aRack_channel : rack_channel) {
                    System.out.println("operation= " + operation + ", date=" + aRack_date + ", channel=" + aRack_channel);

                    request.setEntity(new StringEntity(generate_json_reminder(macaddress, true, count_reminders, operation,
                            aRack_date, aRack_channel,
                            reminderProgramId, reminderOffset, reminderScheduleId, reminderId)));

                    long start = currentTimeMillis();
                    HttpResponse response = client.execute(request);
                    long finish = currentTimeMillis();
                    System.out.println("[DBG] " + (finish - start) + "ms request, " +
                            "Response getStatusLine: " + response.getStatusLine());

                    BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
                    StringBuilder body = new StringBuilder();
                    for (String line = null; (line = reader.readLine()) != null; ) {
                        //body.append(line);
                        System.out.println("[DBG] Response body: " + body.append(line).append("\n"));
                    }

                    arrayList.add(0, response.getStatusLine().getStatusCode());
                    arrayList.add(1, response.getStatusLine().getReasonPhrase());
                    arrayList.add(2, check_body_response(body.toString(), macaddress));

                    if (arrayList.get(0).equals(200)) {
                        break;
                    }
                }
                if (arrayList.get(0).equals(200)) {
                    break;
                }
            }
        }
        return arrayList;
    }

    ArrayList Change_settings(String ams_ip, String macaddress, String option, String value) throws IOException {
        System.out.println("Change settings for macaddress=" + macaddress + ", ams_ip=" + ams_ip + " option=" + option + ", value=" + value);

        HttpClient client = HttpClients.createDefault();
        HttpPost request = new HttpPost("http://" + ams_ip + ":" + ams_port + "/ams/settings");

        ArrayList arrayList = new ArrayList();
        //request.setEntity(new StringEntity(generate_json_change_registration(macaddress, ams_ip, "Change_settings")));
        request.setEntity(new StringEntity(generate_json_setting(macaddress, option, value)));

        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        System.out.println("[DBG] Request string: " + request);
        //+ "\n[DBG] Request entity: " + request.getEntity());

        long start = currentTimeMillis();
        HttpResponse response = client.execute(request);
        long finish = currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms request");

        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
        StringBuilder body = new StringBuilder();
        for (String line = null; (line = reader.readLine()) != null; ) {
            //body.append(line);
            System.out.println("[DBG] Response body: " + body.append(line).append("\n"));
        }

        arrayList.add(0, response.getStatusLine().getStatusCode());
        arrayList.add(1, response.getStatusLine().getReasonPhrase());
        arrayList.add(2, check_body_response(body.toString(), macaddress));
        return arrayList;
    }

    ArrayList Check_registration(String macaddress, String charterapi) throws IOException {
        System.out.println("Check_registration "+ macaddress +" via charterapi: " + charterapi);

        HttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(charterapi + "/amsIp/" + macaddress);

        //request.setHeader("Accept", "*/*");
        //request.setHeader("Content-type", "application/json");
        //request.setHeader("Content-type", "text/plain");
        //request.setHeader("Charset", "UTF-8");
        System.out.println("[DBG] Request string: " + request);
        //+"\n[DBG] Request getRequestLine: "+request.getRequestLine());

        long start = currentTimeMillis();
        HttpResponse response = client.execute(request);
        long finish = currentTimeMillis();
        System.out.print("[DBG] " + (finish - start) + "ms request, ");

        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
        StringBuilder body = new StringBuilder();
        for (String line; (line = reader.readLine()) != null; ) {
            System.out.println("response body: " + body.append(line).append("\n"));
        }

        ArrayList arrayList = new ArrayList();
        arrayList.add(0, response.getStatusLine().getStatusCode());
        arrayList.add(1, response.getStatusLine().getReasonPhrase());
        arrayList.add(2, check_body_response(body.toString(), macaddress));
        return arrayList;
    }

    ArrayList Change_registration(String macaddress, String charterapi, String ams_ip) throws IOException {
        System.out.println("Change_registration "+ macaddress +" to ams " + ams_ip + " via charterapi: " + charterapi);
        //log.info("Change_registration "+ macaddress +" to ams " + ams_ip + " via charterapi: " + charterapi);

        HttpClient client = HttpClients.createDefault();
        HttpPost request = new HttpPost(charterapi + "?requestor=AMS");

        request.setEntity(new StringEntity(generate_json_change_registration(macaddress, ams_ip, "Change_registration")));
        request.setHeader("Content-type", "application/json");
        request.setHeader("Accept", "application/json");
        System.out.println("[DBG] Request string: " + request);
        //+ "\n[DBG] Request json string: " + json_change_registration);

        long start = currentTimeMillis();
        HttpResponse response = client.execute(request);
        long finish = currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms request, " +
                "Response getStatusLine: " + response.getStatusLine());

        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
        StringBuilder body = new StringBuilder();
        for (String line = null; (line = reader.readLine()) != null; ) {
            body.append(line);
            //System.out.println("[DBG] Response body: " + body.append(line).append("\n"));
        }

        ArrayList arrayList = new ArrayList();
        arrayList.add(0, response.getStatusLine().getStatusCode());
        arrayList.add(1, response.getStatusLine().getReasonPhrase());
        arrayList.add(2, check_body_response(body.toString(), macaddress));
        return arrayList;
    }

    private String generate_json_reminder(String macaddress, Boolean newapi, int count_reminders, Enum<Operation> enum_operation,
                                          String reminderProgramStart, int reminderChannelNumber,
                                          String reminderProgramId, int reminderOffset, int reminderScheduleId, int reminderId) {

        //if(count_reminders <= 0){ count_reminders = 1; }

        if(count_reminders > 1440){ count_reminders = 1440; }

        String operation = "";
        if (!newapi) {
            switch (enum_operation.name()) {
                case "add":
                    operation = "Add"; break;
                case "delete":
                    operation = "Delete"; break;
                case "purge":
                    operation = "Purge"; break;
                case "blablabla":
                    operation = "blablabla"; break;
                default:
                    break;
            }
        }

        JSONObject resultJson = new JSONObject();
        resultJson.put("deviceId", macaddress);
        JSONArray array_reminders = new JSONArray();
        resultJson.put("reminders", array_reminders);
        for (int i = 1; i <= count_reminders; i++) {
            JSONObject object_in_reminders = new JSONObject();
            if(!newapi){
                object_in_reminders.put("operation", operation);
            }
            object_in_reminders.put("reminderProgramStart", reminderProgramStart + " " + get_time(count_reminders, i));
            object_in_reminders.put("reminderChannelNumber", reminderChannelNumber);
            object_in_reminders.put("reminderOffset", reminderOffset);
            if(newapi) {
                object_in_reminders.put("reminderProgramId", reminderProgramId);
                object_in_reminders.put("reminderScheduleId", reminderScheduleId);
                object_in_reminders.put("reminderId", reminderId);
            }
            array_reminders.add(object_in_reminders);
        }
        String result = resultJson.toJSONString();
        System.out.println("generated json: " + result);
        return result;
    }

    private String generate_json_reminder_purge(String macaddress, Boolean newapi) {
        //String json = "{\"deviceId\":" + macaddress + ",\"reminders\":[]}";
        JSONObject resultJson = new JSONObject();
        resultJson.put("deviceId", macaddress);
        JSONArray array_reminders = new JSONArray();
        resultJson.put("reminders", array_reminders);

        if (!newapi){
            //String json_purge = "{\"deviceId\":" + macaddress + ",\"reminders\":[{\"operation\":\"Purge\"}]}";
            JSONObject object_in_reminders = new JSONObject();
            object_in_reminders.put("operation", "Purge");
            array_reminders.add(object_in_reminders);
        }

        System.out.println("generated json: " + resultJson.toJSONString());
        return resultJson.toJSONString();
    }

    private String generate_json_change_registration(String macaddress, String ams_ip, String action) {
        //String json = "{\"setting\":{\"groups\":[{\"options\":[],\"id\":\"STBmacaddress\",\"type\":\"device-stb\",\"amsid\":\"" + ams_ip + "\"}]}}";
        JSONObject resultJson = new JSONObject();
        JSONObject object_in_settings = new JSONObject();
        JSONArray array_groups = new JSONArray();

        resultJson.put("settings", object_in_settings);
        object_in_settings.put("groups", array_groups);

        JSONObject object_in_groups = new JSONObject();
        array_groups.add(object_in_groups);
        object_in_groups.put("id", "STB" + macaddress);
        object_in_groups.put("type", "device-stb");
        object_in_groups.put("amsid", ams_ip);
        JSONArray array_options = new JSONArray();
        object_in_groups.put("options", array_options);

        System.out.println("generated json: " + resultJson.toJSONString());
        return resultJson.toJSONString();
    }

    private String generate_json_setting(String macaddress, String option, String value) {
        //String json = "{\"settings\":{\"groups\":[{\"id\":\"STBmacaddress\",\"type\":\"device-stb\",\"options\":[{\"name\":\"Audio Output\",\"value\":\"HDMI\"}]}]}}";
        JSONObject resultJson = new JSONObject();
        JSONObject object_in_settings = new JSONObject();
        JSONArray array_groups = new JSONArray();


        resultJson.put("settings", object_in_settings);
        object_in_settings.put("groups", array_groups);

        JSONObject object_in_groups = new JSONObject();
        array_groups.add(object_in_groups);
        object_in_groups.put("id", "STB" + macaddress);
        object_in_groups.put("type", "device-stb");
        JSONArray array_options = new JSONArray();
        object_in_groups.put("options", array_options);

        JSONObject object_in_options = new JSONObject();
        array_options.add(object_in_options);
        object_in_options.put("name", option);
        object_in_options.put("value", value);

        System.out.println("generated json: " + resultJson.toJSONString());
        return resultJson.toJSONString();
    }

    @Deprecated
    String generate_json_test(String date, int count_remindres, String operation, int reminderOffset) {
        System.out.println("[DBG] [date] Generate_json: with date=" + date + ", " +
                "count_reminders=" + count_remindres + ", " +
                "operation=" + operation + ", " +
                "reminderOffset=" + reminderOffset);

         /*
        //WORKING parsing from json_string to Class:
        Gson g = new Gson();
        Reminder reminder = g.fromJson(json_add2, Reminder.class);
        System.out.println("[DBG] parsing from json_string to Class: \nmacaddress: " + reminder.deviceId);
        System.out.println("[DBG] count of reminders in class: " + reminder.reminders.size());
        for(Reminders rems : reminder.reminders){System.out.println(
                    "operation: " + rems.operation + ", " +
                    "reminderChannelNumber: " + rems.reminderChannelNumber + ", " +
                    "reminderProgramStart: " + rems.reminderProgramStart + ", " +
                    "reminderProgramId: " + rems.reminderProgramId + ", " +
                    "reminderOffset: " + rems.reminderOffset);
            }

        //parsing from Class to json_string
        System.out.println("[DBG] parsing from Class to json_string: \n" + g.toJson(reminder));
*/


/*        //WORKING variant for one class Reminder + one class Reminders
        //==============================================================
        final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

        //from class -> to string json
        //class with fields:
        Reminders rs = new Reminders(operation, reminderChannelNumber, reminderProgramStart, reminderProgramId, reminderOffset);
        Reminder r = new Reminder(macaddress, rs);
        //create json structure:
        String json = GSON.toJson(r);
        System.out.println("[DBG] from class -> to string json:\n" + json);

        //from json string -> to class
        Reminder to_class = GSON.fromJson(json, Reminder.class);
        System.out.println("[DBG] from json string -> to class:\n" + to_class.getDeviceId()+ " " + to_class.getClass());
*/


/*
        //from class -> to string json
        Reminder from_class = new Reminder(macaddress, Arrays.asList("operation", "reminderChannelNumber", "reminderProgramStart", "reminderProgramId", "reminderOffset"));
        String json = GSON.toJson(from_class);
        System.out.println("[DBG] from class -> to string json:\n" + json);

        //from json string -> to class
        Reminder to_class = GSON.fromJson(json, Reminder.class);
        System.out.println("[DBG] from json string -> to class:\n" + to_class.getDeviceId()+ " " + to_class.getReminders_list());
*/

/*
        //WORKING
        JsonObject jo = new JsonParser().parse(json_add2).getAsJsonObject();
        System.out.println("1 show full jsonobject: " + jo);
        show only jsonarray:
        String ja = jo.get("reminders").getAsJsonArray().toString();
        System.out.println("2 only jsonarray: " + ja);
*/
    return "";
    }

    private String check_body_response(String body, String macaddress) {
        String result = "";
        if(body.contains("\"statusCode\":1")){
            //log.warning("one or more statusCode's = " + statuscode[1]);
            System.out.println("! one or more statusCode's = " + statuscode[1]);
            result += "1";
        }
        if(body.contains("\"statusCode\":2")){
            //log.warning("one or more statusCode's = " + statuscode[2]);
            System.out.println("! one or more statusCode's = " + statuscode[2]);
            result += "2";
        }
        if(body.contains("\"statusCode\":3")){
            //log.warning("one or more statusCode's = " + statuscode[3]);
            System.out.println("! one or more statusCode's = " + statuscode[3]);
            result += "3";
        }
        if(body.contains("\"statusCode\":4")){
            //log.warning("one or more statusCode's = " + statuscode[4]);
            System.out.println("! one or more statusCode's = " + statuscode[4]);
            result += "4";
        }
        if(body.contains("\"statusCode\":5")){
            //log.warning("one or more statusCode's = " + statuscode[5]);
            System.out.println("! one or more statusCode's = " + statuscode[5]);
            result += "5";
        }
        if(body.contains("\"status\":\"Failed\"") && body.contains("\"errorMessage\":\"REM-ST-001 Box is not registered\"")){
            result += "REM-ST-001 Box is not registered";
        }
        if(body.contains("\"status\":\"Failed\"") && body.contains("\"errorMessage\":\"REM-002 Reminders Service error: REM-112\"")){
            result += "REM-002 Reminders Service error: REM-012 [" + macaddress + "] Request not accomplished";
        }
        if(body.contains("\"status\":\"Failed\"") && body.contains("\"errorMessage\":\"REM-002 Reminders Service error: Timeout detected by BoxResponseTracker\"")){
            result += "REM-002 Reminders Service error: Timeout detected by BoxResponseTracker";
        }
        if(body.contains("\"status\":\"Failed\"") && body.contains("\"errorMessage\":\"Incorrect request")){
            result += "Incorrect request";
        }
        if(body.contains("REM-002 Reminders Service error: Can not connect to STB with stbId=" + macaddress)){
            result += "REM-002 Reminders Service error: Can not connect to STB with stbId=" + macaddress;
        }
        if(body.contains("REM-008 Reminders parsing error: wrong deviceId")){
            result += "REM-008 Reminders parsing error: wrong deviceId";
        }
        if(body.contains("REM-008 Reminders parsing error: wrong operation")){
            result += "REM-008 Reminders parsing error: wrong operation";
        }
        if(body.contains("REM-008 Reminders parsing error: incorrect message format")){
            result += "REM-008 Reminders parsing error: incorrect message format";
        }
        if(body.contains("Failed to getAmsIpByMacAddress for : " + macaddress + ", with error: No amsIp found for macAddress: STB" +macaddress)){
            result += "No amsIp found for macAddress";
        }
        if(body.contains("STB MAC not found: " + macaddress)){
            result += "STB MAC not found: " + macaddress;
        }
        if(body.contains("incorrect value")){
            result += "incorrect value";
        }
        if(body.contains("SET-025 Unsupported data type: Not a JSON Object:")){
            result += "SET-025 Unsupported data type: Not a JSON Object";
        }

        //System.out.println("[DBG] check_body_for_statuscode: result: " + result);
        return result;
    }

    /*@Deprecated
    ArrayList Purge(String ams_ip, String macaddress) throws IOException {
        System.out.println("Purge for ams_ip=" + ams_ip + " and macaddress=" + macaddress);
        //log.info("Purge for ams_ip=" + ams_ip + " and macaddress=" + macaddress);

        String url = "http://" + ams_ip + ":" + ams_port + postfix_change;
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost request = new HttpPost(url);

        String json_purge = "{\"deviceId\":" + macaddress + ",\"reminders\":[{\"operation\":\"Purge\"}]}";
        request.setEntity(new StringEntity(json_purge));
        //request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");

        System.out.println("[DBG] Request string: " + request);
        //+ "\n[DBG] Request entity: " + request.getEntity());

        long start = currentTimeMillis();
        HttpResponse response = client.execute(request);
        long finish = currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms request, " +
                "Response getStatusLine: " + response.getStatusLine());

        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
        StringBuilder body = new StringBuilder();
        for (String line = null; (line = reader.readLine()) != null; ) {
            body.append(line);
            //System.out.println("[DBG] Response body: " + body.append(line).append("\n"));
        }

   /*     // Считываем json
        Object obj = new JSONParser().parse(json_purge); // Object obj = new JSONParser().parse(new FileReader("JSONExample.json"));
        // Кастим obj в JSONObject
        JSONObject jo = (JSONObject) obj;
        // Достаём firstName and lastName
        String firstName = (String) jo.get("firstName");
        String lastName = (String) jo.get("lastName");
        System.out.println("fio: " + firstName + " " + lastName);
        // Достаем массив номеров
        JSONArray phoneNumbersArr = (JSONArray) jo.get("phoneNumbers");
        Iterator phonesItr = phoneNumbersArr.iterator();
        System.out.println("phoneNumbers:");
        // Выводим в цикле данные массива
        while (phonesItr.hasNext()) {
            JSONObject test = (JSONObject) phonesItr.next();
            System.out.println("- type: " + test.get("type") + ", phone: " + test.get("number"));
        }*/

   /*     ArrayList arrayList = new ArrayList();
        arrayList.add(0, response.getStatusLine().getStatusCode());
        arrayList.add(1, response.getStatusLine().getReasonPhrase());
        arrayList.add(2, check_body_for_statuscode(body.toString()));
        client.close();
        return arrayList;
    }*/

    private String prepare_url(String ams_ip, Enum<Operation> operation, Boolean newapi) {
        String url;
        if (newapi) {
            url = "http://" + ams_ip + ":" + ams_port + "/ams/Reminders?req=" + operation;
        } else {
            url = "http://" + ams_ip + ":" + ams_port + "/ams/Reminders?req=ChangeReminders";
        }
        return url;
    }


    ArrayList QueryDB(String ams_ip, String macaddress) throws ClassNotFoundException, SQLException {
        //ResultSet QueryDB(String macaddress) throws ClassNotFoundException, SQLException {
        System.out.println("QueryDB for macaddress=" + macaddress + " to DB AMS=" + ams_ip);

        String url = "jdbc:oracle:thin:@//ams-db01.enwd.co.sa.charterlab.com:1521/zdev02";
        String username = "ams_ipv6_e591";
        String password = "ams_ipv6_e591";

        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection connection = DriverManager.getConnection(url, username, password);

        Statement statement = connection.createStatement();

        ResultSet result = statement.executeQuery("select * from MAC_IP where MAC_STR = '" + macaddress + "\'");
        ArrayList arrayresult = new ArrayList();
        while (result.next()) {
            arrayresult.add(result.getLong(1));
            arrayresult.add(result.getLong(2));
            arrayresult.add(result.getLong(3));
            arrayresult.add(result.getString(4));
            arrayresult.add(result.getString(5));
        }
        connection.close();

        return arrayresult;
    }

    /** generating String with one date(always tomorrow)
     * @return String with one date(always tomorrow)
     */
    String reminderProgramStart() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat pattern = new SimpleDateFormat("yyyy-MM-dd");
        calendar.add(Calendar.DAY_OF_YEAR, +1);
        return pattern.format(calendar.getTime());
    }

    /** generating String with one/several dates
     * @param count
     * @param several
     * @return
     */
    String get_date(int count, Boolean several) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat pattern = new SimpleDateFormat("yyyy-MM-dd");
        StringBuilder result = new StringBuilder();
        if (several) {
            for (int i = 1; i <= count; i++) {
                calendar.add(Calendar.DAY_OF_YEAR, +1);
                result.append(pattern.format(calendar.getTime()));
                if (i != count) {
                    result.append(" ");
                }
            }
        } else {
            calendar.add(Calendar.DAY_OF_YEAR, +count);
            result = result.append(pattern.format(calendar.getTime()));
        }
        System.out.println("generated result: " + result);
        return result.toString();
    }

    @Deprecated
    String get_time(int count_reminders) {
        int interval_in_minutes;
        if (count_reminders<=48){ interval_in_minutes = 30; }
        else if (count_reminders<=288){ interval_in_minutes = 5; }
        else if (count_reminders<=720){ interval_in_minutes = 2; }
        else interval_in_minutes = 1;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat pattern = new SimpleDateFormat("HH:mm");
        calendar.setTime(new java.util.Date(0, 0, 0, 0, 0));

        StringBuilder result = new StringBuilder();
        for (int i=1; i<=count_reminders; i++){
            result.append(pattern.format(calendar.getTime()));
            calendar.add(Calendar.MINUTE, interval_in_minutes);
            if(i!=count_reminders){
                result.append(" ");
            }
        }
        System.out.println("generated times: " + result);
        return result.toString();
    }

    String get_time(int count_reminders, int number) {
        int interval_in_minutes;
        if (count_reminders<=48){ interval_in_minutes = 30; }
        else if (count_reminders<=288){ interval_in_minutes = 5; }
        else if (count_reminders<=720){ interval_in_minutes = 2; }
        else interval_in_minutes = 1;

        if (number < 1) { number = 1; }

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat pattern = new SimpleDateFormat("HH:mm");
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        calendar.setTime(new java.util.Date(0, 0, 0, 0, 0));
        calendar.add(Calendar.MINUTE, interval_in_minutes*(number-1));
        return pattern.format(calendar.getTime());
    }

    void starttime() {
        start = System.currentTimeMillis();
    }

    void finishtime() {
        finish = System.currentTimeMillis();
    }

}