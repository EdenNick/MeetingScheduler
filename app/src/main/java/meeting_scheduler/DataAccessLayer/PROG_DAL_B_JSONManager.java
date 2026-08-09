package meeting_scheduler.DataAccessLayer;
/**
 * PROG_DAL_B_JSONManager
 * Description: Used to manage the Input and output of the json file containing user preferences.
 * 
 */

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Objects;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

// import java.io.IOException;
// import org.w3c.dom.events.Event;


public class PROG_DAL_B_JSONManager {


    // Class parameters
    private static final File PROG_DATA_UserDataCard = new File("src\\main\\resources\\PROG_DATA_A_UserDataCard.json");
    private LinkedList<PROG_DAL_A_InfoInput> IncomingCardList;      // incoming list of usercards containing user datapreferences
    private LinkedList<PROG_DAL_A_InfoInput> FileCardList;          // Retrieved List of user card from the relvant .Json file.
    private LinkedList<PROG_DAL_A_InfoInput> OutgoingCardList;      // Card list used for all outgoing operations.
    private LinkedList<PROG_DAL_A_InfoInput> tempManagementList;    // temporarylist for performing in class operations
    private boolean                          ObjectReferenceSet = false;

    private boolean                          sameID = false;

    private String[] Weekdays = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    private String[] tempDays;

    private ObjectMapper JsonObjectMapper;

    // iterators
    private ListIterator<PROG_DAL_A_InfoInput> FileCardIterator;
    private ListIterator<PROG_DAL_A_InfoInput> IncomingCardIterator;



    // Testing parameters
    static String                           TestName = "John Smith";
    static int                              TestID = 1;
    static String[]                         TestEmployeeMEETINGDAYS = {"mon", "tue", "wed"};
    static LinkedList<PROG_DAL_A_TimeInput>  TestTimeInterval = new LinkedList<>();
    PROG_DAL_A_InfoInput                     staticInfo;

    private static final File TestFile = new File("src\\main\\resources\\PROG_DATA_A_JsonTestFile.json");


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
        
        this.IncomingCardList       = new LinkedList<>(card);

        if (this.IncomingCardList.size() > 0 ) {    // ensures the list contains at least one object
            this.ObjectReferenceSet = true;
        } else {
            this.ObjectReferenceSet = false;
        }
    }
    





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

        FileCardList = JsonObjectMapper.readValue(PROG_DATA_UserDataCard, new TypeReference<LinkedList<PROG_DAL_A_InfoInput>>() {});

        tempManagementList = new LinkedList<>();

        // iterates through both the incoming list of data cards and the existing datacards to ensure there are no duplicates, if there are,
        // it removes them from the IncomingCardList LinkedList.
        FileCardIterator = FileCardList.listIterator();

        IncomingCardIterator = IncomingCardList.listIterator();

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

                    System.out.println("duplicate name");
                    this.sameID = true;

                    // combine weekdays
                    tempDays = new String[7];


                    // Iterates over both the days in the file and the days from the incoming data card, this ensures
                    // all days are accounted for and are added in the correct order.
                    int index = 0;
                    for (String day : Weekdays) {
                        // iterate through incoming list
                        for (String PersonDay : UserPerson.EmployeeMEETINGDAYS) {
                            if (day.equals(PersonDay)) {
                                tempDays[index] = day;
                            }
                        }

                        // iterate through existing list
                        for (String FileDay : FilePerson.EmployeeMEETINGDAYS) {
                            if (day.equals(FileDay)) {
                                tempDays[index] = day;
                            }
                        }

                        index++;

                    } // (String day : Weekdays)
                    

                    //create a new string with no null values
                    String[] NewWeekday = Arrays.stream(tempDays).filter(Objects::nonNull).toArray(String[]::new);
                    
                    //set new weekdayds for the file data card
                    FilePerson.EmployeeMEETINGDAYS = NewWeekday.clone();

                    // TODO: possibile formatting later.
                    // add new timeInputs to the existingfile

                    // new time inputs
                    for (PROG_DAL_A_TimeInput newInput : UserPerson.TimeIntervals) {

                        // old time inputs
                        for (PROG_DAL_A_TimeInput oldInput : FilePerson.TimeIntervals) {

                            System.out.println("iterate");
                            
                            if (newInput.IsEqual(oldInput)) {
                                System.out.println("remove old input");
                                FilePerson.TimeIntervals.remove(oldInput);
                            }

                        }
                        FilePerson.TimeIntervals.add(newInput);
                    }


                    // break out of this while loop
                    break;
                }

            } // while (FileCardIterator.hasNext()) 

            if (this.sameID == true) {
                // do nothing
                System.out.println("Do nothing");
            } else {
                // id does not exist in file -> add data card
                System.out.println("add to file");
                tempManagementList.add(UserPerson);

            }

            //
            this.sameID = false;
        } // (IncomingCardIterator.hasNext())

        System.out.println(tempManagementList.size());

        FileCardList.addAll(tempManagementList);

        JsonObjectMapper.writerWithDefaultPrettyPrinter().writeValue(PROG_DATA_UserDataCard, FileCardList);

        DiscardCard();

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
