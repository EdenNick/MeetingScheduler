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
import java.util.LinkedList;
// jackson (json file manager)
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import meeting_scheduler.EmployeePreferences.PREF_EMPLOYEE_FullPref;

public class MANAGEFILE_JsonInput {

    // Class parameters
    // ############################################################
    // File path
    private File DATAFILE_Preferences;

    // read/write to json file
    private ObjectMapper Write_JsonObjectMapper;

    // Lock
    //TODO: implement a lock system
    //TODO: possibly implement enum for multiple files?

    // Retreived File
    private LinkedList<PREF_EMPLOYEE_FullPref> JSONFileInputList;


    // Default Contructor
    public MANAGEFILE_JsonInput(File INPUT_FILE) {

        this.DATAFILE_Preferences = INPUT_FILE;

        this.Write_JsonObjectMapper = new ObjectMapper();
        this.Write_JsonObjectMapper.registerModule(new JavaTimeModule());
        this.Write_JsonObjectMapper.enable(SerializationFeature.INDENT_OUTPUT);

    }


    public int WriteTo_Default_EmployeePrefFile(LinkedList<PREF_EMPLOYEE_FullPref> INPUT_DATACARDLIST) throws StreamWriteException, DatabindException, IOException {

        this.JSONFileInputList = new LinkedList<>(INPUT_DATACARDLIST);

        // IF - write only if the input list isn't null
        if (this.JSONFileInputList != null) {
            Write_JsonObjectMapper.writerWithDefaultPrettyPrinter().writeValue(DATAFILE_Preferences, JSONFileInputList);
            // TODO: SYSTEM MESSAGE
        }

        return 0;
    } // WriteToFile()

    
}
