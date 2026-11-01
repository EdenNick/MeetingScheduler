package meeting_scheduler.UserInput;

// IMPORTS
// ############################################################
import java.util.LinkedHashMap;
import java.util.LinkedList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import meeting_scheduler.DataHolder.PreferenceFull;
import meeting_scheduler.DataHolder.PreferenceTime;
import meeting_scheduler.FileManagement.ManageJsonFile;
import meeting_scheduler.SystemInfoManager;
import meeting_scheduler.global;
// ############################################################

public class ManageDataCardSelection {

    // DataContainers
    // ############################################################
    private LinkedList<PreferenceFull>  PreferenceListFromFile;     // Primary File Copy - Use to Read from and wirte to file
    private PreferenceFull              PreferenceCurrentPerson;    // currently selected person being updated
    private LinkedList<PreferenceTime>  PreferenceListCurrent;      // Current selected persons preference times

    // Contains key value pair for datacards
    // Key: position in comboBox, general order
    // Value: PreferenceFull object, dataCard
    private LinkedHashMap<Integer, PreferenceFull> DataCardMap;
    // ############################################################

    // Nodes
    // ############################################################
    private VBox                PrimaryHolder;
    private HBox                ComboBoxHolder;
    private HBox                ButtonHolder;
    private FlowPane            UIOutPutFlowPane;       // Visual display for UI output
    private ComboBox<String>    DatacardComboBox;       // Visual UI for datacard selection
    private TextField           TextFieldName;          // TextField the user enters an employees name in
    private TextField           TextFieldIdent;         // textfield the user enters the employees ID in
    // ############################################################

    // Managing Objects
    // ############################################################
    private InputComboBox       ComboBoxManager;        // creates ui display allowing user selection of different datacards
    private ManageDayTime       TimeInputManager;       // creates display UI nodes allowing interaction with data
    private ManageJsonFile      JSONFileManager;        // Writes and reads from json file
    private ManageJsonFormat    JSONFileFormatter;      // formats data for when its written to the json file
    private ManageDataCardInput DataCardmanager;        // manages and formats datacard organization
    // ############################################################

    // Action Events
    // ############################################################
    private EventHandler<ActionEvent> EVENT_SELECTCOMBOBOX  = null;     // shows data for current combobox selection
    private EventHandler<ActionEvent> EVENT_SETDATATOAFILE  = null;     // updates datacard to user selections
    private EventHandler<ActionEvent> EVENT_DESELECTEDITOR  = null;     // DeSelects data without making any file changes
    private EventHandler<ActionEvent> EVENT_DELETEDATACARD  = null;     // marks a datacard for deletion for the json file formatter
    private EventHandler<ActionEvent> EVENT_KEEPTHEDATACRD  = null;     // de-marks a datacard for deletion for the json file formatter
    // ############################################################

    // Buttons
    // ############################################################
    private Button BUTTON_SELECTCOMBOBOX;
    private Button BUTTON_SETDATATOAFILE;
    private Button BUTTON_DELETEDATACARD;
    private Button BUTTON_DESELECTEDITOR;
    private Button BUTTON_KEEPTHEDATACRD;
    // ############################################################

    // class variables
    // ############################################################
    private int CurrentIndexPosition = -1;
    private final String SELECTED = SystemInfoManager.GET_SYSTEM_VALUE_STRING(global.BASICVALUESSTRING.TEXT, 11); // 11 - SELECTED
    private String STYLE_ONE;
    private String STYLE_TWO;
    private boolean ManageSelectionActive = false;

    private double ButtonWidth;
    private double ButtonHeight;
    // ############################################################




