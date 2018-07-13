package tv.zodiac.dev;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class testPerformance extends API_common{

    private Performance performance = new Performance();
    private ArrayList expected_list = new ArrayList();
    private int sleep_after_iteration = 2000;

    @Test
    void test1_google_com() throws IOException, InterruptedException {
        String url = "google.com";
        expected_list.add(0, HttpStatus.SC_OK);
        expected_list.add(1, "<title>Google</title>");
        int count_iterations = 5;

        printStartHeader(url);
        for (int i = 1; i <= count_iterations; i++) {
            printIterationHeader(url, i, count_iterations);

            request_list = performance.get(url, expected_list, i);
            printPreliminaryMeasurements(request_list);

            for(int j = 0; j<expected_list.size(); j++){
                assertEquals(expected_list.get(j), request_list.get(j));
            }

            current.add(request_list.get(2));
            avg = (int) request_list.get(3);
            med = (int) request_list.get(4);
            min = (int) request_list.get(5);
            min_iteration = (int) request_list.get(6);
            max = (int) request_list.get(7);
            max_iteration = (int) request_list.get(8);
            total_i = (int) request_list.get(9);

            request_list.clear();
            Thread.sleep(sleep_after_iteration);
        }

        printTotalMeasurements(url, count_iterations,
                avg, med, min, min_iteration, max, max_iteration, total_i, current);
        //assertNotEquals(0, avg, "a_avg");
    }

    @Test
    void test2_yandex_ru() throws IOException, InterruptedException {
        String url = "yandex.ru";
        expected_list.add(0, HttpStatus.SC_OK);
        expected_list.add(1, "<title>Яндекс</title>");
        int count_iterations = 5;

        printStartHeader(url);
        for (int i = 1; i <= count_iterations; i++) {
            printIterationHeader(url, i, count_iterations);

            request_list = performance.get(url, expected_list, i);
            printPreliminaryMeasurements(request_list);

            for(int j = 0; j<expected_list.size(); j++){
                assertEquals(expected_list.get(j), request_list.get(j));
            }

            current.add(request_list.get(2));
            avg = (int) request_list.get(3);
            med = (int) request_list.get(4);
            min = (int) request_list.get(5);
            min_iteration = (int) request_list.get(6);
            max = (int) request_list.get(7);
            max_iteration = (int) request_list.get(8);
            total_i = (int) request_list.get(9);

            request_list.clear();
            Thread.sleep(sleep_after_iteration);
        }

        printTotalMeasurements(url, count_iterations,
                avg, med, min, min_iteration, max, max_iteration, total_i, current);
        //assertNotEquals(0, avg, "a_avg");
    }

    @Test
    void test3_local_json_server_127_0_0_1() throws IOException, InterruptedException {
        String url = "127.0.0.1:8080/temperature";
        expected_list.add(0, HttpStatus.SC_OK);
        expected_list.add(1, "{\"success\":true}");
        int count_iterations = 100;

        String json = generate_json(100);

        printStartHeader(url);
        for (int i = 1; i <= count_iterations; i++) {
            printIterationHeader(url, i, count_iterations);

            request_list = performance.post(url, json, expected_list, i);
            //request_list = post(url, json, expected_list, false);
            printPreliminaryMeasurements(request_list);

            for(int j = 0; j<expected_list.size(); j++){
                assertEquals(expected_list.get(j), request_list.get(j));
            }

            current.add(request_list.get(2));
            avg = (int) request_list.get(3);
            med = (int) request_list.get(4);
            min = (int) request_list.get(5);
            min_iteration = (int) request_list.get(6);
            max = (int) request_list.get(7);
            max_iteration = (int) request_list.get(8);
            total_i = (int) request_list.get(9);

            request_list.clear();
            //Thread.sleep(sleep_after_iteration);
        }

        printTotalMeasurements(url, count_iterations,
                avg, med, min, min_iteration, max, max_iteration, total_i, current);
        assertNotEquals(0, avg, "avg");
    }

}
