/**
 * DataFileInput.java
 * 
 * Description: Inputs user submitted data to a selected file (write-only)
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class DATA_FILE_Input {


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

    static Path TemporaryFilePath = Paths.get("src\\TempFile.txt");

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
    public static int DeleteData(String ID) {

        if (CheckState() == 1) {
            System.out.println("ERROR: DataFileInput.DeleteData - CheckState -");
            return 1;
        }

        String DeleteID = ID;

        try (BufferedReader FileReader = Files.newBufferedReader(filePath);
            BufferedWriter FileWriter = Files.newBufferedWriter(TemporaryFilePath)) {
            
            // Current line being read
            String CurrentLine;

            // iterates over all lines in the file
            while((CurrentLine = FileReader.readLine()) != null) {

                // if the current line contains the id that needs to be deleted
                if (CurrentLine.contains(DeleteID)) {
                    
                    // iterates over five lines without performing an operation
                    // effectiveley removes the lines while self organizing
                    for (int x = 0; x < 4; x++) {
                        CurrentLine = FileReader.readLine();
                    }

                } else {
                    FileWriter.write(CurrentLine);
                    FileWriter.newLine();
                }

            } // while()

            FileWriter.close();
            FileReader.close();

            Files.move(TemporaryFilePath, filePath, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("File: " + filePath + " changed");

        } catch (IOException e) {

            System.out.println("ERROR: DataFileInput.DeleteData - file delete - error in the try catch");
            return 1;

        }

        Path filePath = null;
        Locked = false;


        return 0;

    } // DeleteData(String ID)



    /** 
     * organizes specified file
     * iterates over a file removing any blank lines
     * should only be called after data within a file is deleted.
     */
    public static int OrganizeData() {

        if (CheckState() == 1) {
            System.out.println("ERROR: DataFileInput.OrganizeData - CheckState -");
            return 1;
        }


        try (BufferedReader FileReader = Files.newBufferedReader(filePath);
            BufferedWriter FileWriter = Files.newBufferedWriter(TemporaryFilePath)) {
            
            // Current line being read
            String CurrentLine;

            // iterates over all lines in the file
            while((CurrentLine = FileReader.readLine()) != null) {

                if (CurrentLine.isBlank()) {
                    // Do nothing
                } else {
                    FileWriter.write(CurrentLine);
                    FileWriter.newLine();
                }

            } // while()

            FileWriter.close();
            FileReader.close();

            Files.move(TemporaryFilePath, filePath, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("File: " + filePath + " changed");

        } catch (IOException e) {

            System.out.println("ERROR: DataFileInput.DeleteData - file delete - error in the try catch");
            return 1;

        }

        Path filePath = null;
        Locked = false;

        return 0;
    } // OrganizeData()





    
}
