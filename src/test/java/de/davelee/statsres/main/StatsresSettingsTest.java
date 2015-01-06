package de.davelee.statsres.main;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import statsres.main.StatisticalFunctions;
import statsres.main.StatsresSettings;

public class StatsresSettingsTest {
	
	@Test
	public void testSettings ( ) {
		StatsresSettings settings = new StatsresSettings();
		settings.setFile("test.txt");
		assertEquals(settings.getFile(), "test.txt");
		List<String> columnData = new ArrayList<String>();
		columnData.add("TestColumn");
		settings.setColumnData(columnData);
		assertEquals(settings.getColumnData().size(), 1);
		assertEquals(settings.getColumnData().get(0), "TestColumn");
		List<StatisticalFunctions> statisticalFunctions = new ArrayList<StatisticalFunctions>();
		statisticalFunctions.add(StatisticalFunctions.MEAN);
		settings.setStatisticalFunctions(statisticalFunctions);
		assertEquals(settings.getStatisticalFunctions().size(), 1);
		assertEquals(settings.getStatisticalFunctions().get(0), StatisticalFunctions.MEAN);
		List<String> fileContents = settings.saveAsV1File();
		assertEquals(fileContents.size(), 12);
		assertEquals(fileContents.get(3), "Mean=true");
	}
	
	@Test
	public void testDefaultSettings ( ) {
		StatsresSettings settings = StatsresSettings.createDefaultSettings("");
		assertEquals(settings.getFile(), "");
		assertEquals(settings.getColumnData().size(), 0);
		assertEquals(settings.getStatisticalFunctions().size(), 9);
	}

}
