// Package  - DO Not Change
// ############################################################
package meeting_scheduler.SceneManagement;
// ############################################################

// IMPORTS
// ############################################################
import java.util.LinkedList;
import javafx.util.Duration;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;
import meeting_scheduler.DataHolder.PreferenceFull;
import meeting_scheduler.DataHolder.PreferenceTime;
import meeting_scheduler.FileManagement.ManageFilePaths;
import meeting_scheduler.FileManagement.ManageJsonFile;
import meeting_scheduler.SystemInfoManager;
import meeting_scheduler.SystemManagement.ManageAppWindow;
import meeting_scheduler.SystemManagement.ManageScenes;
import meeting_scheduler.UserInput.ManageDataCardSelection;
import meeting_scheduler.UserInput.InputTextField;
import meeting_scheduler.UserInput.InputWeek;
import meeting_scheduler.UserInput.ManageDayTime;
import meeting_scheduler.UserInput.ManageJsonFormat;
import meeting_scheduler.global;
// ############################################################



public class SceneDataCard {

    // Application Window variables
    // ############################################################
    private final Stage         APPLICATION_STAGE;
    private double              stageWidth;
    private double              stageHeight;
    private Scene               sceneDatacard;
    private BackgroundFill      backgroundFill;
    private ParallelTransition  effectFade;
    private ParallelTransition  effectUnfade;
    // ############################################################

    // Nodes
    // ############################################################
    private AnchorPane      rootnodeDatacard;       // RootNode of the scene, contains all nodes
    private VBox            uiInputAll;             // Contains all UI nodes
    private VBox            uiInputTime;            // COntains all Ui nodes for time input
    private VBox            UIDataCardManage;       // Contains the Ui for managing the previously added datacards
    private FlowPane        uiOutputPref;           // Displays Input user time preferences
    private Button          buttonReturn;           // Returns to the main menu scene
    private Button          buttonAddPref;          // Adds input user times to preference
    private Button          buttonSubmitPref;       // Submits user preferences to the json file
    private Button          buttonResetPref;        // resets added preferences
    private Label           labelName;              // Label for the name input
    private Label           labelIdent;             // Label for the Id Input
    private TextField       textfieldIdent;         // Input field for the ID number
    private TextField       textfieldName;          // InputField for the employee name
    private InputTextField  textfieldIdentCreator;  // Obj that creates the ident textfield
    private InputTextField  textfieldNameCreator;   // Obj that creates the name textfield
    // ############################################################

    // Action Events
    // ############################################################
    private EventHandler<ActionEvent> EVENT_RETURNHOME      = null;
    private EventHandler<ActionEvent> EVENT_ADDINFO         = null;
    private EventHandler<ActionEvent> EVENT_SUBMITINFO      = null;
    private EventHandler<ActionEvent> EVENT_RESETTIMEPREF   = null;
    // ############################################################

    // User Data
    // ############################################################
    private LinkedList<PreferenceTime> listUserAddedPref;       // All preferences for a specific individual
    private LinkedList<PreferenceFull> listEmployeeFullPref;    // All preferences for every person
    // ############################################################

    // Data Manager Objects
    // ############################################################
    private ManageJsonFile          ObjJsonFileManager;     // Manages I/O file operations
    private ManageJsonFormat        ObjJsonFileFormatter;   // Manages format operations before write file operations
    private ManageDayTime           ObjTimeInputManager;    // Manages Specific day/time UI operations
    private ManageDataCardSelection ObjDataCardmanager;     // Manages submitted Datacard formatting/edits
    // ############################################################

    // UI formatting variables
    // ############################################################
    private final double ANCHOR_ONE;        // 10.0     // ANCHOR
    private final double ANCHOR_TWO;        // 20.0     //
    private final double SPACING_ONE;       // 10.0     // SPACING
    private final double SPACING_TWO;       // 10.0     //
    private final double WIDTH_ONE;         // 400.0    // WIDTH
    private final double WIDTH_TWO;         // 800.0    //
    private final double WIDTH_THREE;  // 150.0    //
    private final double HEIGHT_ONE;        // 600.0    // HEIGHT
    private final double HEIGHT_TWO;        // 800.0    //
    private final double HEIGHT_THREE; // 30.0     //
    private final double PADDING_ONE;       // 10.0     // PADDING
    private final double PADDING_TWO;       // 10.0     //
    // ############################################################

