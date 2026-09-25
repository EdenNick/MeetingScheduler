/**
 * PROG_BLL_SchedulingCalculation.java
 * 
 * Description: Performs the calculations necessary to schedule a meeting between various individuals.
 * Options include:
 * scheduling everyone or specific people
 * scheduling on specifc days or anytime during the week
 * Scheduling a specific time or anytime during the day, with otions for different times for each day selected
 * Not scheduling meeting on specifed times or days.
 */

// Package  - DO Not Change
// ############################################################
package meeting_scheduler.ScheduleManagement;
// ############################################################

// Imports
// ############################################################
// exception
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
// util
import java.util.LinkedList;
// jackson - json file manager
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import meeting_scheduler.global;
// System Messages
import meeting_scheduler.DataAccessLayer.PROG_DAL_D_SystemMessages;
import meeting_scheduler.EmployeePreferences.PREF_EMPLOYEE_FullPref;
import meeting_scheduler.EmployeePreferences.PREF_EMPLOYEE_TimePref;
import meeting_scheduler.FileManagement.MANAGEFILE_JsonManager;



public class MANAGESCHEDULE_Calculate {

    // TODO: System Messages


    //TODO: convert linked list to an array of the same object
    // TODO organize the scheduler calculations/ streamline it
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

    /**
     * User Preferences
     */
    private int[]                               UserInput_PeopleList;               // IncomingLinkedList of people the user wants scheduled, no operations should be performed on it.
    private boolean                             UserPref_IDsProvided    = false;    // If false, a list of people to schedule was not provided, false as default.

    private String[]                            UserInput_WeekDays;                 // Days the user wants a schedule for, defaults to the whole week.
    private boolean                             userPref_SpecificDays   = false;

    private PREF_EMPLOYEE_TimePref[]            UserInput_UserTimes;                // LinkedList containing the specified times of the user
    private boolean                             UserPref_SpecificTimes  = false;    // boolean if the user wants specified time intervals.


    // Default values TODO
    private String[] Weekdays;
    private int WeekdayLength;





    /**
     * Program calculation variables
     */
    private LinkedList<String>                  Calc_AvailableIDs;          // LinkedList that holds the list of available people for each viable interval.

    private LinkedList<MANAGESCHEDULE_Interval>    CALC_AvailablePeople;
    private LinkedList<PREF_EMPLOYEE_FullPref>    Calc_People;                // Linked list of object PROG_INFO_InfoInput which stores the card info for a persons preference.
    private boolean                             Calc_PeopleSet     = false; // false if the linkedlist peopele has not been set. false as default. 

    private PREF_EMPLOYEE_TimePref                Calc_ViableSchedule;        // Used to set viable time intervals in the ScheduleList LinkedList.
    private boolean                             Calc_IdealSchedule = false; // denotes if a time interval in ScheduleList contians everyone the user wants scheduled;

    private LinkedList<MANAGESCHEDULE_Schedule>     Calc_FullScheduleList;      // LinkedList of viableschedules and the people who can be in them.


    /**
     * File data info and management
     */
    private MANAGEFILE_JsonManager              JsonFileManager = new MANAGEFILE_JsonManager();
    private LinkedList<PREF_EMPLOYEE_FullPref>    PeopleFromFile;             // All datacards contained within the relavant Json File.
















    // New variables

    private String[] DefaultWeekday = global.Global_Data_Get_Weekdays();

    // Calculation preference variables
    private LinkedList<PREF_EMPLOYEE_FullPref> FileData_Employee_FullList;

    private PREF_EMPLOYEE_FullPref[] Employee_FullList_Array;

    private MANAGESCHEDULE_IDandDays IDWeekdayPair_IDENTSort;

    private MANAGESCHEDULE_IDandDays IDWeekdayPair_DAYSort;

    private int[] IDList_AppliedIDAndDayPref;

    // calculatation schedule variables
    private PREF_EMPLOYEE_FullPref[] Employee_FullList_RefinedArray;

    /**
     * Constructor 1
     * Description: primary defualt constructor when there is no preference for who is being shceduled and when the meeting should occur.
     */
    public MANAGESCHEDULE_Calculate() {

        Weekdays        = global.Global_Data_Get_Weekdays();

        WeekdayLength   = global.Global_Data_Get_WeekdaysLength();

        UserInput_WeekDays = DefaultWeekday.clone();
        try {
            Paramater_Init_FullList();
        } catch (IOException e) {
            e.printStackTrace();
        }


        this.Calc_FullScheduleList = new LinkedList<>();

    }



    /**
     * ResetALLPreferences()
     * Description: Resets all User preferences to default
     */
    public void ResetALLPreferences() {

        ResetPreference_People();
        ResetPreference_Weekdays();
        ResetPreference_Times();

    } // ResetALLPreferences



    /**
     * UpdatePeopleToSchedule()
     * Description: Used to update the list of people being used in the calculation
     * @param INPUT_IDENT
     */
    public void SetPreference_People(int[] INPUT_IDENT) {
        this.UserInput_PeopleList   = INPUT_IDENT.clone();
        this.UserPref_IDsProvided   = true;
    }



