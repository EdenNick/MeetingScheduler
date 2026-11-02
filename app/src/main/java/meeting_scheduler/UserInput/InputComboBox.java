// Package  - DO Not Change
// ############################################################
package meeting_scheduler.UserInput;
// ############################################################

// IMPORTS
// ############################################################
import javafx.scene.control.ComboBox;
import meeting_scheduler.SystemInfoManager;
import meeting_scheduler.global;
// ############################################################



public class InputComboBox {

    // Constructor Fields
    // ############################################################
    private final global.TextListState  ENUM_STATE;
    private final ComboBox<String>      COMBOBOX;
    private final String                PARAM_PROMPT;
    private final double                PARAM_WIDTH;
    private final double                PARAM_HEIGHT;
    // ############################################################

    
    //Constructor
    public InputComboBox(global.TextListState INPUT_ENUM_STATE, String INPUT_PROMPT_TEXT, double INPUT_WIDTH, double INPUT_HEIGHT, String[] INPUT_OPTIONS) {
        
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

            case PEOPLE:
                Construct_Field_PeopleList(INPUT_OPTIONS);
                break;

            default:
                break;
        }
    }


    /**
     * Construct_Field_BasicParameters()
     * Description: Sets the prompt and size of the combobox
     */
    private void Construct_Field_BasicParameters() {
        this.COMBOBOX.setPromptText(this.PARAM_PROMPT);
        this.COMBOBOX.setPrefSize(this.PARAM_WIDTH, this.PARAM_HEIGHT);
    }

    /**
     * Construct_Field_WeekList()
     * Description: sets the values of the combobox to be the standard weekdayList
     */
    private void Construct_Field_WeekList() {
        this.COMBOBOX.getItems().addAll(SystemInfoManager.GET_SYSTEM_VALUE_WEEK(global.WEEKTYPE.SHORT));
    }


    /**
     * Construct_Field_PeopleList()
     * Description: constructs the combobox with a user submitted string
     * @param INPUT_PEOPLE
     */
    public void Construct_Field_PeopleList(String[] INPUT_PEOPLE) {
        this.COMBOBOX.getItems().clear();
        this.COMBOBOX.getItems().addAll(INPUT_PEOPLE);
    }

    /**
     * Return_Field_Constructed
     * Description: returns the constructed combobox of type string
     * @return
     */
    public ComboBox<String> Return_Field_Constructed() {
        return this.COMBOBOX;
    }
    
} // InputComboBox{}