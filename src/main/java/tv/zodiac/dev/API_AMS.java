package tv.zodiac.dev;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static java.lang.System.currentTimeMillis;

class API_AMS extends API{

    /** method Add/Modify
     * @param mac      - mac of the box
     * @param operation       - can be Add / Modify / Delete / Purge
     * @param count - count of reminders to generate in json {..}
     * @param reminderProgramStart - TBD
     * @param reminderChannelNumber - TBD
     * @param reminderProgramId - TBD
     * @param reminderOffset - TBD
     * @param reminderScheduleId - TBD
     * @param reminderId - TBD
     * @return arrayList
     * @throws IOException -TBD
     */
    ArrayList Request(String mac, Enum<Operation> operation, int count, String reminderProgramStart, long reminderChannelNumber, String reminderProgramId, long reminderOffset, long reminderScheduleId, long reminderId) throws IOException {
        if(show_extra_info) {
            if(count>1){
                System.out.println(operation + " for macaddress=" + mac + " to ams_ip=" + ams_ip + ", "
                        + "count=" + count + ", "
                        + "reminderProgramStart=" + reminderProgramStart + ", "
                        + "reminderChannelNumber=" + reminderChannelNumber + ", "
                        + "reminderProgramId=" + reminderProgramId + ", "
                        + "reminderOffset=" + reminderOffset + ", "
                        + "reminderScheduleId=multi, "
                        + "reminderId=multi");
            } else {
                System.out.println(operation + " for macaddress=" + mac + " to ams_ip=" + ams_ip + ", "
                        + "count=" + count + ", "
                        + "reminderProgramStart=" + reminderProgramStart + ", "
                        + "reminderChannelNumber=" + reminderChannelNumber + ", "
                        + "reminderProgramId=" + reminderProgramId + ", "
                        + "reminderOffset=" + reminderOffset + ", "
                        + "reminderScheduleId=" + reminderScheduleId + ", "
                        + "reminderId=" + reminderId);
            }
        }

        HttpPost request = new HttpPost(prepare_url(operation));
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        request.setEntity(new StringEntity(generate_json_reminder(mac, count, operation, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId)));
        System.out.println("[DBG] Request string: " + request);

        long start = System.currentTimeMillis();
        HttpResponse response = HttpClients.createDefault().execute(request);
        long diff = System.currentTimeMillis() - start;
        System.out.print("[DBG] " + diff + "ms request");

        ArrayList arrayList = new ArrayList();
        arrayList.add(0, response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
        arrayList.add(1, check_body_response(read_response(new StringBuilder(),response).toString(), mac));

        if (operation.name().equals("add")) {
            if (arrayList.get(1).equals("")) {
                add_list.add((int) diff);
                int avg = get_average_time(add_list);
                arrayList.add(2, avg + "ms/" + add_list.size());
                arrayList.add(3, get_min_time(add_list));
                arrayList.add(4, get_max_time(add_list));
                if (show_extra_info) {
                    if (add_list.size() <= 10) {
                        System.out.println("[DBG] add avg = " + avg + "ms/" + add_list.size() + ": add_list:" + add_list);
                    }
                }
            }
        } else if (operation.name().equals("modify")) {
            modify_list.add((int)diff);
            int avg = get_average_time(modify_list);
            arrayList.add(2, avg + "ms/" + modify_list.size());
            arrayList.add(3, get_min_time(modify_list));
            arrayList.add(4, get_max_time(modify_list));
            if(show_extra_info) {
                if(modify_list.size()<=10) {
                    System.out.println("[DBG] modify avg = " + avg + "ms/" + modify_list.size() + ": modify_list:" + modify_list);
                }
            }
        }

        System.out.println("[DBG] return codes: " + arrayList + "\n");
        return arrayList;
    }

    /** method Delete
     * @param mac      - mac of the box
     * @param reminderScheduleId - TBD
     * @param reminderId - TBD
     * @return arrayList
     * @throws IOException -TBD
     */
    ArrayList Request(String mac, Enum<Operation> operation, int count, long reminderScheduleId, long reminderId) throws IOException {
        Boolean used_delete_operation = true;
        if(show_extra_info) {
            if(count>1) {
                System.out.println("delete for macaddress=" + mac + ", ams_ip=" + ams_ip + ", "
                        + "reminderScheduleId=multi, "
                        + "reminderId=multi");
            } else {
                System.out.println("delete for macaddress=" + mac + ", ams_ip=" + ams_ip + ", "
                        + "reminderScheduleId=" + reminderScheduleId + ", "
                        + "reminderId=" + reminderId);
            }
        }

        HttpPost request = new HttpPost(prepare_url(Operation.delete));
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        request.setEntity(new StringEntity(generate_json_reminder_delete(mac, count, reminderScheduleId, reminderId)));
        System.out.println("[DBG] Request string: " + request);

        long start = System.currentTimeMillis();
        HttpResponse response = HttpClients.createDefault().execute(request);
        long diff = System.currentTimeMillis() - start;
        System.out.print("[DBG] " + diff + "ms request");

        ArrayList arrayList = new ArrayList();
        arrayList.add(0, response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
        arrayList.add(1, check_body_response(read_response(new StringBuilder(),response).toString(), mac));
        if (arrayList.get(1).equals("")) {
            delete_list.add((int)diff);
            int avg = get_average_time(delete_list);
            arrayList.add(2, avg + "ms/" + delete_list.size());
            arrayList.add(3, get_min_time(delete_list));
            arrayList.add(4, get_max_time(delete_list));
            if(show_extra_info) {
                if(delete_list.size()<=10) {
                    System.out.println("[DBG] delete avg = " + avg + "ms/" + delete_list.size() + ": delete_list:" + delete_list);
                }
            }

        }
        System.out.println("[DBG] return codes: " + arrayList + "\n");
        return arrayList;
    }

    /** method for Purge
     * @param mac - TBD
     * @param operation - TBD
     * @return arrayList
     * @throws IOException - TBD
     */
    ArrayList Request(String mac, Enum<Operation> operation) throws IOException {
        if(show_extra_info) {
            System.out.println(operation + " for macaddress=" + mac + " to ams_ip=" + ams_ip);
        }

        HttpPost request = new HttpPost(prepare_url(operation));
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        request.setEntity(new StringEntity(generate_json_reminder_purge(mac)));
        System.out.println("[DBG] Request string: " + request);

        long start = currentTimeMillis();
        HttpResponse response = HttpClients.createDefault().execute(request);
        long diff = System.currentTimeMillis() - start;
        System.out.print("[DBG] " + diff + "ms request");

        ArrayList arrayList = new ArrayList();
        arrayList.add(0, response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
        arrayList.add(1, check_body_response(read_response(new StringBuilder(),response).toString(), mac));
        if (arrayList.get(1).equals("")) {
            purge_list.add((int)diff);
            long avg = get_average_time(purge_list);
            arrayList.add(2, avg + "ms/" + purge_list.size());
            arrayList.add(3, get_min_time(purge_list));
            arrayList.add(4, get_max_time(purge_list));
            if(show_extra_info) {
                if(purge_list.size()<=10) {
                    System.out.println("[DBG] purge avg = " + avg + "ms/" + purge_list.size() + ": purge_list:" + purge_list);
                }
            }
        }
        System.out.println("[DBG] return codes: " + arrayList + "\n");
        return arrayList;
    }

    /** 2nd variant of method for Add/Modify/Delete with RACKs:
     * RACK : String[] rack_date
     * RACK : Integer[] rack_channel
     * @param mac - mac of the box
     * @param operation - can be Add / Modify / Delete
     * @param count - count of reminders to generate in json {..}
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
    ArrayList Request(String mac, Enum<Operation> operation, int count, String[] rack_date, int[] rack_channel, String reminderProgramId, int reminderOffset, long reminderScheduleId, long reminderId) throws IOException {
        if(show_extra_info) {
            System.out.println(operation + " for macaddress=" + mac + ", ams_ip=" + ams_ip + ", "
                    + "count=" + count + ", "
                    + "reminderOffset=" + reminderOffset + ", "
                    + "rack_data.length=" + rack_date.length + ", "
                    + "data=" + Arrays.asList(rack_date) + ", "
                    + "rack_channel.length=" + rack_channel.length + ", "
                    + "channel=" + Arrays.asList(rack_channel));
        }

        HttpPost request = new HttpPost(prepare_url(operation));
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        System.out.println("[DBG] Request string: " + request);
        //+ "\n[DBG] Request entity: " + request.getEntity());

        HttpClient client = HttpClients.createDefault();
        ArrayList arrayList = new ArrayList();
        for (String aRack_date : rack_date) {
            for (int aRack_channel : rack_channel) {
                System.out.println("operation= " + operation + ", date=" + aRack_date + ", channel=" + aRack_channel);

                request.setEntity(new StringEntity(generate_json_reminder(mac, count, operation,
                        aRack_date, aRack_channel,
                        reminderProgramId, reminderOffset, reminderScheduleId, reminderId)));

                long start = currentTimeMillis();
                HttpResponse response = client.execute(request);
                long finish = currentTimeMillis();
                System.out.println("[DBG] " + (finish - start) + "ms request" +
                        "Response getStatusLine: " + response.getStatusLine());

                arrayList.add(0, response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
                arrayList.add(1, check_body_response(read_response(new StringBuilder(),response).toString(), mac));

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

    private String generate_json_setting(String mac, String option, String value) {
        //String json = "{\"settings\":{\"groups\":[{\"id\":\"STBmac\",\"type\":\"device-stb\",\"options\":[{\"name\":\"Audio Output\",\"value\":\"HDMI\"}]}]}}";
        JSONObject json = new JSONObject();
        JSONObject object_in_settings = new JSONObject();
        JSONArray array_groups = new JSONArray();

        json.put("settings", object_in_settings);
        object_in_settings.put("groups", array_groups);

        JSONObject object_in_groups = new JSONObject();
        array_groups.add(object_in_groups);
        object_in_groups.put("id", "STB" + mac);
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

    private String generate_json_reminder(String mac, int count, Enum<Operation> operation, String reminderProgramStart, long reminderChannelNumber, String reminderProgramId, long reminderOffset, long reminderScheduleId, long reminderId) {
        boolean first_clean_reminderScheduleId_list = true;
        boolean first_clean_reminderId_list = true;

        if (count < 0) { count = 0; }

        if (count > 1440) { count = 1440; }

        JSONObject json = new JSONObject();
        json.put("deviceId", mac);
        JSONArray array_reminders = new JSONArray();
        json.put("reminders", array_reminders);
        for (int i = 0; i < count; i++) {
            JSONObject object_in_reminders = new JSONObject();
            if (Objects.equals(reminderProgramStart, "")) {
                object_in_reminders.put("reminderProgramStart", "");
            } else {
                object_in_reminders.put("reminderProgramStart", reminderProgramStart + " " + get_time(count, i+1));
            }

            if (reminderChannelNumber == -1) {
                object_in_reminders.put("reminderChannelNumber", "");
            } else if(reminderChannelNumber == -2) {
                object_in_reminders.put("reminderOffset", null);
            } else {
                object_in_reminders.put("reminderChannelNumber", reminderChannelNumber);
            }

            object_in_reminders.put("reminderProgramId", reminderProgramId);

            if (reminderOffset == -1) {
                object_in_reminders.put("reminderOffset", "");
            } else if (reminderOffset == -2) {
                object_in_reminders.put("reminderOffset", null);
            } else {
                object_in_reminders.put("reminderOffset", reminderOffset);
            }

            //MAIN WORKING VARIANT
            if (reminderScheduleId == 0) {
                object_in_reminders.put("reminderScheduleId", 0);
            } else if (reminderScheduleId == -1) {
                object_in_reminders.put("reminderScheduleId", "");
            } else if (reminderScheduleId == -2) {
                object_in_reminders.put("reminderScheduleId", null);
            } else if (reminderScheduleId == -3) {
                if (operation.name().equals("add")) {
                    object_in_reminders.put("reminderScheduleId", reminderScheduleId());
                } else if (operation.name().equals("modify")){
                    object_in_reminders.put("reminderScheduleId", reminderScheduleId_list.get(i));
                }
            } else if (reminderScheduleId == Long.MAX_VALUE) {
                object_in_reminders.put("reminderScheduleId", Long.MAX_VALUE);
            } else if (reminderScheduleId == Long.MIN_VALUE) {
                object_in_reminders.put("reminderScheduleId", Long.MIN_VALUE);
            } else if (count>1 && operation.name().equals("add")) {
                //todo//todo//todo FIXME
                if(first_clean_reminderScheduleId_list) {
                    reminderScheduleId_list.clear();
                    System.out.println("[DBG] 1st clean for reminderScheduleId_list !!!");
                    first_clean_reminderScheduleId_list = false;
                }
                object_in_reminders.put("reminderScheduleId", reminderScheduleId());
            } else if (count>1 && operation.name().equals("modify")) {
                //todo//todo//todo FIXME
                object_in_reminders.put("reminderScheduleId", reminderScheduleId_list.get(i));
            } else {
                object_in_reminders.put("reminderScheduleId", reminderScheduleId);
            }



            if (reminderId == 0) {
                object_in_reminders.put("reminderId", 0);
            } else if (reminderId == -1) {
                object_in_reminders.put("reminderId", "");
            } else if (reminderId == -2) {
                object_in_reminders.put("reminderId", null);
            } else if (reminderId == -3) {
                if (operation.name().equals("add")) {
                    object_in_reminders.put("reminderId", reminderId());
                } else if (operation.name().equals("modify")){
                    object_in_reminders.put("reminderId", reminderId_list.get(i));
                }
            } else if (reminderId == Long.MAX_VALUE) {
                object_in_reminders.put("reminderId", Long.MAX_VALUE);
            } else if (reminderId == Long.MIN_VALUE) {
                object_in_reminders.put("reminderId", Long.MIN_VALUE);
            } else if (count>1 && operation.name().equals("add")) {
                if(first_clean_reminderId_list) {
                    //todo//todo//todo FIXME
                    reminderId_list.clear();
                    System.out.println("[DBG] 1st clean for reminderId_list !!!");
                    first_clean_reminderId_list = false;
                }
                object_in_reminders.put("reminderId", reminderId());
            } else if (count>1 && operation.name().equals("modify")) {
                //todo//todo//todo FIXME
                object_in_reminders.put("reminderId", reminderId_list.get(i));
            } else {
                object_in_reminders.put("reminderId", reminderId);
            }

            array_reminders.add(object_in_reminders);
        }

        String result = json.toString();
        if(show_generated_json) {
            System.out.println("generated json: " + result);
        }

        if(show_extra_info) {
            if(reminderScheduleId_list.size()<=10) {
                System.out.println("reminderScheduleId_list: size=" + reminderScheduleId_list.size());
                System.out.println(": " + reminderScheduleId_list);
            }
            if(reminderId_list.size()<=10) {
                System.out.println("reminderId_list        : size=" + reminderId_list.size());
                System.out.println(": " + reminderId_list);
            }
        }

        //if(operation.name().equals("modify") && !used_delete_operation) {
            //reminderScheduleId_list.clear();
            //reminderId_list.clear();
            //System.out.println("[DBG] !!! reminderX_list-s are CLEARED !!!");
        //}

        return result;
    }

    private String generate_json_reminder_delete(String mac, int count, long reminderScheduleId, long reminderId) {
        JSONObject json = new JSONObject();
        json.put("deviceId", mac);
        JSONArray array_reminders = new JSONArray();
        json.put("reminders", array_reminders);
        for (int i = 0; i < count; i++) {
            JSONObject object_in_reminders = new JSONObject();

            if (reminderScheduleId == 0) {
                object_in_reminders.put("reminderScheduleId", 0);
            } else if (reminderScheduleId == -1) {
                object_in_reminders.put("reminderScheduleId", "");
            } else if (reminderScheduleId == Long.MAX_VALUE) {
                object_in_reminders.put("reminderScheduleId", Long.MAX_VALUE);
            } else if (reminderScheduleId == Long.MIN_VALUE) {
                object_in_reminders.put("reminderScheduleId", Long.MIN_VALUE);
            } else if (count > 1) {
                object_in_reminders.put("reminderScheduleId", reminderScheduleId_list.get(i));
            } else {
                object_in_reminders.put("reminderScheduleId", reminderScheduleId);
            }

            if (reminderId == 0) {
                object_in_reminders.put("reminderId", 0);
            } else if (reminderId == -1) {
                object_in_reminders.put("reminderId", "");
            } else if (reminderId == Long.MAX_VALUE) {
                object_in_reminders.put("reminderId", Long.MAX_VALUE);
            } else if (reminderId == Long.MIN_VALUE) {
                object_in_reminders.put("reminderId", Long.MIN_VALUE);
            } else if (count > 1) {
                object_in_reminders.put("reminderId", reminderId_list.get(i));
            } else {
                object_in_reminders.put("reminderId", reminderId);
            }
            array_reminders.add(object_in_reminders);
        }
        String result = json.toString();
        if (show_generated_json) {
            System.out.println("generated json: " + result);
        }

        if(show_extra_info) {
            if(reminderScheduleId_list.size()<=10) {
                System.out.println("reminderScheduleId_list: size=" + reminderScheduleId_list.size());
                System.out.println(": " + reminderScheduleId_list);
            }
            if(reminderId_list.size()<=10) {
                System.out.println("reminderId_list        : size=" + reminderId_list.size());
                System.out.println(": " + reminderId_list);
            }
        }

        //reminderScheduleId_list.clear();
        //reminderId_list.clear();
        //System.out.println("[DBG] !!! reminderX_list-s are CLEARED !!!");
        return result;
    }

    private String generate_json_reminder_purge(String mac) {
        //String json = "{\"deviceId\":" + mac + ",\"reminders\":[]}";
        JSONObject json = new JSONObject();
        json.put("deviceId", mac);
        JSONArray array_reminders = new JSONArray();
        json.put("reminders", array_reminders);

        String result = json.toString();
        System.out.println("generated json: " + result);
        return result;
    }

    private String prepare_url(Enum<Operation> operation) {
        return "http://" + ams_ip + ":" + ams_port + "/ams/Reminders?req=" + operation;
    }

    ArrayList Change_settings(String mac, String option, String value) throws IOException {
        System.out.println("Change settings for macaddress=" + mac + ", ams_ip=" + ams_ip + " option=" + option + ", value=" + value);
        HttpPost request = new HttpPost("http://" + ams_ip + ":" + ams_port + "/ams/settings");
        request.setHeader("Content-type", "application/json");

        request.setEntity(new StringEntity(generate_json_setting(mac, option, value)));

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
        arrayList.add(1, check_body_response(read_response(new StringBuilder(),response).toString(), mac));
        System.out.println("[DBG] return codes: " + arrayList);
        return arrayList;
    }

}
