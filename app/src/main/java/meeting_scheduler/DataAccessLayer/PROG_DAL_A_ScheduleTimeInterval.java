/**
 * PROG_DAL_A_ScheduleTimeInterval.java
 * 
 * Description: Object that holds both a persons and the time interval they can be scheduled on.
 * Used for scheduling purposes
 */

// Package  - DO Not Change
// ############################################################
package meeting_scheduler.DataAccessLayer;
// ############################################################

public class PROG_DAL_A_ScheduleTimeInterval {

    private final PROG_DAL_A_InfoInput  PERSON;

    private final int                   INTERVAL;


    public PROG_DAL_A_ScheduleTimeInterval(PROG_DAL_A_InfoInput person, int interval) {

        this.PERSON     = new PROG_DAL_A_InfoInput(person);

        this.INTERVAL   = interval;

    }
    
    public PROG_DAL_A_InfoInput getPerson() {
        
        return this.PERSON;

    }

    public int getInterval() {

        return this.INTERVAL;

    }
}
