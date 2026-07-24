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

    private LinkedList<PROG_INFO_InfoInput> People;                     // Linked list of object PROG_INFO_InfoInput which stores the card info for a persons preference.
    


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

    }



    /**
     * Cosntructor 3
     * Description: Constructor used when a specific set of days are selected and there is no preference for people.
     * @param days
     */
    public PROG_INFO_SchedulingCalculation( String[] days) {

        // this.ScheduleEveryone    = true;
        this.AnyDayAnyTime          = false;
        this.WeekDays               = days.clone();

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

        // iterates over the linked list string of people to select
        for (String Person : PeopleToSchedule) {
            
        }

    }


    /**
     * CalculateSchedule()
     * Description: calculates the schedules
     */
    private void CalculateSchedule() {

        for (PROG_INFO_InfoInput PERSON : People) {

        }

    }



    /**
     * RetrieveSchedule()
     * Description: public method called in order to retrieve the calculated schedule times
     */
    public void RetrieveSchedule() {

    }

}
