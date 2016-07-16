package de.davelee.statsres.gui;

import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;

import de.davelee.statsres.gui.WaitingScreen;

public class WaitingScreenTest {
	
	@Test
	@Ignore
	public void testAddHeaderInfos ( ) {
		WaitingScreen screen = new WaitingScreen();
		screen.addHeaderInfo();
	}
	
	@Test
	@Ignore
	public void testCreateCenterPanel() {
		WaitingScreen screen = new WaitingScreen();
		assertNotNull(screen.createCenterPanel());
	}
	
	@Test
	@Ignore
	public void testSetLocation ( ) {
		WaitingScreen screen = new WaitingScreen();
		screen.setLocation();
	}

}
