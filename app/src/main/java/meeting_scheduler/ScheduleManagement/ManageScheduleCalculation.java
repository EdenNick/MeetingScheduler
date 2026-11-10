// Package  - DO Not Change
// ############################################################
package meeting_scheduler.ScheduleManagement;
// ############################################################

// Imports
// ############################################################
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import meeting_scheduler.global;
import meeting_scheduler.SystemInfoManager;
import meeting_scheduler.DataHolder.PreferenceFull;
import meeting_scheduler.DataHolder.PreferenceTime;
import meeting_scheduler.FileManagement.ManageFilePaths;
import meeting_scheduler.FileManagement.ManageJsonFile;
import meeting_scheduler.UserInput.InputWeek;
// ############################################################



public class ManageScheduleCalculation {

    /**
     * INSTRUCTIONS FOR CLASS USE:
     * 
     * 1. Declare new PROG_INFO_SchedulingCalculation() object
     * 
     * 2. Optional - add user preference through any or all of the following methods
     *      a. UpdatePeopleToSchedule()
     *      b. UpdateListNumber()
     *      c. UpdateWeekDays()
     *      d. SetSpecificTime()
     *      e. SetNonSpecificTime()
     *      f. ResetALLPreferences()
     * 
     * 3. call CalculateSchedule() to calculate the schedules for the user
     * 
     * 4. Optional - use any of the methods in step 2. to update the preferences. Call CalculateSchedule() again to recalculate the schedules
     * 
     * 5. use RetrieveSchedule to retrieve the completed schedules. returns LinkedList<PROG_INFO_Schedule>
     */



    // User Input Preferences
    // ############################################################
    private int[]               UserInput_PeopleList;               // IncomingLinkedList of people the user wants scheduled, no operations should be performed on it.
    private boolean             UserPref_IDsProvided    = false;    // If false, a list of people to schedule was not provided, false as default.

    private InputWeek           UserInput_WeekDays;                 // Days the user wants a schedule for, defaults to the whole week.
    private boolean             userPref_SpecificDays   = false;    // boolean true if the user has provided a specific set of days, false otherwise

    private PreferenceTime[]    UserInput_UserTimes;                // LinkedList containing the specified times of the user
    private boolean             UserPref_SpecificTimes  = false;    // boolean if the user wants specified time intervals.
    // ############################################################

    // Program calculation variables
    // ############################################################
    private LinkedList<String>                  Calc_AvailableIDs;          // LinkedList that holds the list of available people for each viable interval.

    private LinkedList<DataInterval>            CALC_AvailablePeople;       // People who can be scheduled within an interval - NOT the fianl list of people
    private LinkedList<DataCompleteSchedule>    Calc_FullScheduleList;      // LinkedList of viableschedules and the people who can be in them.

    private PreferenceTime                      Calc_ViableSchedule;        // Used to set viable time intervals in the ScheduleList LinkedList.
    private boolean                             Calc_IdealSchedule = false; // denotes if a time interval in ScheduleList contians everyone the user wants scheduled;
    // ############################################################

    // File data info and management
    // ############################################################
    private ManageJsonFile              JsonFileManager = new ManageJsonFile(ManageFilePaths.GetLock_JSN_DataCard());
    private LinkedList<PreferenceFull>  FileData_Employee_FullList;
    // ############################################################

    // Default Values
    // ############################################################
    private DataIDandDays       IDWeekdayPair_IDENTSort;
    private DataIDandDays       IDWeekdayPair_DAYSort;
    private int[]               IDList_AppliedIDAndDayPref;
    private PreferenceFull[]    Employee_FullList_Array;
    private PreferenceFull[]    Employee_FullList_RefinedArray;
    private final String[]      WEEK_DEFAULT;
    private final int           WEEK_LENGTH;
    // ############################################################
    


