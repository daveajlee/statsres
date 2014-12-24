package de.davelee.statsres.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import statsres.main.ReadWriteFile;

public class ReadWriteFileTest {
	
	@Test
	public void testReadCompleteFile ( ) {
		URL url = this.getClass().getResource("/readfiletest.txt");
		List<String> completeFile = ReadWriteFile.readFile(url.getFile(), false);
		assertEquals(completeFile.size(), 2);
		assertEquals(completeFile.get(0), "Hello");
		assertEquals(completeFile.get(1), "This is a test file");
	}
	
	@Test(expected=NullPointerException.class)
	public void testFileNotExists ( ) {
		URL url = this.getClass().getResource("/readfiletestfake.txt");
		List<String> completeFile = ReadWriteFile.readFile(url.getFile(), false);
		assertNull(completeFile);
	}
	
	@Test
	public void testEmptyFile ( ) {
		URL url = this.getClass().getResource("/readfileempty.txt");
		List<String> completeFile = ReadWriteFile.readFile(url.getFile(), false);
		assertEquals(completeFile.size(), 0);
	}
	
	@Test
	public void testReadFirstLine ( ) {
		URL url = this.getClass().getResource("/readfiletest.txt");
		List<String> firstLine = ReadWriteFile.readFile(url.getFile(), true);
		assertEquals(firstLine.size(), 1);
		assertEquals(firstLine.get(0), "Hello");
	}
	
	@Test
	public void testWriteFile ( ) {
		URL url = this.getClass().getResource("/writefiletest.txt");
		List<String> newList = new ArrayList<String>();
		newList.add("WriteTest");
		assertTrue(ReadWriteFile.writeFile(newList, new File(url.getFile()), false));
		newList = new ArrayList<String>();
		newList.add("appendTest");
		assertTrue(ReadWriteFile.writeFile(newList, new File(url.getFile()), true));
	}
	
}
