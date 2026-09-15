/**
 * MANAGEFILE_JsonInput.java
 * 
 * Description: Used to manage the Input of data into the preferences .JSON file.
 * 
 */

// Package  - DO Not Change
// ############################################################
package meeting_scheduler.FIleManagement;
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
import meeting_scheduler.EmployeePreferences.STATIC_EMPLOYEE_FullPref;
import meeting_scheduler.EmployeePreferences.STATIC_EMPLOYEE_TimePref;
import meeting_scheduler.SceneManagement.SCENE_VARIABLES_Local;

public class MANAGEFILE_JsonInput {

    // Class parameters
    // ############################################################
    // File path
    private File DATAFILE_Preferences;

    // read/write to json file
    private ObjectMapper JsonObjectMapper;

    // Lock
    //TODO: implement a lock system

    // Retreived File
    private LinkedList<STATIC_EMPLOYEE_FullPref> JSONFileInputList;


    // Contructor
    public MANAGEFILE_JsonInput(File INPUT_FILE) {
        this.DATAFILE_Preferences = INPUT_FILE;

        this.JsonObjectMapper = new ObjectMapper();
        this.JsonObjectMapper.registerModule(new JavaTimeModule());
        this.JsonObjectMapper.enable(SerializationFeature.INDENT_OUTPUT);

    }


    public void SetInput(LinkedList<STATIC_EMPLOYEE_FullPref> INPUT_DATACARDLIST) {
        this.JSONFileInputList = new LinkedList<>(INPUT_DATACARDLIST);
    }

    public int WriteToFile() throws StreamWriteException, DatabindException, IOException {
        // write the updated list back into the file
        JsonObjectMapper.writerWithDefaultPrettyPrinter().writeValue(DATAFILE_Preferences, JSONFileInputList);

        return 0;
    } // WriteToFile()

    
}
