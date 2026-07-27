/**
 * PROG_INFO_SchedulingCalculation
 * 
 * Description: Performs the calculations necessary to schedule a meeting between various individuals.
 * Options include:
 * scheduling everyone or specific people
 * scheduling on specifc days or anytime during the week
 * Scheduling a specific time or anytime during the day, with otions for different times for each day selected
 * Not scheduling meeting on specifed times or days.
 */

import java.util.LinkedList;

public class PROG_INFO_SchedulingCalculation {

    //private boolean             ScheduleEveryone        = true;         // True for scheduling everyone             - False for scheduling specifc people.
    //private boolean             AnyDayAnyTime           = true;         // True if day and time Don't matter        - False if they do


    private String[]                        WeekDays                    = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    private boolean                         OperationReady = false;     // If false, the full list of people are retrieved as default
    private LinkedList<String>              PeopleToSchedule;           // IncomingLinkedList of people the user wants scheduled, no operations should be performed on it
    private LinkedList<String>              IDList;                     // LinkedList that holds the list of available people for each viable interval.
    private LinkedList<PROG_INFO_InfoInput> People;                     // Linked list of object PROG_INFO_InfoInput which stores the card info for a persons preference.
    private LinkedList<PROG_INFO_Schedule>  ScheduleList;               // LinkedList of viableschedules and the people who can be in them
    private PROG_INFO_TimeInput             TimeInput;                  // Used to set viable time intervals in the ScheduleList LinkedList.



    /**
     * Constructor 1
     * Description: primary defualt constructor when there is no preference for who is being shceduled and when the meeting should occur.
     */
    public PROG_INFO_SchedulingCalculation() {

        // Values set as default
        this.WeekDays       = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        this.OperationReady = false;


        // ScheduleEveryone         = true;
        // AnyDayAnyTime            = true
        // PeopleToSchedule         = null;
        // WeekDays                 = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        // perationReady            = false;

    }



    /**
     * Constructor 2
     * Description: Constructor used when a specifc number of people are to be scheduled but there is no preference for the date or time.
     * @param people
     */
    public PROG_INFO_SchedulingCalculation(LinkedList<String> people) {

        //this.ScheduleEveryone       = false;
        // this.AnyDayANDTime       = true;
        this.PeopleToSchedule       = new LinkedList<String>(people);
        this.OperationReady         = true;

    }



    /**
     * Cosntructor 3
     * Description: Constructor used when a specific set of days are selected and there is no preference for people.
     * @param days
     */
    public PROG_INFO_SchedulingCalculation(String[] days) {

        // this.ScheduleEveryone    = true;
        //this.AnyDayAnyTime          = false;
        this.WeekDays               = days.clone();
        this.OperationReady         = false;

    }



    /**
     * Constructor 4
     * Description: Consturctor used when there is a specifc number of people specified, and the date and time matter.
     * @param people
     * @param days
     */
    public PROG_INFO_SchedulingCalculation(LinkedList<String> people, String[] days) {

        //this.ScheduleEveryone       = false;
        //this.AnyDayAnyTime          = false;
        this.PeopleToSchedule       = new LinkedList<String>(people);
        this.WeekDays               = days.clone();
        this.OperationReady         = true;

    }



    /**
     * UpdatePreferences()
     * Description: updates user preferences through this method, used in order to prevent having to reinitialize this object each time it changes
     */
    public void UpdatePreferences() {

    }


    /**
     * RetrieveUserCards()
     * Description: retrieves the user cards to be stored as a linked list of objects (People)
     */
    private void RetrieveUserCards() {

        People = new LinkedList<PROG_INFO_InfoInput>();

        if (OperationReady == true) {                                       // A linkedlist of user ids was provided iterate through those
            // iterates over the linked list string of people to select
            for (String Person : PeopleToSchedule) {
                // TODO: Implement json
            }
        } else {                                                            // A LinkedList of user ids was NOT provided, retrieve all info from the json file
            
            People = new LinkedList<PROG_INFO_InfoInput>();
            // TODO: Implement json

        }
    }


