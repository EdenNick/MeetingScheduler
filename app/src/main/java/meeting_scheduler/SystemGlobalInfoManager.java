package meeting_scheduler;
import meeting_scheduler.global;
import meeting_scheduler.GlobalValues.GlobalValuesManager;
import meeting_scheduler.GlobalMessages.GlobalMessageManager;

public class SystemGlobalInfoManager {

    private static boolean ACCESS_MESSAGES = false;
    private static int LENGTH_TYPE      = 0;
    private static int LENGTH_CLASS     = 0;
    private static int LENGTH_METHOD    = 0;
    private static int LENGTH_INFO      = 0;

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
        Get_MessageLengths();

        ACCESS_MESSAGES = true;
    }


    // Safe System message access across files - 
    public static void GET_SYSTEM_MESSAGE(int INPUT_VALUE_TYPE, int INPUT_VALUE_CLASS, int INPUT_VALUE_METHOD, int INPUT_VALUE_INFO) {
        if (false == ACCESS_MESSAGES) {
            System.out.println("|TYPE: ERROR   |CLASS: SystemGlobalInfoManager  |METHOD: GET_SYSTEM_MESSAGE       |INFO: ACCESS_MESSAGES is false, global system vairables have not been inititialized");
        } else {

            // Each check ensure the input values are within the valid interval of the array, not greater than the length, and not less than 0
            if ( (LENGTH_TYPE < INPUT_VALUE_TYPE) || (0 > INPUT_VALUE_TYPE) ) {
                // INPUT_VALUE_TYPE to high
                System.out.println("|TYPE: ERROR   |CLASS: SystemGlobalInfoManager  |METHOD: GET_SYSTEM_MESSAGE       |INFO: INPUT_VALUE_TYPE invalid array access value: " + INPUT_VALUE_TYPE);

            } else if ( (LENGTH_CLASS < INPUT_VALUE_CLASS) || (0 > INPUT_VALUE_CLASS) ) {
                // INPUT_VALUE_CLASS to high
                System.out.println("|TYPE: ERROR   |CLASS: SystemGlobalInfoManager  |METHOD: GET_SYSTEM_MESSAGE       |INFO: INPUT_VALUE_CLASS invalid array access value: " + INPUT_VALUE_CLASS);

            } else if ( (LENGTH_METHOD < INPUT_VALUE_METHOD) || (0 > INPUT_VALUE_METHOD) ) {
                // INPUT_VALUE_METHOD to high
                System.out.println("|TYPE: ERROR   |CLASS: SystemGlobalInfoManager  |METHOD: GET_SYSTEM_MESSAGE       |INFO: INPUT_VALUE_METHOD invalid array access value: " + INPUT_VALUE_METHOD);

            } else if ( (LENGTH_INFO < INPUT_VALUE_INFO) || (0 > INPUT_VALUE_INFO) ) {
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


    private static void Get_MessageLengths() {
        SystemGlobalInfoManager.LENGTH_TYPE     = global.Global_Message_Type_ReturnSize();
        SystemGlobalInfoManager.LENGTH_CLASS    = global.Global_Message_Class_ReturnSize();
        SystemGlobalInfoManager.LENGTH_METHOD   = global.Global_Message_Method_ReturnSize();
        SystemGlobalInfoManager.LENGTH_INFO     = global.Global_Message_Info_Return();
    }
}
