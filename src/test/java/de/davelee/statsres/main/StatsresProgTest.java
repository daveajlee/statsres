package de.davelee.statsres.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import statsres.main.StatisticalFunctions;
import statsres.main.StatsresProg;
import statsres.main.StatsresSettings;

public class StatsresProgTest {
	
	@Test
	public void testGetAllFiles ( ) {
		URL url = this.getClass().getResource("/subfolder/");
		StatsresProg statsresProg = new StatsresProg();
		List<String> fileList = statsresProg.getAllFiles(url.getFile());
		assertEquals(fileList.size(), 2);
		assertEquals(fileList.get(0), "/C:/workspace/statsres/target/test-classes/subfolder/subfolder.csv");
		assertEquals(fileList.get(1), "/C:/workspace/statsres/target/test-classes/subfolder/subsubfolder/subsubfolder.csv");
	}
	
	@Test
	public void testSetCalcParameters() {
		StatsresProg statsresProg = new StatsresProg();
		List<String> fileList = new ArrayList<String>();
		fileList.add("/C:/workspace/statsres/target/test-classes/subfolder/subsubfolder/subsubfolder.csv");
		List<String> columns = new ArrayList<String>();
		columns.add("data");
		List<StatisticalFunctions> functions = new ArrayList<StatisticalFunctions>();
		functions.add(StatisticalFunctions.INTER_QUARTILE_RANGE);
		statsresProg.setCalcParameters(fileList, columns, functions);
	}
	
	@Test
	public void testStopProcessing() {
		StatsresProg statsresProg = new StatsresProg();
		statsresProg.stopProcessing();
		assertEquals(statsresProg.getOutput(), "\nWARNING: Processing was interrupted!");
		assertEquals(statsresProg.isStillRunning(), false);
	}
	
	@Test
	public void testSaveFile() {
		StatsresProg statsresProg = new StatsresProg();
		StatsresSettings settings = new StatsresSettings();
		settings.setColumnData(new ArrayList<String>());
		List<StatisticalFunctions> functions = new ArrayList<StatisticalFunctions>();
		functions.add(StatisticalFunctions.MEAN);
		settings.setStatisticalFunctions(functions);
		URL filePath = this.getClass().getResource("/settings.srs");
		assertTrue(statsresProg.saveContent(settings.saveAsV1File(), filePath.getFile(), ".srs"));
	}
	
	@Test
	public void testLoadOutput() {
		StatsresProg statsresProg = new StatsresProg();
		URL filePath = this.getClass().getResource("/readfiletest.txt");
		String text = statsresProg.loadOutputFile(filePath.getFile());
		assertNotNull(text);
		assertEquals("", text);
		URL filePath2 = this.getClass().getResource("/readfiletest.sro");
		String text2 = statsresProg.loadOutputFile(filePath2.getFile());
		assertNotNull(text2);
		assertEquals("Hello\nThis is a test file\n", text2);
	}

}
