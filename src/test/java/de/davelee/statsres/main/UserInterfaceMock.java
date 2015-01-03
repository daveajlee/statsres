package de.davelee.statsres.main;

import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import statsres.main.UserInterface;

public class UserInterfaceMock extends UserInterface {
	
	private static final Logger LOG = LoggerFactory.getLogger(UserInterfaceMock.class);
	
	/**
     * Method to display a yes/no dialog with supplied text and then return result.
     * @return
     */
    public int showYesNoDialog ( final String title, final String dialogText ) {
    	if ( title.contentEquals("NoDialog")) {
    		return JOptionPane.NO_OPTION;
    	}
    	return JOptionPane.YES_OPTION;
    }
    
    public void doExit ( ) {
    	LOG.info("Exit mock reached - does not actually exit");
    }
    
    public void threadSleep ( ) throws InterruptedException {
    	super.threadSleep();
    	throw new InterruptedException();
    }

}
