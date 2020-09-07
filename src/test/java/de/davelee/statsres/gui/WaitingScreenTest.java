package de.davelee.statsres.gui;

import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;

import de.davelee.statsres.gui.WaitingScreen;

@Ignore
public class WaitingScreenTest {
	
	@Test
	public void testAddHeaderInfos ( ) {
		WaitingScreen screen = new WaitingScreen();
		assertNotNull(screen);
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
		assertNotNull(screen);
		screen.setLocation();
	}

}
