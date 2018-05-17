package tv.zodiac.dev;

import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * We are Headend (on localhost): chain of requests: Headend(localhost) -> AMS -> STB -> AMS -> localhost
 */
class testAMS_newAPI_Performance extends API{

    private NEWAPI_AMS AMS = new NEWAPI_AMS();
    final private int count_iterations = 1;
    final private int count_reminders = 500;
    String mac = boxD105;
    private String ams_ip = ams_ip_4;

    @Test
    void test1_Add_Purge() throws IOException, InterruptedException {
        ArrayList add_list,
                purge_list = new ArrayList();
        int a_avg = 0, a_min = 0, a_max=0,
                p_avg = 0, p_min = 0, p_max=0,
                a_iterations = 0,
                p_iterations = 0;

        for (int i = 1; i <= count_iterations; i++) {
            System.out.println("========= ========= ========= Iteration = " + i + "/" + count_iterations + " ========= ========= =========\n"
                    + new Date());
            //int reminderChannelNumber = reminderChannelNumber();
            int reminderChannelNumber = 2;
            //2,31,211,209,63,755,808,631
            long reminderOffset = reminderOffset();
            long reminderScheduleId = reminderScheduleId();
            long reminderId = reminderId();
            add_list = AMS.request(ams_ip, mac, Operation.add, count_reminders, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
            if(add_list.get(0).equals(expected200) && add_list.get(1).equals("")) {
                a_avg = (int)add_list.get(2);
                a_min = (int)add_list.get(3);
                a_max = (int)add_list.get(4);
                a_iterations = (int)add_list.get(5);

                purge_list = AMS.request(ams_ip, mac, Operation.purge);
                if(purge_list.get(1).equals("")) {
                    p_avg = (int)purge_list.get(2);
                    p_min = (int)purge_list.get(3);
                    p_max = (int)purge_list.get(4);
                    p_iterations = (int)add_list.get(5);
                }
            }
            reminderScheduleId_list.clear();
            reminderId_list.clear();
            if(show_debug_info) {
                System.out.println("[DBG] reminderX_list-s are CLEARED !!!");
            }
            add_list.clear();
            purge_list.clear();
        }

        if(a_avg != 0 && p_avg != 0) {
            String result = "========= ========= ========= Total measurements ========= ========= ========="
                    + "\n" + new Date()
                    + "\ncount_reminders=" + count_reminders + ",   add avg=" + a_avg + "ms, min=" + a_min + "ms, max=" + a_max + "ms, count_iterations=" + a_iterations
                    + "\ncount_reminders=" + count_reminders + ", purge avg=" + p_avg + "ms, min=" + p_min + "ms, max=" + p_max + "ms, count_iterations=" + p_iterations
                    + "\n========= ========= ========= ========= ========= =========";
            System.out.println(result);
            FileWriter writer = new FileWriter("output.log", true);
            writer.write(result);
            writer.append('\n');
            writer.flush();
        }
        assertNotEquals(0, a_avg);
        assertNotEquals(0, p_avg);

    }

    @Test
    void test2_Add_Delete_Purge() throws IOException {
        ArrayList add_list,
                delete_list = new ArrayList(),
                purge_list = new ArrayList();
        int a_avg = 0, a_min = 0, a_max=0,
                d_avg = 0, d_min = 0, d_max=0,
                p_avg = 0, p_min = 0, p_max=0,
                a_iterations = 0,
                d_iterations = 0,
                p_iterations = 0;
        for (int i = 1; i <= count_iterations; i++) {
            System.out.println("========= ========= ========= Iteration = " + i + "/" + count_iterations + " ========= ========= =========\n"
                    + new Date());
            int reminderChannelNumber = reminderChannelNumber();
            long reminderOffset = reminderOffset();
            long reminderScheduleId = reminderScheduleId();
            long reminderId = reminderId();
            add_list = AMS.request(ams_ip, mac, Operation.add, count_reminders, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
            if(add_list.get(0).equals(expected200) && add_list.get(1).equals("")) {
                a_avg = (int)add_list.get(2);
                a_min = (int)add_list.get(3);
                a_max = (int)add_list.get(4);
                a_iterations = (int)add_list.get(5);

                delete_list = AMS.request(ams_ip, mac, Operation.delete, count_reminders, reminderScheduleId, reminderId);
                if(delete_list.get(1).equals("")) {
                    d_avg = (int)delete_list.get(2);
                    d_min = (int)delete_list.get(3);
                    d_max = (int)delete_list.get(4);
                    d_iterations = (int)delete_list.get(5);
                }

                purge_list = AMS.request(ams_ip, mac, Operation.purge);
                if(purge_list.get(1).equals("")) {
                    p_avg = (int)purge_list.get(2);
                    p_min = (int)purge_list.get(3);
                    p_max = (int)purge_list.get(4);
                    p_iterations = (int)purge_list.get(5);
                }
            }

            reminderScheduleId_list.clear();
            reminderId_list.clear();
            if(show_debug_info) {
                System.out.println("[DBG] reminderX_list-s are CLEARED !!!");
            }
            add_list.clear();
            delete_list.clear();
            purge_list.clear();
        }

        if(a_avg != 0 && d_avg != 0 && p_avg != 0) {
            String result = "========= ========= ========= Total measurements ========= ========= ========="
                    + "\n" + new Date()
                    + "\ncount_reminders=" + count_reminders + ",    add avg=" + a_avg + "ms, min=" + a_min + "ms, max=" + a_max + "ms, count_iterations=" + a_iterations
                    + "\ncount_reminders=" + count_reminders + ", delete avg=" + d_avg + "ms, min=" + d_min + "ms, max=" + d_max + "ms, count_iterations=" + d_iterations
                    + "\ncount_reminders=" + count_reminders + ",  purge avg=" + p_avg + "ms, min=" + p_min + "ms, max=" + p_max + "ms, count_iterations=" + p_iterations
                    + "\n========= ========= ========= ========= ========= =========";
            System.out.println(result);
            FileWriter writer = new FileWriter("output.log", true);
            writer.write(result);
            writer.append('\n');
            writer.flush();
        }
        assertNotEquals(0, a_avg);
        assertNotEquals(0, d_avg);
        assertNotEquals(0, p_avg);
    }

    @Test
    void test3_Add_Modify_Delete_Purge() throws IOException {
        ArrayList add_list,
                modify_list = new ArrayList(),
                delete_list = new ArrayList(),
                purge_list = new ArrayList();
        int a_avg = 0, a_min = 0, a_max=0,
                m_avg = 0, m_min = 0, m_max=0,
                d_avg = 0, d_min = 0, d_max=0,
                p_avg = 0, p_min = 0, p_max=0,
                a_iterations = 0,
                m_iterations = 0,
                d_iterations = 0,
                p_iterations = 0;
        for (int i = 1; i <= count_iterations; i++) {
            System.out.println("========= ========= ========= Iteration = " + i + "/" + count_iterations + " ========= ========= =========\n"
                    + new Date());
            int reminderChannelNumber = reminderChannelNumber();
            long reminderOffset = reminderOffset();
            long reminderOffset_new = reminderOffset();
            long reminderScheduleId = reminderScheduleId();
            long reminderId = reminderId();
            add_list = AMS.request(ams_ip, mac, Operation.add, count_reminders, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
            if(add_list.get(0).equals(expected200) && add_list.get(1).equals("")) {
                a_avg = (int)add_list.get(2);
                a_min = (int)add_list.get(3);
                a_max = (int)add_list.get(4);
                a_iterations = (int)add_list.get(5);

                modify_list = AMS.request(ams_ip, mac, Operation.modify, count_reminders, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset_new, reminderScheduleId, reminderId);
                if(modify_list.get(1).equals("")) {
                    m_avg = (int)modify_list.get(2);
                    m_min = (int)modify_list.get(3);
                    m_max = (int)modify_list.get(4);
                    m_iterations = (int)modify_list.get(5);
                }

                delete_list = AMS.request(ams_ip, mac, Operation.delete, count_reminders, reminderScheduleId, reminderId);
                if(delete_list.get(1).equals("")) {
                    d_avg = (int)delete_list.get(2);
                    d_min = (int)delete_list.get(3);
                    d_max = (int)delete_list.get(4);
                    d_iterations = (int)delete_list.get(5);
                }

                purge_list = AMS.request(ams_ip, mac, Operation.purge);
                if(purge_list.get(1).equals("")) {
                    p_avg = (int)purge_list.get(2);
                    p_min = (int)purge_list.get(3);
                    p_max = (int)purge_list.get(4);
                    p_iterations = (int)purge_list.get(5);
                }
            }
            reminderScheduleId_list.clear();
            reminderId_list.clear();
            if(show_debug_info) {
                System.out.println("[DBG] reminderX_list-s are CLEARED !!!");
            }
            add_list.clear();
            modify_list.clear();
            delete_list.clear();
            purge_list.clear();
        }

        if(a_avg != 0 && m_avg != 0 && d_avg != 0 && p_avg != 0) {
            String result = "========= ========= ========= Total measurements ========= ========= ========="
                    + "\n" + new Date()
                    + "\ncount_reminders=" + count_reminders + ",    add avg=" + a_avg + "ms, min=" + a_min + "ms, max=" + a_max + "ms, count_iterations=" + a_iterations
                    + "\ncount_reminders=" + count_reminders + ", modify avg=" + m_avg + "ms, min=" + m_min + "ms, max=" + m_max + "ms, count_iterations=" + m_iterations
                    + "\ncount_reminders=" + count_reminders + ", delete avg=" + d_avg + "ms, min=" + d_min + "ms, max=" + d_max + "ms, count_iterations=" + d_iterations
                    + "\ncount_reminders=" + count_reminders + ",  purge avg=" + p_avg + "ms, min=" + p_min + "ms, max=" + p_max + "ms, count_iterations=" + p_iterations
                    + "\n========= ========= ========= ========= ========= =========";
            System.out.println(result);
            FileWriter writer = new FileWriter("output.log", true);
            writer.write(result);
            writer.append('\n');
            writer.flush();
        }
        assertNotEquals(0, a_avg);
        assertNotEquals(0, m_avg);
        assertNotEquals(0, d_avg);
        assertNotEquals(0, p_avg);
    }

}
