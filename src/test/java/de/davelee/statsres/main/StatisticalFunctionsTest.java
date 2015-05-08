package de.davelee.statsres.main;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import de.davelee.statsres.main.StatisticalFunctions;

public class StatisticalFunctionsTest {
	
	@Test
	public void testMean () {
		List<Double> numericalData = new ArrayList<Double>();
		numericalData.add(1.0); 
		numericalData.add(2.0); 
		numericalData.add(3.0);
		double mean = StatisticalFunctions.MEAN.calculate(numericalData);
		assertEquals(mean, 2.0, 0.1);
		assertEquals(StatisticalFunctions.MEAN.getDisplayName(), "Mean");
	}
	
	@Test
	public void testStandardDeviation() {
		List<Double> numericalData = new ArrayList<Double>();
		numericalData.add(1.0); 
		numericalData.add(2.0); 
		numericalData.add(3.0);
		double mean = StatisticalFunctions.STANDARD_DEVIATION.calculate(numericalData);
		assertEquals(mean, 1.0, 0.1);
		assertEquals(StatisticalFunctions.STANDARD_DEVIATION.getDisplayName(), "Standard Deviation");
	}
	
	@Test
	public void testMin() {
		List<Double> numericalData = new ArrayList<Double>();
		numericalData.add(2.0); 
		numericalData.add(1.0); 
		numericalData.add(3.0);
		double min = StatisticalFunctions.MIN.calculate(numericalData);
		assertEquals(min, 1.0, 0.1);
		assertEquals(StatisticalFunctions.MIN.getDisplayName(), "Minimum Value");
	}
	
	@Test
	public void testMax() {
		List<Double> numericalData = new ArrayList<Double>();
		numericalData.add(2.0); 
		numericalData.add(1.0); 
		numericalData.add(3.0); 
		numericalData.add(2.5);
		double max = StatisticalFunctions.MAX.calculate(numericalData);
		assertEquals(max, 3.0, 0.1);
		assertEquals(StatisticalFunctions.MAX.getDisplayName(), "Maximum Value");
	}
	
	@Test
	public void testMedian() {
		List<Double> numericalData = new ArrayList<Double>();
		numericalData.add(2.0); 
		numericalData.add(1.0); 
		numericalData.add(3.0); 
		numericalData.add(2.5);
		double median = StatisticalFunctions.MEDIAN.calculate(numericalData);
		assertEquals(median, 2.25, 0.1);
		List<Double> numericalData2 = new ArrayList<Double>();
		numericalData2.add(2.0); 
		numericalData2.add(1.0); 
		numericalData2.add(3.0);
		double median2 = StatisticalFunctions.MEDIAN.calculate(numericalData2);
		assertEquals(median2, 1.5, 0.1);
		List<Double> numericalData3 = new ArrayList<Double>();
		numericalData3.add(2.0);
		double median3 = StatisticalFunctions.MEDIAN.calculate(numericalData3);
		assertEquals(median3, 2.0, 0.1);
		List<Double> numericalData4 = new ArrayList<Double>();
		numericalData4.add(1.55557); 
		numericalData4.add(2.68437);
		double median4 = StatisticalFunctions.MEDIAN.calculate(numericalData4);
		assertEquals(median4, 2.11997, 0.0001);
		assertEquals(StatisticalFunctions.MEDIAN.getDisplayName(), "Median");
	}
	
	@Test
	public void testCount() {
		List<Double> numericalData = new ArrayList<Double>();
		numericalData.add(2.0); 
		numericalData.add(1.0); 
		numericalData.add(3.0); 
		numericalData.add(2.5);
		assertEquals(StatisticalFunctions.COUNT.calculate(numericalData), 4.0, 0.1);
		assertEquals(StatisticalFunctions.COUNT.getDisplayName(), "Number of Data Values");
	}
	
