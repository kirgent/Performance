package tv.zodiac.dev;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

/**
 * We are Headend (on localhost): chain of requests: localhost -> AMS -> STB -> AMS -> localhost
 */
class testAMS_Reminder_Add_Modify_Delete_Purge__average extends API{

    private API_AMS AMS = new API_AMS();
    final private int countrepeat = 100;
    final private int count = 1;

    @Test
    @Disabled
    void test1_Add_Purge__average() throws IOException {
        ArrayList add_list = new ArrayList();
        ArrayList purge_list = new ArrayList();
        for (int i = 1; i <= countrepeat; i++) {
            long reminderChannelNumber = reminderChannelNumber();
            long reminderOffset = reminderOffset();
            long reminderScheduleId = reminderScheduleId();
            long reminderId = reminderId();
            add_list = AMS.Request(mac, Operation.add, count, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
            if(add_list.get(1).equals("")) {
                purge_list = AMS.Request(mac, Operation.purge);
            }
        }
        System.out.println("========= ========= ========="
                + "\nFINISH average add=" + add_list.get(2)
                + "\nFINISH average purge=" + purge_list.get(2)
                +"\n========= ========= =========");
    }

    @Test
    void test2_Add_Modify_Purge___average() throws IOException {
        ArrayList add_list = new ArrayList();
        ArrayList modify_list = new ArrayList();
        ArrayList purge_list = new ArrayList();
        String a_avg = "", a_min = "", a_max="";
        String m_avg = "", m_min = "", m_max="";
        String p_avg = "", p_min = "", p_max="";
        for (int i = 1; i <= countrepeat; i++) {
            long reminderChannelNumber = reminderChannelNumber();
            long reminderOffset = reminderOffset();
            long reminderOffset_new = reminderOffset();
            long reminderScheduleId = reminderScheduleId();
            long reminderId = reminderId();
            add_list = AMS.Request(mac, Operation.add, count, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
            if(add_list.get(1).equals("")) {
                a_avg = add_list.get(2).toString();
                a_min = add_list.get(3).toString();
                a_max = add_list.get(4).toString();

                modify_list = AMS.Request(mac, Operation.modify, count, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset_new, reminderScheduleId, reminderId);
                if(modify_list.get(1).equals("")) {
                    m_avg = modify_list.get(2).toString();
                    m_min = modify_list.get(3).toString();
                    m_max = modify_list.get(4).toString();
                }

                purge_list = AMS.Request(mac, Operation.purge);
                if(purge_list.get(1).equals("")) {
                    p_avg = purge_list.get(2).toString();
                    p_min = purge_list.get(3).toString();
                    p_max = purge_list.get(4).toString();
                }
            }

            reminderScheduleId_list.clear();
            reminderId_list.clear();
            add_list.clear();
            modify_list.clear();
            purge_list.clear();
        }
        System.out.println("========= ========= ========="
                + "\nFINISH add avg=" + a_avg + ", min=" + a_min + ", max=" + a_max
                + "\nFINISH modify avg=" + m_avg + ", min=" + m_min + ", max=" + m_max
                + "\nFINISH purge avg=" + p_avg + ", min=" + p_min + ", max=" + p_max
                + "\n========= ========= =========");
        /*if (add_list.size()==3) {
            System.out.println("FINISH average add=" + add_list.get(2));
        } else System.out.println("FINISH average add=" + add_list);

        if (modify_list.size()==3) {
            System.out.println("FINISH average modify=" + modify_list.get(2));
        } else System.out.println("FINISH average modify=" + modify_list);

        if (purge_list.size()==3) {
            System.out.println("FINISH average purge=" + purge_list.get(2));
        } else System.out.println("FINISH average add=" + purge_list);*/
    }

    @Test
    void test3_Add_Modify_Delete_Purge__average() throws IOException {
        ArrayList add_list = new ArrayList();
        ArrayList modify_list = new ArrayList();
        ArrayList delete_list = new ArrayList();
        ArrayList purge_list = new ArrayList();
        String a_avg = "", a_min = "", a_max="";
        String m_avg = "", m_min = "", m_max="";
        String d_avg = "", d_min = "", d_max="";
        String p_avg = "", p_min = "", p_max="";
        for (int i = 1; i <= countrepeat; i++) {
            long reminderChannelNumber = reminderChannelNumber();
            long reminderOffset = reminderOffset();
            long reminderOffset_new = reminderOffset();
            long reminderScheduleId = reminderScheduleId();
            long reminderId = reminderId();
            add_list = AMS.Request(mac, Operation.add, count, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
            if (add_list.get(1).equals("")) {
                a_avg = add_list.get(2).toString();
                a_min = add_list.get(3).toString();
                a_max = add_list.get(4).toString();

                modify_list = AMS.Request(mac, Operation.modify, count, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset_new, reminderScheduleId, reminderId);
                if(modify_list.get(1).equals("")) {
                    m_avg = modify_list.get(2).toString();
                    m_min = modify_list.get(3).toString();
                    m_max = modify_list.get(4).toString();
                }

                delete_list = AMS.Request(mac, Operation.delete, count, reminderScheduleId, reminderId);
                if(delete_list.get(1).equals("")) {
                    d_avg = delete_list.get(2).toString();
                    d_min = delete_list.get(3).toString();
                    d_max = delete_list.get(4).toString();
                }

                purge_list = AMS.Request(mac, Operation.purge);
                if(purge_list.get(1).equals("")) {
                    p_avg = purge_list.get(2).toString();
                    p_min = purge_list.get(3).toString();
                    p_max = purge_list.get(4).toString();
                }
            }
        }
        System.out.println("========= ========= ========="
                + "FINISH add avg=" + a_avg + ", min=" + a_min + ", max=" + a_max
                + "\nFINISH modify avg=" + m_avg + ", min=" + m_min + ", max=" + m_max
                + "\nFINISH delete avg=" + d_avg + ", min=" + d_min + ", max=" + d_max
                + "\nFINISH purge avg=" + p_avg + ", min=" + p_min + ", max=" + p_max
                + "========= ========= =========");
        /*System.out.println("FINISH average add=" + add_list.get(2)
                + "\nFINISH average modify=" + modify_list.get(2)
                + "\nFINISH average delete=" + delete_list.get(2)
                + "\nFINISH average purge=" + purge_list.get(2));*/
    }

    @Test
    void test4_Add_Delete_Purge__average() throws IOException, InterruptedException {
        ArrayList add_list = new ArrayList();
        ArrayList delete_list = new ArrayList();
        ArrayList purge_list = new ArrayList();
        for (int i = 1; i <= countrepeat; i++) {
            long reminderScheduleId = reminderScheduleId();
            long reminderId = reminderId();
            add_list = AMS.Request(mac, Operation.add, count, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
            if (add_list.get(1).equals("")) {
                delete_list = AMS.Request(mac, Operation.delete, count, reminderScheduleId, reminderId);
                purge_list = AMS.Request(mac, Operation.purge);
            }
        }
        System.out.println("FINISH average add=" + add_list.get(2)
                + "\nFINISH average delete=" + delete_list.get(2)
                + "\nFINISH average purge=" + purge_list.get(2));
    }

}
