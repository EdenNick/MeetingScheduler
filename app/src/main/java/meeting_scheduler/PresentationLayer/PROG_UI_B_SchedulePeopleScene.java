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
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
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
import javafx.geometry.Pos;
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
// System messages
import meeting_scheduler.DataAccessLayer.PROG_DAL_D_SystemMessages;
// ############################################################

// TODO: finsih system messages

public class PROG_UI_B_SchedulePeopleScene {

    // Apllication
    // ############################################################
    // Reference of the application stage used for local operations
    private final   Stage               ApplicationStage;
    // Scene
    private         Scene               SchedulingScene;
    // Stage width/height
    private         double              StageWidth;
    private         double              stageHeight;
    // transitions
    private         ParallelTransition  fadeMenuNodes;
    private         ParallelTransition  UnfadeMenuNodes;
    // ############################################################


    // Nodes
    // ############################################################
    // Root Node
    private AnchorPane  Schedule_RootNode;
    // Primary UI ScrollPanes
    private ScrollPane  UIInput_FullUIHolder_ScrollPane;
    private ScrollPane  UIOutput_FullUIHolder_scrollPane;
    
    // FUll UI for user input and ouput
    private VBox        UIInput_FullUI_VBOX;
    private VBox        UIOutput_FullUI_VBOX;

    // Ui for each user input section
    private VBox        UIInput_PeopleUI_VBOX;
    private VBox        UIInput_ListUI_VBOX;
    private VBox        UIInput_DayUI_VBOX;
    private VBox        UIInput_TimeUI_VBOX;
    private VBox        UIInput_CalculateUI_VBOX;
    // ############################################################

    // Buttons
    // ############################################################
    private Button      RETURN_ToMenu;
    private Button      RESET_People;
    private Button      RESET_ListNumber;
    private Button      RESET_DaySelection;
    private Button      RESET_TimeInput;
    private Button      RESET_ALLPreferences;
    private Button      INPUT_SelectedDay;
    private Button      INPUT_TimePreferences;
    private Button      INPUT_SelectedNumber;
    private Button      Input_SelectedPeople;
    private Button      REMOVELastPerson;
    private Button      CALCULATE_Schedule;
    private Button      CLEAR_Schedules;
    // ############################################################


    // Action events
    // ############################################################
    private EventHandler<ActionEvent> EVENT_RETURN_HOME         = null;
    private EventHandler<ActionEvent> EVENT_RESET_People        = null;
    private EventHandler<ActionEvent> EVENT_RESET_List          = null;
    private EventHandler<ActionEvent> EVENT_RESET_days          = null;
    private EventHandler<ActionEvent> EVENT_RESET_Times         = null;
    private EventHandler<ActionEvent> EVENT_ADD_timeInput       = null;
    private EventHandler<ActionEvent> EVENT_ADD_People          = null;
    private EventHandler<ActionEvent> EVENT_ADD_List            = null;
    private EventHandler<ActionEvent> EVENT_ADD_Days            = null;
    private EventHandler<ActionEvent> EVENT_REMOVE_Person       = null;
    private EventHandler<ActionEvent> EVENT_CALCULATE_Schedule  = null;
    private EventHandler<ActionEvent> EVENT_CLEAR_schedule      = null;
    // ############################################################


    // File User Info
    // ############################################################
    private LinkedList<PROG_DAL_A_InfoInput>    FileUserInfo;
    private LinkedList<String>                  PersonList;
    private LinkedList<PROG_DAL_A_InfoInput>    FilePeople;
    // ############################################################


    // Data manager Objects
    // ############################################################
    // Schedule Calculator
    private final PROG_BLL_SchedulingCalculation    ScheduleCalculator;
    // User Time Input manager
    private final PROG_UI_C_UserTimeInput           Scheduler_UserTimeInputs;
    // Json File manager
    private final PROG_DAL_B_JSONManager            Scheduler_fileReader;
    // ############################################################
    

    // user input nodes and variables
    // ############################################################
    // People to schedule input
    private ScrollPane                          ScrollAddedPeople;  // holds the flowpane of scheduledpeople so it can be scrolled
    private FlowPane                            AddedPeople;            // list of people the user selected to schedule
    private ComboBox<String>                    Selectable_PersonList;     // Contains the list of all people that can be added to the schedule
    // People to schedule output
    private LinkedList<String>                  SCHEDULE_IDS;          // list of selected ids the user wants scheduled

    // list ammount input
    private Label                               Label_OutputNumber;     // label containing the selected list ammount for visual display
    private TextField                           userInput_listAmmount;  // user input for list ammount
    // list ammount output
    private int                                 SCHEDULE_LIST; // int ammount stored for actual calculations
    private int                                 PrintedLists;

    // Day input
    private ComboBox<String>                    userInput_SelectDays;   // combobox for the user to select which days they want to scheudle on
    private FlowPane                            OutputDays;             // flowpane ontains the input days the user wants selected
    // day output
    private String[]                            SCHEDULE_DAYS;


    // time input 
    private FlowPane                            FlowPane_VBoxDisplay;   // dispalys time inputs
    private LinkedList<VBox>                    List_VBoxTimeInputs;    // contains a set of user prefered times - used exclusivley for iteration
    // time output
    private LinkedList<PROG_DAL_A_TimeInput>    SCHEDULE_TIMES;         // List of prefered times for an individual
    // ############################################################


    // Calculated Schedules
    // ############################################################
    private LinkedList<PROG_DAL_A_Schedule>     CalculatedScheduleList;
    private ObservableList<PROG_DAL_A_Schedule> Schedules;
    // ############################################################


    /**
     * Constructor class
     */
    public PROG_UI_B_SchedulePeopleScene(Stage stage) {

        // Primary Objects
        // ############################################################
        // application stage    - Obejct containing all contents of the page, effectivley the applciaiton window
        this.ApplicationStage           = stage;
        
        // Schedule Calculator  - Object used to calculate viable schedules based off of input user preferences
        this.ScheduleCalculator         = new PROG_BLL_SchedulingCalculation();

        // User time inputs     - Object which is used to create the necessary input ui for user time inputs, verifies correct input
        // contains methods used to store and dispaly this information. In this case it is used to input correct times to create a schedule
        this.Scheduler_UserTimeInputs   = new PROG_UI_C_UserTimeInput();

        // Json file Reader     - Object which can access the relevant Json file to retireve user info. 
        // Used to retrieve current user preferences to create a schedule
        this.Scheduler_fileReader       = new PROG_DAL_B_JSONManager();
        // ############################################################


        // calculating schedules
        // ############################################################
        // observablelist to hold each schedule
        this.Schedules                  = FXCollections.observableArrayList();
        // holds calculated list of schedules before being passed to the Schedules observable lsit
        this.CalculatedScheduleList     = new LinkedList<>();
        // ############################################################


        // user Inputs for the schedule calculation
        // ############################################################
        // linked list of current user inputed people they want to include in the schedule(s) - used to update the schedule calculator
        this.SCHEDULE_IDS       = new LinkedList<>();
        // ammount fo schedules the user wants displayed
        this.SCHEDULE_LIST      = PROG_UI_D_DataVariables.MAXListAmmount;
        this.PrintedLists       = 0;
        // String[] containg all user selected days
        this.SCHEDULE_DAYS      = new String[7];
        // holds a list of prefered times input by the user - max 4
        this.SCHEDULE_TIMES     = new LinkedList<>();
        // ############################################################

            
    } // PROG_UI_B_SchedulePeopleScene(Stage stage)


    
    /**
     * changetoSchedulingScene()
     * Description: Public method meant to be called outside the class in order to set the scene to the Scheduling scene
     */
    public void changetoSchedulingScene() {

        // unfades nodes
        this.UnfadeMenuNodes.play();

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
        
    } // changetoSchedulingScene



