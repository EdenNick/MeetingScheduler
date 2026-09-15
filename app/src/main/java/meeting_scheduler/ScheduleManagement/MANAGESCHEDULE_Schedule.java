/**
 * PROG_DAL_A_Schedule.java
 * 
 * Description: Object that holds the calcualted scheduleinfo.
 */

// Package  - DO Not Change
// ############################################################
package meeting_scheduler.ScheduleManagement;
// ############################################################

// Imports
// ############################################################
// Util
import java.util.LinkedList;
// ############################################################

import meeting_scheduler.StaticPreference.STATIC_EMPLOYEE_TimePref;



public class MANAGESCHEDULE_Schedule {

    public String               WeekDay;            // Holds The Day the TimeInterval exists on
    public STATIC_EMPLOYEE_TimePref Interval;           // Holds a specific interval for that day
    public LinkedList<String>   USERIDs;            // Total ammount of people that can meet for that interval
    public boolean              Schedule = false;   // true if all the people that are in USERIDs are all the people the user wants scheduled, false otherwise.


    public MANAGESCHEDULE_Schedule(String day, STATIC_EMPLOYEE_TimePref times, LinkedList<String> IDs, boolean schedule) {

        this.WeekDay    = day;
        this.Interval   = new STATIC_EMPLOYEE_TimePref(times);
        this.USERIDs    = new LinkedList<String>(IDs);
        this.Schedule   = schedule;

    }
    
}



