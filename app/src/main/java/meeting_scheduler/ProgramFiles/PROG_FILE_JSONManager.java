package meeting_scheduler.ProgramFiles;
/**
 * PROG_FILE_JSONManager
 * Description: Used to manage the Input and output of the json file containing user preferences
 * 
 */

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

// import java.io.IOException;
// import org.w3c.dom.events.Event;


public class PROG_FILE_JSONManager {


    // Class parameters
    private static final File PROG_DATA_UserDataCard = new File("app\\src\\main\\java\\meeting_scheduler\\DataFiles\\PROG_DATA_UserDataCard.json");
    LinkedList<PROG_INFO_InfoInput> IncomingCardList;   // incoming list of usercards containing user datapreferences
    LinkedList<PROG_INFO_InfoInput> FileCardList;  // Retrieved List of user card from the relvant .Json file.
    LinkedList<PROG_INFO_InfoInput> OutgoingCardList;   // Card list used for all outgoing operations.
    boolean                         ObjectReferenceSet = false;

    ObjectMapper JsonObjectMapper;





    // Testing parameters
    static String                           TestName = "John Smith";
    static int                              TestID = 1;
    static String[]                         TestEmployeeMEETINGDAYS = {"mon", "tue", "wed"};
    static LinkedList<PROG_INFO_TimeInput>  TestTimeInterval = new LinkedList<>();
    PROG_INFO_InfoInput                     staticInfo;

    private static final File TestFile = new File("app\\src\\main\\java\\meeting_scheduler\\DataFiles\\JsonTestFile.json");


    public PROG_FILE_JSONManager() {
        JsonObjectMapper = new ObjectMapper();
        JsonObjectMapper.registerModule(new JavaTimeModule());
        JsonObjectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }





    /**
     * SetUserCards()
     * Description: Incoming datacards of employee preferences the user wants saved
     * @param card
     */
    public void SetUserCards(LinkedList<PROG_INFO_InfoInput> card) {
        
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
            System.out.println("ERROR - PROG_FILE_JSONManager - SetToFile - objectrefence null or missing values");
            return 1;
        }

        FileCardList = JsonObjectMapper.readValue(TestFile, new TypeReference<LinkedList<PROG_INFO_InfoInput>>() {});


        // iterates through both the incoming list of data cards and the existing datacards to ensure there are no duplicates, if there are,
        // it removes them from the IncomingCardList LinkedList.
        for (PROG_INFO_InfoInput FilePerson : FileCardList) {
            
            int UserPersonIndex = 0;

            for (PROG_INFO_InfoInput UserPerson : IncomingCardList) {
                
                if (UserPerson.EmployeeID == FilePerson.EmployeeID) {
                    IncomingCardList.remove(UserPersonIndex);
                } else {
                    FileCardList.add(IncomingCardList.get(UserPersonIndex));
                }

                UserPersonIndex++;

            } // for()

        } // for()

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
        FileCardList = JsonObjectMapper.readValue(PROG_DATA_UserDataCard, new TypeReference<LinkedList<PROG_INFO_InfoInput>>() {});

        OutgoingCardList = new LinkedList<>(FileCardList);

        return 0;
    }








    public void JsonWriteTest1() throws StreamWriteException, DatabindException, IOException {

        TestTimeInterval.add(new PROG_INFO_TimeInput("Mon",8, 0, 12, 0));
        TestTimeInterval.add(new PROG_INFO_TimeInput("Mon",14, 1, 15, 30));
        TestTimeInterval.add(new PROG_INFO_TimeInput("Fri",12, 5, 17, 45));

        // creates userinfo object and sets all input testing data
        this.staticInfo = new PROG_INFO_InfoInput(TestName, TestID, TestEmployeeMEETINGDAYS, TestTimeInterval);

        LinkedList<PROG_INFO_InfoInput> ListInfo = new LinkedList<PROG_INFO_InfoInput>();

        ListInfo.add(staticInfo);

        JsonObjectMapper.writerWithDefaultPrettyPrinter().writeValue(TestFile, ListInfo);

    }

    public void JsonWriteTest2() throws StreamWriteException, DatabindException, IOException {

        this.staticInfo = new PROG_INFO_InfoInput("test2", 2, TestEmployeeMEETINGDAYS, TestTimeInterval);

        LinkedList<PROG_INFO_InfoInput> UserCards;// = new LinkedList<PROG_INFO_InfoInput>();

        //JsonObjectMapper.registerModule(new JavaTimeModule());
        //JsonObjectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        //try {

        // read data
            //LinkedList<PROG_INFO_InfoInput> ListInfo = new LinkedList<PROG_INFO_InfoInput>();

            UserCards = JsonObjectMapper.readValue(TestFile, new TypeReference<LinkedList<PROG_INFO_InfoInput>>() {});
        // add data
            UserCards.add(staticInfo);

        //}  catch (IOException e) {
           //System.out.println("ERROR - PROG_FILE_JSONManager - JsonWriteTest2 - try/catch failure");
        // }


        // write to file
        JsonObjectMapper.writerWithDefaultPrettyPrinter().writeValue(TestFile, UserCards);

    }


    
}
