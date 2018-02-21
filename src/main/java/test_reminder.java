import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class test_reminder {

    private static int count_iterations = 1;

    public static void main(String args[]) throws IOException, InterruptedException, ParseException {

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

        int count_reminders = 48;
        //int count_reminders = 288;
        //int count_reminders = 720;
        int default_count_reminders = 48;

        int reminderOffset = 0;
        int reminderOffset_new  = 10;

        String ams_ip = "172.30.81.4";
        //String ams_ip = "172.30.112.19"

        String charterapi_="http://spec.partnerapi.engprod-charter.net/api/pub/networksettingsmiddle/ns/settings";
        String charterapi_b="http://specb.partnerapi.engprod-charter.net/api/pub/networksettingsmiddle/ns/settings";
        String charterapi_c="http://specc.partnerapi.engprod-charter.net/api/pub/networksettingsmiddle/ns/settings";
        String charterapi_d="http://specd.partnerapi.engprod-charter.net/api/pub/networksettingsmiddle/ns/settings";
        String charterapi = charterapi_;

        String macaddress;
        String default_operation = "Check";
        String operation = "";
        String param;


        String synopsys="\nNAME" +
                "\n\tReminders - java app for Add/Edit/Delete/Purge reminders and Check AMS/Change AMS registration on MACADDRESS." +
                "\nSYNOPSYS" +
                "\n\tReminders [MACADDRESS] [OPERATION] [COUNT]" +
                "\nDESCRIPTION" +
                "\n\tMACADDRESS is a box macaddress, e.g. A0722CB1AF24" +
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
                "\n\tused AMS: " + ams_ip +
                "\n\tused charterapi: " + charterapi +
                "\n\tused count of reminders in one request: " + count_reminders +
                "\n\tused count iterations: " + count_iterations +
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
            //check the args[0]: MACADDRESS
            if (args[0].equals("")) {
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

        //check the args[1]: OPERATION
        if (args[1].equalsIgnoreCase("change")) {
            operation = "Change";
            if (!args[2].equals("")){
                param = args[2];
            }
            System.out.print("[DBG] args[1] is correct: "+args[1]+" now operation="+operation+", ams="+ams_ip);
        }

        param = args[2];

        if (args[1].equalsIgnoreCase("all")||args[1].equalsIgnoreCase("add")
                ||args[1].equalsIgnoreCase("edit")||args[1].equalsIgnoreCase("delete")) {
            switch (args[2]){
                case "48": count_reminders=48; break;
                case "288": count_reminders=288; break;
                case "720": count_reminders=720;break;
                default: count_reminders=default_count_reminders;
            }
            operation = args[1];
        }

        Reminder_middle request = new Reminder_middle();

        System.out.println("[DBG] preconditions: "+macaddress+" "+operation+" "+param);
        switch (operation){
            case "Check":
            case "check": request.Check_registration(macaddress); break;
            case "Change":
            case "change": request.Change_registration(macaddress, ams_ip); break;
            case "Purge":
            case "purge": request.Purge(macaddress); break;
            case "All":
            case "all": request.All(macaddress, count_reminders, reminderOffset, reminderOffset_new); break;
            case "Add":
            case "add": request.Add(macaddress, count_reminders, reminderOffset); break;
            case "Edit":
            case "edit": request.Edit(macaddress, count_reminders, reminderOffset, reminderOffset_new); break;
            case "Delete":
            case "delete": request.Delete(macaddress, count_reminders, reminderOffset); break;
            default: request.Check_registration(macaddress);
        }
    }
}
