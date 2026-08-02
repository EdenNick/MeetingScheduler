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

    /**
     * 1. Create Application
     */
    @Override
    public void start(Stage stage) {


        // testing parameters
        // boolean test = true;

        //if (test == true) {

            // Test labels
            Label HeightWidthLabel = new Label();

            stage.widthProperty().addListener((obs, oldWidth, newWidth) -> {
                RetrieveWindowSize(HeightWidthLabel, stage);
            });

            stage.heightProperty().addListener((obs, oldWidth, newWidth) -> {
                RetrieveWindowSize(HeightWidthLabel, stage);
            });


        //}
        /**
         * 2. Root Node - required to hold all other nodes as well as start
         */
        Pane RootNode = new Pane(HeightWidthLabel);

        /**
         * 3. other Nodes
         */

        /**
         * 4. Scene
         */
        Scene PrimaryScene = new Scene(RootNode, 300, 300);

        /**
         * 5. Stage
         */
        stage.setTitle("Application - Test");

        stage.setScene(PrimaryScene);
        
        stage.show();

        // if (test == true) {
            RetrieveWindowSize(HeightWidthLabel, stage);
        // }
    }



    private void RetrieveWindowSize(Label LABEL, Stage STAGE) {
        LABEL.setText("Width: " + (int) STAGE.getWidth() + "\n" + "Height: " + (int) STAGE.getHeight());
    }
    
}