    /**
     * Constructor
     */
    public ManageScheduleCalculation() {

        this.WEEK_LENGTH  = SystemInfoManager.GET_SYSTEM_VALUE_INT(global.BASICVALUESINT.INT, 0);
        this.WEEK_DEFAULT = SystemInfoManager.GET_SYSTEM_VALUE_WEEK(global.WEEKTYPE.SHORT);
        
        this.UserInput_WeekDays = new InputWeek();
        this.UserInput_WeekDays.SetAll();

        this.Calc_FullScheduleList = new LinkedList<>();
        
        try {
            Paramater_Init_FullList();
        } catch (IOException e) {
            e.printStackTrace();
        }

    } // ManageScheduleCalculation()


    // INITIALIZATION
    // ############################################################
    /**
     * Paramater_Init_FullList
     * Description: Initializes the full possible list of people from the relvant Json file.
     * @throws StreamReadException
     * @throws DatabindException
     * @throws IOException
     */
    private void Paramater_Init_FullList() throws StreamReadException, DatabindException, IOException {
        this.FileData_Employee_FullList = JsonFileManager.readJson_FullPref();
        this.Employee_FullList_Array = FileData_Employee_FullList.toArray(new PreferenceFull[0]);
    } // Paramater_Init_FullList()
    // ############################################################





    // PREFERECE RESET
    // ############################################################
    public void ResetALLPreferences() {
        ResetPreference_People();
        ResetPreference_Weekdays();
        ResetPreference_Times();
    } // ResetALLPreferences

    public void ResetPreference_People() {
        this.UserInput_PeopleList   = new int[0];
        this.UserPref_IDsProvided   = false;
    } // ResetPreference_People()

    public void ResetPreference_Weekdays() {
        this.UserInput_WeekDays.SetAll();
        this.userPref_SpecificDays  = false;
    } // ResetPreference_Weekdays()

    public void ResetPreference_Times() {
        this.UserInput_UserTimes    = new PreferenceTime[0];
        this.UserPref_SpecificTimes = false;
    } // ResetPreference_Times
    // ############################################################





    // PREFERENCE SET
    // ############################################################
    public void SetPreference_People(int[] INPUT_IDENT) {
        this.UserInput_PeopleList   = INPUT_IDENT.clone();
        this.UserPref_IDsProvided   = true;
        System.out.println("People size: " + UserInput_PeopleList.length);
    }

    public void SetPreference_Weekdays(String[] INPUT_WEEKDAYS) {
        this.UserInput_WeekDays.CopyAll(INPUT_WEEKDAYS);
        this.userPref_SpecificDays  = true;
    } 

    public void SetPreference_Times(LinkedList<PreferenceTime> INPUT_TIME) {
        this.UserInput_UserTimes    = INPUT_TIME.toArray(new PreferenceTime[0]);
        this.UserPref_SpecificTimes = true;
    } // SetPreference_Times()
    // ############################################################



    // clear calculated schedule
    // ############################################################
    public void ClearCalculations() {
        this.Calc_FullScheduleList.clear();
    }
    // ############################################################



    /**
     * RetrieveSchedule()
     * Description: public method called in order to pass ScheduleList outside the class
     */
    public LinkedList<DataCompleteSchedule> RetrieveSchedule() {

        // Preference Specification set
        // ############################################################
        Paramater_ApplyPref_IDENT();
        Paramater_ApplyPref_WeekDays();
        // ############################################################


        // Preference Employee Set
        // ############################################################
        // Employee_FullList_RefinedArray is the array used in creating the schedules it is populated with the ids of people 
        // who met the previous specifications (besides the atual times)
        this.Employee_FullList_RefinedArray = new PreferenceFull[IDList_AppliedIDAndDayPref.length];

        for (int Position_RefinedList = 0; Position_RefinedList < IDList_AppliedIDAndDayPref.length; Position_RefinedList++) {

            for (int Position_fullList = 0; Position_fullList < Employee_FullList_Array.length; Position_fullList++) {

                if (IDList_AppliedIDAndDayPref[Position_RefinedList] == Employee_FullList_Array[Position_fullList].GetIdent()) {
                    System.out.println("ID added:" + Employee_FullList_Array[Position_fullList].GetIdent());
                    this.Employee_FullList_RefinedArray[Position_RefinedList] = new PreferenceFull(Employee_FullList_Array[Position_fullList]);
                }

            } // for (int Position_fullList = 0; Position_fullList < Employee_FullList_Array.length; Position_fullList++)

        } // for (int Position_RefinedList = 0; Position_RefinedList < IDList_AppliedIDAndDayPref.length; Position_RefinedList++)
        // ############################################################


        // Schedule Calculation
        // ############################################################
        Calculate_ScheduleList();
        // ############################################################
        
        return Calc_FullScheduleList;

    } // RetrieveSchedule()










