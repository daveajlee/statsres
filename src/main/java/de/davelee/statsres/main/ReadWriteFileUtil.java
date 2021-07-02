package de.davelee.statsres.main;
//Import java io package.
import java.io.*;
//Import java.util package.
import java.util.*;

//Import swing packages.
import javax.swing.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for writing and reading result files.
 * @author Dave Lee.
 */
public final class ReadWriteFileUtil {
	
	private static final Logger LOG = LoggerFactory.getLogger(ReadWriteFileUtil.class);
	
	private ReadWriteFileUtil( ) {
		
	}
    
    /**
     * Performs the tasks of a readFile method.
     * Creates a buffered reader and reads the file line by line.
     * @param location a <code>String</code> representation of the location of the file to be read.
     * @param readOnlyFirstLine a <code>boolean</code> which is true iff the first line should be read exclusively!
     * @return a <code>String</code> linked list of the contents of the file.
     */
    public static List<String> readFile(final String location, final boolean readOnlyFirstLine) {
        List<String> readList = new ArrayList<>();
        //Commencing reading of the file.
        try{
            //Create a new buffered reader object and a new string called line.
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(location)));
            String line;
            //Read each line of the file and add it to the linked list.
            do{
                line = fileReader.readLine();
                //Additional check to make sure that the line is not null.
                if(line==null) { 
                	break;
                }
                readList.add(line);
            }
            //Until the end of the file is reached.
            while(line!=null && !readOnlyFirstLine);
            fileReader.close();
        } catch (IOException e){
        	LOG.error("Could not read specified file", e);
            return Collections.emptyList();
        }
        //Return the linked list.
        return readList;
    }
    
    /**
     * Takes a String array and a File to write to.
     * Performs the basic tasks of a writeFile method.
     * Creates a BufferedWriter and writes the String array to the file.
     * @param phrase a <code>String</code> list containing the phrases to be written to the file.
     * @param file a <code>File</code> object containing the file that the phrase should be written to.
     * @param append a <code>boolean</code> indicating whether or not the data should be appended to the end of the file.
     * @return a <code>boolean</code> value indicating whether the file writing was successful.
     */
    public static boolean writeFile(final List<String> phrase, final File file, final boolean append ){
        try{
            //Create the file.
            if ( file.createNewFile() ) {
                //Create a new buffered writer object.
                BufferedWriter filewriter = new BufferedWriter(new FileWriter(file, append));
                //For each phrase in the String array write the phrase to the file and then a new line character.
                for (String s : phrase) {
                    filewriter.write(s);
                    filewriter.newLine();
                }
                //Close the writer.
                filewriter.close();
                return true;
            }
            return false;
        } catch(IOException e){
        	LOG.error("Error while writing file", e);
            JOptionPane.showMessageDialog(null,"An error has occurred while writing this file.","Error: Writing file",JOptionPane.ERROR_MESSAGE);
            return false;
        }
    } 
    
}