    /**
     * UpdateWeekDays()
     * Description: Updates the user selected days they want to schedule a meeting on
     * @param INPUT_WEEKDAYS
     */
    // TODO: UPDATE
    public void SetPreference_Weekdays(String[] INPUT_WEEKDAYS) {
        this.UserInput_WeekDays     = INPUT_WEEKDAYS.clone();
        this.userPref_SpecificDays  = true;
    }



    /**
     * SetSpecificTime()
     * Description: sets the program to find the people who can meet in specified intervals provided by the user
     * @param INPUT_TIME
     */
    public void SetPreference_Times(LinkedList<PREF_EMPLOYEE_TimePref> INPUT_TIME) {
        this.UserInput_UserTimes    = INPUT_TIME.toArray(new PREF_EMPLOYEE_TimePref[0]);
        this.UserPref_SpecificTimes = true;
    } // SetPreference_Times()




    /**
     * ResetPreference_People()
     * Description: Used to Reset the preference for the specific people the user wants scheduled. All people will be used in the scheduler calculations.
     */
    public void ResetPreference_People() {
        this.UserInput_PeopleList   = new int[0];
        this.UserPref_IDsProvided   = false;
    } // ResetPreference_People()



    /**
     * ResetPreference_Weekdays()
     * Description: Used to reset the preference for the specific weekdays to be scheduled. Everyday of the week will be used in the calculation.
     */
    public void ResetPreference_Weekdays() {
        this.UserInput_WeekDays     = DefaultWeekday.clone();
        this.userPref_SpecificDays  = false;
    } // ResetPreference_Weekdays()



    /**
     * SetNonSpecificTime()
     * Description: sets the program to find all possible times within the given day, sets UserSpecifiedTimes to null for garbage collection
     */
    public void ResetPreference_Times() {
        this.UserInput_UserTimes    = new PREF_EMPLOYEE_TimePref[0];
        this.UserPref_SpecificTimes = false;
    } // ResetPreference_Times

















    /**
     * Paramater_Init_FullList
     * Description: Initializes the full possible list of people from the relvant Json file.
     * @throws StreamReadException
     * @throws DatabindException
     * @throws IOException
     */
    private void Paramater_Init_FullList() throws StreamReadException, DatabindException, IOException {

        //FileData_Employees_FullList = new LinkedList<>();

        this.FileData_Employee_FullList = JsonFileManager.ReadFrom_DefaultEmployeePreference();

        this.Employee_FullList_Array = FileData_Employee_FullList.toArray(new PREF_EMPLOYEE_FullPref[0]);

    }







