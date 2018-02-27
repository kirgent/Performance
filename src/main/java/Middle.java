import com.google.gson.*;
import com.google.gson.annotations.SerializedName;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Ignore;
import org.junit.Test;

import javax.lang.model.type.ArrayType;
import java.io.*;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Middle {
    private String macaddress = "6CB56BBA882C";

    Middle(String macaddress, int count_reminders) {
        this.macaddress = macaddress;
        int count_reminders1 = count_reminders;
    }

    Middle(String macaddress) {
        this.macaddress = macaddress;
    }

    Middle() {
    }

    //new API
    //https://chalk.charter.com/pages/viewpage.action?pageId=115175031
    private static String postfix_add = "/ams/Reminders?req=add";
    private static String postfix_delete = "/ams/Reminders?req=delete";
    private static String postfix_modify = "/ams/Reminders?req=modify";
    private static String postfix_purge = "/ams/Reminders?req=purge";
    //old API
    @Deprecated
    private static String postfix_change = "/ams/Reminders?req=ChangeReminders";


    private static String charterapi_ = "http://spec.partnerapi.engprod-charter.net/api/pub/networksettingsmiddle/ns/settings";
    private static String charterapi_b = "http://specb.partnerapi.engprod-charter.net/api/pub/networksettingsmiddle/ns/settings";
    private static String charterapi_c = "http://specc.partnerapi.engprod-charter.net/api/pub/networksettingsmiddle/ns/settings";
    private static String charterapi_d = "http://specd.partnerapi.engprod-charter.net/api/pub/networksettingsmiddle/ns/settings";
    //"http://specd.partnerapi.engprod-charter.net/api/pub/networksettingsmiddle/ns/settings?requestor=AMS";
    String charterapi = charterapi_b;

    private String ams_ip = "172.30.81.4";
    private int ams_port = 8080;

    private static String statuscode = "code of the reminder processing result, one of the following:" +
            "\n0 - requested action with the reminder was accomplished successfully" +
            "\n2 - reminder is set for time in the past" +
            "\n3 - reminder is set for unknown channel" +
            "\n4 - reminder is unknown, applies to reminder deletion attempts";

    private int result = 0;

    private int count_iterations = 1;
    private String[] rack_date = {"2018-03-01", "2018-03-02"};
    private String[] rack_time48 = {"00:00", "00:30", "01:00", "01:30", "02:00", "02:30", "03:00", "03:30", "04:00", "04:30", "05:00", "05:30",
            "06:00", "06:30", "07:00", "07:30", "08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30",
            "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30",
            "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30", "22:00", "22:30", "23:00", "23:30"};
    private String[] rack_time288 = {"00:00", "00:05", "00:10"};
    private String[] rack_time720 = {"00:00", "00:02", "00:04"};
    private String[] rack_channel = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "11"};

    //private String startmessage="[DBG] date: NEW START: count_iterations="+count_iterations+", RACK_DATA=?, RACK_CHANNELS=?";


    int Purge(String macaddress) throws IOException {
        System.out.println("[DBG] [date] Purge:");
        String url = "http://" + ams_ip + ":" + ams_port + postfix_change;
        HttpClient client = HttpClients.createDefault();
        HttpPost request = new HttpPost(url);

        String json_purge = "{\"deviceId\":" + macaddress + ",\"reminders\":[{\"operation\":\"Purge\"}]}";
        StringEntity entity = new StringEntity(json_purge);
        request.setEntity(entity);

        //request.setHeader("Accept", "application/json");
        //request.setHeader("Content-type", "application/json");
        //request.setHeader("Content-type", "text/plain");
        request.setHeader("charset", "UTF-8");

        System.out.println("[DBG] Request string: " + request.toString()
                //+ "[DBG] Request json string: "+json_purge
                + "[DBG] Request entity: " + request.getEntity());

        HttpResponse response = client.execute(request);
        System.out.println("\n[DBG] Response getStatusLine: " + response.getStatusLine());
        //+ "[DBG] Response string: " + response.toString());

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
        return response.getStatusLine().getStatusCode();
    }

    int Check_registration(String macaddress) throws IOException {
        System.out.println("[DBG] [date] Check_registration:"
                + "\n[DBG] used charterapi: " + charterapi);

        String postfix = "/amsIp/";
        String url = charterapi + postfix + macaddress;
        HttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);

        //request.setHeader("Content-type", "text/plain");
        //request.setHeader("Content-type", "application/json");
        //request.setHeader("charset", "utf-8");
        request.setHeader("User-Agent", "curl/7.58.0");
        //request.setHeader("Charset", "UTF-8");
        System.out.println("[DBG] Request string: " + request.toString());
        //+"\n[DBG] Request getRequestLine: "+request.getRequestLine());

        HttpResponse response = client.execute(request);
        System.out.println("\n[DBG] Response getStatusLine: " + response.getStatusLine());
        //+ "[DBG] Response string: " + response.toString());

        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
        StringBuilder body = new StringBuilder();
        for (String line; (line = reader.readLine()) != null; ) {
            System.out.println("[DBG] Response body: " + body.append(line).append("\n"));
        }

        /*if (response.getStatusLine().getStatusCode() != 200) {
            //assert(response.getStatusLine().getStatusCode(), equalTo(200));
            System.out.println("getStatusCode != 200");
        }*/

        return response.getStatusLine().getStatusCode();
    }

    int Change_registration(String macaddress, String ams_ip) throws IOException {
        System.out.println("[DBG] [date] Change_registration:"
                + "\n[DBG] used charterapi: " + charterapi
                + "\n[DBG] used ams: " + ams_ip + ":" + ams_port);

        String postfix = "?requestor=AMS";
        String url = charterapi + postfix;
        HttpClient client = HttpClients.createDefault();
        HttpPost request = new HttpPost(url);

        String json_change = "{\"setting\":{\"groups\":[{\"options\":[],\"id\":\"STB" + macaddress + "\",\"type\":\"device-stb\",\"amsid\":\"" + ams_ip + "\"}]}}";
        StringEntity entity = new StringEntity(json_change);
        request.setEntity(entity);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        System.out.println("[DBG] Request string: " + request
                + "\n[DBG] Request json string: " + json_change
                + "\n[DBG] Request entity: " + request.getEntity()
                + "\n[DBG] Request headers: " + Arrays.toString(request.getAllHeaders()));

        HttpResponse response = client.execute(request);
        System.out.println("\n[DBG] Response getStatusLine: " + response.getStatusLine());
        //+ "[DBG] Response string: " + response.toString());

        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
        StringBuilder body = new StringBuilder();
        for (String line = null; (line = reader.readLine()) != null; ) {
            System.out.println("[DBG] Response body: " + body.append(line).append("\n"));
        }
        return response.getStatusLine().getStatusCode();
    }

    void All(String macaddress, int count_reminders, int reminderOffset, int reminderOffset_new) throws IOException, InterruptedException {
        System.out.println("[DBG] [date] All:");
        Add(macaddress, count_reminders, reminderOffset);
        Edit(macaddress, count_reminders, reminderOffset, reminderOffset_new);
        Delete(macaddress, count_reminders, reminderOffset);
        System.out.println(statuscode);
    }

    int Add(String macaddress, int count_reminders, int reminderOffset) throws IOException, InterruptedException {
        System.out.println("[DBG] [date] Add: for macaddress=" + macaddress + ", "
                + "count_reminders=" + count_reminders + ", "
                + "count_iterations=" + count_iterations + ", "
                + "reminderOffset=" + reminderOffset + ", "
                + "data count=" + rack_date.length + ", "
                + "channel count=" + rack_channel.length);

        String postfix_change = "/ams/Reminders?req=ChangeReminders";
        String url = "http://" + ams_ip + ":" + ams_port + postfix_change;
        HttpClient client = HttpClients.createDefault();
        HttpPost request = new HttpPost(url);

        //ArrayList<String> date = new ArrayList<>(count_reminders);
        //date.add(rack_date[0]);
        for (int c = 1; c <= count_iterations; c++) {
            System.out.println("[DBG] [date] iteration=" + c + "/" + count_iterations);
            for (int j = 0; j < rack_date.length; j++) {
                //System.out.println("for2");
                for (int k = 0; k < rack_channel.length; k++) {
                    //System.out.println("for3");

                    String json = Generate_json(rack_date[j], count_reminders, "Add", reminderOffset);
                    System.out.println(json);

                    StringEntity entity = new StringEntity(json);
                    request.setEntity(entity);

                    request.setHeader("Accept", "application/json");
                    request.setHeader("Content-type", "application/json");
                    //request.setHeader("Content-type", "text/plain");
                    //request.setHeader("User-Agent","curl/7.58.0");
                    //request.setHeader("Charset", "UTF-8");

                    System.out.println("[DBG] Request string: " + request.toString()
                            //+ "\n[DBG] Request json string: "+json_add48
                            + "\n[DBG] Request entity: " + request.getEntity());
                            //+ "\n[DBG] [date]: iteration=" + c + "/" + count_iterations + ", date=" + rack_date[j] + ", channel=" + rack_channel[k]);

                    HttpResponse response = client.execute(request);
                    System.out.println("\n[DBG] Response getStatusLine: " + response.getStatusLine());
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
            }
        }
        return result;
    }

    @Deprecated
    int Edit(String macaddress, int count_reminders, int reminderOffset, int reminderOffset_new) throws IOException {
        System.out.println("[DBG] [date] Edit: for macaddress=" + macaddress + ", "
                + "count_reminders=" + count_reminders + ", "
                + "count_iterations=" + count_iterations + ", "
                + "reminderOffset=" + reminderOffset + ", "
                + "reminderOffset_new=" + reminderOffset_new + ", "
                + "date count=" + rack_date.length + ", "
                + "channel count=" + rack_channel.length);

        String url = "http://" + ams_ip + ":" + ams_port + postfix_change;
        HttpClient client = HttpClients.createDefault();
        HttpPost request = new HttpPost(url);

        StringEntity entity = new StringEntity(Generate_json(rack_date[0], count_reminders, "Delete", reminderOffset));
        request.setEntity(entity);

        for (int c = 1; c <= count_iterations; c++) {
            for (int j = 0; j <= rack_date.length; j++) {
                for (int k = 0; k <= rack_channel.length; k++) {
                    request.setHeader("Accept", "application/json");
                    //request.setHeader("Content-type", "application/json");
                    //request.setHeader("Content-type", "text/plain");
                    //request.setHeader("charset", "UTF-8");

                    System.out.println("[DBG] Request string: " + request.toString()
                            //+ "\n[DBG] Request json string: " + json_delete48_add48
                            + "\n[DBG] Request entity: " + request.getEntity()
                            + "\n[DBG] date: Edit(delete48 + add48) reminderOffset=" + reminderOffset + ", reminderOffset_new=" + reminderOffset_new + ", iteration=" + c + "/" + count_iterations + ", macaddress=" + macaddress + ", data=" + rack_date[j] + ", channel=" + rack_channel[k]);

                    HttpResponse response = client.execute(request);
                    System.out.println("\n[DBG] Response getStatusLine: " + response.getStatusLine());
                    //+ "[DBG] Response string: " + response.toString());

                    BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
                    StringBuilder body = new StringBuilder();
                    for (String line = null; (line = reader.readLine()) != null; ) {
                        System.out.println("[DBG] Response body: " + body.append(line).append("\n"));
                    }
                    result = response.getStatusLine().getStatusCode();
                }
            }
        }
        return result;
    }

    int Delete(String macaddress, int count_reminders, int reminderOffset) throws IOException {
        System.out.println("[DBG] [date] Edit: for macaddress=" + macaddress + ", "
                + "count_reminders=" + count_reminders + ", "
                + "count_iterations=" + count_iterations + ", "
                + "reminderOffset=" + reminderOffset + ", "
                + "data count=" + rack_date.length + ", "
                + "channel count=" + rack_channel.length);

/*        String json_delete48 = "{\"deviceId\":\"" + macaddress + "\",\"reminders\":[" +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + yyyymmdd + " 00:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + yyyymmdd + " 00:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + yyyymmdd + " 01:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + yyyymmdd + " 01:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + yyyymmdd + " 02:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + yyyymmdd + " 02:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + yyyymmdd + " 03:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + yyyymmdd + " 03:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + yyyymmdd + " 04:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + yyyymmdd + " 04:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + yyyymmdd + " 05:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + yyyymmdd + " 05:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + yyyymmdd + " 06:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + yyyymmdd + " 06:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + yyyymmdd + " 07:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + yyyymmdd + " 07:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + yyyymmdd + " 08:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + yyyymmdd + " 08:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + yyyymmdd + " 09:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + yyyymmdd + " 09:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + yyyymmdd + " 10:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + yyyymmdd + " 10:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + yyyymmdd + " 11:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + yyyymmdd + " 11:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + yyyymmdd + " 12:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + yyyymmdd + " 12:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + yyyymmdd + " 13:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + yyyymmdd + " 13:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + yyyymmdd + " 14:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + yyyymmdd + " 14:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + yyyymmdd + " 15:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + yyyymmdd + " 15:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + yyyymmdd + " 16:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + yyyymmdd + " 16:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + yyyymmdd + " 17:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + yyyymmdd + " 17:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + yyyymmdd + " 18:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + yyyymmdd + " 18:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + yyyymmdd + " 19:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + yyyymmdd + " 19:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + yyyymmdd + " 20:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + yyyymmdd + " 20:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + yyyymmdd + " 21:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + yyyymmdd + " 21:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + yyyymmdd + " 22:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + yyyymmdd + " 22:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + yyyymmdd + " 23:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + yyyymmdd + " 23:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + " }" +
                "]}";
*/
        String url = "http://" + ams_ip + ":" + ams_port + postfix_change;
        HttpClient client = HttpClients.createDefault();
        HttpPost request = new HttpPost(url);

        //StringEntity entity = new StringEntity(json_delete48);
        StringEntity entity = new StringEntity(Generate_json(rack_date[0], 5, "Delete", 0));
        //StringEntity entity = new StringEntity(new FileReader("src/main/resources/json_delete48.json").toString());
        request.setEntity(entity);

        //System.out.println(startmessage);
        for (int c = 1; c <= count_iterations; c++) {
            for (int j = 0; j <= rack_date.length; j++) {
                for (int k = 0; k <= rack_channel.length; k++) {
                    request.setHeader("Accept", "application/json");
                    request.setHeader("Content-type", "application/json");
                    //request.setHeader("Content-type", "text/plain");
                    //request.setHeader("charset", "UTF-8");

                    System.out.println("[DBG] Request string: " + request.toString()
                            //+ "\n[DBG] Request json string: " + json_delete48
                            + "\n[DBG] Request entity: " + request.getEntity()
                            + "\n[DBG] date: Delete rems with reminderOffset=" + reminderOffset + ", iteration=" + c + "/" + count_iterations + ", macaddress=" + macaddress + ", date=" + rack_date[j] + ", channel=" + rack_channel[k]);

                    HttpResponse response = client.execute(request);
                    System.out.println("\n[DBG] Response getStatusLine: " + response.getStatusLine());
                    //+ "[DBG] Response string: " + response.toString());

                    BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
                    StringBuilder body = new StringBuilder();
                    for (String line = null; (line = reader.readLine()) != null; ) {
                        System.out.println("[DBG] Response body: " + body.append(line).append("\n"));
                    }
                    result = response.getStatusLine().getStatusCode();
                }
            }
        }
        return result;
    }

    int Modify(String macaddress, int count_reminders, int reminderOffset, int reminderOffset_new) throws IOException {
        System.out.println("[DBG] [date] start Modify:");
        int result = 500;

        return result;
    }

    private String Generate_json(String date, int count_remindres, String operation, int reminderOffset) {
        System.out.println("[DBG] [date] Generate_json: with date=" + date + ", " +
                "count_reminders=" + count_remindres + ", " +
                "operation=" + operation + ", " +
                "reminderOffset=" + reminderOffset);

        int reminderChannelNumber = 2;
        int reminderProgramId = 0;

        //String json1 = "{\"deviceId\":" + macaddress + ",\"reminders\":[{\"operation\":" + operation + ", \"reminderChannelNumber\":" + reminderChannelNumber + ", \"reminderProgramStart\":" + reminderProgramStart + ", \"reminderProgramId\":" + reminderProgramId + ", \"reminderOffset\":" + reminderOffset + "}]}";
        //String json2 = "{\"deviceId\":" + macaddress + ",\"reminders\":[{\"operation\": \"Delete\", \"reminderChannelNumber\":" + reminderChannelNumber + ", \"reminderProgramStart\":" + reminderProgramStart + ", \"reminderProgramId\":" + reminderProgramId + ", \"reminderOffset\":" + reminderOffset + "},{\"operation\":" + operation + ", \"reminderChannelNumber\":" + reminderChannelNumber + ", \"reminderProgramStart\":" + reminderProgramStart + ", \"reminderProgramId\":" + reminderProgramId + ", \"reminderOffset\":" + reminderOffset + "}]}";


/*        ArrayList<String> date = new ArrayList<>(count_remindres);
        System.out.println("date.size(): " + date.size());
        for (int i = 0; i < date.size(); i++) {
            System.out.println("date.get(i): " + date.get(i));
        }
*/
/*        ArrayList<String> channel = new ArrayList<>();
        for (int i=0; i<rack_channel.length; i++) {
            channel.add(Arrays.toString(rack_channel));
            System.out.println("date.get(i): " + channel.get(i));
        }
*/
        JSONObject resultJson = new JSONObject();
        resultJson.put("deviceId", macaddress);

        JSONArray array = new JSONArray();
        resultJson.put("reminders", array);

        for (int i = 0; i < count_remindres; i++) {
            JSONObject object = new JSONObject();
            object.put("operation", operation);
            object.put("reminderChannelNumber", reminderChannelNumber);
            object.put("reminderProgramId", reminderProgramId);
            object.put("reminderOffset", reminderOffset);
            object.put("reminderProgramStart", date + " " + rack_time48[i]);
            array.add(object);
            //System.out.println("date: " + date + " " + rack_time48[i]);
        }
        return resultJson.toJSONString();
    }

    private void Generate_json2(String date, int count_remindres, String operation, int reminderOffset) {
        System.out.println("[DBG] [date] Generate_json: with date=" + date + ", " +
                "count_reminders=" + count_remindres + ", " +
                "operation=" + operation + ", " +
                "reminderOffset=" + reminderOffset);

         /*
        //WORKING parsing from json_string to Class:
        Gson g = new Gson();
        Reminder reminder = g.fromJson(json_add2, Reminder.class);
        System.out.println("[DBG] parsing from json_string to Class: \nmacaddress: " + reminder.deviceId);
        System.out.println("[DBG] count of reminders in class: " + reminder.reminders.size());
        for(Reminders rems : reminder.reminders){System.out.println(
                    "operation: " + rems.operation + ", " +
                    "reminderChannelNumber: " + rems.reminderChannelNumber + ", " +
                    "reminderProgramStart: " + rems.reminderProgramStart + ", " +
                    "reminderProgramId: " + rems.reminderProgramId + ", " +
                    "reminderOffset: " + rems.reminderOffset);
            }

        //parsing from Class to json_string
        System.out.println("[DBG] parsing from Class to json_string: \n" + g.toJson(reminder));
*/


/*        //WORKING variant for one class Reminder + one class Reminders
        //==============================================================
        final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

        //from class -> to string json
        //class with fields:
        Reminders rs = new Reminders(operation, reminderChannelNumber, reminderProgramStart, reminderProgramId, reminderOffset);
        Reminder r = new Reminder(macaddress, rs);
        //create json structure:
        String json = GSON.toJson(r);
        System.out.println("[DBG] from class -> to string json:\n" + json);

        //from json string -> to class
        Reminder to_class = GSON.fromJson(json, Reminder.class);
        System.out.println("[DBG] from json string -> to class:\n" + to_class.getDeviceId()+ " " + to_class.getClass());
*/


/*
        //from class -> to string json
        Reminder from_class = new Reminder(macaddress, Arrays.asList("operation", "reminderChannelNumber", "reminderProgramStart", "reminderProgramId", "reminderOffset"));
        String json = GSON.toJson(from_class);
        System.out.println("[DBG] from class -> to string json:\n" + json);

        //from json string -> to class
        Reminder to_class = GSON.fromJson(json, Reminder.class);
        System.out.println("[DBG] from json string -> to class:\n" + to_class.getDeviceId()+ " " + to_class.getReminders_list());
*/

/*
        //WORKING
        JsonObject jo = new JsonParser().parse(json_add2).getAsJsonObject();
        System.out.println("1 show full jsonobject: " + jo);
        show only jsonarray:
        String ja = jo.get("reminders").getAsJsonArray().toString();
        System.out.println("2 only jsonarray: " + ja);
*/
    }
}