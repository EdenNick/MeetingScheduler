// Package  - DO Not Change
// ############################################################
package meeting_scheduler.FileManagement;
// ############################################################

// Imports
// ############################################################
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import meeting_scheduler.DataHolder.PreferenceFull;
import meeting_scheduler.SystemInfoManager;
// ############################################################



public class ManageJsonFile {

    // Class parameters
    // ############################################################
    private final File              JsonFileManager_FilePath;
    private final ManageJsonInput   JsonFileManager_FileInput;
    private final ManageJsonOutput  JsonFileManager_FileOutput;
    // ############################################################



    // Default constructor - more can be added if more json files are used
    public ManageJsonFile(File INPUT_FILE) {

        // FILE PATH
        this.JsonFileManager_FilePath   = INPUT_FILE;
        // JSON file input (Write)
        this.JsonFileManager_FileInput  = new ManageJsonInput(JsonFileManager_FilePath);
        // JSON file output (Read)
        this.JsonFileManager_FileOutput = new ManageJsonOutput(JsonFileManager_FilePath);

    } // ManageJsonFile



    /**
     * writeFile_FullPreference()
     * Desription: Writes to a json file using a linkedlist of the PreferenceFull Object
     * @param INPUT_EmployeePreference
     */
    public void writeJson_FullPref(LinkedList<PreferenceFull> INPUT_EmployeePreference) {


        LinkedList<PreferenceFull> Input_Copy = new LinkedList<>(INPUT_EmployeePreference);

        // Try/Catch - Write to file 
        try {
            // Obtains lock of File Object
            synchronized(this.JsonFileManager_FilePath) {
                
                // Write to Selected File
                this.JsonFileManager_FileInput.WriteTo_Default_EmployeePrefFile(Input_Copy);
                
                // System Message
                // ############################################################
                // 5 - SUCCESS | 24 - MANAGEFILE_JsonManager | 10 - SYSTEM-FileAccess | 4 - try/catch File access successful
                SystemInfoManager.GET_SYSTEM_MESSAGE(1,1,10,4);
                // ############################################################
            }

        } catch (IOException e) {

            // System Message
            // ############################################################
            // 6 - FAILURE | 24 - MANAGEFILE_JsonManager | 10 - SYSTEM-FileAccess | 4 - try/catch File access failure
            SystemInfoManager.GET_SYSTEM_MESSAGE(1,1,10,5);
            // ############################################################

            e.printStackTrace();

            System.exit(1);
        }

    } // writeJson_FullPref()


    
    /**
     * readJson_FullPref()
     * Description: reads from a Json file, returning a LinkedList of the PreferenceFull Object
     * @return
     */
    public LinkedList<PreferenceFull> readJson_FullPref() {

        try {
            // Obtains lock of File Object
            synchronized(this.JsonFileManager_FilePath) {
                return this.JsonFileManager_FileOutput.Retrieve_DefaultFile();
            }
        } catch (IOException e) {

            // System Message
            // 6 - FAILURE | 24 - MANAGEFILE_JsonManager | 10 - SYSTEM-FileAccess | 4 - try/catch File access failure
            SystemInfoManager.GET_SYSTEM_MESSAGE(1,1,10,5);
            e.printStackTrace();
            System.exit(1);
            return null;
        }

    } // readJson_FullPref()



    
} // ManageJsonFile{}