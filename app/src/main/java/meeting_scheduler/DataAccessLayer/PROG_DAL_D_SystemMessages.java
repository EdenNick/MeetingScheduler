/**
 * PROG_DAL_D_SystemMessages.java
 * 
 * Description: contains the various system messages used throughout the program
 */

// Package  - DO Not Change
// ############################################################
package meeting_scheduler.DataAccessLayer;
// ############################################################

// imports
// ############################################################
// ############################################################



public class PROG_DAL_D_SystemMessages {


    private PROG_DAL_D_SystemMessages() {
        // Do nothing
    }

    // Data Access Layer
    // ############################################################
    // PROG_DAL_B_JSONManager.java
    public static final String ERROR_SetToFile                      = "ERROR    - PROG_DAL_B_JSONManager           - SetToFile()           - object refence null or missing values";

    // PROG_DAL_C_TXTInput.java
    public static final String ERROR_SetFileNameOperationUnderWay   = "ERROR    - PROG_DAL_C_TXTInput              - setFileName()         - called while file operation underway";
    public static final String ERROR_CheckStateNoFileName           = "ERROR    - PROG_DAL_C_TXTInput              - CheckState()          - tried file operation whithout setting FileName";
    public static final String ERROR_CheckStateNoLock               = "ERROR    - PROG_DAL_C_TXTInput              - CheckState()          - tried file opertion without lock set";
    public static final String ERROR_WriteDataCheckState            = "ERROR    - PROG_DAL_C_TXTInput              - writeData()           - Check state failed";
    public static final String ERROR_WritedataFilewrite             = "ERROR    - PROG_DAL_C_TXTInput              - writeData()           - try/catch File write";
    public static final String ERROR_DeleteDataCheckState           = "ERROR    - PROG_DAL_C_TXTInput              - DeleteData()          - Check state failed";
    public static final String ERROR_DeleteDataFileReadWrite        = "ERROR    - PROG_DAL_C_TXTInput              - DeleteData()          - try/catch error";
    public static final String ERROR_OrganizeDataCheckState         = "ERROR    - PROG_DAL_C_TXTInput              - OrganizeData()        - Check state failed";
    public static final String ERROR_OrganizeDataTRYCATCH           = "ERROR    - PROG_DAL_C_TXTInput              - OrganizeData()        - try/catch error file read";

    // PROG_DAL_C_TXTOutput.java
    public static final String ERROR_ReadFileTryCatch               = "ERROR    - PROG_DAL_C_TXTOutput             - ReadFile()            - try/catch error";
    public static final String ERROR_ReadUsertryCatch               = "ERROR    - PROG_DAL_C_TXTOutput             - ReadUser()            - try/catch error";
    // ############################################################



    // Business Logic Layer
    // ############################################################
    // PROG_INFO_InfoFileWrite.java
    public static final String ERROR_CheckUserInfoIDInput           = "ERROR    - PROG_BLL_InfoFileWrite            - CheckUserInfo()       - id already input \n";
    public static final String ERROR_CheckUserInfoInvalidName       = "ERROR    - PROG_BLL_InfoFileWrite            - CheckUserInfo()       - invalid name, blank or white space \n";
    public static final String ERROR_CheckUserInfoInvalidID         = "ERROR    - PROG_BLL_InfoFileWrite            - CheckUserInfo()       - invalid id, less than -1 \n";
    public static final String ERROR_CheckUserInfoInvalidDay        = "ERROR    - PROG_BLL_InfoFileWrite            - CheckUserInfo()       - invalid Day, String blank at position: ";
    public static final String ERROR_CheckUserInfoInvalidInterval   = "ERROR    - PROG_BLL_InfoFileWrite            - CheckUserInfo()       - invalid interval, missing day at position: ";
    public static final String ERROR_CheckUserInfoInvalidStartHour  = "ERROR    - PROG_BLL_InfoFileWrite            - CheckUserInfo()       - invalid Begin Hour, Hour above or below interval limit at position: ";
    public static final String ERROR_CheckUserInfoInvalidStartMin   = "ERROR    - PROG_BLL_InfoFileWrite            - CheckUserInfo()       - invalid Begin Min, Min above or below interval limit at position: ";
    public static final String ERROR_CheckUserInfoInvalidEndHour    = "ERROR    - PROG_BLL_InfoFileWrite            - CheckUserInfo()       - invalid End Hour, Hour above or below interval limit at position: ";
    public static final String ERROR_CheckUserInfoInvalidEndmin     = "ERROR    - PROG_BLL_InfoFileWrite            - CheckUserInfo()       - invalid End Min, Min above or below interval limit at position: ";
    public static final String PASS_CheckUserInfoValidInputs        = "PASS     - PROG_BLL_InfoFileWrite            - CheckUserInfo()       - All inputs valid \n";

