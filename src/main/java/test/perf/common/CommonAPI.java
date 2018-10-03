package test.perf.common;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

import au.com.bytecode.opencsv.CSVReader;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import static java.lang.System.currentTimeMillis;
import static org.junit.Assert.assertNotNull;

/**
 * we are as Middle: send requests to AMS and got responses
 * Middle -> AMS -> box -> AMS -> Middle
 */
public class CommonAPI {

    protected Boolean show_info_level = true;
    protected Boolean show_debug_level = false;
    protected Boolean show_generated_json = false;
    private Boolean show_response_body = false;

    protected static final String INFO_LEVEL = "INF";
    protected static final String DEBUG_LEVEL = "DBG";
    private Date starttime;

    protected enum Operation { add, modify, delete, purge, blablabla, www }

    protected enum Generation { random, increment }

    protected enum Sorting { bubble, quick, selection, insertion, merge }
    protected Sorting sorting = Sorting.insertion;

    //static Logger log = Logger.getLogger(testAMS.class.getName());
    //FileHandler txtFile = new FileHandler ("log.log", true);

    protected final String charterapi_a = "http://spec.partnerapi.engprod-charter.net/common/pub";
    protected final String charterapi_b = "http://specb.partnerapi.engprod-charter.net/common/pub";
    protected final String charterapi_c = "http://specc.partnerapi.engprod-charter.net/common/pub";
    protected final String charterapi_d = "http://specd.partnerapi.engprod-charter.net/common/pub";
    protected final String postfix_settings = "/networksettingsmiddle/ns/settings";
    protected final String charterapi = charterapi_b;

    protected final String mac_wrong = "123456789012";
    final String boxD101 = "A0722CEEC970";//WB20 D101 ???
    final String boxD102 = "3438B7EB2E24";//WB20 D102
    final String boxMoto2145_173 =  "000004B9419F"; //"B077AC5D91DD"; // "000004B9419F"; //Moto_2145_Mondo_DCX3200M_17.3_346
    final String boxMoto2147_Rems = "000004D67F70"; //000004d67f70"; //Moto_2147_Mondo_DCX3200M_REMS
    protected String mac = boxMoto2147_Rems;

    //todo
    protected ArrayList reminderScheduleId_list = new ArrayList();
    public ArrayList reminderId_list = new ArrayList();

    //lists for saving all according request in ms in each iteration
    protected ArrayList add_list = new ArrayList();
    protected ArrayList modify_list = new ArrayList();
    protected ArrayList delete_list = new ArrayList();
    protected ArrayList purge_list = new ArrayList();
    protected ArrayList actual_list = new ArrayList();

    //2-dimensional array for saving min/max value and iteration where this value were last time updated
    private int[] a_max_array = {0, 0}, a_min_array = {0, 0},
            m_max_array = {0, 0}, m_min_array = {0, 0},
            d_max_array = {0, 0}, d_min_array = {0, 0},
            p_max_array = {0, 0}, p_min_array = {0, 0},
            max_array = {0, 0}, min_array = {0, 0};


    //lists for saving all requests in ms (add/modify/delte/purge) for each iteration
    protected ArrayList a_current = new ArrayList();
    protected ArrayList m_current = new ArrayList();
    protected ArrayList d_current = new ArrayList();
    protected ArrayList p_current = new ArrayList();
    protected ArrayList current = new ArrayList();
    //save all measurements for according request: add/modify/delete/purge
    protected int a_avg = 0;
    protected int a_med = 0;
    protected int a_min = 0;
    protected int a_min_iteration = 0;
    protected int a_max = 0;
    protected int a_max_iteration = 0;
    protected int a_total_i = 0;
    protected int m_avg = 0;
    protected int m_med = 0;
    protected int m_min = 0;
    protected int m_min_iteration = 0;
    protected int m_max = 0;
    protected int m_max_iteration = 0;
    protected int m_total_i = 0;
    protected int d_avg = 0;
    protected int d_med = 0;
    protected int d_min = 0;
    protected int d_min_iteration = 0;
    protected int d_max = 0;
    protected int d_max_iteration = 0;
    protected int d_total_i = 0;
    protected int p_avg = 0;
    protected int p_med = 0;
    protected int p_min = 0;
    protected int p_min_iteration = 0;
    protected int p_max = 0;
    protected int p_max_iteration = 0;
    protected int p_total_i = 0;
    protected int avg = 0;
    protected int med = 0;
    protected int min = 0;
    protected int min_iteration = 0;
    protected int max = 0;
    protected int max_iteration = 0;
    protected int total_i = 0;

    protected boolean use_random = false;
    private int timeout = 20000;

    private String REMINDERSLOG = "reminders.log";

    protected String reminderProgramStart = "";

    protected int reminderChannelNumber = reminderChannelNumber(1000);
    //int reminderChannelNumber;

    protected String reminderProgramId = "";
    //String reminderProgramId = "EP002960010113";

    //int reminderOffset = reminderOffset(15);
    protected int reminderOffset = 0;

