package meeting_scheduler.PresentationLayer;

import java.util.LinkedList;

// javaFX
// animation
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
// Scene
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
//event
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
//geometry
import javafx.geometry.Insets;
// stage
import javafx.stage.Stage;
// util
import javafx.util.Duration;

// schedule calculator
import meeting_scheduler.BusinessLogiclayer.PROG_BLL_SchedulingCalculation;
import meeting_scheduler.DataAccessLayer.PROG_DAL_A_Schedule;

/**
 * PROG_UI_B_SchedulePeopleScene()
 * 
 * Description: Application window scene which allows the user to make schedules based off of existing user card information
 * Contains:
 * a. back button       (scene change)
 * b. UI interface allowing user to make schedules    (various buttons, text inputs etc)
 */




public class PROG_UI_C_SchedulePeopleScene {

    // Reference of the application stage used for local operations
    private final Stage ApplicationStage;

    // Scene
    private Scene       SchedulingScene;

    // Root Node
    private AnchorPane  RootNode;

    // scrollpane
    private ScrollPane  UIInterfaceNode;

    // VBox
    private VBox        VboxUIHolder;

    // HBox
    private HBox        HBoxInputPeople;
    private HBox        HBoxInputListNumber;
    private HBox        HBoxInputDayPreference;
    private HBox        HBoxInputTimePreference;
    private HBox        HBoxCalculaeSchedule;

    // Buttons
    private Button      ReturnToMenu;
    private Button      RESET_People;
    private Button      RESET_ListNumber;
    private Button      RESET_DaySelection;
    private Button      RESET_TimeInput;
    private Button      RESET_ALLPreferences;
    private Button      CALCULATE_Schedule;

    // Stage width/height
    private double StageWidth;
    private double stageHeight;

    // transitions
    private ParallelTransition fadeMenuNodes;
    private ParallelTransition UnfadeMenuNodes;

    // Schedule Calculator
    private final PROG_BLL_SchedulingCalculation ScheduleCalculator;

    // Calculated Schedules
    private LinkedList<PROG_DAL_A_Schedule> CalculatedScheduleList;

    /**
     * Constructor class
     */
    public PROG_UI_C_SchedulePeopleScene(Stage stage) {
        this.ApplicationStage = stage;
        this.ScheduleCalculator = new PROG_BLL_SchedulingCalculation();
    }


    
    /**
     * changetoSchedulingScene()
     * Description: Public method meant to be called outside the class in order to set the scene to the Scheduling scene
     */
    public void changetoSchedulingScene() {

        // unfades nodes
        UnfadeMenuNodes.play();

        // Gets the current size of the stage
        this.StageWidth   = this.ApplicationStage.getWidth();
        this.stageHeight  = this.ApplicationStage.getHeight();

        // Sets the stage to the main menu scene
        this.ApplicationStage.setScene(this.SchedulingScene);

        // sets the correct size for the stage
        this.ApplicationStage.setWidth(StageWidth);
        this.ApplicationStage.setHeight(stageHeight);

        // Shows the change
        this.ApplicationStage.show();
        
    }



