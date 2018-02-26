import com.google.gson.*;
import com.google.gson.annotations.SerializedName;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class Middle {

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


    private String ams_ip = "172.30.81.4";
    private int ams_port = 8080;

    private static String statuscode = "code of the reminder processing result, one of the following:" +
            "\n0 - requested action with the reminder was accomplished successfully" +
            "\n2 - reminder is set for time in the past" +
            "\n3 - reminder is set for unknown channel" +
            "\n4 - reminder is unknown, applies to reminder deletion attempts";

    private int count_iterations = 1;

    private String data = "2018-02-22";
    private String[] RACK_DATA = {"2018-02-22"};
    //RACK_DATA=( `date +%Y-%m-%d -d "tomorrow +1day"` `date +%Y-%m-%d -d "tomorrow +2day"` `date +%Y-%m-%d -d "tomorrow +3day"` `date +%Y-%m-%d -d "tomorrow +4day"` `date +%Y-%m-%d -d "tomorrow +5day"` `date +%Y-%m-%d -d "tomorrow +6day"` `date +%Y-%m-%d -d "tomorrow +7day"` `date +%Y-%m-%d -d "tomorrow +8day"` `date +%Y-%m-%d -d "tomorrow +9day"` `date +%Y-%m-%d -d "tomorrow +10day"` )

    //private int channel = 2;
    private String channel = "2";
    private String[] RACK_CHANNEL = {"2"};


    //private String startmessage="[DBG] date: NEW START: count_iterations="+count_iterations+", RACK_DATA=?, RACK_CHANNELS=?";

    void Purge(String macaddress) throws IOException {
        System.out.println("[DBG] start Purge:");

        String url = "http://" + ams_ip + ":" + ams_port + postfix_change;
        HttpClient client = HttpClients.createDefault();
        HttpPost request = new HttpPost(url);

        String json_purge = "{\"deviceId\":\"" + macaddress + "\",\"reminders\":[" +
                "{\"operation\":\"Purge\"}" +
                "]}";
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
        System.out.println("\n[DBG] Response string: " + response.toString());
        //+"\n[DBG] Response getStatusLine: "+response.getStatusLine());

        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
        StringBuilder body = new StringBuilder();
        for (String line = null; (line = reader.readLine()) != null; ) {
            System.out.println("[DBG] Response body: " + body.append(line).append("\n"));
        }
        //JSONTokener tokener = new JSONTokener(builder.toString());
        //JSONArray finalResult = new JSONArray(tokener);

        //client.close();


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


    }

    //to check - alternative http connection there:
    public void Purge2_alternative(String macaddress) throws IOException {
        System.out.println("[DBG] start Purge2_alternative:");

        byte[] postData = postfix_change.getBytes();
        String urlstring = "http://" + ams_ip + ":" + ams_port + postfix_change;
        URL url = new URL(urlstring);
        URLConnection connection = url.openConnection();
        HttpURLConnection http = (HttpURLConnection) connection;
        //the same in one:
        //HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        http.setRequestMethod("POST");
        http.setDoOutput(true);
        http.setInstanceFollowRedirects(false);
        //connection.setRequestProperty("Content-type", "text/plain");
        http.setRequestProperty("Content-Type", "application/json");
        http.setRequestProperty("Charset", "utf-8");
        http.setRequestProperty("Content-Length", Integer.toString(postData.length));
        //http.setRequestProperty("Accept-Charset", "UTF-8");
        //http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

        http.setUseCaches(false);
        try (DataOutputStream out = new DataOutputStream(http.getOutputStream())) {
            out.write(postData);
            System.out.println("Response: " + http.getResponseCode() + " " + http.getResponseMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        http.disconnect();


        byte[] out = "{\"username\":\"root\",\"password\":\"password\"}".getBytes(StandardCharsets.UTF_8);
        int length = out.length;

        http.setFixedLengthStreamingMode(length);
        http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        http.connect();

        try (OutputStream os = http.getOutputStream()) {
            os.write(out);
        }
        //Do something with http.getInputStream()
        System.out.println(http.getOutputStream());
        http.disconnect();


/*        Map<String, String> parameters = new HashMap<>();
        parameters.put("param1", "val");

        con.setDoOutput(true);

        //send POST request
        DataOutputStream out = new DataOutputStream(con.getOutputStream());
        out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
        out.flush();
        out.close();

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response1 = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response1.append(inputLine);
        }
        in.close();
        System.out.println(response1.toString());
*/

/*
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        System.out.println(con.getResponseCode());
        in.close();
        con.disconnect();*/



 /*
        String rawData = "id=10";
        String type = "application/x-www-form-urlencoded";
        String encodedData = URLEncoder.encode( rawData, "UTF-8" );
        URL u = new URL("http://www.example.com/page.php");
        HttpURLConnection conn = (HttpURLConnection) u.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty( "Content-Type", type );
        conn.setRequestProperty( "Content-Length", String.valueOf(encodedData.length()));
        OutputStream os = conn.getOutputStream();
        os.write(encodedData.getBytes());
         */

/*      http.setHeader("Accept", "application/json");
        http.setHeader("Content-type", "application/json");

        //HttpURLConnection httpconnection = (HttpURLConnection)((new URL(http://172.30.81.4:8080/ams/Reminders?req=ChangeReminders).openConnection()));
        URLConnection httpcon = (HttpURLConnection) ((new URL("127.0.0.1:8080").openConnection()));
        http.setDoOutput(true);
        http.setRequestProperty("Content-Type", "application/json");
        http.setRequestProperty("Accept", "application/json");
        http.setRequestMethod("POST");
        http.connect();*/
    }

    void Check_registration(String macaddress) throws IOException {
        String charterapi = charterapi_b;
        System.out.println("[DBG] start Check_registration:"
                + "\n[DBG] used charterapi: " + charterapi);

        String postfix = "/amsIp/";
        String url = charterapi + postfix + macaddress;
        HttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);

        //request.setHeader("Content-type", "text/plain");
        //request.setHeader("Content-type", "application/json");
        //request.setHeader("charset", "utf-8");
        request.setHeader("charset", "UTF-8");
        System.out.println("[DBG] Request string: " + request.toString());
        //+"\n[DBG] Request getRequestLine: "+request.getRequestLine());

        HttpResponse response = client.execute(request);
        System.out.println("\n[DBG] Response string: " + response.toString());
        //+"\n[DBG] Response getStatusLine: "+response.getStatusLine());

        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
        StringBuilder body = new StringBuilder();
        for (String line; (line = reader.readLine()) != null; ) {
            System.out.println("[DBG] Response body: " + body.append(line).append("\n"));
        }

        //JSONTokener tokener = new JSONTokener(builder.toString());
        //JSONArray finalResult = new JSONArray(tokener);

        /*if (response.getStatusLine().getStatusCode() != 200) {
            //assert(response.getStatusLine().getStatusCode(), equalTo(200));
            System.out.println("getStatusCode != 200");
        }*/

        //client.close();
    }

    void Change_registration(String macaddress, String ams_ip) throws IOException {
        String charterapi = charterapi_b;
        System.out.println("[DBG] start Change_registration:"
                + "\n[DBG ]used charterapi: " + charterapi
                + "\n[DBG] used ams: " + ams_ip + ":" + ams_port);

        String postfix = "?requestor=AMS";
        String url = charterapi + postfix;
        HttpClient client = HttpClients.createDefault();
        HttpPost request = new HttpPost(url);

        String json_change = "{\"setting\":{\"groups\":[" +
                "{\"options\":[]," +
                "\"id\":\"STB" + macaddress + "\"," +
                "\"type\":\"device-stb\"," +
                "\"amsid\":\"" + ams_ip + "\"}" +
                "]}}";
        StringEntity entity = new StringEntity(json_change);
        request.setEntity(entity);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        System.out.println("[DBG] Request string: " + request
                + "\n[DBG] Request string: " + request.toString()
                + "\n[DBG] Request json string: " + json_change
                + "\n[DBG] Request entity: " + request.getEntity()
                + "\n[DBG] Request headers: " + Arrays.toString(request.getAllHeaders()));

        HttpResponse response = client.execute(request);
        System.out.println("\n[DBG] Response string: " + response.toString());
        //+"\n[DBG] Response getStatusLine: "+response.getStatusLine());

        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
        StringBuilder body = new StringBuilder();
        for (String line = null; (line = reader.readLine()) != null; ) {
            System.out.println("[DBG] Response body: " + body.append(line).append("\n"));
        }
        //JSONTokener tokener = new JSONTokener(builder.toString());
        //JSONArray finalResult = new JSONArray(tokener);

        //client.close();
    }

    void All(String macaddress, int count_reminders, int reminderOffset, int reminderOffset_new) throws IOException, InterruptedException {
        System.out.println("[DBG] start All:");
        Add(macaddress, count_reminders, reminderOffset);
        Edit(macaddress, count_reminders, reminderOffset, reminderOffset_new);
        Delete(macaddress, count_reminders, reminderOffset);
        System.out.println(statuscode);
    }

    void Add(String macaddress, int count_reminders, int reminderOffset) throws IOException, InterruptedException {
        System.out.println("[DBG] [date] start Add 48rems with Offset=" + reminderOffset + ", iteration=?/" + count_iterations + ", macaddress=" + macaddress + ", data=?, channel=?");

        String json_add48 = "{\"deviceId\":\"" + macaddress + "\",\"reminders\":[" +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 00:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 00:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 01:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 01:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 02:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 02:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 03:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 03:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 04:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 04:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 05:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 05:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 06:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 06:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 07:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 07:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 08:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 08:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 09:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 09:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 10:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 10:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 11:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 11:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 12:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 12:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 13:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 13:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 14:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 14:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 15:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 15:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 16:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 16:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 17:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 17:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 18:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 18:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 19:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 19:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 20:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 20:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 21:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 21:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 22:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 22:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 23:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 23:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + " }" +
                "]}";

        String postfix_change = "/ams/Reminders?req=ChangeReminders";
        String url = "http://" + ams_ip + ":" + ams_port + postfix_change;
        HttpClient client = HttpClients.createDefault();
        HttpPost request = new HttpPost(url);

        StringEntity entity = new StringEntity(json_add48);
        request.setEntity(entity);

        for (int i = 1; i <= count_iterations; i++) {
            for (String aRACK_DATA : RACK_DATA) {
                for (String aRACK_CHANNEL : RACK_CHANNEL) {
                    //request.setHeader("Accept", "application/json");
                    //request.setHeader("Content-type", "application/json");
                    //request.setHeader("Content-type", "text/plain");
                    request.setHeader("charset", "UTF-8");

                    System.out.println("[DBG] Request string: " + request.toString()
                            //+ "\n[DBG] Request json string: "+json_add48
                            + "\n[DBG] Request entity: " + request.getEntity()
                            + "\n[DBG] [date]: iteration=" + i + "/" + count_iterations + ", data=" + aRACK_DATA + ", channel=" + aRACK_CHANNEL);

                    HttpResponse response = client.execute(request);
                    System.out.println("\n[DBG] Response string: " + response.toString());
                    //+ "\n[DBG] Response getStatusLine: " + response.getStatusLine());

                    BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
                    StringBuilder body = new StringBuilder();
                    for (String line = null; (line = reader.readLine()) != null; ) {
                        System.out.println("[DBG] Response body: " + body.append(line).append("\n"));
                    }
                    //client.close();
                    //Thread.sleep(1000);
                }
            }
        }
    }

    void Edit(String macaddress, int count_reminders, int reminderOffset, int reminderOffset_new) throws IOException {
        System.out.println("[DBG] start Edit:");

        String json_delete48_add48 = "{\"deviceId\":\"" + macaddress + "\",\"reminders\":[" +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 00:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 00:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 01:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 01:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 02:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 02:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 03:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 03:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 04:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 04:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 05:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 05:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 06:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 06:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 07:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 07:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 08:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 08:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 09:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 09:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 10:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 10:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 11:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 11:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 12:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 12:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 13:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 13:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 14:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 14:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 15:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 15:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 16:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 16:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 17:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 17:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 18:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 18:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 19:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 19:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 20:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 20:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 21:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 21:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 22:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 22:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 23:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 23:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 00:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset_new + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 00:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset_new + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 01:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset_new + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 01:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset_new + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 02:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset_new + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 02:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset_new + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 03:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset_new + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 03:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset_new + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 04:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset_new + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 04:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset_new + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 05:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset_new + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 05:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset_new + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 06:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset_new + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 06:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset_new + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 07:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset_new + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 07:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset_new + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 08:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset_new + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 08:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset_new + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 09:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset_new + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 09:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset_new + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 10:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset_new + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 10:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset_new + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 11:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset_new + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 11:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset_new + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 12:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset_new + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 12:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset_new + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 13:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset_new + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 13:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset_new + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 14:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset_new + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 14:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset_new + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 15:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset_new + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 15:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset_new + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 16:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset_new + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 16:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset_new + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 17:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset_new + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 17:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset_new + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 18:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset_new + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 18:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset_new + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 19:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset_new + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 19:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset_new + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 20:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset_new + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 20:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset_new + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 21:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset_new + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 21:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset_new + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 22:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset_new + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 22:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset_new + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 23:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset_new + "}," +
                "{\"operation\":\"Add\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 23:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset_new + "}" +
                "]}";

        String url = "http://" + ams_ip + ":" + ams_port + postfix_change;
        HttpClient client = HttpClients.createDefault();
        HttpPost request = new HttpPost(url);

        StringEntity entity = new StringEntity(json_delete48_add48);
        request.setEntity(entity);

        //System.out.println(startmessage);
        for (int i = 1; i <= count_iterations; i++) {
            for (String aRACK_DATA : RACK_DATA) {
                for (String aRACK_CHANNEL : RACK_CHANNEL) {
                    //request.setHeader("Accept", "application/json");
                    //request.setHeader("Content-type", "application/json");
                    //request.setHeader("Content-type", "text/plain");
                    request.setHeader("charset", "UTF-8");

                    System.out.println("[DBG] Request string: " + request.toString()
                            //+ "\n[DBG] Request json string: " + json_delete48_add48
                            + "\n[DBG] Request entity: " + request.getEntity()
                            + "\n[DBG] date: Edit(delete 48 + add 48) with Offset=" + reminderOffset + ", offset_new=" + reminderOffset_new + ", iteration=" + i + "/" + count_iterations + ", macaddress=" + macaddress + ", data=" + aRACK_DATA + ", channel=" + aRACK_CHANNEL);

                    HttpResponse response = client.execute(request);
                    System.out.println("\n[DBG] Response string: " + response.toString());
                    //+ "\n[DBG] Response getStatusLine: " + response.getStatusLine());

                    BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
                    StringBuilder body = new StringBuilder();
                    for (String line = null; (line = reader.readLine()) != null; ) {
                        System.out.println("[DBG] Response body: " + body.append(line).append("\n"));
                    }
                }
            }
        }
    }

    void Delete(String macaddress, int count_reminders, int reminderOffset) throws IOException {
        System.out.println("[DBG] start Delete:");

        String json_delete48 = "{\"deviceId\":\"" + macaddress + "\",\"reminders\":[" +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 00:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 00:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 01:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 01:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 02:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 02:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 03:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 03:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 04:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 04:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 05:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 05:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 06:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 06:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 07:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 07:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 08:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 08:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 09:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 09:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 10:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 10:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 11:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 11:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 12:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 12:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 13:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 13:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 14:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 14:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 15:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 15:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 16:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 16:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 17:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 17:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 18:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 18:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 19:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 19:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 20:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 20:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 21:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 21:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 22:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 22:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 23:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}," +
                "{\"operation\":\"Delete\",\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 23:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + " }" +
                "]}";

        String url = "http://" + ams_ip + ":" + ams_port + postfix_change;
        HttpClient client = HttpClients.createDefault();
        HttpPost request = new HttpPost(url);

        //StringEntity entity = new StringEntity(json_delete48);
        StringEntity entity = new StringEntity(new FileReader("src/main/resources/json_delete48.json").toString());
        request.setEntity(entity);

        //System.out.println(startmessage);
        for (int i = 1; i <= count_iterations; i++) {
            for (String aRACK_DATA : RACK_DATA) {
                for (String aRACK_CHANNEL : RACK_CHANNEL) {
                    request.setHeader("Accept", "application/json");
                    request.setHeader("Content-type", "application/json");
                    //request.setHeader("Content-type", "text/plain");
                    request.setHeader("charset", "UTF-8");

                    System.out.println("[DBG] Request string: " + request.toString()
                            //+ "\n[DBG] Request json string: " + json_delete48
                            + "\n[DBG] Request entity: " + request.getEntity()
                            + "\n[DBG] date: Delete 48rems with Offset=" + reminderOffset + ", iteration=" + i + "/" + count_iterations + ", macaddress=" + macaddress + ", data=" + aRACK_DATA + ", channel=" + aRACK_CHANNEL);

                    HttpResponse response = client.execute(request);
                    System.out.println("\n[DBG] Response string: " + response.toString());
                    //+ "\n[DBG] Response getStatusLine: " + response.getStatusLine());

                    BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
                    StringBuilder body = new StringBuilder();
                    for (String line = null; (line = reader.readLine()) != null; ) {
                        System.out.println("[DBG] Response body: " + body.append(line).append("\n"));
                    }
                }
            }
        }
    }

    void read_json() {
    }


    public String generate_json(String json, int count_reminders){
   //     String macaddress = "A0722CB1AF24";
   //     int reminderOffset = 0;
        String result = "";
        return result;
    }

    void generate_json(){

        //String json, String macaddress, String[] channel, String[] data, int[] reminderOffset) {
        System.out.println("generate_json:");


               /* //working variant:
        String json = json_add5;
        Reminder reminder = new Gson().fromJson(json, Reminder.class);
        System.out.println("[DBG] count of reminders in class: " + reminder.reminders.length);
        System.out.println("deviceId:\"" + reminder.deviceId + "\"");
        for (int i = 0; i < reminder.reminders.length; i++) {
            System.out.println("operation:\"" + reminder.reminders[i].getOperation() + "\" " +
                    "reminderChannelNumber:" + reminder.reminders[i].getReminderChannelNumber() + " " +
                    "reminderProgramStart:\"" + reminder.reminders[i].getReminderProgramStart() + "\" " +
                    "reminderProgramId:" + reminder.reminders[i].getReminderProgramId() + " " +
                    "reminderOffset:" + reminder.reminders[i].getReminderOffset());
        }
        String json2 = "";
        */

        String macaddress = "A0722CB1AF24";
        String operation = "Add";
        int reminderChannelNumber = 2;
        String reminderProgramStart = "2018-02-22 00:00";
        //String reminderProgramStart2 = "00:00";
        int reminderProgramId = 0;
        int reminderOffset = 0;


        //WORKING variant for one class Reminder + one class Reminders
        final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

        //from class -> to string json
        //class with fields:
        Reminders[] rs = new Reminders[](operation, reminderChannelNumber, reminderProgramStart, reminderProgramId, reminderOffset);
        Reminder r = new Reminder(macaddress, rs);
        //create json structure:
        String json = GSON.toJson(r);
        System.out.println("[DBG] from class -> to string json:\n" + json);

        //from json string -> to class
        Reminder to_class = GSON.fromJson(json, Reminder.class);
        System.out.println("[DBG] from json string -> to class:\n" + to_class.getDeviceId()+ " " + to_class.getClass());


/*
        // for ListArrays
        final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

        //from class -> to string json
        Reminder from_class = new Reminder(macaddress, Arrays.asList("operation", "reminderChannelNumber", "reminderProgramStart", "reminderProgramId", "reminderOffset"));
        String json = GSON.toJson(from_class);
        System.out.println("[DBG] from class -> to string json:\n" + json);

        //from json string -> to class
        Reminder to_class = GSON.fromJson(json, Reminder.class);
        System.out.println("[DBG] from json string -> to class:\n" + to_class.getDeviceId()+ " " + to_class.getReminders_list());
*/

/*
        String json_add5 = "{\"deviceId\":" + macaddress + ",\"reminders\":["
                + "{\"operation\":" + operation + ",\"reminderChannelNumber\":" + reminderChannelNumber + ",\"reminderProgramStart\":" + reminderProgramStart + ",\"reminderProgramId\":" + reminderProgramId + ",\"reminderOffset\":" + reminderOffset + "},"
                + "{\"operation\":" + operation + ",\"reminderChannelNumber\":" + reminderChannelNumber + ",\"reminderProgramStart\":" + reminderProgramStart + ",\"reminderProgramId\":" + reminderProgramId + ",\"reminderOffset\":" + reminderOffset + "},"
                + "{\"operation\":" + operation + ",\"reminderChannelNumber\":" + reminderChannelNumber + ",\"reminderProgramStart\":" + reminderProgramStart + ",\"reminderProgramId\":" + reminderProgramId + ",\"reminderOffset\":" + reminderOffset + "},"
                + "{\"operation\":" + operation + ",\"reminderChannelNumber\":" + reminderChannelNumber + ",\"reminderProgramStart\":" + reminderProgramStart + ",\"reminderProgramId\":" + reminderProgramId + ",\"reminderOffset\":" + reminderOffset + "},"
                + "{\"operation\":" + operation + ",\"reminderChannelNumber\":" + reminderChannelNumber + ",\"reminderProgramStart\":" + reminderProgramStart + ",\"reminderProgramId\":" + reminderProgramId + ",\"reminderOffset\":" + reminderOffset + "}]}";

        String json_add1 = "{\"deviceId\":" + macaddress + ",\"reminders\":[{\"operation\":" + operation + ", \"reminderChannelNumber\":" + reminderChannelNumber + ", \"reminderProgramStart\":" + reminderProgramStart + ", \"reminderProgramId\":" + reminderProgramId + ", \"reminderOffset\":" + reminderOffset + "}]}";

        //JSONObject obj = new JSONObject("{\"deviceId\":\"A0722CB1AF24\",\"reminders\":[{\"operation\":\"Add\",\"reminderChannelNumber\":2,\"reminderProgramStart\":\"2018-06-06 00:00\",\"reminderProgramId\":0,\"reminderOffset\":0}]}");
        String trimmed = json_add1.trim();
        JsonObject obj = new JsonParser().parse(trimmed ).getAsJsonObject();
        System.out.println("1 full json as object: " + obj);

        String sss = obj.get("reminders").getAsJsonArray().toString();
        System.out.println("2 jsonarray: " + sss);
*/

//        String pageName = obj.getJSONObject("pageInfo").getString("pageName");
//        JSONArray arr = obj.getJSONArray("posts");
//        for (int i = 0; i < arr.length(); i++)
//        {
//            String post_id = arr.getJSONObject(i).getString("post_id");
//    ......
//        }



    }
}