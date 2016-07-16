package de.davelee.statsres.gui;

import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;

import de.davelee.statsres.gui.SplashWindow;
import de.davelee.statsres.main.UserInterface;

public class SplashWindowTest {
	
	@Test
	@Ignore
	public void testCreateCenterPanelAboutScreen() {
		SplashWindow splashWindow = new SplashWindow();
		assertNotNull(splashWindow.createCenterPanel(true));
	}
	
	@Test
	@Ignore
	public void testCreateCenterPanel() {
		SplashWindow splashWindow = new SplashWindow();
		assertNotNull(splashWindow.createCenterPanel(false));
	}
	
	@Test
	@Ignore
	public void testAddHeaderInfos() {
		SplashWindow splashWindow = new SplashWindow();
		splashWindow.addHeaderInfo();
	}
	
	@Test
	@Ignore
	public void testSetLocationBounds() {
		SplashWindow splashWindow = new SplashWindow();
		splashWindow.setLocationBounds();
	}
	
	@Test
	@Ignore
	public void testCreateMouseListener() {
		SplashWindow splashWindow = new SplashWindow();
		assertNotNull(splashWindow.createMouseListener());
	}
	
	@Test
	@Ignore
	public void testConstructor() {
		SplashWindow splashWindow = new SplashWindow(true, new UserInterface(), true);
		assertNotNull(splashWindow);
		SplashWindow splashWindow2 = new SplashWindow(false, new UserInterface(), true);
		assertNotNull(splashWindow2);
	}

}