    // Styles and text
    // ############################################################
    private final String STYLE_SET;     // /CSS_Styles.css
    private final String STYLE_ONE;     // DataCard_UI_DefaultInputUI
    private final String STYLE_TWO;     // DataCard_UI_TimeOutput
    private final String STYLE_THREE;   // default-label
    private final String TEXT_ONE;      // Enter Name Here:
    private final String TEXT_TWO;      // Enter Full Name
    private final String TEXT_THREE;    // Enter ID Here:
    // ############################################################          

    // Calculation variables
    // ############################################################
    private final String[] WEEKDAYS;        // WeekDays short
    private final int MAX_TIME_INPUT;       // 24
    private boolean ReplaceWithNew;         // replace an existing datacard true/false
    // ############################################################



    /**
     * Constructor class
     */
    public SceneDataCard(Stage stage) {
        
        // Set the stage to the global reference so it can be managed by the scene manager
        // ############################################################
        this.APPLICATION_STAGE       = stage;
        // ############################################################

        // Formatted User Data
        // ############################################################
        this.listUserAddedPref      = new LinkedList<>();
        // ############################################################

        // File Management
        // ############################################################
        this.ObjJsonFileFormatter  = new ManageJsonFormat();
        this.ObjJsonFileManager    = new ManageJsonFile(ManageFilePaths.GetLock_JSN_DataCard());
        // ############################################################

        // UI Styling
        // ############################################################
        this.ANCHOR_ONE = SystemInfoManager.GET_SYSTEM_VALUE_DOUBLE(global.UISPACING.ANCHOR,1); // 1 - 10.0
        this.ANCHOR_TWO = SystemInfoManager.GET_SYSTEM_VALUE_DOUBLE(global.UISPACING.ANCHOR,3); // 3 - 20.0

        this.SPACING_ONE    = SystemInfoManager.GET_SYSTEM_VALUE_DOUBLE(global.UISPACING.SPACING,1);    // 1 - 10.0
        this.SPACING_TWO    = SystemInfoManager.GET_SYSTEM_VALUE_DOUBLE(global.UISPACING.SPACING,1);    // 1 - 10.0
        
        this.WIDTH_ONE      = SystemInfoManager.GET_SYSTEM_VALUE_DOUBLE(global.UISPACING.WIDTH,6);      // 6 - 400.0
        this.WIDTH_TWO      = SystemInfoManager.GET_SYSTEM_VALUE_DOUBLE(global.UISPACING.WIDTH,8);      // 8 - 800.0
        this.WIDTH_THREE    = SystemInfoManager.GET_SYSTEM_VALUE_DOUBLE(global.UISPACING.WIDTH,2);      // 2 - 150.0

        this.HEIGHT_ONE     = SystemInfoManager.GET_SYSTEM_VALUE_DOUBLE(global.UISPACING.HEIGHT,9);     // 9  - 600
        this.HEIGHT_TWO     = SystemInfoManager.GET_SYSTEM_VALUE_DOUBLE(global.UISPACING.HEIGHT,10);    // 10 - 800.0
        this.HEIGHT_THREE   = SystemInfoManager.GET_SYSTEM_VALUE_DOUBLE(global.UISPACING.HEIGHT,2);     // 2  - 30.0

        this.PADDING_ONE    = SystemInfoManager.GET_SYSTEM_VALUE_DOUBLE(global.UISPACING.PADDING,1);    // 1 - 10
        this.PADDING_TWO    = SystemInfoManager.GET_SYSTEM_VALUE_DOUBLE(global.UISPACING.PADDING,1);    // 1 - 10

        this.STYLE_SET      = SystemInfoManager.GET_SYSTEM_VALUE_STRING(global.BASICVALUESSTRING.STYLE,0);  // 0 - /CSS_Styles.css
        this.STYLE_ONE      = SystemInfoManager.GET_SYSTEM_VALUE_STRING(global.BASICVALUESSTRING.STYLE,5);  // 5 - DataCard_UI_DefaultInputUI
        this.STYLE_TWO      = SystemInfoManager.GET_SYSTEM_VALUE_STRING(global.BASICVALUESSTRING.STYLE,6);  // 6 - DataCard_UI_TimeOutput
        this.STYLE_THREE    = SystemInfoManager.GET_SYSTEM_VALUE_STRING(global.BASICVALUESSTRING.STYLE,1);  // 1 - default-label
        this.TEXT_ONE       = SystemInfoManager.GET_SYSTEM_VALUE_STRING(global.BASICVALUESSTRING.TEXT,1);   // 1 - Enter Name Here:
        this.TEXT_TWO       = SystemInfoManager.GET_SYSTEM_VALUE_STRING(global.BASICVALUESSTRING.TEXT,1);   // 2 - Enter Full Name
        this.TEXT_THREE     = SystemInfoManager.GET_SYSTEM_VALUE_STRING(global.BASICVALUESSTRING.TEXT,3);   // 3 - Enter ID Here:
        // ############################################################

        // Default variable creation
        // ############################################################
        this.WEEKDAYS       = SystemInfoManager.GET_SYSTEM_VALUE_WEEK(global.WEEKTYPE.SHORT);
        this.MAX_TIME_INPUT = SystemInfoManager.GET_SYSTEM_VALUE_INT(global.BASICVALUESINT.INT, 11); // 11 - 24
        this.ReplaceWithNew = false;
        // ############################################################

    } // SCENE_CREATE_DataCard()


    
    /**
     * changetoDataCardScene()
     * Description: Public method meant to be called outside the class in order to set the scene to the data card management scene
     */
    public void changetoDataCardScene() {

        // unfades nodes
        effectUnfade.play();

        // Gets the current size of the stage
        this.stageWidth   = this.APPLICATION_STAGE.getWidth();
        this.stageHeight  = this.APPLICATION_STAGE.getHeight();

        // Sets the stage to the main menu scene
        this.APPLICATION_STAGE.setScene(this.sceneDatacard);

        // sets the correct size for the stage
        this.APPLICATION_STAGE.setWidth(stageWidth);
        this.APPLICATION_STAGE.setHeight(stageHeight);

        // Shows the change
        this.APPLICATION_STAGE.show();


        // System Message
        // ############################################################
        // 5 - SUCCESS | 17 - SCENE_CREATE_DataCard | 11 - SYSTEM-switchScene | 11 - Scene Switch to Datacard page
        SystemInfoManager.GET_SYSTEM_MESSAGE(5,17,11,11);
        // ############################################################
        
    } // changetoDataCardScene()



