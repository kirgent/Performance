package tv.zodiac.dev;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class testPerformance extends commonPerformance{

    API_common performance = new API_common();
    int sleep_after_iteration = 1000;

    @Test
    void test_google_com() throws IOException, InterruptedException {
        String server = "google.com";
        String template = "<title>Google</title>";
        int count_iterations = 5;

        printStartHeader(server);
        for (int i = 1; i <= count_iterations; i++) {
            printIterationHeader(server, i, count_iterations);

            request_list = performance.request(server, i, template);
            printPreliminaryResults(request_list);

            assertEquals(expected200, request_list.get(0));
            assertEquals(template, request_list.get(1));

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

        printTotalResults(server, count_iterations,
                avg, med, min, min_iteration, max, max_iteration, total_i, current);
        //assertNotEquals(0, avg, "a_avg");
    }
}
