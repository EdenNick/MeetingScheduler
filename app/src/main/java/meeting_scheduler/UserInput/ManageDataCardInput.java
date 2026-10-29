package meeting_scheduler.UserInput;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;

import meeting_scheduler.SystemInfoManager;
import meeting_scheduler.global;
import meeting_scheduler.DataHolder.PreferenceFull;
import meeting_scheduler.DataHolder.PreferenceTime;

public class ManageDataCardInput {
    
    private static final String[]   WEEKDAY = SystemInfoManager.GET_SYSTEM_VALUE_WEEK(global.WEEKTYPE.SHORT);

    private boolean             NEW_EMPLOYEE_Delete;
    private String              NEW_EMPLOYEE_Name;
    private int                 NEW_EMPLOYEE_Ident;
    private String[]            NEW_EMPLOYEE_Days;
    private PreferenceTime[]    NEW_EMPLOYEE_Intervals;

    // Constructor
    public ManageDataCardInput(PreferenceFull INPUT_PERSON_PREF) {

        this.NEW_EMPLOYEE_Delete    = INPUT_PERSON_PREF.GetStatus();
        this.NEW_EMPLOYEE_Name      = INPUT_PERSON_PREF.GetName();
        this.NEW_EMPLOYEE_Ident     = INPUT_PERSON_PREF.GetIdent();
        this.NEW_EMPLOYEE_Days      = INPUT_PERSON_PREF.GetDays();
        this.NEW_EMPLOYEE_Intervals = INPUT_PERSON_PREF.GetIntervals();

    } // ManageDataCardInput

    public void RemovePreferenceAll() {
        // ManageJsonFormat handles deletion, this method signifies a preference needs to be deleted. it does not delete the object itself.
        NEW_EMPLOYEE_Delete = true;

    } // RemovePreference()


    public void KeepPreferenceAll() {
        // ManageJsonFormat handles deletion, this method signifies a preference needs to be deleted. it does not delete the object itself.
        NEW_EMPLOYEE_Delete = false;

    } // RemovePreference()


