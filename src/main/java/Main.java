import java.io.IOException;
import java.text.ParseException;

public class Main extends API {

    private static String operation;
    private static String param;

    private String macaddress_by_default = "172.30.81.4";
    private String operation_by_default = "Check";

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
            "\n\tused AMS: " + ams_ip +
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


    public void main(String args[]) throws IOException, InterruptedException, ParseException {

        /*Logger logger = Logger.getLogger("test_reminder.log");
        FileHandler fh;
        fh = new FileHandler("test_reminder.log");
        logger.addHandler(fh);
        logger.info("My first log");*/

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


/*        if (args[0].isEmpty()){
            System.out.println("No macaddress specified!" + synopsys);
            return;

            } else if (args[0].length() != 12) {
                System.out.println("Incorrect macaddress specified!" + synopsys);
                return;
            } else {
                macaddress = args[0];
                //System.out.println("[DBG] macaddress is correct: "+macaddress);
            }*/
        //}
        //catch (ArrayIndexOutOfBoundsException e){
        //    System.out.print("No macaddress specified!" + synopsys);
        //}

        //check the args[1] = OPERATION


        //if (args[0].isEmpty())
        if (args.length >= 1) {
            macaddress = args[0];
        }
        else {
            macaddress = macaddress_by_default;
        }


        if (args.length >= 2) {
            operation = args[1];
        }
        else {
            operation = operation_by_default;
        }


        if (args.length >= 3) {
            param = args[2];
            if (operation.equalsIgnoreCase("Change")) {
                ams_ip = param;
            }
        }

        if (operation.equalsIgnoreCase("all")
                || operation.equalsIgnoreCase("add")
                || operation.equalsIgnoreCase("delete")) {
            switch (param) {
                case "48":
                    count_reminders = 48;
                    break;
                case "288":
                    count_reminders = 288;
                    break;
                case "720":
                    count_reminders = 720;
                    break;
                default:
                    count_reminders = 720;
            }
        }


        if (args.length>0){
            for (int i = 0; i < args.length; i++) {
                System.out.println("[DBG] args.length=" + args.length + " and args[" + i + "]: " + args[i]);
            }
        }


        /*for (int i=0; i<args.length; ++i) {
            String arg = args[i];
            if (arg.equals("-v")) {
                Boolean v = true;
            } else if (arg.equals("-size")) {
                int size = Integer.parseInt(args[++i]);//да, здесь может быть ArrayIndexOutOfBoundsException
            } else if (arg.equals("-x")) {
                boolean x = true;
            }
        }*/

        System.out.println("[DBG] used macaddress=" + macaddress + ", operation=" + operation + ", param=" + param);

        API api = new API();

        switch (operation){
            case "Check":
            case "check":
                api.Check_registration(macaddress, charterapi);
                break;
            case "Change":
            case "change":
                api.Change_registration(macaddress, charterapi, ams_ip);
                break;
            case "Purge":
            case "purge":
                //api.Operation(ams_ip_by_default, macaddress[0], "Purge", false);
                break;
            case "Purge2":
            case "purge2":
                //api.Operation(ams_ip_by_default, macaddress[0], "Purge", false);
                break;
            //case "Add":
            //case "add": api.Operation("Add", macaddress, count_reminders, count_iterations, ams_ip_default); break;
            case "Modify":
            case "modify":
                //api.Operation(ams_ip_by_default, macaddress[0], "Modify", true, count_reminders_by_default, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
                break;
            //case "Delete":
            //case "delete": api.Operation("Delete", macaddress, count_reminders, count_iterations, ams_ip_default); break;
            default:
                api.Check_registration(macaddress, charterapi);
        }
    }
}
