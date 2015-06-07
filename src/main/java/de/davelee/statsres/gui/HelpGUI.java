package de.davelee.statsres.gui;

//Import java awt packages.
import java.awt.*;
import java.awt.event.*;
//Import java io and util packages.
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;


import java.util.Map;

//Import java swing packages.
import javax.swing.*;
import javax.swing.event.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HelpGUI is the screen to display the help screen for Statsres.
 * @author Dave Lee
 */
public class HelpGUI extends JFrame {

	private static final long serialVersionUID = 2946013407669763566L;
	private JTextField searchField;
    private JList<String> topicsList;
    private DefaultListModel<String> topicsModel;
    private JEditorPane displayPane;
    
    private Map<String, String> contentUrls;
    
    private static final Logger LOG = LoggerFactory.getLogger(HelpGUI.class);
    
    /**
     * Default constructor for HelpGUI which creates the help screen interface and displays it to the user.
     * @param testMode a <code>boolean</code> which is true iff the gui should not be displayed during JUnit tests.
     */
    public HelpGUI ( final boolean testMode ) {
    	
    	initialiseContent();
        
        addHeaderInfos();
        
        //Get a container to add things to.
        Container c = this.getContentPane();
        
        //Create a panel to display components.
        JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout( new BoxLayout ( dialogPanel, BoxLayout.PAGE_AXIS ) );
        dialogPanel.add(Box.createRigidArea(new Dimension(0, 10))); //Spacer.
        
        //Create grid layout - 2 to 1.
        JPanel helpPanel = new JPanel(new GridLayout(1,2,5,5));
        
        //Add left panel to help panel.
        helpPanel.add(createLeftPanel());
        
        //Add right panel to help panel.
        helpPanel.add(createRightPanel());
        
        helpPanel.setMaximumSize(new Dimension(450,390));
        //Add help panel to dialog panel.
        dialogPanel.add(helpPanel);
        dialogPanel.setMaximumSize(new Dimension(450,390));
        
         //Add the panel to the container.
        c.add ( dialogPanel );
        
        //Display the dialog box to the user.
        if ( !testMode ) {
        	this.setVisible (true);
        	this.setSize ( new Dimension(500,450) );
        }
        
        // Set the window's bounds, centering the window
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - this.getWidth()) / 2;
        int y = (screen.height - this.getHeight()) / 2;
        setBounds(x, y, this.getWidth(), this.getHeight());
        
    }
    
    /**
     * Add the desired infos to the header of the panel e.g. close function, title etc.
     */
    public void addHeaderInfos ( ) {
    	//Set image icon.
        Image img = Toolkit.getDefaultToolkit().getImage(HelpGUI.class.getResource("/logosmall.png"));
        setIconImage(img);
        
        //Close this window if the user hits exit.
        this.addWindowListener ( new WindowAdapter() {
            public void windowClosing ( WindowEvent e ) {
                dispose();
            }
        });
        
        //Initialise GUI with title and close attributes.
        this.setTitle ( "Statsres Help" );
        this.setResizable (false);
        this.setDefaultCloseOperation (DO_NOTHING_ON_CLOSE);
    }
    
    /**
     * Initialise the help content mapping titles to urls.
     */
    public void initialiseContent ( ) {
    	contentUrls = new HashMap<String, String>();
    	contentUrls.put("Welcome", "/intro.html");
    	contentUrls.put("Getting Started", "/gettingstarted.html");
    	contentUrls.put("Input Options", "/inputoptions.html");
    	contentUrls.put("Output", "/output.html");
    	contentUrls.put("Load Settings", "/loadsettings.html");
    	contentUrls.put("Save Settings", "/savesettings.html");
    	contentUrls.put("Load Output", "/loadoutput.html");
    	contentUrls.put("Save Output", "/saveoutput.html");
    }
    
    /**
     * Return the mapping of content urls to titles.
     * @return a <code>String</code> representing the mapping of content urls to titles.
     */
    public Map<String, String> getContentUrls() {
		return contentUrls;
	}
    
    /**
     * Create the left hand panel with search field and titles.
     * @return a <code>JPanel</code> representing the created left hand panel.
     */
    public JPanel createLeftPanel ( ) {
    	//Create left hand panel.
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout( new BoxLayout ( leftPanel, BoxLayout.PAGE_AXIS ) );
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10))); //Spacer.
        
        //Add search field.
        searchField = new JTextField();
        searchField.setColumns(20);
        searchField.addKeyListener( new KeyAdapter() {
            public void keyReleased ( KeyEvent e ) {
                updateList(searchField.getText());
            }
        });
        leftPanel.add(searchField);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 20))); //Spacer.
        
        //Add topics list.
        topicsModel = new DefaultListModel<String>();
        Iterator<String> contentIterator = contentUrls.keySet().iterator();
        while ( contentIterator.hasNext() ) {
        	topicsModel.addElement(contentIterator.next());
        }
        topicsList = new JList<String>(topicsModel);
        topicsList.setVisibleRowCount(20);
        topicsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //Default.
        topicsList.setSelectedIndex(0);
        //Action Listener for when a particular help topic is selected.
        topicsList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged ( ListSelectionEvent lse ) {
                //Get selected item.
                String selectedItem;
                try {
                    selectedItem = topicsList.getSelectedValue().toString();
                } catch ( NullPointerException npe ) {
                	LOG.warn("Selected Item was null - retrieving automatically the first element as selected element", npe);
                    selectedItem = topicsList.getModel().getElementAt(0).toString();
                    topicsList.setSelectedValue(selectedItem, true);
                }
                //If loading content fails, then stack trace and dispose.
                try {
                    displayPane.setPage(HelpGUI.class.getResource(contentUrls.get(selectedItem)));
                } catch ( IOException e ) {
                	LOG.error("Could not load HTML file, exiting ", e);
                    dispose();
                }
            }
        });
        JScrollPane topicsPane = new JScrollPane(topicsList);
        leftPanel.add(topicsPane);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10))); //Spacer.
        leftPanel.setMaximumSize(new Dimension(450,200));
        return leftPanel;
    }
    
    /**
     * Create the right hand panel with the html content page.
     * @return a <code>JPanel</code> representing the created right hand panel.
     */
    public JPanel createRightPanel ( ) {
    	//Create right pane.
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout( new BoxLayout ( rightPanel, BoxLayout.PAGE_AXIS ) );
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10))); //Spacer
        //Add editor pane.
        try {
            displayPane = new JEditorPane(HelpGUI.class.getResource("/intro.html")); 
            displayPane.setMaximumSize(new Dimension(450,390));
        } catch (IOException e) {
            LOG.error("Could not load HTML file, exiting...", e);
            dispose();
        }
        JScrollPane displayScroll = new JScrollPane(displayPane);
        displayScroll.setMaximumSize(new Dimension(450,390));
        rightPanel.add(displayScroll);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10))); //Spacer.
        rightPanel.setMaximumSize(new Dimension(450,390));
        return rightPanel;
    }

	/**
     * This method updates the topic lists according to the search text entered by the user.
     * @param text a <code>String</code> containing the text entered by the user in the search text box.
     */
    public void updateList ( String text ) {
        //Create temp model.
        DefaultListModel<String> tempModel = new DefaultListModel<String>();
        //If text is blank then set tempModel to fullModel. Otherwise, add those which have this prefix.
        if ( "".equalsIgnoreCase(text) ) {
            tempModel = topicsModel;
        } else {
            for ( int i = 0; i < topicsModel.size(); i++ ) {
                if ( includeString(text, topicsModel.get(i).toString()) ) {
                    tempModel.addElement(topicsModel.get(i).toString());
                }
            }
        }
        //Set the list to the temp model.
        topicsList.setModel(tempModel);
        topicsList.setSelectedIndex(0);
    }
    
    /**
     * This method determines whether a string should be included (strToCheck) against another String (strCheckAgainst).
     * Specifically e.g. statsres (in model) should be included if user's text was stats.
     * @param strToCheck a <code>String</code> containing the user's text.
     * @param strCheckAgainst a <code>String</code> containing the text in model.
     * @return a <code>boolean</code> which is true if and only if text in model should be included based on user's text.
     */
    public boolean includeString ( String strToCheck, String strCheckAgainst ) {
    	return strCheckAgainst.contains(strToCheck);
    }
    
}