    /**
     * ConstructSchedulingScene()
     * Description: Performs the necessary operations in order to build the various nodes/components of the stage.
     */
    public void ConstructSchedulingScene() {

        // Root Node creation
        RootNode = new AnchorPane();

        // Button creation
        ButtonCreation();


        /**
         * General format
         * 
         * interfaces should be on the left side in a node that is fixed in size with a scroll wheel to see all interface options
         * 
         * schedules should be on the left side
         * 
         * each schedule should be listed in a vertical column with only one column
         * 
         * each schedule should extend horizontally to show in order
         * 
         * - is it an ideal schedule - weekday - time interval
         * - each person who can be scheduled (wraps around)
         * 
         * 
         */


        // create UI interface
        SchedulingInterface();


        // create Schedule Display
        SchedulingDisplay();


        // Root Node management
        // Root Node - set return home button position
        AnchorPane.setBottomAnchor(ReturnToMenu, 20.0);
        AnchorPane.setRightAnchor(ReturnToMenu, 20.0);

        // Root Node - set UI interface position
        AnchorPane.setTopAnchor(UIInterfaceNode, 20.0);
        AnchorPane.setLeftAnchor(UIInterfaceNode, 20.0);

        RootNode.getChildren().addAll(UIInterfaceNode, ReturnToMenu);


        // Background creation
        SetBackground();


        // Fade Transitions - must be called after every node is added to the root node
        fadeTransitions();

        
        // create menu scene with the current node layout
        this.SchedulingScene = new Scene(RootNode, PROG_UI_A_SceneManager.WindowWidth, PROG_UI_A_SceneManager.WindowHeight);

        // fade all objects before the scene is set
        fadeMenuNodes.play();

    }



    /**
     * ButtonCreation()
     * Description: creates the various buttons used for the scheduling page
     */
    private void ButtonCreation() {

        // Return Home Button
        // ############################################################
        ReturnToMenu = new Button("Return Home");
        EventHandler<ActionEvent> ReturnHome = (ActionEvent e) -> {
            
            // Returns to the home page
            System.out.println("BUTTON CLICK    - SCHEDULE PAGE     - Returning to home page");

            fadeMenuNodes.setOnFinished(event -> {
                PROG_UI_A_Application.SceneManager.MainMenu();
            });

            fadeMenuNodes.play();

        };
        ReturnToMenu.setOnAction(ReturnHome);
        // ############################################################



        // People to schedule reset button
        // ############################################################
        RESET_People = new Button("Reset people to schedule");
        EventHandler<ActionEvent> RESETPEOPLE = (ActionEvent e) -> {
            
            System.out.println("BUTTON CLICK    - SCHEDULE PAGE     - Reset people to schedule");

            // TODO: Add Funcitonality

        };
        RESET_People.setOnAction(RESETPEOPLE);
        // ############################################################



        // number of lists reset button
        // ############################################################
        RESET_ListNumber = new Button("Reset number of lists to output");
        EventHandler<ActionEvent> RESETLISTS = (ActionEvent e) -> {
            
            System.out.println("BUTTON CLICK    - SCHEDULE PAGE     - Reset number of lists to output");

            // TODO: Add Funcitonality

        };
        RESET_ListNumber.setOnAction(RESETLISTS);
        // ############################################################



        // Reset number of days to schedule
        // ############################################################
        RESET_DaySelection = new Button("Reset days to schedule");
        EventHandler<ActionEvent> RESETDAYS = (ActionEvent e) -> {
            
            System.out.println("BUTTON CLICK    - SCHEDULE PAGE     - Reset days to schedule");

            // TODO: Add Funcitonality

        };
        RESET_DaySelection.setOnAction(RESETDAYS);
        // ############################################################



        // Reset specific input times
        // ############################################################
        RESET_TimeInput = new Button("Reset time input");
        EventHandler<ActionEvent> RESETTIMES = (ActionEvent e) -> {
            
            System.out.println("BUTTON CLICK    - SCHEDULE PAGE     - Reset time input");

            // TODO: Add Funcitonality

        };
        RESET_TimeInput.setOnAction(RESETTIMES);
        // ############################################################



        // Reset all preferences
        // ############################################################
        RESET_ALLPreferences = new Button("Reseting all Preferences");
        EventHandler<ActionEvent> RESETALLPREFERENCES = (ActionEvent e) -> {
            
            System.out.println("BUTTON CLICK    - SCHEDULE PAGE     - Reseting all Preferences");

            // TODO: Add Funcitonality

        };
        RESET_ALLPreferences.setOnAction(RESETALLPREFERENCES);
        // ############################################################



        // Calculate Schedule
        // ############################################################
        CALCULATE_Schedule = new Button("Calculate Schedule");
        EventHandler<ActionEvent> CALCULATESCHEDULE = (ActionEvent e) -> {
            
            System.out.println("BUTTON CLICK    - SCHEDULE PAGE     - Calculating schedule");

            // TODO: Add Funcitonality
            // RetrieveSchedule



            // retrieved copy of calculated linked list
            CalculatedScheduleList = new LinkedList<>(ScheduleCalculator.RetrieveSchedule());


        };
        CALCULATE_Schedule.setOnAction(CALCULATESCHEDULE);
        // ############################################################




    } // ButtonCreation()



