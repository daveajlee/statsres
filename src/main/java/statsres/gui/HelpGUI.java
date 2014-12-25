package statsres.gui;

//Import java awt packages.
import java.awt.*;
import java.awt.event.*;
//Import java io and util packages.
import java.io.*;
import java.util.HashMap;

import java.util.Iterator;

//Import java swing packages.
import javax.swing.*;
import javax.swing.event.*;

/**
 * HelpGUI.java is the screen to display the help screen for Statsres.
 * @author Dave Lee
 * @version 1.0
 */
public class HelpGUI extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2946013407669763566L;
	private JTextField theSearchField;
    private JList<String> theTopicsList;
    private DefaultListModel<String> theTopicsModel;
    private JEditorPane theDisplayPane;
    
    private HashMap<String, String> contentUrls;
    
    /**
     * Default constructor for HelpGUI which creates the help screen interface and displays it to the user.
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
        //this.pack ();
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
    
    public HashMap<String, String> getContentUrls() {
		return contentUrls;
	}
    
    public JPanel createLeftPanel ( ) {
    	//Create left hand panel.
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout( new BoxLayout ( leftPanel, BoxLayout.PAGE_AXIS ) );
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10))); //Spacer.
        
        //Add search field.
        theSearchField = new JTextField();
        theSearchField.setColumns(20);
        theSearchField.addKeyListener( new KeyAdapter() {
            public void keyReleased ( KeyEvent e ) {
                updateList(theSearchField.getText());
            }
        });
        leftPanel.add(theSearchField);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 20))); //Spacer.
        
        //Add topics list.
        theTopicsModel = new DefaultListModel<String>();
        Iterator<String> contentIterator = contentUrls.keySet().iterator();
        while ( contentIterator.hasNext() ) {
        	theTopicsModel.addElement(contentIterator.next());
        }
        theTopicsList = new JList<String>(theTopicsModel);
        theTopicsList.setVisibleRowCount(20);
        theTopicsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //Default.
        theTopicsList.setSelectedIndex(0);
        //Action Listener for when a particular help topic is selected.
        theTopicsList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged ( ListSelectionEvent lse ) {
                //Get selected item.
                String selectedItem;
                try {
                    selectedItem = theTopicsList.getSelectedValue().toString();
                }
                catch ( NullPointerException npe ) {
                    selectedItem = theTopicsList.getModel().getElementAt(0).toString();
                    theTopicsList.setSelectedValue(selectedItem, true);
                }
                //If loading content fails, then stack trace and dispose.
                try {
                    theDisplayPane.setPage(HelpGUI.class.getResource(contentUrls.get(selectedItem)));
                }
                catch ( IOException e ) {
                    e.printStackTrace();
                    dispose();
                }
            }
        });
        JScrollPane topicsPane = new JScrollPane(theTopicsList);
        leftPanel.add(topicsPane);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10))); //Spacer.
        leftPanel.setMaximumSize(new Dimension(450,200));
        return leftPanel;
    }
    
    public JPanel createRightPanel ( ) {
    	//Create right pane.
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout( new BoxLayout ( rightPanel, BoxLayout.PAGE_AXIS ) );
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10))); //Spacer
        //Add editor pane.
        try {
            theDisplayPane = new JEditorPane(HelpGUI.class.getResource("/intro.html")); 
            theDisplayPane.setMaximumSize(new Dimension(450,390));
        }
        catch (IOException e) {
            e.printStackTrace();
            dispose();
        }
        JScrollPane displayScroll = new JScrollPane(theDisplayPane);
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
        //If text is blank then set tempModel to fullModel.
        if ( text.equalsIgnoreCase("") ) {
            tempModel = theTopicsModel;
        }
        //Otherwise, add those which have this prefix.
        else {
            for ( int i = 0; i < theTopicsModel.size(); i++ ) {
                if ( includeString(text, theTopicsModel.get(i).toString()) ) {
                    tempModel.addElement(theTopicsModel.get(i).toString());
                }
            }
        }
        //Set the list to the temp model.
        theTopicsList.setModel(tempModel);
        theTopicsList.setSelectedIndex(0);
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