    /**
     * OrganizeGeneral()
     * Description: organizaes the PreferenceTime[] stored in the datacard PreferenceFull Object. Sorts by day and time sequentially.
     */
    public void OrganizeGeneral() {
        
        // sun mon tue wed thu fri sat  ORDERED NORMAL
        // fri mon sat sun thu tue wed  ORDERED ALPHABETICAL

        // Linked Lists holding the PreferenceTime objects for each specific day
        LinkedList<PreferenceTime> ListSUN = new LinkedList<>();
        LinkedList<PreferenceTime> ListMON = new LinkedList<>();
        LinkedList<PreferenceTime> ListTUE = new LinkedList<>();
        LinkedList<PreferenceTime> ListWED = new LinkedList<>();
        LinkedList<PreferenceTime> ListTHU = new LinkedList<>();
        LinkedList<PreferenceTime> ListFRI = new LinkedList<>();
        LinkedList<PreferenceTime> ListSAT = new LinkedList<>();

        // List to gather the Objects from
        PreferenceTime[] IntervalCopy = this.NEW_EMPLOYEE_Intervals.clone();


        // Checks each day, adding the object to its respective list when it encounters it
        int Pos = 0;
        while (Pos < IntervalCopy.length) {
            if (IntervalCopy[Pos].GetWeekDay().equals(WEEKDAY[0])) {    // Sun
                ListSUN.add(IntervalCopy[Pos]);
            }

            if (IntervalCopy[Pos].GetWeekDay().equals(WEEKDAY[1])) {    // Mon
                ListMON.add(IntervalCopy[Pos]);
            }

            if (IntervalCopy[Pos].GetWeekDay().equals(WEEKDAY[2])) {    // TUE
                ListTUE.add(IntervalCopy[Pos]);
            }

            if (IntervalCopy[Pos].GetWeekDay().equals(WEEKDAY[3])) {    // WED
                ListWED.add(IntervalCopy[Pos]);
            }

            if (IntervalCopy[Pos].GetWeekDay().equals(WEEKDAY[4])) {    // THU
                ListTHU.add(IntervalCopy[Pos]);
            }

            if (IntervalCopy[Pos].GetWeekDay().equals(WEEKDAY[5])) {    // FRI
                ListFRI.add(IntervalCopy[Pos]);
            }

            if (IntervalCopy[Pos].GetWeekDay().equals(WEEKDAY[6])) {    // SAT
                ListSAT.add(IntervalCopy[Pos]);
            }

            Pos++;
        } // while (Pos < IntervalCopy.length) 

        // LinkedLists converted to arrays for more efficient sorting
        PreferenceTime[] ArraySun = ListSUN.toArray(new PreferenceTime[0]);
        PreferenceTime[] ArrayMon = ListMON.toArray(new PreferenceTime[0]);
        PreferenceTime[] ArrayTue = ListTUE.toArray(new PreferenceTime[0]);
        PreferenceTime[] ArrayWed = ListWED.toArray(new PreferenceTime[0]);
        PreferenceTime[] ArrayThu = ListTHU.toArray(new PreferenceTime[0]);
        PreferenceTime[] ArrayFri = ListFRI.toArray(new PreferenceTime[0]);
        PreferenceTime[] ArraySat = ListSAT.toArray(new PreferenceTime[0]);

        // Sort by timeframe -> compare by starting Hour First, then starting Min
        Arrays.sort(ArraySun, Comparator.comparing( (PreferenceTime Pref) -> Pref.GetStartTimeHour()).thenComparing(Pref -> Pref.GetStartTimeMin()));
        Arrays.sort(ArrayMon, Comparator.comparing( (PreferenceTime Pref) -> Pref.GetStartTimeHour()).thenComparing(Pref -> Pref.GetStartTimeMin()));
        Arrays.sort(ArrayTue, Comparator.comparing( (PreferenceTime Pref) -> Pref.GetStartTimeHour()).thenComparing(Pref -> Pref.GetStartTimeMin()));
        Arrays.sort(ArrayWed, Comparator.comparing( (PreferenceTime Pref) -> Pref.GetStartTimeHour()).thenComparing(Pref -> Pref.GetStartTimeMin()));
        Arrays.sort(ArrayThu, Comparator.comparing( (PreferenceTime Pref) -> Pref.GetStartTimeHour()).thenComparing(Pref -> Pref.GetStartTimeMin()));
        Arrays.sort(ArrayFri, Comparator.comparing( (PreferenceTime Pref) -> Pref.GetStartTimeHour()).thenComparing(Pref -> Pref.GetStartTimeMin()));
        Arrays.sort(ArraySat, Comparator.comparing( (PreferenceTime Pref) -> Pref.GetStartTimeHour()).thenComparing(Pref -> Pref.GetStartTimeMin()));


        PreferenceTime[] OrganizeFinal = new PreferenceTime[ArraySun.length + ArrayMon.length + ArrayTue.length 
            + ArrayWed.length + ArrayThu.length + ArrayFri.length + ArraySat.length];

        int DestinationPos = 0;
        System.arraycopy(ArraySun, 0, OrganizeFinal, DestinationPos, ArraySun.length);

        DestinationPos += ArraySun.length;
        System.arraycopy(ArrayMon, 0, OrganizeFinal, DestinationPos, ArrayMon.length);

        DestinationPos += ArrayMon.length;
        System.arraycopy(ArrayTue, 0, OrganizeFinal, DestinationPos, ArrayTue.length);

        DestinationPos += ArrayTue.length;
        System.arraycopy(ArrayWed, 0, OrganizeFinal, DestinationPos, ArrayWed.length);

        DestinationPos += ArrayWed.length;
        System.arraycopy(ArrayThu, 0, OrganizeFinal, DestinationPos, ArrayThu.length);

        DestinationPos += ArrayThu.length;
        System.arraycopy(ArrayFri, 0, OrganizeFinal, DestinationPos, ArrayFri.length);

        DestinationPos += ArrayFri.length;
        System.arraycopy(ArraySat, 0, OrganizeFinal, DestinationPos, ArraySat.length);



        // Set the completed intervals
        this.NEW_EMPLOYEE_Intervals = OrganizeFinal.clone();

    } // OrganizeByDay()



