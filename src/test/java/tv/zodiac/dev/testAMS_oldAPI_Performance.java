package tv.zodiac.dev;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.IOException;
import java.util.ArrayList;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

/**
 * We are localhost (Charter Headend). Full chain of requests: localhost -> AMS -> STB -> AMS -> localhost
 */
class testAMS_oldAPI_Performance extends API_common {
    private OldAPI_AMS AMS = new OldAPI_AMS();
    private int timeout = 20000;
    private int sleep_after_iteration = 1000;

    @ParameterizedTest
    @CsvFileSource(resources = "/reminders_oldapi.csv", numLinesToSkip = 1)
    void test00_Add(String ams_ip, String macaddress, String boxname, int count_reminders, int reminderChannelNumber, int reminderOffset, int reminderOffset_new, int count_iterations) throws IOException, InterruptedException {
        check_csv_preconditions(ams_ip, macaddress, count_reminders, reminderChannelNumber, reminderOffset, reminderOffset_new, count_iterations);
        final ArrayList[] add_list = {new ArrayList()};
        int a_avg = 0, a_min = 0, a_max = 0, a_iterations = 0;
        for (int i = 1; i <= count_iterations; i++) {
            String header = "========= ========= ========= Iteration = " + i + "/" + count_iterations + " ========= ========= =========";
            logger(INFO_LEVEL, header);
            //reminderChannelNumber = reminderChannelNumber();
            //int finalReminderChannelNumber = reminderChannelNumber;
            assertTimeoutPreemptively(ofMillis(timeout), () -> {
                add_list[0] = AMS.request(ams_ip, macaddress, Operation.add, count_reminders, reminderChannelNumber, reminderProgramStart, reminderProgramId, reminderOffset);
            });

            if (add_list[0].get(0).equals(expected200) && add_list[0].get(1).equals("")) {
                a_avg = (int) add_list[0].get(2);
                a_min = (int) add_list[0].get(3);
                a_max = (int) add_list[0].get(4);
                a_iterations = (int) add_list[0].get(5);
            }
            add_list[0].clear();
            Thread.sleep(sleep_after_iteration);
        }

        prepare_total_results(macaddress, boxname, count_reminders, count_iterations,
                a_avg, a_min, a_max, a_iterations,
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0);
        assertNotEquals(0, a_avg, "a_avg");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/reminders_oldapi.csv", numLinesToSkip = 1)
    void test11_Add_Purge(String ams_ip, String macaddress, String boxname, int count_reminders, int reminderChannelNumber, int reminderOffset, int reminderOffset_new, int count_iterations) throws IOException, InterruptedException {
        check_csv_preconditions(ams_ip, macaddress, count_reminders, reminderChannelNumber, reminderOffset, reminderOffset_new, count_iterations);
        final ArrayList[] add_list = {new ArrayList()};
        final ArrayList[] purge_list = {new ArrayList()};
        int a_avg = 0, a_min = 0, a_max = 0, a_iterations = 0,
                p_avg = 0, p_min = 0, p_max = 0, p_iterations = 0;
        for (int i = 1; i <= count_iterations; i++) {
            String header = "========= ========= ========= Iteration = " + i + "/" + count_iterations + " ========= ========= =========";
            logger(INFO_LEVEL, header);
            //reminderChannelNumber = reminderChannelNumber();
            //int finalReminderChannelNumber = reminderChannelNumber;
            assertTimeoutPreemptively(ofMillis(timeout), () -> {
                add_list[0] = AMS.request(ams_ip, macaddress, Operation.add, count_reminders, reminderChannelNumber, reminderProgramStart, reminderProgramId, reminderOffset);
            });

            if (add_list[0].get(0).equals(expected200) && add_list[0].get(1).equals("")) {
                a_avg = (int) add_list[0].get(2);
                a_min = (int) add_list[0].get(3);
                a_max = (int) add_list[0].get(4);
                a_iterations = (int) add_list[0].get(5);

                assertTimeoutPreemptively(ofMillis(timeout), () -> {
                    purge_list[0] = AMS.request(ams_ip, macaddress, Operation.purge);
                });
                if (purge_list[0].get(1).equals("")) {
                    p_avg = (int) purge_list[0].get(2);
                    p_min = (int) purge_list[0].get(3);
                    p_max = (int) purge_list[0].get(4);
                    p_iterations = (int) purge_list[0].get(5);
                }
            }
            add_list[0].clear();
            purge_list[0].clear();
            Thread.sleep(sleep_after_iteration);
        }

        prepare_total_results(macaddress, boxname, count_reminders, count_iterations,
                a_avg, a_min, a_max, a_iterations,
                0, 0, 0, 0,
                0, 0, 0, 0,
                p_avg, p_min, p_max, p_iterations);
        assertNotEquals(0, a_avg, "a_avg");
        assertNotEquals(0, p_avg, "p_avg");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/reminders_oldapi.csv", numLinesToSkip = 1)
    void test22_Add_Delete_Purge(String ams_ip, String macaddress, String boxname, int count_reminders, int reminderChannelNumber, int reminderOffset, int reminderOffset_new, int count_iterations) throws IOException, InterruptedException {
        check_csv_preconditions(ams_ip, macaddress, count_reminders, reminderChannelNumber, reminderOffset, reminderOffset_new, count_iterations);
        ArrayList add_list = new ArrayList();
        ArrayList delete_list = new ArrayList();
        ArrayList purge_list = new ArrayList();
        int a_avg = 0, a_min = 0, a_max = 0, a_iterations = 0,
                d_avg = 0, d_min = 0, d_max = 0, d_iterations = 0,
                p_avg = 0, p_min = 0, p_max = 0, p_iterations = 0;
        for (int i = 1; i <= count_iterations; i++) {
            String header = "========= ========= ========= Iteration = " + i + "/" + count_iterations + " ========= ========= =========";
            logger(INFO_LEVEL, header);
            //reminderChannelNumber = reminderChannelNumber();
            //assertTimeoutPreemptively(ofMillis(timeout), () -> {
                add_list = AMS.request(ams_ip, macaddress, Operation.add, count_reminders, reminderChannelNumber, reminderProgramStart, reminderProgramId, reminderOffset);
            //});
            //assertEquals(expected200, add_list.get(0));
            //assertEquals("", add_list.get(1));
            if (add_list.get(0).equals(expected200) && add_list.get(1).equals("")) {
                a_avg = (int) add_list.get(2);
                a_min = (int) add_list.get(3);
                a_max = (int) add_list.get(4);
                a_iterations = (int) add_list.get(5);

                //assertTimeoutPreemptively(ofMillis(timeout), () -> {
                    delete_list = AMS.request(ams_ip, macaddress, Operation.delete, count_reminders, reminderChannelNumber, reminderProgramStart, reminderProgramId, reminderOffset);
                //});
                if (delete_list.get(1).equals("")) {
                    d_avg = (int) delete_list.get(2);
                    d_min = (int) delete_list.get(3);
                    d_max = (int) delete_list.get(4);
                    d_iterations = (int) delete_list.get(5);
                }

                //assertTimeoutPreemptively(ofMillis(timeout), () -> {
                    purge_list = AMS.request(ams_ip, macaddress, Operation.purge);
                //});
                if (purge_list.get(1).equals("")) {
                    p_avg = (int) purge_list.get(2);
                    p_min = (int) purge_list.get(3);
                    p_max = (int) purge_list.get(4);
                    p_iterations = (int) purge_list.get(5);
                }
            }
            add_list.clear();
            delete_list.clear();
            purge_list.clear();
            Thread.sleep(sleep_after_iteration);
        }

        prepare_total_results(macaddress, boxname, count_reminders, count_iterations,
                a_avg, a_min, a_max, a_iterations,
                0, 0, 0, 0,
                d_avg, d_min, d_max, d_iterations,
                p_avg, p_min, p_max, p_iterations);
        assertNotEquals(0, a_avg, "a_avg");
        assertNotEquals(0, d_avg, "d_avg");
        assertNotEquals(0, p_avg, "p_avg");
    }

    private void check_csv_preconditions(String ams_ip, String macaddress, int count_reminders, int reminderChannelNumber, int reminderOffset, int reminderOffset_new, int count_iterations) {
        assertNotNull(ams_ip);
        assertNotNull(macaddress);
        assertNotEquals(0, count_reminders);
        assertNotEquals(0, reminderChannelNumber);
        assertNotEquals(null, reminderOffset);
        assertNotEquals(null, reminderOffset_new);
        assertNotEquals(0, count_iterations);
    }

}
