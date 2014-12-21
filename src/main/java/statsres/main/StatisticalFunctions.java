package statsres.main;

import java.util.Arrays;

public enum StatisticalFunctions {
	
	MEAN {
	
		public double calculate(final double[] numericalData ) {
			double numericalTotal = 0.0;
			for ( int i = 0; i < numericalData.length; i++ ) {
				numericalTotal += numericalData[i];
			}
			return numericalTotal/numericalData.length;
		}
		
	},
	
	MIN {
		public double calculate(final double[] numericalData ) {
			double[] numericalSortedData = Arrays.copyOf(numericalData, numericalData.length);
			Arrays.sort(numericalSortedData);
			return numericalSortedData[0];
		}
	},
	
	MAX {
		public double calculate(final double[] numericalData ) {
			double[] numericalSortedData = Arrays.copyOf(numericalData, numericalData.length);
			Arrays.sort(numericalSortedData);
			return numericalSortedData[numericalSortedData.length-1];
		}
	},
	
	MEDIAN {
		public double calculate(final double[] numericalData ) {
			double[] numericalSortedData = Arrays.copyOf(numericalData, numericalData.length);
			Arrays.sort(numericalSortedData);
			int medianPos = Math.round(numericalSortedData.length/2); double median = -1;
            Double diff = new Double((double) numericalSortedData.length / (double) 2);
            if ( diff.toString().split(".").length > 1 || numericalSortedData.length ==1 ) {
                median = numericalSortedData[medianPos];
            }
            else {
                median = ((numericalSortedData[medianPos] - numericalSortedData[medianPos-1])/2) + numericalSortedData[medianPos-1];
            }
            return median;
		}
	},
	
	COUNT {
		
		public double calculate(final double[] numericalData ) {
			return numericalData.length;
		}
		
	},
	
	QUARTILE_FIRST {
		
		public double calculate(final double[] numericalData ) {
			double[] numericalSortedData = Arrays.copyOf(numericalData, numericalData.length);
			Arrays.sort(numericalSortedData);
			int oneQuartilePos = Math.round(numericalSortedData.length/4); double oneQuartile = -1;
            Double diff = new Double((double) numericalSortedData.length / (double) 4);
            if ( diff.toString().split(".").length > 1 || numericalSortedData.length ==1 ) {
                oneQuartile = numericalSortedData[oneQuartilePos];
            }
            else {
                oneQuartile = ((numericalSortedData[oneQuartilePos] - numericalSortedData[oneQuartilePos-1])/2) + numericalSortedData[oneQuartilePos-1];
            }
            return oneQuartile;
		}
		
	},
	
	QUARTILE_THIRD {
		
		public double calculate(final double[] numericalData ) {
			double[] numericalSortedData = Arrays.copyOf(numericalData, numericalData.length);
			Arrays.sort(numericalSortedData);
			int threeQuartilePos = Math.round(numericalSortedData.length/4) * 3; double threeQuartile = -1;
            Double diff = new Double((double) numericalSortedData.length / (double) 4);
            if ( diff.toString().split(".").length > 1 || numericalSortedData.length ==1 ) {
                threeQuartile = numericalSortedData[threeQuartilePos];
            }
            else {
                threeQuartile = ((numericalSortedData[threeQuartilePos] - numericalSortedData[threeQuartilePos-1])/2) + numericalSortedData[threeQuartilePos-1];
            }
            return threeQuartile;
		}
		
	},
	
	INTER_QUARTILE_RANGE {
		
		public double calculate(final double[] numericalData ) {
			double quartileOne = StatisticalFunctions.QUARTILE_FIRST.calculate(numericalData);
			double quartileThree = StatisticalFunctions.QUARTILE_THIRD.calculate(numericalData);
			return quartileThree - quartileOne;
		}
		
	},
	
	STANDARD_DEVIATION {
		
		public double calculate(final double[] numericalData ) {
			long variance = 0;
            for ( int i = 0; i < numericalData.length; i++ ) {
                variance += Math.pow(numericalData[i]-StatisticalFunctions.MEAN.calculate(numericalData), 2.0);
            }
            return Math.sqrt(variance/(numericalData.length-1));
		}
	};
	
	public abstract double calculate(final double[] numericalData);

}
