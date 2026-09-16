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
import meeting_scheduler.FIleManagement.MANAGEFILE_JsonManager;



public class USERINPUT_JsonFormatting {

    public  boolean                                 Input = false;
    private String                                  Name;
    private int                                     Id;
    private String[]                                Days;
    private LinkedList<PREF_EMPLOYEE_TimePref>      TimeIntervals;
    private PREF_EMPLOYEE_FullPref                  UserDataCard;
    private LinkedList<PREF_EMPLOYEE_FullPref>      AllDataCards;

    private MANAGEFILE_JsonManager  JsonFileManager;
    private Set<PREF_EMPLOYEE_FullPref> HashSet_EmployeePreference;

    // iterator
    private ListIterator<PREF_EMPLOYEE_FullPref>  Iterator_DefaultJson;
    
    public USERINPUT_JsonFormatting() {
        this.JsonFileManager = new MANAGEFILE_JsonManager();

    }
    
    //
    public void JsonFileDefault_Formatting(LinkedList<PREF_EMPLOYEE_FullPref> INPUT_FullEmployeePreference) {


        this.HashSet_EmployeePreference = new HashSet<>();

        int Size_PreferenceList = INPUT_FullEmployeePreference.size();

        for (int Position_PreferenceList = 0; Position_PreferenceList < Size_PreferenceList; Position_PreferenceList++) {

            if (HashSet_EmployeePreference.contains(INPUT_FullEmployeePreference.get(Position_PreferenceList).GetIdent())) {
                // duplicate preferences exist

                // remove element from the hashset
                HashSet_EmployeePreference.remove(INPUT_FullEmployeePreference.get(Position_PreferenceList).GetIdent());

                // store the employee info for lookup
                PREF_EMPLOYEE_FullPref Duplicate_Employee = INPUT_FullEmployeePreference.get(Position_PreferenceList);
                int Duplicate_Ident = Duplicate_Employee.GetIdent();


                LinkedList<PREF_EMPLOYEE_TimePref> Employee_NewPreferenceSet = new LinkedList<>();





                //iterator initialization
                Iterator_DefaultJson = INPUT_FullEmployeePreference.listIterator();

                while (Iterator_DefaultJson.hasNext()) {

                    PREF_EMPLOYEE_FullPref Next_Employee = Iterator_DefaultJson.next();
                    int Next_employeeIdent = Next_Employee.GetIdent();

                    // IF - when employee ids match the Duplicate_Employee id, their prefered intervals are added to a shared list
                    if (Duplicate_Ident == Next_employeeIdent) {


                        for (int pos_CheckPref = 0; pos_CheckPref < Next_Employee.GetIntervals().size(); pos_CheckPref++) {

                            Employee_NewPreferenceSet.add(Next_Employee.GetIntervals().get(pos_CheckPref));

                        } // for (int position_checkPreference = 0; position_checkPreference < Next_Employee.GetIntervals().size(); position_checkPreference++) {


                    } else {
                        // Do Nothing move to the next person
                    }

                } // while (Iterator_DefaultJson.hasNext()) {



                // Form new preference set with existing data
                // TODO: 





            } else {
                // Add new preference to the list
                HashSet_EmployeePreference.add(INPUT_FullEmployeePreference.get(Position_PreferenceList));
            }





        }


        // Combine preferences

        // remove 

        
        JsonfileManager.WriteTo_DefaultEmployeePreference();



    } // ReceiveUserInfo()





    public void JsonFileDefault_Formatremovals() throws StreamReadException, DatabindException, IOException {

        JsonfileManager.WriteTo_DefaultEmployeePreference();
    }

    
}
