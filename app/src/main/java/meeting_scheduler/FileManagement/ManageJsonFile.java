// Package  - DO Not Change
// ############################################################
package meeting_scheduler.FileManagement;
// ############################################################

// Imports
// ############################################################
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import meeting_scheduler.SystemInfoManager;
import meeting_scheduler.DataHolder.PreferenceFull;
// ############################################################



public class ManageJsonFile {


    // Class parameters
    // ############################################################
    // File managemet
    private final File                  JsonFileManager_FilePath;
    private final ManageJsonInput  JsonFileManager_FileInput;
    private final ManageJsonOutput JsonFileManager_FileOutput;

    // File Path TODO: add to global variable
    private final String FilePath_Default = "src\\main\\resources\\PROG_DATA_A_UserDataCard.json";      // DEFAULT
    // ############################################################



    // Default constructor - more can be added if more json files are used
    public ManageJsonFile() {
        // FILE PATH
        this.JsonFileManager_FilePath   = new File(FilePath_Default); // TODO: make file path a global variable

        // JSON file input (Write)
        this.JsonFileManager_FileInput  = new ManageJsonInput(JsonFileManager_FilePath);

        // JSON file output (Read)
        this.JsonFileManager_FileOutput = new ManageJsonOutput(JsonFileManager_FilePath);

    }



    // WRITE - Default preference file
    public void WriteTo_DefaultEmployeePreference(LinkedList<PreferenceFull> INPUT_EmployeePreference) {


        LinkedList<PreferenceFull> Input_Copy = new LinkedList<>(INPUT_EmployeePreference);

        // Try/Catch - Write to file 
        try {
            this.JsonFileManager_FileInput.WriteTo_Default_EmployeePrefFile(Input_Copy);
            
            // System Message
            // 5 - SUCCESS | 24 - MANAGEFILE_JsonManager | 10 - SYSTEM-FileAccess | 4 - try/catch File access successful
            SystemInfoManager.GET_SYSTEM_MESSAGE(1,1,10,4);

        } catch (IOException e) {

            // System Message
            // 6 - FAILURE | 24 - MANAGEFILE_JsonManager | 10 - SYSTEM-FileAccess | 4 - try/catch File access failure
            SystemInfoManager.GET_SYSTEM_MESSAGE(1,1,10,5);
            e.printStackTrace();
        }

    }

    // READ - Default preference file
    public LinkedList<PreferenceFull> ReadFrom_DefaultEmployeePreference() throws StreamReadException, DatabindException, IOException {

        return this.JsonFileManager_FileOutput.Retrieve_DefaultFile();

    }

















































    // public void SetTest(boolean SetTest) {

    //     if (SetTest == true) {
    //         MANAGEFILE_JsonManager.PROG_DATA_UserDataCard = new File(SCENE_VARIABLES_Local.JSON_TestFile);
    //     } else if (SetTest == false) {
    //         MANAGEFILE_JsonManager.PROG_DATA_UserDataCard = new File(SCENE_VARIABLES_Local.JSON_UserDataCard);
    //     }

    // }










    // /**
    //  * SetUserCards()
    //  * Description: Incoming datacards of employee preferences the user wants saved
    //  * @param card
    //  */
    // public void SetUserCards(LinkedList<PREF_EMPLOYEE_FullPref> card) {
        
    //     this.IncomingCardList = new LinkedList<>(card);

    //     // ensures the list contains at least one object
    //     if (this.IncomingCardList.size() > 0 )  { // File operations WILL work
    //         this.ObjectReferenceSet = true;
    //     } else                                  { // File operations WILL NOT work
    //         this.ObjectReferenceSet = false;
    //     }

    // } // SetUserCards()
    





    // /**
    //  * DiscardCard()
    //  * Description: sets the local object reference to null. Should be called after every file operation
    //  * Used to prevent accidental operations on the object after they have concluded.
    //  * ObjectReferenceSet set to false indicates operations should not be performed on the object
    //  */
    // public void DiscardCard() {
    //     this.IncomingCardList   = null;
    //     this.ObjectReferenceSet = false;
    // }





    // /**
    //  * SetToFile()
    //  * Description: used to set incoming datacards to the PROG_DATA_UserDataCard.json file.
    //  * @throws IOException 
    //  * @throws DatabindException 
    //  * @throws StreamReadException 
    //  */
    // public int SetToFile() throws StreamReadException, DatabindException, IOException { // File
        
    //     if (ObjectReferenceSet == false) {
    //         System.out.println(PROG_DAL_D_SystemMessages.ERROR_SetToFile);
    //         return 1;
    //     }

    //     this.FileCardList        = JsonObjectMapper.readValue(PROG_DATA_UserDataCard, new TypeReference<LinkedList<PREF_EMPLOYEE_FullPref>>() {});

    //     this.tempManagementList  = new LinkedList<>();

    //     // iterates through both the incoming list of data cards and the existing datacards to ensure there are no duplicates, if there are,
    //     // it removes them from the IncomingCardList LinkedList.
    //     this.FileCardIterator        = FileCardList      .listIterator();
    //     this.IncomingCardIterator    = IncomingCardList  .listIterator();

    //     // Iterate through incoming json data
    //     this.sameID = false;

    //     while (IncomingCardIterator.hasNext()) {
            
    //         // incoming list of people
    //         PREF_EMPLOYEE_FullPref UserPerson = IncomingCardIterator.next();

    //         // Iterate through current json file data
    //         while (FileCardIterator.hasNext()) {
                
    //             // info retrieved from file
    //             PREF_EMPLOYEE_FullPref FilePerson = FileCardIterator.next();

    //             // incomin datacard has the same id has one in the json file
    //             if (UserPerson.GetIdent() == FilePerson.GetIdent()) {

