import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

class Reminder {
    public  String deviceId;

/*
    //WORKING variant
    public Reminders reminders;
    Reminder(String deviceId, Reminders reminders){
        this.deviceId = deviceId;
        this.reminders = reminders;
    }
    //public String[] getReminders() {return reminders[]};
*/


    //NOT-TESTED variant
    //@SerializedName("reminders")
    //public List<Reminders> reminders;
    public ArrayList<Reminders> reminders;

    //Reminder(String deviceId, List<String> reminders){
    //    this.deviceId = deviceId;
    //}
    //List<String> getReminders_list() {return reminders;}



    //common
    //String getDeviceId() {return deviceId;}
}