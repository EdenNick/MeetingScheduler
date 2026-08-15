/**
 * PROG_UI_D_TextLabels()
 * 
 * Description: Holds the text values used for the various abels throughout the UI
 */

// Package  - DO Not Change
// ############################################################
package meeting_scheduler.PresentationLayer;
// ############################################################



public final class PROG_UI_D_DataVariables {


    private PROG_UI_D_DataVariables() {

    }

    /**
     * Application Default variables
     */

    public static final int WindowStartWidth    = 900;
    public static final int WindowStartHeight   = 700;
    /** 
     * General variables 
     * 
     */
    public static final String[] WEEKDAYS = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

    public static final String[] AMPM = {"AM", "PM"};

    public static final String AM = "AM";

    public static final String PM = "PM";


    /**
     * File Paths
     * 
     */
    public static final String JSON_UserDataCard    = "src\\main\\resources\\PROG_DATA_A_UserDataCard.json";

    public static final String JSON_TestFile        = "src\\main\\resources\\PROG_DATA_A_JsonTestFile.json";

    public static final String TXT_InputTempFile    = "src\\PROG_DATA_TempFile.txt";

    public static final String CSS_Styles           = "/CSS_Styles.css";

    public static final String DOC_Instructions     = "/PROG_UI_D_Instructions.txt";
    /**
     * Label strings
     */

    // PROG_UI_C_DataCardinfoScene.java
    public static final String Prompt_Name              = "Enter Name Here:";
    public static final String Prompt_Name2             = "Enter Full Name";
    public static final String Prompt_ID                = "Enter ID Here:";
    public static final String Prompt_ID2               = "Enter ID";
    public static final String Prompt_Day               = "Select weekday here";
    public static final String Prompt_BeginningTime     = "Input start time:";
    public static final String Prompt_EndingTime        = "Input End time";
    public static final String Prompt_HourLabel         = "Hour";
    public static final String Prompt_MinuteLabel       = "Minute";

    // PROG_UI_C_InstructionsScene.java


    // PROG_UI_C_SchedulePeopleScene.java
    
}