    // Constructor
    public ManageDataCardSelection(ManageJsonFile INPUT_FILEMANAGER, FlowPane INPUT_FLOWPANE, ManageDayTime TIME_INPUT_MANAGER, 
        LinkedList<PreferenceTime> INPUT_CURRENT_LIST, TextField INPUT_TEXTFIELD_NAME, TextField INPUT_TEXTFIELD_IDENT) {
        
        this.JSONFileManager        = INPUT_FILEMANAGER;
        this.UIOutPutFlowPane       = INPUT_FLOWPANE;
        this.TimeInputManager       = TIME_INPUT_MANAGER;
        this.PreferenceListCurrent  = INPUT_CURRENT_LIST;
        this.TextFieldName          = INPUT_TEXTFIELD_NAME;
        this.TextFieldIdent         = INPUT_TEXTFIELD_IDENT;
        this.PreferenceListFromFile = new LinkedList<>();

        this.DataCardMap        = new LinkedHashMap<>();
        this.JSONFileFormatter  = new ManageJsonFormat();

        this.ButtonWidth        = SystemInfoManager.GET_SYSTEM_VALUE_DOUBLE(global.UISPACING.WIDTH, 0);      // 0 - 50.0
        this.ButtonHeight       = SystemInfoManager.GET_SYSTEM_VALUE_DOUBLE(global.UISPACING.HEIGHT, 1);     // 1 - 25.0
        this.STYLE_ONE          = SystemInfoManager.GET_SYSTEM_VALUE_STRING(global.BASICVALUESSTRING.STYLE, 4);
        this.STYLE_TWO          = SystemInfoManager.GET_SYSTEM_VALUE_STRING(global.BASICVALUESSTRING.STYLE,1);  // 1 - default-label
        SetupPreferenceList();
        SetupComboBox();
        EventCreation();
        ButtonCreation();
        CreateUI();

    } // InputDataCardSelection()



    /**
     * GetUI()
     * Description: return the assembled UI for editing entered DataCards
     * @return VBox this.PrimaryHolder
     */
    public VBox GetUI() {
        return this.PrimaryHolder;
    }


    public boolean GetSelectionState() {
        return this.ManageSelectionActive;
    }


    public void SetSelectionState(boolean INPUT_BOOLEAN) {
        this.ManageSelectionActive = INPUT_BOOLEAN;
    }


    /**
     * CreateUI()
     * Description: Creates the user interface, assembling the various nodes already created.
     */
    private void CreateUI() {

        // COMBOBOX HOLDER  - contains labels and the combobox the user uses to select datacards that already exist in the json file
        this.ComboBoxHolder = new HBox(10.0);
        this.ComboBoxHolder.getChildren().addAll(
            DatacardComboBox
        );

        // BUTTON HOLDER    - contains the various user interface buttons used to interact with and edit the info in the combobox
        this.ButtonHolder = new HBox(10.0);
        this.ButtonHolder .getChildren().addAll(
            BUTTON_SELECTCOMBOBOX,
            BUTTON_DELETEDATACARD,
            BUTTON_KEEPTHEDATACRD,
            BUTTON_DESELECTEDITOR
        );

        Label EditInstructions = new Label("Select Datacard to Edit");
        EditInstructions.getStyleClass().add(this.STYLE_TWO);
        this.PrimaryHolder = new VBox();
        this.PrimaryHolder.setSpacing(10.0);
        this.PrimaryHolder.setPrefSize(500.0 , 300.0);
        this.PrimaryHolder.setPadding(new Insets(5.0));
        this.PrimaryHolder.getStyleClass().add(STYLE_ONE);
        this.PrimaryHolder.getChildren().addAll(
            EditInstructions,
            ComboBoxHolder,
            ButtonHolder,
            BUTTON_SETDATATOAFILE
        );

    } // CreateUI()



    /**
     * UpdateListAndComboBox()
     * Description: Updates the preference list to the current Json file info and updates the combobox with this new information
     */
    public void UpdateListAndComboBox() {
        RestoreComboBox();
        SetupPreferenceList();
        UpdateComboBox();
        ClearFlowPane();
    }



    /**
     * SetupPreferenceList()
     * Description: Updates the LinkedHashMap DataCardMap to the current info stored within the LinkedList PreferenceListFromFile
     */
    private void SetupPreferenceList() {
        
        if (this.PreferenceListFromFile.isEmpty() == true) {
            // initialize
            this.PreferenceListFromFile = JSONFileManager.readJson_FullPref();
        } else {
            // clear and reinitialize
            this.PreferenceListFromFile.clear();
            this.PreferenceListFromFile = JSONFileManager.readJson_FullPref();
        }

        this.DataCardMap.clear();
        
        for (int Pos = 0; Pos < this.PreferenceListFromFile.size(); Pos++) {
            this.DataCardMap.put(Pos, PreferenceListFromFile.get(Pos));
        }
    } // SetupPreferenceList()



