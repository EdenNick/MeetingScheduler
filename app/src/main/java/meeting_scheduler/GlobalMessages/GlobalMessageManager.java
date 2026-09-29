// Package  - DO Not Change
// ############################################################
package meeting_scheduler.GlobalMessages;
// ############################################################

//Imports
// ############################################################
// io
import java.io.FileInputStream;
import java.io.IOException;
// util
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Properties;
// global 
import meeting_scheduler.global;
// ############################################################



public class GlobalMessageManager {


    private static String[] Global_Message_Type;

    private static String[] Global_Message_Class;

    private static String[] Global_Message_Action;

    private static String[] Global_Message_Info;

    // Message Path variables
    private static final String PROPPATH_TYPE       = "TYPE.";
    private static final String PROPPATH_CLASS      = "CLASS.";
    private static final String PROPPATH_ACTION     = "ACTION.";
    private static final String PROPPATH_INFO       = "INFO.";



    // Class path variables
    private static final String PROPPATH_GLOBAL         = "GLOBAL.";
    private static final String PROPPATH_USERINPUT      = "USERINPUT.";
    private static final String PROPPATH_UIMANAGE       = "UIMANAGE.";
    private static final String PROPPATH_SCHEDULE       = "SCHEDULE.";
    private static final String PROPPATH_SCENE          = "SCENE.";
    private static final String PROPPATH_GLOBALVALUES   = "GLOBALVALUES.";
    private static final String PROPPATH_GLOBALMESSAGE  = "GLOBALMESSAGE.";
    private static final String PROPPATH_FILE           = "FILE.";
    private static final String PROPPATH_EMPLOYEEPREF   = "EMPLOYEEPREF.";


    // Action path variables
    private static final String PROPPATH_SYST = "SYST.";
    private static final String PROPPATH_USER = "USER.";


    // Info path variables




    private GlobalMessageManager() {
        // 
    }

    public static void MESSAGE_SETUP() {
        Set_Global_Message_Type();
        Set_Global_Message_Class();
        Set_Global_Message_Action();
        Set_Global_Message_Info();

        // public static void Global_Message_Set(String[] Input_Types, String[] Input_Class, String[] Input_Methods, String[] Input_Info)
        global.Global_Message_Set(GlobalMessageManager.Global_Message_Type, GlobalMessageManager.Global_Message_Class, 
            GlobalMessageManager.Global_Message_Action, GlobalMessageManager.Global_Message_Info);
    }

    private static void Set_Global_Message_Type() {
        
        // TODO: CHANGE
        String FilePath = "src\\main\\java\\meeting_scheduler\\GlobalMessages\\GlobalMessageTypes.properties";
        String PropertyPath;

        LinkedList<String> AddAll = new LinkedList<>();

        // Get System Actions
        PropertyPath = PROPPATH_TYPE;
        AddAll.addAll(Arrays.asList(Get_Global_Message_Property(FilePath, PropertyPath)));

        // add all together
        GlobalMessageManager.Global_Message_Type = AddAll.toArray(new String[0]);

    } // Set_Global_Message_Type


    
    private static void Set_Global_Message_Class() {

        // TODO: CHANGE
        String FilePath = "src\\main\\java\\meeting_scheduler\\GlobalMessages\\GlobalMessageClass.properties";
        String PropertyPath;

        LinkedList<String> AddAll = new LinkedList<>();

        // Get GLOBAL
        PropertyPath = PROPPATH_CLASS + PROPPATH_GLOBAL;

        AddAll.addAll(Arrays.asList(Get_Global_Message_Property(FilePath, PropertyPath)));


        // Get USERINPUT
        PropertyPath = PROPPATH_CLASS + PROPPATH_USERINPUT;
        AddAll.addAll(Arrays.asList(Get_Global_Message_Property(FilePath, PropertyPath)));


        // Get UIMANAGE
        PropertyPath = PROPPATH_CLASS + PROPPATH_UIMANAGE;
        AddAll.addAll(Arrays.asList(Get_Global_Message_Property(FilePath, PropertyPath)));


        // Get SCHEDULE
        PropertyPath = PROPPATH_CLASS + PROPPATH_SCHEDULE;
        AddAll.addAll(Arrays.asList(Get_Global_Message_Property(FilePath, PropertyPath)));


        // Get SCENE
        PropertyPath = PROPPATH_CLASS + PROPPATH_SCENE;
        AddAll.addAll(Arrays.asList(Get_Global_Message_Property(FilePath, PropertyPath)));

        
        // Get GLOBALVALUES
        PropertyPath = PROPPATH_CLASS + PROPPATH_GLOBALVALUES;
        AddAll.addAll(Arrays.asList(Get_Global_Message_Property(FilePath, PropertyPath)));


        // Get GLOBALMESSAGE
        PropertyPath = PROPPATH_CLASS + PROPPATH_GLOBALMESSAGE;
        AddAll.addAll(Arrays.asList(Get_Global_Message_Property(FilePath, PropertyPath)));


        // Get FILE
        PropertyPath = PROPPATH_CLASS + PROPPATH_FILE;
        AddAll.addAll(Arrays.asList(Get_Global_Message_Property(FilePath, PropertyPath)));


        // Get EMPLOYEEPREF
        PropertyPath = PROPPATH_CLASS + PROPPATH_EMPLOYEEPREF;
        AddAll.addAll(Arrays.asList(Get_Global_Message_Property(FilePath, PropertyPath)));


        // add all together
        GlobalMessageManager.Global_Message_Class = AddAll.toArray(new String[0]);


    } // Set_Global_Message_Class

