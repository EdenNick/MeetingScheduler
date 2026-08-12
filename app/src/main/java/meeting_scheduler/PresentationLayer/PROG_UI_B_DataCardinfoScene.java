package meeting_scheduler.PresentationLayer;

import java.util.LinkedList;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;
import javafx.util.Duration;
import meeting_scheduler.DataAccessLayer.PROG_DAL_A_InfoInput;
import meeting_scheduler.DataAccessLayer.PROG_DAL_A_TimeInput;
import meeting_scheduler.DataAccessLayer.PROG_DAL_B_JSONManager;

import java.io.IOException;
import java.util.Iterator;

/**
 * PROG_UI_B_DataCardinfoScene()
 * 
 * Description: Application window scene which allows the user to add user datacards to the application
 * Contains:
 * a. back button       (scene change)
 * b. UI interface allowing user to add info    (various buttons, text inputs etc)
 */




public class PROG_UI_B_DataCardinfoScene {

    // Reference of the application stage used for local operations
    private final Stage ApplicationStage;

    // Scene
    private Scene       DataCardScene;

    // Stage width/height
    private double      StageWidth;
    private double      stageHeight;

    // Root Node
    private AnchorPane  RootNode;

    // Vbox
    private VBox        addTimeInfo_input;
    private VBox        UserInterface_Input;

    // Hbox
    private HBox        AddStartTime;
    private HBox        addEndingTime;

    // FlowPane 
    private FlowPane    userInputGraphic;

    // Buttons
    private Button      ReturnToMenu;
    private Button      AddUserInfo;
    private Button      SubmitUserInfo;
    private Button      ResetUserTimePref;

    // Labels
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
    //private TextField   userInput_EndingMinuteSeleciton;
    //private TextField   userInput_EndingHourSeleciton;
    //private TextField   userInput_BeginningMinuteSeleciton;
    //private TextField   userInput_BeginningHourSelection;
    private TextField   userInput_EmployeeID;
    private TextField   userInput_EmployeeName;

    // Background
    private BackgroundFill      backgroundFill;
    
    // transitions
    private ParallelTransition  fadeMenuNodes;
    private ParallelTransition  UnfadeMenuNodes;

    // ComboBox
    //private ComboBox<String>    userInput_WeekDaySelection;

    // CheckBox
    //private ComboBox<String>    StartTimeAMPM;
    //private ComboBox<String>    EndTimeAMPM;

    // individual user Day/time preference
    private LinkedList<PROG_DAL_A_TimeInput>    UserTimeInput;

    // cardlist
    private LinkedList<VBox>                    VBOXUserPreferences;

    // iterators
    private Iterator<VBox>                      VBOXIterator;
    private Iterator<VBox>                      RemoveAllVBOXIterator;

    // User preferences
    private LinkedList<PROG_DAL_A_InfoInput>    InfoInputPreferences;

    // string[] weekdays
    private String[] Weekdays = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

    // Json file manager
    private PROG_DAL_B_JSONManager JsonManager;

    // user tim input manager
    private PROG_UI_C_UserTimeInput DataCard_UserTimeInputs;
    


    /**
     * Constructor class
     */
    public PROG_UI_B_DataCardinfoScene(Stage stage) {
        
        this.ApplicationStage = stage;

        this.UserTimeInput = new LinkedList<>();

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
        VBOXUserPreferences = new LinkedList<>();

        // Creates layout of user submitted info before submission
        this.userInputGraphic = new FlowPane();
        this.userInputGraphic.setPrefSize(600.0, 600.0);
        this.userInputGraphic.setPadding(new Insets(10));
        this.userInputGraphic.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: Black;" +
            "-fx-border-width: 2;" +
            "-fx-border-radius: 5;"
        );

        // Root Node - set return home button position
        AnchorPane.setBottomAnchor(ReturnToMenu, 20.0);
        AnchorPane.setRightAnchor(ReturnToMenu, 20.0);
        
        // Root Node - set UI interface input
        AnchorPane.setTopAnchor(UserInterface_Input, 20.0);
        AnchorPane.setLeftAnchor(UserInterface_Input, 20.0);

        // Root Node - set uer cards
        AnchorPane.setTopAnchor(userInputGraphic, 20.0);
        AnchorPane.setRightAnchor(userInputGraphic, 20.0);

