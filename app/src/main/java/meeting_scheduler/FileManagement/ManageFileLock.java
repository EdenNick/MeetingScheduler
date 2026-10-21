package meeting_scheduler.FileManagement;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ManageFileLock {
    
    // JSON FILE PATHS
    public static final Path JSN_DATACARD       = Paths.get( "app","Data", "PROG_DATA_A_UserDataCard.json");
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




    private static final File JSON_USER_DATACARD = ManageFileLock.JSN_DATACARD.toFile();

    private ManageFileLock() {
        // prevents instatiation
    }

    // ManageFileLock.GetFile_DATACARD()
    public static File GetJSNFile_DATACARD() {
        return ManageFileLock.JSON_USER_DATACARD;
    }

} // ManageFileLock()
