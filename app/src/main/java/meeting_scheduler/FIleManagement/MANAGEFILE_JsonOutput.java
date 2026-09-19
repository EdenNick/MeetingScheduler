/**
 * MANAGEFILE_JsonOutput.java
 * 
 * Description: Used to manage the output of data from the preferences .JSON file.
 * 
 */

// Package  - DO Not Change
// ############################################################
package meeting_scheduler.FileManagement;
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

import meeting_scheduler.DataAccessLayer.PROG_DAL_D_SystemMessages;
import meeting_scheduler.EmployeePreferences.PREF_EMPLOYEE_FullPref;
import meeting_scheduler.EmployeePreferences.PREF_EMPLOYEE_TimePref;
import meeting_scheduler.SceneManagement.SCENE_VARIABLES_Local;

public class MANAGEFILE_JsonOutput {

    // Class parameters
    // ############################################################
    // File path
    private File DATAFILE_Preferences;

    // read/write to json file
    private ObjectMapper JsonObjectMapper;

    // Lock
    //TODO: implement a lock system

    // Retreived File
    private LinkedList<PREF_EMPLOYEE_FullPref> JsonFileRetrievedList;


    // Contructor
    public MANAGEFILE_JsonOutput(File INPUT_FILE) {
        
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
    public LinkedList<PREF_EMPLOYEE_FullPref> Retrieve_DefaultFile() throws StreamReadException, DatabindException, IOException {

        // retrieves existing datacards from the json file
        JsonFileRetrievedList = JsonObjectMapper.readValue(DATAFILE_Preferences, new TypeReference<LinkedList<PREF_EMPLOYEE_FullPref>>() {});

        return new LinkedList<>(JsonFileRetrievedList);

    } // RetrieveFromFile

    
}
