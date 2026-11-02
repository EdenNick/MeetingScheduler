// Package  - DO Not Change
// ############################################################
package meeting_scheduler.UserInput;
// ############################################################

// Imports
// ############################################################
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.HashMap;
import meeting_scheduler.global;
import meeting_scheduler.DataHolder.PreferenceFull;
import meeting_scheduler.DataHolder.PreferenceTime;
// ############################################################



public class ManageJsonFormat {

    // Class Parameters
    // ############################################################
    private static final String[] WEEKDAYS = global.Global_Array_WeekDay_Short_Get();

    private HashMap<Integer, PreferenceFull>    HashSetEmployeePreference;
    private ListIterator<PreferenceFull>        IteratorDefaultJson;
    // private boolean Input = false;
    // ############################################################



    /**
     * Constructor()
     */
    public ManageJsonFormat() {
        //this.JsonFileManager = new MANAGEFILE_JsonManager();

    }
    


    /**
     * JsonFileDefault_Formatting()
     * Description: Formats the Input LinkedList
     * @param INPUT_FullEmployeePreference
     * @return
     */
    public LinkedList<PreferenceFull> JsonFileDefault_Formatting(LinkedList<PreferenceFull> INPUT_FullEmployeePreference) {


        this.HashSetEmployeePreference = new HashMap<>();

        int Size_PreferenceList = INPUT_FullEmployeePreference.size();

        for (int Position_PreferenceList = 0; Position_PreferenceList < Size_PreferenceList; Position_PreferenceList++) {

            // int to integer
            Integer EmployeePreference_Key = Integer.valueOf(INPUT_FullEmployeePreference.get(Position_PreferenceList).GetIdent());


            // IF - the same ID number already exists in the hashset
            if ( HashSetEmployeePreference.containsKey(EmployeePreference_Key) ) {
                
                // remove the original element from the hashset
                HashSetEmployeePreference.remove(INPUT_FullEmployeePreference.get(Position_PreferenceList).GetIdent());

                // store that element as a unique preference and ID number
                PreferenceFull EMPLOYEE_Duplicate = INPUT_FullEmployeePreference.get(Position_PreferenceList);
                
                // Existing data
                boolean     NewPreference_Delete    = EMPLOYEE_Duplicate.GetStatus();
                int         NewPreference_Ident     = EMPLOYEE_Duplicate.GetIdent();
                String      NewPreference_Name      = EMPLOYEE_Duplicate.GetName();
                InputWeek   NewPreference_week      = new InputWeek();

                LinkedList<PreferenceTime> NewPreference_times = new LinkedList<>();



                //iterator initialization
                IteratorDefaultJson = INPUT_FullEmployeePreference.listIterator();
                // iterate over all employees, if any IDs match add their preferences to a single combined linkedlist
                while (IteratorDefaultJson.hasNext()) {

                    // Next employee in the list and their ID number
                    PreferenceFull Next_Employee = IteratorDefaultJson.next();
                    int Next_employeeIdent = Next_Employee.GetIdent();

                    // IF - when employee ids match the Duplicate_Employee id, their prefered intervals are added to a shared list
                    if (NewPreference_Ident == Next_employeeIdent) {


                        // Add all preferences to the new set
                        for (int pos_CheckPref = 0; pos_CheckPref < Next_Employee.GetIntervals().length; pos_CheckPref++) {

                            NewPreference_times.add(Next_Employee.GetIntervals()[pos_CheckPref]);

                        } // for (int position_checkPreference = 0; position_checkPreference < Next_Employee.GetIntervals().size(); position_checkPreference++) {


                    } else {
                        // Do Nothing - move to the next person
                    }

                } // while (IteratorDefaultJson.hasNext()) {


                // Iterate over all preference days to ensure they are added to the list
                // int NumberofValidDays = 0;
                for (int weekday = 0; weekday < ManageJsonFormat.WEEKDAYS.length; weekday++) {

                    for (int IndexPosition = 0; IndexPosition < NewPreference_times.size(); IndexPosition++) {

                        if (NewPreference_times.get(IndexPosition).GetWeekDay() != null) {
                            if ( NewPreference_times.get(IndexPosition).GetWeekDay().equals(WEEKDAYS[weekday]) ) {
                                NewPreference_week.SetDay(weekday);
                                break;
                            }
                        }

                    } // for (int IndexPosition = 0; IndexPosition < Employee_NewPreferenceSet.size(); IndexPosition++) {

                } // for (int weekday = 0; weekday < 7; weekday++)


                // Create new preference for a person and add them to the hash set
                // if NewPreference_Delete is true, do not do this
                // it is necessary for this to be done at the end as the above code aggregates potential sperate preferences of the same person first
                // allowing this if statement to safely add or not add complete preference sets.
                if (NewPreference_Delete == false) {

                    PreferenceFull NEWEmployeePreference = new PreferenceFull(NewPreference_Delete, NewPreference_Name, 
                    NewPreference_Ident, NewPreference_week.GetWeekArray(), NewPreference_times);

                    HashSetEmployeePreference.put(EmployeePreference_Key, NEWEmployeePreference);

                    System.out.println(EmployeePreference_Key);
                }

            } else {
                // Add new preference to the list
                HashSetEmployeePreference.put(EmployeePreference_Key, INPUT_FullEmployeePreference.get(Position_PreferenceList));
            }

            // NO code should exist at this point

        } // for (int Position_PreferenceList = 0; Position_PreferenceList < Size_PreferenceList; Position_PreferenceList++) {


        

        // Add all objects in the hashset to the json preference file

        LinkedList<PreferenceFull> EMPLOYEES_formatted = new LinkedList<>(HashSetEmployeePreference.values());
        //this.JsonFileManager.WriteTo_DefaultEmployeePreference(EMPLOYEES_WriteToFile);

        return EMPLOYEES_formatted;
    } // JsonFileDefault_Formatting(LinkedList<PREF_EMPLOYEE_FullPref> INPUT_FullEmployeePreference) {




