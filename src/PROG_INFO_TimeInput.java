/**
 * DATA_USER_TimeInput.java
 * 
 * Description: Object that stores a single set of clock times meant to be added to a linked list.
 * (Format: Begin -> End)
 */

import java.time.LocalTime;

public class PROG_INFO_TimeInput {

    String      WeekDay;
    LocalTime   PreferedHourBEGIN;
    LocalTime   PreferedHourEND;

    public PROG_INFO_TimeInput(String day, int BeginHOUR, int BeginMIN, int EndHOUR, int EndMIN) {
        
        //stores the day of the prefered time;
        this.WeekDay = day;

        // stores the beginning of the prefered time interval (hour:min)    Military Time
        this.PreferedHourBEGIN = LocalTime.of(BeginHOUR, BeginMIN);

        //stores the ed of the prefered time interval (hour:min)            Military Time
        this.PreferedHourEND = LocalTime.of(EndHOUR, EndMIN);
    }

    /**
     * Converts time between standardizations
     * As a default time is stored as military standard time which ranges from 0-24, witout using AM/PM 
     * to signify which hour of the day it is.
     * This method is used to convert the time to standard time which uses AM/PM designations
     */
    public int TimeConversion() { //DATA_USER_InfoInput RetrieveUserInfo

        //DATA_USER_InfoInput UserInfo = new DATA_USER_InfoInput(RetrieveUserInfo);
        //int IndexPosition = 0;


        System.out.println("Standard Time Input:");

        // iterates over all indexed time positions stored within DATA_USER_InfoInput() object
        //while (UserInfo.TimeIntervals.get(IndexPosition) != null) { 
            
            // Day for Interval
            // String Day      = UserInfo.TimeIntervals.get(IndexPosition).WeekDay;

            // // Start Interval   (hour:min)
            // int BEGINHour   = UserInfo.TimeIntervals.get(IndexPosition).PreferedHourBEGIN.getHour();
            // int BEGINMin    = UserInfo.TimeIntervals.get(IndexPosition).PreferedHourBEGIN.getMinute();

            // // End Interval     (hour:min)
            // int ENDHour     = UserInfo.TimeIntervals.get(IndexPosition).PreferedHourEND.getHour();
            // int ENDMin      = UserInfo.TimeIntervals.get(IndexPosition).PreferedHourEND.getMinute();

            // Start Interval   (hour:min)
            int BEGINHour   = PreferedHourBEGIN.getHour();
            int BEGINMin    = PreferedHourBEGIN.getMinute();

            // End Interval     (hour:min)
            int ENDHour     = PreferedHourEND.getHour();
            int ENDMin      = PreferedHourEND.getMinute();



            String StartInterval    = "AM";
            String EndInterval      = "AM";

            // Converts the beginning hour interval to PM if needed, otherwise it defaults to AM
            if ((12 <= BEGINHour) && (BEGINHour < 24)) {

                BEGINHour       = BEGINHour - 12;
                StartInterval   = "PM";

            }
            
            // Converts the end hour interval to PM if needed, otherwise it defaults to AM
            if ((12 <= ENDHour) && (ENDHour < 24)) {

                ENDHour         = ENDHour - 12;
                EndInterval     = "PM";

            }

            /**
             * Print Format for each index position:
             * Standard Time Input:
             * Day: (day)
             * Interval (Index) : (Hour:Min) (AM/PM) - (Hour:Min) (AM/PM)
             */
            System.out.println("Day: " + WeekDay);
            System.out.print("Interval : (" + BEGINHour + ":" + BEGINMin + ") " + StartInterval);
            System.out.println(" - (" + ENDHour + ":" + ENDMin + ") " + EndInterval);
            
        
        return 0;
    }
    
} // TimeSet
