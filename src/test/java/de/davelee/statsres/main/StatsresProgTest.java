package de.davelee.statsres.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import statsres.main.StatisticalFunctions;
import statsres.main.StatsresProg;
import statsres.main.StatsresSettings;

public class StatsresProgTest {	
	
	private StatsresProg statsresProg;
	
	@Before
	public void init ( ) {
		statsresProg = new StatsresProg();
	}
	
	@Test
	public void testGetAllFiles ( ) {
		URL url = this.getClass().getResource("/subfolder/");
		List<String> fileList = statsresProg.getAllFiles(url.getFile());
		assertEquals(fileList.size(), 2);
		assertEquals(fileList.get(0), "/C:/workspace/statsres/target/test-classes/subfolder/subfolder.csv");
		assertEquals(fileList.get(1), "/C:/workspace/statsres/target/test-classes/subfolder/subsubfolder/subsubfolder.csv");
	}
	
	@Test
	public void testSetCalcParameters() {
		List<String> fileList = new ArrayList<String>();
		fileList.add("/C:/workspace/statsres/target/test-classes/subfolder/subsubfolder/subsubfolder.csv");
		List<String> columns = new ArrayList<String>();
		columns.add("data");
		List<StatisticalFunctions> functions = new ArrayList<StatisticalFunctions>();
		functions.add(StatisticalFunctions.INTER_QUARTILE_RANGE);
		statsresProg.setCalcParameters(fileList, columns, functions);
	}
	
	@Test
	public void testRun() {
		testSetCalcParameters();
		statsresProg.run();
	}
	
	@Test
	public void testStopProcessing() {
		statsresProg.stopProcessing();
		assertEquals(statsresProg.getOutput(), "\nWARNING: Processing was interrupted!");
		assertEquals(statsresProg.isStillRunning(), false);
	}
	
	@Test
	public void testSaveFile() {
		StatsresSettings settings = new StatsresSettings();
		settings.setColumnData(new ArrayList<String>());
		List<StatisticalFunctions> functions = new ArrayList<StatisticalFunctions>();
		functions.add(StatisticalFunctions.MEAN);
		settings.setStatisticalFunctions(functions);
		URL filePath = this.getClass().getResource("/settings.srs");
		assertTrue(statsresProg.saveContent(settings.saveAsV1File(), filePath.getFile(), ".srs"));
		assertTrue(statsresProg.saveContent(settings.saveAsV1File(), filePath.getFile().substring(0, filePath.getFile().length()-4), ".srs"));
	}
	
	@Test
	public void testLoadOutput() {
		URL filePath = this.getClass().getResource("/readfiletest.txt");
		String text = statsresProg.loadOutputFile(filePath.getFile());
		assertNotNull(text);
		assertEquals("", text);
		URL filePath2 = this.getClass().getResource("/readfiletest.sro");
		String text2 = statsresProg.loadOutputFile(filePath2.getFile());
		assertNotNull(text2);
		assertEquals("Hello\nThis is a test file\n", text2);
	}
	
	@Test
	public void testLoadSettings() {
		URL filePath = this.getClass().getResource("/readsettings.srs");
		StatsresSettings settings = statsresProg.loadSettingsFile(filePath.getFile());
		assertEquals(settings.getFile(), "test.txt");
		assertEquals(settings.getColumnData().size(), 1);
		assertEquals(settings.getColumnData().get(0), "TestColumn");
		assertEquals(settings.getStatisticalFunctions().size(), 5);
		assertEquals(settings.getStatisticalFunctions().get(0), StatisticalFunctions.MEAN);
		assertEquals(settings.getStatisticalFunctions().get(1), StatisticalFunctions.MIN);
		assertEquals(settings.getStatisticalFunctions().get(2), StatisticalFunctions.INTER_QUARTILE_RANGE);
		assertEquals(settings.getStatisticalFunctions().get(3), StatisticalFunctions.QUARTILE_FIRST);
		assertEquals(settings.getStatisticalFunctions().get(4), StatisticalFunctions.QUARTILE_THIRD);
		URL filePath2 = this.getClass().getResource("/readsettings.srt");
		StatsresSettings settings2 = statsresProg.loadSettingsFile(filePath2.getFile());
		assertNull(settings2);
		URL filePath3 = this.getClass().getResource("/readsettingsincomplete.srs");
		StatsresSettings settings3 = statsresProg.loadSettingsFile(filePath3.getFile());
		assertNull(settings3);
	}

	@Test
	public void testRemoveZeros() {
		assertEquals(statsresProg.removeZeros("1.00000000"), "1");
		assertEquals(statsresProg.removeZeros("1.001000"), "1.001");
		
	}
	
}
