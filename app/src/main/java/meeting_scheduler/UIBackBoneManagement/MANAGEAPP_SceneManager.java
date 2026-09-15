/**
 * PROG_UI_A_SceneManager.java
 * 
 * Description: Interface which manages scene transitions for the application
 */

// Package  - DO Not Change
// ############################################################
package meeting_scheduler.UIBackBoneManagement;
// ############################################################

// Imports
// ############################################################
// Stage
import javafx.stage.Stage;
import meeting_scheduler.SceneManagement.SCENE_CREATE_DataCard;
import meeting_scheduler.SceneManagement.SCENE_CREATE_Instruct;
import meeting_scheduler.SceneManagement.SCENE_CREATE_MainMenu;
import meeting_scheduler.SceneManagement.SCENE_CREATE_Schedule;
import meeting_scheduler.SceneManagement.SCENE_VARIABLES_Local;
// ############################################################



public class MANAGEAPP_SceneManager {

    // Stage
    private final Stage ApplicationStage;

    // Objects
    private final SCENE_CREATE_MainMenu MainMenu;
    private final SCENE_CREATE_DataCard DataCard;
    private final SCENE_CREATE_Schedule Schedule;
    private final SCENE_CREATE_Instruct Instruct;

    // window size
    public static int WindowWidth;
    public static int WindowHeight;



    /**
     * Constructor
     * @param stage
     */
    public MANAGEAPP_SceneManager (Stage stage) {
        
        // Set local reference to the application stage for use within the class.
        this.ApplicationStage = stage;

        // Initialize all scenes used by the app, they all must share the same stage in order to be changes to be caried out
        MainMenu    = new SCENE_CREATE_MainMenu(ApplicationStage);
        DataCard    = new SCENE_CREATE_DataCard(ApplicationStage);
        Schedule    = new SCENE_CREATE_Schedule(ApplicationStage);
        Instruct    = new SCENE_CREATE_Instruct(ApplicationStage);

        // TODO: possibly change this to a global variable as it is accessed across multiple folders and obejects
        // Default Window width and height values, all scenes access these variables
        MANAGEAPP_SceneManager.WindowWidth     = MANAGEAPP_local.APP_Window_StartWidth;
        MANAGEAPP_SceneManager.WindowHeight    = MANAGEAPP_local.APP_Window_StartHeight;

    }







    /**
     * StartUP()
     * Description: Initializes all objects and listeners needed for each scene change
     */
    public void StartUp() {

        // Listener - width listener, updates the scene manager width for scenes to use
        this.ApplicationStage.widthProperty().addListener((observed, oldWidth, newWidth) -> {
            MANAGEAPP_SceneManager.WindowWidth = newWidth.intValue();
        });

        // Listener - height listener, updates the scene manager height for scenes to use
        this.ApplicationStage.heightProperty().addListener((observed, oldHeight, newHeight) -> {
            MANAGEAPP_SceneManager.WindowHeight = newHeight.intValue();
        });

        // construction - creates main menu page        - CALL ONCE
        this.MainMenu.ConstructMainMenuScene();

        // construction - creates the Data Card page    - CALL ONCE
        this.DataCard.ConstructCardManagerScene();

        // construction - creates the schedule page     - CALL ONCE
        this.Schedule.ConstructSchedulingScene();

        // construction - creates the instruction page  - CALL ONCE
        this.Instruct.ConstructInstructionsScene();

    } // StartUp()



    /**
     * MainMenu()
     * Description: Changes scene to the main menu
     */
    public void SwapToMainMenu() {
        this.MainMenu.ChangeToMainMenu();
    }



    /**
     * DataCardManage()
     * Description: Changess the scene to the data card management page
     */
    public void SwapToDataCard() {
        this.DataCard.changetoDataCardScene();
    }



    /**
     * Schedule()
     * Description: Changes the scene to the scheduling page
     */
    public void SwapToSchedule() {
        this.Schedule.changetoSchedulingScene();
    }



    /**
     * Instructions()
     * Description: Changes the scene to the instructions page
     */
    public void SwapToInstruct() {
        this.Instruct.changetoInstructionsScene();
    }
    
}