    private static void Set_Global_Message_Action() {

        // TODO: CHANGE
        String FilePath = "src\\main\\java\\meeting_scheduler\\GlobalMessages\\GlobalPropertiesAction.properties";
        String PropertyPath;

        LinkedList<String> AddAll = new LinkedList<>();

        // Get System Actions
        PropertyPath = PROPPATH_ACTION + PROPPATH_SYST;
        AddAll.addAll(Arrays.asList(Get_Global_Message_Property(FilePath, PropertyPath)));

        // Get USer Actions
        PropertyPath = PROPPATH_ACTION + PROPPATH_USER;
        AddAll.addAll(Arrays.asList(Get_Global_Message_Property(FilePath, PropertyPath)));

        // add all together
        GlobalMessageManager.Global_Message_Action = AddAll.toArray(new String[0]);

    } // Set_Global_Message_Method

    private static void Set_Global_Message_Info() {

        // TODO: CHANGE
        String FilePath = "src\\main\\java\\meeting_scheduler\\GlobalMessages\\GlobalMessageInfo.properties";
        String PropertyPath;

        LinkedList<String> AddAll = new LinkedList<>();

        // Get System Actions
        PropertyPath = PROPPATH_INFO;
        AddAll.addAll(Arrays.asList(Get_Global_Message_Property(FilePath, PropertyPath)));

        // add all togehter
        GlobalMessageManager.Global_Message_Info = AddAll.toArray(new String[0]);

    } // Set_Global_Message_Info


    private static String[] Get_Global_Message_Property(String INPUT_FILEPATH, String INPUT_PROPPATH) {

        // Properties object
        Properties  Prop_Type = new Properties();
        // File Path
        String      FilePath = INPUT_FILEPATH;
        //Property Path
        String      PropPath = INPUT_PROPPATH;
        // indiivudal property string
        String      IncomingPropString;
        // while loop position
        // TODO make the starting position a global value
        int         IncomingPropPosition = 0;
        // array to be set to global
        String[]    FinalList;

        // Arraylist containg all properites in the file
        LinkedList<String> ArraylistPropStrings = new LinkedList<>();

        // TRY/CATCH
        try ( FileInputStream Input_Type = new FileInputStream(FilePath) ) {
            
            // Load Props from File
            Prop_Type.load(Input_Type);

            // While strings continue to be valid and not NULL, continue
            while ( (IncomingPropString = Prop_Type.getProperty(PropPath + IncomingPropPosition)) != null ) {
                
                //System.out.println("Type: " + IncomingPropString);

                ArraylistPropStrings.add(IncomingPropString);

                IncomingPropPosition++;
            }

            FinalList = ArraylistPropStrings.toArray(new String[0]);

        } catch (IOException e) {
            //TODO: error message
            FinalList = new String[] {"ERROR"};
        }

        // Set to static variable
        return FinalList.clone();
    }


}
