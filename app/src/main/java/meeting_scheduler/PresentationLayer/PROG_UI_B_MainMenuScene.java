/**
 * PROG_UI_B_MainMenuScene.java
 * 
 * Description: main menu scene of the application
 */

// Package  - DO Not Change
// ############################################################
package meeting_scheduler.PresentationLayer;
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
// System Messages
import meeting_scheduler.DataAccessLayer.PROG_DAL_D_SystemMessages;
// ############################################################



public class PROG_UI_B_MainMenuScene {

    // Reference of the application stage used for local operations
    private final Stage ApplicationStage;

    // Scene
    private Scene   Menu_Scene;

    // Root Node
    private AnchorPane  Menu_RootNode;

    // Format Nodes
    private VBox    Menu_UI_ButtonHolder;

    // Buttons
    private Button  Button_EndProgram;
    private Button  Button_SchedulePage;
    private Button  Button_DataCardPage;
    private Button  InstructionButton;

    // transitions
    private ParallelTransition Transition_fadeMenu;
    private ParallelTransition Transition_UnFadeMenu;

    //graphics
    private Circle  circleDecoration;

    // Stage width/height
    private double  StageWidth;
    private double  stageHeight;


    // Action events
    private EventHandler<ActionEvent> closeProgram              = null;
    private EventHandler<ActionEvent> DatacardScenechange       = null;
    private EventHandler<ActionEvent> ScheduleSceneChange       = null;
    private EventHandler<ActionEvent> InstructionSceneChange    = null;




    /**
     * Constructor class
     */
    public PROG_UI_B_MainMenuScene(Stage stage) {
        this.ApplicationStage = stage;
    }


    
    /**
     * ChangeToMainMenu()
     * Description: Public method meant to be called outside the class in order to set the scene to the main menu scene
     */
    public void ChangeToMainMenu() {
        
        // Gets the current size of the stage
        this.StageWidth   = this.ApplicationStage.getWidth();
        this.stageHeight  = this.ApplicationStage.getHeight();

        // sets the correct size for the stage
        this.ApplicationStage.setWidth  (StageWidth);
        this.ApplicationStage.setHeight (stageHeight);

        // Sets the stage to the main menu scene
        this.ApplicationStage.setScene  (this.Menu_Scene);

        // Shows the change
        this.ApplicationStage.show();

        // Unfades the stage
        Transition_UnFadeMenu.play();

    } // ChangeToMainMenu()



    /**
     * ConstructMainMenuStage()
     * Description: Performs the necessary operations in order to build the various nodes/components of the stage. Should only ever be called once
     */
    public void ConstructMainMenuScene() {


        // Nodes
        // ############################################################
        // Root Node
        Menu_RootNode           = new AnchorPane();
        // menu button holder node
        Menu_UI_ButtonHolder    = new VBox(PROG_UI_D_DataVariables.MENU_UI_Spacing);
        Menu_UI_ButtonHolder    .setAlignment(Pos.CENTER);
        // ############################################################

        // event handler creation
        // ############################################################
        MainMenuEventHandlers();
        // ############################################################


        // Button Creation
        // ############################################################
        MainMenuButtons();
        // ############################################################


        // Graphics creation
        // ############################################################
        mainMenuGraphics();
        // ############################################################

        

        // Node Position setting
        // ############################################################
        // Root Node - set Menu buttons position
        AnchorPane.setTopAnchor     (Menu_UI_ButtonHolder,  PROG_UI_D_DataVariables.MENU_UI_TopAnchor);
        AnchorPane.setLeftAnchor    (Menu_UI_ButtonHolder,  PROG_UI_D_DataVariables.MENU_UI_LeftAnchor);
        // Root Node - set end program button position
        AnchorPane.setBottomAnchor  (Button_EndProgram,     PROG_UI_D_DataVariables.MENU_EndProg_BottomAnchor);
        AnchorPane.setRightAnchor   (Button_EndProgram,     PROG_UI_D_DataVariables.MENU_EndProg_RightAnchor);
        // ############################################################



        // Combine Nodes
        // ############################################################
        // menu UI - add scene transition buttons
        Menu_UI_ButtonHolder    .getChildren().addAll(Button_DataCardPage, Button_SchedulePage, InstructionButton);
        // Root Node - UI components and background effects
        Menu_RootNode           .getChildren().addAll(circleDecoration, Menu_UI_ButtonHolder, Button_EndProgram);
        // ############################################################



        // Scene transition creation- must be called after all nodes have been added to root node or it won't work properly
        // ############################################################
        MainMenuSceneTransitions();
        // ############################################################



        // Scene Creation with Root Node Menu_RootNode
        // ############################################################
        this.Menu_Scene = new Scene(Menu_RootNode, PROG_UI_A_SceneManager.WindowWidth, PROG_UI_A_SceneManager.WindowHeight);
        // ############################################################



        // Fade all nodes - required so when the program starts the scene can fade in
        // ############################################################
        Transition_fadeMenu.play();
        // ############################################################



    } // ConstructMainMenuScene()







