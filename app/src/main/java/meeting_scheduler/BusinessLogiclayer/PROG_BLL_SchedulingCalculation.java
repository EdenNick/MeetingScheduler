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
package meeting_scheduler.BusinessLogiclayer;
// ############################################################

// Imports
// ############################################################
// exception
import java.io.IOException;
import java.util.Arrays;
// util
import java.util.LinkedList;
// jackson - json file manager
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
// data manager Objects
import meeting_scheduler.DataAccessLayer.PROG_DAL_A_InfoInput;
import meeting_scheduler.DataAccessLayer.PROG_DAL_A_Schedule;
import meeting_scheduler.DataAccessLayer.PROG_DAL_A_TimeInput;
import meeting_scheduler.DataAccessLayer.PROG_DAL_B_JSONManager;
// System Messages
import meeting_scheduler.DataAccessLayer.PROG_DAL_D_SystemMessages;
// ############################################################



public class PROG_BLL_SchedulingCalculation {



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
     * User Submitted variables for calculation
     */
    private LinkedList<String>                  UserInput_PeopleToSchedule; // IncomingLinkedList of people the user wants scheduled, no operations should be performed on it.
    private boolean                             IDsProvided        = false; // If false, a list of people to schedule was not provided, false as default.


    /**
     * User Preferences for schedule calculation
     */
    private String[]                            Pref_WeekDays;              // Days the user wants a schedule for, defaults to the whole week.
    private LinkedList<PROG_DAL_A_TimeInput>    Pref_UserTimes;             // LinkedList containing the specified times of the user
    private boolean                             Pref_SpecificTimes = false; // boolean if the user wants specified time intervals.


    /**
     * Program calculation variables
     */
    private LinkedList<String>                  Calc_AvailableIDs;          // LinkedList that holds the list of available people for each viable interval.

    private LinkedList<PROG_DAL_A_InfoInput>    CALC_AvailablePeople;
    private LinkedList<PROG_DAL_A_InfoInput>    Calc_People;                // Linked list of object PROG_INFO_InfoInput which stores the card info for a persons preference.
    private boolean                             Calc_PeopleSet     = false; // false if the linkedlist peopele has not been set. false as default. 

    private PROG_DAL_A_TimeInput                Calc_ViableSchedule;        // Used to set viable time intervals in the ScheduleList LinkedList.
    private boolean                             Calc_IdealSchedule = false; // denotes if a time interval in ScheduleList contians everyone the user wants scheduled;

    private LinkedList<PROG_DAL_A_Schedule>     Calc_FullScheduleList;      // LinkedList of viableschedules and the people who can be in them.


    /**
     * File data info and management
     */
    private PROG_DAL_B_JSONManager              JsonFileManager = new PROG_DAL_B_JSONManager();
    private LinkedList<PROG_DAL_A_InfoInput>    PeopleFromFile;             // All datacards contained within the relavant Json File.


