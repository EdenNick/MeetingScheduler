package meeting_scheduler.PresentationLayer;

import java.util.LinkedList;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
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
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import meeting_scheduler.DataAccessLayer.PROG_DAL_C_TXTOutput;

/**
 * PROG_UI_B_InstructionsScene()
 * 
 * Description: Application window scene which shows an "instructions" page
 * Contains:
 * a. back button       (scene change)
 * b. instructions which show how to use the program
 */




public class PROG_UI_C_InstructionsScene {
    
    // file reader
    PROG_DAL_C_TXTOutput fileReader = new PROG_DAL_C_TXTOutput("/PROG_UI_D_Instructions.txt");

    LinkedList<String> InstructionFileText;
    // Reference of the application stage used for local operations
    private final Stage ApplicationStage;

    // Scene
    private Scene       InstructionScene;

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
    public PROG_UI_C_InstructionsScene(Stage stage) {
        this.ApplicationStage = stage;
    }


    
    /**
     * changetoInstructionsScene()
     * Description: Public method meant to be called outside the class in order to set the scene to the instructions scene
     */
    public void changetoInstructionsScene() {

        // Gets the current size of the stage
        this.StageWidth   = this.ApplicationStage.getWidth();
        this.stageHeight  = this.ApplicationStage.getHeight();

        // Sets the stage to the main menu scene
        this.ApplicationStage.setScene(this.InstructionScene);

        // sets the correct size for the stage
        this.ApplicationStage.setWidth(StageWidth);
        this.ApplicationStage.setHeight(stageHeight);

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
            System.out.println("BUTTON CLICK    - INSTRUCTION PAGE  - Returning to home page");
            PROG_UI_A_Application.SceneManager.MainMenu();

        };

        ReturnToMenu.setOnAction(ReturnHome);
        // ############################################################




        // TODO: Add instruction box
        /**
         * Text Box
         */
        TextFlow Instructions = new TextFlow();

        // padding/ line spacing
        Instructions.setPadding(new Insets(5));
        Instructions.setLineSpacing(1);

        // font/color
        Instructions.setStyle(
            "-fx-font-size: 10px;" +
            "-fx-font-family: 'TimesNewRoman';" +
            "-fx-fill: black;"

        );

        // retrieves linkedlist of txt file
        InstructionFileText = new LinkedList<>(fileReader.ReadFile());

        // insert text here
        for (String TXTLine : InstructionFileText) {
            Instructions.getChildren().addAll(
                new Text(TXTLine + "\n")
            );
        }





        //stylize text
        // ((Text) Instructions.getChildren().get(0)).setStyle("");


        //Scroll pane
        ScrollPane instructionScrollPane = new ScrollPane(Instructions);

        //instructionScrollPane.setFitToWidth(true);

        // instructionScrollPane.viewportBoundsProperty().addListener((observation, oldBound, newBound) ->
        //     instructionScrollPane.setPrefWidth(newBound.getWidth())
        // );

        double ScrollPaneWidth = PROG_UI_A_SceneManager.WindowWidth / 1.5;

        double ScrollPanHeight = PROG_UI_A_SceneManager.WindowHeight / 1.5;

        instructionScrollPane.setPrefWidth(ScrollPaneWidth);
        instructionScrollPane.setPrefHeight(ScrollPanHeight);


        this.ApplicationStage.widthProperty().addListener((observed, oldWidth, newWidth) -> {
            instructionScrollPane.setPrefWidth(newWidth.intValue() / 1.5);
        });

        this.ApplicationStage.heightProperty().addListener((observed, oldHeight, newHeight) -> {
            instructionScrollPane.setPrefHeight(newHeight.intValue() / 1.5);
        });




        /**
         * Node management
         */

        // Root Node
        RootNode = new AnchorPane();

        // Root Node - set return home button position
        AnchorPane.setBottomAnchor(ReturnToMenu, 20.0);
        AnchorPane.setRightAnchor(ReturnToMenu, 20.0);

        // root Node - set text position
        AnchorPane.setTopAnchor(instructionScrollPane, 30.0);
        AnchorPane.setLeftAnchor(instructionScrollPane, 30.0);
        


       /**
         * Scene edits
         */

        // Add background gradient
        LinearGradient BackgroundGradient = new LinearGradient(0, 0, 300, 300, false, CycleMethod.NO_CYCLE, 
            new Stop(0, Color.DARKBLUE), new Stop(1, Color.BEIGE)
        );

        BackgroundFill backgroundFill = new BackgroundFill(BackgroundGradient, CornerRadii.EMPTY, Insets.EMPTY);

        RootNode.setBackground(new Background(backgroundFill));
        



        // Add values to the root node
        RootNode.getChildren().addAll(ReturnToMenu, instructionScrollPane);
        
        // create menu scene with the current node layout
        this.InstructionScene = new Scene(RootNode, PROG_UI_A_SceneManager.WindowWidth, PROG_UI_A_SceneManager.WindowHeight);

        this.ApplicationStage.widthProperty().addListener((observed, oldWidth, newWidth) -> {
            instructionScrollPane.setPrefWidth(newWidth.intValue() / 1.5);
        });

        this.ApplicationStage.heightProperty().addListener((observed, oldHeight, newHeight) -> {
            instructionScrollPane.setPrefHeight(newHeight.intValue() / 1.5);
        });

    }
    
}
