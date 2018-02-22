import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

class Reminder {
    private String deviceId;

/*
    //1st variant
    //working variant:
    public Reminders[] reminders;
    Reminder(String deviceId, String[] reminders){
        this.deviceId = deviceId;
        this.reminders = reminders;
    }
    public String[] getReminders() {return reminders[];};
  */


    //2nd variant
    //not-tested
    @SerializedName("reminders")
    private List<String> reminders_list = new ArrayList<>();
    Reminder(String deviceId, List<String> reminders){
        this.deviceId = deviceId;
        this.reminders_list = reminders;
    }
    List<String> getReminders_list() {return reminders_list;}



    //common
    String getDeviceId() {return deviceId;}
}