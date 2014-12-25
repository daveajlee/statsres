package de.davelee.statsres.gui;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import statsres.gui.SplashWindow;

public class SplashWindowTest {
	
	@Test
	public void testCreateCenterPanelAboutScreen() {
		SplashWindow splashWindow = new SplashWindow();
		assertNotNull(splashWindow.createCenterPanel(true));
	}
	
	@Test
	public void testCreateCenterPanel() {
		SplashWindow splashWindow = new SplashWindow();
		assertNotNull(splashWindow.createCenterPanel(false));
	}
	
	@Test
	public void testAddHeaderInfos() {
		SplashWindow splashWindow = new SplashWindow();
		splashWindow.addHeaderInfo();
	}
	

}
