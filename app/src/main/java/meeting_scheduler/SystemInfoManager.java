// Package  - DO Not Change
// ############################################################
package meeting_scheduler;
// ############################################################

import meeting_scheduler.GlobalManagers.GlobalMessageManager;
import meeting_scheduler.GlobalManagers.GlobalValuesManager;



public final class SystemInfoManager {

    private static boolean ACCESS_VALUES = false;

    private static int ARRAYLENGTH_MESSAGE_TYPE      = 0;
    private static int ARRAYLENGTH_MESSAGE_CLASS     = 0;
    private static int ARRAYLENGTH_MESSAGE_ACTION    = 0;
    private static int ARRAYLENGTH_MESSAGE_INFO      = 0;

    private static int TYPE_LENGTH   = 10;
    private static int CLASS_LENGTH  = 15;
    private static int ACTION_LENGTH = 20;
    private static int INFO_LENGTH   = 20;




    private SystemInfoManager() {
        // prevents instantiation
    }
    

    /**
     * Inititlaize_Global_States()
     * Description: Initializes global system values - should only be called once upon startup in main
     */
    public static void Inititlaize_Global_States() {

        if (ACCESS_VALUES == false) {
            // Sets up default standardized values
            GlobalValuesManager.VALUES_SETUP();
            // Sets up system messages
            GlobalMessageManager.MESSAGE_SETUP();
            // Gets message sizes for safe access
            Get_Lengths();

            ACCESS_VALUES = true;
        } else {
            // DO NOTHING
        }

    } // Inititlaize_Global_States()



    /**
     * GET_SYSTEM_MESSAGE()
     * Description: Safe System message access across files
     * @param INPUT_VALUE_TYPE
     * @param INPUT_VALUE_CLASS
     * @param INPUT_VALUE_METHOD
     * @param INPUT_VALUE_INFO
     */
    public static void GET_SYSTEM_MESSAGE(int INPUT_VALUE_TYPE, int INPUT_VALUE_CLASS, int INPUT_VALUE_ACTION, int INPUT_VALUE_INFO) {
        if (false == ACCESS_VALUES) {
            System.out.println("|TYPE: ERROR   |CLASS: SystemGlobalInfoManager  |ACTION: GET_SYSTEM_MESSAGE       |INFO: ACCESS_MESSAGES is false, global system vairables have not been inititialized");
        } else {

            // Each check ensure the input values are within the valid interval of the array, not greater than the length, and not less than 0
            if ( (ARRAYLENGTH_MESSAGE_TYPE < INPUT_VALUE_TYPE) || (0 > INPUT_VALUE_TYPE) ) {
                // INPUT_VALUE_TYPE to high
                System.out.println("|TYPE: ERROR   |CLASS: SystemGlobalInfoManager  |ACTION: GET_SYSTEM_MESSAGE       |INFO: INPUT_VALUE_TYPE invalid array access value: " + INPUT_VALUE_TYPE);

            } else if ( (ARRAYLENGTH_MESSAGE_CLASS < INPUT_VALUE_CLASS) || (0 > INPUT_VALUE_CLASS) ) {
                // INPUT_VALUE_CLASS to high
                System.out.println("|TYPE: ERROR   |CLASS: SystemGlobalInfoManager  |ACTION: GET_SYSTEM_MESSAGE       |INFO: INPUT_VALUE_CLASS invalid array access value: " + INPUT_VALUE_CLASS);

            } else if ( (ARRAYLENGTH_MESSAGE_ACTION < INPUT_VALUE_ACTION) || (0 > INPUT_VALUE_ACTION) ) {
                // INPUT_VALUE_METHOD to high
                System.out.println("|TYPE: ERROR   |CLASS: SystemGlobalInfoManager  |ACTION: GET_SYSTEM_MESSAGE       |INFO: INPUT_VALUE_METHOD invalid array access value: " + INPUT_VALUE_ACTION);

            } else if ( (ARRAYLENGTH_MESSAGE_INFO < INPUT_VALUE_INFO) || (0 > INPUT_VALUE_INFO) ) {
                // INPUT_VALUE_INFO to high
                System.out.println("|TYPE: ERROR   |CLASS: SystemGlobalInfoManager  |ACTION: GET_SYSTEM_MESSAGE       |INFO: INPUT_VALUE_INFO invalid array access value: " + INPUT_VALUE_INFO);

            } else {
                // String format = String.format("|ID: %-7d", FilePerson.GetIdent());
                // TODO; may need to change the format sizing depending on if a message get cutoff or not.
                String TYPE     = String.format("|TYPE: %-"     + TYPE_LENGTH   + "s",  global.Global_Message_Type_Return   (INPUT_VALUE_TYPE));
                String CLASS    = String.format("|CLASS: %-"    + CLASS_LENGTH  + "s",  global.Global_Message_Class_Return  (INPUT_VALUE_CLASS));
                String METHOD   = String.format("|ACTION: %-"   + ACTION_LENGTH + "s",  global.Global_Message_Action_Return (INPUT_VALUE_ACTION));
                String INFO     = String.format("|INFO: %-"     + INFO_LENGTH   + "s",  global.Global_Message_Info_Return   (INPUT_VALUE_INFO));

                String ReturnMessage = TYPE + CLASS + METHOD + INFO;
                System.out.println(ReturnMessage);
            }
        }
    } // GET_SYSTEM_MESSAGE()



