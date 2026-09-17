/**
 * PROG_DAL_A_TimeInput.java
 * 
 * Description: Object that stores a single set of clock times meant to be added to a linked list.
 * (Format: Begin -> End)
 */

// Package  - DO Not Change
// ############################################################
package meeting_scheduler.EmployeePreferences;
// ############################################################

// Imports
// ############################################################
// time
import java.time.LocalTime;
// ############################################################



public class PREF_EMPLOYEE_TimePref {

    // fields must remain public for Json file manipulation
    public String      TIMEPREF_Weekday;
    public LocalTime   TIMEPREF_TimeStart;
    public LocalTime   TIMEPREF_TimeEnd;


    /**
     * Default consturctor
     * Meant for use with Json files, do not alter this.
     */
    public PREF_EMPLOYEE_TimePref() {
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
    public PREF_EMPLOYEE_TimePref(String INPUT_DAY, int INPUT_START_HOUR, int INPUT_START_MINUTE, int INPUT_END_HOUR, int INPUT_END_MINUTE) {
        
        this.TIMEPREF_Weekday       = INPUT_DAY;
        this.TIMEPREF_TimeStart     = LocalTime.of(INPUT_START_HOUR, INPUT_START_MINUTE);    // (hour:min)    Military Time
        this.TIMEPREF_TimeEnd       = LocalTime.of(INPUT_END_HOUR, INPUT_END_MINUTE);        // (hour:min)    Military Time

    }



    /**
     * Copy Constructor
     * @param TIMEPREF_COPY
     */
    public PREF_EMPLOYEE_TimePref(PREF_EMPLOYEE_TimePref TIMEPREF_COPY) {
        
        this.TIMEPREF_Weekday       = TIMEPREF_COPY.GetWeekDay();
        this.TIMEPREF_TimeStart     = TIMEPREF_COPY.GetStartTime();                         // (hour:min)    Military Time
        this.TIMEPREF_TimeEnd       = TIMEPREF_COPY.GetEndTime();                           // (hour:min)    Military Time
        
    }



    /**
     * IsTimePreferenceEqual
     * @param TIMEPREF_INPUT
     * @return
     */
    public boolean IsTimePreferenceEqual(PREF_EMPLOYEE_TimePref TIMEPREF_INPUT) {
        
        // IF - returns false if objects have a different weekday
        if (!this.TIMEPREF_Weekday.equals(TIMEPREF_INPUT.GetWeekDay()))             {
            System.out.println("weekday");
            return false;
        
        // IF - returns false if objects have different starting times 
        } else if (!this.TIMEPREF_TimeStart.equals(TIMEPREF_INPUT.GetStartTime()))  {
            System.out.println("beginhour");
            return false;
        
        // IF - returns false if objects have different ending times
        } else if (!this.TIMEPREF_TimeEnd.equals(TIMEPREF_INPUT.GetEndTime()))      {
            System.out.println("endhour");
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





    /**
     * TimeConversion()
     * Description: Converts time between standardizations
     * As a default time is stored as military standard time which ranges from 0-24, witout using AM/PM 
     * to signify which hour of the day it is.
     * This method is used to convert the time to standard time which uses AM/PM designations
     */
    public int TEST_TimeConversion() { //DATA_USER_InfoInput RetrieveUserInfo

        System.out.println("Standard Time Input:");


        // Start Interval   (hour:min)
        int BEGINHour   = this.TIMEPREF_TimeStart.getHour();
        int BEGINMin    = this.TIMEPREF_TimeStart.getMinute();

        // End Interval     (hour:min)
        int ENDHour     = this.TIMEPREF_TimeEnd.getHour();
        int ENDMin      = this.TIMEPREF_TimeEnd.getMinute();



        String StartInterval    = "AM";
        String EndInterval      = "AM";

        // Converts the beginning hour interval to PM if needed, otherwise it defaults to AM
        if ((12 < BEGINHour) && (BEGINHour < 24)) {

            BEGINHour       = BEGINHour - 12;
            StartInterval   = "PM";

        }
            
        // Converts the end hour interval to PM if needed, otherwise it defaults to AM
        if ((12 < ENDHour) && (ENDHour < 24)) {

            ENDHour         = ENDHour - 12;
            EndInterval     = "PM";

        }

        if (BEGINHour == 12) {
            StartInterval = "PM";
        }

        if (ENDHour == 12) {
            EndInterval = "PM";
        }

        /**
         * Print Format for each index position:
         * Standard Time Input:
         * Day: (day)
         * Interval (Index) : (Hour:Min) (AM/PM) - (Hour:Min) (AM/PM)
         */
        System.out.println("Day: " + this.TIMEPREF_Weekday);
        System.out.print("Interval : (" + BEGINHour + ":" + BEGINMin + ") " + StartInterval);
        System.out.println(" - (" + ENDHour + ":" + ENDMin + ") " + EndInterval);
            
        
        return 0;
    }
    
} // TimeSet
