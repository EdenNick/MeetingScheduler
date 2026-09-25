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
package meeting_scheduler.SceneManagement;
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
// System Messages
import meeting_scheduler.DataAccessLayer.PROG_DAL_D_SystemMessages;
import meeting_scheduler.EmployeePreferences.PREF_EMPLOYEE_TimePref;
import meeting_scheduler.UIBackBoneManagement.MANAGEAPP_AppWindow;
import meeting_scheduler.UIBackBoneManagement.MANAGEAPP_SceneManager;
import meeting_scheduler.UserInput.USERINPUT_JsonFormatting;
import meeting_scheduler.UserInput.USERINPUT_TimeInputManager;

// exceptions
import java.io.IOException;

import meeting_scheduler.EmployeePreferences.PREF_EMPLOYEE_FullPref;
import meeting_scheduler.FileManagement.MANAGEFILE_JsonManager;
// ############################################################

//TODO: standardize sizing with global system variable

public class SCENE_CREATE_DataCard {

    // Application Window variables
    // ############################################################
    // Application stage
    private final Stage         ApplicationStage;
    // Stage width/height
    private double              StageWidth;
    private double              stageHeight;
    // Data Card Info Scene
    private Scene               Scene_DataCard;
    // Background
    private BackgroundFill      backgroundFill;
    // transitions
    private ParallelTransition  EFFECT_Fade;
    private ParallelTransition  EFFECT_Unfade;
    // ############################################################



    // Nodes
    // ############################################################
    // Root Node
    private AnchorPane  DataCard_RootNode;      // RootNode of the scene, contains all nodes
    // Vbox
    private VBox        UI_FullInterface;       // Contains all UI nodes
    private VBox        UI_TimeInterface;       // COntains all Ui nodes for time input
    // FlowPane 
    private FlowPane    Display_InputPref;   // Displays Input user time preferences
    // Button
    private Button      Button_Return;           // Returns to the main menu scene
    private Button      Button_AddPref;         // Adds input user times to preference
    private Button      Button_SubmitPref;       // Submits user preferences to the json file
    private Button      Button_ResetPref;        // resets added preferences
    // Label
    private Label       Label_Name;
    private Label       Label_IDENT;
    // TextFields
    private TextField   userInput_EmployeeID;   // A persons Id
    private TextField   userInput_EmployeeName; // A persons name
    // ############################################################

    // Action Events
    // ############################################################
    private EventHandler<ActionEvent> ReturnHome            = null;
    private EventHandler<ActionEvent> AddInfo               = null;
    private EventHandler<ActionEvent> SubmitInfo            = null;
    private EventHandler<ActionEvent> ResetTimePreference   = null;
    // ############################################################



    // User Data
    // ############################################################
    private LinkedList<PREF_EMPLOYEE_TimePref>    List_UserTimes;         // List of prefered times for an individual

    private LinkedList<VBox>                    List_VBoxTimeInputs;    // contains a set of user prefered times - used exclusivley for iteration

    private Iterator<VBox>                      RemoveAllVBOXIterator;  // iterator to remove all added userpreferences

    // private LinkedList<PROG_DAL_A_InfoInput>    InfoInputPreferences;   // linked list contianing a persons full info to be sent to the json file
    // ############################################################


    // Data Manager Objects
    // ############################################################
    private USERINPUT_JsonFormatting INFOFileWrite;
    // Json file manager
    // private PROG_DAL_B_JSONManager JsonManager;                 // Manages json files
    // user tim input manager
    //private USERINPUT_TimeInput DataCard_UserTimeInputs;    // manages time inputs and displays
    // ############################################################





    // New values
    private USERINPUT_TimeInputManager OBJ_TimeInput_Manager;


    private MANAGEFILE_JsonManager  JsonFileManager;
    
