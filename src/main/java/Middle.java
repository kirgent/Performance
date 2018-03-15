import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

import static java.lang.System.currentTimeMillis;

public class Middle {

    private final static Logger log = Logger.getLogger(Middle.class.getName());
    //FileHandler txtFile = new FileHandler ("log.log", true);
    //private FileHandler fh = new FileHandler("test_reminder.log");

    @Deprecated
    final private static String postfix_change = "/ams/Reminders?req=ChangeReminders";

    final private int ams_port = 8080;

    //private int result = 0;
    //private int count_iterations = 3;

    //for Modify/Add/Delete
    private String reminderProgramId = "EP0";
    private int reminderProgramId_negative = -1;
    private int reminderProgramId_wrong = 9999;

    private int reminderOffset = 0;
    private int reminderOffset_negative = -1;
    private int reminderOffset_wrong = 9999;

    /*private static String statuscode = "code of the reminder processing result, one of the following:" +
            "\
            sted action with the reminder was accomplished successfully" +
            "\n2 - reminder is set for time in the past" +
            "\n3 - reminder is set for unknown channel" +
            "\n4 - reminder is unknown, applies to reminder deletion attempts";*/

    private static String[] statuscode = {
            "0 - requested action with the reminder was accomplished successfully",
            "1 - empty, TBD",
            "2 - reminder is set for time in the past",
            "3 - reminder is set for unknown channel",
            "4 - reminder is unknown, applies to reminder deletion attempts",
            "5 - reminder with provided pair of identifiers (reminderScheduleId and reminderId) is already set (for Add Reminder request)" };

    //DATE
    private String[] rack_date = { "2018-03-08" };

    //TIME
    final private String[] rack_time48 = { "00:00", "00:30", "01:00", "01:30", "02:00", "02:30", "03:00", "03:30", "04:00", "04:30", "05:00", "05:30",
            "06:00", "06:30", "07:00", "07:30", "08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30",
            "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30",
            "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30", "22:00", "22:30", "23:00", "23:30" };
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

    //CHANNEL
    private Integer[] rack_channel = { 2 };
    private Integer[] rack_channel10 = { 2, 3, 4, 5, 6, 7, 8, 9, 12, 13 };
    /*private Integer[] rack_channel30 = { 2, 3, 4, 5, 6, 7, 8, 9, 12, 13,
            14, 16, 18, 19, 22, 23, 25, 28, 30, 31,
            32, 33, 37, 38, 41, 44, 46, 48, 49, 50 };*/

    private String[] get_rack_time(int count_reminders) {
        if (count_reminders == 48) { return rack_time48; }
        else if (count_reminders == 288){ return rack_time288; }
        else if(count_reminders == 720){ return rack_time720; }
        else {
            return rack_time720;
        }
    }

    /**
     * @param operation - can be Add / Modify / Delete
     * @param macaddress - macaddress of the box
     * @param count_reminders - count of reminders to generate in json {..}
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    @Deprecated
    ArrayList Operation(String ams_ip, String macaddress, String operation, int count_reminders, String[] rack_date, Integer[] rack_channel) throws IOException, InterruptedException {
        System.out.println(operation + " for macaddress=" + macaddress + ", "
                + "count_reminders=" + count_reminders + ", "
                + "reminderOffset=" + reminderOffset + ", "
                + "data=" + Arrays.asList(rack_date) + ", "
                + "channel=" + Arrays.asList(rack_channel));
        /*log.info(operation + " for macaddress=" + macaddress + ", "
                + "count_reminders=" + count_reminders + ", "
                + "reminderOffset=" + reminderOffset + ", "
                + "data=" + Arrays.asList(rack_date) + ", "
                + "channel=" + Arrays.asList(rack_channel));*/

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost request = new HttpPost("http://" + ams_ip + ":" + ams_port + postfix_change);

