package tv.zodiac.dev;

import org.apache.http.HttpStatus;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * We are localhost (Charter Headend). Full chain of requests: localhost -> AMS -> STB -> AMS -> localhost
 */
class testAMS_Performance_oldAPI extends API_common {

    OldAPI AMS = new OldAPI();

    @ParameterizedTest
    @CsvFileSource(resources = "/reminders_oldapi.csv", numLinesToSkip = 1)
    void test10_Add(String ams_ip, String mac, String boxname, int sleep_after_iteration, int count_reminders, int count_iterations, int reminderChannelNumber) throws IOException, InterruptedException {
        before(ams_ip, mac, boxname, sleep_after_iteration, count_reminders, count_iterations, reminderChannelNumber);

        for (int i = 1; i <= count_iterations; i++) {
            if(use_random){
                reminderChannelNumber = reminderChannelNumber(1000);
            }
            printIterationHeader(ams_ip, mac, count_reminders, i, count_iterations, reminderChannelNumber);

            //reminderChannelNumber = reminderChannelNumber();
            //int finalReminderChannelNumber = reminderChannelNumber;
            add_list = AMS.requestPerformance(ams_ip, mac, Operation.add, i, count_reminders, reminderChannelNumber, reminderProgramStart, reminderProgramId, reminderOffset);
            printPreliminaryMeasurements(add_list);
            if (add_list.get(0).equals(HttpStatus.SC_OK)) {
                a_current.add(add_list.get(2));
                a_avg = (int) add_list.get(3);
                a_med = (int) add_list.get(4);
                a_min = (int) add_list.get(5);
                a_min_iteration = (int) add_list.get(6);
                a_max = (int) add_list.get(7);
                a_max_iteration = (int) add_list.get(8);
                a_total_i = (int) add_list.get(9);
            }
            add_list.clear();
            Thread.sleep(sleep_after_iteration);
        }

        printTotalMeasurements(mac, boxname, count_reminders, count_iterations,
                a_avg, a_med, a_min, a_min_iteration, a_max, a_max_iteration, a_total_i, a_current,
                0, 0, 0, 0, 0, 0, 0, null,
                d_avg, d_med, d_min, d_min_iteration, d_max, d_max_iteration, d_total_i, d_current,
                p_avg, p_med, p_min, p_min_iteration, p_max, p_max_iteration, p_total_i, p_current);
        assertNotEquals(0, a_avg, "a_avg");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/reminders_oldapi.csv", numLinesToSkip = 1)
    void test11_Add_Purge(String ams_ip, String mac, String boxname, int sleep_after_iteration, int count_reminders, int count_iterations, int reminderChannelNumber) throws IOException, InterruptedException {
        before(ams_ip, mac, boxname, sleep_after_iteration, count_reminders, count_iterations, reminderChannelNumber);

        for (int i = 1; i <= count_iterations; i++) {
            if (use_random){                reminderChannelNumber = reminderChannelNumber(1000);            }
            printIterationHeader(ams_ip, mac, count_reminders, i, count_iterations, reminderChannelNumber);

            add_list = AMS.requestPerformance(ams_ip, mac, Operation.add, i, count_reminders, reminderChannelNumber, reminderProgramStart, reminderProgramId, reminderOffset);
            printPreliminaryMeasurements(add_list);
            if (add_list.get(0).equals(HttpStatus.SC_OK)) {
                a_current.add(add_list.get(2));
                a_avg = (int) add_list.get(3);
                a_med = (int) add_list.get(4);
                a_min = (int) add_list.get(5);
                a_min_iteration = (int) add_list.get(8);
                a_max = (int) add_list.get(6);
                a_max_iteration = (int) add_list.get(9);
                a_total_i = (int) add_list.get(7);

                purge_list = AMS.requestPerformance(ams_ip, mac, Operation.purge, i);
                printPreliminaryMeasurements(purge_list);
                if (purge_list.get(0).equals(HttpStatus.SC_OK)) {
                    p_current.add(purge_list.get(2));
                    p_avg = (int) purge_list.get(3);
                    p_med = (int) purge_list.get(4);
                    p_min = (int) purge_list.get(5);
                    p_min_iteration = (int) purge_list.get(8);
                    p_max = (int) purge_list.get(6);
                    p_max_iteration = (int) purge_list.get(9);
                    p_total_i = (int) purge_list.get(7);
                }
            }
            add_list.clear();
            purge_list.clear();
            Thread.sleep(sleep_after_iteration);
        }

        printTotalMeasurements(mac, boxname, count_reminders, count_iterations,
                a_avg, a_med, a_min, a_min_iteration, a_max, a_max_iteration, a_total_i, a_current,
                0, 0, 0, 0, 0, 0, 0, null,
                d_avg, d_med, d_min, d_min_iteration, d_max, d_max_iteration, d_total_i, d_current,
                p_avg, p_med, p_min, p_min_iteration, p_max, p_max_iteration, p_total_i, p_current);
        assertNotEquals(0, a_avg, "a_avg");
        assertNotEquals(0, p_avg, "p_avg");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/reminders_oldapi.csv", numLinesToSkip = 1)
    void test12_Add_Delete_Purge(String ams_ip, String mac, String boxname, int sleep_after_iteration, int count_reminders, int count_iterations, int reminderChannelNumber) throws IOException, InterruptedException {
        before(ams_ip, mac, boxname, sleep_after_iteration, count_reminders, count_iterations, reminderChannelNumber);

        for (int i = 1; i <= count_iterations; i++) {
            if(use_random){                reminderChannelNumber = reminderChannelNumber(1000);            }
            printIterationHeader(ams_ip, mac, count_reminders, i, count_iterations, reminderChannelNumber);

            add_list = AMS.requestPerformance(ams_ip, mac, Operation.add, i, count_reminders, reminderChannelNumber, reminderProgramStart, reminderProgramId, reminderOffset);
            printPreliminaryMeasurements(add_list);
            if (add_list.get(0).equals(HttpStatus.SC_OK)) {
                a_current.add(add_list.get(2));
                a_avg = (int) add_list.get(3);
                a_med = (int) add_list.get(4);
                a_min = (int) add_list.get(5);
                a_min_iteration = (int) add_list.get(6);
                a_max = (int) add_list.get(7);
                a_max_iteration = (int) add_list.get(8);
                a_total_i = (int) add_list.get(9);

                delete_list = AMS.requestPerformance(ams_ip, mac, Operation.delete, i, count_reminders, reminderChannelNumber, reminderProgramStart, reminderProgramId, reminderOffset);
                printPreliminaryMeasurements(delete_list);
                if (delete_list.get(0).equals(HttpStatus.SC_OK)) {
                    d_current.add(delete_list.get(2));
                    d_avg = (int) delete_list.get(3);
                    d_med = (int) delete_list.get(4);
                    d_min = (int) delete_list.get(5);
                    d_min_iteration = (int) delete_list.get(6);
                    d_max = (int) delete_list.get(7);
                    d_max_iteration = (int) delete_list.get(8);
                    d_total_i = (int) delete_list.get(9);
                }

                purge_list = AMS.requestPerformance(ams_ip, mac, Operation.purge, i);
                printPreliminaryMeasurements(purge_list);
                if (purge_list.get(0).equals(HttpStatus.SC_OK)) {
                    p_current.add(purge_list.get(2));
                    p_avg = (int) purge_list.get(3);
                    p_med = (int) purge_list.get(4);
                    p_min = (int) purge_list.get(5);
                    p_min_iteration = (int) purge_list.get(6);
                    p_max = (int) purge_list.get(7);
                    p_max_iteration = (int) purge_list.get(8);
                    p_total_i = (int) purge_list.get(9);
                }
            }
            add_list.clear();
            delete_list.clear();
            purge_list.clear();
            Thread.sleep(sleep_after_iteration);
        }

        printTotalMeasurements(mac, boxname, count_reminders, count_iterations,
                a_avg, a_med, a_min, a_min_iteration, a_max, a_max_iteration, a_total_i, a_current,
                0, 0, 0, 0, 0, 0, 0, null,
                d_avg, d_med, d_min, d_min_iteration, d_max, d_max_iteration, d_total_i, d_current,
                p_avg, p_med, p_min, p_min_iteration, p_max, p_max_iteration, p_total_i, p_current);
        assertNotEquals(0, a_avg, "a_avg");
        assertNotEquals(0, d_avg, "d_avg");
        assertNotEquals(0, p_avg, "p_avg");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/reminders_oldapi.csv", numLinesToSkip = 1)
    void test19_Purge(String ams_ip, String mac, String boxname, int sleep_after_iteration, int count_reminders, int count_iterations, int reminderChannelNumber) throws IOException, InterruptedException {
        before(ams_ip, mac, boxname, sleep_after_iteration, count_reminders, count_iterations, reminderChannelNumber);

        for (int i = 1; i <= count_iterations; i++) {
            printIterationHeader(ams_ip, mac, count_reminders, i, count_iterations, reminderChannelNumber);

            purge_list = AMS.requestPerformance(ams_ip, mac, Operation.purge, i);
            printPreliminaryMeasurements(purge_list);

            if (purge_list.get(0).equals(HttpStatus.SC_OK)) {
                p_current.add(purge_list.get(2));
                p_avg = (int) purge_list.get(3);
                p_med = (int) purge_list.get(4);
                p_min = (int) purge_list.get(5);
                p_min_iteration = (int) purge_list.get(6);
                p_max = (int) purge_list.get(7);
                p_max_iteration = (int) purge_list.get(8);
                p_total_i = (int) purge_list.get(9);
            }

            purge_list.clear();
            Thread.sleep(sleep_after_iteration);
        }

        printTotalMeasurements(mac, boxname, count_reminders, count_iterations,
                a_avg, a_med, a_min, a_min_iteration, a_max, a_max_iteration, a_total_i, a_current,
                0, 0, 0, 0, 0, 0, 0, null,
                d_avg, d_med, d_min, d_min_iteration, d_max, d_max_iteration, d_total_i, d_current,
                p_avg, p_med, p_min, p_min_iteration, p_max, p_max_iteration, p_total_i, p_current);
        assertNotEquals(0, p_avg, "p_avg");
    }

}