    /**
     * ConstructCardManagerScene()
     * Description: Performs the necessary operations in order to build the various nodes/components of the stage.
     */
    public void ConstructCardManagerScene() {

        // Root Node construction
        // ############################################################
        // rootnode - must be called first
        this.rootnodeDatacard = new AnchorPane();
        // loads css styles
        this.rootnodeDatacard.getStylesheets().add(getClass().getResource(STYLE_SET).toExternalForm());
        // ############################################################

        // Creates event handlers for the various buttons
        // ############################################################
        Create_EventHandlers();
        // ############################################################

        // Creates Buttons for this scene and sets event handlers
        // ############################################################
        Create_Buttons();
        // ############################################################

        // Creates various UI components for the scene
        // ############################################################
        CreateUIComponents();
        // ############################################################

        // initialize Datacard manager
        // ############################################################
        CreatedataCardManager();
        // ############################################################

        // Creates layour for the user input UI
        // ############################################################
        Create_UIDisplays();
        // ############################################################

        // Anchor position set
        // ############################################################
        // Root Node - set return home button position
        AnchorPane.setBottomAnchor  (buttonReturn, ANCHOR_ONE);
        AnchorPane.setRightAnchor   (buttonReturn, ANCHOR_ONE);
        
        // Root Node - set UI interface input
        AnchorPane.setTopAnchor     (uiInputAll, ANCHOR_TWO);
        AnchorPane.setLeftAnchor    (uiInputAll, ANCHOR_TWO);

        // Root Node - set user cards
        AnchorPane.setTopAnchor     (uiOutputPref, ANCHOR_TWO);
        AnchorPane.setRightAnchor   (uiOutputPref, ANCHOR_TWO);
        // ############################################################
        
        // Add all sub-Nodes to the root node
        // ############################################################
        this.rootnodeDatacard.getChildren().addAll(uiInputAll, buttonReturn, uiOutputPref);
        // ############################################################

        // Set Graphical Effects - Must be called after all nodes are created and set, otherwise it will not fully fade/unfade them
        // ############################################################
        Create_SceneEffects();
        // ############################################################

        // create menu scene with the current node layout
        // ############################################################
        this.sceneDatacard = new Scene(rootnodeDatacard, ManageScenes.WindowWidth, ManageScenes.WindowHeight);
        // ############################################################

        // fade all objects before the scene is set
        // ############################################################
        this.effectFade.play();
        // ############################################################
        
        // System Message
        // ############################################################
        // 5 - SUCCESS | 17 - SCENE_CREATE_DataCard | 5 - SYSTEM-CreateScenes | 15 - Scene created and set
        SystemInfoManager.GET_SYSTEM_MESSAGE(5,17,5,15);
        // ############################################################

    } // ConstructCardManagerScene()

    


