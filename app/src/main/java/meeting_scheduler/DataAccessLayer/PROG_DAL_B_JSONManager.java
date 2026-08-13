/**
 * PROG_DAL_B_JSONManager.java
 * 
 * Description: Used to manage the Input and output of the json file containing user preferences.
 * 
 */

// Package  - DO Not Change
// ############################################################
package meeting_scheduler.DataAccessLayer;
// ############################################################

// Imports
// ############################################################
// Java.io
import java.io.File;
import java.io.IOException;
// java.util
import java.util.Arrays;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Objects;
// jackson (json file manager)
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
// ############################################################



public class PROG_DAL_B_JSONManager {


    // Class parameters
    // ############################################################
    // File path
    private static final File PROG_DATA_UserDataCard = new File("src\\main\\resources\\PROG_DATA_A_UserDataCard.json");

    // read/write to json file
    private ObjectMapper JsonObjectMapper;

    // Json Object management
    private LinkedList<PROG_DAL_A_InfoInput>    IncomingCardList;      // incoming list of usercards containing user datapreferences
    private LinkedList<PROG_DAL_A_InfoInput>    FileCardList;          // Retrieved List of user card from the relvant .Json file.
    private LinkedList<PROG_DAL_A_InfoInput>    OutgoingCardList;      // Card list used for all outgoing operations.
    private LinkedList<PROG_DAL_A_InfoInput>    tempManagementList;    // temporarylist for performing in class operations

    // iterators
    private ListIterator<PROG_DAL_A_InfoInput>  FileCardIterator;
    private ListIterator<PROG_DAL_A_InfoInput>  IncomingCardIterator;

    // arrays
    private final   String[] Weekdays = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    private         String[] tempDays;

    // boolean
    private boolean ObjectReferenceSet  = false;
    private boolean sameID              = false;
    // ############################################################



    // Testing parameters
    // ############################################################
    // file path
    private static final File TestFile = new File("src\\main\\resources\\PROG_DATA_A_JsonTestFile.json");

    private static  String                              TestName                = "John Smith";
    private static  int                                 TestID                  = 1;
    private static  String[]                            TestEmployeeMEETINGDAYS = {"mon", "tue", "wed"};
    private static  LinkedList<PROG_DAL_A_TimeInput>    TestTimeInterval        = new LinkedList<>();
    private         PROG_DAL_A_InfoInput                staticInfo;
    // ############################################################



    public PROG_DAL_B_JSONManager() {
        this.JsonObjectMapper = new ObjectMapper();
        this.JsonObjectMapper.registerModule(new JavaTimeModule());
        this.JsonObjectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }





    /**
     * SetUserCards()
     * Description: Incoming datacards of employee preferences the user wants saved
     * @param card
     */
    public void SetUserCards(LinkedList<PROG_DAL_A_InfoInput> card) {
        
        this.IncomingCardList = new LinkedList<>(card);

        // ensures the list contains at least one object
        if (this.IncomingCardList.size() > 0 )  { // File operations WILL work
            this.ObjectReferenceSet = true;
        } else                                  { // File operations WILL NOT work
            this.ObjectReferenceSet = false;
        }

    } // SetUserCards()
    





    /**
     * DiscardCard()
     * Description: sets the local object reference to null. Should be called after every file operation
     * Used to prevent accidental operations on the object after they have concluded.
     * ObjectReferenceSet set to false indicates operations should not be performed on the object
     */
    public void DiscardCard() {
        this.IncomingCardList   = null;
        this.ObjectReferenceSet = false;
    }





