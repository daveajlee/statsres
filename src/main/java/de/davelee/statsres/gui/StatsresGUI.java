package de.davelee.statsres.gui;
//Import the required java classes.
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.border.BevelBorder;
//Import file extension package.
import javax.swing.filechooser.FileNameExtensionFilter;

import de.davelee.statsres.main.*;

/**
 * StatsresGUI.java is a class to display the Statsres application.
 * @author Dave Lee.
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
    
    private JLabel theDataOptionsLabel;
    private JLabel theColumnLabel;
    private DefaultListModel<String> theColumnData;
    private JList<String> theColumnHeadings;
    private JButton theSelectAllButton;
    private JButton theDeselectAllButton;
    
    private JLabel theStatusBarLabel;
    
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
    
    private StatsresProg theOperations;
    private UserInterface theInterface;
    private StatsresSettings theCurrentSettings;
    
    private static final Font ARIAL_BOLD = new Font("Arial", Font.BOLD+Font.ITALIC, 16);
    
    /**
     * Default constructor. Create all of the user interface components and display them to the user.
     * @param ui a <code>UserInterface</code> object containing the current control user interface object.
     * @param statsresProg a <code>StatsresProg</code> object representing the program functions.
     * @param fileName a <code>String</code> with the current file loaded into the interface.
     * @param settings a <code>StatsresSettings</code> object representing the current settings for Statsres.
     * @param testMode a <code>boolean</code> which is true iff the gui should not be displayed during JUnit tests.
     */
    public StatsresGUI ( UserInterface ui, StatsresProg statsresProg, String fileName, StatsresSettings settings, boolean testMode ) {
        
    	//Create ProgramOperations object and store it.
        theOperations = statsresProg;
        theInterface = ui;
        
        if ( settings == null ) {
        	theCurrentSettings = StatsresSettings.createDefaultSettings(fileName);
        } else {
        	theCurrentSettings = settings;
        }
        
        //Set this as the current frame.
        theInterface.setCurrentFrame(this);
    	
        addHeaderInfo(testMode);
        
        //Get a container to add things to.
        Container c = this.getContentPane();
        
        //Add the panel to the container.
        c.add ( createDialogPanel(fileName, testMode) );
        
        //Display the dialog box to the user.
        if (!testMode) {
        	this.setVisible (true);
        	this.setSize ( getPreferredSize() );
        }
        
        setLocationBounds();        
    }
    
    /**
     * Create the status bar panel for the specified width in pixels.
     * @param width a <code>int</code> with the width in pixels.
     * @return a <code>JPanel</code> object representing the created status bar panel.
     */
    public JPanel createStatusBarPanel ( final int width ) {
    	// Code adapted from http://stackoverflow.com/questions/3035880/how-can-i-create-a-bar-in-the-bottom-of-a-java-app-like-a-status-bar
    	JPanel statusPanel = new JPanel();
    	statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
    	statusPanel.setSize(new Dimension(width, 16));
    	statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
    	theStatusBarLabel = new JLabel("Ready...");
    	theStatusBarLabel.setHorizontalAlignment(SwingConstants.LEFT);
    	statusPanel.add(theStatusBarLabel);
    	return statusPanel;
    }
    
    /**
     * Update the status bar with the specified text.
     * @param text a <code>String</code> with the new text.
     */
    public void updateStatusText ( final String text ) {
    	theStatusBarLabel.setText(text);
    }
    
    /**
     * Create the dialog panel based on the specified file name.
     * @param fileName a <code>String</code> with the file name to load. Can be empty.
     * @param testMode a <code>boolean</code> which is true iff the method is being called through JUnit tests.
     * @return a <code>JPanel</code> object representing the created dialog panel.
     */
    public JPanel createDialogPanel ( final String fileName, final boolean testMode ) {
    	JPanel overallDialogPanel = new JPanel(new BorderLayout());
    	
    	JPanel statusPanel = createStatusBarPanel(this.getWidth());
    	
    	//Create a panel to display components.
        JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout( new BoxLayout ( dialogPanel, BoxLayout.PAGE_AXIS ) );
        dialogPanel.add(Box.createRigidArea(new Dimension(0, 10))); //Spacer.
        
        //Add results file panel to dialogPanel.
        dialogPanel.add(createFileOptionsPanel(fileName, testMode));
        dialogPanel.add(Box.createRigidArea(new Dimension(0, 10))); //Spacer.
       
        //Add results selection panel to dialog panel.
        dialogPanel.add(createResultsSelectionPanel());
        dialogPanel.add(Box.createRigidArea(new Dimension(0, 10))); //Spacer.
        
        //Add statsOptionPanel to dialog panel.
        dialogPanel.add(createStatsOptionPanel());
        dialogPanel.add(Box.createRigidArea(new Dimension(0, 10))); //Spacer.
        
        //Add button panel to dialog panel.
        dialogPanel.add(createButtonPanel());
        dialogPanel.add(Box.createRigidArea(new Dimension(0, 10))); //Spacer.
        
        //Add outputTextPanel to dialog panel.
        dialogPanel.add(createOutputTextPanel());
        dialogPanel.add(Box.createRigidArea(new Dimension(0, 10))); //Spacer.
        
        //Add outputPane to dialog panel.
        dialogPanel.add(createOutputPane());
        dialogPanel.add(Box.createRigidArea(new Dimension(0, 10))); //Spacer.
        
        overallDialogPanel.add(dialogPanel, BorderLayout.CENTER);
        
        overallDialogPanel.add(statusPanel, BorderLayout.SOUTH);
        
        return overallDialogPanel;
    }
      
    /**
     * Method to load results file into the interface.
     * @param location a <code>String</code> with the last opened file.
     * @return a <code>String</code> containing the name of the file to load.
     */
    public String loadResultsFile ( String location ) {
        //Determine location of last file as user may wish to choose another file from that directory.
        JFileChooser fileDialog = new JFileChooser(location);
        fileDialog.setDialogTitle("Load Results File");
        fileDialog.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
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
     * Add the header info to the panel e.g. icons, texts etc.
     * @param testMode a <code>boolean</code> which is true iff the method is being called through a JUnit test.
     */
    public void addHeaderInfo ( final boolean testMode ) {
    	//Set image icon.
        Image img = Toolkit.getDefaultToolkit().getImage(StatsresGUI.class.getResource("/logosmall.png"));
        setIconImage(img);
        
        //Call the Exit method in the UserInterface class if the user hits exit.
        this.addWindowListener ( new WindowAdapter() {
            public void windowClosing ( WindowEvent e ) {
                theInterface.exit();
            }
        });
        
        //Initialise GUI with title and close attributes.
        this.setTitle ( "Statsres" );
        this.setResizable (false);
        this.setDefaultCloseOperation (DO_NOTHING_ON_CLOSE);
        this.setJMenuBar(createMenuBar(testMode));
    }
    
    /**
     * Update the status bar with an error message for the specified file type.
     * @param fileType a <code>String</code> with the file type to display an error message for.
     */
    public void showErrorStatus ( final String fileType ) {
    	updateStatusText("The selected file could not be loaded because it is not a valid " + fileType + " file. Please choose another file.");
    }
    
    /**
     * Load the settings from the menu.
     * @param testMode a <code>boolean</code> which is true iff the method is called from JUnit tests.
     * @param fileName a <code>String</code> with the file to load the settings from.
     */
    public void loadSettingsMenu ( final boolean testMode, final String fileName ) {
    	StatsresSettings settings = theOperations.loadSettingsFile(fileName);
        if ( settings != null ) {
        	new StatsresGUI(theInterface, theOperations, "", settings, testMode);
            dispose();
        } else {
        	showErrorStatus("settings");
        }
    }
    
    /**
     * Load the output files from the menu.
     * @param fileName a <code>String</code> with the file to load the output from.
     */
    public void loadOutputMenu ( final String fileName ) {
    	String result = theOperations.loadOutputFile(fileName);
        if ( !"".equalsIgnoreCase(result) ) {
        	theOutputArea.setText(result);
        } else {
        	showErrorStatus("output");
        }
    }
    
    /**
     * Save the settings from the menu.
     * @param fileName a <code>String</code> with the file to save the settings to.
     */
    public void saveSettingsMenu ( final String fileName ) {
        if ( theOperations.saveContent(saveCurrentSettings().saveAsV1File(), fileName, ".srs" ) ) {
        	updateStatusText("Current settings were successfully saved to the selected file!");
        }
    }
    
    /**
     * Save the output text from the menu.
     * @param fileName a <code>String</code> with the file to save the output text to.
     */
    public void saveOutputMenu ( final String fileName ) {
        List<String> output = new ArrayList<String>();
        output.add(theOutputArea.getText());
        if ( theOperations.saveContent(output, fileName, ".sro" ) ) {
            updateStatusText("Output was successfully saved to the selected file!");
        }
    }
    
    /**
     * Create the menu bar.
     * @param testMode a <code>boolean</code> which is true iff the method is called from the JUnit tests.
     * @return a <code>JMenuBar</code> object representing the created menu bar.
     */
    public JMenuBar createMenuBar ( final boolean testMode ) {
    	//Create menu bar and menu items.
        JMenuBar menuBar = new JMenuBar();
        //File menu.
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic('F');
        menuBar.add(fileMenu);
        JMenuItem newMenuItem = new JMenuItem("New Session");
        newMenuItem.addActionListener ( new ActionListener() {
            public void actionPerformed ( ActionEvent e ) {
                clearFields();
            }
        });
        fileMenu.add(newMenuItem);
        fileMenu.addSeparator();
        JMenu loadMenu = new JMenu("Load");
        JMenuItem loadSettingsMenuItem = new JMenuItem("Settings");
        loadSettingsMenuItem.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                loadSettingsMenu(testMode, loadSaveInputOutputFile("", "Load Settings File"));
            }
        });
        loadMenu.add(loadSettingsMenuItem);
        JMenuItem loadOutputMenuItem = new JMenuItem("Output");
        loadOutputMenuItem.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                loadOutputMenu(loadSaveInputOutputFile("", "Load Output File"));
            }
        });
        loadMenu.add(loadOutputMenuItem);
        fileMenu.add(loadMenu);
        fileMenu.addSeparator();
        JMenu saveMenu = new JMenu("Save");
        JMenuItem saveSettingsMenuItem = new JMenuItem("Settings");
        saveSettingsMenuItem.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                saveSettingsMenu(loadSaveInputOutputFile("", "Save Settings File"));
            }
        });
        saveMenu.add(saveSettingsMenuItem);
        JMenuItem saveOutputMenuItem = new JMenuItem("Output");
        saveOutputMenuItem.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                saveOutputMenu(loadSaveInputOutputFile("", "Save Output File"));
            }
        });
        saveMenu.add(saveOutputMenuItem);
        fileMenu.add(saveMenu);
        fileMenu.addSeparator();
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener ( new ActionListener() {
            public void actionPerformed ( ActionEvent e ) {
                theInterface.exit();
            }
        });
        fileMenu.add(exitMenuItem);
        JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic('H');
        menuBar.add(helpMenu);
        JMenuItem contentMenuItem = new JMenuItem("Contents");
        contentMenuItem.addActionListener ( new ActionListener() {
            public void actionPerformed ( ActionEvent e ) {
                new HelpGUI(false);
            }
        });
        helpMenu.add(contentMenuItem);
        helpMenu.addSeparator();
        JMenuItem aboutMenuItem = new JMenuItem("About");
        aboutMenuItem.addActionListener ( new ActionListener() {
            public void actionPerformed ( ActionEvent e ) {
                new SplashWindow(true, theInterface, false);
            }
        });
        helpMenu.add(aboutMenuItem);
        return menuBar;
    }
    
    /**
     * Create a file options panel.
     * @param fileName a <code>String</code> with the current selected file name.
     * @param testMode a <code>boolean</code> which is true iff the method is called from JUnit tests.
     * @return a <code>JPanel</code> object representing the created file options panel.
     */
    public JPanel createFileOptionsPanel ( final String fileName, final boolean testMode ) {
    	//Create fileOptions panel with border layout.
        JPanel fileOptionsPanel = new JPanel();
        fileOptionsPanel.setLayout( new BoxLayout ( fileOptionsPanel, BoxLayout.PAGE_AXIS ) );
        fileOptionsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        fileOptionsPanel.add(Box.createRigidArea(new Dimension(0, 10))); //Spacer.
        //Create file options heading.
        JPanel fileOptionsTextPanel = new JPanel();
        theFileOptionsLabel = new JLabel("File Options:");
        theFileOptionsLabel.setFont(ARIAL_BOLD);
        fileOptionsTextPanel.add(theFileOptionsLabel);
        fileOptionsPanel.add(fileOptionsTextPanel);
        fileOptionsPanel.add(Box.createRigidArea(new Dimension(0, 10))); //Spacer.
        //Create results file panel to choose results file.
        JPanel resultsFilePanel = new JPanel(new FlowLayout());
        //Results File Label.
        theResultsFileLabel = new JLabel("Input File:");
        resultsFilePanel.add(theResultsFileLabel);
        //Results File Field.
        theResultsFileField = new JTextField(theCurrentSettings.getFile());
        theResultsFileField.setColumns(30);
        resultsFilePanel.add(theResultsFileField);
        //Results File Button.
        theResultsFileButton = new JButton("Choose");
        theResultsFileButton.addActionListener ( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String file = loadResultsFile(theResultsFileField.getText());
                if ( !"".equalsIgnoreCase(file) ) {
                    new StatsresGUI(theInterface, theOperations, file, theCurrentSettings, testMode);
                    dispose();
                }
            }
        }); 
        resultsFilePanel.add(theResultsFileButton);
        
        //Add results file panel to dialogPanel.
        fileOptionsPanel.add(resultsFilePanel);
        fileOptionsPanel.add(Box.createRigidArea(new Dimension(0, 10))); //Spacer.
            
        return fileOptionsPanel;
    }
    
    /**
     * Create the results selection panel.
     * @return a <code>JPanel</code> object representing the created results selection panel.
     */
    public JPanel createResultsSelectionPanel ( ) {
    	//Create results selection panel to contain column panel and selection buttons.
        JPanel resultsSelectionPanel = new JPanel();
        resultsSelectionPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        resultsSelectionPanel.setLayout( new BoxLayout ( resultsSelectionPanel, BoxLayout.PAGE_AXIS ) );
        resultsSelectionPanel.add(Box.createRigidArea(new Dimension(0, 10))); //Spacer.
        //Create data options heading.
        JPanel dataOptionsTextPanel = new JPanel();
        theDataOptionsLabel = new JLabel("Data Options:");
        theDataOptionsLabel.setFont(ARIAL_BOLD);
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
        List<String> columns = theCurrentSettings.getColumnData();
        for ( int i = 0; i < columns.size(); i++ ) {
        	theColumnData.addElement(columns.get(i));
        }
        if ( !"".equalsIgnoreCase(theResultsFileField.getText()) ) { 
            //If folder selected then get list of files.
            if ( !theResultsFileField.getText().endsWith(".csv") ) {
                StatsresProg sp = new StatsresProg();
                List<String> fileList = sp.getAllFiles(theResultsFileField.getText());
                if ( ! fileList.isEmpty()) {
                	loadFileGUI(fileList.get(0));
                } else {
                    theResultsFileField.setText("");
                    showErrorStatus("input");
                }
            } else {
                loadFileGUI(theResultsFileField.getText());
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
        return resultsSelectionPanel;
    }
    
    /**
     * Load a file from a GUI button.
     * @param file a <code>String</code> with the file name.
     */
    public void loadFileGUI ( final String file) {
    	//Load contents of file and whole of first line, colon-separated gives content.
        String firstLine = ReadWriteFile.readFile(file, true).get(0);
        String[] contents = firstLine.split(",");
        if ( contents.length == 0 ) {
        	theResultsFileField.setText("");
            showErrorStatus("input");
        }
        for ( int i = 0; i < contents.length; i++ ) {
            theColumnData.addElement(contents[i]);
        }
    }
    
    /**
     * Create the statistical options panel.
     * @return a <code>JPanel</code> object representing the statistical options panel.
     */
    public JPanel createStatsOptionPanel ( ) {
    	//Create statsOptionPanel with box layout.
        JPanel statsOptionsPanel = new JPanel();
        statsOptionsPanel.setLayout( new BoxLayout ( statsOptionsPanel, BoxLayout.PAGE_AXIS ) );
        statsOptionsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        statsOptionsPanel.add(Box.createRigidArea(new Dimension(0, 10))); //Spacer.
        //Create file options heading.
        JPanel statsOptionTextPanel = new JPanel();
        theStatsOptionLabel = new JLabel("Statistical Options:");
        theStatsOptionLabel.setFont(ARIAL_BOLD);
        statsOptionTextPanel.add(theStatsOptionLabel);
        statsOptionsPanel.add(statsOptionTextPanel);
        statsOptionsPanel.add(Box.createRigidArea(new Dimension(0, 10))); //Spacer.
        //Create panel for checkable options - grid layout 5 to 1.
        JPanel checkableFirstPanel = new JPanel(new GridLayout(1,5,5,5));
        //Mean value.
        theMeanBox = new JCheckBox("Mean", theCurrentSettings.getStatisticalFunctions().contains(StatisticalFunctions.MEAN)); 
        checkableFirstPanel.add(theMeanBox);
        //Min value.
        theMinBox = new JCheckBox("Minimum", theCurrentSettings.getStatisticalFunctions().contains(StatisticalFunctions.MIN));
        checkableFirstPanel.add(theMinBox);
        //Max value.
        theMaxBox = new JCheckBox("Maximum", theCurrentSettings.getStatisticalFunctions().contains(StatisticalFunctions.MAX)); 
        checkableFirstPanel.add(theMaxBox);
        //Median value.
        theMedianBox = new JCheckBox("Median", theCurrentSettings.getStatisticalFunctions().contains(StatisticalFunctions.MEDIAN));
        checkableFirstPanel.add(theMedianBox);
        //Count value.
        theCountBox = new JCheckBox("Count", theCurrentSettings.getStatisticalFunctions().contains(StatisticalFunctions.COUNT)); 
        checkableFirstPanel.add(theCountBox);
        //Add checkable first panel to statsOption panel.
        statsOptionsPanel.add(checkableFirstPanel);
        statsOptionsPanel.add(Box.createRigidArea(new Dimension(0, 10))); //Spacer.
        
        //Create panel for second checkable options - grid layout 4 to 1.
        JPanel checkableSecondPanel = new JPanel(new GridLayout(1,5,5,5));
        //IQR value. 
        theIQRBox = new JCheckBox("IQR", theCurrentSettings.getStatisticalFunctions().contains(StatisticalFunctions.INTER_QUARTILE_RANGE));
        checkableSecondPanel.add(theIQRBox);
        //1st Quartile value.
        the1QBox = new JCheckBox("1st Quartile", theCurrentSettings.getStatisticalFunctions().contains(StatisticalFunctions.QUARTILE_FIRST)); 
        checkableSecondPanel.add(the1QBox);
        //3rd Quartile value.
        the3QBox = new JCheckBox("3rd Quartile", theCurrentSettings.getStatisticalFunctions().contains(StatisticalFunctions.QUARTILE_THIRD));
        checkableSecondPanel.add(the3QBox);
        //Standard Deviation value.
        theStDevBox = new JCheckBox("Standard Deviation", theCurrentSettings.getStatisticalFunctions().contains(StatisticalFunctions.STANDARD_DEVIATION));
        checkableSecondPanel.add(theStDevBox);
        //Add checkable second panel to statsOption panel.
        statsOptionsPanel.add(checkableSecondPanel);
        statsOptionsPanel.add(Box.createRigidArea(new Dimension(0, 10))); //Spacer.
        return statsOptionsPanel;
    }
    
    /**
     * Process the list options based on the selected statistical functions and generate an enum.
     * @return a <code>List</code> of <code>StatisticalFunctions</code> containing all selected statistical functions.
     */
    private List<StatisticalFunctions> processListOptions ( ) {
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
        return statisticalFunctions;
    }
    
    /**
     * Create the button panel.
     * @return a <code>JPanel</code> object representing the created button panel.
     */
    public JPanel createButtonPanel ( ) {
    	//Create button panel.
        JPanel buttonPanel = new JPanel(new FlowLayout());
        //Create the Process Button.
        theProcessButton = new JButton ( "Process Results" );
        theProcessButton.addActionListener ( new ActionListener() {
            public void actionPerformed ( ActionEvent e ) {
                //Check process variable.
                if ( theInterface.getProcessRunning() ) {
                    JOptionPane.showMessageDialog(StatsresGUI.this, "Cannot start process as another process is already running!", "ERROR: Attempting to Start Multiple Processes!", JOptionPane.ERROR_MESSAGE);
                } else {
                    //Set isProcessRunning variable to true.
                    theInterface.setProcessRunning(true);
                    //Do all other work in another thread to improve performance.
                    ProcessRunner pr = new ProcessRunner(theInterface, theResultsFileField.getText(), processListOptions(), theColumnHeadings.getSelectedValuesList(), theOutputArea);
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
        return buttonPanel;
    }
    
    /**
     * Create the output text panel.
     * @return a <code>JPanel</code> object representing the output text panel.
     */
    public JPanel createOutputTextPanel ( ) {
    	//Create output area with label and then output area.
        JPanel outputTextPanel = new JPanel();
        theOutputLabel = new JLabel("Output:");
        theOutputLabel.setFont(ARIAL_BOLD);
        outputTextPanel.add(theOutputLabel);
        return outputTextPanel;
    }
    
    /**
     * Create the output pane.
     * @return a <code>JScrollPane</code> object representing the created scroll pane.
     */
    public JScrollPane createOutputPane ( ) {
    	theOutputArea = new JTextArea(8,10);
        theOutputArea.setEditable(false);
        theOutputArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        theOutputArea.setText("No output available!");
        JScrollPane outputPane = new JScrollPane(theOutputArea);
        outputPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        outputPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        return outputPane;
    }
    
    /**
     * Set the window's bounds, centering the window
     */
    public void setLocationBounds ( ) {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - this.getWidth()) / 2;
        int y = (screen.height - this.getHeight()) / 2;
        setBounds(x, y, this.getWidth(), this.getHeight());
    }
    
    /**
     * Method to load/save either input or output file into the interface.
     * @param location a <code>String</code> with the last opened file.
     * @param dialogTitle a <code>String</code> which contains the title of the file dialog.
     * @return a <code>String</code> containing the name of the file to load.
     */
    public String loadSaveInputOutputFile ( final String location, final String dialogTitle ) {
        //Determine location of last file as user may wish to choose another file from that directory.
        JFileChooser fileDialog = new JFileChooser(location);
        fileDialog.setDialogTitle(dialogTitle);
        //Set file selection mode to files only.
        fileDialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
        //Only display relevant extension.
        fileDialog.setFileFilter(createFileNameExtensionFilter(dialogTitle));
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
     * Create a file name extension filter based on the specified dialog title.
     * @param dialogTitle a <code>String</code> with the dialog text.
     * @return a <code>FileNameExtensionFilter</code> object.
     */
    public FileNameExtensionFilter createFileNameExtensionFilter ( final String dialogTitle ) {
    	if (dialogTitle.contains("Settings File")) { 
        	return new FileNameExtensionFilter("Statsres Settings File (.srs)", "srs");
        } else {
        	return new FileNameExtensionFilter("Statsres Output File (.sro)", "sro"); 
        }
    }
    
    /**
     * Clear all the fields of the interface.
     */
    public void clearFields () {
        theResultsFileField.setText(""); 
        theColumnData.clear();
        the1QBox.setSelected(true); 
        the3QBox.setSelected(true);
        theCountBox.setSelected(true); 
        theIQRBox.setSelected(true);
        theMeanBox.setSelected(true); 
        theMedianBox.setSelected(true);
        theStDevBox.setSelected(true); 
        theMaxBox.setSelected(true);
        theMinBox.setSelected(true);
    }
    
    /**
     * Deselect all statistical options in the interface.
     */
    public void deselectAllStatOptions() {
    	the1QBox.setSelected(false); 
        the3QBox.setSelected(false);
        theCountBox.setSelected(false); 
        theIQRBox.setSelected(false);
        theMeanBox.setSelected(false); 
        theMedianBox.setSelected(false);
        theStDevBox.setSelected(false); 
        theMaxBox.setSelected(false);
        theMinBox.setSelected(false);
    }
    
    /**
     * Save current settings to a text file.
     * @return a <code>String</code> linked list containing current settings.
     */
    public StatsresSettings saveCurrentSettings () {
    	StatsresSettings settings = new StatsresSettings();
    	settings.setFile(theResultsFileField.getText());
    	//Convert DefaultListModel to proper List.
    	List<String> columnDataStr = new ArrayList<String>();
    	Enumeration<String> columnDataEnum = theColumnData.elements();
    	while (columnDataEnum.hasMoreElements()) {
    		columnDataStr.add(columnDataEnum.nextElement());
    	}
    	settings.setColumnData(columnDataStr);
    	//Convert checkboxes to list.
    	List<StatisticalFunctions> statisticalFunctions = processListOptions();
    	settings.setStatisticalFunctions(statisticalFunctions);
        //Return settings.
        return settings;
    }
    
}