/**
 * TimeSet.java
 * 
 * Description:
 * Object that stores a single set of clock times meant to be added to a linked list.
 * (Format: Begin -> End)
 */

import java.time.LocalTime;

public class TimeSet {

    LocalTime PreferedHourBEGIN;
    LocalTime PreferedHourEND;

    public TimeSet(int BeginHOUR, int BeginMIN, int EndHOUR, int EndMIN) {
        
        // stores the beginning of the prefered time interval
        this.PreferedHourBEGIN = LocalTime.of(BeginHOUR, BeginMIN);

        //stores the ed of the prefered time interval
        this.PreferedHourEND = LocalTime.of(EndHOUR, EndMIN);
    }
    
} // TimeSet