    /**
     * Constructor class
     */
    public SCENE_CREATE_DataCard(Stage stage) {
        
        this.ApplicationStage           = stage;
        this.List_UserTimes             = new LinkedList<>();
        this.List_VBoxTimeInputs        = new LinkedList<>();
        this.INFOFileWrite              = new USERINPUT_JsonFormatting();

        this.JsonFileManager = new MANAGEFILE_JsonManager();


    }


    
    /**
     * changetoDataCardScene()
     * Description: Public method meant to be called outside the class in order to set the scene to the data card management scene
     */
    public void changetoDataCardScene() {

        // unfades nodes
        EFFECT_Unfade.play();

        // Gets the current size of the stage
        this.StageWidth   = this.ApplicationStage.getWidth();
        this.stageHeight  = this.ApplicationStage.getHeight();

        // Sets the stage to the main menu scene
        this.ApplicationStage.setScene(this.Scene_DataCard);

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

        // Root Node construction
        // ############################################################
        // - must be called first
        this.DataCard_RootNode = new AnchorPane();
        // loads css styles
        this.DataCard_RootNode.getStylesheets().add(getClass().getResource(SCENE_VARIABLES_Local.CSS_Styles).toExternalForm());
        // ############################################################


        // handles event creation
        // ############################################################
        DatacardEventHandler();
        // ############################################################


        // Creates Buttons for this scene
        // ############################################################
        ButtonCreation();
        // ############################################################


        // Creates label for this scene
        // ############################################################
        UI_LabelCreation();
        // ############################################################


        // Creates UI for user inputs
        // ############################################################
        UI_UserInputs();
        // new
        this.OBJ_TimeInput_Manager = new USERINPUT_TimeInputManager(this.Button_AddPref);
        // ############################################################


        // creates layour for the user input UI
        // ############################################################
        cardUIManagement();
        // ############################################################


        // Creates layout of user submitted info before submission
        // ############################################################
        this.Display_InputPref = new FlowPane();
        this.Display_InputPref.setPrefSize(600.0, 600.0);
        this.Display_InputPref.setPadding(new Insets(10));
        this.Display_InputPref.getStyleClass().add(SCENE_VARIABLES_Local.STYLE_DATACARD_TimeOutput);
        // ############################################################


        // Anchor position set
        // ############################################################
        // Root Node - set return home button position
        AnchorPane.setBottomAnchor(Button_Return,    SCENE_VARIABLES_Local.SCHEDULE_Return_BottomAnchor);
        AnchorPane.setRightAnchor(Button_Return,     SCENE_VARIABLES_Local.SCHEDULE_Return_RightAnchor);
        
        // Root Node - set UI interface input
        AnchorPane.setTopAnchor(UI_FullInterface, 20.0);
        AnchorPane.setLeftAnchor(UI_FullInterface, 20.0);

        // Root Node - set user cards
        AnchorPane.setTopAnchor(Display_InputPref, 20.0);
        AnchorPane.setRightAnchor(Display_InputPref, 20.0);
        // ############################################################
        

        // Add all to root node
        // ############################################################
        this.DataCard_RootNode.getChildren().addAll(UI_FullInterface, Button_Return, Display_InputPref);
        // ############################################################

        
        // Set Graphical Effects
        // ############################################################
        SceneEffects();
        // ############################################################


        // create menu scene with the current node layout
        // ############################################################
        this.Scene_DataCard = new Scene(DataCard_RootNode, MANAGEAPP_SceneManager.WindowWidth, MANAGEAPP_SceneManager.WindowHeight);
        // ############################################################


        // fade all objects before the scene is set
        // ############################################################
        this.EFFECT_Fade.play();
        // ############################################################
        
    } // ConstructCardManagerScene(

    

