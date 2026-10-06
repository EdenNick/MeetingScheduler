// Package  - DO Not Change
// ############################################################
package meeting_scheduler.UserInput;
// ############################################################

// IMPORTS
// ############################################################
// javafx
import javafx.scene.control.ComboBox;
import meeting_scheduler.global;
// ############################################################



public class InputComboBox {

    private final global.TextListState  ENUM_STATE;
    private final ComboBox<String>      COMBOBOX;
    private final String    PARAM_PROMPT;
    private final int       PARAM_WIDTH;
    private final int       PARAM_HEIGHT;
    
    public InputComboBox(global.TextListState INPUT_ENUM_STATE, String INPUT_PROMPT_TEXT, int INPUT_WIDTH, int INPUT_HEIGHT) {
        
        this.COMBOBOX       = new ComboBox<>();
        this.ENUM_STATE     = INPUT_ENUM_STATE;
        this.PARAM_PROMPT   = INPUT_PROMPT_TEXT;
        this.PARAM_WIDTH    = INPUT_WIDTH;
        this.PARAM_HEIGHT   = INPUT_HEIGHT;

        Construct_Field_BasicParameters();

        switch(ENUM_STATE) {
            case WEEK:
                Construct_Field_WeekList();
                break;
            default:
                // TODO: SYSTEM MESSAGE
                break;
        }
    }


    private void Construct_Field_BasicParameters() {
        this.COMBOBOX.setPromptText(this.PARAM_PROMPT);
        this.COMBOBOX.setPrefSize(this.PARAM_WIDTH, this.PARAM_HEIGHT);
    }

    private void Construct_Field_WeekList() {
        this.COMBOBOX.getItems().addAll(global.Global_Array_WeekDay_Short_Get());
    }

    public ComboBox<String> Return_Field_Constructed() {
        return this.COMBOBOX;
    }
    
}