    /**
     * RemovePreferenceSpecific()
     * Description: removes a preference at a specific day, if the position entered is invalid no action occurs.
     * @param INPUT_POSITION
     */
    public void RemovePreferenceSpecific(int INPUT_POSITION) {
        
        if ((INPUT_POSITION > 0) && (INPUT_POSITION < NEW_EMPLOYEE_Intervals.length) ) {
            
            String DayToRemove = NEW_EMPLOYEE_Intervals[INPUT_POSITION].GetWeekDay();

            NEW_EMPLOYEE_Intervals[INPUT_POSITION] = null;

            int Position = 0;

            // iterate through NEW_EMPLOYEE_Intervals, if another interval preference has the same day as the one removed, break. otherwise continue
            for (int Pos = 0; Pos < NEW_EMPLOYEE_Intervals.length; Pos++) {
                
                if ((NEW_EMPLOYEE_Intervals[INPUT_POSITION] != null) && (NEW_EMPLOYEE_Intervals[INPUT_POSITION].GetWeekDay().equals(DayToRemove))) {
                    break;
                } else {
                    Position++;
                }

            } // for (int Pos = 0; Pos < NEW_EMPLOYEE_Intervals.length; Pos++)


            // if Position is equal to NEW_EMPLOYEE_Intervals.length, it means the interval list has been passed through 
            // without encountering a duplicate day. 
            // No other preference has the same day, remove the day from the NEW_EMPLOYEE_Days array
            if (Position == NEW_EMPLOYEE_Intervals.length) {
                
                for (int WeekPos = 0; WeekPos < WEEKDAY.length; WeekPos++) {
                    if (WEEKDAY[WeekPos].equals(DayToRemove)) {
                        NEW_EMPLOYEE_Days[WeekPos] = null;
                    }
                }

            } else {
                // duplicate day exists, no removal necessary
            }


        } else {
            // INVALID POSITION -> DO NOTHING
        }

    } // RemovePreferenceSpecific()



    public void UpdateTimeIntervals(LinkedList<PreferenceTime> INPUT_TIMEPREFERENCE) {

        
        this.NEW_EMPLOYEE_Intervals = new PreferenceTime[INPUT_TIMEPREFERENCE.size()];

        for (int Day = 0; Day < WEEKDAY.length; Day++) {
            for (int pos = 0; pos < INPUT_TIMEPREFERENCE.size(); pos++) {
                if (INPUT_TIMEPREFERENCE.get(pos).GetWeekDay().equals(WEEKDAY[Day])) {
                    this.NEW_EMPLOYEE_Days[Day] = WEEKDAY[Day];
                }
            } // for (int pos = 0; pos < INPUT_TIMEPREFERENCE.size(); pos++)

        } // for (int Day = 0; Day < WEEKDAY.length; Day++)

        this.NEW_EMPLOYEE_Intervals = INPUT_TIMEPREFERENCE.toArray(new PreferenceTime[0]);



    } // UpdateTimeIntervals()



    /**
     * GetUpdatedDatacard()
     * Description: returns a new PreferenceFull object with the users selected organization options
     * @return
     */
    public PreferenceFull GetUpdatedDatacard() {
        
        // Convert NEW_EMPLOYEE_Intervals back into a LinkedList
        LinkedList<PreferenceTime> IntervalAsList = new LinkedList<>();

        for (int Pos = 0; Pos < NEW_EMPLOYEE_Intervals.length; Pos++) {
            IntervalAsList.add(NEW_EMPLOYEE_Intervals[Pos]);
        }


        // PreferenceFull(boolean INPUT_DELETE, String INPUT_NAME, int INPUT_IDENT, String[] INPUT_DAYS, LinkedList<PreferenceTime> INPUT_INTERVALS)
        PreferenceFull UpdatedCard = new PreferenceFull(this.NEW_EMPLOYEE_Delete, this.NEW_EMPLOYEE_Name, this.NEW_EMPLOYEE_Ident, this.NEW_EMPLOYEE_Days, IntervalAsList);

        return UpdatedCard;
    } // GetUpdatedDatacard()


} // ManageDataCardInput()
