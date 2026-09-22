package meeting_scheduler.ScheduleManagement;

import java.util.Arrays;

public class MANAGESCHEDULE_IDandDays {

    private int[] Ident;

    private String[][] WeekDays;

    private int ArraySetLength;

    /**
     * MANAGESCHEDULE_IDandDays
     * Primary Constructor
     * @param INPUT_ARRAYLENGTH
     */
    public MANAGESCHEDULE_IDandDays(int INPUT_ARRAYLENGTH) {

        this.Ident          = new int[INPUT_ARRAYLENGTH];
        this.WeekDays       = new String[INPUT_ARRAYLENGTH][];
        this.ArraySetLength = INPUT_ARRAYLENGTH;

        Arrays.fill(Ident, 0);
        Arrays.fill(WeekDays, null);
    }



    /**
     * MANAGESCHEDULE_IDandDays
     * Copy Constructor
     * @param INPUT_ARRAYLENGTH
     */
    public MANAGESCHEDULE_IDandDays(MANAGESCHEDULE_IDandDays INPUT_COPY) {
        this.Ident          = INPUT_COPY.Return_Idents();
        this.WeekDays       = INPUT_COPY.Return_Weekdays();
        this.ArraySetLength = INPUT_COPY.Return_Size();
    }



    public void Set_Values(int INPUT_POSITION, int INPUT_IDENT, String[] INPUT_WEEKDAYS) {
        if (this.ArraySetLength > INPUT_POSITION) {
            
            this.Ident[INPUT_POSITION] = INPUT_IDENT;

            if (INPUT_WEEKDAYS.length == 7) {
                this.WeekDays[INPUT_POSITION] = INPUT_WEEKDAYS.clone();
            }
        }
    }

    
    public int[] Return_Idents() {
        return this.Ident.clone();
    }

    public String[][] Return_Weekdays() {
        return this.WeekDays.clone();
    }
    
    public int Return_Size() {
        return this.ArraySetLength;
    }
}
