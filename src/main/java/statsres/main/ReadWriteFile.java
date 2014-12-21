package statsres.main;
//Import java io package.
import java.io.*;
//Import java.util package.
import java.util.*;

//Import swing packages.
import javax.swing.*;

/*
 * Class for writing and reading result files.
 * @author David Lee.
 * @version 1.0.
 */
public final class ReadWriteFile {
    
    /**
     * Default Constructor - do nothing.
     */
    private ReadWriteFile() {
    }
    
    /*
     * Performs the tasks of a readFile method.
     * Creates a buffered reader and reads the file line by line.
     * @param location a <code>String</code> representation of the location of the file to be read.
     * @param readOnlyFirstLine a <code>boolean</code> which is true iff the first line should be read exclusively!
     * @return a <code>String</code> linked list of the contents of the file.
     */
    public static List<String> readFile(final String location, final boolean readOnlyFirstLine) {
        List<String> theLinkedList = new ArrayList<String>();
        //Commencing reading of the file.
        try{
            //Create a new buffered reader object and a new string called line.
            BufferedReader theReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(location))));
            String line = "";
            //Read each line of the file and add it to the linked list.
            do{
                line = theReader.readLine();
                //Additional check to make sure that the line is not null.
                if(line==null) { break; }
                theLinkedList.add(line);
            }
            //Until the end of the file is reached.
            while(line!=null && !readOnlyFirstLine);
            theReader.close();
        }
        // If any exception then show error message and return null;
        catch (IOException e){
            JOptionPane.showMessageDialog(null,"This program could not read the specified file. Please ensure that the correct file was selected.","Error: Reading file",JOptionPane.ERROR_MESSAGE);
            return null;
        }
        //Return the linked list.
        return theLinkedList;
    }
    
    /*
     * Takes a String array and a File to write to.
     * Performs the basic tasks of a writeFile method.
     * Creates a BufferedWriter and writes the String array to the file.
     * @param phrase a <code>String</code> list containing the phrases to be written to the file.
     * @param theFile a <code>File</code> object containing the file that the phrase should be written to.
     * @param append a <code>boolean</code> indicating whether or not the data should be appended to the end of the file.
     * @return a <code>boolean</code> value indicating whether the file writing was successful.
     */
    public static boolean writeFile(final List<String> phrase, final File theFile, final boolean append ){
        try{
            //Create the file.
            theFile.createNewFile();
            //Create a new buffered writer object.
            BufferedWriter theWriter = new BufferedWriter(new FileWriter(theFile, append));
            //For each phrase in the String array write the phrase to the file and then a new line character.
            for(int i=0;i<phrase.size();i++){
                theWriter.write(phrase.get(i));
                theWriter.newLine();
            }
            //Close the writer.
            theWriter.close();
            return true; //Operations were successful.
        }
        //If any exception then show error message and exit.
        catch(IOException e){
            JOptionPane.showMessageDialog(null,"An error has occurred while writing this file. The program will now exit.","Error: Writing file",JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            System.exit(0);        
            return false; //Operations weren't successful.
        }
    } 
    
}