package de.davelee.statsres.gui;
//Import the required java classes.
import de.davelee.statsres.main.StatsresProg;
import de.davelee.statsres.main.StatsresSettings;
import de.davelee.statsres.main.UserInterface;
import de.davelee.statsres.main.ReadWriteFileUtil;
import de.davelee.statsres.main.StatisticalFunctions;
import de.davelee.statsres.main.ProcessRunner;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.border.BevelBorder;
//Import file extension package.
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * StatsresGUI.java is a class to display the Statsres application.
 * @author Dave Lee.
 */
public class StatsresGUI extends JFrame {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 2556833225592251195L;
    private JTextField resultsFileField;

    private DefaultListModel<String> columnData;
    private JList<String> columnHeadings;
    
    private JLabel statusBarLabel;

    private JCheckBox meanBox;
    private JCheckBox minBox;
    private JCheckBox maxBox;
    private JCheckBox medianBox;
    private JCheckBox stDevBox;
    private JCheckBox iqrBox;
    private JCheckBox firstQuartileBox;
    private JCheckBox thirdQuartileBox;
    private JCheckBox countBox;

    private JTextArea outputArea;
    
    private StatsresProg statsresProg;
    private UserInterface userInterface;
    private StatsresSettings statsresSettings;
    
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
        this.statsresProg = statsresProg;
        userInterface = ui;
        
        if ( settings == null ) {
        	statsresSettings = StatsresSettings.createDefaultSettings(fileName);
        } else {
        	statsresSettings = settings;
        }
        
