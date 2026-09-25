package meeting_scheduler;

public class global {

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
    public enum TextFieldState {
        TEXT, NUMERIC
    }

    public enum TextListState {
        WEEK
    }

    private global() {
        // Restrict instatiation
    }


    // add info to the variables
    public static void Global_Message_Type_Set     (String[] Input_Types) {
       global.Global_Message_Type       = Input_Types.clone();
    }

    public static void Global_Message_Class_Set    (String[] Input_Class) {
        global.Global_Message_Class     = Input_Class.clone();
    }
    
    public static void Global_Message_Method_Set   (String[] Input_Methods) {
        global.Global_Message_Method    = Input_Methods.clone();
    }

    public static void Global_Message_Info_Set     (String[] Input_Info) {
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
