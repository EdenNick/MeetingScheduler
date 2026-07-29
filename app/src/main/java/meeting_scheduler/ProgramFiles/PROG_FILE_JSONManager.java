package meeting_scheduler.ProgramFiles;
/**
 * PROG_FILE_JSONManager
 * Description: Used to manage the Input and output of the json file containing user preferences
 * 
 */

import java.util.LinkedList;

public class PROG_FILE_JSONManager {

    String JsonFile;
    LinkedList<PROG_INFO_InfoInput> UserCardList;
    boolean ObjectReferenceSet = false;

    public PROG_FILE_JSONManager() {

    }

    /**
     * SetUserCard()
     * Description: Sets the local object UserCardList to reference the incoming object card
     * ObjectReferenceSet is set to true to indicate that operations can be performed on the object (i.e it isn't null)
     * @param card
     */
    public void SetUserCard(LinkedList<PROG_INFO_InfoInput> card) {
        this.UserCardList = card;
        this.ObjectReferenceSet = true;
    }

    /**
     * DiscardCard()
     * Description: sets the local object reference to null.
     * Used to prevent accidental operations on the object after they have concluded.
     * ObjectReferenceSet set to false indicates operations should not be performed on the object
     */
    public void DiscardCard() {
        this.UserCardList = null;
        this.ObjectReferenceSet = false;
    }

    private int RetrieveFromFile() {

        if (ObjectReferenceSet == false) {
            System.out.println("ERROR - PROG_FILE_JSONManager - RetrieveFromFile() - UserCardList is null");
            return 1;
        }
        return 0;
    }
    
}
