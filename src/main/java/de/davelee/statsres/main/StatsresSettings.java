package de.davelee.statsres.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class representing the settings in the Statsres program.
 * @author Dave Lee.
 */
public class StatsresSettings {
	
	private String file;
	private List<String> columnData;
	private List<StatisticalFunctions> statisticalFunctions;
	
	/**
	 * Return the file name.
	 * @return a <code>String</code> with the file name.
	 */
	public String getFile() {
		return file;
	}
	
	/**
	 * Set the file name.
	 * @param file a <code>String</code> with the file name.
	 */
	public void setFile(final String file) {
		this.file = file;
	}
	
	/**
	 * Return the column data.
	 * @return a <code>List</code> of <code>String</code> objects containing the column data.
	 */
	public List<String> getColumnData() {
		return columnData;
	}
	
	/**
	 * Set the column data.
	 * @param columnData a <code>List</code> of <code>String</code> objects containing the column data.
	 */
	public void setColumnData(final List<String> columnData) {
		this.columnData = columnData;
	}
	
	/**
	 * Return a list of the selected statistical functions.
	 * @return a <code>List</code> of <code>StatisticalFunctions</code> with the selected statistical functions.
	 */
	public List<StatisticalFunctions> getStatisticalFunctions() {
		return statisticalFunctions;
	}
	
	/**
	 * Set a list of the selected statistical functions.
	 * @param statisticalFunctions a <code>List</code> of <code>StatisticalFunctions</code> with the selected statistical functions.
	 */
	public void setStatisticalFunctions(
			final List<StatisticalFunctions> statisticalFunctions) {
		this.statisticalFunctions = statisticalFunctions;
	}
	
	/**
	 * Method to check if the statistical function is in the array.
	 * @param function a <code>StatisticalFunction</code> object to check.
	 * @param functionList a <code>List</code> of <code>StatisticalFunctions</code> containing selected statistical functions.
	 * @return a <code>boolean</code> which is true iff the function to check is included in the list.
	 */
	private boolean isFunctionInList ( final StatisticalFunctions function, final List<StatisticalFunctions> functionList) {
		for ( StatisticalFunctions myFunction : functionList ) {
			if ( myFunction.getDisplayName().contentEquals(function.getDisplayName()) ) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Convert method to version 1 style.
	 * @return a <code>List</code> of <code>String</code> objects with the file data.
	 */
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
	
	/**
	 * Create a <code>StatsresSettings</code> object based on the default settings.
	 * @param fileName a <code>String</code> with the specified file name.
	 * @return a <code>StatsresSettings</code> object representing the default settings and specified file name.
	 */
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
	
	/**
	 * Load a file from version 1.
	 * @param settings a <code>String</code> array of settings.
	 * @return a <code>StatsresSettings</code> object representing the settings in the array.
	 */
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
