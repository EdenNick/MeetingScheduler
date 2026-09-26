package meeting_scheduler.GlobalMessages;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import meeting_scheduler.global;

public class GlobalMessageManager {


    private static String[] Global_Message_Type;

    private static String[] Global_Message_Class;

    private static String[] Global_Message_Method;

    private static String[] Global_Message_Info;



    private GlobalMessageManager() {
        // 
    }

    public static void MESSAGE_SETUP() {
        Set_Global_Message_Type();
        Set_Global_Message_Class();
        Set_Global_Message_Method();
        Set_Global_Message_Info();

        // public static void Global_Message_Set(String[] Input_Types, String[] Input_Class, String[] Input_Methods, String[] Input_Info)
        // global.Global_Message_Set();
    }

    private static void Set_Global_Message_Type() {

        // Properties obejct
        Properties  Prop_Type = new Properties();
        // indiivudal property string
        String      IncomingPropString;
        // while loop position
        int         IncomingPropPosition = 0;
        // array to be set to global
        String[]    FinalList;

        // Arraylist containg all properites in the file
        ArrayList<String> ArraylistPropStrings = new ArrayList<>();

        // TRY/CATCH
        try ( FileInputStream Input_Type = new FileInputStream("GlobalMessageTypes.properties") ) {
            
            // Load Props from File
            Prop_Type.load(Input_Type);

            // While strings continue to be valid and not NULL, continue
            while ( !(IncomingPropString = Prop_Type.getProperty("Type." + IncomingPropPosition)).equals(null) ) {
                
                System.out.println("Type: " + IncomingPropString);

                ArraylistPropStrings.add(IncomingPropString);

                IncomingPropPosition++;
            }

            FinalList = ArraylistPropStrings.toArray(new String[0]);

        } catch (IOException e) {
            //TODO: error message
            FinalList = new String[] {"ERROR"};
        }

        // Set to static variable
        GlobalMessageManager.Global_Message_Type = FinalList.clone();

    } // Set_Global_Message_Type


    
    private static void Set_Global_Message_Class() {
        
        Properties Prop_Class = new Properties();
        // TODO
    } // Set_Global_Message_Class

    private static void Set_Global_Message_Method() {

        Properties Prop_Method = new Properties();
        // TODO
    } // Set_Global_Message_Method

    private static void Set_Global_Message_Info() {

        Properties Prop_Info = new Properties();
        // TODO
    } // Set_Global_Message_Info


}
