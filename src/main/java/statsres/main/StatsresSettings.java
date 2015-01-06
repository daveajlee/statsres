package statsres.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StatsresSettings {
	
	private String file;
	private List<String> columnData;
	private List<StatisticalFunctions> statisticalFunctions;
	
	public String getFile() {
		return file;
	}
	
	public void setFile(final String file) {
		this.file = file;
	}
	
	public List<String> getColumnData() {
		return columnData;
	}
	
	public void setColumnData(final List<String> columnData) {
		this.columnData = columnData;
	}
	
	public List<StatisticalFunctions> getStatisticalFunctions() {
		return statisticalFunctions;
	}
	
	public void setStatisticalFunctions(
			final List<StatisticalFunctions> statisticalFunctions) {
		this.statisticalFunctions = statisticalFunctions;
	}
	
	//Method to check if the statistical function is in the array.
	private boolean isFunctionInList ( final StatisticalFunctions function, final List<StatisticalFunctions> functionList) {
		for ( StatisticalFunctions myFunction : functionList ) {
			if ( myFunction == function ) {
				return true;
			}
		}
		return false;
	}
	
	//Convert method to version 1 style.
	public List<String> saveAsV1File() {
        //Create list to return.
        List<String> settingsList = new ArrayList<String>();
        //Add file.
        settingsList.add("File=" + file);
        //Add checkbox.
        settingsList.add("File Checkbox=" + (file!=null && file.endsWith(".csv")));
        //Add column data stuff.
        String columnDataStr = "Column Data=";
        for ( int i = 0; i < columnData.size(); i++) {
            columnDataStr += columnData.get(i) + ",";
        }
        settingsList.add(columnDataStr);
        //Now add each checkbox.
        settingsList.add("Mean=" + isFunctionInList(StatisticalFunctions.MEAN, statisticalFunctions));
        settingsList.add("Min=" + isFunctionInList(StatisticalFunctions.MIN, statisticalFunctions));
        settingsList.add("Max=" + isFunctionInList(StatisticalFunctions.MAX, statisticalFunctions));
        settingsList.add("Median=" + isFunctionInList(StatisticalFunctions.MEDIAN, statisticalFunctions));
        settingsList.add("Count=" + isFunctionInList(StatisticalFunctions.COUNT, statisticalFunctions));
        settingsList.add("IQR=" + isFunctionInList(StatisticalFunctions.INTER_QUARTILE_RANGE, statisticalFunctions));
        settingsList.add("1Q=" + isFunctionInList(StatisticalFunctions.QUARTILE_FIRST, statisticalFunctions));
        settingsList.add("3Q=" + isFunctionInList(StatisticalFunctions.QUARTILE_THIRD, statisticalFunctions));
        settingsList.add("StDev=" + isFunctionInList(StatisticalFunctions.STANDARD_DEVIATION, statisticalFunctions));
        //Return linked list.
        return settingsList;
    }
	
	public static StatsresSettings createDefaultSettings ( final String fileName ) {
		StatsresSettings mySettings = new StatsresSettings();
		mySettings.setFile(fileName);
		mySettings.setColumnData(new ArrayList<String>());
		List<StatisticalFunctions> functions = new ArrayList<StatisticalFunctions>();
		functions.add(StatisticalFunctions.MEAN);
		functions.add(StatisticalFunctions.MIN);
		functions.add(StatisticalFunctions.MAX);
		functions.add(StatisticalFunctions.MEDIAN);
		functions.add(StatisticalFunctions.COUNT);
		functions.add(StatisticalFunctions.INTER_QUARTILE_RANGE);
		functions.add(StatisticalFunctions.QUARTILE_FIRST);
		functions.add(StatisticalFunctions.QUARTILE_THIRD);
		functions.add(StatisticalFunctions.STANDARD_DEVIATION);
		mySettings.setStatisticalFunctions(functions);
		return mySettings;
	}
	
	public static StatsresSettings loadV1File ( String[] settings ) {
		StatsresSettings mySettings = new StatsresSettings();
		mySettings.setFile(settings[0]);
		// settings[1] was file or folder - this is no longer needed in version 2 so we ignore settings[1].
		mySettings.setColumnData(new ArrayList<String>(Arrays.asList(settings[2].split(","))));
		List<StatisticalFunctions> functions = new ArrayList<StatisticalFunctions>();
		if ( "true".equalsIgnoreCase(settings[3]) ) {
			functions.add(StatisticalFunctions.MEAN);
		} if ( "true".equalsIgnoreCase(settings[4]) ) {
			functions.add(StatisticalFunctions.MIN);
		} if ( "true".equalsIgnoreCase(settings[5]) ) {
			functions.add(StatisticalFunctions.MAX);
		} if ( "true".equalsIgnoreCase(settings[6]) ) {
			functions.add(StatisticalFunctions.MEDIAN);
		} if ( "true".equalsIgnoreCase(settings[7]) ) {
			functions.add(StatisticalFunctions.COUNT);
		} if ( "true".equalsIgnoreCase(settings[8]) ) {
			functions.add(StatisticalFunctions.INTER_QUARTILE_RANGE);
		} if ( "true".equalsIgnoreCase(settings[9]) ) {
			functions.add(StatisticalFunctions.QUARTILE_FIRST);
		} if ( "true".equalsIgnoreCase(settings[10]) ) {
			functions.add(StatisticalFunctions.QUARTILE_THIRD);
		} if ( "true".equalsIgnoreCase(settings[11]) ) {
			functions.add(StatisticalFunctions.STANDARD_DEVIATION);
		}
		mySettings.setStatisticalFunctions(functions);
		return mySettings;
	}

}