    // PROG_BLL_SchedulingCalculation.java
    public static final String ERROR_CalculateSchedulePeopleSet     = "ERROR    - PROG_BLL_SchedulingCalculation    - CalculateSchedule()   - PeopleSet is false.  \n";
    public static final String ERROR_CalculateScheduleBoolean       = "ERROR    - PROG_BLL_SchedulingCalculation    - CalculateSchedule()   - if/else loop UserTimes not true or false  \n";
    public static final String INFO_CalculateScheduleStartCalc      = "INFO     - PROG_BLL_SchedulingCalculation    - CalculateSchedule()   - Scheduling Calculation has started  \n";
    public static final String INFO_CalculateScheduleTimePref       = "INFO     - PROG_BLL_SchedulingCalculation    - CalculateSchedule()   - Specifc time preference set: ";
    public static final String INFO_CalculateSchedulePersonDay      = "INFO     - PROG_BLL_SchedulingCalculation    - CalculateSchedule()   - Persons preference Matches Day, ID: ";
    public static final String INFO_CalculateScheduleIDAdded        = "INFO     - PROG_BLL_SchedulingCalculation    - CalculateSchedule()   - A ID was successfully added to Calc_AvailableIDs  \n";
    public static final String INFO_CalculateSchedulePersonAdded    = "INFO     - PROG_BLL_SchedulingCalculation    - CalculateSchedule()   - A PROG_DAL_A_InfoInput Person was successfully added to CALC_AvailablePeople  \n";
    public static final String PASS_CalculateScheduleCompleteCalc   = "PASS     - PROG_BLL_SchedulingCalculation    - CalculateSchedule()   - Complete Schedule Calculation.  \n";
    // ############################################################



