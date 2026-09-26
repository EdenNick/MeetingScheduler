/**
 * PROG_UI_C_UserTimeInput.java
 * 
 * Description: Manager for user time inputs for both the data card scene and the scheduler scene
 * 
 */

// Package  - DO Not Change
// ############################################################
package meeting_scheduler.UserInput;
// ############################################################


// Imports
// ############################################################
// Util
import java.util.Iterator;
import java.util.LinkedList;
// Javafx
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
// ############################################################
import meeting_scheduler.DataAccessLayer.PROG_DAL_D_SystemMessages;
import meeting_scheduler.EmployeePreferences.PREF_EMPLOYEE_TimePref;
import meeting_scheduler.SceneManagement.SCENE_VARIABLES_Local;

import meeting_scheduler.global;

public class USERINPUT_TimeInputManager {
    

    private final USERINPUT_TextList OBJ_TextList_Weekday;

    private final USERINPUT_DayTimeInput OBJ_DayTimeInput_Times;


    private final ComboBox<String> Manager_WeekDay;

    private final ComboBox<String> Manager_AMPM_Start;
    private final ComboBox<String> Manager_AMPM_End;

    private final TextField Manager_StartTime_Hour;
    private final TextField Manager_StartTime_Min;
    private final TextField Manager_EndTime_Hour;
    private final TextField Manager_EndTime_Min;

    private PREF_EMPLOYEE_TimePref FullUserPreference;
    private PREF_EMPLOYEE_TimePref PartialUserPreference;

    private Iterator<Node>      Iterator_FlowPaneDisplay;

    private HBox UI_AddStartTime;
    private HBox UI_addEndingTime;

    private VBox UI_TimeInterface;

    private Label LabelDay;
    private Label LabelBeginningTime;
    private Label LabelBeginHour;
    private Label LabelBeginMinute;
    private Label LabelEndingTime;
    private Label LabelEndHour;
    private Label LabelEndMinute;


    //private final MANAGESCHEDULE_Calculate SCHEDULECALCULATOR;


    // TODO: implement more system messages
    
    /**
     * Constructor
     */
    public USERINPUT_TimeInputManager(Button UI_INPUT_AddPreferenceButton) {

        // Objects
        // ############################################################
        this.OBJ_TextList_Weekday   = new USERINPUT_TextList(global.TextListState.WEEK, null, 0, 0);
        this.OBJ_DayTimeInput_Times = new USERINPUT_DayTimeInput();
        // ############################################################

        // UI
        // ############################################################
        this.Manager_WeekDay        = OBJ_TextList_Weekday.Return_Field_Constructed();

        this.Manager_AMPM_Start     = OBJ_DayTimeInput_Times.Return_ComboBox_StartTime();
        this.Manager_AMPM_End       = OBJ_DayTimeInput_Times.Return_ComboBox_EndTime();

        this.Manager_StartTime_Hour = OBJ_DayTimeInput_Times.Return_TextField_Hour_StartTime();
        this.Manager_StartTime_Min  = OBJ_DayTimeInput_Times.Return_TextField_Min_StartTime();
        this.Manager_EndTime_Hour   = OBJ_DayTimeInput_Times.Return_TextField_Hour_EndTime();
        this.Manager_EndTime_Min    = OBJ_DayTimeInput_Times.Return_TextField_Min_EndTime();
        // ############################################################


        // CREATE labels
        CONSTRUCT_UI_Labels();

        // Create UI
        CONSTRUCT_UI_TimeInput(UI_INPUT_AddPreferenceButton);


        // sets size and shape
        SET_SizeShape();
    }


    // TEMP
    public USERINPUT_TimeInputManager() {

        this.OBJ_TextList_Weekday   = new USERINPUT_TextList(global.TextListState.WEEK, null, 0, 0);

        this.OBJ_DayTimeInput_Times = new USERINPUT_DayTimeInput();

        this.Manager_WeekDay        = OBJ_TextList_Weekday.Return_Field_Constructed();

        this.Manager_AMPM_Start     = OBJ_DayTimeInput_Times.Return_ComboBox_StartTime();
        this.Manager_AMPM_End       = OBJ_DayTimeInput_Times.Return_ComboBox_EndTime();

        this.Manager_StartTime_Hour = OBJ_DayTimeInput_Times.Return_TextField_Hour_StartTime();
        this.Manager_StartTime_Min  = OBJ_DayTimeInput_Times.Return_TextField_Min_StartTime();
        this.Manager_EndTime_Hour   = OBJ_DayTimeInput_Times.Return_TextField_Hour_EndTime();
        this.Manager_EndTime_Min    = OBJ_DayTimeInput_Times.Return_TextField_Min_EndTime();


        // CREATE labels
        CONSTRUCT_UI_Labels();

        // Create UI
        //CONSTRUCT_UI_TimeInput(UI_INPUT_AddPreferenceButton);

        // sets size and shape
        SET_SizeShape();
    }


