package test.perf.reminders;

import java.io.IOException;

import org.apache.http.HttpStatus;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * We are localhost (Charter Headend). Full chain of requests: localhost -> AMS -> STB -> AMS -> localhost
 */
class TestPerformanceNewAPI extends NewAPI {
    private NewAPI AMS = new NewAPI();

    @ParameterizedTest
    @CsvFileSource(resources = "/remindersNewAPI.csv", numLinesToSkip = 1)
    void test0_Add(String ams_ip, String mac, String boxname, int sleep_after_iteration, int count_reminders, int count_iterations, int reminderChannelNumber) throws InterruptedException, IOException {
        before(ams_ip, mac, boxname, sleep_after_iteration, count_reminders, count_iterations, reminderChannelNumber);

        for (int i = 1; i <= count_iterations; i++) {
            if(useRandom){
                reminderChannelNumber = reminderChannelNumber(1000);
            }
            printIterationHeader(ams_ip, mac, count_reminders, i, count_iterations, reminderChannelNumber);

            long reminderScheduleId = reminderScheduleId(Generation.RANDOM);
            long reminderId = reminderId(Generation.RANDOM);

            addList = AMS.requestPerformance(ams_ip, mac, Operation.ADD, i, count_reminders, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
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
            reminderScheduleIdList.clear();
            reminderIdList.clear();
            logger(DEBUG_LEVEL, "[DBG] reminderX_list-s are CLEARED !!!");
            addList.clear();
            Thread.sleep(sleep_after_iteration);
        }

        printTotalMeasurements(mac, boxname, count_reminders, count_iterations,
                aAvg, aMed, aMin, aMinIteration, aMax, aMaxIteration, aTotalI, aCurrent,
                mAvg, mMed, mMin, mMinIteration, mMax, mMaxIteration, mTotalI, mCurrent,
                dAvg, dMed, dMin, dMinIteration, dMax, dMaxIteration, dTotalI, dCurrent,
                pAvg, pMed, pMin, pMinIteration, pMax, pMaxIteration, pTotalI, pCurrent);
        assertNotEquals(0, aAvg, "aAvg");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/remindersNewAPI.csv", numLinesToSkip = 1)
    void test1_Add_Purge(String ams_ip, String mac, String boxname, int sleep_after_iteration, int count_reminders, int count_iterations, int reminderChannelNumber) throws InterruptedException, IOException {
        before(ams_ip, mac, boxname, sleep_after_iteration, count_reminders, count_iterations, reminderChannelNumber);

        for (int i = 1; i <= count_iterations; i++) {
            if(useRandom){
                reminderChannelNumber = reminderChannelNumber(1000);
            }
            printIterationHeader(ams_ip, mac, count_reminders, i, count_iterations, reminderChannelNumber);

            long reminderScheduleId = reminderScheduleId(Generation.RANDOM);
            long reminderId = reminderId(Generation.RANDOM);

            addList = AMS.requestPerformance(ams_ip, mac, Operation.ADD, i, count_reminders, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
            printPreliminaryMeasurements(addList);
            if (addList.get(0).equals(HttpStatus.SC_OK)) {
                aAvg = (int) addList.get(3);
                aMed = (int) addList.get(4);
                aMin = (int) addList.get(5);
                aMinIteration = (int) addList.get(6);
                aMax = (int) addList.get(7);
                aMaxIteration = (int) addList.get(8);
                aTotalI = (int) addList.get(9);

                purgeList = AMS.requestPerformance(ams_ip, mac, Operation.PURGE, i);
                printPreliminaryMeasurements(purgeList);
                if(purgeList.get(0).equals(HttpStatus.SC_OK)) {
                    pAvg = (int) purgeList.get(3);
                    pMed = (int) purgeList.get(4);
                    pMin = (int) purgeList.get(5);
                    pMinIteration = (int) purgeList.get(6);
                    pMax = (int) purgeList.get(7);
                    pMaxIteration = (int) purgeList.get(8);
                    pTotalI = (int) purgeList.get(9);
                }
            }
            reminderScheduleIdList.clear();
            reminderIdList.clear();
            logger(DEBUG_LEVEL, "[DBG] reminderX_list-s are CLEARED !!!");
            addList.clear();
            purgeList.clear();
            Thread.sleep(sleep_after_iteration);
        }

        printTotalMeasurements(mac, boxname, count_reminders, count_iterations,
                aAvg, aMed, aMin, aMinIteration, aMax, aMaxIteration, aTotalI, aCurrent,
                mAvg, mMed, mMin, mMinIteration, mMax, mMaxIteration, mTotalI, mCurrent,
                dAvg, dMed, dMin, dMinIteration, dMax, dMaxIteration, dTotalI, dCurrent,
                pAvg, pMed, pMin, pMinIteration, pMax, pMaxIteration, pTotalI, pCurrent);
        assertNotEquals(0, aAvg, "aAvg");
        assertNotEquals(0, pAvg, "pAvg");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/remindersNewAPI.csv", numLinesToSkip = 1)
    void test2_Add_Delete_Purge(String ams_ip, String mac, String boxname, int sleep_after_iteration, int count_reminders, int count_iterations, int reminderChannelNumber) throws IOException, InterruptedException {
        before(ams_ip, mac, boxname, sleep_after_iteration, count_reminders, count_iterations, reminderChannelNumber);

        for (int i = 1; i <= count_iterations; i++) {
            if(useRandom){
                reminderChannelNumber = reminderChannelNumber(1000);
            }
            printIterationHeader(ams_ip, mac, count_reminders, i, count_iterations, reminderChannelNumber);

            long reminderScheduleId = reminderScheduleId(Generation.RANDOM);
            long reminderId = reminderId(Generation.RANDOM);

            addList = AMS.requestPerformance(ams_ip, mac, Operation.ADD, i, count_reminders, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
            printPreliminaryMeasurements(addList);
            if (addList.get(0).equals(HttpStatus.SC_OK)) {
                aAvg = (int) addList.get(3);
                aMed = (int) addList.get(4);
                aMin = (int) addList.get(5);
                aMinIteration = (int) addList.get(6);
                aMax = (int) addList.get(7);
                aMaxIteration = (int) addList.get(8);
                aTotalI = (int) addList.get(9);

                deleteList = AMS.requestPerformance(ams_ip, mac, Operation.DELETE, i, count_reminders, reminderScheduleId, reminderId);
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

            reminderScheduleIdList.clear();
            reminderIdList.clear();
            logger(DEBUG_LEVEL, "[DBG] reminderX_list-s are CLEARED !!!");
            addList.clear();
            deleteList.clear();
            purgeList.clear();
            Thread.sleep(sleep_after_iteration);
        }

        printTotalMeasurements(mac, boxname, count_reminders, count_iterations,
                aAvg, aMed, aMin, aMinIteration, aMax, aMaxIteration, aTotalI, aCurrent,
                mAvg, mMed, mMin, mMinIteration, mMax, mMaxIteration, mTotalI, mCurrent,
                dAvg, dMed, dMin, dMinIteration, dMax, dMaxIteration, dTotalI, dCurrent,
                pAvg, pMed, pMin, pMinIteration, pMax, pMaxIteration, pTotalI, pCurrent);
        assertNotEquals(0, aAvg, "aAvg");
        assertNotEquals(0, dAvg, "dAvg");
        assertNotEquals(0, pAvg, "pAvg");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/remindersNewAPI.csv", numLinesToSkip = 1)
    void test3_Add_Modify_Delete_Purge(String ams_ip, String mac, String boxname, int sleep_after_iteration, int count_reminders, int count_iterations, int reminderChannelNumber) throws IOException, InterruptedException {
        before(ams_ip, mac, boxname, sleep_after_iteration, count_reminders, count_iterations, reminderChannelNumber);

        for (int i = 1; i <= count_iterations; i++) {
            if(useRandom){
                reminderChannelNumber = reminderChannelNumber(1000);
            }
            printIterationHeader(ams_ip, mac, count_reminders, i, count_iterations, reminderChannelNumber);

            long reminderScheduleId = reminderScheduleId(Generation.RANDOM);
            long reminderId = reminderId(Generation.RANDOM);
            //long reminderScheduleId = reminderScheduleId(Generation.INCREMENT);
            //long reminderId = reminderId(Generation.INCREMENT);

            addList = AMS.requestPerformance(ams_ip, mac, Operation.ADD, i, count_reminders, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
            printPreliminaryMeasurements(addList);
            if (addList.get(0).equals(HttpStatus.SC_OK)) {
                aAvg = (int) addList.get(3);
                aMed = (int) addList.get(4);
                aMin = (int) addList.get(5);
                aMinIteration = (int) addList.get(6);
                aMax = (int) addList.get(7);
                aMaxIteration = (int) addList.get(8);
                aTotalI = (int) addList.get(9);

                modifyList = AMS.requestPerformance(ams_ip, mac, Operation.MODIFY, i, count_reminders, reminderProgramStart, reminderChannelNumber, reminderProgramId, reminderOffset, reminderScheduleId, reminderId);
                printPreliminaryMeasurements(modifyList);
                if (modifyList.get(0).equals(HttpStatus.SC_OK)) {
                    mAvg = (int) modifyList.get(3);
                    mMed = (int) modifyList.get(4);
                    mMin = (int) modifyList.get(5);
                    mMinIteration = (int) modifyList.get(6);
                    mMax = (int) modifyList.get(7);
                    mMaxIteration = (int) modifyList.get(8);
                    mTotalI = (int) modifyList.get(9);
                }

                deleteList = AMS.requestPerformance(ams_ip, mac, Operation.DELETE, i, count_reminders, reminderScheduleId, reminderId);
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
                    //pCurrent.ADD(purgeList.get(2));
                    pAvg = (int) purgeList.get(3);
                    pMed = (int) purgeList.get(4);
                    pMin = (int) purgeList.get(5);
                    pMinIteration = (int) purgeList.get(6);
                    pMax = (int) purgeList.get(7);
                    pMaxIteration = (int) purgeList.get(8);
                    pTotalI = (int) purgeList.get(9);
                }
            }
            reminderScheduleIdList.clear();
            reminderIdList.clear();
            logger(DEBUG_LEVEL, "[DBG] reminderX_list-s are CLEARED !!!");
            addList.clear();
            modifyList.clear();
            deleteList.clear();
            purgeList.clear();
            Thread.sleep(sleep_after_iteration);
        }

        printTotalMeasurements(mac, boxname, count_reminders, count_iterations,
                aAvg, aMed, aMin, aMinIteration, aMax, aMaxIteration, aTotalI, aCurrent,
                mAvg, mMed, mMin, mMinIteration, mMax, mMaxIteration, mTotalI, mCurrent,
                dAvg, dMed, dMin, dMinIteration, dMax, dMaxIteration, dTotalI, dCurrent,
                pAvg, pMed, pMin, pMinIteration, pMax, pMaxIteration, pTotalI, pCurrent);
        assertNotEquals(0, aAvg, "aAvg");
        assertNotEquals(0, mAvg, "mAvg");
        assertNotEquals(0, dAvg, "dAvg");
        assertNotEquals(0, pAvg, "pAvg");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/remindersNewAPI.csv", numLinesToSkip = 1)
    void test9_Purge(String ams_ip, String mac, String boxname, int sleep_after_iteration, int count_reminders, int count_iterations, int reminderChannelNumber) throws IOException, InterruptedException {
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
                mAvg, mMed, mMin, mMinIteration, mMax, mMaxIteration, mTotalI, mCurrent,
                dAvg, dMed, dMin, dMinIteration, dMax, dMaxIteration, dTotalI, dCurrent,
                pAvg, pMed, pMin, pMinIteration, pMax, pMaxIteration, pTotalI, pCurrent);
        assertNotEquals(0, pAvg, "pAvg");
    }

}
