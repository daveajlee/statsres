package de.davelee.statsres.gui;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import de.davelee.statsres.gui.WaitingScreen;

public class WaitingScreenTest {
	
	@Test
	public void testAddHeaderInfos ( ) {
		WaitingScreen screen = new WaitingScreen();
		screen.addHeaderInfo();
	}
	
	@Test
	public void testCreateCenterPanel() {
		WaitingScreen screen = new WaitingScreen();
		assertNotNull(screen.createCenterPanel());
	}
	
	@Test
	public void testSetLocation ( ) {
		WaitingScreen screen = new WaitingScreen();
		screen.setLocation();
	}

}
