package meeting_scheduler.GlobalValues;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import meeting_scheduler.global;

public class GlobalValuesManager {
    

    private final static String FILEPATH_ValuesArrays = "GlobalValuesArrays.properties";
    private final static String FILEPATH_ValuesUnique = "GlobalValuesUnique.properties";

    private final static String PropPath_Array      = "ARRAY.";
    private final static String PropPath_Unique     = "UNIQUE.";

    private final static String PropPath_Int        = "INT.";
    private final static String PropPath_Spacing    = "SPACING.";
    private final static String PropPath_Padding    = "PADDING.";
    private final static String PropPath_Width      = "WIDTH.";
    private final static String PropPath_Height     = "HEIGHT.";
    private final static String PropPath_ANCHOR     = "ANCHOR.";

    private final static String PropPath_WeekDay    = "WEEKDAY.";
    private final static String PropPath_AMPM       = "AMPM.";
    private final static String PropPath_Time       = "TIME.";
    private final static String PropPath_Short      = "SHORT.";
    private final static String PropPath_ShortCap   = "SHORTCAP.";
    private final static String PropPath_Long       = "LONG.";
    private final static String PropPath_LongCap    = "LONGCAP.";
    private final static String PropPath_ID         = "ID.";




    private static int[] GLOBAL_VALUE_WeekLength;
    private static int[] GLOBAL_VALUE_TimeIntervals;
    private static int[] GLOBAL_VALUE_IDENTIntervals;

    private static String[] GLOBAL_VALUE_Weekday_Short;
    private static String[] GLOBAL_VALUE_Weekday_ShortCap;
    private static String[] GLOBAL_VALUE_Weekday_Long;
    private static String[] GLOBAL_VALUE_Weekday_LongCap;
    private static String[] GLOBAL_VALUE_AMPM;


    private static double[] GLOBAL_VALUE_Spacing;
    private static double[] GLOBAL_VALUE_Padding;
    private static double[] GLOBAL_VALUE_Width;
    private static double[] GLOBAL_VALUE_Height;
    private static double[] GLOBAL_VALUE_Anchor;

    
    private GlobalValuesManager() {
        // prevents instatiation
    }

    public static void VALUES_SETUP() {
        Set_Global_Values_intArrays();
        Set_Global_Values_StringArrays();
        Set_Global_Values_doubleArrays();

        //TODO: set to global from here
        // void Global_Array_WeekDay_Set (String[] INPUT_SHORT, String[] INPUT_SHORTCAP, String[] INPUT_LONG, String[] INPUT_LONGCAP)
        global.Global_Array_WeekDay_Set     (GLOBAL_VALUE_Weekday_Short, GLOBAL_VALUE_Weekday_ShortCap, GLOBAL_VALUE_Weekday_Long, GLOBAL_VALUE_Weekday_LongCap);

        // void Global_Array_BasicValue_Set (int[] INPUT_WEEKLENGTH, int[] INPUT_TIMEINTERVALS, int[] INPUT_IDENTINTERVAL, String[] INPUT_AMPM)
        global.Global_Array_BasicValue_Set  (GLOBAL_VALUE_WeekLength, GLOBAL_VALUE_TimeIntervals, GLOBAL_VALUE_IDENTIntervals, GLOBAL_VALUE_AMPM);

        // void Global_Array_UISpacing_Set (double[] INPUT_SPACE, double[] INPUT_PAD, double[] INPUT_WIDTH, double[] INPUT_HEIGTH, double[] INPUT_ANCHOR)
        global.Global_Array_UISpacing_Set   (GLOBAL_VALUE_Spacing, GLOBAL_VALUE_Padding, GLOBAL_VALUE_Width, GLOBAL_VALUE_Height, GLOBAL_VALUE_Anchor);
    }

    private static void Set_Global_Values_intArrays() {
        GlobalValuesManager.GLOBAL_VALUE_WeekLength         = GetValues_ConvertToInt(FILEPATH_ValuesUnique, PropPath_Unique + PropPath_Int);
        GlobalValuesManager.GLOBAL_VALUE_TimeIntervals      = GetValues_ConvertToInt(FILEPATH_ValuesArrays, PropPath_Array  + PropPath_Time);
        GlobalValuesManager.GLOBAL_VALUE_IDENTIntervals     = GetValues_ConvertToInt(FILEPATH_ValuesArrays, PropPath_Array  + PropPath_ID);
    }

    private static void Set_Global_Values_StringArrays() {
        GlobalValuesManager.GLOBAL_VALUE_Weekday_Short      = Get_Global_Values_Property(FILEPATH_ValuesArrays, PropPath_Array + PropPath_WeekDay + PropPath_Short);
        GlobalValuesManager.GLOBAL_VALUE_Weekday_ShortCap   = Get_Global_Values_Property(FILEPATH_ValuesArrays, PropPath_Array + PropPath_WeekDay + PropPath_ShortCap);
        GlobalValuesManager.GLOBAL_VALUE_Weekday_Long       = Get_Global_Values_Property(FILEPATH_ValuesArrays, PropPath_Array + PropPath_WeekDay + PropPath_Long);
        GlobalValuesManager.GLOBAL_VALUE_Weekday_LongCap    = Get_Global_Values_Property(FILEPATH_ValuesArrays, PropPath_Array + PropPath_WeekDay + PropPath_LongCap);
        GlobalValuesManager.GLOBAL_VALUE_AMPM               = Get_Global_Values_Property(FILEPATH_ValuesArrays, PropPath_Array + PropPath_AMPM);
    }

    private static void Set_Global_Values_doubleArrays() {
        GlobalValuesManager.GLOBAL_VALUE_Spacing            = GetValues_ConvertToDouble(FILEPATH_ValuesUnique, PropPath_Unique + PropPath_Spacing);
        GlobalValuesManager.GLOBAL_VALUE_Padding            = GetValues_ConvertToDouble(FILEPATH_ValuesUnique, PropPath_Unique + PropPath_Padding);
        GlobalValuesManager.GLOBAL_VALUE_Width              = GetValues_ConvertToDouble(FILEPATH_ValuesUnique, PropPath_Unique + PropPath_Width);
        GlobalValuesManager.GLOBAL_VALUE_Height             = GetValues_ConvertToDouble(FILEPATH_ValuesUnique, PropPath_Unique + PropPath_Height);
        GlobalValuesManager.GLOBAL_VALUE_Anchor             = GetValues_ConvertToDouble(FILEPATH_ValuesUnique, PropPath_Unique + PropPath_ANCHOR);
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



    private static String[] Get_Global_Values_Property(String INPUT_FILEPATH, String INPUT_PROPPATH) {

        // Properties object
        Properties  Prop_Type = new Properties();
        // File Path
        String      FilePath = INPUT_FILEPATH;
        //Property Path
        String      PropPAth = INPUT_PROPPATH;
        // indiivudal property string
        String      IncomingPropString;
        // while loop position
        int         IncomingPropPosition = 0;
        // array to be set to global
        String[]    FinalList;

        // Arraylist containg all properites in the file
        ArrayList<String> ArraylistPropStrings = new ArrayList<>();

        // TRY/CATCH
        try ( FileInputStream Input_Type = new FileInputStream(FilePath) ) {
            
            // Load Props from File
            Prop_Type.load(Input_Type);

            // While strings continue to be valid and not NULL, continue
            while ( !(IncomingPropString = Prop_Type.getProperty(PropPAth + IncomingPropPosition)).equals(null) ) {
                
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
        return FinalList.clone();
    }
} // GlobalValuesManager
