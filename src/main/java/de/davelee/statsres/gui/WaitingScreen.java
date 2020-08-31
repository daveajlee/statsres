package de.davelee.statsres.gui;

//Import the Java GUI packages.
import de.davelee.statsres.main.StatsresProg;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * Screen to display the please wait message during processing.
 * @author Dave Lee
 */
public class WaitingScreen extends JFrame {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 8872811946893980953L;
    private JButton cancelButton;
    private StatsresProg statsresProg;
    
    /**
     * Default constructor for JUnit tests.
     */
    public WaitingScreen ( ) {
    	
    }
    
    /**
     * Constructor to display the waiting screen to the user.
     * @param sp a <code>StatsresProg</code> object which controls program functions in Statsres.
     */
    public WaitingScreen ( StatsresProg sp ) {
        
        //Initialise user interface and program variables.
        statsresProg = sp;
        
        
        
        //Get a container to add things to.
        Container c = this.getContentPane();
        c.add(createCenterPanel(), BorderLayout.CENTER);
        
        //Display the front screen to the user.
        this.pack ();
        this.setVisible (true);
        this.setSize ( getPreferredSize() );
        
    }
    
    /**
     * Add the header info to the panel e.g. icon, title etc.
     */
    public void addHeaderInfo ( ) {
    	//Set image icon.
        Image img = Toolkit.getDefaultToolkit().getImage(WaitingScreen.class.getResource("/logosmall.png"));
        setIconImage(img);
        
        //Initialise GUI with resizable, title and decorate methods.
        this.setTitle ("Statsres - Please Wait!");
        this.setResizable (false);
        this.setUndecorated(false);
    }
    
    /**
     * Construct centre panel with box layout to display all components.
     * @return a <code>JPanel</code> object representing the created center panel.
     */
    public JPanel createCenterPanel ( ) {
        JPanel centrePanel = new JPanel();
        centrePanel.setLayout ( new BoxLayout ( centrePanel, BoxLayout.PAGE_AXIS ) );
        centrePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.black,1), BorderFactory.createEmptyBorder(5,5,5,5)));
        centrePanel.add(Box.createRigidArea(new Dimension(0,10))); //Spacer.
        centrePanel.setBackground(Color.WHITE);
        
        //Construct wait panel to add to the centre panel.
        JPanel waitPanel = new JPanel();
        waitPanel.setBackground(Color.WHITE);
        JLabel pleaseWaitLabel = new JLabel("Please Wait...");
        pleaseWaitLabel.setFont(new Font("Arial", Font.ITALIC, 20));
        waitPanel.add(pleaseWaitLabel);
        centrePanel.add(waitPanel);
        
        //Construct button panel to add to the centre panel.
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed ( ActionEvent e ) {
                statsresProg.stopProcessing();
            }
        });
        buttonPanel.add(cancelButton);
        centrePanel.add(buttonPanel);
        return centrePanel;
    }
    
    /**
     * Position the screen at the center of the screen.
     */
    public void setLocation ( ) {
        Toolkit tools = Toolkit.getDefaultToolkit();
        Dimension screenDim = tools.getScreenSize();
        Dimension displayDim = getPreferredSize();
        this.setLocation ( (int) (screenDim.width/2)-(displayDim.width/2), (int) (screenDim.height/2)-(displayDim.height/2));
    }
    
}
