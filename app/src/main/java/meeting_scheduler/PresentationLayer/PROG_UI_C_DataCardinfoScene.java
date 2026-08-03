package meeting_scheduler.PresentationLayer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * PROG_UI_B_DataCardinfoScene()
 * 
 * Description: Application window scene which allows the user to add user datacards to the application
 * Contains:
 * a. back button       (scene change)
 * b. UI interface allowing user to add info    (various buttons, text inputs etc)
 */




public class PROG_UI_C_DataCardinfoScene {

    // Reference of the application stage used for local operations
    private final Stage ApplicationStage;


    // Scene
    private Scene       DataCardScene;

    //Root Node
    private AnchorPane  RootNode;

    // Buttons
    private Button      ReturnToMenu;

    /**
     * Constructor class
     */
    public PROG_UI_C_DataCardinfoScene(Stage stage) {
        this.ApplicationStage = stage;
    }


    
    /**
     * changetoDataCardScene()
     * Description: Public method meant to be called outside the class in order to set the scene to the data card management scene
     */
    public void changetoDataCardScene() {

        // Sets the stage to the main menu scene
        this.ApplicationStage.setScene(this.DataCardScene);

        // Shows the change
        this.ApplicationStage.show();
        
    }



    /**
     * ConstructCardManagerScene()
     * Description: Performs the necessary operations in order to build the various nodes/components of the stage.
     */
    public void ConstructCardManagerScene() {

        /**
         * Buttons
         */

        // Return Home Button
        ReturnToMenu = new Button("Return Home");

        EventHandler<ActionEvent> ReturnHome = (ActionEvent e) -> {
            
            // Exits the program
            System.out.println("BUTTON CLICK - CARD MANAGER PAGE - Returning to home page");
            PROG_UI_A_Application.SceneManager.MainMenu();

        };

        ReturnToMenu.setOnAction(ReturnHome);
        // ############################################################




        /**
         * Node management
         */

        // Root Node
        RootNode = new AnchorPane();

        // Root Node - set return home button position
        AnchorPane.setBottomAnchor(ReturnToMenu, 20.0);
        AnchorPane.setRightAnchor(ReturnToMenu, 20.0);
        RootNode.getChildren().addAll(ReturnToMenu);

        // create menu scene with the current node layout
        this.DataCardScene = new Scene(RootNode, 500, 500);

    }
}
