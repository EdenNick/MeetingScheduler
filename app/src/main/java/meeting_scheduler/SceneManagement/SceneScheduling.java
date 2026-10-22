// Package  - DO Not Change
// ############################################################
package meeting_scheduler.SceneManagement;
// ############################################################

// Imports
// ############################################################
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
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
import javafx.stage.Stage;
import javafx.util.Duration;
import meeting_scheduler.DataHolder.PreferenceFull;
import meeting_scheduler.DataHolder.PreferenceTime;
import meeting_scheduler.FileManagement.ManageFileLock;
import meeting_scheduler.FileManagement.ManageJsonFile;
import meeting_scheduler.ScheduleManagement.DataCompleteSchedule;
import meeting_scheduler.ScheduleManagement.ManageScheduleCalculation;
import meeting_scheduler.SystemInfoManager;
import meeting_scheduler.SystemManagement.ManageAppWindow;
import meeting_scheduler.SystemManagement.ManageScenes;
import meeting_scheduler.UserInput.InputWeek;
import meeting_scheduler.UserInput.ManageDayTime;
import meeting_scheduler.global;
// ############################################################



public class SceneScheduling {
	
    // Apllication
    // ############################################################
    // Reference of the application stage used for local operations
    private final   Stage               ApplicationStage;
    private         Scene               SchedulingScene;
    private         double              StageWidth;
    private         double              stageHeight;
    private         ParallelTransition  fadeMenuNodes;
    private         ParallelTransition  UnfadeMenuNodes;
    // ############################################################

    // Nodes
    // ############################################################
    private AnchorPane  Schedule_RootNode;          // ROOTNODE
    private ScrollPane  ScrollPaneHolderUIInput;    // INPUTHOLDER
    private VBox        VBoxUI_INPUT;               // UIINPUT
    private VBox        VBoxUI_People;              // -
    private VBox        VBoxUI_List;                // -
    private VBox        VBoxUI_WeekDay;             // -
    private VBox        VBoxUI_Time;                // -
    private VBox        VBoxUI_Calculation;         // -
    private ScrollPane  ScrollPaneHolderUIOutput;   // OUTPUTHOLDER
    private VBox        VBOXUI_OUTPUT;              // UIOUTPUT
    // ############################################################

    // Buttons
    // ############################################################
    private Button      ButtonReturnMenu;           // RETURN
    private Button      ButtonResetPeople;          // RESET
    private Button      ButtonResetList;            // -
    private Button      ButtonResetWeekDay;         // -
    private Button      ButtonResetTime;            // -
    private Button      ButtonResetAll;             // -
    private Button      ButtonInputPeople;          // INPUT
    private Button      ButtonInputList;            // -
    private Button      ButtonInputWeekDay;         // -
    private Button      ButtonInputTime;            // -
    private Button      ButtonRemoveLastPerson;     // REMOVE
    private Button      ButtonCalculateSchedule;    // CALCULATE
    private Button      ButtonClearSchedule;        // CLEAR
    // ############################################################

    // Action events
    // ############################################################
    private EventHandler<ActionEvent> EVENT_RETURN_HOME     = null;
    private EventHandler<ActionEvent> EVENT_RESET_PEOPLE    = null;
    private EventHandler<ActionEvent> EVENT_RESET_List      = null;
    private EventHandler<ActionEvent> EVENT_RESET_days      = null;
    private EventHandler<ActionEvent> EVENT_RESET_Times     = null;
    private EventHandler<ActionEvent> EVENT_ADD_TIME        = null;
    private EventHandler<ActionEvent> EVENT_ADD_PEOPLE      = null;
    private EventHandler<ActionEvent> EVENT_ADD_LIST        = null;
    private EventHandler<ActionEvent> EVENT_ADD_DAYS        = null;
    private EventHandler<ActionEvent> EVENT_REMOVE_PERSON   = null;
    private EventHandler<ActionEvent> EVENT_CALCULATE       = null;
    private EventHandler<ActionEvent> EVENT_CLEAR_SCHEDULE  = null;
    // ############################################################

    // File User Info
    // ############################################################
    private LinkedList<PreferenceFull>  FileUserInfo;               // STORE FILE ACCESS
    // ############################################################

    // Data manager Objects
    // ############################################################
    private final ManageScheduleCalculation SCHEDULER_CALCULATOR;   // CALCULATE
    private       ManageDayTime             SCHEDULER_TIME;         // TIME INPUT
    private final ManageJsonFile            SCHEDULER_FILES;        // FILE ACCESS
    // ############################################################
    
    // EMPLOYEE SELECTION VALUES
    // ############################################################
    // INPUT
    private String[]            ComboBoxPeopleScheduleList;     // contains the strings used to add to the combobox
    private int[]               ComboBoxPeopleScheduleListIDs;  // contains the id of each employee relative to the position in ComboBoxPeopleScheduleList
    private ComboBox<String>    Selectable_PersonList;          // Contains the list of all people that can be added to the schedule
    private int                 ComboBoxPeopleSelectedPosition; // used to hold the specific user selected value in the combobox
    // OUTPUT
    private ScrollPane          ScrollAddedPeople;              // holds the flowpane of scheduledpeople so it can be scrolled
    private FlowPane            AddedPeople;                    // list of people the user selected to schedule
    private LinkedList<String>  SCHEDULE_IDS;                   // IN USE IGNORE WARNING - list of selected ids the user wants scheduled
    // ############################################################

    // SCHEDULE LIST LENGTH VALUES
    // ############################################################
    // INPUT
    private TextField           userInput_listAmmount;          // user input for list ammount
    // OUTPUT
    private Label               Label_OutputNumber;             // label containing the selected list ammount for visual display
    private int                 SCHEDULE_LIST;                  // int ammount stored for actual calculations
    // ############################################################

    // WEEKDAY SELECTION VARIABLES
    // ############################################################
    // INPUT
    private ComboBox<String>    userInput_SelectDays;           // combobox for the user to select which days they want to scheudle on
    private FlowPane            OutputDays;                     // flowpane ontains the input days the user wants selected
    // OUTPUT
    private InputWeek           SCHEDULE_DAYS;                  // holds the selected days to be used in the schedule calculations
    private Text                WeekDayOutputText = new Text(); // Output display text of user selection
    // ############################################################

    // TIME INPUT VARIABLES
    // ############################################################
    // INPUT
    private FlowPane                    FlowPane_VBoxDisplay;   // dispalys time inputs
    // OUTPUT
    private LinkedList<PreferenceTime>  SCHEDULE_TIMES;         // IN USE IGNORE WARNING - List of prefered times for an individual
    // ############################################################

    // Calculated Schedules
    // ############################################################
    private LinkedList<DataCompleteSchedule>        CalculatedScheduleList; // Holds the list of calculated schedules for iteration
    private ObservableList<DataCompleteSchedule>    Schedules;              // Used to display the calculated schedules held in CalculatedScheduleList
    private ManageScheduleOutput                    ScheduleOutputManage;   // Object that manages the visual display of output schedules
    // ############################################################

    // UI Grphical settings
    // ############################################################
    private final double ANCHOR_ONE;    // 10.0
    private final double ANCHOR_TWO;    // 20.0
    private final double SPACING;       // 10.0
    private final double PADDING;       // 10.0
    // 100.0 150.0 250.0 270.0 300.0 570.0 600.0
    private final double WIDTH_A;       // 100.0
    private final double WIDTH_B;       // 150.0
    private final double WIDTH_C;       // 250.0
    private final double WIDTH_D;       // 270.0
    private final double WIDTH_E;       // 300.0
    private final double WIDTH_F;       // 570.0
    private final double WIDTH_G;       // 600.0


