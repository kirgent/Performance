package tv.zodiac.dev;

import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * We are Headend (on localhost): chain of requests: Headend(localhost) -> AMS -> STB -> AMS -> localhost
 */
class testAMS_newAPI_Performance extends API{

    private NEWAPI_AMS AMS = new NEWAPI_AMS();
    final private int count_iterations = 1;
    final private int count_reminders = 500;
    String mac = boxD102;
    private String ams_ip = ams_ip_4;

    @Test
    void test1_Add_Purge() throws IOException, InterruptedException {
        ArrayList add_list,
                purge_list = new ArrayList();
        String a_avg = "0", a_min = "0", a_max="0",
                p_avg = "0", p_min = "0", p_max="0";
        for (int i = 1; i <= count_iterations; i++) {
            System.out.println("========= ========= ========= Iteration = " + i + "/" + count_iterations + " ========= ========= =========");
            //int reminderChannelNumber = reminderChannelNumber();
            int reminderChannelNumber = 2;
            //2,31,211,209,63,755,808,631
            long reminderOffset = reminderOffset();
            long reminderScheduleId = reminderScheduleId();
            long reminderId = reminderId();
            add_list = AMS.request(ams_ip, mac, Operation.add, count_reminders, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
            if(add_list.get(0).equals(expected200) && add_list.get(1).equals("")) {
                a_avg = add_list.get(2).toString();
                a_min = add_list.get(3).toString();
                a_max = add_list.get(4).toString();

                purge_list = AMS.request(ams_ip, mac, Operation.purge);
                if(purge_list.get(1).equals("")) {
                    p_avg = purge_list.get(2).toString();
                    p_min = purge_list.get(3).toString();
                    p_max = purge_list.get(4).toString();
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

        String result = "========= ========= ========= ========= ========= ========="
                + "\n" + new Date()
                + "\ncount_reminders=" + count_reminders + ",   add avg=" + a_avg + ", min=" + a_min + "ms, max=" + a_max + "ms"
                + "\ncount_reminders=" + count_reminders + ", purge avg=" + p_avg + ", min=" + p_min + "ms, max=" + p_max + "ms"
                + "\n========= ========= ========= ========= ========= =========";
        System.out.println(result);
        FileWriter writer = new FileWriter("output.log", true);
        writer.write(result);
        writer.append('\n');
        writer.flush();
    }

    @Test
    void test2_Add_Delete_Purge() throws IOException {
        ArrayList add_list,
                delete_list = new ArrayList(),
                purge_list = new ArrayList();
        String a_avg = "", a_min = "", a_max="",
                d_avg = "", d_min = "", d_max="",
                p_avg = "", p_min = "", p_max="";
        for (int i = 1; i <= count_iterations; i++) {
            System.out.println("========= ========= ========= Iteration = " + i + "/" + count_iterations + " ========= ========= =========");
            int reminderChannelNumber = reminderChannelNumber();
            long reminderOffset = reminderOffset();
            long reminderScheduleId = reminderScheduleId();
            long reminderId = reminderId();
            add_list = AMS.request(ams_ip, mac, Operation.add, count_reminders, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
            if(add_list.get(0).equals(expected200) && add_list.get(1).equals("")) {
                a_avg = add_list.get(2).toString();
                a_min = add_list.get(3).toString();
                a_max = add_list.get(4).toString();

                delete_list = AMS.request(ams_ip, mac, Operation.delete, count_reminders, reminderScheduleId, reminderId);
                if(delete_list.get(1).equals("")) {
                    d_avg = delete_list.get(2).toString();
                    d_min = delete_list.get(3).toString();
                    d_max = delete_list.get(4).toString();
                }

                purge_list = AMS.request(ams_ip, mac, Operation.purge);
                if(purge_list.get(1).equals("")) {
                    p_avg = purge_list.get(2).toString();
                    p_min = purge_list.get(3).toString();
                    p_max = purge_list.get(4).toString();
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
        String result = "========= ========= ========= ========= ========= ========="
                + "\n" + new Date()
                + "\ncount_reminders=" + count_reminders + ",    add avg=" + a_avg + ", min=" + a_min + "ms, max=" + a_max + "ms"
                + "\ncount_reminders=" + count_reminders + ", delete avg=" + d_avg + ", min=" + d_min + "ms, max=" + d_max + "ms"
                + "\ncount_reminders=" + count_reminders + ",  purge avg=" + p_avg + ", min=" + p_min + "ms, max=" + p_max + "ms"
                + "\n========= ========= ========= ========= ========= =========";
        System.out.println(result);
        FileWriter writer = new FileWriter("output.log", true);
        writer.write(result);
        writer.append('\n');
        writer.flush();
    }

    @Test
    void test3_Add_Modify_Delete_Purge() throws IOException {
        ArrayList add_list,
                modify_list = new ArrayList(),
                delete_list = new ArrayList(),
                purge_list = new ArrayList();
        String a_avg = "", a_min = "", a_max="",
                m_avg = "", m_min = "", m_max="",
                d_avg = "", d_min = "", d_max="",
                p_avg = "", p_min = "", p_max="";
        for (int i = 1; i <= count_iterations; i++) {
            System.out.println("========= ========= ========= Iteration = " + i + "/" + count_iterations + " ========= ========= =========");
            int reminderChannelNumber = reminderChannelNumber();
            long reminderOffset = reminderOffset();
            long reminderOffset_new = reminderOffset();
            long reminderScheduleId = reminderScheduleId();
            long reminderId = reminderId();
            add_list = AMS.request(ams_ip, mac, Operation.add, count_reminders, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
            if(add_list.get(0).equals(expected200) && add_list.get(1).equals("")) {
                a_avg = add_list.get(2).toString();
                a_min = add_list.get(3).toString();
                a_max = add_list.get(4).toString();

                modify_list = AMS.request(ams_ip, mac, Operation.modify, count_reminders, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset_new, reminderScheduleId, reminderId);
                if(modify_list.get(1).equals("")) {
                    m_avg = modify_list.get(2).toString();
                    m_min = modify_list.get(3).toString();
                    m_max = modify_list.get(4).toString();
                }

                delete_list = AMS.request(ams_ip, mac, Operation.delete, count_reminders, reminderScheduleId, reminderId);
                if(delete_list.get(1).equals("")) {
                    d_avg = delete_list.get(2).toString();
                    d_min = delete_list.get(3).toString();
                    d_max = delete_list.get(4).toString();
                }

                purge_list = AMS.request(ams_ip, mac, Operation.purge);
                if(purge_list.get(1).equals("")) {
                    p_avg = purge_list.get(2).toString();
                    p_min = purge_list.get(3).toString();
                    p_max = purge_list.get(4).toString();
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
        String result = "========= ========= ========= ========= ========= ========="
                + "\n" + new Date()
                + "\ncount_reminders=" + count_reminders + ",    add avg=" + a_avg + ", min=" + a_min + "ms, max=" + a_max + "ms"
                + "\ncount_reminders=" + count_reminders + ", modify avg=" + m_avg + ", min=" + m_min + "ms, max=" + m_max + "ms"
                + "\ncount_reminders=" + count_reminders + ", delete avg=" + d_avg + ", min=" + d_min + "ms, max=" + d_max + "ms"
                + "\ncount_reminders=" + count_reminders + ",  purge avg=" + p_avg + ", min=" + p_min + "ms, max=" + p_max + "ms"
                + "\n========= ========= ========= ========= ========= =========";
        System.out.println(result);
        FileWriter writer = new FileWriter("output.log", true);
        writer.write(result);
        writer.append('\n');
        writer.flush();
    }

}