    /**
     * cardUIManagement()
     * Description: manages the node layout for the user card input
     */
    private void cardUIManagement() {

        this.UI_TimeInterface = OBJ_TimeInput_Manager.Return_UI_TimeInput();



        // Vbox for UI
        // ############################################################
        // Create and set box parameters
        this.UI_FullInterface = new VBox(10);
        this.UI_FullInterface.setPrefSize(400.0, 600.0);
        this.UI_FullInterface.setPadding(new Insets(10));
        this.UI_FullInterface.getStyleClass().add(SCENE_VARIABLES_Local.STYLE_DATACARD_DefaultUI);


        // add nodes to the box
        this.UI_FullInterface.getChildren().addAll(
            Label_Name,
            userInput_EmployeeName, 

            Label_IDENT,
            userInput_EmployeeID,

            UI_TimeInterface, 

            Button_ResetPref,

            Button_SubmitPref
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
        this.Button_Return = new Button("Return Home");
        this.Button_Return.addEventHandler(ActionEvent.ACTION, this.ReturnHome);
        this.Button_Return.addEventHandler(ActionEvent.ACTION, this.ResetTimePreference);
        this.Button_Return.setPrefSize(SCENE_VARIABLES_Local.SCHEDULE_Button_PrefWidthLarge, SCENE_VARIABLES_Local.SCHEDULE_Button_PrefHeightLarge);
        // ############################################################



        // Add Info Button
        // ############################################################
        this.Button_AddPref = new Button("Add Info");
        this.Button_AddPref.setOnAction(this.AddInfo);
        // ############################################################



        // Submit UserInfo
        // ############################################################
        this.Button_SubmitPref = new Button("Submit Info");
        this.Button_SubmitPref.setOnAction(this.SubmitInfo);
        // ############################################################



        // reset usertimeinput
        // ############################################################
        this.Button_ResetPref = new Button("Reset added time preferences");
        this.Button_ResetPref.setOnAction(this.ResetTimePreference);
        // ############################################################

    } // ButtonCreation()



    /**
     * DatacardEventHandler()
     * Description: handles the creation of various event handlers for the scene
     */
    private void DatacardEventHandler() {

        // Returns to the main menu
        // ############################################################
        this.ReturnHome = event -> {
            
            System.out.println(PROG_DAL_D_SystemMessages.BUTTON_DataCard_returnHome);
            
            EFFECT_Fade.setOnFinished(event2 -> {
                MANAGEAPP_AppWindow.SceneManager.SwapToMainMenu();
            });

            EFFECT_Fade.play();

        };
        // ############################################################



        // Adds a user preference to their datacard
        // ############################################################
        this.AddInfo = event -> {

            System.out.println(PROG_DAL_D_SystemMessages.BUTTON_DataCard_AddInfo);

            // checks to ensure all variables are input
            if ( (OBJ_TimeInput_Manager.CHECK_FullTimeInput() == 0) && (List_VBoxTimeInputs.size() < SCENE_VARIABLES_Local.MAXTimeInputs) ){

                // Temp user preference created for clean seperation of object use
                PREF_EMPLOYEE_TimePref TempUserPreferrence = OBJ_TimeInput_Manager.Return_UserPreference();

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
                OBJ_TimeInput_Manager.UserInputGraphic(TempUserPreferrence, List_UserTimes, List_VBoxTimeInputs, Display_InputPref, true);
                // ############################################################


                // garbage Collection
                TempUserPreferrence = null;

            } else {
                // Do nothing
            }

        };
        // ############################################################


        
        // Submits the added user preferences to the relevant json file
        // ############################################################
        this.SubmitInfo = event -> {
            
            // todo: use filewrite
            // Submits USer info
            System.out.println(PROG_DAL_D_SystemMessages.BUTTON_DataCard_SubmitInfo);

            // collect all info into the relvant linkedlist
            if          (this.userInput_EmployeeName .getText().isBlank())       { // Do nothing
                // user has not submitted a valid name
                System.out.println(PROG_DAL_D_SystemMessages.INFO_DataCard_InvalidName);

            } else if   (this.userInput_EmployeeID   .getText().isBlank())       { // Do nothing
                // user has not submitted a valid name
                System.out.println(PROG_DAL_D_SystemMessages.INFO_DataCard_InvalidID);

            } else if   (this.List_UserTimes         .size() < 1)                { // Do nothing
                // user has not submitted valid user times
                System.out.println(PROG_DAL_D_SystemMessages.INFO_DataCard_InvalidTimes);

            } else {

                // Employee Name
                // ############################################################
                String name = userInput_EmployeeName.getText();
                // ############################################################

                // Employee ID is not blank
                // ############################################################
                int id = Integer.parseInt(userInput_EmployeeID.getText());
                // ############################################################

                // preferred week days linked list
                // Format Input Days to a string
                // ############################################################
                LinkedList<String> preferredDaysList = new LinkedList<>();

                // iterate through weekdays first to ensure a weekday can only be matched once
                for (String Day : SCENE_VARIABLES_Local.WEEKDAYS) {

                    for (PREF_EMPLOYEE_TimePref preference : this.List_UserTimes) {
                        if (preference.GetWeekDay().equals(Day)) {

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
                // ############################################################





                // checks user submitted info
                // ############################################################
                //INFOFileWrite.CheckUserInfo(name, id, preferredDays, this.List_UserTimes);
                // ############################################################


                // writes to file
                // ############################################################
                // TODO: fix
                LinkedList<PREF_EMPLOYEE_FullPref> EMPLOYEES_ToInput;
				try {
					EMPLOYEES_ToInput = new LinkedList<>(JsonFileManager.ReadFrom_DefaultEmployeePreference());
				} catch (IOException e) {
                    EMPLOYEES_ToInput = new LinkedList<>();
					e.printStackTrace();
				}
                
                PREF_EMPLOYEE_FullPref EMPLOYEE_Input = new PREF_EMPLOYEE_FullPref(false, name, id, preferredDays, this.List_UserTimes);
                
                EMPLOYEES_ToInput.add(EMPLOYEE_Input);

                LinkedList<PREF_EMPLOYEE_FullPref> EMPLOYEES_ToWrite = INFOFileWrite.JsonFileDefault_Formatting(EMPLOYEES_ToInput);



                JsonFileManager.WriteTo_DefaultEmployeePreference(EMPLOYEES_ToWrite);

                // ############################################################


            } // if/else ()

        };
        // ############################################################



        // Resets all input time preferences
        // ############################################################
        this.ResetTimePreference = event -> {

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
                    System.out.println("User Input :" + List_UserTimes.size());
                    List_UserTimes.remove(0);

                } // if ()

            } // for ()


            /**
             * remove nodes from the flowpane
             */
            for (Node node: Display_InputPref.getChildren()) {
                if (node instanceof VBox vbox) {
                    vbox.getChildren().clear();
                }

            } // for()

            Display_InputPref.getChildren().clear();
            
        };
        // ############################################################



    } // DatacardEventHandler()







    /**
     * UI_LabelCreation()
     * Description: Creates various text labels for the UI
     */
    private void UI_LabelCreation() {

        // TODO: make global variables
        // Label - Name Prompt
        this.Label_Name          = new Label(SCENE_VARIABLES_Local.Prompt_Name);
        this.Label_Name.getStyleClass().add(SCENE_VARIABLES_Local.STYLE_DEFAULT);


        // Label - ID Prompt
        this.Label_IDENT            = new Label(SCENE_VARIABLES_Local.Prompt_ID);
        this.Label_IDENT.getStyleClass().add(SCENE_VARIABLES_Local.STYLE_DEFAULT);


    } // UI_LabelCreation()


    




    /**
     * UI_UserInputs()
     * Description: creates various UI components where the user inputs direct info
     */
    private void UI_UserInputs() {


        // Name Input
        // ############################################################
        this.userInput_EmployeeName = new TextField();
        this.userInput_EmployeeName.setPromptText(SCENE_VARIABLES_Local.Prompt_Name2);
        this.userInput_EmployeeName.setPrefSize(50, 25.0);
        // ############################################################



        // Enter ID Input
        // ############################################################
        this.userInput_EmployeeID = new TextField();
        this.userInput_EmployeeID.setPromptText(SCENE_VARIABLES_Local.Prompt_ID2);
        this.userInput_EmployeeID.setPrefSize(50, 25.0);
        this.userInput_EmployeeID.setTextFormatter(new TextFormatter<>(change -> {
            
            // User input text
            String TextInput = change.getControlNewText();

            // if the text is empty accept it
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

        this.DataCard_RootNode.setBackground(new Background(backgroundFill));
        // ############################################################



        // Fade transition effect
        // ############################################################
        this.EFFECT_Fade = new ParallelTransition();

        for (Node node : this.DataCard_RootNode.getChildren()) {
            
            FadeTransition NodeFade = new FadeTransition(
                Duration.seconds(2),
                node
            );

            NodeFade.setToValue(0);
            
            EFFECT_Fade.getChildren().addAll(NodeFade);
        }
        // ############################################################



        // Unfade transition effect
        // ############################################################
        this.EFFECT_Unfade = new ParallelTransition();

        for (Node node : this.DataCard_RootNode.getChildren()) {
            
            FadeTransition NodeUnFade = new FadeTransition(
                Duration.seconds(2),
                node
            );

            NodeUnFade.setToValue(1);
            
            EFFECT_Unfade.getChildren().addAll(NodeUnFade);
        }
        // ############################################################

    } // SceneEffects



} // PROG_UI_B_DataCardinfoScene ()
