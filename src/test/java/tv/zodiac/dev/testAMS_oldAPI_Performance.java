package tv.zodiac.dev;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


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
        ArrayList add_avg_list,
                purge_avg_list = new ArrayList();
        String a_avg = "", a_min = "", a_max="",
                p_avg = "", p_min = "", p_max="";

        for (int i = 1; i <= count_iterations; i++) {
            System.out.println("========= ========= =========\nIteration = " + i);
            long reminderChannelNumber = reminderChannelNumber();
            long reminderOffset = reminderOffset();
            add_avg_list = AMS.request(ams_ip, mac, Operation.add, count_reminders, reminderChannelNumber, reminderProgramStart, reminderProgramId, reminderOffset);
            assertEquals(expected200, add_avg_list.get(0));
            assertEquals("", add_avg_list.get(1));
            if(add_avg_list.get(1).equals("")) {
                a_avg = add_avg_list.get(2).toString();
                a_min = add_avg_list.get(3).toString();
                a_max = add_avg_list.get(4).toString();

                purge_avg_list = AMS.request(ams_ip, mac);
                if(purge_avg_list.get(1).equals("")) {
                    p_avg = purge_avg_list.get(2).toString();
                    p_min = purge_avg_list.get(3).toString();
                    p_max = purge_avg_list.get(4).toString();
                }
            }
            add_avg_list.clear();
            purge_avg_list.clear();
        }
        System.out.println("========= ========= ========= ========= ========= ========="
                + "\nFINISH   add avg = " + a_avg + ", min=" + a_min + ", max=" + a_max
                + "\nFINISH purge avg = " + p_avg + ", min=" + p_min + ", max=" + p_max
                + "\n========= ========= ========= ========= ========= =========");
    }

    @Test
    void test2_Add_Delete_Purge() throws IOException {
        ArrayList add_avg_list,
                delete_avg_list = new ArrayList(),
                purge_avg_list = new ArrayList();
        String a_avg = "", a_min = "", a_max="",
                d_avg = "", d_min = "", d_max="",
                p_avg = "", p_min = "", p_max="";
        for (int i = 1; i <= count_iterations; i++) {
            System.out.println("========= ========= =========\nIteration = " + i);
            long reminderChannelNumber = reminderChannelNumber();
            long reminderOffset = reminderOffset();
            add_avg_list = AMS.request(ams_ip, mac, Operation.add, count_reminders, reminderChannelNumber, reminderProgramStart, reminderProgramId, reminderOffset);
            assertEquals(expected200, add_avg_list.get(0));
            assertEquals("", add_avg_list.get(1));
            if (add_avg_list.get(1).equals("")) {
                a_avg = add_avg_list.get(2).toString();
                a_min = add_avg_list.get(3).toString();
                a_max = add_avg_list.get(4).toString();

                delete_avg_list = AMS.request(ams_ip, mac, Operation.delete, count_reminders, reminderChannelNumber, reminderProgramStart, reminderProgramId, reminderOffset);
                if(delete_avg_list.get(1).equals("")) {
                    d_avg = delete_avg_list.get(2).toString();
                    d_min = delete_avg_list.get(3).toString();
                    d_max = delete_avg_list.get(4).toString();
                }

                purge_avg_list = AMS.request(ams_ip, mac);
                if(purge_avg_list.get(1).equals("")) {
                    p_avg = purge_avg_list.get(2).toString();
                    p_min = purge_avg_list.get(3).toString();
                    p_max = purge_avg_list.get(4).toString();
                }
            }
            add_avg_list.clear();
            delete_avg_list.clear();
            purge_avg_list.clear();
        }
        System.out.println("========= ========= ========= ========= ========= ========="
                + "\nFINISH    add avg = " + a_avg + ", min=" + a_min + ", max=" + a_max
                + "\nFINISH delete avg = " + d_avg + ", min=" + d_min + ", max=" + d_max
                + "\nFINISH  purge avg = " + p_avg + ", min=" + p_min + ", max=" + p_max
                + "\n========= ========= ========= ========= ========= =========");
    }

}
