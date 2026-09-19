package meeting_scheduler;

public class global {

    private static String[] Global_Message_Type;     // Contains system message type.

    private static String[] Global_Message_Class;    // Contains system class names.

    private static String[] Global_Message_Method;   // contains system message location.

    private static String[] Global_Message_Info;     // Contains relevant info for the message;

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
}
