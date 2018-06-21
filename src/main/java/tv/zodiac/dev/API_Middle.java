package tv.zodiac.dev;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

class API_Middle extends API_common {

    ArrayList Change_registration(String charterapi, String mac, String ams_ip) throws IOException {
        System.out.println("[DBG] Change_registration " + mac + " to ams " + ams_ip + " via charterapi: " + charterapi);
        HttpPost request = new HttpPost(charterapi + postfix_settings + "?requestor=AMS");
        request.setEntity(new StringEntity(generate_json_change_registration(mac, ams_ip)));
        request.setHeader("Content-type", "application/json");
        request.setHeader("Accept", "application/json");
        System.out.println("[DBG] Request string: " + request);
        //+ "\n[DBG] Request json string: " + json_change_registration);

        long start = System.currentTimeMillis();
        HttpResponse response = HttpClients.createDefault().execute(request);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms request, " +
                "Response getStatusLine: " + response.getStatusLine());

        ArrayList arrayList = new ArrayList();
        arrayList.add(0, response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
        arrayList.add(1, check_body_response(read_response(new StringBuilder(),response).toString(), mac));
        System.out.println("[DBG] return data: " + arrayList);
        return arrayList;
    }

    ArrayList Check_registration(String charterapi, String mac) throws IOException {
        System.out.println("[DBG] Check_registration " + mac + " via charterapi: " + charterapi);
        HttpGet request = new HttpGet(charterapi + postfix_settings + "/amsIp/" + mac);
        //request.setHeader("Accept", "*/*");
        //request.setHeader("Content-type", "application/json");
        //request.setHeader("Content-type", "text/plain");
        //request.setHeader("Charset", "UTF-8");
        System.out.println("[DBG] Request string: " + request);

        long start = System.currentTimeMillis();
        HttpResponse response = HttpClients.createDefault().execute(request);
        long finish = System.currentTimeMillis();
        System.out.print("[DBG] " + (finish - start) + "ms request");

        ArrayList arrayList = new ArrayList();
        arrayList.add(0, response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
        arrayList.add(1, check_body_response(read_response(new StringBuilder(),response).toString(), mac));
        System.out.println("[DBG] return data: " + arrayList);
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
        if(show_generated_json) {
            System.out.println("generated json: " + result);
        }
        return result;
    }

    private String generate_json_change_registration(String mac, String ams_ip) {
        //String json = "{\"setting\":{\"groups\":[{\"options\":[],\"id\":\"STBmacaddress\",\"type\":\"device-stb\",\"amsid\":\"" + ams_ip + "\"}]}}";
        JSONObject json = new JSONObject();
        JSONObject object_in_settings = new JSONObject();
        JSONArray array_groups = new JSONArray();

        json.put("settings", object_in_settings);
        object_in_settings.put("groups", array_groups);

        JSONObject object_in_groups = new JSONObject();
        array_groups.add(object_in_groups);
        object_in_groups.put("id", "STB" + mac);
        object_in_groups.put("type", "device-stb");
        object_in_groups.put("amsid", ams_ip);
        JSONArray array_options = new JSONArray();
        object_in_groups.put("options", array_options);

        String result = json.toString();
        if(show_generated_json) {
            System.out.println("generated json: " + result);
        }
        return result;
    }

    //todo
    private String generate_json_reminder_delete_multiple(String mac, int reminderScheduleId, int reminderId) {
        JSONObject json = new JSONObject();
        json.put("macAddress", mac);
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
        if(show_generated_json) {
            System.out.println("generated json: " + result);
        }
        return result;
    }

    //todo
    private String generate_json_reminder_delete_multiple2(String mac, int reminderScheduleId, int reminderId) {
        JSONObject json = new JSONObject();
        json.put("macAddress", "STB" + mac);
        JSONArray array = new JSONArray();
        json.put("reminderIdentifiers",array);
        //for (int i = 1; i <= count_reminders; i++) {
        JSONObject object_in_array = new JSONObject();
        array.add(object_in_array);
        object_in_array.put("reminderScheduleId", reminderScheduleId);
        object_in_array.put("reminderId", reminderId);
        //}
        String result = json.toJSONString();
        if(show_generated_json) {
            System.out.println("generated json: " + result);
        }
        return result;
    }

    ArrayList GetAllReminder(String charterapi, String deviceId, int lineupId) throws IOException {
        System.out.println("GetAllReminder for " +  deviceId + " via charterapi: " + charterapi);
        long start = System.currentTimeMillis();
        HttpResponse response = HttpClients.createDefault().execute(prepareGetRequest(charterapi + "/remindersmiddle/v1/reminders?deviceId=" + deviceId + "&lineupId=" + lineupId));
        long finish = System.currentTimeMillis();
        System.out.print("[DBG] " + (finish-start) + "ms request");

        ArrayList arrayList = new ArrayList();
        arrayList.add(0, response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
        arrayList.add(1, check_body_response(read_response(new StringBuilder(),response).toString(), deviceId));
        System.out.println("[DBG] return data: " + arrayList);
        return arrayList;
    }

    ArrayList GetStbReminder(String charterapi, String deviceId) throws IOException {
        System.out.println("GetStbReminder for "+ deviceId + " via charterapi: " + charterapi);
        long start = System.currentTimeMillis();
        HttpResponse response = HttpClients.createDefault().execute(prepareGetRequest(charterapi + "/remindersmiddle/v1/stbReminders?deviceId=" + deviceId));
        long finish = System.currentTimeMillis();
        System.out.print("[DBG] " + (finish-start) + "ms request");

        ArrayList arrayList = new ArrayList();
        arrayList.add(0, response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
        arrayList.add(1, check_body_response(read_response(new StringBuilder(),response).toString(), deviceId));
        System.out.println("[DBG] return data: " + arrayList);
        return arrayList;
    }

    ArrayList Delete_multiple_reminders(String charterapi, String deviceId, int reminderScheduleId, int reminderId) throws IOException {
        if(show_debug_level) {
            System.out.println("Delete_multiple_reminders with reminderScheduleId=" + reminderScheduleId + " and reminderId=" + reminderId);
        }
        HttpPost request = new HttpPost(charterapi + "/remindersmiddle/v1/reminders/deleteMultipleReminders");
        request.setEntity(new StringEntity(generate_json_reminder_delete_multiple2(deviceId, reminderScheduleId, reminderId)));
        System.out.println("[DBG] Request string: " + request);

        long start = System.currentTimeMillis();
        HttpResponse response = HttpClients.createDefault().execute(request);
        long finish = System.currentTimeMillis();
        System.out.print("[DBG] " + (finish-start) + "ms request");

        ArrayList arrayList = new ArrayList();
        arrayList.add(0, response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
        arrayList.add(1, check_body_response(read_response(new StringBuilder(),response).toString(), deviceId));
        System.out.println("[DBG] return data: " + arrayList);
        return arrayList;
    }

    ArrayList Schedule_reminder(String charterapi, String deviceId, int lineupId) throws IOException {
        System.out.println("Schedule_a_reminder:");
        HttpPost request = new HttpPost(charterapi + "/remindersmiddle/v1/reminders?lineupId=" + lineupId + "&deviceId=" + deviceId);
        request.setHeader("Content-type", "application/json");
        request.setHeader("Cache-Control", "no-cache");
        request.setEntity(new StringEntity(generate_json_reminder_schedule()));
        System.out.println("[DBG] Request string: " + request);

        long start = System.currentTimeMillis();
        HttpResponse response = HttpClients.createDefault().execute(request);
        long finish = System.currentTimeMillis();
        System.out.print("[DBG] " + (finish-start) + "ms request");

        ArrayList arrayList = new ArrayList();
        arrayList.add(0, response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
        arrayList.add(1, check_body_response(read_response(new StringBuilder(), response).toString(), deviceId));
        System.out.println("[DBG] return data: " + arrayList);
        return arrayList;
    }

}
