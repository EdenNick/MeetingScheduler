package meeting_scheduler.PresentationLayer;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;



public class PROG_UI_B_MainMenuScene {

    // Reference of the application stage used for local operations
    private final Stage ApplicationStage;

    // Buttons
    private Button EndProgramButton;
    private Button SchedulePageButton;
    private Button DatacardPageButton;
    private Button InstructionButton;

    // Root Node
    private AnchorPane  RootNode;

    // Format Nodes
    private VBox   menuButtonHolderNode;

    // Scene
    private Scene  menuScene;

    // Stage width/height
    private double StageWidth;
    private double stageHeight;

    // transitions
    private ParallelTransition fadeMenuNodes;
    private ParallelTransition UnfadeMenuNodes;





    /**
     * Constructor class
     */
    public PROG_UI_B_MainMenuScene(Stage stage) {
        this.ApplicationStage = stage;
    }


    
    /**
     * ChangeToMainMenu()
     * Description: Public method meant to be called outside the class in order to set the scene to the main menu scene
     */
    public void ChangeToMainMenu() {

        // Scene linear transition
        // TranslateTransition SceneTransition = new TranslateTransition(
        //     Duration.seconds(10),
        //     RootNode
        // );

        // SceneTransition.setFromX(0);
        // SceneTransition.setToX(500);
        // SceneTransition.setCycleCount(1);
        // SceneTransition.setAutoReverse(false);
        // SceneTransition.play();

        // Scen angle transition
        // RotateTransition SceneTransition = new RotateTransition(
        //     Duration.seconds(10),
        //     RootNode
        // );

        // SceneTransition.setByAngle(30);
        // SceneTransition.setCycleCount(1);
        // SceneTransition.setInterpolator(Interpolator.LINEAR)
        // SceneTransition.play();
        
        
        
        // unfades nodes
        UnfadeMenuNodes.play();
        
        // Gets the current size of the stage
        this.StageWidth   = this.ApplicationStage.getWidth();
        this.stageHeight  = this.ApplicationStage.getHeight();

        // Sets the stage to the main menu scene
        this.ApplicationStage.setScene(this.menuScene);

        // sets the correct size for the stage
        this.ApplicationStage.setWidth(StageWidth);
        this.ApplicationStage.setHeight(stageHeight);

        // Shows the change
        this.ApplicationStage.show();

    }



