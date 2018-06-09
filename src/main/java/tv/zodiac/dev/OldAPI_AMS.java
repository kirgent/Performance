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
                arrayList.add(5, get_min(Operation.add, current));
                arrayList.add(6, get_max(Operation.add, current));
                arrayList.add(7, add_list.size());
                logger(DEBUG_LEVEL, "[DBG] add avg = " + avg + "ms/" + add_list.size() + ": add_list:" + add_list);

            } else if (operation.name().equals("delete")) {
                delete_list.add(current);
                int avg = get_average(delete_list);
                arrayList.add(2, current);
                arrayList.add(3, avg);
                arrayList.add(4, search_median(delete_list, sort));
                arrayList.add(5, get_min(Operation.delete, current));
                arrayList.add(6, get_max(Operation.delete, current));
                arrayList.add(7, delete_list.size());
                logger(DEBUG_LEVEL, "[DBG] modify avg = " + avg + "ms/" + delete_list.size() + ": modify_list:" + delete_list);
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
            arrayList.add(5, get_min(Operation.purge, current));
            arrayList.add(6, get_max(Operation.purge, current));
            arrayList.add(7, purge_list.size());
            logger(DEBUG_LEVEL, "[DBG] purge avg = " + avg + "ms/" + purge_list.size() + ": purge_list:" + purge_list);

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
