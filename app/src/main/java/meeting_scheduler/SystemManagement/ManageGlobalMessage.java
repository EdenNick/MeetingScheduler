// Package  - DO Not Change
// ############################################################
package meeting_scheduler.SystemManagement;
// ############################################################

//Imports
// ############################################################
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Properties;
import meeting_scheduler.global;
// ############################################################
import meeting_scheduler.FileManagement.ManageFileLock;



public class ManageGlobalMessage {


    // ARRAY VALUE HOLDERS
    // ############################################################
    private static String[] Global_Message_Type;
    private static String[] Global_Message_Class;
    private static String[] Global_Message_Action;
    private static String[] Global_Message_Info;
    // ############################################################

    // Maximum character length read from each property
    // ############################################################
    private static int Global_Message_Type_Length   = 0;
    private static int Global_Message_Class_Length  = 0;
    private static int Global_Message_Action_Length = 0;
    private static int Global_Message_Info_Length   = 0;
    // ############################################################

    // Proprty Path Variables
    // ############################################################
    // Message Paths
    private static final String PROPPATH_TYPE       = "TYPE.";
    private static final String PROPPATH_CLASS      = "CLASS.";
    private static final String PROPPATH_ACTION     = "ACTION.";
    private static final String PROPPATH_INFO       = "INFO.";
    // Message locations
    private static final String PROPPATH_GLOBAL         = "GLOBAL.";
    private static final String PROPPATH_USERINPUT      = "USERINPUT.";
    private static final String PROPPATH_UIMANAGE       = "UIMANAGE.";
    private static final String PROPPATH_SCHEDULE       = "SCHEDULE.";
    private static final String PROPPATH_SCENE          = "SCENE.";
    private static final String PROPPATH_GLOBALVALUES   = "GLOBALVALUES.";
    private static final String PROPPATH_GLOBALMESSAGE  = "GLOBALMESSAGE.";
    private static final String PROPPATH_FILE           = "FILE.";
    private static final String PROPPATH_EMPLOYEEPREF   = "EMPLOYEEPREF.";
    // System message types
    private static final String PROPPATH_SYST = "SYST.";
    private static final String PROPPATH_USER = "USER.";
    // ############################################################





    private ManageGlobalMessage() {
        // 
    }

    public static void MESSAGE_SETUP() {
        Set_Global_Message_Type();
        Set_Global_Message_Class();
        Set_Global_Message_Action();
        Set_Global_Message_Info();

        // public static void Global_Message_Set(String[] Input_Types, String[] Input_Class, String[] Input_Methods, String[] Input_Info)
        global.Global_Message_Set(ManageGlobalMessage.Global_Message_Type, ManageGlobalMessage.Global_Message_Class, 
            ManageGlobalMessage.Global_Message_Action, ManageGlobalMessage.Global_Message_Info);
    }

    
    public static int Return_Length_Type() {
        return Global_Message_Type_Length;
    }

    public static int Return_Length_Class() {
        return Global_Message_Class_Length;
    }

    public static int Return_Length_Action() {
        return Global_Message_Action_Length;
    }

    public static int Return_Length_info() {
        return Global_Message_Info_Length;
    }


    private static void Set_Global_Message_Type() {
        
        InputStream FileInput = ManageFileLock.GetInputStream_MSGTYPE();
        // String FilePath = "/GlobalMessageTypes.properties";
        String PropertyPath;

        LinkedList<String> AddAll = new LinkedList<>();

        // Get System Actions
        PropertyPath = PROPPATH_TYPE;
        AddAll.addAll(Arrays.asList(Get_Global_Message_Property(FileInput, PropertyPath)));

        // add all together
        ManageGlobalMessage.Global_Message_Type = AddAll.toArray(new String[0]);

        int maxLength = 0;
        for (int pos = 0; pos < Global_Message_Type.length; pos++) {
            if (maxLength < Global_Message_Type[pos].length()) {
                maxLength = Global_Message_Type[pos].length();
            }
        }

        ManageGlobalMessage.Global_Message_Type_Length = maxLength;
    } // Set_Global_Message_Type


    
    private static void Set_Global_Message_Class() {

        InputStream FileInput = ManageFileLock.GetInputStream_MSGCLSS();
        // String FilePath = "/GlobalMessageClass.properties";
        String PropertyPath;

        LinkedList<String> AddAll = new LinkedList<>();

        // Get GLOBAL
        PropertyPath = PROPPATH_CLASS + PROPPATH_GLOBAL;

        AddAll.addAll(Arrays.asList(Get_Global_Message_Property(FileInput, PropertyPath)));


        // Get USERINPUT
        PropertyPath = PROPPATH_CLASS + PROPPATH_USERINPUT;
        AddAll.addAll(Arrays.asList(Get_Global_Message_Property(FileInput, PropertyPath)));


        // Get UIMANAGE
        PropertyPath = PROPPATH_CLASS + PROPPATH_UIMANAGE;
        AddAll.addAll(Arrays.asList(Get_Global_Message_Property(FileInput, PropertyPath)));


        // Get SCHEDULE
        PropertyPath = PROPPATH_CLASS + PROPPATH_SCHEDULE;
        AddAll.addAll(Arrays.asList(Get_Global_Message_Property(FileInput, PropertyPath)));


        // Get SCENE
        PropertyPath = PROPPATH_CLASS + PROPPATH_SCENE;
        AddAll.addAll(Arrays.asList(Get_Global_Message_Property(FileInput, PropertyPath)));

        
        // Get GLOBALVALUES
        PropertyPath = PROPPATH_CLASS + PROPPATH_GLOBALVALUES;
        AddAll.addAll(Arrays.asList(Get_Global_Message_Property(FileInput, PropertyPath)));


        // Get GLOBALMESSAGE
        PropertyPath = PROPPATH_CLASS + PROPPATH_GLOBALMESSAGE;
        AddAll.addAll(Arrays.asList(Get_Global_Message_Property(FileInput, PropertyPath)));


        // Get FILE
        PropertyPath = PROPPATH_CLASS + PROPPATH_FILE;
        AddAll.addAll(Arrays.asList(Get_Global_Message_Property(FileInput, PropertyPath)));


        // Get EMPLOYEEPREF
        PropertyPath = PROPPATH_CLASS + PROPPATH_EMPLOYEEPREF;
        AddAll.addAll(Arrays.asList(Get_Global_Message_Property(FileInput, PropertyPath)));


        // add all together
        ManageGlobalMessage.Global_Message_Class = AddAll.toArray(new String[0]);

        int maxLength = 0;
        for (int pos = 0; pos < Global_Message_Class.length; pos++) {
            if (maxLength < Global_Message_Class[pos].length()) {
                maxLength = Global_Message_Class[pos].length();
            }
        }

        ManageGlobalMessage.Global_Message_Class_Length = maxLength;

    } // Set_Global_Message_Class



