// Package  - DO Not Change
// ############################################################
package meeting_scheduler.DataHolder;
// ############################################################

// Imports
// ############################################################
import java.util.LinkedList;
import com.fasterxml.jackson.annotation.JsonProperty;
import meeting_scheduler.SystemInfoManager;
import meeting_scheduler.global;
// ############################################################



public class PreferenceFull {

    /**
     * PreferenceFull
     * 
     * This Object acts as the preference datacard for an individual.
     * It stores what days they prefer as well as each specific timeframe on those days
     * 
     * 
     */

    // Constructor Fields - should always remain private to prevent overwrite
    // ############################################################
    @JsonProperty("EMPLOYEE_Delete")                // True/false if the preference should be removed from the file
    private boolean             EMPLOYEE_Delete;    //
    @JsonProperty("EMPLOYEE_Name")                  // Name of the employee
    private String              EMPLOYEE_Name;      //
    @JsonProperty("EMPLOYEE_Ident")                 // ID value of the employee - should be a unique positive value
    private int                 EMPLOYEE_Ident;     //
    @JsonProperty("EMPLOYEE_Days")                  // Days a preference exists on, defaults as null
    private String[]            EMPLOYEE_Days;      //
    @JsonProperty("EMPLOYEE_Intervals")             // Each preference tied to an employee , each object contains the weekday, start time and end time.
    private PreferenceTime[]    EMPLOYEE_Intervals; //
    // ############################################################

    // Class Variables  - used in class methods
    // ############################################################
    private static final String[]   WEEKDAY     = SystemInfoManager.GET_SYSTEM_VALUE_WEEK(global.WEEKTYPE.SHORT);
    private static final int        WEEK_LEN    = WEEKDAY.length;
    // ############################################################





    // Constructor Methods
    // ############################################################

    /**
     * Default Constructor
     * used for json operations
     */
    public PreferenceFull(){
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
    public PreferenceFull(boolean INPUT_DELETE, String INPUT_NAME, int INPUT_IDENT, String[] INPUT_DAYS, LinkedList<PreferenceTime> newPreference_times) {
        this.EMPLOYEE_Delete    = INPUT_DELETE;
        this.EMPLOYEE_Name      = INPUT_NAME;
        this.EMPLOYEE_Ident     = INPUT_IDENT;
        this.EMPLOYEE_Days      = CheckOrder(INPUT_DAYS);
        this.EMPLOYEE_Intervals = newPreference_times.toArray(new PreferenceTime[0]);
    }

    /** 
     * Copy Constructor
     * @param FULLPREF_COPY
     */
    public PreferenceFull(PreferenceFull FULLPREF_COPY) {
        this.EMPLOYEE_Delete    = FULLPREF_COPY.GetStatus();
        this.EMPLOYEE_Name      = FULLPREF_COPY.GetName();
        this.EMPLOYEE_Ident     = FULLPREF_COPY.GetIdent();
        this.EMPLOYEE_Days      = FULLPREF_COPY.GetDays();
        this.EMPLOYEE_Intervals = FULLPREF_COPY.GetIntervals();
    }
    // ############################################################





    // Parameter Checks - ensure correct input Format
    // ############################################################
    /**
     * CheckOrder
     * Description: checks to ensure that submitted weekdays are both in the correct order and formatted correctly
     * @param Weekdays
     * @return
     */
    private String[] CheckOrder(String[] Weekdays) {

        // String[] to be returned
        String[] Output = new String[7];

        // Iterate over everyDay of the week
        for (int Position_day = 0; Position_day < PreferenceFull.WEEK_LEN; Position_day++) {
            
            // Check if the value stored at Weekdays[Position_day] is an actual day and not a null value
            if (Weekdays[Position_day] != null) {

                // if the day exists and it is in the correct format (same as WEEKDAY[Pos], save it to Output[]])
                if (Weekdays[Position_day].equals(PreferenceFull.WEEKDAY[Position_day])) {

                    Output[Position_day] = PreferenceFull.WEEKDAY[Position_day];

                } // if (Weekdays[Position_day].equals(PreferenceFull.WEEKDAY[Position_day]))

            } // if (Weekdays[Position_day] != null)

        } // for (int Position_day = 0; Position_day < weekLength; Position_day++) 

        // return a clone of Output to ensure no accidental edits
        return Output.clone();
    }
    // ############################################################





    // Get methods, used to ensure safe access of the object
    // ############################################################
    // Return - preference delete status
    public boolean  GetStatus() {
        return this.EMPLOYEE_Delete;
    }

    // Return - Employee name
    public String   GetName() {
        return this.EMPLOYEE_Name;
    }

    // Return - Employee identification
    public int      GetIdent() {
        return this.EMPLOYEE_Ident;
    }

    // Return - Employee day preferences
    public String[] GetDays() {
        return this.EMPLOYEE_Days.clone();
    }

    // Return - Employee interval preferences
    public PreferenceTime[] GetIntervals() {
        return this.EMPLOYEE_Intervals.clone();
    }
    // ############################################################
    




} // PreferenceFull {}