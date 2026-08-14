/**
 * PROG_UI_B_DataCardinfoScene.java
 * 
 * Description: Application window scene which allows the user to add user datacards to the application
 * Contains:
 * a. back button       (scene change)
 * b. UI interface allowing user to add info    (various buttons, text inputs etc)
 */

// Package  - DO Not Change
// ############################################################
package meeting_scheduler.PresentationLayer;
// ############################################################



// Imports
// ############################################################
// Util
import java.util.Iterator;
import java.util.LinkedList;
// Jackson - json manager
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
// Javafx
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;
import javafx.util.Duration;
// data managing objects
import meeting_scheduler.DataAccessLayer.PROG_DAL_A_InfoInput;
import meeting_scheduler.DataAccessLayer.PROG_DAL_A_TimeInput;
import meeting_scheduler.DataAccessLayer.PROG_DAL_B_JSONManager;
// exceptions
import java.io.IOException;
// ############################################################



public class PROG_UI_B_DataCardinfoScene {

    // Application Window variables
    // ############################################################
    // Application stage
    private final Stage         ApplicationStage;
    // Stage width/height
    private double              StageWidth;
    private double              stageHeight;
    // Data Card Info Scene
    private Scene               DataCardScene;
    // Background
    private BackgroundFill      backgroundFill;
    // transitions
    private ParallelTransition  fadeMenuNodes;
    private ParallelTransition  UnfadeMenuNodes;
    // ############################################################



    // Nodes
    // ############################################################
    // Root Node
    private AnchorPane  RootNode;               // RootNode of the scene, contains all nodes
    // Vbox
    private VBox        UI_FullInterface;       // Contains all UI nodes
    private VBox        UI_TimeInterface;       // COntains all Ui nodes for time input
    // Hbox
    private HBox        UI_AddStartTime;        // UI for start time input
    private HBox        UI_addEndingTime;       // UI for end time input
    // FlowPane 
    private FlowPane    FlowPane_VBoxDisplay;   // Displays Input user time preferences
    // Button
    private Button      ButtonReturn;           // Returns to the main menu scene
    private Button      ButtonAddPreference;    // Adds input user times to preference
    private Button      ButtonSubmitPref;       // Submits user preferences to the json file
    private Button      ButtonResetPref;        // resets added preferences
    // Label
    private Label       Labelname;
    private Label       LabelID;
    private Label       LabelDay;
    private Label       LabelBeginHour;
    private Label       LabelBeginMinute;
    private Label       LabelEndHour;
    private Label       LabelEndMinute;
    private Label       LabelBeginningTime;
    private Label       LabelEndingTime;
    // TextFields
    private TextField   userInput_EmployeeID;   // A persons Id
    private TextField   userInput_EmployeeName; // A persons name
    // ############################################################

    

    // User Data
    // ############################################################
    private LinkedList<PROG_DAL_A_TimeInput>    List_UserTimes;         // List of prefered times for an individual

    private LinkedList<VBox>                    List_VBoxTimeInputs;    // contains a set of user prefered times - used exclusivley for iteration

    private Iterator<VBox>                      RemoveAllVBOXIterator;  // iterator to remove all added userpreferences

    private LinkedList<PROG_DAL_A_InfoInput>    InfoInputPreferences;   // linked list contianing a persons full info to be sent to the json file
    // ############################################################


    // Data Manager Objects
    // ############################################################
    // Json file manager
    private PROG_DAL_B_JSONManager JsonManager;                 // Manages json files
    // user tim input manager
    private PROG_UI_C_UserTimeInput DataCard_UserTimeInputs;    // manages time inputs and displays
    // ############################################################


    /**
     * Constructor class
     */
    public PROG_UI_B_DataCardinfoScene(Stage stage) {
        
        this.ApplicationStage = stage;

        this.List_UserTimes = new LinkedList<>();

        this.InfoInputPreferences = new LinkedList<>();

        this.JsonManager = new PROG_DAL_B_JSONManager();

        this.DataCard_UserTimeInputs = new PROG_UI_C_UserTimeInput();
    }


    
    /**
     * changetoDataCardScene()
     * Description: Public method meant to be called outside the class in order to set the scene to the data card management scene
     */
    public void changetoDataCardScene() {

        // unfades nodes
        UnfadeMenuNodes.play();

        // Gets the current size of the stage
        this.StageWidth   = this.ApplicationStage.getWidth();
        this.stageHeight  = this.ApplicationStage.getHeight();

        // Sets the stage to the main menu scene
        this.ApplicationStage.setScene(this.DataCardScene);

        // sets the correct size for the stage
        this.ApplicationStage.setWidth(StageWidth);
        this.ApplicationStage.setHeight(stageHeight);

        // Shows the change
        this.ApplicationStage.show();
        
    }



