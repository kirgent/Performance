package tv.zodiac.dev;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * We are localhost (Charter Headend). Full chain of requests: localhost -> AMS -> STB -> AMS -> localhost
 */
class testAMS_newAPI_Performance extends API_common {
    private NewAPI_AMS AMS = new NewAPI_AMS();
    private int timeout = 20000;
    private int sleep_after_iteration = 1000;

    private ArrayList add_list,
            modify_list = new ArrayList(),
            delete_list = new ArrayList(),
            purge_list = new ArrayList();
    private int a_avg = 0, a_med = 0, a_min = 0, a_min_iteration = 0, a_max = 0, a_max_iteration = 0, a_iteration = 0,
            m_avg = 0, m_med = 0, m_min = 0, m_min_iteration = 0, m_max = 0, m_max_iteration = 0, m_iteration = 0,
            d_avg = 0, d_med = 0, d_min = 0, d_min_iteration = 0, d_max = 0, d_max_iteration = 0, d_iteration = 0,
            p_avg = 0, p_med = 0, p_min = 0, p_min_iteration = 0, p_max = 0, p_max_iteration = 0, p_iteration = 0;

    private ArrayList a_current = new ArrayList(),
            m_current = new ArrayList(),
            d_current = new ArrayList(),
            p_current = new ArrayList();

