package meeting_scheduler.PresentationLayer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
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

    // Stage width/height
    private double StageWidth;
    private double stageHeight;




    
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

        // Gets the current size of the stage
        this.StageWidth   = this.ApplicationStage.getWidth();
        this.stageHeight  = this.ApplicationStage.getHeight();

        // Sets the stage to the main menu scene
        this.ApplicationStage.setScene(this.DataCardScene);

        // sets the correct size for the stage
        this.ApplicationStage.setWidth(StageWidth);
        this.ApplicationStage.setHeight(stageHeight);

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
            System.out.println("BUTTON CLICK    - CARD MANAGER PAGE - Returning to home page");
            PROG_UI_A_Application.SceneManager.MainMenu();

        };

        ReturnToMenu.setOnAction(ReturnHome);
        // ############################################################



        // TODO: add Data card managemnet interface

        /**
         * Node management
         */

        // Root Node
        RootNode = new AnchorPane();

        // Root Node - set return home button position
        AnchorPane.setBottomAnchor(ReturnToMenu, 20.0);
        AnchorPane.setRightAnchor(ReturnToMenu, 20.0);
        RootNode.getChildren().addAll(ReturnToMenu);



        /**
         * Scene edits
         */

        // Add background gradient
        LinearGradient BackgroundGradient = new LinearGradient(0, 0, 300, 300, false, CycleMethod.NO_CYCLE, 
            new Stop(0, Color.DARKBLUE), new Stop(1, Color.BEIGE)
        );

        BackgroundFill backgroundFill = new BackgroundFill(BackgroundGradient, CornerRadii.EMPTY, Insets.EMPTY);

        RootNode.setBackground(new Background(backgroundFill));

        

        // create menu scene with the current node layout
        this.DataCardScene = new Scene(RootNode, PROG_UI_A_SceneManager.WindowWidth, PROG_UI_A_SceneManager.WindowHeight);

    }
}
