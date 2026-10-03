// Package  - DO Not Change
// ############################################################
package meeting_scheduler;
// ############################################################



public final class global {

    // GLOBAL SET VARIABLE - used to ensure values are set only once
    // ############################################################
    private static boolean  STATE_MESSAGE_SET       = false;
    private static boolean  STATE_WEEKDAYS_SET      = false;
    private static boolean  STATE_BASICVALUES_SET   = false;
    private static boolean  STATE_UISPACING_SET     = false;
    // ############################################################


    // GLOBAL WEEKDAY LISTS - standradized string arrays containing various formats of the days of the week
    // ############################################################
    private static String[] Global_Data_WeekDays_Short;
    private static String[] Global_Data_WeekDays_ShortCap;
    private static String[] Global_Data_WeekDays_Long;
    private static String[] Global_Data_WeekDays_LongCap;
    // ############################################################


    // GLOBAL BASIC VALUES - miscellaneous standard values
    // ############################################################  
    private static int[]    Global_Data_WeekLength;
    private static int[]    Global_Data_TimeIntervals;
    private static int[]    Global_Data_IdentConstraint;
    private static String[] Global_Data_AMPM;
    // ############################################################


    // GLOBAL UI SPACING VALUES - standardized spacing values for UI formatting
    // ############################################################
    private static double[] Global_Data_Spacing;
    private static double[] Global_Data_Padding;
    private static double[] Global_Data_Width;
    private static double[] Global_Data_Height;
    private static double[] Global_Data_Anchor;
    // ############################################################


    // GLOBAL MESSAGE VARIABLES - used to contsruct system messages
    // ############################################################
    private static String[] Global_Message_Type;     // Contains system message type.
    private static String[] Global_Message_Class;    // Contains system class names.
    private static String[] Global_Message_Action;   // contains system message action
    private static String[] Global_Message_Info;     // Contains relevant info for the message;
    // ############################################################


    // GLOBAL ENUM CHECK TEXTFIELD - used to determine what type of input a textfield should accept
    // ############################################################
    public static enum TextFieldState {
        TEXT, NUMERIC
    }
    // ############################################################


    // GLOBAL ENUM CHECK TEXTList - used to determine what type of text list needs to be created
    // ############################################################
    public static enum TextListState {
        WEEK
    }
    // ############################################################


    // GLOBAL ENUM CHECK UISPACING - used to select between which UI spacing value to retrieve in the global info manager
    // ############################################################
    public static enum UISPACING {
        SPACING, PADDING, WIDTH, HEIGHT, ANCHOR
    }
    // ############################################################


    // GLOBAL ENUM CHECK BASICVALUES - used to selecg between defualt basic int values
    // ############################################################
    public static enum BASICVALUESINT {
        WEEKLENGTH, TIMEINTERVALS, IDENTCONSTRAINT
    }
    // ############################################################


    // GLOBAL ENUM CHECK BASICVALUES - used to selecg between defualt basic stringvalues
    // ############################################################
    public static enum BASICVALUESSTRING {
        AMPM
    }
    // ############################################################

    // GLOBAL ENUM CHECK WEEKTYPE - used to selecg between String[] weeks
    // ############################################################
    public static enum WEEKTYPE {
        SHORT, SHORTCAP, LONG, LONGCAP
    }
    // ############################################################


    /**
     * global()
     * Constructor - private
     */
    private global() {
        // Restrict instatiation
    }







    /**
     * Global_Message_Set()
     * Description: Used to set the various global message state values
     * @param INPUT_TYPES
     * @param INPUT_CLASS
     * @param INPUT_METHODS
     * @param INPUT_iNFO
     */
    public static void Global_Message_Set (String[] INPUT_TYPES, String[] INPUT_CLASS, String[] INPUT_METHODS, String[] INPUT_iNFO) {

        if (false == STATE_MESSAGE_SET) {
            Global_Message_Type_Set     (INPUT_TYPES);      // - String
            Global_Message_Class_Set    (INPUT_CLASS);      // - String
            Global_Message_Method_Set   (INPUT_METHODS);    // - String
            Global_Message_Info_Set     (INPUT_iNFO);       // - String
            global.STATE_MESSAGE_SET = true;
        } else {
            System.out.println("|TYPE: ERROR |CLASS: global |ACTION: SYSTEM-CreateGlobalValues |INFO: Attempting to load global values in after initialization occured");
        }
    }

