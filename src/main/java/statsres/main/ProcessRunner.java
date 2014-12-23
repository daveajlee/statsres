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
    private JList<String> theColumnHeadings;
    private List<StatisticalFunctions> statisticalFunctions;
    private JTextArea theOutputArea;
    private boolean isFolders;
    private UserInterface theInterface;
    private JFrame theGUI;
    
    public ProcessRunner ( UserInterface ui, String resFileFolder, List<StatisticalFunctions> functions, JList<String> ch, JTextArea oa, boolean folders, JFrame gui ) {
        theInterface = ui;
        theResultsFileFolder = resFileFolder;
        statisticalFunctions = functions;
        theColumnHeadings = ch;
        theOutputArea = oa;
        isFolders = folders;
        theGUI = gui;
    }
    
    public void run () {
        //First, error checking - does selected file/directory exist.
        if ( new File(theResultsFileFolder).exists() ) {
            //Next, error checking - is at least one column heading selected.
            if ( theColumnHeadings.getSelectedValuesList().size() != 0 ) {
                //Final, error checking - are some stats options selected.
                if ( statisticalFunctions.size() > 0 ) {
                    //Run program.
                    theOutputArea.setText("");
                    StatsresProg sp = new StatsresProg();
                    //Create file input variable.
                    List<String> fileList = new LinkedList<String>();
                    //If multiple files,
                    if ( isFolders ) {
                        fileList = sp.getAllFiles(theResultsFileFolder);
                    }
                    else {
                        fileList.add(theResultsFileFolder);
                    }
                    sp.setCalcParameters(fileList, theColumnHeadings.getSelectedValuesList(), statisticalFunctions);
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