    /**
     * ConstructMainMenuStage()
     * Description: Performs the necessary operations in order to build the various nodes/components of the stage.
     */
    public void ConstructMainMenuScene() {

        /**
         * Buttons
         */

        // End program button
        EndProgramButton = new Button("End program");

        EventHandler<ActionEvent> closeProgram = (ActionEvent e) -> {
            
            // Exits the program
            System.out.println("BUTTON CLICK    - MAIN MENU         - Program Ending");
            

            fadeMenuNodes.setOnFinished(event -> {
                Platform.exit();
            });

            fadeMenuNodes.play();

            // Platform.exit();

        };

        EndProgramButton.setOnAction(closeProgram);
        // ############################################################



        // Data card page button
        DatacardPageButton = new Button("Manage Data Cards");

        EventHandler<ActionEvent> DatacardScenechange = (ActionEvent e) -> {
            
            // changes scene to data card management
            System.out.println("BUTTON CLICK    - MAIN MENU         - Scene changing to datacard management");

            fadeMenuNodes.setOnFinished(event -> {
                PROG_UI_A_Application.SceneManager.DataCardManage();
            });

            fadeMenuNodes.play();

            // PROG_UI_A_Application.SceneManager.DataCardManage();

        };

        DatacardPageButton.setOnAction(DatacardScenechange);
        // ############################################################
        


        // Schedule page button
        SchedulePageButton = new Button("Schedule");

        EventHandler<ActionEvent> ScheduleSceneChange = (ActionEvent e) -> {
            
            // Chnages the scene to schedule managment
            System.out.println("BUTTON CLICK    - MAIN MENU         - Scene changing to schedule management");

            fadeMenuNodes.setOnFinished(event -> {
                PROG_UI_A_Application.SceneManager.Schedule();
            });

            fadeMenuNodes.play();

            // PROG_UI_A_Application.SceneManager.Schedule();
        };  

        SchedulePageButton.setOnAction(ScheduleSceneChange);
        // ############################################################


        // Schedule page button
        InstructionButton = new Button("Instructions");

        EventHandler<ActionEvent> InstructionSceneChange = (ActionEvent e) -> {
            
            // Chnages the scene to schedule managment
            System.out.println("BUTTON CLICK    - MAIN MENU         - Scene changing to Instructions");

            fadeMenuNodes.setOnFinished(event -> {
                PROG_UI_A_Application.SceneManager.Instructions();
            });

            fadeMenuNodes.play();

            // PROG_UI_A_Application.SceneManager.Instructions();
        };  

        InstructionButton.setOnAction(InstructionSceneChange);
        // ############################################################





        /**
         * Node management
         */

        // Root Node
        RootNode = new AnchorPane();

        // menu button holder node
        menuButtonHolderNode = new VBox(20);
        
        // menu button holder node - set menu button positions
        menuButtonHolderNode.setAlignment(Pos.CENTER);

        menuButtonHolderNode.getChildren().addAll(DatacardPageButton, SchedulePageButton, InstructionButton);
        

        // Root Node - set end program button position
        AnchorPane.setBottomAnchor(EndProgramButton, 20.0);
        AnchorPane.setRightAnchor(EndProgramButton, 20.0);
        
        // Root Node - set Menu buttons position
        AnchorPane.setTopAnchor(menuButtonHolderNode, 30.0);
        AnchorPane.setLeftAnchor(menuButtonHolderNode, 30.0);
        // ############################################################



        /**
         * Scene edits
         */

        // Add background gradient
        LinearGradient BackgroundGradient = new LinearGradient(0, 0, 300, 300, false, CycleMethod.NO_CYCLE, 
            new Stop(0, Color.DARKBLUE), new Stop(1, Color.BEIGE)
        );

        BackgroundFill backgroundFill = new BackgroundFill(BackgroundGradient, CornerRadii.EMPTY, Insets.EMPTY);

        RootNode.setBackground(new Background(backgroundFill));

        // "earth"
        Circle circleDecoration = new Circle(1500);

        circleDecoration.setFill(Color.BLUE);

        // Root Node - set circle position
        AnchorPane.setTopAnchor(circleDecoration, 30.0);
        AnchorPane.setLeftAnchor(circleDecoration, 30.0);

        /**
         * Root Node Add
         */
        // Root Node - add buttons to the node
        RootNode.getChildren().addAll(circleDecoration, menuButtonHolderNode, EndProgramButton);

        // Transition to fade buttons
        fadeMenuNodes = new ParallelTransition();

        for (Node node : RootNode.getChildren()) {
            
            FadeTransition NodeFade = new FadeTransition(
                Duration.seconds(2),
                node
            );

            NodeFade.setToValue(0);
            
            fadeMenuNodes.getChildren().addAll(NodeFade);
        }
        // ############################################################



        // Transition to Unfade buttons
        UnfadeMenuNodes = new ParallelTransition();

        for (Node node : RootNode.getChildren()) {
            
            FadeTransition NodeUnFade = new FadeTransition(
                Duration.seconds(2),
                node
            );

            NodeUnFade.setToValue(1);
            
            UnfadeMenuNodes.getChildren().addAll(NodeUnFade);
        }
        // ############################################################



        // create menu scene with the current node layout
        this.menuScene = new Scene(RootNode, PROG_UI_A_SceneManager.WindowWidth, PROG_UI_A_SceneManager.WindowHeight);

        // fade all objects before the scene is set
        fadeMenuNodes.play();

    }



} // PROG_UI_A_MainMenuScene
