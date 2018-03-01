import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Middle_new extends Middle_old {

    int Add(String macaddress, int count_reminders) throws IOException, InterruptedException {
        System.out.println("[DBG] [date] Add: for macaddress=" + macaddress + ", "
                + "count_reminders=" + count_reminders + ", "
                + "count_iterations=" + count_iterations + ", "
                + "reminderOffset=" + reminderOffset + ", "
                + "data count=" + rack_date.length + ", "
                + "channel count=" + rack_channel.length);

        String url = "http://" + ams_ip + ":" + ams_port + postfix_add;
        HttpClient client = HttpClients.createDefault();
        HttpPost request = new HttpPost(url);

        for (int c = 1; c <= count_iterations; c++) {
            for (int j = 0; j < rack_date.length; j++) {
                for (int k = 0; k < rack_channel.length; k++) {
                    System.out.println("[DBG] [date] iteration=" + c + "/" + count_iterations + ", channel=" + rack_channel[k]);

                    String json = Generate_json(macaddress, count_reminders, "Add", rack_channel[k], rack_date[j], rack_time, reminderProgramId, reminderOffset);
                    System.out.println(json);

                    StringEntity entity = new StringEntity(json);
                    request.setEntity(entity);

                    request.setHeader("Accept", "application/json");
                    request.setHeader("Content-type", "application/json");
                    //request.setHeader("Content-type", "text/plain");
                    //request.setHeader("User-Agent","curl/7.58.0");
                    //request.setHeader("Charset", "UTF-8");

                    System.out.println("[DBG] Request string: " + request
                            //+ "\n[DBG] Request json string: "+json_add48
                            //+ "\n[DBG] Request entity: " + request.getEntity());
                            + "\n[DBG] [date]: iteration=" + c + "/" + count_iterations + ", date=" + rack_date[j] + ", channel=" + rack_channel[k]);

                    long start = System.currentTimeMillis();
                    HttpResponse response = client.execute(request);
                    long finish = System.currentTimeMillis();
                    System.out.println("[DBG] " + (finish-start) + "ms request");
                    System.out.println("[DBG] Response getStatusLine: " + response.getStatusLine());
                    //+ "[DBG] Response string: " + response.toString());

                    BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
                    StringBuilder body = new StringBuilder();
                    for (String line = null; (line = reader.readLine()) != null; ) {
                        System.out.println("[DBG] Response body: " + body.append(line).append("\n"));
                    }
                    Thread.sleep(1000);
                    result = response.getStatusLine().getStatusCode();
                    if (result != 200) {
                        break;
                    }
                }
                if (result != 200) {
                    break;
                }
            }
        }
        return result;
    }

    int Modify(String macaddress, int count_reminders, int reminderChannelNumber, String reminderProgramStart, int reminderProgramId, int reminderOffset, int reminderScheduleId, int reminderId) throws IOException {
        System.out.println("[DBG] [date] start Modify:");

        //System.out.println("[DBG] [date] Modify iteration=" + c + "/" + count_iterations);

        int result = 500;

        return result;
    }

}