    /**
     * Paramater_ApplyPref_IDENT
     * Description: Based off of the currently set ID preference, this method applies them to the total list of people, removing people from the list who 
     * the user doesn't want scheduled.
     */
    private void Paramater_ApplyPref_IDENT() {

        // IF - the user has provided a specific list of IDs they want scheduled
        if (UserPref_IDsProvided == true) {

            // Stores a key value data pairs in the form of user IDs and the String[] of weekdays they can be scheduled on
            this.IDWeekdayPair_IDENTSort = new DataIDandDays(UserInput_PeopleList.length);

            int CurrentValue_IdentPref = 0;
            int Currentvalue_IdentList = 0;

            // iterate throught he full list of ids the user has selected
            for (int Position_IdentList = 0; Position_IdentList < UserInput_PeopleList.length; Position_IdentList++) {

                // for each id the user selected iterate over the full list of preferences in Employee_FullList_Array[]
                for (int Position_FullList = 0; Position_FullList < Employee_FullList_Array.length; Position_FullList++) {

                    // current ID in the user submitted array
                    CurrentValue_IdentPref = UserInput_PeopleList[Position_IdentList];
                    // current ID in the complete preference array
                    Currentvalue_IdentList = Employee_FullList_Array[Position_FullList].GetIdent();

                    // if these values are the same add the Id and the days associated with that persons preference to the IDWeekdayPair_IDENTSort object
                    // which acts as a key value pair storage
                    if (CurrentValue_IdentPref == Currentvalue_IdentList) {
                        this.IDWeekdayPair_IDENTSort.Set_Values(Position_IdentList, CurrentValue_IdentPref, Employee_FullList_Array[Position_FullList].GetDays());
                        break;
                    }

                } // for (int Position_FullList = 0; Position_FullList < FileData_Employee_FullList.size(); Position_FullList++)

            } // for (int Position_IdentList = 0; Position_IdentList < UserInput_PeopleList.length; Position_IdentList++)
        

        // ELSE - the user has not provided a list of IDs they want scheduled, get key value pairs from the total list of people
        } else {

            // Stores a key value data pairs in the form of user IDs and the String[] of weekdays they can be scheduled on
            this.IDWeekdayPair_IDENTSort = new DataIDandDays(FileData_Employee_FullList.size());

            int CurrentValue_ID = 0;

            // iterate through all preferences in the full preference list
            for (int Position_FullListAll = 0; Position_FullListAll < Employee_FullList_Array.length; Position_FullListAll++) {

                // add the ids and their preferred days to the IDWeekdayPair_IDENTSort object
                CurrentValue_ID = Employee_FullList_Array[Position_FullListAll].GetIdent();
                this.IDWeekdayPair_IDENTSort.Set_Values(Position_FullListAll, CurrentValue_ID, Employee_FullList_Array[Position_FullListAll].GetDays());

            } // for (int Position_FullListAll = 0; Position_FullListAll < FileData_Employee_FullList.size(); Position_FullListAll++)


        }
        
    } // Paramater_ApplyPref_IDENT()










