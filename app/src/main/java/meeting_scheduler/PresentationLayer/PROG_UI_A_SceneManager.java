/**
 * PROG_UI_A_SceneManager.java
 * 
 * Description: Interface which manages scene transitions for the application
 */

// Package  - DO Not Change
// ############################################################
package meeting_scheduler.PresentationLayer;
// ############################################################

// Imports
// ############################################################
// Stage
import javafx.stage.Stage;
// ############################################################



public class PROG_UI_A_SceneManager {

    // Stage
    private final Stage                         ApplicationStage;

    // Objects
    private final PROG_UI_B_MainMenuScene       MainMenu;
    private final PROG_UI_B_DataCardinfoScene   DataCard;
    private final PROG_UI_B_SchedulePeopleScene Schedule;
    private final PROG_UI_B_InstructionsScene   Instructions;

    // window size
    public static int WindowWidth;
    public static int WindowHeight;



    /**
     * Constructor
     * @param stage
     */
    public PROG_UI_A_SceneManager (Stage stage) {
        
        // Set local reference to the application stage for use within the class.
        this.ApplicationStage = stage;

        // Initialize objects used within the class
        MainMenu        = new PROG_UI_B_MainMenuScene(ApplicationStage);
        DataCard        = new PROG_UI_B_DataCardinfoScene(ApplicationStage);
        Schedule        = new PROG_UI_B_SchedulePeopleScene(ApplicationStage);
        Instructions    = new PROG_UI_B_InstructionsScene(ApplicationStage);

        // Default Window width and height values
        PROG_UI_A_SceneManager.WindowWidth     = PROG_UI_D_DataVariables.WindowStartWidth;
        PROG_UI_A_SceneManager.WindowHeight    = PROG_UI_D_DataVariables.WindowStartHeight;

    }







    /**
     * StartUP()
     * Description: Initializes all operarations needed for each scene change
     */
    public void StartUp() {

        this.MainMenu       .ConstructMainMenuScene();
        this.DataCard       .ConstructCardManagerScene();
        this.Schedule       .ConstructSchedulingScene();
        this.Instructions   .ConstructInstructionsScene();

        this.ApplicationStage.widthProperty().addListener((observed, oldWidth, newWidth) -> {
            PROG_UI_A_SceneManager.WindowWidth = newWidth.intValue();
        });

        this.ApplicationStage.heightProperty().addListener((observed, oldHeight, newHeight) -> {
            PROG_UI_A_SceneManager.WindowHeight = newHeight.intValue();
        });

    } // StartUp()



    /**
     * MainMenu()
     * Description: Changes scene to the main menu
     */
    public void MainMenu() {
        this.MainMenu.ChangeToMainMenu();
    }



    /**
     * DataCardManage()
     * Description: Changess the scene to the data card management page
     */
    public void DataCardManage() {
        this.DataCard.changetoDataCardScene();
    }



    /**
     * Schedule()
     * Description: Changes the scene to the scheduling page
     */
    public void Schedule() {
        this.Schedule.changetoSchedulingScene();
    }



    /**
     * Instructions()
     * Description: Changes the scene to the instructions page
     */
    public void Instructions() {
        this.Instructions.changetoInstructionsScene();
    }
    
}
