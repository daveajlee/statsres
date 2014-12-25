package statsres.main;

import java.util.ArrayList;
import java.util.List;

public class StatsresSettings {
	
	private String file;
	private boolean includeSubfolders;
	private List<String> columnData;
	private List<StatisticalFunctions> statisticalFunctions;
	
	public String getFile() {
		return file;
	}
	
	public void setFile(final String file) {
		this.file = file;
	}
	
	public boolean isIncludeSubfolders() {
		return includeSubfolders;
	}
	
	public void setIncludeSubfolders(final boolean includeSubfolders) {
		this.includeSubfolders = includeSubfolders;
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
        settingsList.add("File Checkbox=" + includeSubfolders);
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
	
	public static StatsresSettings loadV1File ( String[] settings ) {
		StatsresSettings mySettings = new StatsresSettings();
		mySettings.setFile(settings[0].split("=")[1]);
		mySettings.setIncludeSubfolders(Boolean.parseBoolean(settings[1].split("=")[1]));
		String[] content = settings[2].split("=")[1].split(",");
		List<String> columnData = new ArrayList<String>();
		for ( String contentStr : content ) {
			columnData.add(contentStr);
		}
		mySettings.setColumnData(columnData);
		List<StatisticalFunctions> functions = new ArrayList<StatisticalFunctions>();
		if ( settings[3].split("=")[1].equalsIgnoreCase("true") ) {
			functions.add(StatisticalFunctions.MEAN);
		} if ( settings[4].split("=")[1].equalsIgnoreCase("true") ) {
			functions.add(StatisticalFunctions.MIN);
		} if ( settings[5].split("=")[1].equalsIgnoreCase("true") ) {
			functions.add(StatisticalFunctions.MAX);
		} if ( settings[6].split("=")[1].equalsIgnoreCase("true") ) {
			functions.add(StatisticalFunctions.MEDIAN);
		} if ( settings[7].split("=")[1].equalsIgnoreCase("true") ) {
			functions.add(StatisticalFunctions.COUNT);
		} if ( settings[8].split("=")[1].equalsIgnoreCase("true") ) {
			functions.add(StatisticalFunctions.INTER_QUARTILE_RANGE);
		} if ( settings[9].split("=")[1].equalsIgnoreCase("true") ) {
			functions.add(StatisticalFunctions.QUARTILE_FIRST);
		} if ( settings[10].split("=")[1].equalsIgnoreCase("true") ) {
			functions.add(StatisticalFunctions.QUARTILE_THIRD);
		} if ( settings[11].split("=")[1].equalsIgnoreCase("true") ) {
			functions.add(StatisticalFunctions.STANDARD_DEVIATION);
		}
		mySettings.setStatisticalFunctions(functions);
		return mySettings;
	}

}
