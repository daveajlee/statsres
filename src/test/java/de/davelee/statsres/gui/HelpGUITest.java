package de.davelee.statsres.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import statsres.gui.HelpGUI;

public class HelpGUITest {
	
	@Test
	public void testIncludeString ( ) {
		HelpGUI gui = new HelpGUI(true);
		assertTrue(gui.includeString("Stats", "Statsres"));
		assertFalse(gui.includeString("Stas", "Statsres"));
	}
	
	@Test
	public void testContent ( ) {
		HelpGUI gui = new HelpGUI(true);
		gui.initialiseContent();
		assertEquals(gui.getContentUrls().size(), 8);
		assertEquals(gui.getContentUrls().get("Output"), "/output.html");
	}
	
	@Test
	public void testAddHeaderInfos ( ) {
		HelpGUI gui = new HelpGUI(true);
		gui.addHeaderInfos();
	}
	
	@Test
	public void testAddLeftPanel ( ) {
		HelpGUI gui = new HelpGUI(true);
		gui.initialiseContent();
		assertNotNull(gui.createLeftPanel());
	}
	
	@Test
	public void testAddRightPanel ( ) {
		HelpGUI gui = new HelpGUI(true);
		assertNotNull(gui.createRightPanel());
	}
	
	@Test
	public void testUpdateList ( ) {
		HelpGUI gui = new HelpGUI(true);
		gui.initialiseContent();
		gui.createLeftPanel();
		gui.createRightPanel();
		gui.updateList("");
		gui.updateList("Getting Started");
	}

}
