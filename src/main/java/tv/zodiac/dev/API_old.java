package tv.zodiac.dev;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

class API_old extends API_common {

    /** Add / Delete method
     * @param server
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
    ArrayList requestPerformance(String server, String mac, Enum<Operation> operation, int i, int count_reminders, long reminderChannelNumber, String reminderProgramStart, String reminderProgramId, long reminderOffset) throws IOException {
        logger(INFO_LEVEL, "[INF] " + new Date() + ": " + operation.toString().toUpperCase() + ":");

        HttpPost request = new HttpPost(prepareUrl(server, operation,false));
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        request.setEntity(new StringEntity(generateJsonReminder(mac, count_reminders, operation, reminderChannelNumber, reminderProgramStart, reminderProgramId, reminderOffset)));
        logger(DEBUG_LEVEL, "[DBG] request string: " + request);

        long start = System.currentTimeMillis();
        HttpResponse response = HttpClients.createDefault().execute(request);
        long finish = System.currentTimeMillis();
        int current = (int)(finish-start);
        logger(DEBUG_LEVEL, "[DBG] " + current + "ms request");

        ArrayList list = new ArrayList();
        //list.add(0, response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
        list.add(0, response.getStatusLine().getStatusCode());
        list.add(1, checkResponseBody(readResponse(new StringBuilder(),response)));
        if (list.get(0).equals(HttpStatus.SC_OK)) {
            if (operation.name().equals("add")) {
                add_list.add(current);
                list.add(2, current);
                list.add(3, getAverage(add_list));
                list.add(4, searchMedian(add_list, sorting));
                int[] min = getMin(Operation.add, current, i);
                list.add(5, min[0]);
                list.add(6, min[1]);
                int[] max = getMax(Operation.add, current, i);
                list.add(7, max[0]);
                list.add(8, max[1]);
                //use add_list.size() = total of success iteration!
                list.add(9, add_list.size());
                //logger(DEBUG_LEVEL, "[DBG] add avg = " + getAverage(add_list) + "ms/" + total_i + ": add_list:" + add_list);

            } else if (operation.name().equals("delete")) {
                delete_list.add(current);
                list.add(2, current);
                list.add(3, getAverage(delete_list));
                list.add(4, searchMedian(delete_list, Sorting.insertion));
                int[] min = getMin(Operation.delete, current, i);
                list.add(5, min[0]);
                list.add(6, min[1]);
                int[] max = getMax(Operation.delete, current, i);
                list.add(7, max[0]);
                list.add(8, max[1]);
                //use delete_list.size() = total of success iteration!
                list.add(9, delete_list.size());
                //logger(DEBUG_LEVEL, "[DBG] delete avg = " + getAverage(delete_list) + "ms/" + total_i + ": delete_list:" + delete_list);
            }
        }
        return list;
    }

    /** Purge method
     * @param server
     * @param mac
     * @return
     * @throws IOException
     */
    ArrayList requestPerformance(String server, String mac, Enum<Operation> operation, int i) throws IOException {
        logger(INFO_LEVEL, "[INF] " + new Date() + ": " + operation.toString().toUpperCase() + ":");

        HttpPost request = new HttpPost(prepareUrl(server, operation, false));
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        request.setEntity(new StringEntity(generateJsonReminderPurge(mac)));
        logger(DEBUG_LEVEL, "[DBG] request string: " + request);

        long start = System.currentTimeMillis();
        HttpResponse response = HttpClients.createDefault().execute(request);
        long finish = System.currentTimeMillis();
        int current = (int)(finish-start);
        logger(DEBUG_LEVEL, "[DBG] " + current + "ms request");

        ArrayList list = new ArrayList();
        //list.add(0, response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
        list.add(0, response.getStatusLine().getStatusCode());
        list.add(1, checkResponseBody(readResponse(new StringBuilder(),response)));
        if (list.get(0).equals(HttpStatus.SC_OK)) {
            purge_list.add(current);
            list.add(2, current);
            list.add(3, getAverage(purge_list));
            list.add(4, searchMedian(purge_list, sorting));
            int[] min = getMin(Operation.purge, current, i);
            list.add(5, min[0]);
            list.add(6, min[1]);
            int[] max = getMax(Operation.purge, current, i);
            list.add(7, max[0]);
            list.add(8, max[1]);
            //use purge_list.size() = total of success iteration!
            list.add(9, purge_list.size());
            //logger(DEBUG_LEVEL, "[DBG] purge avg = " + getAverage(purge_list) + "ms/" + total_i + ": purge_list:" + purge_list);
        }
        return list;
    }

    private String generateJsonReminder(String mac, int count_reminders, Enum<Operation> operation, long reminderChannelNumber, String reminderProgramStart, String reminderProgramId, long reminderOffset) throws IOException {
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
            //object_in_reminders.put("reminderProgramStart", reminderProgramStart + " " + getTime(count_reminders, i+1));
            object_in_reminders.put("reminderProgramStart", getDateTime(i));
            object_in_reminders.put("reminderProgramId", reminderProgramId);
            object_in_reminders.put("reminderOffset", reminderOffset);

            array_reminders.add(object_in_reminders);
        }

        if(show_generated_json && show_info_level) {
            logger(INFO_LEVEL, "[JSON] generated json: " + json);
        }
        return json.toString();
    }

    private String generateJsonReminderPurge(String mac) throws IOException {
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
