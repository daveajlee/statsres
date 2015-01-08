package statsres.main;
//Import Java Util package.
import java.util.*;
//Import Java IO package.
import java.io.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * StatsresProg.java is the central processing class for Statsres.
 * @author David Lee.
 * @version 1.0.
 */
public class StatsresProg extends Thread {
    
    private List<Double> fileData;
    
    //Variables to remember files, columns and functions to allow run method in Thread class with no parameters to be executed as calculate tasks.
    private List<String> files;
    private List<String> columns;
    private List<StatisticalFunctions> statisticalFunctions;
    //Variable to display output and boolean variable indicating whether finished or not.
    private String output = "";
    private boolean stillRunning = false;
    
    private static final Logger LOG = LoggerFactory.getLogger(StatsresProg.class);
    
    /** Default constructor. */
    public StatsresProg() {
        fileData = new ArrayList<Double>();
    }
    
    /**
     * Set the file, columns and output parameters for later calculation of statistical measurements.
     * Added in version 1.1 to enable better threading support.
     * @param files a <code>String</code> linkedlist containing the results file to process.
     * @param columns a <code>String</code> List containing the columns of the results file to process.
     * @param outputs a <code>StatisticalFunctions List</code> containing the statistical measurements required by user.
     */
    public void setCalcParameters ( final List<String> files, final List<String> columns, final List<StatisticalFunctions> functions ) {
        this.files = files;
        this.columns = columns;
        statisticalFunctions = functions;        
    } 
    
    /**
     * Process file into linked list which can be used to calculate the desired statistical measurements.
     */
    public void run (  ) {
    	stillRunning = true;
        output = "";
        //Repeat for all files,
        for ( int g = 0; g < files.size(); g++ ) {
            //Ensure fileData is blank.
            fileData = new ArrayList<Double>();
            //Open results file.
            List<String> resultsFileContents = ReadWriteFile.readFile(files.get(g), false);
            //Look through first row of data file to determine which positions in the array we need to look through.
            Map<String, Integer> columnPositions = new HashMap<String, Integer>();
            String[] firstRow = resultsFileContents.get(0).split(",");
            for ( int i = 0; i < firstRow.length; i++ ) {
            	columnPositions.put(firstRow[i], i);
            }
            if ( !stillRunning ) { 
            	return;
            }
            //Append file name.
            if ( g != 0 ) { 
            	output += "\n\n\n";
            }
            output += "File: " + files.get(g);
            //Now for each column position,
            performCalculationsOnColumns(columnPositions, resultsFileContents);
        }
        //Finished so set stillRunning to false.
        stillRunning = false;
    }
    
    /**
     * Perform calculations on each of the columns.
     */
    public void performCalculationsOnColumns ( final Map<String,Integer> columnPositions, final List<String> resultsFileContents ) {
    	for ( int h = 0; h < columns.size(); h++ ) {
            //Ensure fileData is blank.
            fileData = new ArrayList<Double>();
            //Now go through rest of file contents and add data in the columnPosition of the row.
            try {
            	for ( int i = 1; i < resultsFileContents.size(); i++ ) {
            		String[] thisRow = resultsFileContents.get(i).split(",");
            		fileData.add(Double.parseDouble(thisRow[columnPositions.get(columns.get(h))]));
            	}
            	//Now call performCalculations method on the String LinkedList.
            	if (h != 0) { 
            		output += "\n";
            	}
            	output += "\n" + performCalculations ( fileData, columns.get(h), statisticalFunctions);
            	if ( !stillRunning ) { 
        			return;
        		}
            } catch ( Exception e ) {
            	LOG.error("Error mit non-numerical data", e);
                output += "ERROR: Non-numerical data was present. Please ensure only numerical data is included except for column headings.";
            }
        }
    }
    
    /**
     * Method to perform all statistical calculations.
     * @param data a <code>List</code> containing the data to be used in calculations.
     * @param columnHeading a <code>String</code> containing the column heading currently being processed.
     * @param outputs a <code>StatisticalFunctions List</code> containing the statistical measurements to calculate.
     * @return a <code>String</code> with the results to be presented in text area.
     */
    private String performCalculations ( List<Double> data, String columnHeading, List<StatisticalFunctions> functions ) {
        //Reset the output variable.
        String outputStr = "Statistical Results for column " + columnHeading + ":";
        //Try all of the following - printing out an arithmetic error if an error occurs.
        try {
        	for ( int i = 0; i < functions.size(); i++ ) {
        		outputStr += "\n" + functions.get(i).getDisplayName() + ": " + removeZeros("" + functions.get(i).calculate(data));
        	} 
        } catch ( Exception e ) {
        	LOG.error("Arithmetic error", e);
            outputStr += "\nERROR: An arithmetic error has occurred. Cannot calculate remaining results";
        }
        //Finished so return output variable.
        return outputStr;
    }
    
    /**
     * Method to return the output produced during calculation of statistical measurements.
     * @return a <code>String</code> containing the output.
     */
    public String getOutput () {
        return output;
    }
    