    //                 this.sameID     = true;
    //                 this.tempDays   = new String[7];
    //                 int index       = 0;

    //                 // Iterates over both the days in the file and the days from the incoming data card, this ensures
    //                 // all days are accounted for and are added in the correct order.
    //                 for (String day : Weekdays) {
    //                     // iterate through incoming list
    //                     for (String PersonDay : UserPerson.GetDays()) {
    //                         if (day.equals(PersonDay)) {
    //                             tempDays[index] = day;
    //                         }
    //                     } // for()

    //                     // iterate through existing list
    //                     for (String FileDay : FilePerson.GetDays()) {
    //                         if (day.equals(FileDay)) {
    //                             tempDays[index] = day;
    //                         }
    //                     } // for()

    //                     index++;

    //                 } // (String day : Weekdays)
                    

    //                 // create a new string with no null values
    //                 String[] NewWeekday = Arrays.stream(tempDays).filter(Objects::nonNull).toArray(String[]::new);
                    
    //                 // set new weekdayds for the file data card
    //                 //TODO: new object needs to be made to rectify this
    //                 FilePerson.GetDays() = NewWeekday.clone();


    //                 // formatting new time inputs
    //                 for (PREF_EMPLOYEE_TimePref newInput : UserPerson.TimeIntervals) {

    //                     // old time inputs
    //                     for (PREF_EMPLOYEE_TimePref oldInput : FilePerson.TimeIntervals) {

    //                         if (newInput.IsEqual(oldInput)) {
    //                             System.out.println("remove old input");
    //                             FilePerson.TimeIntervals.remove(oldInput);
    //                         }

    //                     } // for ()

    //                     FilePerson.TimeIntervals.add(newInput);

    //                 } // for()


    //                 // break out of this while loop
    //                 break;
    //             }

    //         } // while (FileCardIterator.hasNext()) 

    //         if (this.sameID == true) {
    //             // do nothing
    //         } else {
    //             // id does not exist in file -> add data card
    //             tempManagementList.add(UserPerson);
    //         } // if ()

    //         this.sameID = false;

    //     } // (IncomingCardIterator.hasNext())

    //     // Add updated information to the list of filecard linkedlist of json data
    //     FileCardList.addAll(tempManagementList);

    //     // write the updated list back into the file
    //     JsonObjectMapper.writerWithDefaultPrettyPrinter().writeValue(PROG_DATA_UserDataCard, FileCardList);

    //     // discard the incoming list to ensure it is not used again by accident
    //     DiscardCard();

    //     // no errors
    //     return 0;
    // }





    // /**
    //  * RetrieveFromFile()
    //  * Description: used to set incoming datacards to the PROG_DATA_UserDataCard.json file.
    //  * @throws IOException 
    //  * @throws DatabindException 
    //  * @throws StreamReadException 
    //  */
    // public int RetrieveFromFile() throws StreamReadException, DatabindException, IOException {

    //     // retrieves existing datacrads from files
    //     FileCardList = JsonObjectMapper.readValue(PROG_DATA_UserDataCard, new TypeReference<LinkedList<PREF_EMPLOYEE_FullPref>>() {});

    //     OutgoingCardList = new LinkedList<>(FileCardList);

    //     return 0;
    // }



    // /**
    //  * ReturnFile()
    //  * Description: Returns the outgoingcard list
    //  */
    // public LinkedList<PREF_EMPLOYEE_FullPref> ReturnFile() {

    //     return OutgoingCardList;
    // }








    // public void JsonWriteTest1() throws StreamWriteException, DatabindException, IOException {

    //     TestTimeInterval.add(new PREF_EMPLOYEE_TimePref("Mon",8, 0, 12, 0));
    //     TestTimeInterval.add(new PREF_EMPLOYEE_TimePref("Mon",14, 1, 15, 30));
    //     TestTimeInterval.add(new PREF_EMPLOYEE_TimePref("Fri",12, 5, 17, 45));

    //     // creates userinfo object and sets all input testing data
    //     this.staticInfo = new PREF_EMPLOYEE_FullPref(TestName, TestID, TestEmployeeMEETINGDAYS, TestTimeInterval);

    //     LinkedList<PREF_EMPLOYEE_FullPref> ListInfo = new LinkedList<PREF_EMPLOYEE_FullPref>();

    //     ListInfo.add(staticInfo);

    //     JsonObjectMapper.writerWithDefaultPrettyPrinter().writeValue(TestFile, ListInfo);

    // }

    // public void JsonWriteTest2() throws StreamWriteException, DatabindException, IOException {

    //     this.staticInfo = new PREF_EMPLOYEE_FullPref("test2", 2, TestEmployeeMEETINGDAYS, TestTimeInterval);

    //     LinkedList<PREF_EMPLOYEE_FullPref> UserCards;// = new LinkedList<PROG_INFO_InfoInput>();

    //     //JsonObjectMapper.registerModule(new JavaTimeModule());
    //     //JsonObjectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    //     //try {

    //     // read data
    //         //LinkedList<PROG_INFO_InfoInput> ListInfo = new LinkedList<PROG_INFO_InfoInput>();

    //         UserCards = JsonObjectMapper.readValue(TestFile, new TypeReference<LinkedList<PREF_EMPLOYEE_FullPref>>() {});
    //     // add data
    //         UserCards.add(staticInfo);

    //     //}  catch (IOException e) {
    //        //System.out.println("ERROR - PROG_FILE_JSONManager - JsonWriteTest2 - try/catch failure");
    //     // }


    //     // write to file
    //     JsonObjectMapper.writerWithDefaultPrettyPrinter().writeValue(TestFile, UserCards);

    // }


    
}
