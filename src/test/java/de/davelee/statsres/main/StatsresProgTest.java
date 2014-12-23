package de.davelee.statsres.main;

import static org.junit.Assert.assertEquals;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import statsres.main.StatisticalFunctions;
import statsres.main.StatsresProg;

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

}