    /**
     * ConstructSchedulingScene()
     * Description: Performs the necessary operations in order to build the various nodes/components of the stage.
     */
    public void ConstructSchedulingScene() {


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


        
        // Root Node creation
        // ############################################################
        // Create Node
        Schedule_RootNode = new AnchorPane();
        // get the CSS styles for the sub-nodes
        Schedule_RootNode.getStylesheets().add(getClass().getResource(PROG_UI_D_DataVariables.CSS_Styles).toExternalForm());
        // ############################################################



        // General Node creation    - Creates the primary nodes used for the UI
        // ############################################################
        NodeCreation();
        // ############################################################



        // Event Handler Creation   - Creates the various event handlers used throughout the scene
        // ############################################################
        EventHandlerCreation();
        // ############################################################



        // Button creation          - Creates the relevant Buttons used throughout the scene and assigns the relevant events to them
        // ############################################################
        ButtonCreation();
        // ############################################################



        // UI Creation              - Creates the UI layout and other inputs for the user to input preferences and create schedules
        // ############################################################
        SchedulingInterface();
        Scheduler_UserTimeInputs.UI_data_construction();
        // ############################################################


        // create Schedule Display  = Creates the output UI displaying created schedules for the user
        // ############################################################
        SchedulingDisplay();
        // ############################################################


        // Set Node position within Root Node
        // ############################################################
        // Root Node - set return home button position
        AnchorPane.setBottomAnchor  (RETURN_ToMenu,                     PROG_UI_D_DataVariables.SCHEDULE_Return_BottomAnchor);
        AnchorPane.setRightAnchor   (RETURN_ToMenu,                     PROG_UI_D_DataVariables.SCHEDULE_Return_RightAnchor);

        // Root Node - set UI interface position
        AnchorPane.setTopAnchor     (UIInput_FullUIHolder_ScrollPane,   PROG_UI_D_DataVariables.SCHEDULE_UIInput_TopAnchor);
        AnchorPane.setLeftAnchor    (UIInput_FullUIHolder_ScrollPane,   PROG_UI_D_DataVariables.SCHEDULE_UIInput_LeftAnchor);

        // Root Node - set Schedule Display position
        AnchorPane.setTopAnchor     (UIOutput_FullUIHolder_scrollPane,  PROG_UI_D_DataVariables.SCHEDULE_UIOutput_TopAnchor);
        AnchorPane.setRightAnchor   (UIOutput_FullUIHolder_scrollPane,  PROG_UI_D_DataVariables.SCHEDULE_UIOutput_RightAnchor);
        // ############################################################

        // Add UI to each root node
        // ############################################################
        Schedule_RootNode.getChildren().addAll(UIInput_FullUIHolder_ScrollPane, UIOutput_FullUIHolder_scrollPane,  RETURN_ToMenu);
        // ############################################################


        // Background creation and application to the root node
        // ############################################################
        SetBackground();
        // ############################################################


        // Fade Transitions - must be called after every node is added to the root node
        fadeTransitions();
        // ############################################################

        
        // Scene creation to be set to the current scene
        // ############################################################
        this.SchedulingScene = new Scene(Schedule_RootNode, PROG_UI_A_SceneManager.WindowWidth, PROG_UI_A_SceneManager.WindowHeight);
        // ############################################################


        // fade all objects before the scene is set
        // ############################################################
        fadeMenuNodes.play();
        // ############################################################

    }


    /**
     * NodeCreation()
     * Descrtiption:
     * creates the various nodes used throughout the scheduling scene.
     */
    private void NodeCreation() {


        // UIInput_FullUIHolder_ScrollPane
        // ############################################################
        
        // Contains All UI Input nodes and elements
        // ############################################################
        this.UIInput_FullUI_VBOX = new VBox(PROG_UI_D_DataVariables.SCHEDULE_UIINPUT_PREFSpacing);
        this.UIInput_FullUI_VBOX.getStyleClass().add("ScheduleUI-Base");
        this.UIInput_FullUI_VBOX.setPadding(new Insets(PROG_UI_D_DataVariables.SCHEDULE_UIINPUT_PREFInsets));
        // ############################################################



        // Contains All UI Input for selecting People
        // ############################################################
        this.UIInput_PeopleUI_VBOX = new VBox(PROG_UI_D_DataVariables.SCHEDULE_UIINPUT_PREFSpacing);
        this.UIInput_PeopleUI_VBOX.getStyleClass().add("ScheduleInputUI-mainBoxes");
        this.UIInput_PeopleUI_VBOX.setPrefSize(PROG_UI_D_DataVariables.SCHEDULE_UIINPUT_PREFWidth1, PROG_UI_D_DataVariables.SCHEDULE_UIINPUT_PREFHeight1);
        this.UIInput_PeopleUI_VBOX.setPadding(new Insets(PROG_UI_D_DataVariables.SCHEDULE_UIINPUT_PREFInsets));
        // ############################################################



        // Contains all UI Inputs for selected the schedule List ammount
        // ############################################################
        this.UIInput_ListUI_VBOX = new VBox(PROG_UI_D_DataVariables.SCHEDULE_UIINPUT_PREFSpacing);
        this.UIInput_ListUI_VBOX.getStyleClass().add("ScheduleInputUI-mainBoxes");
        this.UIInput_ListUI_VBOX.setPrefSize(PROG_UI_D_DataVariables.SCHEDULE_UIINPUT_PREFWidth1, PROG_UI_D_DataVariables.SCHEDULE_UIINPUT_PREFHeight1);
        this.UIInput_ListUI_VBOX.setPadding(new Insets(PROG_UI_D_DataVariables.SCHEDULE_UIINPUT_PREFInsets));
        // ############################################################



        // Contains all UI inputs for selecting days for the schedule
        // ############################################################
        this.UIInput_DayUI_VBOX = new VBox(PROG_UI_D_DataVariables.SCHEDULE_UIINPUT_PREFSpacing);
        this.UIInput_DayUI_VBOX.getStyleClass().add("ScheduleInputUI-mainBoxes");
        this.UIInput_DayUI_VBOX.setPrefSize(PROG_UI_D_DataVariables.SCHEDULE_UIINPUT_PREFWidth1, PROG_UI_D_DataVariables.SCHEDULE_UIINPUT_PREFHeight1);
        this.UIInput_DayUI_VBOX.setPadding(new Insets(PROG_UI_D_DataVariables.SCHEDULE_UIINPUT_PREFInsets));
        // ############################################################



        // Contains all UI nodes for selecting times for the scheudle
        // ############################################################
        this.UIInput_TimeUI_VBOX = new VBox(PROG_UI_D_DataVariables.SCHEDULE_UIINPUT_PREFSpacing);
        this.UIInput_TimeUI_VBOX.getStyleClass().add("ScheduleInputUI-mainBoxes");
        this.UIInput_TimeUI_VBOX.setPrefSize(PROG_UI_D_DataVariables.SCHEDULE_UIINPUT_PREFWidth1, PROG_UI_D_DataVariables.SCHEDULE_UIINPUT_PREFHeight1);
        this.UIInput_TimeUI_VBOX.setPadding(new Insets(PROG_UI_D_DataVariables.SCHEDULE_UIINPUT_PREFInsets));
        // ############################################################



        // Contains all UI nodes for calculating the schedule
        // ############################################################
        this.UIInput_CalculateUI_VBOX = new VBox(PROG_UI_D_DataVariables.SCHEDULE_UIINPUT_PREFSpacing);
        this.UIInput_CalculateUI_VBOX.getStyleClass().add("ScheduleInputUI-mainBoxes");
        this.UIInput_CalculateUI_VBOX.setPrefSize(PROG_UI_D_DataVariables.SCHEDULE_UIINPUT_PREFWidth1, PROG_UI_D_DataVariables.SCHEDULE_UIINPUT_PREFHeight1);
        this.UIInput_CalculateUI_VBOX.setPadding(new Insets(PROG_UI_D_DataVariables.SCHEDULE_UIINPUT_PREFInsets));
        // ############################################################





        // UIOutput_FullUIHolder_scrollPane
        // ############################################################

        // Contains all schedule nodes
        // ############################################################
        this.UIOutput_FullUI_VBOX = new VBox(PROG_UI_D_DataVariables.SCHEDULE_UIOUTPUT_PREFSpacing);
        this.UIOutput_FullUI_VBOX.getStyleClass().add("ScheduleUI-Base");
        this.UIOutput_FullUI_VBOX.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        this.UIOutput_FullUI_VBOX.setPadding(new Insets(PROG_UI_D_DataVariables.SCHEDULE_UIOUTPUT_PREFInsets));
        // ############################################################
    }