    /**
     * CalculateSchedule()
     * Description: calculates the schedules
     */
    private int CalculateSchedule() {

        RetrieveUserCards();                                        // retrieves user info

        ScheduleList    = new LinkedList<PROG_INFO_Schedule>();     // List of possible schedules
        IDList          = new LinkedList<String>();                 // List of people who can be scheduled in a time interval

        for (int day = 0; day < 7; day++) {                         // for each day of the week find the total ammount of people that can meet. 0 = Sun, 6 = Sat

            int IndexAmmount = 100;
            // iterates over the list storing the smallest index size
            // this prevents exceptions later from geting indexes that don't exist
            for (PROG_INFO_InfoInput Person : People) {
                if (Person.TimeIntervals.size() < IndexAmmount) {
                    IndexAmmount = Person.TimeIntervals.size();
                }
            }


            int position = 0;

            while (position < IndexAmmount) {

                int StartIntervalHour   = -1;
                int StartIntervalMin    = -1;
                int EndIntervalHour     = 24;
                int EndIntervalMin      = 60;



                for (PROG_INFO_InfoInput Person : People) { // 4. repeat this for all intervals that exist for that day

                    // 1. find the latest start time of the earliest time interval from all people on that day
                    if (Person.TimeIntervals.get(position).PreferedHourBEGIN.getHour() > StartIntervalHour) {
                        StartIntervalHour   = Person.TimeIntervals.get(position).PreferedHourBEGIN.getHour();       // HOUR
                    }

                    if (Person.TimeIntervals.get(position).PreferedHourBEGIN.getMinute() > StartIntervalMin) {
                        StartIntervalMin    = Person.TimeIntervals.get(position).PreferedHourBEGIN.getMinute();     // MIN
                    }




                    // 2. find the earliest end time from the same interval on that day
                    if (Person.TimeIntervals.get(position).PreferedHourEND.getHour() > EndIntervalHour) {
                        EndIntervalHour     = Person.TimeIntervals.get(position).PreferedHourEND.getHour();         // HOUR
                    }

                    if (Person.TimeIntervals.get(position).PreferedHourEND.getMinute() > EndIntervalMin) {
                        EndIntervalMin      = Person.TimeIntervals.get(position).PreferedHourEND.getMinute();       // MIN
                    }

                } // for()




                // 3. store the total number of people that fall within that interval
                for (PROG_INFO_InfoInput Person : People) { // 4. repeat this for all intervals that exist for that day

                    // 1. find the latest start time of the earliest time interval from all people on that day
                    int BeginHour   = Person.TimeIntervals.get(position).PreferedHourBEGIN.getHour();
                    int BeginMIN    = Person.TimeIntervals.get(position).PreferedHourBEGIN.getMinute();
                    int EndHour     = Person.TimeIntervals.get(position).PreferedHourEND.getHour();
                    int EndMin      = Person.TimeIntervals.get(position).PreferedHourEND.getMinute();

                    // if persons interval lies between then its id is added to the list
                    if ((BeginHour <= StartIntervalHour) && (BeginMIN <= StartIntervalMin) && (EndHour >= EndIntervalHour) && (EndMin >= EndIntervalMin)) {
                        IDList.add(String.valueOf(Person.EmployeeID));
                    }

                } // for()


                if (PeopleToSchedule.equals(IDList)) {      // everyone the user wanted is on the list for this interval
                    OperationReady = true;
                } else {                                    // not everyone is on the list
                    OperationReady = false;
                }


                TimeInput = new PROG_INFO_TimeInput(WeekDays[position], StartIntervalHour, StartIntervalMin, EndIntervalHour, EndIntervalMin);

                // 6. if any of these lists contain all the specified people, mark that list
                ScheduleList.add(new PROG_INFO_Schedule(WeekDays[position], TimeInput, IDList, OperationReady));
                
                
                position++;

            } // while()

        } // for()


        // 5. discard all lists beside the one with the most ammount of people, if there are more than one with the same ammount store all of them.
        int ScheduleListSize = ScheduleList.size();

        boolean FullList = false;

        for (int position = 0; position < ScheduleListSize; position++) {
            
            if (ScheduleList.get(position).Schedule == true) {
                FullList = true;
            }

        } // for()


        // TODO: add mechanism for user to interact with this process desciding how many lists to keep, what lsits, etc.

        if (FullList == true) {
            // 7. iterate through all lists focusing first on those with the identifier
            // Discard all lists where .schedule == false
        } else {
            // 8. if none with the identfier are found look for the top lists (user ammount specified) based on quantity of people
            // sort through lists keeping only at most the top three if possible
        }


        // 9. final set of lists



        return 0;
    }



    /**
     * RetrieveSchedule()
     * Description: public method called in order to retrieve the calculated schedule times
     */
    public LinkedList<PROG_INFO_Schedule> RetrieveSchedule() {
        return ScheduleList;
    }

}
