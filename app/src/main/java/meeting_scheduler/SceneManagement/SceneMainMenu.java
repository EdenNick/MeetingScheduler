// Package  - DO Not Change
// ############################################################
package meeting_scheduler.SceneManagement;
// ############################################################

// Imports
// ############################################################
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import meeting_scheduler.SystemInfoManager;
import meeting_scheduler.SystemManagement.ManageAppWindow;
import meeting_scheduler.SystemManagement.ManageScenes;
import meeting_scheduler.global;
// ############################################################



public class SceneMainMenu {

    // Application Window variables
    // ############################################################
    // Reference of the application stage used for local operations
    private final Stage ApplicationStage;
    private Scene   sceneMenu;
    private double  stageWidth;
    private double  stageHeight;
    private ParallelTransition TransitionFadeMenu;
    private ParallelTransition TransitionUnFadeMenu;
    private final int TIME_FADE;
    private final int TIME_UNFADE;
    private final int OPACIY_FADE;
    private final int OPACITY_UNFADE;
    // ############################################################

    // Nodes
    // ############################################################
    private AnchorPane  rootNodeAnchorPane;
    private VBox    ButtonHolderVBox;
    private Button  ButtonDataCardPage;
    private Button  ButtonSchedulePage;
    private Button  ButtonInstructionPage;
    private Button  ButtonEndProgram;
    // ############################################################

    // Graphic
    // ############################################################
    private Circle  circleDecoration;
    // ############################################################

    // Action events
    // ############################################################
    private EventHandler<ActionEvent> eventCloseProgram              = null;
    private EventHandler<ActionEvent> eventScenechangeDataCard       = null;
    private EventHandler<ActionEvent> eventSceneChangeSchedule       = null;
    private EventHandler<ActionEvent> eventSceneChangeInstruction    = null;
    // ############################################################


    // Styling Variables
    // ############################################################
    private final String STYLE_FILE;
    private final String STYLE_ONE;
    private final String STYLE_TWO;
    private final double SPACING;
    private final double ANCHOR_DEFAULT;
    private final double ANCHOR_SECONDARY;
    private final double ENDBUTTON_WIDTH;
    private final double ENDBUTTON_HEIGHT;
    private final double MENUBUTTON_WIDTH;
    private final double MENUBUTTON_HEIGHT;
    // ############################################################



