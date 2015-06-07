package de.davelee.statsres.main;

import java.util.*;
import java.io.*;

import javax.swing.*;

import de.davelee.statsres.gui.*;

/**
 * This class runs processes in a thread for Statsres.
 * @author Dave Lee
 */
public class ProcessRunner extends Thread {

    private String resultsFileFolder;
    private List<String> columnHeadings;
    private List<StatisticalFunctions> statisticalFunctions;
    private JTextArea outputArea;
    private UserInterface userInterface;
    
    /**
     * Create a new runner for processes.
     * @param userInterface a <code>UserInterface</code> object representing the current user interface.
     * @param resultsFileFolder a <code>String</code> with the results file or all results file in the selected directory.
     * @param functions a <code>List</code> of <code>StatisticalFunctions</code> with the statistical functions selected by the user. 
     * @param ch a <code>List</code> of <code>String</code> with the column headings to be processed.
     * @param oa a <code>JTextArea</code> to display the output text.
     */
    public ProcessRunner ( UserInterface userInterface, String resultsFileFolder, List<StatisticalFunctions> functions, List<String> ch, JTextArea oa ) {
        this.resultsFileFolder = resultsFileFolder;
        statisticalFunctions = functions;
        columnHeadings = ch;
        outputArea = oa;
        this.userInterface = userInterface;
    }
    
    /**
     * Run the process based on the current variables in this <code>ProcessRunner</code> object.
     */
    public void run () {
        //First, error checking - does selected file/directory exist.
        if ( !new File(resultsFileFolder).exists() ) {
        	outputArea.setText("An invalid file or directory was selected. Please select another file or directory.");
        } else if ( columnHeadings.isEmpty() ) {
        	//Next, error checking - is at least one column heading selected.
        	outputArea.setText("No data columns were selected. Please select a data column from the column list.");
        } else if ( statisticalFunctions.isEmpty() ) {
        	//Final, error checking - are some stats options selected.
        	outputArea.setText("No statistical output was selected. Please select some statistical measurements and try again.");
        } else {
        	//Run program.
            outputArea.setText("");
            StatsresProg sp = new StatsresProg();
            //Create file input variable.
            List<String> fileList = new LinkedList<String>();
            //If multiple files,
            if ( resultsFileFolder.endsWith(".csv") ) {
            	fileList = sp.getAllFiles(resultsFileFolder);
            } else {
            	fileList.add(resultsFileFolder);
            }
            sp.setCalcParameters(fileList, columnHeadings, statisticalFunctions);
            WaitingScreen ws = new WaitingScreen(sp);
            sp.start();
            while ( sp.isStillRunning() ) {
            	//Process is running in separate thread ... do nothing here!
            }
            ws.dispose();
            outputArea.setText(sp.getOutput()); 
        } 
        userInterface.setProcessRunning(false);
    }
    
}