    private void Paramater_ApplyPref_WeekDays() {


        // Ensures the key value pair is initialized and contains some values
        if (this.IDWeekdayPair_IDENTSort == null) {
            Paramater_ApplyPref_IDENT();
        }


        // the user has specific days they want scheduled
        if (userPref_SpecificDays == false) {

            // the days the user wants scheduled
            String[]    UserWeekDays    = this.UserInput_WeekDays.GetWeekArray();
            // the list of ids that can be scheduled on these days
            int[]       IDList          = this.IDWeekdayPair_IDENTSort.GetArraySetIDs();
            // ID weekday pair
            String[][]  PREF_Weekdays   = this.IDWeekdayPair_IDENTSort.GetArraySetWeekDays();
            // size of the key value list
            int         Size_IDWeekday  = this.IDWeekdayPair_IDENTSort.GetArraySetLength();
            // size of the sorted day list
            int         DaySort_constructionSize = 0;
            // holds the current day
            String      DayReturned;


            // ############################################################
            // IDWeekdayPair_DAYSort stores the days that can be scheduled on based on the preferences of each person and the submitted days of the user
            // in essence it finds the days that can be scheduled on and reduces them based on what days the user allows the system to be scheduled on
            // i.e if mon, wed and fri, are all days the selected ID list can be scheduled on and the user only selects wednesday the list gets reduced to just wed
            // ############################################################
            
            // Nested for loop to get the correct size for IDWeekdayPair_DAYSort the object that stores the final list of ids and days that can be scheduled
            // ############################################################
            // Iterates over the full key value pair
            // iterates through the positions of IDs stored in the pair
            for (int Position_IDWeekday = 0; Position_IDWeekday < Size_IDWeekday; Position_IDWeekday++) {

                // iterates over each day in the current key value ID weekdays pair
                for (int Position_Day = 0; Position_Day < this.WEEK_LENGTH; Position_Day++) {

                    // get the day stored in PREF_Weekdays correlating to the currently iterated ID
                    DayReturned = PREF_Weekdays[Position_IDWeekday][Position_Day];

                    // ensures the value exists
                    if (DayReturned != null) {
                        // if the day a user can be scheduled on is one that the user wants to schedule a meeting on, increment the construction size and break
                        if (DayReturned.equals(UserWeekDays[Position_Day])) {
                            DaySort_constructionSize++;
                            break;
                        }
                    } // if (DayReturned != null)

                } // for (int Position_Day = 0; Position_Day < this.WEEK_LENGTH; Position_Day++)

            } // for (int Position_IDWeekday = 0; Position_IDWeekday < Size_IDWeekday; Position_IDWeekday++)
            // ############################################################



            // CREATE IDWeekdayPair_DAYSort of size DaySort_constructionSize based on the previous for loops
            // ############################################################
            this.IDWeekdayPair_DAYSort = new DataIDandDays(DaySort_constructionSize);
            // ############################################################



            // Populate IDWeekdayPair_DAYSort with ids and days that the user wants scheduled. this will be the list used in schedule calculations
            // ############################################################
            for (int Position_IDWeekday = 0; Position_IDWeekday < Size_IDWeekday; Position_IDWeekday++) {
                
                // iterates over each day in the current key value ID weekdays pair
                for (int Position_Day = 0; Position_Day < this.WEEK_LENGTH; Position_Day++) {
                    
                    // if the day match at least once
                    DayReturned = PREF_Weekdays[Position_IDWeekday][Position_Day];

                    if (DayReturned != null) {
                        if (DayReturned.equals(UserWeekDays[Position_Day])) {

                            this.IDWeekdayPair_DAYSort.Set_Values(Position_IDWeekday, IDList[Position_IDWeekday], PREF_Weekdays[Position_IDWeekday]);
                            break;

                        } // if (DayReturned.equals(UserWeekDays[Position_Day]))

                    } // if (DayReturned != null)

                } // for (int Position_Day = 0; Position_Day < this.WEEK_LENGTH; Position_Day++)

            } // for (int Position_IDWeekday = 0; Position_IDWeekday < Size_IDWeekday; Position_IDWeekday++)
            // ############################################################


            // Set IDList_AppliedIDAndDayPref to a sorted List
            // ############################################################
            this.IDList_AppliedIDAndDayPref = IDWeekdayPair_DAYSort.GetArraySetIDs();
            // ############################################################

        } else {
            // the user has not specified any days
            // Set IDList_AppliedIDAndDayPref to the unsorted default list of available ids based on user specifications
            // ############################################################
            this.IDList_AppliedIDAndDayPref = IDWeekdayPair_IDENTSort.GetArraySetIDs();
            // ############################################################

        }

    } // Paramater_ApplyPref_WeekDays()









