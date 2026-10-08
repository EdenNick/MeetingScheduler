// Package  - DO Not Change
// ############################################################
package meeting_scheduler.SceneManagement;
// ############################################################

// Imports
// ############################################################
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
import meeting_scheduler.SystemInfoManager;
import meeting_scheduler.FileManagement.ManageTXTOutput;
import meeting_scheduler.SystemManagement.ManageAppWindow;
import meeting_scheduler.SystemManagement.ManageScenes;
// ############################################################
import meeting_scheduler.global;



public class SceneInstructions {
    


    // Application
    // ############################################################
    // Reference of the application stage used for local operations
    private final Stage ApplicationStage;
    private Scene       InstructionScene;
    private double      StageWidth;
    private double      stageHeight;
    // ############################################################

    // Nodes
    // ############################################################
    private AnchorPane          Instruction_RootNode;
    private ScrollPane          instructionScrollPane;
    private TextFlow            InstructionTextFlow;
    private Button              Button_ReturnToMenu;
    private ParallelTransition  Transition_FadeNodes;
    private ParallelTransition  Transition_UnFadeNodes;
    // ############################################################

    // event handlers
    // ############################################################
    private EventHandler<ActionEvent> ReturnHome = null;
    // ############################################################

    // File manager
    // ############################################################
    ManageTXTOutput fileReader;
    // ############################################################

    // data management
    // ############################################################
    LinkedList<String> InstructionFileText;
    // ############################################################


    private String Style;
    private String styleOne;
    private String styleTwo;
    private String styleThree;

    private double Anchor;
    private double returnButtonWidth;
    private double returnButtonHeight;
    private double Padding;

    private int FadeTime;
    private int FadeOpacity;
    private int UnFadeTime;
    private int UnFadeOpacity;

    /**
     * Constructor class
     */
    public SceneInstructions(Stage stage) {
        // set the stage
        this.ApplicationStage = stage;
        // create the file reader object and set it to read from the instructions file
        this.fileReader = new ManageTXTOutput("/PROG_UI_D_Instructions.txt");



    this.Style      = SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_STRING(global.BASICVALUESSTRING.STRING, 15); // 15 - /CSS_Styles.css
    this.styleOne   = SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_STRING(global.BASICVALUESSTRING.STRING, 23); // 23 - Instructions_UI_TextHolder
    this.styleTwo   = SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_STRING(global.BASICVALUESSTRING.STRING, 24); // 24 - Instructions_UI_TextTitle
    this.styleThree = SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_STRING(global.BASICVALUESSTRING.STRING, 25); // 25 - Instructions_UI_ScrollPane

    this.Anchor     = SystemInfoManager.GET_SYSTEM_UI_SPACING(global.UISPACING.ANCHOR, 3); // 3 - 20
    this.Padding    = SystemInfoManager.GET_SYSTEM_UI_SPACING(global.UISPACING.PADDING, 0); // 0 - 5
    this.returnButtonWidth  = SystemInfoManager.GET_SYSTEM_UI_SPACING(global.UISPACING.WIDTH, 2); // 2 - 150
    this.returnButtonHeight = SystemInfoManager.GET_SYSTEM_UI_SPACING(global.UISPACING.HEIGHT, 3); // 2 - 30
    
    this.FadeTime       = SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_INT(global.BASICVALUESINT.INT, 4); // 4 - 2
    this.FadeOpacity    = SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_INT(global.BASICVALUESINT.INT, 7); // 6 - 0
    this.UnFadeTime     = SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_INT(global.BASICVALUESINT.INT, 5); // 5 - 2
    this.UnFadeOpacity  = SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_INT(global.BASICVALUESINT.INT, 7); // 7 - 1

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
        
        // System Message
        // 5 - SUCCESS | 18 - SCENE_CREATE_Instruct | 12 - SYSTEM-SetScenes | 12 - Scene Switch to instruction page
        SystemInfoManager.GET_SYSTEM_MESSAGE(5,18,12,12);

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
        Instruction_RootNode.getStylesheets().add(getClass().getResource(this.Style).toExternalForm());
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
        AnchorPane.setBottomAnchor   (Button_ReturnToMenu,   this.Anchor);
        AnchorPane.setRightAnchor   (Button_ReturnToMenu,   this.Anchor);

        // root Node - set text position
        AnchorPane.setTopAnchor     (instructionScrollPane, this.Anchor);
        AnchorPane.setLeftAnchor    (instructionScrollPane, this.Anchor);
        // ############################################################


        // Add sub-nodes to their positions
        // ############################################################
        Instruction_RootNode.getChildren().addAll(Button_ReturnToMenu, instructionScrollPane);
        // ############################################################


        // Scene Creation with Root Node Instruction_RootNode
        // ############################################################
        this.InstructionScene = new Scene(Instruction_RootNode, ManageScenes.WindowWidth, ManageScenes.WindowHeight);
        // ############################################################


        // Scene transition
        // ############################################################
        // create trnasitions
        SceneTransitions();
        // fade all objects before the scene is set
        Transition_FadeNodes.play();
        // ############################################################


        // System Message
        // ############################################################
        // 5 - SUCCESS | 18 - SCENE_CREATE_Instruct | 5 - SYSTEM-CreateScenes | 15 - Scene created and set
        SystemInfoManager.GET_SYSTEM_MESSAGE(5,18,5,15);
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
        Button_ReturnToMenu.setPrefSize(this.returnButtonWidth, this.returnButtonHeight);
        // ############################################################

    } // ButtonCreation()



