package meeting_scheduler.UserInput;

import javafx.scene.control.ComboBox;
import meeting_scheduler.global;

public class USERINPUT_TextList {

    private final String Parameter_Prompt;

    private final global.TextListState Enum_State;

    private final ComboBox<String> ComboBox;

    private final int Parameter_Width;

    private final int Parameter_height;

    public USERINPUT_TextList(global.TextListState INPUT_ENUM_STATE, String INPUT_PROMPT_TEXT, int INPUT_WIDTH, int INPUT_HEIGHT) {
        this.ComboBox = new ComboBox<>();

        this.Enum_State         = INPUT_ENUM_STATE;

        this.Parameter_Prompt   = INPUT_PROMPT_TEXT;
        this.Parameter_Width    = INPUT_WIDTH;
        this.Parameter_height   = INPUT_HEIGHT;


        Construct_Field_BasicParameters();

        switch(Enum_State) {
            case WEEK:
                Construct_Field_WeekList();
                break;
            default:
                // TODO: Error
                break;
        }
    }


    private void Construct_Field_BasicParameters() {
        this.ComboBox.setPromptText(this.Parameter_Prompt);
        this.ComboBox.setPrefSize(this.Parameter_Width, this.Parameter_height);
    }

    private void Construct_Field_WeekList() {
        this.ComboBox.getItems().addAll(global.Global_Data_Get_Weekdays());
    }

    public ComboBox<String> Return_Field_Constructed() {
        return this.ComboBox;
    }
    
}
