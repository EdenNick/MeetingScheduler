package meeting_scheduler.PresentationLayer;

import java.io.File;

import javafx.application.Application;

/**
 * Project Name: Project Meeting Scheduler 
 * Created By: Nicholas Edenfield
 * 
 * 
 * MeetingScheduler.java
 * 
 * Description: main file
 */

public class MeetingScheduler {

    /**
     * static main
     */
    public static void main(String[] args) throws Exception {

        // Program start message
        System.out.println("Hello, World!");
        System.out.println("Program Start");
        

        boolean test = false;

        // Test for program internal logic
        if (test == true) {

            //meeting_scheduler.PresentationLayer.PROG_TEST_FullTest FULL_TEST = new meeting_scheduler.PresentationLayer.PROG_TEST_FullTest();

            //FULL_TEST.FullTest();

            File file = new File("src\\main\\resources\\PROG_DATA_A_JsonTestFile.json");

            System.out.println(file.getAbsolutePath());
            System.out.println(file.getParentFile().exists());

        // System start
        } else {

            // launch application window
            Application.launch (PROG_UI_A_Application.class, args);

        }

        // Program end message
        System.out.println("Program End");
        System.out.println("Goodbye World");

    } // main(String[] args)



} // MeetingScheduler