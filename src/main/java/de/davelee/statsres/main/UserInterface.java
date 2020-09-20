package de.davelee.statsres.main;

//Import java swing package.
import javax.swing.*;

import de.davelee.statsres.gui.SplashWindow;
import de.davelee.statsres.gui.StatsresGUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main User Interface processing class for Statsres.
 * @author Dave Lee.
 */
public class UserInterface {

    private JFrame currentFrame;
    private boolean processRunning = false;
    
    private String exitDialogTitle;
    private String exitDialogMessage;
    
    private static final Logger LOG = LoggerFactory.getLogger(UserInterface.class);
    
    /**
     * Default constructor - initialise variables.
     */
    public UserInterface ( ) {
        currentFrame = new JFrame();
        setExitDialogTitle("Please Confirm");
        setExitDialogMessage("Are you sure you wish to exit 'Statsres'?");
    }
    
    /**
     * Method to set the current frame which is being displayed to the user.
     * @param currentFrame a <code>JFrame</code> object containing the current frame displayed to user.
     */
    public void setCurrentFrame ( JFrame currentFrame ) {
        this.currentFrame = currentFrame;
    }
    
    /**
     * Method to return the current frame displayed to the user.
     * @return a <code>JFrame</code> object containing the current frame being displayed to user.
     */
    public JFrame getCurrentFrame ( ) {
        return currentFrame;
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
     * Return the title for the exit dialog.
     * @return a <code>String</code> representing the title of the exit dialog.
     */
    public String getExitDialogTitle() {
		return exitDialogTitle;
	}

    /**
     * Set the title for the exit dialog.
     * @param exitDialogTitle a <code>String</code> representing the title of the exit dialog.
     */
	public void setExitDialogTitle(final String exitDialogTitle) {
		this.exitDialogTitle = exitDialogTitle;
	}

	/**
     * Return the message for the exit dialog.
     * @return a <code>String</code> representing the message of the exit dialog.
     */
	public String getExitDialogMessage() {
		return exitDialogMessage;
	}

	/**
     * Set the message for the exit dialog.
     * @param exitDialogMessage a <code>String</code> representing the message of the exit dialog.
     */
	public void setExitDialogMessage(final String exitDialogMessage) {
		this.exitDialogMessage = exitDialogMessage;
	}

	/**
     * Method to exit the program. Print warning dialog to user first.
     */
    public void exit ( ) {
        //Confirm user meant to exit.
        int result = showYesNoDialog(getExitDialogTitle(), getExitDialogMessage());
        if ( result == JOptionPane.YES_OPTION ) {
            //If yes, then exit.
            doExit();
        }
    }
    
    /**
     * Method to display a yes/no dialog with supplied text and then return result.
     * @param title a <code>String</code> with the title for the dialog.
     * @param dialogText a <code>String</code> with the text for the dialog.
     * @return a <code>int</code> with the result of the dialog.
     */
    public int showYesNoDialog ( final String title, final String dialogText ) {
        ImageIcon imageIcon = new ImageIcon(UserInterface.class.getResource("/statsres-logo-icon.png"));
    	return JOptionPane.showOptionDialog(currentFrame,dialogText,title,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,imageIcon,new String[] { "Yes", "No" },"No");
    }
    
    /**
     * Method to perform the actual exit.
     */
    public void doExit() {
    	System.exit(0);
    }
    
    /**
     * Method to set the thread to sleep.
     */
    public void doSleep() {
    	try {
    		threadSleep();
    	} catch ( InterruptedException ie) {
            LOG.info("Sleep interrupted - moving to start screen");
        }
    }
    
    /**
     * Control how long a thread sleeps and throw an execution if interrupted.
     * @throws InterruptedException a <code>InterruptedException</code> which is thrown iff the pause is interrupted.
     */
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
        new StatsresGUI(userInterface, new StatsresProg(), "", null, false);
        ss.dispose();
    }
    
}
