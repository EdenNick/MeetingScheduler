/**
 * TimeSet.java
 * 
 * Description:
 * Object that stores a single set of clock times meant to be added to a linked list.
 * (Format: Begin -> End)
 */

import java.time.LocalTime;

public class DATA_USER_TimeInput {

    String      WeekDay;
    LocalTime   PreferedHourBEGIN;
    LocalTime   PreferedHourEND;

    public DATA_USER_TimeInput(String day, int BeginHOUR, int BeginMIN, int EndHOUR, int EndMIN) {
        
        //stores the day of the prefered time;
        this.WeekDay = day;

        // stores the beginning of the prefered time interval (hour:min)    Military Time
        this.PreferedHourBEGIN = LocalTime.of(BeginHOUR, BeginMIN);

        //stores the ed of the prefered time interval (hour:min)            Military Time
        this.PreferedHourEND = LocalTime.of(EndHOUR, EndMIN);
    }
    
} // TimeSet