        //ResetUserTimePref
        

        this.RootNode.getChildren().addAll(UserInterface_Input, ReturnToMenu, userInputGraphic);
        // ############################################################

        
        // Set Graphical Effects
        SceneEffects();


        // create menu scene with the current node layout
        this.DataCardScene = new Scene(RootNode, PROG_UI_A_SceneManager.WindowWidth, PROG_UI_A_SceneManager.WindowHeight);

        // fade all objects before the scene is set
        this.fadeMenuNodes.play();
        
    }







    /**
     * UserInputGraphic()
     * Descriiption: manages the visual output of the user submitted data.
     */
    private void UserInputGraphic(PROG_DAL_A_TimeInput InputTime) {

        // System.out.println("UserInputGraphic");
        // userInputGraphic = new FlowPane();

        // VBox creation and variables
        VBox IndividualDataCard = new VBox();
        IndividualDataCard.setPrefSize(100.0, 100.0);

        int     Index       = (this.UserTimeInput.size() + 1);
        int     StartHour   = InputTime.PreferedHourBEGIN.getHour();
        String  startMin    = Integer.toString(InputTime.PreferedHourBEGIN.getMinute());
        int     EndHour     = InputTime.PreferedHourEND.getHour();
        String  EndMin      = Integer.toString(InputTime.PreferedHourEND.getMinute());

        // add a leading zero to the start minute
        if (Integer.parseInt(startMin) < 10) {
            startMin = "0" + startMin;
        }

        // add a leading zero the end minute
        if (Integer.parseInt(EndMin) < 10) {
            EndMin = "0" + EndMin;
        }

        String  StartTimeFrame  = "AM";
        String  EndTimeFrame    = "AM";
        
        // if pm is selected for the starting time add 12 hours to the starting hour
        // and ending hour, (am can't occure before pm)
        if (DataCard_UserTimeInputs.Return_AMPM_StartTime().getValue().equals("PM")) {
            StartTimeFrame  = "PM";
            EndTimeFrame    = "PM";
        }

        // if pm is selected for the ending time add 12 hours to the ending time
        if(DataCard_UserTimeInputs.Return_AMPM_EndTime().getValue().equals("PM")) {
            EndTimeFrame    = "PM";
        }

        Label   InputNumber = new Label("Input Number: " + Index);
        InputNumber.setId("" + Index);

        Label   WeekDay     = new Label("WeekDay: " + InputTime.WeekDay);        
        Label   TimeFrame   = new Label("" + StartHour + ":" + startMin + " " + StartTimeFrame + " - " + EndHour + ":" + EndMin + " " + EndTimeFrame);



        // Delete Card Button
        // ############################################################
        Button DeleteCard = new Button("Delete Card");

        EventHandler<ActionEvent> DeleteInfoCard = (ActionEvent e) -> {
            
            // Deletes User Card
            System.out.println("BUTTON CLICK    - CARD MANAGER PAGE - Deleteing Card");
            
            // remove all nodes in the current Vbox
            //IndividualDataCard.getChildren().removeAll(InputNumber, WeekDay, TimeFrame, DeleteCard);
            IndividualDataCard.getChildren().clear();

            // Create new iterator to iterate over nodes in the linkedlist UserPreferences
            VBOXIterator = VBOXUserPreferences.iterator();


            // loop through list to remove empty Vbox node
            int LinkedListIndex = 0;
            while (VBOXIterator.hasNext()) {

                // next Vbox in iterator
                VBox tempBox = VBOXIterator.next();

                if (tempBox.getChildren().isEmpty()) {

                    // removes empty Vbox from the linked list of preferences
                    VBOXIterator.remove();

                    //removes InputTime from UserTimeInput LinkedList
                    UserTimeInput.remove(LinkedListIndex);

                } // if()

                LinkedListIndex++;

            } // for()
            
            // removes node from the parent flowpane
            userInputGraphic.getChildren().remove(IndividualDataCard);



            /**
             * Update index number for each node
             */
            int flowPaneIndex = 1;
            for (Node node: userInputGraphic.getChildren()) {

                if (node instanceof VBox vbox) {
                    Label newLabel = (Label) vbox.getChildren().get(0);   // lookup("#" + flowPaneIndex);
                    if (newLabel != null) {
                        newLabel.setText("Input Number: " + flowPaneIndex);
                        flowPaneIndex++;
                    }
                }

            } // for()

            System.out.println("UserTimeInput ammount" + UserTimeInput.size());

        };

        DeleteCard.setOnAction(DeleteInfoCard);
        // ############################################################


        // adds relevant nodes to the vbox
        IndividualDataCard.getChildren().addAll(
            // Input number: #
            InputNumber,

            // WeekDay: ##
            WeekDay,

            // Beginning time - ending time
            TimeFrame,

            // Button to delete the card
            DeleteCard
        );

        // add to linked list of preferences
        UserTimeInput.add(InputTime);
        System.out.println("UserTimeInput ammount" + UserTimeInput.size());

        // add to linked list Vbox
        VBOXUserPreferences.add(IndividualDataCard);

        // add vbox to flowpane
        userInputGraphic.getChildren().add(IndividualDataCard);
    
    } // UserInputGraphic







    /**
     * cardUIManagement()
     * Description: manages the node layout for the user card input
     */
    private void cardUIManagement() {


        /**
         * Node Management
         */

        // HBox for beginning Hour/Min
        // ############################################################
        this.AddStartTime = new HBox(10);
        //AddStartTime.setPrefSize(300.0, 500.0);
        this.AddStartTime.setPadding(new Insets(10));
        this.AddStartTime.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: Black;" +
            "-fx-border-width: 2;" +
            "-fx-border-radius: 5;"
        );

        this.AddStartTime.getChildren().addAll(

            LabelBeginHour,
            DataCard_UserTimeInputs.Return_Hour_Begin(),

            LabelBeginMinute,
            DataCard_UserTimeInputs.Return_Minute_Begin(),

            DataCard_UserTimeInputs.Return_AMPM_StartTime()

        );
        // ############################################################



        // HBox for Ending Hour/Min
        // ############################################################
        this.addEndingTime = new HBox(10);
        //AddStartTime.setPrefSize(200.0, 400.0);
        this.addEndingTime.setPadding(new Insets(10));
        this.addEndingTime.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: Black;" +
            "-fx-border-width: 2;" +
            "-fx-border-radius: 5;"
        );

        this.addEndingTime.getChildren().addAll(

            LabelEndHour,
            DataCard_UserTimeInputs.Return_Hour_End(),

            LabelEndMinute,
            DataCard_UserTimeInputs.Return_Minute_End(),

            DataCard_UserTimeInputs.Return_AMPM_EndTime()

        );
        // ############################################################



        // Vbox for adding time intervals
        // ############################################################

        this.addTimeInfo_input = new VBox(10);
        //this.addTimeInfo_input.setPrefSize(200.0, 400.0);
        this.addTimeInfo_input.setPadding(new Insets(10));
        this.addTimeInfo_input.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: Black;" +
            "-fx-border-width: 2;" +
            "-fx-border-radius: 5;"
        );

        Region ButtonSpace = new Region();
        VBox.setVgrow(ButtonSpace, Priority.ALWAYS);

        this.addTimeInfo_input.getChildren().addAll(

            LabelDay,
            DataCard_UserTimeInputs.Return_WeekDay(),
            
            LabelBeginningTime,
            AddStartTime,

            LabelEndingTime,
            addEndingTime,

            ButtonSpace,

            AddUserInfo

        );
        // ############################################################



        // Vbox for UI
        // ############################################################
        // Create and set box parameters
        this.UserInterface_Input = new VBox(10);
        this.UserInterface_Input.setPrefSize(400.0, 600.0);
        this.UserInterface_Input.setPadding(new Insets(10));
        this.UserInterface_Input.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: Black;" +
            "-fx-border-width: 2;" +
            "-fx-border-radius: 5;"
        );

        // add nodes to the box
        this.UserInterface_Input.getChildren().addAll(
            Labelname,
            userInput_EmployeeName, 

            LabelID,
            userInput_EmployeeID,

            addTimeInfo_input, 

            ResetUserTimePref,

            SubmitUserInfo
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
        this.ReturnToMenu = new Button("Return Home");

        EventHandler<ActionEvent> ReturnHome = (ActionEvent e) -> {
            
            // Exits the program
            System.out.println("BUTTON CLICK    - CARD MANAGER PAGE - Returning to home page");
            
            fadeMenuNodes.setOnFinished(event -> {
                PROG_UI_A_Application.SceneManager.MainMenu();
            });

            fadeMenuNodes.play();

        };

        this.ReturnToMenu.setOnAction(ReturnHome);
        // ############################################################



        // Add Info Button
        // ############################################################
        this.AddUserInfo = new Button("Add Info");

        EventHandler<ActionEvent> AddInfo = (ActionEvent e) -> {

            // adds user info - does not submit anything
            System.out.println("BUTTON CLICK    - CARD MANAGER PAGE - Add User Info");


            // if (        DataCard_UserTimeInputs.Return_WeekDay()        .getValue() == null)  { // Do nothing
            //     // user has not submitted a weekday
            //     System.out.println("user has not submitted a weekday");

            // } else if ( DataCard_UserTimeInputs.Return_Hour_Begin()     .getText().isBlank())   { // Do nothing
            //     // user has not submitted a beginning hour
            //     System.out.println("user has not submitted a beginning hour");

            // } else if ( DataCard_UserTimeInputs.Return_Minute_Begin()   .getText().isBlank())   { // Do nothing
            //     // user has not submitted a beginning minute
            //     System.out.println("user has not submitted a beginning minute");

            // } else if ( DataCard_UserTimeInputs.Return_Hour_End()       .getText().isBlank())   { // Do nothing
            //     // user has not submitted a ending hour
            //     System.out.println("user has not submitted a ending hour");

            // } else if ( DataCard_UserTimeInputs.Return_Minute_End()     .getText().isBlank())   { // Do nothing
            //     // user has not submitted a ending minute
            //     System.out.println("user has not submitted a ending minute");
            // } else {

            //     // Correct info has been submitted
            //     System.out.println("Correct info has been submitted");

            //     String  WeekDay     = DataCard_UserTimeInputs.Return_WeekDay()                      .getValue();
            //     int     BeginHour   = Integer.parseInt(DataCard_UserTimeInputs.Return_Hour_Begin()  .getText());
            //     int     BeginMinute = Integer.parseInt(DataCard_UserTimeInputs.Return_Minute_Begin().getText());
            //     int     EndHour     = Integer.parseInt(DataCard_UserTimeInputs.Return_Hour_End()    .getText());
            //     int     EndMinute   = Integer.parseInt(DataCard_UserTimeInputs.Return_Minute_End()  .getText());

            //     // new user preference
            //     PROG_DAL_A_TimeInput UserPreference = new PROG_DAL_A_TimeInput(WeekDay, BeginHour, BeginMinute, EndHour, EndMinute);

                DataCard_UserTimeInputs.ButtonPressTimeInput();


                // add preference to flowpane
                UserInputGraphic(DataCard_UserTimeInputs.Return_UserPreference());
                
                // null for garbage collection
                // UserPreference = null;

            //} // else ()

        };

        this.AddUserInfo.setOnAction(AddInfo);
        // ############################################################



        // Submit UserInfo
        // ############################################################
        this.SubmitUserInfo = new Button("Submit Info");

        EventHandler<ActionEvent> SubmitInfo = (ActionEvent e) -> {
            
            // Submits USer info
            System.out.println("BUTTON CLICK    - CARD MANAGER PAGE - Submit User Info");

            // collect all info into the relvant linkedlist
            // Employee name

            if          (userInput_EmployeeName .getText().isBlank())       { // Do nothing
                // user has not submitted a valid name
                System.out.println("user has not submitted a valid name");

            } else if   (userInput_EmployeeID   .getText().isBlank())       { // Do nothing
                // user has not submitted a valid name
                System.out.println("user has not submitted a valid ID");

            } else if   (UserTimeInput          .size() < 1)                { // Do nothing
                // user has not submitted valid user times
                System.out.println("user has not submitted valid user times");

            } else {


                String name    = userInput_EmployeeName.getText();
                int    id      = Integer.parseInt(userInput_EmployeeID.getText());

                // preferred week days linked list
                LinkedList<String> preferredDaysList = new LinkedList<>();

                // iterate through weekdays first to ensure a weekday can only be matched once
                for (String Day : PROG_UI_D_DataVariables.WEEKDAYS) {

                    for (PROG_DAL_A_TimeInput preference : this.UserTimeInput) {
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


                // PROG_DAL_A_InfoInput(String name, int ID, String[] week, LinkedList<PROG_DAL_A_TimeInput> times);
                InfoInputPreferences.add(new PROG_DAL_A_InfoInput(name, id, preferredDays, this.UserTimeInput));

                // testing
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
                // UserTimeInput           = new LinkedList<>();
                InfoInputPreferences    = new LinkedList<>();
        }

        };
        this.SubmitUserInfo.setOnAction(SubmitInfo);
        // ############################################################



        // reset usertimeinput
        // ############################################################
        this.ResetUserTimePref = new Button("Reset added time preferences");

        EventHandler<ActionEvent> ResetTimePreference = (ActionEvent e) -> {

            // linkedlist Vbox full of user preferences
            RemoveAllVBOXIterator = VBOXUserPreferences.iterator();
            //int LinkedListIndex = 0;

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
                    System.out.println("user Input :" + UserTimeInput.size());
                    // System.out.println("user Input index :" + LinkedListIndex);
                    UserTimeInput.remove(0);

                } // if()

                

                //LinkedListIndex++;

            } // for()


            /**
             * remove nodes from the flowpane
             */
            for (Node node: userInputGraphic.getChildren()) {
                if (node instanceof VBox vbox) {
                    vbox.getChildren().clear();
                }

            } // for()

            userInputGraphic.getChildren().clear();
            

        };
        this.ResetUserTimePref.setOnAction(ResetTimePreference);

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


    }


    




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



        // // Weekday Input
        // // ############################################################
        // this.userInput_WeekDaySelection = new ComboBox<>();
        // this.userInput_WeekDaySelection.getItems().addAll(PROG_UI_D_DataVariables.WEEKDAYS);
        // this.userInput_WeekDaySelection.setPrefSize(100, 25.0);
        // // ############################################################



        // // Beginning Hour Input
        // // ############################################################
        // this.userInput_BeginningHourSelection = new TextField();
        // this.userInput_BeginningHourSelection.setPrefSize(50, 25.0);
        // this.userInput_BeginningHourSelection.setTextFormatter(new TextFormatter<>(change -> {
            
        //     // User input text
        //     String TextInput = change.getControlNewText();

        //     // if th etext is empty accept it
        //     if (TextInput.isEmpty()) {
        //         return change;
        //     }

        //     // test if the text is a valid int within a valid range
        //     try {
        //         int intValue = Integer.parseInt(TextInput);

        //         if (intValue >= 0 && intValue <= 12) {
        //             return change;
        //         }
        //     } catch (NumberFormatException e) {
        //         // Invalid Input
        //     }

        //     return null;
        // }));
        // // ############################################################



        // // Beginning Minute Input
        // // ############################################################
        // this.userInput_BeginningMinuteSeleciton = new TextField();
        // this.userInput_BeginningMinuteSeleciton.setPrefSize(50, 25.0);
        // this.userInput_BeginningMinuteSeleciton.setTextFormatter(new TextFormatter<>(change -> {
            
        //     // User input text
        //     String TextInput = change.getControlNewText();

        //     // if th etext is empty accept it
        //     if (TextInput.isEmpty()) {
        //         return change;
        //     }

        //     // test if the text is a valid int within a valid range
        //     try {
        //         int intValue = Integer.parseInt(TextInput);

        //         if (intValue >= 0 && intValue < 60) {
        //             return change;
        //         }
        //     } catch (NumberFormatException e) {
        //         // Invalid Input
        //     }

        //     return null;
        // }));
        // // ############################################################



        // // CheckBox for beginning AM/PM
        // // ############################################################
        // this.StartTimeAMPM = new ComboBox<>();
        // this.StartTimeAMPM.getItems().addAll("AM", "PM");
        // this.StartTimeAMPM.getSelectionModel().select("AM");
        // this.StartTimeAMPM.valueProperty().addListener((observed, oldvalue, newvalue) -> {

        //     if (newvalue.equals("PM")) {
        //         this.EndTimeAMPM.getSelectionModel().select("PM");
        //     }

        // });
        // // ############################################################



        // // Ending Hour Input
        // // ############################################################
        // this.userInput_EndingHourSeleciton = new TextField();
        // this.userInput_EndingHourSeleciton.setPrefSize(50, 25.0);
        // this.userInput_EndingHourSeleciton.setTextFormatter(new TextFormatter<>(change -> {
            
        //     // User input text
        //     String TextInput = change.getControlNewText();

        //     // if th etext is empty accept it
        //     if (TextInput.isEmpty()) {
        //         return change;
        //     }

        //     // ensures end hour is never before the beginning hour
        //     if (!userInput_BeginningHourSelection.getText().isBlank()) {

        //         if ( (Integer.parseInt(userInput_BeginningHourSelection.getText()) > Integer.parseInt(TextInput)) 
        //             && (StartTimeAMPM.getValue().equals(EndTimeAMPM.getValue())) ) {
        //             userInput_EndingHourSeleciton.setText(userInput_BeginningHourSelection.getText());
        //         }

        //     }

        //     // test if the text is a valid int within a valid range
        //     try {
        //         int intValue = Integer.parseInt(TextInput);

        //         if (intValue >= 0 && intValue <= 12) {
        //             return change;
        //         }
        //     } catch (NumberFormatException e) {
        //         // Invalid Input
        //     }

        //     return null;
        // }));
        // // ############################################################



        // // Ending Minute Input
        // // ############################################################
        // this.userInput_EndingMinuteSeleciton = new TextField();
        // this.userInput_EndingMinuteSeleciton.setPrefSize(50, 25.0);
        // this.userInput_EndingMinuteSeleciton.setTextFormatter(new TextFormatter<>(change -> {
            
        //     // User input text
        //     String TextInput = change.getControlNewText();

        //     // if th etext is empty accept it
        //     if (TextInput.isEmpty()) {
        //         return change;
        //     }

        //     // if both the beginning and ending hour are the same and at the same period (AM/PM) ensure the ending minute is always later
        //     if ((!userInput_BeginningHourSelection.getText().isBlank()) && (!userInput_EndingHourSeleciton.getText().isBlank())) {

        //         // TODO: organize the nonsense
        //         if ( (Integer.parseInt(userInput_BeginningHourSelection.getText()) == Integer.parseInt(userInput_EndingHourSeleciton.getText()) )
        //             && ( Integer.parseInt(TextInput) < Integer.parseInt(userInput_BeginningMinuteSeleciton.getText()) )
        //             && (StartTimeAMPM.getValue().equals(EndTimeAMPM.getValue())) ) {

        //             userInput_EndingMinuteSeleciton.setText(userInput_BeginningMinuteSeleciton.getText());

        //         }

        //     }

        //     // test if the text is a valid int within a valid range
        //     try {
        //         int intValue = Integer.parseInt(TextInput);

        //         if (intValue >= 0 && intValue < 60) {
        //             return change;
        //         }

        //     } catch (NumberFormatException e) {
        //         // Invalid Input
        //     }

        //     return null;
        // }));
        // // ############################################################


        // // CheckBox for Ending AM/PM
        // // ############################################################
        // this.EndTimeAMPM = new ComboBox<>();
        // this.EndTimeAMPM.getItems().addAll("AM", "PM");
        // this.EndTimeAMPM.getSelectionModel().select("AM");
        // // ############################################################

    } // UI_UserInputs()



    /**
     * SceneEffects()
     * Description: graphical manipulations and effects
     */
    private void SceneEffects() {

        /**
         * Scene edits
         */
        // ############################################################
        // Add background gradient
        LinearGradient BackgroundGradient = new LinearGradient(0, 0, 300, 300, false, CycleMethod.NO_CYCLE, 
            new Stop(0, Color.DARKBLUE), new Stop(1, Color.BEIGE)
        );

        this.backgroundFill = new BackgroundFill(BackgroundGradient, CornerRadii.EMPTY, Insets.EMPTY);

        this.RootNode.setBackground(new Background(backgroundFill));

        // Transition to fade buttons
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



        // Transition to Unfade buttons
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

    }
}
