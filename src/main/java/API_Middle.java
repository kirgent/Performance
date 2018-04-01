import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static java.lang.System.currentTimeMillis;

public class API_Middle extends API {

    ArrayList Change_registration(String macaddress, String charterapi, String ams_ip) throws IOException {
        System.out.println("Change_registration "+ macaddress +" to ams " + ams_ip + " via charterapi: " + charterapi);
        //log.info("Change_registration "+ macaddress +" to ams " + ams_ip + " via charterapi: " + charterapi);

        HttpClient client = HttpClients.createDefault();
        HttpPost request = new HttpPost(charterapi + "?requestor=AMS");

        request.setEntity(new StringEntity(generate_json_change_registration(macaddress, ams_ip, "Change_registration")));
        request.setHeader("Content-type", "application/json");
        request.setHeader("Accept", "application/json");
        System.out.println("[DBG] Request string: " + request);
        //+ "\n[DBG] Request json string: " + json_change_registration);

        long start = currentTimeMillis();
        HttpResponse response = client.execute(request);
        long finish = currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms request, " +
                "Response getStatusLine: " + response.getStatusLine());

        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
        StringBuilder body = new StringBuilder();
        for (String line = null; (line = reader.readLine()) != null; ) {
            body.append(line);
            //System.out.println("[DBG] Response body: " + body.append(line).append("\n"));
        }

        ArrayList arrayList = new ArrayList();
        arrayList.add(0, response.getStatusLine().getStatusCode());
        arrayList.add(1, response.getStatusLine().getReasonPhrase());
        arrayList.add(2, check_body_response(body.toString(), macaddress));
        return arrayList;
    }

    ArrayList Check_registration(String macaddress, String charterapi) throws IOException {
        System.out.println("Check_registration "+ macaddress +" via charterapi: " + charterapi);

        HttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(charterapi + "/amsIp/" + macaddress);

        //request.setHeader("Accept", "*/*");
        //request.setHeader("Content-type", "application/json");
        //request.setHeader("Content-type", "text/plain");
        //request.setHeader("Charset", "UTF-8");
        System.out.println("[DBG] Request string: " + request);
        //+"\n[DBG] Request getRequestLine: "+request.getRequestLine());

        long start = currentTimeMillis();
        HttpResponse response = client.execute(request);
        long finish = currentTimeMillis();
        System.out.print("[DBG] " + (finish - start) + "ms request, ");

        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
        StringBuilder body = new StringBuilder();
        for (String line; (line = reader.readLine()) != null; ) {
            System.out.println("response body: " + body.append(line).append("\n"));
        }

        ArrayList arrayList = new ArrayList();
        arrayList.add(0, response.getStatusLine().getStatusCode());
        arrayList.add(1, response.getStatusLine().getReasonPhrase());
        arrayList.add(2, check_body_response(body.toString(), macaddress));
        return arrayList;
    }

}
