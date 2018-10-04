package test.perf.common;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

class MiddleAPI extends CommonAPI {

    ArrayList changeRegistration(String charterapi, String mac, String ams_ip) throws IOException {
        System.out.println("[DBG] changeRegistration " + mac + " to ams " + ams_ip + " via charterapi: " + charterapi);
        HttpPost request = new HttpPost(charterapi + postfixSettings + "?requestor=AMS");
        request.setEntity(new StringEntity(generateJsonChangeRegistration(mac, ams_ip)));
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
        arrayList.add(1, checkResponseBody(readResponse(new StringBuilder(),response)));
        System.out.println("[DBG] return data: " + arrayList);
        return arrayList;
    }

    ArrayList checkRegistration(String charterapi, String mac) throws IOException {
        System.out.println("[DBG] checkRegistration " + mac + " via charterapi: " + charterapi);
        HttpGet request = new HttpGet(charterapi + postfixSettings + "/amsIp/" + mac);
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
        arrayList.add(1, checkResponseBody(readResponse(new StringBuilder(),response)));
        System.out.println("[DBG] return data: " + arrayList);
        return arrayList;
    }

    private String generateJsonReminderSchedule() {
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
        if(SHOW_GENERATED_JSON) {
            System.out.println("generated json: " + result);
        }
        return result;
    }

    private String generateJsonChangeRegistration(String mac, String ams_ip) {
        //String json = "{\"setting\":{\"groups\":[{\"options\":[],\"id\":\"STBmacaddress\",\"type\":\"device-stb\",\"amsid\":\"" + amsIp + "\"}]}}";
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
        if(SHOW_GENERATED_JSON) {
            System.out.println("generated json: " + result);
        }
        return result;
    }

    //todo
    private String generateJsonReminderDeleteMultiple(String mac, int reminderScheduleId, int reminderId) {
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
        if(SHOW_GENERATED_JSON) {
            System.out.println("generated json: " + result);
        }
        return result;
    }

    //todo
    private String generateJsonReminderDeleteMultiple2(String mac, int reminderScheduleId, int reminderId) {
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
        if(SHOW_GENERATED_JSON) {
            System.out.println("generated json: " + result);
        }
        return result;
    }

    public ArrayList getAllReminder(String charterapi, String deviceId, int lineupId) throws IOException {
        System.out.println("getAllReminder for " +  deviceId + " via charterapi: " + charterapi);
        long start = System.currentTimeMillis();
        HttpResponse response = HttpClients.createDefault().execute(prepareGetRequest(charterapi + "/remindersmiddle/v1/reminders?deviceId=" + deviceId + "&lineupId=" + lineupId));
        long finish = System.currentTimeMillis();
        System.out.print("[DBG] " + (finish-start) + "ms request");

        ArrayList arrayList = new ArrayList();
        arrayList.add(0, response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
        arrayList.add(1, checkResponseBody(readResponse(new StringBuilder(),response)));
        System.out.println("[DBG] return data: " + arrayList);
        return arrayList;
    }

    public ArrayList getStbReminder(String charterapi, String deviceId) throws IOException {
        System.out.println("getStbReminder for "+ deviceId + " via charterapi: " + charterapi);
        long start = System.currentTimeMillis();
        HttpResponse response = HttpClients.createDefault().execute(prepareGetRequest(charterapi + "/remindersmiddle/v1/stbReminders?deviceId=" + deviceId));
        long finish = System.currentTimeMillis();
        System.out.print("[DBG] " + (finish-start) + "ms request");

        ArrayList arrayList = new ArrayList();
        arrayList.add(0, response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
        arrayList.add(1, checkResponseBody(readResponse(new StringBuilder(),response)));
        System.out.println("[DBG] return data: " + arrayList);
        return arrayList;
    }

    public ArrayList deleteMultipleReminders(String charterapi, String deviceId, int reminderScheduleId, int reminderId) throws IOException {
        if(SHOW_DEBUG_LEVEL) {
            System.out.println("deleteMultipleReminders with reminderScheduleId=" + reminderScheduleId + " and reminderId=" + reminderId);
        }
        HttpPost request = new HttpPost(charterapi + "/remindersmiddle/v1/reminders/deleteMultipleReminders");
        request.setEntity(new StringEntity(generateJsonReminderDeleteMultiple2(deviceId, reminderScheduleId, reminderId)));
        System.out.println("[DBG] Request string: " + request);

        long start = System.currentTimeMillis();
        HttpResponse response = HttpClients.createDefault().execute(request);
        long finish = System.currentTimeMillis();
        System.out.print("[DBG] " + (finish-start) + "ms request");

        ArrayList arrayList = new ArrayList();
        arrayList.add(0, response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
        arrayList.add(1, checkResponseBody(readResponse(new StringBuilder(),response)));
        System.out.println("[DBG] return data: " + arrayList);
        return arrayList;
    }

    public ArrayList scheduleReminder(String charterapi, String deviceId, int lineupId) throws IOException {
        System.out.println("Schedule_a_reminder:");
        HttpPost request = new HttpPost(charterapi + "/remindersmiddle/v1/reminders?lineupId=" + lineupId + "&deviceId=" + deviceId);
        request.setHeader("Content-type", "application/json");
        request.setHeader("Cache-Control", "no-cache");
        request.setEntity(new StringEntity(generateJsonReminderSchedule()));
        System.out.println("[DBG] Request string: " + request);

        long start = System.currentTimeMillis();
        HttpResponse response = HttpClients.createDefault().execute(request);
        long finish = System.currentTimeMillis();
        System.out.print("[DBG] " + (finish-start) + "ms request");

        ArrayList arrayList = new ArrayList();
        arrayList.add(0, response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
        arrayList.add(1, checkResponseBody(readResponse(new StringBuilder(), response)));
        System.out.println("[DBG] return data: " + arrayList);
        return arrayList;
    }

}