    /**
     * MainMenuButtons()
     * Description: creates the main menu buttons
     */
    private void MainMenuButtons() {

        // End program button
        // ############################################################
        Button_EndProgram = new Button("End program");
        Button_EndProgram.setOnAction(this.closeProgram);
        // ############################################################


        // Data card page button
        // ############################################################
        Button_DataCardPage = new Button("Manage Data Cards");
        Button_DataCardPage.setOnAction(this.DatacardScenechange);
        // ############################################################
        

        // Schedule page button
        // ############################################################
        Button_SchedulePage = new Button("Schedule"); 
        Button_SchedulePage.setOnAction(this.ScheduleSceneChange);
        // ############################################################


        // Schedule page button
        // ############################################################
        InstructionButton = new Button("Instructions");
        InstructionButton.setOnAction(this.InstructionSceneChange);
        // ############################################################


    } // MainMenuButtons()


    /**
     * MainMenuEventHandlers
     * Description: handles the creation of the various event handlers used for the main menu
     */
    private void MainMenuEventHandlers() {

        // Exits the program
        // ############################################################
        this.closeProgram = event -> {
            
            System.out.println(PROG_DAL_D_SystemMessages.BUTTON_MainMenu_end);
            
            Transition_fadeMenu.setOnFinished(event2 -> {
                Platform.exit();
            });

            Transition_fadeMenu.play();

        };
        // ############################################################


        // changes scene to data card management
        // ############################################################
        this.DatacardScenechange = event -> {
            
            System.out.println(PROG_DAL_D_SystemMessages.BUTTON_MainMenu_ToDataCard);

            Transition_fadeMenu.setOnFinished(event2 -> {
                PROG_UI_A_Application.SceneManager.DataCardManage();
            });

            Transition_fadeMenu.play();

        };
        // ############################################################


        // changes the scene to schedule managment
        // ############################################################
        this.ScheduleSceneChange = event -> {
            
            System.out.println(PROG_DAL_D_SystemMessages.BUTTON_MainMenu_ToSchedule);

            Transition_fadeMenu.setOnFinished(event2 -> {
                PROG_UI_A_Application.SceneManager.Schedule();
            });

            Transition_fadeMenu.play();

        };
        // ############################################################


        // changes the scene to schedule managment
        // ############################################################
        this.InstructionSceneChange = event -> {
            
            System.out.println(PROG_DAL_D_SystemMessages.BUTTON_MainMenu_ToInstructions);

            Transition_fadeMenu.setOnFinished(event2 -> {
                PROG_UI_A_Application.SceneManager.Instructions();
            });

            Transition_fadeMenu.play();

        };
        // ############################################################

    }






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

        Menu_RootNode.setBackground(new Background(backgroundFill));

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
        Transition_fadeMenu = new ParallelTransition();

        for (Node MenuNode : Menu_RootNode.getChildren()) {
            
            FadeTransition NodeFade = new FadeTransition(
                Duration.seconds(PROG_UI_D_DataVariables.MENU_FadeTime),
                MenuNode
            );

            NodeFade.setToValue(PROG_UI_D_DataVariables.MENU_FadeOpacity);
            
            Transition_fadeMenu.getChildren().addAll(NodeFade);
        }
        // ############################################################



        // Transition to Unfade button
        // ############################################################
        Transition_UnFadeMenu = new ParallelTransition();

        for (Node MenuNode : Menu_RootNode.getChildren()) {
            
            FadeTransition NodeUnFade = new FadeTransition(
                Duration.seconds(PROG_UI_D_DataVariables.MENU_UnFadeTime),
                MenuNode
            );

            NodeUnFade.setToValue(PROG_UI_D_DataVariables.MENU_UnFadeOpacity);
            
            Transition_UnFadeMenu.getChildren().addAll(NodeUnFade);
        }
        // ############################################################

    } // MainMenuSceneTransitions()



} // PROG_UI_A_MainMenuScene
