package de.davelee.statsres.gui;

import javax.swing.filechooser.FileNameExtensionFilter;

import org.junit.Test;

import de.davelee.statsres.gui.StatsresGUI;
import de.davelee.statsres.main.StatsresProg;
import de.davelee.statsres.main.StatsresProgMock;
import de.davelee.statsres.main.StatsresSettings;
import de.davelee.statsres.main.UserInterface;

import static org.junit.Assert.*;

public class StatsresGUITest {
	
	@Test
	public void testAddHeaderInfo ( ) {
		StatsresGUI gui = new StatsresGUI(new UserInterface(), new StatsresProg(), "", null, true);
		assertNotNull(gui);
		gui.addHeaderInfo(true);
	}
	
	@Test
	public void testCreateFileOptionsPanel ( ) {
		StatsresGUI gui = new StatsresGUI(new UserInterface(), new StatsresProg(), "", null, true);
		assertNotNull(gui.createFileOptionsPanel("", true));
		assertNotNull(gui.createFileOptionsPanel("", true));
		assertNotNull(gui.createFileOptionsPanel(this.getClass().getResource("/subfolder/subsubfolder/subsubfolder.csv").getFile(), true));
		assertNotNull(gui.createResultsSelectionPanel());
		assertNotNull(gui.createFileOptionsPanel(this.getClass().getResource("/subfolder").getFile(), true));
		assertNotNull(gui.createResultsSelectionPanel());
	}
	
	@Test
	public void testCreateResultsSelectionPanel ( ) {
		StatsresGUI gui = new StatsresGUI(new UserInterface(), new StatsresProg(), "", null, true);
		assertNotNull(gui.createFileOptionsPanel("", true));
		assertNotNull(gui.createResultsSelectionPanel());
		String trueStr = "=true";
		String[] settingsArray = new String[] { "=test.txt",trueStr,"=hello,bye",trueStr,trueStr,trueStr,trueStr,trueStr,trueStr,trueStr,trueStr,trueStr};
		StatsresSettings settings = StatsresSettings.loadV1File(settingsArray);
		new StatsresGUI(new UserInterface(), new StatsresProg(), "test.txt", settings, true);
	}
	
	@Test
	public void testCreateStatsOptionPanel ( ) {
		StatsresGUI gui = new StatsresGUI(new UserInterface(), new StatsresProg(), "", null, true);
		assertNotNull(gui.createStatsOptionPanel());
	}
	
	@Test
	public void testButtonPanel ( ) {
		StatsresGUI gui = new StatsresGUI(new UserInterface(), new StatsresProg(), "", null, true);
		assertNotNull(gui.createButtonPanel());
	}

	@Test
	public void testOutputPanel ( ) {
		StatsresGUI gui = new StatsresGUI(new UserInterface(), new StatsresProg(), "", null, true);
		assertNotNull(gui.createOutputTextPanel());
	}
	
	@Test
	public void testOutputPane ( ) {
		StatsresGUI gui = new StatsresGUI(new UserInterface(), new StatsresProg(), "", null, true);
		assertNotNull(gui.createOutputPane());
	}
	
	@Test
	public void testLocationBounds ( ) {
		StatsresGUI gui = new StatsresGUI(new UserInterface(), new StatsresProg(), "", null, true);
		assertNotNull(gui);
		gui.setLocationBounds();
	}
	
	@Test
	public void testCreateDialogPanel ( ) {
		StatsresGUI gui = new StatsresGUI(new UserInterface(), new StatsresProg(), "", null, true);
		assertNotNull(gui.createDialogPanel("", true));
	}
	
	@Test
	public void testClearFields ( ) {
		StatsresGUI gui = new StatsresGUI(new UserInterface(), new StatsresProg(), "", null, true);
		assertNotNull(gui.createDialogPanel("", true));
		gui.clearFields();
	}
	
	@Test
	public void testSaveCurrentSettings ( ) {
		StatsresGUI gui = new StatsresGUI(new UserInterface(), new StatsresProg(), "", null, true);
		assertNotNull(gui.createDialogPanel("", true));
		assertNotNull(gui.saveCurrentSettings());
		gui.deselectAllStatOptions();
		assertNotNull(gui.saveCurrentSettings());
	}
	
	@Test
	public void testSettingsNotNull ( ) {
		StatsresGUI gui = new StatsresGUI(new UserInterface(), new StatsresProg(), "", StatsresSettings.createDefaultSettings(""), true);
		assertNotNull(gui);
	}
	
	@Test
	public void testMenu ( ) {
		StatsresGUI gui = new StatsresGUI(new UserInterface(), new StatsresProgMock(), "", StatsresSettings.createDefaultSettings(""), true);
		String testFileNameGood = "status.srs";
		assertTrue(gui.saveSettingsMenu(testFileNameGood));
		gui.saveSettingsMenu("status.sro");
		gui.saveOutputMenu(testFileNameGood);
		gui.saveOutputMenu("status.sro");
		gui.loadSettingsMenu(true, testFileNameGood);
		gui.loadSettingsMenu(true, "settings.srs");
		gui.loadOutputMenu("output.sro");
		gui.loadOutputMenu("output2.sro");
	}
	
	@Test
	public void testFileExtension ( ) {
		StatsresGUI gui = new StatsresGUI(new UserInterface(), new StatsresProgMock(), "", StatsresSettings.createDefaultSettings(""), true);
		FileNameExtensionFilter filter = gui.createFileNameExtensionFilter("Settings File");
		assertEquals(filter.getExtensions()[0], "srs");
		FileNameExtensionFilter filter2 = gui.createFileNameExtensionFilter("Output File");
		assertEquals(filter2.getExtensions()[0], "sro");
	}

}
