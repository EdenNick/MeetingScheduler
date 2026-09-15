/**
 * PROG_DAL_A_ScheduleTimeInterval.java
 * 
 * Description: Object that holds both a persons and the time interval they can be scheduled on.
 * Used for scheduling purposes
 */

// Package  - DO Not Change
// ############################################################
package meeting_scheduler.ScheduleManagement;
// ############################################################

import meeting_scheduler.EmployeePreferences.STATIC_EMPLOYEE_FullPref;

public class MANAGESCHEDULE_Interval {

    private final STATIC_EMPLOYEE_FullPref  PERSON;

    private final int                   INTERVAL;


    public MANAGESCHEDULE_Interval(STATIC_EMPLOYEE_FullPref person, int interval) {

        this.PERSON     = new STATIC_EMPLOYEE_FullPref(person);

        this.INTERVAL   = interval;

    }
    
    public STATIC_EMPLOYEE_FullPref getPerson() {
        
        return this.PERSON;

    }

    public int getInterval() {

        return this.INTERVAL;

    }
}
