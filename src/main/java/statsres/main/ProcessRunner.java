/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package statsres.main;

import statsres.gui.*;
import java.util.*;
import java.io.*;
import javax.swing.*;

/**
 *
 * @author Dave
 */
public class ProcessRunner extends Thread {

    private String theResultsFileFolder;
    private JList theColumnHeadings;
    private LinkedList<Boolean> theStatsOptions;
    private JTextArea theOutputArea;
    private boolean isFolders;
    private UserInterface theInterface;
    private JFrame theGUI;
    
    public ProcessRunner ( UserInterface ui, String resFileFolder, LinkedList<Boolean> so, JList ch, JTextArea oa, boolean folders, JFrame gui ) {
        theInterface = ui;
        theResultsFileFolder = resFileFolder;
        theStatsOptions = so;
        theColumnHeadings = ch;
        theOutputArea = oa;
        isFolders = folders;
        theGUI = gui;
    }
    
    public void run () {
        //First, error checking - does selected file/directory exist.
        File f = new File(theResultsFileFolder);
        if ( f.exists() ) {
            //Next, error checking - is at least one column heading selected.
            if ( theColumnHeadings.getSelectedValues().length != 0 ) {
                //Final, error checking - are some stats options selected.
                int booleanCount = 0;
                for ( int i = 0; i < theStatsOptions.size(); i++ ) {
                    if ( theStatsOptions.get(i) == true ) { booleanCount++; }
                }
                if ( booleanCount != 0 ) {
                    //Convert the column headings into a string array.
                    String[] columns = new String[theColumnHeadings.getSelectedValues().length];
                    for ( int i = 0; i < columns.length; i++ ) {
                        columns[i] = theColumnHeadings.getSelectedValues()[i].toString();
                    }
                    //Run program.
                    theOutputArea.setText("");
                    StatsresProg sp = new StatsresProg();
                    //Create file input variable.
                    LinkedList<String> fileList = new LinkedList<String>();
                    //If multiple files,
                    if ( isFolders ) {
                        fileList = sp.getAllFiles(theResultsFileFolder);
                    }
                    else {
                        fileList.add(theResultsFileFolder);
                    }
                    sp.setCalcParameters(fileList, columns, theStatsOptions);
                    //theInterface.getCurrentFrame().dispose();
                    WaitingScreen ws = new WaitingScreen(sp);
                    sp.start();
                    while ( sp.isStillRunning() ) {
                        //Process is running in separate thread ... do nothing here!
                    }
                    ws.dispose();
                    theOutputArea.setText(sp.getOutput());
                }
                else {
                    JOptionPane.showMessageDialog(theGUI, "No statistical output was selected. Please select some statistical measurements and try again.", "ERROR: No Statistical Output Selected", JOptionPane.ERROR_MESSAGE);
                }
            }
            else {
                JOptionPane.showMessageDialog(theGUI, "No data columns were selected. Please select a data column from the column list.", "ERROR: No Data Column Selected", JOptionPane.ERROR_MESSAGE);
            }
        }
        else {
            JOptionPane.showMessageDialog(theGUI, "An invalid file or directory was selected. Please select another file or directory.", "ERROR: Invalid File/Directory", JOptionPane.ERROR_MESSAGE);
        }
        theInterface.setProcessRunning(false);
    }
    
}
