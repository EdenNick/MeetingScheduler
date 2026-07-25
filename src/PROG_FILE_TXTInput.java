/**
 * DataFileInput.java
 * 
 * Description: Inputs user submitted data to a selected file (write-only).
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

public class PROG_FILE_TXTInput {


    /** 
     * File Lists:
     * 
     * 1. UserScheduleStore.txt
     * 2.
     * 3.
     * 
     */


    
    static Path     FilePath;
    static Path     TemporaryFilePath = Paths.get("src\\PROG_DATA_TempFile.txt");
    static boolean  Locked = false;
    List<String>    TextLines;





    /**
     * setFileName()
     * Description: Sets the current state of file parameters
     * Sets the filename used in operations, and sets a lock to prevent race conditions or unwanted file operationsw
     * necessary as this class contains methods that write to files.
     */
    public static int setFileName(String name) {

        if (Locked == false) {

            FilePath    = Paths.get(name);
            Locked      = true;

        } else if (Locked == true) {
            System.out.println("ERROR - PROG_FILE_TXTInput - setFileName() - called while file operation underway");
            return 1;
        }

        return 0;
    }





    /**
     * CheckState()
     * Description: Checks the current state of the file parameters
     * checks the name to ensure it is valid, and the lock to ensure the file name is not overritten
     * returns 1 on error, 0 on success
     */
    public static int CheckState() {

        if (FilePath == null) {
            System.out.println("ERROR - PROG_FILE_TXTInput - CheckState() - tried file operation whithout setting FileName");
            return 1;
        }

        if (Locked == false) {
            System.out.println("ERROR - PROG_FILE_TXTInput - CheckState() - tried file opertion without lock set");
            return 1;
        }

        return 0;
    }





    /**
     * writes incoming data into a selected txt file 
     * does so in order without overwriting existing dta
     * returns 1 on error 0 on success
     */
    public static int writeData(List<String> lines) {

        if (CheckState() == 1) {
            System.out.println("ERROR - PROG_FILE_TXTInput - writeData() - Check state failed");
            return 1;
        }


        // lines to be added to the file
        List<String> TextLines = new ArrayList<>(lines);


        // write to specified file, appending to the end
        try {

            Files.write(FilePath, TextLines, StandardOpenOption.CREATE, StandardOpenOption.APPEND);

        } catch (IOException e) {
            System.out.println("ERROR - PROG_FILE_TXTInput - writeData() - try/catch File write");
            return 1;
        }

        TextLines   = null;
        FilePath    = null;
        Locked      = false;


        return 0;

    } // writeData()





    /**
     * DeleteData()
     * Description: deletes specified data within a txt file
     * does not clean or organize file structure
     * returns 1 on error 0 on success
     */
    public static int DeleteData(String ID) {

        if (CheckState() == 1) {
            System.out.println("ERROR - PROG_FILE_TXTInput - writeData() - Check state failed");
            return 1;
        }


        String DeleteID = ID;

        try (BufferedReader FileReader = Files.newBufferedReader(FilePath);
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

            Files.move(TemporaryFilePath, FilePath, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("File: " + FilePath + " changed");

        } catch (IOException e) {
            System.out.println("ERROR - PROG_FILE_TXTInput - DeleteData() - try/catch error");
            return 1;
        }

        FilePath    = null;
        Locked      = false;

        return 0;

    } // DeleteData()



    /** 
     * OrganizeData()
     * Description: organizes specified .txt file
     * Iterates over a file removing any blank lines
     * should only be called after data within a file is deleted.
     */
    public static int OrganizeData() {

        if (CheckState() == 1) {
            System.out.println("ERROR - PROG_FILE_TXTInput - writeData() - Check state failed");
            return 1;
        }



        try (BufferedReader FileReader = Files.newBufferedReader(FilePath);
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

            Files.move(TemporaryFilePath, FilePath, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("File: " + FilePath + " changed");

        } catch (IOException e) {

            System.out.println("ERROR - PROG_FILE_TXTInput - OrganizeData() - try/catch error");
            return 1;

        }

        FilePath = null;
        Locked = false;

        return 0;

    } // OrganizeData()



}