    /**
     * Constructor 1
     * Description: primary defualt constructor when there is no preference for who is being shceduled and when the meeting should occur.
     */
    public PROG_BLL_SchedulingCalculation() {

        // Values set as default
        this.Pref_WeekDays              = new String[] {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        this.IDsProvided                = false;

    }





    /**
     * ResetALLPreferences()
     * Description: Resets all User preferences to default
     */
    public void ResetALLPreferences() {

        // Values set as default
        this.Pref_WeekDays              = new String[] {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        this.IDsProvided                = false;
        this.Pref_SpecificTimes         = false;

    }





    /**
     * UpdatePeopleToSchedule()
     * Description: Used to update the list of people being used in the calculation
     * @param people
     */
    public void UpdatePeopleToSchedule(LinkedList<String> people) {

        this.UserInput_PeopleToSchedule = new LinkedList<String>(people);
        this.IDsProvided                = true;

    }





    /**
     * 
     * @param days
     */
    /**
     * UpdatePeopleToSchedule()
     * Description: Used to update the list of people being used in the calculation
     * @param people
     */
    public void ResetPeopleToSchedule() {

        this.UserInput_PeopleToSchedule = new LinkedList<String>();
        this.IDsProvided                = false;

    }





    /**
     * UpdateWeekDays()
     * Description: Updates the user selected days they want to schedule a meeting on
     * @param days
     */
    public void UpdateWeekDays(String[] days) {

        this.Pref_WeekDays              = days.clone();

    }





    /**
     * SetSpecificTime()
     * Description: sets the program to find the people who can meet in specified intervals provided by the user
     * @param time
     */
    public void SetSpecificTime(LinkedList<PROG_DAL_A_TimeInput> time) {
        
        this.Pref_SpecificTimes         = true;
        this.Pref_UserTimes             = new LinkedList<PROG_DAL_A_TimeInput>(time);

        System.out.println("Specific times size: " + this.Pref_UserTimes.size());

    }





    /**
     * SetNonSpecificTime()
     * Description: sets the program to find all possible times within the given day, sets UserSpecifiedTimes to null for garbage collection
     */
    public void SetNonSpecificTime() {

        this.Pref_SpecificTimes         = false;
        this.Pref_UserTimes             = null;
    }



    /**
     * RetrieveSchedule()
     * Description: public method called in order to pass ScheduleList outside the class
     */
    public LinkedList<PROG_DAL_A_Schedule> RetrieveSchedule() {

        try {
            CalculateSchedule();
        } catch (IOException e) {
            System.out.println("ERROR - PROG_INFO_SchedulingCalculation - RetrieveSchedule - CalculateSchedule");
            e.printStackTrace();
        }
        
        return Calc_FullScheduleList;
    }



    /**
     * RetrieveUserCards()
     * Description: retrieves the user cards to be stored as a linked list of objects (People)
     * @throws IOException 
     * @throws DatabindException 
     * @throws StreamReadException 
     */
    private void RetrieveUserCards() throws StreamReadException, DatabindException, IOException {

        // Calc_People - calculated list of people the user wants scheduled based on their input fromm UserInput_PeopleToSchedule
        Calc_People = new LinkedList<PROG_DAL_A_InfoInput>();

        // retrieves the latest list of datacards from the relevant Json file.
        JsonFileManager.RetrieveFromFile();
        
        // PeopleFromFile is a new linkedlist containing a copy of the retrieved json file data calculated from JsonFileManager.RetrieveFromFile();
        PeopleFromFile = new LinkedList<PROG_DAL_A_InfoInput>(JsonFileManager.ReturnFile());



        // A linkedlist of user ids was provided iterate through those
        if (IDsProvided == true) {

            for (PROG_DAL_A_InfoInput FilePerson : PeopleFromFile) {
                // iterates over the linked list string of people to select
                for (String IDOfPerson : UserInput_PeopleToSchedule) {
                    
                    if(FilePerson.EmployeeID == Integer.parseInt(IDOfPerson)) {
                        Calc_People.add(FilePerson);
                    }

                }
            }

        // A LinkedList of user ids was NOT provided, retrieve all info from the json file
        } else {
            
            for (PROG_DAL_A_InfoInput FilePerson : PeopleFromFile) {
                // iterates over the linked list string of people to select
                Calc_People.add(FilePerson);

            }

        }

        // Calc_People is set containing a list of the people to schedule
        Calc_PeopleSet = true;

    }





    /**
     * CalculateSchedule()
     * Description: calculates the schedules based on user preference
     * Its segmented into steps for readablility
     * @throws IOException 
     * @throws DatabindException 
     * @throws StreamReadException 
     */
    private int CalculateSchedule() throws StreamReadException, DatabindException, IOException {

        // General System Info
        System.out.println("");
        System.out.println(PROG_DAL_D_SystemMessages.INFO_CalculateScheduleStartCalc);
        System.out.println("");
        System.out.println(PROG_DAL_D_SystemMessages.INFO_CalculateScheduleTimePref + Pref_SpecificTimes);;
        System.out.println("");

        /**
         * Step 1. Local private LinkedLists are created to store the total list of possible schedules and the list of people who can be scheduled for a specific interval
         */
        Calc_FullScheduleList   = new LinkedList<PROG_DAL_A_Schedule>();     // List of possible schedules


        /**
         * Step 2. LinkedList variable People is created and set with retireved user card information to be used in calculations, a check is sued to ensure this happened
         */
        RetrieveUserCards(); // retrieves user info
        if (Calc_PeopleSet == false) {
            System.out.println(PROG_DAL_D_SystemMessages.ERROR_CalculateSchedulePeopleSet);
            return 1;
        }

        
        /**
         * Step 3. For each Person in the People linkedlist the size of the timeIntervals LinkedList each stores is found, the size of the largest one is kept
         * This ensures all indexes in each TimeIntervals LinkedList is covered.
         */
        int MaxSize = 0;
        for (PROG_DAL_A_InfoInput Person : Calc_People) {
            if (Person.TimeIntervals.size() > MaxSize) {
                MaxSize = Person.TimeIntervals.size();
            }

        } // for ()


        /**
         * Step 4.
         * Iterate over each day the user selected, at least one at most every day of the week
         */
        // ############################################################
        for (String Pref_Day : Pref_WeekDays) {



            /**
             * Step 5.
             * for each day iterate through every user selected time inteval they want to make a schedule for.
             * If they did not submit any preferences create a schedule for the full hours of the day
             */

            // interval of user submitted time(s)
            // ############################################################
            int Interval_HourStart;
            int Interval_MinStart;
            int Interval_HourEnd;
            int Interval_MinEnd;
            // ############################################################


            /**
             * Calculating times
             * This section calculates which people can meet in the selected time interval(s) for that day
             */
            if (Pref_SpecificTimes == false) {              // user did not submit any times

                // iterate over each person with these default values
                Interval_HourStart  = 0;
                Interval_MinStart   = 0;
                Interval_HourEnd    = 23;
                Interval_MinEnd     = 59;

                // calculates schedule
                CalculatePerPerson(Interval_HourStart, Interval_MinStart, Interval_HourEnd, Interval_MinEnd, MaxSize, Pref_Day);

                
            } else if (Pref_SpecificTimes == true) {        // User did submit times

                for (PROG_DAL_A_TimeInput TimeInputInterval : Pref_UserTimes) {

                    // user input time intervals
                    Interval_HourStart  = TimeInputInterval.PreferedHourBEGIN   .getHour();
                    Interval_MinStart   = TimeInputInterval.PreferedHourBEGIN   .getMinute();
                    Interval_HourEnd    = TimeInputInterval.PreferedHourEND     .getHour();
                    Interval_MinEnd     = TimeInputInterval.PreferedHourEND     .getMinute();
                    
                    // calculates schedule
                    CalculatePerPerson(Interval_HourStart, Interval_MinStart, Interval_HourEnd, Interval_MinEnd, MaxSize, Pref_Day);

                } // for (PROG_DAL_A_TimeInput TimeInputInterval : Pref_UserTimes)
                
            } // else if (Pref_SpecificTimes == true)
            

        } // for (String Day : WeekDays)
        // ############################################################



        /**
         * Step 11. All possbile lists with the selected user parameters have been created, it is ready to be used at this point.
         */
        System.out.println("");
        System.out.println(PROG_DAL_D_SystemMessages.PASS_CalculateScheduleCompleteCalc);
        System.out.println("");
        return 0;

    } // CalculateSchedule()





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



        // TODO: change system messages
        // Step 1 & 2:
        // for each interval cheack if each person can be scheduled for that interval
        // ############################################################
        // BreakPerson1:   // BREAK
        for (PROG_DAL_A_InfoInput Person : Calc_People) {

            // for each person iterate through all time intervals they have for that specific day
            for (int Person_timeIntervalIndex = 0; Person_timeIntervalIndex < MAXSIZE; Person_timeIntervalIndex++) {

                // ensure the interval exists
                if (Person.TimeIntervals.size() > Person_timeIntervalIndex) {

                    /**
                     * Step 6.
                     * If the persons time preference lies on the selected interval, check it.
                     * if its valid, add it to the lsit of valid ids.
                     */
                    if (Person.TimeIntervals.get(Person_timeIntervalIndex).WeekDay.equals(PREFDAY)) {
                        
                        // A persons preference contains a matching day
                        System.out.println(PROG_DAL_D_SystemMessages.INFO_CalculateSchedulePersonDay + Person.EmployeeID);
                        
                        // 7.a Find the latest start time of the earliest time interval from all people on that day
                        PREF_BeginHour  = Person.TimeIntervals.get(Person_timeIntervalIndex).PreferedHourBEGIN.getHour();
                        PREF_BeginMIN   = Person.TimeIntervals.get(Person_timeIntervalIndex).PreferedHourBEGIN.getMinute();
                        PREF_EndHour    = Person.TimeIntervals.get(Person_timeIntervalIndex).PreferedHourEND.getHour();
                        PREF_EndMin     = Person.TimeIntervals.get(Person_timeIntervalIndex).PreferedHourEND.getMinute();

                        // Step 7.
                        // If persons interval lies between then its id is added to the list

                        // interval lies between the hours
                        if ( (PREF_BeginHour > Interval_HourStart) && (PREF_EndHour < Interval_HourEnd) ) {
                                
                            System.out.println(PROG_DAL_D_SystemMessages.INFO_CalculateScheduleIDAdded);
                            //Calc_AvailableIDs.add(String.valueOf(Person.EmployeeID));
                            CALC_AvailablePeople.add(Person);

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
                            
                            // break BreakPerson1;
                            
                        // if the starting hours are the same but ending hours arn't
                        } else if ( (PREF_BeginHour == Interval_HourStart) && (PREF_BeginMIN > Interval_MinStart) 
                            && (PREF_EndHour < Interval_HourEnd) 
                        ) {

                            System.out.println(PROG_DAL_D_SystemMessages.INFO_CalculateScheduleIDAdded);
                            //Calc_AvailableIDs.add(String.valueOf(Person.EmployeeID));
                            CALC_AvailablePeople.add(Person);

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

                            // break BreakPerson1;
                            
                        // if the ending hours are the same but starting hours arn't
                        } else if ( (PREF_BeginHour > Interval_HourStart) && (PREF_EndHour == Interval_HourEnd) 
                            && (PREF_EndMin <= Interval_MinEnd) 
                        ) {

                            System.out.println(PROG_DAL_D_SystemMessages.INFO_CalculateScheduleIDAdded);
                            //Calc_AvailableIDs.add(String.valueOf(Person.EmployeeID));
                            CALC_AvailablePeople.add(Person);

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

                            // break BreakPerson1;

                        // if both the starting and ending hours are the same
                        } else if ( (PREF_BeginHour == Interval_HourStart) && (PREF_BeginMIN >= Interval_MinStart) 
                            && (PREF_EndHour == Interval_HourEnd) && (PREF_EndMin <= Interval_MinEnd) 
                        ) {

                            System.out.println(PROG_DAL_D_SystemMessages.INFO_CalculateScheduleIDAdded);
                            //Calc_AvailableIDs.add(String.valueOf(Person.EmployeeID));
                            CALC_AvailablePeople.add(Person);

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

                            // break BreakPerson1;

                        }


                    } // if (Person.TimeIntervals.get(Person_timeIntervalIndex).WeekDay.equals(Pref_Day))

                } // if (Person.TimeIntervals.size() > Person_timeIntervalIndex)
            
            } // for (int Person_timeIntervalIndex = 0; Person_timeIntervalIndex < MaxSize; Person_timeIntervalIndex++)

        } // for (PROG_DAL_A_InfoInput Person : Calc_People)
        // ############################################################





        /**
         * Step 3 & 4:
         * Refined times calculation
         * Determines intervals within user selected times that people can meet. Accounts for situations where
         * people can only meet for part of the meeting times
         */
        // ############################################################
        for (int HourRangeIndex = 0; HourRangeIndex < 24; HourRangeIndex++) {


            Calc_AvailableIDs       = new LinkedList<String>();


            int CalculatedInterval_HourStart    = 0;
            int CalculatedInterval_MinStart     = 0;
            int CalculatedInterval_HourEnd      = 0;
            int CalculatedInterval_MinEnd       = 0;


            if (RefinedHourRange[HourRangeIndex] == 1) {

                // gets start times
                CalculatedInterval_HourStart = HourRangeIndex;
                CalculatedInterval_MinStart = RefinedMinStartRange[HourRangeIndex];

                // safety ensures the while loop ends eventually
                int safety = 0;
                while ( (RefinedHourRange[HourRangeIndex] == 1) && (safety < 25) ){
                    CalculatedInterval_HourEnd = HourRangeIndex;
                    CalculatedInterval_MinEnd = RefinedMinEndRange[HourRangeIndex];


                    HourRangeIndex++;
                    safety++;
                }


                // calculate people within the new intervals
                // for each interval cheack if each person can be scheduled for that interval
                // ############################################################
                BreakPerson2:   // BREAK
                for (PROG_DAL_A_InfoInput AvailablePerson : CALC_AvailablePeople) { 

                    // for each person iterate through all time intervals they have for that specific day
                    for (int Person_timeIntervalIndex = 0; Person_timeIntervalIndex < MAXSIZE; Person_timeIntervalIndex++) {

                        // ensure the interval exists
                        if (AvailablePerson.TimeIntervals.size() > Person_timeIntervalIndex) {

                            /**
                             * Step 6.
                             * If the persons time preference lies on the selected interval, check it.
                             * if its valid, add it to the lsit of valid ids.
                             */
                            if (AvailablePerson.TimeIntervals.get(Person_timeIntervalIndex).WeekDay.equals(PREFDAY)) {
                                
                                // A persons preference contains a matching day
                                System.out.println(PROG_DAL_D_SystemMessages.INFO_CalculateSchedulePersonDay + AvailablePerson.EmployeeID);
                                
                                // 7.a Find the latest start time of the earliest time interval from all people on that day
                                PREF_BeginHour  = AvailablePerson.TimeIntervals.get(Person_timeIntervalIndex).PreferedHourBEGIN.getHour();
                                PREF_BeginMIN   = AvailablePerson.TimeIntervals.get(Person_timeIntervalIndex).PreferedHourBEGIN.getMinute();
                                PREF_EndHour    = AvailablePerson.TimeIntervals.get(Person_timeIntervalIndex).PreferedHourEND.getHour();
                                PREF_EndMin     = AvailablePerson.TimeIntervals.get(Person_timeIntervalIndex).PreferedHourEND.getMinute();

                                // Step 7.
                                // If persons interval lies between then its id is added to the list

                                // interval lies between the hours
                                if ( (PREF_BeginHour > Interval_HourStart) && (PREF_EndHour < Interval_HourEnd) ) {
                                        
                                    System.out.println(PROG_DAL_D_SystemMessages.INFO_CalculateScheduleIDAdded);
                                    Calc_AvailableIDs.add(String.valueOf(AvailablePerson.EmployeeID));

                                    break BreakPerson2;
                                    
                                // if the starting hours are the same but ending hours arn't
                                } else if ( (PREF_BeginHour == Interval_HourStart) && (PREF_BeginMIN > Interval_MinStart) 
                                    && (PREF_EndHour < Interval_HourEnd) 
                                ) {

                                    System.out.println(PROG_DAL_D_SystemMessages.INFO_CalculateScheduleIDAdded);
                                    Calc_AvailableIDs.add(String.valueOf(AvailablePerson.EmployeeID));

                                    break BreakPerson2;
                                    
                                // if the ending hours are the same but starting hours arn't
                                } else if ( (PREF_BeginHour > Interval_HourStart) && (PREF_EndHour == Interval_HourEnd) 
                                    && (PREF_EndMin <= Interval_MinEnd) 
                                ) {

                                    System.out.println(PROG_DAL_D_SystemMessages.INFO_CalculateScheduleIDAdded);
                                    Calc_AvailableIDs.add(String.valueOf(AvailablePerson.EmployeeID));

                                    break BreakPerson2;

                                // if both the starting and ending hours are the same
                                } else if ( (PREF_BeginHour == Interval_HourStart) && (PREF_BeginMIN >= Interval_MinStart) 
                                    && (PREF_EndHour == Interval_HourEnd) && (PREF_EndMin <= Interval_MinEnd) 
                                ) {

                                    System.out.println(PROG_DAL_D_SystemMessages.INFO_CalculateScheduleIDAdded);
                                    Calc_AvailableIDs.add(String.valueOf(AvailablePerson.EmployeeID));

                                    break BreakPerson2;

                                }


                            } // if (Person.TimeIntervals.get(Person_timeIntervalIndex).WeekDay.equals(Pref_Day))

                        } // if (Person.TimeIntervals.size() > Person_timeIntervalIndex)
                    
                    } // for (int Person_timeIntervalIndex = 0; Person_timeIntervalIndex < MaxSize; Person_timeIntervalIndex++)

                } // for (PROG_DAL_A_InfoInput Person : Calc_People)
                // ############################################################





                /**
                 * Submitting schedules
                 */

                // Check if the list contains the full ammount of people the user wants to schedule if true set Calc_IdealSchedule to true, false otherwise
                // ############################################################
                if (IDsProvided == true) {

                    if (UserInput_PeopleToSchedule.equals(Calc_AvailableIDs)) {    // everyone the user wanted is on the list for this interval
                        Calc_IdealSchedule = true;
                    } else {                                                        // not everyone is on the list
                        Calc_IdealSchedule = false;
                    } // (UserInput_PeopleToSchedule.equals(Calc_AvailableIDs))

                } else {

                    // no users specified so no ideal schedule
                    Calc_IdealSchedule = false;

                } // (IDsProvided == true)
                // ############################################################


                


                // step 9.
                // create a new schedule and add it to the full list of schedules.
                // only continue if a possible schedule has at least one person otherwise many garbage objects will be created
                // ############################################################
                if (Calc_AvailableIDs.size() > 0) {
                    
                    System.out.println("HourRange"      + Arrays.toString(RefinedHourRange));
                    System.out.println("startMinRange"  + Arrays.toString(RefinedMinStartRange));
                    System.out.println("EndMinRange"    + Arrays.toString(RefinedMinEndRange));
                    // Testing
                    System.out.println("Day:        " + PREFDAY);
                    System.out.println("startHour:  " + CalculatedInterval_HourStart);
                    System.out.println("startMin:   " + CalculatedInterval_MinStart);
                    System.out.println("endhour:    " + CalculatedInterval_HourEnd);
                    System.out.println("endMinute:  " + CalculatedInterval_MinEnd);
                    System.out.println("");

                    /**
                     * Step 11. create new TimeInput LinkedList containing this schedule
                     */
                    Calc_ViableSchedule = new PROG_DAL_A_TimeInput(PREFDAY, CalculatedInterval_HourStart, CalculatedInterval_MinStart, 
                        CalculatedInterval_HourEnd, CalculatedInterval_MinEnd);


                    /**
                     * Step 12. Add this new schedule to the ScheduleList (contains all createdSchedules)
                     */
                    Calc_FullScheduleList.add(new PROG_DAL_A_Schedule(PREFDAY, Calc_ViableSchedule, Calc_AvailableIDs, Calc_IdealSchedule));

                } // if (Calc_AvailableIDs.size() > 0)
                // ############################################################


            } // if (RefinedHourRange[HourRangeIndex] == 1)


        } // for (int HourRangeIndex = 0; HourRangeIndex < 24; HourRangeIndex++)
        // ############################################################


    } // CalculatePerPerson()




} // PROG_BLL_SchedulingCalculation {}
