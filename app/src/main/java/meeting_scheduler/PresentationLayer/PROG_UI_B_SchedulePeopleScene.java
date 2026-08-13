package meeting_scheduler.PresentationLayer;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.Collectors;

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
import javafx.scene.control.Labeled;
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

// schedule calculator
import meeting_scheduler.BusinessLogiclayer.PROG_BLL_SchedulingCalculation;
import meeting_scheduler.DataAccessLayer.PROG_DAL_A_Schedule;
import meeting_scheduler.DataAccessLayer.PROG_DAL_A_TimeInput;

/**
 * PROG_UI_B_SchedulePeopleScene()
 * 
 * Description: Application window scene which allows the user to make schedules based off of existing user card information
 * Contains:
 * a. back button       (scene change)
 * b. UI interface allowing user to make schedules    (various buttons, text inputs etc)
 */




public class PROG_UI_B_SchedulePeopleScene {

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
    private Button      Input_SelectedDay;

    // labels
    private Label       Label_inputDay;
    private Label       Label_OutputDay;
    private Label       Label_inputTime;
    private Label       Label_inputNumber;
    private Label       Label_ScheduleNow;

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

    // Time Input manager
    private PROG_UI_C_UserTimeInput Scheduler_UserTimeInputs;

    // Calculated Schedules
    private LinkedList<PROG_DAL_A_Schedule>     CalculatedScheduleList;

    // user input graphic variables
    private LinkedList<PROG_DAL_A_TimeInput>    UserTimeInput;
    private Iterator<VBox>                      VBOXIterator;
    private LinkedList<VBox>                    VBOXUserPreferences;
    private FlowPane                            userInputGraphic;




    /**
     * Constructor class
     */
    public PROG_UI_B_SchedulePeopleScene(Stage stage) {
        this.ApplicationStage = stage;
        
        this.ScheduleCalculator = new PROG_BLL_SchedulingCalculation();

        this.Scheduler_UserTimeInputs = new PROG_UI_C_UserTimeInput();
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
        
        // Node Creation
        // ############################################################
        // Primary Node
        HBoxInputListNumber = new HBox(10);
        HBoxInputListNumber.getStyleClass().add("UserPreference-box");
        HBoxInputListNumber.setPrefSize(400.0, 100.0);
        HBoxInputListNumber.setPadding(new Insets(10));

        // Secondary Node - Input
        Holder_ListAmmount = new VBox(10);

        // Secodnary Node - output

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
        // ############################################################
        


        // Output
        // ############################################################
        // TODO: possibly add label which displays number
        // ############################################################


        // Add all to the nodes
        // ############################################################
        // Secondary Node - output

        // secondary Node - Input
        Holder_ListAmmount.getChildren().addAll(Label_inputNumber, userInput_listAmmount);

        // Primary Node
        HBoxInputListNumber.getChildren().addAll(Holder_ListAmmount);
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
        this.HBoxInputDayPreference.setPrefSize(400.0, 100.0);
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
        this.HBoxInputTimePreference.setPrefSize(400.0, 100.0);
        this.HBoxInputTimePreference.setPadding(new Insets(10));

        // Secodnary Node - input
        this.Holder_TimePreference = new VBox(10);

        // Secondary Node - output

        // ############################################################



        // Input Section
        // ############################################################
        // label
        this.Label_inputTime = new Label("Input the times to schedule");
        this.Label_inputTime.getStyleClass().add("default-label");

        // TODO: create interface


        // ############################################################



        // Output Section
        // ############################################################
        // TODO: create Output Section
        // ############################################################



        // Add all to the nodes
        // ############################################################

        // secondary node - output


        // seconary node - input
        this.Holder_TimePreference.getChildren().addAll(Label_inputTime);

        // primary node
        this.HBoxInputTimePreference.getChildren().addAll(Holder_TimePreference);
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
        this.HBoxCalculaeSchedule.setPrefSize(400.0, 100.0);
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
     * UserInputGraphic()
     * Descriiption: manages the visual output of the user submitted data.
     */
    private void UserInputGraphic(PROG_DAL_A_TimeInput InputTime) {

        // UserTimeInput        -   individual user Day/time preference     -   private LinkedList<PROG_DAL_A_TimeInput>    UserTimeInput;

        // VBOXIterator         -   iterates over nodes in Vbox             -   private Iterator<VBox>                      VBOXIterator;

        // VBOXUserPreferences  -   Cardlist of each VBox that holds a time -   private LinkedList<VBox>                    VBOXUserPreferences;

        // userInputGraphic     -   FlowPane that stores all the Vboxes     -   private FlowPane                            userInputGraphic;

        if (userInputGraphic.getChildren().size() < 4) {

            
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

            String  StartTimeFrame  = Scheduler_UserTimeInputs.Return_AMPM_StartTime().getValue(); //= "AM";

            String  EndTimeFrame    = Scheduler_UserTimeInputs.Return_AMPM_EndTime().getValue();
            
            // if pm is selected for the starting time add 12 hours to the starting hour
            // and ending hour, (am can't occure before pm)
            // if (Scheduler_UserTimeInputs.Return_AMPM_StartTime().getValue().equals("PM")) {
            //     StartTimeFrame  = "PM";
            //     EndTimeFrame    = "PM";
            // }

            // // if pm is selected for the ending time add 12 hours to the ending time
            // if(Scheduler_UserTimeInputs.Return_AMPM_EndTime().getValue().equals("PM")) {
            //     EndTimeFrame    = "PM";
            // }

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

                InputNumber,    // Input number: ##

                WeekDay,        // WeekDay: day

                TimeFrame,      // Beginning time - ending time

                DeleteCard      // Button to delete the card
            );

            // add to linked list of preferences
            UserTimeInput.add(InputTime);
            System.out.println("UserTimeInput ammount" + UserTimeInput.size());

            // add to linked list Vbox
            VBOXUserPreferences.add(IndividualDataCard);

            // add vbox to flowpane
            userInputGraphic.getChildren().add(IndividualDataCard);


        } // if()
    
    } // UserInputGraphic


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
