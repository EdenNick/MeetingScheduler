// Package  - DO Not Change
// ############################################################
package meeting_scheduler.DataHolder;
// ############################################################

// Imports
// ############################################################
import java.time.LocalTime;
// ############################################################



public class PreferenceTime {

    // fields must remain public for Json file manipulation
    public String      TIMEPREF_Weekday;
    public LocalTime   TIMEPREF_TimeStart;
    public LocalTime   TIMEPREF_TimeEnd;


    /**
     * Default consturctor
     * Meant for use with Json files, do not alter this.
     */
    public PreferenceTime() {
        // DO NOT ADD CODE HERE
    }


    
    /**
     * Primary Constructor
     * @param day
     * @param BeginHOUR
     * @param BeginMIN
     * @param EndHOUR
     * @param EndMIN
     */
    public PreferenceTime(String INPUT_DAY, int INPUT_START_HOUR, int INPUT_START_MINUTE, int INPUT_END_HOUR, int INPUT_END_MINUTE) {
        
        this.TIMEPREF_Weekday       = INPUT_DAY;
        this.TIMEPREF_TimeStart     = LocalTime.of(INPUT_START_HOUR, INPUT_START_MINUTE);    // (hour:min)    Military Time
        this.TIMEPREF_TimeEnd       = LocalTime.of(INPUT_END_HOUR, INPUT_END_MINUTE);        // (hour:min)    Military Time

    }



    /**
     * Copy Constructor
     * @param TIMEPREF_COPY
     */
    public PreferenceTime(PreferenceTime TIMEPREF_COPY) {
        
        this.TIMEPREF_Weekday       = TIMEPREF_COPY.GetWeekDay();
        this.TIMEPREF_TimeStart     = TIMEPREF_COPY.GetStartTime();                         // (hour:min)    Military Time
        this.TIMEPREF_TimeEnd       = TIMEPREF_COPY.GetEndTime();                           // (hour:min)    Military Time
        
    }



    /**
     * IsTimePreferenceEqual
     * @param TIMEPREF_INPUT
     * @return
     */
    public boolean IsTimePreferenceEqual(PreferenceTime TIMEPREF_INPUT) {
        
        // IF - returns false if objects have a different weekday
        if (!this.TIMEPREF_Weekday.equals(TIMEPREF_INPUT.GetWeekDay()))             {
            return false;
        
        // IF - returns false if objects have different starting times 
        } else if (!this.TIMEPREF_TimeStart.equals(TIMEPREF_INPUT.GetStartTime()))  {
            return false;
        
        // IF - returns false if objects have different ending times
        } else if (!this.TIMEPREF_TimeEnd.equals(TIMEPREF_INPUT.GetEndTime()))      {
            return false;
        
        // ELSE - returns true if objects are the same
        } else {
            return true;
        }

    }



    // Return - Weekday Preference
    public String GetWeekDay() {
        return this.TIMEPREF_Weekday;
    }


    // Return - Start Time Preference
    public LocalTime GetStartTime() {
        return this.TIMEPREF_TimeStart;
    }


    // Return - End Time Preference
    public LocalTime GetEndTime() {
        return this.TIMEPREF_TimeEnd;
    }


    // Return - Start Time Hour
    public int GetStartTimeHour() {
        return this.TIMEPREF_TimeStart.getHour();
    }


    // Return - Start Time Minute
    public int GetStartTimeMin() {
        return this.TIMEPREF_TimeStart.getMinute();
    }


    // Return - End Time Hour
    public int GetEndTimeHour() {
        return this.TIMEPREF_TimeEnd.getHour();
    }


    // Return - End Time Minute
    public int GetEndTimeMin() {
        return this.TIMEPREF_TimeEnd.getMinute();
    }


    
} // TimeSet
