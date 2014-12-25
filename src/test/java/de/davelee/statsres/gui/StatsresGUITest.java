package de.davelee.statsres.gui;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import statsres.gui.StatsresGUI;
import statsres.main.StatsresSettings;
import statsres.main.UserInterface;

public class StatsresGUITest {
	
	@Test
	public void testAddHeaderInfo ( ) {
		StatsresGUI gui = new StatsresGUI();
		gui.addHeaderInfo(true);
	}
	
	@Test
	public void testCreateFileOptionsPanel ( ) {
		StatsresGUI gui = new StatsresGUI();
		assertNotNull(gui.createFileOptionsPanel("", true, true));
		assertNotNull(gui.createFileOptionsPanel("", false, true));
	}
	
	@Test
	public void testCreateResultsSelectionPanel ( ) {
		StatsresGUI gui = new StatsresGUI();
		assertNotNull(gui.createFileOptionsPanel("", false, true));
		assertNotNull(gui.createResultsSelectionPanel());
		String[] settingsArray = new String[] { "=test.txt","=true","=hello,bye","=true","=true","=true","=true","=true","=true","=true","=true","=true"};
		StatsresSettings settings = StatsresSettings.loadV1File(settingsArray);
		new StatsresGUI(new UserInterface(), "test.txt", true, settings, true);
	}
	
	@Test
	public void testCreateStatsOptionPanel ( ) {
		StatsresGUI gui = new StatsresGUI();
		assertNotNull(gui.createStatsOptionPanel());
	}
	
	@Test
	public void testButtonPanel ( ) {
		StatsresGUI gui = new StatsresGUI();
		assertNotNull(gui.createButtonPanel());
	}

	@Test
	public void testOutputPanel ( ) {
		StatsresGUI gui = new StatsresGUI();
		assertNotNull(gui.createOutputTextPanel());
	}
	
	@Test
	public void testOutputPane ( ) {
		StatsresGUI gui = new StatsresGUI();
		assertNotNull(gui.createOutputPane());
	}
	
	@Test
	public void testLocationBounds ( ) {
		StatsresGUI gui = new StatsresGUI();
		gui.setLocationBounds();
	}
	
	@Test
	public void testCreateDialogPanel ( ) {
		StatsresGUI gui = new StatsresGUI();
		assertNotNull(gui.createDialogPanel("", true, true));
	}
	
	@Test
	public void testClearFields ( ) {
		StatsresGUI gui = new StatsresGUI();
		assertNotNull(gui.createDialogPanel("", true, true));
		gui.clearFields();
	}
	
	@Test
	public void testSaveCurrentSettings ( ) {
		StatsresGUI gui = new StatsresGUI();
		assertNotNull(gui.createDialogPanel("", true, true));
		assertNotNull(gui.saveCurrentSettings());
	}
}
