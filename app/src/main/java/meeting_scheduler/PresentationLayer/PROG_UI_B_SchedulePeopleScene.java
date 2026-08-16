/**
 * PROG_UI_B_SchedulePeopleScene()
 * 
 * Description: Application window scene which allows the user to make schedules based off of existing user card information
 * Contains:
 * a. back button       (scene change)
 * b. UI interface allowing user to make schedules    (various buttons, text inputs etc)
 */

// Package  - DO Not Change
// ############################################################
package meeting_scheduler.PresentationLayer;

import java.io.IOException;
// Imports
// ############################################################
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.Collectors;
// javaFX
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
import javafx.scene.text.Text;
//event
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
//geometry
import javafx.geometry.Insets;
// stage
import javafx.stage.Stage;
// util
import javafx.util.Duration;
// data management objects
import meeting_scheduler.BusinessLogiclayer.PROG_BLL_SchedulingCalculation;
import meeting_scheduler.DataAccessLayer.PROG_DAL_A_InfoInput;
import meeting_scheduler.DataAccessLayer.PROG_DAL_A_Schedule;
import meeting_scheduler.DataAccessLayer.PROG_DAL_A_TimeInput;
import meeting_scheduler.DataAccessLayer.PROG_DAL_B_JSONManager;
// ############################################################



public class PROG_UI_B_SchedulePeopleScene {

    // Apllication
    // ############################################################
    // Reference of the application stage used for local operations
    private final Stage         ApplicationStage;
    // Scene
    private Scene               SchedulingScene;
    // Stage width/height
    private double              StageWidth;
    private double              stageHeight;
    // transitions
    private ParallelTransition  fadeMenuNodes;
    private ParallelTransition  UnfadeMenuNodes;
    // ############################################################


    // Nodes
    // ############################################################
    // Root Node
    private AnchorPane  RootNode;
    // scrollpane
    private ScrollPane  UIInterfaceNode;
    //flowPane
    private FlowPane    OutputDays;
    private FlowPane    AddedPeople;
    // VBox
    private VBox        VboxUIHolder;
    // HBox
    private HBox        HBoxInputPeople;
    private HBox        HBoxInputListNumber;
    private HBox        HBoxInputDayPreference;
    private HBox        HBoxInputTimePreference;
    private HBox        HBoxCalculaeSchedule;
    private HBox        UI_AddStartTime;
    private HBox        UI_addEndingTime;
    // VBox
    private VBox        Holder_DayPreference;
    private VBox        Output_DayPreference;
    private VBox        Holder_TimePreference;
    private VBox        Holder_ListAmmount;
    private VBox        Holder_ListPeople;
    private VBox        Holder_Calculate;
    private VBox        Holder_ListOutput;
    private VBox        Output_ListPeople;
    // Buttons
    private Button      ReturnToMenu;
    private Button      RESET_People;
    private Button      RESET_ListNumber;
    private Button      RESET_DaySelection;
    private Button      RESET_TimeInput;
    private Button      RESET_ALLPreferences;
    private Button      CALCULATE_Schedule;
    private Button      Input_SelectedDay;
    private Button      Add_TimePreferences;
    private Button      Input_SelectedNumber;
    private Button      Input_SelectedPeople;
    // labels
    private Label       Label_inputDay;
    private Label       Label_OutputDay;
    private Label       Label_inputTime;
    private Label       Label_inputNumber;
    private Label       Label_ScheduleNow;
    private Label       LabelBeginHour;
    private Label       LabelBeginMinute;
    private Label       LabelEndHour;
    private Label       LabelEndMinute;
    private Label       Label_OutputNumber;
    private Label       Label_AmmountOutput;

    // userInputs
    private TextField   userInput_listAmmount;
    // combo box
    private ComboBox<String>    userInput_SelectDays;
    private ComboBox<String>    ComboBoxPersonList;
    // ############################################################


    // Data manager Objects
    // ############################################################
    // Schedule Calculator
    private final PROG_BLL_SchedulingCalculation    ScheduleCalculator;
    // User Time Input manager
    private final PROG_UI_C_UserTimeInput           Scheduler_UserTimeInputs;
    // Json File
    private final PROG_DAL_B_JSONManager            Scheduler_fileReader;
    // ############################################################
    

    // user Time input graphic variables
    // ############################################################
    private FlowPane                            FlowPane_VBoxDisplay;   // dispalys time inputs

