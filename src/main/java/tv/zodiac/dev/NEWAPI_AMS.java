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

class NEWAPI_AMS extends API{

    /** Add / Modify method
     * @param mac      - mac of the box
     * @param operation       - can be Add / Modify / Delete / Purge
     * @param count_reminders - count_reminders of reminders to generate in json {..}
     * @param reminderProgramStart - TBD
     * @param reminderChannelNumber - TBD
     * @param reminderProgramId - TBD
     * @param reminderOffset - TBD
     * @param reminderScheduleId - TBD
     * @param reminderId - TBD
     * @return arrayList
     * @throws IOException -TBD
     */
    ArrayList request(String ams_ip, String mac, Enum<Operation> operation, int count_reminders, String reminderProgramStart, long reminderChannelNumber, String reminderProgramId, long reminderOffset, long reminderScheduleId, long reminderId) throws IOException {
        //if(show_debug_info) {
            if(count_reminders>1) {
                System.out.println("[DBG] " + operation + " for macaddress=" + mac + " to ams_ip=" + ams_ip + ", "
                        + "count_reminders=" + count_reminders + ", "
                        + "reminderProgramStart=" + reminderProgramStart + ", "
                        + "reminderChannelNumber=" + reminderChannelNumber + ", "
                        + "reminderProgramId=" + reminderProgramId + ", "
                        + "reminderOffset=" + reminderOffset + ", "
                        + "reminderScheduleId=multi, "
                        + "reminderId=multi");
            } else {
                System.out.println("[DBG] " + operation + " for macaddress=" + mac + " to ams_ip=" + ams_ip + ", "
                        + "count_reminders=" + count_reminders + ", "
                        + "reminderProgramStart=" + reminderProgramStart + ", "
                        + "reminderChannelNumber=" + reminderChannelNumber + ", "
                        + "reminderProgramId=" + reminderProgramId + ", "
                        + "reminderOffset=" + reminderOffset + ", "
                        + "reminderScheduleId=" + reminderScheduleId + ", "
                        + "reminderId=" + reminderId);
            }
        //}

        HttpPost request = new HttpPost(prepare_url(ams_ip, operation, true));
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        request.setEntity(new StringEntity(generate_json_reminder(mac, count_reminders, operation, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId)));
        if(show_debug_info) {
            System.out.println("[DBG] request string: " + request);
        }

        long start = System.currentTimeMillis();
        HttpResponse response = HttpClients.createDefault().execute(request);
        long diff = System.currentTimeMillis() - start;
        System.out.print("[DBG] " + diff + "ms request");

        ArrayList arrayList = new ArrayList();
        arrayList.add(0, response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
        arrayList.add(1, check_body_response(read_response(new StringBuilder(),response).toString(), mac));

        if (arrayList.get(1).equals("")) {
            if (operation.name().equals("add")) {
                add_avg_list.add((int) diff);
                int avg = get_average_time(add_avg_list);
                arrayList.add(2, avg + "ms/" + add_avg_list.size());
                arrayList.add(3, get_min_time(add_avg_list) + "ms");
                arrayList.add(4, get_max_time(add_avg_list) + "ms");
                if (show_debug_info) {
                    if (add_avg_list.size() <= 10) {
                        System.out.println("[DBG] add avg = " + avg + "ms/" + add_avg_list.size() + ": add_avg_list:" + add_avg_list);
                    }
                }
            } else if (operation.name().equals("modify")) {
                modify_avg_list.add((int) diff);
                int avg = get_average_time(modify_avg_list);
                arrayList.add(2, avg + "ms/" + modify_avg_list.size());
                arrayList.add(3, get_min_time(modify_avg_list) + "ms");
                arrayList.add(4, get_max_time(modify_avg_list) + "ms");
                if (show_debug_info) {
                    if (modify_avg_list.size() <= 10) {
                        System.out.println("[DBG] modify avg = " + avg + "ms/" + modify_avg_list.size() + ": modify_avg_list:" + modify_avg_list);
                    }
                }
            }
        }

        System.out.println("[DBG] return codes: " + arrayList + "\n");
        return arrayList;
    }

    /** Delete method
     * @param mac      - mac of the box
     * @param reminderScheduleId - TBD
     * @param reminderId - TBD
     * @return arrayList
     * @throws IOException -TBD
     */
    ArrayList request(String ams_ip, String mac, Enum<Operation> operation, int count_reminders, long reminderScheduleId, long reminderId) throws IOException {
        //if(show_debug_info) {
            if(count_reminders>1) {
                System.out.println("[DBG] delete for macaddress=" + mac + ", ams_ip=" + ams_ip + ", "
                        + "reminderScheduleId=multi, "
                        + "reminderId=multi");
            } else {
                System.out.println("[DBG] delete for macaddress=" + mac + ", ams_ip=" + ams_ip + ", "
                        + "reminderScheduleId=" + reminderScheduleId + ", "
                        + "reminderId=" + reminderId);
            }
        //}
        HttpPost request = new HttpPost(prepare_url(ams_ip, Operation.delete, true));
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        request.setEntity(new StringEntity(generate_json_reminder_delete(mac, count_reminders, reminderScheduleId, reminderId)));
        if(show_debug_info) {
            System.out.println("[DBG] request string: " + request);
        }

        long start = System.currentTimeMillis();
        HttpResponse response = HttpClients.createDefault().execute(request);
        long diff = System.currentTimeMillis() - start;
        System.out.print("[DBG] " + diff + "ms request");

        ArrayList arrayList = new ArrayList();
        arrayList.add(0, response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
        arrayList.add(1, check_body_response(read_response(new StringBuilder(),response).toString(), mac));
        if (arrayList.get(1).equals("")) {
            if(arrayList.get(1).equals("")) {
                delete_avg_list.add((int) diff);
                int avg = get_average_time(delete_avg_list);
                arrayList.add(2, avg + "ms/" + delete_avg_list.size());
                arrayList.add(3, get_min_time(delete_avg_list) + "ms");
                arrayList.add(4, get_max_time(delete_avg_list) + "ms");
                if (show_debug_info) {
                    if (delete_avg_list.size() <= 10) {
                        System.out.println("[DBG] delete avg = " + avg + "ms/" + delete_avg_list.size() + ": delete_avg_list:" + delete_avg_list);
                    }
                }
            }
        }
        System.out.println("[DBG] return codes: " + arrayList + "\n");
        return arrayList;
    }

    /** Purge method
     * @param mac - TBD
     * @param operation - TBD
     * @return arrayList
     * @throws IOException - TBD
     */
    ArrayList request(String ams_ip, String mac, Enum<Operation> operation) throws IOException {
        //if(show_debug_info) {
            System.out.println("[DBG] " + operation + " for macaddress=" + mac + " to ams_ip=" + ams_ip);
        //}
        HttpPost request = new HttpPost(prepare_url(ams_ip, operation, true));
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        request.setEntity(new StringEntity(generate_json_reminder_purge(mac)));
        if(show_debug_info) {
            System.out.println("[DBG] request string: " + request);
        }

        long start = currentTimeMillis();
        HttpResponse response = HttpClients.createDefault().execute(request);
        long diff = System.currentTimeMillis() - start;
        System.out.print("[DBG] " + diff + "ms request");

        ArrayList arrayList = new ArrayList();
        arrayList.add(0, response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
        arrayList.add(1, check_body_response(read_response(new StringBuilder(),response).toString(), mac));
        if (arrayList.get(1).equals("")) {
            purge_avg_list.add((int)diff);
            long avg = get_average_time(purge_avg_list);
            arrayList.add(2, avg + "ms/" + purge_avg_list.size());
            arrayList.add(3, get_min_time(purge_avg_list) + "ms");
            arrayList.add(4, get_max_time(purge_avg_list) + "ms");
            if(show_debug_info) {
                if(purge_avg_list.size()<=10) {
                    System.out.println("[DBG] purge avg = " + avg + "ms/" + purge_avg_list.size() + ": purge_avg_list:" + purge_avg_list);
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
     * @param count_reminders - count_reminders of reminders to generate in json {..}
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
    ArrayList request(String ams_ip, String mac, Enum<Operation> operation, int count_reminders, String[] rack_date, int[] rack_channel, String reminderProgramId, int reminderOffset, long reminderScheduleId, long reminderId) throws IOException {
        if(show_debug_info) {
            System.out.println("[DBG] "+ operation + " for macaddress=" + mac + ", ams_ip=" + ams_ip + ", "
                    + "count_reminders=" + count_reminders + ", "
                    + "reminderOffset=" + reminderOffset + ", "
                    + "rack_data.length=" + rack_date.length + ", "
                    + "data=" + Arrays.asList(rack_date) + ", "
                    + "rack_channel.length=" + rack_channel.length + ", "
                    + "channel=" + Arrays.asList(rack_channel));
        }

        HttpPost request = new HttpPost(prepare_url(ams_ip, operation, true));
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        if(show_debug_info) {
            System.out.println("[DBG] request string: " + request);
        }

        HttpClient client = HttpClients.createDefault();
        ArrayList arrayList = new ArrayList();
        for (String aRack_date : rack_date) {
            for (int aRack_channel : rack_channel) {
                System.out.println("operation= " + operation + ", date=" + aRack_date + ", channel=" + aRack_channel);

                request.setEntity(new StringEntity(generate_json_reminder(mac, count_reminders, operation,
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
        if(show_generated_json) {
            System.out.println("generated json: " + result);
        }
        return result;
    }

    private String generate_json_reminder(String mac, int count_reminders, Enum<Operation> operation, String reminderProgramStart, long reminderChannelNumber, String reminderProgramId, long reminderOffset, long reminderScheduleId, long reminderId) {
        boolean first_clean_reminderScheduleId_list = true;
        boolean first_clean_reminderId_list = true;

        if (count_reminders < 0) { count_reminders = 0; }

        if (count_reminders > 1440) { count_reminders = 1440; }

        JSONObject json = new JSONObject();
        json.put("deviceId", mac);
        JSONArray array_reminders = new JSONArray();
        json.put("reminders", array_reminders);
        for (int i = 0; i < count_reminders; i++) {
            JSONObject object_in_reminders = new JSONObject();
            if (Objects.equals(reminderProgramStart, "")) {
                object_in_reminders.put("reminderProgramStart", "");
            } else {
                object_in_reminders.put("reminderProgramStart", reminderProgramStart + " " + get_time(count_reminders, i+1));
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
            } else if (count_reminders>1 && operation.name().equals("add")) {
                //todo//todo//todo FIXME
                if(first_clean_reminderScheduleId_list) {
                    reminderScheduleId_list.clear();
                    if(show_debug_info) {
                        System.out.println("[DBG] preliminary clean reminderScheduleId_list !!!");
                    }
                    first_clean_reminderScheduleId_list = false;
                }
                object_in_reminders.put("reminderScheduleId", reminderScheduleId());
            } else if (count_reminders>1 && operation.name().equals("modify")) {
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
            } else if (count_reminders>1 && operation.name().equals("add")) {
                if(first_clean_reminderId_list) {
                    //todo//todo//todo FIXME
                    reminderId_list.clear();
                    if(show_debug_info) {
                        System.out.println("[DBG] preliminary clean reminderId_list !!!");
                    }
                    first_clean_reminderId_list = false;
                }
                object_in_reminders.put("reminderId", reminderId());
            } else if (count_reminders>1 && operation.name().equals("modify")) {
                //todo//todo//todo FIXME
                object_in_reminders.put("reminderId", reminderId_list.get(i));
            } else {
                object_in_reminders.put("reminderId", reminderId);
            }

            array_reminders.add(object_in_reminders);
        }

        if(show_debug_info) {
            if(reminderScheduleId_list.size()<=10) {
                System.out.println("reminderScheduleId_list: size=" + reminderScheduleId_list.size());
                System.out.println(": " + reminderScheduleId_list);
            }
            if(reminderId_list.size()<=10) {
                System.out.println("reminderId_list        : size=" + reminderId_list.size());
                System.out.println(": " + reminderId_list);
            }
        }

        String result = json.toString();
        if(show_generated_json) {
            System.out.println("generated json: " + result);
        }
        return result;
    }

    private String generate_json_reminder_delete(String mac, int count_reminders, long reminderScheduleId, long reminderId) {
        JSONObject json = new JSONObject();
        json.put("deviceId", mac);
        JSONArray array_reminders = new JSONArray();
        json.put("reminders", array_reminders);
        for (int i = 0; i < count_reminders; i++) {
            JSONObject object_in_reminders = new JSONObject();

            if (reminderScheduleId == 0) {
                object_in_reminders.put("reminderScheduleId", 0);
            } else if (reminderScheduleId == -1) {
                object_in_reminders.put("reminderScheduleId", "");
            } else if (reminderScheduleId == Long.MAX_VALUE) {
                object_in_reminders.put("reminderScheduleId", Long.MAX_VALUE);
            } else if (reminderScheduleId == Long.MIN_VALUE) {
                object_in_reminders.put("reminderScheduleId", Long.MIN_VALUE);
            } else if (count_reminders > 1) {
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
            } else if (count_reminders > 1) {
                object_in_reminders.put("reminderId", reminderId_list.get(i));
            } else {
                object_in_reminders.put("reminderId", reminderId);
            }
            array_reminders.add(object_in_reminders);
        }

        if(show_debug_info) {
            if(reminderScheduleId_list.size()<=10) {
                System.out.println("reminderScheduleId_list: size=" + reminderScheduleId_list.size());
                System.out.println(": " + reminderScheduleId_list);
            }
            if(reminderId_list.size()<=10) {
                System.out.println("reminderId_list        : size=" + reminderId_list.size());
                System.out.println(": " + reminderId_list);
            }
        }

        String result = json.toString();
        if(show_generated_json) {
            System.out.println("generated json: " + result);
        }
        return result;
    }

    private String generate_json_reminder_purge(String mac) {
        //String json = "{\"deviceId\":" + mac + ",\"reminders\":[]}";
        JSONObject json = new JSONObject();
        json.put("deviceId", mac);
        JSONArray array_reminders = new JSONArray();
        json.put("reminders", array_reminders);

        String result = json.toString();
        if(show_generated_json) {
            System.out.println("generated json: " + result);
        }
        return result;
    }

    ArrayList Change_settings(String mac, String option, String value) throws IOException {
        System.out.println("Change settings for macaddress=" + mac + ", ams_ip=" + ams_ip + " option=" + option + ", value=" + value);
        HttpPost request = new HttpPost("http://" + ams_ip + ":" + ams_port + "/ams/settings");
        request.setHeader("Content-type", "application/json");

        request.setEntity(new StringEntity(generate_json_setting(mac, option, value)));

        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        if(show_debug_info) {
            System.out.println("[DBG] request string: " + request);
        }
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
