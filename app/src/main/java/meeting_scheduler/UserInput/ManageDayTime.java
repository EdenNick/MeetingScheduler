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
// DataAccessLayer
import meeting_scheduler.DataAccessLayer.PROG_DAL_D_SystemMessages;
// EmployeePreferences
import meeting_scheduler.EmployeePreferences.PREF_EMPLOYEE_TimePref;
// SceneManagement
import meeting_scheduler.SystemInfoManager;
// global
import meeting_scheduler.global;
// ############################################################



public class ManageDayTime {
    

    private final InputComboBox OBJ_TEXTLIST_WEEKDAY;
    private final InputDayTime  OBJ_DAYTIMEINPUT_TIMES;


    private final ComboBox<String> MANAGER_WEEKDAY;
    private final ComboBox<String> MANAGER_AMPM_START;
    private final ComboBox<String> MANAGER_AMPM_END;

    private final TextField MANAGER_STARTTIME_HOUR;
    private final TextField MANAGER_STARTTIME_MIN;
    private final TextField MANAGER_ENDTIME_HOUR;
    private final TextField MANAGER_ENDTIME_MIN;

    private PREF_EMPLOYEE_TimePref fullUserPreference;
    // private PREF_EMPLOYEE_TimePref PartialUserPreference;

    private Iterator<Node>      IteratorFlowPaneDisplay;

    private HBox UIAddStartTime;
    private HBox UIAddEndtime;

    private VBox UITimeInterface;

    private Label labelDay;
    private Label labelBeginningTime;
    private Label labelBeginHour;
    private Label labelBeginMinute;
    private Label labelEndingTime;
    private Label labelEndHour;
    private Label labelEndMinute;



    
    
    /**
     * Constructor
     */
    public ManageDayTime(Button UI_INPUT_AddPreferenceButton) {

        // Objects
        // ############################################################
        this.OBJ_TEXTLIST_WEEKDAY   = new InputComboBox(global.TextListState.WEEK, null, 0, 0);
        this.OBJ_DAYTIMEINPUT_TIMES = new InputDayTime();
        // ############################################################

        // UI
        // ############################################################
        this.MANAGER_WEEKDAY        = OBJ_TEXTLIST_WEEKDAY.Return_Field_Constructed();

        this.MANAGER_AMPM_START     = OBJ_DAYTIMEINPUT_TIMES.Return_ComboBox_StartTime();
        this.MANAGER_AMPM_END       = OBJ_DAYTIMEINPUT_TIMES.Return_ComboBox_EndTime();

        this.MANAGER_STARTTIME_HOUR = OBJ_DAYTIMEINPUT_TIMES.Return_TextField_Hour_StartTime();
        this.MANAGER_STARTTIME_MIN  = OBJ_DAYTIMEINPUT_TIMES.Return_TextField_Min_StartTime();
        this.MANAGER_ENDTIME_HOUR   = OBJ_DAYTIMEINPUT_TIMES.Return_TextField_Hour_EndTime();
        this.MANAGER_ENDTIME_MIN    = OBJ_DAYTIMEINPUT_TIMES.Return_TextField_Min_EndTime();
        // ############################################################


        // CREATE labels
        CONSTRUCT_UI_Labels();

        // Create UI
        CONSTRUCT_UI_TimeInput(UI_INPUT_AddPreferenceButton);


        // sets size and shape
        SET_SizeShape();
    }


    // TEMP
    public ManageDayTime() {

        this.OBJ_TEXTLIST_WEEKDAY   = new InputComboBox(global.TextListState.WEEK, null, 0, 0);

        this.OBJ_DAYTIMEINPUT_TIMES = new InputDayTime();

        this.MANAGER_WEEKDAY        = OBJ_TEXTLIST_WEEKDAY.Return_Field_Constructed();

        this.MANAGER_AMPM_START     = OBJ_DAYTIMEINPUT_TIMES.Return_ComboBox_StartTime();
        this.MANAGER_AMPM_END       = OBJ_DAYTIMEINPUT_TIMES.Return_ComboBox_EndTime();

        this.MANAGER_STARTTIME_HOUR = OBJ_DAYTIMEINPUT_TIMES.Return_TextField_Hour_StartTime();
        this.MANAGER_STARTTIME_MIN  = OBJ_DAYTIMEINPUT_TIMES.Return_TextField_Min_StartTime();
        this.MANAGER_ENDTIME_HOUR   = OBJ_DAYTIMEINPUT_TIMES.Return_TextField_Hour_EndTime();
        this.MANAGER_ENDTIME_MIN    = OBJ_DAYTIMEINPUT_TIMES.Return_TextField_Min_EndTime();


        // CREATE labels
        CONSTRUCT_UI_Labels();

        // Create UI
        //CONSTRUCT_UI_TimeInput(UI_INPUT_AddPreferenceButton);

        // sets size and shape
        SET_SizeShape();
    }