    /**
     * EventHandlerCreation()
     * Description: creates various events used within this scene.
     */
    private void EventHandlerCreation() {


        // return to the Home page
        // ############################################################
        this.EVENT_RETURN_HOME = event -> {
            // Returns to the home page
            System.out.println(PROG_DAL_D_SystemMessages.BUTTON_Schedule_returnHome);
            fadeMenuNodes.play();
        };
        // ############################################################



        // Resets the number of people used in the scheudle
        // ############################################################
        this.EVENT_RESET_People = event -> {
            
            System.out.println(PROG_DAL_D_SystemMessages.BUTTON_Schedule_ResetPeople);

            // remove all text from the flowpane
            this.AddedPeople.getChildren().clear();

            // clear the comboBox of all items
            this.Selectable_PersonList.getItems().clear();

            // Reset the comboBox with the full List
            this.Selectable_PersonList.getItems().addAll(PersonList);

            // Reset the schedule calculator
            ScheduleCalculator.ResetPeopleToSchedule();

            // reset the linked list of user ids
            this.SCHEDULE_IDS = new LinkedList<>();

        };
        // ############################################################



        // Add people to schedule
        // ############################################################
        EVENT_ADD_People = event -> {
            
            System.out.println(PROG_DAL_D_SystemMessages.BUTTON_Schedule_InputPeople);

            
            if ((Selectable_PersonList.getValue() != null) && (!Selectable_PersonList.getValue().isBlank())) {

                // gets the person selected from the combobox
                String SelectedPerson = Selectable_PersonList.getValue();

                // removes that person from the combobox
                this.Selectable_PersonList.getItems().remove(SelectedPerson);

                // adds them to the seleted list of people
                this.AddedPeople.getChildren().add(new Text(SelectedPerson));

                // gets the user if od the selected person
                String[] getID = SelectedPerson.split("\\s+");

                // adds the id to the list of people to schedule
                SCHEDULE_IDS.add(getID[1]); // add id as string
                
                // updates the scheduler with the updated list
                ScheduleCalculator.UpdatePeopleToSchedule(SCHEDULE_IDS);

            } // if()


        };
        // ############################################################



        // Removes the last person added to the schedule
        // ############################################################
        this.EVENT_REMOVE_Person = event -> {
            
            System.out.println(PROG_DAL_D_SystemMessages.BUTTON_Schedule_RemovePerson);

            // remove all text from the flowpane
            if ((this.AddedPeople.getChildren().size()) > 0) {

                // gets the text of the last person added to the flowpane
                String LastPerson = ((Text) this.AddedPeople.getChildren().getLast()).getText();

                // removes last person from the flowpane
                this.AddedPeople.getChildren().removeLast();

                // Reset the comboBox with the full List
                this.Selectable_PersonList.getItems().add(LastPerson);

                // if the list of people is more than 1 remove them if less, reset the list
                if (SCHEDULE_IDS.size() > 1) {

                    this.SCHEDULE_IDS.removeLast();
                    ScheduleCalculator.UpdatePeopleToSchedule(SCHEDULE_IDS);

                } else {

                    ScheduleCalculator.ResetPeopleToSchedule();
                    this.SCHEDULE_IDS = new LinkedList<>();
                }

            } // if ((this.AddedPeople.getChildren().size()) > 0)

        };
        // ############################################################



        // Reset The number of schedules the user wants displayed at most
        // ############################################################
        this.EVENT_RESET_List = event -> {
            
            System.out.println(PROG_DAL_D_SystemMessages.BUTTON_Schedule_ResetListNum);
            
            // Text value set to nothing
            this.Label_OutputNumber.setText(PROG_UI_D_DataVariables.EmptyText);

        };
        // ############################################################



        // add list number the user wants
        // ############################################################
        this.EVENT_ADD_List = event -> {
            
            System.out.println(PROG_DAL_D_SystemMessages.BUTTON_Schedule_InputListNum);

            if ((userInput_listAmmount.getText() != null) && (!userInput_listAmmount.getText().isBlank())) {

                String SelectedAmmount = userInput_listAmmount.getText();

                Label_OutputNumber.setText(SelectedAmmount);

                SCHEDULE_LIST = Integer.parseInt(SelectedAmmount);

            } // if()

        };
        // ############################################################



        // Resets the selected days for the schedule
        // ############################################################
        this.EVENT_RESET_days = event -> {
            
            System.out.println(PROG_DAL_D_SystemMessages.BUTTON_Schedule_ResetDays);

            // Resets the flowPanes current list of selected days to an empty string
            this.OutputDays.getChildren().clear();
            this.OutputDays.getChildren().add(new Text(""));

            // Resets the ComboBox Input values to their original state
            this.userInput_SelectDays.getItems().clear();
            this.userInput_SelectDays.getItems().addAll(PROG_UI_D_DataVariables.WEEKDAYS.clone());

            // resets the list of user selected days
            this.SCHEDULE_DAYS  = new String[7];

            // resets the schedule calcualtor to look through everyday of the week
            ScheduleCalculator.UpdateWeekDays(PROG_UI_D_DataVariables.WEEKDAYS.clone()); // input String[]

        };
        // ############################################################



        // Adds a user selected day
        // ############################################################
        this.EVENT_ADD_Days = event -> {
            
            System.out.println(PROG_DAL_D_SystemMessages.BUTTON_Schedule_InputDays);
            
            if ((userInput_SelectDays.getValue() != null) && (!userInput_SelectDays.getValue().isBlank())) {

                // retireves the selected user value
                String UserInput_day = userInput_SelectDays.getValue();

                // removes the selected day from the combobox
                this.userInput_SelectDays.getItems().remove(UserInput_day);


                // adds the day to the string[] containing all selected user days
                for (int WeekdayIndex = 0; WeekdayIndex < PROG_UI_D_DataVariables.WEEKDAYS.length; WeekdayIndex++) {

                    if (UserInput_day.equals(PROG_UI_D_DataVariables.WEEKDAYS[WeekdayIndex])) {
                        this.SCHEDULE_DAYS[WeekdayIndex] = UserInput_day;
                    }

                } // for (int WeekdayIndex = 0; WeekdayIndex < PROG_UI_D_DataVariables.WEEKDAYS.length; WeekdayIndex++)
                
                

                // removes the null values from the string[] 
                String[] SelectedDays_SchedulerInput = Arrays.stream(this.SCHEDULE_DAYS).filter(Objects::nonNull).toArray(String[]::new);

                // string to be displayed as text in the flowpane
                String DisplayText_days = String.join(" - ", SelectedDays_SchedulerInput);

                // sets the output text in the flowpane
                OutputDays.getChildren().set(0, (new Text(DisplayText_days)) );

                // scheduler is updated with the new list of selected days
                ScheduleCalculator.UpdateWeekDays(SelectedDays_SchedulerInput);

            } // if()

        };
        // ############################################################


        // Resets the time inputs from the user
        // ############################################################
        this.EVENT_RESET_Times = event -> {
            
            System.out.println(PROG_DAL_D_SystemMessages.BUTTON_Schedule_ResetTime);

            // Reset List_UserTimes - clearing all elements in the Linked list
            this.SCHEDULE_TIMES.clear();

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
        // ############################################################



        // Add time input from the user
        // ############################################################
        this.EVENT_ADD_timeInput = event -> {

            // System Message
            System.out.println(PROG_DAL_D_SystemMessages.BUTTON_Schedule_InputTime);

            // ButtonPressPartialTimeInput() ensures all variables have been input, returns 0 on success
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
                 * LinkedList<PROG_DAL_A_TimeInput> SCHEDULE_TIMES  - A list of all time preferences a person has. To be submitted to the scheduler for calculation
                 * 
                 * LinkedList<VBox> List_VBoxTimeInputs             - a copied list of all VBoxes stored in the flowpane display. Used to iterate, not to be displayed
                 * 
                 * FlowPane FlowPane_VBoxDisplay                    - A flowpane which displays the various Vboxs that hold user preferences. display only
                 */
                Scheduler_UserTimeInputs.UserInputGraphic(TempUserPreferrence, SCHEDULE_TIMES, List_VBoxTimeInputs, FlowPane_VBoxDisplay, false);
                // ############################################################


                // garbage Collection
                TempUserPreferrence = null;


                // update the scheduler to the updated list of user times
                ScheduleCalculator.SetSpecificTime(SCHEDULE_TIMES);

            } else {
                // Do nothing
            }

        };
        // ############################################################




        // Calculates the user schedules
        // ############################################################
        this.EVENT_CALCULATE_Schedule = event -> {
            
            System.out.println(PROG_DAL_D_SystemMessages.BUTTON_Schedule_Calculate);

            // retrieved copy of calculated linked list
            this.CalculatedScheduleList = new LinkedList<>(ScheduleCalculator.RetrieveSchedule());

            // displays list up to SCHEDULE_LIST size
            for (int CalcScheduleIndex = 0; CalcScheduleIndex < this.SCHEDULE_LIST; CalcScheduleIndex++) {

                // esnures no out of bound exceptions occur
                if (CalcScheduleIndex < CalculatedScheduleList.size()) {
                    
                    Schedules.add(CalculatedScheduleList.get(CalcScheduleIndex));

                } // if (CalcScheduleIndex < CalculatedScheduleList.size())

            } // for (int CalcScheduleIndex = 0; CalcScheduleIndex < this.SCHEDULE_LIST; CalcScheduleIndex++)
        };
        // ############################################################



        // Clears the schedule list
        // ############################################################
        this.EVENT_CLEAR_schedule = event -> {
            
            // clears all calculated schedules
            System.out.println(PROG_DAL_D_SystemMessages.BUTTON_Schedule_clear);
            
            PrintedLists = 0;

            for (Node node : UIOutput_FullUI_VBOX.getChildren()) {
                if (node instanceof HBox hbox) {
                    hbox.getChildren().clear();
                }
            }

            UIOutput_FullUI_VBOX.getChildren().clear();

        };
        // ############################################################




    }



