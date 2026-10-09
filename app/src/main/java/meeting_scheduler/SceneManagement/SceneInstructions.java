// Package  - DO Not Change
// ############################################################
package meeting_scheduler.SceneManagement;
// ############################################################

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
// Imports
// ############################################################
import java.util.LinkedList;
import java.util.Properties;

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
import javafx.scene.layout.Pane;
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
import meeting_scheduler.global;
// ############################################################



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
    private AnchorPane          rootNodeAnchorPane;
    private ScrollPane          InstructionScrollPane;
    private Pane                TextHolderPane;
    private Button              ButtonReturnToMenu;
    private ParallelTransition  TransitionFadeNodes;
    private ParallelTransition  TransitionUnFadeNodes;
    // ############################################################

    // event handlers
    // ############################################################
    private EventHandler<ActionEvent> EventReturnHome = null;
    // ############################################################

    // File manager
    // ############################################################
    // ManageTXTOutput fileReader;
    // ############################################################

    // data management
    // ############################################################
    // LinkedList<String> InstructionFileText;
    // ############################################################

    // Styling
    // ############################################################
    private final String STYLE_FILE;
    private final String STYLE_DEFAULT;
    private final String STYLE_ONE;
    private final String STYLE_TWO;
    private final String STYLE_THREE;

    private final double ANCHOR;
    private final double RETURNBUTOON_WIDTH;
    private final double RETURNBUTTON_HEIGHT;
    private final double PADDING;

    private final int TIME_FADE;
    private final int TIME_UNFADE;
    private final int OPACITY_FADE;
    private final int OPACITY_UNFADE;
    // ############################################################



    /**
     * Constructor class
     */
    public SceneInstructions(Stage stage) {

        // set the stage to the global reference so the scene manager can function
        // ############################################################
        this.ApplicationStage = stage;
        // ############################################################

        // Set UI Styling
        // ############################################################
        this.STYLE_FILE     = SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_STRING(global.BASICVALUESSTRING.STRING, 15); // 15 - /CSS_Styles.css
        this.STYLE_DEFAULT  = SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_STRING(global.BASICVALUESSTRING.STRING, 16); // 16 - default-label
        this.STYLE_ONE      = SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_STRING(global.BASICVALUESSTRING.STRING, 23); // 23 - Instructions_UI_TextHolder
        this.STYLE_TWO      = SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_STRING(global.BASICVALUESSTRING.STRING, 24); // 24 - Instructions_UI_TextTitle
        this.STYLE_THREE    = SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_STRING(global.BASICVALUESSTRING.STRING, 25); // 25 - Instructions_UI_ScrollPane

        this.ANCHOR     = SystemInfoManager.GET_SYSTEM_UI_SPACING(global.UISPACING.ANCHOR, 3);  // 3 - 20
        this.PADDING    = SystemInfoManager.GET_SYSTEM_UI_SPACING(global.UISPACING.PADDING, 0); // 0 - 5
        this.RETURNBUTOON_WIDTH  = SystemInfoManager.GET_SYSTEM_UI_SPACING(global.UISPACING.WIDTH, 2);  // 2 - 150
        this.RETURNBUTTON_HEIGHT = SystemInfoManager.GET_SYSTEM_UI_SPACING(global.UISPACING.HEIGHT, 3); // 2 - 30
        
        this.TIME_FADE      = SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_INT(global.BASICVALUESINT.INT, 4); // 4 - 2
        this.OPACITY_FADE   = SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_INT(global.BASICVALUESINT.INT, 6); // 6 - 0
        this.TIME_UNFADE    = SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_INT(global.BASICVALUESINT.INT, 5); // 5 - 2
        this.OPACITY_UNFADE = SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_INT(global.BASICVALUESINT.INT, 7); // 7 - 1
        // ############################################################

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
        TransitionUnFadeNodes.play();
        


        // System Message
        // ############################################################
        // 5 - SUCCESS | 18 - SCENE_CREATE_Instruct | 12 - SYSTEM-SetScenes | 12 - Scene Switch to instruction page
        SystemInfoManager.GET_SYSTEM_MESSAGE(5,18,12,12);
        // ############################################################

    } // changetoInstructionsScene



    /**
     * ConstructInstructionsScene()
     * Description: Performs the necessary operations in order to build the various nodes/components of the stage.
     */
    public void ConstructInstructionsScene() {

        // Node Construction
        // ############################################################
        // Root Node
        rootNodeAnchorPane = new AnchorPane();
        // Import styles
        rootNodeAnchorPane.getStylesheets().add(getClass().getResource(this.STYLE_FILE).toExternalForm());
        // Text Box
        TextHolderPane = new Pane();
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
        AnchorPane.setBottomAnchor  (ButtonReturnToMenu,   this.ANCHOR);
        AnchorPane.setRightAnchor   (ButtonReturnToMenu,   this.ANCHOR);

        // root Node - set text position
        AnchorPane.setTopAnchor     (InstructionScrollPane, this.ANCHOR);
        AnchorPane.setLeftAnchor    (InstructionScrollPane, this.ANCHOR);
        // ############################################################

        // Add sub-nodes to their positions
        // ############################################################
        rootNodeAnchorPane.getChildren().addAll(ButtonReturnToMenu, InstructionScrollPane);
        // ############################################################

        // Scene Creation with Root Node rootNodeAnchorPane
        // ############################################################
        this.InstructionScene = new Scene(rootNodeAnchorPane, ManageScenes.WindowWidth, ManageScenes.WindowHeight);
        // ############################################################

        // Scene transition
        // ############################################################
        // create trnasitions
        SceneTransitions();
        // fade all objects before the scene is set
        TransitionFadeNodes.play();
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
        ButtonReturnToMenu = new Button("Return Home");
        ButtonReturnToMenu.setOnAction(this.EventReturnHome);
        ButtonReturnToMenu.setPrefSize(this.RETURNBUTOON_WIDTH, this.RETURNBUTTON_HEIGHT);
        // ############################################################

    } // ButtonCreation()



    private void instructionsEventhandler() {

        // Returns to the main menu
        // ############################################################
        this.EventReturnHome = event -> {

            TransitionFadeNodes.setOnFinished(event2 -> {
                ManageAppWindow.SceneManager.SwapToMainMenu();
            });

            TransitionFadeNodes.play();


            // System Message
            // ############################################################
            // 5 - SUCCESS | 18 - SCENE_CREATE_Instruct | 12 - USER-ButtonPress | 13 - Scene Switch to Main Menu
            SystemInfoManager.GET_SYSTEM_MESSAGE(5,18,12,13);
            // ############################################################

        };
        // ############################################################

    } // instructionsEventhandler



    /**
     * InstructionCreation()
     * Description: creates the instruction box for users
     */
    private void InstructionCreation() {

        // TODO: Continue to Update graphics
        // Holder for the various text objects
        this.TextHolderPane = new Pane();

        // Title
        Text TITLE = GetInstructionText("TITLE.", this.STYLE_TWO);
        TITLE.setId("TITLE");
        TITLE.setX(50);
        TITLE.setY(this.ANCHOR + 10);



        // SubTitle
        Text SUB_TITLE = GetInstructionText("DESCRIPTION.", this.STYLE_DEFAULT );
        SUB_TITLE.setId("SUB_TITLE");
        SUB_TITLE.setX(60);
        SUB_TITLE.setY(this.ANCHOR + 30);


        // PageInfo
        Text INFO = GetInstructionText("PAGEINFO.", this.STYLE_DEFAULT);
        INFO.setId("INFO");
        INFO.setX(20);
        INFO.setY(this.ANCHOR + 80);

        

        // Extra text
        Text EXTRA = GetInstructionText("EXTRATEXT.", this.STYLE_DEFAULT );
        EXTRA.setId("EXTRA");
        EXTRA.setX(20);
        EXTRA.setY(this.ANCHOR + 200);


        this.TextHolderPane.getChildren().addAll(
            TITLE,
            SUB_TITLE,
            INFO,
            EXTRA
        );




        // Scroll pane management - must be called after instructions construction
        // ############################################################
        // Create scrollpane
        this.InstructionScrollPane = new ScrollPane(this.TextHolderPane);
        // set style
        this.InstructionScrollPane.getStyleClass().add(this.STYLE_THREE);
        this.InstructionScrollPane.setFitToHeight(true);
        this.InstructionScrollPane.setFitToWidth(true);
        // set width/height
        this.InstructionScrollPane.setPrefWidth(ManageScenes.WindowWidth / 1.5);
        this.InstructionScrollPane.setPrefHeight(ManageScenes.WindowHeight / 1.5);
        // ############################################################


    
        // Add listeners to ensure the text adjusts to window size changes
        // ############################################################
        this.ApplicationStage.widthProperty().addListener((observed, oldWidth, newWidth) -> {
            this.InstructionScrollPane.setPrefWidth(newWidth.intValue() / 1.5);
            this.TextHolderPane.setPrefWidth(newWidth.intValue() / 1.5);
        });

        this.ApplicationStage.heightProperty().addListener((observed, oldHeight, newHeight) -> {
            this.InstructionScrollPane.setPrefHeight(newHeight.intValue() / 1.5);
            this.TextHolderPane.setPrefHeight(newHeight.intValue() / 1.5);
        });
        // ############################################################

    } // InstructionCreation



    private static Text GetInstructionText(String INPUT_PROPPATH, String INPUT_STYLE) {

        // Properties object
        Properties  Prop_Type = new Properties();
        // File Path
        String      FilePath = "/USERINSTRUCTIONS.properties";
        //Property Path
        String      PropPath = INPUT_PROPPATH;
        // indiivudal property string
        String      IncomingPropString;
        // while loop position
        int         IncomingPropPosition = 0;
        // array to be set to global
        Text        ReturnText;

        // Arraylist containg all properites in the file
        ArrayList<String> ArraylistPropStrings = new ArrayList<>();

        // TRY/CATCH
        try ( InputStream FileInput = SceneInstructions.class.getResourceAsStream(FilePath) ) {
            
            // Load Props from File
            Prop_Type.load(FileInput);

            // While strings continue to be valid and not NULL, continue
            while ( (IncomingPropString = Prop_Type.getProperty(PropPath + IncomingPropPosition)) != null ) {

                ArraylistPropStrings.add(IncomingPropString);
                IncomingPropPosition++;
            }

            ReturnText = new Text(String.join("\n", ArraylistPropStrings));

        } catch (IOException e) {
            // TODO: SYSTEM MESSAGE
            ReturnText = new Text("ERROR");
        }


        ReturnText.getStyleClass().add(INPUT_STYLE);
        // Set to static variable
        return ReturnText;
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

        rootNodeAnchorPane.setBackground(new Background(backgroundFill));

    } // BackgroundManagement



    /**
     * SceneTransitions()
     * Description: manages the scene transitions
     */
    private void SceneTransitions() {

        // Transition to fade buttons
        TransitionFadeNodes = new ParallelTransition();

        for (Node InstructionNode : rootNodeAnchorPane.getChildren()) {
            
            FadeTransition NodeFade = new FadeTransition(
                Duration.seconds(this.TIME_FADE),
                InstructionNode
            );

            NodeFade.setToValue(this.OPACITY_FADE);
            TransitionFadeNodes.getChildren().addAll(NodeFade);
        }
        // ############################################################



        // Transition to Unfade buttons
        TransitionUnFadeNodes = new ParallelTransition();

        for (Node InstructionNode : rootNodeAnchorPane.getChildren()) {

            FadeTransition NodeUnFade = new FadeTransition(
                Duration.seconds(this.TIME_UNFADE),
                InstructionNode
            );

            NodeUnFade.setToValue(this.OPACITY_UNFADE);            
            TransitionUnFadeNodes.getChildren().addAll(NodeUnFade);
        }
        // ############################################################

    } // SceneTransitions


    
} // PROG_UI_B_InstructionsScene