    // 30.0 60.0 100.0 180.0 200.0 380.0
    private final double HEIGHT_A;      // 30.0
    private final double HEIGHT_B;      // 60.0
    private final double HEIGHT_C;      // 100.0
    private final double HEIGHT_D;      // 180.0
    private final double HEIGHT_E;      // 200.0
    private final double HEIGHT_F;      // 400.0

    private final String STYLE_FILE;    // 
    private final String STYLE_A;       // 
    private final String STYLE_B;       // 
    private final String STYLE_C;       // 
    private final String STYLE_D;       // 
    private final String STYLE_E;       // 
    // ############################################################

    // Global variables
    // ############################################################
    private final int maxListAmmount;
    private final String[] WEEKDAY;
    private final int LENGTH_ID;
    private final int LENGTH_NAME;
    private final String SELECTED;
    // ############################################################





    /**
     * Constructor class
     */
    public SceneScheduling(Stage stage) {

        // Apllication
        // ############################################################
        // application stage    - Obejct containing all contents of the page, effectivley the applciaiton window
        this.ApplicationStage = stage;
        // ############################################################

        // Data manager Objects
        // ############################################################
        // Schedule Calculator  - Object used to calculate viable schedules based off of input user preferences
        this.SCHEDULER_CALCULATOR = new ManageScheduleCalculation();
        // Json file Reader     - Object which can access the relevant Json file to retireve user info. Used to retrieve current user preferences to create a schedule
        this.SCHEDULER_FILES = new ManageJsonFile(ManageFileLock.GetLock_JSN_DataCard());
        // ############################################################

        // calculating schedules
        // ############################################################
        // observablelist to hold each schedule
        this.Schedules = FXCollections.observableArrayList();
        // holds calculated list of schedules before being passed to the Schedules observable lsit
        this.CalculatedScheduleList = new LinkedList<>();
        // ############################################################


        // Global variables
        // ############################################################
        this.LENGTH_ID      = SystemInfoManager.GET_SYSTEM_VALUE_INT(global.BASICVALUESINT.INT, 9); // 9 - 7
        this.LENGTH_NAME    = SystemInfoManager.GET_SYSTEM_VALUE_INT(global.BASICVALUESINT.INT, 10); // 10 - 30
        this.maxListAmmount = SystemInfoManager.GET_SYSTEM_VALUE_INT(global.BASICVALUESINT.INT, 8); // 8 - 20
        this.WEEKDAY        = SystemInfoManager.GET_SYSTEM_VALUE_WEEK(global.WEEKTYPE.SHORT);
        this.SELECTED       = SystemInfoManager.GET_SYSTEM_VALUE_STRING(global.BASICVALUESSTRING.TEXT, 11); // 11 - SELECTED
        // ############################################################

        // GRAPHICAL STYLING
        // ############################################################
        this.ANCHOR_TWO = SystemInfoManager.GET_SYSTEM_VALUE_DOUBLE(global.UISPACING.ANCHOR, 3);  // 3 - 20
        this.ANCHOR_ONE = SystemInfoManager.GET_SYSTEM_VALUE_DOUBLE(global.UISPACING.ANCHOR, 1);  // 1 - 10

        this.SPACING = SystemInfoManager.GET_SYSTEM_VALUE_DOUBLE(global.UISPACING.SPACING, 0); // 0 - 10
        this.PADDING = SystemInfoManager.GET_SYSTEM_VALUE_DOUBLE(global.UISPACING.PADDING, 1); // 1 - 10

        // 100.0 150.0 250.0 270.0 300.0 570.0 600.0
        this.WIDTH_A = SystemInfoManager.GET_SYSTEM_VALUE_DOUBLE(global.UISPACING.WIDTH, 1);   // 1 - 100
        this.WIDTH_B = SystemInfoManager.GET_SYSTEM_VALUE_DOUBLE(global.UISPACING.WIDTH, 2);   // 2 - 150
        this.WIDTH_C = SystemInfoManager.GET_SYSTEM_VALUE_DOUBLE(global.UISPACING.WIDTH, 4);   // 4 - 250
        this.WIDTH_D = SystemInfoManager.GET_SYSTEM_VALUE_DOUBLE(global.UISPACING.WIDTH, 5);   // 5 - 300
        this.WIDTH_E = SystemInfoManager.GET_SYSTEM_VALUE_DOUBLE(global.UISPACING.WIDTH, 5);   // 5 - 300
        this.WIDTH_F = SystemInfoManager.GET_SYSTEM_VALUE_DOUBLE(global.UISPACING.WIDTH, 7);   // 7 - 600
        this.WIDTH_G = SystemInfoManager.GET_SYSTEM_VALUE_DOUBLE(global.UISPACING.WIDTH, 7);   // 7 - 600

        // 30.0 60.0 100.0 180.0 200.0 380.0
        this.HEIGHT_A = SystemInfoManager.GET_SYSTEM_VALUE_DOUBLE(global.UISPACING.HEIGHT, 2);  // 2 - 30
        this.HEIGHT_B = SystemInfoManager.GET_SYSTEM_VALUE_DOUBLE(global.UISPACING.HEIGHT, 6);  // 6 - 50
        this.HEIGHT_C = SystemInfoManager.GET_SYSTEM_VALUE_DOUBLE(global.UISPACING.HEIGHT, 7);  // 7 - 100
        this.HEIGHT_D = SystemInfoManager.GET_SYSTEM_VALUE_DOUBLE(global.UISPACING.HEIGHT, 7);  // 7 - 100
        this.HEIGHT_E = SystemInfoManager.GET_SYSTEM_VALUE_DOUBLE(global.UISPACING.HEIGHT, 8);  // 8 - 200
        this.HEIGHT_F = SystemInfoManager.GET_SYSTEM_VALUE_DOUBLE(global.UISPACING.HEIGHT, 9);  // 9 - 400

        this.STYLE_FILE = SystemInfoManager.GET_SYSTEM_VALUE_STRING(global.BASICVALUESSTRING.STYLE, 0); // 0 - /CSS_Styles.css
        this.STYLE_A = SystemInfoManager.GET_SYSTEM_VALUE_STRING(global.BASICVALUESSTRING.STYLE, 11); // 11 - Schedule_UI_Base
        this.STYLE_B = SystemInfoManager.GET_SYSTEM_VALUE_STRING(global.BASICVALUESSTRING.STYLE, 12); // 12 - Schedule_UI_MainInputs
        this.STYLE_C = SystemInfoManager.GET_SYSTEM_VALUE_STRING(global.BASICVALUESSTRING.STYLE, 13); // 13 - Schedule_UI_IndividualInput
        this.STYLE_D = SystemInfoManager.GET_SYSTEM_VALUE_STRING(global.BASICVALUESSTRING.STYLE, 15); // 15 - Schedule_UI_ComboBox
        this.STYLE_E = SystemInfoManager.GET_SYSTEM_VALUE_STRING(global.BASICVALUESSTRING.STYLE, 1); // 1 - default-label
        // ############################################################
    




        // user Inputs for the schedule calculation
        // ############################################################
        // linked list of current user inputed people they want to include in the schedule(s) - used to update the schedule calculator
        this.SCHEDULE_IDS       = new LinkedList<>();
        // ammount fo schedules the user wants displayed
        this.SCHEDULE_LIST      = this.maxListAmmount;
        // String[] containg all user selected days
        this.SCHEDULE_DAYS      = new InputWeek();
        // holds a list of prefered times input by the user - max 4
        this.SCHEDULE_TIMES     = new LinkedList<>();
        // ############################################################

    } // PROG_UI_B_SchedulePeopleScene(Stage stage)


    
    /**
     * changetoSchedulingScene()
     * Description: Public method meant to be called outside the class in order to set the scene to the Scheduling scene
     */
    public void changetoSchedulingScene() {

        // fires the reset people button to ensure an up to date list of people in the file is always loaded in
        this.ButtonResetPeople.fire();

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

        // System Message
        // ############################################################
        // 5 - SUCCESS | 20 - SCENE_CREATE_Schedule | 8 - SYSTEM-SetScenes | 14 - Scene Switch to Scheduling page
        SystemInfoManager.GET_SYSTEM_MESSAGE(5,20,8,14);
        // ############################################################
        
    } // changetoSchedulingScene



