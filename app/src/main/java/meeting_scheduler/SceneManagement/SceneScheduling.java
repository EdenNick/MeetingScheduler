// Package  - DO Not Change
// ############################################################
package meeting_scheduler.SceneManagement;
// ############################################################

// Imports
// ############################################################
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import javafx.util.Duration;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import meeting_scheduler.SystemInfoManager;
import meeting_scheduler.DataHolder.PreferenceFull;
import meeting_scheduler.DataHolder.PreferenceTime;
import meeting_scheduler.FileManagement.ManageJsonFile;
import meeting_scheduler.ScheduleManagement.ManageScheduleCalculation;
import meeting_scheduler.ScheduleManagement.DataCompleteSchedule;
import meeting_scheduler.SystemManagement.ManageAppWindow;
import meeting_scheduler.SystemManagement.ManageScenes;
import meeting_scheduler.UserInput.ManageDayTime;
import meeting_scheduler.global;
// ############################################################



public class SceneScheduling {

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
    private LinkedList<PreferenceFull>  FileUserInfo;
    private LinkedList<String>                  PersonList;
    private LinkedList<PreferenceFull>  FilePeople;
    // ############################################################


    // Data manager Objects
    // ############################################################
    // Schedule Calculator
    private final ManageScheduleCalculation      ScheduleCalculator;
    // User Time Input manager
    private ManageDayTime    Scheduler_UserTimeInputs;
    // Json File manager
    private final ManageJsonFile        Scheduler_fileReader;
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

    // Day input
    private ComboBox<String>                    userInput_SelectDays;   // combobox for the user to select which days they want to scheudle on
    private FlowPane                            OutputDays;             // flowpane ontains the input days the user wants selected
    // day output
    private String[]                            SCHEDULE_DAYS;


    // time input 
    private FlowPane                            FlowPane_VBoxDisplay;   // dispalys time inputs
    //private LinkedList<VBox>                    List_VBoxTimeInputs;    // contains a set of user prefered times - used exclusivley for iteration
    // time output
    private LinkedList<PreferenceTime>    SCHEDULE_TIMES;         // List of prefered times for an individual
    // ############################################################


    // Calculated Schedules
    // ############################################################
    private LinkedList<DataCompleteSchedule>     CalculatedScheduleList;
    private ObservableList<DataCompleteSchedule> Schedules;
    // ############################################################




    private int maxListAmmount = SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_INT(global.BASICVALUESINT.INT, 8); // 4 - 20
    private String Style      = SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_STRING(global.BASICVALUESSTRING.STRING, 15); // 15 - /CSS_Styles.css
    private double DefaultAnchor      = SystemInfoManager.GET_SYSTEM_UI_SPACING(global.UISPACING.ANCHOR, 3); // 3 - 20
    private double SecondaryAnchor      = SystemInfoManager.GET_SYSTEM_UI_SPACING(global.UISPACING.ANCHOR, 1); // 3 - 10
    private double Spacing        = SystemInfoManager.GET_SYSTEM_UI_SPACING(global.UISPACING.SPACING, 0); // 2 - 10
    private double Padding        = SystemInfoManager.GET_SYSTEM_UI_SPACING(global.UISPACING.PADDING, 1); // 1 - 10
    private String StyleOne   = SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_STRING(global.BASICVALUESSTRING.STRING, 26); // 26 - Schedule_UI_Base
    private String Styletwo   = SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_STRING(global.BASICVALUESSTRING.STRING, 27); // 27 - Schedule_UI_MainInputs
    private String Stylethree   = SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_STRING(global.BASICVALUESSTRING.STRING, 28); // 28 - Schedule_UI_IndividualInput
    private String StyleFour   = SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_STRING(global.BASICVALUESSTRING.STRING, 29); // 29 - Schedule_UI_NameFlowBox
    private String StyleFive   = SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_STRING(global.BASICVALUESSTRING.STRING, 30); // 30 - Schedule_UI_ComboBox
    private String StyleSix   = SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_STRING(global.BASICVALUESSTRING.STRING, 31); // 31 - Schedule_UI_ScheduleListBox
     private String StyleSeven      = SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_STRING(global.BASICVALUESSTRING.STRING, 15); // 16 - default-label
    private double widthOne     = SystemInfoManager.GET_SYSTEM_UI_SPACING(global.UISPACING.WIDTH, 7); // 7 - 600
    private double heightOne    = SystemInfoManager.GET_SYSTEM_UI_SPACING(global.UISPACING.HEIGHT, 7); // 7 - 100
    private double widthTwo     = SystemInfoManager.GET_SYSTEM_UI_SPACING(global.UISPACING.WIDTH, 2); // 2 - 150
    private double heightTwo    = SystemInfoManager.GET_SYSTEM_UI_SPACING(global.UISPACING.HEIGHT, 2); // 2 - 30
    private double widththree     = SystemInfoManager.GET_SYSTEM_UI_SPACING(global.UISPACING.WIDTH, 4); // 4 - 250
    private double heightthree    = SystemInfoManager.GET_SYSTEM_UI_SPACING(global.UISPACING.HEIGHT, 7); // 7 - 100
    private double widthfour     = SystemInfoManager.GET_SYSTEM_UI_SPACING(global.UISPACING.WIDTH, 5); // 5 - 300
    private double heightfour    = SystemInfoManager.GET_SYSTEM_UI_SPACING(global.UISPACING.HEIGHT, 7); // 7 - 100
    private double widthfive     = SystemInfoManager.GET_SYSTEM_UI_SPACING(global.UISPACING.WIDTH, 4); // 4 - 250
    private double heightfive    = SystemInfoManager.GET_SYSTEM_UI_SPACING(global.UISPACING.HEIGHT, 2); // 2 - 30
    private double widthSix     = SystemInfoManager.GET_SYSTEM_UI_SPACING(global.UISPACING.WIDTH, 1); // 1 - 100
    private double heightSix   = SystemInfoManager.GET_SYSTEM_UI_SPACING(global.UISPACING.HEIGHT, 7); // 7 - 100