    /**
     * SchedulingInterface()
     * Description: creates the interface used for adding user preferences to the schedule.
     */
    private void SchedulingInterface() {

        // contains UI elements
        // add nodes here not UIInterfaceNode
        this.VboxUIHolder = new VBox();
        this.VboxUIHolder.setPadding(new Insets(10));


        // UI componenets creation
        // people Input
        InputPeople();

        // List number
        InputListNumber();

        // day preference
        InputDayPreference();

        // time input + no time input checkbox
        InputTimePreference();

        CalculateSchedule();

        this.VboxUIHolder.getChildren().addAll(
            // people Input
            HBoxInputPeople,
            RESET_People,

            // List number
            HBoxInputListNumber,
            RESET_ListNumber,

            // day preference
            HBoxInputDayPreference,
            RESET_DaySelection,

            // time input + no time input checkbox
            HBoxInputTimePreference,
            RESET_TimeInput,

            // reset preferences
            RESET_ALLPreferences,

            // Calculate schedule
            HBoxCalculaeSchedule,
            CALCULATE_Schedule

        );


        // contains vbox which contains UI elements
        UIInterfaceNode = new ScrollPane(this.VboxUIHolder);

        // sets default interface dimensions
        this.UIInterfaceNode.setPrefWidth((PROG_UI_A_SceneManager.WindowWidth / 2) - 80.0);
        this.UIInterfaceNode.setPrefHeight(PROG_UI_A_SceneManager.WindowHeight - 100.0);

        // updates interface dimensions
        this.ApplicationStage.widthProperty().addListener((observed, oldWidth, newWidth) -> {
            UIInterfaceNode.setPrefWidth((newWidth.intValue() / 2) - 80.0);
        });

        this.ApplicationStage.heightProperty().addListener((observed, oldHeight, newHeight) -> {
            UIInterfaceNode.setPrefHeight(newHeight.intValue() - 100.0);
        });

    }


    private void InputPeople() {

        HBoxInputPeople = new HBox();

        // TODO: create interface
    }

    private void InputListNumber() {
        HBoxInputListNumber = new HBox();

        // TODO: create interface

    }

    private void InputDayPreference() {
        HBoxInputDayPreference = new HBox();

        // TODO: create interface
    }

    private void InputTimePreference() {
        HBoxInputTimePreference = new HBox();

        // TODO: create interface
    }

    private void CalculateSchedule() {
        HBoxCalculaeSchedule = new HBox();


        // TODO: create interface
    }



    /**
     * SchedulingDisplay()
     * Description: creates the display board for the finished schedules
     */
    private void SchedulingDisplay() {

    }



    /**
     * SetBackground()
     * Description: Sets the scene background
     */
    private void SetBackground() {

        // Add background gradient
        LinearGradient BackgroundGradient = new LinearGradient(0, 0, 300, 300, false, CycleMethod.NO_CYCLE, 
            new Stop(0, Color.DARKBLUE), new Stop(1, Color.BEIGE)
        );

        BackgroundFill backgroundFill = new BackgroundFill(BackgroundGradient, CornerRadii.EMPTY, Insets.EMPTY);

        RootNode.setBackground(new Background(backgroundFill));

    } // SetBackground()



    /**
     * fadeTransitions()
     * Description: creates the fade transitions for the scene change
     */
    private void fadeTransitions() {

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

    } // fadeTransitions()
    
}
