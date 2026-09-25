package meeting_scheduler.UserInput;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import meeting_scheduler.global;
import meeting_scheduler.SceneManagement.SCENE_VARIABLES_Local;

public class USERINPUT_DayTimeInput {

    private final ComboBox<String> AMPM_START;
    private final ComboBox<String> AMPM_END;

    private final TextField INPUTTIME_HOUR_Start;
    private final TextField INPUTTIME_MIN_Start;
    private final TextField INPUTTIME_HOUR_End;
    private final TextField INPUTTIME_MIN_End;



    public USERINPUT_DayTimeInput() {
        
        this.INPUTTIME_HOUR_Start   = new TextField();
        this.INPUTTIME_MIN_Start    = new TextField();
        this.INPUTTIME_HOUR_End     = new TextField();
        this.INPUTTIME_MIN_End      = new TextField();
        this.AMPM_START             = new ComboBox<>();
        this.AMPM_END               = new ComboBox<>();


        Construct_comboBox_Default(AMPM_START);
        Construct_comboBox_Default(AMPM_END);

        this.AMPM_START.valueProperty().addListener((observed, oldvalue, newvalue) -> {
            if (newvalue.equals(SCENE_VARIABLES_Local.PM)) {
                this.AMPM_END.getSelectionModel().select(SCENE_VARIABLES_Local.PM);
            }
        });

        this.AMPM_END.valueProperty().addListener((observed, oldvalue, newvalue) -> {
            if (newvalue.equals(SCENE_VARIABLES_Local.AM)) {
                this.AMPM_START.getSelectionModel().select(SCENE_VARIABLES_Local.AM);
            }
        });

        Construct_TextField_Default_Hour(this.INPUTTIME_HOUR_Start);
        Construct_TextField_Default_Hour_END(this.INPUTTIME_HOUR_End);

        Construct_TextField_Default_Min(this.INPUTTIME_MIN_Start);
        Construct_TextField_Default_Min_END(this.INPUTTIME_MIN_End);

    } // USERINPUT_DayTimeInput()


    public ComboBox<String> Return_ComboBox_StartTime() {
        return this.AMPM_START;
    }

    public ComboBox<String> Return_ComboBox_EndTime()   {
        return this.AMPM_END;
    }

    public TextField Return_TextField_Hour_StartTime()  {
        return this.INPUTTIME_HOUR_Start;
    }

    public TextField Return_TextField_Min_StartTime()   {
        return this.INPUTTIME_MIN_Start;
    }

    public TextField Return_TextField_Hour_EndTime()    {
        return this.INPUTTIME_HOUR_End;
    }

    public TextField Return_TextField_Min_EndTime()     {
        return this.INPUTTIME_MIN_End;
    }



    /**
     * Construct_comboBox_Defualt()
     * Description: Default state for comboxes, holds either AM or PM selection for time frame
     * @param INPUT_COMBOBOX
     */
    private void Construct_comboBox_Default(ComboBox<String> INPUT_COMBOBOX) {
        //TODO: 
        // INPUT_COMBOBOX.setPrefSize(100, 25.0);
        INPUT_COMBOBOX.getItems().addAll(global.Global_Data_Get_TimeFrames());
        INPUT_COMBOBOX.getSelectionModel().select("AM");
    } // Construct_comboBox_Defualt()



    /**
     * Construct_TextField_Default_Hour()
     * Description: Default Hour state for the textfield, holds an Hour for [0, 12] inclusive
     * @param INPUT_TEXTFIELD
     */
    private void Construct_TextField_Default_Hour(TextField INPUT_TEXTFIELD) {

        INPUT_TEXTFIELD.setTextFormatter(new TextFormatter<>(change -> {

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
    } // Construct_TextField_Default_Hour()



    /**
     * Construct_TextField_Default_Min()
     * Description: Default Minute state for the textfield, holds an Hour for [0, 12) inclusive
     * @param INPUT_TEXTFIELD
     */
    private void Construct_TextField_Default_Min(TextField INPUT_TEXTFIELD) {

        INPUT_TEXTFIELD.setTextFormatter(new TextFormatter<>(change -> {
            
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

    } // Construct_TextField_Default_Min()



    /**
     * Construct_TextField_Default_Hour_END()
     * Description: for the end hour textfield only, alters the field to never be before the beginning hour textifeld
     * @param INPUT_TEXTFIELD
     */
    private void Construct_TextField_Default_Hour_END(TextField INPUT_TEXTFIELD) {
        INPUT_TEXTFIELD.setTextFormatter(new TextFormatter<>(change -> {
            
            // User input text
            String TextInput = change.getControlNewText();

            // if the text is empty accept it
            if (TextInput.isEmpty()) {
                return change;
            }

            // ensures end hour is never before the beginning hour
            if (!this.INPUTTIME_HOUR_Start.getText().isBlank()) {                                               // if the input is not blank

                if (    (Integer.parseInt(this.INPUTTIME_HOUR_Start.getText()) > Integer.parseInt(TextInput))   // if -> beginning hour > ending hour
                    &&  (this.AMPM_START.getValue().equals(this.AMPM_END.getValue()))                           // and -> both start and end itervals are AM or PM
                    &&  (Integer.parseInt(this.INPUTTIME_HOUR_Start.getText()) != 12)                           // and the beginning hour is not 12
                ) {
                    this.INPUTTIME_HOUR_End.setText(this.INPUTTIME_HOUR_Start.getText());                       // Ending hour changes to equal starting hour
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
    
    } // Construct_TextField_Default_Hour_END()



    /**
     * Construct_TextField_Default_Min_END()
     * Description: for the end minute textfield only, alters the field to never be before the beginning minute textifeld
     * @param INPUT_TEXTFIELD
     */
    private void Construct_TextField_Default_Min_END(TextField INPUT_TEXTFIELD) {
        INPUT_TEXTFIELD.setTextFormatter(new TextFormatter<>(change -> {
            
            // User input text
            String TextInput = change.getControlNewText();

            // if th etext is empty accept it
            if (TextInput.isEmpty()) {
                return change;
            }

            // if both the beginning and ending hour are the same and at the same period (AM/PM) ensure the ending minute is always later
            if ((!this.INPUTTIME_HOUR_Start.getText().isBlank()) && (!this.INPUTTIME_HOUR_End.getText().isBlank())) {                   // if both start/end hours are input

                if ( (Integer.parseInt(this.INPUTTIME_HOUR_Start.getText()) == Integer.parseInt(this.INPUTTIME_HOUR_End.getText()))     // if -> beginning hour equals ending hour
                    &&  ( Integer.parseInt(TextInput) < Integer.parseInt(this.INPUTTIME_MIN_Start.getText()) )                          // and -> ending min < starting min
                    &&  ( this.AMPM_START.getValue().equals(this.AMPM_END.getValue()) )                                                 // and -> both times are AM or PM
                ) {
                    this.INPUTTIME_MIN_End.setText(this.INPUTTIME_MIN_Start.getText());                                                 // set ending min same as start min
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
    } // Construct_TextField_Default_Min_END()

} // USERINPUT_DayTimeInput()
