package meeting_scheduler.ProgramFiles;
/**
 * DATA_USER_InfoFileWrite.java
 * 
 * Description: Used to recieve user inputs and write user info, Id, name, etc
 * into the proper file.
 * Performs vital operation of verifying data exists and is usable.
 * 
 * Format ->
 * 
 * ID:
 * Name:
 * Days:
 * Times
 * "####################"
 */

import java.io.IOException;
import java.util.LinkedList;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

public class PROG_INFO_InfoFileWrite {

    public  boolean                             Input = false;
    public  String                              Name;
    public  int                                 Id;
    public  String[]                            Days = new String[6];
    public  LinkedList<PROG_INFO_TimeInput>     TimeIntervals;
    private PROG_INFO_InfoInput                 UserDataCard;
    private LinkedList<PROG_INFO_InfoInput>     AllDataCards;

    private PROG_FILE_JSONManager               JsonfileManager = new PROG_FILE_JSONManager();


    /**
     * CheckUserInfo()
     * Description Checks User input
     * This method is used to check and store the User inputs from the UI ensuring they are valid and can be used.
     * Should be called once per object given to WriteUserInfo()
     * @param name
     * @param id
     * @param days
     * @param intervals
     * @return
     */
    public int CheckUserInfo(String name, int id, String[] days, LinkedList<PROG_INFO_TimeInput> intervals) {


        for (PROG_INFO_InfoInput Person : AllDataCards) {
            if (id == Person.EmployeeID) {
                System.out.println("INVALID INPUT - PROG_INFO_InfoFileWrite - ReceiveUserInfo() - id already input");
                return 0;
            }
        }


        // Check Name to ensure it contains at least one character
        if (!name.isBlank()) {
            Name = name;
        } else {
            System.out.println("Error - PROG_INFO_InfoFileWrite - ReceiveUserInfo() - invalid name, blank or white space");
            return 1;
        }



        // Check Id to ensure it is a valid positive number or the default id (-1)
        if (id >= -1) {
            Id = id;
        } else {
            System.out.println("Error - PROG_INFO_InfoFileWrite - ReceiveUserInfo() - invalid id, less than -1");
            return 1;
        }



        // Check Days to ensure each array position contains a string that isn't blank
        for (int day = 0; day < 6; day++) {
            if (!days[day].isBlank()) {

            } else {
                System.out.println("Error - PROG_INFO_InfoFileWrite - ReceiveUserInfo() - invalid Day, String blank at position: " + day);
                return 1;
            }
        }



        // Check TimeIntervals
        // - check each day is valid
        // - check each prefered hour is within valid time 00 - 24
        // - check each prefered min is within valid time 00 - 59
        for (int intervalPosition = 0; intervalPosition < intervals.size(); intervalPosition++) {

            String  Day         = intervals.get(intervalPosition).WeekDay;
            int     BeginHour   = intervals.get(intervalPosition).PreferedHourBEGIN.getHour();
            int     BeginMIN    = intervals.get(intervalPosition).PreferedHourBEGIN.getMinute();
            int     EndHour     = intervals.get(intervalPosition).PreferedHourEND.getHour();
            int     EndMin      = intervals.get(intervalPosition).PreferedHourEND.getMinute();


            if (Day.isBlank()) {
                System.out.println("ERROR - PROG_INFO_InfoFileWrite - ReceiveUserInfo() - invalid interval, missing day at position: " + intervalPosition);
                return 1;
            }

            if (!(-1 < BeginHour) || !(BeginHour < 24)) {
                System.out.println("ERROR - PROG_INFO_InfoFileWrite - ReceiveUserInfo() - invalid Begin Hour, Hour above or below interval limit at position: " + intervalPosition);
                return 1;
            }

            if (!(-1 < BeginMIN) || !(BeginHour < 60)) {
                System.out.println("ERROR - PROG_INFO_InfoFileWrite - ReceiveUserInfo() - invalid Begin Min, Min above or below interval limit at position: " + intervalPosition);
                return 1;
            }

            if (!(-1 < EndHour) || !(EndHour < 24)) {
                System.out.println("ERROR - PROG_INFO_InfoFileWrite - ReceiveUserInfo() - invalid End Hour, Hour above or below interval limit at position: " + intervalPosition);
                return 1;
            }

            if (!(-1 < EndMin) || !(EndMin < 60)) {
                System.out.println("ERROR - PROG_INFO_InfoFileWrite - ReceiveUserInfo() - invalid End Min, Min above or below interval limit at position: " + intervalPosition);
                return 1;
            }


        }

        System.out.println("Success - ReceiveUserInfo - All inputs valid");
        
        // Assign Data
        //Create USer Data card
        this.UserDataCard = new PROG_INFO_InfoInput(Name, Id, Days, TimeIntervals);
        // Assign Card to the DataCard list
        this.AllDataCards.add(UserDataCard);
        return 0;

    } // ReceiveUserInfo()





    /**
     * WriteUserInfo
     * Writes User Info
     * Adds the submitted info into the Default file containing User info Cards
     * in the correct format.
     *  * Format -> 
     * ID:
     * Name:
     * Days:
     * Times
     * "####################"
     * @throws IOException 
     * @throws DatabindException 
     * @throws StreamReadException 
     */
    public int WriteUserInfo() throws StreamReadException, DatabindException, IOException {

        JsonfileManager.SetUserCards(AllDataCards);

        JsonfileManager.SetToFile();

        return 0;
    }

    
}