    /**
     * Constructor class
     */
    public SceneMainMenu(Stage stage) {

        // Set local stage variable to reference the global stage variable, Allows scene manager to work properly
        // ############################################################
        this.ApplicationStage = stage;
        // ############################################################

        // Set local styling variable
        // ############################################################
        this.STYLE_FILE = SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_STRING(global.BASICVALUESSTRING.STRING, 15); // 15 - /CSS_STYLE_FILEs.css
        this.STYLE_ONE  = SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_STRING(global.BASICVALUESSTRING.STRING, 17); // 17 - MainMenu_UI_MenuOptions
        this.STYLE_TWO  = SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_STRING(global.BASICVALUESSTRING.STRING, 18); // 18 - MainMenu_ButtonEndProgram

        this.SPACING            = SystemInfoManager.GET_SYSTEM_UI_SPACING(global.UISPACING.SPACING, 2); // 2 - 20
        this.ANCHOR_DEFAULT     = SystemInfoManager.GET_SYSTEM_UI_SPACING(global.UISPACING.ANCHOR, 3);  // 3 - 20
        this.ANCHOR_SECONDARY   = SystemInfoManager.GET_SYSTEM_UI_SPACING(global.UISPACING.ANCHOR, 5);  // 5 - 30

        this.MENUBUTTON_WIDTH   = SystemInfoManager.GET_SYSTEM_UI_SPACING(global.UISPACING.WIDTH, 4);   // 4 - 250
        this.MENUBUTTON_HEIGHT  = SystemInfoManager.GET_SYSTEM_UI_SPACING(global.UISPACING.HEIGHT, 6);  // 6 - 50

        this.ENDBUTTON_WIDTH    = SystemInfoManager.GET_SYSTEM_UI_SPACING(global.UISPACING.WIDTH, 1);   // 1 - 100
        this.ENDBUTTON_HEIGHT   = SystemInfoManager.GET_SYSTEM_UI_SPACING(global.UISPACING.HEIGHT, 6);  // 6 - 50
        
        this.TIME_FADE      = SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_INT(global.BASICVALUESINT.INT, 4); // 4 - 2
        this.TIME_UNFADE    = SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_INT(global.BASICVALUESINT.INT, 5); // 5 - 2
        this.OPACIY_FADE    = SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_INT(global.BASICVALUESINT.INT, 6); // 6 - 0
        this.OPACITY_UNFADE = SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_INT(global.BASICVALUESINT.INT, 7); // 7 - 1
        // ############################################################

    } // SceneMainMenu()


    
    /**
     * ChangeToMainMenu()
     * Description: Public method meant to be called outside the class in order to set the scene to the main menu scene
     */
    public void ChangeToMainMenu() {
        
        // Gets the current size of the stage
        this.stageWidth   = this.ApplicationStage.getWidth();
        this.stageHeight  = this.ApplicationStage.getHeight();

        // sets the correct size for the stage
        this.ApplicationStage.setWidth  (stageWidth);
        this.ApplicationStage.setHeight (stageHeight);

        // Sets the stage to the main menu scene
        this.ApplicationStage.setScene  (this.sceneMenu);

        // Shows the change
        this.ApplicationStage.show();

        // Unfades the stage
        TransitionUnFadeMenu.play();



        // System Message
        // ############################################################
        // 5 - SUCCESS | 19 - SCENE_CREATE_MainMenu | 8 - SYSTEM-SetScenes | 13 - Scene Switch to Main Menu
        SystemInfoManager.GET_SYSTEM_MESSAGE(5,19,8,13);
        // ############################################################

    } // ChangeToMainMenu()



    /**
     * ConstructMainMenuStage()
     * Description: Performs the necessary operations in order to build the various nodes/components of the stage. Should only ever be called once
     */
    public void ConstructMainMenuScene() {


        // Root Node construction
        // ############################################################
        rootNodeAnchorPane = new AnchorPane();
        rootNodeAnchorPane.getStylesheets().add(getClass().getResource(this.STYLE_FILE).toExternalForm());
        // ############################################################

        // event handler creation
        // ############################################################
        MainMenuEventHandlers();
        // ############################################################

        // Button Creation
        // ############################################################
        MainMenuButtons();
        // ############################################################

        // Node Creation and Set
        // ############################################################
        NodeConstructionSet();
        // ############################################################

        // Graphics creation
        // ############################################################
        mainMenuGraphics();
        // ############################################################

        // Set Nodes to Root Node
        // ############################################################
        // menu UI - add scene transition buttons
        ButtonHolderVBox.getChildren().addAll(ButtonDataCardPage, ButtonSchedulePage, ButtonInstructionPage);
        // Root Node - UI components and background effects
        rootNodeAnchorPane.getChildren().addAll(circleDecoration, ButtonHolderVBox, ButtonEndProgram);
        // ############################################################

        // Node Position setting
        // ############################################################
        // Root Node - set Menu buttons position
        AnchorPane.setTopAnchor     (ButtonHolderVBox,  this.ANCHOR_SECONDARY);
        AnchorPane.setLeftAnchor    (ButtonHolderVBox,  this.ANCHOR_DEFAULT);
        // Root Node - set end program button position
        AnchorPane.setBottomAnchor  (ButtonEndProgram,  this.ANCHOR_DEFAULT);
        AnchorPane.setRightAnchor   (ButtonEndProgram,  this.ANCHOR_DEFAULT);
        // ############################################################

        // Scene transition creation- must be called after all nodes have been added to root node or it won't work properly
        // ############################################################
        MainMenuSceneTransitions();
        // ############################################################

        // Scene Creation with Root Node rootNodeAnchorPane
        // ############################################################
        this.sceneMenu = new Scene(rootNodeAnchorPane, ManageScenes.WindowWidth, ManageScenes.WindowHeight);
        // ############################################################

        // Fade all nodes - required so when the program starts the scene can fade in
        // ############################################################
        TransitionFadeMenu.play();
        // ############################################################



        // System Message
        // ############################################################
        // 5 - SUCCESS | 19 - SCENE_CREATE_MainMenu | 5 - SYSTEM-CreateScenes | 15 - Scene created and set
        SystemInfoManager.GET_SYSTEM_MESSAGE(5,19,5,15);
        // ############################################################
        
    } // ConstructMainMenuScene()



