package de.davelee.statsres.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.swing.filechooser.FileNameExtensionFilter;

import org.junit.Ignore;
import org.junit.Test;

import de.davelee.statsres.gui.StatsresGUI;
import de.davelee.statsres.main.StatsresProg;
import de.davelee.statsres.main.StatsresProgMock;
import de.davelee.statsres.main.StatsresSettings;
import de.davelee.statsres.main.UserInterface;

public class StatsresGUITest {
	
	@Test
	@Ignore
	public void testAddHeaderInfo ( ) {
		StatsresGUI gui = new StatsresGUI(new UserInterface(), new StatsresProg(), "", null, true);
		gui.addHeaderInfo(true);
	}
	
	@Test
	@Ignore
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
	@Ignore
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
	@Ignore
	public void testCreateStatsOptionPanel ( ) {
		StatsresGUI gui = new StatsresGUI(new UserInterface(), new StatsresProg(), "", null, true);
		assertNotNull(gui.createStatsOptionPanel());
	}
	
	@Test
	@Ignore
	public void testButtonPanel ( ) {
		StatsresGUI gui = new StatsresGUI(new UserInterface(), new StatsresProg(), "", null, true);
		assertNotNull(gui.createButtonPanel());
	}

	@Test
	@Ignore
	public void testOutputPanel ( ) {
		StatsresGUI gui = new StatsresGUI(new UserInterface(), new StatsresProg(), "", null, true);
		assertNotNull(gui.createOutputTextPanel());
	}
	
	@Test
	@Ignore
	public void testOutputPane ( ) {
		StatsresGUI gui = new StatsresGUI(new UserInterface(), new StatsresProg(), "", null, true);
		assertNotNull(gui.createOutputPane());
	}
	
	@Test
	@Ignore
	public void testLocationBounds ( ) {
		StatsresGUI gui = new StatsresGUI(new UserInterface(), new StatsresProg(), "", null, true);
		gui.setLocationBounds();
	}
	
	@Test
	@Ignore
	public void testCreateDialogPanel ( ) {
		StatsresGUI gui = new StatsresGUI(new UserInterface(), new StatsresProg(), "", null, true);
		assertNotNull(gui.createDialogPanel("", true));
	}
	
	@Test
	@Ignore
	public void testClearFields ( ) {
		StatsresGUI gui = new StatsresGUI(new UserInterface(), new StatsresProg(), "", null, true);
		assertNotNull(gui.createDialogPanel("", true));
		gui.clearFields();
	}
	
	@Test
	@Ignore
	public void testSaveCurrentSettings ( ) {
		StatsresGUI gui = new StatsresGUI(new UserInterface(), new StatsresProg(), "", null, true);
		assertNotNull(gui.createDialogPanel("", true));
		assertNotNull(gui.saveCurrentSettings());
		gui.deselectAllStatOptions();
		assertNotNull(gui.saveCurrentSettings());
	}
	
	@Test
	@Ignore
	public void testSettingsNotNull ( ) {
		StatsresGUI gui = new StatsresGUI(new UserInterface(), new StatsresProg(), "", StatsresSettings.createDefaultSettings(""), true);
		assertNotNull(gui);
	}
	
	@Test
	@Ignore
	public void testMenu ( ) {
		StatsresGUI gui = new StatsresGUI(new UserInterface(), new StatsresProgMock(), "", StatsresSettings.createDefaultSettings(""), true);
		String testFileNameGood = "status.srs";
		gui.saveSettingsMenu(testFileNameGood);
		gui.saveSettingsMenu("status.sro");
		gui.saveOutputMenu(testFileNameGood);
		gui.saveOutputMenu("status.sro");
		gui.loadSettingsMenu(true, testFileNameGood);
		gui.loadSettingsMenu(true, "settings.srs");
		gui.loadOutputMenu("output.sro");
		gui.loadOutputMenu("output2.sro");
	}
	
	@Test
	@Ignore
	public void testFileExtension ( ) {
		StatsresGUI gui = new StatsresGUI(new UserInterface(), new StatsresProgMock(), "", StatsresSettings.createDefaultSettings(""), true);
		FileNameExtensionFilter filter = gui.createFileNameExtensionFilter("Settings File");
		assertEquals(filter.getExtensions()[0], "srs");
		FileNameExtensionFilter filter2 = gui.createFileNameExtensionFilter("Output File");
		assertEquals(filter2.getExtensions()[0], "sro");
	}

}
