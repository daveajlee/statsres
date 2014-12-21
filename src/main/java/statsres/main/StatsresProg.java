package statsres.main;
//Import Java Util package.
import java.util.*;
//Import Java IO package.
import java.io.*;

/**
 * StatsresProg.java is the central processing class for Statsres.
 * @author David Lee.
 * @version 1.0.
 */
public class StatsresProg extends Thread {
    
    private LinkedList<String> fileData;
    
    //Variables to remember files, columns and outputs to allow run method in Thread class with no parameters to be executed as calculate tasks.
    private LinkedList<String> theFiles;
    private List<String> theColumns;
    private LinkedList<Boolean> theOutputs;
    //Variable to display output and boolean variable indicating whether finished or not.
    private String theOutput;
    private boolean isProcessing = false;
    
    /** Default constructor. */
    public StatsresProg() {
        fileData = new LinkedList<String>();
    }
    
    /**
     * Set the file, columns and output parameters for later calculation of statistical measurements.
     * Added in version 1.1 to enable better threading support.
     * @param files a <code>String</code> linkedlist containing the results file to process.
     * @param columns a <code>String</code> List containing the columns of the results file to process.
     * @param outputs a <code>boolean</code> linked list containing the statistical measurements required by user.
     */
    public void setCalcParameters ( LinkedList<String> files, List<String> columns, LinkedList<Boolean> outputs ) {
        theFiles = files;
        theColumns = columns;
        theOutputs = outputs;
        //Set isProcessing to true - indicates processing about to start.
        isProcessing = true;
    } 
    
    /**
     * Process file into linked list which can be used to calculate the desired statistical measurements.
     */
    public void run (  ) {
        theOutput = ""; //Initialise output.
        //Repeat for all files,
        for ( int g = 0; g < theFiles.size(); g++ ) {
            //Ensure fileData is blank.
            fileData = new LinkedList<String>();
            //Open results file.
            List<String> resultsFileContents = ReadWriteFile.readFile(theFiles.get(g), false);
            //Look through first row of data file to determine which positions in the array we need to look through.
            int[] columnPositions = new int[theColumns.size()];
            for ( int i = 0; i < columnPositions.length; i++ ) {
                columnPositions[i] = 0;
                if ( !isProcessing ) { return; }
            }
            String[] firstRow = resultsFileContents.get(0).split(",");
            for ( int i = 0; i < firstRow.length; i++ ) {
                //Check if this row is included in any of the columns.
                for ( int j = 0; j < theColumns.size(); j++ ) {
                    //For the moment, only support one column, so break when find first one.
                    if ( firstRow[i].equalsIgnoreCase(theColumns.get(j)) ) { columnPositions[j] = i; }
                    if ( !isProcessing ) { return; }
                }
            }
            //Append file name.
            if ( g == 0 ) { theOutput = "File: " + theFiles.get(0); }
            else { theOutput += "\n\n\nFile: " + theFiles.get(g); }
            //Now for each column position,
            for ( int h = 0; h < columnPositions.length; h++ ) {
                //Ensure fileData is blank.
                fileData = new LinkedList<String>();
                //Now go through rest of file contents and add data in the columnPosition of the row.
                for ( int i = 1; i < resultsFileContents.size(); i++ ) {
                    String[] thisRow = resultsFileContents.get(i).split(",");
                    fileData.add(thisRow[columnPositions[h]]);
                    if ( !isProcessing ) { return; }
                }
                //Now call performCalculations method on the String LinkedList.
                if (h == 0) { theOutput += "\n" + performCalculations ( fileData, theColumns.get(h), theOutputs); }
                else { theOutput += "\n\n" + performCalculations ( fileData, theColumns.get(h), theOutputs); }
            }
        }
        //Finished so set isProcessing to false.
        isProcessing = false;
    }
    
