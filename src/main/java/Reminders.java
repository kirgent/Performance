public class Reminders {
    private String operation;
    private int reminderChannelNumber;
    private String reminderProgramStart;
    private int reminderProgramId;
    private int reminderOffset;


    public String getOperation() {return operation;}
    public void setOperation(String operation) {this.operation = operation;}

    public int getReminderChannelNumber() {return reminderChannelNumber;}
    public void setReminderChannelNumber(int reminderChannelNumber){this.reminderChannelNumber = reminderChannelNumber;}

    public String getReminderProgramStart() {return reminderProgramStart;}
    public void setReminderProgramStart(String reminderProgramStart) {this.reminderProgramStart = reminderProgramStart;}

    public int getReminderProgramId() {return reminderProgramId;}
    public void setReminderProgramId(int reminderProgramId) {this.reminderProgramId = reminderProgramId;}

    public int getReminderOffset() {return reminderOffset;}
    public void setReminderOffset(int reminderOffset) {this.reminderOffset = reminderOffset;}
}