    private static void Global_Message_Type_Set         (String[] INPUT_TYPES) {
        global.Global_Message_Type      = INPUT_TYPES.clone();
    }

    private static void Global_Message_Class_Set        (String[] INPUT_CLASS) {
        global.Global_Message_Class     = INPUT_CLASS.clone();
    }
    
    private static void Global_Message_Method_Set       (String[] INPUT_METHODS) {
        global.Global_Message_Action    = INPUT_METHODS.clone();
    }

    private static void Global_Message_Info_Set         (String[] INPUT_iNFO) {
        global.Global_Message_Info      = INPUT_iNFO.clone();
    }


    
    public static String Global_Message_Type_Return     (int INPUT_POSITION) {
        if ( (INPUT_POSITION < global.Global_Message_Type.length) && (INPUT_POSITION >= 0) && (global.Global_Message_Type.length > 0)) {
            return global.Global_Message_Type[INPUT_POSITION];
        } else {
            System.out.println("|TYPE: ERROR |CLASS: global |ACTION: SYSTEM-GetGlobalValues |INFO: Invalid array access: Global_Message_Type");
            return "INVALIDSTRING";
        }
    }

    public static String Global_Message_Class_Return    (int INPUT_POSITION) {
        if ( (INPUT_POSITION < global.Global_Message_Class.length) && (INPUT_POSITION >= 0) && (global.Global_Message_Class.length > 0)) {
            return global.Global_Message_Class[INPUT_POSITION];
        } else {
            System.out.println("|TYPE: ERROR |CLASS: global |ACTION: SYSTEM-GetGlobalValues |INFO: Invalid array access: Global_Message_Class");
            return "INVALIDSTRING";
        }
    }

    public static String Global_Message_Action_Return   (int INPUT_POSITION) {
        if ( (INPUT_POSITION < global.Global_Message_Action.length) && (INPUT_POSITION >= 0) && (global.Global_Message_Action.length > 0)) {
            return global.Global_Message_Action[INPUT_POSITION];
        } else {
            System.out.println("|TYPE: ERROR |CLASS: global |ACTION: SYSTEM-GetGlobalValues |INFO: Invalid array access: Global_Message_Action");
            return "INVALIDSTRING";
        }
    }

    public static String Global_Message_Info_Return     (int INPUT_POSITION) {
        if ( (INPUT_POSITION < global.Global_Message_Info.length) && (INPUT_POSITION >= 0) && (global.Global_Message_Info.length > 0)) {
            return global.Global_Message_Info[INPUT_POSITION];
        } else {
            System.out.println("|TYPE: ERROR |CLASS: global |ACTION: SYSTEM-GetGlobalValues |INFO: Invalid array access: Global_Message_Info");
            return "INVALIDSTRING";
        }
    }


    public static int Global_Message_Type_ReturnSize    () {
        return global.Global_Message_Type.length;
    }

    public static int Global_Message_Class_ReturnSize   () {
        return global.Global_Message_Class.length;
    }

    public static int Global_Message_Action_ReturnSize  () {
        return global.Global_Message_Action.length;
    }

    public static int Global_Message_Info_Return        () {
        return global.Global_Message_Info.length;
    }



    /**
     * Global_Array_WeekDay_Set()
     * Description: USed to set the various standardized weekday strings used throughout the program.
     * @param INPUT_SHORT
     * @param INPUT_SHORTCAP
     * @param INPUT_LONG
     * @param INPUT_LONGCAP
     */
    public static void Global_Array_WeekDay_Set (String[] INPUT_SHORT, String[] INPUT_SHORTCAP, String[] INPUT_LONG, String[] INPUT_LONGCAP) {
        if (false == STATE_WEEKDAYS_SET) {
            Global_Array_WeekDay_Short_Set      (INPUT_SHORT);      // - String
            Global_Array_WeekDay_ShortCap_Set   (INPUT_SHORTCAP);   // - String
            Global_Array_WeekDay_Long_Set       (INPUT_LONG);       // - String
            Global_Array_WeekDay_LongCap_Set    (INPUT_LONGCAP);    // - String
            global.STATE_WEEKDAYS_SET           = true;
        } else {
            System.out.println("|TYPE: ERROR  |CLASS: global |ACTION: SYSTEM-CreateGlobalValues |INFO: Attempting to load global values in after initialization occured");
        }
    }

