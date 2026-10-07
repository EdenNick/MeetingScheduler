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
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import meeting_scheduler.SystemInfoManager;
import meeting_scheduler.DataHolder.PreferenceFull;
// ############################################################



public class ManageJsonOutput {

    // Class parameters
    // ############################################################
    // File path
    private File DATAFILE_Preferences;

    // read/write to json file
    private ObjectMapper JsonObjectMapper;

    // Lock
    //TODO: implement a lock system

    // Retreived File
    private LinkedList<PreferenceFull> JsonFileRetrievedList;


    // Contructor
    public ManageJsonOutput(File INPUT_FILE) {
        
        this.DATAFILE_Preferences = INPUT_FILE;

        this.JsonObjectMapper = new ObjectMapper();
        this.JsonObjectMapper.registerModule(new JavaTimeModule());
        this.JsonObjectMapper.enable(SerializationFeature.INDENT_OUTPUT);

    }  


    /**
     * RetrieveFromFile()
     * Description: used to set incoming datacards to the PROG_DATA_UserDataCard.json file.
     * @throws IOException 
     * @throws DatabindException 
     * @throws StreamReadException 
     */
    public LinkedList<PreferenceFull> Retrieve_DefaultFile() throws StreamReadException, DatabindException, IOException {

        // retrieves existing datacards from the json file
        try {
            JsonFileRetrievedList = JsonObjectMapper.readValue(DATAFILE_Preferences, new TypeReference<LinkedList<PreferenceFull>>() {});

            // System Message
            // 5 - SUCCESS | 25 - MANAGEFILE_JsonOutput | 10 - SYSTEM-FileAccess | 8 - try/catch Json file read Successful
            SystemInfoManager.GET_SYSTEM_MESSAGE(5,25,10,8);

        } catch (IOException e){

            // System Message
            // 6 - FAILURE | 25 - MANAGEFILE_JsonOutput | 10 - SYSTEM-FileAccess | 8 - try/catch Json file read Successful
            SystemInfoManager.GET_SYSTEM_MESSAGE(6,25,10,9);

            e.printStackTrace();
        }

        return new LinkedList<>(JsonFileRetrievedList);

    } // RetrieveFromFile

    
}