    // Returns user preferences meant for input into Json File
    public PREF_EMPLOYEE_TimePref Return_UserPreference() {
        return this.FullUserPreference;
    }

    public VBox Return_UI_TimeInput() {
        return this.UI_TimeInterface;
    }



    private void SET_SizeShape() {
        // this.Manager_WeekDay
        this.Manager_WeekDay.setPrefSize(100.0,25.0);

        // this.Manager_AMPM_Start
        this.Manager_AMPM_Start.setPrefSize(80.0,25.0);
        // this.Manager_AMPM_End
        this.Manager_AMPM_End.setPrefSize(80.0,25.0);

        // this.Manager_StartTime_Hour
        this.Manager_StartTime_Hour.setPrefSize(60.0,25.0);
        // this.Manager_StartTime_Min
        this.Manager_StartTime_Min.setPrefSize(60.0,25.0);
        // this.Manager_EndTime_Hour
        this.Manager_EndTime_Hour.setPrefSize(60.0,25.0);
        // this.Manager_EndTime_Min
        this.Manager_EndTime_Min.setPrefSize(60.0,25.0);
    }


    private void CONSTRUCT_UI_Labels() {
        // Label - Weekday Prompt
        this.LabelDay           = new Label(SCENE_VARIABLES_Local.Prompt_Day);
        this.LabelDay.getStyleClass().add(SCENE_VARIABLES_Local.STYLE_DEFAULT);


        // Label - Beginning time Prompt
        this.LabelBeginningTime = new Label(SCENE_VARIABLES_Local.Prompt_BeginningTime);
        this.LabelBeginningTime.getStyleClass().add(SCENE_VARIABLES_Local.STYLE_DEFAULT);


        // Label - Beginning Hour Label
        this.LabelBeginHour     = new Label(SCENE_VARIABLES_Local.Prompt_HourLabel);
        this.LabelBeginHour.getStyleClass().add(SCENE_VARIABLES_Local.STYLE_DEFAULT);


        // Label - Beginning Minute Label
        this.LabelBeginMinute   = new Label(SCENE_VARIABLES_Local.Prompt_MinuteLabel);
        this.LabelBeginMinute.getStyleClass().add(SCENE_VARIABLES_Local.STYLE_DEFAULT);


        // Label - Ending time Prompt
        this.LabelEndingTime    = new Label(SCENE_VARIABLES_Local.Prompt_EndingTime);
        this.LabelEndingTime.getStyleClass().add(SCENE_VARIABLES_Local.STYLE_DEFAULT);


        // Label - Ending hour Label
        this.LabelEndHour       = new Label(SCENE_VARIABLES_Local.Prompt_HourLabel);
        this.LabelEndHour.getStyleClass().add(SCENE_VARIABLES_Local.STYLE_DEFAULT);


        // Label - Ending Minute Label
        this.LabelEndMinute     = new Label(SCENE_VARIABLES_Local.Prompt_MinuteLabel);
        this.LabelEndMinute.getStyleClass().add(SCENE_VARIABLES_Local.STYLE_DEFAULT);
    } // CONSTRUCT_UI_Labels()