    private LinkedList<PROG_DAL_A_TimeInput>    List_UserTimes;         // List of prefered times for an individual

    private LinkedList<VBox>                    List_VBoxTimeInputs;    // contains a set of user prefered times - used exclusivley for iteration

    private Iterator<VBox>                      RemoveAllVBOXIterator;  // iterator to remove all added userpreferences

    private LinkedList<PROG_DAL_A_InfoInput>    InfoInputPreferences;   // linked list contianing a persons full info to be sent to the json file
    // ############################################################

    // File User Info
    // ############################################################
    private LinkedList<PROG_DAL_A_InfoInput>    FileUserInfo;
    private LinkedList<String>                  PersonList;
    // ############################################################



    // Calculated Schedules
    // ############################################################
    private LinkedList<PROG_DAL_A_Schedule>     CalculatedScheduleList;
    // ############################################################


    /**
     * Constructor class
     */
    public PROG_UI_B_SchedulePeopleScene(Stage stage) {
        this.ApplicationStage = stage;
        
        this.ScheduleCalculator = new PROG_BLL_SchedulingCalculation();

        this.Scheduler_UserTimeInputs = new PROG_UI_C_UserTimeInput();

        this.Scheduler_fileReader = new PROG_DAL_B_JSONManager();
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
        List_UserTimes = new LinkedList<>();

        List_VBoxTimeInputs = new LinkedList<>();

        //RemoveAllVBOXIterator = new Iterator<>() {
            
        InfoInputPreferences = new LinkedList<>(); 




        SchedulingInterface();

        Scheduler_UserTimeInputs.UI_data_construction();


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
            System.out.println("");

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
            System.out.println("");

            // remove all text from the flowpane
            this.AddedPeople.getChildren().clear();

            // clear the comboBox of all items
            this.ComboBoxPersonList.getItems().clear();

            // Reset the comboBox with the full List
            ComboBoxPersonList.getItems().addAll(PersonList);


        };
        RESET_People.setOnAction(RESETPEOPLE);
        // ############################################################



        // number of lists reset button
        // ############################################################
        RESET_ListNumber = new Button("Reset number of lists to output");
        EventHandler<ActionEvent> RESETLISTS = (ActionEvent e) -> {
            
            System.out.println("BUTTON CLICK    - SCHEDULE PAGE     - Reset number of lists to output");
            System.out.println("");

            this.Label_OutputNumber.setText("");

        };
        RESET_ListNumber.setOnAction(RESETLISTS);
        // ############################################################



        // Reset number of days to schedule
        // ############################################################
        RESET_DaySelection = new Button("Reset days to schedule");
        EventHandler<ActionEvent> RESETDAYS = (ActionEvent e) -> {
            
            System.out.println("BUTTON CLICK    - SCHEDULE PAGE     - Reset days to schedule");
            System.out.println("");

            // Resets the flowPanes current list of selected days to an empty string
            this.OutputDays.getChildren().clear();
            this.OutputDays.getChildren().add(new Text(""));

            // Resets the ComboBox Input values to their original state
            this.userInput_SelectDays.getItems().clear();
            this.userInput_SelectDays.getItems().addAll(PROG_UI_D_DataVariables.WEEKDAYS);

            // resets the schedule calcualtor to look through everyday of the week
            ScheduleCalculator.UpdateWeekDays(PROG_UI_D_DataVariables.WEEKDAYS); // input String[]

        };
        RESET_DaySelection.setOnAction(RESETDAYS);
        // ############################################################



        // Reset specific input times
        // ############################################################
        RESET_TimeInput = new Button("Reset time input");
        EventHandler<ActionEvent> RESETTIMES = (ActionEvent e) -> {
            
            System.out.println("BUTTON CLICK    - SCHEDULE PAGE     - Reset time input");
            System.out.println("");

            // Reset List_UserTimes - clearing all elements in the Linked list
            this.List_UserTimes.clear();

            // Reset List_VBoxTimeInputs

            // clear all nodes in each Vbox
            for (VBox vbox : List_VBoxTimeInputs) {
                vbox.getChildren().clear();
            }

            // clear all the elements in the linked list
            this.List_VBoxTimeInputs.clear();

            // Reset FlowPane_VBoxDisplay
            
            // clear all elements in each node fo the flowpane
            for (Node node: FlowPane_VBoxDisplay.getChildren()) {
                if (node instanceof VBox vbox) {
                    vbox.getChildren().clear();
                }

            } // for()

            // Clear all node in the FlowPane
            FlowPane_VBoxDisplay.getChildren().clear();



        };
        RESET_TimeInput.setOnAction(RESETTIMES);
        // ############################################################