    /**
     * UpdateInfo()
     * Description: Updates the combox selectors info, adding all people from PreferenceListFromFile
     */
    private void UpdateComboBox() {

        String[]    ComboBoxText = new String[this.PreferenceListFromFile.size()];
        int         ID;
        String      Name;
        String      FormatText;

        // For each dataCard stored in the file, format its info and add it to the ComboBoxText array
        for (int Pos = 0; Pos < this.DataCardMap.size(); Pos++) {
            ID                  = this.DataCardMap.get(Pos).GetIdent();
            Name                = this.DataCardMap.get(Pos).GetName();
            FormatText          = String.format("ID: %07d | Name: %-20.20s", ID, Name);
            ComboBoxText[Pos]   = FormatText;
        } // for (int Pos = 0; Pos < this.DataCardMap.size(); Pos++)

        // update and set the combobox to contain the new lsit of values
        ComboBoxManager.Construct_Field_PeopleList(ComboBoxText);

    } // UpdateInfo()



    /**
     * RestoreComboBox()
     * Description: restores a selected datacard in a combobox to its previous value
     */
    private void RestoreComboBox() {

        // ensure the current index is set to an actual datacard
        if (this.CurrentIndexPosition >= 0) {

            // retrieve the selected datacard values from the map
            int     ID      = DataCardMap.get(this.CurrentIndexPosition).GetIdent();
            String  Name    = DataCardMap.get(this.CurrentIndexPosition).GetName();

            // format the values
            String FormatText = String.format("ID: %07d | Name: %-20.20s", ID, Name);

            // remove the 'SELECTED' text that exisits at the combobox position
            this.DatacardComboBox.getItems().remove(this.CurrentIndexPosition);

            // add in the formatted text back to where it should be relative to its position in the hashmap
            this.DatacardComboBox.getItems().add(this.CurrentIndexPosition, FormatText);

            // set CurrentIndexPosition to -1, as it should no longer be set to any valid position (any number equal to or greater than 0)
            this.CurrentIndexPosition = -1;

            // Set the combobox to select a null value, resetting the selection to a blank one
            // this does not alter the values in the combobox and is purely asthetic
            this.DatacardComboBox.getSelectionModel().clearSelection();
            this.DatacardComboBox.setValue(null);

        } // if (this.CurrentIndexPosition >= 0)

    } // RestoreComboBox()



    /**
     * ClearFlowPane()
     * Description: Clears the output Flowpnae of all node/values
     */
    private void ClearFlowPane() {

        // Clear all preference times from the current preference list
        PreferenceListCurrent.clear();

        // for all nodes in the Flowpane clear them of any values so garbage collection can occur
        for (Node node: UIOutPutFlowPane.getChildren()) {
            if (node instanceof VBox vbox) {
                vbox.getChildren().clear();
            }

        } // for()

        // clear all nodes in the flowpane
        UIOutPutFlowPane.getChildren().clear();

    } // ClearFlowPane()



    /**
     * SetupComboBox()
     * Description: initial setup for the combobox manager. should only be called once to avoid unecessary object creation/reset
     */
    private void SetupComboBox() {

        String[]    ComboBoxText = new String[this.PreferenceListFromFile.size()];
        int         ID;
        String      Name;
        String      FormatText;

        // For each dataCard stored in the file, format its info and add it to the ComboBoxText array
        for (int Pos = 0; Pos < this.DataCardMap.size(); Pos++) {
            ID                  = this.DataCardMap.get(Pos).GetIdent();
            Name                = this.DataCardMap.get(Pos).GetName();
            FormatText          = String.format("ID: %07d | Name: %-20.20s", ID, Name);
            ComboBoxText[Pos]   = FormatText;
        } // for (int Pos = 0; Pos < this.DataCardMap.size(); Pos++)

        // create a new combobox manager with specified size and text options
        this.ComboBoxManager    = new InputComboBox(global.TextListState.PEOPLE, null, 350.0, 25.0, ComboBoxText);
        
        // set the constructed comboBox to the local variable for use in this class
        this.DatacardComboBox   = ComboBoxManager.Return_Field_Constructed();
        
    } // SetupComboBox()



