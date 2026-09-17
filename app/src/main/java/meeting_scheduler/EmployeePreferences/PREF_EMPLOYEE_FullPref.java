/** 
 * PROG_DAL_A_InfoInput.java
 * 
 * Description: Stores static info to be applyed to different files.
 * Should only be concurrently.
 */

// Package  - DO Not Change
// ############################################################
package meeting_scheduler.EmployeePreferences;
// ############################################################



// Imports
// ############################################################
// Util
import java.util.LinkedList;
// ############################################################


public class PREF_EMPLOYEE_FullPref {

    // fields must remain public in order for JSON file retrieval and write to work
    public boolean                             EMPLOYEE_Delete;
    public String                              EMPLOYEE_Name;
    public int                                 EMPLOYEE_Ident;
    public String[]                            EMPLOYEE_Days;
    public LinkedList<PREF_EMPLOYEE_TimePref>  EMPLOYEE_Intervals;



    /**
     * Default Constructor
     * used for json operations
     */
    public PREF_EMPLOYEE_FullPref(){
        // com.fasterxml.jackson requires a no argument constructor - Do NOT put anything here
    }

    /**
     * Main Constructor
     * @param EMPLOYEE_Delete
     * @param EMPLOYEE_Name
     * @param EMPLOYEE_Ident
     * @param EMPLOYEE_Days
     * @param EMPLOYEE_Intervals
     */
    public PREF_EMPLOYEE_FullPref(boolean INPUT_DELETE, String INPUT_NAME, int INPUT_IDENT, String[] INPUT_DAYS, LinkedList<PREF_EMPLOYEE_TimePref> INPUT_INTERVALS) {
        this.EMPLOYEE_Delete    = INPUT_DELETE;
        this.EMPLOYEE_Name      = INPUT_NAME;
        this.EMPLOYEE_Ident     = INPUT_IDENT;
        this.EMPLOYEE_Days      = INPUT_DAYS.clone();
        this.EMPLOYEE_Intervals = new LinkedList<>(INPUT_INTERVALS);
    }

    /** 
     * Copy Constructor
     * @param FULLPREF_COPY
     */
    public PREF_EMPLOYEE_FullPref(PREF_EMPLOYEE_FullPref FULLPREF_COPY) {
        this.EMPLOYEE_Delete    = FULLPREF_COPY.GetStatus();
        this.EMPLOYEE_Name      = FULLPREF_COPY.GetName();
        this.EMPLOYEE_Ident     = FULLPREF_COPY.GetIdent();
        this.EMPLOYEE_Days      = FULLPREF_COPY.GetDays();
        this.EMPLOYEE_Intervals = FULLPREF_COPY.GetIntervals();
    }

    // Return - preference delete status
    public boolean  GetStatus() {
        return this.EMPLOYEE_Delete;
    }

    // Return - employee name
    public String   GetName() {
        return this.EMPLOYEE_Name;
    }

    // Return - employee identification
    public int      GetIdent() {
        return this.EMPLOYEE_Ident;
    }

    // Return - employee day preferences
    public String[] GetDays() {
        return this.EMPLOYEE_Days;
    }

    // Return - employee interval preferences
    public LinkedList<PREF_EMPLOYEE_TimePref> GetIntervals() {
        return this.EMPLOYEE_Intervals;
    }
    
}
