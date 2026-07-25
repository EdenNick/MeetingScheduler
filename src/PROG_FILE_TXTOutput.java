/**
 * DataFileOutput.java
 * 
 * Description: Retrieves data from files (read-only).
 * No locks or preventative measures need to be implemented as all methods are read only operations.
 */

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;


public class PROG_FILE_TXTOutput {

    private String              FILENAME    = "";
    private String              InvalidText = "File could not be read";
    private LinkedList<String>  USERID;
    private LinkedList<String>  FileText;
    private Path                FilePath;


    /**
     * Constructor
     * used when the full contents of a single file Need to be read;
     * @param FileToRead
     */
    public PROG_FILE_TXTOutput(String FileToRead) {

        this.FILENAME = FileToRead;
        this.FileText = new LinkedList<String>();
        this.FileText.add(FILENAME);
        this.FilePath = Paths.get(FILENAME);

    }


    /**
     * Constructor
     * Description: used when a set of ids need to be read
     * @param FileToRead
     * @param ID
     */
    public PROG_FILE_TXTOutput(String FileToRead, LinkedList<String> ID) {

        this.FILENAME = FileToRead;
        this.USERID   = new LinkedList<String>(ID);
        this.FileText.add(FILENAME);
        this.FilePath = Paths.get(FILENAME);

    }


    /**
     * UpdateFileOutput
     * Used to update the file being read
     */
    public void UpdateFileOutput(String FileToRead, LinkedList<String> ID) {
        this.FILENAME = FileToRead;
        this.USERID   = new LinkedList<String>(ID);
        this.FileText.add(FILENAME);
        this.FilePath = Paths.get(FILENAME);
    }



    /**
     * reads the specified file as a whole.
     */
    public LinkedList<String> ReadFile() {

        // FileText = new LinkedList<>();


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

        // FileText = new LinkedList<>();

        try (BufferedReader FileReader = Files.newBufferedReader(FilePath);){

            String CurrentLine;

            // iterates over all lines in the file
            while((CurrentLine = FileReader.readLine()) != null) {

                //if a line contains a user ID
                for (String ID : USERID ) {
                    if (CurrentLine.contains(ID)) {
                        
                        FileText.add(CurrentLine);

                        for (int x = 0; x < 4; x++) {
                            CurrentLine = FileReader.readLine();
                            FileText.add(CurrentLine);
                        }

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
