package meeting_scheduler.PresentationLayer;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * PROG_UI_B_InstructionsScene()
 * 
 * Description: Application window scene which shows an "instructions" page
 * Contains:
 * a. back button       (scene change)
 * b. instructions which show how to use the program
 */




public class PROG_UI_C_InstructionsScene {

    // Reference of the application stage used for local operations
    private final Stage ApplicationStage;

    // Scene
    private Scene       InstructionScene;

    //Root Node
    private AnchorPane  RootNode;

    // Buttons
    private Button      ReturnToMenu;


    /**
     * Constructor class
     */
    public PROG_UI_C_InstructionsScene(Stage stage) {
        this.ApplicationStage = stage;
    }


    
    /**
     * changetoInstructionsScene()
     * Description: Public method meant to be called outside the class in order to set the scene to the instructions scene
     */
    public void changetoInstructionsScene() {

        // Sets the stage to the main menu scene
        this.ApplicationStage.setScene(this.InstructionScene);

        // Shows the change
        this.ApplicationStage.show();
        
    }



    /**
     * ConstructInstructionsScene()
     * Description: Performs the necessary operations in order to build the various nodes/components of the stage.
     */
    public void ConstructInstructionsScene() {



        /**
         * Buttons
         */

        // Return Home Button
        ReturnToMenu = new Button("Return Home");

        EventHandler<ActionEvent> ReturnHome = (ActionEvent e) -> {
            
            // Exits the program
            System.out.println("BUTTON CLICK - INSTRUCTION PAGE - Returning to home page");
            PROG_UI_A_Application.SceneManager.MainMenu();

        };

        ReturnToMenu.setOnAction(ReturnHome);
        // ############################################################




        // TODO: Add instruction box



        /**
         * Node management
         */

        // Root Node
        RootNode = new AnchorPane();

        // Root Node - set return home button position
        AnchorPane.setBottomAnchor(ReturnToMenu, 20.0);
        AnchorPane.setRightAnchor(ReturnToMenu, 20.0);


        // Text and background
        Text testText = new Text("Testing");

        Rectangle TextBackground = new Rectangle();
        TextBackground.setWidth(50);
        TextBackground.setHeight(100);

        TextBackground.setFill(Color.BURLYWOOD);


        StackPane InstructionBox = new StackPane();

        InstructionBox.getChildren().addAll(TextBackground, testText);
        

        RootNode.getChildren().addAll(ReturnToMenu, InstructionBox);
        
        // create menu scene with the current node layout
        this.InstructionScene = new Scene(RootNode, 500, 500);

    }
    
}
