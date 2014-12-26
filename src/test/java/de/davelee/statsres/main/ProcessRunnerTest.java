package de.davelee.statsres.main;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;

import org.junit.Test;

import statsres.main.ProcessRunner;
import statsres.main.StatisticalFunctions;
import statsres.main.UserInterface;

public class ProcessRunnerTest {
	
	@Test
	public void testProcessRunner ( ) {
		List<String> columns = new ArrayList<String>();
		columns.add("data");
		List<StatisticalFunctions> functions = new ArrayList<StatisticalFunctions>();
		functions.add(StatisticalFunctions.INTER_QUARTILE_RANGE);
		ProcessRunner runner = new ProcessRunner ( new UserInterface(), "/C:/workspace/statsres/target/test-classes/subfolder/subsubfolder/subsubfolder.csv", functions, columns, new JTextArea(), true);
		runner.run();
	}

}
