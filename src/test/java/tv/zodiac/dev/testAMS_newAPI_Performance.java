package tv.zodiac.dev;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

/**
 * We are Headend (on localhost): chain of requests: Headend(localhost) -> AMS -> STB -> AMS -> localhost
 */
class testAMS_newAPI_Performance extends API{

    private NEWAPI_AMS AMS = new NEWAPI_AMS();
    final private int countrepeat = 1;
    final private int count = 10;

    @Test
    @Disabled
    void test1_Add_Purge() throws IOException {
        ArrayList add_list = new ArrayList(),
                purge_list = new ArrayList();
        String a_avg = "", a_min = "", a_max="",
                p_avg = "", p_min = "", p_max="";
        for (int i = 1; i <= countrepeat; i++) {
            System.out.println("========= ========= =========\nIteration = " + i);
            long reminderChannelNumber = reminderChannelNumber();
            long reminderOffset = reminderOffset();
            long reminderScheduleId = reminderScheduleId();
            long reminderId = reminderId();
            add_list = AMS.request(mac, Operation.add, count, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
            if(add_list.get(1).equals("")) {
                a_avg = add_list.get(2).toString();
                a_min = add_list.get(3).toString();
                a_max = add_list.get(4).toString();

                purge_list = AMS.request(mac, Operation.purge);
                if(purge_list.get(1).equals("")) {
                    p_avg = purge_list.get(2).toString();
                    p_min = purge_list.get(3).toString();
                    p_max = purge_list.get(4).toString();
                }
            }
            reminderScheduleId_list.clear();
            reminderId_list.clear();
            System.out.println("[DBG] !!! reminderX_list-s are CLEARED !!!");
            add_list.clear();
            purge_list.clear();
        }
        System.out.println("========= ========= ========= ========= ========= ========="
                + "\nFINISH   add avg = " + a_avg + ", min=" + a_min + ", max=" + a_max
                + "\nFINISH purge avg = " + p_avg + ", min=" + p_min + ", max=" + p_max
                + "\n========= ========= ========= ========= ========= =========");
    }

    @Test
    void test2_Add_Modify_Purge() throws IOException {
        ArrayList add_list = new ArrayList(),
                modify_list = new ArrayList(),
                purge_list = new ArrayList();
        String a_avg = "", a_min = "", a_max="",
                m_avg = "", m_min = "", m_max="",
                p_avg = "", p_min = "", p_max="";
        for (int i = 1; i <= countrepeat; i++) {
            System.out.println("========= ========= =========\nIteration = " + i);
            long reminderChannelNumber = reminderChannelNumber();
            long reminderOffset = reminderOffset();
            long reminderOffset_new = reminderOffset();
            long reminderScheduleId = reminderScheduleId();
            long reminderId = reminderId();
            add_list = AMS.request(mac, Operation.add, count, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
            if(add_list.get(1).equals("")) {
                a_avg = add_list.get(2).toString();
                a_min = add_list.get(3).toString();
                a_max = add_list.get(4).toString();

                modify_list = AMS.request(mac, Operation.modify, count, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset_new, reminderScheduleId, reminderId);
                if(modify_list.get(1).equals("")) {
                    m_avg = modify_list.get(2).toString();
                    m_min = modify_list.get(3).toString();
                    m_max = modify_list.get(4).toString();
                }

                purge_list = AMS.request(mac, Operation.purge);
                if(purge_list.get(1).equals("")) {
                    p_avg = purge_list.get(2).toString();
                    p_min = purge_list.get(3).toString();
                    p_max = purge_list.get(4).toString();
                }
            }

            reminderScheduleId_list.clear();
            reminderId_list.clear();
            System.out.println("[DBG] !!! reminderX_list-s are CLEARED !!!");
            add_list.clear();
            modify_list.clear();
            purge_list.clear();
        }
        System.out.println("========= ========= ========= ========= ========= ========="
                + "\nFINISH    add avg = " + a_avg + ", min=" + a_min + ", max=" + a_max
                + "\nFINISH modify avg = " + m_avg + ", min=" + m_min + ", max=" + m_max
                + "\nFINISH  purge avg = " + p_avg + ", min=" + p_min + ", max=" + p_max
                + "\n========= ========= ========= ========= ========= =========");
    }

    @Test
    void test3_Add_Modify_Delete_Purge() throws IOException {
        ArrayList add_list = new ArrayList(),
                modify_list = new ArrayList(),
                delete_list = new ArrayList(),
                purge_list = new ArrayList();
        String a_avg = "", a_min = "", a_max="",
                m_avg = "", m_min = "", m_max="",
                d_avg = "", d_min = "", d_max="",
                p_avg = "", p_min = "", p_max="";
        for (int i = 1; i <= countrepeat; i++) {
            System.out.println("========= ========= =========\nIteration = " + i);
            long reminderChannelNumber = reminderChannelNumber();
            long reminderOffset = reminderOffset();
            long reminderOffset_new = reminderOffset();
            long reminderScheduleId = reminderScheduleId();
            long reminderId = reminderId();
            add_list = AMS.request(mac, Operation.add, count, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
            if (add_list.get(1).equals("")) {
                a_avg = add_list.get(2).toString();
                a_min = add_list.get(3).toString();
                a_max = add_list.get(4).toString();

                modify_list = AMS.request(mac, Operation.modify, count, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset_new, reminderScheduleId, reminderId);
                if(modify_list.get(1).equals("")) {
                    m_avg = modify_list.get(2).toString();
                    m_min = modify_list.get(3).toString();
                    m_max = modify_list.get(4).toString();
                }

                delete_list = AMS.request(mac, Operation.delete, count, reminderScheduleId, reminderId);
                if(delete_list.get(1).equals("")) {
                    d_avg = delete_list.get(2).toString();
                    d_min = delete_list.get(3).toString();
                    d_max = delete_list.get(4).toString();
                }

                purge_list = AMS.request(mac, Operation.purge);
                if(purge_list.get(1).equals("")) {
                    p_avg = purge_list.get(2).toString();
                    p_min = purge_list.get(3).toString();
                    p_max = purge_list.get(4).toString();
                }
            }
            reminderScheduleId_list.clear();
            reminderId_list.clear();
            System.out.println("[DBG] !!! reminderX_list-s are CLEARED !!!");
            add_list.clear();
            modify_list.clear();
            delete_list.clear();
            purge_list.clear();
        }
        System.out.println("========= ========= ========= ========= ========= ========="
                + "\nFINISH    add avg = " + a_avg + ", min=" + a_min + ", max=" + a_max
                + "\nFINISH modify avg = " + m_avg + ", min=" + m_min + ", max=" + m_max
                + "\nFINISH delete avg = " + d_avg + ", min=" + d_min + ", max=" + d_max
                + "\nFINISH  purge avg = " + p_avg + ", min=" + p_min + ", max=" + p_max
                + "\n========= ========= ========= ========= ========= =========");
    }

    @Test
    void test4_Add_Delete_Purge() throws IOException {
        ArrayList add_list = new ArrayList(),
                delete_list = new ArrayList(),
                purge_list = new ArrayList();
        String a_avg = "", a_min = "", a_max="",
                d_avg = "", d_min = "", d_max="",
                p_avg = "", p_min = "", p_max="";
        for (int i = 1; i <= countrepeat; i++) {
            System.out.println("========= ========= =========\nIteration = " + i);
            long reminderScheduleId = reminderScheduleId();
            long reminderId = reminderId();
            add_list = AMS.request(mac, Operation.add, count, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
            if (add_list.get(1).equals("")) {
                a_avg = add_list.get(2).toString();
                a_min = add_list.get(3).toString();
                a_max = add_list.get(4).toString();

                delete_list = AMS.request(mac, Operation.delete, count, reminderScheduleId, reminderId);
                if(delete_list.get(1).equals("")) {
                    d_avg = delete_list.get(2).toString();
                    d_min = delete_list.get(3).toString();
                    d_max = delete_list.get(4).toString();
                }

                purge_list = AMS.request(mac, Operation.purge);
                if(purge_list.get(1).equals("")) {
                    p_avg = purge_list.get(2).toString();
                    p_min = purge_list.get(3).toString();
                    p_max = purge_list.get(4).toString();
                }
            }
            reminderScheduleId_list.clear();
            reminderId_list.clear();
            System.out.println("[DBG] !!! reminderX_list-s are CLEARED !!!");
            add_list.clear();
            delete_list.clear();
            purge_list.clear();
        }
        System.out.println("========= ========= ========= ========= ========= ========="
                + "\nFINISH    add avg = " + a_avg + ", min=" + a_min + ", max=" + a_max
                + "\nFINISH delete avg = " + d_avg + ", min=" + d_min + ", max=" + d_max
                + "\nFINISH  purge avg = " + p_avg + ", min=" + p_min + ", max=" + p_max
                + "\n========= ========= ========= ========= ========= =========");
    }

}