    // Presentation Layer
    // ############################################################
    // PROG_UI_B_DataCardinfoScene.java
    public static final String BUTTON_DataCard_returnHome           = "BUTTON   - PROG_UI_B_DataCardinfoScene       - CARD MANAGER PAGE     - Return to home page  \n";
    public static final String BUTTON_DataCard_AddInfo              = "BUTTON   - PROG_UI_B_DataCardinfoScene       - CARD MANAGER PAGE     - Add User Info \n";
    public static final String BUTTON_DataCard_SubmitInfo           = "BUTTON   - PROG_UI_B_DataCardinfoScene       - CARD MANAGER PAGE     - Submit User Info \n";
    public static final String INFO_DataCard_InvalidName            = "INFO     - PROG_UI_B_DataCardinfoScene       - CARD MANAGER PAGE     - user has not submitted a valid name \n";
    public static final String INFO_DataCard_InvalidID              = "INFO     - PROG_UI_B_DataCardinfoScene       - CARD MANAGER PAGE     - user has not submitted a valid ID \n";
    public static final String INFO_DataCard_InvalidTimes           = "INFO     - PROG_UI_B_DataCardinfoScene       - CARD MANAGER PAGE     - user has not submitted valid user times \n";
    // PROG_UI_B_InstructionsScene.java
    public static final String BUTTON_Instruction_returnHome        = "BUTTON   - PROG_UI_B_InstructionsScene       - INSTRUCTION PAGE      - Returning to home page \n";
    // PROG_UI_B_MainMenuScene.java
    public static final String BUTTON_MainMenu_end                  = "BUTTON   - PROG_UI_B_MainMenuScene           - MAIN MENU             - Program Ending \n";
    public static final String BUTTON_MainMenu_ToDataCard           = "BUTTON   - PROG_UI_B_MainMenuScene           - MAIN MENU             - Scene changing to datacard management \n";
    public static final String BUTTON_MainMenu_ToSchedule           = "BUTTON   - PROG_UI_B_MainMenuScene           - MAIN MENU             - Scene changing to schedule management \n";
    public static final String BUTTON_MainMenu_ToInstructions       = "BUTTON   - PROG_UI_B_MainMenuScene           - MAIN MENU             - Scene changing to Instructions \n";
    // PROG_UI_B_SchedulePeopleScene
    public static final String BUTTON_Schedule_returnHome           = "BUTTON   - PROG_UI_B_SchedulePeopleScene     - Schedule Page         - Returning to home page \n";
    public static final String BUTTON_Schedule_ResetPeople          = "BUTTON   - PROG_UI_B_SchedulePeopleScene     - Schedule Page         - Reset people to schedule \n";
    public static final String BUTTON_Schedule_InputPeople          = "BUTTON   - PROG_UI_B_SchedulePeopleScene     - Schedule Page         - Input Selected People \n";
    public static final String BUTTON_Schedule_RemovePerson         = "BUTTON   - PROG_UI_B_SchedulePeopleScene     - Schedule Page         - Remove last person \n";
    public static final String BUTTON_Schedule_ResetListNum         = "BUTTON   - PROG_UI_B_SchedulePeopleScene     - Schedule Page         - Reset number of lists to output \n";
    public static final String BUTTON_Schedule_InputListNum         = "BUTTON   - PROG_UI_B_SchedulePeopleScene     - Schedule Page         - Input Selected Ammount \n";
    public static final String BUTTON_Schedule_ResetDays            = "BUTTON   - PROG_UI_B_SchedulePeopleScene     - Schedule Page         - Reset days to schedule \n";
    public static final String BUTTON_Schedule_InputDays            = "BUTTON   - PROG_UI_B_SchedulePeopleScene     - Schedule Page         - Input Selected Day \n";
    public static final String BUTTON_Schedule_ResetTime            = "BUTTON   - PROG_UI_B_SchedulePeopleScene     - Schedule Page         - Reset time input \n";
    public static final String BUTTON_Schedule_InputTime            = "BUTTON   - PROG_UI_B_SchedulePeopleScene     - Schedule Page         - Add time User Info \n";
    public static final String BUTTON_Schedule_Calculate            = "BUTTON   - PROG_UI_B_SchedulePeopleScene     - Schedule Page         - Calculating schedule \n";
    public static final String BUTTON_Schedule_clear                = "BUTTON   - PROG_UI_B_SchedulePeopleScene     - Schedule Page         - Clearing calculated Schedules \n";
    // PROG_UI_C_UserTimeInput.java
    public static final String INFO_PreferenceInput_weekday         = "INFO     - PROG_UI_C_UserTimeInput           - Time Input            - User has not submitted a weekday \n";
    public static final String INFO_PreferenceInput_StartHour       = "INFO     - PROG_UI_C_UserTimeInput           - Time Input            - User has not submitted a beginning hour \n";
    public static final String INFO_PreferenceInput_StartMin        = "INFO     - PROG_UI_C_UserTimeInput           - Time Input            - User has not submitted a beginning minute \n";
    public static final String INFO_PreferenceInput_EndHour         = "INFO     - PROG_UI_C_UserTimeInput           - Time Input            - User has not submitted a ending hour \n";
    public static final String INFO_PreferenceInput_EndMin          = "INFO     - PROG_UI_C_UserTimeInput           - Time Input            - User has not submitted a ending minute \n";
    public static final String PASS_PreferenceInput_CorrectInput    = "INFO     - PROG_UI_C_UserTimeInput           - Time Input            - Correct info has been submitted \n";
    // ############################################################

    
}
