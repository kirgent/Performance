package tv.zodiac.dev;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * We are localhost (Charter Headend). Full chain of requests: localhost -> AMS -> STB -> AMS -> localhost
 */
class testAMS_oldAPI_Performance extends API_common {
    private OldAPI_AMS AMS = new OldAPI_AMS();
    private int timeout = 20000;
    private int sleep_after_iteration = 1000;

    private void before(String ams_ip, String mac, String boxname, int count_reminders, int reminderChannelNumber, int reminderOffset, int reminderOffset_new, int count_iterations) throws IOException {
        check_csv(ams_ip, mac, boxname, count_reminders, reminderChannelNumber, reminderOffset, reminderOffset_new, count_iterations);
        logger(INFO_LEVEL, "[INF] " + new Date() + ": New start for mac=" + mac + "(" + boxname + ") to ams=" + ams_ip + ", "
                + "count_reminders=" + count_reminders + ", "
                + "reminderChannelNumber=" + reminderChannelNumber + ", "
                + "reminderProgramStart=" + reminderProgramStart + ", "
                + "reminderProgramId=" + reminderProgramId + ", "
                + "reminderOffset=" + reminderOffset);
    }

    private void check_csv(String ams_ip, String mac, String boxname, int count_reminders, int reminderChannelNumber, int reminderOffset, int reminderOffset_new, int count_iterations) {
        assertNotNull(ams_ip);
        assertNotNull(mac);
        assertNotNull(boxname);
        assertNotEquals(0, count_reminders);
        assertNotEquals(0, reminderChannelNumber);
        assertNotEquals(null, reminderOffset);
        assertNotEquals(null, reminderOffset_new);
        assertNotEquals(0, count_iterations);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/reminders_oldapi.csv", numLinesToSkip = 1)
    void test00_Add(String ams_ip, String mac, String boxname, int count_reminders, int reminderChannelNumber, int reminderOffset, int reminderOffset_new, int count_iterations) throws IOException, InterruptedException {
        before(ams_ip, mac, boxname, count_reminders, reminderChannelNumber, reminderOffset, reminderOffset_new, count_iterations);
        ArrayList add_list;
        ArrayList a_current = new ArrayList();
        int a_avg = 0, a_min = 0, a_max = 0, a_iteration = 0;
        for (int i = 1; i <= count_iterations; i++) {
            String header = "========= ========= ========= Iteration = " + i + "/" + count_iterations + " ========= ========= =========";
            logger(INFO_LEVEL, header);
            //reminderChannelNumber = reminderChannelNumber();
            //int finalReminderChannelNumber = reminderChannelNumber;
            add_list = AMS.request(ams_ip, mac, Operation.add, count_reminders, reminderChannelNumber, reminderProgramStart, reminderProgramId, reminderOffset);
            if (add_list.get(0).equals(expected200) && add_list.get(1).equals("")) {
                a_current.add(add_list.get(2));
                a_avg = (int) add_list.get(3);
                a_min = (int) add_list.get(4);
                a_max = (int) add_list.get(5);
                a_iteration = (int) add_list.get(6);
            }
            add_list.clear();
            Thread.sleep(sleep_after_iteration);
        }

        prepare_total_results(mac, boxname, count_reminders, count_iterations,
                a_avg, a_min, a_max, a_iteration, a_current,
                0, 0, 0, 0, null,
                0, 0, 0, 0, null,
                0, 0, 0, 0, null);
        assertNotEquals(0, a_avg, "a_avg");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/reminders_oldapi.csv", numLinesToSkip = 1)
    void test11_Add_Purge(String ams_ip, String mac, String boxname, int count_reminders, int reminderChannelNumber, int reminderOffset, int reminderOffset_new, int count_iterations) throws IOException, InterruptedException {
        before(ams_ip, mac, boxname, count_reminders, reminderChannelNumber, reminderOffset, reminderOffset_new, count_iterations);
        ArrayList add_list,
                purge_list = new ArrayList();
        int a_avg = 0, a_min = 0, a_max = 0, a_iteration = 0,
                p_avg = 0, p_min = 0, p_max = 0, p_iteration = 0;
        ArrayList a_current = new ArrayList();
        ArrayList p_current = new ArrayList();
        for (int i = 1; i <= count_iterations; i++) {
            String header = "========= ========= ========= Iteration = " + i + "/" + count_iterations + " ========= ========= =========";
            logger(INFO_LEVEL, header);
            //reminderChannelNumber = reminderChannelNumber();
            //int finalReminderChannelNumber = reminderChannelNumber;

            add_list = AMS.request(ams_ip, mac, Operation.add, count_reminders, reminderChannelNumber, reminderProgramStart, reminderProgramId, reminderOffset);
            if (add_list.get(0).equals(expected200) && add_list.get(1).equals("")) {
                a_current.add(add_list.get(2));
                a_avg = (int) add_list.get(3);
                a_min = (int) add_list.get(4);
                a_max = (int) add_list.get(5);
                a_iteration = (int) add_list.get(6);

                purge_list = AMS.request(ams_ip, mac, Operation.purge);
                if (purge_list.get(1).equals("")) {
                    p_current.add(purge_list.get(2));
                    p_avg = (int) purge_list.get(3);
                    p_min = (int) purge_list.get(4);
                    p_max = (int) purge_list.get(5);
                    p_iteration = (int) purge_list.get(6);
                }
            }
            add_list.clear();
            purge_list.clear();
            Thread.sleep(sleep_after_iteration);
        }

        prepare_total_results(mac, boxname, count_reminders, count_iterations,
                a_avg, a_min, a_max, a_iteration, a_current,
                0, 0, 0, 0, null,
                0, 0, 0, 0, null,
                p_avg, p_min, p_max, p_iteration, p_current);
        assertNotEquals(0, a_avg, "a_avg");
        assertNotEquals(0, p_avg, "p_avg");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/reminders_oldapi.csv", numLinesToSkip = 1)
    void test22_Add_Delete_Purge(String ams_ip, String mac, String boxname, int count_reminders, int reminderChannelNumber, int reminderOffset, int reminderOffset_new, int count_iterations) throws IOException, InterruptedException {
        before(ams_ip, mac, boxname, count_reminders, reminderChannelNumber, reminderOffset, reminderOffset_new, count_iterations);
        ArrayList add_list,
                delete_list = new ArrayList(),
                purge_list = new ArrayList();
        int a_avg = 0, a_min = 0, a_max = 0, a_iteration = 0,
                d_avg = 0, d_min = 0, d_max = 0, d_iteration = 0,
                p_avg = 0, p_min = 0, p_max = 0, p_iteration = 0;
        ArrayList a_current = new ArrayList();
        ArrayList d_current = new ArrayList();
        ArrayList p_current = new ArrayList();
        for (int i = 1; i <= count_iterations; i++) {
            String header = "========= ========= ========= Iteration = " + i + "/" + count_iterations + " ========= ========= =========";
            logger(INFO_LEVEL, header);
            //reminderChannelNumber = reminderChannelNumber();
            add_list = AMS.request(ams_ip, mac, Operation.add, count_reminders, reminderChannelNumber, reminderProgramStart, reminderProgramId, reminderOffset);
            //assertEquals(expected200, add_list.get(0));
            //assertEquals("", add_list.get(1));
            if (add_list.get(0).equals(expected200) && add_list.get(1).equals("")) {
                a_current.add(add_list.get(2));
                a_avg = (int) add_list.get(3);
                a_min = (int) add_list.get(4);
                a_max = (int) add_list.get(5);
                a_iteration = (int) add_list.get(6);

                delete_list = AMS.request(ams_ip, mac, Operation.delete, count_reminders, reminderChannelNumber, reminderProgramStart, reminderProgramId, reminderOffset);
                if (delete_list.get(1).equals("")) {
                    d_current.add(delete_list.get(2));
                    d_avg = (int) delete_list.get(3);
                    d_min = (int) delete_list.get(4);
                    d_max = (int) delete_list.get(5);
                    d_iteration = (int) delete_list.get(6);
                }

                purge_list = AMS.request(ams_ip, mac, Operation.purge);
                if (purge_list.get(1).equals("")) {
                    p_current.add(purge_list.get(2));
                    p_avg = (int) purge_list.get(3);
                    p_min = (int) purge_list.get(4);
                    p_max = (int) purge_list.get(5);
                    p_iteration = (int) purge_list.get(6);
                }
            }
            add_list.clear();
            delete_list.clear();
            purge_list.clear();
            Thread.sleep(sleep_after_iteration);
        }

        prepare_total_results(mac, boxname, count_reminders, count_iterations,
                a_avg, a_min, a_max, a_iteration, a_current,
                0, 0, 0, 0, null,
                d_avg, d_min, d_max, d_iteration, d_current,
                p_avg, p_min, p_max, p_iteration, p_current);
        assertNotEquals(0, a_avg, "a_avg");
        assertNotEquals(0, d_avg, "d_avg");
        assertNotEquals(0, p_avg, "p_avg");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/reminders_oldapi.csv", numLinesToSkip = 1)
    void test99_Purge(String ams_ip, String mac, String boxname, int count_reminders, int reminderChannelNumber, int reminderOffset, int reminderOffset_new, int count_iterations) throws IOException, InterruptedException {
        before(ams_ip, mac, boxname, count_reminders, reminderChannelNumber, reminderOffset, reminderOffset_new, count_iterations);
        ArrayList purge_list;
        int p_avg = 0, p_min = 0, p_max = 0, p_iteration = 0;
        ArrayList p_current = new ArrayList();
        for (int i = 1; i <= count_iterations; i++) {
            String header = "========= ========= ========= Iteration = " + i + "/" + count_iterations + " ========= ========= =========";
            logger(INFO_LEVEL, header);
            //reminderChannelNumber = reminderChannelNumber();

            purge_list = AMS.request(ams_ip, mac, Operation.purge);
            if (purge_list.get(1).equals("")) {
                p_current.add(purge_list.get(2));
                p_avg = (int) purge_list.get(3);
                p_min = (int) purge_list.get(4);
                p_max = (int) purge_list.get(5);
                p_iteration = (int) purge_list.get(6);
            }

            purge_list.clear();
            Thread.sleep(sleep_after_iteration);
        }

        prepare_total_results(mac, boxname, count_reminders, count_iterations,
                -1, 0, 0, 0, null,
                0, 0, 0, 0, null,
                0, 0, 0, 0, null,
                p_avg, p_min, p_max, p_iteration, p_current);
        assertNotEquals(0, p_avg, "p_avg");
    }

}