    private static void Set_Global_Message_Action() {

        InputStream FileInput = ManageFileLock.GetInputStream_MSGACTN();
        // String FilePath = "/GlobalPropertiesAction.properties";
        String PropertyPath;

        LinkedList<String> AddAll = new LinkedList<>();

        // Get System Actions
        PropertyPath = PROPPATH_ACTION + PROPPATH_SYST;
        AddAll.addAll(Arrays.asList(Get_Global_Message_Property(FileInput, PropertyPath)));

        // Get USer Actions
        PropertyPath = PROPPATH_ACTION + PROPPATH_USER;
        AddAll.addAll(Arrays.asList(Get_Global_Message_Property(FileInput, PropertyPath)));

        // add all together
        ManageGlobalMessage.Global_Message_Action = AddAll.toArray(new String[0]);

        int maxLength = 0;
        for (int pos = 0; pos < Global_Message_Action.length; pos++) {
            if (maxLength < Global_Message_Action[pos].length()) {
                maxLength = Global_Message_Action[pos].length();
            }
        }

        ManageGlobalMessage.Global_Message_Action_Length = maxLength;

    } // Set_Global_Message_Method



    private static void Set_Global_Message_Info() {

        InputStream FileInput = ManageFileLock.GetInputStream_MSGINFO();
        // String FilePath = "/GlobalMessageInfo.properties";
        String PropertyPath;

        LinkedList<String> AddAll = new LinkedList<>();

        // Get System Actions
        PropertyPath = PROPPATH_INFO;
        AddAll.addAll(Arrays.asList(Get_Global_Message_Property(FileInput, PropertyPath)));

        // add all togehter
        ManageGlobalMessage.Global_Message_Info = AddAll.toArray(new String[0]);

        int maxLength = 0;
        for (int pos = 0; pos < Global_Message_Info.length; pos++) {
            if (maxLength < Global_Message_Info[pos].length()) {
                maxLength = Global_Message_Info[pos].length();
            }
        }

        ManageGlobalMessage.Global_Message_Info_Length = maxLength;

    } // Set_Global_Message_Info



    private static String[] Get_Global_Message_Property(InputStream INPUT_INPUTSTREAM, String INPUT_PROPPATH) {

        // Properties object
        Properties  Prop_Type = new Properties();
        // File Path
        InputStream FileInput = INPUT_INPUTSTREAM;
        //String      FilePath = INPUT_FILEPATH;
        //Property Path
        String      PropPath = INPUT_PROPPATH;
        // indiivudal property string
        String      IncomingPropString;
        // while loop position
        int         IncomingPropPosition = 0;
        // array to be set to global
        String[]    FinalList;

        // Arraylist containg all properites in the file
        LinkedList<String> ArraylistPropStrings = new LinkedList<>();

        // TRY/CATCH
        // (InputStream FileInput = ManageGlobalMessage.class.getResourceAsStream(FilePath))
        try {
            
            // Load Props from File
            Prop_Type.load(FileInput);

            // While strings continue to be valid and not NULL, continue
            while ( (IncomingPropString = Prop_Type.getProperty(PropPath + IncomingPropPosition)) != null ) {

                ArraylistPropStrings.add(IncomingPropString);
                IncomingPropPosition++;
            }

            FinalList = ArraylistPropStrings.toArray(new String[0]);

        } catch (IOException e) {
            // ERROR MESSAGE
            FinalList = new String[0];
            System.exit(1);
        }

        // Set to static variable
        return FinalList.clone();
    }


} // ManageGlobalMessage{}