    protected long reminderScheduleId;
    {
        try {
            reminderScheduleId = reminderScheduleId(Generation.random);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected long reminderId;
    {
        try {
            reminderId = reminderId(Generation.random);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String[] statuscode = {
            "0 - requested operation with the reminder was accomplished successfully. Always returned for \"Reminders Purge\" request (Request ID=3)",
            "1 - the number of reminders for the STB exceeded the limitation Applies to \"Reminders Add\" request (Request ID=0)",
            "2 - reminder is set for time in the past. Applies to \"Reminders Add\" request (Request ID=0)",
            "3 - reminder is set for unknown channel. \"Reminders Add\" request (Request ID=0)",
            "4 - reminder is unknown. Applies to \"Reminders Delete\" request (Request ID=1) and \"Reminders Modify\" request (Request ID=2)",
            "5 - reminder with provided pair of identifiers (reminderScheduleId and reminderId) is already set \"Reminders Add\" request (Request ID=0)"};

    private final String ams_ip_4 = "172.30.81.4";
    private final String ams_ip_19 = "172.30.112.19";
    private final String ams_ip_132 = "172.30.82.132";
    protected String ams_ip = ams_ip_4;
    protected int ams_port = 8080;

    protected int getAverage(ArrayList list) {
        int sum = 0;
        if (list.size() > 0) {
            for (Object aList : list) {
                sum = sum + (int) aList;
            }
        }
        return sum / list.size();
    }

    protected void sortBubble(ArrayList list) throws IOException {
        long start = System.currentTimeMillis();
        for (int k = 0; k < list.size() - 1; k++) {
            for (int i = 0; i < list.size() - 1; i++) {
                if ((int) list.get(i) > (int) list.get(i + 1)) {
                    int temp = (int) list.get(i);
                    list.set(i, list.get(i + 1));
                    list.set(i + 1, temp);
                }
            }
            logger(DEBUG_LEVEL, "sorted list: " + list);
        }
        long finish = System.currentTimeMillis();
        logger(INFO_LEVEL, (int) (finish - start) + "ms for sortBubble");
    }

    protected void sortQuick(ArrayList list) throws IOException {
        long start = System.currentTimeMillis();
        sortQuickRecursive(list, 0, list.size()-1);
        long finish = System.currentTimeMillis();
        logger(DEBUG_LEVEL, "sorted list: " + list);
        logger(INFO_LEVEL, (int) (finish-start) + "ms for sortQuick");
    }

    private void sortQuickRecursive(ArrayList list, int lowerIndex, int higherIndex) throws IOException {
        int i = lowerIndex;
        int j = higherIndex;
        //calculate middle of the list
        int middle = (int) list.get(lowerIndex+(higherIndex-lowerIndex)/2);
        // Divide into two arrays
        while (i <= j) {
            //In each iteration, we will identify a number from left side which
            //is greater then the pivot value, and also we will identify a number
            //from right side which is less then the pivot value. Once the search
            //is done, then we exchange both numbers.
            while ((int)list.get(i) < middle)
                i++;
            while ((int)list.get(j) > middle)
                j--;
            if (i <= j) {
                //exchange numbers: i <=> j
                int temp = (int) list.get(i);
                list.set(i, list.get(j));
                list.set(j, temp);
                //move index to next position on both sides
                i++;
                j--;
            }
        }
        //call quicksort() method recursively
        if (lowerIndex < j) {
            sortQuickRecursive(list, lowerIndex, j);
        }
        if (i < higherIndex) {
            sortQuickRecursive(list, i, higherIndex);
        }
        logger(DEBUG_LEVEL, "sorted list: " + list);
    }

    public void sortSelection(ArrayList list) throws IOException {
        long start = System.currentTimeMillis();
        for (int i = 0; i < list.size()-1; i++) {
            int index = i;
            for (int j = i + 1; j < list.size(); j++) {
                if ((int) list.get(j) < (int) list.get(index))
                    index = j;
                logger(INFO_LEVEL, "sorted list: " + list);
            }

            int smallerNumber = (int) list.get(index);
            list.set(index, list.get(i));
            list.set(i, smallerNumber);

            logger(DEBUG_LEVEL, "sorted list: " + list);
        }

        long finish = System.currentTimeMillis();
        logger(INFO_LEVEL, (int) (finish - start) + "ms for sortSelection");
    }

    protected void sortInsertion(ArrayList list) throws IOException {
        long start = System.currentTimeMillis();
        int temp;
        for (int i=1; i<list.size(); i++) {
            for(int j=i; j>0; j--){
                if((int) list.get(j) < (int) list.get(j-1)){
                    temp = (int) list.get(j);
                    list.set(j, list.get(j-1));
                    list.set(j-1, temp);
                }
            }
            logger(DEBUG_LEVEL, "sorted list: " + list);
        }
        long finish = System.currentTimeMillis();
        logger(INFO_LEVEL, (int) (finish-start) + "ms for sortInsertion");
    }

    protected void sortMerge(int list[]) throws IOException {
        int array[] = list;
        int length = list.length;
        int tempMergArr[] = new int[length];

        long start = System.currentTimeMillis();
        sortMerge_doMergeSort(0, length - 1, tempMergArr, list);
        long finish = System.currentTimeMillis();

        logger(DEBUG_LEVEL, "sortMerge: sorted list: " + list);
        logger(INFO_LEVEL, (int) (finish-start) + "ms for sortMerge");
    }

    private void sortMerge_doMergeSort(int lowerIndex, int higherIndex, int tempMergArr[], int list[]) throws IOException {
        if (lowerIndex < higherIndex) {
            int middle = lowerIndex + (higherIndex - lowerIndex) / 2;

            // Below step sorts the left side of the array
            sortMerge_doMergeSort(lowerIndex, middle, tempMergArr, list);
            //logger(INFO_LEVEL, "sortMerge_doMergeSort: sorted list: " + list);

            // Below step sorts the right side of the array
            sortMerge_doMergeSort(middle + 1, higherIndex, tempMergArr, list);
            //logger(INFO_LEVEL, "sortMerge_doMergeSort: sorted list: " + list);

            // Now merge both sides
            sortMerge_mergeParts(lowerIndex, middle, higherIndex, tempMergArr, list);
            //logger(INFO_LEVEL, "sortMerge_doMergeSort: sorted list: " + list);
        }
    }

    private void sortMerge_mergeParts(int lowerIndex, int middle, int higherIndex, int tempMergArr[], int list[]) {
        for (int i = lowerIndex; i <= higherIndex; i++) {
            tempMergArr[i] = list[i];
        }
        int i = lowerIndex;
        int j = middle + 1;
        int k = lowerIndex;
        while (i <= middle && j <= higherIndex) {
            if (tempMergArr[i] <= tempMergArr[j]) {
                list[k] = tempMergArr[i];
                i++;
            } else {
                list[k] = tempMergArr[j];
                j++;
            }
            k++;
        }
        while (i <= middle) {
            list[k] = tempMergArr[i];
            k++;
            i++;
        }
    }

    private int searchMax(ArrayList list) throws IOException {
        long start = System.currentTimeMillis();
        int max = 0;
        if (list.size() > 0) {
            for (Object aList : list) {
                if ((int) aList > max) {
                    max = (int) aList;
                }
            }
        }
        long finish = System.currentTimeMillis();
        logger(INFO_LEVEL, (int) (finish-start) + "ms for searchMax()");

        return max;
    }

    protected int searchMedian(ArrayList list, Enum<Sorting> sorting) throws IOException {
        int median;
        switch (sorting.name()) {
            case "bubble":
                sortBubble(list);
                break;
            case "quick":
                sortQuick(list);
                break;
            case "selection":
                sortSelection(list);
                break;
            case "insertion":
                sortInsertion(list);
                break;
            default:
                sortQuick(list);
                break;
            }

            if (list.size() % 2 == 0) {
                //if chetnoe - take avg of 2 middle elements
                median = ((int) list.get(list.size() / 2 - 1) + (int) list.get(list.size() / 2)) / 2;
            } else {
                //if nechetnoe - just take middle element
                median = (int) list.get(list.size() / 2);
            }
        return median;
    }

    private int searchMin(ArrayList list) throws IOException {
        long start = System.currentTimeMillis();
        int min = 0;
        if (list.size() > 0) {
            min = (int)list.get(0);
            for (Object aList : list) {
                if ((int) aList < min) {
                    min = (int) aList;
                }
            }
        }
        long finish = System.currentTimeMillis();
        logger(INFO_LEVEL, (int) (finish-start) + "ms for searchMin()");

        return min;
    }

    /**
     * @param operation
     * @param current
     * @param i
     * @return
     */
    protected int[] getMin(Enum<Operation> operation, int current, int i) {
        long start = System.currentTimeMillis();
        // SLOWly ???
        /*switch (operation.name()) {
            case "add":
                return searchMin(add_list);
            case "modify":
                return searchMin(modify_list);
            case "delete":
                return searchMin(delete_list);
            case "purge":
                return searchMin(purge_list);
            default:
                return 0;
        }*/


        // FAST??? to_confirm
        int min[] = new int[2];
        switch (operation.name()) {
            case "add":
                min = Arrays.copyOf(a_min_array, a_min_array.length);
                break;
            case "modify":
                min = Arrays.copyOf(m_min_array, m_min_array.length);
                break;
            case "delete":
                min = Arrays.copyOf(d_min_array, d_min_array.length);
                break;
            case "purge":
                min = Arrays.copyOf(p_min_array, p_min_array.length);
                break;
            case "common":
                min = Arrays.copyOf(min_array, min_array.length);
                break;
        }

        if(min[0] == 0){
            // save params of 1st request
            min[0] = current;
            min[1] = 1;
        } else {
            if (current < min[0]) {
                min[0] = current;
                min[1] = i;
            }
        }

        //todo TO REMOVE???
        switch (operation.name()) {
            case "add":
                a_min_array = Arrays.copyOf(min, min.length);
                break;
            case "modify":
                m_min_array = Arrays.copyOf(min, min.length);
                break;
            case "delete":
                d_min_array = Arrays.copyOf(min, min.length);
                break;
            case "purge":
                p_min_array = Arrays.copyOf(min, min.length);
                break;
            case "common":
                min_array = Arrays.copyOf(min, min.length);
                break;
        }
        long finish = System.currentTimeMillis();
        //logger(INFO_LEVEL, (int) (finish-start) + "ms for getMin()");

        return min;
    }

    protected int[] getMax(Enum<Operation> operation, int current, int i) {
        long start = System.currentTimeMillis();
        int max[] = new int[2];

        //SLOWly???
        /*switch (operation.name()) {
            case "add":
                max = searchMax(add_list);
                break;
            case "modify":
                max = searchMax(modify_list);
                break;
            case "delete":
                max = searchMax(delete_list);
                break;
            case "purge":
                max = searchMax(purge_list);
                break;
        }*/

        //FAST??? to_confirm!
        switch (operation.name()) {
            case "add":
                max = Arrays.copyOf(a_max_array, a_max_array.length);
                break;
            case "modify":
                max = Arrays.copyOf(m_max_array, m_max_array.length);
                break;
            case "delete":
                max = Arrays.copyOf(d_max_array, d_max_array.length);
                break;
            case "purge":
                max = Arrays.copyOf(p_max_array, p_max_array.length);
                break;
            case "common":
                max = Arrays.copyOf(max_array, max_array.length);
                break;
        }

        if(max[0] == 0){
            max[0] = current;
            max[1] = 1;
        } else {
            if (current > max[0]) {
                max[0] = current;
                max[1] = i;
            }
        }

        //todo TO REMOVE???
        switch (operation.name()) {
            case "add":
                a_max_array = Arrays.copyOf(max, max.length);
                break;
            case "modify":
                m_max_array = Arrays.copyOf(max, max.length);
                break;
            case "delete":
                d_max_array = Arrays.copyOf(max, max.length);
                break;
            case "purge":
                p_max_array = Arrays.copyOf(max, max.length);
                break;
            case "common":
                max_array = Arrays.copyOf(max, max.length);
                break;
        }
        long finish = System.currentTimeMillis();
        //logger(INFO_LEVEL, (int) (finish-start) + "ms for getMax()");

        return max;
    }

    @Deprecated
    String generate_json_test(String date, int count_reminders, String operation, int reminderOffset) {
        System.out.println("[DBG] [date] Generate_json: with date=" + date + ", " +
                "count_reminders=" + count_reminders + ", " +
                "operation=" + operation + ", " +
                "reminderOffset=" + reminderOffset);
         /*
        //WORKING parsing from json_string to Class:
        Gson g = new Gson();
        Reminder reminder = g.fromJson(json_add2, Reminder.class);
        System.out.println("[DBG] parsing from json_string to Class: \nmac: " + reminder.deviceId);
        System.out.println("[DBG] count of reminders in class: " + reminder.reminders.size());
        for(Reminders rems : reminder.reminders){System.out.println(
                    "operation: " + rems.operation + ", " +
                    "reminderChannelNumber: " + rems.reminderChannelNumber + ", " +
                    "reminderProgramStart: " + rems.reminderProgramStart + ", " +
                    "reminderProgramId: " + rems.reminderProgramId + ", " +
                    "reminderOffset: " + rems.reminderOffset);
            }

        //parsing from Class to json_string
        System.out.println("[DBG] parsing from Class to json_string: \n" + g.toJson(reminder));
*/
/*        //WORKING variant for one class Reminder + one class Reminders
        //==============================================================
        final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

        //from class -> to string json
        //class with fields:
        Reminders rs = new Reminders(operation, reminderChannelNumber, reminderProgramStart, reminderProgramId, reminderOffset);
        Reminder r = new Reminder(mac, rs);
        //create json structure:
        String json = GSON.toJson(r);
        System.out.println("[DBG] from class -> to string json:\n" + json);

        //from json string -> to class
        Reminder to_class = GSON.fromJson(json, Reminder.class);
        System.out.println("[DBG] from json string -> to class:\n" + to_class.getDeviceId()+ " " + to_class.getClass());
*/
/*
        //from class -> to string json
        Reminder from_class = new Reminder(mac, Arrays.asList("operation", "reminderChannelNumber", "reminderProgramStart", "reminderProgramId", "reminderOffset"));
        String json = GSON.toJson(from_class);
        System.out.println("[DBG] from class -> to string json:\n" + json);

        //from json string -> to class
        Reminder to_class = GSON.fromJson(json, Reminder.class);
        System.out.println("[DBG] from json string -> to class:\n" + to_class.getDeviceId()+ " " + to_class.getReminders_list());
*/
/*
        //WORKING
        JsonObject jo = new JsonParser().parse(json_add2).getAsJsonObject();
        System.out.println("1 show full jsonobject: " + jo);
        show only jsonarray:
        String ja = jo.get("reminders").getAsJsonArray().toString();
        System.out.println("2 only jsonarray: " + ja);
*/
    return "";
    }

    protected String checkResponseBody(String body) throws IOException {
        String result = "";
        if(body.contains("\"statusCode\":1")){
            //log.warning("one or more statusCode's = " + statuscode[1]);
            logger(INFO_LEVEL, "! one or more statusCode's = " + statuscode[1]);
            result += "1";
        }
        if(body.contains("\"statusCode\":2")){
            //log.warning("one or more statusCode's = " + statuscode[2]);
            logger(INFO_LEVEL, "! one or more statusCode's = " + statuscode[2]);
            result += "2";
        }
        if(body.contains("\"statusCode\":3")){
            //log.warning("one or more statusCode's = " + statuscode[3]);
            logger(INFO_LEVEL,"! one or more statusCode's = " + statuscode[3]);
            result += "3";
        }
        if(body.contains("\"statusCode\":4")){
            //log.warning("one or more statusCode's = " + statuscode[4]);
            logger(INFO_LEVEL,"! one or more statusCode's = " + statuscode[4]);
            result += "4";
        }
        if(body.contains("\"statusCode\":5")){
            //log.warning("one or more statusCode's = " + statuscode[5]);
            logger(INFO_LEVEL,"! one or more statusCode's = " + statuscode[5]);
            result += "5";
        }
        if(body.contains("\"status\":\"Failed\"") && body.contains("\"errorMessage\":\"Request not accomplished\"")){
            result += "Request not accomplished";
        }
        if(body.contains("\"status\":\"Failed\"") && body.contains("\"errorMessage\":\"REM-ST-001 Box is not registered\"")){
            result += "REM-ST-001 Box is not registered";
        }
        if(body.contains("\"status\":\"Failed\"") && body.contains("\"errorMessage\":\"unknown MAC\"")){
            result += "unknown MAC";
        }
        if(body.contains("\"status\":\"Failed\"") && body.contains("\"errorMessage\":\"STB not available\"")){
            result += "STB not available";
        }
        if(body.contains("\"status\":\"Failed\"") && body.contains("\"errorMessage\":\"REM-002 Reminders Service error: REM-112\"")){
            result += "REM-002 Reminders Service error: REM-012 [mac] Request not accomplished";
        }
        if(body.contains("\"status\":\"Failed\"") && body.contains("\"errorMessage\":\"Timeout detected by BoxResponseTracker")){
            result += "Timeout detected by BoxResponseTracker";
        }
        if(body.contains("\"status\":\"Failed\"") && body.contains("\"errorMessage\":\"REM-008 Reminders parsing error: missing program start\"")){
            result += "REM-008 Reminders parsing error: missing program start";
        }
        if(body.contains("\"status\":\"Failed\"") && body.contains("\"errorMessage\":\"REM-008 Reminders parsing error: invalid program start\"")){
            result += "REM-008 Reminders parsing error: invalid program start";
        }
        //if(body.contains("\"status\":\"Failed\"") && body.contains("\"errorMessage\":\"REM-008 Reminders parsing error: missing channel number\"")){
            //result += "REM-008 Reminders parsing error: missing channel number";
        //}
        if(body.contains("\"status\":\"Failed\"") && body.contains("\"errorMessage\":\"REM-008 Reminders parsing error: invalid channel number\"")){
            result += "REM-008 Reminders parsing error: invalid channel number";
        }
        if(body.contains("\"status\":\"Failed\"") && body.contains("\"errorMessage\":\"REM-008 Reminders parsing error: missing offset\"")){
            result += "REM-008 Reminders parsing error: missing offset";
        }
        if(body.contains("\"status\":\"Failed\"") && body.contains("\"errorMessage\":\"REM-008 Reminders parsing error: invalid offset\"")){
            result += "REM-008 Reminders parsing error: invalid offset";
        }
        if(body.contains("\"status\":\"Failed\"") && body.contains("\"errorMessage\":\"REM-008 Reminders parsing error: incorrect reminderScheduleId\"")){
            result += "REM-008 Reminders parsing error: incorrect reminderScheduleId";
        }
        if(body.contains("\"status\":\"Failed\"") && body.contains("\"errorMessage\":\"REM-008 Reminders parsing error: wrong number of reminders\"")){
            result += "REM-008 Reminders parsing error: wrong number of reminders";
        }
        if(body.contains("\"status\":\"Failed\"") && body.contains("\"errorMessage\":\"Incorrect request: ChangeReminders\"")){
            result += "Incorrect request: ChangeReminders";
        }
        if(body.contains("\"status\":\"Failed\"") && body.contains("\"errorMessage\":\"Incorrect request: blablabla\"")){
            result += "Incorrect request: blablabla";
        }
        if(body.contains("\"status\":\"Failed\"") && body.contains("\"errorMessage\":\"name cannot be null\"")){
            result += "name cannot be null";
        }
        if(body.contains("REM-002 Reminders Service error: Can not connect to STB with stbId=[mac]")){
            result += "REM-002 Reminders Service error: Can not connect to STB with stbId=[mac]";
        }
        if(body.contains("REM-008 Reminders parsing error: wrong deviceId")){
            result += "REM-008 Reminders parsing error: wrong deviceId";
        }
        if(body.contains("REM-008 Reminders parsing error: wrong operation")){
            result += "REM-008 Reminders parsing error: wrong operation";
        }
        if(body.contains("REM-008 Reminders parsing error: incorrect message format")){
            result += "REM-008 Reminders parsing error: incorrect message format";
        }
        if(body.contains("REM-008 Reminders parsing error: incorrect reminderId")){
            result += "REM-008 Reminders parsing error: incorrect reminderId";
        }
        if(body.contains("Failed to getAmsIpBymac for : [mac], with error: No amsIp found for mac: STB[mac]")){
            result += "No amsIp found for mac";
        }
        if(body.contains("STB MAC not found: [mac]")){
            result += "STB MAC not found: [mac]";
        }
        if(body.contains("incorrect value")){
            result += "incorrect value";
        }
        if(body.contains("SET-025 Unsupported data type: Not a JSON Object:")){
            result += "SET-025 Unsupported data type: Not a JSON Object";
        }
        if(body.contains("responseCode\":\"ERROR_SCHEDULING_REMINDER")){
            result += "ERROR_SCHEDULING_REMINDER";
        }
        //if(Objects.equals(result, "")){
            //result = " ";
        //}
        //System.out.println("[DBG] check_body_for_statuscode: result: " + result);
        return result;
    }

    protected String checkResponseBody(String body, ArrayList expected_list){
        StringBuilder result = new StringBuilder();

        for(int j = 1; j<expected_list.size(); j++){
            if(body.contains((CharSequence) expected_list.get(j))){
                result.append(expected_list.get(j));
            }
        }

        return result.toString();
    }

    protected final String readResponse(StringBuilder body, HttpResponse response) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8));
        //StringBuilder body = new StringBuilder();
        for (String line; (line = reader.readLine()) != null; ) {
            body.append(line);
            //if (reader.readLine() == null) {
                //logger(DEBUG_LEVEL, "\n");
            //}
        }
        if(show_response_body) {
            logger(DEBUG_LEVEL, "[DBG] response body: " + body);
        }
        return body.toString();
    }

    protected HttpGet prepareGetRequest(String uri) throws IOException {
        HttpGet request = new HttpGet(uri);
        request.setHeader("Content-type", "application/json");
        request.setHeader("Cache-Control", "no-cache");
        logger(DEBUG_LEVEL, "[DBG] request string: " + request);
        return request;
    }

    HttpPost preparePostRequest(String uri) throws IOException {
        HttpPost request = new HttpPost(uri);
        logger(DEBUG_LEVEL, "[DBG] request string: " + request);
        return request;
    }

    /*@Deprecated
    ArrayList Purge(String ams_ip, String mac) throws IOException {
        System.out.println("Purge for ams_ip=" + ams_ip + " and mac=" + mac);
        //log.info("Purge for ams_ip=" + ams_ip + " and mac=" + mac);

        String url = "http://" + ams_ip + ":" + ams_port + postfix_change;
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost request = new HttpPost(url);

        String json_purge = "{\"deviceId\":" + mac + ",\"reminders\":[{\"operation\":\"Purge\"}]}";
        request.setEntity(new StringEntity(json_purge));
        //request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");

        System.out.println("[DBG] Request string: " + request);
        //+ "\n[DBG] Request entity: " + request.getEntity());

        long start = currentTimeMillis();
        HttpResponse response = client.execute(request);
        long finish = currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms request, " +
                "Response getStatusLine: " + response.getStatusLine());

        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
        StringBuilder body = new StringBuilder();
        for (String line = null; (line = reader.readLine()) != null; ) {
            body.append(line);
            //System.out.println("[DBG] Response body: " + body.append(line).append("\n"));
        }

   /*     // Считываем json
        Object obj = new JSONParser().parse(json_purge); // Object obj = new JSONParser().parse(new FileReader("JSONExample.json"));
        // Кастим obj в JSONObject
        JSONObject jo = (JSONObject) obj;
        // Достаём firstName and lastName
        String firstName = (String) jo.get("firstName");
        String lastName = (String) jo.get("lastName");
        System.out.println("fio: " + firstName + " " + lastName);
        // Достаем массив номеров
        JSONArray phoneNumbersArr = (JSONArray) jo.get("phoneNumbers");
        Iterator phonesItr = phoneNumbersArr.iterator();
        System.out.println("phoneNumbers:");
        // Выводим в цикле данные массива
        while (phonesItr.hasNext()) {
            JSONObject test = (JSONObject) phonesItr.next();
            System.out.println("- type: " + test.get("type") + ", phone: " + test.get("number"));
        }*/

   /*     ArrayList arrayList = new ArrayList();
        arrayList.add(0, response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
        arrayList.add(1, check_body_for_statuscode(body.toString()));
        client.close();
        return arrayList;
    }*/

    public ArrayList QueryDB(String ams_ip, String mac) throws ClassNotFoundException, SQLException {
        //ResultSet QueryDB(String mac) throws ClassNotFoundException, SQLException {
        System.out.println("QueryDB for mac=" + mac + " to DB on AMS=" + ams_ip);

        String url = "jdbc:oracle:thin:@//ams-db01.enwd.co.sa.charterlab.com:1521/zdev02";
        String username = "ams_ipv6_e591";
        String password = "ams_ipv6_e591";

        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();

        long start = currentTimeMillis();
        ResultSet result = statement.executeQuery("select * from MAC_IP where MAC_STR = '" + mac + "\'");
        long finish = currentTimeMillis();
        System.out.println("[DBG] " + (finish - start) + "ms query");

        ArrayList actual = new ArrayList();
        while (result.next()) {
            actual.add(result.getLong(1));
            actual.add(result.getLong(2));
            actual.add(result.getInt(3));
            actual.add(result.getString(4));
            actual.add(result.getString(5));
        }
        if(!actual.isEmpty()) {
            System.out.println("[DBG] return result: "
                    + actual.get(0) + "  "
                    + actual.get(1) + "  "
                    + actual.get(2) + "  "
                    + actual.get(3) + "  "
                    + actual.get(4));
        }
        connection.close();
        return actual;
    }

    /**
     * @return just return the day=tomorrow: yyyy-mm-dd
     */
    protected String reminderProgramStart() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat pattern = new SimpleDateFormat("yyyy-MM-dd");
        calendar.add(Calendar.DAY_OF_YEAR, +1);
        return pattern.format(calendar.getTime());
    }

