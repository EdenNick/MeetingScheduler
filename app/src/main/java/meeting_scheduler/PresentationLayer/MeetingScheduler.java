package meeting_scheduler.PresentationLayer;

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

        System.out.println("Hello, World!");
        System.out.println("Program Start");
        

        boolean test = false;

        if (test == true) {

            meeting_scheduler.PresentationLayer.PROG_TEST_FullTest FULL_TEST = new meeting_scheduler.PresentationLayer.PROG_TEST_FullTest();

            FULL_TEST.FullTest();

        } else {

            // launch application window
            Application.launch (PROG_UI_A_Application.class, args);

        }

        System.out.println("Program End");

    } // main(String[] args)



} // MeetingScheduler