        //Set this as the current frame.
        userInterface.setCurrentFrame(this);
    	
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
    	statusBarLabel = new JLabel("Ready...");
    	statusBarLabel.setHorizontalAlignment(SwingConstants.LEFT);
    	statusPanel.add(statusBarLabel);
    	return statusPanel;
    }
    
    /**
     * Update the status bar with the specified text.
     * @param text a <code>String</code> with the new text.
     */
    public void updateStatusText ( final String text ) {
    	statusBarLabel.setText(text);
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
        Image img = Toolkit.getDefaultToolkit().getImage(StatsresGUI.class.getResource("/statsres-logo-icon.png"));
        setIconImage(img);
        
        //Call the Exit method in the UserInterface class if the user hits exit.
        this.addWindowListener ( new WindowAdapter() {
            public void windowClosing ( WindowEvent e ) {
                userInterface.exit();
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
    	StatsresSettings settings = statsresProg.loadSettingsFile(fileName);
        if ( settings != null ) {
        	new StatsresGUI(userInterface, statsresProg, "", settings, testMode);
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
    	String result = statsresProg.loadOutputFile(fileName);
        if ( !"".equalsIgnoreCase(result) ) {
        	outputArea.setText(result);
        } else {
        	showErrorStatus("output");
        }
    }
    
    /**
     * Save the settings from the menu.
     * @param fileName a <code>String</code> with the file to save the settings to.
     * @return a <code>boolean</code> which is true iff the settings were saved successfully.
     */
    public boolean saveSettingsMenu ( final String fileName ) {
        if ( statsresProg.saveContent(saveCurrentSettings().saveAsV1File(), fileName, ".srs" ) ) {
        	updateStatusText("Current settings were successfully saved to the selected file!");
        	return true;
        }
        return false;
    }
    
    /**
     * Save the output text from the menu.
     * @param fileName a <code>String</code> with the file to save the output text to.
     */
    public void saveOutputMenu ( final String fileName ) {
        List<String> output = new ArrayList<String>();
        output.add(outputArea.getText());
        if ( statsresProg.saveContent(output, fileName, ".sro" ) ) {
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
                userInterface.exit();
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
                new SplashWindow(true, userInterface, false);
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
        JLabel fileOptionsLabel = new JLabel("File Options:");
        fileOptionsLabel.setFont(ARIAL_BOLD);
        fileOptionsTextPanel.add(fileOptionsLabel);
        fileOptionsPanel.add(fileOptionsTextPanel);
        fileOptionsPanel.add(Box.createRigidArea(new Dimension(0, 10))); //Spacer.
        //Create results file panel to choose results file.
        JPanel resultsFilePanel = new JPanel(new FlowLayout());
        //Results File Label.
        JLabel resultsFileLabel = new JLabel("Input File:");
        resultsFilePanel.add(resultsFileLabel);
        //Results File Field.
        resultsFileField = new JTextField(statsresSettings.getFile());
        resultsFileField.setColumns(30);
        resultsFilePanel.add(resultsFileField);
        //Results File Button.
        JButton resultsFileButton = new JButton("Choose");
        resultsFileButton.addActionListener ( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String file = loadResultsFile(resultsFileField.getText());
                if ( !"".equalsIgnoreCase(file) ) {
                	statsresSettings.setFile(file);
                    new StatsresGUI(userInterface, statsresProg, file, statsresSettings, testMode);
                    dispose();
                }
            }
        }); 
        resultsFilePanel.add(resultsFileButton);
        
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
        JLabel dataOptionsLabel = new JLabel("Data Options:");
        dataOptionsLabel.setFont(ARIAL_BOLD);
        dataOptionsTextPanel.add(dataOptionsLabel);
        resultsSelectionPanel.add(dataOptionsTextPanel);
        resultsSelectionPanel.add(Box.createRigidArea(new Dimension(0, 10))); //Spacer.
        //JPanel for columns.
        JPanel columnPanel = new JPanel(new FlowLayout());
        //JLabel for column heading.
        JLabel columnLabel = new JLabel("Select Column(s):");
        columnPanel.add(columnLabel);
        //JList for column headings.
        columnData = new DefaultListModel<String>();
        //Only process contents of file if current settings is not null or a file was selected!
        List<String> columns = statsresSettings.getColumnData();
        for ( int i = 0; i < columns.size(); i++ ) {
        	columnData.addElement(columns.get(i));
        }
        if ( !"".equalsIgnoreCase(resultsFileField.getText()) ) { 
            //If folder selected then get list of files.
            if ( !resultsFileField.getText().endsWith(".csv") ) {
                StatsresProg sp = new StatsresProg();
                List<String> fileList = sp.getAllFiles(resultsFileField.getText());
                if ( ! fileList.isEmpty()) {
                	loadFileGUI(fileList.get(0));
                } else {
                    resultsFileField.setText("");
                    showErrorStatus("input");
                }
            } else {
                loadFileGUI(resultsFileField.getText());
            }
        }
        columnHeadings = new JList<String>(columnData);
        columnHeadings.setVisibleRowCount(3);
        JScrollPane columnPane = new JScrollPane(columnHeadings);
        columnPanel.add(columnPane);
        //Add column panel to results selection panel.
        resultsSelectionPanel.add(columnPanel);
        resultsSelectionPanel.add(Box.createRigidArea(new Dimension(0,5)));
        //Create select button panel.
        JPanel selectButtonPanel = new JPanel();
        JButton selectAllButton = new JButton("Select All");
        selectAllButton.addActionListener( new ActionListener() {
            public void actionPerformed ( ActionEvent e ) {
                if ( columnHeadings.getModel().getSize()>0 ) {
                    columnHeadings.setSelectionInterval(0, columnHeadings.getModel().getSize()-1);
                }
            }
        }); 
        selectButtonPanel.add(selectAllButton);
        JButton deselectAllButton = new JButton("Deselect All");
        deselectAllButton.addActionListener( new ActionListener() {
            public void actionPerformed ( ActionEvent e ) {
                columnHeadings.clearSelection();
            }
        });
        selectButtonPanel.add(deselectAllButton);
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
        String firstLine = ReadWriteFileUtil.readFile(file, true).get(0);
        String[] contents = firstLine.split(",");
        if ( contents.length == 0 ) {
        	resultsFileField.setText("");
            showErrorStatus("input");
        }
        for ( int i = 0; i < contents.length; i++ ) {
            columnData.addElement(contents[i]);
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
        JLabel statsOptionLabel = new JLabel("Statistical Options:");
        statsOptionLabel.setFont(ARIAL_BOLD);
        statsOptionTextPanel.add(statsOptionLabel);
        statsOptionsPanel.add(statsOptionTextPanel);
        statsOptionsPanel.add(Box.createRigidArea(new Dimension(0, 10))); //Spacer.
        //Create panel for checkable options - grid layout 5 to 1.
        JPanel checkableFirstPanel = new JPanel(new GridLayout(1,5,5,5));
        //Mean value.
        meanBox = new JCheckBox("Mean", statsresSettings.getStatisticalFunctions().contains(StatisticalFunctions.MEAN)); 
        checkableFirstPanel.add(meanBox);
        //Min value.
        minBox = new JCheckBox("Minimum", statsresSettings.getStatisticalFunctions().contains(StatisticalFunctions.MIN));
        checkableFirstPanel.add(minBox);
        //Max value.
        maxBox = new JCheckBox("Maximum", statsresSettings.getStatisticalFunctions().contains(StatisticalFunctions.MAX)); 
        checkableFirstPanel.add(maxBox);
        //Median value.
        medianBox = new JCheckBox("Median", statsresSettings.getStatisticalFunctions().contains(StatisticalFunctions.MEDIAN));
        checkableFirstPanel.add(medianBox);
        //Count value.
        countBox = new JCheckBox("Count", statsresSettings.getStatisticalFunctions().contains(StatisticalFunctions.COUNT)); 
        checkableFirstPanel.add(countBox);
        //Add checkable first panel to statsOption panel.
        statsOptionsPanel.add(checkableFirstPanel);
        statsOptionsPanel.add(Box.createRigidArea(new Dimension(0, 10))); //Spacer.
        
        //Create panel for second checkable options - grid layout 4 to 1.
        JPanel checkableSecondPanel = new JPanel(new GridLayout(1,5,5,5));
        //IQR value. 
        iqrBox = new JCheckBox("IQR", statsresSettings.getStatisticalFunctions().contains(StatisticalFunctions.INTER_QUARTILE_RANGE));
        checkableSecondPanel.add(iqrBox);
        //1st Quartile value.
        firstQuartileBox = new JCheckBox("1st Quartile", statsresSettings.getStatisticalFunctions().contains(StatisticalFunctions.QUARTILE_FIRST)); 
        checkableSecondPanel.add(firstQuartileBox);
        //3rd Quartile value.
        thirdQuartileBox = new JCheckBox("3rd Quartile", statsresSettings.getStatisticalFunctions().contains(StatisticalFunctions.QUARTILE_THIRD));
        checkableSecondPanel.add(thirdQuartileBox);
        //Standard Deviation value.
        stDevBox = new JCheckBox("Standard Deviation", statsresSettings.getStatisticalFunctions().contains(StatisticalFunctions.STANDARD_DEVIATION));
        checkableSecondPanel.add(stDevBox);
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
        List<StatisticalFunctions> statisticalFunctions = new ArrayList<StatisticalFunctions>();
        if ( meanBox.isSelected() ) {
        	statisticalFunctions.add(StatisticalFunctions.MEAN);
        } 
        if ( minBox.isSelected() ) {
        	statisticalFunctions.add(StatisticalFunctions.MIN);
        }
        if ( maxBox.isSelected() ) {
        	statisticalFunctions.add(StatisticalFunctions.MAX);
        }
        if ( medianBox.isSelected() ) {
        	statisticalFunctions.add(StatisticalFunctions.MEDIAN);
        }
        if ( countBox.isSelected() ) {
        	statisticalFunctions.add(StatisticalFunctions.COUNT);
        }
        if ( firstQuartileBox.isSelected() ) {
        	statisticalFunctions.add(StatisticalFunctions.QUARTILE_FIRST);
        }
        if ( thirdQuartileBox.isSelected() ) {
        	statisticalFunctions.add(StatisticalFunctions.QUARTILE_THIRD);
        }
        if ( iqrBox.isSelected() ) {
        	statisticalFunctions.add(StatisticalFunctions.INTER_QUARTILE_RANGE);
        }
        if ( stDevBox.isSelected() ) {
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
        JButton processButton = new JButton ( "Process Results" );
        processButton.addActionListener ( new ActionListener() {
            public void actionPerformed ( ActionEvent e ) {
                //Check process variable.
                if ( userInterface.getProcessRunning() ) {
                    JOptionPane.showMessageDialog(StatsresGUI.this, "Cannot start process as another process is already running!", "ERROR: Attempting to Start Multiple Processes!", JOptionPane.ERROR_MESSAGE);
                } else {
                    //Set isProcessRunning variable to true.
                    userInterface.setProcessRunning(true);
                    //Do all other work in another thread to improve performance.
                    ProcessRunner pr = new ProcessRunner(userInterface, resultsFileField.getText(), processListOptions(), columnHeadings.getSelectedValuesList(), outputArea);
                    new Thread(pr).start();
                }
            }
        });
        processButton.setMaximumSize(new Dimension(50, 25));
        buttonPanel.add ( processButton );
        //Create the Clear Button.
        JButton clearButton = new JButton("Reset");
        clearButton.addActionListener ( new ActionListener() {
            public void actionPerformed ( ActionEvent e ) {
                clearFields();
            }
        });
        clearButton.setMaximumSize(new Dimension(50, 25));
        buttonPanel.add ( clearButton );
        //Create the Exit Button.
        JButton exitButton = new JButton ( "Exit" );
        exitButton.addActionListener ( new ActionListener() {
            public void actionPerformed ( ActionEvent e ) {
                userInterface.exit();
            }
        });
        exitButton.setMaximumSize(new Dimension(50, 25));
        buttonPanel.add ( exitButton );
        return buttonPanel;
    }
    
    /**
     * Create the output text panel.
     * @return a <code>JPanel</code> object representing the output text panel.
     */
    public JPanel createOutputTextPanel ( ) {
    	//Create output area with label and then output area.
        JPanel outputTextPanel = new JPanel();
        JLabel outputLabel = new JLabel("Output:");
        outputLabel.setFont(ARIAL_BOLD);
        outputTextPanel.add(outputLabel);
        return outputTextPanel;
    }
    
    /**
     * Create the output pane.
     * @return a <code>JScrollPane</code> object representing the created scroll pane.
     */
    public JScrollPane createOutputPane ( ) {
    	outputArea = new JTextArea(8,10);
        outputArea.setEditable(false);
        outputArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        outputArea.setText("No output available!");
        JScrollPane outputPane = new JScrollPane(outputArea);
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
        resultsFileField.setText(""); 
        columnData.clear();
        firstQuartileBox.setSelected(true); 
        thirdQuartileBox.setSelected(true);
        countBox.setSelected(true); 
        iqrBox.setSelected(true);
        meanBox.setSelected(true); 
        medianBox.setSelected(true);
        stDevBox.setSelected(true); 
        maxBox.setSelected(true);
        minBox.setSelected(true);
    }
    
    /**
     * Deselect all statistical options in the interface.
     */
    public void deselectAllStatOptions() {
    	firstQuartileBox.setSelected(false); 
        thirdQuartileBox.setSelected(false);
        countBox.setSelected(false); 
        iqrBox.setSelected(false);
        meanBox.setSelected(false); 
        medianBox.setSelected(false);
        stDevBox.setSelected(false); 
        maxBox.setSelected(false);
        minBox.setSelected(false);
    }
    
    /**
     * Save current settings to a text file.
     * @return a <code>String</code> linked list containing current settings.
     */
    public StatsresSettings saveCurrentSettings () {
    	StatsresSettings settings = new StatsresSettings();
    	settings.setFile(resultsFileField.getText());
    	//Convert DefaultListModel to proper List.
    	List<String> columnDataStr = new ArrayList<String>();
    	Enumeration<String> columnDataEnum = columnData.elements();
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