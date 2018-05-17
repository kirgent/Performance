package tv.zodiac.dev;

import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;


/**
 * We are Headend (on localhost): chain of requests: Headend(localhost) -> AMS -> STB -> AMS -> localhost
 */
class testAMS_oldAPI_Performance extends API{

    private OLDAPI_AMS AMS = new OLDAPI_AMS();
    final private int count_iterations = 1;
    final private int count_reminders = 10;
    String mac = boxMoto2145_173;
    private String ams_ip = ams_ip_19;

    @Test
    void test1_Add_Purge() throws IOException {
        ArrayList add_list,
                purge_list = new ArrayList();
        String a_avg = "0", a_min = "0", a_max="0",
                p_avg = "0", p_min = "0", p_max="0";

        for (int i = 1; i <= count_iterations; i++) {
            System.out.println("========= ========= ========= Iteration = " + i + "/" + count_iterations + " ========= ========= =========\n"
                    + new Date());
            int reminderChannelNumber = reminderChannelNumber();
            long reminderOffset = reminderOffset();
            add_list = AMS.request(ams_ip, mac, Operation.add, count_reminders, reminderChannelNumber, reminderProgramStart, reminderProgramId, reminderOffset);
            //assertEquals(expected200, add_list.get(0));
            //assertEquals("", add_list.get(1));
            if(add_list.get(0).equals(expected200) && add_list.get(1).equals("")) {
                a_avg = add_list.get(2).toString();
                a_min = add_list.get(3).toString();
                a_max = add_list.get(4).toString();

                purge_list = AMS.request(ams_ip, mac);
                if(purge_list.get(1).equals("")) {
                    p_avg = purge_list.get(2).toString();
                    p_min = purge_list.get(3).toString();
                    p_max = purge_list.get(4).toString();
                }
            }
            add_list.clear();
            purge_list.clear();
        }
        String result = "========= ========= ========= Total measurements ========= ========= ========="
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
        String a_avg = "0", a_min = "0", a_max="0",
                d_avg = "0", d_min = "0", d_max="0",
                p_avg = "0", p_min = "0", p_max="0";
        for (int i = 1; i <= count_iterations; i++) {
            System.out.println("========= ========= ========= Iteration = " + i + "/" + count_iterations + " ========= ========= =========\n"
                    + new Date());
            int reminderChannelNumber = reminderChannelNumber();
            long reminderOffset = reminderOffset();
            add_list = AMS.request(ams_ip, mac, Operation.add, count_reminders, reminderChannelNumber, reminderProgramStart, reminderProgramId, reminderOffset);
            //assertEquals(expected200, add_list.get(0));
            //assertEquals("", add_list.get(1));
            if (add_list.get(0).equals(expected200) && add_list.get(1).equals("")) {
                a_avg = add_list.get(2).toString();
                a_min = add_list.get(3).toString();
                a_max = add_list.get(4).toString();

                delete_list = AMS.request(ams_ip, mac, Operation.delete, count_reminders, reminderChannelNumber, reminderProgramStart, reminderProgramId, reminderOffset);
                if(delete_list.get(1).equals("")) {
                    d_avg = delete_list.get(2).toString();
                    d_min = delete_list.get(3).toString();
                    d_max = delete_list.get(4).toString();
                }

                purge_list = AMS.request(ams_ip, mac);
                if(purge_list.get(1).equals("")) {
                    p_avg = purge_list.get(2).toString();
                    p_min = purge_list.get(3).toString();
                    p_max = purge_list.get(4).toString();
                }
            }
            add_list.clear();
            delete_list.clear();
            purge_list.clear();
        }
        String result = "========= ========= ========= Total measurements ========= ========= ========="
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

}