    /**
     * Paramater_ApplyPref_IDENT
     * Description: Based off of the currently set ID preference, this method applies them to the total list of people, removing people from the list who 
     * the user doesn't want scheduled.
     */
    private void Paramater_ApplyPref_IDENT() {

        // IF - the user has provided a specific list of IDs they want scheduled
        if (UserPref_IDsProvided == false) {

            // Stores a key value data pairs in the form of user IDs and the String[] of weekdays they can be scheduled on
            this.IDWeekdayPair_IDENTSort = new MANAGESCHEDULE_IDandDays(UserInput_PeopleList.length);

            int CurrentValue_IdentPref = 0;

            int Currentvalue_IdentList = 0;

            for (int Position_IdentList = 0; Position_IdentList < UserInput_PeopleList.length; Position_IdentList++) {

                for (int Position_FullList = 0; Position_FullList < Employee_FullList_Array.length; Position_FullList++) {

                    CurrentValue_IdentPref = UserInput_PeopleList[Position_IdentList];

                    Currentvalue_IdentList = Employee_FullList_Array[Position_FullList].GetIdent();

                    if (CurrentValue_IdentPref == Currentvalue_IdentList) {

                        this.IDWeekdayPair_IDENTSort.Set_Values(Position_IdentList, CurrentValue_IdentPref, Employee_FullList_Array[Position_FullList].GetDays());

                    }

                } // for (int Position_FullList = 0; Position_FullList < FileData_Employee_FullList.size(); Position_FullList++)

            } // for (int Position_IdentList = 0; Position_IdentList < UserInput_PeopleList.length; Position_IdentList++)
        

        // ELSE - the user has not provided a list of IDs they want scheduled, get key value pairs from the total list of people
        } else {

            // Stores a key value data pairs in the form of user IDs and the String[] of weekdays they can be scheduled on
            this.IDWeekdayPair_IDENTSort = new MANAGESCHEDULE_IDandDays(FileData_Employee_FullList.size());

            int CurrentValue_ID = 0;

            for (int Position_FullListAll = 0; Position_FullListAll < Employee_FullList_Array.length; Position_FullListAll++) {

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



        if (userPref_SpecificDays == false) {

            int[] IDList = this.IDWeekdayPair_IDENTSort.Return_Idents();
            String[][] PREF_Weekdays = this.IDWeekdayPair_IDENTSort.Return_Weekdays();

            int Size_IDWeekday = this.IDWeekdayPair_IDENTSort.Return_Size();

            int DaySort_constructionSize = 0;

            String DayReturned;
            // Nested for loop to get the correct size for IDWeekdayPair_DAYSort
            // Iterates over the full key value pair
            for (int Position_IDWeekday = 0; Position_IDWeekday < Size_IDWeekday; Position_IDWeekday++) {

                // iterates over each day in the current key value ID weekdays pair
                for (int Position_Day = 0; Position_Day < WeekdayLength; Position_Day++) {

                    // if the day match at least once
                    DayReturned = PREF_Weekdays[Position_IDWeekday][Position_Day];

                    if (DayReturned != null) {
                        if (DayReturned.equals(this.UserInput_WeekDays[Position_Day])) {
                            DaySort_constructionSize++;
                            break;
                        }
                    }

                }

            } // for (int Position_IDWeekday = 0; Position_IDWeekday < Size_IDWeekday; Position_IDWeekday++)



            this.IDWeekdayPair_DAYSort = new MANAGESCHEDULE_IDandDays(DaySort_constructionSize);



            // Nested for loop to get the correct size for IDWeekdayPair_DAYSort
            // Iterates over the full key value pair

            for (int Position_IDWeekday = 0; Position_IDWeekday < Size_IDWeekday; Position_IDWeekday++) {

                // iterates over each day in the current key value ID weekdays pair
                for (int Position_Day = 0; Position_Day < WeekdayLength; Position_Day++) {

                    // if the day match at least once
                    DayReturned = PREF_Weekdays[Position_IDWeekday][Position_Day];

                    if (DayReturned != null) {
                        if (DayReturned.equals(this.UserInput_WeekDays[Position_Day])) {

                        this.IDWeekdayPair_DAYSort.Set_Values(Position_IDWeekday, IDList[Position_IDWeekday], PREF_Weekdays[Position_IDWeekday]);
                        break;
                        }
                    }

                }

            } // for (int Position_IDWeekday = 0; Position_IDWeekday < Size_IDWeekday; Position_IDWeekday++)


            // int[] of ids to have their times checked
            IDList_AppliedIDAndDayPref = IDWeekdayPair_DAYSort.Return_Idents();
            System.out.println("ID size:" + IDList_AppliedIDAndDayPref.length);

        } else {

            // int[] of ids to have their times checked
            IDList_AppliedIDAndDayPref = IDWeekdayPair_IDENTSort.Return_Idents();
            System.out.println("ID size:" + IDList_AppliedIDAndDayPref.length);

        }

    } // Paramater_ApplyPref_WeekDays()






    public void Calculate_ScheduleList() {

        String[] WeekDay_Iteration;

        if (userPref_SpecificDays == false) {
            WeekDay_Iteration = UserInput_WeekDays.clone();
        } else {
            WeekDay_Iteration = global.Global_Data_Get_Weekdays();
        }

        int MaxSize = 0;
        int intervalLength;
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
        for (String DAY_Current : WeekDay_Iteration) {

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
                CalculatePerPerson(Interval_HourStart, Interval_MinStart, Interval_HourEnd, Interval_MinEnd, MaxSize, DAY_Current);

                
            } else if (UserPref_SpecificTimes == true) {        // User did submit times

                // For each itnerval the user entered
                for (PREF_EMPLOYEE_TimePref TimeInterval_UserInput : UserInput_UserTimes) {

                    // user input time intervals
                    Interval_HourStart  = TimeInterval_UserInput.GetStartTimeHour();
                    Interval_MinStart   = TimeInterval_UserInput.GetStartTimeMin();
                    Interval_HourEnd    = TimeInterval_UserInput.GetEndTimeHour();
                    Interval_MinEnd     = TimeInterval_UserInput.GetEndTimeMin();
                    
                    // calculates schedule
                    CalculatePerPerson(Interval_HourStart, Interval_MinStart, Interval_HourEnd, Interval_MinEnd, MaxSize, DAY_Current);

                } // for (PROG_DAL_A_TimeInput TimeInputInterval : UserInput_UserTimes)
                
            } // else if (UserPref_SpecificTimes == true)
            
        } // for (String Day : WeekDays)

    } // Calculate_ScheduleList()

















    /**
     * RetrieveSchedule()
     * Description: public method called in order to pass ScheduleList outside the class
     */
    public LinkedList<MANAGESCHEDULE_Schedule> RetrieveSchedule() {

        //TODO: maybe change later

        Paramater_ApplyPref_IDENT();

        Paramater_ApplyPref_WeekDays();

        // Employee_FullList_RefinedArray is the array used in creating the schedules it is populated with the ids of people 
        // who met the previous specifications (besides the atual times)
        this.Employee_FullList_RefinedArray = new PREF_EMPLOYEE_FullPref[IDList_AppliedIDAndDayPref.length];

        for (int Position_RefinedList = 0; Position_RefinedList < IDList_AppliedIDAndDayPref.length; Position_RefinedList++) {
            for (int Position_fullList = 0; Position_fullList < Employee_FullList_Array.length; Position_fullList++) {

                if (IDList_AppliedIDAndDayPref[Position_RefinedList] == Employee_FullList_Array[Position_fullList].GetIdent()) {
                    this.Employee_FullList_RefinedArray[Position_RefinedList] = new PREF_EMPLOYEE_FullPref(Employee_FullList_Array[Position_fullList]);
                }
            }
        }

        // calculates the schedules
        Calculate_ScheduleList();
        
        return Calc_FullScheduleList;
    }





    // /**
    //  * RetrieveUserCards()
    //  * Description: Retrieves valid employee preferences based on who can be scheduled. This depends on what Id preferences the user
    //  * Submitted and what weekdays they have chosen.
    //  * @throws IOException 
    //  * @throws DatabindException 
    //  * @throws StreamReadException 
    //  */
    // private void RetrieveUserCards() throws StreamReadException, DatabindException, IOException {

    //     // Calc_People - calculated list of people the user wants scheduled based on their input fromm UserInput_PeopleToSchedule
    //     Calc_People = new LinkedList<PREF_EMPLOYEE_FullPref>();
        
    //     // PeopleFromFile is a new linkedlist containing a copy of the retrieved json file data calculated from JsonFileManager.RetrieveFromFile();
    //     PeopleFromFile = JsonFileManager.ReadFrom_DefaultEmployeePreference();



    //     // A linkedlist of user ids was provided iterate through those
    //     if (this.UserPref_IDsProvided == true) {

    //         for (PREF_EMPLOYEE_FullPref FilePerson : PeopleFromFile) {
    //             // iterates over the linked list string of people to select
    //             for (String IDOfPerson : UserInput_PeopleList) {
                    
    //                 if(FilePerson.GetIdent() == Integer.parseInt(IDOfPerson)) {
    //                     Calc_People.add(FilePerson);
    //                     break;
    //                 }

    //             }
    //         }

    //     // A LinkedList of user ids was NOT provided, retrieve all info from the json file
    //     } else {
            
    //         for (PREF_EMPLOYEE_FullPref FilePerson : PeopleFromFile) {
    //             // iterates over the linked list string of people to select
    //             Calc_People.add(FilePerson);

    //         }

    //     }

    //     // Calc_People is set containing a list of the people to schedule
    //     Calc_PeopleSet = true;

    // }





    // /**
    //  * CalculateSchedule()
    //  * Description: calculates the schedules based on user preference
    //  * Its segmented into steps for readablility
    //  * @throws IOException 
    //  * @throws DatabindException 
    //  * @throws StreamReadException 
    //  */
    // private int CalculateSchedule() throws StreamReadException, DatabindException, IOException {

    //     // General System Info
    //     System.out.println(PROG_DAL_D_SystemMessages.INFO_CalculateScheduleStartCalc);
    //     System.out.println(PROG_DAL_D_SystemMessages.INFO_CalculateScheduleTimePref + UserPref_SpecificTimes);;
    //     System.out.println("");

    //     /**
    //      * Local private LinkedLists are created to store the total list of possible schedules and the list of people who can be scheduled for a specific interval
    //      */
    //     Calc_FullScheduleList   = new LinkedList<MANAGESCHEDULE_Schedule>();     // List of possible schedules


    //     /**
    //      * LinkedList variable People is created and set with retireved user card information to be used in calculations, a check is sued to ensure this happened
    //      */
    //     RetrieveUserCards(); // retrieves user info
    //     if (Calc_PeopleSet == false) {
    //         System.out.println(PROG_DAL_D_SystemMessages.ERROR_CalculateSchedulePeopleSet);
    //         return 1;
    //     }

        
    //     /**
    //      * For each Person in the People linkedlist the size of the timeIntervals LinkedList each stores is found, the size of the largest one is kept
    //      * This ensures all indexes in each TimeIntervals LinkedList is covered.
    //      */
    //     int MaxSize = 0;
    //     for (PREF_EMPLOYEE_FullPref Person : Calc_People) {
    //         if (Person.GetIntervals().length > MaxSize) {
    //             MaxSize = Person.GetIntervals().size();
    //         }

    //     } // for ()


    //     /**
    //      * Iterate over each day the user selected, at least one at most every day of the week
    //      */
    //     // ############################################################
    //     for (String Pref_Day : UserInput_WeekDays) {



    //         /**
    //          * for each day iterate through every user selected time interval they want to make a schedule for.
    //          * If they did not submit any preferences create a schedule for the full hours of the day
    //          */

    //         // interval of user submitted time(s)
    //         // ############################################################
    //         int Interval_HourStart;
    //         int Interval_MinStart;
    //         int Interval_HourEnd;
    //         int Interval_MinEnd;
    //         // ############################################################


    //         /**
    //          * Calculating times
    //          * This section calculates which people can meet in the selected time interval(s) for that day
    //          */
    //         if (UserPref_SpecificTimes == false) {              // user did not submit any times

    //             // iterate over each person with these default values
    //             Interval_HourStart  = 0;
    //             Interval_MinStart   = 0;
    //             Interval_HourEnd    = 23;
    //             Interval_MinEnd     = 59;

    //             // calculates schedule
    //             CalculatePerPerson(Interval_HourStart, Interval_MinStart, Interval_HourEnd, Interval_MinEnd, MaxSize, Pref_Day);

                
    //         } else if (UserPref_SpecificTimes == true) {        // User did submit times

    //             for (PREF_EMPLOYEE_TimePref TimeInputInterval : UserInput_UserTimes) {

    //                 // user input time intervals
    //                 Interval_HourStart  = TimeInputInterval.GetStartTimeHour();
    //                 Interval_MinStart   = TimeInputInterval.GetStartTimeMin();
    //                 Interval_HourEnd    = TimeInputInterval.GetEndTimeHour();
    //                 Interval_MinEnd     = TimeInputInterval.GetEndTimeMin();
                    
    //                 // calculates schedule
    //                 CalculatePerPerson(Interval_HourStart, Interval_MinStart, Interval_HourEnd, Interval_MinEnd, MaxSize, Pref_Day);

    //             } // for (PROG_DAL_A_TimeInput TimeInputInterval : UserInput_UserTimes)
                
    //         } // else if (UserPref_SpecificTimes == true)
            

    //     } // for (String Day : WeekDays)
    //     // ############################################################



    //     /**
    //      * All possbile lists with the selected user parameters have been created, it is ready to be used at this point.
    //      */
    //     System.out.println(PROG_DAL_D_SystemMessages.PASS_CalculateScheduleCompleteCalc);
    //     return 0;

    // } // CalculateSchedule()





    /**
     * CalculatePerPerson()
     * Description: runs calculations for the full list of people provided
     * segmeneted like this to remove duplicate code
     */
    private void CalculatePerPerson(int STARTHOUR, int STARTMIN, int ENDHOUR, int ENDMIN, int MAXSIZE, String PREFDAY) {

        /**
         * General process:
         * This function is called for each time interval a person wants scheduled during a specific day
         * 
         * Step 1. Determine all people who can meet during the input time interval
         * 
         * step 2. for all people who can meet within the interval, use the int arrays to mark the hours people can attend within that time frame
         * 
         * step 3. for all time ranges where people can meet, create a new list of people that can meet within each time range
         * 
         * step 4. for each new list of people created, create a new shcedule with that list and relevant time frame and add it to the total schedule list
         */

        // Available people who can be scheduled within a standard time interval
        CALC_AvailablePeople    = new LinkedList<>();


        // List of people who can be scheduled in a sub time Interval
        Calc_AvailableIDs       = new LinkedList<String>();


        /**
         * Prime Time Intervals
         * Either the default values for a day or the interval the user submitted
         */
        // ############################################################
        int Interval_HourStart  = STARTHOUR;
        int Interval_MinStart   = STARTMIN;
        int Interval_HourEnd    = ENDHOUR;
        int Interval_MinEnd     = ENDMIN;
        // ############################################################


        /**
         * User time interval defualt values
         * these values will be set to each unique time interval a person has for this specific day.
         */
        // ############################################################
        int PREF_BeginHour      = 0;
        int PREF_BeginMIN       = 0;;
        int PREF_EndHour        = 23;
        int PREF_EndMin         = 59;
        // ############################################################


        /**
         * Interval Arrays
         * These arrays act as interval trackers for the time frames people can meet during this specific day
         * 
         * RefinedHourRange: Each value represents an hour within the day 0-24, if anyone is available to meet on that hour, it is marked as 1.
         * 
         * RefinedMinStartRange: Each value represent the starting minute interval corresponding to the relevant hour in hour range. 
         * It holds the latest starting minute for that hour. i.e a value of 30 in int[7] means a valid meeting time can start at 7:30
         * 
         * RefinedMinEndRange: Each value represent the ending minute interval corresponding to the relevant hour in hour range. 
         * It holds the latest starting minute for that hour. i.e a value of 30 in int[15] means a valid meeting time ends at 15:30 (3:30 - standard time)
         */
        // ############################################################
        int[] RefinedHourRange      = new int[24];
        int[] RefinedMinStartRange  = new int[24];
        int[] RefinedMinEndRange    = new int[24];

        Arrays.fill(RefinedMinEndRange, 59);
        // ############################################################



        // Step 1 & 2:
        // For each interval the user has submitted, check who can be scheduled within that interval, saving both the person and the interval personal preference interval
        // that lies within the interval
        // ############################################################
        // BreakPerson1:   // BREAK
        for (int Position_Preference = 0; Position_Preference < Employee_FullList_RefinedArray.length; Position_Preference++) {
        //for (PREF_EMPLOYEE_FullPref Person : Employee_FullList_RefinedArray) {

            // for each person iterate through all time intervals they have for that specific day
            for (int Person_timeIntervalIndex = 0; Person_timeIntervalIndex < MAXSIZE; Person_timeIntervalIndex++) {

                // ensure the interval exists
                if (Employee_FullList_RefinedArray[Position_Preference].GetIntervals().length > Person_timeIntervalIndex) {

                    /**
                     * If the persons time preference lies on the selected interval, check it.
                     * if its valid, add it to the list of valid ids.
                     */
                    if (Employee_FullList_RefinedArray[Position_Preference].GetIntervals()[Person_timeIntervalIndex].GetWeekDay().equals(PREFDAY)) {
                        
                        // A persons preference contains a matching day
                        System.out.println(PROG_DAL_D_SystemMessages.INFO_CalculateSchedulePersonDay + Employee_FullList_RefinedArray[Position_Preference].GetIdent());
                        
                        // 7.a Find the latest start time of the earliest time interval from all people on that day
                        PREF_BeginHour  = Employee_FullList_RefinedArray[Position_Preference].GetIntervals()[Person_timeIntervalIndex].GetStartTimeHour();
                        PREF_BeginMIN   = Employee_FullList_RefinedArray[Position_Preference].GetIntervals()[Person_timeIntervalIndex].GetStartTimeMin();
                        PREF_EndHour    = Employee_FullList_RefinedArray[Position_Preference].GetIntervals()[Person_timeIntervalIndex].GetEndTimeHour();
                        PREF_EndMin     = Employee_FullList_RefinedArray[Position_Preference].GetIntervals()[Person_timeIntervalIndex].GetEndTimeMin();


                        // If persons interval lies between then they're added to the list of available people

                        // interval lies between the hours
                        if ( (PREF_EndHour > Interval_HourStart) || (PREF_BeginHour < Interval_HourEnd) ) {
                                
                            // System message
                            System.out.println(PROG_DAL_D_SystemMessages.INFO_CalculateScheduleIDAdded);
                            
                            // linkedlist of available people and the intervals that lie within the time frame
                            CALC_AvailablePeople.add(new MANAGESCHEDULE_Interval(Employee_FullList_RefinedArray[Position_Preference], Person_timeIntervalIndex));

                            // for each person
                            // marks a range of valid intervals with 1
                            for (int validHour = PREF_BeginHour; validHour <= PREF_EndHour; validHour++) {
                                RefinedHourRange[validHour] = 1;
                            }
                            
                            // sets the latest start time for this starting hour
                            if (RefinedMinStartRange[PREF_BeginHour] < PREF_BeginMIN) {
                                RefinedMinStartRange[PREF_BeginHour] = PREF_BeginMIN;
                            }
                            
                            // sets the earliest end time for this ending hour
                            if (RefinedMinEndRange[PREF_EndHour] > PREF_EndMin) {
                                RefinedMinEndRange[PREF_EndHour] = PREF_EndMin;
                            }

                        // if the starting hours are the same but ending hours arn't
                        } else if ( (PREF_EndHour == Interval_HourStart) || (PREF_BeginHour == Interval_MinStart) ) {

                            if ( (PREF_EndMin >= Interval_MinStart) || (PREF_BeginMIN <= Interval_MinEnd) ) {

                                // System message
                                System.out.println(PROG_DAL_D_SystemMessages.INFO_CalculateScheduleIDAdded);
                                
                                // linkedlist of available people and the intervals that lie within the time frame
                                CALC_AvailablePeople.add(new MANAGESCHEDULE_Interval(Employee_FullList_RefinedArray[Position_Preference], Person_timeIntervalIndex));

                                // for each person
                                // marks a range of valid intervals with 1
                                for (int validHour = PREF_BeginHour; validHour <= PREF_EndHour; validHour++) {
                                    RefinedHourRange[validHour] = 1;
                                }
                                
                                // sets the latest start time for this starting hour
                                if (RefinedMinStartRange[PREF_BeginHour] < PREF_BeginMIN) {
                                    RefinedMinStartRange[PREF_BeginHour] = PREF_BeginMIN;
                                }
                                
                                // sets the earliest end time for this ending hour
                                if (RefinedMinEndRange[PREF_EndHour] > PREF_EndMin) {
                                    RefinedMinEndRange[PREF_EndHour] = PREF_EndMin;
                                }

                            } // if ( (PREF_BeginMIN >= Interval_MinStart) || (PREF_EndMin <= Interval_MinEnd) ) 

                        } // else if ( (PREF_BeginHour == Interval_HourStart) || (PREF_EndHour > Interval_MinStart) )


                    } // if (Person.TimeIntervals.get(Person_timeIntervalIndex).WeekDay.equals(Pref_Day))

                } // if (Person.TimeIntervals.size() > Person_timeIntervalIndex)
            
            } // for (int Person_timeIntervalIndex = 0; Person_timeIntervalIndex < MaxSize; Person_timeIntervalIndex++)

        } // for (PROG_DAL_A_InfoInput Person : Calc_People)
        // ############################################################



        // Iterate through the list ensuring any marked times fall between the user entered interval
        // (int STARTHOUR, int STARTMIN, int ENDHOUR, int ENDMIN, int MAXSIZE, String PREFDAY)
        for (int index = 0; index < 24; index++) {

            if (index < STARTHOUR) {
                RefinedHourRange[index] = 0;
            }

            if (index > ENDHOUR) {
                RefinedHourRange[index] = 0;
            }

        }

        RefinedMinStartRange[STARTHOUR]     = STARTMIN;
        RefinedMinEndRange[ENDHOUR]         = ENDMIN;



        /**
         * Step 3&4 :
         * Refined times calculation
         * Determines intervals within user selected times that people can meet. Accounts for situations where
         * people can only meet for part of the meeting times
         */
        // ############################################################
        for (int HourRangeIndex = 0; HourRangeIndex < 24; HourRangeIndex++) {

            // general range where people can be scheduled
            // using the arrays that hold valid start times via marking hour positions as ones, these times can be retireved and stored in these
            // values to be compared against various people
            int CalculateInterval_HourStart    = 0;
            int CalculateInterval_MinStart     = 0;
            int CalculateInterval_HourEnd      = 0;
            int CalculateInterval_MinEnd       = 0;

            // each person preferred time
            int Person_BeginHour  = 0;
            int Person_BeginMIN   = 0;
            int Person_EndHour    = 0;
            int Person_EndMin     = 0;


            // when RefinedHourRange is one, that means an hour of the day contains at least one person who can be scheduled on it
            if (RefinedHourRange[HourRangeIndex] == 1) {


                // gets start times

                // HourRangeIndex is the starting hour
                CalculateInterval_HourStart = HourRangeIndex;
                // RefinedMinStartRange holds a minute value stored at the index positon HourRangeIndex
                CalculateInterval_MinStart = RefinedMinStartRange[HourRangeIndex];

                // safety ensures the while loop ends eventually
                int safety = 0;

                // loops throught the list of RefinedHourRange to find the end points available to schedule
                // each range of hours is marked as ones on RefinedHourRange. so each interval is an uninterrupted sequence of ones.
                // when the sequence no longer has any ones, the while loop stops and the calculations begin.
                // if there are more intervals the above for loop goes to the next one or until the hours in the day are up.
                while ( (RefinedHourRange[HourRangeIndex] == 1) && (safety < 24) ) {
                    
                    // retireves end hour number
                    CalculateInterval_HourEnd = HourRangeIndex;
                    // retrieves minute stored at RefinedMinEndRange at index position HourRangeIndex
                    CalculateInterval_MinEnd = RefinedMinEndRange[HourRangeIndex];

                    // loop through the index position until no more valid hours are found (RefinedHourRange[HourRangeIndex] == 1)
                    HourRangeIndex++;
                    // iterate to ensure while loop stops eventually and the hour position never exceeds 23, highest time is 23:59 or 11:59 pm
                    safety++;

                } // ( (RefinedHourRange[HourRangeIndex] == 1) && (safety < 25) )

                

                // calculate people within the new intervals
                // for each interval cheack if each person can be scheduled for that interval
                // ############################################################
                for (MANAGESCHEDULE_Interval AvailablePerson : CALC_AvailablePeople) { 

                    PREF_EMPLOYEE_FullPref Person_Available = AvailablePerson.getPerson();
                    int Interval = AvailablePerson.getInterval();


                    Person_BeginHour  = Person_Available.GetIntervals()[Interval].GetStartTimeHour();
                    Person_BeginMIN   = Person_Available.GetIntervals()[Interval].GetStartTimeMin();
                    Person_EndHour    = Person_Available.GetIntervals()[Interval].GetEndTimeHour();
                    Person_EndMin     = Person_Available.GetIntervals()[Interval].GetEndTimeMin();


                    // ensures if the datacrd times are within the user preference time, that the time shortens to that preference not vice versa
                    if (CalculateInterval_HourStart < Person_BeginHour) {
                        CalculateInterval_HourStart = Person_BeginHour;
                    }

                    if (CalculateInterval_HourStart == Person_BeginHour) {
                        if (CalculateInterval_MinStart < Person_BeginMIN) {
                            CalculateInterval_MinStart  = Person_BeginMIN;
                        }
                    }

                    if (CalculateInterval_HourEnd > Person_EndHour) {
                        CalculateInterval_HourEnd   = Person_EndHour;
                    }

                    if (CalculateInterval_HourEnd == Person_EndHour) {
                        if (CalculateInterval_MinEnd > Person_EndMin) {
                            CalculateInterval_MinEnd    = Person_EndMin;
                        }
                    }



                }
                // ############################################################


                

                Iterator<MANAGESCHEDULE_Interval> TimeIntervalIterator = CALC_AvailablePeople.iterator();

                while (TimeIntervalIterator.hasNext()) {

                    MANAGESCHEDULE_Interval Iterator_PERSON = TimeIntervalIterator.next();

                    PREF_EMPLOYEE_FullPref AvailablePerson = Iterator_PERSON.getPerson();

                    int Interval = Iterator_PERSON.getInterval();

                    Person_BeginHour  = AvailablePerson.GetIntervals()[Interval].GetStartTimeHour();
                    Person_BeginMIN   = AvailablePerson.GetIntervals()[Interval].GetStartTimeMin();
                    Person_EndHour    = AvailablePerson.GetIntervals()[Interval].GetEndTimeHour();
                    Person_EndMin     = AvailablePerson.GetIntervals()[Interval].GetEndTimeMin();



                    // check if a persons beginning time lies before the interval ends
                    // or if a persons ending time comes after the interval starts
                    if (Person_BeginHour < CalculateInterval_HourEnd)           {

                        Calc_AvailableIDs.add(Integer.toString(AvailablePerson.GetIdent()));


                    } else if ( (Person_BeginHour == CalculateInterval_HourEnd) && (Person_BeginMIN < CalculateInterval_MinEnd) )   {

                        Calc_AvailableIDs.add(Integer.toString(AvailablePerson.GetIdent()));

                    } else if (Person_EndHour > CalculateInterval_HourStart)    { 

                        Calc_AvailableIDs.add(Integer.toString(AvailablePerson.GetIdent()));

                    } else if ( (Person_EndHour == CalculateInterval_HourStart) && (Person_EndMin < CalculateInterval_MinStart) )   {

                        Calc_AvailableIDs.add(Integer.toString(AvailablePerson.GetIdent()));

                    }


                } // while (iterator.hasNext())


                // a copy of the calculated available ids to schedule
                LinkedList<String> Calc_AvailableIDsCOPY = new LinkedList<>(Calc_AvailableIDs);

                // iterator for the original linked list
                Iterator<String> duplicateIterator = Calc_AvailableIDs.iterator();

                // for loop over the copy linked list
                for (String PERSON_ID : Calc_AvailableIDsCOPY) {

                    // ammount of copies of ids
                    int Ammount = 0;

                    
                    // while iterator has next
                    while (duplicateIterator.hasNext()) {
                    
                        // iterator get next
                        String ListedPerson = duplicateIterator.next();

                        // if a persons id is in the list increment it by one
                        if (ListedPerson.equals(PERSON_ID)) {
                            Ammount++;
                        }

                        // if a person ever appears more than once, remove the extra copy.
                        if (Ammount > 1) {
                            duplicateIterator.remove();
                            Ammount = 1;
                        }
                    }

                }




                /**
                 * Submitting schedules
                 */

                // Check if the list contains the full ammount of people the user wants to schedule if true set Calc_IdealSchedule to true, false otherwise
                // ############################################################
                if (UserPref_IDsProvided == true) {

                    if (UserInput_PeopleList.equals(Calc_AvailableIDs)) {    // everyone the user wanted is on the list for this interval
                        Calc_IdealSchedule = true;
                    } else {                                                        // not everyone is on the list
                        Calc_IdealSchedule = false;
                    } // (UserInput_PeopleToSchedule.equals(Calc_AvailableIDs))

                } else {

                    // no users specified so no ideal schedule
                    Calc_IdealSchedule = false;

                } // (IDsProvided == true)
                // ############################################################


                


                // create a new schedule and add it to the full list of schedules.
                // only continue if a possible schedule has at least one person otherwise many garbage objects will be created
                // ############################################################
                if (Calc_AvailableIDs.size() > 0) {
                    
                    System.out.println("HourRange"      + Arrays.toString(RefinedHourRange));
                    System.out.println("startMinRange"  + Arrays.toString(RefinedMinStartRange));
                    System.out.println("EndMinRange"    + Arrays.toString(RefinedMinEndRange));
                    // Testing
                    System.out.println("Day:        " + PREFDAY);
                    System.out.println("startHour:  " + CalculateInterval_HourStart);
                    System.out.println("startMin:   " + CalculateInterval_MinStart);
                    System.out.println("endhour:    " + CalculateInterval_HourEnd);
                    System.out.println("endMinute:  " + CalculateInterval_MinEnd);
                    System.out.println("");

                    /**
                     * Step 11. create new TimeInput LinkedList containing this schedule
                     */
                    Calc_ViableSchedule = new PREF_EMPLOYEE_TimePref(PREFDAY, CalculateInterval_HourStart, CalculateInterval_MinStart, 
                        CalculateInterval_HourEnd, CalculateInterval_MinEnd);


                    /**
                     * Step 12. Add this new schedule to the ScheduleList (contains all createdSchedules)
                     */
                    Calc_FullScheduleList.add(new MANAGESCHEDULE_Schedule(PREFDAY, Calc_ViableSchedule, Calc_AvailableIDs, Calc_IdealSchedule));

                } // if (Calc_AvailableIDs.size() > 0)
                // ############################################################





            } // if (RefinedHourRange[HourRangeIndex] == 1)


        } // for (int HourRangeIndex = 0; HourRangeIndex < 24; HourRangeIndex++)
        // ############################################################


    } // CalculatePerPerson()




} // PROG_BLL_SchedulingCalculation {}