        // Add Time info
        // ############################################################
        this.Add_TimePreferences = new Button("Add Info");

        EventHandler<ActionEvent> AddTimeInfo = (ActionEvent e) -> {

            // System Message
            System.out.println("BUTTON CLICK    - CARD MANAGER PAGE - Add User Info");
            System.out.println("");

            // ButtonPressPartialTimeInput() ensures all variables have been input, returns 0 on success
            // TODO: check VBox input size
            if ( (Scheduler_UserTimeInputs.ButtonPressPartialTimeInput() == 0) && (List_VBoxTimeInputs.size() < 4) ){

                // Temp user preference created for clean seperation of object use
                PROG_DAL_A_TimeInput TempUserPreferrence = Scheduler_UserTimeInputs.Return_TimeUserPreference();


                /**
                 * ############################################################
                 * PROG_UI_C_UserTimeInput Scheduler_UserTimeInputs .TimeUserInputGraphic()
                 * 
                 * Inputs user preference to be displayed to the user as well as stored for eventual submission to file
                 * 
                 * PROG_DAL_A_TimeInput TempUserPreferrence         - individual time preference containing day. start and end times
                 * 
                 * LinkedList<PROG_DAL_A_TimeInput> List_UserTimes  - A list of all time preferences a person has. To be submitted to the scheduler for calculation
                 * 
                 * LinkedList<VBox> List_VBoxTimeInputs             - a copied list of all VBoxes stored in the flowpane display. Used to iterate, not to be displayed
                 * 
                 * FlowPane FlowPane_VBoxDisplay                    - A flowpane which displays the various Vboxs that hold user preferences. display only
                 */
                Scheduler_UserTimeInputs.UserInputGraphic(TempUserPreferrence, List_UserTimes, List_VBoxTimeInputs, FlowPane_VBoxDisplay, false);
                // ############################################################


                // garbage Collection
                TempUserPreferrence = null;


                // update the scheduler to the updated list of user times
                ScheduleCalculator.SetSpecificTime(List_UserTimes);

            } else {
                // Do nothing
            }

        };
        this.Add_TimePreferences.setOnAction(AddTimeInfo);
        // ############################################################



        // Reset all preferences
        // ############################################################
        RESET_ALLPreferences = new Button("Reseting all Preferences");
        EventHandler<ActionEvent> RESETALLPREFERENCES = (ActionEvent e) -> {
            
            System.out.println("BUTTON CLICK    - SCHEDULE PAGE     - Reseting all Preferences");
            System.out.println("");

            // Reset Selected Names
            // ############################################################
            // remove all text from the flowpane
            this.AddedPeople.getChildren().clear();

            // clear the comboBox of all items
            this.ComboBoxPersonList.getItems().clear();

            // Reset the comboBox with the full List
            ComboBoxPersonList.getItems().addAll(PersonList);
            // ############################################################



            // Reset List Number
            // ############################################################
            this.Label_OutputNumber.setText("");
            // ############################################################



            // Reset Days
            // ############################################################
            // Resets the flowPanes current list of selected days to an empty string
            this.OutputDays.getChildren().clear();
            this.OutputDays.getChildren().add(new Text(""));
            // Resets the ComboBox Input values to their original state
            this.userInput_SelectDays.getItems().clear();
            this.userInput_SelectDays.getItems().addAll(PROG_UI_D_DataVariables.WEEKDAYS);
            // resets the schedule calcualtor to look through everyday of the week
            ScheduleCalculator.UpdateWeekDays(PROG_UI_D_DataVariables.WEEKDAYS); // input String[]
            // ############################################################



            // Reset Times
            // ############################################################
            // Reset List_UserTimes - clearing all elements in the Linked list
            this.List_UserTimes.clear();
            // Reset List_VBoxTimeInputs
            // clear all nodes in each Vbox
            for (VBox vbox : List_VBoxTimeInputs) {
                vbox.getChildren().clear();
            }
            // clear all the elements in the linked list
            this.List_VBoxTimeInputs.clear();
            // Reset FlowPane_VBoxDisplay
            // clear all elements in each node fo the flowpane
            for (Node node: FlowPane_VBoxDisplay.getChildren()) {
                if (node instanceof VBox vbox) {
                    vbox.getChildren().clear();
                }

            } // for()
            // Clear all node in the FlowPane
            FlowPane_VBoxDisplay.getChildren().clear();
            // ############################################################


        };
        RESET_ALLPreferences.setOnAction(RESETALLPREFERENCES);
        // ############################################################



