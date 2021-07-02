package de.davelee.statsres.gui;

import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;

import de.davelee.statsres.main.UserInterface;

@Ignore
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
		assertNotNull(splashWindow);
		splashWindow.addHeaderInfo();
	}
	
	@Test
	public void testSetLocationBounds() {
		SplashWindow splashWindow = new SplashWindow();
		assertNotNull(splashWindow);
		splashWindow.setLocationBounds();
	}
	
	@Test
	public void testCreateMouseListener() {
		SplashWindow splashWindow = new SplashWindow();
		assertNotNull(splashWindow.createMouseListener());
	}
	
	@Test
	public void testConstructor() {
		SplashWindow splashWindow = new SplashWindow(true, new UserInterface(), true);
		assertNotNull(splashWindow);
		SplashWindow splashWindow2 = new SplashWindow(false, new UserInterface(), true);
		assertNotNull(splashWindow2);
	}

}
