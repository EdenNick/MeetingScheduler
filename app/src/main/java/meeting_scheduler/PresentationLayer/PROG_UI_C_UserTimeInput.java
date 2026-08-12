package meeting_scheduler.PresentationLayer;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import meeting_scheduler.DataAccessLayer.PROG_DAL_A_TimeInput;


public class PROG_UI_C_UserTimeInput {
    

    private ComboBox<String> Select_AMPM_StartTime;
    private ComboBox<String> Select_AMPM_EndTime;
    private ComboBox<String> Select_WeekDay;

    private TextField Select_Hour_Begin;
    private TextField Select_Minute_Begin;
    private TextField Select_Hour_End;
    private TextField Select_Minute_End;

    private PROG_DAL_A_TimeInput UserPreference;



    public PROG_UI_C_UserTimeInput() {

        this.Select_AMPM_StartTime  = new ComboBox<>();
        this.Select_AMPM_EndTime    = new ComboBox<>();
        this.Select_WeekDay         = new ComboBox<>();

        this.Select_Hour_Begin      = new TextField();
        this.Select_Minute_Begin    = new TextField();
        this.Select_Hour_End        = new TextField();
        this.Select_Minute_End      = new TextField();

    }


    public ComboBox<String> Return_AMPM_StartTime() {
        return this.Select_AMPM_StartTime;
    }

    public ComboBox<String> Return_AMPM_EndTime() {
        return this.Select_AMPM_EndTime;
    }

    public ComboBox<String> Return_WeekDay() {
        return this.Select_WeekDay;
    }

    public TextField Return_Hour_Begin() {
        return this.Select_Hour_Begin;
    }

    public TextField Return_Minute_Begin() {
        return this.Select_Minute_Begin;
    }

    public TextField Return_Hour_End() {
        return this.Select_Hour_End;
    }

    public TextField Return_Minute_End() {
        return this.Select_Minute_End;
    }

    public PROG_DAL_A_TimeInput Return_UserPreference() {
        return this.UserPreference;
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
    public void ButtonPressTimeInput() {

        if (Select_WeekDay.getValue() == null)  { // Do nothing
                // user has not submitted a weekday
                System.out.println("user has not submitted a weekday");

            } else if (Select_Hour_Begin.getText().isBlank())   { // Do nothing
                // user has not submitted a beginning hour
                System.out.println("user has not submitted a beginning hour");

            } else if (Select_Minute_Begin.getText().isBlank())   { // Do nothing
                // user has not submitted a beginning minute
                System.out.println("user has not submitted a beginning minute");

            } else if (Select_Hour_End.getText().isBlank())   { // Do nothing
                // user has not submitted a ending hour
                System.out.println("user has not submitted a ending hour");

            } else if (Select_Minute_End.getText().isBlank())   { // Do nothing
                // user has not submitted a ending minute
                System.out.println("user has not submitted a ending minute");
            } else {

                // Correct info has been submitted
                System.out.println("Correct info has been submitted");

                String  WeekDay     = Select_WeekDay.getValue();
                int     BeginHour   = Integer.parseInt(Select_Hour_Begin.getText());
                int     BeginMinute = Integer.parseInt(Select_Minute_Begin.getText());
                int     EndHour     = Integer.parseInt(Select_Hour_End.getText());
                int     EndMinute   = Integer.parseInt(Select_Minute_End.getText());

                // new user preference
                this.UserPreference = new PROG_DAL_A_TimeInput(WeekDay, BeginHour, BeginMinute, EndHour, EndMinute);

            } // else ()
    }
}
