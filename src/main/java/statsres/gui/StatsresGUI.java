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
    
    private StatsresProg theOperations;
    private UserInterface theInterface;
    private StatsresSettings theCurrentSettings;
    
    /**
     * Default constructor. Create all of the user interface components and display them to the user.
     * @param ui a <code>UserInterface</code> object containing the current control user interface object.
     * @param fileName a <code>String</code> with the current file loaded into the interface.
     * @param isMultipleFiles a <code>boolean</code> indicating whether it is folder or file selection.
     * @param settings a <code>String</code> array of settings for the interface - null equals a new session.
     */
    public StatsresGUI ( UserInterface ui, String fileName, StatsresSettings settings, boolean testMode ) {
        
    	//Create ProgramOperations object and store it.
        theOperations = new StatsresProg();
        theInterface = ui;
        
        if ( settings == null ) {
        	theCurrentSettings = StatsresSettings.createDefaultSettings(fileName);
        } else {
        	theCurrentSettings = settings;
        }
        
        //Set this as the current frame.
        theInterface.setCurrentFrame(this);
    	
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
    
    public JPanel createDialogPanel ( final String fileName, final boolean testMode ) {
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
        
        return dialogPanel;
    }
      
    /**
     * Method to load results file into the interface.
     * @param location a <code>String</code> with the last opened file.
     * @param allowDirs a <code>Boolean</code> indicating whether user can select directories or files.
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
    
    public void addHeaderInfo ( final boolean testMode ) {
    	//Set image icon.
        Image img = Toolkit.getDefaultToolkit().getImage(StatsresGUI.class.getResource("/logosmall.png"));
        setIconImage(img);
        
        //Call the Exit method in the UserInterface class if the user hits exit.
        this.addWindowListener ( new WindowAdapter() {
            public void windowClosing ( WindowEvent e ) {
                theInterface.exit("Please Confirm","Are you sure you wish to exit 'Statsres'?");
            }
        });
        
        //Initialise GUI with title and close attributes.
        this.setTitle ( "Statsres" );
        this.setResizable (false);
        this.setDefaultCloseOperation (DO_NOTHING_ON_CLOSE);
        this.setJMenuBar(createMenuBar(testMode));
    }
    
    private void loadSettingsMenu ( boolean testMode ) {
    	StatsresSettings settings = theOperations.loadSettingsFile(loadSaveInputOutputFile("", "Load Settings File"));
        if ( settings != null ) {
        	new StatsresGUI(theInterface, "", settings, testMode);
            dispose();
        } else {
        	JOptionPane.showMessageDialog(StatsresGUI.this, "The selected file could not be loaded because it is not a valid settings file. Please choose another file.", "ERROR: Could not load selected file", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void loadOutputMenu ( ) {
    	String result = theOperations.loadOutputFile(loadSaveInputOutputFile("", "Load Output File"));
        if ( !"".equalsIgnoreCase(result) ) {
        	theOutputArea.setText(result);
        } else {
        	JOptionPane.showMessageDialog(StatsresGUI.this, "The selected file could not be loaded because it is not a valid output file. Please choose another file.", "ERROR: Could not load selected file", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void saveSettingsMenu ( ) {
    	String fileName = loadSaveInputOutputFile("", "Save Settings File");
        if ( theOperations.saveContent(saveCurrentSettings().saveAsV1File(), fileName, ".srs" ) ) {
        	JOptionPane.showMessageDialog(StatsresGUI.this, "Current settings were successfully saved to the selected file!", "Settings Saved Successfully", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void saveOutputMenu ( ) {
    	String fileName = loadSaveInputOutputFile("", "Load Settings File");
        List<String> output = new ArrayList<String>();
        output.add(theOutputArea.getText());
        if ( theOperations.saveContent(output, fileName, ".sro" ) ) {
            JOptionPane.showMessageDialog(StatsresGUI.this, "Output was successfully saved to the selected file!", "Output Saved Successfully", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
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
                loadSettingsMenu(testMode);
            }
        });
        loadMenu.add(loadSettingsMenuItem);
        JMenuItem loadOutputMenuItem = new JMenuItem("Output");
        loadOutputMenuItem.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                loadOutputMenu();
            }
        });
        loadMenu.add(loadOutputMenuItem);
        fileMenu.add(loadMenu);
        fileMenu.addSeparator();
        JMenu saveMenu = new JMenu("Save");
        JMenuItem saveSettingsMenuItem = new JMenuItem("Settings");
        saveSettingsMenuItem.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                saveSettingsMenu();
            }
        });
        saveMenu.add(saveSettingsMenuItem);
        JMenuItem saveOutputMenuItem = new JMenuItem("Output");
        saveOutputMenuItem.addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                saveOutputMenu();
            }
        });
        saveMenu.add(saveOutputMenuItem);
        fileMenu.add(saveMenu);
        fileMenu.addSeparator();
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener ( new ActionListener() {
            public void actionPerformed ( ActionEvent e ) {
                theInterface.exit("Please Confirm","Are you sure you wish to exit 'Statsres'?");
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
    
    public JPanel createFileOptionsPanel ( final String fileName, final boolean testMode ) {
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
        theResultsFileField = new JTextField(theCurrentSettings.getFile());
        theResultsFileField.setColumns(30);
        resultsFilePanel.add(theResultsFileField);
        //Results File Button.
        theResultsFileButton = new JButton("Choose");
        theResultsFileButton.addActionListener ( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String file = loadResultsFile(theResultsFileField.getText());
                if ( !"".equalsIgnoreCase(file) ) {
                    new StatsresGUI(theInterface, file, theCurrentSettings, testMode);
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
    
    public JPanel createResultsSelectionPanel ( ) {
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
                    JOptionPane.showMessageDialog(StatsresGUI.this, "The selected folder could not be loaded because it is not a valid input file. Please choose another file.", "ERROR: Could not load selected file", JOptionPane.ERROR_MESSAGE);
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
    
    public void loadFileGUI ( final String file) {
    	//Load contents of file and whole of first line, colon-separated gives content.
        String firstLine = ReadWriteFile.readFile(file, true).get(0);
        String[] contents = firstLine.split(",");
        if ( contents.length == 0 ) {
        	theResultsFileField.setText("");
            JOptionPane.showMessageDialog(StatsresGUI.this, "The selected file could not be loaded because it is not a valid input file. Please choose another file.", "ERROR: Could not load selected file", JOptionPane.ERROR_MESSAGE);
        }
        for ( int i = 0; i < contents.length; i++ ) {
            theColumnData.addElement(contents[i]);
        }
    }
    
    public JPanel createStatsOptionPanel ( ) {
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
                theInterface.exit("Please Confirm","Are you sure you wish to exit 'Statsres'?");
            }
        });
        theExitButton.setMaximumSize(new Dimension(50, 25));
        buttonPanel.add ( theExitButton );
        return buttonPanel;
    }
    
    public JPanel createOutputTextPanel ( ) {
    	//Create output area with label and then output area.
        JPanel outputTextPanel = new JPanel();
        theOutputLabel = new JLabel("Output:");
        theOutputLabel.setFont(new Font("Arial", Font.BOLD+Font.ITALIC, 16));
        outputTextPanel.add(theOutputLabel);
        return outputTextPanel;
    }
    
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
    
    public void setLocationBounds ( ) {
    	// Set the window's bounds, centering the window
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - this.getWidth()) / 2;
        int y = (screen.height - this.getHeight()) / 2;
        setBounds(x, y, this.getWidth(), this.getHeight());
    }
    
    /**
     * Method to load/save either input or output file into the interface.
     * @param location a <code>String</code> with the last opened file.
     * @param isInput a <code>Boolean</code> indicating whether it is input (true) or output (false).
     * @param load a <code>Boolean</code> which is true iff load function activated. Otherwise save will be displayed in title.
     * @return a <code>String</code> containing the name of the file to load.
     */
    public String loadSaveInputOutputFile ( final String location, final String dialogTitle ) {
        //Determine location of last file as user may wish to choose another file from that directory.
        JFileChooser fileDialog = new JFileChooser(location);
        fileDialog.setDialogTitle(dialogTitle);
        //Set file selection mode to files only.
        fileDialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
        //Only display relevant extension.
        FileNameExtensionFilter filter;
        if (dialogTitle.contains("Settings File")) { 
        	filter = new FileNameExtensionFilter("Statsres Settings File (.srs)", "srs");
        } else {
        	filter = new FileNameExtensionFilter("Statsres Output File (.sro)", "sro"); 
        }
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
     * Private method to save current settings to a text file.
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