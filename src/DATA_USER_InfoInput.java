/** 
 * StaticInfoInput.java
 * 
 * Description:
 * stores static info to be applyed to different files
 * should only be concurrently
 */

import java.util.LinkedList;

public class DATA_USER_InfoInput {

    //TODO: update default values

    /**
     * DEFAULT VALUES: 
     * 
     * EmployeeName:    default
     * EmployeeID:      -1
     * MeetingDays:     (empty String[6] array)
     * timesIntervals:  unitialized
     */

    
    // stores employee name
    String EmployeeName = "default";

    // stores employee ID
    int EmployeeID = -1;

    // stores the days they can meet on
    String[] EmployeeMEETINGDAYS = new String[6];

    // stores a set of specific clock times
    LinkedList<DATA_USER_TimeInput> TimeIntervals;

    /**
     * Main Constructor method to be called to input all user inputs at once
     * @param name
     * @param ID
     * @param week
     * @param times
     */
    public DATA_USER_InfoInput(String name, int ID, String[] week, LinkedList<DATA_USER_TimeInput> times) {
        this.EmployeeName        = name;
        this.EmployeeID          = ID;
        this.EmployeeMEETINGDAYS = week;
        this.TimeIntervals       = new LinkedList<>(times);
    }

    /** 
     * Copy Constructor method
     * Currently no use.
     */
    public DATA_USER_InfoInput(DATA_USER_InfoInput copy) {
        this.EmployeeName        = copy.EmployeeName;
        this.EmployeeID          = copy.EmployeeID;
        this.EmployeeMEETINGDAYS = copy.EmployeeMEETINGDAYS;
        this.TimeIntervals       = copy.TimeIntervals;
    }
    
}
