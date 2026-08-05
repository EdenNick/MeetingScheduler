package meeting_scheduler.PresentationLayer;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * PROG_UI_B_DataCardinfoScene()
 * 
 * Description: Application window scene which allows the user to add user datacards to the application
 * Contains:
 * a. back button       (scene change)
 * b. UI interface allowing user to add info    (various buttons, text inputs etc)
 */




public class PROG_UI_C_DataCardinfoScene {

    // Reference of the application stage used for local operations
    private final Stage ApplicationStage;

    // Scene
    private Scene       DataCardScene;

    // Stage width/height
    private double      StageWidth;
    private double      stageHeight;

    // Root Node
    private AnchorPane  RootNode;

    // Vbox
    private VBox        addTimeInfo_input;
    private VBox        UserInterface_Input;

    // Hbox
    private HBox        AddStartTime;
    private HBox        addEndingTime;

    // Buttons
    private Button      ReturnToMenu;
    private Button      AddUserInfo;
    private Button      SubmitUserInfo;

    // Labels
    private Label       nameLabel;
    private Label       IDLabel;
    private Label       DayLabel;
    private Label       BeginHourLabel;
    private Label       BeginMinuteLabel;
    private Label       EndHourLabel;
    private Label       EndMinuteLabel;
    private Label       BeginningTime;
    private Label       EndingTime;

    // TextFields
    private TextField   userInput_EndingMinuteSeleciton;
    private TextField   userInput_EndingHourSeleciton;
    private TextField   userInput_BeginningMinuteSeleciton;
    private TextField   userInput_BeginningHourSelection;
    private TextField   userInput_EmployeeID;
    private TextField   userInput_EmployeeName;

    // Background
    private BackgroundFill      backgroundFill;
    
    // transitions
    private ParallelTransition  fadeMenuNodes;
    private ParallelTransition  UnfadeMenuNodes;

    // ComboBox
    private ComboBox<String>    userInput_WeekDaySelection;
    

    
    /**
     * Constructor class
     */
    public PROG_UI_C_DataCardinfoScene(Stage stage) {
        this.ApplicationStage = stage;
    }


    
    /**
     * changetoDataCardScene()
     * Description: Public method meant to be called outside the class in order to set the scene to the data card management scene
     */
    public void changetoDataCardScene() {

        // unfades nodes
        UnfadeMenuNodes.play();

        // Gets the current size of the stage
        this.StageWidth   = this.ApplicationStage.getWidth();
        this.stageHeight  = this.ApplicationStage.getHeight();

        // Sets the stage to the main menu scene
        this.ApplicationStage.setScene(this.DataCardScene);

        // sets the correct size for the stage
        this.ApplicationStage.setWidth(StageWidth);
        this.ApplicationStage.setHeight(stageHeight);

        // Shows the change
        this.ApplicationStage.show();
        
    }



    /**
     * ConstructCardManagerScene()
     * Description: Performs the necessary operations in order to build the various nodes/components of the stage.
     */
    public void ConstructCardManagerScene() {


        // Creates Buttons for this scene
        ButtonCreation();


        // Creates label for this scene
        UI_LabelCreation();


        // Creates UI for user inputs
        UI_UserInputs();



        /**
         * Node Management
         */

        // HBox for beginning Hour/Min
        // ############################################################
        this.AddStartTime = new HBox(10);
        //AddStartTime.setPrefSize(200.0, 400.0);
        this.AddStartTime.setPadding(new Insets(10));
        this.AddStartTime.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: Black;" +
            "-fx-border-width: 2;" +
            "-fx-border-radius: 5;"
        );

        this.AddStartTime.getChildren().addAll(

            BeginHourLabel,
            userInput_BeginningHourSelection,

            BeginMinuteLabel,
            userInput_BeginningMinuteSeleciton

        );
        // ############################################################



