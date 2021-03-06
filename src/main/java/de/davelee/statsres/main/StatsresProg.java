package de.davelee.statsres.main;
//Import Java Util package.
import java.util.*;
//Import Java IO package.
import java.io.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * StatsresProg is the central processing class for Statsres.
 * @author Dave Lee.
 */
public class StatsresProg extends Thread {
    
    private List<Double> fileData;
    
    //Variables to remember files, columns and functions to allow run method in Thread class with no parameters to be executed as calculate tasks.
    private List<String> files;
    private List<String> columns;
    private List<StatisticalFunctions> statisticalFunctions;
    //Variable to display output and boolean variable indicating whether finished or not.
    private StringBuilder outputBuilder = new StringBuilder();
    private boolean stillRunning = false;
    
    private static final Logger LOG = LoggerFactory.getLogger(StatsresProg.class);
    
    /** Default constructor. */
    public StatsresProg() {
        fileData = new ArrayList<>();
    }
    
    /**
     * Set the file, columns and output parameters for later calculation of statistical measurements.
     * Added in version 1.1 to enable better threading support.
     * @param files a <code>String</code> linkedlist containing the results file to process.
     * @param columns a <code>String</code> List containing the columns of the results file to process.
     * @param functions a <code>StatisticalFunctions List</code> containing the statistical measurements required by user.
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
        outputBuilder = new StringBuilder();
        //Repeat for all files,
        for ( int g = 0; g < files.size(); g++ ) {
            //Ensure fileData is blank.
            fileData = new ArrayList<>();
            //Open results file.
            List<String> resultsFileContents = ReadWriteFileUtil.readFile(files.get(g), false);
            //Look through first row of data file to determine which positions in the array we need to look through.
            Map<String, Integer> columnPositions = new HashMap<>();
            String[] firstRow = resultsFileContents.get(0).split(",");
            for ( int i = 0; i < firstRow.length; i++ ) {
            	columnPositions.put(firstRow[i], i);
            }
            if ( !stillRunning ) { 
            	return;
            }
            //Append file name.
            if ( g != 0 ) { 
            	outputBuilder.append("\n\n\n");
            }
            outputBuilder.append("File: ");
            outputBuilder.append(files.get(g));
            //Now for each column position,
            performCalculationsOnColumns(columnPositions, resultsFileContents);
        }
        //Finished so set stillRunning to false.
        stillRunning = false;
    }
    
    /**
     * Perform calculations on each of the columns.
     * @param columnPositions a <code>Map</code> containing a mapping of column names to positions in the list.
     * @param resultsFileContents a <code>List</code> containing the results file contents to perform calculations on. 
     */
    public void performCalculationsOnColumns ( final Map<String,Integer> columnPositions, final List<String> resultsFileContents ) {
    	for ( int h = 0; h < columns.size(); h++ ) {
            //Ensure fileData is blank.
            fileData = new ArrayList<>();
            //Now go through rest of file contents and add data in the columnPosition of the row.
            try {
            	for ( int i = 1; i < resultsFileContents.size(); i++ ) {
            		String[] thisRow = resultsFileContents.get(i).split(",");
            		fileData.add(Double.parseDouble(thisRow[columnPositions.get(columns.get(h))]));
            	}
            	//Now call performCalculations method on the String LinkedList.
            	if (h != 0) { 
            		outputBuilder.append("\n");
            	}
            	outputBuilder.append("\n");
            	outputBuilder.append(performCalculations ( fileData, columns.get(h), statisticalFunctions));
            	if ( !stillRunning ) { 
        			return;
        		}
            } catch ( Exception e ) {
            	LOG.error("Error mit non-numerical data", e);
                outputBuilder.append("ERROR: Non-numerical data was present. Please ensure only numerical data is included except for column headings.");
            }
        }
    }
    
    /**
     * Method to perform all statistical calculations.
     * @param data a <code>List</code> containing the data to be used in calculations.
     * @param columnHeading a <code>String</code> containing the column heading currently being processed.
     * @param functions a <code>StatisticalFunctions List</code> containing the statistical measurements to calculate.
     * @return a <code>String</code> with the results to be presented in text area.
     */
    private String performCalculations ( List<Double> data, String columnHeading, List<StatisticalFunctions> functions ) {
        //Reset the output variable.
        StringBuilder outputStrBuilder = new StringBuilder();
        outputStrBuilder.append("Statistical Results for column ");
        outputStrBuilder.append(columnHeading);
        outputStrBuilder.append(":");
        //Try all of the following - printing out an arithmetic error if an error occurs.
        try {
            for (StatisticalFunctions function : functions) {
                outputStrBuilder.append("\n");
                outputStrBuilder.append(function.getDisplayName());
                outputStrBuilder.append(": ");
                outputStrBuilder.append(removeZeros("" + function.calculate(data)));
            }
        } catch ( Exception e ) {
        	LOG.error("Arithmetic error", e);
            outputStrBuilder.append("\nERROR: An arithmetic error has occurred. Cannot calculate remaining results");
        }
        //Finished so return output variable.
        return outputStrBuilder.toString();
    }
    
    /**
     * Method to return the output produced during calculation of statistical measurements.
     * @return a <code>String</code> containing the output.
     */
    public String getOutput () {
        return outputBuilder.toString();
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
        outputBuilder.append("\nWARNING: Processing was interrupted!");
    }
    
    private boolean fileAlreadyAdded ( final String fileToCheck, final List<String> currentFileList) {
    	boolean alreadyExists = false;
        for (String s : currentFileList) {
            if (s.equalsIgnoreCase(fileToCheck)) {
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
     * @return a <code>List</code> containing all files in subfolders of the specified directory.
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
    	List<String> filesToProcess = new ArrayList<>();
    	//Obtain a list for this directory of subfolders on the next level and files in this folder.
        String[] directoryFiles = new File(directory).list();
        if ( directoryFiles == null ) { 
        	return filesToProcess;
        }
        //Go through all files or subdirectories in this directory
        for (String directoryFile : directoryFiles) {
            //It is a csv file - great we add it to the list if we don't have it.
            if (directoryFile.endsWith(".csv")) {
                if (!fileAlreadyAdded(directoryFile, filesToProcess)) {
                    filesToProcess.add(directory + directoryFile);
                }
            } else if (!directoryFile.contains(".")) {
                //It must be a directory.
                filesToProcess.addAll(getFilesInDirectory(directory + directoryFile + "/"));
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
            StringBuilder outputTextBuilder = new StringBuilder();
            List<String> outputList = ReadWriteFileUtil.readFile(location, false);
            //Process file into single string with new line characters.
            for (String s : outputList) {
                outputTextBuilder.append(s);
                outputTextBuilder.append("\n");
            }
            //Return output text.
            return outputTextBuilder.toString();
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
        return ReadWriteFileUtil.writeFile(content, new File(editedLocation), true);
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
            List<String> outputList = ReadWriteFileUtil.readFile(location, false);
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