    /**
     * Method to return whether or not processing is still running for checking different threads.
     * @return a <code>boolean</code> which is true iff processing is still running.
     */
    public boolean isStillRunning () {
        return stillRunning;
    }
    
    /**
     * Method to stop processing and add message to output saying that processing was interrupted.
     */
    public void stopProcessing () {
        stillRunning = false;
        output += "\nWARNING: Processing was interrupted!";
    }
    
    private boolean fileAlreadyAdded ( final String fileToCheck, final List<String> currentFileList) {
    	boolean alreadyExists = false;
        for ( int i = 0; i < currentFileList.size(); i++ ) {
            if ( currentFileList.get(i).equalsIgnoreCase(fileToCheck) ) {
                alreadyExists = true; 
                break;
            }
        }
        return alreadyExists;
    }
    
    /**
     * Method to return all files with the extension .csv in subfolders
     * of the folder supplied by the user.
     * @param directory a <code>String</code> with the folder of which subfolders should be searched.
     */
    public List<String> getAllFiles ( final String directory ) {
    	return getFilesInDirectory(directory);
    }
    
    /**
     * Private heler method to return all files for a specified directory.
     * @param directory a <code>String</code> with the folder to be searched.
     * @return a <code>String</code> List with all files.
     */
    private List<String> getFilesInDirectory ( final String directory ) {
    	List<String> filesToProcess = new ArrayList<String>();
    	//Obtain a list for this directory of subfolders on the next level and files in this folder.
        String[] directoryFiles = new File(directory).list();
        if ( directoryFiles == null ) { 
        	return filesToProcess;
        }
        //Go through all files or subdirectories in this directory
        for ( int i = 0; i < directoryFiles.length; i++ ) {
        	//It is a csv file - great we add it to the list if we don't have it.
        	if ( directoryFiles[i].endsWith(".csv") ) {
        		if (!fileAlreadyAdded(directoryFiles[i], filesToProcess)) { 
        			filesToProcess.add(directory + directoryFiles[i]);
        		}
        	} else if (!directoryFiles[i].contains(".")){
        		//It must be a directory.
                filesToProcess.addAll(getFilesInDirectory(directory + directoryFiles[i] + "/"));
            }
        }
        return filesToProcess;
    }
    
    /**
     * Method to load output from a text file.
     * @param location a <code>String</code> containing the file to be read.
     * @return a <code>String</code> containing the content to be put into output area.
     */
    public String loadOutputFile ( String location ) {
        //Check for valid extension.
        if ( !location.endsWith(".sro") ) {
            return "";
        } else {
            String outputText = "";
            List<String> outputList = ReadWriteFile.readFile(location, false);
            //Process file into single string with new line characters.
            for ( int i = 0; i < outputList.size(); i++ ) {
                outputText += outputList.get(i) + "\n";
            }
            //Return output text.
            return outputText;
        }
    }
    
    /**
     * Method to save content to a text file.
     * @param content a <code>String</code> list containing the content.
     * @param location a <code>String</code> containing the file to save to.
     * @param fileExtension a <code>String</code> with the desired file extension.
     * @return a <code>boolean</code> indicating success of file writing.
     */
    public boolean saveContent ( final List<String> content, final String location, final String fileExtension ) {
        String editedLocation = location;
        if ( content.isEmpty() || location.contentEquals("")) {
        	return false;
        }
    	//First check if need to add extension.
        if ( !editedLocation.endsWith(fileExtension) ) {
            editedLocation += fileExtension;
        }
        //Then write file.
        return ReadWriteFile.writeFile(content, new File(editedLocation), true);
    }
    
    /**
     * Method to load settings from a text file.
     * @param location a <code>String</code> containing the file to be read.
     * @return a <code>String</code> array containing the settings to be used.
     */
    public StatsresSettings loadSettingsFile ( String location ) {
        //Check for valid extension.
        if ( !location.endsWith(".srs") ) {
            return null;
        } else {
            //Create options listing.
            String[] settings = new String[12];
            //Read in file.
            List<String> outputList = ReadWriteFile.readFile(location, false);
            //Error checking - all settings must be present.
            if ( outputList.size() != settings.length ) { 
            	return null;
            }
            //Split string - second part goes into settings array.
            for ( int i = 0; i < outputList.size(); i++ ) {
                settings[i] = outputList.get(i).split("=")[1];
            }
            return StatsresSettings.loadV1File(settings);
        }
    }

     /** 
     * Private helper method to remove zeros from strings where they are not required.
     * @param doubleStr a <code>String</code> with zeros to be removed.
     * @return a <code>String</code> with/without zeros as appropriate.
     */
     public String removeZeros ( String doubleStr ) {
        int startRemovePos = doubleStr.indexOf(".");
        for ( int j = startRemovePos+1; j < doubleStr.length(); j++ ) {
            if ( !"0".equalsIgnoreCase(doubleStr.substring(j, j+1)) ) {
                startRemovePos = (j+1);
            }
        }
        return doubleStr.substring(0, startRemovePos);
     }
     
}
