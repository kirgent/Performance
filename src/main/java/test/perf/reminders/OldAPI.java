package test.perf.reminders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import test.perf.common.CommonAPI;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

class OldAPI extends CommonAPI {

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
    ArrayList requestPerformance(String server, String mac, Operation operation, int i, int count_reminders, long reminderChannelNumber, String reminderProgramStart, String reminderProgramId, long reminderOffset) throws IOException {
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
        //list.ADD(0, response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
        list.add(0, response.getStatusLine().getStatusCode());
        list.add(1, checkResponseBody(readResponse(new StringBuilder(),response)));
        if (list.get(0).equals(HttpStatus.SC_OK)) {
            if (operation.name().equals("ADD")) {
                addList.add(current);
                list.add(2, current);
                list.add(3, getAverage(addList));
                list.add(4, searchMedian(addList, sorting));
                int[] min = getMin(Operation.ADD, current, i);
                list.add(5, min[0]);
                list.add(6, min[1]);
                int[] max = getMax(Operation.ADD, current, i);
                list.add(7, max[0]);
                list.add(8, max[1]);
                //use addList.size() = total of success iteration!
                list.add(9, addList.size());
                //logger(DEBUG_LEVEL, "[DBG] ADD avg = " + getAverage(addList) + "ms/" + totalI + ": addList:" + addList);

            } else if (operation.name().equals("DELETE")) {
                deleteList.add(current);
                list.add(2, current);
                list.add(3, getAverage(deleteList));
                list.add(4, searchMedian(deleteList, Sorting.INSERTION));
                int[] min = getMin(Operation.DELETE, current, i);
                list.add(5, min[0]);
                list.add(6, min[1]);
                int[] max = getMax(Operation.DELETE, current, i);
                list.add(7, max[0]);
                list.add(8, max[1]);
                //use deleteList.size() = total of success iteration!
                list.add(9, deleteList.size());
                //logger(DEBUG_LEVEL, "[DBG] DELETE avg = " + getAverage(deleteList) + "ms/" + totalI + ": deleteList:" + deleteList);
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
    ArrayList requestPerformance(String server, String mac, Operation operation, int i) throws IOException {
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
        //list.ADD(0, response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
        list.add(0, response.getStatusLine().getStatusCode());
        list.add(1, checkResponseBody(readResponse(new StringBuilder(),response)));
        if (list.get(0).equals(HttpStatus.SC_OK)) {
            purgeList.add(current);
            list.add(2, current);
            list.add(3, getAverage(purgeList));
            list.add(4, searchMedian(purgeList, sorting));
            int[] min = getMin(Operation.PURGE, current, i);
            list.add(5, min[0]);
            list.add(6, min[1]);
            int[] max = getMax(Operation.PURGE, current, i);
            list.add(7, max[0]);
            list.add(8, max[1]);
            //use purgeList.size() = total of success iteration!
            list.add(9, purgeList.size());
            //logger(DEBUG_LEVEL, "[DBG] PURGE avg = " + getAverage(purgeList) + "ms/" + totalI + ": purgeList:" + purgeList);
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

            if(operation.name().equals("ADD")) {
                object_in_reminders.put("operation", "Add");
            } else if (operation.name().equals("DELETE")){
                object_in_reminders.put("operation", "Delete");
            }

            object_in_reminders.put("reminderChannelNumber", reminderChannelNumber);
            //object_in_reminders.put("reminderProgramStart", reminderProgramStart + " " + getTime(count_reminders, i+1));
            object_in_reminders.put("reminderProgramStart", getDateTime(i));
            object_in_reminders.put("reminderProgramId", reminderProgramId);
            object_in_reminders.put("reminderOffset", reminderOffset);

            array_reminders.add(object_in_reminders);
        }

        if(SHOW_GENERATED_JSON && SHOW_INFO_LEVEL) {
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

        if(SHOW_GENERATED_JSON && SHOW_INFO_LEVEL) {
            logger(INFO_LEVEL, "[JSON] generated json: " + json);
        }
        return json.toString();
    }

}