    /**
     * SetToFile()
     * Description: used to set incoming datacards to the PROG_DATA_UserDataCard.json file.
     * @throws IOException 
     * @throws DatabindException 
     * @throws StreamReadException 
     */
    public int SetToFile() throws StreamReadException, DatabindException, IOException { // File
        
        if (ObjectReferenceSet == false) {
            System.out.println("ERROR - PROG_FILE_JSONManager - SetToFile - object refence null or missing values");
            return 1;
        }

        this.FileCardList        = JsonObjectMapper.readValue(PROG_DATA_UserDataCard, new TypeReference<LinkedList<PROG_DAL_A_InfoInput>>() {});

        this.tempManagementList  = new LinkedList<>();

        // iterates through both the incoming list of data cards and the existing datacards to ensure there are no duplicates, if there are,
        // it removes them from the IncomingCardList LinkedList.
        this.FileCardIterator        = FileCardList      .listIterator();
        this.IncomingCardIterator    = IncomingCardList  .listIterator();

        // Iterate through incoming json data
        this.sameID = false;

        while (IncomingCardIterator.hasNext()) {
            
            // incoming list of people
            PROG_DAL_A_InfoInput UserPerson = IncomingCardIterator.next();

            // Iterate through current json file data
            while (FileCardIterator.hasNext()) {
                
                // info retrieved from file
                PROG_DAL_A_InfoInput FilePerson = FileCardIterator.next();

                // incomin datacard has the same id has one in the json file
                if (UserPerson.EmployeeID == FilePerson.EmployeeID) {

                    this.sameID     = true;
                    this.tempDays   = new String[7];
                    int index       = 0;

                    // Iterates over both the days in the file and the days from the incoming data card, this ensures
                    // all days are accounted for and are added in the correct order.
                    for (String day : Weekdays) {
                        // iterate through incoming list
                        for (String PersonDay : UserPerson.EmployeeMEETINGDAYS) {
                            if (day.equals(PersonDay)) {
                                tempDays[index] = day;
                            }
                        } // for()

                        // iterate through existing list
                        for (String FileDay : FilePerson.EmployeeMEETINGDAYS) {
                            if (day.equals(FileDay)) {
                                tempDays[index] = day;
                            }
                        } // for()

                        index++;

                    } // (String day : Weekdays)
                    

                    // create a new string with no null values
                    String[] NewWeekday = Arrays.stream(tempDays).filter(Objects::nonNull).toArray(String[]::new);
                    
                    // set new weekdayds for the file data card
                    FilePerson.EmployeeMEETINGDAYS = NewWeekday.clone();


                    // formatting new time inputs
                    for (PROG_DAL_A_TimeInput newInput : UserPerson.TimeIntervals) {

                        // old time inputs
                        for (PROG_DAL_A_TimeInput oldInput : FilePerson.TimeIntervals) {

                            if (newInput.IsEqual(oldInput)) {
                                System.out.println("remove old input");
                                FilePerson.TimeIntervals.remove(oldInput);
                            }

                        } // for ()

                        FilePerson.TimeIntervals.add(newInput);

                    } // for()


                    // break out of this while loop
                    break;
                }

            } // while (FileCardIterator.hasNext()) 

            if (this.sameID == true) {
                // do nothing
            } else {
                // id does not exist in file -> add data card
                tempManagementList.add(UserPerson);
            } // if ()

            this.sameID = false;

        } // (IncomingCardIterator.hasNext())

        // Add updated information to the list of filecard linkedlist of json data
        FileCardList.addAll(tempManagementList);

        // write the updated list back into the file
        JsonObjectMapper.writerWithDefaultPrettyPrinter().writeValue(PROG_DATA_UserDataCard, FileCardList);

        // discard the incoming list to ensure it is not used again by accident
        DiscardCard();

        // no errors
        return 0;
    }





    /**
     * RetrieveFromFile()
     * Description: used to set incoming datacards to the PROG_DATA_UserDataCard.json file.
     * @throws IOException 
     * @throws DatabindException 
     * @throws StreamReadException 
     */
    public int RetrieveFromFile() throws StreamReadException, DatabindException, IOException {

        // retrieves existing datacrads from files
        FileCardList = JsonObjectMapper.readValue(PROG_DATA_UserDataCard, new TypeReference<LinkedList<PROG_DAL_A_InfoInput>>() {});

        OutgoingCardList = new LinkedList<>(FileCardList);

        return 0;
    }



    /**
     * ReturnFile()
     * Description: Returns the outgoingcard list
     */
    public LinkedList<PROG_DAL_A_InfoInput> ReturnFile() {

        return OutgoingCardList;
    }








    public void JsonWriteTest1() throws StreamWriteException, DatabindException, IOException {

        TestTimeInterval.add(new PROG_DAL_A_TimeInput("Mon",8, 0, 12, 0));
        TestTimeInterval.add(new PROG_DAL_A_TimeInput("Mon",14, 1, 15, 30));
        TestTimeInterval.add(new PROG_DAL_A_TimeInput("Fri",12, 5, 17, 45));

        // creates userinfo object and sets all input testing data
        this.staticInfo = new PROG_DAL_A_InfoInput(TestName, TestID, TestEmployeeMEETINGDAYS, TestTimeInterval);

        LinkedList<PROG_DAL_A_InfoInput> ListInfo = new LinkedList<PROG_DAL_A_InfoInput>();

        ListInfo.add(staticInfo);

        JsonObjectMapper.writerWithDefaultPrettyPrinter().writeValue(TestFile, ListInfo);

    }

    public void JsonWriteTest2() throws StreamWriteException, DatabindException, IOException {

        this.staticInfo = new PROG_DAL_A_InfoInput("test2", 2, TestEmployeeMEETINGDAYS, TestTimeInterval);

        LinkedList<PROG_DAL_A_InfoInput> UserCards;// = new LinkedList<PROG_INFO_InfoInput>();

        //JsonObjectMapper.registerModule(new JavaTimeModule());
        //JsonObjectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        //try {

        // read data
            //LinkedList<PROG_INFO_InfoInput> ListInfo = new LinkedList<PROG_INFO_InfoInput>();

            UserCards = JsonObjectMapper.readValue(TestFile, new TypeReference<LinkedList<PROG_DAL_A_InfoInput>>() {});
        // add data
            UserCards.add(staticInfo);

        //}  catch (IOException e) {
           //System.out.println("ERROR - PROG_FILE_JSONManager - JsonWriteTest2 - try/catch failure");
        // }


        // write to file
        JsonObjectMapper.writerWithDefaultPrettyPrinter().writeValue(TestFile, UserCards);

    }


    
}
