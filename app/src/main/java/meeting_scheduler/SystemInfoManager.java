// Package  - DO Not Change
// ############################################################
package meeting_scheduler;
// ############################################################

// Imports
// ############################################################
// GlobalValues
import meeting_scheduler.GlobalValues.GlobalValuesManager;
// GlobalMessages
import meeting_scheduler.GlobalMessages.GlobalMessageManager;
// ############################################################



public class SystemInfoManager {

    private static boolean ACCESS_VALUES = false;

    private static int ARRAYLENGTH_MESSAGE_TYPE      = 0;
    private static int ARRAYLENGTH_MESSAGE_CLASS     = 0;
    private static int ARRAYLENGTH_MESSAGE_ACTION    = 0;
    private static int ARRAYLENGTH_MESSAGE_INFO      = 0;

    private static int TYPE_LENGTH   = 10;
    private static int CLASS_LENGTH  = 15;
    private static int ACTION_LENGTH = 20;
    private static int INFO_LENGTH   = 20;

    // private static int LENGTH_DATA_SPACING      = 0;
    // private static int LENGTH_DATA_PADDING      = 0;
    // private static int LENGTH_DATA_WIDTH        = 0;
    // private static int LENGTH_DATA_HEIGHT       = 0;
    // private static int LENGTH_DATA_ANCHOR       = 0;



    // private final global.UISPACING ENUM_UISPACING_STATES;



    private SystemInfoManager() {
        // prevents instantiation
    }
    
