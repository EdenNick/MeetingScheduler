/**
 * TEST_ALL_FullTest.java
 * 
 * Description: File used to test various functions of the program.
 */

// Package  - DO Not Change
// ############################################################
package meeting_scheduler.UIBackBoneManagement;
// ############################################################

// Imports
// ############################################################
// exceptions
import java.io.IOException;
// util
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
// jackson - json file manager
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;

import meeting_scheduler.EmployeePreferences.STATIC_EMPLOYEE_FullPref;
import meeting_scheduler.EmployeePreferences.STATIC_EMPLOYEE_TimePref;
import meeting_scheduler.FIleManagement.MANAGEFILE_JsonManager;
import meeting_scheduler.FIleManagement.MANAGEFILE_TXTInput;



public class MANAGEAPP_FullTest {


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
    static  String                              TestName                = "John Smith";
    static  int                                 TestID                  = 1;
    static  String[]                            TestEmployeeMEETINGDAYS = {"mon", "tue", "wed"};
    static  LinkedList<STATIC_EMPLOYEE_TimePref>    TestTimeInterval        = new LinkedList<>();
    private STATIC_EMPLOYEE_FullPref                staticInfo;


    // Constructor - Fills objects with parameters for testing
   // Constructor - Fills objects with parameters for testing
    public MANAGEAPP_FullTest() {

        // Adds a single beignning and ending time to the list
        MANAGEAPP_FullTest.TestTimeInterval.add(new STATIC_EMPLOYEE_TimePref("Mon",8, 0, 12, 0));
        MANAGEAPP_FullTest.TestTimeInterval.add(new STATIC_EMPLOYEE_TimePref("Mon",14, 1, 15, 30));
        MANAGEAPP_FullTest.TestTimeInterval.add(new STATIC_EMPLOYEE_TimePref("Fri",12, 5, 17, 45));

        // creates userinfo object and sets all input testing data
        this.staticInfo = new STATIC_EMPLOYEE_FullPref(TestName, TestID, TestEmployeeMEETINGDAYS, TestTimeInterval);

    }

    /**
     * FullTest()
     * Runs a full test of all components throughout the entire program
     * @throws IOException 
     * @throws DatabindException 
     * @throws StreamWriteException 
     */
    public void FullTest() throws StreamWriteException, DatabindException, IOException {
        INFOTest();
        FILETest();
    }



    /**
     * UserTest()
     * Runs a full test for all user data variables and actions
     * @throws IOException 
     * @throws DatabindException 
     * @throws StreamWriteException 
     */
    public void INFOTest() throws StreamWriteException, DatabindException, IOException {
        //TEST_INFO_BasicInfoInput();
        //TEST_INFO_StandardTimeConversion();
        TEST_INFO_JsonTest();
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
     * TEST_INFO_JsonTest()
     * Description: tests the file read and write capabilities of the json manager
     */
    public void TEST_INFO_JsonTest() throws StreamWriteException, DatabindException, IOException  {

        MANAGEFILE_JsonManager test = new MANAGEFILE_JsonManager();
        test.JsonWriteTest1();
        test.JsonWriteTest2();

    }





    /**
     * TEST_INFO_BasicInfoInput
     * Description: Tests the basic functionality of user data variables
     * including: name, id, meeting days, and meeting times.
     */
    public void TEST_INFO_BasicInfoInput() {

        System.out.println("TEST_USER_BasicInfoInput    Test: Start");


        // Prints the testing info to verify it can be accessed correctly
        System.out.println("Name        : " + staticInfo.GetName());
        System.out.println("ID          : " + staticInfo.GetIdent());
        System.out.println("Days        : " + Arrays.toString(staticInfo.GetDays()));
        System.out.print("Time Pref   : " + staticInfo.GetIntervals().get(0).GetWeekDay());
        System.out.print(" " + staticInfo.GetIntervals().get(0).GetStartTime());
        System.out.println(" - " + staticInfo.GetIntervals().get(0).GetEndTime());

       
        System.out.println("TEST_USER_BasicInfoInput    Test: Complete");

    } // TEST_USER_BasicInfoInput()


    /**
     * TEST_INFO_StandardTimeConversion
     * Description: tests the functionality of the time conversion method in 
     * the DATA_USER_TimeInput class
     */
    public void TEST_INFO_StandardTimeConversion()  {

        System.out.println("TEST_USER_TimeConvert       Test: Start");

        //int IndexPosition = 0;

        for (STATIC_EMPLOYEE_TimePref TimeInterval : staticInfo.GetIntervals()) {

            TimeInterval.TEST_TimeConversion();

        }

        System.out.println("TEST_USER_TimeConvert       Test: Complete");

    }


    /**
     * TEST_INFO_AddCardToFile
     * Description: tests format for User Card information when input to txt files
     * @param ID
     */
    public void TEST_INFO_AddCardToFile(String ID) {

        System.out.println("TEST_File_AddCardToFile:    Test: Start");


        List<String> TestTextLine = new ArrayList<>();


        // Sets file to perform an action on.
        MANAGEFILE_TXTInput.setFileName("app\\src\\main\\java\\meeting_scheduler\\DataLayer\\PROG_DATA_B_TextTestFile.txt");

        // Data to add to the file
        TestTextLine.add("ID: " + ID);                                  // Keep an Eye on this variable, caused problems when deleting file info
        TestTextLine.add("name: \"John Smith\"");
        TestTextLine.add("days: \"mon,tue,wed\"");
        TestTextLine.add("time: \"time1\", \"time2\", \"time3\"");
        TestTextLine.add("####################");


        MANAGEFILE_TXTInput.writeData(TestTextLine);


        System.out.println("TEST_File_AddCardToFile:    Test: Complete");

    } // TEST_File_AddCardToFile(String ID)



    /**
     * TEST_File_RemCardFromFile()
     * Description: Removes a user data input card from the file
     */
    public void TEST_FILE_RemCardFromFile() {

        System.out.println("TEST_File_RemCardFromFile   Test: Start");

        // Sets file to perform an action on.
        MANAGEFILE_TXTInput.setFileName("app\\src\\main\\java\\meeting_scheduler\\DataLayer\\PROG_DATA_B_TextTestFile.txt");

        // Delete data with User ID "001".
        MANAGEFILE_TXTInput.DeleteData("001");


        System.out.println("TEST_File_RemCardFromFile   Test: Complete");

    } // TEST_File_RemCardFromFile()



    /**
     * TEST_FILE_OrganizeUserInfo()
     * Description: organize the file containing user preference inputs to
     * ensure it is corrently formatted
     */
    public void TEST_FILE_OrganizeUserInfo() {

        System.out.println("TEST_FILE_OrganizeUserInfo  Test: Start");

        List<String> TestTextLine = new ArrayList<>();

        MANAGEFILE_TXTInput.setFileName("app\\src\\main\\java\\meeting_scheduler\\DataLayer\\PROG_DATA_B_TextTestFile.txt");

        // adding a series of blank spaces to the file to simulate unformated lines of space
        TestTextLine.add(" ");
        TestTextLine.add("");
        TestTextLine.add("      ");
        TestTextLine.add("  ");

        MANAGEFILE_TXTInput.OrganizeData();

        System.out.println("TEST_FILE_OrganizeUserInfo  Test: Complete");

    }





}