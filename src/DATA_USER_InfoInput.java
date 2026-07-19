/** 
 * StaticInfoInput.java
 * 
 * Description:
 * stores static info to be applyed to different files
 * should only be concurrently
 */

import java.util.LinkedList;

public class DATA_USER_InfoInput {

    // stores employee name
    static String EmployeeName = "default";

    // stores employee ID
    static int EmployeeID = -1;

    // stores the days they can meet on
    static String[] EmployeeMEETINGDAYS = new String[6];

    // stores a set of specific clock times
    static LinkedList<DATA_USER_TimeInput> TimeIntervals = null;

    /**
     * Method to be called to input all user inputs at once
     * @param name
     * @param ID
     * @param week
     * @param times
     */
    public void UserInput(String name, int ID, String[] week, LinkedList<DATA_USER_TimeInput> times) {
        EmployeeName        = name;
        EmployeeID          = ID;
        EmployeeMEETINGDAYS = week;
        TimeIntervals       = new LinkedList<>(times);
    }
    
}
