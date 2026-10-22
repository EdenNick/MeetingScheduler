package meeting_scheduler.FileManagement;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ManageFileLock {
    
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
    private static final File JSON_USER_DATACARD = ManageFileLock.PATH_JSN_DATACARD.toFile();
    // ############################################################

    // Resource Input Streams
    // ############################################################
    // MESSAGES
    private static final InputStream STREAM_PRP_MSGTYPE = ManageFileLock.class.getResourceAsStream(PRP_MSGTYPE);
    private static final InputStream STREAM_PRP_MSGCLSS = ManageFileLock.class.getResourceAsStream(PRP_MSGCLSS);
    private static final InputStream STREAM_PRP_MSGACTN = ManageFileLock.class.getResourceAsStream(PRP_MSGACTN);
    private static final InputStream STREAM_PRP_MSGINFO = ManageFileLock.class.getResourceAsStream(PRP_MSGINFO);
    // GLOBAL VALUES
    private static final InputStream STREAM_PRP_VALARRY = ManageFileLock.class.getResourceAsStream(PRP_VALARRY);
    private static final InputStream STREAM_PRP_VALUNQE = ManageFileLock.class.getResourceAsStream(PRP_VALUNQE);
    // INSTRUCTIONS
    private static final InputStream STREAM_PRP_INSTRCT = ManageFileLock.class.getResourceAsStream(PRP_INSTRCT);
    // ############################################################





    private ManageFileLock() {
        // prevents instatiation
    }


    public static File GetLock_JSN_DataCard() {
        if (JSON_USER_DATACARD.exists() == true) {
            return ManageFileLock.JSON_USER_DATACARD;
        } else {
            // TODO: Error Message
            System.exit(1);
        }

        return null;
    }


    public static InputStream GetInputStream_MSGTYPE() {
        if (ManageFileLock.STREAM_PRP_MSGTYPE != null) {
            return ManageFileLock.STREAM_PRP_MSGTYPE;
        } else {
            // TODO: Error Message
            System.exit(1);
        }

        return null;
    }


    public static InputStream GetInputStream_MSGCLSS() {
        if (ManageFileLock.STREAM_PRP_MSGCLSS != null) {
            return STREAM_PRP_MSGCLSS;
        } else {
            // TODO: Error Message
            System.exit(1);
        }

        return null;
    }


    public static InputStream GetInputStream_MSGACTN() {
        if (ManageFileLock.STREAM_PRP_MSGACTN != null) {
            return STREAM_PRP_MSGACTN;
        } else {
            // TODO: Error Message
            System.exit(1);
        }

        return null;
    }


    public static InputStream GetInputStream_MSGINFO() {
        if (ManageFileLock.STREAM_PRP_MSGINFO != null) {
            return STREAM_PRP_MSGINFO;
        } else {
            // TODO: Error Message
            System.exit(1);
        }

        return null;
    }


    public static InputStream GetInputStream_VALARRY() {
        if (ManageFileLock.STREAM_PRP_VALARRY != null) {
            return STREAM_PRP_VALARRY;
        } else {
            // TODO: Error Message
            System.exit(1);
        }

        return null;
    }


    public static InputStream GetInputStream_VALUNQE() {
        if (ManageFileLock.STREAM_PRP_VALUNQE != null) {
            return STREAM_PRP_VALUNQE;
        } else {
            // TODO: Error Message
            System.exit(1);
        }

        return null;
    }


    public static InputStream GetInputStream_INSTRCT() {
        if (ManageFileLock.STREAM_PRP_INSTRCT != null) {
            return STREAM_PRP_INSTRCT;
        } else {
            // TODO: Error Message
            System.exit(1);
        }

        return null;
    }

} // ManageFileLock()
