package meeting_scheduler.FileManagement;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ManageFilePaths {
    
    // Data File paths (Read/Write)
    // ############################################################
    private static final Path PATH_JSN_DATACARD       = Paths.get( "app","Data", "PROG_DATA_A_UserDataCard.json");
    // ############################################################

    // Resource FILE PATHS (Read Only)
    // ############################################################
    // .properties Files
    private static final String PRP_MSGTYPE = "/GlobalMessageTypes.properties";
    private static final String PRP_MSGCLSS = "/GlobalMessageClass.properties";
    private static final String PRP_MSGACTN = "/GlobalPropertiesAction.properties";
    private static final String PRP_MSGINFO = "/GlobalMessageInfo.properties";
    private static final String PRP_VALARRY = "/GlobalValuesArrays.properties";
    private static final String PRP_VALUNQE = "/GlobalValuesUnique.properties";
    private static final String PRP_INSTRCT = "/USERINSTRUCTIONS.properties";
    // .txt Files
    private static final String TXT_INSTRCT = "src/main/resources/PROG_UI_D_Instructions.txt";
    private static final String TXT_TEMP    = "src/PROG_DATA_TempFile.txt";
    // ############################################################

    // File Objects
    // ############################################################
    private static final File JSON_USER_DATACARD = ManageFilePaths.PATH_JSN_DATACARD.toFile();
    // ############################################################



    private ManageFilePaths() {
        // prevents instatiation
    }

    // FILE LOCKS
    // ############################################################
    public static File GetLock_JSN_DataCard() {
        if (JSON_USER_DATACARD.exists() == true) {
            return ManageFilePaths.JSON_USER_DATACARD;
        } else {
            // TODO: Error Message
            System.exit(1);
        }

        return null;
    }

    // ############################################################





    // RESOURCE FILES
    // ############################################################
    public static String GetInputStream_MSGTYPE() {
        return ManageFilePaths.PRP_MSGTYPE;
    }


    public static String GetInputStream_MSGCLSS() {
        return ManageFilePaths.PRP_MSGCLSS;
    }


    public static String GetInputStream_MSGACTN() {
        return ManageFilePaths.PRP_MSGACTN;
    }


    public static String GetInputStream_MSGINFO() {
        return ManageFilePaths.PRP_MSGINFO;
    }


    public static String GetInputStream_VALARRY() {
        return ManageFilePaths.PRP_VALARRY;
    }


    public static String GetInputStream_VALUNQE() {
        return ManageFilePaths.PRP_VALUNQE;
    }


    public static String GetInputStream_INSTRCT() {
        return ManageFilePaths.PRP_INSTRCT;
    }
    // ############################################################


    
} // ManageFileLock()
