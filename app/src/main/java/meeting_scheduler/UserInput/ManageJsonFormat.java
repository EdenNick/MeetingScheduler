// Package  - DO Not Change
// ############################################################
package meeting_scheduler.UserInput;
// ############################################################

// Imports
// ############################################################
import java.io.IOException;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.HashMap;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import meeting_scheduler.global;
import meeting_scheduler.DataHolder.PreferenceFull;
import meeting_scheduler.DataHolder.PreferenceTime;
// ############################################################



public class ManageJsonFormat {

    private static final String[] WEEKDAYS = global.Global_Array_WeekDay_Short_Get();

    private HashMap<Integer, PreferenceFull>    HashSetEmployeePreference;
    private ListIterator<PreferenceFull>        IteratorDefaultJson;
    // private boolean Input = false;
    
    /**
     * Constructor()
     */
    public ManageJsonFormat() {
        //this.JsonFileManager = new MANAGEFILE_JsonManager();

    }
    
    //
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
                String[]    NewPreference_Days      = new String[7];

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
                //int NumberofValidDays = 0;
                for (int weekday = 0; weekday < 7; weekday++) {

                    for (int IndexPosition = 0; IndexPosition < NewPreference_times.size(); IndexPosition++) {

                        if ( WEEKDAYS[weekday].equals(NewPreference_times.get(IndexPosition).GetWeekDay()) ) {
                            NewPreference_Days[weekday] = WEEKDAYS[weekday];
                            //NumberofValidDays++;
                            break;
                        } // if ( WEEKDAYS[weekday].equals(Employee_NewPreferenceSet.get(IndexPosition).GetWeekDay()) ) {

                    } // for (int IndexPosition = 0; IndexPosition < Employee_NewPreferenceSet.size(); IndexPosition++) {

                } // for (int weekday = 0; weekday < 7; weekday++) {



                
                String[] NewPreference_week = new String[7];
                //int InputDay = 0;
                for (int position_day = 0; position_day < 7; position_day++) {

                    if (NewPreference_Days[position_day] != null) {
                        NewPreference_week[position_day] = NewPreference_Days[position_day];
                    } else {
                        NewPreference_week[position_day] = null;
                    }
                }


                // Create new preference for a person and add them to the hash set
                PreferenceFull NEWEmployeePreference = new PreferenceFull(NewPreference_Delete, NewPreference_Name, NewPreference_Ident, NewPreference_week, NewPreference_times);


                HashSetEmployeePreference.put(EmployeePreference_Key, NEWEmployeePreference);

                System.out.println(EmployeePreference_Key);



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





    public void JsonFileDefault_Formatremovals() throws StreamReadException, DatabindException, IOException {

        //TODO: 
    }

    
}