    private void CreatedataCardManager() {
        // InputDataCardSelection(ManageJsonFile INPUT_FILEMANAGER, FlowPane INPUT_FLOWPANE, ManageDayTime TIME_INPUT_MANAGER, LinkedList<PreferenceTime> INPUT_CURRENT_LIST)
        this.ObjDataCardmanager = new ManageDataCardSelection(this.ObjJsonFileManager, this.uiOutputPref, this.ObjTimeInputManager, this.listUserAddedPref, 
            this.textfieldName, this.textfieldIdent);
        this.UIDataCardManage = ObjDataCardmanager.GetUI();
    }




    /**
     * cardUIManagement()
     * Description: manages the node layout for the user card input
     */
    private void Create_UIDisplays() {

        // Vbox for UI
        // ############################################################
        // add nodes to the box
        this.uiInputAll.getChildren().addAll(
            this.UIDataCardManage,

            this.labelName,
            this.textfieldName, 

            this.labelIdent,
            this.textfieldIdent,

            this.uiInputTime,

            this.buttonResetPref,
            
            this.buttonSubmitPref
        );
        // ############################################################

    } // cardUIManagement






    /**
     * DatacardEventHandler()
     * Description: handles the creation of various event handlers for the scene
     */
    private void Create_EventHandlers() {

        // Returns to the main menu
        // ############################################################
        this.EVENT_RETURNHOME = event -> {
            
            this.textfieldName.clear();
            this.textfieldIdent.clear();
            this.ObjTimeInputManager.ClearInputs();

            effectFade.setOnFinished(event2 -> {
                ManageAppWindow.SceneManager.SwapToMainMenu();
            });

            effectFade.play();

            // System Message
            // ############################################################
            // 5 - SUCCESS | 17 - SCENE_CREATE_DataCard | 8 - SYSTEM-SetScenes | 13 - Scene Switch to Main Menu
            SystemInfoManager.GET_SYSTEM_MESSAGE(5,17,8,13);
            // ############################################################

        };
        // ############################################################



        // Adds a user preference to their datacard
        // ############################################################
        this.EVENT_ADDINFO = event -> {

            // checks to ensure all variables are input
            if ( (ObjTimeInputManager.CHECK_FullTimeInput() == 0) && (this.listUserAddedPref.size() < this.MAX_TIME_INPUT) ) {

                // Temp user preference created for clean seperation of object use
                PreferenceTime TempUserPreferrence = ObjTimeInputManager.Return_UserPreference();

                /**
                 * ############################################################
                 * PROG_UI_C_UserTimeInput DataCard_UserTimeInputs .FileReadyUserInputGraphic()
                 * 
                 * Inputs user preference to be displayed to the user as well as stored for eventual submission to file
                 * 
                 * PROG_DAL_A_TimeInput TempUserPreferrence             - individual time preference containing day. start and end times
                 * 
                 * LinkedList<PROG_DAL_A_TimeInput> listUserAddedPref   - A list of all time preferences a person has. To be submitted to the json file not displayed
                 * 
                 * LinkedList<VBox> List_VBoxTimeInputs                 - a copied list of all VBoxes stored in the flowpane display. Used to iterate, not to be displayed
                 * 
                 * FlowPane uiOutputPref                                - A flowpane which displays the various Vboxs that hold user preferences. display only
                 */
                ObjTimeInputManager.UserInputGraphic(TempUserPreferrence, this.listUserAddedPref, uiOutputPref, true);
                // ############################################################


                // garbage Collection
                TempUserPreferrence = null;


                // System Message
                // ############################################################
                // 5 - SUCCESS | 17 - SCENE_CREATE_DataCard | 13 - USER-AddingInfo | 16 - Data has been added
                SystemInfoManager.GET_SYSTEM_MESSAGE(5,17,13,16);
                // ############################################################

            } else {

                // System Message
                // ############################################################
                // 6 - FAILURE | 17 - SCENE_CREATE_DataCard | 13 - USER-AddingInfo | 17 - Data has not been added
                SystemInfoManager.GET_SYSTEM_MESSAGE(6,17,13,17);
                // ############################################################

            }


        };
        // ############################################################


        
        // Submits the added user preferences to the relevant json file
        // ############################################################
        this.EVENT_SUBMITINFO = event -> {
            
            // Submits USer info
            //System.out.println(PROG_DAL_D_SystemMessages.BUTTON_DataCard_EVENT_SUBMITINFO);

            // collect all info into the relvant linkedlist
            if (this.textfieldName.getText().isBlank())         { // Do nothing - Display message
                
                // System Message - user has not submitted a valid name
                // ############################################################
                // 6 - FAILURE | 17 - SCENE_CREATE_DataCard | 14 - USER-SubmittingInfo | 21 - data has not been submitted, invalid paramater
                SystemInfoManager.GET_SYSTEM_MESSAGE(6,17,14,21);
                // ############################################################

            } else if (this.textfieldIdent.getText().isBlank()) { // Do nothing - Display message
                
                // System Message - user has not submitted a valid name
                // ############################################################
                // 6 - FAILURE | 17 - SCENE_CREATE_DataCard | 14 - USER-SubmittingInfo | 21 - data has not been submitted, invalid paramater
                SystemInfoManager.GET_SYSTEM_MESSAGE(6,17,14,21);
                // ############################################################

            } else if (this.listUserAddedPref.size() < 1)       { // Do nothing - Display message
                
                // System Message - user has not submitted valid user times
                // ############################################################
                // 6 - FAILURE | 17 - SCENE_CREATE_DataCard | 14 - USER-SubmittingInfo | 21 - data has not been submitted, invalid paramater
                SystemInfoManager.GET_SYSTEM_MESSAGE(6,17,14,21);
                // ############################################################

            } else {

                // get the employee name
                String name = textfieldName.getText();

                // Get the employee ID
                int id = Integer.parseInt(textfieldIdent.getText());

                // Week Days Array
                // ############################################################
                InputWeek PreferredDays = new InputWeek();

                for (int WeekPos = 0; WeekPos < this.WEEKDAYS.length; WeekPos++) {
                    for (int PrefPos = 0; PrefPos < this.listUserAddedPref.size(); PrefPos++) {
                        if (this.listUserAddedPref.get(PrefPos).GetWeekDay().equals(this.WEEKDAYS[WeekPos])) {

                            PreferredDays.SetDay(WeekPos);
                            break; // should break to the first for loop
                        }

                    } // for (int PrefPos = 0; PrefPos < this.listUserAddedPref.size(); PrefPos++)

                } // for (int Pos = 0; Pos < this.WEEKDAYS.length; Pos++)
                // ############################################################

                // read the current json file data obtaining all datacards
                listEmployeeFullPref = ObjJsonFileManager.readJson_FullPref();
                
                // create a new datacard with the user submitted info
                PreferenceFull EMPLOYEE_Input = new PreferenceFull(false, name, id, PreferredDays.GetWeekArray(), this.listUserAddedPref);
                
                System.out.println("size: " + this.listUserAddedPref.size());
                // add this datacard to the main list
                listEmployeeFullPref.add(EMPLOYEE_Input);

                LinkedList<PreferenceFull> EMPLOYEES_ToWrite;

                this.ReplaceWithNew = ObjDataCardmanager.GetSelectionState();

                if (ReplaceWithNew == false) {
                    // use the file formatter to combine any datacards with the same ID
                    EMPLOYEES_ToWrite = ObjJsonFileFormatter.JsonFileDefault_Formatting(listEmployeeFullPref);
                } else {
                    // use the file formatter to replace any datacards that match the one stored at a specific position, in this case, the last one
                    EMPLOYEES_ToWrite = ObjJsonFileFormatter.JsonFileDefault_ReplaceWithInput(listEmployeeFullPref, (listEmployeeFullPref.size() - 1));
                }

                this.ReplaceWithNew = false;

                // write this updated list back to the file
                ObjJsonFileManager.writeJson_FullPref(EMPLOYEES_ToWrite);

                // update the datacard manager with the new info
                ObjDataCardmanager.UpdateListAndComboBox();

                // System Message
                // ############################################################
                // 5 - SUCCESS | 17 - SCENE_CREATE_DataCard | 14 - USER-SubmittingInfo | 20 - Data has been submitted
                SystemInfoManager.GET_SYSTEM_MESSAGE(5,17,14,20);
                // ############################################################

            } // if/else ()
            
        };
        // ############################################################



        // Resets all input time preferences
        // ############################################################
        this.EVENT_RESETTIMEPREF = event -> {

            listUserAddedPref.clear();
            /**
             * remove nodes from the flowpane
             */
            for (Node node: uiOutputPref.getChildren()) {
                if (node instanceof VBox vbox) {
                    vbox.getChildren().clear();
                }

            } // for()

            uiOutputPref.getChildren().clear();
            
            // System Message
            // ############################################################
            // 5 - SUCCESS | 17 - SCENE_CREATE_DataCard | 15 - USER-ResettingInfo | 22 - Time preference reset
            SystemInfoManager.GET_SYSTEM_MESSAGE(5,17,15,22);
            // ############################################################
        };
        // ############################################################



    } // DatacardEventHandler()



