package meeting_scheduler.PresentationLayer;

/**
 * PROG_UI_A_SceneManager
 * Description: Interface which manages scene transitions for the application
 */

import javafx.stage.Stage;

public class PROG_UI_A_SceneManager {

    private final Stage ApplicationStage;

    private final PROG_UI_B_MainMenuScene         MainMenu;
    private final PROG_UI_B_DataCardinfoScene     DataCard;
    private final PROG_UI_B_SchedulePeopleScene   Schedule;
    private final PROG_UI_B_InstructionsScene     Instructions;

    public PROG_UI_A_SceneManager (Stage stage) {
        
        // Set local reference to the application stage for use within the class.
        this.ApplicationStage = stage;

        // Initialize objects used within the class
        MainMenu        = new PROG_UI_B_MainMenuScene(ApplicationStage);
        DataCard        = new PROG_UI_B_DataCardinfoScene(ApplicationStage);
        Schedule        = new PROG_UI_B_SchedulePeopleScene(ApplicationStage);
        Instructions    = new PROG_UI_B_InstructionsScene(ApplicationStage);

    }

    /**
     * MainMenu()
     * Description: Changes scene to the main menu
     */
    public void MainMenu() {
        this.MainMenu.ChangeToMainMenu();
    }

    /**
     * DataCardManage
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