    /**
     * ConstructCardManagerScene()
     * Description: Performs the necessary operations in order to build the various nodes/components of the stage.
     */
    public void ConstructCardManagerScene() {

        // Root Node
        // - must be called first
        this.RootNode = new AnchorPane();
        
        // loads css styles
        RootNode.getStylesheets().add(getClass().getResource("/CSS_Styles.css").toExternalForm());


        // Creates Buttons for this scene
        ButtonCreation();


        // Creates label for this scene
        UI_LabelCreation();


        // Creates UI for user inputs
        UI_UserInputs();
        DataCard_UserTimeInputs.UI_data_construction();


        // creates layour for the user input UI
        cardUIManagement();


        // VBox user info linkedlist
        List_VBoxTimeInputs = new LinkedList<>();

        // Creates layout of user submitted info before submission
        this.FlowPane_VBoxDisplay = new FlowPane();
        this.FlowPane_VBoxDisplay.setPrefSize(600.0, 600.0);
        this.FlowPane_VBoxDisplay.setPadding(new Insets(10));
        this.FlowPane_VBoxDisplay.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: Black;" +
            "-fx-border-width: 2;" +
            "-fx-border-radius: 5;"
        );


        // Root Node - set return home button position
        AnchorPane.setBottomAnchor(ButtonReturn, 20.0);
        AnchorPane.setRightAnchor(ButtonReturn, 20.0);
        
        // Root Node - set UI interface input
        AnchorPane.setTopAnchor(UI_FullInterface, 20.0);
        AnchorPane.setLeftAnchor(UI_FullInterface, 20.0);

        // Root Node - set user cards
        AnchorPane.setTopAnchor(FlowPane_VBoxDisplay, 20.0);
        AnchorPane.setRightAnchor(FlowPane_VBoxDisplay, 20.0);
        

        // Add all to root node
        this.RootNode.getChildren().addAll(UI_FullInterface, ButtonReturn, FlowPane_VBoxDisplay);
        // ############################################################

        
        // Set Graphical Effects
        SceneEffects();


        // create menu scene with the current node layout
        this.DataCardScene = new Scene(RootNode, PROG_UI_A_SceneManager.WindowWidth, PROG_UI_A_SceneManager.WindowHeight);

