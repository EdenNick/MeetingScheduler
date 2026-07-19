/**
 * Project Name: Project Meeting Scheduler 
 * Created By: NIcholas Edenfield
 * 
 * 
 * MeetingScheduler.java
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MeetingScheduler {

    /**
     * Input Format:
     * Name String
     * ID   int
     * days String[6] (sun - mon - tue - wed - thu - fri - sat)
     * time TimeSet (int BeginHOUR, int BeginMIN, int EndHOUR, int EndMIN)
     */

    // TEST INFO
    static String               TESTName = "John Smith";
    static int                  TESTID = 1;
    static String[]             TESTEmployeeMEETINGDAYS = {"mon", "tue", "wed"};
    static LinkedList<TimeSet>  TESTInterval = new LinkedList<>();
    

    /**
     * static main
     */
    public static void main(String[] args) throws Exception {

        System.out.println("Hello, World!");

        MeetingScheduler MeetingScheduler = new MeetingScheduler();

        MeetingScheduler.InputTest1();
        MeetingScheduler.FileDataTest1();

    } // void main





    /**
     * First input test
     * used to test the basic functionality of user inputs
     * including: name, id, meeting days, and meeting times.
     */
    public void InputTest1() {

        System.out.println("Input Test 1: Start");


        // Adds a single beignning and ending time to the list
        TESTInterval.add(new TimeSet(1, 0, 2, 0));


        // sets all input testing data
        StaticInfoInput staticInfo = new StaticInfoInput();
        staticInfo.UserInput(TESTName, TESTID, TESTEmployeeMEETINGDAYS, TESTInterval);


        // Prints the testing info to verify it can be accessed correctly
        System.out.println("Name        : " + StaticInfoInput.EmployeeName);
        System.out.println("ID          : " + StaticInfoInput.EmployeeID);
        System.out.println("Days        : " + Arrays.toString(StaticInfoInput.EmployeeMEETINGDAYS));
        System.out.println("time begin  : " + StaticInfoInput.TimeIntervals.get(0).PreferedHourBEGIN);
        System.out.println("time end    : " + StaticInfoInput.TimeIntervals.get(0).PreferedHourEND);

        // Testing complete
        System.out.println("Input Test 1: Complete");


    } // public void InputTest1()

    public void FileDataTest1() {

        System.out.println("File Data test 1: Start");

        List<String> TestTextLine = new ArrayList<>();

        //set file name to src\TextTestFile.txt
        DataFileInput.setFileName("src\\TextTestFile.txt");

        TestTextLine.add("\n");
        TestTextLine.add("/////////////////////////");
        TestTextLine.add("");
        TestTextLine.add("USER SLOT: ##");
        TestTextLine.add("name: \"John Smith\"");
        TestTextLine.add("ID: #####");
        TestTextLine.add("days: \"mon,tue,wed\"");
        TestTextLine.add("time: \"time1\", \"time2\", \"time3\"");
        TestTextLine.add("");
        TestTextLine.add("/////////////////////////");

        DataFileInput.writeData(TestTextLine);


        System.out.println("File Data test 1: Complete");

    }

} // public class MeetingScheduler



/** 
 * 
 * Import Info
 * 
 * - MeetingScheduler.java -
 * import java.util.Arrays;
 * import java.util.LinkedList;
 * 
 * 
 * - StaticInfoInput.java -
 * import java.util.LinkedList;
 * 
 * 
 * - TimeSet.java -
 * import java.time.LocalTime;
 */