    /**
     * ButtonCreation()
     * Description: Creates the buttons for the UI
     */
    private void ButtonCreation() {

        // Select Combobox for editing
        // ############################################################
        this.BUTTON_SELECTCOMBOBOX = new Button("Select");
        this.BUTTON_SELECTCOMBOBOX.setOnAction(this.EVENT_SELECTCOMBOBOX);
        this.BUTTON_SELECTCOMBOBOX.setPrefSize(this.ButtonWidth, this.ButtonHeight);
        // ############################################################

        // Set Data to file
        // ############################################################
        this.BUTTON_SETDATATOAFILE = new Button("Update");
        this.BUTTON_SETDATATOAFILE.setOnAction(this.EVENT_SETDATATOAFILE);
        this.BUTTON_SELECTCOMBOBOX.setPrefSize(this.ButtonWidth, this.ButtonHeight);
        // ############################################################

        // DE-Select Dtacard without making any changes
        // ############################################################
        this.BUTTON_DESELECTEDITOR = new Button("De-Select");
        this.BUTTON_DESELECTEDITOR.setOnAction(this.EVENT_DESELECTEDITOR);
        this.BUTTON_SELECTCOMBOBOX.setPrefSize(this.ButtonWidth, this.ButtonHeight);
        // ############################################################

        // Delete DataCard
        // ############################################################
        this.BUTTON_DELETEDATACARD = new Button("Delete");
        this.BUTTON_DELETEDATACARD.setOnAction(this.EVENT_DELETEDATACARD);
        this.BUTTON_SELECTCOMBOBOX.setPrefSize(this.ButtonWidth, this.ButtonHeight);
        // ############################################################

        // Delete DataCard
        // ############################################################
        this.BUTTON_KEEPTHEDATACRD = new Button("UN-Delete");
        this.BUTTON_DELETEDATACARD.setOnAction(this.EVENT_KEEPTHEDATACRD);
        this.BUTTON_SELECTCOMBOBOX.setPrefSize(this.ButtonWidth, this.ButtonHeight);
        // ############################################################

    } // ButtonCreation()



