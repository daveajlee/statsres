package statsres.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum StatisticalFunctions {
	
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
	
	COUNT {
		
		public double calculate(final List<Double> numericalData ) {
			return numericalData.size();
		}
		
		public String getDisplayName() {
			return "Number of Data Values";
		}
	},
	
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
	
	public abstract double calculate(final List<Double> numericalData);
	
	public abstract String getDisplayName();

}
