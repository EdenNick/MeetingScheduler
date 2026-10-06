// Package  - DO Not Change
// ############################################################
package meeting_scheduler.UserInput;
// ############################################################

// Imports
// ############################################################
// javafx
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import meeting_scheduler.global;
import meeting_scheduler.SystemInfoManager;
// ############################################################



public class InputTextField {


    private final TextField TEXTFIELD;
    private final String    PARAM_PROMPT;
    private final double    PARAM_WIDTH;
    private final double    PARAM_HEIGHT;

    private final int IDENT_MIN = SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_INT(global.BASICVALUESINT.IDENTCONSTRAINT, 0);
    private final int IDENT_MAX = SystemInfoManager.GET_SYSTEM_GLOBAL_VALUE_INT(global.BASICVALUESINT.IDENTCONSTRAINT, 1);
    /**
     * Constructor()
     * @param INPUT_ENUM_STATE
     * @param INPUT_PROMPT_TEXT
     * @param INPUT_WIDTH
     * @param INPUT_HEIGHT
     */
    public InputTextField(global.TextFieldState INPUT_ENUM_STATE, String INPUT_PROMPT_TEXT, double INPUT_WIDTH, double INPUT_HEIGHT) {

        this.TEXTFIELD      = new TextField();
        this.PARAM_PROMPT   = INPUT_PROMPT_TEXT;
        this.PARAM_WIDTH    = INPUT_WIDTH;
        this.PARAM_HEIGHT   = INPUT_HEIGHT;


        Construct_Field_BasicParameters();

        switch(INPUT_ENUM_STATE) {
            case TEXT:
                Construct_Field_Text();
                break;
            case NUMERIC:
                Construct_Field_Numeric();
                break;
            default:
                // TODO: SYSTEM MESSAGE ERROR
                break;
        }
    
    }


    private void Construct_Field_BasicParameters() {
        this.TEXTFIELD.setPromptText(this.PARAM_PROMPT);
        this.TEXTFIELD.setPrefSize(this.PARAM_WIDTH, this.PARAM_HEIGHT);
    }



    private void Construct_Field_Text() {

        this.TEXTFIELD.setTextFormatter(new TextFormatter<>(change -> {
            
            // User input text
            String TextInput = change.getControlNewText();

            // if the text is empty accept it
            if (TextInput.isEmpty()) {
                return change;
            }

            // test if the text is a valid int within a valid range
            try {
                if(TextInput.matches("^[a-zA-Z ]*$")){
                    return change;
                }
            } catch (Error e) {
                // Invalid Input
            }

            return null;

        }));

    } // Construct_Field_Text()



    private void Construct_Field_Numeric() {

        this.TEXTFIELD.setTextFormatter(new TextFormatter<>(change -> {
            
            // User input text
            String TextInput = change.getControlNewText();

            // if the text is empty accept it
            if (TextInput.isEmpty()) {
                return change;
            }

            // test if the text is a valid int within a valid range
            try {

                int intValue = Integer.parseInt(TextInput);
                
                if (intValue >= IDENT_MIN && intValue < IDENT_MAX) {
                    return change;
                }
            } catch (NumberFormatException e) {
                // Invalid Input
            }

            return null;

        }));

    } // Construct_Field_Numeric()


    public TextField Return_Field_constructed() {
        return this.TEXTFIELD;
    }
}