        // HBox for Ending Hour/Min
        // ############################################################
        this.addEndingTime = new HBox(10);
        //AddStartTime.setPrefSize(200.0, 400.0);
        this.addEndingTime.setPadding(new Insets(10));
        this.addEndingTime.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: Black;" +
            "-fx-border-width: 2;" +
            "-fx-border-radius: 5;"
        );

        this.addEndingTime.getChildren().addAll(

            EndHourLabel,
            userInput_EndingHourSeleciton,

            EndMinuteLabel,
            userInput_EndingMinuteSeleciton

        );
        // ############################################################



        // Vbox for adding time intervals
        // ############################################################

        this.addTimeInfo_input = new VBox(10);
        this.addTimeInfo_input.setPrefSize(200.0, 400.0);
        this.addTimeInfo_input.setPadding(new Insets(10));
        this.addTimeInfo_input.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: Black;" +
            "-fx-border-width: 2;" +
            "-fx-border-radius: 5;"
        );

        Region ButtonSpace = new Region();
        VBox.setVgrow(ButtonSpace, Priority.ALWAYS);

        this.addTimeInfo_input.getChildren().addAll(

            DayLabel,
            userInput_WeekDaySelection,
            
            BeginningTime,
            AddStartTime,

            EndingTime,
            addEndingTime,

            ButtonSpace,

            AddUserInfo

        );
        // ############################################################



        // Vbox for UI
        // ############################################################
        // Create and set box parameters
        this.UserInterface_Input = new VBox(10);
        this.UserInterface_Input.setPrefSize(300.0, 600.0);
        this.UserInterface_Input.setPadding(new Insets(10));
        this.UserInterface_Input.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: Black;" +
            "-fx-border-width: 2;" +
            "-fx-border-radius: 5;"
        );

        // add nodes to the box
        this.UserInterface_Input.getChildren().addAll(
            nameLabel,
            userInput_EmployeeName, 

            IDLabel,
            userInput_EmployeeID,

            addTimeInfo_input
        );
        // ############################################################



        // Root Node
        // ############################################################
        this.RootNode = new AnchorPane();

        // Root Node - set return home button position
        AnchorPane.setBottomAnchor(ReturnToMenu, 20.0);
        AnchorPane.setRightAnchor(ReturnToMenu, 20.0);
        
        // root Node - set UI interface input
        AnchorPane.setTopAnchor(UserInterface_Input, 20.0);
        AnchorPane.setLeftAnchor(UserInterface_Input, 20.0);
        
        this.RootNode.getChildren().addAll(UserInterface_Input, ReturnToMenu);
        // ############################################################

        
        // Set Graphical Effects
        SceneEffects();


        // create menu scene with the current node layout
        this.DataCardScene = new Scene(RootNode, PROG_UI_A_SceneManager.WindowWidth, PROG_UI_A_SceneManager.WindowHeight);

        // fade all objects before the scene is set
        this.fadeMenuNodes.play();
        
    }





    /**
     * UI_ButtonCreation()
     * Description: Create various Button interfaces
     */
    private void ButtonCreation() {

        // Return Home Button
        // ############################################################
        this.ReturnToMenu = new Button("Return Home");

        EventHandler<ActionEvent> ReturnHome = (ActionEvent e) -> {
            
            // Exits the program
            System.out.println("BUTTON CLICK    - CARD MANAGER PAGE - Returning to home page");
            
            fadeMenuNodes.setOnFinished(event -> {
                PROG_UI_A_Application.SceneManager.MainMenu();
            });

            fadeMenuNodes.play();

        };

        this.ReturnToMenu.setOnAction(ReturnHome);
        // ############################################################



        // Add Info Button
        // ############################################################
        this.AddUserInfo = new Button("Add Info");

        EventHandler<ActionEvent> AddInfo = (ActionEvent e) -> {
            // adds user info - does not submit anything
            System.out.println("BUTTON CLICK    - CARD MANAGER PAGE - Add User Info");
        };

        this.AddUserInfo.setOnAction(AddInfo);
        // ############################################################



        // Submit UserInfo
        // ############################################################
        this.SubmitUserInfo = new Button("Submit Info");

        EventHandler<ActionEvent> SubmitInfo = (ActionEvent e) -> {
            // Submits USer info
            System.out.println("BUTTON CLICK    - CARD MANAGER PAGE - Submit User Info");
        };

        this.SubmitUserInfo.setOnAction(SubmitInfo);
        // ############################################################

    } // ButtonCreation()



    /**
     * UI_LabelCreation()
     * Description: Creates various text labels for the UI
     */
    private void UI_LabelCreation() {

        // Name label
        this.nameLabel = new Label("Enter Name Here:");
        this.nameLabel.setStyle(
            "-fx-font-size: 12px;" +
            "-fx-font-weight: bold;"
        );


        // ID Label
        this.IDLabel = new Label("Enter ID Here:");
        this.IDLabel.setStyle(
            "-fx-font-size: 12px;" +
            "-fx-font-weight: bold;"
        );


        // Weekday label
        this.DayLabel = new Label("Select weekday here");
        this.DayLabel.setStyle(
            "-fx-font-size: 12px;" +
            "-fx-font-weight: bold;"
        );


        // Beginning Hour Label
        this.BeginHourLabel = new Label("Hour");
        this.BeginHourLabel.setStyle(
            "-fx-font-size: 12px;" +
            "-fx-font-weight: bold;"
        );


        // Beginning Minute Label
        this.BeginMinuteLabel = new Label("Min");
        this.BeginMinuteLabel.setStyle(
            "-fx-font-size: 12px;" +
            "-fx-font-weight: bold;"
        );


        // Ending hour Label
        this.EndHourLabel = new Label("Hour");
        this.EndHourLabel.setStyle(
            "-fx-font-size: 12px;" +
            "-fx-font-weight: bold;"
        );


        // Ending Minute Label
        this.EndMinuteLabel = new Label("Min");
        this.EndMinuteLabel.setStyle(
            "-fx-font-size: 12px;" +
            "-fx-font-weight: bold;"
        );


        // Beginning tim
        this.BeginningTime = new Label("Input start time:");
        this.BeginningTime.setStyle(
            "-fx-font-size: 12px;" +
            "-fx-font-weight: bold;"
        );


        // Ending time
        this.EndingTime = new Label("Input End time");
        this.EndingTime.setStyle(
            "-fx-font-size: 12px;" +
            "-fx-font-weight: bold;"
        );


    }


    
    /**
     * UI_USerInputs()
     * Description: creates various UI components where the user inputs direct info
     */
    private void UI_UserInputs() {

        /**
         * UI Interface Buttons
         */

        // Enter Name
        // ############################################################
        
        // Name Input
        this.userInput_EmployeeName = new TextField();
        this.userInput_EmployeeName.setPromptText("Enter Full Name");
        this.userInput_EmployeeName.setPrefSize(50, 25.0);
        // ############################################################



        // Enter ID
        // ############################################################
        // ID Input
        this.userInput_EmployeeID = new TextField();
        this.userInput_EmployeeID.setPromptText("Enter ID");
        this.userInput_EmployeeID.setPrefSize(50, 25.0);

        // ID restricted to int
        this.userInput_EmployeeID.setTextFormatter(new TextFormatter<>(change -> {
            
            // User input text
            String TextInput = change.getControlNewText();

            // if th etext is empty accept it
            if (TextInput.isEmpty()) {
                return change;
            }

            // test if the text is a valid int within a valid range
            try {
                int intValue = Integer.parseInt(TextInput);

                if (intValue >= 0 && intValue < 9999999) {
                    return change;
                }
            } catch (NumberFormatException e) {
                // Invalid Input
            }

            return null;

        }));
        // ############################################################



        // Weekday
        // ############################################################

        // Weekday Input
        this.userInput_WeekDaySelection = new ComboBox<>();
        this.userInput_WeekDaySelection.getItems().addAll("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat");
        this.userInput_WeekDaySelection.setPrefSize(100, 25.0);
        // ############################################################



        // Beginning Hour INput
        // ############################################################
        this.userInput_BeginningHourSelection = new TextField();
        this.userInput_BeginningHourSelection.setPrefSize(50, 25.0);
        
        this.userInput_BeginningHourSelection.setTextFormatter(new TextFormatter<>(change -> {
            
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
        // ############################################################



        // Beginning Minute Input
        // ############################################################

        this.userInput_BeginningMinuteSeleciton = new TextField();
        this.userInput_BeginningMinuteSeleciton.setPrefSize(50, 25.0);
        
        this.userInput_BeginningMinuteSeleciton.setTextFormatter(new TextFormatter<>(change -> {
            
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
        // ############################################################



        // Ending Hour Input
        // ############################################################
        

        this.userInput_EndingHourSeleciton = new TextField();
        this.userInput_EndingHourSeleciton.setPrefSize(50, 25.0);
        
        this.userInput_EndingHourSeleciton.setTextFormatter(new TextFormatter<>(change -> {
            
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
        // ############################################################



        // Ending Minute Input
        // ############################################################

        this.userInput_EndingMinuteSeleciton = new TextField();
        this.userInput_EndingMinuteSeleciton.setPrefSize(50, 25.0);
        
        this.userInput_EndingMinuteSeleciton.setTextFormatter(new TextFormatter<>(change -> {
            
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
        // ############################################################

    } // UI_UserInputs()



    /**
     * SceneEffects()
     * Description: graphical manipulations and effects
     */
    private void SceneEffects() {

        /**
         * Scene edits
         */
        // ############################################################
        // Add background gradient
        LinearGradient BackgroundGradient = new LinearGradient(0, 0, 300, 300, false, CycleMethod.NO_CYCLE, 
            new Stop(0, Color.DARKBLUE), new Stop(1, Color.BEIGE)
        );

        this.backgroundFill = new BackgroundFill(BackgroundGradient, CornerRadii.EMPTY, Insets.EMPTY);

        this.RootNode.setBackground(new Background(backgroundFill));

        // Transition to fade buttons
        this.fadeMenuNodes = new ParallelTransition();

        for (Node node : this.RootNode.getChildren()) {
            
            FadeTransition NodeFade = new FadeTransition(
                Duration.seconds(2),
                node
            );

            NodeFade.setToValue(0);
            
            fadeMenuNodes.getChildren().addAll(NodeFade);
        }
        // ############################################################



        // Transition to Unfade buttons
        this.UnfadeMenuNodes = new ParallelTransition();

        for (Node node : this.RootNode.getChildren()) {
            
            FadeTransition NodeUnFade = new FadeTransition(
                Duration.seconds(2),
                node
            );

            NodeUnFade.setToValue(1);
            
            UnfadeMenuNodes.getChildren().addAll(NodeUnFade);
        }
        // ############################################################

    }
}
