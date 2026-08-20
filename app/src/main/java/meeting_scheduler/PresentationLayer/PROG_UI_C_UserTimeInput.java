/**
 * PROG_UI_C_UserTimeInput.java
 * 
 * Description: Manager for user time inputs for both the data card scene and the scheduler scene
 * 
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
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import meeting_scheduler.DataAccessLayer.PROG_DAL_A_TimeInput;
// ############################################################
import meeting_scheduler.DataAccessLayer.PROG_DAL_D_SystemMessages;



public class PROG_UI_C_UserTimeInput {
    

    private ComboBox<String> Select_AMPM_StartTime;
    private ComboBox<String> Select_AMPM_EndTime;
    private ComboBox<String> Select_WeekDay;

    private TextField Select_Hour_Begin;
    private TextField Select_Minute_Begin;
    private TextField Select_Hour_End;
    private TextField Select_Minute_End;

    private PROG_DAL_A_TimeInput FullUserPreference;
    private PROG_DAL_A_TimeInput PartialUserPreference;

    private Iterator<VBox>      VBOXIterator;



    /**
     * Constructor
     */
    public PROG_UI_C_UserTimeInput() {

        this.Select_AMPM_StartTime  = new ComboBox<>();
        this.Select_AMPM_EndTime    = new ComboBox<>();
        this.Select_WeekDay         = new ComboBox<>();

        this.Select_Hour_Begin      = new TextField();
        this.Select_Minute_Begin    = new TextField();
        this.Select_Hour_End        = new TextField();
        this.Select_Minute_End      = new TextField();

    }

    // Returns the start time AMPM selection ComboBox
    public ComboBox<String> Return_AMPM_StartTime() {
        return this.Select_AMPM_StartTime;
    }

    // Returns the end time AMPM selection ComboBox
    public ComboBox<String> Return_AMPM_EndTime() {
        return this.Select_AMPM_EndTime;
    }

    // Returns the WeekDay selection ComboBox
    public ComboBox<String> Return_WeekDay() {
        return this.Select_WeekDay;
    }

    // Returns start hour input TextField
    public TextField Return_Hour_Begin() {
        return this.Select_Hour_Begin;
    }

    // Returns start minute input TextField
    public TextField Return_Minute_Begin() {
        return this.Select_Minute_Begin;
    }

    // Returns end hour input TextField
    public TextField Return_Hour_End() {
        return this.Select_Hour_End;
    }

    // Returns end minute input TextField
    public TextField Return_Minute_End() {
        return this.Select_Minute_End;
    }

    // Returns user preferences meant for input into Json File
    public PROG_DAL_A_TimeInput Return_FileReadyUserPreference() {
        return this.FullUserPreference;
    }

    // Returns user preferences meant for Schedule Calculation
    public PROG_DAL_A_TimeInput Return_TimeUserPreference() {
        return this.PartialUserPreference;
    }


    /**
     * UI_data_construction()
     * Description: adds the necessary data and functions to the various user inputs
     */
    public void UI_data_construction() {


        // ComboBoxes
        // ############################################################
        // ComboBox for beginning AM/PM
        this.Select_AMPM_StartTime.getItems().addAll(PROG_UI_D_DataVariables.AMPM);
        this.Select_AMPM_StartTime.getSelectionModel().select(PROG_UI_D_DataVariables.AM);
        this.Select_AMPM_StartTime.valueProperty().addListener((observed, oldvalue, newvalue) -> {
            if (newvalue.equals(PROG_UI_D_DataVariables.PM)) {
                this.Select_AMPM_EndTime.getSelectionModel().select(PROG_UI_D_DataVariables.PM);
            }
        });

        // ComboBox for ending AM/PM
        this.Select_AMPM_EndTime.getItems().addAll(PROG_UI_D_DataVariables.AMPM);
        this.Select_AMPM_EndTime.getSelectionModel().select(PROG_UI_D_DataVariables.AM);
        this.Select_AMPM_EndTime.valueProperty().addListener((observed, oldvalue, newvalue) -> {
            if (newvalue.equals(PROG_UI_D_DataVariables.AM)) {
                this.Select_AMPM_StartTime.getSelectionModel().select(PROG_UI_D_DataVariables.AM);
            }
        });

        // ComboBox for Weekday selection
        this.Select_WeekDay = new ComboBox<>();
        this.Select_WeekDay.getItems().addAll(PROG_UI_D_DataVariables.WEEKDAYS);
        this.Select_WeekDay.setPrefSize(100, 25.0);
        // ############################################################



        // TextFields
        // ############################################################
        // Beginning Hour Input
        this.Select_Hour_Begin.setPrefSize(50, 25.0);
        this.Select_Hour_Begin.setTextFormatter(new TextFormatter<>(change -> {

            // User input text
            String TextInput = change.getControlNewText();

            // if th etext is empty accept it
            if (TextInput.isEmpty()) {
                return change;
            }

            // test if the text is a valid int within a valid range
            try {
                int intValue = Integer.parseInt(TextInput);
                if (intValue >= 0 && intValue <= 12) {
                    return change;
                }
            } catch (NumberFormatException e) {
                // Invalid Input
            }
            return null;
        }));

        // Beginning Minute Input
        this.Select_Minute_Begin.setPrefSize(50, 25.0);
        this.Select_Minute_Begin.setTextFormatter(new TextFormatter<>(change -> {
            
            // User input text
            String TextInput = change.getControlNewText();

            // if th etext is empty accept it
            if (TextInput.isEmpty()) {
                return change;
            }

            // test if the text is a valid int within a valid range
            try {
                int intValue = Integer.parseInt(TextInput);

                if (intValue >= 0 && intValue < 60) {
                    return change;
                }
            } catch (NumberFormatException e) {
                // Invalid Input
            }
            return null;
        }));

        // Ending Hour Input
        this.Select_Hour_End.setPrefSize(50, 25.0);
        this.Select_Hour_End.setTextFormatter(new TextFormatter<>(change -> {
            
            // User input text
            String TextInput = change.getControlNewText();

            // if the text is empty accept it
            if (TextInput.isEmpty()) {
                return change;
            }

            // ensures end hour is never before the beginning hour
            if (!this.Select_Hour_Begin.getText().isBlank()) {                                                  // if the input is not blank

                if (    (Integer.parseInt(this.Select_Hour_Begin.getText()) > Integer.parseInt(TextInput))      // if -> beginning hour > ending hour
                    &&  (this.Select_AMPM_StartTime.getValue().equals(this.Select_AMPM_EndTime.getValue()))     // and -> both start and end itervals are AM or PM
                ) {
                    this.Select_Hour_End.setText(this.Select_Hour_Begin.getText());                             // Ending hour changes to equal starting hour
                }

            }

            // regain input
            TextInput = change.getControlNewText();

            // test if the text is a valid int within a valid range
            try {
                int intValue = Integer.parseInt(TextInput);

                if (intValue >= 0 && intValue <= 12) {
                    return change;
                }
            } catch (NumberFormatException e) {
                // Invalid Input
            }

            return null;
        }));



        // Ending Minute Input
        this.Select_Minute_End.setPrefSize(50, 25.0);
        this.Select_Minute_End.setTextFormatter(new TextFormatter<>(change -> {
            
            // User input text
            String TextInput = change.getControlNewText();

            // if th etext is empty accept it
            if (TextInput.isEmpty()) {
                return change;
            }

            // if both the beginning and ending hour are the same and at the same period (AM/PM) ensure the ending minute is always later
            if ((!this.Select_Hour_Begin.getText().isBlank()) && (!this.Select_Hour_End.getText().isBlank())) {                     // if both start/end hours are input

                if ( (Integer.parseInt(this.Select_Hour_Begin.getText()) == Integer.parseInt(this.Select_Hour_End.getText()))       // if -> beginning hour equals ending hour
                    &&  ( Integer.parseInt(TextInput) < Integer.parseInt(this.Select_Minute_Begin.getText()) )                      // and -> ending min < starting min
                    &&  ( this.Select_AMPM_StartTime.getValue().equals(this.Select_AMPM_EndTime.getValue()) )                       // and -> both times are AM or PM
                ) {
                    this.Select_Minute_End.setText(this.Select_Minute_Begin.getText());                                             // set ending min same as start min
                }

            } // if ()

            // test if the text is a valid int within a valid range
            try {
                int intValue = Integer.parseInt(TextInput);
                if (intValue >= 0 && intValue < 60) {
                    return change;
                }
            } catch (NumberFormatException e) {
                // Invalid Input
            }
            return null;
        }));
        // ############################################################

    } // UI_data_construction()


    /**
     * ButtonPressTimeInput()
     * Description: checks to ensure all variables have been input then creates a user preference to return
     */
    public int ButtonPressFullTimeInput() {

        if (Select_WeekDay.getValue() == null)  {               // Error Return
            // user has not submitted a weekday
            System.out.println(PROG_DAL_D_SystemMessages.INFO_PreferenceInput_weekday);
            return 1;

        } else if (Select_Hour_Begin.getText().isBlank())   {   // Error Return
            // user has not submitted a beginning hour
            System.out.println(PROG_DAL_D_SystemMessages.INFO_PreferenceInput_StartHour);
            return 1;

        } else if (Select_Minute_Begin.getText().isBlank()) {   // Error Return
            // user has not submitted a beginning minute
            System.out.println(PROG_DAL_D_SystemMessages.INFO_PreferenceInput_StartMin);
            return 1;

        } else if (Select_Hour_End.getText().isBlank())     {   // Error Return
            // user has not submitted a ending hour
            System.out.println(PROG_DAL_D_SystemMessages.INFO_PreferenceInput_EndHour);
            return 1;

        } else if (Select_Minute_End.getText().isBlank())   {   // Error Return
            // user has not submitted a ending minute
            System.out.println(PROG_DAL_D_SystemMessages.INFO_PreferenceInput_EndMin);
            return 1;

        } else {                                                // No Error return

            // Correct info has been submitted
            System.out.println(PROG_DAL_D_SystemMessages.PASS_PreferenceInput_CorrectInput);

            String  WeekDay     = Select_WeekDay.getValue();
            int     BeginHour   = Integer.parseInt(Select_Hour_Begin    .getText());
            int     BeginMinute = Integer.parseInt(Select_Minute_Begin  .getText());
            int     EndHour     = Integer.parseInt(Select_Hour_End      .getText());
            int     EndMinute   = Integer.parseInt(Select_Minute_End    .getText());

            // new user preference
            this.FullUserPreference = new PROG_DAL_A_TimeInput(WeekDay, BeginHour, BeginMinute, EndHour, EndMinute);
            
            return 0;

        } // else ()

    } // ButtonPressTimeInput()



    /**
     * UserInputGraphicCalculation()
     * Descriiption: manages the visual output of the user submitted data for card info input
     */
    public void UserInputGraphic(PROG_DAL_A_TimeInput Input_UserTime, LinkedList<PROG_DAL_A_TimeInput> List_UserTimes, LinkedList<VBox> List_VBoxTimeInputs, FlowPane FlowPane_VBoxDisplay, boolean FullInput) {


        // PROG_DAL_A_TimeInput Input_UserTime              - Input time being processed and formatted correctly

        // LinkedList<PROG_DAL_A_TimeInput> List_UserTimes  - holds the preferred times of each user

        // LinkedList<VBox> List_VBoxTimeInputs             - holds a list of vboxes containing user time preferences, used only to iterate over the vboxes safely

        // FlowPane FlowPane_VBoxDisplay                    - The flowpane that holds the vboxes that will actually be displayed, should contain the same vboxes that VBOXUserPreferences has
        
        // boolean FullInput                                - boolean for if the weekday needs to be input or not, true for datacard info, false for scheduling



        // VBox creation and preference set
        // ############################################################
        VBox IndividualDataCard = new VBox();
        IndividualDataCard.setPrefSize(100.0, 70.0);
        // ############################################################


        // VBox data variable creation
        // ############################################################
        // index position
        int Index = (List_UserTimes.size() + 1);

        // Starting Time
        String  StartTimeFrame  = this.Select_AMPM_StartTime.getValue();
        int     StartHour       = Input_UserTime.PreferedHourBEGIN.getHour();
        String  startMin        = Integer.toString(Input_UserTime.PreferedHourBEGIN.getMinute());

        // Ending Time
        String  EndTimeFrame    = this.Select_AMPM_EndTime.getValue();
        int     EndHour         = Input_UserTime.PreferedHourEND.getHour();
        String  EndMin          = Integer.toString(Input_UserTime.PreferedHourEND.getMinute());

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
        Label   WeekDay     = new Label("WeekDay: " + Input_UserTime.WeekDay);
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
            VBOXIterator = List_VBoxTimeInputs.iterator();


            // loop through list to remove empty Vbox node
            int LinkedListIndex = 0;
            while (VBOXIterator.hasNext()) {

                // next Vbox in iterator
                VBox tempBox = VBOXIterator.next();

                // if the Vbox is empty remove it from the list and remove the relevant time preference from UserTimeInput
                if (tempBox.getChildren().isEmpty()) {

                    // removes empty Vbox from the linked list List_VBoxTimeInputs
                    VBOXIterator.remove();

                    // removes InputTime from UserTimeInput LinkedList
                    List_UserTimes.remove(LinkedListIndex);

                } // if()

                LinkedListIndex++;

            } // for()
            

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

            } // for()

            System.out.println("UserTimeInput ammount: " + List_UserTimes.size());

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

        if (FullInput == false) {
            NewWeekday      = Input_UserTime.WeekDay;
        } else {
            NewWeekday      = "N/A";
        }

        // Start Hour
        // if Pm is selected and the time isn't 12, incremented by + 12, else just use the normal time
        if ( (StartTimeFrame.equals("PM")) && (Input_UserTime.PreferedHourBEGIN.getHour() < 12) ) {
            NewStartHour    = Input_UserTime.PreferedHourBEGIN.getHour() + 12;
        } else {
            NewStartHour    = Input_UserTime.PreferedHourBEGIN.getHour();
        }

        // Start Minute
        NewStartMin         = Input_UserTime.PreferedHourBEGIN.getMinute();

        // End Hour
        // if Pm is selected, incremented by + 12, else just use the normal time
        if ( (EndTimeFrame.equals("PM")) && (Input_UserTime.PreferedHourEND.getHour() < 12) ){
            NewEndHour      = Input_UserTime.PreferedHourEND.getHour() + 12;
        } else {
            NewEndHour      = Input_UserTime.PreferedHourEND.getHour();
        }

        // Ends Minute
        NewEndMin           = Input_UserTime.PreferedHourEND.getMinute();
        // ############################################################



        // List_UserTimes - list of all timeinputs
        // ############################################################
        List_UserTimes.add(new PROG_DAL_A_TimeInput(NewWeekday, NewStartHour, NewStartMin, NewEndHour, NewEndMin));
        System.out.println("UserTimeInput ammount" + List_UserTimes.size());
        // ############################################################



        // add to VBox to linkedlist of all Vboxes
        // ############################################################
        List_VBoxTimeInputs.add(IndividualDataCard);
        // ############################################################



        // add vbox to flowpane
        // ############################################################
        FlowPane_VBoxDisplay.getChildren().add(IndividualDataCard);
        // ############################################################
    
    } // UserInputGraphic()



    /**
     * ButtonPressTimeInput()
     * Description: for the scheduler, checks to esnure valid tiems are given.
     */
    public int ButtonPressPartialTimeInput() {

        if (Select_Hour_Begin.getText().isBlank())   { // Do nothing
            // user has not submitted a beginning hour
            System.out.println("user has not submitted a beginning hour");
            return 1;

        } else if (Select_Minute_Begin.getText().isBlank())   { // Do nothing
            // user has not submitted a beginning minute
            System.out.println("user has not submitted a beginning minute");
            return 1;

        } else if (Select_Hour_End.getText().isBlank())   { // Do nothing
            // user has not submitted a ending hour
            System.out.println("user has not submitted a ending hour");
            return 1;

        } else if (Select_Minute_End.getText().isBlank())   { // Do nothing
            // user has not submitted a ending minute
            System.out.println("user has not submitted a ending minute");
            return 1;

        } else {

            // Correct info has been submitted
            System.out.println("Correct info has been submitted");

            int     BeginHour   = Integer.parseInt(Select_Hour_Begin.getText());
            int     BeginMinute = Integer.parseInt(Select_Minute_Begin.getText());
            int     EndHour     = Integer.parseInt(Select_Hour_End.getText());
            int     EndMinute   = Integer.parseInt(Select_Minute_End.getText());

            // new user preference
            this.PartialUserPreference = new PROG_DAL_A_TimeInput("N/A", BeginHour, BeginMinute, EndHour, EndMinute);
            
            return 0;

        } // else ()

    } // ButtonPressTimeInput()



    
}