    /**
     * ButtonCreation()
     * Description: creates the various buttons used for the scheduling page setting
     */
    private void ButtonCreation() {

        // Return Home Button
        // ############################################################
        this.RETURN_ToMenu          = new Button("Return Home");
        this.RETURN_ToMenu.setOnAction(this.EVENT_RETURN_HOME);
        this.RETURN_ToMenu.setPrefSize(PROG_UI_D_DataVariables.SCHEDULE_Button_PrefWidthLarge, PROG_UI_D_DataVariables.SCHEDULE_Button_PrefHeightLarge);
        // ############################################################



        // Adding removing people

        // People to schedule reset button
        // ############################################################
        this.RESET_People           = new Button("Reset people to schedule");
        this.RESET_People.setOnAction(this.EVENT_RESET_People);
        this.RESET_People.setPrefSize(PROG_UI_D_DataVariables.SCHEDULE_Button_PrefWidthLarge, PROG_UI_D_DataVariables.SCHEDULE_Button_PrefHeightLarge);
        // ############################################################

        // Add people to schedule Button
        // ############################################################
        this.Input_SelectedPeople   = new Button("Input Selected Ammount");
        this.Input_SelectedPeople.setOnAction(EVENT_ADD_People);
        this.Input_SelectedPeople.setPrefSize(PROG_UI_D_DataVariables.SCHEDULE_Button_PrefWidthLarge, PROG_UI_D_DataVariables.SCHEDULE_Button_PrefHeightLarge);
        // ############################################################

        // People to schedule reset button
        // ############################################################
        this.REMOVELastPerson       = new Button("Remove last person");
        this.REMOVELastPerson.setOnAction(EVENT_REMOVE_Person);
        this.REMOVELastPerson.setPrefSize(PROG_UI_D_DataVariables.SCHEDULE_Button_PrefWidthLarge, PROG_UI_D_DataVariables.SCHEDULE_Button_PrefHeightLarge);
        // ############################################################



        // Adding removing list ammount

        // number of lists reset button
        // ############################################################
        this.RESET_ListNumber       = new Button("Reset number of lists");
        this.RESET_ListNumber.setOnAction(EVENT_RESET_List);
        this.RESET_ListNumber.setPrefSize(PROG_UI_D_DataVariables.SCHEDULE_Button_PrefWidthLarge, PROG_UI_D_DataVariables.SCHEDULE_Button_PrefHeightLarge);
        // ############################################################

        // Input number of schedule lists to display
        // ############################################################
        this.INPUT_SelectedNumber = new Button("Input Selected Ammount");
        this.INPUT_SelectedNumber.setOnAction(this.EVENT_ADD_List);
        this.INPUT_SelectedNumber.setPrefSize(PROG_UI_D_DataVariables.SCHEDULE_Button_PrefWidthLarge, PROG_UI_D_DataVariables.SCHEDULE_Button_PrefHeightLarge);
        // ############################################################


        // Adding removing days to schedule on

        // Reset number of days to schedule
        // ############################################################
        this.RESET_DaySelection     = new Button("Reset days to schedule");
        this.RESET_DaySelection.setOnAction(EVENT_RESET_days);
        this.RESET_DaySelection.setPrefSize(PROG_UI_D_DataVariables.SCHEDULE_Button_PrefWidthLarge, PROG_UI_D_DataVariables.SCHEDULE_Button_PrefHeightLarge);
        // ############################################################

        // Input selected day
        // ############################################################
        this.INPUT_SelectedDay = new Button("Input Selected Day");
        this.INPUT_SelectedDay.setOnAction(this.EVENT_ADD_Days);
        this.INPUT_SelectedDay.setPrefSize(PROG_UI_D_DataVariables.SCHEDULE_Button_PrefWidthLarge, PROG_UI_D_DataVariables.SCHEDULE_Button_PrefHeightLarge);
        // ############################################################



        // Reset specific input times
        // ############################################################
        this.RESET_TimeInput        = new Button("Reset time input");
        this.RESET_TimeInput.setOnAction(EVENT_RESET_Times);
        this.RESET_TimeInput.setPrefSize(PROG_UI_D_DataVariables.SCHEDULE_Button_PrefWidthLarge, PROG_UI_D_DataVariables.SCHEDULE_Button_PrefHeightLarge);
        // ############################################################



        // Add Time info
        // ############################################################
        this.INPUT_TimePreferences  = new Button("Add Info");
        this.INPUT_TimePreferences.setOnAction(EVENT_ADD_timeInput);
        this.INPUT_TimePreferences.setPrefSize(PROG_UI_D_DataVariables.SCHEDULE_Button_PrefWidthLarge, PROG_UI_D_DataVariables.SCHEDULE_Button_PrefHeightLarge);
        // ############################################################



        // Reset all preferences
        // ############################################################
        this.RESET_ALLPreferences   = new Button("Reseting all Preferences");
        this.RESET_ALLPreferences.addEventHandler(ActionEvent.ACTION, EVENT_RESET_People);
        this.RESET_ALLPreferences.addEventHandler(ActionEvent.ACTION, EVENT_RESET_List);
        this.RESET_ALLPreferences.addEventHandler(ActionEvent.ACTION, EVENT_RESET_days);
        this.RESET_ALLPreferences.addEventHandler(ActionEvent.ACTION, EVENT_RESET_Times);
        this.RESET_ALLPreferences.setPrefSize(PROG_UI_D_DataVariables.SCHEDULE_Button_PrefWidthLarge, PROG_UI_D_DataVariables.SCHEDULE_Button_PrefHeightLarge);
        // ############################################################



        // Calculate Schedule
        // ############################################################
        this.CALCULATE_Schedule     = new Button("Calculate Schedule");
        this.CALCULATE_Schedule.setOnAction(EVENT_CALCULATE_Schedule);
        this.CALCULATE_Schedule.setPrefSize(PROG_UI_D_DataVariables.SCHEDULE_Button_PrefWidthLarge, PROG_UI_D_DataVariables.SCHEDULE_Button_PrefHeightLarge);
        // ############################################################



        // Clear Schedule
        // ############################################################
        this.CLEAR_Schedules        = new Button("Clear Schedule list");
        this.CLEAR_Schedules.setOnAction(EVENT_CLEAR_schedule);
        this.CLEAR_Schedules.setPrefSize(PROG_UI_D_DataVariables.SCHEDULE_Button_PrefWidthLarge, PROG_UI_D_DataVariables.SCHEDULE_Button_PrefHeightLarge);
        // ############################################################



    } // ButtonCreation()



