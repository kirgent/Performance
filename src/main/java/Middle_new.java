import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

class Middle_new extends Middle {

    //new API
    //https://chalk.charter.com/pages/viewpage.action?pageId=115175031
    private static String postfix_add = "/ams/Reminders?req=add";
    private static String postfix_delete = "/ams/Reminders?req=delete";
    private static String postfix_modify = "/ams/Reminders?req=modify";
    private static String postfix_purge = "/ams/Reminders?req=purge";

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
            for (String aRack_date : rack_date) {
                for (String aRack_channel : rack_channel) {
                    /*System.out.println("[DBG] [date] iteration=" + c + "/" + count_iterations + ", " +
                            "channel=" + aRack_channel + ", " +
                            "date=" + aRack_date);*/
                    log.info("iteration=" + c + "/" + count_iterations + ", date=" + aRack_date + ", channel=" + aRack_channel + ", " +
                            "channel=" + aRack_channel + ", " +
                            "date=" + aRack_date);

                    String json = Generate_json(macaddress, count_reminders, "Add", aRack_channel, aRack_date, get_rack_time(count_reminders), reminderProgramId, reminderOffset);
                    System.out.println(json);

                    StringEntity entity = new StringEntity(json);
                    request.setEntity(entity);

                    //request.setHeader("Accept", "application/json");
                    request.setHeader("Content-type", "application/json");
                    //request.setHeader("Content-type", "text/plain");
                    //request.setHeader("User-Agent","curl/7.58.0");
                    //request.setHeader("Charset", "UTF-8");

                    System.out.println("[DBG] Request string: " + request);
                            //+ "\n[DBG] Request json string: "+json_add48
                            //+ "\n[DBG] Request entity: " + request.getEntity());

                    long start = System.currentTimeMillis();
                    HttpResponse response = client.execute(request);
                    long finish = System.currentTimeMillis();
                    System.out.println("[DBG] " + (finish - start) + "ms request" +
                            "[DBG] Response getStatusLine: " + response.getStatusLine());

                    BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
                    StringBuilder body = new StringBuilder();
                    for (String line = null; (line = reader.readLine()) != null; ) {
                        body.append(line);
                        //System.out.println("[DBG] Response body: " + body.append(line).append("\n"));
                    }
                    result = response.getStatusLine().getStatusCode();

                    if(body.toString().contains("\"statusCode\":2")){ log.warning("one or more statusCode's = "+ statuscode[2]); }
                    if(body.toString().contains("\"statusCode\":3")){ log.warning("one or more statusCode's = "+ statuscode[3]); }
                    if(body.toString().contains("\"statusCode\":4")){ log.warning("one or more statusCode's = "+ statuscode[4]); }

                    if (result != 200) { break; }
                }
                if (result != 200) { break; }
            }
            if (result != 200) { break; }
        }
        return result;
    }

    int Modify(String macaddress, int count_reminders, int reminderChannelNumber, String reminderProgramStart, int reminderProgramId, int reminderOffset, int reminderScheduleId, int reminderId) throws IOException, InterruptedException {
        System.out.println("[DBG] [date] Modify: for macaddress=" + macaddress + ", "
                + "count_reminders=" + count_reminders + ", "
                + "count_iterations=" + count_iterations + ", "
                + "reminderOffset=" + reminderOffset + ", "
                + "data count=" + rack_date.length + ", "
                + "channel count=" + rack_channel.length);

        String url = "http://" + ams_ip + ":" + ams_port + postfix_modify;
        HttpClient client = HttpClients.createDefault();
        HttpPost request = new HttpPost(url);

        for (int c = 1; c <= count_iterations; c++) {
            for (String aRack_date : rack_date) {
                for (String aRack_channel : rack_channel) {
                    //System.out.println("[DBG] Delete iteration=" + c + "/" + count_iterations + ", date=" + aRack_date + ", channel=" + aRack_channel);
                    log.info("Delete iteration=" + c + "/" + count_iterations + ", date=" + aRack_date + ", channel=" + aRack_channel + ", " +
                            "channel=" + aRack_channel + ", " +
                            "date=" + aRack_date);

                    StringEntity entity = new StringEntity(Generate_json(macaddress, count_reminders, "Delete", aRack_channel, aRack_date, get_rack_time(count_reminders), reminderProgramId, reminderOffset));
                    request.setEntity(entity);

                    //request.setHeader("Accept", "application/json");
                    request.setHeader("Content-type", "application/json");
                    //request.setHeader("Content-type", "text/plain");
                    //request.setHeader("User-Agent","curl/7.58.0");
                    //request.setHeader("Charset", "UTF-8");

                    System.out.println("[DBG] Request string: " + request);
                        //+ "\n[DBG] Request entity: " + request.getEntity());

                    long start = System.currentTimeMillis();
                    HttpResponse response = client.execute(request);
                    long finish = System.currentTimeMillis();
                    System.out.println("[DBG] " + (finish - start) + "ms request" +
                            "[DBG] Response getStatusLine: " + response.getStatusLine());

                    BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
                    StringBuilder body = new StringBuilder();
                    for (String line = null; (line = reader.readLine()) != null; ) {
                        body.append(line);
                        //System.out.println("[DBG] Response body: " + body.append(line).append("\n"));
                    }
                    result = response.getStatusLine().getStatusCode();

                    if(body.toString().contains("\"statusCode\":2")){ log.warning("one or more statusCode's = "+ statuscode[2]); }
                    if(body.toString().contains("\"statusCode\":3")){ log.warning("one or more statusCode's = "+ statuscode[3]); }
                    if(body.toString().contains("\"statusCode\":4")){ log.warning("one or more statusCode's = "+ statuscode[4]); }

                    if (result != 200) { break; }
                }
                if (result != 200) { break; }
            }
            if (result != 200) { break; }
        }
        return result;
    }

    int Delete(String macaddress, int count_reminders) throws IOException, InterruptedException {
        System.out.println("[DBG] [date] Edit: for macaddress=" + macaddress + ", "
                + "count_reminders=" + count_reminders + ", "
                + "count_iterations=" + count_iterations + ", "
                + "reminderOffset=" + reminderOffset + ", "
                + "data count=" + rack_date.length + ", "
                + "channel count=" + rack_channel.length);

        String url = "http://" + ams_ip + ":" + ams_port + postfix_delete;
        HttpClient client = HttpClients.createDefault();
        HttpPost request = new HttpPost(url);

        for (int c = 1; c <= count_iterations; c++) {
            for (String aRack_date : rack_date) {
                for (String aRack_channel : rack_channel) {
                    //System.out.println("[DBG] Delete iteration=" + c + "/" + count_iterations + ", date=" + aRack_date + ", channel=" + aRack_channel);
                    log.info("Delete iteration=" + c + "/" + count_iterations + ", date=" + aRack_date + ", channel=" + aRack_channel + ", " +
                            "channel=" + aRack_channel + ", " +
                            "date=" + aRack_date);

                    StringEntity entity = new StringEntity(Generate_json(macaddress, count_reminders, "Delete", aRack_channel, aRack_date, get_rack_time(count_reminders), reminderProgramId, reminderOffset));
                    request.setEntity(entity);
                    //request.setHeader("Accept", "application/json");
                    request.setHeader("Content-type", "application/json");
                    //request.setHeader("Content-type", "text/plain");
                    //request.setHeader("User-Agent","curl/7.58.0");
                    //request.setHeader("Charset", "UTF-8");

                    System.out.println("[DBG] Request string: " + request);
                    //+ "\n[DBG] Request entity: " + request.getEntity());

                    long start = System.currentTimeMillis();
                    HttpResponse response = client.execute(request);
                    long finish = System.currentTimeMillis();
                    System.out.println("[DBG] " + (finish - start) + "ms request" +
                            "[DBG] Response getStatusLine: " + response.getStatusLine());

                    BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
                    StringBuilder body = new StringBuilder();
                    for (String line = null; (line = reader.readLine()) != null; ) {
                        body.append(line);
                        //System.out.println("[DBG] Response body: " + body.append(line).append("\n"));
                    }
                    result = response.getStatusLine().getStatusCode();

                    if(body.toString().contains("\"statusCode\":2")){ log.warning("one or more statusCode's = "+ statuscode[2]); }
                    if(body.toString().contains("\"statusCode\":3")){ log.warning("one or more statusCode's = "+ statuscode[3]); }
                    if(body.toString().contains("\"statusCode\":4")){ log.warning("one or more statusCode's = "+ statuscode[4]); }

                    if (result != 200) { break; }
                }
                if (result != 200) { break; }
            }
            if (result != 200) { break; }
        }
        return result;
    }

    /**
     * @param macaddress
     * @return
     * @throws IOException
     */
    ArrayList Purge(String macaddress) throws IOException {
        log.info("Purge for macaddress=" + macaddress);

        String url = "http://" + ams_ip + ":" + ams_port + postfix_purge;
        HttpClient client = HttpClients.createDefault();
        HttpPost request = new HttpPost(url);

        String json_purge = "{\"deviceId\":" + macaddress + ",\"reminders\":[{\"operation\":\"Purge\"}]}";
        StringEntity entity = new StringEntity(json_purge);
        request.setEntity(entity);

        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        //request.setHeader("Content-type", "text/plain");
        //request.setHeader("User-Agent","curl/7.58.0");
        //request.setHeader("Charset", "UTF-8");

        System.out.println("[DBG] Request string: " + request);
        //+ "\n[DBG] Request entity: " + request.getEntity());

        long start = System.currentTimeMillis();
        HttpResponse response = client.execute(request);
        long finish = System.currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms request" +
                "[DBG] Response getStatusLine: " + response.getStatusLine());

        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
        StringBuilder body = new StringBuilder();
        for (String line = null; (line = reader.readLine()) != null; ) {
            System.out.println("[DBG] Response body: " + body.append(line).append("\n"));
        }

   /*     // Считываем json
        Object obj = new JSONParser().parse(json_purge); // Object obj = new JSONParser().parse(new FileReader("JSONExample.json"));
        // Кастим obj в JSONObject
        JSONObject jo = (JSONObject) obj;
        // Достаём firstName and lastName
        String firstName = (String) jo.get("firstName");
        String lastName = (String) jo.get("lastName");
        System.out.println("fio: " + firstName + " " + lastName);
        // Достаем массив номеров
        JSONArray phoneNumbersArr = (JSONArray) jo.get("phoneNumbers");
        Iterator phonesItr = phoneNumbersArr.iterator();
        System.out.println("phoneNumbers:");
        // Выводим в цикле данные массива
        while (phonesItr.hasNext()) {
            JSONObject test = (JSONObject) phonesItr.next();
            System.out.println("- type: " + test.get("type") + ", phone: " + test.get("number"));
        }*/
        ArrayList actual = new ArrayList();
        actual.add(response.getStatusLine().getStatusCode());
        actual.add(response.getStatusLine().getReasonPhrase());
        return actual;
    }

    /**
     * @param operation - can be Add / Modify / Delete
     * @param macaddress - macaddress of the box
     * @param count_reminders - count of reminders to generate in json {..}
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    int Operation(String operation, String macaddress, int count_reminders, int reminderChannelNumber, String reminderProgramStart, int reminderProgramId, int reminderOffset, int reminderScheduleId, int reminderId) throws IOException, InterruptedException {
        log.info(operation + " for macaddress=" + macaddress + ", "
                + "count_reminders=" + count_reminders + ", "
                + "count_iterations=" + count_iterations + ", "
                + "reminderOffset=" + reminderOffset + ", "
                + "data count=" + rack_date.length + ", "
                + "channel count=" + rack_channel.length);

        String url = "http://" + ams_ip + ":" + ams_port + get_postfix(operation);
        HttpClient client = HttpClients.createDefault();
        HttpPost request = new HttpPost(url);

        for (int c = 1; c <= count_iterations; c++) {
            for (String aRack_date : rack_date) {
                for (String aRack_channel : rack_channel) {
                    log.info(operation + " iteration=" + c + "/" + count_iterations + ", date=" + aRack_date + ", channel=" + aRack_channel);

                    StringEntity entity = new StringEntity(Generate_json(macaddress, count_reminders, operation, aRack_channel, aRack_date, get_rack_time(count_reminders), reminderProgramId, reminderOffset));
                    request.setEntity(entity);

                    request.setHeader("Content-type", "application/json");
                    //request.setHeader("Accept", "application/json");
                    //request.setHeader("Content-type", "text/plain");
                    //request.setHeader("User-Agent","curl/7.58.0");
                    //request.setHeader("Charset", "UTF-8");

                    System.out.println("[DBG] Request string: " + request);
                    //+ "\n[DBG] Request entity: " + request.getEntity());

                    long start = System.currentTimeMillis();
                    HttpResponse response = client.execute(request);
                    long finish = System.currentTimeMillis();
                    System.out.println("[DBG] " + (finish - start) + "ms request, " +
                            "Response getStatusLine: " + response.getStatusLine());

                    BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
                    StringBuilder body = new StringBuilder();
                    for (String line = null; (line = reader.readLine()) != null; ) {
                        body.append(line);
                        //System.out.println("[DBG] Response body: " + body.append(line).append("\n"));
                    }
                    result = response.getStatusLine().getStatusCode();

                    if(body.toString().contains("\"statusCode\":2")){ log.warning("one or more statusCode's = "+ statuscode[2]); }
                    if(body.toString().contains("\"statusCode\":3")){ log.warning("one or more statusCode's = "+ statuscode[3]); }
                    if(body.toString().contains("\"statusCode\":4")){ log.warning("one or more statusCode's = "+ statuscode[4]); }

                    if (result != 200) { break; }
                }
                if (result != 200) { break; }
            }
            if (result != 200) { break; }
        }
        return result;
    }

    private String get_postfix(String operation) {
        String result = "";
        if (Objects.equals(operation, "Add")){ result = postfix_add; }
        else if(Objects.equals(operation, "Modify")){ result = postfix_modify; }
        else if(Objects.equals(operation, "Delete")) { result = postfix_delete; }
        return result;
    }

}