    private void CONSTRUCT_UI_TimeInput(Button UI_INPUT_AddPreferenceButton) {

        // HBox for beginning Hour/Min
        // ############################################################
        this.UI_AddStartTime = new HBox(10);
        //AddStartTime.setPrefSize(300.0, 500.0);
        this.UI_AddStartTime.setPadding(new Insets(10));
        this.UI_AddStartTime.getStyleClass().add(SCENE_VARIABLES_Local.STYLE_DATACARD_TimePref);

        this.UI_AddStartTime.getChildren().addAll(

            LabelBeginHour,
            this.Manager_StartTime_Hour,

            LabelBeginMinute,
            this.Manager_StartTime_Min,

            this.Manager_AMPM_Start

        );
        // ############################################################



        // HBox for Ending Hour/Min
        // ############################################################
        this.UI_addEndingTime = new HBox(10);
        //AddStartTime.setPrefSize(200.0, 400.0);
        this.UI_addEndingTime.setPadding(new Insets(10));
        this.UI_addEndingTime.getStyleClass().add(SCENE_VARIABLES_Local.STYLE_DATACARD_TimePref);
        this.UI_addEndingTime.getChildren().addAll(

            LabelEndHour,
            this.Manager_EndTime_Hour,

            LabelEndMinute,
            this.Manager_EndTime_Min,

            Manager_AMPM_End

        );
        // ############################################################



        // Vbox for adding time intervals
        // ############################################################
        this.UI_TimeInterface = new VBox(10);
        //this.addTimeInfo_input.setPrefSize(200.0, 400.0);
        this.UI_TimeInterface.setPadding(new Insets(10));
        this.UI_TimeInterface.getStyleClass().add(SCENE_VARIABLES_Local.STYLE_DATACARD_TimePref);

        Region ButtonSpace = new Region();
        VBox.setVgrow(ButtonSpace, Priority.ALWAYS);

        this.UI_TimeInterface.getChildren().addAll(

            LabelDay,
            this.Manager_WeekDay,
            
            LabelBeginningTime,
            UI_AddStartTime,

            LabelEndingTime,
            UI_addEndingTime,

            ButtonSpace,

            UI_INPUT_AddPreferenceButton

        );
        // ############################################################
    } // CONSTRUCT_UI_TimeInput




    /**
     * ButtonPressTimeInput()
     * Description: checks to ensure all variables have been input then creates a user preference to return
     */
    public int CHECK_FullTimeInput() {

        if (this.Manager_WeekDay.getValue() == null)  {               // Error Return
            // user has not submitted a weekday
            System.out.println(PROG_DAL_D_SystemMessages.INFO_PreferenceInput_weekday);
            return 1;

        } else if (this.Manager_StartTime_Hour.getText().isBlank())   {   // Error Return
            // user has not submitted a beginning hour
            System.out.println(PROG_DAL_D_SystemMessages.INFO_PreferenceInput_StartHour);
            return 1;

        } else if (this.Manager_StartTime_Min.getText().isBlank()) {   // Error Return
            // user has not submitted a beginning minute
            System.out.println(PROG_DAL_D_SystemMessages.INFO_PreferenceInput_StartMin);
            return 1;

        } else if (this.Manager_EndTime_Hour.getText().isBlank())     {   // Error Return
            // user has not submitted a ending hour
            System.out.println(PROG_DAL_D_SystemMessages.INFO_PreferenceInput_EndHour);
            return 1;

        } else if (this.Manager_EndTime_Min.getText().isBlank())   {   // Error Return
            // user has not submitted a ending minute
            System.out.println(PROG_DAL_D_SystemMessages.INFO_PreferenceInput_EndMin);
            return 1;

        } else {                                                // No Error return

            // Correct info has been submitted
            System.out.println(PROG_DAL_D_SystemMessages.PASS_PreferenceInput_CorrectInput);

            String  WeekDay     = this.Manager_WeekDay.getValue();
            int     BeginHour   = Integer.parseInt(this.Manager_StartTime_Hour  .getText());
            int     BeginMinute = Integer.parseInt(this.Manager_StartTime_Min   .getText());
            int     EndHour     = Integer.parseInt(this.Manager_EndTime_Hour    .getText());
            int     EndMinute   = Integer.parseInt(this.Manager_EndTime_Min     .getText());

            // new user preference
            this.FullUserPreference = new PREF_EMPLOYEE_TimePref(WeekDay, BeginHour, BeginMinute, EndHour, EndMinute);
            
            return 0;

        } // else ()

    } // ButtonPressTimeInput()



