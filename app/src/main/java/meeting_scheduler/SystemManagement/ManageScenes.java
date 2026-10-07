// Package  - DO Not Change
// ############################################################
package meeting_scheduler.SystemManagement;
// ############################################################

// Imports
// ############################################################
import javafx.stage.Stage;
import meeting_scheduler.SceneManagement.SceneDataCard;
import meeting_scheduler.SceneManagement.SceneInstructions;
import meeting_scheduler.SceneManagement.SceneMainMenu;
import meeting_scheduler.SceneManagement.SceneScheduling;
import meeting_scheduler.global;
import meeting_scheduler.SystemInfoManager;
// ############################################################



public class ManageScenes {

    // Stage
    private final Stage ApplicationStage;

    // Objects
    private final SceneMainMenu MainMenu;
    private final SceneDataCard DataCard;
    private final SceneScheduling Schedule;
    private final SceneInstructions Instruct;

    // window size
    public static int WindowWidth;
    public static int WindowHeight;



    /**
     * Constructor
     * @param stage
     */
    public ManageScenes (Stage stage) {
        
        // Set local reference to the application stage for use within the class.
        this.ApplicationStage = stage;

        // Initialize all scenes used by the app, they all must share the same stage in order to be changes to be caried out
        MainMenu    = new SceneMainMenu(ApplicationStage);
        DataCard    = new SceneDataCard(ApplicationStage);
        Schedule    = new SceneScheduling(ApplicationStage);
        Instruct    = new SceneInstructions(ApplicationStage);

        // Default Window width and height values, all scenes access these variables
        ManageScenes.WindowWidth     = SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_INT(global.BASICVALUESINT.INT, 1);
        ManageScenes.WindowHeight    = SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_INT(global.BASICVALUESINT.INT, 2);

    }







    /**
     * StartUP()
     * Description: Initializes all objects and listeners needed for each scene change
     */
    public void StartUp() {

        // Listener - width listener, updates the scene manager width for scenes to use
        this.ApplicationStage.widthProperty().addListener((observed, oldWidth, newWidth) -> {
            ManageScenes.WindowWidth = newWidth.intValue();
        });

        // Listener - height listener, updates the scene manager height for scenes to use
        this.ApplicationStage.heightProperty().addListener((observed, oldHeight, newHeight) -> {
            ManageScenes.WindowHeight = newHeight.intValue();
        });

        // construction - creates main menu page        - CALL ONCE
        this.MainMenu.ConstructMainMenuScene();

        // construction - creates the Data Card page    - CALL ONCE
        this.DataCard.ConstructCardManagerScene();

        // construction - creates the schedule page     - CALL ONCE
        this.Schedule.ConstructSchedulingScene();

        // construction - creates the instruction page  - CALL ONCE
        this.Instruct.ConstructInstructionsScene();


        // System Message
        // 5 - SUCCESS | 12 - MANAGEAPP_SceneManager| 5 - SYSTEM-CreateScenes | 29 - SceneManager startup complete
        SystemInfoManager.GET_SYSTEM_MESSAGE(5,12,5,28);

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
