/**
 * PROG_BLL_InfoFileWrite.java
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

// Package  - DO Not Change
// ############################################################
package meeting_scheduler.FIleManagement;
// ############################################################

// Imports
// ############################################################
// exception
import java.io.IOException;
// util
import java.util.LinkedList;
// jackson - json manager
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

// System Messages
import meeting_scheduler.DataAccessLayer.PROG_DAL_D_SystemMessages;
import meeting_scheduler.StaticPreference.STATIC_EMPLOYEE_FullPref;
import meeting_scheduler.StaticPreference.STATIC_EMPLOYEE_TimePref;



public class MANAGEFILE_InfoFileWrite {

    public  boolean                             Input = false;
    private String                              Name;
    private int                                 Id;
    private String[]                            Days;
    private LinkedList<STATIC_EMPLOYEE_TimePref>    TimeIntervals;
    private STATIC_EMPLOYEE_FullPref                UserDataCard;
    private LinkedList<STATIC_EMPLOYEE_FullPref>    AllDataCards;

    private MANAGEFILE_JsonOutput              JsonfileManager = new MANAGEFILE_JsonOutput();

    
    public MANAGEFILE_InfoFileWrite() {

        AllDataCards = new LinkedList<>();
    }

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
    public int CheckUserInfo(String name, int id, String[] days, LinkedList<STATIC_EMPLOYEE_TimePref> intervals) {


        for (STATIC_EMPLOYEE_FullPref Person : AllDataCards) {
            if (id == Person.GetIdent()) {
                System.out.println(PROG_DAL_D_SystemMessages.ERROR_CheckUserInfoIDInput);
                return 1;
            }
        }


        // Check Name to ensure it contains at least one character
        if (!name.isBlank()) {
            this.Name = name;
        } else {
            System.out.println(PROG_DAL_D_SystemMessages.ERROR_CheckUserInfoInvalidName);
            return 1;
        }



        // Check Id to ensure it is a valid positive number or the default id (-1)
        if (id >= -1) {
            this.Id = id;
        } else {
            System.out.println(PROG_DAL_D_SystemMessages.ERROR_CheckUserInfoInvalidID);
            return 1;
        }



        // Check Days to ensure each array position contains a string that isn't blank
        for (int day = 0; day < days.length; day++) {
            if (!days[day].isBlank()) {

            } else {
                System.out.println(PROG_DAL_D_SystemMessages.ERROR_CheckUserInfoInvalidDay + day);
                return 1;
            }
        }

        this.Days = days.clone();


        // Check TimeIntervals
        // - check each day is valid
        // - check each prefered hour is within valid time 00 - 24
        // - check each prefered min is within valid time 00 - 59
        for (int intervalPosition = 0; intervalPosition < intervals.size(); intervalPosition++) {

            String  Day         = intervals.get(intervalPosition).GetWeekDay();
            int     BeginHour   = intervals.get(intervalPosition).GetStartTimeHour();
            int     BeginMIN    = intervals.get(intervalPosition).GetStartTimeMin();
            int     EndHour     = intervals.get(intervalPosition).GetEndTimeHour();
            int     EndMin      = intervals.get(intervalPosition).GetEndTimeMin();


            if (Day.isBlank()) {
                System.out.println(PROG_DAL_D_SystemMessages.ERROR_CheckUserInfoInvalidInterval + intervalPosition);
                return 1;
            }

            if ((BeginHour < 0) || (BeginHour > 24)) {
                System.out.println(PROG_DAL_D_SystemMessages.ERROR_CheckUserInfoInvalidStartHour + intervalPosition);
                return 1;
            }

            if ((BeginMIN < 0)  || (BeginHour > 60)) {
                System.out.println(PROG_DAL_D_SystemMessages.ERROR_CheckUserInfoInvalidStartMin + intervalPosition);
                return 1;
            }

            if ((EndHour < 0)   || (EndHour > 24)) {
                System.out.println(PROG_DAL_D_SystemMessages.ERROR_CheckUserInfoInvalidEndHour + intervalPosition);
                return 1;
            }

            if ((EndMin < 0)    || (EndMin > 60)) {
                System.out.println(PROG_DAL_D_SystemMessages.ERROR_CheckUserInfoInvalidEndmin + intervalPosition);
                return 1;
            }


        }

        TimeIntervals = new LinkedList<>(intervals);

        System.out.println(PROG_DAL_D_SystemMessages.PASS_CheckUserInfoValidInputs);
        
        // Assign Data
        //Create USer Data card
        this.UserDataCard = new STATIC_EMPLOYEE_FullPref(this.Name , this.Id, this.Days, this.TimeIntervals);
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

        JsonfileManager.DiscardCard();

        AllDataCards = new LinkedList<>();

        return 0;
    }

    
}
