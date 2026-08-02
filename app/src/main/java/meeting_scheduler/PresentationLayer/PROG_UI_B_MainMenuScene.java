package meeting_scheduler.PresentationLayer;

/**
 * PROG_UI_B_MainMenuScene()
 * 
 * Description: Application window scene for the mian menu - first and primary scene of the pplication
 * Contains:
 * a. Exit application button       (End Program)
 * b. Add/remove data card button   (change scene)
 * c. scheudling button             (change scene)
 * d. basic instructions button     (change scene)
 */



import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;



public class PROG_UI_B_MainMenuScene {

    // Reference of the application stage used for local operations
    private final Stage ApplicationStage;



    /**
     * Constructor class
     */
    public PROG_UI_B_MainMenuScene(Stage stage) {
        this.ApplicationStage = stage;
    }


    
    /**
     * ChangeToMainMenu()
     * Description: Public method meant to be called outside the class in order to set the scene to the main menu scene
     */
    public void ChangeToMainMenu() {

        ConstructMainMenuScene();
        
    }



    /**
     * ConstructMainMenuStage()
     * Description: Performs the necessary operations in order to build the various nodes/components of the stage.
     */
    private void ConstructMainMenuScene() {

    }



} // PROG_UI_A_MainMenuScene