    /**
     * EventCreation()
     * Description: creates the events for the UI buttons
     */
    private void EventCreation() {

        // SELECT THE DATACARD  - Selects currently set postion in the comboBox displaying the relevant data of the selected DatCard
        // ############################################################
        this.EVENT_SELECTCOMBOBOX = event -> {

            // If a combobox is selected
            if ( (DatacardComboBox.getValue() != null) && (DatacardComboBox.getValue() != this.SELECTED) ) {
                
                // Clear the FlowPane of Any previous Nodes
                ClearFlowPane();

                // If a DataCard was already Selected restore the previous one to the list
                RestoreComboBox();

                // Get the current index position of the selected Datacard
                this.CurrentIndexPosition = DatacardComboBox.getSelectionModel().getSelectedIndex();

                // remove the text at the current position
                this.DatacardComboBox.getItems().remove(this.CurrentIndexPosition);

                // replace the text with 'SELECTED'
                this.DatacardComboBox.getItems().add(this.CurrentIndexPosition, this.SELECTED);
                



                // Set the current Person to the datacard object stored in DataCardMap, the combobox and DataCardMap both store values in the same positions
                PreferenceCurrentPerson = DataCardMap.get(CurrentIndexPosition);

                // Set the name TextField to contain the name of the person selected
                this.TextFieldName.setText(PreferenceCurrentPerson.GetName());

                // Set the ID TextField to contain the ID of the perosn selected
                this.TextFieldIdent.setText(Integer.toString(PreferenceCurrentPerson.GetIdent()));

                // set the datacard manager to a new isntance with this preference
                DataCardmanager = new ManageDataCardInput(PreferenceCurrentPerson);

                // Organize the time preference in the DataCard
                DataCardmanager.OrganizeGeneral();

                // For all time preferences in the datacard add all of them to the Flowpane for display
                int Intervalelength = PreferenceCurrentPerson.GetIntervals().length;

                for (int Pos = 0; Pos < Intervalelength; Pos++) {

                    /**
                     * ############################################################
                     * ManageDayTime  TimeInputManager.UserInputGraphic()
                     * 
                     * Inputs user preference to be displayed to the user as well as stored for eventual submission to file
                     * 
                     * PreferenceCurrentPerson.GetIntervals()[Pos]          - individual time preference containing day. start and end times
                     * 
                     * PreferenceListCurrent                                - A list of all time preferences a person has. To be submitted to the json file not displayed
                     * 
                     * FlowPane UIOutPutFlowPane                            - A flowpane which displays the various Vboxs that hold user preferences. display only
                     * 
                     * true                                                 - indicates the weekday should be displayed with the other info
                     */
                    TimeInputManager.UserInputGraphic(PreferenceCurrentPerson.GetIntervals()[Pos], PreferenceListCurrent, this.UIOutPutFlowPane , true);
                    // ############################################################

                } // for (int Pos = 0; Pos < Intervalelength; Pos++)


                // datacard selection is currently active
                this.ManageSelectionActive = true;


            } else {
                // Nothing selected -> do nothing
            }

        };
        // ############################################################



        // Set DAta     - Set the altered Data to the file, updating the ui in the process
        // ############################################################
        this.EVENT_SETDATATOAFILE = event -> {

            if (this.CurrentIndexPosition >= 0) {
                
                // Set updated preference times to the selected datacard using DataCardmanager
                this.DataCardmanager.UpdateTimeIntervals(this.PreferenceListCurrent);

                // Update the DataCard map with the new version of the selected datacard created with GetUpdatedDatacard()
                this.DataCardMap.put(this.CurrentIndexPosition, this.DataCardmanager.GetUpdatedDatacard());

                // Update the File preferencelist with the new DataCard, so it can be written to the Json File
                this.PreferenceListFromFile.set(this.CurrentIndexPosition, DataCardmanager.GetUpdatedDatacard());

                // Use the File formatter to remove any datacards marked for removal from the File List
                this.JSONFileFormatter.JsonFileDefault_FormatRemovals(this.PreferenceListFromFile);

                // Write PreferenceListFromFile to the Json file, updating it with new or altered info
                JSONFileManager.writeJson_FullPref(this.PreferenceListFromFile);

                // retrieve the updated file information and map it to the DataCard Map
                SetupPreferenceList();

                // Update the comboBox with the new or altered list of datacard in the DataCard Map
                UpdateComboBox();

                // Clear the FlowPane if there are any nodes still there
                ClearFlowPane();

            } // if (this.CurrentIndexPosition >= 0)

        };
        // ############################################################



        // DE-SELECT        - A user selected DataCard without making any changes to it or the file
        // ############################################################
        this.EVENT_DESELECTEDITOR = event -> {
            // clear the flowpane of anyValues
            ClearFlowPane();
            // restore the selected datacard to the comboBox
            RestoreComboBox();

            // Selection is no longer active
            this.ManageSelectionActive = false;
        };
        // ############################################################



        // DELETE DATACARD  - Mark the datacard for deletion, applied when user submits the info
        // ############################################################
        this.EVENT_DELETEDATACARD = event -> {
            if (CurrentIndexPosition >= 0) {
                // Mark a datacard for deletion
                DataCardmanager.RemovePreferenceAll();
            }
        };
        // ############################################################



        // UN-DELETE DATACARD   - De-Mark the datacard for deletion
        // ############################################################
        this.EVENT_KEEPTHEDATACRD = event -> {
            if (CurrentIndexPosition >= 0) {
                // Mark a datacard to not be deleted
                DataCardmanager.KeepPreferenceAll();
            }
        };
        // ############################################################

        
    } // EventCreation()
    


} // InputDataCardSelection()