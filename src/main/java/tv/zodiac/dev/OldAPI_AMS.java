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

class OldAPI_AMS extends API_common {

    /** Add / Delete method
     * @param ams_ip
     * @param mac
     * @param operation
     * @param count_reminders
     * @param reminderChannelNumber
     * @param reminderProgramStart
     * @param reminderProgramId
     * @param reminderOffset
     * @return
     * @throws IOException
     */
    ArrayList request(String ams_ip, String mac, Enum<Operation> operation, int count_reminders, long reminderChannelNumber, String reminderProgramStart, String reminderProgramId, long reminderOffset) throws IOException {
        logger(INFO_LEVEL, "[INF] " + new Date() + ": " + operation.toString().toUpperCase() + ":");

        HttpPost request = new HttpPost(prepare_url(ams_ip, operation,false));
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        request.setEntity(new StringEntity(generate_json_reminder(mac, count_reminders, operation, reminderChannelNumber, reminderProgramStart, reminderProgramId, reminderOffset)));
        logger(DEBUG_LEVEL, "[DBG] request string: " + request);

        long start = System.currentTimeMillis();
        HttpResponse response = HttpClients.createDefault().execute(request);
        long finish = System.currentTimeMillis();
        int diff = (int)(finish-start);
        logger(DEBUG_LEVEL, "[DBG] " + diff + "ms request");

        ArrayList arrayList = new ArrayList();
        arrayList.add(0, response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
        arrayList.add(1, check_body_response(read_response(new StringBuilder(),response), mac));
        if (arrayList.get(1).equals("")) {
            if (operation.name().equals("add")) {
                add_avg_list.add(diff);
                int avg = get_average(add_avg_list);
                arrayList.add(2, diff);
                arrayList.add(3, avg);
                arrayList.add(4, get_median(add_avg_list, sort));
                arrayList.add(5, get_min(add_avg_list));
                arrayList.add(6, get_max(add_avg_list));
                arrayList.add(7, add_avg_list.size());
                logger(DEBUG_LEVEL, "[DBG] add avg = " + avg + "ms/" + add_avg_list.size() + ": add_list:" + add_avg_list);

            } else if (operation.name().equals("delete")) {
                delete_avg_list.add(diff);
                int avg = get_average(delete_avg_list);
                arrayList.add(2, diff);
                arrayList.add(3, avg);
                arrayList.add(4, get_median(delete_avg_list, sort));
                arrayList.add(5, get_min(delete_avg_list));
                arrayList.add(6, get_max(delete_avg_list));
                arrayList.add(7, delete_avg_list.size());
                logger(DEBUG_LEVEL, "[DBG] modify avg = " + avg + "ms/" + delete_avg_list.size() + ": modify_list:" + delete_avg_list);
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

        return arrayList;
    }

    /** Purge method
     * @param ams_ip
     * @param mac
     * @return
     * @throws IOException
     */
    ArrayList request(String ams_ip, String mac, Enum<Operation> operation) throws IOException {
        logger(INFO_LEVEL, "[INF] " + new Date() + ": " + operation.toString().toUpperCase() + ":");

        HttpPost request = new HttpPost(prepare_url(ams_ip, Operation.purge, false));
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        request.setEntity(new StringEntity(generate_json_reminder_purge(mac)));
        logger(DEBUG_LEVEL, "[DBG] request string: " + request);

        long start = System.currentTimeMillis();
        HttpResponse response = HttpClients.createDefault().execute(request);
        long finish = System.currentTimeMillis();
        int diff = (int)(finish-start);
        logger(DEBUG_LEVEL, "[DBG] " + diff + "ms request");

        ArrayList arrayList = new ArrayList();
        arrayList.add(0, response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
        arrayList.add(1, check_body_response(read_response(new StringBuilder(),response), mac));
        if (arrayList.get(1).equals("")) {
            purge_avg_list.add(diff);
            int avg = get_average(purge_avg_list);
            arrayList.add(2, diff);
            arrayList.add(3, avg);
            arrayList.add(4, get_median(purge_avg_list, sort));
            arrayList.add(5, get_min(purge_avg_list));
            arrayList.add(6, get_max(purge_avg_list));
            arrayList.add(7, purge_avg_list.size());
            logger(DEBUG_LEVEL, "[DBG] purge avg = " + avg + "ms/" + purge_avg_list.size() + ": purge_avg_list:" + purge_avg_list);

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
        return arrayList;
    }

    private String generate_json_reminder(String mac, int count_reminders, Enum<Operation> operation, long reminderChannelNumber, String reminderProgramStart, String reminderProgramId, long reminderOffset) throws IOException {
        if (count_reminders < 0) { count_reminders = 0; }
        //if (count_reminders > 1440) { count_reminders = 1440; }

        JSONObject json = new JSONObject();
        json.put("deviceId", mac);
        JSONArray array_reminders = new JSONArray();
        json.put("reminders", array_reminders);
        for (int i = 0; i < count_reminders; i++) {
            JSONObject object_in_reminders = new JSONObject();

            if(operation.name().equals("add")) {
                object_in_reminders.put("operation", "Add");
            } else if (operation.name().equals("delete")){
                object_in_reminders.put("operation", "Delete");
            }

            object_in_reminders.put("reminderChannelNumber", reminderChannelNumber);
            //object_in_reminders.put("reminderProgramStart", reminderProgramStart + " " + get_time(count_reminders, i+1));
            object_in_reminders.put("reminderProgramStart", get_date_time(i));
            object_in_reminders.put("reminderProgramId", reminderProgramId);
            object_in_reminders.put("reminderOffset", reminderOffset);

            array_reminders.add(object_in_reminders);
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
        JSONObject object_in_reminders = new JSONObject();
        object_in_reminders.put("operation", "Purge");
        array_reminders.add(object_in_reminders);

        if(show_generated_json && show_info_level) {
            logger(INFO_LEVEL, "[JSON] generated json: " + json);
        }
        return json.toString();
    }

}