    private String[] WEEKDAY = SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_WEEKARRAY(global.WEEKTYPE.SHORT);
    private int WEEKDAYLength = SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_INT(global.BASICVALUESINT.INT, 0); // 0 - 7


    /**
     * Constructor class
     */
    public SceneScheduling(Stage stage) {

        // Primary Objects
        // ############################################################
        // application stage    - Obejct containing all contents of the page, effectivley the applciaiton window
        this.ApplicationStage           = stage;
        
        // Schedule Calculator  - Object used to calculate viable schedules based off of input user preferences
        this.ScheduleCalculator         = new ManageScheduleCalculation();

        // User time inputs     - Object which is used to create the necessary input ui for user time inputs, verifies correct input
        // contains methods used to store and dispaly this information. In this case it is used to input correct times to create a schedule
        //this.Scheduler_UserTimeInputs   = new USERINPUT_TimeInputManager(); // TODO: schedule calculator

        // Json file Reader     - Object which can access the relevant Json file to retireve user info. 
        // Used to retrieve current user preferences to create a schedule
        this.Scheduler_fileReader       = new ManageJsonFile();
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
        this.SCHEDULE_LIST      = this.maxListAmmount;
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

        // fires the reset people button to ensure an up to date list of people in the file is always loaded in
        this.RESET_People.fire();

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
        // 5 - SUCCESS | 20 - SCENE_CREATE_Schedule | 8 - SYSTEM-SetScenes | 14 - Scene Switch to Scheduling page
        SystemInfoManager.GET_SYSTEM_MESSAGE(5,20,8,14);
        
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
        Schedule_RootNode.getStylesheets().add(getClass().getResource(this.Style).toExternalForm());
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
        //Scheduler_UserTimeInputs.UI_data_construction();
        // ############################################################


        // create Schedule Display  = Creates the output UI displaying created schedules for the user
        // ############################################################
        SchedulingDisplay();
        // ############################################################


        // Set Node position within Root Node
        // ############################################################
        // Root Node - set return home button position
        AnchorPane.setBottomAnchor  (RETURN_ToMenu,                     this.SecondaryAnchor);
        AnchorPane.setRightAnchor   (RETURN_ToMenu,                     this.SecondaryAnchor);

        // Root Node - set UI interface position
        AnchorPane.setTopAnchor     (UIInput_FullUIHolder_ScrollPane,   this.DefaultAnchor);
        AnchorPane.setLeftAnchor    (UIInput_FullUIHolder_ScrollPane,   this.DefaultAnchor);

        // Root Node - set Schedule Display position
        AnchorPane.setTopAnchor     (UIOutput_FullUIHolder_scrollPane,  this.DefaultAnchor);
        AnchorPane.setRightAnchor   (UIOutput_FullUIHolder_scrollPane,  this.DefaultAnchor);
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
        this.SchedulingScene = new Scene(Schedule_RootNode, ManageScenes.WindowWidth, ManageScenes.WindowHeight);
        // ############################################################


        // fade all objects before the scene is set
        // ############################################################
        fadeMenuNodes.play();
        // ############################################################


        // System Message
        // 5 - SUCCESS | 20 - SCENE_CREATE_Schedule | 5 - SYSTEM-CreateScenes | 15 - Scene created and set
        SystemInfoManager.GET_SYSTEM_MESSAGE(5,20,5,15);

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
        this.UIInput_FullUI_VBOX = new VBox(this.Spacing);
        this.UIInput_FullUI_VBOX.getStyleClass().add(this.StyleOne);
        this.UIInput_FullUI_VBOX.setPadding(new Insets(this.Padding));
        // ############################################################



        // Contains All UI Input for selecting People
        // ############################################################
        this.UIInput_PeopleUI_VBOX = new VBox(this.Spacing);
        this.UIInput_PeopleUI_VBOX.getStyleClass().add(this.Styletwo);
        this.UIInput_PeopleUI_VBOX.setPrefSize(this.widthOne, this.heightOne);
        this.UIInput_PeopleUI_VBOX.setPadding(new Insets(this.Padding));
        // ############################################################



        // Contains all UI Inputs for selected the schedule List ammount
        // ############################################################
        this.UIInput_ListUI_VBOX = new VBox(this.Spacing);
        this.UIInput_ListUI_VBOX.getStyleClass().add(this.Styletwo);
        this.UIInput_ListUI_VBOX.setPrefSize(this.widthOne, this.heightOne);
        this.UIInput_ListUI_VBOX.setPadding(new Insets(this.Padding));
        // ############################################################



        // Contains all UI inputs for selecting days for the schedule
        // ############################################################
        this.UIInput_DayUI_VBOX = new VBox(this.Spacing);
        this.UIInput_DayUI_VBOX.getStyleClass().add(this.Styletwo);
        this.UIInput_DayUI_VBOX.setPrefSize(this.widthOne, this.heightOne);
        this.UIInput_DayUI_VBOX.setPadding(new Insets(this.Padding));
        // ############################################################



        // Contains all UI nodes for selecting times for the scheudle
        // ############################################################
        this.UIInput_TimeUI_VBOX = new VBox(this.Spacing);
        this.UIInput_TimeUI_VBOX.getStyleClass().add(this.Styletwo);
        this.UIInput_TimeUI_VBOX.setPrefSize(this.widthOne, this.heightOne);
        this.UIInput_TimeUI_VBOX.setPadding(new Insets(this.Padding));
        // ############################################################



        // Contains all UI nodes for calculating the schedule
        // ############################################################
        this.UIInput_CalculateUI_VBOX = new VBox(this.Spacing);
        this.UIInput_CalculateUI_VBOX.getStyleClass().add(this.Styletwo);
        this.UIInput_CalculateUI_VBOX.setPrefSize(this.widthOne, this.heightOne);
        this.UIInput_CalculateUI_VBOX.setPadding(new Insets(this.Padding));
        // ############################################################





        // UIOutput_FullUIHolder_scrollPane
        // ############################################################

        // Contains all schedule nodes
        // ############################################################
        this.UIOutput_FullUI_VBOX = new VBox(this.Spacing);
        this.UIOutput_FullUI_VBOX.getStyleClass().add(this.StyleOne);
        this.UIOutput_FullUI_VBOX.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        this.UIOutput_FullUI_VBOX.setPadding(new Insets(this.Padding));
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
            
            fadeMenuNodes.setOnFinished(event2 -> {
                 ManageAppWindow.SceneManager.SwapToMainMenu(); 
            });

            fadeMenuNodes.play();

            // System Message
            // 5 - SUCCESS | 19 - SCENE_CREATE_MainMenu | 12 - USER-ButtonPress | 12 - Scene Switch to instruction page
            SystemInfoManager.GET_SYSTEM_MESSAGE(5,19,12,12);
        };
        // ############################################################



