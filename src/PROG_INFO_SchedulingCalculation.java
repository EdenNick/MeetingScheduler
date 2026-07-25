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

    private boolean             ScheduleEveryone        = true;         // True for scheduling everyone             - False for scheduling specifc people.
    private boolean             AnyDayAnyTime           = true;         // True if day and time Don't matter        - False if they do
    private LinkedList<String>  PeopleToSchedule;                       // stores user id of people to be scheduled
    private String[]            WeekDays                = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

    private boolean             OperationReady          = false;        // variable use to prevent unwanted operations on PeopleLinkedList. if false, the full list of people are retrieved as default
    private LinkedList<PROG_INFO_InfoInput> People;                     // Linked list of object PROG_INFO_InfoInput which stores the card info for a persons preference.
    private LinkedList<PROG_INFO_Schedule>  ScheduleList = new LinkedList<PROG_INFO_Schedule>();
    private PROG_INFO_Schedule              Schedule;


    /**
     * Constructor 1
     * Description: primary defualt constructor when there is no preference for who is being shceduled and when the meeting should occur.
     */
    public PROG_INFO_SchedulingCalculation() {

        // Values remain as default no action taken
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

        this.ScheduleEveryone       = false;
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
        this.AnyDayAnyTime          = false;
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

        this.ScheduleEveryone       = false;
        this.AnyDayAnyTime          = false;
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

        RetrieveUserCards(); // retrieves user info

        //for (PROG_INFO_InfoInput PERSON : People) {
        //}


        // for each day of the week find the total ammount of people that can meet
        for (int day = 0; day < 7; day++) {     // 0 = Sun, 6 = Sat

            int IndexAmmount = 100;
            // iterates over the list storing the smallest index size
            // this prevents exceptions later from geting indexes that don't exist
            for (PROG_INFO_InfoInput Person : People) {
                if (Person.TimeIntervals.size() < IndexAmmount) {
                    IndexAmmount = Person.TimeIntervals.size();
                }
            }


            int position        = 0;

            while (position < IndexAmmount) {

                int StartInterval   = -1;
                int EndInterval     = 24;

                for (PROG_INFO_InfoInput Person : People) { // 4. repeat this for all intervals that exist for that day

                    // 1. find the latest start time of the earliest time interval from all people on that day
                    if (Person.TimeIntervals.get(position).PreferedHourBEGIN.getHour() > StartInterval) {
                        StartInterval   = Person.TimeIntervals.get(position).PreferedHourBEGIN.getHour();
                    }

                    // 2. find the earliest end time from the same interval on that day
                    if (Person.TimeIntervals.get(position).PreferedHourEND.getHour() > EndInterval) {
                        EndInterval     = Person.TimeIntervals.get(position).PreferedHourEND.getHour();
                    }

                } // for()

                // 3. store the total number of people that fall within that interval
                for (PROG_INFO_InfoInput Person : People) {

                    if ((StartInterval >= Person.TimeIntervals.get(position).PreferedHourBEGIN.getHour()) && (EndInterval) <= Person.TimeIntervals.get(position).PreferedHourEND.getHour()) {
                        // add to the list for that day
                        Schedule = new PROG_INFO_Schedule(WeekDays[position], null, Person.EmployeeID, ScheduleEveryone)
                        ScheduleList.add
                    }
                } // for()
                
                
                position++;

            } // while() 
            // 5. discard all lists beside the one with the most ammount of people, if there are more than one with the same ammount store all of them.


            // 6. if any of these lists contain all the specified people, mark that list

        }


        // 7. iterate through all lists focusing first on those with the identifier



        // 8. if none with the identfier are found look for the top lists (user ammount specified) based on quantity of people


        // 9. store specified lists



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