    /**
     * Calculate_ScheduleList()
     * Description: Calculates schedule using parameters calculated in Paramater_ApplyPref_IDENT() and Paramater_ApplyPref_WeekDays();
     */
    private void Calculate_ScheduleList() {

        // calculation variables
        // ############################################################
        String[]    WeekDay_Iteration;  // WeekDays that should be iterated over
        String      DAY_Current;        // Current Day being iterated over from WeekDay_Iteration
        int         MaxSize = 0;        // maximum ammount of time intervals that need to be traveresed over
        int         intervalLength;     // number of intervals within an individual datacard
        // ############################################################


        // ensures a valid weekday list exists in any scenario
        // ############################################################
        if (userPref_SpecificDays == true) {
            WeekDay_Iteration = UserInput_WeekDays.GetWeekArray();
            System.out.println("true");
        } else {
            WeekDay_Iteration = this.WEEK_DEFAULT.clone();
            System.out.println("false");
        }
        // ############################################################


        // Loop through the full list of people being checked for the schedule
        // find the datacard with the largest number of intervals and save that value to MaxSize
        for (int position = 0; position < Employee_FullList_Array.length; position++) {

            intervalLength = Employee_FullList_Array[position].GetIntervals().length;

            if (intervalLength > MaxSize) {
                MaxSize = intervalLength;
            }
        }



        /**
         * Iterate over each day the user selected, at least one at most every day of the week
         */
        // ############################################################
        for (int Pos = 0; Pos < WEEK_DEFAULT.length; Pos++) {

            if (WeekDay_Iteration[Pos] != null) {

                DAY_Current = WeekDay_Iteration[Pos];
                System.out.println("Day current " + DAY_Current);
                /**
                 * for each day iterate through every user selected time interval they want to make a schedule for.
                 * If they did not submit any preferences create a schedule for the full hours of the day
                 */

                // interval of user submitted time(s)
                // ############################################################
                int Interval_HourStart  = 0;
                int Interval_MinStart   = 0;
                int Interval_HourEnd    = 23;
                int Interval_MinEnd     = 59;
                // ############################################################


                /**
                 * Calculating times
                 * This section calculates which people can meet in the selected time interval(s) for that day
                 */
                if (UserPref_SpecificTimes == false) {              // user did not submit any times

                    // iterate over each person with these default values
                    // calculates schedule
                    // CalculatePerInterval(Interval_HourStart, Interval_MinStart, Interval_HourEnd, Interval_MinEnd, MaxSize, DAY_Current);
                    CalculateGeneralInterval(Interval_HourStart, Interval_MinStart, Interval_HourEnd, Interval_MinEnd, DAY_Current);
                    
                } else if (UserPref_SpecificTimes == true) {        // User did submit times

                    // For each itnerval the user entered
                    for (PreferenceTime TimeInterval_UserInput : UserInput_UserTimes) {

                        // user input time intervals
                        Interval_HourStart  = TimeInterval_UserInput.GetStartTimeHour();
                        Interval_MinStart   = TimeInterval_UserInput.GetStartTimeMin();
                        Interval_HourEnd    = TimeInterval_UserInput.GetEndTimeHour();
                        Interval_MinEnd     = TimeInterval_UserInput.GetEndTimeMin();
                        
                        // calculates schedule
                        // CalculatePerInterval(Interval_HourStart, Interval_MinStart, Interval_HourEnd, Interval_MinEnd, MaxSize, DAY_Current);
                        CalculateGeneralInterval(Interval_HourStart, Interval_MinStart, Interval_HourEnd, Interval_MinEnd, DAY_Current);

                    } // for (PROG_DAL_A_TimeInput TimeInputInterval : UserInput_UserTimes)
                    
                } // else if (UserPref_SpecificTimes == true)

            } // if (WeekDay_Iteration[Pos] != null)
            
        } // for (String Day : WeekDays)

    } // Calculate_ScheduleList()












































