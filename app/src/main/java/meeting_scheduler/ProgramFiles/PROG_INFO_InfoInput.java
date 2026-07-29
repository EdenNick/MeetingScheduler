package meeting_scheduler.ProgramFiles;
/** 
 * StaticInfoInput.java
 * 
 * Description: Stores static info to be applyed to different files.
 * Should only be concurrently.
 */

import java.util.LinkedList;

public class PROG_INFO_InfoInput {

    /**
     * DEFAULT VALUES: 
     * 
     * EmployeeName:    default
     * EmployeeID:      -1
     * MeetingDays:     (empty String[6] array)
     * timesIntervals:  unitialized
     */

    public  String                          EmployeeName        = "Default";
    public  int                             EmployeeID          = -1;
    public  String[]                        EmployeeMEETINGDAYS;
    public  LinkedList<PROG_INFO_TimeInput> TimeIntervals;

    /**
     * Main Constructor
     * Called to input all user inputs at once
     * @param name
     * @param ID
     * @param week
     * @param times
     */
    public PROG_INFO_InfoInput(String name, int ID, String[] week, LinkedList<PROG_INFO_TimeInput> times) {
        this.EmployeeName        = name;
        this.EmployeeID          = ID;
        this.EmployeeMEETINGDAYS = week.clone();
        this.TimeIntervals       = new LinkedList<>(times);
    }

    /** 
     * Copy Constructor
     * Currently no use.
     * @param copy
     */
    public PROG_INFO_InfoInput(PROG_INFO_InfoInput copy) {
        this.EmployeeName        = copy.EmployeeName;
        this.EmployeeID          = copy.EmployeeID;
        this.EmployeeMEETINGDAYS = copy.EmployeeMEETINGDAYS;
        this.TimeIntervals       = copy.TimeIntervals;
    }
    
}