    /**
     * GET_SYSTEM_UI_SPACING()
     * Description: Description: Returns a single double Value from one the global double arrays containing default values. Uses global.UISPACING
     * @param INPUT_ENUM
     * @param INPUT_POSITION
     * @return
     */
    public static double GET_SYSTEM_UI_SPACING(global.UISPACING INPUT_ENUM, int INPUT_POSITION) {

        double ReturnValue = 0.0;

        if (false == ACCESS_VALUES) {
            System.out.println("|TYPE: ERROR   |CLASS: SystemGlobalInfoManager  |ACTION: GET_SYSTEM_VALUE       |INFO: ACCESS_MESSAGES is false, global system vairables have not been inititialized");
        } else {
            // retrieves the length of the relevant array for safe access
            switch (INPUT_ENUM) {
                case SPACING:
                    ReturnValue = global.Global_Array_Spacing_Get(INPUT_POSITION);
                    break;

                case PADDING:
                    ReturnValue = global.Global_Array_Padding_Get(INPUT_POSITION);
                    break;

                case WIDTH:
                    ReturnValue = global.Global_Array_Width_Get(INPUT_POSITION);
                    break;

                case HEIGHT:
                    ReturnValue = global.Global_Array_Height_Get(INPUT_POSITION);
                    break;

                case ANCHOR:
                    ReturnValue = global.Global_Array_Anchor_Get(INPUT_POSITION);
                    break;

                default:
                    System.out.println("|TYPE: ERROR |CLASS: SystemInfoManager |ACTION: SYSTEM-GetGlobalValues |INFO: Invalid enum input, defaulting to double 0.0");
                    break;
            }
        }

        return ReturnValue;

    } // GET_SYSTEM_UI_SPACING()



    /**
     * GET_SYSTEM_GLOBAL_VALUE_INT()
     * Description: Returns a single int Value from one the global int arrays containing default values. Uses global.BASICVALUESINT
     * @param INPUT_ENUM
     * @param INPUT_POSITION
     * @return
     */
    public static int GET_SYSTEM_GLOBAL_VALUE_INT(global.BASICVALUESINT INPUT_ENUM, int INPUT_POSITION) {

        int ReturnValue = -1;

        if (false == ACCESS_VALUES) {
            System.out.println("|TYPE: ERROR   |CLASS: SystemGlobalInfoManager  |ACTION: GET_SYSTEM_VALUE       |INFO: ACCESS_MESSAGES is false, global system vairables have not been inititialized");
        } else {
            switch(INPUT_ENUM) {

                case INT:
                    ReturnValue = global.Global_Array_intValues_Get        (INPUT_POSITION);
                    break;

                case TIMEINTERVALS:
                    ReturnValue = global.Global_Array_TimeIntervals_Get     (INPUT_POSITION);
                    break;

                case IDENTCONSTRAINT:
                    ReturnValue = global.Global_Array_IdentConstraint_Get   (INPUT_POSITION);
                    break;

                default:
                    System.out.println("|TYPE: ERROR |CLASS: SystemInfoManager |ACTION: SYSTEM-GetGlobalValues |INFO: Invalid enum input, defaulting to int -1");
                    break;
            }
        }

        return ReturnValue;
        
    } // GET_SYSTEM_GLOBAL_VALUE_INT()



