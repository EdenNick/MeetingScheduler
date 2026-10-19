package meeting_scheduler.FileManagement;

public class DataFilePath {
    

    // JSON FILE PATHS
    public static final String JSN_DATACARD     = "app\\src\\main\\java\\meeting_scheduler\\Storage\\PROG_DATA_A_UserDataCard.json";
    public static final String JSN_TEST         = "src/main/resources/PROG_DATA_A_JsonTestFile.json";
    
    // PROPERTIES FILE PATHS
    public static final String PRP_MSGTYPE      = "/GlobalMessageTypes.properties";
    public static final String PRP_MSGCLASS     = "/GlobalMessageClass.properties";
    public static final String PRP_MSGACTION    = "/GlobalPropertiesAction.properties";
    public static final String PRP_MSGINFO      = "/GlobalMessageInfo.properties";

    public static final String PRP_VALARRAY     = "/GlobalValuesArrays.properties";
    public static final String PRP_VALUNIQUE    = "/GlobalValuesUnique.properties";

    public static final String PRP_INSTRUCT     = "/USERINSTRUCTIONS.properties";
    // TXT FILE PATHS
    public static final String TXT_INSTRUCT     = "src/main/resources/PROG_UI_D_Instructions.txt";
    public static final String TXT_TEMP         = "src/PROG_DATA_TempFile.txt";
    
    private DataFilePath() {
        // prevents instantiation
    }
}