        // fade all objects before the scene is set
        this.fadeMenuNodes.play();
        
    } // ConstructCardManagerScene()







    /**
     * UserInputGraphic()
     * Descriiption: manages the visual output of the user submitted data.
     */
    private void UserInputGraphic(PROG_DAL_A_TimeInput Input_UserTime) {

        // TODO: remove later
        // // UserTimeInput        -   individual user Day/time preference     -   private LinkedList<PROG_DAL_A_TimeInput>    UserTimeInput;

        // // VBOXIterator         -   iterates over nodes in Vbox             -   private Iterator<VBox>                      VBOXIterator;

        // // VBOXUserPreferences  -   List of each VBox that holds a time     -   private LinkedList<VBox>                    VBOXUserPreferences;

        // // userInputGraphic     -   FlowPane that stores all the Vboxes     -   private FlowPane                            userInputGraphic;

    } // UserInputGraphic







    /**
     * cardUIManagement()
     * Description: manages the node layout for the user card input
     */
    private void cardUIManagement() {

        // HBox for beginning Hour/Min
        // ############################################################
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
            DataCard_UserTimeInputs.Return_Hour_Begin(),

            LabelBeginMinute,
            DataCard_UserTimeInputs.Return_Minute_Begin(),

            DataCard_UserTimeInputs.Return_AMPM_StartTime()

        );
        // ############################################################



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
            DataCard_UserTimeInputs.Return_Hour_End(),

            LabelEndMinute,
            DataCard_UserTimeInputs.Return_Minute_End(),

            DataCard_UserTimeInputs.Return_AMPM_EndTime()

        );
        // ############################################################



        // Vbox for adding time intervals
        // ############################################################
        this.UI_TimeInterface = new VBox(10);
        //this.addTimeInfo_input.setPrefSize(200.0, 400.0);
        this.UI_TimeInterface.setPadding(new Insets(10));
        this.UI_TimeInterface.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: Black;" +
            "-fx-border-width: 2;" +
            "-fx-border-radius: 5;"
        );

        Region ButtonSpace = new Region();
        VBox.setVgrow(ButtonSpace, Priority.ALWAYS);

        this.UI_TimeInterface.getChildren().addAll(

            LabelDay,
            DataCard_UserTimeInputs.Return_WeekDay(),
            
            LabelBeginningTime,
            UI_AddStartTime,

            LabelEndingTime,
            UI_addEndingTime,

            ButtonSpace,

            ButtonAddPreference

        );
        // ############################################################



        // Vbox for UI
        // ############################################################
        // Create and set box parameters
        this.UI_FullInterface = new VBox(10);
        this.UI_FullInterface.setPrefSize(400.0, 600.0);
        this.UI_FullInterface.setPadding(new Insets(10));
        this.UI_FullInterface.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: Black;" +
            "-fx-border-width: 2;" +
            "-fx-border-radius: 5;"
        );

        // add nodes to the box
        this.UI_FullInterface.getChildren().addAll(
            Labelname,
            userInput_EmployeeName, 

            LabelID,
            userInput_EmployeeID,

            UI_TimeInterface, 

            ButtonResetPref,

            ButtonSubmitPref
        );
        // ############################################################


    } // cardUIManagement







    /**
     * UI_ButtonCreation()
     * Description: Create various Button interfaces
     */
    private void ButtonCreation() {

        // Return Home Button
        // ############################################################
        this.ButtonReturn = new Button("Return Home");

        EventHandler<ActionEvent> ReturnHome = (ActionEvent e) -> {
            
            // Exits the program
            System.out.println("BUTTON CLICK    - CARD MANAGER PAGE - Returning to home page");
            
            fadeMenuNodes.setOnFinished(event -> {
                PROG_UI_A_Application.SceneManager.MainMenu();
            });

            fadeMenuNodes.play();

        };

        this.ButtonReturn.setOnAction(ReturnHome);
        // ############################################################



        // Add Info Button
        // ############################################################
        this.ButtonAddPreference = new Button("Add Info");

        EventHandler<ActionEvent> AddInfo = (ActionEvent e) -> {

            // System Message
            System.out.println("BUTTON CLICK    - CARD MANAGER PAGE - Add User Info");

            // checks to ensure all variables are input
            // TODO: update size check
            if ( (DataCard_UserTimeInputs.ButtonPressFullTimeInput() == 0) && (List_VBoxTimeInputs.size() < 10) ){

                // Temp user preference created for clean seperation of object use
                PROG_DAL_A_TimeInput TempUserPreferrence = DataCard_UserTimeInputs.Return_FileReadyUserPreference();

                /**
                 * ############################################################
                 * PROG_UI_C_UserTimeInput DataCard_UserTimeInputs .FileReadyUserInputGraphic()
                 * 
                 * Inputs user preference to be displayed to the user as well as stored for eventual submission to file
                 * 
                 * PROG_DAL_A_TimeInput TempUserPreferrence         - individual time preference containing day. start and end times
                 * 
                 * LinkedList<PROG_DAL_A_TimeInput> List_UserTimes  - A list of all time preferences a person has. To be submitted to the json file not displayed
                 * 
                 * LinkedList<VBox> List_VBoxTimeInputs             - a copied list of all VBoxes stored in the flowpane display. Used to iterate, not to be displayed
                 * 
                 * FlowPane FlowPane_VBoxDisplay                    - A flowpane which displays the various Vboxs that hold user preferences. display only
                 */
                DataCard_UserTimeInputs.FileReadyUserInputGraphic(TempUserPreferrence, List_UserTimes, List_VBoxTimeInputs, FlowPane_VBoxDisplay);
                // ############################################################


                // garbage Collection
                TempUserPreferrence = null;

            } else {
                // Do nothing
            }

        };

        this.ButtonAddPreference.setOnAction(AddInfo);
        // ############################################################



        // Submit UserInfo
        // ############################################################
        this.ButtonSubmitPref = new Button("Submit Info");

        EventHandler<ActionEvent> SubmitInfo = (ActionEvent e) -> {
            
            // Submits USer info
            System.out.println("BUTTON CLICK    - CARD MANAGER PAGE - Submit User Info");

            // collect all info into the relvant linkedlist
            if          (userInput_EmployeeName .getText().isBlank())       { // Do nothing
                // user has not submitted a valid name
                System.out.println("user has not submitted a valid name");

            } else if   (userInput_EmployeeID   .getText().isBlank())       { // Do nothing
                // user has not submitted a valid name
                System.out.println("user has not submitted a valid ID");

            } else if   (List_UserTimes         .size() < 1)                { // Do nothing
                // user has not submitted valid user times
                System.out.println("user has not submitted valid user times");

            } else {


                String name    = userInput_EmployeeName.getText();
                int    id      = Integer.parseInt(userInput_EmployeeID.getText());

                // preferred week days linked list
                LinkedList<String> preferredDaysList = new LinkedList<>();

                // iterate through weekdays first to ensure a weekday can only be matched once
                for (String Day : PROG_UI_D_DataVariables.WEEKDAYS) {

                    for (PROG_DAL_A_TimeInput preference : this.List_UserTimes) {
                        if (preference.WeekDay == Day) {

                            preferredDaysList.add(Day); // each day should only be added once
                            break; // should break to the first for loop
                        }
                    }

                }

                // create String array with no null values
                String[] preferredDays = new String[preferredDaysList.size()];

                for (int index = 0; index < preferredDaysList.size(); index++) {
                    preferredDays[index] = preferredDaysList.get(index);
                }


                /**
                 * ############################################################
                 * LinkedList<PROG_DAL_A_InfoInput> InfoInputPreferences    - full list of all preferences where each index is a persons full preferences
                 * 
                 * PROG_DAL_A_InfoInput(String name, int ID, String[] week, LinkedList<PROG_DAL_A_TimeInput> times);
                 */
                InfoInputPreferences.add(new PROG_DAL_A_InfoInput(name, id, preferredDays, this.List_UserTimes));
                // ############################################################


                // testing
                // TODO: Remove later
                System.out.println(InfoInputPreferences.size());
                System.out.println(InfoInputPreferences.getLast().EmployeeMEETINGDAYS);

                // 1. set user card so file can be updated
                JsonManager.SetUserCards(InfoInputPreferences);

                // 2. set to file
                try {
                    JsonManager.SetToFile(); // submit linkedlist of peoples preferences
                } catch (StreamReadException e1)    {
                    e1.printStackTrace();
                } catch (DatabindException e1)      {
                    e1.printStackTrace();
                } catch (IOException e1)            {
                    e1.printStackTrace();
                }

                // 3. reset Json Manager
                JsonManager.DiscardCard();

                // clear linkedlist and any other data for a fresh start
                InfoInputPreferences = new LinkedList<>();

            } // if/else ()

        };
        this.ButtonSubmitPref.setOnAction(SubmitInfo);
        // ############################################################



        // reset usertimeinput
        // ############################################################
        this.ButtonResetPref = new Button("Reset added time preferences");

        EventHandler<ActionEvent> ResetTimePreference = (ActionEvent e) -> {

            // linkedlist Vbox full of user preferences
            RemoveAllVBOXIterator = List_VBoxTimeInputs.iterator();

            while (RemoveAllVBOXIterator.hasNext()) {

                // next Vbox in iterator
                VBox tempBox = RemoveAllVBOXIterator.next();

                // clears all nodes in the Vbox
                tempBox.getChildren().clear();

                // if the Vbox is fully empty
                if (tempBox.getChildren().isEmpty()) {

                    // removes empty Vbox from the linked list of preferences
                    RemoveAllVBOXIterator.remove();

                    //removes InputTime from UserTimeInput LinkedList
                    System.out.println("user Input :" + List_UserTimes.size());
                    List_UserTimes.remove(0);

                } // if ()

            } // for ()


            /**
             * remove nodes from the flowpane
             */
            for (Node node: FlowPane_VBoxDisplay.getChildren()) {
                if (node instanceof VBox vbox) {
                    vbox.getChildren().clear();
                }

            } // for()

            FlowPane_VBoxDisplay.getChildren().clear();
            

        };
        this.ButtonResetPref.setOnAction(ResetTimePreference);

    } // ButtonCreation()







    /**
     * UI_LabelCreation()
     * Description: Creates various text labels for the UI
     */
    private void UI_LabelCreation() {

        // Label - Name Prompt
        this.Labelname          = new Label(PROG_UI_D_DataVariables.Prompt_Name);
        this.Labelname.getStyleClass().add("default-label");


        // Label - ID Prompt
        this.LabelID            = new Label(PROG_UI_D_DataVariables.Prompt_ID);
        this.LabelID.getStyleClass().add("default-label");


        // Label - Weekday Prompt
        this.LabelDay           = new Label(PROG_UI_D_DataVariables.Prompt_Day);
        this.LabelDay.getStyleClass().add("default-label");


        // Label - Beginning time Prompt
        this.LabelBeginningTime = new Label(PROG_UI_D_DataVariables.Prompt_BeginningTime);
        this.LabelBeginningTime.getStyleClass().add("default-label");


        // Label - Beginning Hour Label
        this.LabelBeginHour     = new Label(PROG_UI_D_DataVariables.Prompt_HourLabel);
        this.LabelBeginHour.getStyleClass().add("default-label");


        // Label - Beginning Minute Label
        this.LabelBeginMinute   = new Label(PROG_UI_D_DataVariables.Prompt_MinuteLabel);
        this.LabelBeginMinute.getStyleClass().add("default-label");


        // Label - Ending time Prompt
        this.LabelEndingTime    = new Label(PROG_UI_D_DataVariables.Prompt_EndingTime);
        this.LabelEndingTime.getStyleClass().add("default-label");


        // Label - Ending hour Label
        this.LabelEndHour       = new Label(PROG_UI_D_DataVariables.Prompt_HourLabel);
        this.LabelEndHour.getStyleClass().add("default-label");


        // Label - Ending Minute Label
        this.LabelEndMinute     = new Label(PROG_UI_D_DataVariables.Prompt_MinuteLabel);
        this.LabelEndMinute.getStyleClass().add("default-label");


    } // UI_LabelCreation()


    




    /**
     * UI_UserInputs()
     * Description: creates various UI components where the user inputs direct info
     */
    private void UI_UserInputs() {

        //TODO: make sure inputs are erased after submission

        // Name Input
        // ############################################################
        this.userInput_EmployeeName = new TextField();
        this.userInput_EmployeeName.setPromptText("Enter Full Name");
        this.userInput_EmployeeName.setPrefSize(50, 25.0);
        // ############################################################



        // Enter ID Input
        // ############################################################
        this.userInput_EmployeeID = new TextField();
        this.userInput_EmployeeID.setPromptText("Enter ID");
        this.userInput_EmployeeID.setPrefSize(50, 25.0);
        this.userInput_EmployeeID.setTextFormatter(new TextFormatter<>(change -> {
            
            // User input text
            String TextInput = change.getControlNewText();

            // if th etext is empty accept it
            if (TextInput.isEmpty()) {
                return change;
            }

            // test if the text is a valid int within a valid range
            try {
                int intValue = Integer.parseInt(TextInput);

                if (intValue >= 0 && intValue < 9999999) {
                    return change;
                }
            } catch (NumberFormatException e) {
                // Invalid Input
            }

            return null;

        }));
        // ############################################################

    } // UI_UserInputs()



    /**
     * SceneEffects()
     * Description: graphical manipulations and effects
     */
    private void SceneEffects() {

        // Add background
        // ############################################################
        LinearGradient BackgroundGradient = new LinearGradient(0, 0, 300, 300, false, CycleMethod.NO_CYCLE, 
            new Stop(0, Color.DARKBLUE), new Stop(1, Color.BEIGE)
        );

        this.backgroundFill = new BackgroundFill(BackgroundGradient, CornerRadii.EMPTY, Insets.EMPTY);

        this.RootNode.setBackground(new Background(backgroundFill));
        // ############################################################



        // Fade transition effect
        // ############################################################
        this.fadeMenuNodes = new ParallelTransition();

        for (Node node : this.RootNode.getChildren()) {
            
            FadeTransition NodeFade = new FadeTransition(
                Duration.seconds(2),
                node
            );

            NodeFade.setToValue(0);
            
            fadeMenuNodes.getChildren().addAll(NodeFade);
        }
        // ############################################################



        // Unfade transition effect
        // ############################################################
        this.UnfadeMenuNodes = new ParallelTransition();

        for (Node node : this.RootNode.getChildren()) {
            
            FadeTransition NodeUnFade = new FadeTransition(
                Duration.seconds(2),
                node
            );

            NodeUnFade.setToValue(1);
            
            UnfadeMenuNodes.getChildren().addAll(NodeUnFade);
        }
        // ############################################################

    } // SceneEffects



} // PROG_UI_B_DataCardinfoScene ()