    /**
     * UI_ButtonCreation()
     * Description: Create various Button interfaces
     */
    private void Create_Buttons() {

        // Return Home Button
        // ############################################################
        this.buttonReturn = new Button("Return Home");
        this.buttonReturn.addEventHandler(ActionEvent.ACTION, this.EVENT_RETURNHOME);
        this.buttonReturn.addEventHandler(ActionEvent.ACTION, this.EVENT_RESETTIMEPREF);
        this.buttonReturn.setPrefSize(this.WIDTH_THREE, this.HEIGHT_THREE);
        // ############################################################

        // Add Info Button
        // ############################################################
        this.buttonAddPref = new Button("Add Info");
        this.buttonAddPref.setOnAction(this.EVENT_ADDINFO);
        this.buttonAddPref.setPrefSize(this.WIDTH_THREE, this.HEIGHT_THREE);
        // ############################################################

        // Submit UserInfo
        // ############################################################
        this.buttonSubmitPref = new Button("Submit Info");
        this.buttonSubmitPref.setOnAction(this.EVENT_SUBMITINFO);
        this.buttonSubmitPref.setPrefSize(this.WIDTH_THREE, this.HEIGHT_THREE);
        // ############################################################

        // reset usertimeinput
        // ############################################################
        this.buttonResetPref = new Button("Reset added time preferences");
        this.buttonResetPref.setOnAction(this.EVENT_RESETTIMEPREF);
        this.buttonResetPref.setPrefSize(this.WIDTH_THREE, this.HEIGHT_THREE);
        // ############################################################

    } // ButtonCreation()