    /**
     * ButtonPressTimeInput()
     * Description: checks to ensure all variables beside the weekday have been input then creates a user preference to return
     */
    public int CHECK_PartialTimeInput() {

        if (this.Manager_StartTime_Hour.getText().isBlank())   {   // Error Return
            // user has not submitted a beginning hour
            System.out.println(PROG_DAL_D_SystemMessages.INFO_PreferenceInput_StartHour);
            return 1;

        } else if (this.Manager_StartTime_Min.getText().isBlank()) {   // Error Return
            // user has not submitted a beginning minute
            System.out.println(PROG_DAL_D_SystemMessages.INFO_PreferenceInput_StartMin);
            return 1;

        } else if (this.Manager_EndTime_Hour.getText().isBlank())     {   // Error Return
            // user has not submitted a ending hour
            System.out.println(PROG_DAL_D_SystemMessages.INFO_PreferenceInput_EndHour);
            return 1;

        } else if (this.Manager_EndTime_Min.getText().isBlank())   {   // Error Return
            // user has not submitted a ending minute
            System.out.println(PROG_DAL_D_SystemMessages.INFO_PreferenceInput_EndMin);
            return 1;

        } else {                                                // No Error return

            // Correct info has been submitted
            System.out.println(PROG_DAL_D_SystemMessages.PASS_PreferenceInput_CorrectInput);

            int     BeginHour   = Integer.parseInt(this.Manager_StartTime_Hour  .getText());
            int     BeginMinute = Integer.parseInt(this.Manager_StartTime_Min   .getText());
            int     EndHour     = Integer.parseInt(this.Manager_EndTime_Hour    .getText());
            int     EndMinute   = Integer.parseInt(this.Manager_EndTime_Min     .getText());

            // new user preference
            this.FullUserPreference = new PREF_EMPLOYEE_TimePref(null, BeginHour, BeginMinute, EndHour, EndMinute);
            
            return 0;

        } // else ()

    } // ButtonPressTimeInput()