    /**
     * NodeConstructionSet()
     * Description: constructs the various nodes and sets their default parameters
     */
    private void NodeConstructionSet() {
        // menu button holder node
        ButtonHolderVBox= new VBox(this.SPACING);
        ButtonHolderVBox.getStyleClass().add(this.STYLE_ONE);
        ButtonHolderVBox.setPrefWidth(ManageScenes.WindowWidth / 1.5);
        ButtonHolderVBox.setPrefHeight(ManageScenes.WindowHeight / 1.5);
        ButtonHolderVBox.setAlignment(Pos.CENTER);
    } // NodeConstructionSet()



    /**
     * MainMenuButtons()
     * Description: creates the main menu buttons
     */
    private void MainMenuButtons() {

        // End program button
        // ############################################################
        this.ButtonEndProgram = new Button("End program");
        this.ButtonEndProgram.setOnAction(this.eventCloseProgram);
        this.ButtonEndProgram.getStyleClass().add(this.STYLE_TWO);
        this.ButtonEndProgram.setPrefWidth(this.ENDBUTTON_WIDTH);
        this.ButtonEndProgram.setPrefHeight(this.ENDBUTTON_HEIGHT);
        // ############################################################

        // Data card page button
        // ############################################################
        this.ButtonDataCardPage = new Button("Manage Data Cards");
        this.ButtonDataCardPage.setOnAction(this.eventScenechangeDataCard);
        this.ButtonDataCardPage.setPrefWidth(this.MENUBUTTON_WIDTH);
        this.ButtonDataCardPage.setPrefHeight(this.MENUBUTTON_HEIGHT);
        // ############################################################
        
        // Schedule page button
        // ############################################################
        this.ButtonSchedulePage = new Button("Schedule"); 
        this.ButtonSchedulePage.setOnAction(this.eventSceneChangeSchedule);
        this.ButtonSchedulePage.setPrefWidth(this.MENUBUTTON_WIDTH);
        this.ButtonSchedulePage.setPrefHeight(this.MENUBUTTON_HEIGHT);
        // ############################################################

        // Schedule page button
        // ############################################################
        ButtonInstructionPage = new Button("Instructions");
        ButtonInstructionPage.setOnAction(this.eventSceneChangeInstruction);
        ButtonInstructionPage.setPrefWidth(this.MENUBUTTON_WIDTH);
        ButtonInstructionPage.setPrefHeight(this.MENUBUTTON_HEIGHT);
        // ############################################################

    } // MainMenuButtons()



