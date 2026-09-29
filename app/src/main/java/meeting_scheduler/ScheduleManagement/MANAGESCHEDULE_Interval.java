// Package  - DO Not Change
// ############################################################
package meeting_scheduler.ScheduleManagement;
// ############################################################

// IMPORTS
// ############################################################
import meeting_scheduler.EmployeePreferences.PREF_EMPLOYEE_FullPref;
// ############################################################



public class MANAGESCHEDULE_Interval {

    private final PREF_EMPLOYEE_FullPref  PERSON;

    private final int                   INTERVAL;


    public MANAGESCHEDULE_Interval(PREF_EMPLOYEE_FullPref person, int interval) {

        this.PERSON     = new PREF_EMPLOYEE_FullPref(person);

        this.INTERVAL   = interval;

    }
    
    public PREF_EMPLOYEE_FullPref getPerson() {
        
        return this.PERSON;

    }

    public int getInterval() {

        return this.INTERVAL;

    }
}
