// Package  - DO Not Change
// ############################################################
package meeting_scheduler.SceneManagement;
// ############################################################

import java.io.IOException;
import java.util.LinkedList;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import meeting_scheduler.DataHolder.PreferenceFull;
import meeting_scheduler.FileManagement.ManageFileLock;
import meeting_scheduler.FileManagement.ManageJsonFile;
import meeting_scheduler.ScheduleManagement.DataCompleteSchedule;
import meeting_scheduler.SystemInfoManager;
import meeting_scheduler.global;
// Imports
// ############################################################

// ############################################################



public class ManageScheduleOutput {
    
    // Vbox containing scheduling outputs
    private VBox VboxSchedulerOutput;

    // UI Styling variables
    private final double SPACING;

    // ObservableList of all calculated schedules
    private ObservableList<DataCompleteSchedule> SCHEDULES;

    // tracks the current number of schedules displayed in VboxSchedulerOutput
    private int ScheduleNumber;

    // Json file manager for accessing employee data
    private ManageJsonFile Scheduler_fileReader;
    // LinkedList used to hold info gianed from the json file manager
    private LinkedList<PreferenceFull>  FilePeople;

    private double Padding;
    private String StyleSix;

    private final String AM;
    private final String PM;





    /**
     * Constructor
     */
    public ManageScheduleOutput(VBox INPUT_SCHEDULEROUTPUT, double INPUT_SPACING, ObservableList<DataCompleteSchedule> INPUT_SCHEDULES) {
        // pass by reference so changes in instances of this object carry over to the corresponding scheduler scene instance
        this.VboxSchedulerOutput = INPUT_SCHEDULEROUTPUT;
        this.SPACING = INPUT_SPACING;
        this.SCHEDULES = INPUT_SCHEDULES;
        this.Scheduler_fileReader = new ManageJsonFile(ManageFileLock.GetFile_DATACARD());

        this.AM = SystemInfoManager.GET_SYSTEM_VALUE_STRING(global.BASICVALUESSTRING.AMPM, 0); // 0 - AM
        this.PM = SystemInfoManager.GET_SYSTEM_VALUE_STRING(global.BASICVALUESSTRING.AMPM, 0); // 1 - PM

        this.Padding    = SystemInfoManager.GET_SYSTEM_VALUE_DOUBLE(global.UISPACING.PADDING, 1); // 1 - 10
        this.StyleSix   = SystemInfoManager.GET_SYSTEM_VALUE_STRING(global.BASICVALUESSTRING.STRING, 31); // 31 - Schedule_UI_ScheduleListBox

    } // ManageScheduleOutput()

    public int UpdateOutput(int INPUT_SCHEDULENUMBER) {

        this.ScheduleNumber = INPUT_SCHEDULENUMBER;
        ConstructOutput();
        return this.ScheduleNumber;
    }