    /**
     * CalculateInterval()
     * Description: runs calculations for the full list of people provided one at a time
     * segmeneted like this to remove duplicate code
     */
    private void CalculateGeneralInterval(int STARTHOUR, int STARTMIN, int ENDHOUR, int ENDMIN, String PREFDAY) {

        // Calculation variables
        // ############################################################
        // user submitted preference day and time
        int     IntervalStartHour   = STARTHOUR;
        int     IntervalStartMin    = STARTMIN;
        int     IntervalEndHour     = ENDHOUR;
        int     IntervalEndMin      = ENDMIN;
        String  IntervalDay         = PREFDAY;

        // prevalence and range indicators
        int[] HourPrevalence    = new int[24];      // each position marks how many people can meet in that specific hour
        int[] MinStartRange     = new int[24];      // each position marks the latest start minute time for that specific hour
        int[] MinEndRange       = new int[24];      // each position marks the earliest end time for that specific hour

        // ensures arrays are initialized with correct values
        Arrays.fill(HourPrevalence, 0);
        Arrays.fill(MinStartRange, 0);
        Arrays.fill(MinEndRange, 59);

        // preference variables to store an interval in a selected datacard
        int     PreferenceStartHour;
        int     PreferenceStartMin;
        int     PreferenceEndHour;
        int     PreferenceEndMin; 



        // 2D array storing hour and all employees who fall on that Hour
        Object[][] EmployeeHourRelation = new Object[24][1];

        for (int Pos = 0; Pos < EmployeeHourRelation.length; Pos++) {
            // initialize an array to hold the IDs
            EmployeeHourRelation[Pos][0] = new int[Employee_FullList_RefinedArray.length];
            // set all values to -1, values of -1 will be filtered out later
            Arrays.fill(((int[]) EmployeeHourRelation[Pos][0]), -1);
            //EmployeeHourRelation[Pos][0] = new LinkedList<Integer>();
        }
        // ############################################################


        // current interval position within a single datacard
        int IntervalPos;
        PreferenceFull Employee;


        // Iterate through every Preference datacard in the refined Array previously calculated
        // ############################################################
        for (int EmployeePosition = 0; EmployeePosition < Employee_FullList_RefinedArray.length; EmployeePosition++) {
            // for (PreferenceFull Employee: Employee_FullList_RefinedArray) {
            
            Employee = Employee_FullList_RefinedArray[EmployeePosition];
            IntervalPos = 0;

            // iterate through every interval in each Preference 
            while (IntervalPos < Employee.GetIntervals().length) {
                
                // if the interval lies on the correct day retrieve and mark its times in the Hour Prevalence int[]
                if (Employee.GetIntervals()[IntervalPos].GetWeekDay().equals(IntervalDay)) {

                    PreferenceStartHour = Employee.GetIntervals()[IntervalPos].GetStartTimeHour();
                    PreferenceStartMin  = Employee.GetIntervals()[IntervalPos].GetStartTimeMin(); 
                    PreferenceEndHour   = Employee.GetIntervals()[IntervalPos].GetEndTimeHour();  
                    PreferenceEndMin    = Employee.GetIntervals()[IntervalPos].GetEndTimeMin();

                    for (int Pos = PreferenceStartHour; Pos < PreferenceEndHour; Pos++) {
                        HourPrevalence[Pos]++;
                    }

                    if (MinStartRange[PreferenceStartHour] < PreferenceStartMin) {
                        MinStartRange[PreferenceStartHour] = PreferenceStartMin;
                    }

                    if (MinEndRange[PreferenceEndHour] > PreferenceEndMin) {
                        MinEndRange[PreferenceEndHour] = PreferenceEndMin;
                    }

                    // access the 2d array [Hour][int[] for IDs for the hour]
                    for (int Pos = PreferenceStartHour; Pos <= PreferenceEndHour; Pos++) {
                        ((int[]) EmployeeHourRelation[Pos][0])[EmployeePosition] = Employee.GetIdent();
                    }

                } // (Employee.GetIntervals()[IntervalPos].GetWeekDay().equals(IntervalDay))

                IntervalPos++;

            } // while (IntervalPos < Employee.GetIntervals().length)

        } // for (PreferenceFull Employee: Employee_FullList_RefinedArray)
        // ############################################################


        System.out.println("Hour Range" + Arrays.toString(HourPrevalence));

        // Refine the data based on user Input
        // remove any values that fall outside user time input
        // ############################################################
        // remove all prevalence data for values that fall before the hour start time
        for (int Pos = 0; Pos < IntervalStartHour; Pos++) {
            HourPrevalence[Pos] = 0;
        }
        // remove all prevalence data for values that fall after the hour end itme
        for (int Pos = IntervalEndHour; Pos < HourPrevalence.length; Pos++) {
            HourPrevalence[Pos] = 0;
        }

        // Ensure that the minute range for the selected hours take precedence over any user preference if it falls outside the range
        if (MinStartRange[IntervalStartHour] < IntervalStartMin) {
            MinStartRange[IntervalStartHour] = IntervalStartMin;
        }

        // Ensure that the minute range for the selected hours take precedence over any user preference if it falls outside the range
        if (MinEndRange[IntervalEndHour] > IntervalEndMin) {
            MinEndRange[IntervalEndHour] = IntervalEndMin;
        }
        // ############################################################

        int StartHour       = 0;
        int EndHour         = 0;
        int CurrentAmmount  = 0;
        int NextAmmount     = 0;


        System.out.println("Hour Range" + Arrays.toString(HourPrevalence));

        // TODO:
        int[] CurrentIDs;
        int[] NextIDs;


        System.out.println("End interval: " + IntervalEndHour);
        // iterate through the full prevalence set
        for (int Pos = IntervalStartHour; Pos < IntervalEndHour; Pos++) {

            if (HourPrevalence[Pos] > 0) {

                CurrentIDs = (int[]) EmployeeHourRelation[Pos][0];
                Arrays.sort(CurrentIDs);

                NextIDs = (int[]) EmployeeHourRelation[Pos][0];
                Arrays.sort(NextIDs);


                // Starting hour for a schedule
                StartHour       = Pos;
                // current ammount of people in that hour
                CurrentAmmount  = HourPrevalence[Pos];
                // ammount of people in the next hour
                NextAmmount     = HourPrevalence[Pos];

                // While the Id stored in each Array remain the same, incrment the position until a difference occurs
                while ( (Arrays.equals(CurrentIDs, NextIDs)) && (Pos < IntervalEndHour) && (HourPrevalence[Pos] > 0)) {
                    NextAmmount = HourPrevalence[Pos];
                    NextIDs = (int[]) EmployeeHourRelation[Pos][0];
                    Arrays.sort(NextIDs);
                    Pos++;
                }
                // ending hour for a schedule, should be equal to the previous hour before a difference occurs between the two arrays
                if (Pos >= (StartHour + 1)) {
                    EndHour = Pos - 1;
                } else {
                    EndHour = StartHour;
                }

                System.out.println("Starting Hour: " + StartHour);
                System.out.println("Ending Hour: " + EndHour);
                // Create a schedule with this time frame
                CalculateSpecificRange(StartHour, MinStartRange[StartHour], EndHour, MinEndRange[EndHour], IntervalDay);

            } // if (HourPrevalence[Pos] > 0)

            System.out.println("pos value: " + Pos);
        } // for (int Pos = IntervalStartHour; Pos < IntervalEndHour; Pos++)






    } // CalculateInterval()





