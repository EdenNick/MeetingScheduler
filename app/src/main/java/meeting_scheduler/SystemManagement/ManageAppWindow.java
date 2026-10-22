// Package  - DO Not Change
// ############################################################
package meeting_scheduler.SystemManagement;
// ############################################################

// Imports
// ############################################################
import javafx.application.Application;
import javafx.stage.Stage;
import meeting_scheduler.SystemInfoManager;
import meeting_scheduler.global;
// ############################################################



public class ManageAppWindow extends Application {

    // Local Variables
    public static ManageScenes SceneManager;

    
    /**
     * start()
     * Description: starts the application window
     * NOTE: do not change method name, or remove the ovverride, both are required for javafx.applicatiion to start the application window
     */
    @Override
    public void start(Stage stage) {

        // SceneManager - manages various scenes contained within the appliation
        ManageAppWindow.SceneManager = new ManageScenes(stage);

        // Startup - initializes construction for all scenes and sets the applicaiton window dimensions
        ManageAppWindow.SceneManager.StartUp();

        // System Message
        // 5 - SUCCESS | 9 - MANAGEAPP_AppWindow | 1 - SYSTEM-START | 27 - windowrun start
        SystemInfoManager.GET_SYSTEM_MESSAGE(5,9,1,27);

        WindowRun(stage);


    } // start()



    /**
     * WindowRun()
     * Description: method for running the applicaiton
     * @param stage
     */
    private void WindowRun(Stage stage) {

        // Sets the name of the application window
        stage.setTitle(SystemInfoManager.GET_SYSTEM_VALUE_STRING(global.BASICVALUESSTRING.TEXT, 0)); // 0 - Employee Meeting Scheduler);

        // MainMenu - the application shows the main menu scene on startup
        ManageAppWindow.SceneManager.SwapToMainMenu();

    } // WindowRun()



}// PROG_UI_A_Application
