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

import meeting_scheduler.SystemInfoManager;
import meeting_scheduler.EmployeePreferences.PREF_EMPLOYEE_FullPref;
// ############################################################



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


    public void WriteTo_Default_EmployeePrefFile(LinkedList<PREF_EMPLOYEE_FullPref> INPUT_DATACARDLIST) throws StreamWriteException, DatabindException, IOException {

        this.JSONFileInputList = new LinkedList<>(INPUT_DATACARDLIST);

        // IF - write only if the input list isn't null
        if (this.JSONFileInputList != null) {

            try {
                Write_JsonObjectMapper.writerWithDefaultPrettyPrinter().writeValue(DATAFILE_Preferences, JSONFileInputList);

                // System Message
                // 5 - SUCCESS | 23 - MANAGEFILE_JsonInput | 10 - SYSTEM-FileAccess | 6 - try/catch Json file Write Successful
                SystemInfoManager.GET_SYSTEM_MESSAGE(5,23,10,6);

            } catch (IOException e) {

                // System Message
                // 6 - FAILURE | 23 - MANAGEFILE_JsonInput | 10 - SYSTEM-FileAccess | 6 - try/catch Json file Write Failure
                SystemInfoManager.GET_SYSTEM_MESSAGE(6,23,10,7);

                e.printStackTrace();
            }
            
        } else {

            // System Message
            // 6 - FAILURE | 23 - MANAGEFILE_JsonInput | 10 - SYSTEM-FileAccess | 6 - File task - invalid input
            SystemInfoManager.GET_SYSTEM_MESSAGE(6,23,10,10);
        }

    } // WriteToFile()

    
}
