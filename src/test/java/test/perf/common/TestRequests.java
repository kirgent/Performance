package test.perf.common;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpStatus;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestRequests extends CommonAPI {

    private ArrayList expectedList = new ArrayList();

    @ParameterizedTest
    @CsvFileSource(resources = "/performance.csv", numLinesToSkip = 1)
    void test0_any_server(int countIterations, int sleepAfterIteration) throws IOException, InterruptedException {
        String url = "http://localhost:8080/unidata-frontend";
        //String url = "http://localhost:8080/unidata-frontend/#main?section=home";
        expectedList.add(0, HttpStatus.SC_OK);
        //expectedList.ADD(1, "<title>Google</title>");

        printStartHeader(url);
        for (int i = 1; i <= countIterations; i++) {
            printIterationHeader(url, i, countIterations);

            actualList = get(url, expectedList, i);
            printPreliminaryMeasurements(actualList);

            for(int j = 0; j< expectedList.size(); j++){
                assertEquals(expectedList.get(j), actualList.get(j));
            }

            current.add(actualList.get(2));
            avg = (int) actualList.get(3);
            med = (int) actualList.get(4);
            min = (int) actualList.get(5);
            minIteration = (int) actualList.get(6);
            max = (int) actualList.get(7);
            maxIteration = (int) actualList.get(8);
            totalI = (int) actualList.get(9);

            actualList.clear();
            Thread.sleep(sleepAfterIteration);
        }

        printTotalMeasurements(url, countIterations,
                avg, med, min, minIteration, max, maxIteration, totalI, current);
        //assertNotEquals(0, avg, "aAvg");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/remindersPerformance.csv", numLinesToSkip = 1)
    void test1_google_com(int count_iterations, int sleep_after_iteration) throws IOException, InterruptedException {
        String url = "google.com";
        expectedList.add(0, HttpStatus.SC_OK);
        expectedList.add(1, "<title>Google</title>");
        //int count_iterations = 5;

        printStartHeader(url);
        for (int i = 1; i <= count_iterations; i++) {
            printIterationHeader(url, i, count_iterations);

            actualList = get(url, expectedList, i);
            printPreliminaryMeasurements(actualList);

            //for(int j = 0; j<expectedList.size(); j++){
                //assertEquals(expectedList.get(j), actualList.get(j));
            //}

            current.add(actualList.get(2));
            avg = (int) actualList.get(3);
            med = (int) actualList.get(4);
            min = (int) actualList.get(5);
            minIteration = (int) actualList.get(6);
            max = (int) actualList.get(7);
            maxIteration = (int) actualList.get(8);
            totalI = (int) actualList.get(9);

            actualList.clear();
            Thread.sleep(sleep_after_iteration);
        }

        printTotalMeasurements(url, count_iterations,
                avg, med, min, minIteration, max, maxIteration, totalI, current);
        //assertNotEquals(0, avg, "aAvg");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/remindersPerformance.csv", numLinesToSkip = 1)
    void test2_yandex_ru(int count_iterations, int sleep_after_iteration) throws IOException, InterruptedException {
        String url = "yandex.ru";
        expectedList.add(0, HttpStatus.SC_OK);
        expectedList.add(1, "<title>Яндекс</title>");
        //int count_iterations = 5;

        printStartHeader(url);
        for (int i = 1; i <= count_iterations; i++) {
            printIterationHeader(url, i, count_iterations);

            actualList = get(url, expectedList, i);
            printPreliminaryMeasurements(actualList);

            for(int j = 0; j< expectedList.size(); j++){
                assertEquals(expectedList.get(j), actualList.get(j));
            }

            current.add(actualList.get(2));
            avg = (int) actualList.get(3);
            med = (int) actualList.get(4);
            min = (int) actualList.get(5);
            minIteration = (int) actualList.get(6);
            max = (int) actualList.get(7);
            maxIteration = (int) actualList.get(8);
            totalI = (int) actualList.get(9);

            actualList.clear();
            Thread.sleep(sleep_after_iteration);
        }

        printTotalMeasurements(url, count_iterations,
                avg, med, min, minIteration, max, maxIteration, totalI, current);
        //assertNotEquals(0, avg, "aAvg");
    }

}