    // Calculate Schedule Specific range
    private void CalculateSpecificRange(int STARTHOUR, int STARTMIN, int ENDHOUR, int ENDMIN, String PREFDAY) {
        
        // Calculation variables
        // ############################################################
        // user submitted preference day and time
        int     IntervalStartHour   = STARTHOUR;
        int     IntervalStartMin    = STARTMIN;
        int     IntervalEndHour     = ENDHOUR;
        int     IntervalEndMin      = ENDMIN;
        String  IntervalDay         = PREFDAY;
        // preference variables to store an interval in a selected datacard
        int     PreferenceStartHour;
        int     PreferenceStartMin;
        int     PreferenceEndHour;
        int     PreferenceEndMin; 

        // List of Ids that can exist in a defined schedule
        LinkedList<String> CalculationIDList = new LinkedList<>();
        // ############################################################

        // current interval position within a single datacard
        int IntervalPos;

        // Iterate through every Preference datacard in the refined Array previously calculated
        // ############################################################
        for (PreferenceFull Employee: this.Employee_FullList_RefinedArray) {

            IntervalPos = 0;
            // iterate through every interval in each Preference 
            while (IntervalPos < Employee.GetIntervals().length) {
                
                // if the interval lies on the correct day retrieve its times and check if it can be within the schedule
                if (Employee.GetIntervals()[IntervalPos].GetWeekDay().equals(IntervalDay)) {

                    PreferenceStartHour = Employee.GetIntervals()[IntervalPos].GetStartTimeHour();
                    PreferenceStartMin  = Employee.GetIntervals()[IntervalPos].GetStartTimeMin(); 
                    PreferenceEndHour   = Employee.GetIntervals()[IntervalPos].GetEndTimeHour();  
                    PreferenceEndMin    = Employee.GetIntervals()[IntervalPos].GetEndTimeMin();

                    // DataCard preference falls within hour range
                    if ( (IntervalStartHour < PreferenceStartHour) || (IntervalEndHour > PreferenceEndHour) ) {
                        
                        CalculationIDList.add(Integer.toString(Employee.GetIdent()));
                        break;
                    
                    // the interval and preference hour start times are the same
                    } else if (IntervalStartHour == PreferenceStartHour) {

                        // the preference start minute is on or after the interval minute
                        if (IntervalStartMin <= PreferenceStartMin) {
                            CalculationIDList.add(Integer.toString(Employee.GetIdent()));
                            break;
                        }
                    
                    // the interval and preference hour end times are the same
                    } else if (IntervalEndHour == PreferenceEndHour) {
                        
                        // the preference end minute is on or before the interval minute
                        if (IntervalEndMin >= PreferenceEndMin) {
                            CalculationIDList.add(Integer.toString(Employee.GetIdent()));
                            break;
                        }

                    }

                } // (Employee.GetIntervals()[IntervalPos].GetWeekDay().equals(IntervalDay))

                IntervalPos++;

            } // while (IntervalPos < Employee.GetIntervals().length)

        } // for (PreferenceFull Employee: Employee_FullList_RefinedArray)
        // ############################################################



        /**
         * Submitting schedules
         */
        // Check if the list contains the full ammount of people the user wants to schedule if true set Calc_IdealSchedule to true, false otherwise
        // ############################################################
        if (CalculationIDList.size() > 0) {

            int[] Calc_AvailableIDs_AsInt = CalculationIDList.stream().mapToInt(Integer::parseInt).toArray();

            if (UserPref_IDsProvided == true) {

                // sort so both arrays are in the same order, necessary so Arrays.equals can check them both properly
                Arrays.sort(UserInput_PeopleList);
                Arrays.sort(Calc_AvailableIDs_AsInt);

                if ( Arrays.equals(UserInput_PeopleList, Calc_AvailableIDs_AsInt) ) {   // everyone the user wanted is on the list for this interval
                    Calc_IdealSchedule = true;
                } else {                                                                // not everyone is on the list
                    Calc_IdealSchedule = false;
                } // (UserInput_PeopleToSchedule.equals(Calc_AvailableIDs))

            } else {
                // no users specified so no ideal schedule
                Calc_IdealSchedule = false;
            } // (IDsProvided == true)
            // ############################################################


            // Create A schedule preference time
            // ############################################################
            Calc_ViableSchedule = new PreferenceTime(IntervalDay, IntervalStartHour, IntervalStartMin, IntervalEndHour, IntervalEndMin);
            // ############################################################


            // Full Schedule List
            // ############################################################
            Calc_FullScheduleList.add(new DataCompleteSchedule(PREFDAY, Calc_ViableSchedule, CalculationIDList, Calc_IdealSchedule));
            // ############################################################

        } // if (CalculationIDList.size() > 0)

    } //CalculateSpecificRange





} // PROG_BLL_SchedulingCalculation {}