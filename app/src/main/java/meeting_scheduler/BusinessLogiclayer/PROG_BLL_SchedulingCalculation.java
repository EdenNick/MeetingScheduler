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
         * Step 4. Iterate over each day of the week stored in the WeekDays String[], the string may chnage due to user preference so it must be 
         * iterated over using a for loop.
         */
        for (String Pref_Day : Pref_WeekDays) {



            /**
             * Step 5. For each day of the week the total ammount of timeintervals will be iterated over, because not all people will have the same ammount,
             * the maximum ammount of indexes will be used in the for loop to ensure everytime interval is iterated over.
             */
            for (int Index_TimeInterval = 0; Index_TimeInterval < MaxSize; Index_TimeInterval++) {                    // iterates through each index


                // List of people who can be scheduled in a time interval
                Calc_AvailableIDs       = new LinkedList<String>();


                // interval set to calculate the schedule around - needs to be reset for each set of time intervals otherwise it might miss some
                // set to specific interval if user preference is input,
                // continuously updated if not
                int Interval_HourStart  = 0;
                int Interval_MinStart   = 0;
                int Interval_HourEnd    = 23;
                int Interval_MinEnd     = 59;


                /**
                 * Step 6. iterate through all people in the People LinkedList, looking at only the index positon from step 5.
                 * checking first to see if the TimeInterval index is on the correct day. For all the intervals that are, store the latest time in that
                 * index and the earliest time in that index as within the start and end intervals for hours and minutes
                 */
                if (Pref_SpecificTimes == false) {

                    System.out.println(PROG_DAL_D_SystemMessages.INFO_CalculateScheduleNoTimePref);

                    for (PROG_DAL_A_InfoInput Person : Calc_People) {

                        try {

                            if (Person.TimeIntervals.get(Index_TimeInterval).WeekDay.equals(Pref_Day)) { // If the day is not correct the interval shouldn't be checked.

                                // 6.a find the latest start time of the earliest time interval from all people on that day
                                if (Person.TimeIntervals.get(Index_TimeInterval).PreferedHourBEGIN.getHour() > Interval_HourStart) {
                                    Interval_HourStart   = Person.TimeIntervals.get(Index_TimeInterval).PreferedHourBEGIN.getHour();       // HOUR
                                }

                                if (Person.TimeIntervals.get(Index_TimeInterval).PreferedHourBEGIN.getMinute() > Interval_MinStart) {
                                    Interval_MinStart    = Person.TimeIntervals.get(Index_TimeInterval).PreferedHourBEGIN.getMinute();     // MIN
                                }

                                // 6.b find the earliest end time from the same interval on that day
                                if (Person.TimeIntervals.get(Index_TimeInterval).PreferedHourEND.getHour() > Interval_HourEnd) {
                                    Interval_HourEnd     = Person.TimeIntervals.get(Index_TimeInterval).PreferedHourEND.getHour();         // HOUR
                                }

                                if (Person.TimeIntervals.get(Index_TimeInterval).PreferedHourEND.getMinute() > Interval_MinEnd) {
                                    Interval_MinEnd      = Person.TimeIntervals.get(Index_TimeInterval).PreferedHourEND.getMinute();       // MIN
                                }

                            }

                        } catch (Exception e) {

                            // try/catch exists due to index out of bounds. This is expected to happen as each list is dynamic in size
                            // TODO: look into possible solution for this to reduce complexity

                        }
                    
                    } // for()

                }
 
                // TODO: might need possible implementation of check to ensure start and end intervals are within bounds
            
                /**
                 * Step 7. For all people in the People LinkedList, check the time interval at that index, if it lies between the stored
                 * interval, add it to the list
                 */

                // variables that contain a persons time preference, resets each new interval
                int PREF_BeginHour;
                int PREF_BeginMIN;
                int PREF_EndHour;
                int PREF_EndMin;
                
                if (Pref_SpecificTimes == false) {

                    /**
                     * no user selected times, program calculates any possible interval set
                     */

                    for (PROG_DAL_A_InfoInput Person : Calc_People) { // 4. repeat this for all intervals that exist for that day

                        if (Person.TimeIntervals.get(Index_TimeInterval).WeekDay.equals(Pref_Day)) {
                            
                            // A persons preference contains a matching day
                            System.out.println(PROG_DAL_D_SystemMessages.INFO_CalculateSchedulePersonDay + Person.EmployeeID);
                            
                            // 7.a Find the latest start time of the earliest time interval from all people on that day
                            PREF_BeginHour  = Person.TimeIntervals.get(Index_TimeInterval).PreferedHourBEGIN.getHour();
                            PREF_BeginMIN   = Person.TimeIntervals.get(Index_TimeInterval).PreferedHourBEGIN.getMinute();
                            PREF_EndHour    = Person.TimeIntervals.get(Index_TimeInterval).PreferedHourEND.getHour();
                            PREF_EndMin     = Person.TimeIntervals.get(Index_TimeInterval).PreferedHourEND.getMinute();

                            // 7.b if persons interval lies between then its id is added to the list
                            if ( (PREF_BeginHour >= Interval_HourStart) && (PREF_BeginMIN >= Interval_MinStart) 
                                && (PREF_EndHour <= Interval_HourEnd) && (PREF_EndMin <= Interval_MinEnd)
                            ) {
                                Calc_AvailableIDs.add(String.valueOf(Person.EmployeeID));
                            } // if ()

                        } // if (Person.TimeIntervals.get(Index).WeekDay.equals(Day))

                    } // for (PROG_DAL_A_InfoInput Person : Calc_People)


                } else if (Pref_SpecificTimes == true) {

                    System.out.println(PROG_DAL_D_SystemMessages.INFO_CalculateScheduleTimePref);

                    /**
                     * User selected times, program calculates based off of provided intervals in UserSpecifiedTimes
                     */

                    for (PROG_DAL_A_TimeInput Interval : Pref_UserTimes) {

                        // user submitted times
                        Interval_HourStart  = Interval.PreferedHourBEGIN.getHour();
                        Interval_MinStart   = Interval.PreferedHourBEGIN.getMinute();
                        Interval_HourEnd    = Interval.PreferedHourEND.getHour();
                        Interval_MinEnd     = Interval.PreferedHourEND.getMinute();

                        
                        for (PROG_DAL_A_InfoInput Person : Calc_People) { // 4. repeat this for all intervals that exist for that day


                            if (Person.TimeIntervals.get(Index_TimeInterval).WeekDay.equals(Pref_Day)) {

                                // A persons preference contains a matching day
                                System.out.println(PROG_DAL_D_SystemMessages.INFO_CalculateSchedulePersonDay + Person.EmployeeID);

                                // 1. find the latest start time of the earliest time interval from all people on that day
                                // times from a persons file
                                PREF_BeginHour  = Person.TimeIntervals.get(Index_TimeInterval).PreferedHourBEGIN.getHour();
                                PREF_BeginMIN   = Person.TimeIntervals.get(Index_TimeInterval).PreferedHourBEGIN.getMinute();
                                PREF_EndHour    = Person.TimeIntervals.get(Index_TimeInterval).PreferedHourEND.getHour();
                                PREF_EndMin     = Person.TimeIntervals.get(Index_TimeInterval).PreferedHourEND.getMinute();


                                // System.out.println("Start hour: " + BeginHour + " " + StartIntervalHour);
                                // System.out.println("Start minute: " + BeginMIN + " " + StartIntervalMin);
                                // System.out.println("end hour: " + EndHour + " " + EndIntervalHour);
                                // System.out.println("end minute: " + EndMin + " " + EndIntervalMin);


                                // if persons interval lies between then its id is added to the list
                                // interval lies between the hours
                                if ( (PREF_BeginHour > Interval_HourStart) && (PREF_EndHour < Interval_HourEnd) ) {
                                    
                                    System.out.println(PROG_DAL_D_SystemMessages.INFO_CalculateScheduleIDAdded);
                                    Calc_AvailableIDs.add(String.valueOf(Person.EmployeeID));
                                
                                // if the starting hours are the same but ending hours arn't
                                } else if ( (PREF_BeginHour == Interval_HourStart) && (PREF_BeginMIN > Interval_MinStart) 
                                    && (PREF_EndHour < Interval_HourEnd) 
                                ) {

                                    System.out.println(PROG_DAL_D_SystemMessages.INFO_CalculateScheduleIDAdded);
                                    Calc_AvailableIDs.add(String.valueOf(Person.EmployeeID));
                                
                                // if the ending hours are the same but starting hours arn't
                                } else if ( (PREF_BeginHour > Interval_HourStart) && (PREF_EndHour == Interval_HourEnd) 
                                    && (PREF_EndMin <= Interval_MinEnd) 
                                ) {

                                    System.out.println(PROG_DAL_D_SystemMessages.INFO_CalculateScheduleIDAdded);
                                    Calc_AvailableIDs.add(String.valueOf(Person.EmployeeID));

                                // if both the starting and ending hours are the same
                                } else if ( (PREF_BeginHour == Interval_HourStart) && (PREF_BeginMIN >= Interval_MinStart) 
                                    && (PREF_EndHour == Interval_HourEnd) && (PREF_EndMin <= Interval_MinEnd) 
                                ) {

                                    System.out.println(PROG_DAL_D_SystemMessages.INFO_CalculateScheduleIDAdded);
                                    Calc_AvailableIDs.add(String.valueOf(Person.EmployeeID));

                                // if both the starting and ending hours are the same
                                   
                                }


                            } // if (Person.TimeIntervals.get(Index).WeekDay.equals(Day))

                        } // for (PROG_DAL_A_InfoInput Person : Calc_People)

                    } // for (PROG_DAL_A_TimeInput Interval : Pref_UserTimes)

                } else {
                    // should never get here
                    System.out.println(PROG_DAL_D_SystemMessages.ERROR_CalculateScheduleBoolean);
                    return 1;
                }
            
            
                /**
                 * Step 8. check if the people available to attend that schedule represent everyone the user wanted
                 * true if yes, false otherwise.
                 */
                if (IDsProvided == true) {
                    if (UserInput_PeopleToSchedule.equals(Calc_AvailableIDs)) {    // everyone the user wanted is on the list for this interval
                        Calc_IdealSchedule = true;
                    } else {                                        // not everyone is on the list
                        Calc_IdealSchedule = false;
                    }
                } else {
                    // no users specified so no ideal schedule
                    Calc_IdealSchedule = false;
                }


                // only continue if a possible schedule has at least one person
                // otherwise many garbage objects will be created
                if (Calc_AvailableIDs.size() > 0) {
                    /**
                     * Step 9. create new TimeInput LinkedList containing this schedule
                     */
                    System.out.println("Day: " + Pref_Day);
                    System.out.println("startHour: " + Interval_HourStart);
                    System.out.println("startMin: " + Interval_MinStart);
                    System.out.println("endhour: " + Interval_HourEnd);
                    System.out.println("endMinute: " + Interval_MinEnd);
                    Calc_ViableSchedule = new PROG_DAL_A_TimeInput(Pref_Day, Interval_HourStart, Interval_MinStart, Interval_HourEnd, Interval_MinEnd);


                    /**
                     * Step 10. Add this new schedule to the ScheduleList (contains all createdSchedules)
                     */
                    Calc_FullScheduleList.add(new PROG_DAL_A_Schedule(Pref_Day, Calc_ViableSchedule, Calc_AvailableIDs, Calc_IdealSchedule));
                
                }
            } // for (int Index = 0; Index <= MaxSize; Index++)


        } // for (String Day : WeekDays)



        /**
         * Step 11. All possbile lists with the selected user parameters have been created, it is ready to be used at this point.
         */

        System.out.println(PROG_DAL_D_SystemMessages.PASS_CalculateScheduleCompleteCalc);
        return 0;

    } // CalculateSchedule()





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


}
