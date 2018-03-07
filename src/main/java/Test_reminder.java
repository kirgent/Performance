import java.io.IOException;
import java.text.ParseException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class Test_reminder {

    private static int reminderChannelNumber = 2;
    private static String reminderProgramStart = "2013-03-08 00:00";
    private static int reminderProgramId = 1;
    private static int reminderOffset = 0;
    private static int reminderScheduleId = 1;
    private static int reminderId = 1;

    @Deprecated
    int reminderOffset_new  = 10;

    public static void main(String args[]) throws IOException, InterruptedException, ParseException {

        Logger logger = Logger.getLogger("test_reminder.log");
        FileHandler fh;
        fh = new FileHandler("test_reminder.log");
        logger.addHandler(fh);
        logger.info("My first log");

        /*String months[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep","Oct", "Nov", "Dec"};
        int year;
        GregorianCalendar gcalendar = new GregorianCalendar();

        System.out.println("date: "+months[gcalendar.get(Calendar.MONTH)]+" "
                +gcalendar.get(Calendar.YEAR)+"-"
                +gcalendar.get(Calendar.MONTH)+" "
                +gcalendar.get(Calendar.DAY_OF_MONTH)+":"
                +gcalendar.get(Calendar.MINUTE)+":"
                +gcalendar.get(Calendar.SECOND));*/
/*
        String oldstring = "2011-01-18 00:00:00.0";
        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(oldstring);
        String newstring = new SimpleDateFormat("yyyy-MM-dd").format(date);
        System.out.println(newstring); // 2011-01-18 */

        int count_reminders = 2;
        int count_iterations = 10;

        //String ams_ip_default = "172.30.81.4";
        String ams_ip_default = "172.30.112.19";


        String charterapi_ = "http://spec.partnerapi.engprod-charter.net/api/pub/networksettingsmiddle/ns/settings";
        String charterapi_b = "http://specb.partnerapi.engprod-charter.net/api/pub/networksettingsmiddle/ns/settings";
        String charterapi_c = "http://specc.partnerapi.engprod-charter.net/api/pub/networksettingsmiddle/ns/settings";
        String charterapi_d = "http://specd.partnerapi.engprod-charter.net/api/pub/networksettingsmiddle/ns/settings";
        String charterapi = charterapi_b;

        String macaddress = "6CB56BBA882C";
        String operation = "";
        String param = "";

/*        String json_add5 = "{\"deviceId\":" + macaddress + ",\"reminders\":["
                + "{\"operation\":Add,\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 00:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "},"
                + "{\"operation\":Add,\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 00:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "},"
                + "{\"operation\":Add,\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 01:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "},"
                + "{\"operation\":Add,\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 01:30\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "},"
                + "{\"operation\":Add,\"reminderChannelNumber\":" + channel + ",\"reminderProgramStart\":\"" + data + " 02:00\",\"reminderProgramId\":0,\"reminderOffset\":" + reminderOffset + "}]}";
*/

        String synopsys="\nNAME" +
                "\n\tReminders - java app for Add/Edit/Delete/Purge reminders and Check AMS/Change AMS registration on MACADDRESS." +
                "\nSYNOPSYS" +
                "\n\tReminders [MACADDRESS] [OPERATION] [COUNT]" +
                "\nDESCRIPTION" +
                "\n\tMACADDRESS is a box macaddress, e.g. 6CB56BBA882C" +
                "\n\tOPERATION is a action for curl/json e.g. Add, Edit (it's really will be Delete+Add), Delete, Purge, Change" +
                "\n\tCOUNT is a count of reminders, can be 48, 288, 720 in one curl request" +
                "\nOPTIONS" +
                "\n\tReminders - print this help" +
                "\n\tReminders MACADDRESS - send curl for checking registration" +
                "\n\tReminders MACADDRESS Check - send curl for checking registration" +
                "\n\tReminders MACADDRESS Change ams_ip- send curl for changing registration to ams_ip" +
                "\n\tReminders MACADDRESS Purge - clear all reminders" +
                "\n\tReminders MACADDRESS Add [48][288][576]- add reminders (accordingly 48/288/576)" +
                "\n\tReminders MACADDRESS Edit [48][288][576]- delete reminders with current offset + add reminders with new_offset (accordingly 48/288/576)" +
                "\n\tReminders MACADDRESS Delete [48][288][576]- delete reminders (accordingly 48/288/576)" +
                "\n\tReminders MACADDRESS All [48][288][576] - add + edit + delete reminders (accordingly 48/288/576)" +
                "\nCURRENT SETTINGS" +
                "\n\tused AMS: " + ams_ip_default +
                "\n\tused charterapi: " + charterapi +
                "\n\tused count of reminders in one request: " + count_reminders +
                //"\n\tused count iterations: " + count_iterations +
                "\nSTATUSCODE" +
                "\n\tcode of the reminder processing result, one of the following:" +
                "\n\t0 - requested action with the reminder was accomplished successfully" +
                "\n\t2 - reminder is set for time in the past" +
                "\n\t3 - reminder is set for unknown channel" +
                "\n\t4 - reminder is unknown, applies to reminder deletion attempts" +
                "\n\thttps://svn.developonbox.ru/Charter_Docs/Projects/Cloud_Based_Guide/Requirements/APIs/Reminders%20Propagation%20API-v6.docx";

        for (int i = 0; i < args.length; i++) {
            System.out.println("[DBG] args[" + i + "]: " + args[i]);
        }

        //try {
            //check the args[0] = MACADDRESS
            if (args[0].isEmpty()){
                System.out.println("No macaddress specified!" + synopsys);
                return;
            } else if (args[0].length() != 12) {
                System.out.println("Incorrect macaddress specified!" + synopsys);
                return;
            } else {
                macaddress = args[0];
                //System.out.println("[DBG] macaddress is correct: "+macaddress);
            }
        //}
        //catch (ArrayIndexOutOfBoundsException e){
        //    System.out.print("No macaddress specified!" + synopsys);
        //}

        //check the args[1] = OPERATION
        if (!args[1].isEmpty()){
            operation = args[1];
            if (args[1].equalsIgnoreCase("change")){
                if (!args[2].isEmpty()){
                    param = args[2];
                }
            }
            else if (args[1].equalsIgnoreCase("all")
                    ||args[1].equalsIgnoreCase("add")
                    ||args[1].equalsIgnoreCase("edit")
                    ||args[1].equalsIgnoreCase("delete")
                    ||args[1].equalsIgnoreCase("test")) {
                switch (args[2]){
                    case "48": count_reminders=48; break;
                    case "288": count_reminders=288; break;
                    case "720": count_reminders=720;break;
                    default: count_reminders=48;
                }
            }
        }

        //param - can be as count_reminders if operation=All/Add/Edit/Delete
        //param - can be as ams_ip if operation=Change
/*        if (args[2].isEmpty()){
            param = args[2];
            //count_reminders = args[2];
            //ams_ip = args[2];
        }
*/
        //param = args[2];

        Middle api = new Middle();

        System.out.println("[DBG] used macaddress=" + macaddress + ", operation=" + operation + ", param="+param);
        switch (operation){
            case "Check":
            case "check": api.Check_registration(macaddress, charterapi); break;
            case "Change":
            case "change": api.Change_registration(macaddress, charterapi, ams_ip_default); break;
            case "Purge":
            case "purge": api.Purge(ams_ip_default, macaddress); break;
            //case "Add":
            //case "add": api.Operation("Add", macaddress, count_reminders, count_iterations, ams_ip_default); break;
            case "Modify":
            case "modify": api.Operation2("Modify", macaddress, count_reminders, ams_ip_default, reminderChannelNumber, reminderProgramStart, reminderProgramId, reminderOffset, reminderScheduleId, reminderId); break;
            //case "Delete":
            //case "delete": api.Operation("Delete", macaddress, count_reminders, count_iterations, ams_ip_default); break;
            default: api.Check_registration(macaddress, charterapi);
        }
    }
}
