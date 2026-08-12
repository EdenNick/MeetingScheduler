package meeting_scheduler.PresentationLayer;

import java.util.LinkedList;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;
import meeting_scheduler.DataAccessLayer.PROG_DAL_C_TXTOutput;

/**
 * PROG_UI_B_InstructionsScene()
 * 
 * Description: Application window scene which shows an "instructions" page
 * Contains:
 * a. back button       (scene change)
 * b. instructions which show how to use the program
 */




public class PROG_UI_B_InstructionsScene {
    
    // file reader
    PROG_DAL_C_TXTOutput fileReader = new PROG_DAL_C_TXTOutput("/PROG_UI_D_Instructions.txt");

    LinkedList<String> InstructionFileText;
    
    // Reference of the application stage used for local operations
    private final Stage ApplicationStage;

    // Scene
    private Scene       InstructionScene;

    //Root Node
    private AnchorPane  RootNode;

    //textflow
    private TextFlow    Instructions;

    // scrollpane
    private ScrollPane  instructionScrollPane;

    // Buttons
    private Button      ReturnToMenu;

    // Stage width/height
    private double StageWidth;
    private double stageHeight;

    // transitions
    private ParallelTransition fadeMenuNodes;
    private ParallelTransition UnfadeMenuNodes;

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

        // unfades nodes
        UnfadeMenuNodes.play();

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


        // Root Node creation
        RootNode = new AnchorPane();

        // Import styles
        RootNode.getStylesheets().add(getClass().getResource("/CSS_Styles.css").toExternalForm());

        /// Button Creation
        ButtonCreation();


        // Text Box
        Instructions = new TextFlow();

        // retrieves linkedlist of txt file
        InstructionFileText = new LinkedList<>(fileReader.ReadFile());
        
        // removes file name from the instructions
        InstructionFileText.remove(0);
        
        // insert text here
        for (String TXTLine : InstructionFileText) {
            Instructions.getChildren().addAll(
                new Text(TXTLine + "\n")
            );
        }

        // padding/ line spacing
        this.Instructions.setPadding(new Insets(5));
        this.Instructions.setLineSpacing(1);

        // set style for generl text
        this.Instructions.getStyleClass().add("instructions-text");

        // set style for title
        this.Instructions.getChildren().get(0).getStyleClass().add("instructions-title");




        // Scroll pane management
        this.instructionScrollPane = new ScrollPane(this.Instructions);
        this.instructionScrollPane.getStyleClass().add("instructions-scrollPane");

        // set width/height
        double ScrollPaneWidth = PROG_UI_A_SceneManager.WindowWidth / 1.5;
        double ScrollPanHeight = PROG_UI_A_SceneManager.WindowHeight / 1.5;
        this.instructionScrollPane.setPrefWidth(ScrollPaneWidth);
        this.instructionScrollPane.setPrefHeight(ScrollPanHeight);


        // this.ApplicationStage.widthProperty().addListener((observed, oldWidth, newWidth) -> {
        //     instructionScrollPane.setPrefWidth(newWidth.intValue() / 1.5);
        // });

        // this.ApplicationStage.heightProperty().addListener((observed, oldHeight, newHeight) -> {
        //     instructionScrollPane.setPrefHeight(newHeight.intValue() / 1.5);
        // });




        /**
         * Root Node management
         */
        // Root Node - set return home button position
        AnchorPane.setBottomAnchor(ReturnToMenu, 20.0);
        AnchorPane.setRightAnchor(ReturnToMenu, 20.0);

        // root Node - set text position
        AnchorPane.setTopAnchor(instructionScrollPane, 30.0);
        AnchorPane.setLeftAnchor(instructionScrollPane, 30.0);
        
        // Add values to the root node
        RootNode.getChildren().addAll(ReturnToMenu, instructionScrollPane);


        // Background creation
        BackgroundManagement();

        
        // Transition to fade effects
        SceneTransitions();


        // Scene Creation
        this.InstructionScene = new Scene(RootNode, PROG_UI_A_SceneManager.WindowWidth, PROG_UI_A_SceneManager.WindowHeight);

        this.ApplicationStage.widthProperty().addListener((observed, oldWidth, newWidth) -> {
            instructionScrollPane.setPrefWidth(newWidth.intValue() / 1.5);
        });

        this.ApplicationStage.heightProperty().addListener((observed, oldHeight, newHeight) -> {
            instructionScrollPane.setPrefHeight(newHeight.intValue() / 1.5);
        });

        // fade all objects before the scene is set
        fadeMenuNodes.play();

    }



    /**
     * ButtonCreation()
     * Description: creates the various buttons for the instruction page
     */
    private void ButtonCreation() {
        // Return Home Button
        // ############################################################
        ReturnToMenu = new Button("Return Home");
        EventHandler<ActionEvent> ReturnHome = (ActionEvent e) -> {
            
            // Exits the program
            System.out.println("BUTTON CLICK    - INSTRUCTION PAGE  - Returning to home page");

            fadeMenuNodes.setOnFinished(event -> {
                PROG_UI_A_Application.SceneManager.MainMenu();
            });

            fadeMenuNodes.play();

            // PROG_UI_A_Application.SceneManager.MainMenu();

        };
        ReturnToMenu.setOnAction(ReturnHome);
        // ############################################################

    }



    /**
     * BackgroundManagement()
     * Description: manages the various background effets for the scene
     */
    private void BackgroundManagement() {

        // Add background gradient
        LinearGradient BackgroundGradient = new LinearGradient(0, 0, 300, 300, false, CycleMethod.NO_CYCLE, 
            new Stop(0, Color.DARKBLUE), new Stop(1, Color.BEIGE)
        );

        BackgroundFill backgroundFill = new BackgroundFill(BackgroundGradient, CornerRadii.EMPTY, Insets.EMPTY);

        RootNode.setBackground(new Background(backgroundFill));

    }



    /**
     * SceneTransitions()
     * Description: manages the scene transitions
     */
    private void SceneTransitions() {

        // Transition to fade buttons
        fadeMenuNodes = new ParallelTransition();

        for (Node node : RootNode.getChildren()) {
            
            FadeTransition NodeFade = new FadeTransition(
                Duration.seconds(2),
                node
            );

            NodeFade.setToValue(0);
            
            fadeMenuNodes.getChildren().addAll(NodeFade);
        }
        // ############################################################



        // Transition to Unfade buttons
        UnfadeMenuNodes = new ParallelTransition();

        for (Node node : RootNode.getChildren()) {
            
            FadeTransition NodeUnFade = new FadeTransition(
                Duration.seconds(2),
                node
            );

            NodeUnFade.setToValue(1);
            
            UnfadeMenuNodes.getChildren().addAll(NodeUnFade);
        }
        // ############################################################

    }
    
}