    private void CreateUIComponents() {

        // Object responsible for creating the Time input UI
        // Must be called after buttons are initialized otherwise the constructor will fail
        this.ObjTimeInputManager = new ManageDayTime(this.buttonAddPref, true, WIDTH_THREE, HEIGHT_THREE);
        this.uiInputTime = this.ObjTimeInputManager.Return_UI_TimeInput();

        // create labels
        // ############################################################
        // Label - Name Prompt
        this.labelName = new Label(this.TEXT_ONE);
        this.labelName.getStyleClass().add(this.STYLE_THREE);
        // Label - ID Prompt
        this.labelIdent = new Label(TEXT_THREE);
        this.labelIdent.getStyleClass().add(STYLE_THREE);
        // ############################################################

        // TextFields
        // ############################################################
        // textfieldIdent
        double InputIdentWidth      = SystemInfoManager.GET_SYSTEM_VALUE_DOUBLE(global.UISPACING.WIDTH, 0);   // 0 - 50
        double InputIdentHeight     = SystemInfoManager.GET_SYSTEM_VALUE_DOUBLE(global.UISPACING.HEIGHT, 1);  // 1 - 25
        this.textfieldIdentCreator  = new InputTextField(global.TextFieldState.NUMERIC, TEXT_THREE, InputIdentWidth, InputIdentHeight);
        this.textfieldIdent         = textfieldIdentCreator.Return_Field_constructed();
        // textfieldName
        double InputNameWidth       = SystemInfoManager.GET_SYSTEM_VALUE_DOUBLE(global.UISPACING.WIDTH, 0);   // 0 - 50
        double InputNameHeight      = SystemInfoManager.GET_SYSTEM_VALUE_DOUBLE(global.UISPACING.HEIGHT, 1);  // 1 - 25
        this.textfieldNameCreator   = new InputTextField(global.TextFieldState.TEXT, TEXT_TWO, InputNameWidth, InputNameHeight);
        this.textfieldName          = textfieldNameCreator.Return_Field_constructed();
        // ############################################################

        // Holder objects
        // ############################################################
        // uiInputAll - contains all UI Nodes
        this.uiInputAll = new VBox();
        this.uiInputAll.setSpacing(this.SPACING_ONE);
        this.uiInputAll.setPrefSize(this.WIDTH_ONE, this.HEIGHT_ONE);
        this.uiInputAll.setPadding(new Insets(this.PADDING_ONE));
        this.uiInputAll.getStyleClass().add(STYLE_ONE);

        // uiOutputPref - contains all interface output nodes
        this.uiOutputPref = new FlowPane();
        this.uiOutputPref.setHgap(this.SPACING_TWO);
        this.uiOutputPref.setVgap(this.SPACING_TWO);
        this.uiOutputPref.setPrefSize(this.WIDTH_TWO, this.HEIGHT_TWO);
        this.uiOutputPref.setPadding(new Insets(this.PADDING_TWO));
        this.uiOutputPref.getStyleClass().add(STYLE_TWO);
        // ############################################################


    }

