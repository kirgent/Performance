package com.perf.my;

import org.apache.http.HttpStatus;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class testPerformance extends API_common{

    private Performance performance = new Performance();
    private ArrayList expected_list = new ArrayList();
    //private int sleep_after_iteration = 0;


    @ParameterizedTest
    @CsvFileSource(resources = "/performance.csv", numLinesToSkip = 1)
    //@CsvSource({ "1, 0", "10, 0", "100, 0" })
    void test0_any_server(int count_internal_iterations, int sleep_after_iteration, String url) throws IOException, InterruptedException {
    //@RepeatedTest(50)
    //void test0_any_server() throws IOException, InterruptedException {
        //int count_internal_iterations = 1;
        //int sleep_after_iteration = 0;
        //String url = "http://localhost:8080/unidata-frontend";
        //String url = "http://localhost:8080/unidata-frontend/#main?section=home";
        expected_list.add(0, HttpStatus.SC_OK);
        //expected_list.add(1, "<title>Google</title>");

        printStartHeader(url);
        for (int i = 1; i <= count_internal_iterations; i++) {
            printIterationHeader(url, i, count_internal_iterations);

            actual_list = performance.get(url, expected_list, i);
            printPreliminaryMeasurements(actual_list);

            for(int j = 0; j<expected_list.size(); j++){
                assertEquals(expected_list.get(j), actual_list.get(j));
            }

            current.add(actual_list.get(2));
            avg = (int) actual_list.get(3);
            med = (int) actual_list.get(4);
            min = (int) actual_list.get(5);
            min_iteration = (int) actual_list.get(6);
            max = (int) actual_list.get(7);
            max_iteration = (int) actual_list.get(8);
            total_i = (int) actual_list.get(9);

            actual_list.clear();
            Thread.sleep(sleep_after_iteration);
        }

        printTotalMeasurements(url, count_internal_iterations,
                avg, med, min, min_iteration, max, max_iteration, total_i, current);
        //assertNotEquals(0, avg, "a_avg");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/performance.csv", numLinesToSkip = 1)
    void test1_google_com(int count_internal_iterations, int sleep_after_iteration) throws IOException, InterruptedException {
        String url = "google.com";
        expected_list.add(0, HttpStatus.SC_OK);
        expected_list.add(1, "<title>Google</title>");
        //int count_iterations = 5;

        printStartHeader(url);
        for (int i = 1; i <= count_internal_iterations; i++) {
            printIterationHeader(url, i, count_internal_iterations);

            actual_list = performance.get(url, expected_list, i);
            printPreliminaryMeasurements(actual_list);

            //for(int j = 0; j<expected_list.size(); j++){
                //assertEquals(expected_list.get(j), actual_list.get(j));
            //}

            current.add(actual_list.get(2));
            avg = (int) actual_list.get(3);
            med = (int) actual_list.get(4);
            min = (int) actual_list.get(5);
            min_iteration = (int) actual_list.get(6);
            max = (int) actual_list.get(7);
            max_iteration = (int) actual_list.get(8);
            total_i = (int) actual_list.get(9);

            actual_list.clear();
            Thread.sleep(sleep_after_iteration);
        }

        printTotalMeasurements(url, count_internal_iterations,
                avg, med, min, min_iteration, max, max_iteration, total_i, current);
        //assertNotEquals(0, avg, "a_avg");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/performance.csv", numLinesToSkip = 1)
    void test2_yandex_ru(int count_internal_iterations, int sleep_after_iteration) throws IOException, InterruptedException {
        String url = "yandex.ru";
        expected_list.add(0, HttpStatus.SC_OK);
        expected_list.add(1, "<title>Яндекс</title>");
        //int count_iterations = 5;

        printStartHeader(url);
        for (int i = 1; i <= count_internal_iterations; i++) {
            printIterationHeader(url, i, count_internal_iterations);

            actual_list = performance.get(url, expected_list, i);
            printPreliminaryMeasurements(actual_list);

            for(int j = 0; j<expected_list.size(); j++){
                assertEquals(expected_list.get(j), actual_list.get(j));
            }

            current.add(actual_list.get(2));
            avg = (int) actual_list.get(3);
            med = (int) actual_list.get(4);
            min = (int) actual_list.get(5);
            min_iteration = (int) actual_list.get(6);
            max = (int) actual_list.get(7);
            max_iteration = (int) actual_list.get(8);
            total_i = (int) actual_list.get(9);

            actual_list.clear();
            Thread.sleep(sleep_after_iteration);
        }

        printTotalMeasurements(url, count_internal_iterations,
                avg, med, min, min_iteration, max, max_iteration, total_i, current);
        //assertNotEquals(0, avg, "a_avg");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/performance.csv", numLinesToSkip = 1)
    void test3_local_json_server_127_0_0_1(int count_internal_iterations, int sleep_after_iteration) throws IOException {
        String url = "127.0.0.1:8080/temperature";
        expected_list.add(0, HttpStatus.SC_OK);
        expected_list.add(1, "{\"success\":true}");
        //int count_iterations = 100;

        String json = generate_json(100);

        printStartHeader(url);
        for (int i = 1; i <= count_internal_iterations; i++) {
            printIterationHeader(url, i, count_internal_iterations);

            actual_list = performance.post(url, json, expected_list, i);
            //actual_list = post(url, json, expected_list, false);
            printPreliminaryMeasurements(actual_list);

            for(int j = 0; j<expected_list.size(); j++){
                assertEquals(expected_list.get(j), actual_list.get(j));
            }

            current.add(actual_list.get(2));
            avg = (int) actual_list.get(3);
            med = (int) actual_list.get(4);
            min = (int) actual_list.get(5);
            min_iteration = (int) actual_list.get(6);
            max = (int) actual_list.get(7);
            max_iteration = (int) actual_list.get(8);
            total_i = (int) actual_list.get(9);

            actual_list.clear();
            //Thread.sleep(sleep_after_iteration);
        }

        printTotalMeasurements(url, count_internal_iterations,
                avg, med, min, min_iteration, max, max_iteration, total_i, current);
        assertNotEquals(0, avg, "avg");
    }

}