        // Calculate Schedule
        // ############################################################
        CALCULATE_Schedule = new Button("Calculate Schedule");
        EventHandler<ActionEvent> CALCULATESCHEDULE = (ActionEvent e) -> {
            
            System.out.println("BUTTON CLICK    - SCHEDULE PAGE     - Calculating schedule");
            System.out.println("");

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

        // Node Creation
        // ############################################################
        // Primary Node
        this.HBoxInputPeople = new HBox(10);
        this.HBoxInputPeople.getStyleClass().add("UserPreference-box");
        this.HBoxInputPeople.setPrefSize(600.0, 100.0);
        this.HBoxInputPeople.setPadding(new Insets(10));
        // Secondary Node - Input
        this.Holder_ListPeople = new VBox(10);
        // Secodnary Node - output
        this.Output_ListPeople = new VBox(2);
        this.AddedPeople = new FlowPane();
        this.AddedPeople.setPrefSize(600.0, 100.0);
        this.AddedPeople.getStyleClass().add("flowBox-names");
        this.AddedPeople.setPadding(new Insets(2));
        this.AddedPeople.setHgap(5.0);
        this.AddedPeople.setVgap(2.0);
        // ############################################################



        // Input
        // ############################################################
        Label Label_inputPeople = new Label("Input the people to schedule");
        Label_inputPeople.getStyleClass().add("default-label");

        // comboBox to display the names
        ComboBoxPersonList = new ComboBox<>();


        // button to update list
        // scroll through people in the file (id + name)

        // clicking a person adds them to a seperate scroll list that they can also be taken off of
        try {
            Scheduler_fileReader.RetrieveFromFile();
        } catch (IOException e) {
            // ERROR
            e.printStackTrace();
        }

        // Full list of all Datacards in the file
        FileUserInfo = new LinkedList<>(Scheduler_fileReader.ReturnFile());
        
        // LinkedList of all people in the file showing both ID and full name
        this.PersonList = new LinkedList<>();

        for (PROG_DAL_A_InfoInput FilePerson : FileUserInfo) {
            PersonList.add("| ID: " + FilePerson.EmployeeID + " Name: " + FilePerson.EmployeeName + " |");
        }

        // add list to combobox
        ComboBoxPersonList.getItems().addAll(PersonList);

        // Button
        Input_SelectedPeople = new Button("Input Selected Ammount");
        EventHandler<ActionEvent> INPUTPeople = (ActionEvent e) -> {
            
            System.out.println("BUTTON CLICK    - SCHEDULE PAGE     - Input Selected People");
            System.out.println("");

            
            if ((ComboBoxPersonList.getValue() != null) && (!ComboBoxPersonList.getValue().isBlank())) {

                System.out.println("Check");

                String SelectedPerson = ComboBoxPersonList.getValue();

                ComboBoxPersonList.getItems().remove(SelectedPerson);

                AddedPeople.getChildren().add(new Text(SelectedPerson));

            } // if()


        };
        Input_SelectedPeople.setOnAction(INPUTPeople);
        // ############################################################



        // Output
        // ############################################################
        Label       addedPeopleLabel = new Label("Selected People to Schedule");
        // ############################################################



        // Add all to the nodes
        // ############################################################
        // Secondary Node - output
        this.Output_ListPeople.getChildren().addAll(addedPeopleLabel, AddedPeople);
        // Secondary Node - Input
        this.Holder_ListPeople.getChildren().addAll(Label_inputPeople, ComboBoxPersonList, Input_SelectedPeople);
        // Primary Node
        this.HBoxInputPeople.getChildren().addAll(Holder_ListPeople, Output_ListPeople);
        // ############################################################

    } // InputPeople()