    protected int reminderChannelNumber(int limit) {
        return Math.abs(new Random().nextInt(limit));
    }

    protected int reminderOffset(int limit) {
        return Math.abs(new Random().nextInt(limit));
    }

    void printArrayList(ArrayList list){
        if(list != null)
        {
            for(Object a : list)
                if(a != null) System.out.println("element:" + a);
        }
    }

    static Boolean checkContainsAllNulls(ArrayList list)
    {
        if(list != null)
        {
            for(Object a : list)
                if(a != null) return false;
        }
        return true;
    }

    protected long reminderScheduleId(Enum<Generation> generation) throws IOException {
        if(generation.name().equals("random")) {
            reminderScheduleId = Math.abs(new Random().nextLong());
            //long reminderScheduleId = Math.abs(random.nextInt(1000));
        } else if(generation.name().equals("increment")){
            reminderScheduleId = 1;
            reminderScheduleId = (int)reminderScheduleId_list.get(reminderScheduleId_list.size()) + 1;
        }

        reminderScheduleId_list.add(reminderScheduleId);
        //logger(DEBUG_LEVEL, "reminderScheduleId_list<-add = " + reminderScheduleId);
        return reminderScheduleId;
    }

    protected long reminderId(Enum<Generation> generation) throws IOException {
        if(generation.name().equals("random")) {
            reminderId = Math.abs(new Random().nextLong());
            //long reminderId = Math.abs(random.nextInt(1000));
        } else if (generation.name().equals("increment")) {
            reminderId = (int)reminderId_list.get(reminderId_list.size()) + 1;
        }

        reminderId_list.add(reminderId);
        //logger(DEBUG_LEVEL, "reminderId_list<-add         = " + reminderId);
        return reminderId;
    }