        // Resets the number of people used in the schedule
        // ############################################################
        this.EVENT_RESET_People = event -> {

            // Retrieve the list from the file reader
            try {
				this.FileUserInfo = Scheduler_fileReader.ReadFrom_DefaultEmployeePreference();
			} catch (StreamReadException e) {
				e.printStackTrace();
			} catch (DatabindException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
        
            // LinkedList of all people in the file showing both ID and full name
            this.PersonList.clear();

            // Add people to the list
            for (PreferenceFull FilePerson : FileUserInfo) {
                String format = String.format("|ID: %-7d", FilePerson.GetIdent());
                PersonList.add(format + "| Name: " + FilePerson.GetName());
            }

            // remove all text from the flowpane
            this.AddedPeople.getChildren().clear();

            // clear the comboBox of all items
            this.Selectable_PersonList.getItems().clear();

            // Reset the comboBox with the full List
            this.Selectable_PersonList.getItems().addAll(PersonList);

            // Reset the schedule calculator
            ScheduleCalculator.ResetPreference_People();

            // reset the linked list of user ids
            this.SCHEDULE_IDS = new LinkedList<>();

            // System Message
            // 5 - SUCCESS | 19 - SCENE_CREATE_MainMenu | 12 - USER-ButtonPress | 24 - people preference reset
            SystemInfoManager.GET_SYSTEM_MESSAGE(5,19,12,24);
        };
        // ############################################################



        // Add people to schedule
        // ############################################################
        EVENT_ADD_People = event -> {

            //TODO fix
            if ((Selectable_PersonList.getValue() != null) && (!Selectable_PersonList.getValue().isBlank())) {

                // gets the person selected from the combobox
                String SelectedPerson = Selectable_PersonList.getValue();


                // removes that person from the combobox
                this.Selectable_PersonList.getItems().remove(SelectedPerson);

                // adds them to the seleted list of people
                this.AddedPeople.getChildren().add(new Text(SelectedPerson));

                // gets the user if of the selected person
                String[] getID = SelectedPerson.split("\\s+");


                // adds the id to the list of people to schedule
                SCHEDULE_IDS.add(getID[1]); // add id as string
                
                int[] IDS_TOSchedule = IDConversion(SCHEDULE_IDS);



                // updates the scheduler with the updated list
                ScheduleCalculator.SetPreference_People(IDS_TOSchedule);

                // System Message
                // 5 - SUCCESS | 19 - SCENE_CREATE_MainMenu | 12 - USER-ButtonPress | 16 - Data has been added
                SystemInfoManager.GET_SYSTEM_MESSAGE(5,19,12,16);

            } // if()

            // System Message
            // 6 - FAILURE | 19 - SCENE_CREATE_MainMenu | 12 - USER-ButtonPress | 17 - Data has not been added
            SystemInfoManager.GET_SYSTEM_MESSAGE(6,19,12,17);

        };
        // ############################################################



        // Removes the last person added to the schedule
        // ############################################################
        this.EVENT_REMOVE_Person = event -> {

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
                    // temp
                    int[] IDS_TOSchedule = IDConversion(SCHEDULE_IDS);

                    ScheduleCalculator.SetPreference_People(IDS_TOSchedule);

                } else {

                    ScheduleCalculator.ResetPreference_People();
                    this.SCHEDULE_IDS = new LinkedList<>();
                }

                // System Message
                // 5 - SUCCESS | 19 - SCENE_CREATE_MainMenu | 12 - USER-ButtonPress | 18 - data has been removed
                SystemInfoManager.GET_SYSTEM_MESSAGE(5,19,12,18);

            } // if ((this.AddedPeople.getChildren().size()) > 0)