    /**
     * ConstructSchedulingScene()
     * Description: Performs the necessary operations in order to build the various nodes/components of the stage.
     */
    public void ConstructSchedulingScene() {
        
        // Root Node creation
        // ############################################################
        // Create Node
        Schedule_RootNode = new AnchorPane();
        // get the CSS styles for the sub-nodes
        Schedule_RootNode.getStylesheets().add(getClass().getResource(this.STYLE_FILE).toExternalForm());
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
        // ############################################################

        // create Schedule Display  - Creates the output UI displaying created schedules for the user
        // ############################################################
        SchedulingDisplay();
        // ############################################################

        // Logic Setup              - Sets up internal system variables and scheduling logic. SHOULD ONLY BE CALLED ONCE
        // ############################################################
        LogicSetupAll();
        // ############################################################

        // Set Node position within Root Node
        // ############################################################
        // Root Node - set return home button position
        AnchorPane.setBottomAnchor  (ButtonReturnMenu,          this.ANCHOR_ONE);
        AnchorPane.setRightAnchor   (ButtonReturnMenu,          this.ANCHOR_ONE);

        // Root Node - set UI interface position
        AnchorPane.setTopAnchor     (ScrollPaneHolderUIInput,   this.ANCHOR_TWO);
        AnchorPane.setLeftAnchor    (ScrollPaneHolderUIInput,   this.ANCHOR_TWO);

        // Root Node - set Schedule Display position
        AnchorPane.setTopAnchor     (ScrollPaneHolderUIOutput,  this.ANCHOR_TWO);
        AnchorPane.setRightAnchor   (ScrollPaneHolderUIOutput,  this.ANCHOR_TWO);
        // ############################################################

        // Add UI to each root node
        // ############################################################
        Schedule_RootNode.getChildren().addAll(ScrollPaneHolderUIInput, ScrollPaneHolderUIOutput,  ButtonReturnMenu);
        // ############################################################

        // Background creation and application to the root node
        // ############################################################
        SetBackground();
        // ############################################################

        // Fade Transitions - must be called after every node is added to the root node
        // ############################################################
        fadeTransitions();
        // ############################################################

        // Scene creation to be set to the current scene
        // ############################################################
        this.SchedulingScene = new Scene(Schedule_RootNode, ManageScenes.WindowWidth, ManageScenes.WindowHeight);
        // ############################################################

        // fade all objects before the scene is set
        // ############################################################
        fadeMenuNodes.play();
        // ############################################################



        // System Message
        // ############################################################
        // 5 - SUCCESS | 20 - SCENE_CREATE_Schedule | 5 - SYSTEM-CreateScenes | 15 - Scene created and set
        SystemInfoManager.GET_SYSTEM_MESSAGE(5,20,5,15);
        // ############################################################

    } // ConstructSchedulingScene



    /**
     * LogicSetupAll()
     * Description: Sets up the internal logic used for scheduling
     */
    private void LogicSetupAll() {
        LogicSetUpInputPeople();        // IN USE
        LogicSetUpListNumber();         // IN USE
        LogicSetUpDayPreference();      // IN USE
        LogicSetupTimePreference();     // NOT IN USE
        LogicSetupCalculateSchedule();  // NOT IN USE
    } // LogicSetupAll()



    /**
     * LogicSetUpInputPeople()
     * Description: Sets up the combobox where the user adds people they want to schedule
     */
    private void LogicSetUpInputPeople() {

        this.FileUserInfo = SCHEDULER_FILES.readJson_FullPref();

        // System Message
        // ############################################################
        // 5 - SUCCESS | 19 - SCENE_CREATE_MainMenu | 10 - SYSTEM-FileAccess | 4 - try/catch File access Successful
        SystemInfoManager.GET_SYSTEM_MESSAGE(5,19,10,4);
        // ############################################################

        this.ComboBoxPeopleScheduleList     = new String[FileUserInfo.size()];
        this.ComboBoxPeopleScheduleListIDs  = new int[ComboBoxPeopleScheduleList.length];

        String formatID;
        String formatName;

        for (int Pos = 0; Pos < ComboBoxPeopleScheduleList.length; Pos++) {
            formatID = String.format("|ID: %-" + this.LENGTH_ID + "d |", FileUserInfo.get(Pos).GetIdent());
            formatName = String.format("|Name: %-" + this.LENGTH_NAME + "s |", FileUserInfo.get(Pos).GetName());
            // combines string and sets it to array
            this.ComboBoxPeopleScheduleList[Pos] = formatID + formatName; // Holds formatted string for combobox
            this.ComboBoxPeopleScheduleListIDs[Pos] = FileUserInfo.get(Pos).GetIdent(); // holds id of employees at relative position
        }

        // add list to combobox
        this.Selectable_PersonList.getItems().addAll(this.ComboBoxPeopleScheduleList);

        // adds event listener to the combobox so the selected positions index is captured and updated with user input
        // this is used to add/remove selected people from the schedule
        this.Selectable_PersonList.getSelectionModel().selectedIndexProperty().addListener((observable, oldPosition, newPosition) -> {
            ComboBoxPeopleSelectedPosition = newPosition.intValue();
            System.out.println(ComboBoxPeopleSelectedPosition);
        });

    } // LogicSetUpInputPeople()



    /**
     * LogicSetUpListNumber()
     * Description: Sets up the internal logic for the list value selection
     */
    private void LogicSetUpListNumber() {
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
                
                if (intValue >= 0 && intValue < this.maxListAmmount) {
                    return change;
                }

            } catch (NumberFormatException e) {
                // Invalid Input
            }