    /**
     * InputListNumber()
     * Description: receives the input to specificy how many lists should be shown
     */
    private void InputListNumber() {
        
        // Node Creation
        // ############################################################
        // Primary Node
        HBoxInputListNumber = new HBox(10);
        HBoxInputListNumber.getStyleClass().add("UserPreference-box");
        HBoxInputListNumber.setPrefSize(600.0, 100.0);
        HBoxInputListNumber.setPadding(new Insets(10));

        // Secondary Node - Input
        Holder_ListAmmount = new VBox(10);

        // Secodnary Node - output
        Holder_ListOutput = new VBox(10);
        // ############################################################
        


        // Input Section
        // ############################################################
        // label
        Label_inputNumber = new Label("Input the total ammount of lists to Display");
        Label_inputNumber.getStyleClass().add("default-label");

        // textfield Input
        this.userInput_listAmmount = new TextField();
        this.userInput_listAmmount.setPromptText("Enter List Ammount");
        this.userInput_listAmmount.setPrefSize(150.0, 40.0);
        this.userInput_listAmmount.setTextFormatter(new TextFormatter<>(change -> {
            
            // User input text
            String TextInput = change.getControlNewText();

            // if the text is empty accept it
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


        // Button
        Input_SelectedNumber = new Button("Input Selected Ammount");
        EventHandler<ActionEvent> INPUTNumber = (ActionEvent e) -> {
            
            System.out.println("BUTTON CLICK    - SCHEDULE PAGE     - Input Selected Ammount");
            System.out.println("");

            
            if ((userInput_listAmmount.getText() != null) && (!userInput_listAmmount.getText().isBlank())) {

                String SelectedAmmount = userInput_listAmmount.getText();

                Label_OutputNumber.setText(SelectedAmmount);

            } // if()


        };
        Input_SelectedNumber.setOnAction(INPUTNumber);

        // ############################################################
        


        // Output
        // ############################################################
        Label_AmmountOutput = new Label("Ammount of schedules to be displayed");

        // Stores the Ammount input variable
        Label_OutputNumber = new Label("");
        // ############################################################


        // Add all to the nodes
        // ############################################################
        // Secondary Node - output
        Holder_ListOutput.getChildren().addAll(Label_AmmountOutput, Label_OutputNumber);
        // secondary Node - Input
        Holder_ListAmmount.getChildren().addAll(Label_inputNumber, userInput_listAmmount, Input_SelectedNumber);

        // Primary Node
        HBoxInputListNumber.getChildren().addAll(Holder_ListAmmount, Holder_ListOutput);
        // ############################################################

    } // InputListNumber()



    /**
     * InputDayPreference()
     * Description: User input for what preferred days they want in the schedule
     */
    private void InputDayPreference() {

        // Node construction
        // ############################################################
        // HBox - primary Node
        this.HBoxInputDayPreference = new HBox(10);
        this.HBoxInputDayPreference.getStyleClass().add("UserPreference-box");
        this.HBoxInputDayPreference.setPrefSize(600.0, 100.0);
        this.HBoxInputDayPreference.setPadding(new Insets(10));

        // VBox - secondary node - input
        this.Holder_DayPreference = new VBox(10);

        // VBox - secondary node - output
        this.Output_DayPreference = new VBox(10);
        // ############################################################



        // Input Section
        // ############################################################
        // label
        this.Label_inputDay = new Label("Input the days to schedule");
        this.Label_inputDay.getStyleClass().add("default-label");

        // combo box for days of the week, add multiple days
        this.userInput_SelectDays = new ComboBox<>();
        this.userInput_SelectDays.getItems().addAll(PROG_UI_D_DataVariables.WEEKDAYS);
        this.userInput_SelectDays.setPrefSize(150.0, 40.0);
        // ############################################################



        // Output Section
        // ############################################################
        // label
        this.Label_OutputDay = new Label("Days Selected");
        this.Label_OutputDay.getStyleClass().add("default-label");

        // flowPane to hold and display text
        this.OutputDays = new FlowPane();
        this.OutputDays.setPrefSize(150.0, 40.0);
        // 
        this.OutputDays.getChildren().add(new Text(""));
        this.OutputDays.getChildren().addListener((javafx.collections.ListChangeListener<Node>) change -> {

            // ensures OutputDays has enough nodes to complete all operations without index out of bound errors
            // Also exists due to reset button activating this listner -> do not remove the if statmenet
            if (this.OutputDays.getChildren().size() > 1) {


                // Incoming input, the day to be added to the list - should always be the second node (index 1)
                String NewDay = ((Text) this.OutputDays.getChildren().get(1)).getText();

                // exisitng days, days to be kept in the list - should always be the first node (index 0)
                String[] CollectedDays = ((Text) this.OutputDays.getChildren().get(0)).getText().split(" ");

                // NewDayList, the new list which contains all the days from the existing node and the incoming input
                String[] NewDayList = PROG_UI_D_DataVariables.WEEKDAYS.clone();
                
                boolean hasDay;
                int NewDayList_index = 0;

                // iterate through every day of the week
                for (String WeekDay: PROG_UI_D_DataVariables.WEEKDAYS) {
                    
                    hasDay = false;

                    // for each day see if the current string[] of collected days has it or if the new one does
                    for (int collectedDays_index = 0; collectedDays_index < CollectedDays.length; collectedDays_index++) {

                        // if either are true set has day to true
                        if ( (WeekDay.equals(CollectedDays[collectedDays_index])) || (WeekDay.equals(NewDay)) ) {
                            hasDay = true;
                            break;
                        }

                    } // for()

                    if (hasDay == true) {
                        // do nothing
                    } else {
                        // if the day is not contained in either variable, set the relevant day to null in the new list
                        NewDayList[NewDayList_index] = null;
                    }

                    // incrment the new Day List index
                    NewDayList_index++;

                } // for()

                // new text to be set to the flowPane
                String NewTextString = Arrays.stream(NewDayList).filter(Objects::nonNull).collect(Collectors.joining(" "));

                // remove incoming node
                this.OutputDays.getChildren().remove(1);

                // set existing node to new text
                Text NewTextNode = (Text) this.OutputDays.getChildren().get(0);
                NewTextNode.setText(NewTextString);

                // Update scheduleing Object with new list of days
                ScheduleCalculator.UpdateWeekDays(NewTextString.split(" ")); // input String[]

            } // if()
        });
        // ############################################################



        // Buttons
        // ############################################################
        // Input Selected Day - must be called after userInput_SelectDays and OutputDays construction
        Input_SelectedDay = new Button("Input Selected Day");
        EventHandler<ActionEvent> INPUTDAY = (ActionEvent e) -> {
            
            System.out.println("BUTTON CLICK    - SCHEDULE PAGE     - Input Selected Day");
            System.out.println("");

            
            if ((userInput_SelectDays.getValue() != null) && (!userInput_SelectDays.getValue().isBlank())) {

                String SelectedDay = userInput_SelectDays.getValue();

                userInput_SelectDays.getItems().remove(SelectedDay);

                OutputDays.getChildren().add(new Text(SelectedDay));

            } // if()


        };
        Input_SelectedDay.setOnAction(INPUTDAY);
        // ############################################################



        // Add all to the nodes
        // ############################################################
        // Secondary Node - Output
        this.Output_DayPreference.getChildren().addAll(Label_OutputDay, OutputDays);

        // Secondary Node - Input
        this.Holder_DayPreference.getChildren().addAll(Label_inputDay, userInput_SelectDays, Input_SelectedDay);

        // Primary Node
        this.HBoxInputDayPreference.getChildren().addAll(Holder_DayPreference, Output_DayPreference);
        // ############################################################

    } // InputDayPreference()



    /**
     * InputTimePreference()
     * Description: user input for the specific times they want scheduled
     */
    private void InputTimePreference() {

        // Node construction
        // ############################################################
        // Primary Node
        this.HBoxInputTimePreference = new HBox(10);
        this.HBoxInputTimePreference.getStyleClass().add("UserPreference-box");
        this.HBoxInputTimePreference.setPrefSize(620.0, 220.0);
        this.HBoxInputTimePreference.setPadding(new Insets(10));

        // Secodnary Node - input
        this.Holder_TimePreference = new VBox(10);
        this.Holder_TimePreference.setPrefSize(300.0, 200.0);

        // Secondary Node - output
        this.FlowPane_VBoxDisplay = new FlowPane();
        this.FlowPane_VBoxDisplay.getStyleClass().add("UserPreference-box");
        this.FlowPane_VBoxDisplay.setPrefSize(270.0, 190.0);
        this.FlowPane_VBoxDisplay.setPadding(new Insets(10));
        this.FlowPane_VBoxDisplay.setHgap(5.0);
        this.FlowPane_VBoxDisplay.setVgap(5.0);
        // ############################################################



        // Input Section
        // ############################################################
        // label
        this.Label_inputTime = new Label("Input the times to schedule");
        this.Label_inputTime.getStyleClass().add("default-label");

        this.LabelBeginHour = new Label("Hour");
        this.Label_inputTime.getStyleClass().add("default-label");

        this.LabelBeginMinute = new Label("Minute");
        this.Label_inputTime.getStyleClass().add("default-label");

        this.LabelEndHour = new Label("Hour");
        this.Label_inputTime.getStyleClass().add("default-label");

        this.LabelEndMinute = new Label("Minute");
        this.Label_inputTime.getStyleClass().add("default-label");


        // HBox for beginning Hour/Min
        this.UI_AddStartTime = new HBox(10);
        //AddStartTime.setPrefSize(300.0, 500.0);
        this.UI_AddStartTime.setPadding(new Insets(10));
        this.UI_AddStartTime.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: Black;" +
            "-fx-border-width: 2;" +
            "-fx-border-radius: 5;"
        );
        this.UI_AddStartTime.getChildren().addAll(

            LabelBeginHour,
            Scheduler_UserTimeInputs.Return_Hour_Begin(),

            LabelBeginMinute,
            Scheduler_UserTimeInputs.Return_Minute_Begin(),

            Scheduler_UserTimeInputs.Return_AMPM_StartTime()

        );
        


        // HBox for Ending Hour/Min
        // ############################################################
        this.UI_addEndingTime = new HBox(10);
        //AddStartTime.setPrefSize(200.0, 400.0);
        this.UI_addEndingTime.setPadding(new Insets(10));
        this.UI_addEndingTime.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: Black;" +
            "-fx-border-width: 2;" +
            "-fx-border-radius: 5;"
        );

        this.UI_addEndingTime.getChildren().addAll(

            LabelEndHour,
            Scheduler_UserTimeInputs.Return_Hour_End(),

            LabelEndMinute,
            Scheduler_UserTimeInputs.Return_Minute_End(),

            Scheduler_UserTimeInputs.Return_AMPM_EndTime()

        );
        // ############################################################

        // ############################################################



        // Output Section
        // ############################################################

        // ############################################################



        // Add all to the nodes
        // ############################################################

        // secondary node - output


        // seconary node - input
        this.Holder_TimePreference.getChildren().addAll(Label_inputTime, UI_AddStartTime, UI_addEndingTime, Add_TimePreferences);

        // primary node
        this.HBoxInputTimePreference.getChildren().addAll(Holder_TimePreference, FlowPane_VBoxDisplay);
        // ############################################################

    } // InputTimePreference()



    /**
     * CalculateSchedule()
     * Description: hold the calculate schedule button
     */
    private void CalculateSchedule() {

        // Node Creation
        // ############################################################
        // Primary Node
        this.HBoxCalculaeSchedule = new HBox(10);
        this.HBoxCalculaeSchedule.getStyleClass().add("UserPreference-box");
        this.HBoxCalculaeSchedule.setPrefSize(500.0, 100.0);
        this.HBoxCalculaeSchedule.setPadding(new Insets(10));

        // Secondary Node - input
        this.Holder_Calculate = new VBox(10);

        // Secondary Node - output

        // ############################################################



        // Input Section
        // ############################################################
        // label
        this.Label_ScheduleNow = new Label("Calculate Schedule");
        this.Label_ScheduleNow.getStyleClass().add("default-label");
        // ############################################################


        // Output Section
        // ############################################################
        // TODO: create output interface section
        // ############################################################



        // Add all to the nodes
        // ############################################################

        // secondary node - output

        
        // seconary node - input
        this.Holder_Calculate.getChildren().addAll(Label_ScheduleNow);

        // primary node
        this.HBoxCalculaeSchedule.getChildren().addAll(Holder_Calculate);
        // ############################################################

    } // CalculateSchedule()


    


    /**
     * SchedulingDisplay()
     * Description: creates the display board for the finished schedules
     */
    private void SchedulingDisplay() {

        // TODO: create Display


        // scroll pane

        // for each schedule

        // schedule should be a rectanglar box containg from left to right
        // - full list boolean - day - time frame - list of people

        

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
