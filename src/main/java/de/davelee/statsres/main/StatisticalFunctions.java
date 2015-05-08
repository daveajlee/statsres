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
			for ( int i = 0; i < numericalData.size(); i++ ) {
				numericalTotal += numericalData.get(i);
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
			List<Double> numericalSortedData = new ArrayList<Double>(numericalData);
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
			List<Double> numericalSortedData = new ArrayList<Double>(numericalData);
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
			List<Double> numericalSortedData = new ArrayList<Double>(numericalData);
			Collections.sort(numericalSortedData);
			int medianPos = Math.round(numericalSortedData.size()/2); 
			double median = -1;
            if ( numericalSortedData.size() ==1 ) {
                median = numericalSortedData.get(medianPos);
            } else {
                median = ((numericalSortedData.get(medianPos) - numericalSortedData.get(medianPos-1))/2) + numericalSortedData.get(medianPos-1);
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
			List<Double> numericalSortedData = new ArrayList<Double>(numericalData);
			Collections.sort(numericalSortedData);
			int oneQuartilePos = Math.round(numericalSortedData.size()/4); 
			double oneQuartile = -1;
            if ( numericalSortedData.size() ==1 || oneQuartilePos == 0 ) {
                oneQuartile = numericalSortedData.get(oneQuartilePos);
            } else {
                oneQuartile = ((numericalSortedData.get(oneQuartilePos) - numericalSortedData.get(oneQuartilePos-1))/2) + numericalSortedData.get(oneQuartilePos-1);
            }
            return oneQuartile;
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
			List<Double> numericalSortedData = new ArrayList<Double>(numericalData);
			Collections.sort(numericalSortedData);
			int threeQuartilePos = Math.round(numericalSortedData.size()/4) * 3; 
			double threeQuartile = -1;
            if ( numericalSortedData.size() ==1 || threeQuartilePos == 0 ) {
                threeQuartile = numericalSortedData.get(threeQuartilePos);
            } else {
                threeQuartile = ((numericalSortedData.get(threeQuartilePos) - numericalSortedData.get(threeQuartilePos-1))/2) + numericalSortedData.get(threeQuartilePos-1);
            }
            return threeQuartile;
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
			long variance = 0;
            for ( int i = 0; i < numericalData.size(); i++ ) {
                variance += Math.pow(numericalData.get(i)-StatisticalFunctions.MEAN.calculate(numericalData), 2.0);
            }
            return Math.sqrt(variance/(numericalData.size()-1));
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
