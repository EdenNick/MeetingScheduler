/**
 * DataFileInput.java
 * 
 * Description: Inputs user submitted data to a selected file (write-only)
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class DataFileInput {


    /** 
     * File Lists:
     * 
     * 1. UserScheduleStore.txt
     * 2.
     * 3.
     * 
     * Files should only be written past line 20 (starting on line 21)
     */


    
    static Path filePath;

    static boolean Locked = false;

    List<String> textlines;


    /**
     * sets the current state of file parameters
     * Sets the filename used in operations, and sets a lock to prevent race conditions or unwanted file operationsw
     * 
     */
    public static int setFileName(String name) {

        if (Locked == false) {
            filePath = Paths.get(name);
            Locked = true;
        } else if (Locked == true) {
            System.out.println("ERROR: DataFileInput.setFileName - race condition - called while file operation underway");
            return 1;
        }

        return 0;
    }

    /**
     * Checks the current state of the file parameters
     * checks the name to ensure it is valid, and the lock to ensure the file name is not overritten
     * returns 1 on error, 0 on success
     */
    public static int CheckState() {

        if (filePath == null) {
            System.out.println("ERROR: DataFileInput.CheckState - no file name - tried file operation whithout setting FileName");
            return 1;
        }

        if (Locked == false) {
            System.out.println("ERROR: DataFileInput.CheckState - process not locked - tried file opertion without lock set");
            return 1;
        }

        return 0;
    }



    /**
     * writes incoming data into a selected file 
     * does so in order without overwriting existing dta
     * returns 1 on error 0 on success
     */
    public static int writeData(List<String> lines) {

        if (CheckState() == 1) {
            System.out.println("ERROR: DataFileInput.writeData - CheckState -");
            return 1;
        }

        // lines to be added to the file
        List<String> textlines = new ArrayList<>(lines);


        // write to specified file, appending to the end
        try {

            Files.write(filePath, textlines, StandardOpenOption.CREATE, StandardOpenOption.APPEND);

        } catch (IOException e) {

            System.out.println("ERROR: DataFileInput.writeData - file write - error in the try catch");
            return 1;

        }

        Path filePath = null;
        Locked = false;


        return 0;

    }



    /**
     * deletes specified data within a file
     * does not clean or organize file structure
     * returns 1 on error 0 on success
     */
    public static int DeleteData() {

        if (CheckState() == 1) {
            System.out.println("ERROR: DataFileInput.DeleteData - CheckState -");
            return 1;
        }

        return 0;
    }



    /** 
     * organizes specified file
     * should only be called after data within a file is deleted
     * does not clean or organize file structure
     */
    public static int OrganizeData() {

        if (CheckState() == 1) {
            System.out.println("ERROR: DataFileInput.OrganizeData - CheckState -");
            return 1;
        }

        return 0;
    }





    
}
