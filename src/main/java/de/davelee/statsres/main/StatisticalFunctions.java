package de.davelee.statsres.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum StatisticalFunctions {
	
	/**
	 * Enum representing the mean function.
	 */
	MEAN {
	
		public double calculate(final List<Double> numericalData ) {
			double numericalTotal = 0.0;
			for ( Double myNumber : numericalData) {
				numericalTotal += myNumber;
			}
			return numericalTotal/numericalData.size();
		}
		
		public String getDisplayName() {
			return "Mean";
		}
		
	},
	
	/**
	 * Enum representing the minimum function.
	 */
	MIN {
		public double calculate(final List<Double> numericalData ) {
			List<Double> numericalSortedData = new ArrayList<>(numericalData);
			Collections.sort(numericalSortedData);
			return numericalSortedData.get(0);
		}
		
		public String getDisplayName() {
			return "Minimum Value";
		}
	},
	
	/**
	 * Enum representing the maximum function.
	 */
	MAX {
		public double calculate(final List<Double> numericalData ) {
			List<Double> numericalSortedData = new ArrayList<>(numericalData);
			Collections.sort(numericalSortedData);
			return numericalSortedData.get(numericalSortedData.size()-1);
		}
		
		public String getDisplayName() {
			return "Maximum Value";
		}
	},
	
	/**
	 * Enum representing the median function.
	 */
	MEDIAN {
		public double calculate(final List<Double> numericalData ) {
			List<Double> numericalSortedData = new ArrayList<>(numericalData);
			Collections.sort(numericalSortedData);
			int medianPos = (Math.round((float) numericalSortedData.size()/ 2.0f))-1;
			double median;
			if ( numericalSortedData.size() % 2 != 0 ) {
				median = numericalSortedData.get(medianPos);
			} else {
				median = (numericalSortedData.get(medianPos) + numericalSortedData.get(medianPos+1))/2;
			}
			return median;
		}
		
		public String getDisplayName() {
			return "Median";
		}
	},
	
	/**
	 * Enum representing the count function.
	 */
	COUNT {
		
		public double calculate(final List<Double> numericalData ) {
			return numericalData.size();
		}
		
		public String getDisplayName() {
			return "Number of Data Values";
		}
	},
	
	/**
	 * Enum representing the 1st Quartile.
	 */
	QUARTILE_FIRST {
		
		public double calculate(final List<Double> numericalData ) {
			//Put the numbers in order.
			List<Double> numericalSortedData = new ArrayList<>(numericalData);
			Collections.sort(numericalSortedData);
			// Divide the list into the lower half.
			List<Double> firstHalfData;
			if ( numericalSortedData.size() % 2 != 0 ) {
				firstHalfData = numericalSortedData.subList(0, (int) Math.floor((double) numericalSortedData.size()/2.0));
			} else {
				firstHalfData = numericalData.subList(0, numericalSortedData.size()/2);
			}
			//Find the median for this half.
			return StatisticalFunctions.MEDIAN.calculate(firstHalfData);
		}
		
		public String getDisplayName() {
			return "1st Quartile";
		}
		
	},
	
	/**
	 * Enum representing the 3rd Quartile.
	 */
	QUARTILE_THIRD {
		
		public double calculate(final List<Double> numericalData ) {
			//Put the numbers in order.
			List<Double> numericalSortedData = new ArrayList<>(numericalData);
			Collections.sort(numericalSortedData);
			// Divide the list into the lower half.
			List<Double> secondHalfData;
			if ( numericalSortedData.size() % 2 != 0 ) {
				secondHalfData = numericalSortedData.subList((int) Math.ceil((double) numericalSortedData.size()/2.0), numericalSortedData.size());
			} else {
				secondHalfData = numericalData.subList( numericalSortedData.size()/2, numericalSortedData.size());
			}
			//Find the median for this half.
			return StatisticalFunctions.MEDIAN.calculate(secondHalfData);
		}
		
		public String getDisplayName() {
			return "3rd Quartile";
		}
		
	},
	
	/**
	 * Enum representing the inter quartile range (difference between 1st Quartile and 3rd Quartile) function.
	 */
	INTER_QUARTILE_RANGE {
		
		public double calculate(final List<Double> numericalData ) {
			double quartileOne = StatisticalFunctions.QUARTILE_FIRST.calculate(numericalData);
			double quartileThree = StatisticalFunctions.QUARTILE_THIRD.calculate(numericalData);
			return quartileThree - quartileOne;
		}
		
		public String getDisplayName() {
			return "Interquartile Range";
		}
		
	},
	
	/**
	 * Enum representing the standard deviation function.
	 */
	STANDARD_DEVIATION {
		
		public double calculate(final List<Double> numericalData ) {
			//1. Work out the Mean (the simple average of the numbers)
			double mean = StatisticalFunctions.MEAN.calculate(numericalData);
			//2. Then for each number: subtract the Mean and square the result
			List<Double> squaredData = new ArrayList<>();
			for ( Double data : numericalData ) {
				squaredData.add(Math.pow(data-mean, 2.0));
			}
			//3. Then work out the mean of those squared differences.
			double squaredMean = StatisticalFunctions.MEAN.calculate(squaredData);
			//4. Take the square root of that and we are done!
			return Math.sqrt(squaredMean);
		}
		
		public String getDisplayName() {
			return "Standard Deviation";
		}
	};
	
	/**
	 * Method to calculate the answer for this statistical function for the specified numerical data.
	 * @param numericalData a <code>List</code> of <code>Double</code> with the numerical data.
	 * @return a <code>double</code> representing the answer for this statistical function based on the numerical data.
	 */
	public abstract double calculate(final List<Double> numericalData);
	
	/**
	 * Return the display name for this statistical function.
	 * @return a <code>String</code> which is the display name for this statistical function.
	 */
	public abstract String getDisplayName();

}