    protected String getDateSeveral(int count) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat pattern = new SimpleDateFormat("yyyy-MM-dd");
        StringBuilder result = new StringBuilder();
        for (int i = 1; i <= count; i++) {
            calendar.add(Calendar.DAY_OF_YEAR, +1);
            result.append(pattern.format(calendar.getTime()));
            if (i != count) {
                result.append(" ");
            }
        }
        System.out.println("generated date: " + result);
        return result.toString();
    }

    @Deprecated
    String get_time_old(int count) {
        int interval_in_minutes;
        if (count<=48){ interval_in_minutes = 30; }
        else if (count<=288){ interval_in_minutes = 5; }
        else if (count<=720){ interval_in_minutes = 2; }
        else interval_in_minutes = 1;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat pattern = new SimpleDateFormat("HH:mm");
        calendar.setTime(new java.util.Date(0, 0, 0, 0, 0));

        StringBuilder result = new StringBuilder();
        for (int i=1; i<=count; i++){
            result.append(pattern.format(calendar.getTime()));
            calendar.add(Calendar.MINUTE, interval_in_minutes);
            if(i!=count){
                result.append(" ");
            }
        }
        System.out.println("generated times: " + result);
        return result.toString();
    }

    String getTime(int count, int number) {
        int interval_in_minutes;
        if (count<=48){ interval_in_minutes = 30; }
        else if (count<=288){ interval_in_minutes = 5; }
        else if (count<=720){ interval_in_minutes = 2; }
        else interval_in_minutes = 1;

        if (number < 1) { number = 1; }

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat pattern = new SimpleDateFormat("HH:mm");
        calendar.setTime(new Date(0, 0, 0, 0, 0));
        calendar.add(Calendar.MINUTE, interval_in_minutes*(number-1));
        String result = pattern.format(calendar.getTime());
        System.out.println("generated time: " + result);
        return result;
    }

    String getTime(int i) {
        //Calendar cal = Calendar.getInstance();
        //cal.add(Calendar.DATE, -1);
        //System.out.println("Yesterday's date = "+ cal.getTime());

        //int interval_in_minutes;
        //if (count<=48){ interval_in_minutes = 30; }
        //else if (count<=288){ interval_in_minutes = 5; }
        //else if (count<=720){ interval_in_minutes = 2; }
        //else interval_in_minutes = 1;

        //if (number < 1) { number = 1; }

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat pattern = new SimpleDateFormat("HH:mm");
        calendar.setTime(new Date(0, 0, 0, 0, 0));
        StringBuilder result = new StringBuilder();
        calendar.add(Calendar.MINUTE, i);
        result = result.append(pattern.format(calendar.getTime()));
        System.out.println("generated time: " + result);
        return result.toString();
    }

    protected String getDate(int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat pattern = new SimpleDateFormat("yyyy-MM-dd");
        StringBuilder result = new StringBuilder();

        calendar.add(Calendar.DAY_OF_YEAR, +i);
        result = result.append(pattern.format(calendar.getTime()));

        System.out.println("generated date: " + result);
        return result.toString();
    }

    /**
     * @param i - сколько минут прибавить к дате "завтра" (+сутки от текущего времени),
     *          например, сейчас "2018-05-18 21:01", тогда вызов getDateTime(10) вернет "2018-05-19 21:11"
     * @return - возвращаемый формат - "yyyy-mm-dd hh:mm"
     */
    protected String getDateTime(int i){
        //int count_rems_in_day = 1440;
        //int count_full_days = count_reminders / count_rems_in_day;
        //int ostatok = count_reminders - (count_full_days * count_rems_in_day);
        //System.out.println("count_full_days=" + count_full_days + ", ostatok=" + ostatok);

        //String result = getDate((i/count_rems_in_day)+1) + " " + getTime(i);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat pattern = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        StringBuilder result = new StringBuilder();
        calendar.add(Calendar.DAY_OF_YEAR, +1);
        calendar.add(Calendar.MINUTE,+i);
        result = result.append(pattern.format(calendar.getTime()));

        //System.out.println("generated date_time: " + result);
        return result.toString();
    }

    protected String prepareUrl(String server, Operation operation, boolean newapi) {
        String result = null;
        if(operation.name().equals("www")) {
            result = server;
        } else if (operation.name().equals("www")) {
            if (newapi) {
                result = server + ":" + ams_port + "/ams/Reminders2?req=" + operation;
            } else {
                result = server + ":" + ams_port + "/ams/Reminders?req=ChangeReminders";
            }
        }
        return result;
    }

    protected void printTotalMeasurements(String mac, String boxname, int count_reminders, int count_iterations,
                                          int a_avg, int a_med, int a_min, int a_min_iteration, int a_max, int a_max_iteration, int a_iteration, ArrayList a_current,
                                          int m_avg, int m_med, int m_min, int m_min_iteration, int m_max, int m_max_iteration, int m_iteration, ArrayList m_current,
                                          int d_avg, int d_med, int d_min, int d_min_iteration, int d_max, int d_max_iteration, int d_iteration, ArrayList d_current,
                                          int p_avg, int p_med, int p_min, int p_min_iteration, int p_max, int p_max_iteration, int p_iteration, ArrayList p_current
    ) throws IOException {

        String header = "========= ========= Total measurements ========= ========="
                + "\n" + starttime + " - test was started"
                + "\n" + new Date() + " - test is done, mac=" + mac + "(" + boxname + "), count_reminders=" + count_reminders + ", count_iterations=" + a_iteration + "/" + count_iterations;
        String a = "\n   add avg=" + a_avg + "ms, med=" + a_med + "ms, min=" + a_min + "ms/" + a_min_iteration + ", max=" + a_max + "ms/" + a_max_iteration + ", i=" + a_iteration;
        String m = "\nmodify avg=" + m_avg + "ms, med=" + m_med + "ms, min=" + m_min + "ms/" + m_min_iteration + ", max=" + m_max + "ms/" + m_max_iteration + ", i=" + m_iteration;
        String d = "\ndelete avg=" + d_avg + "ms, med=" + d_med + "ms, min=" + d_min + "ms/" + d_min_iteration + ", max=" + d_max + "ms/" + d_max_iteration + ", i=" + d_iteration;
        String p = "\n purge avg=" + p_avg + "ms, med=" + p_med + "ms, min=" + p_min + "ms/" + p_min_iteration + ", max=" + p_max + "ms/" + p_max_iteration + ", i=" + p_iteration;
        String footer = "\n========= ========= ========= ========= ========= =========";

        String result = "";
        //if (a_avg != 0) {
            result += header;
            //result += a;
            if (a_avg != 0) {            result += a;        }
            if (m_avg != 0) {            result += m;        }
            if (d_avg != 0) {            result += d;        }
            if (p_avg != 0) {            result += p;        }

            if (a_current != null) {
                //result += a_current;
                writeFile("a.log", a_current.toString(), false);
            }
            if (m_current != null) {
                //result += m_current;
                writeFile("m.log", m_current.toString(), false);
            }
            if (d_current != null){
                //result += d_current;
                writeFile("d.log", d_current.toString(), false);
            }
            if (p_current != null) {
                //result += p_current;
                writeFile("p.log", p_current.toString(), false);
            }

            result += footer;
            logger(INFO_LEVEL, result);
        //}
    }

    protected void printTotalMeasurements(String server, int count_iterations,
                                          int avg, int med, int min, int min_iteration, int max, int max_iteration, int iteration, ArrayList current) throws IOException {

        String header = "========= ========= ========= Total measurements ========= ========= ========="
                + "\n" + starttime + " - test was started"
                + "\n" + new Date() + " - test is done for server=" + server + ", count_iterations=" + iteration + "/" + count_iterations;
        String a = "\nrequest avg=" + avg + "ms, med=" + med + "ms, min=" + min + "ms/" + min_iteration + ", max=" + max + "ms/" + max_iteration + ", i=" + iteration;
        String footer = "\n========= ========= ========= ========= ========= ========= ========= =========";

        String result = "";
        //if (a_avg != 0) {
        result += header;
        //result += a;
        if (avg != 0) {            result += a;        }

        if (current != null) {
            //result += a_current;
            writeFile("a.log", current.toString(), false);
        }

        result += footer;
        logger(INFO_LEVEL, result);
        //}
    }

    protected void printPreliminaryMeasurements(ArrayList list) throws IOException {
        if(list.get(0).equals(HttpStatus.SC_OK)){
            logger(INFO_LEVEL, "[INF] return data: [" + list.get(0) + ", " + list.get(1) + "]"
                + " measurements: cur=" + list.get(2)
                + ", avg=" + list.get(3)
                + ", med=" + list.get(4)
                + ", min=" + list.get(5) + "(/" + list.get(6) + ")"
                + ", max=" + list.get(7) + "(/" + list.get(8) + ")"
                + ", i=" + list.get(9));
        } else {
            logger(INFO_LEVEL, "[INF] return data: [" + list.get(0) + ", " + list.get(1) + "]");
        }

    }
    protected void logger(String level, String s) throws IOException {
        boolean append = true;
        if(level.equals("INF") && show_info_level) {
            System.out.println(s);
            writeFile(REMINDERSLOG, s + "\n", append);
        } else if(level.equals("DBG") && show_debug_level) {
            System.out.println(s);
            writeFile(REMINDERSLOG, s + "\n", append);
        }
    }

    private void writeFile(String filename, String s, boolean append) throws IOException {
        FileWriter writer = new FileWriter(filename, append);
        writer.write(s);
        writer.flush();
        writer.close();
    }

    void printStartHeader(String url) throws IOException {
        starttime = new Date();
        logger(INFO_LEVEL, "[INF] " + starttime + ": New start for url=" + url);
    }

    protected void printIterationHeader(String ams_ip, String mac, int count_reminders, int i, int count_iterations, int reminderChannelNumber) throws IOException {
        String header = "========= ========= Iteration = " + i
                + "/" + count_iterations
                + ", mac=" + mac
                + ", ams=" + ams_ip
                + ", count_reminders=" + count_reminders
                + ", reminderChannelNumber=" + reminderChannelNumber
                + " ========= =========";
        logger(INFO_LEVEL, header);
    }

    protected void printIterationHeader(String url, int i, int count_iterations) throws IOException {
        String header = "========= ========= Iteration = " + i
                + "/" + count_iterations
                + ", server=" + url
                + " ========= =========";
        logger(INFO_LEVEL, header);
    }

    private void check_csv(String ams_ip, String mac, String boxname, int sleep_after_iteration, int count_reminders, int count_iterations, int reminderChannelNumber) {
        assertNotNull(ams_ip);
        assertNotNull(mac);
        assertNotNull(boxname);
        //assertNotEquals(0, count_reminders);
        //assertNotEquals(0, count_iterations);
        //assertNotEquals(0, reminderChannelNumber);
    }

    protected void read_csv() throws IOException {
        //Build reader instance
        //Read data.csv
        //Default seperator is comma
        //Default quote character is double quote
        //Start reading from line number 2 (line numbers start from zero)
        CSVReader reader = new CSVReader(new FileReader("common.csv"), ',' , '"' , 1);
        //Read CSV line by line and use the string array as you want
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            if (nextLine != null) {
                //Verifying the read data here
                System.out.println(Arrays.toString(nextLine));
            }
        }
    }

    protected void before(String ams_ip, String mac, String boxname, int sleep_after_iteration, int count_reminders, int count_iterations, int reminderChannelNumber) throws IOException {
        check_csv(ams_ip, mac, boxname, sleep_after_iteration, count_reminders, count_iterations, reminderChannelNumber);

        printStartHeader(ams_ip, mac, boxname, count_reminders, reminderChannelNumber);

        if(reminderChannelNumber == -1){
            use_random = true;
        }
    }

    private void printStartHeader(String ams_ip, String mac, String boxname, int count_reminders, int reminderChannelNumber) throws IOException {
        starttime = new Date();
        logger(INFO_LEVEL, "[INF] " + starttime + ": New start for mac=" + mac + "(" + boxname + ") to ams=" + ams_ip + ", "
                + "count_reminders=" + count_reminders + ", "
                + "reminderProgramStart=" + reminderProgramStart + ", "
                + "reminderChannelNumber=" + reminderChannelNumber + ", "
                + "reminderProgramId=" + reminderProgramId + ", "
                + "reminderOffset=" + reminderOffset + ", "
                + "reminderScheduleId=, "
                + "reminderId=");
    }


    ArrayList post(String url, String json, ArrayList patterns, boolean parse_json) throws IOException {
        //logger(CLIENTLOG, INFO_LEVEL, "post request to: " + url);
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(url);
        //request.setHeader("User-Agent", USER_AGENT);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");

        /*List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("sn", "C02G8416DRJM"));
        urlParameters.add(new BasicNameValuePair("cn", ""));
        urlParameters.add(new BasicNameValuePair("locale", ""));
        urlParameters.add(new BasicNameValuePair("caller", ""));
        urlParameters.add(new BasicNameValuePair("num", "12345"));*/
        //request.setEntity(new UrlEncodedFormEntity(urlParameters));
        //if(show_generated_json) {
        //    logger(CLIENTLOG, INFO_LEVEL, "generated json: " + json);
        //}
        request.setEntity(new StringEntity(json));

        long start = System.currentTimeMillis();
        HttpResponse response = client.execute(request);
        long finish = System.currentTimeMillis();
        int current = (int)(finish-start);
        //logger(CLIENTLOG, INFO_LEVEL, "[DBG] " + current + "ms request");

        String responseBody = readResponse(response);
        //if(show_response_body){
        //    logger(CLIENTLOG, INFO_LEVEL, "responseBody: " + responseBody);
        //}
        //todo
        ArrayList list = new ArrayList();
        list.add(0, response.getStatusLine().getStatusCode());
        check_responsebody(responseBody, patterns, list);
        /*String responsebody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        if(show_response_json){
            logger(INFO_LEVEL,"response body: " + responsebody);
        }*/

        if(parse_json) {
            ArrayList arrayList_parsed = new ArrayList();
            try {
                JSONParser parser = new JSONParser();
                Object resultObject = parser.parse(responseBody);

                if (resultObject instanceof JSONArray) {
                    JSONArray array = (JSONArray) resultObject;
                    for (Object object : array) {
                        JSONObject obj = (JSONObject) object;
                        arrayList_parsed.add(obj.get("measurements"));
                        //parsed.add(obj.get("date"));
                        //logger(CLIENTLOG, INFO_LEVEL, "JSONArray JSONParser-ed data: " + arrayList_parsed);
                    }
                } else if (resultObject instanceof JSONObject) {
                    JSONObject obj = (JSONObject) resultObject;
                    arrayList_parsed.add(obj.get("measurements"));
                    //parsed.add(obj.get("date"));
                    //logger(CLIENTLOG, INFO_LEVEL, "JSONObject JSONParser-ed data: " + arrayList_parsed);
                }
            } catch (ParseException e) {
                //todo: handle exception
                System.out.println("catch exception: JSONParser: seems not json format");
                e.printStackTrace();
            }

            //logger(CLIENTLOG, INFO_LEVEL, "filtered data parsed: " + arrayList_parsed);
        }

        //logger(CLIENTLOG, INFO_LEVEL, "filtered data: " + arrayList + "\n");
        return list;
    }

    private String readResponse(HttpResponse response) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8));
        StringBuilder builder = new StringBuilder();
        for (String line; (line = reader.readLine()) != null;) {
            builder.append(line);
            //todo
            /*if (reader.readLine() == null) {
                logger(INFO_LEVEL, "\n");
            }*/
        }
        return builder.toString();
    }

    private void check_responsebody(String responseBody, ArrayList patterns, ArrayList arrayList) {
        for (int i=1; i<patterns.size(); i++){
            if(responseBody.contains(patterns.get(i).toString())) {
                int a = responseBody.indexOf(patterns.get(i).toString());
                int l = patterns.get(i).toString().length();
                arrayList.add(i,responseBody.substring(a, a+l));
            } else {
                arrayList.add(i,"<>");
            }
        }
    }

    protected String generate_json(int count_pairs) {
        JSONObject json = new JSONObject();
        JSONArray array_measurements = new JSONArray();
        json.put("measurements", array_measurements);
        for (int i = 0; i < count_pairs; i++) {
            JSONObject object_in_measurements = new JSONObject();

            object_in_measurements.put("date", (int) (System.currentTimeMillis() / 1000L));
            object_in_measurements.put("temperature", generate_t_value());
            object_in_measurements.put("unit", "C");
            //object_in_measurements.put("unit", "K");
            //object_in_measurements.put("unit", "F");
            array_measurements.add(object_in_measurements);
        }
        return json.toString();
    }

    private int generate_t_value() {
        return Math.abs(new Random().nextInt(1000));
    }

}