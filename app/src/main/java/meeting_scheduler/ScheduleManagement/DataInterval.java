// Package  - DO Not Change
// ############################################################
package meeting_scheduler.ScheduleManagement;
// ############################################################

// Imports
// ############################################################
import meeting_scheduler.DataHolder.PreferenceFull;
// ############################################################



public class DataInterval {

    // Constructor Fields
    // ############################################################
    private final PreferenceFull    PERSON;
    private final int               INTERVAL;
    // ############################################################

    // Constructor
    // ############################################################
    public DataInterval(PreferenceFull person, int interval) {
        this.PERSON     = new PreferenceFull(person);
        this.INTERVAL   = interval;
    }
    // ############################################################
    

    // Getter Methods
    // ############################################################
    public PreferenceFull getPerson() {
        return this.PERSON;
    }

    public int getInterval() {
        return this.INTERVAL;
    }
    // ############################################################



} // DataInterval{}
