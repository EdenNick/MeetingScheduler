package meeting_scheduler.FileManagement;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ManageFileLock {
    
    private static final Path FilePath = Paths.get( "app","src","main", "java", "meeting_scheduler", "Storage", "PROG_DATA_A_UserDataCard.json");
    private static final File JSON_USER_DATACARD = FilePath.toFile();

    private ManageFileLock() {
        // prevents instatiation
    }

    // ManageFileLock.GetFile_DATACARD()
    public static File GetFile_DATACARD() {
        System.out.println("RESOLVE: " + FilePath.toAbsolutePath());
        System.out.println("EXISTS: " + JSON_USER_DATACARD.exists());
        
        return ManageFileLock.JSON_USER_DATACARD;
    }

} // ManageFileLock()
