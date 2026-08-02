package meeting_scheduler.PresentationLayer;

import javafx.stage.Stage;

/**
 * PROG_UI_B_SchedulePeopleScene()
 * 
 * Description: Application window scene which allows the user to make schedules based off of existing user card information
 * Contains:
 * a. back button       (scene change)
 * b. UI interface allowing user to make schedules    (various buttons, text inputs etc)
 */




public class PROG_UI_B_SchedulePeopleScene {

    // Reference of the application stage used for local operations
    private final Stage ApplicationStage;



    /**
     * Constructor class
     */
    public PROG_UI_B_SchedulePeopleScene(Stage stage) {
        this.ApplicationStage = stage;
    }


    
    /**
     * changetoSchedulingScene()
     * Description: Public method meant to be called outside the class in order to set the scene to the Scheduling scene
     */
    public void changetoSchedulingScene() {

        ConstructSchedulingScene();
        
    }



    /**
     * ConstructSchedulingScene()
     * Description: Performs the necessary operations in order to build the various nodes/components of the stage.
     */
    private void ConstructSchedulingScene() {

    }
    
}
