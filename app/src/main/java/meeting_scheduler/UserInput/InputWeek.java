package meeting_scheduler.UserInput;

import meeting_scheduler.SystemInfoManager;
import meeting_scheduler.global;
public class InputWeek {



    private String[] CheckWeek  = SystemInfoManager.GET_SYSTEM_VALUE_WEEK(global.WEEKTYPE.SHORT);
    private String[] InputWeek  = new String[this.CheckWeek.length];
    private String[] NullWeek   = new String[this.CheckWeek.length];

    // CONSTRUCTOR
    public InputWeek() {}



    /**
     * SetDay()
     * Description: Sets the value at INPUT_POSITION to its valid corresponding day i.e 0 = Sun, 1 = Mon, etc.
     * @param INPUT_POSITION
     */
    public void SetDay(int INPUT_POSITION) {
        if (CheckPosition(INPUT_POSITION) == true) {
            this.InputWeek[INPUT_POSITION] = CheckWeek[INPUT_POSITION];
        } else {
            // DO NOTHING
        }
    } // SetDay


    /**
     * UnSetDay()
     * Description: Unsets the value at INPUT_POSITION to null. i.e 0 sets Sun to Null, 1 sets Mon to Null, etc.
     * @param INPUT_POSITION
     */
    public void UnSetDay(int INPUT_POSITION) {
        if (CheckPosition(INPUT_POSITION) == true) {
            this.InputWeek[INPUT_POSITION] = null;
        } else {
            // DO NOTHING
        }
    } // UnSetDay


    /**
     * SetAll()
     * Description: Sets all positions in the array to valid days.
     */
    public void SetAll() {
        this.InputWeek = CheckWeek.clone();
    }


    /**
     * UnSetAll()
     * Description: Sets all positions in the array to null values.
     */
    public void UnSetAll() {
        this.InputWeek = NullWeek.clone();
    }


    /**
     * CopyAll()
     * Description: Copies all values from one array to another
     */
    public void CopyAll(String[] INPUT_WEEKARRAY) {
        for (int Pos = 0; Pos < this.CheckWeek.length; Pos++) {
            if (INPUT_WEEKARRAY[Pos] != null) {
                if (INPUT_WEEKARRAY[Pos].equals(this.CheckWeek[Pos])) {
                    SetDay(Pos);
                }
            }
        }
    }


    /**
     * GetWeekArray()
     * Description: Returns the Week array the user set.
     * @return
     */
    public String[] GetWeekArray() {
        return this.InputWeek.clone();
    }


    /**
     * CheckPosition()
     * Description: boolean check to determine if INPUT_POSITION falls within a valid range [0-7), returns true if yes, false otherwise.
     * @param INPUT_POSITION
     * @return
     */
    private boolean CheckPosition(int INPUT_POSITION) {
        if ( (INPUT_POSITION >= 0) && (INPUT_POSITION < this.CheckWeek.length) ) {
            return true;
        } else {
            return false;
        }
    }


} // InputWeek{}
