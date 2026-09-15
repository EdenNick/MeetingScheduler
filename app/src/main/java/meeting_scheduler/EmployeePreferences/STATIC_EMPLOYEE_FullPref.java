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


public class STATIC_EMPLOYEE_FullPref {

    private String                                  EMPLOYEE_Name;
    private int                                     EMPLOYEE_Ident;
    private String[]                                EMPLOYEE_Days;
    private LinkedList<STATIC_EMPLOYEE_TimePref>    EMPLOYEE_Intervals;



    /**
     * Default Constructor
     * used for json operations
     */
    public STATIC_EMPLOYEE_FullPref(){
        // com.fasterxml.jackson requires a no argument constructor - Do NOT put anything here
    }

    /**
     * Main Constructor
     * @param name
     * @param ID
     * @param week
     * @param times
     */
    public STATIC_EMPLOYEE_FullPref(String INPUT_NAME, int INPUT_IDENT, String[] INPUT_DAYS, LinkedList<STATIC_EMPLOYEE_TimePref> INPUT_INTERVALS) {
        this.EMPLOYEE_Name      = INPUT_NAME;
        this.EMPLOYEE_Ident     = INPUT_IDENT;
        this.EMPLOYEE_Days      = INPUT_DAYS.clone();
        this.EMPLOYEE_Intervals = new LinkedList<>(INPUT_INTERVALS);
    }

    /** 
     * Copy Constructor
     * @param copy
     */
    public STATIC_EMPLOYEE_FullPref(STATIC_EMPLOYEE_FullPref FULLPREF_COPY) {
        this.EMPLOYEE_Name      = FULLPREF_COPY.GetName();
        this.EMPLOYEE_Ident     = FULLPREF_COPY.GetIdent();
        this.EMPLOYEE_Days      = FULLPREF_COPY.GetDays();
        this.EMPLOYEE_Intervals = FULLPREF_COPY.GetIntervals();
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
    public LinkedList<STATIC_EMPLOYEE_TimePref> GetIntervals() {
        return this.EMPLOYEE_Intervals;
    }
    
}