    /**
     * SceneEffects()
     * Description: graphical manipulations and effects
     */
    private void Create_SceneEffects() {

        // Add background
        // ############################################################
        LinearGradient BackgroundGradient = new LinearGradient(0, 0, 300, 300, false, CycleMethod.NO_CYCLE, 
            new Stop(0, Color.DARKBLUE), new Stop(1, Color.BEIGE)
        );

        this.backgroundFill = new BackgroundFill(BackgroundGradient, CornerRadii.EMPTY, Insets.EMPTY);
        this.rootnodeDatacard.setBackground(new Background(backgroundFill));
        // ############################################################



        // Fade transition effect
        // ############################################################
        this.effectFade = new ParallelTransition();

        for (Node node : this.rootnodeDatacard.getChildren()) {
            
            FadeTransition NodeFade = new FadeTransition(
                Duration.seconds(2),
                node
            );

            NodeFade.setToValue(0);
            effectFade.getChildren().addAll(NodeFade);
        }
        // ############################################################



        // Unfade transition effect
        // ############################################################
        this.effectUnfade = new ParallelTransition();

        for (Node node : this.rootnodeDatacard.getChildren()) {
            
            FadeTransition NodeUnFade = new FadeTransition(
                Duration.seconds(2),
                node
            );

            NodeUnFade.setToValue(1);
            effectUnfade.getChildren().addAll(NodeUnFade);
        }
        // ############################################################

    } // SceneEffects



} // PROG_UI_B_DataCardinfoScene ()