    /**
     * UserInputGraphicCalculation()
     * Descriiption: manages the visual output of the user submitted data for card info input
     */
    public void UserInputGraphic(PREF_EMPLOYEE_TimePref Input_UserTime, LinkedList<PREF_EMPLOYEE_TimePref> List_UserTimes, FlowPane FlowPane_VBoxDisplay, boolean FullInput) {


        // PROG_DAL_A_TimeInput Input_UserTime              - Input time being processed and formatted correctly

        // LinkedList<PROG_DAL_A_TimeInput> List_UserTimes  - holds the preferred times of each user

        // FlowPane FlowPane_VBoxDisplay                    - The flowpane that holds the vboxes that will actually be displayed, should contain the same vboxes that VBOXUserPreferences has
        
        // boolean FullInput                                - boolean for if the weekday needs to be input or not, true for datacard info, false for scheduling



        // VBox creation and preference set
        // ############################################################
        VBox IndividualDataCard = new VBox();
        IndividualDataCard.setPrefSize(130.0, 100.0);
        IndividualDataCard.getStyleClass().add(SCENE_VARIABLES_Local.STYLE_DATACARD_TimeOutputCard);
        IndividualDataCard.setAlignment(Pos.CENTER);
        // ############################################################


        // VBox data variable creation
        // ############################################################
        // index position
        int Index = (List_UserTimes.size() + 1);

        // Starting Time
        String  StartTimeFrame  = this.Manager_AMPM_Start.getValue();
        int     StartHour       = Input_UserTime.GetStartTimeHour();
        String  startMin        = Integer.toString(Input_UserTime.GetStartTimeMin());

        // Ending Time
        String  EndTimeFrame    = this.Manager_AMPM_End.getValue();
        int     EndHour         = Input_UserTime.GetEndTimeHour();
        String  EndMin          = Integer.toString(Input_UserTime.GetEndTimeMin());

        // Add a leading zero to the start of the minute inputs if it is less than 10
        if (Integer.parseInt(startMin)  < 10) {
            startMin = "0" + startMin;
        }

        if (Integer.parseInt(EndMin)    < 10) {
            EndMin = "0" + EndMin;
        }
        // ############################################################


        // Text Labels for the Vbox
        // ############################################################
        // Input number
        Label   InputNumber = new Label("Input Number: " + Index);
        InputNumber.setId("" + Index);
        // weekday
        Label   WeekDay     = new Label("WeekDay: " + Input_UserTime.GetWeekDay());
        // Timeframe
        Label   TimeFrame   = new Label("" + StartHour + ":" + startMin + " " + StartTimeFrame + " - " + EndHour + ":" + EndMin + " " + EndTimeFrame);
        // ############################################################


        // Delete Card Button   -   Handles deleting the Vbox
        // ############################################################
        Button DeleteCard = new Button("Delete Card");

        EventHandler<ActionEvent> DeleteInfoCard = (ActionEvent e) -> {
            
            // System Message
            System.out.println("BUTTON CLICK    - CARD MANAGER PAGE - Deleteing Card");
            
            // remove all nodes in the current Vbox
            IndividualDataCard.getChildren().clear();

            // Create new iterator to iterate over nodes in the linkedlist UserPreferences
            Iterator_FlowPaneDisplay = FlowPane_VBoxDisplay.getChildren().iterator();

            // loop through list to remove empty Vbox node
            int LinkedListIndex = 0;
            while (Iterator_FlowPaneDisplay.hasNext()) {

                // next Vbox in iterator
                Node Node_FlowPane = Iterator_FlowPaneDisplay.next();

                // if the Vbox is empty remove it from the list and remove the relevant time preference from List_UserTimes
                if (Node_FlowPane instanceof VBox Vbox_flowPane) {
                    if (Vbox_flowPane.getChildren().isEmpty()) {

                        // removes empty Vbox from the linked list FlowPane_VBoxDisplay
                        FlowPane_VBoxDisplay.getChildren().remove(Vbox_flowPane);
                        // removes InputTime from List_UserTimes LinkedList
                        List_UserTimes.remove(LinkedListIndex);
                        break;

                    } // if()
                }

                LinkedListIndex++;

            } // while (Iterator_FlowPaneDisplay.hasNext())
            

            // removes the VBox node from the flowPane
            FlowPane_VBoxDisplay.getChildren().remove(IndividualDataCard);


            // Update the Index number for each node everytime the flowPane changes
            int flowPaneIndex = 1;
            for (Node node: FlowPane_VBoxDisplay.getChildren()) {

                if (node instanceof VBox vbox) {
                    Label newLabel = (Label) vbox.getChildren().get(0);   // lookup("#" + flowPaneIndex);
                    if (newLabel != null) {
                        newLabel.setText("Input Number: " + flowPaneIndex);
                        flowPaneIndex++;
                    }
                }

            } // for (Node node: FlowPane_VBoxDisplay.getChildren())



        }; // Event Handler

        DeleteCard.setOnAction(DeleteInfoCard);
        // ############################################################





        // VBox add the relevant nodes
        // ############################################################
        if (FullInput == true) {

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

        } else {

            IndividualDataCard.getChildren().addAll(
                // Input number: #
                InputNumber,

                // WeekDay: ##
                // WeekDay,

                // Beginning time - ending time
                TimeFrame,

                // Button to delete the card
                DeleteCard
            );

        }
        // ############################################################




        /**
         * Due to formatting, hours must be adjusted when they are input into the List_UserTimes LinkedList
         * 
         * if any of the times are set to PM, the hour should be incremented by + 12, since the object holds military time, and can't differentiate between AM/PM by itself
         */

        // User prefered time inputs
        // ############################################################

        // time input preferences
        String  NewWeekday;
        int     NewStartHour;
        int     NewStartMin;
        int     NewEndHour;
        int     NewEndMin;

        if (FullInput == true) {
            NewWeekday      = Input_UserTime.GetWeekDay();
        } else {
            NewWeekday      = null;
        }

        // Start Hour
        // if Pm is selected and the time isn't 12, incremented by + 12, else just use the normal time
        if ( (StartTimeFrame.equals("PM")) && (Input_UserTime.GetStartTimeHour() < 12) ) {
            NewStartHour    = Input_UserTime.GetStartTimeHour() + 12;
        } else {
            NewStartHour    = Input_UserTime.GetStartTimeHour();
        }

        // Start Minute
        NewStartMin         = Input_UserTime.GetStartTimeMin();

        // End Hour
        // if Pm is selected, incremented by + 12, else just use the normal time
        if ( (EndTimeFrame.equals("PM")) && (Input_UserTime.GetEndTimeHour() < 12) ){
            NewEndHour      = Input_UserTime.GetEndTimeHour() + 12;
        } else {
            NewEndHour      = Input_UserTime.GetEndTimeHour();
        }

        // Ends Minute
        NewEndMin           = Input_UserTime.GetEndTimeMin();
        // ############################################################


        // List_UserTimes - list of all timeinputs
        // ############################################################
        List_UserTimes.add(new PREF_EMPLOYEE_TimePref(NewWeekday, NewStartHour, NewStartMin, NewEndHour, NewEndMin));
        System.out.println("UserTimeInput ammount" + List_UserTimes.size()); // TODO: remove
        // ############################################################


        // add vbox to flowpane
        // ############################################################
        FlowPane_VBoxDisplay.getChildren().add(IndividualDataCard);
        // ############################################################
    
    } // UserInputGraphic()




    
} // USERINPUT_TimeInputManager()