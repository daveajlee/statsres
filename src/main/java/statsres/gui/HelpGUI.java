package statsres.gui;

//Import java awt packages.
import java.awt.*;
import java.awt.event.*;
//Import java io and util packages.
import java.io.*;
import java.util.*;
//Import java swing packages.
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

/**
 * HelpGUI.java is the screen to display the help screen for Statsres.
 * @author Dave Lee
 * @version 1.0
 */
public class HelpGUI extends JFrame {

    private JTextField theSearchField;
    private JList theTopicsList;
    private DefaultListModel theTopicsModel;
    private JEditorPane theDisplayPane;
    
    /**
     * Default constructor for HelpGUI which creates the help screen interface and displays it to the user.
     */
    public HelpGUI ( ) {
        
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
        
        //Get a container to add things to.
        Container c = this.getContentPane();
        
        //Create a panel to display components.
        JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout( new BoxLayout ( dialogPanel, BoxLayout.PAGE_AXIS ) );
        dialogPanel.add(Box.createRigidArea(new Dimension(0, 10))); //Spacer.
        
        //Create grid layout - 2 to 1.
        JPanel helpPanel = new JPanel(new GridLayout(1,2,5,5));
        
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
        theTopicsModel = new DefaultListModel();
        theTopicsModel.addElement("Welcome"); theTopicsModel.addElement("Getting Started");
        theTopicsModel.addElement("Input Options"); theTopicsModel.addElement("Output");
        theTopicsModel.addElement("Load Settings"); theTopicsModel.addElement("Save Settings");
        theTopicsModel.addElement("Load Output"); theTopicsModel.addElement("Save Output");
        theTopicsList = new JList(theTopicsModel);
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
                    //If statements to display correct content.
                    if ( selectedItem.equalsIgnoreCase("Welcome") ) {
                        theDisplayPane.setPage(HelpGUI.class.getResource("/intro.html"));
                    }
                    else if ( selectedItem.equalsIgnoreCase("Getting Started") ) {
                        theDisplayPane.setPage(HelpGUI.class.getResource("/gettingstarted.html"));
                    }
                    else if ( selectedItem.equalsIgnoreCase("Input Options") ) {
                        theDisplayPane.setPage(HelpGUI.class.getResource("/inputoptions.html"));
                    }
                    else if ( selectedItem.equalsIgnoreCase("Output") ) {
                        theDisplayPane.setPage(HelpGUI.class.getResource("/output.html"));
                    }
                    else if ( selectedItem.equalsIgnoreCase("Load Settings") ) {
                        theDisplayPane.setPage(HelpGUI.class.getResource("/loadsettings.html"));
                    }
                    else if ( selectedItem.equalsIgnoreCase("Save Settings") ) {
                        theDisplayPane.setPage(HelpGUI.class.getResource("/savesettings.html"));
                    }
                    else if ( selectedItem.equalsIgnoreCase("Load Output") ) {
                        theDisplayPane.setPage(HelpGUI.class.getResource("/loadoutput.html"));
                    }
                    else if ( selectedItem.equalsIgnoreCase("Save Output") ) {
                        theDisplayPane.setPage(HelpGUI.class.getResource("/saveoutput.html"));
                    }
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
        
        //Add left panel to help panel.
        helpPanel.add(leftPanel);
        
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
        helpPanel.add(rightPanel);
        helpPanel.setMaximumSize(new Dimension(450,390));
        //Add help panel to dialog panel.
        dialogPanel.add(helpPanel);
        dialogPanel.setMaximumSize(new Dimension(450,390));
        
         //Add the panel to the container.
        c.add ( dialogPanel );
        
        //Display the dialog box to the user.
        //this.pack ();
        this.setVisible (true);
        this.setSize ( new Dimension(500,450) );
        
        // Set the window's bounds, centering the window
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - this.getWidth()) / 2;
        int y = (screen.height - this.getHeight()) / 2;
        setBounds(x, y, this.getWidth(), this.getHeight());
        
    }
    
    /**
     * This method updates the topic lists according to the search text entered by the user.
     * @param text a <code>String</code> containing the text entered by the user in the search text box.
     */
    public void updateList ( String text ) {
        //Create temp model.
        DefaultListModel tempModel = new DefaultListModel();
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
        for ( int i = 0; i < strToCheck.length(); i++ ) {
            if ( !strToCheck.substring(i, (i+1)).equalsIgnoreCase(strCheckAgainst.substring(i, (i+1))) ) {
                return false;
            }
        }
        return true;
    }
    
}
