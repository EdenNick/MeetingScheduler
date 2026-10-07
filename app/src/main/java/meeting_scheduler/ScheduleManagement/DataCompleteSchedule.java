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

    //TODO: change datatypes

    public String                   WeekDay;            // Holds The Day the TimeInterval exists on
    public PreferenceTime   Interval;           // Holds a specific interval for that day
    public LinkedList<String>       USERIDs;            // Total ammount of people that can meet for that interval
    public boolean                  Schedule = false;   // true if all the people that are in USERIDs are all the people the user wants scheduled, false otherwise.


    public DataCompleteSchedule(String day, PreferenceTime times, LinkedList<String> IDs, boolean schedule) {

        this.WeekDay    = day;
        this.Interval   = new PreferenceTime(times);
        this.USERIDs    = new LinkedList<String>(IDs);
        this.Schedule   = schedule;

    }
    
}



