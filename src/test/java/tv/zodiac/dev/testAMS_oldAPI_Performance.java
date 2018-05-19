package tv.zodiac.dev;

import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * We are localhost (Charter Headend). Full chain of requests: localhost -> AMS -> STB -> AMS -> localhost
 */
class testAMS_oldAPI_Performance extends API{

    private OLDAPI_AMS AMS = new OLDAPI_AMS();
    final private int count_iterations = 1;
    final private int count_reminders = 1000;
    String mac = boxMoto2145_173;
    private String ams_ip = ams_ip_19;

    @Test
    void test1_Add_Purge() throws IOException, InterruptedException {
        ArrayList add_list,
                purge_list = new ArrayList();
        int a_avg = 0, a_min = 0, a_max=0, a_iterations = 0,
                p_avg = 0, p_min = 0, p_max=0, p_iterations = 0;

        for (int i = 1; i <= count_iterations; i++) {
            System.out.println("========= ========= ========= Iteration = " + i + "/" + count_iterations + " ========= ========= =========");
            int reminderChannelNumber = reminderChannelNumber();
            long reminderOffset = reminderOffset();
            add_list = AMS.request(ams_ip, mac, Operation.add, count_reminders, reminderChannelNumber, reminderProgramStart, reminderProgramId, reminderOffset);
            //assertEquals(expected200, add_list.get(0));
            //assertEquals("", add_list.get(1));
            if(add_list.get(0).equals(expected200) && add_list.get(1).equals("")) {
                a_avg = (int)add_list.get(2);
                a_min = (int)add_list.get(3);
                a_max = (int)add_list.get(4);
                a_iterations = (int)add_list.get(5);

                purge_list = AMS.request(ams_ip, mac);
                if(purge_list.get(1).equals("")) {
                    p_avg = (int)purge_list.get(2);
                    p_min = (int)purge_list.get(3);
                    p_max = (int)purge_list.get(4);
                    p_iterations = (int)add_list.get(5);
                }
            }
            add_list.clear();
            purge_list.clear();
            Thread.sleep(1000);
        }

        if(a_avg != 0 && p_avg != 0) {
            String result = "========= ========= ========= Total measurements ========= ========= ========="
                    + "\n" + new Date() + ", macaddress=" + mac + ", count_reminders=" + count_reminders + ", count_iterations=" + a_iterations + "/" + count_iterations
                    + "\n   add avg=" + a_avg + "ms, min=" + a_min + "ms, max=" + a_max + "ms, /" + a_iterations
                    + "\n purge avg=" + p_avg + "ms, min=" + p_min + "ms, max=" + p_max + "ms, /" + p_iterations
                    + "\n========= ========= ========= ========= ========= ========= ========= =========";
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
    void test2_Add_Delete_Purge() throws IOException, InterruptedException {
        ArrayList add_list,
                delete_list = new ArrayList(),
                purge_list = new ArrayList();
        int a_avg = 0, a_min = 0, a_max=0, a_iterations = 0,
                d_avg = 0, d_min = 0, d_max=0, d_iterations = 0,
                p_avg = 0, p_min = 0, p_max=0, p_iterations = 0;
        for (int i = 1; i <= count_iterations; i++) {
            System.out.println("========= ========= ========= Iteration = " + i + "/" + count_iterations + " ========= ========= =========");
            int reminderChannelNumber = reminderChannelNumber();
            long reminderOffset = reminderOffset();
            add_list = AMS.request(ams_ip, mac, Operation.add, count_reminders, reminderChannelNumber, reminderProgramStart, reminderProgramId, reminderOffset);
            //assertEquals(expected200, add_list.get(0));
            //assertEquals("", add_list.get(1));
            if (add_list.get(0).equals(expected200) && add_list.get(1).equals("")) {
                a_avg = (int)add_list.get(2);
                a_min = (int)add_list.get(3);
                a_max = (int)add_list.get(4);
                a_iterations = (int)add_list.get(5);

                delete_list = AMS.request(ams_ip, mac, Operation.delete, count_reminders, reminderChannelNumber, reminderProgramStart, reminderProgramId, reminderOffset);
                if(delete_list.get(1).equals("")) {
                    d_avg = (int)delete_list.get(2);
                    d_min = (int)delete_list.get(3);
                    d_max = (int)delete_list.get(4);
                    d_iterations = (int)add_list.get(5);
                }

                purge_list = AMS.request(ams_ip, mac);
                if(purge_list.get(1).equals("")) {
                    p_avg = (int)purge_list.get(2);
                    p_min = (int)purge_list.get(3);
                    p_max = (int)purge_list.get(4);
                    p_iterations = (int)add_list.get(5);
                }
            }
            add_list.clear();
            delete_list.clear();
            purge_list.clear();
            Thread.sleep(1000);
        }

        if(a_avg != 0 && d_avg != 0 && p_avg != 0) {
            String result = "========= ========= ========= Total measurements ========= ========= ========="
                    + "\n" + new Date() + ", macaddress=" + mac + ", count_reminders=" + count_reminders + ", count_iterations=" + a_iterations + "/" + count_iterations
                    + "\n   add avg=" + a_avg + "ms, min=" + a_min + "ms, max=" + a_max + "ms, /" + a_iterations
                    + "\ndelete avg=" + d_avg + "ms, min=" + d_min + "ms, max=" + d_max + "ms, /" + d_iterations
                    + "\n purge avg=" + p_avg + "ms, min=" + p_min + "ms, max=" + p_max + "ms, /" + p_iterations
                    + "\n========= ========= ========= ========= ========= ========= ========= =========";
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

}
