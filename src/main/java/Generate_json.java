import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;

class Generate_json {
    //String json, String macaddress, String[] channel, String[] data, int[] reminderOffset) {
    String macaddress="A0722CB1AF24";
    int reminderOffset = 0;

    String json_add1 = "{\"deviceId\":" + macaddress+ ",\"reminders\":[{\"operation\":\"Add\",\"reminderChannelNumber\":2,\"reminderProgramStart\":\"2018-06-06 00:00\",\"reminderProgramId\":0,\"reminderOffset\":0}]}";

    /*String json_add5 = "{\"deviceId\":" + macaddress + ",\"reminders\":["
            + "{\"operation\":Add,\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 00:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "},"
            + "{\"operation\":Add,\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 00:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "},"
            + "{\"operation\":Add,\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 01:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "},"
            + "{\"operation\":Add,\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 01:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "},"
            + "{\"operation\":Add,\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 02:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}]}";
*/

        /* //working variant:
        String json = json_add5;
        Reminder reminder = new Gson().fromJson(json, Reminder.class);
        System.out.println("[DBG] count of reminders in class: " + reminder.reminders.length);
        System.out.println("deviceId:\"" + reminder.deviceId + "\"");
        for (int i = 0; i < reminder.reminders.length; i++) {
            System.out.println("operation:\"" + reminder.reminders[i].getOperation() + "\" " +
                    "reminderChannelNumber:" + reminder.reminders[i].getReminderChannelNumber() + " " +
                    "reminderProgramStart:\"" + reminder.reminders[i].getReminderProgramStart() + "\" " +
                    "reminderProgramId:" + reminder.reminders[i].getReminderProgramId() + " " +
                    "reminderOffset:" + reminder.reminders[i].getReminderOffset());
        }
        String json2 = "";
        */

    //not-tested variant:
    /*final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    Reminder reminder = new Reminder(macaddress, Arrays.asList("Moscow", "Berlin", "Dubai"));
    String json = GSON.toJson(reminder);
*/
    //System.out.println(json);



    //Reminder reminder2 = new Gson().toJson(json2, Reminder.class);
    //Reminder reminder123 = g.fromJson(new FileReader("src/main/resources/json_add5.json"), Reminder.class);
    //Reminder reminder = g.fromJson(json_add48, Reminder.class)
}
