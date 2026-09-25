package meeting_scheduler.UserInput;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
//import meeting_scheduler.UserInput.USERINPUT_TextField.TextFieldState;

import meeting_scheduler.global;

public class USERINPUT_TextField {



    private final String Parameter_Prompt;

    private final global.TextFieldState Enum_State;

    private final TextField TextField;

    private final int Parameter_Width;

    private final int Parameter_height;

    public USERINPUT_TextField(global.TextFieldState INPUT_ENUM_STATE, String INPUT_PROMPT_TEXT, int INPUT_WIDTH, int INPUT_HEIGHT) {

        this.TextField          = new TextField();

        this.Enum_State         = INPUT_ENUM_STATE;

        this.Parameter_Prompt   = INPUT_PROMPT_TEXT;
        this.Parameter_Width    = INPUT_WIDTH;
        this.Parameter_height   = INPUT_HEIGHT;


        Construct_Field_BasicParameters();

        switch(Enum_State) {
            case TEXT:
                Construct_Field_Text();
                break;
            case NUMERIC:
                Construct_Field_Numeric();
                break;
            default:
                // TODO: Error
                break;
        }
    
    }


    private void Construct_Field_BasicParameters() {
        this.TextField.setPromptText(this.Parameter_Prompt);
        this.TextField.setPrefSize(this.Parameter_Width, this.Parameter_height);
    }



    private void Construct_Field_Text() {

        this.TextField.setTextFormatter(new TextFormatter<>(change -> {
            
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

        this.TextField.setTextFormatter(new TextFormatter<>(change -> {
            
            // User input text
            String TextInput = change.getControlNewText();

            // if the text is empty accept it
            if (TextInput.isEmpty()) {
                return change;
            }

            // test if the text is a valid int within a valid range
            try {
                int intValue = Integer.parseInt(TextInput);

                //TODO: add global variable for min an max ident numbers;
                if (intValue >= 0 && intValue < 9999999) {
                    return change;
                }
            } catch (NumberFormatException e) {
                // Invalid Input
            }

            return null;

        }));

    } // Construct_Field_Numeric()


    public TextField Return_Field_constructed() {
        return this.TextField;
    }
}