    /**
     * Method to perform all statistical calculations.
     * @param data a <code>LinkedList</code> containing the data to be used in calculations.
     * @param columnHeading a <code>String</code> containing the column heading currently being processed.
     * @param outputs a <code>boolean</code> array containing the statistical measurements to calculate.
     * @return a <code>String</code> with the results to be presented in text area.
     */
    private String performCalculations ( LinkedList<String> data, String columnHeading, LinkedList<Boolean> outputs ) {
        //Reset the output variable.
        String output = "Statistical Results for column " + columnHeading + ":";
        //Convert data into numerical data to perform calculations.
        double[] numericalData = new double[data.size()];
        try {
            for ( int i = 0; i < numericalData.length; i++ ) {
                numericalData[i] = Double.parseDouble(data.get(i));
            }
        }
        catch ( Exception e ) {
            return ("ERROR: Non-numerical data was present. Please ensure only numerical data is included except for column headings.");
        }
        //Now process statistical outputs.
        double oneQuartile = -1; double threeQuartile = -1; double mean = -1;
        //Try all of the following - printing out an arithmetic error if an error occurs.
        try {
            //Mean.
            if ( outputs.get(0) || outputs.get(8) ) {
            	mean = StatisticalFunctions.MEAN.calculate(numericalData);
                if (outputs.get(0)) { output += "\nMean: " + removeZeros("" + mean); }
            }
            //Min.
            if ( outputs.get(1) ) {
                double min = StatisticalFunctions.MIN.calculate(numericalData);
                output += "\nMinimum Value: " + removeZeros("" + min);
            }
            //Max.
            if ( outputs.get(2) ) {
                double max = StatisticalFunctions.MAX.calculate(numericalData);
                output += "\nMaximum Value: " + removeZeros("" + max);
            }
            //Median.
            if ( outputs.get(3) ) {
                double median = StatisticalFunctions.MEDIAN.calculate(numericalData);
                output += "\nMedian: " + removeZeros("" + median); 
            }
            //Count.
            if ( outputs.get(4) ) {
                double count = StatisticalFunctions.COUNT.calculate(numericalData);
                output += "\nNumber of Data Values: " + removeZeros("" + count);
            }   
            //1st Quartile.
            if ( outputs.get(5) || outputs.get(7) ) {
                oneQuartile = StatisticalFunctions.QUARTILE_FIRST.calculate(numericalData);
                if (outputs.get(5) ) { output += "\n1st Quartile: " + removeZeros("" + oneQuartile); }
            }
            //3rd Quartile.
            if ( outputs.get(6) || outputs.get(7) ) {
                threeQuartile = StatisticalFunctions.QUARTILE_THIRD.calculate(numericalData);
                if (outputs.get(6)) { output += "\n3rd Quartile: " + removeZeros("" + threeQuartile); }
            }
            //IQR.
            if ( outputs.get(7) ) {
                double iqr = StatisticalFunctions.INTER_QUARTILE_RANGE.calculate(numericalData);
                output += "\nInterquartile Range: " + removeZeros("" + iqr);
            }
            //Standard Deviation.
            if ( outputs.get(8) ) {
            	double stDev = StatisticalFunctions.STANDARD_DEVIATION.calculate(numericalData);
                output += "\nStandard Deviation: " + removeZeros("" + stDev);
            }
        } catch ( Exception e ) {
            output += "\nERROR: An arithmetic error has occurred. Cannot calculate remaining results";
            return output;
        }
        //Finished so return output variable.
        return output;
    }
    
    /**
     * Method to return the output produced during calculation of statistical measurements.
     * @return a <code>String</code> containing the output.
     */
    public String getOutput () {
        return theOutput;
    }
    
    /**
     * Method to return whether or not processing is still running for checking different threads.
     * @return a <code>boolean</code> which is true iff processing is still running.
     */
    public boolean isStillRunning () {
        return isProcessing;
    }
    
    /**
     * Method to stop processing and add message to output saying that processing was interrupted.
     */
    public void stopProcessing () {
        isProcessing = false;
        theOutput += "\nWARNING: Processing was interrupted!";
    }
    
    /**
     * Method to return all files with the extension .csv in subfolders
     * of the folder supplied by the user.
     * @param file a <code>String</code> with the folder of which subfolders should be searched.
     */
    public LinkedList<String> getAllFiles ( String file ) {
        LinkedList<String> filesToProcess = new LinkedList<String>();
        boolean areMoreFiles = true; String currentFile = file; int fileCounter = 0; boolean setFileCounter = false;
        while ( areMoreFiles ) {
            String[] files = getSubFiles(currentFile);
            if ( currentFile.endsWith(".csv") ) {
                boolean dontadd = false;
                for ( int i = 0; i < filesToProcess.size(); i++ ) {
                    String one = currentFile;
                    String two = filesToProcess.get(i);
                    if ( one.equalsIgnoreCase(two) ) {
                        dontadd = true; break;
                    }
                }
                if (!dontadd) { filesToProcess.add(currentFile); }
            }
            if ( files != null ) {
                for ( int i = 0; i < files.length; i++ ) {
                    if ( files[i].endsWith(".csv") ) {
                        boolean dontadd = false;
                        for ( int j = 0; j < filesToProcess.size(); j++ ) {
                            String one = currentFile.replace("\\", "") + files[i].replace("\\", "");
                            String two = filesToProcess.get(j).replace("\\", "");
                            if ( one.equalsIgnoreCase(two) ) {
                                dontadd = true; break;
                            }
                        }
                        if (!dontadd) { filesToProcess.add(currentFile + "\\" + files[i]); }
                    }
                }
                if ( !setFileCounter ) { fileCounter = 0; }
                if ( fileCounter < files.length ) {
                    if ( !currentFile.endsWith("\\") ) {
                        currentFile += "\\" + files[fileCounter];
                    }
                    else {
                        currentFile += files[fileCounter] + "\\";
                    }
                    setFileCounter = false;
                }
                else {
                    String[] fileSplit = currentFile.split("\\\\"); currentFile = "";
                    for ( int i = 0; i < fileSplit.length-1; i++ ) {
                        if ( !fileSplit[i].equalsIgnoreCase("") ) {
                            currentFile += fileSplit[i] + "\\";
                        }
                    }
                    //Find location of previous file in array to start from there.
                    String[] aboveFiles = getSubFiles(currentFile);
                    fileCounter = -1;
                    for ( int j = 0; j < aboveFiles.length; j++ ) {
                        if ( aboveFiles[j].equalsIgnoreCase(fileSplit[fileSplit.length-1]) ) {
                            fileCounter = (j+1); setFileCounter = true;
                            break;
                        }
                    }
                }
            }
            else {
                String[] fileSplit = currentFile.split("\\\\"); currentFile = "";
                for ( int i = 0; i < fileSplit.length-1; i++ ) {
                     if ( !fileSplit[i].equalsIgnoreCase("") ) {
                        currentFile += fileSplit[i] + "\\";
                     }
                }
                fileCounter++; setFileCounter = true;
            }
            String checkFile2 = "";
            String[] fileSplit2 = file.split("\\\\");
            for ( int i = 0; i < fileSplit2.length-1; i++ ) {
                if ( !fileSplit2[i].equalsIgnoreCase("") ) {
                    checkFile2 += fileSplit2[i] + "\\";
                }
            }
            if ( currentFile.equalsIgnoreCase(checkFile2) ) {
                areMoreFiles = false;
            }
        }
        //Return fileToProcess list.
        return filesToProcess;
    }
    
