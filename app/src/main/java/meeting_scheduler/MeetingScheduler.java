// Package  - DO Not Change
// ############################################################
package meeting_scheduler;
// ############################################################

// Imports
// ############################################################
import javafx.application.Application;
import meeting_scheduler.SystemManagement.ManageAppWindow;
// ############################################################



public class MeetingScheduler {

    /**
     * static main
     */
    public static void main(String[] args) throws Exception {

        // Program start message
        System.out.println("Hello, World!");
        
        // Initialize global values
        SystemInfoManager.Inititlaize_Global_States();

        // System Message
        // 1 - START | 1 - MeetingScheduler | 1 - SYSTEM-START | 1 - System Startup successful
        SystemInfoManager.GET_SYSTEM_MESSAGE(1,1,1,1);

        // System Message
        // 1 - START | 1 - MeetingScheduler | 3 - SYSTEM-CreateGlobalValues | 3 - System global values created and ready for use
        SystemInfoManager.GET_SYSTEM_MESSAGE(1,1,3,3);



        // launch application window
        Application.launch (ManageAppWindow.class, args);



        // System Message
        // 1 - START | 1 - MeetingScheduler | 2 - SYSTEM-END | 2 - System ENDING successful
        SystemInfoManager.GET_SYSTEM_MESSAGE(1,1,2,2);

        // Program end message;
        System.out.println("Goodbye World");

    } // main(String[] args)

} // MeetingScheduler