    /**
     * SchedulingInterface()
     * Description: creates the interface used for adding user preferences to the schedule.
     */
    private void SchedulingInterface() {

        // contains UI elements
        // add nodes here not UIInterfaceNode
        // this.UIInput_FullUI_VBOX = new VBox(10);
        // this.UIInput_FullUI_VBOX.setPadding(new Insets(10));


        // UI Sub node creation
        // ############################################################
        // UI Input for People
        InputPeople();

        // UI Input for List ammount
        InputListNumber();

        // UI Input for day preference
        InputDayPreference();

        // UI Input for Time Preference
        InputTimePreference();

        // UI Input for calculaitng the schedule
        CalculateSchedule();
        // ############################################################


        // Add all Nodes
        // ############################################################
        this.UIInput_FullUI_VBOX.getChildren().addAll(
            // people Input
            UIInput_PeopleUI_VBOX,

            // List number
            UIInput_ListUI_VBOX,

            // day preference
            UIInput_DayUI_VBOX,

            // time input
            UIInput_TimeUI_VBOX,

            // reset preferences
            RESET_ALLPreferences,

            // Calculate schedule
            UIInput_CalculateUI_VBOX

        );
        // ############################################################

        

        // sets the UI Input within the scrllPane and adjusts preferences
        // ############################################################
        this.UIInput_FullUIHolder_ScrollPane = new ScrollPane(this.UIInput_FullUI_VBOX);
        // sets default interface dimensions
        this.UIInput_FullUIHolder_ScrollPane.setPrefWidth((PROG_UI_A_SceneManager.WindowWidth / 2) - 80.0);
        this.UIInput_FullUIHolder_ScrollPane.setPrefHeight(PROG_UI_A_SceneManager.WindowHeight - 100.0);

        this.UIInput_FullUIHolder_ScrollPane.setFitToHeight(true);
        this.UIInput_FullUIHolder_ScrollPane.setFitToWidth(true);

        // updates interface dimensions
        this.ApplicationStage.widthProperty().addListener((observed, oldWidth, newWidth) -> {
            UIInput_FullUIHolder_ScrollPane.setPrefWidth((newWidth.intValue() / 2) - 80.0);
        });

        this.ApplicationStage.heightProperty().addListener((observed, oldHeight, newHeight) -> {
            UIInput_FullUIHolder_ScrollPane.setPrefHeight(newHeight.intValue() - 100.0);
        });
        // ############################################################

    } // SchedulingInterface()



    /**
     * InputPeople()
     * Description: node used to input the preferred people for the schedule calculation
     */
    private void InputPeople() {
        
        // Node Creation
        // ############################################################
        // Primary Node         - holds User input
        HBox Primary_InputPeople    = new HBox(PROG_UI_D_DataVariables.SCHEDULE_UIINPUT_PREFSpacing);
        // Primary Node Input   - Input for user
        VBox Input_InputPeople      = new VBox(PROG_UI_D_DataVariables.SCHEDULE_UIINPUT_PREFSpacing);
        // Primary Node Output  - output of selected choiced
        VBox Output_InputPeople     = new VBox(PROG_UI_D_DataVariables.SCHEDULE_UIINPUT_PREFSpacing);
        // Secondary Node       - Holds Reset Button
        VBox Secondary_InputPeople  = new VBox(PROG_UI_D_DataVariables.SCHEDULE_UIINPUT_PREFSpacing);
        // comboBox to display the names available to select
        this.Selectable_PersonList  = new ComboBox<>();
        // flowpane node to hold te name list of selected people
        this.AddedPeople            = new FlowPane();
        // scrollpane to hold the flowpane AddedPeople
        ScrollAddedPeople           = new ScrollPane(this.AddedPeople);
        // ############################################################


        // Node set sizing
        // ############################################################
        Input_InputPeople           .setPrefSize(PROG_UI_D_DataVariables.SCHEDULE_PrimaryNode1_PREFWidth,   PROG_UI_D_DataVariables.SCHEDULE_PrimaryNode1_PREFHeight);
        Output_InputPeople          .setPrefSize(PROG_UI_D_DataVariables.SCHEDULE_PrimaryNode2_PREFWidth,   PROG_UI_D_DataVariables.SCHEDULE_PrimaryNode2_PREFHeight);
        this.Selectable_PersonList  .setPrefSize(PROG_UI_D_DataVariables.SCHEDULE_INPUT_PrefWidthLarge,     PROG_UI_D_DataVariables.SCHEDULE_INPUT_PrefHeightLarge);
        this.AddedPeople            .setPrefSize(100.0, 100.0);
        // this.AddedPeople         .getStyleClass().add("flowBox-names");
        this.AddedPeople            .setPadding(new Insets(2));
        this.AddedPeople            .setHgap(5.0);
        this.AddedPeople            .setVgap(2.0);
        // ############################################################


        // Label creation
        // ############################################################
        Label Label_inputPeople = new Label("Input the people to schedule");
        Label Label_addedPeople = new Label("Selected People to Schedule");
        // ############################################################


        // styling
        // ############################################################
        // labels
        Label_inputPeople.getStyleClass().add("default-label");
        Label_addedPeople.getStyleClass().add("default-label");
        // ############################################################


        // Initial Setup of functions
        // ############################################################
        // retrieve the list of user preferences from the relevant json file
        try {
            Scheduler_fileReader.RetrieveFromFile();
        } catch (IOException e) {
            // ERROR
            e.printStackTrace();
        }

        // Retrieve the list from the file reader
        this.FileUserInfo   = new LinkedList<>(Scheduler_fileReader.ReturnFile());
        
        // LinkedList of all people in the file showing both ID and full name
        this.PersonList     = new LinkedList<>();

        // Add people to the list
        for (PROG_DAL_A_InfoInput FilePerson : FileUserInfo) {
            PersonList.add("|ID: " + FilePerson.EmployeeID + " Name: " + FilePerson.EmployeeName + "|");
        }

        // add list to combobox
        this.Selectable_PersonList.getItems().addAll(PersonList);
        // ############################################################


        // Add all to the primary Node
        // ############################################################
        // Primary Node output
        Output_InputPeople          .getChildren().addAll(Label_addedPeople, ScrollAddedPeople); // AddedPeople
        // Primary Node Input
        Input_InputPeople           .getChildren().addAll(Label_inputPeople, Selectable_PersonList, this.Input_SelectedPeople, this.REMOVELastPerson);
        // Primary Node
        Primary_InputPeople         .getChildren().addAll(Input_InputPeople, Output_InputPeople);
        // ############################################################

        // Add all to the Secondary node
        // ############################################################
        Secondary_InputPeople       .getChildren().addAll(this.RESET_People);
        // ############################################################


        // ADD primary and secondary nodes to the UI Holder UIInput_PeopleUI_VBOX
        // ############################################################
        this.UIInput_PeopleUI_VBOX  .getChildren().addAll(Primary_InputPeople, Secondary_InputPeople);
        // ###########################################################

    } // InputPeople()



