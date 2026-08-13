/**
 * PROG_DAL_C_TXTOutput.java
 * 
 * Description: Retrieves data from files (read-only).
 * No locks or preventative measures need to be implemented as all methods are read only operations.
 */

// Package  - DO Not Change
// ############################################################
package meeting_scheduler.DataAccessLayer;
// ############################################################

// Imports
// ############################################################
// reader
import java.io.BufferedReader;
import java.io.IOException;
// stream
import java.io.InputStream;
import java.io.InputStreamReader;
// util
import java.util.LinkedList;
// ############################################################

public class PROG_DAL_C_TXTOutput {

    private String              FILENAME    = "";
    private String              InvalidText = " File could not be read";
    private LinkedList<String>  USERID;
    private LinkedList<String>  FileText;
    // private Path                FilePath;





    /**
     * Constructor
     * used when the full contents of a single file Need to be read;
     * @param FileToRead
     */
    public PROG_DAL_C_TXTOutput(String FileToRead) {

        this.FILENAME = FileToRead;
        this.FileText = new LinkedList<String>();
        this.FileText.add(FILENAME);
        // this.FilePath = Paths.get(FILENAME);

    }





    /**
     * Constructor
     * Description: used when a set of ids need to be read
     * @param FileToRead
     * @param ID
     */
    public PROG_DAL_C_TXTOutput(String FileToRead, LinkedList<String> ID) {

        this.FILENAME = FileToRead;
        this.USERID   = new LinkedList<String>(ID);
        this.FileText.add(FILENAME);
        // this.FilePath = Paths.get(FILENAME);

    }





    /**
     * UpdateFileOutput
     * Used to update the file being read
     */
    public void UpdateFileOutput(String FileToRead, LinkedList<String> ID) {

        this.FILENAME = FileToRead;
        this.USERID   = new LinkedList<String>(ID);
        this.FileText.add(FILENAME);
        // this.FilePath = Paths.get(FILENAME);

    }





    /**
     * ReadFile()
     * Description: Reads the specified file as a whole
     * returning a linked list of each line within it.
     */
    public LinkedList<String> ReadFile() {

        // FileText = new LinkedList<>();


        try (InputStream input = getClass().getResourceAsStream(FILENAME);
            BufferedReader FileReader = new BufferedReader(new InputStreamReader(input))) {


            String CurrentLine;

            // iterates over all lines in the file
            while((CurrentLine = FileReader.readLine()) != null) {

                FileText.add(CurrentLine);

            } // while()

            FileReader.close();

            //System.out.println("File: " + FilePath + " read");

            
        } catch (IOException e) {

            System.out.println("ERROR - PROG_FILE_TXTOutput - ReadFile() - try/catch error");
            FileText.add(InvalidText);
            return FileText;

        }

        return FileText;

    }





    /**
     * ReadUser()
     * Description: reads specified user or users using the
     * local linkedlist<string> USERID
     */
    public LinkedList<String> ReadUser() {

        // FileText = new LinkedList<>();

        try (InputStream input = getClass().getResourceAsStream(FILENAME);
            BufferedReader FileReader = new BufferedReader(new InputStreamReader(input))) {

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

            // System.out.println("File: " + FilePath + " read");

            
        } catch (Exception e) {

            System.out.println("ERROR - PROG_FILE_TXTOutput - ReadUser() - try/catch error");
            FileText.add(InvalidText);
            return FileText;

        }

        return FileText;
    }


    
}
