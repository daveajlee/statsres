package de.davelee.statsres.gui;

//Import the Java GUI packages.
import de.davelee.statsres.main.UserInterface;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * SplashWindow.java is the screen to display the splash screen at the start of the statsres program.
 * Also displays about screen when user clicks Help and then About.
 * @author Dave Lee
 */
public class SplashWindow extends JFrame {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -2481626829536427543L;
    private JLabel loadingLabel;
    private JLabel versionLabel;
    
    private static final String VERSION_NUMBER = "2.2.0-SNAPSHOT";
    private static final String FONT_FAMILY = "Arial";
    
    /**
     * Default constructor for JUnit tests.
     */
    public SplashWindow ( ) {
    	
    }
    
    /**
     * Constructor to display the splash screen to the user.
     * @param isAboutScreen a <code>boolean</code> which is true if we are to display about screen.
     * @param ui a <code>UserInterface</code> object which controls interface processing in Statsres.
     * @param testMode a <code>boolean</code> which is true iff the gui should not be displayed during JUnit tests.
     */
    public SplashWindow (boolean isAboutScreen, UserInterface ui, boolean testMode ) {
        
    	//Initialise user interface and set this as current frame.
        ui.setCurrentFrame(this);
        
        addHeaderInfo();
        
        //Get a container to add things to.
        Container c = this.getContentPane();
        
        c.add(createCenterPanel(isAboutScreen), BorderLayout.CENTER);
        
        if ( isAboutScreen ) {
        	c.addMouseListener(createMouseListener());
        }
        
        setLocationBounds();
       
        //Display the front screen to the user.
        if ( !testMode ) {
        	this.pack ();
        	this.setVisible (true);
        	this.setSize ( getPreferredSize() );
        }
        
    }
    
    /**
     * Add the infos e.g. title, icon etc. to the header panel.
     */
    public void addHeaderInfo ( ) {
        
        //Set image icon.
        Image img = Toolkit.getDefaultToolkit().getImage(SplashWindow.class.getResource("/logosmall.png"));
        setIconImage(img);
        
        //Initialise GUI with resizable, title and decorate methods.
        this.setTitle ("Statsres");
        this.setResizable (true);
        this.setUndecorated(true);
    }
    
    /**
     * Create the center panel according to whether it is for an about screen or the splash screen.
     * @param isAboutScreen a <code>boolean</code> which is true iff the about screen should be displayed.
     * @return a <code>JPanel</code> representing the created center panel.
     */
    public JPanel createCenterPanel ( boolean isAboutScreen ) {
    	//Construct centre panel with box layout to display all components.
        JPanel centrePanel = new JPanel();
        centrePanel.setLayout ( new BoxLayout ( centrePanel, BoxLayout.PAGE_AXIS ) );
        centrePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.black,1), BorderFactory.createEmptyBorder(5,5,5,5)));
        centrePanel.add(Box.createRigidArea(new Dimension(0,10))); //Spacer.
        centrePanel.setBackground(Color.WHITE);
        
        //Construct logo panel to add to the centre panel.
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(Color.WHITE);
        ImageIcon image = new ImageIcon(SplashWindow.class.getResource("/logo.png"));
        JLabel label = new JLabel("", image, JLabel.CENTER);
        logoPanel.add(label);
        centrePanel.add(logoPanel);
        
        //Construct title panel to add to the centre panel.
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.WHITE);
        JLabel titleLabel = new JLabel("Statsres");
        titleLabel.setFont(new Font(FONT_FAMILY, Font.BOLD, 25));
        titlePanel.add(titleLabel);
        centrePanel.add(titlePanel);
        
        //This presents "Loading... Please Wait" if using this as a splash screen.
        if ( !isAboutScreen ) {
            //Construct loading panel to add to the centre panel.
            JPanel loadingPanel = new JPanel();
            loadingPanel.setBackground(Color.WHITE);
            loadingLabel = new JLabel("Loading... Please Wait");
            loadingLabel.setFont(new Font(FONT_FAMILY, Font.BOLD+Font.ITALIC, 16));
            loadingPanel.add(loadingLabel);
            centrePanel.add(loadingPanel);
        } else {
            //Construct version panel to add to the centre panel. This presents "Version" if about screen.
            JPanel versionPanel = new JPanel();
            versionPanel.setBackground(Color.WHITE);
            versionLabel = new JLabel("Version " + VERSION_NUMBER);
            versionLabel.setFont(new Font(FONT_FAMILY, Font.BOLD+Font.ITALIC, 16));
            versionPanel.add(versionLabel);
            centrePanel.add(versionPanel);
        }
        
        //Construct copyright panel to add to the centre panel.
        JPanel copyrightPanel = new JPanel();
        copyrightPanel.setBackground(Color.WHITE);
        JLabel copyrightLabel = new JLabel("Original Author: David A J Lee");
        copyrightLabel.setFont(new Font(FONT_FAMILY, Font.ITALIC, 10) );
        copyrightPanel.add(copyrightLabel);
        centrePanel.add(copyrightPanel);
        return centrePanel;
    }
    
    /**
     * Create the mouse listener which when clicked no longer displays the screen to the user.
     * @return a <code>MouseListener</code> object representing the created mouse listener.
     */
    public MouseListener createMouseListener ( ) {
        return new MouseListener () {
        	public void mouseClicked(MouseEvent e) {
        		dispose();
            }
            
        	public void mousePressed(MouseEvent e) {
        		throw new UnsupportedOperationException();
            }
        	public void mouseReleased(MouseEvent e) {
        		throw new UnsupportedOperationException();
            }
            public void mouseEntered(MouseEvent e) {
            	throw new UnsupportedOperationException();
            }
            public void mouseExited(MouseEvent e) {
            	throw new UnsupportedOperationException();
            }
     	};
    }
    
    /**
     * Position the screen at the center of the screen.
     */
    public void setLocationBounds ( ) {
        Toolkit tools = Toolkit.getDefaultToolkit();
        Dimension screenDim = tools.getScreenSize();
        Dimension displayDim = getPreferredSize();
        this.setLocation ( (int) (screenDim.width/2)-(displayDim.width/2), (int) (screenDim.height/2)-(displayDim.height/2));
    }
    
}
