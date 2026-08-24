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
    // Application Window Size
    public static final int     WindowStartWidth            = 900;
    public static final int     WindowStartHeight           = 700;

    // Main Menu
    // ############################################################
    // UI Spacing
    public static final int     MENU_UI_Spacing             = 20;
    // UI anchor positioning
    public static final double  MENU_UI_TopAnchor           = 30.0;
    public static final double  MENU_UI_LeftAnchor          = 30.0;
    public static final double  MENU_EndProg_BottomAnchor   = 20.0;
    public static final double  MENU_EndProg_RightAnchor    = 20.0;
    // Fade transitions - seconds
    public static final int     MENU_FadeTime               = 2;
    public static final int     MENU_UnFadeTime             = 2;
    public static final int     MENU_FadeOpacity            = 0;
    public static final int     MENU_UnFadeOpacity          = 1;
    // button sizing
    public static final double  MENU_ButtonWidth            = 222.0;
    public static final double  MENU_ButtonHeight           = 45.0;
    public static final double  MENU_endButtonWidth         = 100.0;
    public static final double  MENU_endButtonHeight        = 50.0;
    // ############################################################

    // Instruciton Page
    // ############################################################
    // UI anchor positioning
    public static final double  INSTRUCTION_Return_RightAnchor      = 20.0;
    public static final double  INSTRUCTION_Return_BottomAnchor     = 20.0;
    public static final double  INSTRUCTION_Instruction_TopAnchor   = 20.0;
    public static final double  INSTRUCTION_Instruction_LeftAnchor  = 20.0;
    // Fade transitions - seconds
    public static final int     INSTRUCTION_FadeTime                = 2;
    public static final int     INSTRUCTION_UnFadeTime              = 2;
    public static final int     INSTRUCTION_FadeOpacity             = 0;
    public static final int     INSTRUCTION_UnFadeOpacity           = 1;
    // ############################################################

    // Scheduling Page
    // ############################################################
    // UI anchor positioning
    public static final double  SCHEDULE_Return_RightAnchor         = 10.0;
    public static final double  SCHEDULE_Return_BottomAnchor        = 10.0;
    public static final double  SCHEDULE_UIInput_LeftAnchor         = 20.0;
    public static final double  SCHEDULE_UIInput_TopAnchor          = 20.0;
    public static final double  SCHEDULE_UIOutput_RightAnchor       = 20.0;
    public static final double  SCHEDULE_UIOutput_TopAnchor         = 20.0;

    // UI Input Components
    // Default width and height for UI Input
    public static final double  SCHEDULE_UIINPUT_PREFWidth1         = 600.0;
    public static final double  SCHEDULE_UIINPUT_PREFHeight1        = 120.0;
    // Default Spacing
    public static final int     SCHEDULE_UIINPUT_PREFSpacing        = 10;
    // Default Insents
    public static final int     SCHEDULE_UIINPUT_PREFInsets         = 10;
    // Default primary node 1 width/height
    public static final double  SCHEDULE_PrimaryNode1_PREFWidth     = 250.0;
    public static final double  SCHEDULE_PrimaryNode1_PREFHeight    = 100.0;
    // Default primary node 2 width/height
    public static final double  SCHEDULE_PrimaryNode2_PREFWidth     = 330.0;
    public static final double  SCHEDULE_PrimaryNode2_PREFHeight    = 100.0;
    // Button Sizing
    public static final double  SCHEDULE_Button_PrefWidthSmall      = 80.0;
    public static final double  SCHEDULE_Button_PrefHeightSmall     = 30.0;
    public static final double  SCHEDULE_Button_PrefWidthMed        = 100.0;
    public static final double  SCHEDULE_Button_PrefHeightMed       = 30.0;
    public static final double  SCHEDULE_Button_PrefWidthLarge      = 150.0;
    public static final double  SCHEDULE_Button_PrefHeightLarge     = 30.0;
    // Input Sizing
    public static final double  SCHEDULE_INPUT_PrefWidthLarge       = 240.0;
    public static final double  SCHEDULE_INPUT_PrefHeightLarge      = 30.0;



    // UI Output Components
    // Default Spacing
    public static final int     SCHEDULE_UIOUTPUT_PREFSpacing       = 10;
    // Default Insents
    public static final int     SCHEDULE_UIOUTPUT_PREFInsets        = 10;
    // primaryBox
    public static final double  SCHEDULE_OUTPUT_primaryheight       = 120.0;
    public static final double  SCHEDULE_OUTPUT_primarywidth        = 800.0;

    public static final double  SCHEDULE_OUTPUT_Secondaryheight1    = 90.0;
    public static final double  SCHEDULE_OUTPUT_Secondarywidth1     = 600.0;

    public static final double  SCHEDULE_OUTPUT_Secondaryheight2    = 50.0;
    public static final double  SCHEDULE_OUTPUT_Secondarywidth2     = 120.0;

    public static final double  SCHEDULE_OUTPUT_Secondaryheight3    = 50.0;
    public static final double  SCHEDULE_OUTPUT_Secondarywidth3     = 100.0;

    public static final double  SCHEDULE_OUTPUT_Secondaryheight4    = 50.0;
    public static final double  SCHEDULE_OUTPUT_Secondarywidth4     = 220.0;

    public static final double  SCHEDULE_OUTPUT_DeleteButtonWidth   = 50.0;
    public static final double  SCHEDULE_OUTPUT_DeleteButtonHeight  = 50.0;
    // ############################################################


    // Style variable names
    // ############################################################
    public static final String STYLE_DEFAULT                    = "default-label";
    public static final String STYLE_MENU_MenuOptions           = "MainMenu_UI_MenuOptions";
    public static final String STYLE_MENU_Endprogram            = "MainMenu_Button_Endprogram";
    public static final String STYLE_DATACARD_TimePref          = "DataCard_UI_TimePref";
    public static final String STYLE_DATACARD_DefaultUI         = "DataCard_UI_DefaultInputUI";
    public static final String STYLE_DATACARD_TimeOutput        = "DataCard_UI_TimeOutput";
    public static final String STYLE_DATACARD_TimeOutputCard    = "DataCard_UI_TimeOutputCard";
    public static final String STYLE_INSTRUCTION_TextHolder     = "Instructions_UI_TextHolder";
    public static final String STYLE_INSTRUCTION_TextTitle      = "Instructions_UI_TextTitle";
    public static final String STYLE_INSTRUCTION_ScrollPane     = "Instructions_UI_ScrollPane";
    public static final String STYLE_SCHEDULLE_Base             = "Schedule_UI_Base";
    public static final String STYLE_SCHEDULLE_MainInputs       = "Schedule_UI_MainInputs";
    public static final String STYLE_SCHEDULLE_IndividualInput  = "Schedule_UI_IndividualInput";
    public static final String STYLE_SCHEDULLE_NameFlowBox      = "Schedule_UI_NameFlowBox";
    public static final String STYLE_SCHEDULLE_ComboBox         = "Schedule_UI_ComboBox";
    public static final String STYLE_SCHEDULLE_ScheduleListBox  = "Schedule_UI_ScheduleListBox";
    // ############################################################


    
    /** 
     * General variables 
     * 
     */
    public static final String[]    WEEKDAYS        = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    public static final String[]    AMPM            = {"AM", "PM"};
    public static final String      AM              = "AM";
    public static final String      PM              = "PM";
    public static final String      EmptyText       = "";
    public static final int         MAXListAmmount  = 20;
    public static final int         MAXTimeInputs   = 30;


    /**
     * File Paths
     */
    // Json Files
    // ############################################################
    public static final String JSON_UserDataCard    = "src\\main\\resources\\PROG_DATA_A_UserDataCard.json";
    public static final String JSON_TestFile        = "src\\main\\resources\\PROG_DATA_A_JsonTestFile.json";
    // ############################################################
    
    // TXT files
    // ############################################################
    public static final String TXT_InputTempFile    = "src\\PROG_DATA_TempFile.txt";
    public static final String DOC_Instructions     = "/PROG_UI_D_Instructions.txt";
    // ############################################################
    
    // CSS styles
    // ############################################################
    public static final String CSS_Styles           = "/CSS_Styles.css";
    // ############################################################



    /**
     * Label strings
     */

    // PROG_UI_C_DataCardinfoScene.java
    // ############################################################
    public static final String Prompt_Name              = "Enter Name Here:";
    public static final String Prompt_Name2             = "Enter Full Name";
    public static final String Prompt_ID                = "Enter ID Here:";
    public static final String Prompt_ID2               = "Enter ID";
    public static final String Prompt_Day               = "Select weekday here";
    public static final String Prompt_BeginningTime     = "Input start time:";
    public static final String Prompt_EndingTime        = "Input End time";
    public static final String Prompt_HourLabel         = "Hour";
    public static final String Prompt_MinuteLabel       = "Minute";
    // ############################################################


    // PROG_UI_C_InstructionsScene.java
    // ############################################################

    // ############################################################


    // PROG_UI_C_SchedulePeopleScene.java
    // ############################################################

    // ############################################################


}
