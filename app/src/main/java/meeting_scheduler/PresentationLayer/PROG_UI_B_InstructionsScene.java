package meeting_scheduler.PresentationLayer;

import javafx.stage.Stage;

/**
 * PROG_UI_B_InstructionsScene()
 * 
 * Description: Application window scene which shows an "instructions" page
 * Contains:
 * a. back button       (scene change)
 * b. instructions which show how to use the program
 */




public class PROG_UI_B_InstructionsScene {

    // Reference of the application stage used for local operations
    private final Stage ApplicationStage;



    /**
     * Constructor class
     */
    public PROG_UI_B_InstructionsScene(Stage stage) {
        this.ApplicationStage = stage;
    }


    
    /**
     * changetoInstructionsScene()
     * Description: Public method meant to be called outside the class in order to set the scene to the instructions scene
     */
    public void changetoInstructionsScene() {

        ConstructInstructionsScene();
        
    }



    /**
     * ConstructInstructionsScene()
     * Description: Performs the necessary operations in order to build the various nodes/components of the stage.
     */
    private void ConstructInstructionsScene() {

    }
    
}
