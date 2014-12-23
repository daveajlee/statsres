package statsres.gui;
//Import the required java classes.
import statsres.main.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
//Import file extension package.
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * StatsresGUI.java is a class to display the Statsres application.
 * @author David Lee.
 * @version 1.0.
 */
public class StatsresGUI extends JFrame {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 2556833225592251195L;
	private JLabel theFileOptionsLabel;
    private JLabel theResultsFileLabel;
    private JTextField theResultsFileField;
    private JButton theResultsFileButton;
    private JCheckBox theIncludeSubFoldersBox;
    
    private JLabel theDataOptionsLabel;
    private JLabel theColumnLabel;
    private DefaultListModel<String> theColumnData;
    private JList<String> theColumnHeadings;
    private JButton theSelectAllButton;
    private JButton theDeselectAllButton;
    
    private JLabel theStatsOptionLabel;
    private JCheckBox theMeanBox;
    private JCheckBox theMinBox;
    private JCheckBox theMaxBox;
    private JCheckBox theMedianBox;
    private JCheckBox theStDevBox;
    private JCheckBox theIQRBox;
    private JCheckBox the1QBox;
    private JCheckBox the3QBox;
    private JCheckBox theCountBox;
    
    private JButton theProcessButton;
    private JButton theClearButton;
    private JButton theExitButton;
    
    private JLabel theOutputLabel;
    private JTextArea theOutputArea;
            
    private JMenuBar theMenuBar;
    private JMenu theFileMenu;
    private JMenu theLoadMenu;
    private JMenu theSaveMenu;
    private JMenu theHelpMenu;
    
    private JMenuItem theNewMenu;
    private JMenuItem theLoadSettingsMenu;
    private JMenuItem theLoadOutputMenu;
    private JMenuItem theSaveSettingsMenu;
    private JMenuItem theSaveOutputMenu;
    private JMenuItem theContentMenu;
    private JMenuItem theAboutMenu;
    private JMenuItem theExitMenu;
    
    private StatsresProg theOperations;
    private UserInterface theInterface;
    private String[] theCurrentSettings;

