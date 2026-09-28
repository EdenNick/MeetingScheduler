package meeting_scheduler;
import meeting_scheduler.GlobalValues.GlobalValuesManager;
import meeting_scheduler.GlobalMessages.GlobalMessageManager;

public class SystemGlobalInfoManager {

    private static boolean ACCESS_VALUES = false;
    private static int LENGTH_MESSAGE_TYPE      = 0;
    private static int LENGTH_MESSAGE_CLASS     = 0;
    private static int LENGTH_MESSAGE_METHOD    = 0;
    private static int LENGTH_MESSAGE_INFO      = 0;

    private static int LENGTH_DATA_SPACING      = 0;
    private static int LENGTH_DATA_PADDING      = 0;
    private static int LENGTH_DATA_WIDTH        = 0;
    private static int LENGTH_DATA_HEIGHT       = 0;
    private static int LENGTH_DATA_ANCHOR       = 0;



    // private final global.UISPACING ENUM_UISPACING_STATES;



    private SystemGlobalInfoManager() {
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
    public static void GET_SYSTEM_MESSAGE(int INPUT_VALUE_TYPE, int INPUT_VALUE_CLASS, int INPUT_VALUE_METHOD, int INPUT_VALUE_INFO) {
        if (false == ACCESS_VALUES) {
            System.out.println("|TYPE: ERROR   |CLASS: SystemGlobalInfoManager  |METHOD: GET_SYSTEM_MESSAGE       |INFO: ACCESS_MESSAGES is false, global system vairables have not been inititialized");
        } else {

            // Each check ensure the input values are within the valid interval of the array, not greater than the length, and not less than 0
            if ( (LENGTH_MESSAGE_TYPE < INPUT_VALUE_TYPE) || (0 > INPUT_VALUE_TYPE) ) {
                // INPUT_VALUE_TYPE to high
                System.out.println("|TYPE: ERROR   |CLASS: SystemGlobalInfoManager  |METHOD: GET_SYSTEM_MESSAGE       |INFO: INPUT_VALUE_TYPE invalid array access value: " + INPUT_VALUE_TYPE);

            } else if ( (LENGTH_MESSAGE_CLASS < INPUT_VALUE_CLASS) || (0 > INPUT_VALUE_CLASS) ) {
                // INPUT_VALUE_CLASS to high
                System.out.println("|TYPE: ERROR   |CLASS: SystemGlobalInfoManager  |METHOD: GET_SYSTEM_MESSAGE       |INFO: INPUT_VALUE_CLASS invalid array access value: " + INPUT_VALUE_CLASS);

            } else if ( (LENGTH_MESSAGE_METHOD < INPUT_VALUE_METHOD) || (0 > INPUT_VALUE_METHOD) ) {
                // INPUT_VALUE_METHOD to high
                System.out.println("|TYPE: ERROR   |CLASS: SystemGlobalInfoManager  |METHOD: GET_SYSTEM_MESSAGE       |INFO: INPUT_VALUE_METHOD invalid array access value: " + INPUT_VALUE_METHOD);

            } else if ( (LENGTH_MESSAGE_INFO < INPUT_VALUE_INFO) || (0 > INPUT_VALUE_INFO) ) {
                // INPUT_VALUE_INFO to high
                System.out.println("|TYPE: ERROR   |CLASS: SystemGlobalInfoManager  |METHOD: GET_SYSTEM_MESSAGE       |INFO: INPUT_VALUE_INFO invalid array access value: " + INPUT_VALUE_INFO);

            } else {
                // String format = String.format("|ID: %-7d", FilePerson.GetIdent());
                // TODO; may need to change the format sizing depending on if a message get cutoff or not.
                String TYPE     = String.format("|TYPE: %-8s",      global.Global_Message_Type_Return   (INPUT_VALUE_TYPE));
                String CLASS    = String.format("|CLASS: %-25s",    global.Global_Message_Class_Return  (INPUT_VALUE_CLASS));
                String METHOD   = String.format("|METHOD: %-25s",   global.Global_Message_Method_Return (INPUT_VALUE_METHOD));
                String INFO     = String.format("|INFO: %-50s",     global.Global_Message_Info_Return   (INPUT_VALUE_INFO));

                String ReturnMessage = TYPE + CLASS + METHOD + INFO;
                System.out.println(ReturnMessage);
            }
        }
    }







    public static double GET_SYSTEM_UI_SPACING(global.UISPACING INPUT_ENUM, int INPUT_POSITION) {

        int length = 0;
        double ReturnValue = 0.0;

        // retrieves the length of the relevant array for safe access
        switch (INPUT_ENUM) {
            case SPACING:
                length = LENGTH_DATA_SPACING;
                break;
            case PADDING:
                length = LENGTH_DATA_PADDING;
                break;
            case WIDTH:
                length = LENGTH_DATA_WIDTH;
                break;
            case HEIGHT:
                length = LENGTH_DATA_HEIGHT;
                break;
            case ANCHOR:
                length = LENGTH_DATA_ANCHOR;
                break;
        }

        if ( (INPUT_POSITION > length) || (INPUT_POSITION < 0) ) {
            // TODO: SYSTEM MESSAGE ERROR
            return ReturnValue;
        } else {
            // TODO: SYSTEM MESSAGE Success  
            return ReturnValue;
        }

        // return 0.0;
    } // GET_SYSTEM_UI_SPACING()






    private static void Get_Lengths() {
        // SYSTEM MESSAGES
        SystemGlobalInfoManager.LENGTH_MESSAGE_TYPE     = global.Global_Message_Type_ReturnSize();
        SystemGlobalInfoManager.LENGTH_MESSAGE_CLASS    = global.Global_Message_Class_ReturnSize();
        SystemGlobalInfoManager.LENGTH_MESSAGE_METHOD   = global.Global_Message_Method_ReturnSize();
        SystemGlobalInfoManager.LENGTH_MESSAGE_INFO     = global.Global_Message_Info_Return();

        // UI SPACING VALUES
        SystemGlobalInfoManager.LENGTH_DATA_SPACING     = global.Global_Array_Spacing_GetLength();
        SystemGlobalInfoManager.LENGTH_DATA_PADDING     = global.Global_Array_Padding_GetLength();
        SystemGlobalInfoManager.LENGTH_DATA_WIDTH       = global.Global_Array_Width_GetLength();
        SystemGlobalInfoManager.LENGTH_DATA_HEIGHT      = global.Global_Array_Height_GetLength();
        SystemGlobalInfoManager.LENGTH_DATA_ANCHOR      = global.Global_Array_Anchor_GetLength();
    }




}
