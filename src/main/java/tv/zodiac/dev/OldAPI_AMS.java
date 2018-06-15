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
    ArrayList request_perf(String ams_ip, String mac, Enum<Operation> operation, int i, int count_reminders, long reminderChannelNumber, String reminderProgramStart, String reminderProgramId, long reminderOffset) throws IOException {
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

        ArrayList list = new ArrayList();
        list.add(0, response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
        list.add(1, check_body_response(read_response(new StringBuilder(),response), mac));
        if (list.get(1).equals("")) {
            if (operation.name().equals("add")) {
                add_list.add(current);
                int[] min = get_min(Operation.add, current, i);
                int[] max = get_max(Operation.add, current, i);
                //use add_list.size() = total of success iteration!
                int total_i = add_list.size();
                list.add(2, current);
                list.add(3, get_average(add_list));
                list.add(4, search_median(add_list, Sorting.selection));
                list.add(5, min[0]);
                list.add(6, min[1]);
                list.add(7, max[0]);
                list.add(8, max[1]);
                list.add(9, total_i);
                //logger(DEBUG_LEVEL, "[DBG] add avg = " + get_average(add_list) + "ms/" + total_i + ": add_list:" + add_list);

            } else if (operation.name().equals("delete")) {
                delete_list.add(current);
                int[] min = get_min(Operation.delete, current, i);
                int[] max = get_max(Operation.delete, current, i);
                //use delete_list.size() = total of success iteration!
                int total_i = delete_list.size();
                list.add(2, current);
                list.add(3, get_average(delete_list));
                list.add(4, search_median(delete_list, Sorting.selection));
                list.add(5, min[0]);
                list.add(6, min[1]);
                list.add(7, max[0]);
                list.add(8, max[1]);
                list.add(9, total_i);
                //logger(DEBUG_LEVEL, "[DBG] delete avg = " + get_average(delete_list) + "ms/" + total_i + ": delete_list:" + delete_list);
            }
        }
        return list;
    }

    /** Purge method
     * @param ams_ip
     * @param mac
     * @return
     * @throws IOException
     */
    ArrayList request_perf(String ams_ip, String mac, Enum<Operation> operation, int i) throws IOException {
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

        ArrayList list = new ArrayList();
        list.add(0, response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
        list.add(1, check_body_response(read_response(new StringBuilder(),response), mac));
        if (list.get(1).equals("")) {
            purge_list.add(current);
            int[] min = get_min(Operation.purge, current, i);
            int[] max = get_max(Operation.purge, current, i);
            //use purge_list.size() = total of success iteration!
            int total_i = purge_list.size();
            list.add(2, current);
            list.add(3, get_average(purge_list));
            list.add(4, search_median(purge_list, Sorting.selection));
            list.add(5, min[0]);
            list.add(6, min[1]);
            list.add(7, max[0]);
            list.add(8, max[1]);
            list.add(9, total_i);
            //logger(DEBUG_LEVEL, "[DBG] purge avg = " + get_average(purge_list) + "ms/" + total_i + ": purge_list:" + purge_list);
        }
        return list;
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