	@Test
	public void testFirstQuartile() {
		List<Double> numericalData = new ArrayList<Double>();
		numericalData.add(2.0); 
		numericalData.add(1.0); 
		numericalData.add(3.0); 
		numericalData.add(2.5);
		numericalData.add(5.0); 
		numericalData.add(6.0);
		double quartile1 = StatisticalFunctions.QUARTILE_FIRST.calculate(numericalData);
		assertEquals(quartile1, 1.5, 0.1);
		List<Double> numericalData2 = new ArrayList<Double>();
		numericalData2.add(2.0); 
		numericalData2.add(1.0); 
		numericalData2.add(3.0); 
		numericalData2.add(2.5);
		double quartile12 = StatisticalFunctions.QUARTILE_FIRST.calculate(numericalData2);
		assertEquals(quartile12, 1.5, 0.1);
		List<Double> numericalData3 = new ArrayList<Double>();
		numericalData3.add(2.0);
		double quartile13 = StatisticalFunctions.QUARTILE_FIRST.calculate(numericalData3);
		assertEquals(quartile13, 2.0, 0.1);
		List<Double> numericalData4 = new ArrayList<Double>();
		numericalData4.add(1.55557); 
		numericalData4.add(2.68437);
		double quartile14 = StatisticalFunctions.QUARTILE_FIRST.calculate(numericalData4);
		assertEquals(quartile14, 1.55557, 0.0001);
		assertEquals(StatisticalFunctions.QUARTILE_FIRST.getDisplayName(), "1st Quartile");
	}
	
	@Test
	public void testThreeQuartile() {
		List<Double> numericalData = new ArrayList<Double>();
		numericalData.add(2.0); 
		numericalData.add(1.0); 
		numericalData.add(3.0); 
		numericalData.add(2.5);
		numericalData.add(5.0); 
		numericalData.add(6.0);
		double quartile3 = StatisticalFunctions.QUARTILE_THIRD.calculate(numericalData);
		assertEquals(quartile3, 2.75, 0.1);
		List<Double> numericalData2 = new ArrayList<Double>();
		numericalData2.add(2.0); 
		numericalData2.add(1.0); 
		numericalData2.add(3.0); 
		numericalData2.add(2.5);
		double quartile32 = StatisticalFunctions.QUARTILE_THIRD.calculate(numericalData2);
		assertEquals(quartile32, 2.75, 0.1);
		List<Double> numericalData3 = new ArrayList<Double>();
		numericalData3.add(2.0);
		double quartile13 = StatisticalFunctions.QUARTILE_THIRD.calculate(numericalData3);
		assertEquals(quartile13, 2.0, 0.1);
		List<Double> numericalData4 = new ArrayList<Double>();
		numericalData4.add(1.55557); 
		numericalData4.add(2.68437);
		double quartile14 = StatisticalFunctions.QUARTILE_THIRD.calculate(numericalData4);
		assertEquals(quartile14, 1.55557, 0.0001);
		assertEquals(StatisticalFunctions.QUARTILE_THIRD.getDisplayName(), "3rd Quartile");
	}
	
	@Test
	public void testInterQuartileRange() {
		List<Double> numericalData = new ArrayList<Double>();
		numericalData.add(2.0); 
		numericalData.add(1.0); 
		numericalData.add(3.0); 
		numericalData.add(2.5);
		numericalData.add(5.0); 
		numericalData.add(6.0);
		double iqr1 = StatisticalFunctions.INTER_QUARTILE_RANGE.calculate(numericalData);
		assertEquals(iqr1, 1.25, 0.1);
		List<Double> numericalData2 = new ArrayList<Double>();
		numericalData2.add(2.0); 
		numericalData2.add(1.0); 
		numericalData2.add(3.0); 
		numericalData2.add(2.5);
		double iqr2 = StatisticalFunctions.INTER_QUARTILE_RANGE.calculate(numericalData2);
		assertEquals(iqr2, 1.25, 0.1);
		assertEquals(StatisticalFunctions.INTER_QUARTILE_RANGE.getDisplayName(), "Interquartile Range");
	}

}
