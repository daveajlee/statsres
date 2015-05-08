package de.davelee.statsres.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.junit.Test;

import de.davelee.statsres.main.UserInterface;

public class UserInterfaceTest {
	
	@Test
	public void testCurrentFrame() {
		UserInterface userInterface = new UserInterface();
		assertNotNull(userInterface.getCurrentFrame());
		JFrame newFrame = new JFrame();
		userInterface.setCurrentFrame(newFrame);
		assertEquals(newFrame, userInterface.getCurrentFrame());
		userInterface.setCurrentFrame(null);
		assertNull(userInterface.getCurrentFrame());
	}
	
	@Test
	public void testProcessRunning() {
		UserInterface userInterface = new UserInterface();
		assertFalse(userInterface.getProcessRunning());
		userInterface.setProcessRunning(true);
		assertTrue(userInterface.getProcessRunning());
	}
	
	@Test
	public void testExit() {
		UserInterface userInterface = new UserInterfaceMock();
		userInterface.setExitDialogTitle("Please Confirm");
		userInterface.exit();
		userInterface.setExitDialogTitle("NoDialog");
		userInterface.exit();
	}
	
	@Test
	public void testYesNoDialog() {
		UserInterface userInterface = new UserInterfaceMock();
		assertEquals(userInterface.showYesNoDialog("NoDialog", "No2Dialog"), JOptionPane.NO_OPTION);
		assertEquals(userInterface.showYesNoDialog("YesDialog", "YesDialog"), JOptionPane.YES_OPTION);
	}
	
	@Test
	public void testSleep() {
		UserInterface userInterface = new UserInterfaceMock();
		userInterface.doSleep();
	}

}
