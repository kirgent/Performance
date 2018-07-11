package tv.zodiac.dev;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

class commonPerformance extends API_common {

    ArrayList add_list,
            modify_list = new ArrayList(),
            delete_list = new ArrayList(),
            purge_list = new ArrayList(),
            request_list = new ArrayList();

    ArrayList a_current = new ArrayList(),
            m_current = new ArrayList(),
            d_current = new ArrayList(),
            p_current = new ArrayList(),
            current = new ArrayList();

    int a_avg = 0, a_med = 0, a_min = 0, a_min_iteration = 0, a_max = 0, a_max_iteration = 0, a_total_i = 0,
            m_avg = 0, m_med = 0, m_min = 0, m_min_iteration = 0, m_max = 0, m_max_iteration = 0, m_total_i = 0,
            d_avg = 0, d_med = 0, d_min = 0, d_min_iteration = 0, d_max = 0, d_max_iteration = 0, d_total_i = 0,
            p_avg = 0, p_med = 0, p_min = 0, p_min_iteration = 0, p_max = 0, p_max_iteration = 0, p_total_i = 0,
            avg = 0, med = 0, min = 0, min_iteration = 0, max = 0, max_iteration = 0, total_i = 0;


    boolean use_random = false;
    private int timeout = 20000;

    void before(String ams_ip, String mac, String boxname, int sleep_after_iteration, int count_reminders, int count_iterations, int reminderChannelNumber) throws IOException {
        check_csv(ams_ip, mac, boxname, sleep_after_iteration, count_reminders, count_iterations, reminderChannelNumber);

        printStartHeader(ams_ip, mac, boxname, count_reminders, reminderChannelNumber);

        if(reminderChannelNumber == -1){
            use_random = true;
        }
    }

    private void printStartHeader(String ams_ip, String mac, String boxname, int count_reminders, int reminderChannelNumber) throws IOException {
        starttime = new Date();
        logger(INFO_LEVEL, "[INF] " + starttime + ": New start for mac=" + mac + "(" + boxname + ") to ams=" + ams_ip + ", "
                + "count_reminders=" + count_reminders + ", "
                + "reminderProgramStart=" + reminderProgramStart + ", "
                + "reminderChannelNumber=" + reminderChannelNumber + ", "
                + "reminderProgramId=" + reminderProgramId + ", "
                + "reminderOffset=" + reminderOffset + ", "
                + "reminderScheduleId=, "
                + "reminderId=");
    }


}
