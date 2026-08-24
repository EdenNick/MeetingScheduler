/**
 * PROG_UI_B_InstructionsScene.java
 * 
 * Description: Application window scene which shows an "instructions" page
 * Contains:
 * a. back button       (scene change)
 * b. instructions which show how to use the program
 */

// Package  - DO Not Change
// ############################################################
package meeting_scheduler.PresentationLayer;
// ############################################################



// Imports
// ############################################################
// util
import java.util.LinkedList;
// javafx
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
// Data Managing Objects
import meeting_scheduler.DataAccessLayer.PROG_DAL_C_TXTOutput;
// System messages
import meeting_scheduler.DataAccessLayer.PROG_DAL_D_SystemMessages;
// ############################################################




public class PROG_UI_B_InstructionsScene {
    


    // Application
    // ############################################################
    // Reference of the application stage used for local operations
    private final Stage ApplicationStage;
    // Scene
    private Scene       InstructionScene;
    // Stage width/height
    private double      StageWidth;
    private double      stageHeight;
    // ############################################################

    // Nodes
    // ############################################################
    // Root Node
    private AnchorPane          Instruction_RootNode;
    // scrollpane
    private ScrollPane          instructionScrollPane;
    // Textflow
    private TextFlow            InstructionTextFlow;
    // Buttons
    private Button              Button_ReturnToMenu;
    // transitions
    private ParallelTransition  Transition_FadeNodes;
    private ParallelTransition  Transition_UnFadeNodes;
    // ############################################################

    // event handlers
    // ############################################################
    private EventHandler<ActionEvent> ReturnHome = null;
    // ############################################################

    // File manager
    // ############################################################
    PROG_DAL_C_TXTOutput fileReader;
    // ############################################################

    // data management
    // ############################################################
    LinkedList<String> InstructionFileText;
    // ############################################################





    /**
     * Constructor class
     */
    public PROG_UI_B_InstructionsScene(Stage stage) {
        // set the stage
        this.ApplicationStage = stage;
        // create the fiel reader object and set it to read from the instructions file
        this.fileReader = new PROG_DAL_C_TXTOutput(PROG_UI_D_DataVariables.DOC_Instructions);

    } // PROG_UI_B_InstructionsScene(Stage stage)


    
    /**
     * changetoInstructionsScene()
     * Description: Public method meant to be called outside the class in order to set the scene to the instructions scene
     */
    public void changetoInstructionsScene() {

        // Gets the current size of the stage
        this.StageWidth   = this.ApplicationStage.getWidth();
        this.stageHeight  = this.ApplicationStage.getHeight();

        // sets the correct size for the stage
        this.ApplicationStage.setWidth(StageWidth);
        this.ApplicationStage.setHeight(stageHeight);

        // Sets the stage to the main menu scene
        this.ApplicationStage.setScene(this.InstructionScene);

        // Shows the change
        this.ApplicationStage.show();

        // unfades nodes
        Transition_UnFadeNodes.play();
        
    } // changetoInstructionsScene



    /**
     * ConstructInstructionsScene()
     * Description: Performs the necessary operations in order to build the various nodes/components of the stage.
     */
    public void ConstructInstructionsScene() {


        // Node Construction
        // ############################################################
        // Root Node
        Instruction_RootNode = new AnchorPane();
        // Import styles
        Instruction_RootNode.getStylesheets().add(getClass().getResource(PROG_UI_D_DataVariables.CSS_Styles).toExternalForm());
        // Text Box
        InstructionTextFlow = new TextFlow();
        // ############################################################


        // event handler creation
        // ############################################################
        instructionsEventhandler();
        // ############################################################


        // Button Creation
        // ############################################################
        ButtonCreation();
        // ############################################################


        // Instruction Creation
        // ############################################################
        InstructionCreation();
        // ############################################################


        // Background creation
        // ############################################################
        BackgroundManagement();
        // ############################################################


        // Node Visual formatting
        // ############################################################
        // Root Node - set return home button position
        AnchorPane.setRightAnchor   (Button_ReturnToMenu,   PROG_UI_D_DataVariables.SCHEDULE_Return_BottomAnchor);
        AnchorPane.setRightAnchor   (Button_ReturnToMenu,   PROG_UI_D_DataVariables.SCHEDULE_Return_RightAnchor);

        // root Node - set text position
        AnchorPane.setTopAnchor     (instructionScrollPane, PROG_UI_D_DataVariables.INSTRUCTION_Instruction_TopAnchor);
        AnchorPane.setLeftAnchor    (instructionScrollPane, PROG_UI_D_DataVariables.INSTRUCTION_Instruction_LeftAnchor);
        // ############################################################


        // Add sub-nodes to their positions
        // ############################################################
        Instruction_RootNode.getChildren().addAll(Button_ReturnToMenu, instructionScrollPane);
        // ############################################################


        // Scene Creation with Root Node Instruction_RootNode
        // ############################################################
        this.InstructionScene = new Scene(Instruction_RootNode, PROG_UI_A_SceneManager.WindowWidth, PROG_UI_A_SceneManager.WindowHeight);
        // ############################################################


        // Scene transition
        // ############################################################
        // create trnasitions
        SceneTransitions();
        // fade all objects before the scene is set
        Transition_FadeNodes.play();
        // ############################################################


    } // ConstructInstructionsScene()



