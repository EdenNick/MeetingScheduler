package meeting_scheduler;

public class global {

    // SET booleans
    private static boolean STATE_MESSAGE_SET = false;

    // Data Variables
    private static final String[]   Global_Data_WeekDays    = new String[] {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

    private static final String[]   Global_Data_TimeFrame   = new String[] {"AM", "PM"};

    private static final int        Global_Data_WeekLength  = 7;

    private static final int[]      Global_Data_IdentConstraint = new int[] {0, 9999999};


    // Mesage states
    private static String[] Global_Message_Type;     // Contains system message type.

    private static String[] Global_Message_Class;    // Contains system class names.

    private static String[] Global_Message_Method;   // contains system message location.

    private static String[] Global_Message_Info;     // Contains relevant info for the message;


    // ENUM checks
    public static enum TextFieldState {
        TEXT, NUMERIC
    }

    public static enum TextListState {
        WEEK
    }

    private global() {
        // Restrict instatiation
    }





    public static void Global_Message_Set(String[] Input_Types, String[] Input_Class, String[] Input_Methods, String[] Input_Info) {

        if (false == STATE_MESSAGE_SET) {
            Global_Message_Type_Set     (Input_Types);
            Global_Message_Class_Set    (Input_Class);
            Global_Message_Method_Set   (Input_Methods);
            Global_Message_Info_Set     (Input_Info);
            global.STATE_MESSAGE_SET = true;
        } else {
            // SYSTEM MESSAGE
        }
    }



    // add info to the variables
    private static void Global_Message_Type_Set     (String[] Input_Types) {
        global.Global_Message_Type      = Input_Types.clone();
    }

    private static void Global_Message_Class_Set    (String[] Input_Class) {
        global.Global_Message_Class     = Input_Class.clone();
    }
    
    private static void Global_Message_Method_Set   (String[] Input_Methods) {
        global.Global_Message_Method    = Input_Methods.clone();
    }

    private static void Global_Message_Info_Set     (String[] Input_Info) {
        global.Global_Message_Info      = Input_Info.clone();
    }


    
    public static String Global_Message_Type_Return    (int Input_Position) {
        return global.Global_Message_Type[Input_Position];
    }

    public static String Global_Message_Class_Return   (int Input_Position) {
        return global.Global_Message_Class[Input_Position];
    }

    public static String Global_Message_Method_Return  (int Input_Position) {
        return global.Global_Message_Method[Input_Position];
    }

    public static String Global_Message_Info_Return    (int Input_Position) {
        return global.Global_Message_Info[Input_Position];
    }



    public static String[] Global_Data_Get_Weekdays() {
        return global.Global_Data_WeekDays.clone();
    }


    public static int Global_Data_Get_WeekdaysLength() {
        return global.Global_Data_WeekLength;
    }

    public static String[] Global_Data_Get_TimeFrames() {
        return global.Global_Data_TimeFrame;
    }
}
