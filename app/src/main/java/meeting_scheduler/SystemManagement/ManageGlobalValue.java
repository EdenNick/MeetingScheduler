// Package  - DO Not Change
// ############################################################
package meeting_scheduler.SystemManagement;
// ############################################################

// IMPORTS
// ############################################################
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import meeting_scheduler.SystemInfoManager;
import meeting_scheduler.global;
import meeting_scheduler.FileManagement.ManageFilePaths;
// ############################################################



public class ManageGlobalValue {
    
    // INPUT STREAMS
    // ############################################################
    private final static String VALUES_ARRAY   = ManageFilePaths.GetInputStream_VALARRY();
    private final static String VALUES_UNIQUE  = ManageFilePaths.GetInputStream_VALUNQE();
    // ############################################################


    // PROP PATH UNIQUE
    // ############################################################
    private final static String PATH_UNIQUE_INT             = "UNIQUE.INT.";
    private final static String PATH_UNIQUE_STRING_TEXT     = "UNIQUE.STRING.TEXT.";
    private final static String PATH_UNIQUE_STRING_STYLE    = "UNIQUE.STRING.STYLE.";
    private final static String PATH_UNIQUE_DOUBLE_SPACING  = "UNIQUE.DOUBLE.SPACING.";
    private final static String PATH_UNIQUE_DOUBLE_PADDING  = "UNIQUE.DOUBLE.PADDING.";
    private final static String PATH_UNIQUE_DOUBLE_WIDTH    = "UNIQUE.DOUBLE.WIDTH.";
    private final static String PATH_UNIQUE_DOUBLE_HEIGHT   = "UNIQUE.DOUBLE.HEIGHT.";
    private final static String PATH_UNIQUE_DOUBLE_ANCHOR   = "UNIQUE.DOUBLE.ANCHOR.";
    // ############################################################

    // PROP PATH ARRAY
    // ############################################################
    // WEEKS
    private final static String PATH_ARRAY_WEEKSHORT        = "ARRAY.WEEKDAY.SHORT.";
    private final static String PATH_ARRAY_WEEKSHORTCAP     = "ARRAY.WEEKDAY.SHORTCAP.";
    private final static String PATH_ARRAY_WEEKLONG         = "ARRAY.WEEKDAY.LONG.";
    private final static String PATH_ARRAY_WEEKLONGCAP      = "ARRAY.WEEKDAY.LONGCAP.";
    // AM PM
    private final static String PATH_ARRAY_AMPM             = "ARRAY.AMPM.";
    // Time intervals
    private final static String PATH_ARRAY_TIME             = "ARRAY.TIME.";
    // valid ID range
    private final static String PATH_ARRAY_IDENT            = "ARRAY.ID.";
    // ############################################################



    // INT
    // ############################################################
    private static int[] GLOBAL_VALUE_int;
    private static int[] GLOBAL_VALUE_TimeIntervals;
    private static int[] GLOBAL_VALUE_IDENTIntervals;
    // ############################################################

    // STRING
    // ############################################################
    // WEEKDAYS
    private static String[] STRING_WEEKDAY_SHORT;
    private static String[] STRING_WEEKDAY_SHORTCAP;
    private static String[] STRING_WEEKDAY_LONG;
    private static String[] STRING_WEEKDAY_LONGCAP;
    // TEXT
    private static String[] STRING_TEXT;
    // STYLES
    private static String[] STRING_STYLE;
    // AM PM
    private static String[] STRING_AMPM;
    // ############################################################

    // DOUBLE
    // ############################################################
    // UI SPACING
    private static double[] DOUBLE_UI_SPACING;
    private static double[] DOUBLE_UI_PADDING;
    private static double[] DOUBLE_UI_WIDTH;
    private static double[] DOUBLE_UI_HEIGHT;
    private static double[] DOUBLE_UI_ANCHOR;
    // ############################################################

    
    private ManageGlobalValue() {
        // prevents instatiation
    }

    public static void VALUES_SETUP() {
        
        SetValues_int();
        SetValues_String();
        SetValuesDouble();

        // void Global_Array_UISpacing_Set (double[] INPUT_SPACE, double[] INPUT_PAD, double[] INPUT_WIDTH, double[] INPUT_HEIGTH, double[] INPUT_ANCHOR)
        global.Global_Array_UISpacing_Set   (DOUBLE_UI_SPACING, DOUBLE_UI_PADDING, DOUBLE_UI_WIDTH, DOUBLE_UI_HEIGHT, DOUBLE_UI_ANCHOR);
        
        // void Global_Array_WeekDay_Set (String[] INPUT_SHORT, String[] INPUT_SHORTCAP, String[] INPUT_LONG, String[] INPUT_LONGCAP)
        global.Global_Array_WeekDay_Set     (STRING_WEEKDAY_SHORT, STRING_WEEKDAY_SHORTCAP, STRING_WEEKDAY_LONG, STRING_WEEKDAY_LONGCAP);

        // void GlobalValueSetINT (int[] INPUT_UNIQUE, int[] INPUT_TIMEINTERVALS, int[] INPUT_IDENTINTERVAL)
        global.GlobalValueSetINT            (GLOBAL_VALUE_int, GLOBAL_VALUE_TimeIntervals, GLOBAL_VALUE_IDENTIntervals);

        // void GlobalValueSetString (String[] INPUT_TEXT, String[] INPUT_STYLE, String[] INPUT_AMPM)
        global.GlobalValueSetString         (STRING_TEXT, STRING_STYLE, STRING_AMPM);
    }