    // Initializes global system values - should only be called once upon startup in main
    public static void Inititlaize_Global_States() {
        // Sets up default standardized values
        GlobalValuesManager.VALUES_SETUP();
        // Sets up system messages
        GlobalMessageManager.MESSAGE_SETUP();
        // Gets message sizes for safe access
        Get_Lengths();


        SystemInfoManager.TYPE_LENGTH    = GlobalMessageManager.Return_Length_Type();
        SystemInfoManager.CLASS_LENGTH   = GlobalMessageManager.Return_Length_Class();
        SystemInfoManager.ACTION_LENGTH  = GlobalMessageManager.Return_Length_Action();
        SystemInfoManager.INFO_LENGTH    = GlobalMessageManager.Return_Length_info();

        ACCESS_VALUES = true;
    }


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
    }







    public static double GET_SYSTEM_UI_SPACING(global.UISPACING INPUT_ENUM, int INPUT_POSITION) {

        //int length = 0;
        double ReturnValue = 0.0;
        double[] GlobalArray;

        // retrieves the length of the relevant array for safe access
        switch (INPUT_ENUM) {
            case SPACING:
                GlobalArray = global.Global_Array_Spacing_Get();
                break;
            case PADDING:
                GlobalArray = global.Global_Array_Padding_Get();
                break;
            case WIDTH:
                GlobalArray = global.Global_Array_Width_Get();
                break;
            case HEIGHT:
                GlobalArray = global.Global_Array_Height_Get();
                break;
            case ANCHOR:
                GlobalArray = global.Global_Array_Anchor_Get();
                break;
            default:
                System.out.println("|TYPE: ERROR |CLASS: SystemInfoManager |ACTION: SYSTEM-GetGlobalValues |INFO: Invalid enum input, defaulting to empty array");
                GlobalArray = new double[0];
                break;
        }

        if ( (INPUT_POSITION > GlobalArray.length) || (INPUT_POSITION < 0) ) {
            System.out.println("|TYPE: ERROR |CLASS: SystemInfoManager |ACTION: SYSTEM-GetGlobalValues |INFO: Invalid Input position length, returning defualt return value");
            return ReturnValue;
        } else {
            System.out.println("|TYPE: SUCCESS |CLASS: SystemInfoManager |ACTION: SYSTEM-GetGlobalValues |INFO: Valid Inputs, returning value"); 
            ReturnValue = GlobalArray[INPUT_POSITION];
            return ReturnValue;
        }

        // return 0.0;
    } // GET_SYSTEM_UI_SPACING()


    public static int GET_SYSTEM_GLOBAL_VALUE_INT(global.BASICVALUESINT INPUT_ENUM, int INPUT_POSITION) {

        int ReturnValue = -1;
        int[] GlobalArray;

        switch(INPUT_ENUM) {

            case WEEKLENGTH:
                GlobalArray = global.Global_Array_WeekLength_Get();
                break;
            case TIMEINTERVALS:
                GlobalArray = global.Global_Array_TimeIntervals_Get();
                break;
            case IDENTCONSTRAINT:
                GlobalArray = global.Global_Array_IdentConstraint_Get();
                break;
            default:
                System.out.println("|TYPE: ERROR |CLASS: SystemInfoManager |ACTION: SYSTEM-GetGlobalValues |INFO: Invalid enum input, defaulting to empty array");
                GlobalArray = new int[0];
                break;
        }

        if ( (INPUT_POSITION > GlobalArray.length) || (INPUT_POSITION < 0) ) {
            System.out.println("|TYPE: ERROR |CLASS: SystemInfoManager |ACTION: SYSTEM-GetGlobalValues |INFO: Invalid Input position length, returning defualt return value");
            return ReturnValue;
        } else {
            System.out.println("|TYPE: SUCCESS |CLASS: SystemInfoManager |ACTION: SYSTEM-GetGlobalValues |INFO: Valid Inputs, returning value");  
            ReturnValue = GlobalArray[INPUT_POSITION];
            return ReturnValue;
        }
        
    }

    public static String GET_SYSTEM_GLOBAL_VALUE_STRING(global.BASICVALUESSTRING INPUT_ENUM, int INPUT_POSITION) {

        String ReturnValue = "ERROR";
        String[] GlobalArray;

        switch(INPUT_ENUM) {

            case AMPM:
                GlobalArray = global.Global_Array_AMPM_Get();
                break;
            default:
                System.out.println("|TYPE: ERROR |CLASS: SystemInfoManager |ACTION: SYSTEM-GetGlobalValues |INFO: Invalid enum input, defaulting to empty array");
                GlobalArray = new String[0];
                break;
        }

        if ( (INPUT_POSITION > GlobalArray.length) || (INPUT_POSITION < 0) ) {
            System.out.println("|TYPE: ERROR |CLASS: SystemInfoManager |ACTION: SYSTEM-GetGlobalValues |INFO: Invalid Input position length, returning defualt return value");
            return ReturnValue;
        } else {
            System.out.println("|TYPE: SUCCESS |CLASS: SystemInfoManager |ACTION: SYSTEM-GetGlobalValues |INFO: Valid Inputs, returning value");  
            return ReturnValue;
        }

    }



    private static void Get_Lengths() {
        // SYSTEM MESSAGES
        SystemInfoManager.ARRAYLENGTH_MESSAGE_TYPE     = global.Global_Message_Type_ReturnSize();
        SystemInfoManager.ARRAYLENGTH_MESSAGE_CLASS    = global.Global_Message_Class_ReturnSize();
        SystemInfoManager.ARRAYLENGTH_MESSAGE_ACTION   = global.Global_Message_Action_ReturnSize();
        SystemInfoManager.ARRAYLENGTH_MESSAGE_INFO     = global.Global_Message_Info_Return();

        // UI SPACING VALUES
        // SystemInfoManager.LENGTH_DATA_SPACING     = global.Global_Array_Spacing_GetLength();
        // SystemInfoManager.LENGTH_DATA_PADDING     = global.Global_Array_Padding_GetLength();
        // SystemInfoManager.LENGTH_DATA_WIDTH       = global.Global_Array_Width_GetLength();
        // SystemInfoManager.LENGTH_DATA_HEIGHT      = global.Global_Array_Height_GetLength();
        // SystemInfoManager.LENGTH_DATA_ANCHOR      = global.Global_Array_Anchor_GetLength();
    }




}