    // Returns user preferences meant for input into Json File
    public PREF_EMPLOYEE_TimePref Return_UserPreference() {
        return this.fullUserPreference;
    }

    public VBox Return_UI_TimeInput() {
        return this.UITimeInterface;
    }



    private void SET_SizeShape() {

        double Width = 0.0;
        double Height = 0.0;


        // this.MANAGER_WEEKDAY
        Width = SystemInfoManager.GET_SYSTEM_UI_SPACING(global.UISPACING.WIDTH, 1);      // 1 - 100
        Height = SystemInfoManager.GET_SYSTEM_UI_SPACING(global.UISPACING.HEIGHT, 1);    // 1 - 25
        this.MANAGER_WEEKDAY.setPrefSize(Width,Height);

        // this.MANAGER_AMPM_START
        Width = SystemInfoManager.GET_SYSTEM_UI_SPACING(global.UISPACING.WIDTH, 1);      // 1 - 100
        Height = SystemInfoManager.GET_SYSTEM_UI_SPACING(global.UISPACING.HEIGHT, 1);    // 1 - 25
        this.MANAGER_AMPM_START.setPrefSize(Width,Height);

        // this.MANAGER_AMPM_END
        Width = SystemInfoManager.GET_SYSTEM_UI_SPACING(global.UISPACING.WIDTH, 1);      // 1 - 100
        Height = SystemInfoManager.GET_SYSTEM_UI_SPACING(global.UISPACING.HEIGHT, 1);    // 1 - 25
        this.MANAGER_AMPM_END.setPrefSize(Width,Height);

        // this.MANAGER_STARTTIME_HOUR
        Width = SystemInfoManager.GET_SYSTEM_UI_SPACING(global.UISPACING.WIDTH, 0);      // 0 - 50
        Height = SystemInfoManager.GET_SYSTEM_UI_SPACING(global.UISPACING.HEIGHT, 1);    // 1 - 25
        this.MANAGER_STARTTIME_HOUR.setPrefSize(Width,Height);

        // this.MANAGER_STARTTIME_MIN
        Width = SystemInfoManager.GET_SYSTEM_UI_SPACING(global.UISPACING.WIDTH, 0);      // 0 - 50
        Height = SystemInfoManager.GET_SYSTEM_UI_SPACING(global.UISPACING.HEIGHT, 1);    // 1 - 25
        this.MANAGER_STARTTIME_MIN.setPrefSize(Width,Height);

        // this.MANAGER_ENDTIME_HOUR
        Width = SystemInfoManager.GET_SYSTEM_UI_SPACING(global.UISPACING.WIDTH, 0);      // 0 - 50
        Height = SystemInfoManager.GET_SYSTEM_UI_SPACING(global.UISPACING.HEIGHT, 1);    // 1 - 25
        this.MANAGER_ENDTIME_HOUR.setPrefSize(Width,Height);

        // this.MANAGER_ENDTIME_MIN
        Width = SystemInfoManager.GET_SYSTEM_UI_SPACING(global.UISPACING.WIDTH, 0);      // 0 - 50
        Height = SystemInfoManager.GET_SYSTEM_UI_SPACING(global.UISPACING.HEIGHT, 1);    // 1 - 25
        this.MANAGER_ENDTIME_MIN.setPrefSize(Width,Height);
    }


