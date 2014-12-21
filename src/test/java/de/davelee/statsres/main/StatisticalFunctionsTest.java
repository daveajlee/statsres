package de.davelee.statsres.main;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import statsres.main.StatisticalFunctions;

public class StatisticalFunctionsTest {
	
	@Test
	public void testMean () {
		double[] numericalData = new double[] { 1.0, 2.0, 3.0 };
		double mean = StatisticalFunctions.MEAN.calculate(numericalData);
		assertEquals(mean, 2.0, 0.1);
	}
	
	@Test
	public void testStandardDeviation() {
		double[] numericalData = new double[] { 1.0, 2.0, 3.0 };
		double mean = StatisticalFunctions.STANDARD_DEVIATION.calculate(numericalData);
		assertEquals(mean, 1.0, 0.1);
	}
	
	@Test
	public void testMin() {
		double[] numericalData = new double[] { 2.0, 1.0, 3.0 };
		double min = StatisticalFunctions.MIN.calculate(numericalData);
		assertEquals(min, 1.0, 0.1);
	}
	
	@Test
	public void testMax() {
		double[] numericalData = new double[] { 2.0, 1.0, 3.0, 2.5 };
		double max = StatisticalFunctions.MAX.calculate(numericalData);
		assertEquals(max, 3.0, 0.1);
	}
	
	@Test
	public void testMedian() {
		double[] numericalData = new double[] { 2.0, 1.0, 3.0, 2.5 };
		double median = StatisticalFunctions.MEDIAN.calculate(numericalData);
		assertEquals(median, 2.25, 0.1);
		double[] numericalData2 = new double[] { 2.0, 1.0, 3.0 };
		double median2 = StatisticalFunctions.MEDIAN.calculate(numericalData2);
		assertEquals(median2, 1.5, 0.1);
	}
	
	@Test
	public void testCount() {
		double[] numericalData = new double[] { 2.0, 1.0, 3.0, 2.5 };
		assertEquals(StatisticalFunctions.COUNT.calculate(numericalData), 4.0, 0.1);
	}
	
	@Test
	public void testFirstQuartile() {
		double[] numericalData = new double[] { 2.0, 1.0, 3.0, 2.5, 5.0, 6.0 };
		double quartile1 = StatisticalFunctions.QUARTILE_FIRST.calculate(numericalData);
		assertEquals(quartile1, 1.5, 0.1);
		double[] numericalData2 = new double[] { 2.0, 1.0, 3.0, 2.5 };
		double quartile12 = StatisticalFunctions.QUARTILE_FIRST.calculate(numericalData2);
		assertEquals(quartile12, 1.5, 0.1);
	}
	
	@Test
	public void testThreeQuartile() {
		double[] numericalData = new double[] { 2.0, 1.0, 3.0, 2.5, 5.0, 6.0 };
		double quartile3 = StatisticalFunctions.QUARTILE_THIRD.calculate(numericalData);
		assertEquals(quartile3, 2.75, 0.1);
		double[] numericalData2 = new double[] { 2.0, 1.0, 3.0, 2.5 };
		double quartile32 = StatisticalFunctions.QUARTILE_THIRD.calculate(numericalData2);
		assertEquals(quartile32, 2.75, 0.1);
	}
	
	@Test
	public void testInterQuartileRange() {
		double[] numericalData = new double[] { 2.0, 1.0, 3.0, 2.5, 5.0, 6.0 };
		double iqr1 = StatisticalFunctions.INTER_QUARTILE_RANGE.calculate(numericalData);
		assertEquals(iqr1, 1.25, 0.1);
		double[] numericalData2 = new double[] { 2.0, 1.0, 3.0, 2.5 };
		double iqr2 = StatisticalFunctions.INTER_QUARTILE_RANGE.calculate(numericalData2);
		assertEquals(iqr2, 1.25, 0.1);
	}

}
