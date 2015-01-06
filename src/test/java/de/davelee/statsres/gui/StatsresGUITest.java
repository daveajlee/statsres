package de.davelee.statsres.gui;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import statsres.gui.StatsresGUI;
import statsres.main.StatsresSettings;
import statsres.main.UserInterface;

public class StatsresGUITest {
	
	@Test
	public void testAddHeaderInfo ( ) {
		StatsresGUI gui = new StatsresGUI(new UserInterface(), "", null, true);
		gui.addHeaderInfo(true);
	}
	
	@Test
	public void testCreateFileOptionsPanel ( ) {
		StatsresGUI gui = new StatsresGUI(new UserInterface(), "", null, true);
		assertNotNull(gui.createFileOptionsPanel("", true));
		assertNotNull(gui.createFileOptionsPanel("", true));
		assertNotNull(gui.createFileOptionsPanel(this.getClass().getResource("/subfolder/subsubfolder/subsubfolder.csv").getFile(), true));
		assertNotNull(gui.createResultsSelectionPanel());
		assertNotNull(gui.createFileOptionsPanel(this.getClass().getResource("/subfolder").getFile(), true));
		assertNotNull(gui.createResultsSelectionPanel());
	}
	
	@Test
	public void testCreateResultsSelectionPanel ( ) {
		StatsresGUI gui = new StatsresGUI(new UserInterface(), "", null, true);
		assertNotNull(gui.createFileOptionsPanel("", true));
		assertNotNull(gui.createResultsSelectionPanel());
		String[] settingsArray = new String[] { "=test.txt","=true","=hello,bye","=true","=true","=true","=true","=true","=true","=true","=true","=true"};
		StatsresSettings settings = StatsresSettings.loadV1File(settingsArray);
		new StatsresGUI(new UserInterface(), "test.txt", settings, true);
	}
	
	@Test
	public void testCreateStatsOptionPanel ( ) {
		StatsresGUI gui = new StatsresGUI(new UserInterface(), "", null, true);
		assertNotNull(gui.createStatsOptionPanel());
	}
	
	@Test
	public void testButtonPanel ( ) {
		StatsresGUI gui = new StatsresGUI(new UserInterface(), "", null, true);
		assertNotNull(gui.createButtonPanel());
	}

	@Test
	public void testOutputPanel ( ) {
		StatsresGUI gui = new StatsresGUI(new UserInterface(), "", null, true);
		assertNotNull(gui.createOutputTextPanel());
	}
	
	@Test
	public void testOutputPane ( ) {
		StatsresGUI gui = new StatsresGUI(new UserInterface(), "", null, true);
		assertNotNull(gui.createOutputPane());
	}
	
	@Test
	public void testLocationBounds ( ) {
		StatsresGUI gui = new StatsresGUI(new UserInterface(), "", null, true);
		gui.setLocationBounds();
	}
	
	@Test
	public void testCreateDialogPanel ( ) {
		StatsresGUI gui = new StatsresGUI(new UserInterface(), "", null, true);
		assertNotNull(gui.createDialogPanel("", true));
	}
	
	@Test
	public void testClearFields ( ) {
		StatsresGUI gui = new StatsresGUI(new UserInterface(), "", null, true);
		assertNotNull(gui.createDialogPanel("", true));
		gui.clearFields();
	}
	
	@Test
	public void testSaveCurrentSettings ( ) {
		StatsresGUI gui = new StatsresGUI(new UserInterface(), "", null, true);
		assertNotNull(gui.createDialogPanel("", true));
		assertNotNull(gui.saveCurrentSettings());
		gui.deselectAllStatOptions();
		assertNotNull(gui.saveCurrentSettings());
	}
	
	@Test
	public void testSettingsNotNull ( ) {
		StatsresGUI gui = new StatsresGUI(new UserInterface(), "", StatsresSettings.createDefaultSettings(""), true);
		assertNotNull(gui);
	}
}
