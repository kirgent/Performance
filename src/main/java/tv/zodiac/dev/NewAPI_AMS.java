package tv.zodiac.dev;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import static java.lang.System.currentTimeMillis;

class NewAPI_AMS extends API_common {

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
        logger(INFO_LEVEL, "[INF] " + new Date() + ": " + operation.toString().toUpperCase() + ":");

        HttpPost request = new HttpPost(prepare_url(ams_ip, operation, true));
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        request.setEntity(new StringEntity(generate_json_reminder(mac, count_reminders, operation, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId)));
        logger(DEBUG_LEVEL, "[DBG] request string: " + request);


        long start = System.currentTimeMillis();
        HttpResponse response = HttpClients.createDefault().execute(request);
        long finish = System.currentTimeMillis();
        int current = (int)(finish-start);
        logger(DEBUG_LEVEL, "[DBG] " + current + "ms request");

        start = System.currentTimeMillis();
        ArrayList arrayList = new ArrayList();
        arrayList.add(0, response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
        arrayList.add(1, check_body_response(read_response(new StringBuilder(),response), mac));
        if (arrayList.get(1).equals("")) {
            if (operation.name().equals("add")) {
                add_list.add(current);
                int avg = get_average(add_list);
                arrayList.add(2, current);
                arrayList.add(3, avg);
                arrayList.add(4, search_median(add_list, sort));
                arrayList.add(5, get_min(Operation.add, current, add_list.size()));
                arrayList.add(6, get_max(Operation.add, current, add_list.size()));
                arrayList.add(7, add_list.size());
                logger(DEBUG_LEVEL, "[DBG] " + new Date() + ": add avg = " + avg + "ms" + add_list.size() + ": add_list:" + add_list);

            } else if (operation.name().equals("modify")) {
                modify_list.add(current);
                int avg = get_average(modify_list);
                arrayList.add(2, current);
                arrayList.add(3, avg);
                arrayList.add(4, search_median(modify_list, sort));
                arrayList.add(5, get_min(Operation.modify, current, modify_list.size()));
                arrayList.add(6, get_max(Operation.modify, current, modify_list.size()));
                arrayList.add(7, modify_list.size());
                logger(DEBUG_LEVEL, "[DBG] " + new Date() + ": modify avg = " + avg + "ms/" + modify_list.size() + ": modify_list:" + modify_list);
            }

            logger(INFO_LEVEL, "[INF] return data: [" + arrayList.get(0) + ", " + arrayList.get(1) + "]"
                    + " measurements: cur=" + arrayList.get(2)
                    + ", avg=" + arrayList.get(3)
                    + ", med=" + arrayList.get(4)
                    + ", min=" + arrayList.get(5)
                    + ", max=" + arrayList.get(6)
                    + ", i=" + arrayList.get(7));
        } else {
            logger(INFO_LEVEL, "[INF] return data: [" + arrayList.get(0) + ", " + arrayList.get(1) + "]");
        }
        finish = System.currentTimeMillis();
        logger(INFO_LEVEL, (int)(finish-start) + "ms for parsing request");

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
        logger(INFO_LEVEL, "[INF] " + new Date() + ": " + operation.toString().toUpperCase() + ":");

        HttpPost request = new HttpPost(prepare_url(ams_ip, Operation.delete, true));
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        request.setEntity(new StringEntity(generate_json_reminder_delete(mac, count_reminders, reminderScheduleId, reminderId)));
        logger(DEBUG_LEVEL, "[DBG] request string: " + request);

        long start = System.currentTimeMillis();
        HttpResponse response = HttpClients.createDefault().execute(request);
        long finish = System.currentTimeMillis();
        int current = (int)(finish-start);
        logger(DEBUG_LEVEL, "[DBG] " + current + "ms request");

        start = System.currentTimeMillis();
        ArrayList arrayList = new ArrayList();
        arrayList.add(0, response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
        arrayList.add(1, check_body_response(read_response(new StringBuilder(),response), mac));
        if (arrayList.get(1).equals("")) {
            if(arrayList.get(1).equals("")) {
                delete_list.add(current);
                int avg = get_average(delete_list);
                arrayList.add(2, current);
                arrayList.add(3, avg);
                arrayList.add(4, search_median(delete_list, sort));
                arrayList.add(5, get_min(Operation.delete, current, delete_list.size()));
                arrayList.add(6, get_max(Operation.delete, current, delete_list.size()));
                arrayList.add(7, delete_list.size());
                logger(DEBUG_LEVEL, "[DBG] " + new Date() + ": delete avg = " + avg + "ms/" + delete_list.size() + ": delete_list:" + delete_list);
            }

            logger(INFO_LEVEL, "[INF] return data: [" + arrayList.get(0) + ", " + arrayList.get(1) + "]"
                    + " measurements: cur=" + arrayList.get(2)
                    + ", avg=" + arrayList.get(3)
                    + ", med=" + arrayList.get(4)
                    + ", min=" + arrayList.get(5)
                    + ", max=" + arrayList.get(6)
                    + ", i=" + arrayList.get(7));
        } else {
            logger(INFO_LEVEL, "[INF] return data: [" + arrayList.get(0) + ", " + arrayList.get(1) + "]");
        }
        finish = System.currentTimeMillis();
        logger(INFO_LEVEL, (int)(finish-start) + "ms for parsing request");

        return arrayList;
    }

    /** Purge method
     * @param mac - TBD
     * @param operation - TBD
     * @return arrayList
     * @throws IOException - TBD
     */
    ArrayList request(String ams_ip, String mac, Enum<Operation> operation) throws IOException {
        logger(INFO_LEVEL, "[INF] " + new Date() + ": " + operation.toString().toUpperCase() + ":");

        HttpPost request = new HttpPost(prepare_url(ams_ip, operation, true));
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        request.setEntity(new StringEntity(generate_json_reminder_purge(mac)));
        logger(DEBUG_LEVEL, "[DBG] request string: " + request);

        long start = currentTimeMillis();
        HttpResponse response = HttpClients.createDefault().execute(request);
        long finish = System.currentTimeMillis();
        int current = (int)(finish-start);
        logger(DEBUG_LEVEL, "[DBG] " + current + "ms request");

        start = System.currentTimeMillis();
        ArrayList arrayList = new ArrayList();
        arrayList.add(0, response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
        arrayList.add(1, check_body_response(read_response(new StringBuilder(),response), mac));
        if (arrayList.get(1).equals("")) {
            purge_list.add(current);
            int avg = get_average(purge_list);
            arrayList.add(2, current);
            arrayList.add(3, avg);
            arrayList.add(4, search_median(purge_list, sort));
            arrayList.add(5, get_min(Operation.purge, current, purge_list.size()));
            arrayList.add(6, get_max(Operation.purge, current, purge_list.size()));
            arrayList.add(7, purge_list.size());
            logger(DEBUG_LEVEL,"[DBG] " + new Date() + ": purge avg = " + avg + "ms/" + purge_list.size() + ": purge_list:" + purge_list);

            logger(INFO_LEVEL, "[INF] return data: [" + arrayList.get(0) + ", " + arrayList.get(1) + "]"
                    + " measurements: cur=" + arrayList.get(2)
                    + ", avg=" + arrayList.get(3)
                    + ", med=" + arrayList.get(4)
                    + ", min=" + arrayList.get(5)
                    + ", max=" + arrayList.get(6)
                    + ", i=" + arrayList.get(7));
        } else {
            logger(INFO_LEVEL, "[INF] return data: [" + arrayList.get(0) + ", " + arrayList.get(1) + "]");
        }
        finish = System.currentTimeMillis();
        logger(INFO_LEVEL, (int)(finish-start) + "ms for parsing request");

        return arrayList;
    }

    private String generate_json_setting(String mac, String option, String value) throws IOException {
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

        if(show_generated_json) {
            logger(INFO_LEVEL, "[JSON] generated json: " + json);
        }
        return json.toString();
    }

    private String generate_json_reminder(String mac, int count_reminders, Enum<Operation> operation, String reminderProgramStart, long reminderChannelNumber, String reminderProgramId, long reminderOffset, long reminderScheduleId, long reminderId) throws IOException {
        boolean first_clean_reminderScheduleId_list = true;
        boolean first_clean_reminderId_list = true;

        if (count_reminders < 0) { count_reminders = 0; }

        JSONObject json = new JSONObject();
        json.put("deviceId", mac);
        JSONArray array_reminders = new JSONArray();
        json.put("reminders", array_reminders);
        for (int i = 0; i < count_reminders; i++) {
            JSONObject object_in_reminders = new JSONObject();

            //generation reminderProgramStart
            if (Objects.equals(reminderProgramStart, "-1")) {
                object_in_reminders.put("reminderProgramStart", "");
            } else {
                //object_in_reminders.put("reminderProgramStart", reminderProgramStart + " " + get_time(count_reminders, i+1));
                object_in_reminders.put("reminderProgramStart", get_date_time(i));
            }

            //generation reminderChannelNumber
            if (reminderChannelNumber == -1) {
                object_in_reminders.put("reminderChannelNumber", "");
            } else if(reminderChannelNumber == -2) {
                object_in_reminders.put("reminderOffset", null);
            } else {
                object_in_reminders.put("reminderChannelNumber", reminderChannelNumber);
            }

            //reminderProgramId
            object_in_reminders.put("reminderProgramId", reminderProgramId);

            //generation reminderOffset
            if (reminderOffset == -1) {
                object_in_reminders.put("reminderOffset", "");
            } else if (reminderOffset == -2) {
                object_in_reminders.put("reminderOffset", null);
            } else {
                object_in_reminders.put("reminderOffset", reminderOffset);
            }

            //MAIN WORKING VARIANT
            //generation reminderScheduleId
            if (reminderScheduleId == 0) {
                object_in_reminders.put("reminderScheduleId", 0);
            } else if (reminderScheduleId == -1) {
                object_in_reminders.put("reminderScheduleId", "");
            } else if (reminderScheduleId == -2) {
                object_in_reminders.put("reminderScheduleId", null);
            } else if (reminderScheduleId == 1) {
                if (operation.name().equals("add")) {
                    object_in_reminders.put("reminderScheduleId", reminderScheduleId(Generation.increment));
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
                    logger(DEBUG_LEVEL, "[DBG] preliminary clean reminderScheduleId_list !!!");
                    first_clean_reminderScheduleId_list = false;
                }
                object_in_reminders.put("reminderScheduleId", reminderScheduleId(Generation.random));
            } else if (count_reminders>1 && operation.name().equals("modify")) {
                //todo//todo//todo FIXME
                object_in_reminders.put("reminderScheduleId", reminderScheduleId_list.get(i));
            } else {
                object_in_reminders.put("reminderScheduleId", reminderScheduleId);
            }


            //generation reminderId
            if (reminderId == 0) {
                object_in_reminders.put("reminderId", 0);
            } else if (reminderId == -1) {
                object_in_reminders.put("reminderId", "");
            } else if (reminderId == -2) {
                object_in_reminders.put("reminderId", null);
            } else if (reminderId == 1) {
                if (operation.name().equals("add")) {
                    object_in_reminders.put("reminderId", reminderId(Generation.increment));
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
                    logger(DEBUG_LEVEL,"[DBG] preliminary clean reminderId_list !!!");
                    first_clean_reminderId_list = false;
                }
                object_in_reminders.put("reminderId", reminderId(Generation.random));
            } else if (count_reminders>1 && operation.name().equals("modify")) {
                //todo//todo//todo FIXME
                object_in_reminders.put("reminderId", reminderId_list.get(i));
            } else {
                object_in_reminders.put("reminderId", reminderId);
            }

            array_reminders.add(object_in_reminders);
        }


        if(reminderScheduleId_list.size()<=10) {
            logger(DEBUG_LEVEL,"reminderScheduleId_list : size=" + reminderScheduleId_list.size() + " : " + reminderScheduleId_list);
        }
        if(reminderId_list.size()<=10) {
            logger(DEBUG_LEVEL,"reminderId_list         : size=" + reminderId_list.size() + " : " + reminderId_list);
        }

        if(show_generated_json) {
            logger(INFO_LEVEL, "[JSON] generated json: " + json);
        }
        return json.toString();
    }

    private String generate_json_reminder_delete(String mac, int count_reminders, long reminderScheduleId, long reminderId) throws IOException {
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

        if(show_debug_level) {
            if(reminderScheduleId_list.size()<=10) {
                logger(DEBUG_LEVEL, "reminderScheduleId_list : size=" + reminderScheduleId_list.size() + " : " + reminderScheduleId_list);
            }
            if(reminderId_list.size()<=10) {
                logger(DEBUG_LEVEL, "reminderId_list         : size=" + reminderId_list.size() + " : " + reminderId_list);
            }
        }

        if(show_generated_json && show_info_level) {
            logger(INFO_LEVEL, "[JSON] generated json: " + json);
        }
        return json.toString();
    }

    private String generate_json_reminder_purge(String mac) throws IOException {
        JSONObject json = new JSONObject();
        json.put("deviceId", mac);
        JSONArray array_reminders = new JSONArray();
        json.put("reminders", array_reminders);

        if(show_generated_json && show_info_level) {
            logger(INFO_LEVEL, "[JSON] generated json: " + json);
        }
        return json.toString();
    }

    ArrayList change_settings(String mac, String option, String value) throws IOException {
        logger(INFO_LEVEL, "Change settings for macaddress=" + mac + ", ams_ip=" + ams_ip + " option=" + option + ", value=" + value);
        HttpPost request = new HttpPost("http://" + ams_ip + ":" + ams_port + "/ams/settings");
        request.setHeader("Content-type", "application/json");

        request.setEntity(new StringEntity(generate_json_setting(mac, option, value)));

        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        logger(DEBUG_LEVEL, "[DBG] request string: " + request);
        //+ "\n[DBG] Request entity: " + request.getEntity());

        long start = currentTimeMillis();
        HttpResponse response = HttpClients.createDefault().execute(request);
        long finish = System.currentTimeMillis();
        int current = (int)(finish-start);
        logger(DEBUG_LEVEL, "[DBG] " + current + "ms request");

        ArrayList arrayList = new ArrayList();
        arrayList.add(0, response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
        arrayList.add(1, check_body_response(read_response(new StringBuilder(),response), mac));
        logger(INFO_LEVEL, "[INF] return data: " + arrayList + "\n");
        return arrayList;
    }

}
