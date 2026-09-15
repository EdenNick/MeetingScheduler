/**
 * PROG_UI_A_Application.java
 * 
 * Description: primary application class, handles the javafx thread creation and destruction if needed.
 */

// Package  - DO Not Change
// ############################################################
package meeting_scheduler.ApplicationManagement;
// ############################################################

// Imports
// ############################################################
// Javafx
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
// ############################################################



public class MANAGEAPP_AppWindow extends Application {

    // Local Variables
    public static MANAGEAPP_SceneManager SceneManager;



    /**
     * start()
     * Description: starts the application window
     * NOTE: do not change method name, or remove the ovverride, both are required for javafx.applicatiion to start the application window
     */
    @Override
    public void start(Stage stage) {

        // SceneManager - manages various scenes contained within the appliation
        MANAGEAPP_AppWindow.SceneManager = new MANAGEAPP_SceneManager(stage);

        // Startup - initializes construction for all scenes and sets the applicaiton window dimensions
        MANAGEAPP_AppWindow.SceneManager.StartUp();

        // Set True for testing, keep false otherwise
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
        stage.setTitle(MANAGEAPP_local.APP_Window_Title);

        // MainMenu - the application shows the main menu scene on startup
        MANAGEAPP_AppWindow.SceneManager.MainMenu();

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