    private static void Global_Array_WeekDay_Short_Set      (String[] INPUT_SHORT) {
        global.Global_Data_WeekDays_Short       = INPUT_SHORT.clone();
    }

    private static void Global_Array_WeekDay_ShortCap_Set   (String[] INPUT_SHORTCAP) {
        global.Global_Data_WeekDays_ShortCap    = INPUT_SHORTCAP.clone();
    }

    private static void Global_Array_WeekDay_Long_Set       (String[] INPUT_LONG) {
        global.Global_Data_WeekDays_Long        = INPUT_LONG.clone();
    }

    private static void Global_Array_WeekDay_LongCap_Set    (String[] INPUT_LONGCAP) {
        global.Global_Data_WeekDays_LongCap     = INPUT_LONGCAP.clone();
    }



    public static String[] Global_Array_WeekDay_Short_Get() {
        if (global.Global_Data_WeekDays_Short != null) {
            return global.Global_Data_WeekDays_Short.clone();
        } else {
            System.out.println("|TYPE: ERROR  |CLASS: global |ACTION: SYSTEM-GetGlobalValues |INFO: Array is null: Global_Data_WeekDays_Short");
            global.Global_Data_WeekDays_Short = new String[] {"INVALID ARRAY"};
            return Global_Data_WeekDays_Short;
        }
    }

    public static String[] Global_Array_WeekDay_ShortCap_Get() {
        if (global.Global_Data_WeekDays_ShortCap != null) {
            return global.Global_Data_WeekDays_ShortCap.clone();
        } else {
            System.out.println("|TYPE: ERROR  |CLASS: global |ACTION: SYSTEM-GetGlobalValues |INFO: Array is null: Global_Data_WeekDays_ShortCap");
            global.Global_Data_WeekDays_ShortCap = new String[] {"INVALID ARRAY"};
            return Global_Data_WeekDays_ShortCap;
        }
    }

    public static String[] Global_Array_WeekDay_Long_Get() {
        if (global.Global_Data_WeekDays_Long != null) {
            return global.Global_Data_WeekDays_Long.clone();
        } else {
            System.out.println("|TYPE: ERROR  |CLASS: global |ACTION: SYSTEM-GetGlobalValues |INFO: Array is null: Global_Data_WeekDays_Long");
            global.Global_Data_WeekDays_Long = new String[] {"INVALID ARRAY"};
            return Global_Data_WeekDays_Long;
        }
    }

    public static String[] Global_Array_WeekDay_LongCap_Get() {
        if (global.Global_Data_WeekDays_LongCap != null) {
            return global.Global_Data_WeekDays_LongCap.clone();
        } else {
            System.out.println("|TYPE: ERROR  |CLASS: global |ACTION: SYSTEM-GetGlobalValues |INFO: Array is null: Global_Data_WeekDays_LongCap");
            global.Global_Data_WeekDays_LongCap = new String[] {"INVALID ARRAY"};
            return Global_Data_WeekDays_LongCap;
        }
    }





    /**
     * Global_Array_BasicValue_Set()
     * Description: Sets basic standardized value arrays used throughout the program
     * @param INPUT_WEEKLENGTH
     * @param INPUT_TIMEINTERVALS
     * @param INPUT_IDENTINTERVAL
     * @param INPUT_AMPM
     */
    public static void Global_Array_BasicValue_Set (int[] INPUT_WEEKLENGTH, int[] INPUT_TIMEINTERVALS, int[] INPUT_IDENTINTERVAL, String[] INPUT_AMPM) {
        if (false == STATE_BASICVALUES_SET) {
            Global_Array_WeekLength_Set     (INPUT_WEEKLENGTH);     // - int[]
            Global_Array_TimeIntervals_Set  (INPUT_TIMEINTERVALS);  // - int[]
            Global_Array_IdentConstraint_Set(INPUT_IDENTINTERVAL);  // - int[]
            Global_Array_AMPM_Set           (INPUT_AMPM);           // - String[]
            global.STATE_BASICVALUES_SET        = true;
        } else {
            System.out.println("|TYPE: ERROR  |CLASS: global |ACTION: SYSTEM-CreateGlobalValues |INFO: Attempting to load global values in after initialization occured");
        }
    }