    /**
     * ButtonCreation()
     * Description: creates the various buttons for the instruction page
     */
    private void ButtonCreation() {

        // Return Home Button
        // ############################################################
        Button_ReturnToMenu = new Button("Return Home");
        Button_ReturnToMenu.setOnAction(this.ReturnHome);
        Button_ReturnToMenu.setPrefSize(PROG_UI_D_DataVariables.SCHEDULE_Button_PrefWidthLarge, PROG_UI_D_DataVariables.SCHEDULE_Button_PrefHeightLarge);
        // ############################################################

    } // ButtonCreation()



    private void instructionsEventhandler() {

        // Returns to the main menu
        // ############################################################
        this.ReturnHome = event -> {
            
            System.out.println(PROG_DAL_D_SystemMessages.BUTTON_Instruction_returnHome);

            Transition_FadeNodes.setOnFinished(event2 -> {
                PROG_UI_A_Application.SceneManager.MainMenu();
            });

            Transition_FadeNodes.play();

        };
        // ############################################################

    } // instructionsEventhandler



    /**
     * InstructionCreation()
     * Description: creates the instruction box for users
     */
    private void InstructionCreation() {

        // Instruction text creation
        // ############################################################
        // retrieves linkedlist of txt file
        InstructionFileText = new LinkedList<>(fileReader.ReadFile());
        
        // removes file name from the instructions - should always be the first index
        InstructionFileText.remove(0);
        
        // inserts text each index in the linked list is ts own line in the text
        for (String TXTLine : InstructionFileText) {
            InstructionTextFlow.getChildren().addAll(
                new Text(TXTLine + "\n")
            );
        }
        // ############################################################



        // Textflow viisual formatting
        // ############################################################
        // padding/ line spacing
        this.InstructionTextFlow.setPadding(new Insets(5));
        this.InstructionTextFlow.setLineSpacing(1);
        // set style for general text
        this.InstructionTextFlow.getStyleClass().add(PROG_UI_D_DataVariables.STYLE_INSTRUCTION_TextHolder);
        // set style for title should always be the first node
        this.InstructionTextFlow.getChildren().get(0).getStyleClass().add(PROG_UI_D_DataVariables.STYLE_INSTRUCTION_TextTitle);
        // ############################################################



        // Scroll pane management - must be called after instructions construction
        // ############################################################
        // Create scrollpane
        this.instructionScrollPane = new ScrollPane(this.InstructionTextFlow);
        // set style
        this.instructionScrollPane.getStyleClass().add(PROG_UI_D_DataVariables.STYLE_INSTRUCTION_ScrollPane);
        this.instructionScrollPane.setFitToHeight(true);
        this.instructionScrollPane.setFitToWidth(true);
        // set width/height
        this.instructionScrollPane.setPrefWidth(PROG_UI_A_SceneManager.WindowWidth / 1.5);
        this.instructionScrollPane.setPrefHeight(PROG_UI_A_SceneManager.WindowHeight / 1.5);
        // ############################################################


    
        // Add listeners to ensure the text adjusts to window size changes
        // ############################################################
        this.ApplicationStage.widthProperty().addListener((observed, oldWidth, newWidth) -> {
            instructionScrollPane.setPrefWidth(newWidth.intValue() / 1.5);
        });

        this.ApplicationStage.heightProperty().addListener((observed, oldHeight, newHeight) -> {
            instructionScrollPane.setPrefHeight(newHeight.intValue() / 1.5);
        });
        // ############################################################

    } // InstructionCreation



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

        Instruction_RootNode.setBackground(new Background(backgroundFill));

    } // BackgroundManagement



    /**
     * SceneTransitions()
     * Description: manages the scene transitions
     */
    private void SceneTransitions() {

        // Transition to fade buttons
        Transition_FadeNodes = new ParallelTransition();

        for (Node InstructionNode : Instruction_RootNode.getChildren()) {
            
            FadeTransition NodeFade = new FadeTransition(
                Duration.seconds(PROG_UI_D_DataVariables.INSTRUCTION_FadeTime),
                InstructionNode
            );

            NodeFade.setToValue(PROG_UI_D_DataVariables.INSTRUCTION_FadeOpacity);
            
            Transition_FadeNodes.getChildren().addAll(NodeFade);
        }
        // ############################################################



        // Transition to Unfade buttons
        Transition_UnFadeNodes = new ParallelTransition();

        for (Node InstructionNode : Instruction_RootNode.getChildren()) {
            
            FadeTransition NodeUnFade = new FadeTransition(
                Duration.seconds(PROG_UI_D_DataVariables.INSTRUCTION_UnFadeTime),
                InstructionNode
            );

            NodeUnFade.setToValue(PROG_UI_D_DataVariables.INSTRUCTION_UnFadeOpacity);
            
            Transition_UnFadeNodes.getChildren().addAll(NodeUnFade);
        }
        // ############################################################

    } // SceneTransitions


    
} // PROG_UI_B_InstructionsScene
