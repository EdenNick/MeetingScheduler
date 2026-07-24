/**
 * TEST_ALL_FullTest.java
 * 
 * Description: File used to test various functions of the program.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class PROG_TEST_FullTest {


    /**
     * Format for method naming
     * 
     * TEST_(TypeOfTest)_(basicTestDescription)
     * 
     * INFO     - indicates test directly ties to user data variables
     * FILE     - indicates test focuses on file management/manipulation
     * UI       - indicates test focusing on UI functinality
     * GRAPH    - indicates visual test for graphical elements
     */


    /**
     * Input    Format:
     * Name     String
     * ID       int
     * Days     String[6] (sun - mon - tue - wed - thu - fri - sat)
     * Time     TimeSet (int BeginHOUR, int BeginMIN, int EndHOUR, int EndMIN)
     */


    /**
     * Testing Parameters
     */
    static String                           TestName = "John Smith";
    static int                              TestID = 1;
    static String[]                         TestEmployeeMEETINGDAYS = {"mon", "tue", "wed"};
    static LinkedList<PROG_INFO_TimeInput>  TestTimeInterval = new LinkedList<>();
    PROG_INFO_InfoInput                     staticInfo;


    // Constructor - Fills objects with parameters for testing
    public PROG_TEST_FullTest() {

        // Adds a single beignning and ending time to the list
        this.TestTimeInterval.add(new PROG_INFO_TimeInput("Mon",8, 0, 12, 0));
        this.TestTimeInterval.add(new PROG_INFO_TimeInput("Mon",14, 1, 15, 30));
        this.TestTimeInterval.add(new PROG_INFO_TimeInput("Fri",12, 5, 17, 45));

        // creates userinfo object and sets all input testing data
        this.staticInfo = new PROG_INFO_InfoInput(TestName, TestID, TestEmployeeMEETINGDAYS, TestTimeInterval);

    }

    /**
     * FullTest()
     * Runs a full test of all components throughout the entire program
     */
    public void FullTest() {
        INFOTest();
        FILETest();
    }



    /**
     * UserTest()
     * Runs a full test for all user data variables and actions
     */
    public void INFOTest() {
        TEST_INFO_BasicInfoInput();
        TEST_INFO_StandardTimeConversion();
    }



    /**
     * FILETest()
     * Runs a full test for all File manipulation actions
     */
    public void FILETest() {
        TEST_INFO_AddCardToFile("001");
        TEST_INFO_AddCardToFile("002");
        TEST_FILE_RemCardFromFile();
        TEST_FILE_OrganizeUserInfo();
    }


    




    /**
     * TEST_INFO_BasicInfoInput
     * Tests the basic functionality of user data variables
     * including: name, id, meeting days, and meeting times.
     */
    public void TEST_INFO_BasicInfoInput() {

        System.out.println("TEST_USER_BasicInfoInput    Test: Start");


        // Prints the testing info to verify it can be accessed correctly
        System.out.println("Name        : " + staticInfo.EmployeeName);
        System.out.println("ID          : " + staticInfo.EmployeeID);
        System.out.println("Days        : " + Arrays.toString(staticInfo.EmployeeMEETINGDAYS));
        System.out.print("Time Pref   : " + staticInfo.TimeIntervals.get(0).WeekDay);
        System.out.print(" " + staticInfo.TimeIntervals.get(0).PreferedHourBEGIN);
        System.out.println(" - " + staticInfo.TimeIntervals.get(0).PreferedHourEND);

       
        System.out.println("TEST_USER_BasicInfoInput    Test: Complete");

    } // TEST_USER_BasicInfoInput()


    /**
     * TEST_INFO_StandardTimeConversion
     * tests the functionality of the time conversion method in 
     * the DATA_USER_TimeInput class
     */
    public void TEST_INFO_StandardTimeConversion()  {

        System.out.println("TEST_USER_TimeConvert       Test: Start");

        //int IndexPosition = 0;

        for (PROG_INFO_TimeInput TimeInterval : staticInfo.TimeIntervals) {

            TimeInterval.TimeConversion();

        }

        System.out.println("TEST_USER_TimeConvert       Test: Complete");

    }


    /**
     * TEST_INFO_AddCardToFile
     * tests format for User Card information when input to txt files
     * @param ID
     */
    public void TEST_INFO_AddCardToFile(String ID) {

        System.out.println("TEST_File_AddCardToFile:    Test: Start");


        List<String> TestTextLine = new ArrayList<>();


        // Sets file to perform an action on.
        PROG_FILE_Input.setFileName("src\\PROG_DATA_TextTestFile.txt");

        // Data to add to the file
        TestTextLine.add("ID: " + ID);                                  // Keep an Eye on this variable, caused problems when deleting file info
        TestTextLine.add("name: \"John Smith\"");
        TestTextLine.add("days: \"mon,tue,wed\"");
        TestTextLine.add("time: \"time1\", \"time2\", \"time3\"");
        TestTextLine.add("####################");


        PROG_FILE_Input.writeData(TestTextLine);


        System.out.println("TEST_File_AddCardToFile:    Test: Complete");

    } // TEST_File_AddCardToFile(String ID)



    /**
     * TEST_File_RemCardFromFile()
     * Removes a user data input card from the file
     */
    public void TEST_FILE_RemCardFromFile() {

        System.out.println("TEST_File_RemCardFromFile   Test: Start");

        // Sets file to perform an action on.
        PROG_FILE_Input.setFileName("src\\PROG_DATA_TextTestFile.txt");

        // Delete data with User ID "001".
        PROG_FILE_Input.DeleteData("001");


        System.out.println("TEST_File_RemCardFromFile   Test: Complete");

    } // TEST_File_RemCardFromFile()



    /**
     * TEST_FILE_OrganizeUserInfo()
     * organize the file containing user preference inputs to
     * ensure it is corrently formatted
     */
    public void TEST_FILE_OrganizeUserInfo() {

        System.out.println("TEST_FILE_OrganizeUserInfo  Test: Start");

        List<String> TestTextLine = new ArrayList<>();

        PROG_FILE_Input.setFileName("src\\PROG_DATA_TextTestFile.txt");

        // adding a series of blank spaces to the file to simulate unformated lines of space
        TestTextLine.add(" ");
        TestTextLine.add("");
        TestTextLine.add("      ");
        TestTextLine.add("  ");

        PROG_FILE_Input.OrganizeData();

        System.out.println("TEST_FILE_OrganizeUserInfo  Test: Complete");

    }





} // TEST_ALL_FullTest