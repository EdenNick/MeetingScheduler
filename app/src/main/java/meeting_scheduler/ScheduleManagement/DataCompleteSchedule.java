// Package  - DO Not Change
// ############################################################
package meeting_scheduler.ScheduleManagement;
// ############################################################

// Imports
// ############################################################
import java.util.LinkedList;

import meeting_scheduler.DataHolder.PreferenceTime;
// ############################################################


public class DataCompleteSchedule {

    private String              WeekDay;            // Holds The Day the TimeInterval exists on
    private PreferenceTime      Interval;           // Holds a specific interval for that day
    private LinkedList<String>  USERIDs;            // Total ammount of people that can meet for that interval
    private boolean             Schedule = false;   // true if all the people that are in USERIDs are all the people the user wants scheduled, false otherwise.


    public DataCompleteSchedule(String day, PreferenceTime times, LinkedList<String> IDs, boolean schedule) {

        this.WeekDay    = day;
        this.Interval   = new PreferenceTime(times);
        this.USERIDs    = new LinkedList<>(IDs);
        this.Schedule   = schedule;

    }
    
    public String GetWeekday() {
        return this.WeekDay;
    }

    public PreferenceTime GetInterval() {
        return this.Interval;
    }

    public LinkedList<String> GetUserIds() {
        return this.USERIDs;
    }

    public boolean GetFullScheduleState() {
        return this.Schedule;
    }
}