    /**
     * InputListNumber()
     * Description: receives the input to specificy how many lists should be shown
     */
    private void InputListNumber() {
        
        // Node Creation
        // ############################################################
        // Primary Node
        HBox Primary_InputList      = new HBox(PROG_UI_D_DataVariables.SCHEDULE_UIINPUT_PREFSpacing);
        // Primary Node Input
        VBox Input_InputList        = new VBox(PROG_UI_D_DataVariables.SCHEDULE_UIINPUT_PREFSpacing);
        // Primary Node Output
        VBox Output_InputList       = new VBox(PROG_UI_D_DataVariables.SCHEDULE_UIINPUT_PREFSpacing);
        // Secondary Node
        HBox Secondary_InputList    = new HBox(PROG_UI_D_DataVariables.SCHEDULE_UIINPUT_PREFSpacing);
        // Text Field Input
        this.userInput_listAmmount  = new TextField();
        // ############################################################
        

        // Node set sizing
        // ############################################################
        // Primary Node Input
        Input_InputList             .setPrefSize(PROG_UI_D_DataVariables.SCHEDULE_PrimaryNode1_PREFWidth,   PROG_UI_D_DataVariables.SCHEDULE_PrimaryNode1_PREFHeight);
        // Primary Node Output
        Output_InputList            .setPrefSize(PROG_UI_D_DataVariables.SCHEDULE_PrimaryNode2_PREFWidth,   PROG_UI_D_DataVariables.SCHEDULE_PrimaryNode2_PREFHeight);
        // Text Field Input
        this.userInput_listAmmount  .setPrefSize(PROG_UI_D_DataVariables.SCHEDULE_INPUT_PrefWidthLarge,     PROG_UI_D_DataVariables.SCHEDULE_INPUT_PrefHeightLarge);
        // ############################################################

        // labels
        // ############################################################
        Label Label_inputNumber     = new Label("Input the total ammount of lists to Display");
        Label Label_AmmountOutput   = new Label("Ammount of schedules to be displayed");
        // ############################################################


        // styling
        // ############################################################
        Label_inputNumber.getStyleClass().add("default-label");
        // ############################################################


        // Textfield setup functions
        // ############################################################
        this.userInput_listAmmount.setPromptText("Enter List Ammount");
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
                
                if (intValue >= 0 && intValue < PROG_UI_D_DataVariables.MAXListAmmount) {
                    return change;
                }

            } catch (NumberFormatException e) {
                // Invalid Input
            }