            return null;

        }));
        // ############################################################
    } // LogicSetUpListNumber()



    /**
     * LogicSetUpDayPreference()
     * Description: Sets up th internal logic for day preference selection
     */
    private void LogicSetUpDayPreference() {
        this.userInput_SelectDays.getItems().addAll(this.WEEKDAY);
        this.WeekDayOutputText = new Text();
        this.WeekDayOutputText.setText("");
        this.OutputDays.getChildren().add(this.WeekDayOutputText);
    } // LogicSetUpdayPreference()



    /**
     * LogicSetupTimePreference()
     * Description: Sets up th internal logic for the time preference selection
     */
    private void LogicSetupTimePreference() {
        // CURRENTLY NOT NEEDED
    } // LogicSetupTimePreference()



    /**
     * LogicSetupCalculateSchedule()
     * Desccription: Sets up the internal logic for the calculation of the schedule
     */
    private void LogicSetupCalculateSchedule() {
        // CURRENTLY NOT NEEDED
    } // LogicSetupCalculateSchedule()



    /**
     * SchedulingInterface()
     * Description: Creates the interface used for adding user preferences to the schedule. SHOULD ONLY BE CALLED ONCE
     */
    private void SchedulingInterface() {

        // UI Sub node creation
        // ############################################################
        InputPeople();          // UI Input for People
        InputListNumber();      // UI Input for List ammount
        InputDayPreference();   // UI Input for day preference
        InputTimePreference();  // UI Input for Time Preference
        CalculateSchedule();    // UI Input for calculaitng the schedule
        // ############################################################

        // Add all Nodes
        // ############################################################
        this.VBoxUI_INPUT.getChildren().addAll(
            // people Input
            VBoxUI_People,

            // List number
            VBoxUI_List,

            // day preference
            VBoxUI_WeekDay,

            // time input
            VBoxUI_Time,

            // reset preferences
            ButtonResetAll,

            // Calculate schedule
            VBoxUI_Calculation

        );
        // ############################################################

        
        // sets the UI Input within the scrllPane and adjusts preferences
        // ############################################################
        this.ScrollPaneHolderUIInput = new ScrollPane(this.VBoxUI_INPUT);
        
        // sets default interface dimensions
        this.ScrollPaneHolderUIInput.setPrefWidth((ManageScenes.WindowWidth / 2) - 80.0);
        this.ScrollPaneHolderUIInput.setPrefHeight(ManageScenes.WindowHeight - 100.0);

        this.ScrollPaneHolderUIInput.setFitToHeight(false);
        this.ScrollPaneHolderUIInput.setFitToWidth(false);

        // updates interface dimensions
        this.ApplicationStage.widthProperty().addListener((observed, oldWidth, newWidth) -> {
            ScrollPaneHolderUIInput.setPrefWidth((newWidth.intValue() / 2) - 80.0);
        });

        this.ApplicationStage.heightProperty().addListener((observed, oldHeight, newHeight) -> {
            ScrollPaneHolderUIInput.setPrefHeight(newHeight.intValue() - 100.0);
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
        HBox Primary_InputPeople    = new HBox(this.SPACING);
        // Primary Node Input   - Input for user
        VBox Input_InputPeople      = new VBox(this.SPACING);
        // Primary Node Output  - output of selected choiced
        VBox Output_InputPeople     = new VBox(this.SPACING);
        // Secondary Node       - Holds Reset Button
        VBox Secondary_InputPeople  = new VBox(this.SPACING);
        // comboBox to display the names available to select
        this.Selectable_PersonList  = new ComboBox<>();
        // flowpane node to hold te name list of selected people
        this.AddedPeople            = new FlowPane();
        // scrollpane to hold the flowpane AddedPeople
        this.ScrollAddedPeople      = new ScrollPane(this.AddedPeople);
        // ############################################################

        // Node set sizing
        // ############################################################
        Input_InputPeople           .setPrefSize(this.WIDTH_C, this.HEIGHT_C);
        Output_InputPeople          .setPrefSize(this.WIDTH_E, this.HEIGHT_C);
        this.Selectable_PersonList  .setPrefSize(this.WIDTH_C, this.HEIGHT_A);
        this.AddedPeople            .setPrefSize(this.WIDTH_A, this.HEIGHT_C);
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
        Label_inputPeople.getStyleClass().add(this.STYLE_E);
        Label_addedPeople.getStyleClass().add(this.STYLE_E);
        this.Selectable_PersonList.getStyleClass().add(this.STYLE_D);
        // ############################################################

        // Add all to the primary Node
        // ############################################################
        // Primary Node output
        Output_InputPeople  .getChildren().addAll(Label_addedPeople, ScrollAddedPeople); // AddedPeople
        // Primary Node Input
        Input_InputPeople   .getChildren().addAll(Label_inputPeople, Selectable_PersonList, this.ButtonInputPeople, this.ButtonRemoveLastPerson);
        // Primary Node
        Primary_InputPeople .getChildren().addAll(Input_InputPeople, Output_InputPeople);
        // ############################################################

        // Add all to the Secondary node
        // ############################################################
        Secondary_InputPeople.getChildren().addAll(this.ButtonResetPeople);
        // ############################################################

        // ADD primary and secondary nodes to the UI Holder VBoxUI_People
        // ############################################################
        this.VBoxUI_People.getChildren().addAll(Primary_InputPeople, Secondary_InputPeople);
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
        HBox Primary_InputList      = new HBox(this.SPACING);
        // Primary Node Input
        VBox Input_InputList        = new VBox(this.SPACING);
        // Primary Node Output
        VBox Output_InputList       = new VBox(this.SPACING);
        // Secondary Node
        HBox Secondary_InputList    = new HBox(this.SPACING);
        // Text Field Input
        this.userInput_listAmmount  = new TextField();
        // ############################################################
        
        // Node set sizing
        // ############################################################
        // Primary Node Input
        Input_InputList             .setPrefSize(this.WIDTH_C, this.HEIGHT_C);
        // Primary Node Output
        Output_InputList            .setPrefSize(this.WIDTH_E, this.HEIGHT_C);
        // Text Field Input
        this.userInput_listAmmount  .setPrefSize(this.WIDTH_C, this.WIDTH_C);
        // ############################################################

        // labels
        // ############################################################
        Label Label_inputNumber     = new Label("Input the total ammount of lists to Display");
        Label Label_AmmountOutput   = new Label("Ammount of schedules to be displayed");
        // ############################################################

        // styling
        // ############################################################
        Label_inputNumber.getStyleClass().add(this.STYLE_E);
        // ############################################################

        // Output
        // ############################################################
        // Stores the Ammount input variable
        this.Label_OutputNumber = new Label("");
        // ############################################################

        // Add all to the primary node
        // ############################################################
        // Secondary Node - output
        Output_InputList    .getChildren().addAll(Label_AmmountOutput, Label_OutputNumber);
        // secondary Node - Input
        Input_InputList     .getChildren().addAll(Label_inputNumber, userInput_listAmmount, this.ButtonInputList);
        // Primary Node
        Primary_InputList   .getChildren().addAll(Input_InputList, Output_InputList);
        // ############################################################

        // Add all to the secondary node
        // ############################################################
        Secondary_InputList.getChildren().addAll(this.ButtonResetList);
        // ############################################################

        // add all to the list UI VBoxUI_List
        // ############################################################
        VBoxUI_List.getChildren().addAll(Primary_InputList, Secondary_InputList);
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
        HBox Primary_InputDay       = new HBox(this.SPACING);
        // Primary Node input
        VBox Input_InputDay         = new VBox(this.SPACING);
        // Primary Node output
        VBox Output_InputDay        = new VBox(this.SPACING);
        // secondary node
        HBox Secondary_InputDay     = new HBox(this.SPACING);
        // combo box for days of the week, add multiple days
        this.userInput_SelectDays   = new ComboBox<>();
        // flowPane to hold and display selected day inputs
        this.OutputDays             = new FlowPane();
        // ############################################################

        // Node sizing
        // ############################################################
        // Primary Node input
        Input_InputDay              .setPrefSize(this.WIDTH_C, this.HEIGHT_C);
        // Primary Node output
        Output_InputDay             .setPrefSize(this.WIDTH_E, this.HEIGHT_C);
        // combo box for days of the week, add multiple days
        this.userInput_SelectDays   .setPrefSize(this.WIDTH_B, this.HEIGHT_A);
         // flowPane to hold and display selected day inputs
        this.OutputDays             .setPrefSize(this.WIDTH_B, this.HEIGHT_A);
        // ############################################################

        // label creation
        // ############################################################
        Label Label_inputDay    = new Label("Input the days to schedule");
        Label Label_OutputDay   = new Label("Days Selected");
        // ############################################################

        // Styling
        // ############################################################
        // labels
        Label_inputDay  .getStyleClass().add(this.STYLE_E);
        Label_OutputDay .getStyleClass().add(this.STYLE_E);
        // ############################################################

        // Add all to the primary node
        // ############################################################
        // Primary Node Output
        Output_InputDay .getChildren().addAll(Label_OutputDay, OutputDays);
        // Primary Node Input
        Input_InputDay  .getChildren().addAll(Label_inputDay, userInput_SelectDays, this.ButtonInputWeekDay);
        // Primary Node
        Primary_InputDay.getChildren().addAll(Input_InputDay, Output_InputDay);
        // ############################################################

        // add all to the secondary node
        // ############################################################
        Secondary_InputDay.getChildren().addAll(ButtonResetWeekDay);
        // ############################################################
        
        // Input all into UI Day Input this.VBoxUI_WeekDay.
        // ############################################################
        this.VBoxUI_WeekDay.getChildren().addAll(Primary_InputDay, Secondary_InputDay);
        // ############################################################

    } // InputDayPreference()





    /**
     * InputTimePreference()
     * Description: user input for the specific times they want scheduled
     */
    private void InputTimePreference() {

        // Node construction
        // ############################################################
        // INPUT
        this.SCHEDULER_TIME = new ManageDayTime(this.ButtonInputTime, false);
        VBox TimeInput = SCHEDULER_TIME.Return_UI_TimeInput();
        // OUTPUT
        this.FlowPane_VBoxDisplay = new FlowPane();
        this.FlowPane_VBoxDisplay.setMinSize( (this.WIDTH_G - 30.0), (this.HEIGHT_C + 5.0) );
        this.FlowPane_VBoxDisplay.setMaxSize( (this.WIDTH_G - 30.0), (this.HEIGHT_C + 5.0));
        this.FlowPane_VBoxDisplay.setPadding(new Insets(this.PADDING));
        this.FlowPane_VBoxDisplay.setHgap(5.0);
        this.FlowPane_VBoxDisplay.setVgap(2.0);
        this.FlowPane_VBoxDisplay.getStyleClass().add(this.STYLE_C);
        // // ############################################################

        // add all to Time Ui holder VBoxUI_Time
        // ############################################################
        this.VBoxUI_Time.getChildren().addAll(TimeInput, FlowPane_VBoxDisplay);
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
        HBox CalculateButtons   = new HBox(this.SPACING);
        // Secondary Node - input
        VBox Holder_Calculate   = new VBox(this.SPACING);
        // ############################################################

        // Input Section
        // ############################################################
        // label
        Label Label_ScheduleNow = new Label("Calculate Schedule");
        // ############################################################

        // Styling
        // ############################################################
        Label_ScheduleNow.getStyleClass().add(this.STYLE_E);
        // ############################################################

        // Add all to the nodes
        // ############################################################
        // seconary node input
        Holder_Calculate    .getChildren().addAll(Label_ScheduleNow);
        // primary node
        CalculateButtons    .getChildren().addAll(Holder_Calculate, this.ButtonCalculateSchedule, this.ButtonClearSchedule);
        // ############################################################

        // input all into the schedule UI
        // ############################################################
        this.VBoxUI_Calculation.getChildren().addAll(CalculateButtons);
        // ############################################################

    } // CalculateSchedule()


    


    /**
     * SchedulingDisplay()
     * Description: creates the display board for the finished schedules
     */
    private void SchedulingDisplay() {
        
        // scroll pane for scrolling between the vbox nodes
        this.ScrollPaneHolderUIOutput = new ScrollPane();
        this.ScrollPaneHolderUIOutput.setContent(VBOXUI_OUTPUT);
        this.ScrollPaneHolderUIOutput.setFitToWidth(true);
        this.ScrollPaneHolderUIOutput.setFitToHeight(true);

        // sets default interface dimensions
        this.ScrollPaneHolderUIOutput.setPrefWidth((ManageScenes.WindowWidth / 2) - 80.0);
        this.ScrollPaneHolderUIOutput.setPrefHeight(ManageScenes.WindowHeight - 100.0);

        // updates interface dimensions
        this.ApplicationStage.widthProperty().addListener((observed, oldWidth, newWidth) -> {
            ScrollPaneHolderUIOutput.setPrefWidth((newWidth.intValue() / 2) - 80.0);
        });

        this.ApplicationStage.heightProperty().addListener((observed, oldHeight, newHeight) -> {
            ScrollPaneHolderUIOutput.setPrefHeight(newHeight.intValue() - 100.0);
        });

        // observablelist to hold each schedule
        this.Schedules = FXCollections.observableArrayList();

       //this.VBOXUI_OUTPUT.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

       ScheduleOutputManage = new ManageScheduleOutput(VBOXUI_OUTPUT, SPACING, Schedules);


        Schedules.addListener((ListChangeListener<DataCompleteSchedule>) change -> {

            int ScheduleNumber = 0;

            while ((change.next()) && (ScheduleNumber < this.SCHEDULE_LIST) ) {

                if (change.wasAdded()) {


                    ScheduleNumber = ScheduleOutputManage.UpdateOutput(ScheduleNumber);
                    

                } // if (change.wasAdded())


            } // while (change.next)


        }); // event listner
        

    } // SchedulingDisplay()





    /**
     * IDConversion()
     * Description: Converts LinkedList of String into int[]
     */
    private int[] IDConversion(LinkedList<String> SCHEDULE_IDS) {
        
        int SetLength = SCHEDULE_IDS.size();
        int[] idsToReturn = new int[SetLength];

        for (int position = 0; position < SetLength; position++) {
            idsToReturn[position] = Integer.parseInt(SCHEDULE_IDS.get(position));
        }

        return idsToReturn.clone();
    }





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





    /**
     * NodeCreation()
     * Descrtiption:
     * creates the various nodes used throughout the scheduling scene.
     */
    private void NodeCreation() {

        // Contains All UI Input for selecting People
        // ############################################################
        this.VBoxUI_People = new VBox(this.SPACING);
        this.VBoxUI_People.getStyleClass().add(this.STYLE_B);
        this.VBoxUI_People.setMinSize(this.WIDTH_G, (HEIGHT_E - 20.0) );
        this.VBoxUI_People.setMaxSize(this.WIDTH_G, (HEIGHT_E - 20.0));
        this.VBoxUI_People.setPadding(new Insets(this.PADDING));
        // ############################################################

        // Contains all UI Inputs for selected the schedule List ammount
        // ############################################################
        this.VBoxUI_List = new VBox(this.SPACING);
        this.VBoxUI_List.getStyleClass().add(this.STYLE_B);
        this.VBoxUI_List.setMinSize(this.WIDTH_G, (HEIGHT_E - 20.0));
        this.VBoxUI_List.setMaxSize(this.WIDTH_G, (HEIGHT_E - 20.0));
        this.VBoxUI_List.setPadding(new Insets(this.PADDING));
        // ############################################################

        // Contains all UI inputs for selecting days for the schedule
        // ############################################################
        this.VBoxUI_WeekDay = new VBox(this.SPACING);
        this.VBoxUI_WeekDay.getStyleClass().add(this.STYLE_B);
        this.VBoxUI_WeekDay.setMinSize(this.WIDTH_G, (HEIGHT_E - 20.0));
        this.VBoxUI_WeekDay.setMaxSize(this.WIDTH_G, (HEIGHT_E - 20.0));
        this.VBoxUI_WeekDay.setPadding(new Insets(this.PADDING));
        // ############################################################

        // Contains all UI nodes for selecting times for the scheudle
        // ############################################################
        this.VBoxUI_Time = new VBox(this.SPACING);
        this.VBoxUI_Time.getStyleClass().add(this.STYLE_B);
        this.VBoxUI_Time.setMinSize(this.WIDTH_G, (this.HEIGHT_F - 20.0) );
        this.VBoxUI_Time.setMaxSize(this.WIDTH_G, (this.HEIGHT_F - 20.0));
        this.VBoxUI_Time.setPadding(new Insets(this.PADDING));
        // ############################################################

        // Contains all UI nodes for calculating the schedule
        // ############################################################
        this.VBoxUI_Calculation = new VBox(this.SPACING);
        this.VBoxUI_Calculation.getStyleClass().add(this.STYLE_B);
        this.VBoxUI_Calculation.setMinSize(this.WIDTH_G, this.HEIGHT_B);
        this.VBoxUI_Calculation.setMaxSize(this.WIDTH_G, this.HEIGHT_B);
        this.VBoxUI_Calculation.setPadding(new Insets(this.PADDING));
        // ############################################################

        // Contains All UI Input nodes and elements
        // ############################################################
        this.VBoxUI_INPUT = new VBox(this.SPACING);
        this.VBoxUI_INPUT.getStyleClass().add(this.STYLE_A);
        //this.VBoxUI_INPUT.setMinSize(this.WIDTH_G, 1200.0); // TODO
        this.VBoxUI_INPUT.setPadding(new Insets(this.PADDING));
        // ############################################################

        // Contains all schedule nodes
        // ############################################################
        this.VBOXUI_OUTPUT = new VBox(this.SPACING);
        this.VBOXUI_OUTPUT.getStyleClass().add(this.STYLE_A);
        this.VBOXUI_OUTPUT.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        this.VBOXUI_OUTPUT.setPadding(new Insets(this.PADDING));
        // ############################################################

    } //NodeCreation()





    /**
     * EventHandlerCreation()
     * Description: creates various events used within this scene.
     */
    private void EventHandlerCreation() {


        // return to the Home page
        // ############################################################
        this.EVENT_RETURN_HOME = event -> {
            
            fadeMenuNodes.setOnFinished(event2 -> {
                 ManageAppWindow.SceneManager.SwapToMainMenu(); 
            });

            fadeMenuNodes.play();



            // System Message
            // ############################################################
            // 5 - SUCCESS | 19 - SCENE_CREATE_MainMenu | 12 - USER-ButtonPress | 12 - Scene Switch to instruction page
            SystemInfoManager.GET_SYSTEM_MESSAGE(5,19,12,12);
            // ############################################################
        };
        // ############################################################



        // Resets the number of people used in the schedule
        // ############################################################
        this.EVENT_RESET_PEOPLE = event -> {

            this.FileUserInfo = SCHEDULER_FILES.readJson_FullPref();

            // System Message
            // ############################################################
            // 5 - SUCCESS | 19 - SCENE_CREATE_MainMenu | 10 - SYSTEM-FileAccess | 4 - try/catch File access Successful
            SystemInfoManager.GET_SYSTEM_MESSAGE(5,19,10,4);
            // ############################################################
    
            // remove all text from the flowpane
            this.AddedPeople.getChildren().clear();

            // clear the comboBox of all items
            this.Selectable_PersonList.getItems().clear();

            // Reset the comboBox with the full List
            this.ComboBoxPeopleScheduleList = new String[FileUserInfo.size()];
            this.ComboBoxPeopleScheduleListIDs = new int[ComboBoxPeopleScheduleList.length];

            String formatID;
            String formatName;

            for (int Pos = 0; Pos < ComboBoxPeopleScheduleList.length; Pos++) {
                
                formatID = String.format("|ID: %-" + this.LENGTH_ID + "d |", FileUserInfo.get(Pos).GetIdent());
                formatName = String.format("|Name: %-" + this.LENGTH_NAME + "s |", FileUserInfo.get(Pos).GetName());
                // combines string and sets it to array
                this.ComboBoxPeopleScheduleList[Pos] = formatID + formatName; // Holds formatted string for combobox
                this.ComboBoxPeopleScheduleListIDs[Pos] = FileUserInfo.get(Pos).GetIdent(); // holds id of employees at relative position
            }

            // add list to combobox
            this.Selectable_PersonList.getItems().addAll(this.ComboBoxPeopleScheduleList);

            // Reset the schedule calculator
            SCHEDULER_CALCULATOR.ResetPreference_People();

            // reset the linked list of user ids
            this.SCHEDULE_IDS.clear();



            // System Message
            // ############################################################
            // 5 - SUCCESS | 19 - SCENE_CREATE_MainMenu | 12 - USER-ButtonPress | 24 - people preference reset
            SystemInfoManager.GET_SYSTEM_MESSAGE(5,19,12,24);
            // ############################################################
        };
        // ############################################################



        // Add people to schedule
        // ############################################################
        EVENT_ADD_PEOPLE = event -> {

            if ( (Selectable_PersonList.getValue() != null) && (!Selectable_PersonList.getValue().isBlank()) && (!Selectable_PersonList.getValue().equals("SELECTED")) ) {

                // gets the String currently selected from the combobox
                String SelectedPerson = ComboBoxPeopleScheduleList[this.ComboBoxPeopleSelectedPosition];

                // gets the user id of the selected person
                String SelectedPersonID = String.valueOf(ComboBoxPeopleScheduleListIDs[this.ComboBoxPeopleSelectedPosition]);
                String SelectedPersonPosition = String.valueOf(this.ComboBoxPeopleSelectedPosition);
                int SelectedpositionInt = Integer.parseInt(SelectedPersonPosition);

                // removes that person from the combobox
                this.Selectable_PersonList.getItems().remove(SelectedPerson);
                // replaces the name with Selected, thi is necessary in order for position selection to remain functional
                this.Selectable_PersonList.getItems().add(SelectedpositionInt, this.SELECTED);

                // creates new text object with the string value of the combobox, with the relative position as its id so it can be retrieved later
                Text AddedPerson = new Text(SelectedPerson);
                AddedPerson.setId(SelectedPersonPosition);
                // adds them to the seleted list of people
                this.AddedPeople.getChildren().add(AddedPerson);


                // adds the id to the list of people to schedule
                SCHEDULE_IDS.add(SelectedPersonID); // add id as string
                int[] IDS_TOSchedule = IDConversion(SCHEDULE_IDS); // convert to linked list String to int[]

                // updates the scheduler with the updated list
                SCHEDULER_CALCULATOR.SetPreference_People(IDS_TOSchedule);



                // System Message
                // ############################################################
                // 5 - SUCCESS | 19 - SCENE_CREATE_MainMenu | 12 - USER-ButtonPress | 16 - Data has been added
                SystemInfoManager.GET_SYSTEM_MESSAGE(5,19,12,16);
                // ############################################################

            } else {

                // System Message
                // ############################################################
                // 6 - FAILURE | 19 - SCENE_CREATE_MainMenu | 12 - USER-ButtonPress | 17 - Data has not been added
                SystemInfoManager.GET_SYSTEM_MESSAGE(6,19,12,17);
                // ############################################################
            }

        };
        // ############################################################



        // Removes the last person added to the schedule
        // ############################################################
        this.EVENT_REMOVE_PERSON = event -> {

            // remove all text from the flowpane
            if ((this.AddedPeople.getChildren().size()) > 0) {

                // get the ID of the Text Object being removed from the flowpane
                int PositionToAdd = Integer.parseInt(((Text) this.AddedPeople.getChildren().getLast()).getId());

                // removes last person from the flowpane
                this.AddedPeople.getChildren().removeLast();

                // Reset the comboBox with the full List
                String PersonToAddComboBox= ComboBoxPeopleScheduleList[PositionToAdd];

                // removes the 'Selected' from the combobox
                this.Selectable_PersonList.getItems().remove(PositionToAdd);
                // Adds the employee value back to its original position within the list.
                this.Selectable_PersonList.getItems().add(PositionToAdd, PersonToAddComboBox);


                // if the list of people is more than 1 remove them, if its 1 or less, reset the list to prevent errors
                if (SCHEDULE_IDS.size() > 1) {

                    this.SCHEDULE_IDS.removeLast();
                    // temp
                    int[] IDS_TOSchedule = IDConversion(SCHEDULE_IDS);

                    SCHEDULER_CALCULATOR.SetPreference_People(IDS_TOSchedule);

                } else {

                    SCHEDULER_CALCULATOR.ResetPreference_People();
                    this.SCHEDULE_IDS.clear();
                }



                // System Message
                // ############################################################
                // 5 - SUCCESS | 19 - SCENE_CREATE_MainMenu | 12 - USER-ButtonPress | 18 - data has been removed
                SystemInfoManager.GET_SYSTEM_MESSAGE(5,19,12,18);
                // ############################################################

            } else {

                // System Message
                // ############################################################
                // 6 - FAILURE | 19 - SCENE_CREATE_MainMenu | 12 - USER-ButtonPress | 19 - Data has not been removed
                SystemInfoManager.GET_SYSTEM_MESSAGE(6,19,12,19);
                // ############################################################

            } // if ((this.AddedPeople.getChildren().size()) > 0)

        };
        // ############################################################



        // Reset The number of schedules the user wants displayed at most
        // ############################################################
        this.EVENT_RESET_List = event -> {
            
            // Label text value set to nothing
            this.Label_OutputNumber.setText("");
            // Schedule calculation variable set to default list ammout - 20
            this.SCHEDULE_LIST = this.maxListAmmount;

            // System Message
            // ############################################################
            // 5 - SUCCESS | 19 - SCENE_CREATE_MainMenu | 12 - USER-ButtonPress | 18 - data has been removed
            SystemInfoManager.GET_SYSTEM_MESSAGE(5,19,12,18);
            // ############################################################

        };
        // ############################################################



        // add list number the user wants
        // ############################################################
        this.EVENT_ADD_LIST = event -> {

            if ((userInput_listAmmount.getText() != null) && (!userInput_listAmmount.getText().isBlank())) {

                String SelectedAmmountString = userInput_listAmmount.getText();
                int SelectedammountInt = Integer.parseInt(SelectedAmmountString);

                this.Label_OutputNumber.setText(SelectedAmmountString);

                this.SCHEDULE_LIST = SelectedammountInt;



                // System Message
                // ############################################################
                // 5 - SUCCESS | 19 - SCENE_CREATE_MainMenu | 12 - USER-ButtonPress | 16 - Data has been added
                SystemInfoManager.GET_SYSTEM_MESSAGE(5,19,12,16);
                // ############################################################

            } else {

                // System Message
                // ############################################################
                // 6 - FAILURE | 19 - SCENE_CREATE_MainMenu | 12 - USER-ButtonPress | 17 - Data has not been added
                SystemInfoManager.GET_SYSTEM_MESSAGE(6,19,12,17);
                // ############################################################
            }

        };
        // ############################################################



        // Resets the selected days for the schedule
        // ############################################################
        this.EVENT_RESET_days = event -> {

            // Resets the flowPanes current list of selected days to an empty string
            this.WeekDayOutputText.setText("");
            // Resets the ComboBox Input values to their original state
            this.userInput_SelectDays.getItems().clear();
            this.userInput_SelectDays.getItems().addAll(this.WEEKDAY);

            // resets the list of user selected days
            this.SCHEDULE_DAYS.UnSetAll();

            // resets the schedule calcualtor to look through everyday of the week
            SCHEDULER_CALCULATOR.ResetPreference_Weekdays(); // input String[]



            // System Message
            // ############################################################
            // 5 - SUCCESS | 19 - SCENE_CREATE_MainMenu | 12 - USER-ButtonPress | 18 - data has been removed
            SystemInfoManager.GET_SYSTEM_MESSAGE(5,19,12,18);
            // ############################################################

        };
        // ############################################################



        // Adds a user selected day
        // ############################################################
        this.EVENT_ADD_DAYS = event -> {
            
            if ( (userInput_SelectDays.getValue() != null) && (!userInput_SelectDays.getValue().isBlank()) && (!userInput_SelectDays.getValue().equals("SELECTED")) ) {

                int UserSelectedPosition = userInput_SelectDays.getSelectionModel().selectedIndexProperty().intValue();

                // removes the selected day from the combobox
                this.userInput_SelectDays.getItems().remove(UserSelectedPosition);
                this.userInput_SelectDays.getItems().add(UserSelectedPosition, this.SELECTED);
                //this.SCHEDULE_DAYS[UserSelectedPosition] = this.WEEKDAY[UserSelectedPosition];
                this.SCHEDULE_DAYS.SetDay(UserSelectedPosition);
            
                // removes the null values from the string[] 
                String[] SelectedDays_SchedulerInput = Arrays.stream(this.SCHEDULE_DAYS.GetWeekArray()).filter(Objects::nonNull).toArray(String[]::new);

                // string to be displayed as text in the flowpane
                String DisplayText_days = String.join(" - ", SelectedDays_SchedulerInput);

                // sets the output text in the flowpane
                this.WeekDayOutputText.setText(DisplayText_days);

                // scheduler is updated with the new list of selected days
                SCHEDULER_CALCULATOR.SetPreference_Weekdays(this.SCHEDULE_DAYS.GetWeekArray());



                // System Message
                // ############################################################
                // 5 - SUCCESS | 19 - SCENE_CREATE_MainMenu | 12 - USER-ButtonPress | 16 - Data has been added
                SystemInfoManager.GET_SYSTEM_MESSAGE(5,19,12,16);
                // ############################################################

            } else {

                // System Message
                // ############################################################
                // 6 - FAILURE | 19 - SCENE_CREATE_MainMenu | 12 - USER-ButtonPress | 17 - Data has not been added
                SystemInfoManager.GET_SYSTEM_MESSAGE(6,19,12,17);
                // ############################################################
            }

        };
        // ############################################################


        // Resets the time inputs from the user
        // ############################################################
        this.EVENT_RESET_Times = event -> {

            // Reset List_UserTimes - clearing all elements in the Linked list
            this.SCHEDULE_TIMES.clear();
            
            // clear all elements in each node fo the flowpane
            for (Node node: FlowPane_VBoxDisplay.getChildren()) {
                if (node instanceof VBox vbox) {
                    vbox.getChildren().clear();
                }

            } // for()

            // Clear all node in the FlowPane
            FlowPane_VBoxDisplay.getChildren().clear();

            SCHEDULER_CALCULATOR.ResetPreference_Times();


            // System Message
            // ############################################################
            // 5 - SUCCESS | 19 - SCENE_CREATE_MainMenu | 12 - USER-ButtonPress | 18 - data has been removed
            SystemInfoManager.GET_SYSTEM_MESSAGE(5,19,12,18);
            // ############################################################
        };
        // ############################################################



        // Add time input from the user
        // ############################################################
        this.EVENT_ADD_TIME = event -> {

            // ButtonPressPartialTimeInput() ensures all variables have been input, returns 0 on success
            if ( (SCHEDULER_TIME.CHECK_PartialTimeInput() == 0) && (SCHEDULE_TIMES.size() < 4) ) {

                /**
                 * ############################################################
                 * PROG_UI_C_UserTimeInput SCHEDULER_TIME .TimeUserInputGraphic()
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
                SCHEDULER_TIME.UserInputGraphic(SCHEDULER_TIME.Return_UserPreference(), SCHEDULE_TIMES,  FlowPane_VBoxDisplay, false);
                // ############################################################

                // update the scheduler to the updated list of user times
                SCHEDULER_CALCULATOR.SetPreference_Times(SCHEDULE_TIMES);

                // System Message
                // ############################################################
                // 5 - SUCCESS | 19 - SCENE_CREATE_MainMenu | 12 - USER-ButtonPress | 16 - Data has been added
                SystemInfoManager.GET_SYSTEM_MESSAGE(5,19,12,16);
                // ############################################################

            } else {

                // System Message
                // ############################################################
                // 6 - FAILURE | 19 - SCENE_CREATE_MainMenu | 12 - USER-ButtonPress | 17 - Data has not been added
                SystemInfoManager.GET_SYSTEM_MESSAGE(6,19,12,17);
                // ############################################################
            }

        };
        // ############################################################




        // Calculates the user schedules
        // ############################################################
        this.EVENT_CALCULATE = event -> {

            // retrieved copy of calculated linked list
            this.CalculatedScheduleList = new LinkedList<>(SCHEDULER_CALCULATOR.RetrieveSchedule());

            // displays list up to SCHEDULE_LIST size
            for (int CalcScheduleIndex = 0; CalcScheduleIndex < this.SCHEDULE_LIST; CalcScheduleIndex++) {

                // esnures no out of bound exceptions occur
                if (CalcScheduleIndex < CalculatedScheduleList.size()) {
                    
                    Schedules.add(CalculatedScheduleList.get(CalcScheduleIndex));

                } // if (CalcScheduleIndex < CalculatedScheduleList.size())

            } // for (int CalcScheduleIndex = 0; CalcScheduleIndex < this.SCHEDULE_LIST; CalcScheduleIndex++)



            // System Message
            // ############################################################
            // 5 - SUCCESS | 19 - SCENE_CREATE_MainMenu | 12 - USER-ButtonPress | 25 - Calculating schedule
            SystemInfoManager.GET_SYSTEM_MESSAGE(5,19,12,25);
            // ############################################################
        };
        // ############################################################



        // Clears the schedule list
        // ############################################################
        this.EVENT_CLEAR_SCHEDULE = event -> {

            this.CalculatedScheduleList = new LinkedList<>();

            this.Schedules.clear();


            for (Node node : VBOXUI_OUTPUT.getChildren()) {
                if (node instanceof HBox hbox) {
                    hbox.getChildren().clear();
                }
            }

            VBOXUI_OUTPUT.getChildren().clear();



            // System Message
            // ############################################################
            // 5 - SUCCESS | 19 - SCENE_CREATE_MainMenu | 12 - USER-ButtonPress | 26 - Clearing schedule
            SystemInfoManager.GET_SYSTEM_MESSAGE(5,19,12,26);
            // ############################################################

        };
        // ############################################################




    } // EventHandlerCreation()





    /**
     * ButtonCreation()
     * Description: creates the various buttons used for the scheduling page setting
     */
    private void ButtonCreation() {

        // Return Home Button
        // ############################################################
        this.ButtonReturnMenu = new Button("Return Home");
        this.ButtonReturnMenu.setOnAction(this.EVENT_RETURN_HOME);
        this.ButtonReturnMenu.setPrefSize(this.WIDTH_B, this.HEIGHT_A);
        // ############################################################

        // People to schedule reset button
        // ############################################################
        this.ButtonResetPeople = new Button("Reset people to schedule");
        this.ButtonResetPeople.setOnAction(this.EVENT_RESET_PEOPLE);
        this.ButtonResetPeople.setPrefSize(this.WIDTH_B, this.HEIGHT_A);
        // ############################################################

        // Add people to schedule Button
        // ############################################################
        this.ButtonInputPeople = new Button("Input Selected Person");
        this.ButtonInputPeople.setOnAction(EVENT_ADD_PEOPLE);
        this.ButtonInputPeople.setPrefSize(this.WIDTH_B, this.HEIGHT_A);
        // ############################################################

        // People to schedule reset button
        // ############################################################
        this.ButtonRemoveLastPerson = new Button("Remove last person");
        this.ButtonRemoveLastPerson.setOnAction(EVENT_REMOVE_PERSON);
        this.ButtonRemoveLastPerson.setPrefSize(this.WIDTH_B, this.HEIGHT_A);
        // ############################################################

        // number of lists reset button
        // ############################################################
        this.ButtonResetList = new Button("Reset number of lists");
        this.ButtonResetList.setOnAction(EVENT_RESET_List);
        this.ButtonResetList.setPrefSize(this.WIDTH_B, this.HEIGHT_A);
        // ############################################################

        // Input number of schedule lists to display
        // ############################################################
        this.ButtonInputList = new Button("Input Selected Ammount");
        this.ButtonInputList.setOnAction(this.EVENT_ADD_LIST);
        this.ButtonInputList.setPrefSize(this.WIDTH_B, this.HEIGHT_A);
        // ############################################################

        // Reset number of days to schedule
        // ############################################################
        this.ButtonResetWeekDay = new Button("Reset days to schedule");
        this.ButtonResetWeekDay.setOnAction(EVENT_RESET_days);
        this.ButtonResetWeekDay.setPrefSize(this.WIDTH_B, this.HEIGHT_A);
        // ############################################################

        // Input selected day
        // ############################################################
        this.ButtonInputWeekDay = new Button("Input Selected Day");
        this.ButtonInputWeekDay.setOnAction(this.EVENT_ADD_DAYS);
        this.ButtonInputWeekDay.setPrefSize(this.WIDTH_B, this.HEIGHT_A);
        // ############################################################

        // Reset specific input times
        // ############################################################
        this.ButtonResetTime = new Button("Reset time input");
        this.ButtonResetTime.setOnAction(EVENT_RESET_Times);
        this.ButtonResetTime.setPrefSize(this.WIDTH_B, this.HEIGHT_A);
        // ############################################################

        // Add Time info
        // ############################################################
        this.ButtonInputTime = new Button("Add Info");
        this.ButtonInputTime.setOnAction(EVENT_ADD_TIME);
        this.ButtonInputTime.setPrefSize(this.WIDTH_B, this.HEIGHT_A);
        // ############################################################

        // Reset all preferences
        // ############################################################
        this.ButtonResetAll = new Button("Reseting all Preferences");
        this.ButtonResetAll.addEventHandler(ActionEvent.ACTION, EVENT_RESET_PEOPLE);
        this.ButtonResetAll.addEventHandler(ActionEvent.ACTION, EVENT_RESET_List);
        this.ButtonResetAll.addEventHandler(ActionEvent.ACTION, EVENT_RESET_days);
        this.ButtonResetAll.addEventHandler(ActionEvent.ACTION, EVENT_RESET_Times);
        this.ButtonResetAll.setPrefSize(this.WIDTH_B, this.HEIGHT_A);
        // ############################################################

        // Calculate Schedule
        // ############################################################
        this.ButtonCalculateSchedule = new Button("Calculate Schedule");
        this.ButtonCalculateSchedule.setOnAction(EVENT_CALCULATE);
        this.ButtonCalculateSchedule.setPrefSize(this.WIDTH_B, this.HEIGHT_A);
        // ############################################################

        // Clear Schedule
        // ############################################################
        this.ButtonClearSchedule = new Button("Clear Schedule list");
        this.ButtonClearSchedule.setOnAction(EVENT_CLEAR_SCHEDULE);
        this.ButtonClearSchedule.setPrefSize(this.WIDTH_B, this.HEIGHT_A);
        // ############################################################

    } // ButtonCreation()
    




} // SceneScheduling {}