package meeting_scheduler.DataAccessLayer;
/**
 * PROG_INFO_Schedule
 * Description: Object that holds the calcualted scheduleinfo
 */

import java.util.LinkedList;

public class PROG_DAL_A_Schedule {

    public String              WeekDay;            // Holds The Day the TimeInterval exists on
    public PROG_DAL_A_TimeInput Interval;           // Holds a specific interval for that day
    public LinkedList<String>  USERIDs;            // Total ammount of people that can meet for that interval
    public boolean             Schedule = false;   // true if all the people that are in USERIDs are all the people the user wants scheduled, false otherwise.


    public PROG_DAL_A_Schedule(String day, PROG_DAL_A_TimeInput times, LinkedList<String> IDs, boolean schedule) {

        this.WeekDay    = day;
        this.Interval   = new PROG_DAL_A_TimeInput(times);
        this.USERIDs    = new LinkedList<String>(IDs);
        this.Schedule   = schedule;

    }
    
}