    /**
     * Method to save current output to a text file.
     * @param output a <code>String</code> containing the currently displayed output.
     * @param location a <code>String</code> containing the file to save to.
     * @return a <code>boolean</code> indicating success of file writing.
     */
    public boolean saveOutputFile ( String output, String location ) {
        //First check if need to add extension.
        if ( !location.endsWith(".sro") ) {
            location += ".sro";
        }
        //Then write file.
        LinkedList<String> out = new LinkedList<String>();
        out.add(output);
        return ReadWriteFile.writeFile(out, new File(location), false);
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
        }
        //Now read in file.
        else {
            String outputText = "";
            List<String> output = ReadWriteFile.readFile(location, false);
            //Process file into single string with new line characters.
            for ( int i = 0; i < output.size(); i++ ) {
                outputText += output.get(i) + "\n";
            }
            //Return output text.
            return outputText;
        }
    }
    
    /**
     * Method to save current settings to a text file.
     * @param settings a <code>String</code> linked list containing the current settings.
     * @param location a <code>String</code> containing the file to save to.
     * @return a <code>boolean</code> indicating success of file writing.
     */
    public boolean saveSettingsFile ( LinkedList<String> settings, String location ) {
        //First check if need to add extension.
        if ( !location.endsWith(".srs") ) {
            location += ".srs";
        }
        //Then write file.
        return ReadWriteFile.writeFile(settings, new File(location), false);
    }
    
    /**
     * Method to load settings from a text file.
     * @param location a <code>String</code> containing the file to be read.
     * @return a <code>String</code> array containing the settings to be used.
     */
    public String[] loadSettingsFile ( String location ) {
        //Check for valid extension.
        if ( !location.endsWith(".srs") ) {
            return null;
        }
        //Now read in file.
        else {
            //Create options listing.
            String[] settings = new String[12];
            //Read in file.
            List<String> output = ReadWriteFile.readFile(location, false);
            //Error checking - all settings must be present.
            if ( output.size() != settings.length ) { return null; }
            //Split string - second part goes into settings array.
            for ( int i = 0; i < output.size(); i++ ) {
                settings[i] = output.get(i).split("=")[1];
            }
            //Return settings array.
            return settings;
        }
    }
    
    /**
     * Private helper method to determine if there are more files below this one.
     * @param file a <code>String</code> with the current location.
     * @return a <code>String</code> array of files in that location.
     */
     private String[] getSubFiles ( String file ) {
        return new File ( file ).list();
     }

     /** 
     * Private helper method to remove zeros from strings where they are not required.
     * @param doubleStr a <code>String</code> with zeros to be removed.
     * @return a <code>String</code> with/without zeros as appropriate.
     */
     private String removeZeros ( String doubleStr ) {
        int startPos = doubleStr.indexOf("."); boolean removeZeros = true;
        for ( int j = (startPos+1); j < doubleStr.length(); j++ ) {
            if ( !doubleStr.substring(j, j+1).equalsIgnoreCase("0") ) {
                removeZeros = false;
            }
        }
        if ( removeZeros ) { doubleStr = doubleStr.substring(0, startPos); }
        return doubleStr;
     }
     
}