            return null;

        }));
        // ############################################################

        
        // Output
        // ############################################################
        // Stores the Ammount input variable
        this.Label_OutputNumber = new Label(PROG_UI_D_DataVariables.EmptyText);
        // ############################################################


        // Add all to the primary node
        // ############################################################
        // Secondary Node - output
        Output_InputList        .getChildren().addAll(Label_AmmountOutput, Label_OutputNumber);
        // secondary Node - Input
        Input_InputList         .getChildren().addAll(Label_inputNumber, userInput_listAmmount, this.INPUT_SelectedNumber);
        // Primary Node
        Primary_InputList       .getChildren().addAll(Input_InputList, Output_InputList);
        // ############################################################

        // Add all to the secondary node
        // ############################################################
        Secondary_InputList     .getChildren().addAll(this.RESET_ListNumber);
        // ############################################################


        // add all to the list UI UIInput_ListUI_VBOX
        // ############################################################
        UIInput_ListUI_VBOX     .getChildren().addAll(Primary_InputList, Secondary_InputList);
        // ############################################################
        
    } // InputListNumber()



    /**
     * InputDayPreference()
     * Description: User input for what preferred days they want in the schedule
     */
    private void InputDayPreference() {

        // Node construction
        // ############################################################
        // Primary Node
        HBox Primary_InputDay       = new HBox(PROG_UI_D_DataVariables.SCHEDULE_UIINPUT_PREFSpacing);
        // Primary Node input
        VBox Input_InputDay         = new VBox(PROG_UI_D_DataVariables.SCHEDULE_UIINPUT_PREFSpacing);
        // Primary Node output
        VBox Output_InputDay        = new VBox(PROG_UI_D_DataVariables.SCHEDULE_UIINPUT_PREFSpacing);
        // secondary node
        HBox Secondary_InputDay     = new HBox(PROG_UI_D_DataVariables.SCHEDULE_UIINPUT_PREFSpacing);
        // combo box for days of the week, add multiple days
        this.userInput_SelectDays   = new ComboBox<>();
        // flowPane to hold and display selected day inputs
        this.OutputDays             = new FlowPane();
        // ############################################################



        // Node sizing
        // ############################################################
        // Primary Node input
        Input_InputDay              .setPrefSize(PROG_UI_D_DataVariables.SCHEDULE_PrimaryNode1_PREFWidth,   PROG_UI_D_DataVariables.SCHEDULE_PrimaryNode1_PREFHeight);
        // Primary Node output
        Output_InputDay             .setPrefSize(PROG_UI_D_DataVariables.SCHEDULE_PrimaryNode2_PREFWidth,   PROG_UI_D_DataVariables.SCHEDULE_PrimaryNode2_PREFHeight);
        // combo box for days of the week, add multiple days
        this.userInput_SelectDays   .setPrefSize(150.0, 40.0);
         // flowPane to hold and display selected day inputs
        this.OutputDays             .setPrefSize(150.0, 40.0);
        // ############################################################

        // label creation
        // ############################################################
        Label Label_inputDay    = new Label("Input the days to schedule");
        Label Label_OutputDay   = new Label("Days Selected");
        // ############################################################


        // Styling
        // ############################################################
        // labels
        Label_inputDay  .getStyleClass().add("default-label");
        Label_OutputDay .getStyleClass().add("default-label");
        // ############################################################


        // Output Section
        // ############################################################

        // Add days to the combobox
        this.userInput_SelectDays.getItems().addAll(PROG_UI_D_DataVariables.WEEKDAYS);
        // add defualt text to output
        this.OutputDays.getChildren().add(new Text(PROG_UI_D_DataVariables.EmptyText));

        // this.OutputDays.getChildren().addListener((javafx.collections.ListChangeListener<Node>) change -> {

        //     // ensures OutputDays has enough nodes to complete all operations without index out of bound errors
        //     // Also exists due to reset button activating this listner -> do not remove the if statmenet
        //     if (this.OutputDays.getChildren().size() > 1) {


        //     } // if()
        // });
        // // ############################################################





        // Add all to the primary node
        // ############################################################
        // Primary Node Output
        Output_InputDay         .getChildren().addAll(Label_OutputDay, OutputDays);
        // Primary Node Input
        Input_InputDay          .getChildren().addAll(Label_inputDay, userInput_SelectDays, this.INPUT_SelectedDay);
        // Primary Node
        Primary_InputDay        .getChildren().addAll(Input_InputDay, Output_InputDay);
        // ############################################################

        // add all to the secondary node
        // ############################################################
        Secondary_InputDay      .getChildren().addAll(RESET_DaySelection);
        // ############################################################
        


        // Input all into UI Day Input this.UIInput_DayUI_VBOX.
        // ############################################################
        this.UIInput_DayUI_VBOX .getChildren().addAll(Primary_InputDay, Secondary_InputDay);
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
        HBox Primary_InputTime      = new HBox(PROG_UI_D_DataVariables.SCHEDULE_UIINPUT_PREFSpacing);
        // Primary Node input
        VBox Input_InputTime        = new VBox(PROG_UI_D_DataVariables.SCHEDULE_UIINPUT_PREFSpacing);
        // Secondary Node
        HBox Secondary_InputTime    = new HBox(PROG_UI_D_DataVariables.SCHEDULE_UIINPUT_PREFSpacing);
        // HBox for beginning Hour/Min input
        HBox Add_StartTime          = new HBox(PROG_UI_D_DataVariables.SCHEDULE_UIINPUT_PREFSpacing);
        // hbox for ending hour/min input
        HBox Add_EndTime            = new HBox(PROG_UI_D_DataVariables.SCHEDULE_UIINPUT_PREFSpacing);
        // flowpane to hold time output boxes   -   NOTE: must be declared within class to work not method
        this.FlowPane_VBoxDisplay   = new FlowPane();
        // linkedlist for input VBoxes  - used for iteration only
        this.List_VBoxTimeInputs    = new LinkedList<>();
        // ############################################################


        // Node sizing
        // ############################################################
        // Primary Node input
        Input_InputTime             .setPrefSize(300.0, 200.0);
        // HBox for beginning Hour/Min input
        Add_StartTime               .setPadding(new Insets(10));
        // hbox for ending hour/min input
        Add_EndTime                 .setPadding(new Insets(10));
        // flowpane to hold time output boxes
        this.FlowPane_VBoxDisplay   .setPrefSize(270.0, 190.0);
        this.FlowPane_VBoxDisplay   .setPadding(new Insets(10));
        this.FlowPane_VBoxDisplay   .setHgap(5.0);
        this.FlowPane_VBoxDisplay   .setVgap(2.0);
        // ############################################################

        // label creation
        // ############################################################
        Label Label_inputTime   = new Label("Input the times to schedule");
        Label Label_BeginHour   = new Label("Hour");
        Label Label_BeginMinute = new Label("Minute");
        Label Label_EndHour     = new Label("Hour");
        Label Label_EndMinute   = new Label("Minute");
        // ############################################################


        // styling
        // ############################################################
        Label_inputTime             .getStyleClass().add("default-label");
        Label_BeginHour             .getStyleClass().add("default-label");
        Label_BeginMinute           .getStyleClass().add("default-label");
        Label_EndHour               .getStyleClass().add("default-label");
        Label_EndMinute             .getStyleClass().add("default-label");
        Add_StartTime               .getStyleClass().add("UserPreference-box");
        Add_EndTime                 .getStyleClass().add("UserPreference-box");
        this.FlowPane_VBoxDisplay   .getStyleClass().add("UserPreference-box");
        // ############################################################



        // Input creation functions
        // ############################################################

        // beginning time input
        Add_StartTime.getChildren().addAll(

            Label_BeginHour,
            Scheduler_UserTimeInputs.Return_Hour_Begin(),

            Label_BeginMinute,
            Scheduler_UserTimeInputs.Return_Minute_Begin(),

            Scheduler_UserTimeInputs.Return_AMPM_StartTime()

        );
        
        // ending time input
        Add_EndTime.getChildren().addAll(

            Label_EndHour,
            Scheduler_UserTimeInputs.Return_Hour_End(),

            Label_EndMinute,
            Scheduler_UserTimeInputs.Return_Minute_End(),

            Scheduler_UserTimeInputs.Return_AMPM_EndTime()

        );
        // ############################################################


        // Add all to the nodes
        // ############################################################
        // primary node output
        // - FlowPane_VBoxDisplay
        // primary node input
        Input_InputTime             .getChildren().addAll(Label_inputTime, Add_StartTime, Add_EndTime, this.INPUT_TimePreferences);
        // primary node
        Primary_InputTime           .getChildren().addAll(Input_InputTime, this.FlowPane_VBoxDisplay);
        // ############################################################

        // Secondary Node
        // ############################################################
        Secondary_InputTime         .getChildren().addAll(this.RESET_TimeInput);
        // ############################################################


        // add all to Time Ui holder UIInput_TimeUI_VBOX
        // ############################################################
        this.UIInput_TimeUI_VBOX    .getChildren().addAll(Primary_InputTime, Secondary_InputTime);
        // ############################################################

    } // InputTimePreference()



    /**
     * CalculateSchedule()
     * Description: hold the calculate schedule button
     */
    private void CalculateSchedule() {

        // Node Creation
        // ############################################################
        // primary node
        HBox CalculateButtons   = new HBox(PROG_UI_D_DataVariables.SCHEDULE_UIINPUT_PREFSpacing);
        // Secondary Node - input
        VBox Holder_Calculate   = new VBox(PROG_UI_D_DataVariables.SCHEDULE_UIINPUT_PREFSpacing);
        // ############################################################


        // Input Section
        // ############################################################
        // label
        Label Label_ScheduleNow = new Label("Calculate Schedule");
        // ############################################################


        // Styling
        // ############################################################
        Label_ScheduleNow.getStyleClass().add("default-label");
        // ############################################################


        // Add all to the nodes
        // ############################################################
        // seconary node input
        Holder_Calculate                .getChildren().addAll(Label_ScheduleNow);
        // primary node
        CalculateButtons                .getChildren().addAll(Holder_Calculate, this.CALCULATE_Schedule, this.CLEAR_Schedules);
        // ############################################################


        // input all into the schedule UI
        // ############################################################
        this.UIInput_CalculateUI_VBOX   .getChildren().addAll(CalculateButtons);
        // ############################################################

    } // CalculateSchedule()


    


    /**
     * SchedulingDisplay()
     * Description: creates the display board for the finished schedules
     */
    private void SchedulingDisplay() {

        
        // scroll pane for scrolling between the vbox nodes
        this.UIOutput_FullUIHolder_scrollPane = new ScrollPane();
        this.UIOutput_FullUIHolder_scrollPane.setContent(UIOutput_FullUI_VBOX);
        this.UIOutput_FullUIHolder_scrollPane.setFitToWidth(true);
        this.UIOutput_FullUIHolder_scrollPane.setFitToHeight(true);
        //this.UIOutput_FullUI_VBOX.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        // sets default interface dimensions
        this.UIOutput_FullUIHolder_scrollPane.setPrefWidth((PROG_UI_A_SceneManager.WindowWidth / 2) - 80.0);
        this.UIOutput_FullUIHolder_scrollPane.setPrefHeight(PROG_UI_A_SceneManager.WindowHeight - 100.0);

        // updates interface dimensions
        this.ApplicationStage.widthProperty().addListener((observed, oldWidth, newWidth) -> {
            UIOutput_FullUIHolder_scrollPane.setPrefWidth((newWidth.intValue() / 2) - 80.0);
        });

        this.ApplicationStage.heightProperty().addListener((observed, oldHeight, newHeight) -> {
            UIOutput_FullUIHolder_scrollPane.setPrefHeight(newHeight.intValue() - 100.0);
        });

        // observablelist to hold each schedule
        this.Schedules = FXCollections.observableArrayList();

       //this.UIOutput_FullUI_VBOX.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        Schedules.addListener((ListChangeListener<PROG_DAL_A_Schedule>) change -> {

            int ScheduleNumber = 0;

            while ((change.next()) && (ScheduleNumber < this.SCHEDULE_LIST) ) {

                if (change.wasAdded()) {


                    // ensure list of people is up to date
                    try {
                        Scheduler_fileReader.RetrieveFromFile();
                    } catch (IOException e) {
                        // error
                        e.printStackTrace();
                    }

                    // Node Creation
                    // ############################################################
                    // primary Box conatining schedule Info and user list
                    VBox        ScheduleBox_Primary     = new VBox(PROG_UI_D_DataVariables.SCHEDULE_UIOUTPUT_PREFSpacing);
                    // secondary box containing only the schedule info
                    HBox        ScheduleBox_Secondary   = new HBox(PROG_UI_D_DataVariables.SCHEDULE_UIOUTPUT_PREFSpacing);
                    // List of people in schedule
                    FlowPane    SchedulePeopleList      = new FlowPane();
                    // deletion Button
                    Button      DeleteList              = new Button("X");
                    // DeleteList.setId(Integer.toString(ScheduleNumber)); // sets Id to the list number
                    // ############################################################


                    // schedule creation functions
                    // ############################################################
                    String StartingAMPM = "AM";
                    String EndingAMPM   = "AM";

                    // Time frame of the schedule
                    int StartHour       = Schedules.getLast().Interval.PreferedHourBEGIN.getHour();
                    String startMinute  = Integer.toString(Schedules.getLast().Interval.PreferedHourBEGIN.getMinute());

                    int Endhour         = Schedules.getLast().Interval.PreferedHourEND.getHour();
                    String EndMinute    = Integer.toString(Schedules.getLast().Interval.PreferedHourEND.getMinute());

                    // Convert to PM if necessary
                    if (StartHour >= 12) {
                        StartingAMPM = "PM";
                    }

                    if (StartHour > 12) {
                        StartHour -= 12;
                    }

                    // Convert to PM if necessary
                    if (Endhour >= 12) {
                        EndingAMPM = "PM";
                    }
                    if (Endhour > 12) {
                        Endhour -= 12;
                    }

                    // Add a leading zero to the start of the minute inputs if it is less than 10
                    if (Integer.parseInt(startMinute)  < 10) {
                        startMinute = "0" + startMinute;
                    }

                    if (Integer.parseInt(EndMinute)    < 10) {
                        EndMinute   = "0" + EndMinute;
                    }


                    //int PersonAmmount = Schedules.getLast().USERIDs.size();

                    this.FilePeople = new LinkedList<>(Scheduler_fileReader.ReturnFile());

                    for (String ScheduleID : Schedules.getLast().USERIDs) {

                        for (int FileIndex = 0; FileIndex < FilePeople.size(); FileIndex++) {

                            if (ScheduleID.equals(Integer.toString(FilePeople.get(FileIndex).EmployeeID))) {

                                // add name to the flowpane list
                                SchedulePeopleList.getChildren().add(
                                    new Label(" |ID: " + ScheduleID + " - " + "Name: " + FilePeople.get(FileIndex).EmployeeName)
                                );

                                // breaks out of for loop
                                break;

                            } // if ()

                        } // for (int FileIndex = 0; FileIndex < FilePeople.size(); FileIndex++)

                    } // for (String ScheduleID : Schedules.getLast().USERIDs)

                    // ############################################################



                    // labels
                    // ############################################################
                    // denotes if the list contains all desired people
                    Label FullList          = new Label("| Full List: " + String.valueOf(Schedules.getLast().Schedule));
                    Label ScheduleDay       = new Label("| Day: " + String.valueOf(Schedules.getLast().WeekDay));
                    Label ScheduletimeFrame = new Label("| Time Frame: " + StartHour + ":" + startMinute + " " + StartingAMPM + 
                    " - " + Endhour + ":" + EndMinute + " " + EndingAMPM);
                    // ############################################################


                    // Styling
                    // ############################################################
                    // VBox
                    ScheduleBox_Primary     .setPadding(new Insets(PROG_UI_D_DataVariables.SCHEDULE_UIOUTPUT_PREFInsets));
                    ScheduleBox_Primary     .setPrefSize(PROG_UI_D_DataVariables.SCHEDULE_OUTPUT_primarywidth,     PROG_UI_D_DataVariables.SCHEDULE_OUTPUT_primaryheight);
                    ScheduleBox_Primary     .getStyleClass().add("ScheduleList-VBox");

                    // HBox
                    ScheduleBox_Secondary   .setPrefSize(PROG_UI_D_DataVariables.SCHEDULE_OUTPUT_Secondarywidth1,   PROG_UI_D_DataVariables.SCHEDULE_OUTPUT_Secondaryheight1);
                    ScheduleBox_Secondary   .setPadding(new Insets(PROG_UI_D_DataVariables.SCHEDULE_UIOUTPUT_PREFInsets));
                    ScheduleBox_Secondary   .getStyleClass().add("ScheduleList-HBox");
                    ScheduleBox_Secondary   .setAlignment(Pos.CENTER_LEFT);

                    // flowPane
                    SchedulePeopleList      .setPrefSize(PROG_UI_D_DataVariables.SCHEDULE_OUTPUT_Secondarywidth1,   PROG_UI_D_DataVariables.SCHEDULE_OUTPUT_Secondaryheight1);

                    // labels
                    FullList                .setPrefSize(PROG_UI_D_DataVariables.SCHEDULE_OUTPUT_Secondarywidth2,   PROG_UI_D_DataVariables.SCHEDULE_OUTPUT_Secondaryheight2);
                    ScheduleDay             .setPrefSize(PROG_UI_D_DataVariables.SCHEDULE_OUTPUT_Secondarywidth3,   PROG_UI_D_DataVariables.SCHEDULE_OUTPUT_Secondaryheight3);
                    ScheduletimeFrame       .setPrefSize(PROG_UI_D_DataVariables.SCHEDULE_OUTPUT_Secondarywidth4,   PROG_UI_D_DataVariables.SCHEDULE_OUTPUT_Secondaryheight4);

                    // button
                    DeleteList              .setMaxSize(PROG_UI_D_DataVariables.SCHEDULE_OUTPUT_DeleteButtonWidth, PROG_UI_D_DataVariables.SCHEDULE_OUTPUT_DeleteButtonHeight);
                    // ############################################################



                    // schedule logic implementation


                    DeleteList.setOnAction(event -> {

                        // clear all info in the node
                        ScheduleBox_Primary.getChildren().clear();

                        // remove node from the list
                        UIOutput_FullUI_VBOX.getChildren().remove(ScheduleBox_Primary);

                    });



                    ScheduleBox_Secondary.getChildren().addAll(
                        
                        DeleteList,

                        FullList,

                        ScheduleDay,

                        ScheduletimeFrame

                    );


                    ScheduleBox_Primary.getChildren().addAll(

                        ScheduleBox_Secondary,

                        SchedulePeopleList
                    );


                    this.UIOutput_FullUI_VBOX.getChildren().addAll(

                        ScheduleBox_Primary

                    );




                    // increment schedule number ammount
                    ScheduleNumber++;

                } // if (change.wasAdded())


            } // while (change.next)


        }); // event listner
        

    } // SchedulingDisplay()



    /**
     * SetBackground()
     * Description: Sets the scene background
     */
    private void SetBackground() {
        // TODO: finish background

        // Add background gradient
        LinearGradient BackgroundGradient = new LinearGradient(0, 0, 300, 300, false, CycleMethod.NO_CYCLE, 
            new Stop(0, Color.DARKBLUE), new Stop(1, Color.BEIGE)
        );

        BackgroundFill backgroundFill = new BackgroundFill(BackgroundGradient, CornerRadii.EMPTY, Insets.EMPTY);

        Schedule_RootNode.setBackground(new Background(backgroundFill));

    } // SetBackground()



    /**
     * fadeTransitions()
     * Description: creates the fade transitions for the scene change
     */
    private void fadeTransitions() {

        // Transition to fade buttons
        fadeMenuNodes = new ParallelTransition();

        for (Node node : Schedule_RootNode.getChildren()) {
            
            FadeTransition NodeFade = new FadeTransition(
                Duration.seconds(2),
                node
            );

            NodeFade.setToValue(0);
            
            fadeMenuNodes.getChildren().addAll(NodeFade);
        }
        // ############################################################


        // Fade to Menu
        // ############################################################
        fadeMenuNodes.setOnFinished(event -> { PROG_UI_A_Application.SceneManager.MainMenu(); });
        // ############################################################


        // Transition to Unfade buttons
        UnfadeMenuNodes = new ParallelTransition();

        for (Node node : Schedule_RootNode.getChildren()) {
            
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
