package test.perf.common;

import java.io.IOException;

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


    public static void main(String args[]) throws IOException {

    }
}
