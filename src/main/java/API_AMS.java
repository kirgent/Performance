import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Rule;
import org.junit.rules.Timeout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.System.currentTimeMillis;

class API_AMS extends API{

    @Rule
    final public Timeout globalTimeout = Timeout.seconds(20);

    /** 1st method for Add/Modify/Delete
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

        HttpPost request = new HttpPost(prepare_url(operation, true));
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        request.setEntity(new StringEntity(generate_json_reminder(true, macaddress, count_reminders, operation,
                reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId)));
        System.out.println("[DBG] Request string: " + request);


        long start = System.currentTimeMillis();
        HttpResponse response = HttpClients.createDefault().execute(request);
        long finish = System.currentTimeMillis();
        System.out.print("[DBG] " + (finish-start) + "ms request");

        ArrayList arrayList = new ArrayList();
        arrayList.add(0, response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
        arrayList.add(1, check_body_response(read_response(new StringBuilder(),response).toString(), macaddress));
        System.out.println("[DBG] return codes: " + arrayList);
        return arrayList;
    }

    /** method for Add/Delete OLD_API
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

        HttpPost request = new HttpPost(prepare_url(operation, false));
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        request.setEntity(new StringEntity(generate_json_reminder(false, macaddress, count_reminders, operation, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId)));
        System.out.println("[DBG] Request string: " + request);

        long start = currentTimeMillis();
        HttpResponse response = HttpClients.createDefault().execute(request);
        long finish = currentTimeMillis();
        System.out.print("[DBG] " + (finish - start) + "ms request");
        //"[DBG] Response getStatusLine: " + response.getStatusLine());

        ArrayList arrayList = new ArrayList();
        arrayList.add(0, response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
        arrayList.add(1, check_body_response(read_response(new StringBuilder(),response).toString(), macaddress));
        System.out.println("\n[DBG] return codes: " + arrayList);
        return arrayList;
    }

    /** method for Purge (OLD_API / NEW_API)
     * @param ams_ip - TBD
     * @param macaddress - TBD
     * @param operation - TBD
     * @param newapi - TBD
     * @return arrayList
     * @throws IOException - TBD
     */
    ArrayList Request(String ams_ip, String macaddress, Enum<API.Operation> operation, Boolean newapi) throws IOException {
        System.out.println(operation + " (newapi=" + newapi + ") for macaddress=" + macaddress + ", ams_ip=" + ams_ip);

        HttpPost request = new HttpPost(prepare_url(operation, newapi));
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        request.setEntity(new StringEntity(generate_json_reminder_purge(macaddress, newapi)));
        System.out.println("[DBG] Request string: " + request);

        long start = currentTimeMillis();
        HttpResponse response = HttpClients.createDefault().execute(request);
        long finish = currentTimeMillis();
        System.out.print("[DBG] " + (finish - start) + "ms request");
        //"[DBG] Response getStatusLine: " + response.getStatusLine());

        ArrayList arrayList = new ArrayList();
        arrayList.add(0, response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
        arrayList.add(1, check_body_response(read_response(new StringBuilder(),response).toString(), macaddress));
        System.out.println("\n[DBG] return codes: " + arrayList);
        return arrayList;
    }

    /** 2nd variant of method for Add/Modify/Delete with RACKs:
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
        System.out.println(operation + " (newapi=true) for macaddress=" + macaddress + ", ams_ip=" + ams_ip + ", "
                    + "count_reminders=" + count_reminders + ", "
                    + "reminderOffset=" + reminderOffset + ", "
                    //+ "rack_data.length=" + rack_date.length + ", "
                    + "data=" + Arrays.asList(rack_date) + ", "
                    //+ "rack_channel.length=" + rack_channel.length + ", "
                    + "channel=" + Arrays.asList(rack_channel));

        HttpPost request = new HttpPost(prepare_url(operation, true));
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        System.out.println("[DBG] Request string: " + request);
        //+ "\n[DBG] Request entity: " + request.getEntity());

        HttpClient client = HttpClients.createDefault();
        ArrayList arrayList = new ArrayList();
        for (String aRack_date : rack_date) {
            for (int aRack_channel : rack_channel) {
                System.out.println("operation= " + operation + ", date=" + aRack_date + ", channel=" + aRack_channel);

                request.setEntity(new StringEntity(generate_json_reminder(true, macaddress, count_reminders, operation,
                        aRack_date, aRack_channel,
                        reminderProgramId, reminderOffset, reminderScheduleId, reminderId)));

                long start = currentTimeMillis();
                HttpResponse response = client.execute(request);
                long finish = currentTimeMillis();
                System.out.println("[DBG] " + (finish - start) + "ms request" +
                        "Response getStatusLine: " + response.getStatusLine());

                arrayList.add(0, response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
                arrayList.add(1, check_body_response(read_response(new StringBuilder(),response).toString(), macaddress));

                if (arrayList.get(0).equals(200)) {
                    break;
                }
            }
            if (arrayList.get(0).equals(200)) {
                break;
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
        HttpPost request = new HttpPost("http://" + ams_ip + ":" + ams_port + "/ams/settings");
        request.setHeader("Content-type", "application/json");

        request.setEntity(new StringEntity(generate_json_setting(macaddress, option, value)));

        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        System.out.println("[DBG] Request string: " + request);
        //+ "\n[DBG] Request entity: " + request.getEntity());

        long start = currentTimeMillis();
        HttpResponse response = HttpClients.createDefault().execute(request);
        long finish = currentTimeMillis();
        System.out.print("[DBG] " + (finish - start) + "ms request");

        ArrayList arrayList = new ArrayList();
        arrayList.add(0, response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
        arrayList.add(1, check_body_response(read_response(new StringBuilder(),response).toString(), macaddress));
        System.out.println("\n[DBG] return codes: " + arrayList);
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
     * @param macaddress - TBD
     * @param newapi - TBD
     * @return - TBD
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

    /** generation json for reminder
     * @param macaddress - TBD
     * @param newapi - TBD
     * @param count_reminders
     * @param reminderProgramStart
     * @param reminderChannelNumber
     * @param reminderProgramId
     * @param reminderOffset
     * @param reminderScheduleId
     * @param reminderId
     * @return
     */
    private String generate_json_reminder(Boolean newapi, String macaddress, int count_reminders, Enum<Operation> operation,
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

    private String prepare_url(Enum<Operation> operation, Boolean newapi) {
        String url;
        if (newapi) {
            url = "http://" + ams_ip + ":" + ams_port + "/ams/Reminders?req=" + operation;
        } else {
            url = "http://" + ams_ip + ":" + ams_port + "/ams/Reminders?req=ChangeReminders";
        }
        return url;
    }

}
