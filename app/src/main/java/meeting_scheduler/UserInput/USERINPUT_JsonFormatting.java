/**
 * PROG_BLL_InfoFileWrite.java
 * 
 * Description: Used to format the default employee preferences Json file and write it to the correct file ensuring
 * organization and that information is streamlined (no duplicates, disjointed preferences, etc)
 * Performs vital operation of verifying data exists and is usable.
 */

// Package  - DO Not Change
// ############################################################
package meeting_scheduler.UserInput;
// ############################################################

// Imports
// ############################################################
// exception
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
// util
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Set;

// jackson - json manager
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
// System Messages
import meeting_scheduler.DataAccessLayer.PROG_DAL_D_SystemMessages;
import meeting_scheduler.EmployeePreferences.PREF_EMPLOYEE_FullPref;
import meeting_scheduler.EmployeePreferences.PREF_EMPLOYEE_TimePref;
import meeting_scheduler.FileManagement.MANAGEFILE_JsonManager;



public class USERINPUT_JsonFormatting {


    public static final String[]    WEEKDAYS        = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    public  boolean                                 Input = false;
    

    private MANAGEFILE_JsonManager  JsonFileManager;
    private HashMap<Integer, PREF_EMPLOYEE_FullPref> HashSet_EmployeePreference;

    // iterator
    private ListIterator<PREF_EMPLOYEE_FullPref>  Iterator_DefaultJson;
    
    public USERINPUT_JsonFormatting() {
        this.JsonFileManager = new MANAGEFILE_JsonManager();

    }
    
    //
    public LinkedList<PREF_EMPLOYEE_FullPref> JsonFileDefault_Formatting(LinkedList<PREF_EMPLOYEE_FullPref> INPUT_FullEmployeePreference) {


        this.HashSet_EmployeePreference = new HashMap<>();


        int Size_PreferenceList = INPUT_FullEmployeePreference.size();

        for (int Position_PreferenceList = 0; Position_PreferenceList < Size_PreferenceList; Position_PreferenceList++) {

            // int to integer
            Integer EmployeePreference_Key = Integer.valueOf(INPUT_FullEmployeePreference.get(Position_PreferenceList).GetIdent());


            // IF - the same ID number already exists in the hashset
            if ( HashSet_EmployeePreference.containsKey(EmployeePreference_Key) ) {
                
                // remove the original element from the hashset
                HashSet_EmployeePreference.remove(INPUT_FullEmployeePreference.get(Position_PreferenceList).GetIdent());

                // store that element as a unique preference and ID number
                PREF_EMPLOYEE_FullPref EMPLOYEE_Duplicate = INPUT_FullEmployeePreference.get(Position_PreferenceList);
                
                // Existing data
                boolean     NewPreference_Delete    = EMPLOYEE_Duplicate.GetStatus();
                int         NewPreference_Ident     = EMPLOYEE_Duplicate.GetIdent();
                String      NewPreference_Name      = EMPLOYEE_Duplicate.GetName();
                String[]    NewPreference_Days      = new String[7];

                LinkedList<PREF_EMPLOYEE_TimePref> NewPreference_times = new LinkedList<>();





                //iterator initialization
                Iterator_DefaultJson = INPUT_FullEmployeePreference.listIterator();
                // iterate over all employees, if any IDs match add their preferences to a single combined linkedlist
                while (Iterator_DefaultJson.hasNext()) {

                    // Next employee in the list and their ID number
                    PREF_EMPLOYEE_FullPref Next_Employee = Iterator_DefaultJson.next();
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

                } // while (Iterator_DefaultJson.hasNext()) {



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
                PREF_EMPLOYEE_FullPref NEWEmployeePreference = new PREF_EMPLOYEE_FullPref(NewPreference_Delete, NewPreference_Name, NewPreference_Ident, NewPreference_week, NewPreference_times);


                HashSet_EmployeePreference.put(EmployeePreference_Key, NEWEmployeePreference);

                System.out.println(EmployeePreference_Key);



            } else {
                // Add new preference to the list
                HashSet_EmployeePreference.put(EmployeePreference_Key, INPUT_FullEmployeePreference.get(Position_PreferenceList));
            }

            // NO code should exist at this point

        } // for (int Position_PreferenceList = 0; Position_PreferenceList < Size_PreferenceList; Position_PreferenceList++) {


        

        // Add all objects in the hashset to the json preference file

        LinkedList<PREF_EMPLOYEE_FullPref> EMPLOYEES_formatted = new LinkedList<>(HashSet_EmployeePreference.values());
        //this.JsonFileManager.WriteTo_DefaultEmployeePreference(EMPLOYEES_WriteToFile);

        return EMPLOYEES_formatted;
    } // JsonFileDefault_Formatting(LinkedList<PREF_EMPLOYEE_FullPref> INPUT_FullEmployeePreference) {





    public void JsonFileDefault_Formatremovals() throws StreamReadException, DatabindException, IOException {

        //JsonfileManager.WriteTo_DefaultEmployeePreference();
    }

    
}
