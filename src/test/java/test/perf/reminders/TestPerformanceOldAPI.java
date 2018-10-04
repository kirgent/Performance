package test.perf.reminders;

import java.io.IOException;

import org.apache.http.HttpStatus;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import test.perf.common.CommonAPI;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * We are localhost (Charter Headend). Full chain of requests: localhost -> AMS -> STB -> AMS -> localhost
 */
class TestPerformanceOldAPI extends CommonAPI {

    private OldAPI AMS = new OldAPI();

    @ParameterizedTest
    @CsvFileSource(resources = "/remindersOldAPI.csv", numLinesToSkip = 1)
    void test10_Add(String ams_ip, String mac, String boxname, int sleep_after_iteration, int count_reminders, int count_iterations, int reminderChannelNumber) throws IOException, InterruptedException {
        before(ams_ip, mac, boxname, sleep_after_iteration, count_reminders, count_iterations, reminderChannelNumber);

        for (int i = 1; i <= count_iterations; i++) {
            if(useRandom){
                reminderChannelNumber = reminderChannelNumber(1000);
            }
            printIterationHeader(ams_ip, mac, count_reminders, i, count_iterations, reminderChannelNumber);

            //reminderChannelNumber = reminderChannelNumber();
            //int finalReminderChannelNumber = reminderChannelNumber;
            addList = AMS.requestPerformance(ams_ip, mac, Operation.ADD, i, count_reminders, reminderChannelNumber, reminderProgramStart, reminderProgramId, reminderOffset);
            printPreliminaryMeasurements(addList);
            if (addList.get(0).equals(HttpStatus.SC_OK)) {
                aAvg = (int) addList.get(3);
                aMed = (int) addList.get(4);
                aMin = (int) addList.get(5);
                aMinIteration = (int) addList.get(6);
                aMax = (int) addList.get(7);
                aMaxIteration = (int) addList.get(8);
                aTotalI = (int) addList.get(9);
            }
            addList.clear();
            Thread.sleep(sleep_after_iteration);
        }

        printTotalMeasurements(mac, boxname, count_reminders, count_iterations,
                aAvg, aMed, aMin, aMinIteration, aMax, aMaxIteration, aTotalI, aCurrent,
                0, 0, 0, 0, 0, 0, 0, null,
                dAvg, dMed, dMin, dMinIteration, dMax, dMaxIteration, dTotalI, dCurrent,
                pAvg, pMed, pMin, pMinIteration, pMax, pMaxIteration, pTotalI, pCurrent);
        assertNotEquals(0, aAvg, "aAvg");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/remindersOldAPI.csv", numLinesToSkip = 1)
    void test11_Add_Purge(String ams_ip, String mac, String boxname, int sleep_after_iteration, int count_reminders, int count_iterations, int reminderChannelNumber) throws IOException, InterruptedException {
        before(ams_ip, mac, boxname, sleep_after_iteration, count_reminders, count_iterations, reminderChannelNumber);

        for (int i = 1; i <= count_iterations; i++) {
            if (useRandom){                reminderChannelNumber = reminderChannelNumber(1000);            }
            printIterationHeader(ams_ip, mac, count_reminders, i, count_iterations, reminderChannelNumber);

            addList = AMS.requestPerformance(ams_ip, mac, Operation.ADD, i, count_reminders, reminderChannelNumber, reminderProgramStart, reminderProgramId, reminderOffset);
            printPreliminaryMeasurements(addList);
            if (addList.get(0).equals(HttpStatus.SC_OK)) {
                aAvg = (int) addList.get(3);
                aMed = (int) addList.get(4);
                aMin = (int) addList.get(5);
                aMinIteration = (int) addList.get(8);
                aMax = (int) addList.get(6);
                aMaxIteration = (int) addList.get(9);
                aTotalI = (int) addList.get(7);

                purgeList = AMS.requestPerformance(ams_ip, mac, Operation.PURGE, i);
                printPreliminaryMeasurements(purgeList);
                if (purgeList.get(0).equals(HttpStatus.SC_OK)) {
                    pAvg = (int) purgeList.get(3);
                    pMed = (int) purgeList.get(4);
                    pMin = (int) purgeList.get(5);
                    pMinIteration = (int) purgeList.get(8);
                    pMax = (int) purgeList.get(6);
                    pMaxIteration = (int) purgeList.get(9);
                    pTotalI = (int) purgeList.get(7);
                }
            }
            addList.clear();
            purgeList.clear();
            Thread.sleep(sleep_after_iteration);
        }

        printTotalMeasurements(mac, boxname, count_reminders, count_iterations,
                aAvg, aMed, aMin, aMinIteration, aMax, aMaxIteration, aTotalI, aCurrent,
                0, 0, 0, 0, 0, 0, 0, null,
                dAvg, dMed, dMin, dMinIteration, dMax, dMaxIteration, dTotalI, dCurrent,
                pAvg, pMed, pMin, pMinIteration, pMax, pMaxIteration, pTotalI, pCurrent);
        assertNotEquals(0, aAvg, "aAvg");
        assertNotEquals(0, pAvg, "pAvg");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/remindersOldAPI.csv", numLinesToSkip = 1)
    void test12_Add_Delete_Purge(String ams_ip, String mac, String boxname, int sleep_after_iteration, int count_reminders, int count_iterations, int reminderChannelNumber) throws IOException, InterruptedException {
        before(ams_ip, mac, boxname, sleep_after_iteration, count_reminders, count_iterations, reminderChannelNumber);

        for (int i = 1; i <= count_iterations; i++) {
            if(useRandom){                reminderChannelNumber = reminderChannelNumber(1000);            }
            printIterationHeader(ams_ip, mac, count_reminders, i, count_iterations, reminderChannelNumber);

            addList = AMS.requestPerformance(ams_ip, mac, Operation.ADD, i, count_reminders, reminderChannelNumber, reminderProgramStart, reminderProgramId, reminderOffset);
            printPreliminaryMeasurements(addList);
            if (addList.get(0).equals(HttpStatus.SC_OK)) {
                aAvg = (int) addList.get(3);
                aMed = (int) addList.get(4);
                aMin = (int) addList.get(5);
                aMinIteration = (int) addList.get(6);
                aMax = (int) addList.get(7);
                aMaxIteration = (int) addList.get(8);
                aTotalI = (int) addList.get(9);

                deleteList = AMS.requestPerformance(ams_ip, mac, Operation.DELETE, i, count_reminders, reminderChannelNumber, reminderProgramStart, reminderProgramId, reminderOffset);
                printPreliminaryMeasurements(deleteList);
                if (deleteList.get(0).equals(HttpStatus.SC_OK)) {
                    dAvg = (int) deleteList.get(3);
                    dMed = (int) deleteList.get(4);
                    dMin = (int) deleteList.get(5);
                    dMinIteration = (int) deleteList.get(6);
                    dMax = (int) deleteList.get(7);
                    dMaxIteration = (int) deleteList.get(8);
                    dTotalI = (int) deleteList.get(9);
                }

                purgeList = AMS.requestPerformance(ams_ip, mac, Operation.PURGE, i);
                printPreliminaryMeasurements(purgeList);
                if (purgeList.get(0).equals(HttpStatus.SC_OK)) {
                    pAvg = (int) purgeList.get(3);
                    pMed = (int) purgeList.get(4);
                    pMin = (int) purgeList.get(5);
                    pMinIteration = (int) purgeList.get(6);
                    pMax = (int) purgeList.get(7);
                    pMaxIteration = (int) purgeList.get(8);
                    pTotalI = (int) purgeList.get(9);
                }
            }
            addList.clear();
            deleteList.clear();
            purgeList.clear();
            Thread.sleep(sleep_after_iteration);
        }

        printTotalMeasurements(mac, boxname, count_reminders, count_iterations,
                aAvg, aMed, aMin, aMinIteration, aMax, aMaxIteration, aTotalI, aCurrent,
                0, 0, 0, 0, 0, 0, 0, null,
                dAvg, dMed, dMin, dMinIteration, dMax, dMaxIteration, dTotalI, dCurrent,
                pAvg, pMed, pMin, pMinIteration, pMax, pMaxIteration, pTotalI, pCurrent);
        assertNotEquals(0, aAvg, "aAvg");
        assertNotEquals(0, dAvg, "dAvg");
        assertNotEquals(0, pAvg, "pAvg");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/remindersOldAPI.csv", numLinesToSkip = 1)
    void test19_Purge(String ams_ip, String mac, String boxname, int sleep_after_iteration, int count_reminders, int count_iterations, int reminderChannelNumber) throws IOException, InterruptedException {
        before(ams_ip, mac, boxname, sleep_after_iteration, count_reminders, count_iterations, reminderChannelNumber);

        for (int i = 1; i <= count_iterations; i++) {
            printIterationHeader(ams_ip, mac, count_reminders, i, count_iterations, reminderChannelNumber);

            purgeList = AMS.requestPerformance(ams_ip, mac, Operation.PURGE, i);
            printPreliminaryMeasurements(purgeList);

            if (purgeList.get(0).equals(HttpStatus.SC_OK)) {
                pAvg = (int) purgeList.get(3);
                pMed = (int) purgeList.get(4);
                pMin = (int) purgeList.get(5);
                pMinIteration = (int) purgeList.get(6);
                pMax = (int) purgeList.get(7);
                pMaxIteration = (int) purgeList.get(8);
                pTotalI = (int) purgeList.get(9);
            }

            purgeList.clear();
            Thread.sleep(sleep_after_iteration);
        }

        printTotalMeasurements(mac, boxname, count_reminders, count_iterations,
                aAvg, aMed, aMin, aMinIteration, aMax, aMaxIteration, aTotalI, aCurrent,
                0, 0, 0, 0, 0, 0, 0, null,
                dAvg, dMed, dMin, dMinIteration, dMax, dMaxIteration, dTotalI, dCurrent,
                pAvg, pMed, pMin, pMinIteration, pMax, pMaxIteration, pTotalI, pCurrent);
        assertNotEquals(0, pAvg, "pAvg");
    }

}
