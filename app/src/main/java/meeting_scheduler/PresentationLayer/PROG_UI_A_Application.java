package meeting_scheduler.PresentationLayer;

/**
 * PROG_UI_A_Application
 * 
 * Description: primary application class, handles the javafx thread creation and destruction if needed.
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
// import javafx.scene.layout.StackPane;



public class PROG_UI_A_Application extends Application {

    // Local Variables
    public static PROG_UI_A_SceneManager SceneManager;



    /**
     * start()
     * Description: starts applicaiton
     */
    @Override
    public void start(Stage stage) {

        // Creates Object that manages various application scenes
        PROG_UI_A_Application.SceneManager = new PROG_UI_A_SceneManager(stage);

        // Initialization method used to create the various scenes
        PROG_UI_A_Application.SceneManager.StartUp();


        // testing 
        boolean test = false;

        if (test == false) {
            WindowRun(stage);
        } else {
            WindowTest(stage);
        }

    }







    /**
     * WindowRun()
     * Description: method for running the applicaiton
     * @param stage
     */
    private void WindowRun(Stage stage) {

        // Sets the name of the application window
        stage.setTitle("Application - RunTest");

        // Initializes the main menu scene on startup
        PROG_UI_A_Application.SceneManager.MainMenu();

    }







    /**
     * WindowTest()
     * Description: method to run a test for the window, showing window size
     * @param stage
     */
    private void WindowTest(Stage stage) {

        stage.setTitle("Application - StartTest");

        Label HeightWidthLabel = new Label();

        stage.widthProperty().addListener((observed, oldWidth, newWidth) -> {
            RetrieveWindowSize(HeightWidthLabel, stage);
        });

        stage.heightProperty().addListener((observed, oldHeight, newHeight) -> {
              RetrieveWindowSize(HeightWidthLabel, stage);
        });



        Pane RootNode = new Pane(HeightWidthLabel);



        Scene PrimaryScene = new Scene(RootNode, 300, 300);


        stage.setTitle("Application - Test");

        stage.setScene(PrimaryScene);
        
        stage.show();
        
        RetrieveWindowSize(HeightWidthLabel, stage);


    }






    
    /**
     * RetrieveWindowSize()
     * Description: calculates window size for testing
     * @param LABEL
     * @param STAGE
     */
    private void RetrieveWindowSize(Label LABEL, Stage STAGE) {
        LABEL.setText("Width: " + (int) STAGE.getWidth() + "\n" + "Height: " + (int) STAGE.getHeight());
    }
    


}// PROG_UI_A_Application
