// Package  - DO Not Change
// ############################################################
package meeting_scheduler.DataHolder;
// ############################################################

// Imports
// ############################################################
import java.util.LinkedList;
import meeting_scheduler.global;
import meeting_scheduler.SystemInfoManager;
// ############################################################



public class PreferenceFull {

    // fields must remain public in order for JSON file retrieval and write to work
    public boolean                  EMPLOYEE_Delete;
    public String                   EMPLOYEE_Name;
    public int                      EMPLOYEE_Ident;
    public String[]                 EMPLOYEE_Days = new String[7];
    public PreferenceTime[] EMPLOYEE_Intervals;



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
    public PreferenceFull(boolean INPUT_DELETE, String INPUT_NAME, int INPUT_IDENT, String[] INPUT_DAYS, LinkedList<PreferenceTime> INPUT_INTERVALS) {
        this.EMPLOYEE_Delete    = INPUT_DELETE;
        this.EMPLOYEE_Name      = INPUT_NAME;
        this.EMPLOYEE_Ident     = INPUT_IDENT;
        this.EMPLOYEE_Days      = CheckOrder(INPUT_DAYS);
        this.EMPLOYEE_Intervals = INPUT_INTERVALS.toArray(new PreferenceTime[0]);
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



    /**
     * CheckOrder
     * Description: checks to ensure that submitted weekdays are both in the correct order and formatted correctly
     * @param Weekdays
     * @return
     */
    private String[] CheckOrder(String[] Weekdays) {

        String[] Output     = new String[7];
        String[] Weekday    = SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_WEEKARRAY(global.WEEKTYPE.SHORT);
        int      weekLength = SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_INT(global.BASICVALUESINT.INT, 0);

        for (int Position_day = 0; Position_day < weekLength; Position_day++) {
            if (Weekdays[Position_day].equals(Weekday[Position_day])) {
                Output[Position_day] = Weekday[Position_day];
            }
        }

        return Output.clone();
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
        return this.EMPLOYEE_Days.clone();
    }

    // Return - employee interval preferences
    public PreferenceTime[] GetIntervals() {
        return this.EMPLOYEE_Intervals.clone();
    }
    
}
