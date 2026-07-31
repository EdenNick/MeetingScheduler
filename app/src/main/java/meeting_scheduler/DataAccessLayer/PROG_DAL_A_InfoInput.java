package meeting_scheduler.DataAccessLayer;
/** 
 * PROG_DAL_A_InfoInput.java
 * 
 * Description: Stores static info to be applyed to different files.
 * Should only be concurrently.
 */

import java.util.LinkedList;

public class PROG_DAL_A_InfoInput {

    /**
     * DEFAULT VALUES: 
     * 
     * EmployeeName:    default
     * EmployeeID:      -1
     * MeetingDays:     (empty String[6] array)
     * timesIntervals:  unitialized
     */

    public  String                              EmployeeName        = "Default";
    public  int                                 EmployeeID          = -1;
    public  String[]                            EmployeeMEETINGDAYS;
    public  LinkedList<PROG_DAL_A_TimeInput>    TimeIntervals;



    /**
     * Default Constructor
     * used for json operations
     */
    public PROG_DAL_A_InfoInput(){
        // com.fasterxml.jackson requires a no argument constructor - Do NOT put anything here
    }

    /**
     * Main Constructor
     * @param name
     * @param ID
     * @param week
     * @param times
     */
    public PROG_DAL_A_InfoInput(String name, int ID, String[] week, LinkedList<PROG_DAL_A_TimeInput> times) {
        this.EmployeeName        = name;
        this.EmployeeID          = ID;
        this.EmployeeMEETINGDAYS = week.clone();
        this.TimeIntervals       = new LinkedList<>(times);
    }

    /** 
     * Copy Constructor
     * @param copy
     */
    public PROG_DAL_A_InfoInput(PROG_DAL_A_InfoInput copy) {
        this.EmployeeName        = copy.EmployeeName;
        this.EmployeeID          = copy.EmployeeID;
        this.EmployeeMEETINGDAYS = copy.EmployeeMEETINGDAYS;
        this.TimeIntervals       = copy.TimeIntervals;
    }
    
}