    private void CONSTRUCT_UI_Labels() {
        // Label - Weekday Prompt
        this.labelDay           = new Label(SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_STRING(global.BASICVALUESSTRING.STRING, 5));
        this.labelDay.getStyleClass().add(SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_STRING(global.BASICVALUESSTRING.STRING, 16));


        // Label - Beginning time Prompt
        this.labelBeginningTime = new Label(SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_STRING(global.BASICVALUESSTRING.STRING, 6));
        this.labelBeginningTime.getStyleClass().add(SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_STRING(global.BASICVALUESSTRING.STRING, 16));


        // Label - Beginning Hour Label
        this.labelBeginHour     = new Label(SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_STRING(global.BASICVALUESSTRING.STRING, 8));
        this.labelBeginHour.getStyleClass().add(SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_STRING(global.BASICVALUESSTRING.STRING, 16));


        // Label - Beginning Minute Label
        this.labelBeginMinute   = new Label(SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_STRING(global.BASICVALUESSTRING.STRING, 9));
        this.labelBeginMinute.getStyleClass().add(SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_STRING(global.BASICVALUESSTRING.STRING, 16));


        // Label - Ending time Prompt
        this.labelEndingTime    = new Label(SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_STRING(global.BASICVALUESSTRING.STRING, 7));
        this.labelEndingTime.getStyleClass().add(SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_STRING(global.BASICVALUESSTRING.STRING, 16));


        // Label - Ending hour Label
        this.labelEndHour       = new Label(SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_STRING(global.BASICVALUESSTRING.STRING, 8));
        this.labelEndHour.getStyleClass().add(SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_STRING(global.BASICVALUESSTRING.STRING, 16));


        // Label - Ending Minute Label
        this.labelEndMinute     = new Label(SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_STRING(global.BASICVALUESSTRING.STRING, 9));
        this.labelEndMinute.getStyleClass().add(SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_STRING(global.BASICVALUESSTRING.STRING, 16));
    } // CONSTRUCT_UI_Labels()