    /**
     * Default constructor. Create all of the user interface components and display them to the user.
     * @param ui a <code>UserInterface</code> object containing the current control user interface object.
     * @param fileName a <code>String</code> with the current file loaded into the interface.
     * @param isMultipleFiles a <code>boolean</code> indicating whether it is folder or file selection.
     * @param settings a <code>String</code> array of settings for the interface - null equals a new session.
     */
    public StatsresGUI ( UserInterface ui, String fileName, boolean isMultipleFiles, String[] settings ) {
        
        //Set image icon.
        /*Image img = Toolkit.getDefaultToolkit().getImage(StatsresGUI.class.getResource("/logosmall.png"));
        setIconImage(img);*/
        
        //Create ProgramOperations object and store it.
        theOperations = new StatsresProg();
        theInterface = ui;
        theCurrentSettings = settings;
        
        //Set this as the current frame.
        theInterface.setCurrentFrame(this);
        
        //Call the Exit method in the UserInterface class if the user hits exit.
        this.addWindowListener ( new WindowAdapter() {
            public void windowClosing ( WindowEvent e ) {
                theInterface.exit();
            }
        });
        
        //Create menu bar and menu items.
        theMenuBar = new JMenuBar();
        //File menu.
        theFileMenu = new JMenu("File");
        theFileMenu.setMnemonic('F');
        theMenuBar.add(theFileMenu);
        theNewMenu = new JMenuItem("New Session");
        theNewMenu.addActionListener ( new ActionListener() {
            public void actionPerformed ( ActionEvent e ) {
                clearFields();
            }
        });
        theFileMenu.add(theNewMenu);
        theFileMenu.addSeparator();
        theLoadMenu = new JMenu("Load");
        theLoadSettingsMenu = new JMenuItem("Settings");
        theLoadSettingsMenu.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                String fileName = loadSaveInputOutputFile("", true, true);
                if ( !fileName.equalsIgnoreCase("") ) {
                    String[] settings = theOperations.loadSettingsFile(fileName);
                    if ( settings != null ) {
                        new StatsresGUI(theInterface, "", false, settings);
                        dispose();
                    }
                    else {
                        JOptionPane.showMessageDialog(StatsresGUI.this, "The selected file could not be loaded because it is not a valid settings file. Please choose another file.", "ERROR: Could not load selected file", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        theLoadMenu.add(theLoadSettingsMenu);
        theLoadOutputMenu = new JMenuItem("Output");
        theLoadOutputMenu.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                String fileName = loadSaveInputOutputFile("", false, true);
                if ( !fileName.equalsIgnoreCase("") ) {
                    String result = theOperations.loadOutputFile(fileName);
                    if ( !result.equalsIgnoreCase("") ) {
                        theOutputArea.setText(result);
                    }
                    else {
                        JOptionPane.showMessageDialog(StatsresGUI.this, "The selected file could not be loaded because it is not a valid output file. Please choose another file.", "ERROR: Could not load selected file", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        theLoadMenu.add(theLoadOutputMenu);
        theFileMenu.add(theLoadMenu);
        theFileMenu.addSeparator();
        theSaveMenu = new JMenu("Save");
        theSaveSettingsMenu = new JMenuItem("Settings");
        theSaveSettingsMenu.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                String fileName = loadSaveInputOutputFile("", true, false);
                if ( !fileName.equalsIgnoreCase("") ) {
                    if ( theOperations.saveContent(getCurrentSettings(), fileName, ".srs" ) ) {
                        JOptionPane.showMessageDialog(StatsresGUI.this, "Current settings were successfully saved to the selected file!", "Settings Saved Successfully", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });
        theSaveMenu.add(theSaveSettingsMenu);
        theSaveOutputMenu = new JMenuItem("Output");
        theSaveOutputMenu.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                String fileName = loadSaveInputOutputFile("", false, false);
                if ( !fileName.equalsIgnoreCase("") ) {
                	List<String> output = new ArrayList<String>();
                	output.add(theOutputArea.getText());
                    if ( theOperations.saveContent(output, fileName, ".sro" ) ) {
                        JOptionPane.showMessageDialog(StatsresGUI.this, "Output was successfully saved to the selected file!", "Output Saved Successfully", JOptionPane.INFORMATION_MESSAGE);
                    } 
                }
            }
        });
        theSaveMenu.add(theSaveOutputMenu);
        theFileMenu.add(theSaveMenu);
        theFileMenu.addSeparator();
        theExitMenu = new JMenuItem("Exit");
        theExitMenu.addActionListener ( new ActionListener() {
            public void actionPerformed ( ActionEvent e ) {
                theInterface.exit();
            }
        });
        theFileMenu.add(theExitMenu);
        theHelpMenu = new JMenu("Help");
        theHelpMenu.setMnemonic('H');
        theMenuBar.add(theHelpMenu);
        theContentMenu = new JMenuItem("Contents");
        theContentMenu.addActionListener ( new ActionListener() {
            public void actionPerformed ( ActionEvent e ) {
                new HelpGUI();
            }
        });
        theHelpMenu.add(theContentMenu);
        theHelpMenu.addSeparator();
        theAboutMenu = new JMenuItem("About");
        theAboutMenu.addActionListener ( new ActionListener() {
            public void actionPerformed ( ActionEvent e ) {
                new SplashWindow(true, theInterface);
            }
        });
        theHelpMenu.add(theAboutMenu);
        
        //Initialise GUI with title and close attributes.
        this.setTitle ( "Statsres" );
        this.setResizable (false);
        this.setDefaultCloseOperation (DO_NOTHING_ON_CLOSE);
        this.setJMenuBar(theMenuBar);
        
        //Get a container to add things to.
        Container c = this.getContentPane();
        
        //Create a panel to display components.
        JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout( new BoxLayout ( dialogPanel, BoxLayout.PAGE_AXIS ) );
        dialogPanel.add(Box.createRigidArea(new Dimension(0, 10))); //Spacer.
        
        //Create fileOptions panel with border layout.
        JPanel fileOptionsPanel = new JPanel();
        fileOptionsPanel.setLayout( new BoxLayout ( fileOptionsPanel, BoxLayout.PAGE_AXIS ) );
        fileOptionsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        fileOptionsPanel.add(Box.createRigidArea(new Dimension(0, 10))); //Spacer.
        //Create file options heading.
        JPanel fileOptionsTextPanel = new JPanel();
        theFileOptionsLabel = new JLabel("File Options:");
        theFileOptionsLabel.setFont(new Font("Arial", Font.BOLD+Font.ITALIC, 16));
        fileOptionsTextPanel.add(theFileOptionsLabel);
        fileOptionsPanel.add(fileOptionsTextPanel);
        fileOptionsPanel.add(Box.createRigidArea(new Dimension(0, 10))); //Spacer.
        //Create results file panel to choose results file.
        JPanel resultsFilePanel = new JPanel(new FlowLayout());
        //Results File Label.
        theResultsFileLabel = new JLabel("Input File:");
        resultsFilePanel.add(theResultsFileLabel);
        //Results File Field.
        if ( theCurrentSettings != null ) {
            theResultsFileField = new JTextField(theCurrentSettings[0]);
        }
        else {
            theResultsFileField = new JTextField(fileName);
        }
        theResultsFileField.setColumns(30);
        resultsFilePanel.add(theResultsFileField);
        //Results File Button.
        theResultsFileButton = new JButton("Choose");
        theResultsFileButton.addActionListener ( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String file = loadResultsFile(theResultsFileField.getText(), theIncludeSubFoldersBox.isSelected());
                if ( !file.equalsIgnoreCase("") ) {
                    new StatsresGUI(theInterface, file, theIncludeSubFoldersBox.isSelected(),theCurrentSettings);
                    dispose();
                }
            }
        }); 
        resultsFilePanel.add(theResultsFileButton);
        
        //Add results file panel to dialogPanel.
        fileOptionsPanel.add(resultsFilePanel);
        fileOptionsPanel.add(Box.createRigidArea(new Dimension(0, 10))); //Spacer.
            
        //Create subfolder panel containing options about whether subfolders are included.
        JPanel subfolderPanel = new JPanel ( new FlowLayout() );
        //Include SubFolders JCheckBox.
        if ( theCurrentSettings != null ) {
            isMultipleFiles = Boolean.parseBoolean(theCurrentSettings[1]);
        }
        if ( isMultipleFiles ) {
            theIncludeSubFoldersBox = new JCheckBox("Include Input Files in Subfolders", true);
        }
        else {
            theIncludeSubFoldersBox = new JCheckBox("Include Input Files in Subfolders", false);
        }
        theIncludeSubFoldersBox.addActionListener ( new ActionListener ( ) {
            public void actionPerformed ( ActionEvent e ) {
                if ( theIncludeSubFoldersBox.isSelected() ) {
                    theResultsFileLabel.setText("Input Folder:");
                }
                else {
                    theResultsFileLabel.setText("Input File:");
                }
            }
        });
        subfolderPanel.add(theIncludeSubFoldersBox);
        
        //Add subfolder panel to dialog panel.
        fileOptionsPanel.add(subfolderPanel);
        fileOptionsPanel.add(Box.createRigidArea(new Dimension(0, 10))); //Spacer.
        
        //Add results file panel to dialogPanel.
        dialogPanel.add(fileOptionsPanel);
        dialogPanel.add(Box.createRigidArea(new Dimension(0, 10))); //Spacer.
        
        //Create results selection panel to contain column panel and selection buttons.
        JPanel resultsSelectionPanel = new JPanel();
        resultsSelectionPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        resultsSelectionPanel.setLayout( new BoxLayout ( resultsSelectionPanel, BoxLayout.PAGE_AXIS ) );
        resultsSelectionPanel.add(Box.createRigidArea(new Dimension(0, 10))); //Spacer.
        //Create data options heading.
        JPanel dataOptionsTextPanel = new JPanel();
        theDataOptionsLabel = new JLabel("Data Options:");
        theDataOptionsLabel.setFont(new Font("Arial", Font.BOLD+Font.ITALIC, 16));
        dataOptionsTextPanel.add(theDataOptionsLabel);
        resultsSelectionPanel.add(dataOptionsTextPanel);
        resultsSelectionPanel.add(Box.createRigidArea(new Dimension(0, 10))); //Spacer.
        //JPanel for columns.
        JPanel columnPanel = new JPanel(new FlowLayout());
        //JLabel for column heading.
        theColumnLabel = new JLabel("Select Column(s):");
        columnPanel.add(theColumnLabel);
        //JList for column headings.
        theColumnData = new DefaultListModel<String>();
        //Only process contents of file if current settings is not null or a file was selected!
        if ( theCurrentSettings != null ) {
            String[] columns = theCurrentSettings[2].split(",");
            for ( int i = 0; i < columns.length; i++ ) {
                theColumnData.addElement(columns[i]);
            }
        }
        else if ( !theResultsFileField.getText().equalsIgnoreCase("") ) { 
            //If folder selected then get list of files.
            List<String> fileList = new LinkedList<String>();
            if ( theIncludeSubFoldersBox.isSelected() ) {
                StatsresProg sp = new StatsresProg();
                //System.out.println("Running on file list...");
                fileList = sp.getAllFiles(theResultsFileField.getText());
            }
            else {
                fileList.add(theResultsFileField.getText());
            }
            //Load contents of file and whole of first line, colon-separated gives content.
            if ( fileList.size() > 0 ) {
                String firstLine = ReadWriteFile.readFile(fileList.get(0), true).get(0);
                if ( firstLine != null ) {
                    if ( firstLine.contains(",") ) {
                        boolean addElement = true;
                        for ( int j = 0; j < firstLine.length(); j++ ) {
                            if ( !Character.isLetterOrDigit(firstLine.charAt(j)) && firstLine.charAt(j)!=',' ) {
                                addElement = false;
                            }
                        }
                        if ( addElement ) {
                            String[] contents = firstLine.split(",");
                            for ( int i = 0; i < contents.length; i++ ) {
                                theColumnData.addElement(contents[i]);
                            }
                        }
                        else {
                            theResultsFileField.setText("");
                            JOptionPane.showMessageDialog(StatsresGUI.this, "The selected file could not be loaded because it is not a valid input file. Please choose another file.", "ERROR: Could not load selected file", JOptionPane.ERROR_MESSAGE);
                        }   
                    }
                    else {
                        theResultsFileField.setText("");
                        JOptionPane.showMessageDialog(StatsresGUI.this, "The selected file could not be loaded because it is not a valid input file. Please choose another file.", "ERROR: Could not load selected file", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                theResultsFileField.setText("");
                JOptionPane.showMessageDialog(StatsresGUI.this, "The selected file could not be loaded because it is not a valid input file. Please choose another file.", "ERROR: Could not load selected file", JOptionPane.ERROR_MESSAGE);
            }
        }
        theColumnHeadings = new JList<String>(theColumnData);
        theColumnHeadings.setVisibleRowCount(3);
        JScrollPane columnPane = new JScrollPane(theColumnHeadings);
        columnPanel.add(columnPane);
        //Add column panel to results selection panel.
        resultsSelectionPanel.add(columnPanel);
        resultsSelectionPanel.add(Box.createRigidArea(new Dimension(0,5)));
        //Create select button panel.
        JPanel selectButtonPanel = new JPanel();
        theSelectAllButton = new JButton("Select All");
        theSelectAllButton.addActionListener( new ActionListener() {
            public void actionPerformed ( ActionEvent e ) {
                if ( theColumnHeadings.getModel().getSize()>0 ) {
                    theColumnHeadings.setSelectionInterval(0, theColumnHeadings.getModel().getSize()-1);
                }
            }
        }); 
        selectButtonPanel.add(theSelectAllButton);
        theDeselectAllButton = new JButton("Deselect All");
        theDeselectAllButton.addActionListener( new ActionListener() {
            public void actionPerformed ( ActionEvent e ) {
                theColumnHeadings.clearSelection();
            }
        });
        selectButtonPanel.add(theDeselectAllButton);
        //Add selectButtonPanel to results selection panel.
        resultsSelectionPanel.add(selectButtonPanel);
        resultsSelectionPanel.add(Box.createRigidArea(new Dimension(0, 10))); //Spacer.
        
        //Add results selection panel to dialog panel.
        dialogPanel.add(resultsSelectionPanel);
        dialogPanel.add(Box.createRigidArea(new Dimension(0, 10))); //Spacer.
        
        //Create statsOptionPanel with box layout.
        JPanel statsOptionsPanel = new JPanel();
        statsOptionsPanel.setLayout( new BoxLayout ( statsOptionsPanel, BoxLayout.PAGE_AXIS ) );
        statsOptionsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        statsOptionsPanel.add(Box.createRigidArea(new Dimension(0, 10))); //Spacer.
        //Create file options heading.
        JPanel statsOptionTextPanel = new JPanel();
        theStatsOptionLabel = new JLabel("Statistical Options:");
        theStatsOptionLabel.setFont(new Font("Arial", Font.BOLD+Font.ITALIC, 16));
        statsOptionTextPanel.add(theStatsOptionLabel);
        statsOptionsPanel.add(statsOptionTextPanel);
        statsOptionsPanel.add(Box.createRigidArea(new Dimension(0, 10))); //Spacer.
        //Create panel for checkable options - grid layout 5 to 1.
        JPanel checkableFirstPanel = new JPanel(new GridLayout(1,5,5,5));
        //Mean value.
        if ( theCurrentSettings != null ) { theMeanBox = new JCheckBox("Mean", Boolean.parseBoolean(theCurrentSettings[3])); }
        else { theMeanBox = new JCheckBox("Mean", true); }
        checkableFirstPanel.add(theMeanBox);
        //Min value.
        if ( theCurrentSettings != null ) { theMinBox = new JCheckBox("Minimum", Boolean.parseBoolean(theCurrentSettings[4])); }
        else { theMinBox = new JCheckBox("Minimum", true); }
        checkableFirstPanel.add(theMinBox);
        //Max value.
        if ( theCurrentSettings != null ) { theMaxBox = new JCheckBox("Maximum", Boolean.parseBoolean(theCurrentSettings[5])); }
        else { theMaxBox = new JCheckBox("Maximum", true); }
        checkableFirstPanel.add(theMaxBox);
        //Median value.
        if ( theCurrentSettings != null ) { theMedianBox = new JCheckBox("Median", Boolean.parseBoolean(theCurrentSettings[6])); }
        else { theMedianBox = new JCheckBox("Median", true); }
        checkableFirstPanel.add(theMedianBox);
        //Count value.
        if ( theCurrentSettings != null ) { theCountBox = new JCheckBox("Count", Boolean.parseBoolean(theCurrentSettings[7])); }
        else { theCountBox = new JCheckBox("Count", true); }
        checkableFirstPanel.add(theCountBox);
        //Add checkable first panel to statsOption panel.
        statsOptionsPanel.add(checkableFirstPanel);
        statsOptionsPanel.add(Box.createRigidArea(new Dimension(0, 10))); //Spacer.
        
        //Create panel for second checkable options - grid layout 4 to 1.
        JPanel checkableSecondPanel = new JPanel(new GridLayout(1,5,5,5));
        //IQR value.
        if ( theCurrentSettings != null ) { theIQRBox = new JCheckBox("IQR", Boolean.parseBoolean(theCurrentSettings[8])); }
        else { theIQRBox = new JCheckBox("IQR", true); }
        checkableSecondPanel.add(theIQRBox);
        //1st Quartile value.
        if ( theCurrentSettings != null ) { the1QBox = new JCheckBox("1st Quartile", Boolean.parseBoolean(theCurrentSettings[9])); }
        else { the1QBox = new JCheckBox("1st Quartile", true); }
        checkableSecondPanel.add(the1QBox);
        //3rd Quartile value.
        if ( theCurrentSettings != null ) { the3QBox = new JCheckBox("3rd Quartile", Boolean.parseBoolean(theCurrentSettings[10])); }
        else { the3QBox = new JCheckBox("3rd Quartile", true); }
        checkableSecondPanel.add(the3QBox);
        //Standard Deviation value.
        if ( theCurrentSettings != null ) { theStDevBox = new JCheckBox("Standard Deviation", Boolean.parseBoolean(theCurrentSettings[11])); }
        theStDevBox = new JCheckBox("Standard Deviation", true);
        checkableSecondPanel.add(theStDevBox);
        //Add checkable second panel to statsOption panel.
        statsOptionsPanel.add(checkableSecondPanel);
        statsOptionsPanel.add(Box.createRigidArea(new Dimension(0, 10))); //Spacer.
        
        //Add statsOptionPanel to dialog panel.
        dialogPanel.add(statsOptionsPanel);
        dialogPanel.add(Box.createRigidArea(new Dimension(0, 10))); //Spacer.
        
        //Create button panel.
        JPanel buttonPanel = new JPanel(new FlowLayout());
        //Create the Process Button.
        theProcessButton = new JButton ( "Process Results" );
        theProcessButton.addActionListener ( new ActionListener() {
            public void actionPerformed ( ActionEvent e ) {
                //Check process variable.
                if ( theInterface.getProcessRunning() ) {
                    JOptionPane.showMessageDialog(StatsresGUI.this, "Cannot start process as another process is already running!", "ERROR: Attempting to Start Multiple Processes!", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    //Set isProcessRunning variable to true.
                    theInterface.setProcessRunning(true);
                    //Make list of boolean options.
                    java.util.List<StatisticalFunctions> statisticalFunctions = new ArrayList<StatisticalFunctions>();
                    if ( theMeanBox.isSelected() ) {
                    	statisticalFunctions.add(StatisticalFunctions.MEAN);
                    } 
                    if ( theMinBox.isSelected() ) {
                    	statisticalFunctions.add(StatisticalFunctions.MIN);
                    }
                    if ( theMaxBox.isSelected() ) {
                    	statisticalFunctions.add(StatisticalFunctions.MAX);
                    }
                    if ( theMedianBox.isSelected() ) {
                    	statisticalFunctions.add(StatisticalFunctions.MEDIAN);
                    }
                    if ( theCountBox.isSelected() ) {
                    	statisticalFunctions.add(StatisticalFunctions.COUNT);
                    }
                    if ( the1QBox.isSelected() ) {
                    	statisticalFunctions.add(StatisticalFunctions.QUARTILE_FIRST);
                    }
                    if ( the3QBox.isSelected() ) {
                    	statisticalFunctions.add(StatisticalFunctions.QUARTILE_THIRD);
                    }
                    if ( theIQRBox.isSelected() ) {
                    	statisticalFunctions.add(StatisticalFunctions.INTER_QUARTILE_RANGE);
                    }
                    if ( theStDevBox.isSelected() ) {
                    	statisticalFunctions.add(StatisticalFunctions.STANDARD_DEVIATION);
                    }
                    //Do all other work in another thread to improve performance.
                    ProcessRunner pr = new ProcessRunner(theInterface, theResultsFileField.getText(), statisticalFunctions, theColumnHeadings, theOutputArea, theIncludeSubFoldersBox.isSelected(), StatsresGUI.this );
                    new Thread(pr).start();
                }
            }
        });
        theProcessButton.setMaximumSize(new Dimension(50, 25));
        buttonPanel.add ( theProcessButton );
        //Create the Clear Button.
        theClearButton = new JButton("Reset");
        theClearButton.addActionListener ( new ActionListener() {
            public void actionPerformed ( ActionEvent e ) {
                clearFields();
            }
        });
        theClearButton.setMaximumSize(new Dimension(50, 25));
        buttonPanel.add ( theClearButton );
        //Create the Exit Button.
        theExitButton = new JButton ( "Exit" );
        theExitButton.addActionListener ( new ActionListener() {
            public void actionPerformed ( ActionEvent e ) {
                theInterface.exit();
            }
        });
        theExitButton.setMaximumSize(new Dimension(50, 25));
        buttonPanel.add ( theExitButton );
        //Add Create and Exit buttons to the dialog.
        dialogPanel.add( buttonPanel );
        dialogPanel.add(Box.createRigidArea(new Dimension(0, 10))); //Spacer.
        
        //Create output area with label and then output area.
        JPanel outputTextPanel = new JPanel();
        theOutputLabel = new JLabel("Output:");
        theOutputLabel.setFont(new Font("Arial", Font.BOLD+Font.ITALIC, 16));
        outputTextPanel.add(theOutputLabel);
        dialogPanel.add(outputTextPanel);
        dialogPanel.add(Box.createRigidArea(new Dimension(0, 10))); //Spacer.
        
        theOutputArea = new JTextArea(8,10);
        theOutputArea.setEditable(false);
        theOutputArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        theOutputArea.setText("No output available!");
        JScrollPane theOutputPane = new JScrollPane(theOutputArea);
        theOutputPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        theOutputPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        dialogPanel.add(theOutputPane);
        dialogPanel.add(Box.createRigidArea(new Dimension(0, 10))); //Spacer.
        
        //Add the panel to the container.
        c.add ( dialogPanel );
        
        //Display the dialog box to the user.
        //this.pack ();
        this.setVisible (true);
        this.setSize ( getPreferredSize() );
        
        // Set the window's bounds, centering the window
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - this.getWidth()) / 2;
        int y = (screen.height - this.getHeight()) / 2;
        setBounds(x, y, this.getWidth(), this.getHeight());
        
    }
      
    /**
     * Method to load results file into the interface.
     * @param location a <code>String</code> with the last opened file.
     * @param allowDirs a <code>Boolean</code> indicating whether user can select directories or files.
     * @return a <code>String</code> containing the name of the file to load.
     */
    public String loadResultsFile ( String location, boolean allowDirs ) {
        //Determine location of last file as user may wish to choose another file from that directory.
        JFileChooser fileDialog;
        if ( !location.equalsIgnoreCase("") ) {
            String[] locSplit = location.split("\\\\");
            String folderLocation = "";
            for ( int i = 0; i < locSplit.length-1; i++ ) {
                folderLocation += locSplit[i] + "\\";
            }
            //Create results file open dialog box.
            fileDialog = new JFileChooser(folderLocation);
        }
        else {
            //Create results file open dialog box.
            fileDialog = new JFileChooser();
        }
        fileDialog.setDialogTitle("Load Results File");
        //Determine what user can select.
        if ( allowDirs ) {
            fileDialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        }
        else {
            fileDialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
        }
        //Only display .csv files.
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Comma Separated Values File (.csv)", "csv");
        fileDialog.setFileFilter(filter);
        int returnVal = fileDialog.showOpenDialog(this);
        //Check if the user submitted a file.
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            //Return the path of the file selected.
            return fileDialog.getSelectedFile().getPath();
        }
        //Return blank if user didn't select file.
        return "";
    }
    
    /**
     * Method to load/save either input or output file into the interface.
     * @param location a <code>String</code> with the last opened file.
     * @param isInput a <code>Boolean</code> indicating whether it is input (true) or output (false).
     * @param load a <code>Boolean</code> which is true iff load function activated. Otherwise save will be displayed in title.
     * @return a <code>String</code> containing the name of the file to load.
     */
    public String loadSaveInputOutputFile ( String location, boolean isInput, boolean load ) {
        //Determine location of last file as user may wish to choose another file from that directory.
        JFileChooser fileDialog;
        if ( !location.equalsIgnoreCase("") ) {
            String[] locSplit = location.split("\\\\");
            String folderLocation = "";
            for ( int i = 0; i < locSplit.length-1; i++ ) {
                folderLocation += locSplit[i] + "\\";
            }
            //Create results file open dialog box.
            fileDialog = new JFileChooser(folderLocation);
        }
        else {
            //Create results file open dialog box.
            fileDialog = new JFileChooser();
        }
        if (isInput) { 
        	if (load) { 
        		fileDialog.setDialogTitle("Load Settings File"); 
        	} else {
        		fileDialog.setDialogTitle("Save Settings File");
        	}
        } else {
        	if (load) {
        		fileDialog.setDialogTitle("Load Output File"); 
        	} else {
        		fileDialog.setDialogTitle("Save Output File");
        	}
        }
        //Set file selection mode to files only.
        fileDialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
        //Only display relevant extension.
        FileNameExtensionFilter filter;
        if (isInput) { filter = new FileNameExtensionFilter("Statsres Settings File (.srs)", "srs"); }
        else { filter = new FileNameExtensionFilter("Statsres Output File (.sro)", "sro"); }
        fileDialog.setFileFilter(filter);
        int returnVal = fileDialog.showOpenDialog(this);
        //Check if the user submitted a file.
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            //Return the path of the file selected.
            return fileDialog.getSelectedFile().getPath();
        }
        //Return blank if user didn't select file.
        return "";
    }
    
    /**
     * Private method to clear all the fields of the interface.
     */
    private void clearFields () {
        theResultsFileField.setText(""); theIncludeSubFoldersBox.setSelected(false);
        theColumnData.clear();
        the1QBox.setSelected(true); the3QBox.setSelected(true);
        theCountBox.setSelected(true); theIQRBox.setSelected(true);
        theMeanBox.setSelected(true); theMedianBox.setSelected(true);
        theStDevBox.setSelected(true); theMaxBox.setSelected(true);
        theMinBox.setSelected(true);
    }
    
    /**
     * Private method to save current settings to a text file.
     * @return a <code>String</code> linked list containing current settings.
     */
    private LinkedList<String> getCurrentSettings () {
        //Create list to return.
        LinkedList<String> settingsList = new LinkedList<String>();
        //Add file.
        settingsList.add("File=" + theResultsFileField.getText());
        //Add checkbox.
        settingsList.add("File Checkbox=" + theIncludeSubFoldersBox.isSelected());
        //Add column data stuff.
        String columnData = "Column Data=";
        for ( int i = 0; i < theColumnData.getSize(); i++) {
            columnData += theColumnData.get(i) + ",";
        }
        settingsList.add(columnData);
        //Now add each checkbox.
        settingsList.add("Mean=" + theMeanBox.isSelected());
        settingsList.add("Min=" + theMinBox.isSelected());
        settingsList.add("Max=" + theMaxBox.isSelected());
        settingsList.add("Median=" + theMedianBox.isSelected());
        settingsList.add("Count=" + theCountBox.isSelected());
        settingsList.add("IQR=" + theIQRBox.isSelected());
        settingsList.add("1Q=" + the1QBox.isSelected());
        settingsList.add("3Q=" + the3QBox.isSelected());
        settingsList.add("StDev=" + theStDevBox.isSelected());
        //Return linked list.
        return settingsList;
    }
    
}