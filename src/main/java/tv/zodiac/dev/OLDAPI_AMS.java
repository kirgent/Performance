package tv.zodiac.dev;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

class OLDAPI_AMS extends API{

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
        //if(show_debug_info) {
            System.out.println("[DBG] " + operation + " for macaddress=" + mac + " to ams_ip=" + ams_ip + ", "
                    + "count_reminders=" + count_reminders + ", "
                    + "operation=" + operation + ", "
                    + "reminderChannelNumber=" + reminderChannelNumber + ", "
                    + "reminderProgramStart=" + reminderProgramStart + ", "
                    + "reminderProgramId=" + reminderProgramId + ", "
                    + "reminderOffset=" + reminderOffset);
        //}

        HttpPost request = new HttpPost(prepare_url(ams_ip, operation,false));
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        request.setEntity(new StringEntity(generate_json_reminder(mac, count_reminders, operation, reminderChannelNumber, reminderProgramStart, reminderProgramId, reminderOffset)));
        if(show_debug_info) {
            System.out.println("[DBG] Request string: " + request);
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
                arrayList.add(3, get_min_time(add_avg_list));
                arrayList.add(4, get_max_time(add_avg_list));
                if (show_debug_info) {
                    if (add_avg_list.size() <= 10) {
                        System.out.println("[DBG] add avg = " + avg + "ms/" + add_avg_list.size() + ": add_list:" + add_avg_list);
                    }
                }
            } else if (operation.name().equals("delete")) {
                delete_avg_list.add((int) diff);
                int avg = get_average_time(delete_avg_list);
                arrayList.add(2, avg + "ms/" + delete_avg_list.size());
                arrayList.add(3, get_min_time(delete_avg_list));
                arrayList.add(4, get_max_time(delete_avg_list));
                if (show_debug_info) {
                    if (delete_avg_list.size() <= 10) {
                        System.out.println("[DBG] modify avg = " + avg + "ms/" + delete_avg_list.size() + ": modify_list:" + delete_avg_list);
                    }
                }
            }
        }

        System.out.println("[DBG] return codes: " + arrayList + "\n");
        return arrayList;
    }

    /** Purge method
     * @param ams_ip
     * @param mac
     * @return
     * @throws IOException
     */
    ArrayList request(String ams_ip, String mac) throws IOException {
        //if(show_debug_info) {
            System.out.println("[DBG] purge for macaddress=" + mac + " to ams_ip=" + ams_ip);
        //}

        HttpPost request = new HttpPost(prepare_url(ams_ip, Operation.purge, false));
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        request.setEntity(new StringEntity(generate_json_reminder_purge(mac)));
        if(show_debug_info) {
            System.out.println("[DBG] Request string: " + request);
        }

        long start = System.currentTimeMillis();
        HttpResponse response = HttpClients.createDefault().execute(request);
        long diff = System.currentTimeMillis() - start;
        System.out.print("[DBG] " + diff + "ms request");

        ArrayList arrayList = new ArrayList();
        arrayList.add(0, response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
        arrayList.add(1, check_body_response(read_response(new StringBuilder(),response).toString(), mac));

        System.out.println("[DBG] return codes: " + arrayList + "\n");
        return arrayList;
    }

    private String generate_json_reminder(String mac, int count_reminders, Enum<Operation> operation, long reminderChannelNumber, String reminderProgramStart, String reminderProgramId, long reminderOffset) {
        if (count_reminders < 0) { count_reminders = 0; }
        if (count_reminders > 1440) { count_reminders = 1440; }

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
            object_in_reminders.put("reminderProgramStart", reminderProgramStart + " " + get_time(count_reminders, i+1));
            object_in_reminders.put("reminderProgramId", reminderProgramId);
            object_in_reminders.put("reminderOffset", reminderOffset);

            array_reminders.add(object_in_reminders);
        }

        String result = json.toString();
        if(show_generated_json) {
            System.out.println("generated json: " + result);
        }
        return result;
    }

    private String generate_json_reminder_purge(String mac) {
        JSONObject json = new JSONObject();
        json.put("deviceId", mac);
        JSONArray array_reminders = new JSONArray();
        json.put("reminders", array_reminders);
        JSONObject object_in_reminders = new JSONObject();
        object_in_reminders.put("operation", "Purge");
        array_reminders.add(object_in_reminders);

        String result = json.toString();
        if(show_generated_json) {
            System.out.println("generated json: " + result);
        }
        return result;
    }

}