    private static void SetValues_int() {
        ManageGlobalValue.GLOBAL_VALUE_int                = GetValues_ConvertToInt(VALUES_UNIQUE, PATH_UNIQUE_INT);
        ManageGlobalValue.GLOBAL_VALUE_TimeIntervals      = GetValues_ConvertToInt(VALUES_ARRAY, PATH_ARRAY_TIME);
        ManageGlobalValue.GLOBAL_VALUE_IDENTIntervals     = GetValues_ConvertToInt(VALUES_ARRAY, PATH_ARRAY_IDENT);
    }

    private static void SetValues_String() {
        ManageGlobalValue.STRING_TEXT               = Get_Global_Values_Property(VALUES_UNIQUE, PATH_UNIQUE_STRING_TEXT);
        ManageGlobalValue.STRING_STYLE              = Get_Global_Values_Property(VALUES_UNIQUE, PATH_UNIQUE_STRING_STYLE);
        ManageGlobalValue.STRING_WEEKDAY_SHORT      = Get_Global_Values_Property(VALUES_ARRAY, PATH_ARRAY_WEEKSHORT);
        ManageGlobalValue.STRING_WEEKDAY_SHORTCAP   = Get_Global_Values_Property(VALUES_ARRAY, PATH_ARRAY_WEEKSHORTCAP);
        ManageGlobalValue.STRING_WEEKDAY_LONG       = Get_Global_Values_Property(VALUES_ARRAY, PATH_ARRAY_WEEKLONG);
        ManageGlobalValue.STRING_WEEKDAY_LONGCAP    = Get_Global_Values_Property(VALUES_ARRAY, PATH_ARRAY_WEEKLONGCAP);
        ManageGlobalValue.STRING_AMPM               = Get_Global_Values_Property(VALUES_ARRAY, PATH_ARRAY_AMPM);
    }

    private static void SetValuesDouble() {
        ManageGlobalValue.DOUBLE_UI_SPACING            = GetValues_ConvertToDouble(VALUES_UNIQUE, PATH_UNIQUE_DOUBLE_SPACING);
        ManageGlobalValue.DOUBLE_UI_PADDING            = GetValues_ConvertToDouble(VALUES_UNIQUE, PATH_UNIQUE_DOUBLE_PADDING);
        ManageGlobalValue.DOUBLE_UI_WIDTH              = GetValues_ConvertToDouble(VALUES_UNIQUE, PATH_UNIQUE_DOUBLE_WIDTH);
        ManageGlobalValue.DOUBLE_UI_HEIGHT             = GetValues_ConvertToDouble(VALUES_UNIQUE, PATH_UNIQUE_DOUBLE_HEIGHT);
        ManageGlobalValue.DOUBLE_UI_ANCHOR             = GetValues_ConvertToDouble(VALUES_UNIQUE, PATH_UNIQUE_DOUBLE_ANCHOR);
    }






    private static int[] GetValues_ConvertToInt(String INPUT_FILEPATH, String INPUT_PROPPATH) {

        String[] Temp = Get_Global_Values_Property(INPUT_FILEPATH, INPUT_PROPPATH);
        int[] Return_Array = new int[Temp.length];

        for (int Pos = 0; Pos < Temp.length; Pos++) {
            Return_Array[Pos] = Integer.parseInt(Temp[Pos]);
        }

        return Return_Array.clone();
    }



    private static double[] GetValues_ConvertToDouble(String INPUT_FILEPATH, String INPUT_PROPPATH) {
        
        String[] Temp = Get_Global_Values_Property(INPUT_FILEPATH, INPUT_PROPPATH);
        double[] Return_Array = new double[Temp.length];

        for (int Pos = 0; Pos < Temp.length; Pos++) {
            Return_Array[Pos] = Double.parseDouble(Temp[Pos]);
        }
        return Return_Array.clone();
    }



    private static String[] Get_Global_Values_Property(String INPUT_INPUSTREAM, String INPUT_PROPPATH) {

        // Properties object
        Properties  Prop_Type = new Properties();
        // File Path
        String      FilePath = INPUT_INPUSTREAM;
        //Property Path
        String      PropPath = INPUT_PROPPATH;
        // indiivudal property string
        String      IncomingPropString;
        // while loop position
        int         IncomingPropPosition = 0;
        // array to be set to global
        String[]    FinalList;

        // Arraylist containg all properites in the file
        ArrayList<String> ArraylistPropStrings = new ArrayList<>();

        // TRY/CATCH
        // ( InputStream FileInput = ManageGlobalValue.class.getResourceAsStream(FilePath) )
        try ( InputStream FileInput = ManageGlobalValue.class.getResourceAsStream(FilePath) ){
            
            // Load Props from File
            Prop_Type.load(FileInput);

            // While strings continue to be valid and not NULL, continue
            while ( (IncomingPropString = Prop_Type.getProperty(PropPath + IncomingPropPosition)) != null ) {
                
                //System.out.println("Type: " + IncomingPropString);

                ArraylistPropStrings.add(IncomingPropString);
                IncomingPropPosition++;
            }

            FinalList = ArraylistPropStrings.toArray(new String[0]);

        } catch (IOException e) {
            // System Message
            // 6 - FAILURE | 21 - GlobalValuesManager | 10 - SYSTEM-FileAccess | 6 - try/catch Json file Write Failure
            SystemInfoManager.GET_SYSTEM_MESSAGE(6,25,10,7);
            FinalList = new String[0];
            System.exit(1);
        }

        // Set to static variable
        return FinalList.clone();
    }
} // GlobalValuesManager