            // System Message
            // 6 - FAILURE | 19 - SCENE_CREATE_MainMenu | 12 - USER-ButtonPress | 19 - Data has not been removed
            SystemInfoManager.GET_SYSTEM_MESSAGE(6,19,12,19);

        };
        // ############################################################



        // Reset The number of schedules the user wants displayed at most
        // ############################################################
        this.EVENT_RESET_List = event -> {
            
            // TODO: find a better way to do this.
            // Text value set to nothing
            this.Label_OutputNumber.setText("");

            // System Message
            // 5 - SUCCESS | 19 - SCENE_CREATE_MainMenu | 12 - USER-ButtonPress | 18 - data has been removed
            SystemInfoManager.GET_SYSTEM_MESSAGE(5,19,12,18);

        };
        // ############################################################



        // add list number the user wants
        // ############################################################
        this.EVENT_ADD_List = event -> {

            if ((userInput_listAmmount.getText() != null) && (!userInput_listAmmount.getText().isBlank())) {

                String SelectedAmmount = userInput_listAmmount.getText();

                Label_OutputNumber.setText(SelectedAmmount);

                SCHEDULE_LIST = Integer.parseInt(SelectedAmmount);

                // System Message
                // 5 - SUCCESS | 19 - SCENE_CREATE_MainMenu | 12 - USER-ButtonPress | 16 - Data has been added
                SystemInfoManager.GET_SYSTEM_MESSAGE(5,19,12,16);

            } // if()

            // System Message
            // 6 - FAILURE | 19 - SCENE_CREATE_MainMenu | 12 - USER-ButtonPress | 17 - Data has not been added
            SystemInfoManager.GET_SYSTEM_MESSAGE(6,19,12,17);

        };
        // ############################################################



        // Resets the selected days for the schedule
        // ############################################################
        this.EVENT_RESET_days = event -> {

            // Resets the flowPanes current list of selected days to an empty string
            this.OutputDays.getChildren().clear();
            this.OutputDays.getChildren().add(new Text(""));

            // Resets the ComboBox Input values to their original state
            this.userInput_SelectDays.getItems().clear();
            this.userInput_SelectDays.getItems().addAll(this.WEEKDAY);

            // resets the list of user selected days
            this.SCHEDULE_DAYS  = new String[7];

            // resets the schedule calcualtor to look through everyday of the week
            ScheduleCalculator.ResetPreference_Weekdays(); // input String[]

            // System Message
            // 5 - SUCCESS | 19 - SCENE_CREATE_MainMenu | 12 - USER-ButtonPress | 18 - data has been removed
            SystemInfoManager.GET_SYSTEM_MESSAGE(5,19,12,18);

        };
        // ############################################################



        // Adds a user selected day TODO fix weekdaylist
        // ############################################################
        this.EVENT_ADD_Days = event -> {
            
            if ((userInput_SelectDays.getValue() != null) && (!userInput_SelectDays.getValue().isBlank())) {

                // retireves the selected user value
                String UserInput_day = userInput_SelectDays.getValue();

                // removes the selected day from the combobox
                this.userInput_SelectDays.getItems().remove(UserInput_day);


                // adds the day to the string[] containing all selected user days
                for (int WeekdayIndex = 0; WeekdayIndex < this.WEEKDAYLength; WeekdayIndex++) {

                    if (UserInput_day.equals(this.WEEKDAY[WeekdayIndex])) {
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
                ScheduleCalculator.SetPreference_Weekdays(SelectedDays_SchedulerInput);

                // System Message
                // 5 - SUCCESS | 19 - SCENE_CREATE_MainMenu | 12 - USER-ButtonPress | 16 - Data has been added
                SystemInfoManager.GET_SYSTEM_MESSAGE(5,19,12,16);

            } // if()

            // System Message
            // 6 - FAILURE | 19 - SCENE_CREATE_MainMenu | 12 - USER-ButtonPress | 17 - Data has not been added
            SystemInfoManager.GET_SYSTEM_MESSAGE(6,19,12,17);

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

            ScheduleCalculator.ResetPreference_Times();

            // System Message
            // 5 - SUCCESS | 19 - SCENE_CREATE_MainMenu | 12 - USER-ButtonPress | 18 - data has been removed
            SystemInfoManager.GET_SYSTEM_MESSAGE(5,19,12,18);
        };
        // ############################################################



        // Add time input from the user
        // ############################################################
        this.EVENT_ADD_timeInput = event -> {

            // ButtonPressPartialTimeInput() ensures all variables have been input, returns 0 on success
            if ( (Scheduler_UserTimeInputs.CHECK_PartialTimeInput() == 0) && (SCHEDULE_TIMES.size() < 4) ){

                // Temp user preference created for clean seperation of object use
                PreferenceTime TempUserPreferrence = Scheduler_UserTimeInputs.Return_UserPreference();


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
                Scheduler_UserTimeInputs.UserInputGraphic(TempUserPreferrence, SCHEDULE_TIMES,  FlowPane_VBoxDisplay, false);
                // ############################################################


                // garbage Collection
                TempUserPreferrence = null;


                // update the scheduler to the updated list of user times
                ScheduleCalculator.SetPreference_Times(SCHEDULE_TIMES);

                // System Message
                // 5 - SUCCESS | 19 - SCENE_CREATE_MainMenu | 12 - USER-ButtonPress | 16 - Data has been added
                SystemInfoManager.GET_SYSTEM_MESSAGE(5,19,12,16);

            } else {
                // System Message
                // 6 - FAILURE | 19 - SCENE_CREATE_MainMenu | 12 - USER-ButtonPress | 17 - Data has not been added
                SystemInfoManager.GET_SYSTEM_MESSAGE(6,19,12,17);
            }

        };
        // ############################################################




        // Calculates the user schedules
        // ############################################################
        this.EVENT_CALCULATE_Schedule = event -> {

            // retrieved copy of calculated linked list
            this.CalculatedScheduleList = new LinkedList<>(ScheduleCalculator.RetrieveSchedule());

            // displays list up to SCHEDULE_LIST size
            for (int CalcScheduleIndex = 0; CalcScheduleIndex < this.SCHEDULE_LIST; CalcScheduleIndex++) {

                // esnures no out of bound exceptions occur
                if (CalcScheduleIndex < CalculatedScheduleList.size()) {
                    
                    Schedules.add(CalculatedScheduleList.get(CalcScheduleIndex));

                } // if (CalcScheduleIndex < CalculatedScheduleList.size())

            } // for (int CalcScheduleIndex = 0; CalcScheduleIndex < this.SCHEDULE_LIST; CalcScheduleIndex++)

            // System Message
            // 5 - SUCCESS | 19 - SCENE_CREATE_MainMenu | 12 - USER-ButtonPress | 25 - Calculating schedule
            SystemInfoManager.GET_SYSTEM_MESSAGE(5,19,12,25);
        };
        // ############################################################



        // Clears the schedule list
        // ############################################################
        this.EVENT_CLEAR_schedule = event -> {

            this.CalculatedScheduleList = new LinkedList<>();

            this.Schedules.clear();


            for (Node node : UIOutput_FullUI_VBOX.getChildren()) {
                if (node instanceof HBox hbox) {
                    hbox.getChildren().clear();
                }
            }

            UIOutput_FullUI_VBOX.getChildren().clear();

            // System Message
            // 5 - SUCCESS | 19 - SCENE_CREATE_MainMenu | 12 - USER-ButtonPress | 26 - Clearing schedule
            SystemInfoManager.GET_SYSTEM_MESSAGE(5,19,12,26);

        };
        // ############################################################




    }

    // TEMP function
    private int[] IDConversion(LinkedList<String> SCHEDULE_IDS) {
        
        String[] Converted = SCHEDULE_IDS.toArray(new String[0]);

        int[] idsToReturn = new int[Converted.length];

        for (int position = 0; position < Converted.length; position++) {
            idsToReturn[position] = Integer.parseInt(Converted[position]);
        }

        return idsToReturn.clone();
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
        this.RETURN_ToMenu.setPrefSize(this.widthTwo, this.heightTwo);
        // ############################################################



        // Adding removing people

        // People to schedule reset button
        // ############################################################
        this.RESET_People           = new Button("Reset people to schedule");
        this.RESET_People.setOnAction(this.EVENT_RESET_People);
        this.RESET_People.setPrefSize(this.widthTwo, this.heightTwo);
        // ############################################################

        // Add people to schedule Button
        // ############################################################
        this.Input_SelectedPeople   = new Button("Input Selected Ammount");
        this.Input_SelectedPeople.setOnAction(EVENT_ADD_People);
        this.Input_SelectedPeople.setPrefSize(this.widthTwo, this.heightTwo);
        // ############################################################

        // People to schedule reset button
        // ############################################################
        this.REMOVELastPerson       = new Button("Remove last person");
        this.REMOVELastPerson.setOnAction(EVENT_REMOVE_Person);
        this.REMOVELastPerson.setPrefSize(this.widthTwo, this.heightTwo);
        // ############################################################



        // Adding removing list ammount

        // number of lists reset button
        // ############################################################
        this.RESET_ListNumber       = new Button("Reset number of lists");
        this.RESET_ListNumber.setOnAction(EVENT_RESET_List);
        this.RESET_ListNumber.setPrefSize(this.widthTwo, this.heightTwo);
        // ############################################################

        // Input number of schedule lists to display
        // ############################################################
        this.INPUT_SelectedNumber = new Button("Input Selected Ammount");
        this.INPUT_SelectedNumber.setOnAction(this.EVENT_ADD_List);
        this.INPUT_SelectedNumber.setPrefSize(this.widthTwo, this.heightTwo);
        // ############################################################


        // Adding removing days to schedule on

        // Reset number of days to schedule
        // ############################################################
        this.RESET_DaySelection     = new Button("Reset days to schedule");
        this.RESET_DaySelection.setOnAction(EVENT_RESET_days);
        this.RESET_DaySelection.setPrefSize(this.widthTwo, this.heightTwo);
        // ############################################################

        // Input selected day
        // ############################################################
        this.INPUT_SelectedDay = new Button("Input Selected Day");
        this.INPUT_SelectedDay.setOnAction(this.EVENT_ADD_Days);
        this.INPUT_SelectedDay.setPrefSize(this.widthTwo, this.heightTwo);
        // ############################################################



        // Reset specific input times
        // ############################################################
        this.RESET_TimeInput        = new Button("Reset time input");
        this.RESET_TimeInput.setOnAction(EVENT_RESET_Times);
        this.RESET_TimeInput.setPrefSize(this.widthTwo, this.heightTwo);
        // ############################################################



        // Add Time info
        // ############################################################
        this.INPUT_TimePreferences  = new Button("Add Info");
        this.INPUT_TimePreferences.setOnAction(EVENT_ADD_timeInput);
        this.INPUT_TimePreferences.setPrefSize(this.widthTwo, this.heightTwo);
        // ############################################################



        // Reset all preferences
        // ############################################################
        this.RESET_ALLPreferences   = new Button("Reseting all Preferences");
        this.RESET_ALLPreferences.addEventHandler(ActionEvent.ACTION, EVENT_RESET_People);
        this.RESET_ALLPreferences.addEventHandler(ActionEvent.ACTION, EVENT_RESET_List);
        this.RESET_ALLPreferences.addEventHandler(ActionEvent.ACTION, EVENT_RESET_days);
        this.RESET_ALLPreferences.addEventHandler(ActionEvent.ACTION, EVENT_RESET_Times);
        this.RESET_ALLPreferences.setPrefSize(this.widthTwo, this.heightTwo);
        // ############################################################



        // Calculate Schedule
        // ############################################################
        this.CALCULATE_Schedule     = new Button("Calculate Schedule");
        this.CALCULATE_Schedule.setOnAction(EVENT_CALCULATE_Schedule);
        this.CALCULATE_Schedule.setPrefSize(this.widthTwo, this.heightTwo);
        // ############################################################



        // Clear Schedule
        // ############################################################
        this.CLEAR_Schedules        = new Button("Clear Schedule list");
        this.CLEAR_Schedules.setOnAction(EVENT_CLEAR_schedule);
        this.CLEAR_Schedules.setPrefSize(this.widthTwo, this.heightTwo);
        // ############################################################



    } // ButtonCreation()



    /**
     * SchedulingInterface()
     * Description: creates the interface used for adding user preferences to the schedule.
     */
    private void SchedulingInterface() {


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
        this.UIInput_FullUIHolder_ScrollPane.setPrefWidth((ManageScenes.WindowWidth / 2) - 80.0);
        this.UIInput_FullUIHolder_ScrollPane.setPrefHeight(ManageScenes.WindowHeight - 100.0);

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
        HBox Primary_InputPeople    = new HBox(this.Spacing);
        // Primary Node Input   - Input for user
        VBox Input_InputPeople      = new VBox(this.Spacing);
        // Primary Node Output  - output of selected choiced
        VBox Output_InputPeople     = new VBox(this.Spacing);
        // Secondary Node       - Holds Reset Button
        VBox Secondary_InputPeople  = new VBox(this.Spacing);
        // comboBox to display the names available to select
        this.Selectable_PersonList  = new ComboBox<>();
        // flowpane node to hold te name list of selected people
        this.AddedPeople            = new FlowPane();
        // scrollpane to hold the flowpane AddedPeople
        ScrollAddedPeople           = new ScrollPane(this.AddedPeople);
        // ############################################################


        // Node set sizing
        // ############################################################
        Input_InputPeople           .setPrefSize(this.widththree,   this.heightthree);
        Output_InputPeople          .setPrefSize(this.widthfour,   this.heightfour);
        this.Selectable_PersonList  .setPrefSize(this.widthfive,     this.heightfive);
        this.AddedPeople            .setPrefSize(this.widthSix, this.heightSix);
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
        Label_inputPeople.getStyleClass().add(this.StyleSeven);
        Label_addedPeople.getStyleClass().add(this.StyleSeven);
        this.Selectable_PersonList.getStyleClass().add(this.StyleFive);
        // ############################################################


        // Retrieve the list from the file reader
        try {
			this.FileUserInfo   = Scheduler_fileReader.ReadFrom_DefaultEmployeePreference();

            // System Message
            // ############################################################
            // 5 - SUCCESS | 19 - SCENE_CREATE_MainMenu | 10 - SYSTEM-FileAccess | 4 - try/catch File access Successful
            SystemInfoManager.GET_SYSTEM_MESSAGE(5,19,10,4);
            // ############################################################

		} catch (IOException e) {
            // System Message
            // ############################################################
            // 5 - SUCCESS | 19 - SCENE_CREATE_MainMenu | 10 - SYSTEM-FileAccess | 5 - try/catch File access failure
            SystemInfoManager.GET_SYSTEM_MESSAGE(5,19,10,4);
            // ############################################################
            e.printStackTrace();
        }
        
        // LinkedList of all people in the file showing both ID and full name
        this.PersonList     = new LinkedList<>();


        // Add people to the list
        for (PreferenceFull FilePerson : FileUserInfo) {
            String format = String.format("|ID: %-7d", FilePerson.GetIdent());
            PersonList.add(format + "| Name: " + FilePerson.GetName());
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
        HBox Primary_InputList      = new HBox(this.Spacing);
        // Primary Node Input
        VBox Input_InputList        = new VBox(this.Spacing);
        // Primary Node Output
        VBox Output_InputList       = new VBox(this.Spacing);
        // Secondary Node
        HBox Secondary_InputList    = new HBox(this.Spacing);
        // Text Field Input
        this.userInput_listAmmount  = new TextField();
        // ############################################################
        

        // Node set sizing
        // ############################################################
        // Primary Node Input
        Input_InputList             .setPrefSize(this.widththree,   this.heightthree);
        // Primary Node Output
        Output_InputList            .setPrefSize(this.widthfour,   this.heightfour);
        // Text Field Input
        this.userInput_listAmmount  .setPrefSize(this.widthfive,   this.widthfive);
        // ############################################################

        // labels
        // ############################################################
        Label Label_inputNumber     = new Label("Input the total ammount of lists to Display");
        Label Label_AmmountOutput   = new Label("Ammount of schedules to be displayed");
        // ############################################################


        // styling
        // ############################################################
        Label_inputNumber.getStyleClass().add(this.StyleSeven);
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
                
                if (intValue >= 0 && intValue < this.maxListAmmount) {
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
        this.Label_OutputNumber = new Label("");
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
        HBox Primary_InputDay       = new HBox(this.Spacing);
        // Primary Node input
        VBox Input_InputDay         = new VBox(this.Spacing);
        // Primary Node output
        VBox Output_InputDay        = new VBox(this.Spacing);
        // secondary node
        HBox Secondary_InputDay     = new HBox(this.Spacing);
        // combo box for days of the week, add multiple days
        this.userInput_SelectDays   = new ComboBox<>();
        // flowPane to hold and display selected day inputs
        this.OutputDays             = new FlowPane();
        // ############################################################



        // Node sizing
        // ############################################################
        // Primary Node input
        Input_InputDay              .setPrefSize(this.widththree,   this.heightthree);
        // Primary Node output
        Output_InputDay             .setPrefSize(this.widthfour,   this.heightfour);
        // combo box for days of the week, add multiple days
        this.userInput_SelectDays   .setPrefSize(this.widthTwo, this.heightTwo);
         // flowPane to hold and display selected day inputs
        this.OutputDays             .setPrefSize(this.widthTwo, this.heightTwo);
        // ############################################################

        // label creation
        // ############################################################
        Label Label_inputDay    = new Label("Input the days to schedule");
        Label Label_OutputDay   = new Label("Days Selected");
        // ############################################################


        // Styling
        // ############################################################
        // labels
        Label_inputDay  .getStyleClass().add(this.StyleSeven);
        Label_OutputDay .getStyleClass().add(this.StyleSeven);
        // ############################################################


        // Output Section
        // ############################################################
        // Add days to the combobox
        this.userInput_SelectDays.getItems().addAll(this.WEEKDAY);
        // add defualt text to output
        this.OutputDays.getChildren().add(new Text(""));
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

        // // Node construction
        // // ############################################################
        this.FlowPane_VBoxDisplay   = new FlowPane();

        // // flowpane to hold time output boxes // TODO
        this.FlowPane_VBoxDisplay   .setPrefSize(270.0, 190.0);
        this.FlowPane_VBoxDisplay   .setPadding(new Insets(10));
        this.FlowPane_VBoxDisplay   .setHgap(5.0);
        this.FlowPane_VBoxDisplay   .setVgap(2.0);

        this.FlowPane_VBoxDisplay   .getStyleClass().add(this.Stylethree);
        // // ############################################################


        this.Scheduler_UserTimeInputs   = new ManageDayTime(this.INPUT_TimePreferences); // TODO: schedule calculator

        // add all to Time Ui holder UIInput_TimeUI_VBOX
        // ############################################################
        this.UIInput_TimeUI_VBOX = Scheduler_UserTimeInputs.Return_UI_TimeInput();
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
        HBox CalculateButtons   = new HBox(this.Spacing);
        // Secondary Node - input
        VBox Holder_Calculate   = new VBox(this.Spacing);
        // ############################################################


        // Input Section
        // ############################################################
        // label
        Label Label_ScheduleNow = new Label("Calculate Schedule");
        // ############################################################


        // Styling
        // ############################################################
        Label_ScheduleNow.getStyleClass().add(this.StyleSeven);
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


        // TODO: make this its own class
        
        // scroll pane for scrolling between the vbox nodes
        this.UIOutput_FullUIHolder_scrollPane = new ScrollPane();
        this.UIOutput_FullUIHolder_scrollPane.setContent(UIOutput_FullUI_VBOX);
        this.UIOutput_FullUIHolder_scrollPane.setFitToWidth(true);
        this.UIOutput_FullUIHolder_scrollPane.setFitToHeight(true);
        //this.UIOutput_FullUI_VBOX.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        // sets default interface dimensions
        this.UIOutput_FullUIHolder_scrollPane.setPrefWidth((ManageScenes.WindowWidth / 2) - 80.0);
        this.UIOutput_FullUIHolder_scrollPane.setPrefHeight(ManageScenes.WindowHeight - 100.0);

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

        Schedules.addListener((ListChangeListener<DataCompleteSchedule>) change -> {

            int ScheduleNumber = 0;

            while ((change.next()) && (ScheduleNumber < this.SCHEDULE_LIST) ) {

                if (change.wasAdded()) { // TODO; possibly chnage so this is its own object


                    // Node Creation
                    // ############################################################
                    // primary Box conatining schedule Info and user list
                    VBox        ScheduleBox_Primary     = new VBox(this.Spacing);
                    // secondary box containing only the schedule info
                    HBox        ScheduleBox_Secondary   = new HBox(this.Spacing);
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
                    int StartHour       = Schedules.getLast().Interval.GetStartTimeHour();
                    String startMinute  = Integer.toString(Schedules.getLast().Interval.GetStartTimeMin());

                    int Endhour         = Schedules.getLast().Interval.GetEndTimeHour();
                    String EndMinute    = Integer.toString(Schedules.getLast().Interval.GetEndTimeMin());

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

                    try {
						this.FilePeople = Scheduler_fileReader.ReadFrom_DefaultEmployeePreference();

                        // System Message
                        // 5 - SUCCESS | 19 - SCENE_CREATE_MainMenu | 10 - SYSTEM-FileAccess | 4 - try/catch File access Successful
                        SystemInfoManager.GET_SYSTEM_MESSAGE(5,19,10,4);
                        
					} catch (IOException e) {

                        // System Message
                        // 5 - SUCCESS | 19 - SCENE_CREATE_MainMenu | 10 - SYSTEM-FileAccess | 5 - try/catch File access failure
                        SystemInfoManager.GET_SYSTEM_MESSAGE(5,19,10,4);
						e.printStackTrace();
					}

                    // used in placement position of the schedule in the list
                    int FlowPanePeopleAmmount = 0;

                    for (String ScheduleID : Schedules.getLast().USERIDs) {

                        for (int FileIndex = 0; FileIndex < FilePeople.size(); FileIndex++) {

                            if (ScheduleID.equals(Integer.toString(FilePeople.get(FileIndex).GetIdent()))) {

                                // add name to the flowpane list
                                SchedulePeopleList.getChildren().add(
                                    new Label(" |ID: " + ScheduleID + " - " + "Name: " + FilePeople.get(FileIndex).GetName())
                                );

                                FlowPanePeopleAmmount++;

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
                    ScheduleBox_Primary     .setPadding(new Insets(this.Padding));
                    ScheduleBox_Primary     .setPrefSize(800,     120);
                    ScheduleBox_Primary     .getStyleClass().add(this.StyleSix);

                    // HBox
                    ScheduleBox_Secondary   .setPrefSize(90,   60);
                    ScheduleBox_Secondary   .setPadding(new Insets(this.Padding));
                    ScheduleBox_Secondary   .getStyleClass().add(this.StyleSix);
                    ScheduleBox_Secondary   .setAlignment(Pos.CENTER_LEFT);

                    // flowPane
                    SchedulePeopleList      .setPrefSize(90,   60);

                    // labels
                    FullList                .setPrefSize(50,   120);
                    ScheduleDay             .setPrefSize(50,   100);
                    ScheduletimeFrame       .setPrefSize(50,   200);

                    // button
                    DeleteList              .setMaxSize(50, 50);
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




                    // put in order based off of ammount of people in each schedule list

                    if (this.UIOutput_FullUI_VBOX.getChildren().size() == 0) {

                        this.UIOutput_FullUI_VBOX.getChildren().addAll(

                            ScheduleBox_Primary

                        );

                    } else {

                        int VBoxIndex = 0;

                        boolean placed = false;

                        while (VBoxIndex < this.UIOutput_FullUI_VBOX.getChildren().size()) {

                            VBox vbox = (VBox) this.UIOutput_FullUI_VBOX.getChildren().get(0);
                            FlowPane PeopleList = (FlowPane) vbox.getChildren().get(1);

                            int NumberOfPeople = PeopleList.getChildren().size();

                            if (FlowPanePeopleAmmount >= NumberOfPeople) {

                                this.UIOutput_FullUI_VBOX.getChildren().add(VBoxIndex, ScheduleBox_Primary);
                                placed = true;
                                break;

                            }

                            VBoxIndex++;

                        } // (VBoxIndex < this.UIOutput_FullUI_VBOX.getChildren().size())

                        // if the while loop can not place the schedule it is automatically placed at the end
                        // ie every other list contained a greater number of people
                        if (placed == false) {
                            this.UIOutput_FullUI_VBOX.getChildren().add(ScheduleBox_Primary);
                        }


                    } // if else()






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
