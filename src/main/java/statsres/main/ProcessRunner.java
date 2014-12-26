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
    private List<String> theColumnHeadings;
    private List<StatisticalFunctions> statisticalFunctions;
    private JTextArea theOutputArea;
    private boolean isFolders;
    private UserInterface theInterface;
    
    public ProcessRunner ( UserInterface ui, String resFileFolder, List<StatisticalFunctions> functions, List<String> ch, JTextArea oa, boolean folders ) {
        theResultsFileFolder = resFileFolder;
        statisticalFunctions = functions;
        theColumnHeadings = ch;
        theOutputArea = oa;
        isFolders = folders;
        theInterface = ui;
    }
    
    public void run () {
        //First, error checking - does selected file/directory exist.
        if ( new File(theResultsFileFolder).exists() ) {
            //Next, error checking - is at least one column heading selected.
            if ( theColumnHeadings.size() != 0 ) {
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
                    sp.setCalcParameters(fileList, theColumnHeadings, statisticalFunctions);
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
                    theOutputArea.setText("No statistical output was selected. Please select some statistical measurements and try again.");
                }
            }
            else {
                theOutputArea.setText("No data columns were selected. Please select a data column from the column list.");
            }
        }
        else {
            theOutputArea.setText("An invalid file or directory was selected. Please select another file or directory.");
        }
        theInterface.setProcessRunning(false);
    }
    
}
