package meeting_scheduler.PresentationLayer;

import javafx.stage.Stage;

/**
 * PROG_UI_B_DataCardinfoScene()
 * 
 * Description: Application window scene which allows the user to add user datacards to the application
 * Contains:
 * a. back button       (scene change)
 * b. UI interface allowing user to add info    (various buttons, text inputs etc)
 */




public class PROG_UI_B_DataCardinfoScene {

    // Reference of the application stage used for local operations
    private final Stage ApplicationStage;



    /**
     * Constructor class
     */
    public PROG_UI_B_DataCardinfoScene(Stage stage) {
        this.ApplicationStage = stage;
    }


    
    /**
     * changetoDataCardScene()
     * Description: Public method meant to be called outside the class in order to set the scene to the data card management scene
     */
    public void changetoDataCardScene() {

        ConstructCardManagerScene();
        
    }



    /**
     * ConstructCardManagerScene()
     * Description: Performs the necessary operations in order to build the various nodes/components of the stage.
     */
    private void ConstructCardManagerScene() {

    }
}
