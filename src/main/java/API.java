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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Logger;

import static java.lang.System.currentTimeMillis;

public class API {

    @Rule
    public Timeout globalTimeout = Timeout.seconds(20);

    static API api = new API();

    private final static Logger log = Logger.getLogger(API.class.getName());

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

    int expected200 = 200;
    String expected200t = "OK";

    int expected400 = 400;
    String expected400t = "Bad Request";

    int expected404 = 404;
    String expected404t = "Not Found";

    int expected405 = 405;
    String expected405t = "Method Not Allowed";

    int expected500 = 500;
    String expected500t = "Internal Server Error";

    int expected504 = 504;
    String expected504t = "Server data timeout";

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
    String[] macaddress = {boxD101[0], boxD101[1]};

    //DATES
    String reminderProgramStart = "2018-03-22";
    String reminderProgramStart_for_statuscode2 = "2000-01-01 00:00";
    String[] rack_date = {"2018-03-15"};

    //CHANNELS
    int reminderChannelNumber = 2;
    int reminderChannelNumber_empty;
    int reminderChannelNumber_for_statuscode3 = 9999;
    int reminderChannelNumber_for_statuscode4 = 1000;
    /*private Integer[] rack_channel30 = { 2, 3, 4, 5, 6, 7, 8, 9, 12, 13,
            14, 16, 18, 19, 22, 23, 25, 28, 30, 31,
            32, 33, 37, 38, 41, 44, 46, 48, 49, 50 };*/
    Integer[] rack_channel = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11};

    //String reminderProgramId = "EP0"; //"reminderProgramId": "EP002960010113"
    String reminderProgramId = "EP002960010113";
    String reminderProgramId_wrong = "-1";

    int reminderOffset = 0;
    int reminderOffset_empty;
    int reminderScheduleId = 1;
    int reminderScheduleId_empty;
    int reminderId = 1;
    int reminderId_empty;

    //int count_iterations = 3;

    private static String[] statuscode = {
            "0 - requested action with the reminder was accomplished successfully",
            "1 - empty, TBD",
            "2 - reminder is set for time in the past",
            "3 - reminder is set for unknown channel",
            "4 - reminder is unknown, applies to reminder deletion attempts",
            "5 - reminder with provided pair of identifiers (reminderScheduleId and reminderId) is already set (for Add Reminder request)" };

    String ams_ip = "172.30.81.4";
    //String ams_ip = "172.30.82.132";
    //String ams_ip = "172.30.112.19";
    private int ams_port = 8080;


    //String[] rack_time48_ = get_rack_time();
    //TIMES
    final private String[] rack_time48 = {"00:00", "00:30", "01:00", "01:30", "02:00", "02:30", "03:00", "03:30", "04:00", "04:30", "05:00", "05:30",
            "06:00", "06:30", "07:00", "07:30", "08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30",
            "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30",
            "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30", "22:00", "22:30", "23:00", "23:30"};
    final private String[] rack_time288 = { "00:00", "00:05", "00:10", "00:15", "00:20", "00:25", "00:30",  "00:35", "00:40", "00:45", "00:50", "00:55",
            "01:00", "01:05", "01:10", "01:15", "01:20", "01:25", "01:30", "01:35", "01:40", "01:45", "01:50", "01:55",
            "02:00", "02:05", "02:10", "02:15", "02:20", "02:25", "02:30", "02:35", "02:40", "02:45", "02:50", "02:55",
            "03:00", "03:05", "03:10", "03:15", "03:20", "03:25", "03:30", "03:35", "03:40", "03:45", "03:50", "03:55",
            "04:00", "04:05", "04:10", "04:15", "04:20", "04:25", "04:30", "04:35", "04:40", "04:45", "04:50", "04:55",
            "05:00", "05:05", "05:10", "05:15", "05:20", "05:25", "05:30", "05:35", "05:40", "05:45", "05:50", "05:55",
            "06:00", "06:05", "06:10", "06:15", "06:20", "06:25", "06:30", "06:35", "06:40", "06:45", "06:50", "06:55",
            "07:00", "07:05", "07:10", "07:15", "07:20", "07:25", "07:30", "07:35", "07:40", "07:45", "07:50", "07:55",
            "08:00", "08:05", "08:10", "08:15", "08:20", "08:25", "08:30", "08:35", "08:40", "08:45", "08:50", "08:55",
            "09:00", "09:05", "09:10", "09:15", "09:20", "09:25", "09:30", "09:35", "09:40", "09:45", "09:50", "09:55",
            "10:00", "10:05", "10:10", "10:15", "10:20", "10:25", "10:30", "10:35", "10:40", "10:45", "10:50", "10:55",
            "11:00", "11:05", "11:10", "11:15", "11:20", "11:25", "11:30", "11:35", "11:40", "11:45", "11:50", "11:55",
            "12:00", "12:05", "12:10", "12:15", "12:20", "12:25", "12:30", "12:35", "12:40", "12:45", "12:50", "12:55",
            "13:00", "13:05", "13:10", "13:15", "13:20", "13:25", "13:30", "13:35", "13:40", "13:45", "13:50", "13:55",
            "14:00", "14:05", "14:10", "14:15", "14:20", "14:25", "14:30", "14:35", "14:40", "14:45", "14:50", "14:55",
            "15:00", "15:05", "15:10", "15:15", "15:20", "15:25", "15:30", "15:35", "15:40", "15:45", "15:50", "15:55",
            "16:00", "16:05", "16:10", "16:15", "16:20", "16:25", "16:30", "16:35", "16:40", "16:45", "16:50", "16:55",
            "17:00", "17:05", "17:10", "17:15", "17:20", "17:25", "17:30", "17:35", "17:40", "17:45", "17:50", "17:55",
            "18:00", "18:05", "18:10", "18:15", "18:20", "18:25", "18:30", "18:35", "18:40", "18:45", "18:50", "18:55",
            "19:00", "19:05", "19:10", "19:15", "19:20", "19:25", "19:30", "19:35", "19:40", "19:45", "19:50", "19:55",
            "20:00", "20:05", "20:10", "20:15", "20:20", "20:25", "20:30", "20:35", "20:40", "20:45", "20:50", "20:55",
            "21:00", "21:05", "21:10", "21:15", "21:20", "21:25", "21:30", "21:35", "21:40", "21:45", "21:50", "21:55",
            "22:00", "22:05", "22:10", "22:15", "22:20", "22:25", "22:30", "22:35", "22:40", "22:45", "22:50", "22:55",
            "23:00", "23:05", "23:10", "23:15", "23:20", "23:25", "23:30", "23:35", "23:40", "23:45", "23:50", "23:55" };
    final private String[] rack_time720 = { "00:00", "00:02", "00:04", "00:06", "00:08", "00:10", "00:12", "00:14", "00:16", "00:18", "00:20", "00:22", "00:24", "00:26", "00:28", "00:30", "00:32", "00:34", "00:36", "00:38", "00:40", "00:42", "00:44", "00:46", "00:48", "00:50", "00:52", "00:54", "00:56", "00:58",
            "01:00", "01:02", "01:04", "01:06", "01:08", "01:10", "01:12", "01:14", "01:16", "01:18", "01:20", "01:22", "01:24", "01:26", "01:28", "01:30", "01:32", "01:34", "01:36", "01:38", "01:40", "01:42", "01:44", "01:46", "01:48", "01:50", "01:52", "01:54", "01:56", "01:58",
            "02:00", "02:02", "02:04", "02:06", "02:08", "02:10", "02:12", "02:14", "02:16", "02:18", "02:20", "02:22", "02:24", "02:26", "02:28", "02:30", "02:32", "02:34", "02:36", "02:38", "02:40", "02:42", "02:44", "02:46", "02:48", "02:50", "02:52", "02:54", "02:56", "02:58",
            "03:00", "03:02", "03:04", "03:06", "03:08", "03:10", "03:12", "03:14", "03:16", "03:18", "03:20", "03:22", "03:24", "03:26", "03:28", "03:30", "03:32", "03:34", "03:36", "03:38", "03:40", "03:42", "03:44", "03:46", "03:48", "03:50", "03:52", "03:54", "03:56", "03:58",
            "04:00", "04:02", "04:04", "04:06", "04:08", "04:10", "04:12", "04:14", "04:16", "04:18", "04:20", "04:22", "04:24", "04:26", "04:28", "04:30", "04:32", "04:34", "04:36", "04:38", "04:40", "04:42", "04:44", "04:46", "04:48", "04:50", "04:52", "04:54", "04:56", "04:58",
            "05:00", "05:02", "05:04", "05:06", "05:08", "05:10", "05:12", "05:14", "05:16", "05:18", "05:20", "05:22", "05:24", "05:26", "05:28", "05:30", "05:32", "05:34", "05:36", "05:38", "05:40", "05:42", "05:44", "05:46", "05:48", "05:50", "05:52", "05:54", "05:56", "05:58",
            "06:00", "06:02", "06:04", "06:06", "06:08", "06:10", "06:12", "06:14", "06:16", "06:18", "06:20", "06:22", "06:24", "06:26", "06:28", "06:30", "06:32", "06:34", "06:36", "06:38", "06:40", "06:42", "06:44", "06:46", "06:48", "06:50", "06:52", "06:54", "06:56", "06:58",
            "07:00", "07:02", "07:04", "07:06", "07:08", "07:10", "07:12", "07:14", "07:16", "07:18", "07:20", "07:22", "07:24", "07:26", "07:28", "07:30", "07:32", "07:34", "07:36", "07:38", "07:40", "07:42", "07:44", "07:46", "07:48", "07:50", "07:52", "07:54", "07:56", "07:58",
            "08:00", "08:02", "08:04", "08:06", "08:08", "08:10", "08:12", "08:14", "08:16", "08:18", "08:20", "08:22", "08:24", "08:26", "08:28", "08:30", "08:32", "08:34", "08:36", "08:38", "08:40", "08:42", "08:44", "08:46", "08:48", "08:50", "08:52", "08:54", "08:56", "08:58",
            "09:00", "09:02", "09:04", "09:06", "09:08", "09:10", "09:12", "09:14", "09:16", "09:18", "09:20", "09:22", "09:24", "09:26", "09:28", "09:30", "09:32", "09:34", "09:36", "09:38", "09:40", "09:42", "09:44", "09:46", "09:48", "09:50", "09:52", "09:54", "09:56", "09:58",
            "10:00", "10:02", "10:04", "10:06", "10:08", "10:10", "10:12", "10:14", "10:16", "10:18", "10:20", "10:22", "10:24", "10:26", "10:28", "10:30", "10:32", "10:34", "10:36", "10:38", "10:40", "10:42", "10:44", "10:46", "10:48", "10:50", "10:52", "10:54", "10:56", "10:58",
            "11:00", "11:02", "11:04", "11:06", "11:08", "11:10", "11:12", "11:14", "11:16", "11:18", "11:20", "11:22", "11:24", "11:26", "11:28", "11:30", "11:32", "11:34", "11:36", "11:38", "11:40", "11:42", "11:44", "11:46", "11:48", "11:50", "11:52", "11:54", "11:56", "11:58",
            "12:00", "12:02", "12:04", "12:06", "12:08", "12:10", "12:12", "12:14", "12:16", "12:18", "12:20", "12:22", "12:24", "12:26", "12:28", "12:30", "12:32", "12:34", "12:36", "12:38", "12:40", "12:42", "12:44", "12:46", "12:48", "12:50", "12:52", "12:54", "12:56", "12:58",
            "13:00", "13:02", "13:04", "13:06", "13:08", "13:10", "13:12", "13:14", "13:16", "13:18", "13:20", "13:22", "13:24", "13:26", "13:28", "13:30", "13:32", "13:34", "13:36", "13:38", "13:40", "13:42", "13:44", "13:46", "13:48", "13:50", "13:52", "13:54", "13:56", "13:58",
            "14:00", "14:02", "14:04", "14:06", "14:08", "14:10", "14:12", "14:14", "14:16", "14:18", "14:20", "14:22", "14:24", "14:26", "14:28", "14:30", "14:32", "14:34", "14:36", "14:38", "14:40", "14:42", "14:44", "14:46", "14:48", "14:50", "14:52", "14:54", "14:56", "14:58",
            "15:00", "15:02", "15:04", "15:06", "15:08", "15:10", "15:12", "15:14", "15:16", "15:18", "15:20", "15:22", "15:24", "15:26", "15:28", "15:30", "15:32", "15:34", "15:36", "15:38", "15:40", "15:42", "15:44", "15:46", "15:48", "15:50", "15:52", "15:54", "15:56", "15:58",
            "16:00", "16:02", "16:04", "16:06", "16:08", "16:10", "16:12", "16:14", "16:16", "16:18", "16:20", "16:22", "16:24", "16:26", "16:28", "16:30", "16:32", "16:34", "16:36", "16:38", "16:40", "16:42", "16:44", "16:46", "16:48", "16:50", "16:52", "16:54", "16:56", "16:58",
            "17:00", "17:02", "17:04", "17:06", "17:08", "17:10", "17:12", "17:14", "17:16", "17:18", "17:20", "17:22", "17:24", "17:26", "17:28", "17:30", "17:32", "17:34", "17:36", "17:38", "17:40", "17:42", "17:44", "17:46", "17:48", "17:50", "17:52", "17:54", "17:56", "17:58",
            "18:00", "18:02", "18:04", "18:06", "18:08", "18:10", "18:12", "18:14", "18:16", "18:18", "18:20", "18:22", "18:24", "18:26", "18:28", "18:30", "18:32", "18:34", "18:36", "18:38", "18:40", "18:42", "18:44", "18:46", "18:48", "18:50", "18:52", "18:54", "18:56", "18:58",
            "19:00", "19:02", "19:04", "19:06", "19:08", "19:10", "19:12", "19:14", "19:16", "19:18", "19:20", "19:22", "19:24", "19:26", "19:28", "19:30", "19:32", "19:34", "19:36", "19:38", "19:40", "19:42", "19:44", "19:46", "19:48", "19:50", "19:52", "19:54", "19:56", "19:58",
            "20:00", "20:02", "20:04", "20:06", "20:08", "20:10", "20:12", "20:14", "20:16", "20:18", "20:20", "20:22", "20:24", "20:26", "20:28", "20:30", "20:32", "20:34", "20:36", "20:38", "20:40", "20:42", "20:44", "20:46", "20:48", "20:50", "20:52", "20:54", "20:56", "20:58",
            "21:00", "21:02", "21:04", "21:06", "21:08", "21:10", "21:12", "21:14", "21:16", "21:18", "21:20", "21:22", "21:24", "21:26", "21:28", "21:30", "21:32", "21:34", "21:36", "21:38", "21:40", "21:42", "21:44", "21:46", "21:48", "21:50", "21:52", "21:54", "21:56", "21:58",
            "22:00", "22:02", "22:04", "22:06", "22:08", "22:10", "22:12", "22:14", "22:16", "22:18", "22:20", "22:22", "22:24", "22:26", "22:28", "22:30", "22:32", "22:34", "22:36", "22:38", "22:40", "22:42", "22:44", "22:46", "22:48", "22:50", "22:52", "22:54", "22:56", "22:58",
            "23:00", "23:02", "23:04", "23:06", "23:08", "23:10", "23:12", "23:14", "23:16", "23:18", "23:20", "23:22", "23:24", "23:26", "23:28", "23:30", "23:32", "23:34", "23:36", "23:38", "23:40", "23:42", "23:44", "23:46", "23:48", "23:50", "23:52", "23:54", "23:56", "23:58" };


    private String[] get_rack_time(int count_reminders) {
        if (count_reminders == 48) { return rack_time48; }
        else if (count_reminders == 288){ return rack_time288; }
        else if(count_reminders == 720){ return rack_time720; }
        else {
            return rack_time720;
        }
    }

    /** 1st variant of operation method for Add/Modify/Delete/Purge
     * with String reminderProgramStart + Integer reminderChannelNumber
     * @param operation       - can be Add / Modify / Delete / Purge
     * @param macaddress      - macaddress of the box
     * @param count_reminders - count of reminders to generate in json {..}
     * @return
     * @throws IOException
     */
    ArrayList Operation(String ams_ip, String macaddress, String operation, Boolean newapi, int count_reminders,
                        String reminderProgramStart, Integer reminderChannelNumber, String reminderProgramId,
                        int reminderOffset, int reminderScheduleId, int reminderId) throws IOException {
        if (Objects.equals(operation, "Purge")) {
            System.out.println(operation + " newapi=" + newapi + " for macaddress=" + macaddress + ", ams_ip=" + ams_ip);
        } else {
            System.out.println(operation + " newapi=" + newapi + " for macaddress=" + macaddress + ", ams_ip=" + ams_ip + ", "
                    + "reminderProgramStart=" + reminderProgramStart + ", "
                    + "reminderChannelNumber=" + reminderChannelNumber + ", "
                    + "reminderProgramId=" + reminderProgramId + ", "
                    + "reminderOffset=" + reminderOffset + ", "
                    + "reminderScheduleId=" + reminderScheduleId + ", "
                    + "reminderId=" + reminderId);
        }

        HttpClient client = HttpClients.createDefault();
        HttpPost request = new HttpPost(prepare_url(operation, newapi));
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        //request.setHeader("Charset", "UTF-8");
        System.out.println("[DBG] Request string: " + request);

        if (Objects.equals(operation, "Purge")) {
            request.setEntity(new StringEntity(generate_json_reminder_purge(macaddress, newapi)));
        } else {
            request.setEntity(new StringEntity(generate_json_reminder(macaddress, newapi, count_reminders, operation, reminderProgramStart, get_rack_time(count_reminders), reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId)));
        }

        long start = currentTimeMillis();
        HttpResponse response = client.execute(request);
        long finish = currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms request");
        //"[DBG] Response getStatusLine: " + response.getStatusLine());

        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
        StringBuilder body = new StringBuilder();
        for (String line = null; (line = reader.readLine()) != null; ) {
            //body.append(line);
            System.out.println("[DBG] Response body: " + body.append(line).append("\n"));
        }

        ArrayList arrayList = new ArrayList();
        arrayList.add(0, response.getStatusLine().getStatusCode());
        arrayList.add(1, response.getStatusLine().getReasonPhrase());
        arrayList.add(2, check_body_for_statuscode(body.toString(), macaddress));
        return arrayList;
    }


    /** 2operation method for Add/Modify/Delete/Purge
     * String[] rack_date + Integer[] rack_channel,
     * @param operation       - can be Add / Modify / Delete / Purge
     * @param macaddress      - macaddress of the box
     * @param count_reminders - count of reminders to generate in json {..}
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    ArrayList Operation(String ams_ip, String macaddress, String operation, Boolean newapi, int count_reminders,
                        String[] rack_date, Integer[] rack_channel, String reminderProgramId,
                        int reminderOffset, int reminderScheduleId, int reminderId) throws IOException {
        if (Objects.equals(operation, "Purge")) {
            System.out.println(operation + " newapi=" + newapi + " for macaddress=" + macaddress + ", ams_ip=" + ams_ip);
        } else {
            System.out.println(operation + " newapi=" + newapi + " for macaddress=" + macaddress + ", ams_ip=" + ams_ip + ", "
                    + "count_reminders=" + count_reminders + ", "
                    + "reminderOffset=" + reminderOffset + ", "
                    //+ "rack_data.length=" + rack_date.length + ", "
                    + "data=" + Arrays.asList(rack_date) + ", "
                    //+ "rack_channel.length=" + rack_channel.length + ", "
                    + "channel=" + Arrays.asList(rack_channel));
        }

        HttpClient client = HttpClients.createDefault();
        HttpPost request = new HttpPost(prepare_url(operation, newapi));
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        System.out.println("[DBG] Request string: " + request);
        //+ "\n[DBG] Request entity: " + request.getEntity());

        ArrayList arrayList = new ArrayList();
        if (Objects.equals(operation, "Purge")) {
            request.setEntity(new StringEntity(generate_json_reminder_purge(macaddress, newapi)));

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
            arrayList.add(2, check_body_for_statuscode(body.toString(), macaddress));
        } else {
            for (String aRack_date : rack_date) {
                for (int aRack_channel : rack_channel) {
                    System.out.println("operation= " + operation + ", date=" + aRack_date + ", channel=" + aRack_channel);

                    request.setEntity(new StringEntity(generate_json_reminder(macaddress, newapi, count_reminders, operation, aRack_date, get_rack_time(count_reminders), aRack_channel, reminderProgramId, reminderOffset, reminderScheduleId, reminderId)));

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
                    arrayList.add(2, check_body_for_statuscode(body.toString(), macaddress));

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
        //log.info("Change settings for ams_ip=" + ams_ip + " option=" + option + " new_value=" + new_value);

        String url = "http://" + ams_ip + ":" + ams_port + "/ams/settings";
        HttpClient client = HttpClients.createDefault();
        HttpPost request = new HttpPost(url);

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
        arrayList.add(2, check_body_for_statuscode(body.toString(), macaddress));
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
        System.out.println("[DBG] " + (finish - start) + "ms request");

        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
        StringBuilder body = new StringBuilder();
        for (String line; (line = reader.readLine()) != null; ) {
            System.out.println("[DBG] Response body: " + body.append(line).append("\n"));
        }

        ArrayList arrayList = new ArrayList();
        arrayList.add(0, response.getStatusLine().getStatusCode());
        arrayList.add(1, response.getStatusLine().getReasonPhrase());
        arrayList.add(2, check_body_for_statuscode(body.toString(), macaddress));
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
        arrayList.add(2, check_body_for_statuscode(body.toString(), macaddress));
        return arrayList;
    }

    /**
     * NEW generate_json method
     *
     * @param macaddress
     * @param count_remindres
     * @param reminderChannelNumber
     * @param date
     * @param rack_time
     * @param reminderProgramId
     * @param reminderOffset
     * @param reminderScheduleId
     * @param reminderId
     * @return
     */
    private String generate_json_reminder(String macaddress, Boolean newapi,
                                          int count_remindres, String operation, String date, String rack_time[], int reminderChannelNumber,
                                          String reminderProgramId, int reminderOffset, int reminderScheduleId, int reminderId) {
        if(count_remindres <= 0){
            count_remindres = 1;
        }

        JSONObject resultJson = new JSONObject();
        resultJson.put("deviceId", macaddress);
        JSONArray array_reminders = new JSONArray();
        resultJson.put("reminders", array_reminders);
        for (int i = 0; i < count_remindres; i++) {
            JSONObject object_in_reminders = new JSONObject();
            if (!newapi) {
                object_in_reminders.put("operation", operation);
            }
            object_in_reminders.put("reminderProgramStart", date + " " + rack_time[i]);
            object_in_reminders.put("reminderChannelNumber", reminderChannelNumber);
            object_in_reminders.put("reminderProgramId", reminderProgramId);
            object_in_reminders.put("reminderOffset", reminderOffset);
            object_in_reminders.put("reminderScheduleId", reminderScheduleId);
            object_in_reminders.put("reminderId", reminderId);
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

    private String check_body_for_statuscode(String body, String macaddress) {
        String result = "";
        if(body.contains("\"statusCode\":1")){ log.warning("one or more statusCode's = " + statuscode[1]);
            result += "1";
        }
        if(body.contains("\"statusCode\":2")){ log.warning("one or more statusCode's = " + statuscode[2]);
            result += "2";
        }
        if(body.contains("\"statusCode\":3")){ log.warning("one or more statusCode's = " + statuscode[3]);
            result += "3";
        }
        if(body.contains("\"statusCode\":4")){ log.warning("one or more statusCode's = " + statuscode[4]);
            result += "4";
        }
        if(body.contains("\"statusCode\":5")){ log.warning("one or more statusCode's = " + statuscode[5]);
            result += "5";
        }
        if (body.contains("\"status\":\"Failed\"") && body.contains("\"errorMessage\":\"REM-ST-001 Box is not registered\"")) {
            result += "REM-ST-001 Box is not registered";
        }
        if (body.contains("\"status\":\"Failed\"") && body.contains("\"errorMessage\":\"REM-002 Reminders Service error: REM-112\"")) {
            result += "REM-002 Reminders Service error: REM-012 [" + macaddress + "] Request not accomplished";
        }
        if (body.contains("\"status\":\"Failed\"") && body.contains("\"errorMessage\":\"REM-002 Reminders Service error: Timeout detected by BoxResponseTracker\"")) {
            result += "REM-002 Reminders Service error: Timeout detected by BoxResponseTracker";
        }
        if(body.contains("REM-002 Reminders Service error: Can not connect to STB with stbId=" + macaddress)) {
            result += "REM-002 Reminders Service error: Can not connect to STB with stbId=" + macaddress;
        }
        if(body.contains("Failed to getAmsIpByMacAddress for :") && body.contains("No amsIp found for macAddress:")){
            result += "No amsIp found for macAddress";
        }
        if (body.contains("STB MAC not found: " + macaddress)) {
            result += "STB MAC not found: " + macaddress;
        }
        if (body.contains("incorrect value")) {
            result += "incorrect value";
        }
        if (body.contains("SET-025 Unsupported data type: Not a JSON Object:")) {
            result += "SET-025 Unsupported data type: Not a JSON Object";
        }

        System.out.println("[DBG] check_body_for_statuscode: result: " + result);
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

    private String prepare_url(String operation, Boolean newapi) {
        String url, postfix;
        if (newapi) {
            if (Objects.equals(operation, "Add")) {
                postfix = "/ams/Reminders?req=add";
            } else if (Objects.equals(operation, "Modify")) {
                postfix = "/ams/Reminders?req=modify";
            } else if (Objects.equals(operation, "Delete")) {
                postfix = "/ams/Reminders?req=delete";
            } else if (Objects.equals(operation, "Purge")) {
                postfix = "/ams/Reminders?req=purge";
            } else postfix = "/ams/Reminders?req=" + operation;

            url = "http://" + ams_ip + ":" + ams_port + postfix;
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

}