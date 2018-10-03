package test.perf.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

class Performance extends CommonAPI {

    ArrayList get(String url, ArrayList expected_list, int i) throws IOException {
        logger(INFO_LEVEL, "[INF] " + new Date());

        HttpGet request = new HttpGet(prepareUrl(url, Operation.www,false));
        logger(DEBUG_LEVEL, "[DBG] request string: " + request);

        long start = System.currentTimeMillis();
        HttpResponse response = HttpClients.createDefault().execute(request);
        long finish = System.currentTimeMillis();
        int current = (int)(finish-start);
        logger(DEBUG_LEVEL, "[DBG] " + current + "ms request");

        ArrayList list = new ArrayList();
        //list.add(0, response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
        list.add(0, response.getStatusLine().getStatusCode());
        list.add(1, checkResponseBody(readResponse(new StringBuilder(),response), expected_list));
        if (list.get(0).equals(HttpStatus.SC_OK)) {
            actual_list.add(current);
            int[] min = getMin(Operation.add, current, i);
            int[] max = getMax(Operation.add, current, i);
            list.add(2, current);
            list.add(3, getAverage(actual_list));
            list.add(4, searchMedian(actual_list, Sorting.quick));
            list.add(5, min[0]);
            list.add(6, min[1]);
            list.add(7, max[0]);
            list.add(8, max[1]);

            //use request_list.size() = total of success iteration!
            list.add(9, actual_list.size());
            //logger(DEBUG_LEVEL, "[DBG] add avg = " + getAverage(add_list) + "ms/" + total_i + ": add_list:" + add_list);
        }
        return list;
    }

    ArrayList post(String server, String json, ArrayList template, int i) throws IOException {
        logger(INFO_LEVEL, "[INF] " + new Date());

        HttpPost request = new HttpPost(prepareUrl(server, Operation.www,false));
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        request.setEntity(new StringEntity(json));
        logger(DEBUG_LEVEL, "[DBG] request string: " + request);

        long start = System.currentTimeMillis();
        HttpResponse response = HttpClients.createDefault().execute(request);
        long finish = System.currentTimeMillis();
        int current = (int)(finish-start);
        logger(DEBUG_LEVEL, "[DBG] " + current + "ms request");

        ArrayList list = new ArrayList();
        //list.add(0, response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
        list.add(0, response.getStatusLine().getStatusCode());
        list.add(1, checkResponseBody(readResponse(new StringBuilder(),response), template));
        if (list.get(0).equals(HttpStatus.SC_OK)) {
            actual_list.add(current);
            int[] min = getMin(Operation.add, current, i);
            int[] max = getMax(Operation.add, current, i);
            list.add(2, current);
            list.add(3, getAverage(actual_list));
            list.add(4, searchMedian(actual_list, Sorting.insertion));
            list.add(5, min[0]);
            list.add(6, min[1]);
            list.add(7, max[0]);
            list.add(8, max[1]);

            //use request_list.size() = total of success iteration!
            list.add(9, actual_list.size());
            //logger(DEBUG_LEVEL, "[DBG] add avg = " + getAverage(add_list) + "ms/" + total_i + ": add_list:" + add_list);
        }
        return list;
    }

}
