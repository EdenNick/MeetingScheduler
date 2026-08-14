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

    // Data Access Layer
    // ############################################################
    // PROG_DAL_B_JSONManager.java
    public static final String ERROR_SetToFile                      = "ERROR - PROG_DAL_B_JSONManager           - SetToFile()           - object refence null or missing values";

    // PROG_DAL_C_TXTInput.java
    public static final String ERROR_SetFileNameOperationUnderWay   = "ERROR - PROG_DAL_C_TXTInput              - setFileName()         - called while file operation underway";
    public static final String ERROR_CheckStateNoFileName           = "ERROR - PROG_DAL_C_TXTInput              - CheckState()          - tried file operation whithout setting FileName";
    public static final String ERROR_CheckStateNoLock               = "ERROR - PROG_DAL_C_TXTInput              - CheckState()          - tried file opertion without lock set";
    public static final String ERROR_WriteDataCheckState            = "ERROR - PROG_DAL_C_TXTInput              - writeData()           - Check state failed";
    public static final String ERROR_WritedataFilewrite             = "ERROR - PROG_DAL_C_TXTInput              - writeData()           - try/catch File write";
    public static final String ERROR_DeleteDataCheckState           = "ERROR - PROG_DAL_C_TXTInput              - DeleteData()          - Check state failed";
    public static final String ERROR_DeleteDataFileReadWrite        = "ERROR - PROG_DAL_C_TXTInput              - DeleteData()          - try/catch error";
    public static final String ERROR_OrganizeDataCheckState         = "ERROR - PROG_DAL_C_TXTInput              - OrganizeData()        - Check state failed";

    // PROG_DAL_C_TXTOutput.java
    public static final String ERROR_ReadFileTryCatch               = "ERROR - PROG_DAL_C_TXTOutput             - ReadFile()            - try/catch error";
    public static final String ERROR_ReadUsertryCatch               = "ERROR - PROG_DAL_C_TXTOutput             - ReadUser()            - try/catch error";
    // ############################################################



    // Business Logic Layer
    // ############################################################
    // PROG_INFO_InfoFileWrite.java
    public static final String ERROR_CheckUserInfoIDInput           = "ERROR - PROG_BLL_InfoFileWrite           - CheckUserInfo()       - id already input";
    public static final String ERROR_CheckUserInfoInvalidName       = "ERROR - PROG_BLL_InfoFileWrite           - CheckUserInfo()       - invalid name, blank or white space";
    public static final String ERROR_CheckUserInfoInvalidID         = "ERROR - PROG_BLL_InfoFileWrite           - CheckUserInfo()       - invalid id, less than -1";
    public static final String ERROR_CheckUserInfoInvalidDay        = "ERROR - PROG_BLL_InfoFileWrite           - CheckUserInfo()       - invalid Day, String blank at position: ";
    public static final String ERROR_CheckUserInfoInvalidInterval   = "ERROR - PROG_BLL_InfoFileWrite           - CheckUserInfo()       - invalid interval, missing day at position: ";
    public static final String ERROR_CheckUserInfoInvalidStartHour  = "ERROR - PROG_BLL_InfoFileWrite           - CheckUserInfo()       - invalid Begin Hour, Hour above or below interval limit at position: ";
    public static final String ERROR_CheckUserInfoInvalidStartMin   = "ERROR - PROG_BLL_InfoFileWrite           - CheckUserInfo()       - invalid Begin Min, Min above or below interval limit at position: ";
    public static final String ERROR_CheckUserInfoInvalidEndHour    = "ERROR - PROG_BLL_InfoFileWrite           - CheckUserInfo()       - invalid End Hour, Hour above or below interval limit at position: ";
    public static final String ERROR_CheckUserInfoInvalidEndmin     = "ERROR - PROG_BLL_InfoFileWrite           - CheckUserInfo()       - invalid End Min, Min above or below interval limit at position: ";
    public static final String PASS_CheckUserInfoValidInputs        = "PASS  - PROG_BLL_InfoFileWrite           - CheckUserInfo()       - All inputs valid";

    // PROG_BLL_SchedulingCalculation.java
    public static final String ERROR_CalculateSchedulePeopleSet     = "ERROR - PROG_BLL_SchedulingCalculation   - CalculateSchedule()   - PeopleSet is false.";
    public static final String ERROR_CalculateScheduleBoolean       = "ERROR - PROG_BLL_SchedulingCalculation   - CalculateSchedule()   - if/else loop UserTimes not true or false";
    public static final String INFO_CalculateScheduleNoTimePref     = "INFO  - PROG_BLL_SchedulingCalculation   - CalculateSchedule()   - Specifc time preference NOT set.";
    public static final String INFO_CalculateScheduleTimePref       = "INFO  - PROG_BLL_SchedulingCalculation   - CalculateSchedule()   - Specifc time preference set.";
    public static final String INFO_CalculateSchedulePersonDay      = "INFO  - PROG_BLL_SchedulingCalculation   - CalculateSchedule()   - Persons preference Matches Day, ID: ";
    public static final String INFO_CalculateScheduleIDAdded        = "INFO  - PROG_BLL_SchedulingCalculation   - CalculateSchedule()   - A ID was successfully added to Calc_AvailableIDs";
    public static final String PASS_CalculateScheduleCompleteCalc   = "PASS  - PROG_BLL_SchedulingCalculation   - CalculateSchedule()   - Complete Schedule Calculation.";
    // ############################################################



    private PROG_DAL_D_SystemMessages() {
        // Do nothing
    }
    
}
