package test.perf.common;

import java.io.IOException;
import java.text.ParseException;

public class Main {

    private static String macaddress;
    private static String charterapi;
    private static String operation;
    private static String amsIp;
    private static String param;

    private static String macByDefault = "172.30.81.4";
    private static String operationByDefault = "Check";
    private static int count;

    String synopsys="\nNAME" +
            "\n\tReminders - java app for Add/Edit/Delete/Purge reminders and Check AMS/Change AMS registration on mac." +
            "\nSYNOPSYS" +
            "\n\tReminders [mac] [OPERATION] [COUNT]" +
            "\nDESCRIPTION" +
            "\n\tmac is a box mac, e.g. 6CB56BBA882C" +
            "\n\tOPERATION is a action for curl/json e.g. Add, Edit (it's really will be Delete+Add), Delete, Purge, Change" +
            "\n\tCOUNT is a count of reminders, can be 48, 288, 720 in one curl request" +
            "\nOPTIONS" +
            "\n\tReminders - print this help" +
            "\n\tReminders mac                - send curl for checking registration" +
            "\n\tReminders mac check          - send curl for checking registration" +
            "\n\tReminders mac change amsIp  - send curl for changing registration to amsIp" +
            "\n\tReminders mac PURGE          - clear all reminders" +
            "\n\tReminders mac ADD [count]    - ADD reminders (accordingly 48/288/720)" +
            "\n\tReminders mac MODIFY [count] - MODIFY reminders" +
            "\n\tReminders mac DELETE [count] - DELETE reminders" +
            "\n\tReminders mac all [count]    - ADD + MODIFY + DELETE reminders" +
            "\nCURRENT SETTINGS" +
            "\n\tused AMS: " +
            "\n\tused charterapi: " +
            "\n\tused count of reminders in one request: " +
            //"\n\tused count iterations: " + count_iterations +
            "\nSTATUSCODE" +
            "\n\tcode of the reminder processing result, one of the following:" +
            "\n\t0 - requested action with the reminder was accomplished successfully" +
            "\n\t2 - reminder is set for time in the past" +
            "\n\t3 - reminder is set for unknown channel" +
            "\n\t4 - reminder is unknown, applies to reminder deletion attempts" +
            "\n\thttps://svn.developonbox.ru/Charter_Docs/Projects/Cloud_Based_Guide/Requirements/APIs/Reminders%20Propagation%20API-v6.docx";


    public static void main(String args[]) throws IOException, InterruptedException, ParseException {

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
            System.out.println("No mac specified!" + synopsys);
            return;

            } else if (args[0].length() != 12) {
                System.out.println("Incorrect mac specified!" + synopsys);
                return;
            } else {
                mac = args[0];
                //System.out.println("[DBG] mac is correct: "+mac);
            }*/
        //}
        //catch (ArrayIndexOutOfBoundsException e){
        //    System.out.print("No mac specified!" + synopsys);
        //}

        //check the args[1] = OPERATION


        //if (args[0].isEmpty())
        if (args.length >= 1) {
            macaddress = args[0];
        }
        else {
            macaddress = macByDefault;
        }


        if (args.length >= 2) {
            operation = args[1];
        }
        else {
            operation = operationByDefault;
        }


        if (args.length >= 3) {
            param = args[2];
            if (operation.equalsIgnoreCase("Change")) {
                amsIp = param;
            }
        }

        if (operation.equalsIgnoreCase("all")
                || operation.equalsIgnoreCase("ADD")
                || operation.equalsIgnoreCase("DELETE")) {
            switch (param) {
                case "48":
                    count = 48;
                    break;
                case "288":
                    count = 288;
                    break;
                case "720":
                    count = 720;
                    break;
                default:
                    count = 720;
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

        System.out.println("[DBG] used mac=" + macaddress + ", operation=" + operation + ", param=" + param);

        MiddleAPI Middle = new MiddleAPI();

        switch (operation){
            case "Check":
            case "check":
                Middle.checkRegistration(charterapi, macaddress);
                break;
            case "Change":
            case "change":
                Middle.changeRegistration(macaddress, charterapi, amsIp);
                break;
            case "Purge":
            case "PURGE":
                //common.Operation(ams_ip_by_default, mac[0], "Purge", false);
                break;
            case "Purge2":
            case "purge2":
                //common.Operation(ams_ip_by_default, mac[0], "Purge", false);
                break;
            //case "Add":
            //case "ADD": common.Operation("Add", mac, count, count_iterations, ams_ip_default); break;
            case "Modify":
            case "MODIFY":
                //common.Operation(ams_ip_by_default, mac[0], "Modify", true, count_by_default, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
                break;
            //case "Delete":
            //case "DELETE": common.Operation("Delete", mac, count, count_iterations, ams_ip_default); break;

            default:
                Middle.checkRegistration(charterapi, macaddress);
        }
    }
}
