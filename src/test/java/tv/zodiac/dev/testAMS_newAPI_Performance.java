package tv.zodiac.dev;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.IOException;
import java.util.ArrayList;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.*;

/**
 * We are localhost (Charter Headend). Full chain of requests: localhost -> AMS -> STB -> AMS -> localhost
 */
class testAMS_newAPI_Performance extends API_common {
    private NewAPI_AMS AMS = new NewAPI_AMS();
    private int timeout = 20000;
    private int sleep_after_iteration = 1000;
    Integer[] rack_channels_wb = { 2, 31, 63, 209, 211, 631, 755, 808 };
    Integer[] rack_channels_moto = { 2 };

    @ParameterizedTest
    @CsvFileSource(resources = "/reminders.csv", numLinesToSkip = 1)
    void test0_Add(String ams_ip, String macaddress, String boxname, int count_reminders, int reminderChannelNumber, int reminderOffset, int reminderOffset_new, int count_iterations) throws InterruptedException, IOException {
        check_csv_preconditions(ams_ip, macaddress, count_reminders, reminderChannelNumber, reminderOffset, reminderOffset_new, count_iterations);
        final ArrayList[] add_list = {new ArrayList()};
        int a_avg = 0, a_min = 0, a_max=0, a_iterations = 0;
        for (int i = 1; i <= count_iterations; i++) {
            String header = "========= ========= ========= Iteration = " + i + "/" + count_iterations + " ========= ========= =========\n";
            logger(INFO_LEVEL, header);
            long reminderScheduleId = reminderScheduleId();
            long reminderId = reminderId();

            assertTimeoutPreemptively(ofMillis(timeout), () -> {
                add_list[0] = AMS.request(ams_ip, macaddress, Operation.add, count_reminders, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
            });
            if(add_list[0].get(0).equals(expected200) && add_list[0].get(1).equals("")) {
                a_avg = (int) add_list[0].get(2);
                a_min = (int) add_list[0].get(3);
                a_max = (int) add_list[0].get(4);
                a_iterations = (int) add_list[0].get(5);
            }
            reminderScheduleId_list.clear();
            reminderId_list.clear();
            logger(DEBUG_LEVEL, "[DBG] reminderX_list-s are CLEARED !!!");
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
    @CsvFileSource(resources = "/reminders.csv", numLinesToSkip = 1)
    void test1_Add_Purge(String ams_ip, String macaddress, String boxname, int count_reminders, int reminderChannelNumber, int reminderOffset, int reminderOffset_new, int count_iterations) throws InterruptedException, IOException {
        check_csv_preconditions(ams_ip, macaddress, count_reminders, reminderChannelNumber, reminderOffset, reminderOffset_new, count_iterations);
        ArrayList add_list = new ArrayList();
        ArrayList purge_list = new ArrayList();
        int a_avg = 0, a_min = 0, a_max=0, a_iterations = 0,
                p_avg = 0, p_min = 0, p_max=0, p_iterations = 0;
        for (int i = 1; i <= count_iterations; i++) {
            String header = "========= ========= ========= Iteration = " + i + "/" + count_iterations + " ========= ========= =========\n";
            logger(INFO_LEVEL, header);
            long reminderScheduleId = reminderScheduleId();
            long reminderId = reminderId();

            //assertTimeoutPreemptively(ofMillis(timeout), () -> {
                add_list = AMS.request(ams_ip, macaddress, Operation.add, count_reminders, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
            //});
            if(add_list.get(0).equals(expected200) && add_list.get(1).equals("")) {
                a_avg = (int) add_list.get(2);
                a_min = (int) add_list.get(3);
                a_max = (int) add_list.get(4);
                a_iterations = (int) add_list.get(5);

                //assertTimeoutPreemptively(ofMillis(timeout), () -> {
                    purge_list = AMS.request(ams_ip, macaddress, Operation.purge);
                //});
                if(purge_list.get(1).equals("")) {
                    p_avg = (int) purge_list.get(2);
                    p_min = (int) purge_list.get(3);
                    p_max = (int) purge_list.get(4);
                    p_iterations = (int) purge_list.get(5);
                }
            }
            reminderScheduleId_list.clear();
            reminderId_list.clear();
            logger(DEBUG_LEVEL, "[DBG] reminderX_list-s are CLEARED !!!");
            add_list.clear();
            purge_list.clear();
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
    @CsvFileSource(resources = "/reminders.csv", numLinesToSkip = 1)
    void test2_Add_Delete_Purge(String ams_ip, String macaddress, String boxname, int count_reminders, int reminderChannelNumber, int reminderOffset, int reminderOffset_new, int count_iterations) throws IOException, InterruptedException {
        check_csv_preconditions(ams_ip, macaddress, count_reminders, reminderChannelNumber, reminderOffset, reminderOffset_new, count_iterations);
        ArrayList add_list,
                delete_list = new ArrayList(),
                purge_list = new ArrayList();
        int a_avg = 0, a_min = 0, a_max = 0, a_iterations = 0,
                d_avg = 0, d_min = 0, d_max = 0, d_iterations = 0,
                p_avg = 0, p_min = 0, p_max = 0, p_iterations = 0;
        for (int i = 1; i <= count_iterations; i++) {
            String header = "========= ========= ========= Iteration = " + i + "/" + count_iterations + " ========= ========= =========\n";
            logger(INFO_LEVEL, header);
            long reminderScheduleId = reminderScheduleId();
            long reminderId = reminderId();

            add_list = AMS.request(ams_ip, macaddress, Operation.add, count_reminders, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
            if (add_list.get(0).equals(expected200) && add_list.get(1).equals("")) {
                a_avg = (int) add_list.get(2);
                a_min = (int) add_list.get(3);
                a_max = (int) add_list.get(4);
                a_iterations = (int) add_list.get(5);

                delete_list = AMS.request(ams_ip, macaddress, Operation.delete, count_reminders, reminderScheduleId, reminderId);
                if (delete_list.get(1).equals("")) {
                    d_avg = (int) delete_list.get(2);
                    d_min = (int) delete_list.get(3);
                    d_max = (int) delete_list.get(4);
                    d_iterations = (int) delete_list.get(5);
                }

                    /*purge_list = AMS.request(ams_ip, macaddress, Operation.purge);
                    if (purge_list.get(1).equals("")) {
                        p_avg = (int) purge_list.get(2);
                        p_min = (int) purge_list.get(3);
                        p_max = (int) purge_list.get(4);
                        p_iterations = (int) purge_list.get(5);
                    }*/
            }

            reminderScheduleId_list.clear();
            reminderId_list.clear();
            logger(DEBUG_LEVEL, "[DBG] reminderX_list-s are CLEARED !!!");
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
        //assertNotEquals(0, p_avg, "p_avg");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/reminders.csv", numLinesToSkip = 1)
    void test3_Add_Modify_Delete_Purge(String ams_ip, String macaddress, String boxname, int count_reminders, int reminderChannelNumber, int reminderOffset, int reminderOffset_new, int count_iterations) throws IOException, InterruptedException {
        check_csv_preconditions(ams_ip, macaddress, count_reminders, reminderChannelNumber, reminderOffset, reminderOffset_new, count_iterations);
        ArrayList add_list;
        ArrayList modify_list = new ArrayList();
        ArrayList delete_list = new ArrayList();
        ArrayList purge_list = new ArrayList();
        int a_avg = 0, a_min = 0, a_max = 0, a_iterations = 0,
                m_avg = 0, m_min = 0, m_max = 0, m_iterations = 0,
                d_avg = 0, d_min = 0, d_max = 0, d_iterations = 0,
                p_avg = 0, p_min = 0, p_max = 0, p_iterations = 0;
        for (int i = 1; i <= count_iterations; i++) {
            String header = "========= ========= ========= Iteration = " + i + "/" + count_iterations + " ========= ========= =========\n";
            logger(INFO_LEVEL, header);
            long reminderScheduleId = reminderScheduleId();
            long reminderId = reminderId();

            add_list = AMS.request(ams_ip, macaddress, Operation.add, count_reminders, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
            if (add_list.get(0).equals(expected200) && add_list.get(1).equals("")) {
                a_avg = (int) add_list.get(2);
                a_min = (int) add_list.get(3);
                a_max = (int) add_list.get(4);
                a_iterations = (int) add_list.get(5);

                modify_list = AMS.request(ams_ip, macaddress, Operation.modify, count_reminders, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset_new, reminderScheduleId, reminderId);
                if (modify_list.get(1).equals("")) {
                    m_avg = (int) modify_list.get(2);
                    m_min = (int) modify_list.get(3);
                    m_max = (int) modify_list.get(4);
                    m_iterations = (int) modify_list.get(5);
                }

                delete_list = AMS.request(ams_ip, macaddress, Operation.delete, count_reminders, reminderScheduleId, reminderId);
                if (delete_list.get(1).equals("")) {
                    d_avg = (int) delete_list.get(2);
                    d_min = (int) delete_list.get(3);
                    d_max = (int) delete_list.get(4);
                    d_iterations = (int) delete_list.get(5);
                }

                /*purge_list = AMS.request(ams_ip, macaddress, Operation.purge);
                if (purge_list.get(1).equals("")) {
                    p_avg = (int) purge_list.get(2);
                    p_min = (int) purge_list.get(3);
                    p_max = (int) purge_list.get(4);
                    p_iterations = (int) purge_list.get(5);
                }*/
            }
            reminderScheduleId_list.clear();
            reminderId_list.clear();
            logger(DEBUG_LEVEL, "[DBG] reminderX_list-s are CLEARED !!!");
            add_list.clear();
            modify_list.clear();
            delete_list.clear();
            purge_list.clear();
            Thread.sleep(sleep_after_iteration);
        }

        prepare_total_results(macaddress, boxname, count_reminders, count_iterations,
                a_avg, a_min, a_max, a_iterations,
                m_avg, m_min, m_max, m_iterations,
                d_avg, d_min, d_max, d_iterations,
                p_avg, p_min, p_max, p_iterations);
        assertNotEquals(0, a_avg, "a_avg");
        assertNotEquals(0, m_avg, "m_avg");
        assertNotEquals(0, d_avg, "d_avg");
        //assertNotEquals(0, p_avg, "p_avg");
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