    /**
     * JsonFileDefault_FormatRemovals()
     * Description: Removes any Preferences From the list marked for deletion based on the status boolean in each object
     * @param INPUT_FullEmployeePreference
     */
    public void JsonFileDefault_FormatRemovals(LinkedList<PreferenceFull> INPUT_FullEmployeePreference) {

        ListIterator<PreferenceFull> Iterator =  INPUT_FullEmployeePreference.listIterator();

        // while there is a value accessible in the linkedlist
        while (Iterator.hasNext()) {
            
            // check its deletion status
            if (Iterator.next().GetStatus() == true) {
                // if true -> remove
                Iterator.remove();
            } else {
                // false -> should not be deleted
            }

        } //while (Iterator.hasNext())

    } // JsonFileDefault_FormatRemovals()



    /**
     * JsonFileDefault_ReplaceWithInput()
     * Description: Formats replacment of a single data card at a time
     * @param INPUT_FullEmployeePreference
     * @param INPUT_POSITION
     * @return
     */
    public LinkedList<PreferenceFull> JsonFileDefault_ReplaceWithInput(LinkedList<PreferenceFull> INPUT_FullEmployeePreference, int INPUT_POSITION) {

        // get the recently added preference from the linked list
        PreferenceFull  ReplcementPreference    = INPUT_FullEmployeePreference.get(INPUT_POSITION);
        int             ReplacementPreferenceID = ReplcementPreference.GetIdent();

        // remove the recently added preference
        INPUT_FullEmployeePreference.remove(INPUT_POSITION);

        // convert the linkedlist to an array for more efficient iteration
        PreferenceFull[] IterationArrayPreferences = INPUT_FullEmployeePreference.toArray(new PreferenceFull[0]);
        // linkedlist to be returned
        LinkedList<PreferenceFull> NewPreferenceList = new LinkedList<>();

        for (int Pos = 0; Pos < IterationArrayPreferences.length; Pos++) {

            if (IterationArrayPreferences[Pos].GetIdent() != ReplacementPreferenceID) {
                NewPreferenceList.add(IterationArrayPreferences[Pos]);
            } else {
                NewPreferenceList.add(ReplcementPreference);
            }
            
        } // for (int Pos = 0; Pos < IterationArrayPreferences.length; Pos++)


        return NewPreferenceList;
    } // JsonFileDefault_ReplaceWithInput()

    

} // ManageJsonFormat{}