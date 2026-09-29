// Package  - DO Not Change
// ############################################################
package meeting_scheduler;
// ############################################################

// Imports
// ############################################################
// io
// import java.io.File;
// javafx
import javafx.application.Application;
// import meeting_scheduler.SceneManagement.SCENE_VARIABLES_Local;
// UIBackBoneManagement
import meeting_scheduler.UIBackBoneManagement.MANAGEAPP_AppWindow;
// ############################################################



public class MeetingScheduler {

    /**
     * static main
     */
    public static void main(String[] args) throws Exception {

        // Program start message
        System.out.println("Hello, World!");
        System.out.println("Program Start");
        
        SystemInfoManager.Inititlaize_Global_States();


        // launch application window
        Application.launch (MANAGEAPP_AppWindow.class, args);


        // Program end message
        System.out.println("Program End");
        System.out.println("Goodbye World");

    } // main(String[] args)

} // MeetingScheduler