    private void CONSTRUCT_UI_TimeInput(Button UI_INPUT_AddPreferenceButton) {

        // HBox for beginning Hour/Min
        // ############################################################
        this.UIAddStartTime = new HBox(10);
        //AddStartTime.setPrefSize(300.0, 500.0);
        this.UIAddStartTime.setPadding(new Insets(10));
        this.UIAddStartTime.getStyleClass().add(SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_STRING(global.BASICVALUESSTRING.STRING, 19));

        this.UIAddStartTime.getChildren().addAll(

            labelBeginHour,
            this.MANAGER_STARTTIME_HOUR,

            labelBeginMinute,
            this.MANAGER_STARTTIME_MIN,

            this.MANAGER_AMPM_START

        );
        // ############################################################



        // HBox for Ending Hour/Min
        // ############################################################
        this.UIAddEndtime = new HBox(10);
        //AddStartTime.setPrefSize(200.0, 400.0);
        this.UIAddEndtime.setPadding(new Insets(10));
        this.UIAddEndtime.getStyleClass().add(SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_STRING(global.BASICVALUESSTRING.STRING, 19));
        this.UIAddEndtime.getChildren().addAll(

            labelEndHour,
            this.MANAGER_ENDTIME_HOUR,

            labelEndMinute,
            this.MANAGER_ENDTIME_MIN,

            MANAGER_AMPM_END

        );
        // ############################################################



        // Vbox for adding time intervals
        // ############################################################
        this.UITimeInterface = new VBox(10);
        //this.addTimeInfo_input.setPrefSize(200.0, 400.0);
        this.UITimeInterface.setPadding(new Insets(10));
        this.UITimeInterface.getStyleClass().add(SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_STRING(global.BASICVALUESSTRING.STRING, 19));

        Region ButtonSpace = new Region();
        VBox.setVgrow(ButtonSpace, Priority.ALWAYS);

        this.UITimeInterface.getChildren().addAll(

            labelDay,
            this.MANAGER_WEEKDAY,
            
            labelBeginningTime,
            UIAddStartTime,

            labelEndingTime,
            UIAddEndtime,

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

        if (this.MANAGER_WEEKDAY.getValue() == null)  {               // Error Return
            // user has not submitted a weekday
            System.out.println(PROG_DAL_D_SystemMessages.INFO_PreferenceInput_weekday);
            return 1;

        } else if (this.MANAGER_STARTTIME_HOUR.getText().isBlank())   {   // Error Return
            // user has not submitted a beginning hour
            System.out.println(PROG_DAL_D_SystemMessages.INFO_PreferenceInput_StartHour);
            return 1;

        } else if (this.MANAGER_STARTTIME_MIN.getText().isBlank()) {   // Error Return
            // user has not submitted a beginning minute
            System.out.println(PROG_DAL_D_SystemMessages.INFO_PreferenceInput_StartMin);
            return 1;

        } else if (this.MANAGER_ENDTIME_HOUR.getText().isBlank())     {   // Error Return
            // user has not submitted a ending hour
            System.out.println(PROG_DAL_D_SystemMessages.INFO_PreferenceInput_EndHour);
            return 1;

        } else if (this.MANAGER_ENDTIME_MIN.getText().isBlank())   {   // Error Return
            // user has not submitted a ending minute
            System.out.println(PROG_DAL_D_SystemMessages.INFO_PreferenceInput_EndMin);
            return 1;

        } else {                                                // No Error return

            // Correct info has been submitted
            System.out.println(PROG_DAL_D_SystemMessages.PASS_PreferenceInput_CorrectInput);

            String  WeekDay     = this.MANAGER_WEEKDAY.getValue();
            int     BeginHour   = Integer.parseInt(this.MANAGER_STARTTIME_HOUR  .getText());
            int     BeginMinute = Integer.parseInt(this.MANAGER_STARTTIME_MIN   .getText());
            int     EndHour     = Integer.parseInt(this.MANAGER_ENDTIME_HOUR    .getText());
            int     EndMinute   = Integer.parseInt(this.MANAGER_ENDTIME_MIN     .getText());

            // new user preference
            this.fullUserPreference = new PREF_EMPLOYEE_TimePref(WeekDay, BeginHour, BeginMinute, EndHour, EndMinute);
            
            return 0;

        } // else ()

    } // ButtonPressTimeInput()



    /**
     * ButtonPressTimeInput()
     * Description: checks to ensure all variables beside the weekday have been input then creates a user preference to return
     */
    public int CHECK_PartialTimeInput() {

        if (this.MANAGER_STARTTIME_HOUR.getText().isBlank())   {   // Error Return
            // user has not submitted a beginning hour
            System.out.println(PROG_DAL_D_SystemMessages.INFO_PreferenceInput_StartHour);
            return 1;

        } else if (this.MANAGER_STARTTIME_MIN.getText().isBlank()) {   // Error Return
            // user has not submitted a beginning minute
            System.out.println(PROG_DAL_D_SystemMessages.INFO_PreferenceInput_StartMin);
            return 1;

        } else if (this.MANAGER_ENDTIME_HOUR.getText().isBlank())     {   // Error Return
            // user has not submitted a ending hour
            System.out.println(PROG_DAL_D_SystemMessages.INFO_PreferenceInput_EndHour);
            return 1;

        } else if (this.MANAGER_ENDTIME_MIN.getText().isBlank())   {   // Error Return
            // user has not submitted a ending minute
            System.out.println(PROG_DAL_D_SystemMessages.INFO_PreferenceInput_EndMin);
            return 1;

        } else {                                                // No Error return

            // Correct info has been submitted
            System.out.println(PROG_DAL_D_SystemMessages.PASS_PreferenceInput_CorrectInput);

            int     BeginHour   = Integer.parseInt(this.MANAGER_STARTTIME_HOUR  .getText());
            int     BeginMinute = Integer.parseInt(this.MANAGER_STARTTIME_MIN   .getText());
            int     EndHour     = Integer.parseInt(this.MANAGER_ENDTIME_HOUR    .getText());
            int     EndMinute   = Integer.parseInt(this.MANAGER_ENDTIME_MIN     .getText());

            // new user preference
            this.fullUserPreference = new PREF_EMPLOYEE_TimePref(null, BeginHour, BeginMinute, EndHour, EndMinute);
            
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
        double Width = SystemInfoManager.GET_SYSTEM_UI_SPACING(global.UISPACING.WIDTH, 2);      // 2 - 150
        double Height = SystemInfoManager.GET_SYSTEM_UI_SPACING(global.UISPACING.HEIGHT, 7);    // 7 - 100
        IndividualDataCard.setPrefSize(Width, Height);
        // IndividualDataCard.setPrefSize(130.0, 100.0);
        IndividualDataCard.getStyleClass().add(SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_STRING(global.BASICVALUESSTRING.STRING, 22));
        IndividualDataCard.setAlignment(Pos.CENTER);
        // ############################################################


        // VBox data variable creation
        // ############################################################
        // index position
        int Index = (List_UserTimes.size() + 1);

        // Starting Time
        String  StartTimeFrame  = this.MANAGER_AMPM_START.getValue();
        int     StartHour       = Input_UserTime.GetStartTimeHour();
        String  startMin        = Integer.toString(Input_UserTime.GetStartTimeMin());

        // Ending Time
        String  EndTimeFrame    = this.MANAGER_AMPM_END.getValue();
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
            IteratorFlowPaneDisplay = FlowPane_VBoxDisplay.getChildren().iterator();

            // loop through list to remove empty Vbox node
            int LinkedListIndex = 0;
            while (IteratorFlowPaneDisplay.hasNext()) {

                // next Vbox in iterator
                Node Node_FlowPane = IteratorFlowPaneDisplay.next();

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

            } // while (IteratorFlowPaneDisplay.hasNext())
            

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
        // ############################################################


        // add vbox to flowpane
        // ############################################################
        FlowPane_VBoxDisplay.getChildren().add(IndividualDataCard);
        // ############################################################
    
    } // UserInputGraphic()




    
} // USERINPUT_TimeInputManager()