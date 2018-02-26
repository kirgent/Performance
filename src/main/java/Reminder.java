import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

class Reminder {
    private String deviceId;


    //1st variant
    //working variant:
    public Reminders reminders;
    Reminder(String deviceId, Reminders reminders){
        this.deviceId = deviceId;
        this.reminders = reminders;
    }
    //public String[] getReminders() {return reminders[];};


/*
    //2nd variant
    //not-tested
    @SerializedName("reminders")
    private List<String> reminders = new ArrayList<>();
    Reminder(String deviceId, List<String> reminders){
        this.deviceId = deviceId;
        this.reminders = reminders;
    }
    //List<String> getReminders_list() {return reminders;}

*/

    //common
    String getDeviceId() {return deviceId;}
}