    /**
     * MainMenuEventHandlers
     * Description: handles the creation of the various event handlers used for the main menu
     */
    private void MainMenuEventHandlers() {

        // Exits the program
        // ############################################################
        this.eventCloseProgram = event -> {
            
            TransitionFadeMenu.setOnFinished(event2 -> {
                Platform.exit();
            });

            TransitionFadeMenu.play();



            // System Message
            // ############################################################
            // 5 - SUCCESS | 19 - SCENE_CREATE_MainMenu | 12 - USER-ButtonPress | 23 - closing program
            SystemInfoManager.GET_SYSTEM_MESSAGE(5,19,12,23);
            // ############################################################
        };
        // ############################################################


        // changes scene to data card management
        // ############################################################
        this.eventScenechangeDataCard = event -> {

            TransitionFadeMenu.setOnFinished(event2 -> {
                ManageAppWindow.SceneManager.SwapToDataCard();
            });

            TransitionFadeMenu.play();



            // System Message
            // ############################################################
            // 5 - SUCCESS | 19 - SCENE_CREATE_MainMenu | 12 - USER-ButtonPress | 11 - Scene Switch to Datacard page
            SystemInfoManager.GET_SYSTEM_MESSAGE(5,19,12,11);
            // ############################################################

        };
        // ############################################################


        // changes the scene to schedule managment
        // ############################################################
        this.eventSceneChangeSchedule = event -> {

            TransitionFadeMenu.setOnFinished(event2 -> {
                ManageAppWindow.SceneManager.SwapToSchedule();
            });

            TransitionFadeMenu.play();



            // System Message
            // ############################################################
            // 5 - SUCCESS | 19 - SCENE_CREATE_MainMenu | 12 - USER-ButtonPress | 11 - Scene Switch to Scheduling page
            SystemInfoManager.GET_SYSTEM_MESSAGE(5,19,12,14);
            // ############################################################

        };
        // ############################################################


        // changes the scene to schedule managment
        // ############################################################
        this.eventSceneChangeInstruction = event -> {

            TransitionFadeMenu.setOnFinished(event2 -> {
                ManageAppWindow.SceneManager.SwapToInstruct();
            });

            TransitionFadeMenu.play();



            // System Message
            // ############################################################
            // 5 - SUCCESS | 19 - SCENE_CREATE_MainMenu | 12 - USER-ButtonPress | 12 - Scene Switch to instruction page
            SystemInfoManager.GET_SYSTEM_MESSAGE(5,19,12,12);
            // ############################################################
            
        };
        // ############################################################

    } // MainMenuEventHandlers()



    /**
     * mainMenuGraphics()
     * Description: create the various graphical elements for the main menu
     */
    private void mainMenuGraphics() {


        // TODO: finish graphics once program functionality achieved
        // Background Creation
        // ############################################################
        LinearGradient BackgroundGradient = new LinearGradient(0, 0, 300, 300, false, CycleMethod.NO_CYCLE, 
            new Stop(0, Color.DARKBLUE), new Stop(1, Color.BEIGE)
        );

        BackgroundFill backgroundFill = new BackgroundFill(BackgroundGradient, CornerRadii.EMPTY, Insets.EMPTY);

        rootNodeAnchorPane.setBackground(new Background(backgroundFill));

        // "earth"
        circleDecoration = new Circle(1500);
        circleDecoration.setFill(Color.BLUE);

        // Root Node - set circle position
        AnchorPane.setTopAnchor(circleDecoration, 30.0);
        AnchorPane.setLeftAnchor(circleDecoration, 30.0);
        // ############################################################


    } // mainMenuGraphics()


    
    /**
     * MainMenuSceneTransitions()
     * Description the fade in and fade out transitions for the main menu
     */
    private void MainMenuSceneTransitions() {

        // Transition to fade button
        // ############################################################
        TransitionFadeMenu = new ParallelTransition();

        for (Node MenuNode : rootNodeAnchorPane.getChildren()) {
            
            FadeTransition NodeFade = new FadeTransition(
                Duration.seconds(this.TIME_FADE),
                MenuNode
            );

            NodeFade.setToValue(this.OPACIY_FADE);
            
            TransitionFadeMenu.getChildren().addAll(NodeFade);
        }
        // ############################################################



        // Transition to Unfade button
        // ############################################################
        TransitionUnFadeMenu = new ParallelTransition();

        for (Node MenuNode : rootNodeAnchorPane.getChildren()) {
            
            FadeTransition NodeUnFade = new FadeTransition(
                Duration.seconds(this.TIME_UNFADE),
                MenuNode
            );

            NodeUnFade.setToValue(this.OPACITY_UNFADE);
            
            TransitionUnFadeMenu.getChildren().addAll(NodeUnFade);
        }
        // ############################################################

    } // MainMenuSceneTransitions()



} // PROG_UI_A_MainMenuScene
