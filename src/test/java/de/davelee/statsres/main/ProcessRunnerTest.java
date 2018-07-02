package de.davelee.statsres.main;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;

import org.junit.Ignore;
import org.junit.Test;

import de.davelee.statsres.main.ProcessRunner;
import de.davelee.statsres.main.StatisticalFunctions;
import de.davelee.statsres.main.UserInterface;

public class ProcessRunnerTest {
	
	@Test
	@Ignore
	public void testProcessRunner ( ) {
		List<String> columns = new ArrayList<String>();
		columns.add("data");
		List<StatisticalFunctions> functions = new ArrayList<StatisticalFunctions>();
		functions.add(StatisticalFunctions.INTER_QUARTILE_RANGE);
		ProcessRunner runner = new ProcessRunner ( new UserInterface(), "/C:/workspace/statsres/target/test-classes/subfolder/subsubfolder", functions, columns, new JTextArea());
		runner.run();
		ProcessRunner runner2 = new ProcessRunner ( new UserInterface(), "/C:/workspace/statsres/target/test-classes/subfolder/subsubfolder/subsubfolder2.csv", functions, columns, new JTextArea());
		runner2.run();
		ProcessRunner runner3 = new ProcessRunner ( new UserInterface(), "/C:/workspace/statsres/target/test-classes/subfolder/subsubfolder/subsubfolder.csv", new ArrayList<StatisticalFunctions>(), columns, new JTextArea());
		runner3.run();
	}

}
