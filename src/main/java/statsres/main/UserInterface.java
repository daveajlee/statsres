package statsres.main;

//Import java swing package.
import javax.swing.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//Import Statsres gui package.
import statsres.gui.*;

/**
 * UserInterface.java - Main User Interface processing class for Statsres.
 * @author David Lee.
 * @version 1.0.
 */
public class UserInterface {

    private JFrame theCurrentFrame;
    private boolean processRunning = false;
    
    private static final Logger LOG = LoggerFactory.getLogger(UserInterface.class);
    
    /**
     * Default constructor - initialise variables.
     */
    public UserInterface ( ) {
        theCurrentFrame = new JFrame();
    }
    
    /**
     * Method to set the current frame which is being displayed to the user.
     * @param currentFrame a <code>JFrame</code> object containing the current frame displayed to user.
     */
    public void setCurrentFrame ( JFrame currentFrame ) {
        theCurrentFrame = currentFrame;
    }
    
    /**
     * Method to return the current frame displayed to the user.
     * @return a <code>JFrame</code> object containing the current frame being displayed to user.
     */
    public JFrame getCurrentFrame ( ) {
        return theCurrentFrame;
    }
    
    /**
     * Method to set the value of process running.
     * @param flag a <code>boolean</code> value.
     */
    public void setProcessRunning ( boolean flag ) {
        processRunning = flag;
    }
    
    /**
     * Method to get the value of process running.
     * @return a <code>boolean</code> value.
     */
    public boolean getProcessRunning ( ) {
        return processRunning;
    }
    
    /**
     * Method to exit the program. Print warning dialog to user first.
     */
    public void exit ( final String title, final String dialogText ) {
        //Confirm user meant to exit.
        int result = showYesNoDialog(title, dialogText);
        if ( result == JOptionPane.YES_OPTION ) {
            //If yes, then exit.
            doExit();
        }
    }
    
    /**
     * Method to display a yes/no dialog with supplied text and then return result.
     * @return
     */
    public int showYesNoDialog ( final String title, final String dialogText ) {
    	return JOptionPane.showOptionDialog(theCurrentFrame,dialogText,title,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,new String[] { "Yes", "No" },"No");
    }
    
    /**
     * Method to perform the actual exit.
     */
    public void doExit() {
    	System.exit(0);
    }
    
    public void doSleep() {
    	try {
    		threadSleep();
    	} catch ( InterruptedException ie) {
            LOG.info("Sleep interrupted - moving to start screen");
        }
    }
    
    public void threadSleep() throws InterruptedException {
    	Thread.sleep(2000);
    }
    
    /**
     * Main method to run Statsres.
     * @param args a <code>String</code> arguments - not used in this version.
     */
    public static void main (String[] args) {
        //Display splash screen for two seconds then load Statsres.
    	UserInterface userInterface = new UserInterface();
        SplashWindow ss = new SplashWindow(false, userInterface, false);
        userInterface.doSleep();
        new StatsresGUI(userInterface, "", false,null, false);
        ss.dispose();
    }
    
}
