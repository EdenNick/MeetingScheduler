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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
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

    //flowPane
    private FlowPane    OutputDays;

    // VBox
    private VBox        VboxUIHolder;

    // HBox
    private HBox        HBoxInputPeople;
    private HBox        HBoxInputListNumber;
    private HBox        HBoxInputDayPreference;
    private HBox        HBoxInputTimePreference;
    private HBox        HBoxCalculaeSchedule;

    // VBox
    private VBox        Holder_DayPreference;
    private VBox        Output_DayPreference;
    private VBox        Holder_TimePreference;
    private VBox        Holder_ListAmmount;
    private VBox        Holder_ListPeople;
    private VBox        Holder_Calculate;
    private 

    // combo box
    private ComboBox<String>    userInput_SelectDays;

    // Buttons
    private Button      ReturnToMenu;
    private Button      RESET_People;
    private Button      RESET_ListNumber;
    private Button      RESET_DaySelection;
    private Button      RESET_TimeInput;
    private Button      RESET_ALLPreferences;
    private Button      CALCULATE_Schedule;

    // labels
    private Label       Label_inputDay;
    private Label       Label_OutputDay;
    private Label       Label_inputTime;
    private Label       Label_inputNumber;
    private Label       Label_ScheduleNow;
    private 

    // userInputs
    private TextField   userInput_listAmmount;

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

        RootNode.getStylesheets().add(getClass().getResource("/CSS_Styles.css").toExternalForm());


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
        this.VboxUIHolder = new VBox(10);
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

    } // SchedulingInterface()



    /**
     * InputPeople()
     * Description: node used to input the preferred people for the schedule calculation
     */
    private void InputPeople() {

        // Create new HBox
        this.HBoxInputPeople = new HBox(10);
        this.HBoxInputPeople.getStyleClass().add("UserPreference-box");
        this.HBoxInputPeople.setPrefSize(400.0, 100.0);
        this.HBoxInputPeople.setPadding(new Insets(10));



        // TODO: create interface

        // button to update list
        // scroll through people in the file (id + name)

        // clicking a person adds them to a seperate scroll list that they can also be taken off of



        // VBox for formatting
        Holder_ListPeople = new VBox(10);
        Label Label_inputPeople = new Label("Input the people to schedule");
        Label_inputPeople.getStyleClass().add("default-label");

        this.Holder_ListPeople.getChildren().addAll(Label_inputPeople);
        
        
        // Add to HBox Node
        this.HBoxInputPeople.getChildren().addAll(Holder_ListPeople);

    } // InputPeople()



    /**
     * InputListNumber()
     * Description: receives the input to specificy how many lists should be shown
     */
    private void InputListNumber() {
        
        // Create new HBox
        // ############################################################
        HBoxInputListNumber = new HBox(10);
        HBoxInputListNumber.getStyleClass().add("UserPreference-box");
        HBoxInputListNumber.setPrefSize(400.0, 100.0);
        HBoxInputListNumber.setPadding(new Insets(10));
        // ############################################################
        


        // Input Section
        // ############################################################
        // Enter List Ammount
        this.userInput_listAmmount = new TextField();
        this.userInput_listAmmount.setPromptText("Enter List Ammount");
        this.userInput_listAmmount.setPrefSize(150.0, 40.0);
        this.userInput_listAmmount.setTextFormatter(new TextFormatter<>(change -> {
            
            // User input text
            String TextInput = change.getControlNewText();

            // if th etext is empty accept it
            if (TextInput.isEmpty()) {
                return change;
            }

            // test if the text is a valid int within a valid range
            try {

                int intValue = Integer.parseInt(TextInput);
                
                // TODO: may need to change the max value later dpending on formatting
                if (intValue >= 0 && intValue < 20) {
                    return change;
                }

            } catch (NumberFormatException e) {
                // Invalid Input
            }

            return null;

        }));
        

        //TODO: possibly add label which displays number


        // VBox for formatting
        Holder_ListAmmount = new VBox(10);

        // label
        Label_inputNumber = new Label("Input the total ammount of lists to Display");
        Label_inputNumber.getStyleClass().add("default-label");

        // Add all to the node
        Holder_ListAmmount.getChildren().addAll(Label_inputNumber, userInput_listAmmount);
        // ############################################################
        


        // Add all to the node
        // ############################################################
        HBoxInputListNumber.getChildren().addAll(Holder_ListAmmount);
        // ############################################################

    } // InputListNumber()



    /**
     * InputDayPreference()
     * Description: User input for what preferred days they want in the schedule
     */
    private void InputDayPreference() {

        // Create new HBox
        // ############################################################
        this.HBoxInputDayPreference = new HBox(10);
        this.HBoxInputDayPreference.getStyleClass().add("UserPreference-box");
        this.HBoxInputDayPreference.setPrefSize(400.0, 100.0);
        this.HBoxInputDayPreference.setPadding(new Insets(10));
        // ############################################################



        // Input Section
        // ############################################################
        // VBox for formatting
        this.Holder_DayPreference = new VBox(10);

        // label
        this.Label_inputDay = new Label("Input the days to schedule");
        this.Label_inputDay.getStyleClass().add("default-label");

        // combo box for days of the week, add multiple days
        this.userInput_SelectDays = new ComboBox<>();
        this.userInput_SelectDays.getItems().addAll(PROG_UI_D_DataVariables.WEEKDAYS);
        this.userInput_SelectDays.setPrefSize(150.0, 40.0);

        // Button
        // TODO: add button here


        // Add all to the node
        this.Holder_DayPreference.getChildren().addAll(Label_inputDay, userInput_SelectDays);
        // ############################################################



        // Output Section
        // ############################################################
        this.Output_DayPreference = new VBox(10);

        // label
        this.Label_OutputDay = new Label("Days Selected");
        this.Label_OutputDay.getStyleClass().add("default-label");



        // TODO: add list of selected days - button to add selected days to the list
        this.OutputDays = new FlowPane();



        // Add all to the node
        this.Output_DayPreference.getChildren().addAll(Label_OutputDay);
        // ############################################################



        // Add all to the node
        // ############################################################
        this.HBoxInputDayPreference.getChildren().addAll(Holder_DayPreference, Output_DayPreference);
        // ############################################################

    } // InputDayPreference()



    /**
     * InputTimePreference()
     * Description: user input for the specific times they want scheduled
     */
    private void InputTimePreference() {

        // Create new HBox
        // ############################################################
        this.HBoxInputTimePreference = new HBox(10);
        this.HBoxInputTimePreference.getStyleClass().add("UserPreference-box");
        this.HBoxInputTimePreference.setPrefSize(400.0, 100.0);
        this.HBoxInputTimePreference.setPadding(new Insets(10));
        // ############################################################



        // Input Section
        // ############################################################
        // VBox for formatting
        this.Holder_TimePreference = new VBox(10);
        
        // label
        this.Label_inputTime = new Label("Input the times to schedule");
        this.Label_inputTime.getStyleClass().add("default-label");

        // Add all to the node
        this.Holder_TimePreference.getChildren().addAll(Label_inputTime);
        // ############################################################



        // Output Section
        // ############################################################
        // TODO: create Output Section
        // ############################################################

        // Add all to the node
        // ############################################################
        this.HBoxInputTimePreference.getChildren().addAll(Holder_TimePreference);
        // ############################################################

    } // InputTimePreference()



    /**
     * CalculateSchedule()
     * Description: hold the calculate schedule button
     */
    private void CalculateSchedule() {

        // Create new HBox
        // ############################################################
        this.HBoxCalculaeSchedule = new HBox(10);
        this.HBoxCalculaeSchedule.getStyleClass().add("UserPreference-box");
        this.HBoxCalculaeSchedule.setPrefSize(400.0, 100.0);
        this.HBoxCalculaeSchedule.setPadding(new Insets(10));
        // ############################################################



        // Input Section
        // ############################################################
        // VBox for formatting
        this.Holder_Calculate = new VBox(10);

        // label
        this.Label_ScheduleNow = new Label("Calculate Schedule");
        this.Label_ScheduleNow.getStyleClass().add("default-label");

        // Add all to the node
        this.Holder_Calculate.getChildren().addAll(Label_ScheduleNow);
        // ############################################################


        // Output Section
        // ############################################################
        // TODO: create output interface section
        // ############################################################



        // Add all to the node
        // ############################################################
        this.HBoxCalculaeSchedule.getChildren().addAll(Holder_Calculate);
        // ############################################################

    } // CalculateSchedule()



    /**
     * SchedulingDisplay()
     * Description: creates the display board for the finished schedules
     */
    private void SchedulingDisplay() {

        // TODO: create Display

    } // SchedulingDisplay()



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