    private void ConstructOutput() {

        // Node Creation
        // ############################################################
        // primary Box conatining schedule Info and user list
        VBox        ScheduleBox_Primary     = new VBox(this.SPACING);
        // secondary box containing only the schedule info
        HBox        ScheduleBox_Secondary   = new HBox(this.SPACING);
        // List of people in schedule
        FlowPane    SchedulePeopleList      = new FlowPane();
        // deletion Button
        Button      DeleteList              = new Button("X");
        // DeleteList.setId(Integer.toString(ScheduleNumber)); // sets Id to the list number
        // ############################################################


        //int PersonAmmount = Schedules.getLast().USERIDs.size();

        this.FilePeople = Scheduler_fileReader.readJson_FullPref();

        // System Message
        // 5 - SUCCESS | 19 - SCENE_CREATE_MainMenu | 10 - SYSTEM-FileAccess | 4 - try/catch File access Successful
        SystemInfoManager.GET_SYSTEM_MESSAGE(5,19,10,4);

        // used in placement position of the schedule in the list
        int FlowPanePeopleAmmount = 0;

        for (String ScheduleID : this.SCHEDULES.getLast().USERIDs) {

            for (int FileIndex = 0; FileIndex < FilePeople.size(); FileIndex++) {

                if (ScheduleID.equals(Integer.toString(FilePeople.get(FileIndex).GetIdent()))) {

                    // add name to the flowpane list
                    String SchedulePersontext = String.format("| ID: %-9s  Name: %-20s |", ScheduleID, FilePeople.get(FileIndex).GetName());

                    Label SchedulePerson = new Label(SchedulePersontext);

                    SchedulePeopleList.getChildren().add(
                        SchedulePerson
                    );

                    FlowPanePeopleAmmount++;

                    // breaks out of for loop
                    break;

                } // if ()

            } // for (int FileIndex = 0; FileIndex < FilePeople.size(); FileIndex++)

        } // for (String ScheduleID : Schedules.getLast().USERIDs)

        // ############################################################


        Label FullList = new Label();
        Label ScheduleDay = new Label();
        Label ScheduletimeFrame = new Label();
        // labels
        // ############################################################
        LabelCreation(FullList, ScheduleDay, ScheduletimeFrame);
        // ############################################################


        // Styling
        // ############################################################
        // VBox
        ScheduleBox_Primary     .setPadding(new Insets(this.Padding));
        ScheduleBox_Primary     .setPrefSize(800,     120);
        ScheduleBox_Primary     .getStyleClass().add(this.StyleSix);

        // HBox
        ScheduleBox_Secondary   .setPrefSize(800,   60);
        ScheduleBox_Secondary   .setPadding(new Insets(this.Padding));
        ScheduleBox_Secondary   .getStyleClass().add(this.StyleSix);
        ScheduleBox_Secondary   .setAlignment(Pos.CENTER_LEFT);

        // flowPane
        SchedulePeopleList      .setPrefSize(90,   60);

        // labels
        FullList                .setPrefSize(100,   120);
        ScheduleDay             .setPrefSize(100,   100);
        ScheduletimeFrame       .setPrefSize(200,   200);

        // button
        DeleteList              .setMaxSize(50, 50);
        // ############################################################



        // schedule logic implementation


        DeleteList.setOnAction(event -> {
            // clear all info in the node
            ScheduleBox_Primary.getChildren().clear();
            // remove node from the list
            this.VboxSchedulerOutput.getChildren().remove(ScheduleBox_Primary);

        });

        // Containes general info like date and time
        ScheduleBox_Secondary.getChildren().addAll(
            
            DeleteList,
            FullList,
            ScheduleDay,
            ScheduletimeFrame

        );

        // VBox containing all relevant info fro a single calculated schedule
        ScheduleBox_Primary.getChildren().addAll(
            ScheduleBox_Secondary,
            SchedulePeopleList
        );




        // put in order based off of ammount of people in each schedule list

        if (this.VboxSchedulerOutput.getChildren().size() == 0) {

            this.VboxSchedulerOutput.getChildren().addAll(

                ScheduleBox_Primary

            );

        } else {

            int VBoxIndex = 0;

            boolean placed = false;

            while (VBoxIndex < this.VboxSchedulerOutput.getChildren().size()) {

                VBox vbox = (VBox) this.VboxSchedulerOutput.getChildren().get(0);
                FlowPane PeopleList = (FlowPane) vbox.getChildren().get(1);

                int NumberOfPeople = PeopleList.getChildren().size();

                if (FlowPanePeopleAmmount >= NumberOfPeople) {

                    this.VboxSchedulerOutput.getChildren().add(VBoxIndex, ScheduleBox_Primary);
                    placed = true;
                    break;

                }

                VBoxIndex++;

            } // (VBoxIndex < this.UIOutput_FullUI_VBOX.getChildren().size())

            // if the while loop can not place the schedule it is automatically placed at the end
            // ie every other list contained a greater number of people
            if (placed == false) {
                this.VboxSchedulerOutput.getChildren().add(ScheduleBox_Primary);
            }


        } // if else()






        // increment schedule number ammount
        this.ScheduleNumber++;
    } // ConstructOutput()



    /**
     * LabelCreation()
     * Description: creates the various labels for 
     */
    private void LabelCreation(Label INPUT_FullList, Label INPUT_ScheduleDay, Label INPUT_ScheduletimeFrame) {
        // schedule creation functions
        // ############################################################
        String StartingAMPM = this.AM;
        String EndingAMPM   = this.AM;

        // Time frame of the schedule
        int StartHour       = this.SCHEDULES.getLast().Interval.GetStartTimeHour();
        String startMinute  = Integer.toString(this.SCHEDULES.getLast().Interval.GetStartTimeMin());

        int Endhour         = this.SCHEDULES.getLast().Interval.GetEndTimeHour();
        String EndMinute    = Integer.toString(this.SCHEDULES.getLast().Interval.GetEndTimeMin());

        // Convert to PM if necessary
        if (StartHour >= 12) {
            StartingAMPM = this.PM;
        }

        if (StartHour > 12) {
            StartHour -= 12;
        }

        // Convert to PM if necessary
        if (Endhour >= 12) {
            EndingAMPM = this.PM;
        }
        if (Endhour > 12) {
            Endhour -= 12;
        }

        // Add a leading zero to the start of the minute inputs if it is less than 10
        if (Integer.parseInt(startMinute)  < 10) {
            startMinute = "0" + startMinute;
        }

        if (Integer.parseInt(EndMinute)    < 10) {
            EndMinute   = "0" + EndMinute;
        }






        boolean IsFullList = this.SCHEDULES.getLast().Schedule;
        String FullListText = String.format("| Full List: %-5s |", IsFullList);

        String DayToSchedule = this.SCHEDULES.getLast().WeekDay;
        String ScheduleDayText = String.format("| Day: %-3s |", DayToSchedule);

        String TimeStart        = String.format(StartHour + ":" + startMinute + " " + StartingAMPM);
        String TimeEnd          = String.format(Endhour + ":" + EndMinute + " " + EndingAMPM);
        String ScheduleTimeText = String.format("| Time Frame: %-5s - %-5s |", TimeStart, TimeEnd);


        INPUT_FullList.setText(FullListText);
        INPUT_ScheduleDay.setText(ScheduleDayText);
        INPUT_ScheduletimeFrame.setText(ScheduleTimeText);

    }
    

} // ManageScheduleOutput{}
