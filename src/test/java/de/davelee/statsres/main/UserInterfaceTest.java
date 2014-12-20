package de.davelee.statsres.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import javax.swing.JFrame;

import org.junit.Test;

import statsres.main.UserInterface;

public class UserInterfaceTest {
	
	@Test
	public void testVersion() {
		UserInterface userInterface = new UserInterface();
		assertEquals(userInterface.getVersion(), "1.1");
	}
	
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

}