    /**
     * GET_SYSTEM_GLOBAL_VALUE_STRING()
     * Description: Returns a single String Value from one the global string arrays containing default values. Uses global.BASICVALUESSTRING
     * @param INPUT_ENUM
     * @param INPUT_POSITION
     * @return
     */
    public static String GET_SYSTEM_GLOBAL_VALUE_STRING(global.BASICVALUESSTRING INPUT_ENUM, int INPUT_POSITION) {

        String ReturnValue = "ERROR";

        if (false == ACCESS_VALUES) {
            System.out.println("|TYPE: ERROR   |CLASS: SystemGlobalInfoManager  |ACTION: GET_SYSTEM_VALUE       |INFO: ACCESS_MESSAGES is false, global system vairables have not been inititialized");
        } else {
            switch(INPUT_ENUM) {

                case AMPM:
                    ReturnValue = global.Global_Array_AMPM_Get      (INPUT_POSITION);
                    break;

                case STRING:
                    ReturnValue = global.Global_Array_String_Get    (INPUT_POSITION);
                    break;
                
                default:
                    System.out.println("|TYPE: ERROR |CLASS: SystemInfoManager |ACTION: SYSTEM-GetGlobalValues |INFO: Invalid enum input, defaulting to string 'ERROR'");
                    break;
            }
        }

        return ReturnValue;

    } // GET_SYSTEM_GLOBAL_VALUE_STRING()



    /**
     * GET_SYSTEM_GLOBAL_VALUE_WEEKARRAY()
     * Description: Returns a string array containing every day of the week in various formats depending on input. Uses global.WEEKTYPE.
     * @param INPUT_ENUM
     * @return
     */
    public static String[] GET_SYSTEM_GLOBAL_VALUE_WEEKARRAY(global.WEEKTYPE INPUT_ENUM) {

        String[] ReturnValue = new String[] {"INVALIDSTRING"};

        if (false == ACCESS_VALUES) {
            System.out.println("|TYPE: ERROR   |CLASS: SystemGlobalInfoManager  |ACTION: GET_SYSTEM_VALUE       |INFO: ACCESS_MESSAGES is false, global system vairables have not been inititialized");
        } else {
            switch(INPUT_ENUM) {

                case SHORT:
                    ReturnValue = global.Global_Array_WeekDay_Short_Get();
                    break;
                case SHORTCAP:
                    ReturnValue = global.Global_Array_WeekDay_ShortCap_Get();
                    break;
                case LONG:
                    ReturnValue = global.Global_Array_WeekDay_Long_Get();
                    break;
                case LONGCAP:
                    ReturnValue = global.Global_Array_WeekDay_LongCap_Get();
                    break;
                default:
                    System.out.println("|TYPE: ERROR |CLASS: SystemInfoManager |ACTION: SYSTEM-GetGlobalValues |INFO: Invalid enum input, defaulting to string[] 'INVALIDSTRING'");
                    break;
            }
        }

        return ReturnValue;

    } // GET_SYSTEM_GLOBAL_VALUE_WEEKARRAY()



    /**
     * Get_Lengths()
     * Description: gets the lengths of the various message components to ensure safe access and correct formatting
     */
    private static void Get_Lengths() {
        // SYSTEM MESSAGES
        SystemInfoManager.ARRAYLENGTH_MESSAGE_TYPE      = global.Global_Message_Type_ReturnSize();
        SystemInfoManager.ARRAYLENGTH_MESSAGE_CLASS     = global.Global_Message_Class_ReturnSize();
        SystemInfoManager.ARRAYLENGTH_MESSAGE_ACTION    = global.Global_Message_Action_ReturnSize();
        SystemInfoManager.ARRAYLENGTH_MESSAGE_INFO      = global.Global_Message_Info_Return();

        // maximum string length of each type of message
        SystemInfoManager.TYPE_LENGTH                   = GlobalMessageManager.Return_Length_Type();
        SystemInfoManager.CLASS_LENGTH                  = GlobalMessageManager.Return_Length_Class();
        SystemInfoManager.ACTION_LENGTH                 = GlobalMessageManager.Return_Length_Action();
        SystemInfoManager.INFO_LENGTH                   = GlobalMessageManager.Return_Length_info();

    } // Get_Lengths() 



} // SystemInfoManager{}