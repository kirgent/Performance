import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static java.lang.System.currentTimeMillis;

class API_AMS extends API{

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
    ArrayList Request(String ams_ip, String macaddress, Enum<API.Operation> operation, int count_reminders,
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

        starttime();
        HttpResponse response = client.execute(request);
        finishtime();
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
    ArrayList Request(String ams_ip, String macaddress, Enum<API.Operation> operation, int count_reminders,
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
    ArrayList Request_purge(String ams_ip, String macaddress, Enum<API.Operation> operation, Boolean newapi) throws IOException {
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
    ArrayList Request(String ams_ip, String macaddress, Enum<API.Operation> operation, int count_reminders,
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

    /** method for change settings on AMS
     * @param ams_ip
     * @param macaddress
     * @param option
     * @param value
     * @return
     * @throws IOException
     */
    ArrayList Change_settings(String ams_ip, String macaddress, String option, String value) throws IOException {
        System.out.println("Change settings for macaddress=" + macaddress + ", ams_ip=" + ams_ip + " option=" + option + ", value=" + value);

        HttpClient client = HttpClients.createDefault();
        HttpPost request = new HttpPost("http://" + ams_ip + ":" + ams_port + "/ams/settings");
        request.setHeader("Content-type", "application/json");

        ArrayList arrayList = new ArrayList();
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

    private String generate_json_setting(String macaddress, String option, String value) {
        //String json = "{\"settings\":{\"groups\":[{\"id\":\"STBmacaddress\",\"type\":\"device-stb\",\"options\":[{\"name\":\"Audio Output\",\"value\":\"HDMI\"}]}]}}";
        JSONObject json = new JSONObject();
        JSONObject object_in_settings = new JSONObject();
        JSONArray array_groups = new JSONArray();

        json.put("settings", object_in_settings);
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

        String result = json.toString();
        System.out.println("generated json: " + result);
        return result;
    }

    /** method for OLD/NEW API
     * @param macaddress
     * @param newapi
     * @return
     */
    private String generate_json_reminder_purge(String macaddress, Boolean newapi) {
        //String json = "{\"deviceId\":" + macaddress + ",\"reminders\":[]}";
        JSONObject json = new JSONObject();
        json.put("deviceId", macaddress);
        JSONArray array_reminders = new JSONArray();
        json.put("reminders", array_reminders);

        if (!newapi){
            //String json_purge = "{\"deviceId\":" + macaddress + ",\"reminders\":[{\"operation\":\"Purge\"}]}";
            JSONObject object_in_reminders = new JSONObject();
            object_in_reminders.put("operation", "Purge");
            array_reminders.add(object_in_reminders);
        }

        String result = json.toString();
        System.out.println("generated json: " + result);
        return result;
    }

    /** method for OLD/NEW API
     * @param macaddress
     * @param newapi
     * @param count_reminders
     * @param enum_operation
     * @param reminderProgramStart
     * @param reminderChannelNumber
     * @param reminderProgramId
     * @param reminderOffset
     * @param reminderScheduleId
     * @param reminderId
     * @return
     */
    private String generate_json_reminder(String macaddress, Boolean newapi, int count_reminders, Enum<Operation> operation,
                                          String reminderProgramStart, int reminderChannelNumber,
                                          String reminderProgramId, int reminderOffset, int reminderScheduleId, int reminderId) {
        //if(count_reminders <= 0){ count_reminders = 1; }
        if(count_reminders > 1440){ count_reminders = 1440; }

        String action = "";

        if (!newapi) {
            switch (operation.name()) {
                case "add"       : action = "Add"; break;
                case "delete"    : action = "Delete"; break;
                case "purge"     : action = "Purge"; break;
                case "blablabla" : action = "blablabla"; break;
                default: break;
            }
        }

        JSONObject json = new JSONObject();
        json.put("deviceId", macaddress);
        JSONArray array_reminders = new JSONArray();
        json.put("reminders", array_reminders);
        for (int i = 1; i <= count_reminders; i++) {
            JSONObject object_in_reminders = new JSONObject();
            if (!newapi) {
                object_in_reminders.put("operation", action);
            }

            if (!operation.name().equals("delete")) {
                object_in_reminders.put("reminderProgramStart", reminderProgramStart + " " + get_time(count_reminders, i));
                object_in_reminders.put("reminderChannelNumber", reminderChannelNumber);
                object_in_reminders.put("reminderOffset", reminderOffset);
                if (newapi) {
                    object_in_reminders.put("reminderProgramId", reminderProgramId);
                    object_in_reminders.put("reminderScheduleId", reminderScheduleId);
                    object_in_reminders.put("reminderId", reminderId);
                }
            } else {
                object_in_reminders.put("reminderScheduleId", reminderScheduleId);
                object_in_reminders.put("reminderId", reminderId);
            }

            array_reminders.add(object_in_reminders);
        }
        String result = json.toString();
        System.out.println("generated json: " + result);
        return result;
    }

    private String prepare_url(String ams_ip, Enum<Operation> operation, Boolean newapi) {
        String url;
        if (newapi) {
            url = "http://" + ams_ip + ":" + ams_port + "/ams/Reminders?req=" + operation;
        } else {
            url = "http://" + ams_ip + ":" + ams_port + "/ams/Reminders?req=ChangeReminders";
        }
        return url;
    }

}