    private static void Global_Array_WeekLength_Set         (int[] INPUT_WEEKLENGTH) {
        global.Global_Data_WeekLength       = INPUT_WEEKLENGTH.clone();
    }

    private static void Global_Array_TimeIntervals_Set      (int[] INPUT_TIMEINTERVALS) {
        global.Global_Data_TimeIntervals    = INPUT_TIMEINTERVALS.clone();
    }

    private static void Global_Array_IdentConstraint_Set    (int[] INPUT_IDENTINTERVAL) {
        global.Global_Data_IdentConstraint  = INPUT_IDENTINTERVAL.clone();
    }

    private static void Global_Array_AMPM_Set               (String[] INPUT_AMPM) {
        global.Global_Data_AMPM             = INPUT_AMPM.clone();
    }



    public static int Global_Array_WeekLength_Get           (int INPUT_POSITION) {
        if ( (INPUT_POSITION < global.Global_Data_WeekLength.length) && (INPUT_POSITION >= 0) && (global.Global_Data_WeekLength.length > 0)) {
            return global.Global_Data_WeekLength[INPUT_POSITION];
        } else {
            System.out.println("|TYPE: ERROR  |CLASS: global |ACTION: SYSTEM-GetGlobalValues |INFO: Invalid array access: Global_Data_WeekLength");
            return 0;
        }
    }

    public static int Global_Array_TimeIntervals_Get        (int INPUT_POSITION) {
        if ( (INPUT_POSITION < global.Global_Data_TimeIntervals.length) && (INPUT_POSITION >= 0) && (global.Global_Data_TimeIntervals.length > 0)) {
            return global.Global_Data_TimeIntervals[INPUT_POSITION];
        } else {
            System.out.println("|TYPE: ERROR  |CLASS: global |ACTION: SYSTEM-GetGlobalValues |INFO: Invalid array access: Global_Data_TimeIntervals");
            return 0;
        }
    }

    public static int Global_Array_IdentConstraint_Get      (int INPUT_POSITION) {
        if ( (INPUT_POSITION < global.Global_Data_IdentConstraint.length) && (INPUT_POSITION >= 0) && (global.Global_Data_IdentConstraint.length > 0)) {
            return global.Global_Data_IdentConstraint[INPUT_POSITION];
        } else {
            System.out.println("|TYPE: ERROR  |CLASS: global |ACTION: SYSTEM-GetGlobalValues |INFO: Invalid array access: Global_Data_IdentConstraint");
            return 0;
        }
    }

    public static String Global_Array_AMPM_Get              (int INPUT_POSITION) {
        if ( (INPUT_POSITION < global.Global_Data_AMPM.length) && (INPUT_POSITION >= 0) && (global.Global_Data_AMPM.length > 0)) {
            return global.Global_Data_AMPM[INPUT_POSITION];
        } else {
            System.out.println("|TYPE: ERROR  |CLASS: global |ACTION: SYSTEM-GetGlobalValues |INFO: Invalid array access: Global_Data_AMPM");
            return "INVALID STRING";
        }
    }

    public static String[] Global_Array_AMPM_GetFull        () {
        return global.Global_Data_AMPM.clone();
    }