    private void instructionsEventhandler() {

        // Returns to the main menu
        // ############################################################
        this.ReturnHome = event -> {

            Transition_FadeNodes.setOnFinished(event2 -> {
                ManageAppWindow.SceneManager.SwapToMainMenu();
            });

            Transition_FadeNodes.play();

            // System Message
            // 5 - SUCCESS | 18 - SCENE_CREATE_Instruct | 12 - USER-ButtonPress | 13 - Scene Switch to Main Menu
            SystemInfoManager.GET_SYSTEM_MESSAGE(5,18,12,13);
        };
        // ############################################################

    } // instructionsEventhandler



    /**
     * InstructionCreation()
     * Description: creates the instruction box for users
     */
    private void InstructionCreation() {

        // TODO: redo this section, this is too unorganized and prone to failure

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
        this.InstructionTextFlow.setPadding(new Insets(this.Padding));
        this.InstructionTextFlow.setLineSpacing(1);
        // set style for general text
        this.InstructionTextFlow.getStyleClass().add(this.styleOne);
        // set style for title should always be the first node
        this.InstructionTextFlow.getChildren().get(0).getStyleClass().add(this.styleTwo);
        // ############################################################



        // Scroll pane management - must be called after instructions construction
        // ############################################################
        // Create scrollpane
        this.instructionScrollPane = new ScrollPane(this.InstructionTextFlow);
        // set style
        this.instructionScrollPane.getStyleClass().add(this.styleThree);
        this.instructionScrollPane.setFitToHeight(true);
        this.instructionScrollPane.setFitToWidth(true);
        // set width/height
        this.instructionScrollPane.setPrefWidth(ManageScenes.WindowWidth / 1.5);
        this.instructionScrollPane.setPrefHeight(ManageScenes.WindowHeight / 1.5);
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
                Duration.seconds(this.FadeTime),
                InstructionNode
            );

            NodeFade.setToValue(this.FadeOpacity);
            Transition_FadeNodes.getChildren().addAll(NodeFade);
        }
        // ############################################################



        // Transition to Unfade buttons
        Transition_UnFadeNodes = new ParallelTransition();

        for (Node InstructionNode : Instruction_RootNode.getChildren()) {

            FadeTransition NodeUnFade = new FadeTransition(
                Duration.seconds(this.UnFadeTime),
                InstructionNode
            );

            NodeUnFade.setToValue(this.UnFadeOpacity);            
            Transition_UnFadeNodes.getChildren().addAll(NodeUnFade);
        }
        // ############################################################

    } // SceneTransitions


    
} // PROG_UI_B_InstructionsScene