        ArrayList arrayList = new ArrayList();
        for (String aRack_date : rack_date) {
            for (Integer aRack_channel : rack_channel) {
                //log.info(operation + " iteration=" + c + "/" + count_iterations + ", date=" + aRack_date + ", channel=" + aRack_channel);

                StringEntity entity = new StringEntity(generate_json(macaddress, count_reminders, operation, aRack_channel, aRack_date, get_rack_time(count_reminders), reminderProgramId, reminderOffset));
                request.setEntity(entity);

                request.setHeader("Content-type", "application/json");
                //request.setHeader("Accept", "application/json");
                //request.setHeader("Content-type", "text/plain");
                //request.setHeader("User-Agent","curl/7.58.0");
                //request.setHeader("Charset", "UTF-8");

                System.out.println("[DBG] Request string: " + request);
                    //+ "\n[DBG] Request entity: " + request.getEntity());

                long start = currentTimeMillis();
                HttpResponse response = client.execute(request);
                long finish = currentTimeMillis();
                System.out.println("[DBG] " + (finish - start) + "ms request");
                //"Response getStatusLine: " + response.getStatusLine());

                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
                StringBuilder body = new StringBuilder();
                for (String line = null; (line = reader.readLine()) != null; ) {
                    body.append(line);
                    //System.out.println("[DBG] Response body: " + body.append(line).append("\n"));
                }

                arrayList.add(0, response.getStatusLine().getStatusCode());
                arrayList.add(1, response.getStatusLine().getReasonPhrase());
                arrayList.add(2, check_body_for_statuscode(body.toString()));

                if (arrayList.get(0).equals(200)) { break; }
            }
            if (arrayList.get(0).equals(200)) { break; }
        }
        client.close();
        return arrayList;
    }

    /**
     * @param operation       - can be Add / Modify / Delete
     * @param macaddress      - macaddress of the box
     * @param count_reminders - count of reminders to generate in json {..}
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    ArrayList Operation(String ams_ip, String macaddress, String operation, int count_reminders, Integer[] rack_channel, String reminderProgramStart, String reminderProgramId, int reminderOffset, int reminderScheduleId, int reminderId) throws IOException, InterruptedException {
        System.out.println(operation + " for macaddress=" + macaddress + ", "
                + "count_reminders=" + count_reminders + ", "
                + "reminderOffset=" + reminderOffset + ", "
                + "data count=" + rack_date.length + ", "
                + "channel count=" + rack_channel.length);
        /*log.info(operation + " for macaddress=" + macaddress + ", "
                + "count_reminders=" + count_reminders + ", "
                + "reminderOffset=" + reminderOffset + ", "
                + "data count=" + rack_date.length + ", "
                + "channel count=" + rack_channel.length);*/

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost request = new HttpPost("http://" + ams_ip + ":" + ams_port + get_postfix(operation));

        ArrayList arrayList = new ArrayList();
        for (String aRack_date : rack_date) {
            for (int aRack_channel : rack_channel) {
                System.out.println("operation= " + operation + ", date=" + aRack_date + ", channel=" + aRack_channel);
                //log.info("operation= " + operation +", date=" + aRack_date + ", channel=" + aRack_channel);

                StringEntity entity = new StringEntity(generate_json(macaddress, count_reminders, aRack_channel, aRack_date, get_rack_time(count_reminders), reminderProgramId, reminderOffset, reminderScheduleId, reminderId));
                request.setEntity(entity);
                request.setHeader("Content-type", "application/json");
                //request.setHeader("Accept", "application/json");
                //request.setHeader("Content-type", "text/plain");
                //request.setHeader("User-Agent","curl/7.58.0");
                //request.setHeader("Charset", "UTF-8");

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
                    //body.append(line);
                    System.out.println("[DBG] Response body: " + body.append(line).append("\n"));
                }

                arrayList.add(0, response.getStatusLine().getStatusCode());
                arrayList.add(1, response.getStatusLine().getReasonPhrase());
                arrayList.add(2, check_body_for_statuscode(body.toString()));

                if (arrayList.get(0).equals(200)) {
                    break;
                }
            }
            if (arrayList.get(0).equals(200)) {
                break;
            }
        }
        client.close();
        return arrayList;
    }

    /** operation realization only for Purge
     * @param operation  - can be Add / Modify / Delete
     * @param macaddress - macaddress of the box
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    ArrayList Operation(String ams_ip, String macaddress, String operation, String api) throws IOException, InterruptedException {
        System.out.println("Purge for ams_ip=" + ams_ip + " and macaddress=" + macaddress);
        //log.info("Purge for ams_ip=" + ams_ip + " and macaddress=" + macaddress);

        String url = "http://" + ams_ip + ":" + ams_port + postfix_change;
        if (Objects.equals(api, "newapi")) {
            url = "http://" + ams_ip + ":" + ams_port + get_postfix(operation);
        }

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost request = new HttpPost(url);

        request.setEntity(new StringEntity(generate_json(macaddress, api)));
        //request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        System.out.println("[DBG] Request string: " + request);
        //+ "\n[DBG] Request entity: " + request.getEntity());

        long start = currentTimeMillis();
        HttpResponse response = client.execute(request);
        long finish = currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms request" +
                "[DBG] Response getStatusLine: " + response.getStatusLine());

        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
        StringBuilder body = new StringBuilder();
        for (String line = null; (line = reader.readLine()) != null; ) {
            body.append(line);
            //System.out.println("[DBG] Response body: " + body.append(line).append("\n"));
        }

        ArrayList arrayList = new ArrayList();
        arrayList.add(0, response.getStatusLine().getStatusCode());
        arrayList.add(1, response.getStatusLine().getReasonPhrase());
        arrayList.add(2, check_body_for_statuscode(body.toString()));
        client.close();

        return arrayList;
    }

    ArrayList Check_registration(String macaddress, String charterapi) throws IOException {
        System.out.println("Check_registration "+ macaddress +" via charterapi: " + charterapi);
        //log.info("Check_registration "+ macaddress +" via charterapi: " + charterapi);

        //HttpClient client = HttpClients.createDefault();
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(charterapi + "/amsIp/" + macaddress);

        request.setHeader("Accept", "*/*");
        //request.setHeader("Content-type", "application/json");
        //request.setHeader("Content-type", "text/plain");
        //request.setHeader("Charset", "UTF-8");
        System.out.println("[DBG] Request string: " + request);
        //+"\n[DBG] Request getRequestLine: "+request.getRequestLine());

        long start = currentTimeMillis();
        HttpResponse response = client.execute(request);
        long finish = currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms request, " +
                "Response getStatusLine: " + response.getStatusLine());

        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
        StringBuilder body = new StringBuilder();
        for (String line; (line = reader.readLine()) != null; ) {
            System.out.println("[DBG] Response body: " + body.append(line).append("\n"));
        }
        client.close();

        ArrayList arrayList = new ArrayList();
        arrayList.add(0, response.getStatusLine().getStatusCode());
        arrayList.add(1, response.getStatusLine().getReasonPhrase());
        arrayList.add(2, check_body_for_statuscode(body.toString()));
        return arrayList;
    }

    ArrayList Change_registration(String macaddress, String charterapi, String ams_ip) throws IOException {
        System.out.println("Change_registration "+ macaddress +" to ams " + ams_ip + " via charterapi: " + charterapi);
        //log.info("Change_registration "+ macaddress +" to ams " + ams_ip + " via charterapi: " + charterapi);

        String postfix = "?requestor=AMS";
        String url = charterapi + postfix;
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost request = new HttpPost(url);

        String json_change_registration = "{\"setting\":{\"groups\":[{\"options\":[],\"id\":\"STB" + macaddress + "\",\"type\":\"device-stb\",\"amsid\":\"" + ams_ip + "\"}]}}";

        request.setEntity(new StringEntity(json_change_registration));
        request.setHeader("Content-type", "application/json");
        //request.setHeader("Accept", "application/json");
        System.out.println("[DBG] Request string: " + request
                + "\n[DBG] Request json string: " + json_change_registration);
        //+ "\n[DBG] Request entity: " + request.getEntity());
        //+ "\n[DBG] Request headers: " + request.getAllHeaders());

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
        client.close();

        ArrayList arrayList = new ArrayList();
        arrayList.add(0, response.getStatusLine().getStatusCode());
        arrayList.add(1, response.getStatusLine().getReasonPhrase());
        arrayList.add(2, check_body_for_statuscode(body.toString()));
        return arrayList;
    }

    /**
     * OLD function
     *
     * @param macaddress
     * @param count_remindres
     * @param operation
     * @param reminderChannelNumber
     * @param date
     * @param rack_time
     * @param reminderProgramId
     * @param reminderOffset
     * @return
     */
    @Deprecated
    private String generate_json(String macaddress, int count_remindres, String operation, int reminderChannelNumber, String date, String rack_time[], String reminderProgramId, int reminderOffset) {
        JSONObject resultJson = new JSONObject();
        resultJson.put("deviceId", macaddress);
        JSONArray array = new JSONArray();
        resultJson.put("reminders", array);
        for (int i = 0; i < count_remindres; i++) {
            JSONObject object = new JSONObject();
            object.put("operation", operation);
            object.put("reminderChannelNumber", reminderChannelNumber);
            object.put("reminderProgramId", reminderProgramId);
            object.put("reminderProgramStart", date + " " + rack_time[i]);
            object.put("reminderOffset", reminderOffset);
            array.add(object);
        }
        //System.out.println("generated json: " + resultJson.toJSONString());
        return resultJson.toJSONString();
    }

    /**
     * NEW function
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
    private String generate_json(String macaddress, int count_remindres, int reminderChannelNumber, String date, String rack_time[], String reminderProgramId, int reminderOffset, int reminderScheduleId, int reminderId) {

        JSONObject resultJson = new JSONObject();
        resultJson.put("deviceId", macaddress);
        JSONArray array = new JSONArray();
        resultJson.put("reminders", array);
        for (int i = 0; i < count_remindres; i++) {
            JSONObject object = new JSONObject();
            object.put("reminderChannelNumber", reminderChannelNumber);
            object.put("reminderProgramStart", date + " " + rack_time[i]);
            object.put("reminderProgramId", reminderProgramId);
            object.put("reminderOffset", reminderOffset);
            object.put("reminderScheduleId", reminderScheduleId);
            object.put("reminderId", reminderId);
            array.add(object);
        }
        System.out.println("generated json: " + resultJson.toJSONString());
        return resultJson.toJSONString();
    }

    /**
     * NEW function only for Purge
     *
     * @param macaddress
     * @return
     */
    private String generate_json(String macaddress, String api) {
        //NEWAPI Purge
        //String json_purge = "{\"deviceId\":" + macaddress + ",\"reminders\":[]}";
        JSONObject resultJson = new JSONObject();
        resultJson.put("deviceId", macaddress);
        JSONArray array = new JSONArray();
        resultJson.put("reminders", array);

        if (Objects.equals(api, "oldapi")){
            //OLDAPI Purge
            //String json_purge = "{\"deviceId\":" + macaddress + ",\"reminders\":[{\"operation\":\"Purge\"}]}";
            JSONObject object = new JSONObject();
            object.put("operation", "Purge");
            array.add(object);
        }

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

    private String check_body_for_statuscode(String body){
        //ArrayList result = new ArrayList();
        String result = "";

        if(body.contains("\"statusCode\":1")){ log.warning("one or more statusCode's = " + statuscode[1]);
            //result.add(1);
            result += "1";
        }
        if(body.contains("\"statusCode\":2")){ log.warning("one or more statusCode's = " + statuscode[2]);
            //result.add(2);
            result += "2";
        }
        if(body.contains("\"statusCode\":3")){ log.warning("one or more statusCode's = " + statuscode[3]);
            //result.add(3);
            result += "3";
        }
        if(body.contains("\"statusCode\":4")){ log.warning("one or more statusCode's = " + statuscode[4]);
            //result.add(4);
            result += "4";
        }
        if(body.contains("\"statusCode\":5")){ log.warning("one or more statusCode's = " + statuscode[5]);
            //result.add(5);
            result += "5";
        }
        if(body.contains("\"message\":\"SUCCESS\"")){
            result += "SUCCESS";
        }
        if(body.contains("\"errorMessage\":\"REM-ST-001 Box is not registered\"")){
            result += "REM-ST-001 Box is not registered";
        }
        if(body.contains("Failed to getAmsIpByMacAddress for :") && body.contains("No amsIp found for macAddress:")){
            result += "No amsIp found for macAddress";
        }

        //System.out.println("[DBG] result: " + result);
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

    private String get_postfix(String operation) {
        String result = "";
        if (Objects.equals(operation, "Add")){
            result = "/ams/Reminders?req=add"; }
        else if(Objects.equals(operation, "Modify")){
            result = "/ams/Reminders?req=modify"; } else if(Objects.equals(operation, "Delete")) {
            result = "/ams/Reminders?req=delete";
        } else if (Objects.equals(operation, "Purge")) {
            result = "/ams/Reminders?req=purge";
        } else result = "/ams/Reminders?req=" + operation;
        return result;
    }

    public String get_date(int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat pattern = new SimpleDateFormat("yyyy-MM-dd");
        calendar.add(Calendar.DAY_OF_YEAR, i);
        return pattern.format(calendar.getTime());
    }

}