    /**
     * Global_Array_UISpacing_Set()
     * Description: Used to set the standardized UI Spacing values used troughout the program
     * @param INPUT_SPACE
     * @param INPUT_PAD
     * @param INPUT_WIDTH
     * @param INPUT_HEIGTH
     * @param INPUT_ANCHOR
     */
    public static void Global_Array_UISpacing_Set (double[] INPUT_SPACE, double[] INPUT_PAD, double[] INPUT_WIDTH, double[] INPUT_HEIGTH, double[] INPUT_ANCHOR) {
        if (false == STATE_UISPACING_SET) {
            Global_Array_Spacing_Set    (INPUT_SPACE);
            Global_Array_Padding_Set    (INPUT_PAD);
            Global_Array_Width_Set      (INPUT_WIDTH);
            Global_Array_Height_Set     (INPUT_HEIGTH);
            Global_Array_Anchor_Set     (INPUT_ANCHOR);
            global.STATE_UISPACING_SET  = true;
        } else {
            System.out.println("|TYPE: ERROR  |CLASS: global |ACTION: SYSTEM-CreateGlobalValues |INFO: Attempting to load global values in after initialization occured");
        }
    }

    private static void Global_Array_Spacing_Set    (double[] INPUT_SPACE) {
        global.Global_Data_Spacing  = INPUT_SPACE.clone();
    }

    private static void Global_Array_Padding_Set    (double[] INPUT_PAD) {
        global.Global_Data_Padding  = INPUT_PAD.clone();
    }

    private static void Global_Array_Width_Set      (double[] INPUT_WIDTH) {
        global.Global_Data_Width    = INPUT_WIDTH.clone();
    }

    private static void Global_Array_Height_Set     (double[] INPUT_HEIGTH) {
        global.Global_Data_Height   = INPUT_HEIGTH.clone();
    }

    private static void Global_Array_Anchor_Set     (double[] INPUT_ANCHOR) {
        global.Global_Data_Anchor   = INPUT_ANCHOR.clone();
    }



    public static double Global_Array_Spacing_Get   (int INPUT_POSITION) {
        if ( (INPUT_POSITION < global.Global_Data_Spacing.length) && (INPUT_POSITION >= 0) && (global.Global_Data_Spacing.length > 0)) {
            return global.Global_Data_Spacing[INPUT_POSITION];
        } else {
            System.out.println("|TYPE: ERROR  |CLASS: global |ACTION: SYSTEM-GetGlobalValues |INFO: Invalid array access: Global_Data_Spacing");
            return 0.0;
        }
    }

    public static double Global_Array_Padding_Get   (int INPUT_POSITION) {
        if ( (INPUT_POSITION < global.Global_Data_Padding.length) && (INPUT_POSITION >= 0) && (global.Global_Data_Padding.length > 0)) {
            return global.Global_Data_Padding[INPUT_POSITION];
        } else {
            System.out.println("|TYPE: ERROR  |CLASS: global |ACTION: SYSTEM-GetGlobalValues |INFO: Invalid array access: Global_Data_Padding");
            return 0.0;
        }
    }

    public static double Global_Array_Width_Get     (int INPUT_POSITION) {
        if ( (INPUT_POSITION < global.Global_Data_Width.length) && (INPUT_POSITION >= 0) && (global.Global_Data_Width.length > 0)) {
            return global.Global_Data_Width[INPUT_POSITION];
        } else {
            System.out.println("|TYPE: ERROR  |CLASS: global |ACTION: SYSTEM-GetGlobalValues |INFO: Invalid array access: Global_Data_Width");
            return 0.0;
        }
    }

    public static double Global_Array_Height_Get    (int INPUT_POSITION) {
        if ( (INPUT_POSITION < global.Global_Data_Height.length) && (INPUT_POSITION >= 0) && (global.Global_Data_Height.length > 0)) {
            return global.Global_Data_Height[INPUT_POSITION];
        } else {
            System.out.println("|TYPE: ERROR  |CLASS: global |ACTION: SYSTEM-GetGlobalValues |INFO: Invalid array access: Global_Data_Height");
            return 0.0;
        }
    }

    public static double Global_Array_Anchor_Get    (int INPUT_POSITION) {
        if ( (INPUT_POSITION < global.Global_Data_Anchor.length) && (INPUT_POSITION >= 0) && (global.Global_Data_Anchor.length > 0)) {
            return global.Global_Data_Anchor[INPUT_POSITION];
        } else {
            System.out.println("|TYPE: ERROR  |CLASS: global |ACTION: SYSTEM-GetGlobalValues |INFO: Invalid array access: Global_Data_Anchor");
            return 0.0;
        }
    }




} // global{}
