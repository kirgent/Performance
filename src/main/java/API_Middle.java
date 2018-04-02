import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

class API_Middle extends API {

    ArrayList Change_registration(String macaddress, String charterapi, String ams_ip) throws IOException {
        System.out.println("Change_registration "+ macaddress +" to ams " + ams_ip + " via charterapi: " + charterapi);
        //log.info("Change_registration "+ macaddress +" to ams " + ams_ip + " via charterapi: " + charterapi);

        HttpClient client = HttpClients.createDefault();
        HttpPost request = new HttpPost(charterapi + settings_postfix + "?requestor=AMS");

        request.setEntity(new StringEntity(generate_json_change_registration(macaddress, ams_ip, "Change_registration")));
        request.setHeader("Content-type", "application/json");
        request.setHeader("Accept", "application/json");
        System.out.println("[DBG] Request string: " + request);
        //+ "\n[DBG] Request json string: " + json_change_registration);

        starttime();
        HttpResponse response = client.execute(request);
        finishtime();
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

    ArrayList Check_registration(String macaddress, String charterapi) throws IOException {
        System.out.println("Check_registration "+ macaddress +" via charterapi: " + charterapi);

        HttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(charterapi + settings_postfix + "/amsIp/" + macaddress);

        //request.setHeader("Accept", "*/*");
        //request.setHeader("Content-type", "application/json");
        //request.setHeader("Content-type", "text/plain");
        //request.setHeader("Charset", "UTF-8");
        System.out.println("[DBG] Request string: " + request);
        //+"\n[DBG] Request getRequestLine: "+request.getRequestLine());

        starttime();
        HttpResponse response = client.execute(request);
        finishtime();
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

    ArrayList Delete_multiple_reminders(String macaddress, String charterapi, int reminderScheduleId, int reminderId) throws IOException {
        System.out.println("Delete_multiple_reminders with reminderScheduleId=" + reminderScheduleId +
                " and reminderId=" + reminderId);
        HttpClient client = HttpClients.createDefault();
        HttpPost request = new HttpPost(charterapi + "/remindersmiddle/v1/reminders/deleteMultipleReminders");
        System.out.println("[DBG] Request string: " + request);

        //curl -X POST http://<ipAddress>:<port>/remindersmiddle/v1/reminders/deleteMultipleReminders -d '
        // {"macAddress":"STB12345",
        // [
        // {"reminderScheduleId":1222,"reminderId":34843},
        // {"reminderScheduleId":1223,"reminderId":34841},
        // {"reminderScheduleId":1224,"reminderId":34842}
        // ]}'
        request.setEntity(new StringEntity(generate_json_reminder_delete_multiple2(macaddress, reminderScheduleId, reminderId)));

        starttime();
        HttpResponse response = client.execute(request);
        finishtime();
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

    ArrayList Schedule_a_reminder(String macaddress, String charterapi, int lineupId) throws IOException {
        System.out.println("Schedule_a_reminder:");
        HttpClient client = HttpClients.createDefault();
        HttpPost request = new HttpPost(charterapi + "/remindersmiddle/v1/reminders?lineupId=" + lineupId + "&deviceId=" + macaddress);
        //request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        request.setHeader("Cache-Control", "no-cache");
        System.out.println("[DBG] Request string: " + request);

        request.setEntity(new StringEntity(generate_json_reminder_schedule()));

        starttime();
        HttpResponse response = client.execute(request);
        finishtime();
        System.out.print("[DBG] " + (finish - start) + "ms request, ");

        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
        StringBuilder body = new StringBuilder();
        for (String line; (line = reader.readLine()) != null; ) {
            System.out.println("response body: " + body.append(line).append("\n"));
        }
        System.out.println("[DBG] get:"+ response.getHeaders("status"));

        ArrayList arrayList = new ArrayList();
        arrayList.add(0, response.getStatusLine().getStatusCode());
        arrayList.add(1, response.getStatusLine().getReasonPhrase());
        arrayList.add(2, check_body_response(body.toString(), macaddress));
        return arrayList;
    }

    private String generate_json_reminder_schedule() {
        /*Sample Middle cURL (local):
        {"reminderType":"Series",
        "connectorValue":"123456",
        "deliveryId":"12345-MV000199170000-1405522800000",
        "channelId":12345,
        "programId":"MV000199170000",
             "channelNumber":12,
             "startTime":1405522800000,
             "reminderPresetTime":5,
             "newOnly":true}'*/

        JSONObject json = new JSONObject();
        json.put("reminderType", "Series");
        json.put("connectorValue", "123456");
        json.put("deliveryId", "12345-MV000199170000-1405522800000");
        json.put("channelId", 12345);
        json.put("programId", "MV000199170000");
        json.put("channelNumber", 2);
        json.put("startTime", 1405522800);
        json.put("reminderPresetTime", 5);
        json.put("newOnly", true);
        String result = json.toJSONString();
        System.out.println("generated json: " + result);
        return result;
    }

    String generate_json_change_registration(String macaddress, String ams_ip, String action) {
        //String json = "{\"setting\":{\"groups\":[{\"options\":[],\"id\":\"STBmacaddress\",\"type\":\"device-stb\",\"amsid\":\"" + ams_ip + "\"}]}}";
        JSONObject json = new JSONObject();
        JSONObject object_in_settings = new JSONObject();
        JSONArray array_groups = new JSONArray();

        json.put("settings", object_in_settings);
        object_in_settings.put("groups", array_groups);

        JSONObject object_in_groups = new JSONObject();
        array_groups.add(object_in_groups);
        object_in_groups.put("id", "STB" + macaddress);
        object_in_groups.put("type", "device-stb");
        object_in_groups.put("amsid", ams_ip);
        JSONArray array_options = new JSONArray();
        object_in_groups.put("options", array_options);

        String result = json.toString();
        System.out.println("generated json: " + result);
        return result;
    }

    ArrayList GetAllReminder(String macaddress, String charterapi, int lineupId) throws IOException {
        System.out.println("GetAllReminder "+ macaddress +" via charterapi: " + charterapi);
        HttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(charterapi + "/remindersmiddle/v1/reminders?deviceId=" + macaddress + "&lineupId=" + lineupId);
        System.out.println("[DBG] Request string: " + request);

        starttime();
        HttpResponse response = client.execute(request);
        finishtime();
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

    //todo
    private String generate_json_reminder_delete_multiple(String macaddress, int reminderScheduleId, int reminderId) {
        JSONObject json = new JSONObject();
        json.put("macAddress", macaddress);
        JSONArray array = new JSONArray();
        //todo
        json.put("",array);
        //for (int i = 1; i <= count_reminders; i++) {
        JSONObject object_in_array = new JSONObject();
        array.add(object_in_array);
        object_in_array.put("reminderScheduleId", reminderScheduleId);
        object_in_array.put("reminderId", reminderId);
        //}
        String result = json.toJSONString();
        System.out.println("generated json: " + result);
        return result;
    }

    //todo
    private String generate_json_reminder_delete_multiple2(String macaddress, int reminderScheduleId, int reminderId) {
        JSONObject json = new JSONObject();
        json.put("macAddress", "STB" + macaddress);
        JSONArray array = new JSONArray();
        json.put("reminderIdentifiers",array);
        //for (int i = 1; i <= count_reminders; i++) {
        JSONObject object_in_array = new JSONObject();
        array.add(object_in_array);
        object_in_array.put("reminderScheduleId", reminderScheduleId);
        object_in_array.put("reminderId", reminderId);
        //}
        String result = json.toJSONString();
        System.out.println("generated json: " + result);
        return result;
    }

}
