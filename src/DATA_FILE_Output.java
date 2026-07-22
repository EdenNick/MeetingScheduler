/**
 * DataFileOutput.java
 * 
 * Description: 
 * Retrieves data from files (read-only)
 * No locks or preventative measures need to be implemented as all methods are read only operations
 */

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;


public class DATA_FILE_Output {

    private String              FILENAME    = "";
    private String              InvalidText = "File could not be read";
    private String              USERID      = "-1";
    private LinkedList<String>  FileText;
    private Path                FilePath;


    /**
     * Constructor
     * used when the full contents of a single file Need to be read;
     * @param FileToRead
     */
    public void DATA_FILE_Output(String FileToRead) {

        this.FILENAME   = FileToRead;
        FileText.add(FILENAME);
        FilePath = Paths.get(FILENAME);

    }

    public void DATA_FILE_Output(String FileToRead, int ID) {

        // Ensures valid ID
        if (ID < -1) {
            ID = -1;
        }

        this.FILENAME   = FileToRead;
        this.USERID     = Integer.toString(ID);
        FileText.add(FILENAME);
        FilePath = Paths.get(FILENAME);

    }

    /**
     * reads the specified file either as a whole.
     */
    public LinkedList<String> ReadFile() {

        FileText = new LinkedList<>();


        try (BufferedReader FileReader = Files.newBufferedReader(FilePath);){


            String CurrentLine;

            // iterates over all lines in the file
            while((CurrentLine = FileReader.readLine()) != null) {

                FileText.add(CurrentLine);

            } // while()

            FileReader.close();

            System.out.println("File: " + FilePath + " read");

            
        } catch (Exception e) {

            System.out.println("DATA_FILE_Output - ReadFile() - error in try/catch");
            FileText.add(InvalidText);
            return FileText;

        }

        return FileText;

    }


    public LinkedList<String> ReadUser() {

        FileText = new LinkedList<>();

        try (BufferedReader FileReader = Files.newBufferedReader(FilePath);){

            String CurrentLine;

            // iterates over all lines in the file
            while((CurrentLine = FileReader.readLine()) != null) {

                // if the current line contains the id that needs to be deleted
                if (CurrentLine.contains(USERID)) {
                    
                    FileText.add(CurrentLine);

                    for (int x = 0; x < 4; x++) {
                        CurrentLine = FileReader.readLine();
                        FileText.add(CurrentLine);
                    }

                }

            } // while()

            FileReader.close();

            System.out.println("File: " + FilePath + " read");

            
        } catch (Exception e) {

            System.out.println("DATA_FILE_Output - ReadFile() - error in try/catch");
            FileText.add(InvalidText);
            return FileText;

        }

        return FileText;
    }
    
}