    private void print_start_header(String ams_ip, String mac, String boxname, int count_reminders, int reminderChannelNumber, int reminderOffset, int reminderOffset_new, int count_iterations) throws IOException {
        //String start = new Date().toString();
        check_csv(ams_ip, mac, boxname, count_reminders, reminderChannelNumber, reminderOffset, reminderOffset_new, count_iterations);
        logger(INFO_LEVEL, "[INF] " + new Date() + ": New start for mac=" + mac + "(" + boxname + ") to ams=" + ams_ip + ", "
                + "count_reminders=" + count_reminders + ", "
                + "reminderProgramStart=, "
                + "reminderChannelNumber=" + reminderChannelNumber + ", "
                + "reminderProgramId=" + reminderProgramId + ", "
                + "reminderOffset=" + reminderOffset + ", "
                + "reminderScheduleId=, "
                + "reminderId=");
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
    @CsvFileSource(resources = "/reminders.csv", numLinesToSkip = 1)
    void test0_Add(String ams_ip, String mac, String boxname, int count_reminders, int reminderChannelNumber, int reminderOffset, int reminderOffset_new, int count_iterations) throws InterruptedException, IOException {
        print_start_header(ams_ip, mac, boxname, count_reminders, reminderChannelNumber, reminderOffset, reminderOffset_new, count_iterations);

        /*ArrayList add_list;
        int a_avg = 0, a_med = 0, a_min = 0, a_max = 0, a_iteration = 0;
        ArrayList a_current = new ArrayList();*/

        for (int i = 1; i <= count_iterations; i++) {
            print_iteration_header(ams_ip, mac, count_reminders, i, count_iterations);

            long reminderScheduleId = reminderScheduleId(Generation.random);
            long reminderId = reminderId(Generation.random);

            add_list = AMS.request(ams_ip, mac, Operation.add, count_reminders, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
            if(add_list.get(0).equals(expected200) && add_list.get(1).equals("")) {
                a_current.add(add_list.get(2));
                a_avg = (int) add_list.get(3);
                a_med = (int) add_list.get(4);
                a_min = (int) add_list.get(5);
                a_min_iteration = (int) add_list.get(8);
                a_max = (int) add_list.get(6);
                a_max_iteration = (int) add_list.get(9);
                a_iteration = (int) add_list.get(7);
            }
            reminderScheduleId_list.clear();
            reminderId_list.clear();
            logger(DEBUG_LEVEL, "[DBG] reminderX_list-s are CLEARED !!!");
            add_list.clear();
            Thread.sleep(sleep_after_iteration);
        }

        prepare_total_results(mac, boxname, count_reminders, count_iterations,
                a_avg, a_med, a_min, a_min_iteration, a_max, a_max_iteration, a_iteration, a_current,
                m_avg, m_med, m_min, m_min_iteration, m_max, m_max_iteration, m_iteration, m_current,
                d_avg, d_med, d_min, d_min_iteration, d_max, d_max_iteration, d_iteration, d_current,
                p_avg, p_med, p_min, p_min_iteration, p_max, p_max_iteration, p_iteration, p_current);
        assertNotEquals(0, a_avg, "a_avg");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/reminders.csv", numLinesToSkip = 1)
    void test1_Add_Purge(String ams_ip, String mac, String boxname, int count_reminders, int reminderChannelNumber, int reminderOffset, int reminderOffset_new, int count_iterations) throws InterruptedException, IOException {
        print_start_header(ams_ip, mac, boxname, count_reminders, reminderChannelNumber, reminderOffset, reminderOffset_new, count_iterations);

        /*ArrayList add_list,
                purge_list = new ArrayList();
        int a_avg = 0, a_med = 0, a_min = 0, a_max = 0, a_iteration = 0,
                p_avg = 0, p_med = 0, p_min = 0, p_max = 0, p_iteration = 0;
        ArrayList a_current = new ArrayList(),
                p_current = new ArrayList();*/

        for (int i = 1; i <= count_iterations; i++) {
            print_iteration_header(ams_ip, mac, count_reminders, i, count_iterations);

            long reminderScheduleId = reminderScheduleId(Generation.random);
            long reminderId = reminderId(Generation.random);

            add_list = AMS.request(ams_ip, mac, Operation.add, count_reminders, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
            if(add_list.get(0).equals(expected200) && add_list.get(1).equals("")) {
                a_current.add(add_list.get(2));
                a_avg = (int) add_list.get(3);
                a_med = (int) add_list.get(4);
                a_min = (int) add_list.get(5);
                a_min_iteration = (int) add_list.get(8);
                a_max = (int) add_list.get(6);
                a_max_iteration = (int) add_list.get(9);
                a_iteration = (int) add_list.get(7);

                purge_list = AMS.request(ams_ip, mac, Operation.purge);
                if(purge_list.get(1).equals("")) {
                    p_avg = (int) purge_list.get(3);
                    p_med = (int) purge_list.get(4);
                    p_min = (int) purge_list.get(5);
                    p_min_iteration = (int) purge_list.get(8);
                    p_max = (int) purge_list.get(6);
                    p_max_iteration = (int) purge_list.get(9);
                    p_iteration = (int) purge_list.get(7);
                }
            }
            reminderScheduleId_list.clear();
            reminderId_list.clear();
            logger(DEBUG_LEVEL, "[DBG] reminderX_list-s are CLEARED !!!");
            add_list.clear();
            purge_list.clear();
            Thread.sleep(sleep_after_iteration);
        }

        prepare_total_results(mac, boxname, count_reminders, count_iterations,
                a_avg, a_med, a_min, a_min_iteration, a_max, a_max_iteration, a_iteration, a_current,
                m_avg, m_med, m_min, m_min_iteration, m_max, m_max_iteration, m_iteration, m_current,
                d_avg, d_med, d_min, d_min_iteration, d_max, d_max_iteration, d_iteration, d_current,
                p_avg, p_med, p_min, p_min_iteration, p_max, p_max_iteration, p_iteration, p_current);
        assertNotEquals(0, a_avg, "a_avg");
        assertNotEquals(0, p_avg, "p_avg");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/reminders.csv", numLinesToSkip = 1)
    void test2_Add_Delete_Purge(String ams_ip, String mac, String boxname, int count_reminders, int reminderChannelNumber, int reminderOffset, int reminderOffset_new, int count_iterations) throws IOException, InterruptedException {
        print_start_header(ams_ip, mac, boxname, count_reminders, reminderChannelNumber, reminderOffset, reminderOffset_new, count_iterations);

        /*ArrayList add_list,
                delete_list = new ArrayList(),
                purge_list = new ArrayList();
        int a_avg = 0, a_med = 0, a_min = 0, a_max = 0, a_iteration = 0,
                d_avg = 0, d_med = 0, d_min = 0, d_max = 0, d_iteration = 0,
                p_avg = 0, p_med = 0, p_min = 0, p_max = 0, p_iteration = 0;
        ArrayList a_current = new ArrayList(),
                d_current = new ArrayList(),
                p_current = new ArrayList();*/

        for (int i = 1; i <= count_iterations; i++) {
            print_iteration_header(ams_ip, mac, count_reminders, i, count_iterations);

            long reminderScheduleId = reminderScheduleId(Generation.random);
            long reminderId = reminderId(Generation.random);

            add_list = AMS.request(ams_ip, mac, Operation.add, count_reminders, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
            if (add_list.get(0).equals(expected200) && add_list.get(1).equals("")) {
                a_current.add(add_list.get(2));
                a_avg = (int) add_list.get(3);
                a_med = (int) add_list.get(4);
                a_min = (int) add_list.get(5);
                a_min_iteration = (int) add_list.get(8);
                a_max = (int) add_list.get(6);
                a_max_iteration = (int) add_list.get(9);
                a_iteration = (int) add_list.get(7);

                delete_list = AMS.request(ams_ip, mac, Operation.delete, count_reminders, reminderScheduleId, reminderId);
                if (delete_list.get(1).equals("")) {
                    d_current.add(delete_list.get(2));
                    d_avg = (int) delete_list.get(3);
                    d_med = (int) delete_list.get(4);
                    d_min = (int) delete_list.get(5);
                    d_min_iteration = (int) delete_list.get(8);
                    d_max = (int) delete_list.get(6);
                    d_max_iteration = (int) delete_list.get(9);
                    d_iteration = (int) delete_list.get(7);
                }

                purge_list = AMS.request(ams_ip, mac, Operation.purge);
                if (purge_list.get(1).equals("")) {
                    p_avg = (int) purge_list.get(3);
                    p_med = (int) purge_list.get(4);
                    p_min = (int) purge_list.get(5);
                    p_min_iteration = (int) purge_list.get(8);
                    p_max = (int) purge_list.get(6);
                    p_max_iteration = (int) purge_list.get(9);
                    p_iteration = (int) purge_list.get(7);
                }
            }

            reminderScheduleId_list.clear();
            reminderId_list.clear();
            logger(DEBUG_LEVEL, "[DBG] reminderX_list-s are CLEARED !!!");
            add_list.clear();
            delete_list.clear();
            purge_list.clear();
            Thread.sleep(sleep_after_iteration);
        }

        prepare_total_results(mac, boxname, count_reminders, count_iterations,
                a_avg, a_med, a_min, a_min_iteration, a_max, a_max_iteration, a_iteration, a_current,
                m_avg, m_med, m_min, m_min_iteration, m_max, m_max_iteration, m_iteration, m_current,
                d_avg, d_med, d_min, d_min_iteration, d_max, d_max_iteration, d_iteration, d_current,
                p_avg, p_med, p_min, p_min_iteration, p_max, p_max_iteration, p_iteration, p_current);
        assertNotEquals(0, a_avg, "a_avg");
        assertNotEquals(0, d_avg, "d_avg");
        assertNotEquals(0, p_avg, "p_avg");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/reminders.csv", numLinesToSkip = 1)
    void test3_Add_Modify_Delete_Purge(String ams_ip, String mac, String boxname, int count_reminders, int reminderChannelNumber, int reminderOffset, int reminderOffset_new, int count_iterations) throws IOException, InterruptedException {
        print_start_header(ams_ip, mac, boxname, count_reminders, reminderChannelNumber, reminderOffset, reminderOffset_new, count_iterations);

        /*ArrayList add_list,
                modify_list = new ArrayList(),
                delete_list = new ArrayList(),
                purge_list = new ArrayList();
        int a_avg = 0, a_med = 0, a_min = 0, a_max = 0, a_iteration = 0,
                m_avg = 0, m_med = 0, m_min = 0, m_max = 0, m_iteration = 0,
                d_avg = 0, d_med = 0, d_min = 0, d_max = 0, d_iteration = 0,
                p_avg = 0, p_med = 0, p_min = 0, p_max = 0, p_iteration = 0;
        ArrayList a_current = new ArrayList(),
                m_current = new ArrayList(),
                d_current = new ArrayList(),
                p_current = new ArrayList();*/

        for (int i = 1; i <= count_iterations; i++) {
            print_iteration_header(ams_ip, mac, count_reminders, i, count_iterations);

            long reminderScheduleId = reminderScheduleId(Generation.random);
            long reminderId = reminderId(Generation.random);
            //long reminderScheduleId = reminderScheduleId(Generation.increment);
            //long reminderId = reminderId(Generation.increment);

            add_list = AMS.request(ams_ip, mac, Operation.add, count_reminders, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
            if (add_list.get(0).equals(expected200) && add_list.get(1).equals("")) {
                a_current.add(add_list.get(2));
                a_avg = (int) add_list.get(3);
                a_med = (int) add_list.get(4);
                a_min = (int) add_list.get(5);
                a_min_iteration = (int) add_list.get(8);
                a_max = (int) add_list.get(6);
                a_max_iteration = (int) add_list.get(9);
                a_iteration = (int) add_list.get(7);

                modify_list = AMS.request(ams_ip, mac, Operation.modify, count_reminders, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset_new, reminderScheduleId, reminderId);
                if (modify_list.get(1).equals("")) {
                    m_current.add(modify_list.get(2));
                    m_avg = (int) modify_list.get(3);
                    m_med = (int) modify_list.get(4);
                    m_min = (int) modify_list.get(5);
                    m_min_iteration = (int) modify_list.get(8);
                    m_max = (int) modify_list.get(6);
                    m_max_iteration = (int) modify_list.get(9);
                    m_iteration = (int) modify_list.get(7);
                }

                delete_list = AMS.request(ams_ip, mac, Operation.delete, count_reminders, reminderScheduleId, reminderId);
                if (delete_list.get(1).equals("")) {
                    d_current.add(delete_list.get(2));
                    d_avg = (int) delete_list.get(3);
                    d_med = (int) delete_list.get(4);
                    d_min = (int) delete_list.get(5);
                    d_min_iteration = (int) delete_list.get(8);
                    d_max = (int) delete_list.get(6);
                    d_max_iteration = (int) delete_list.get(9);
                    d_iteration = (int) delete_list.get(7);
                }

                purge_list = AMS.request(ams_ip, mac, Operation.purge);
                if (purge_list.get(1).equals("")) {
                    p_avg = (int) purge_list.get(3);
                    p_med = (int) purge_list.get(4);
                    p_min = (int) purge_list.get(5);
                    p_min_iteration = (int) purge_list.get(8);
                    p_max = (int) purge_list.get(6);
                    p_max_iteration = (int) purge_list.get(9);
                    p_iteration = (int) purge_list.get(7);
                }
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

        prepare_total_results(mac, boxname, count_reminders, count_iterations,
                a_avg, a_med, a_min, a_min_iteration, a_max, a_max_iteration, a_iteration, a_current,
                m_avg, m_med, m_min, m_min_iteration, m_max, m_max_iteration, m_iteration, m_current,
                d_avg, d_med, d_min, d_min_iteration, d_max, d_max_iteration, d_iteration, d_current,
                p_avg, p_med, p_min, p_min_iteration, p_max, p_max_iteration, p_iteration, p_current);
        assertNotEquals(0, a_avg, "a_avg");
        assertNotEquals(0, m_avg, "m_avg");
        assertNotEquals(0, d_avg, "d_avg");
        assertNotEquals(0, p_avg, "p_avg");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/reminders.csv", numLinesToSkip = 1)
    void test9_Purge(String ams_ip, String mac, String boxname, int count_reminders, int reminderChannelNumber, int reminderOffset, int reminderOffset_new, int count_iterations) throws IOException, InterruptedException {
        print_start_header(ams_ip, mac, boxname, count_reminders, reminderChannelNumber, reminderOffset, reminderOffset_new, count_iterations);

        /*ArrayList purge_list;
        int p_avg = 0, p_med = 0, p_min = 0, p_max = 0, p_iteration = 0;
        ArrayList p_current = new ArrayList();*/

        for (int i = 1; i <= count_iterations; i++) {
            print_iteration_header(ams_ip, mac, count_reminders, i, count_iterations);

            purge_list = AMS.request(ams_ip, mac, Operation.purge);
            if (purge_list.get(1).equals("")) {
                p_avg = (int) purge_list.get(3);
                p_med = (int) purge_list.get(4);
                p_min = (int) purge_list.get(5);
                p_min_iteration = (int) purge_list.get(8);
                p_max = (int) purge_list.get(6);
                p_max_iteration = (int) purge_list.get(9);
                p_iteration = (int) purge_list.get(7);
            }

            purge_list.clear();
            Thread.sleep(sleep_after_iteration);
        }

        prepare_total_results(mac, boxname, count_reminders, count_iterations,
                -1, a_med, a_min, a_min_iteration, a_max, a_max_iteration, a_iteration, a_current,
                m_avg, m_med, m_min, m_min_iteration, m_max, m_max_iteration, m_iteration, m_current,
                d_avg, d_med, d_min, d_min_iteration, d_max, d_max_iteration, d_iteration, d_current,
                p_avg, p_med, p_min, p_min_iteration, p_max, p_max_iteration, p_iteration, p_current);
        assertNotEquals(0, p_avg, "p_avg");
    }

}
