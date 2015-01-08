package statsres.gui;

//Import the Java GUI packages.
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//Import statsres main package.
import statsres.main.*;

/**
 * SplashWindow.java is the screen to display the splash screen at the start of the statsres program.
 * Also displays about screen when user clicks Help > About.
 * @author Dave Lee
 * @version 1.1
 */
public class SplashWindow extends JFrame {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -2481626829536427543L;
    private JLabel theTitleLabel;
    private JLabel theLoadingLabel;
    private JLabel theVersionLabel;
    private JLabel theCopyrightLabel;
    private UserInterface theInterface;
    
    private static final String VERSION_NUMBER = "1.1";
    private static final String FONT_FAMILY = "Arial";
    
    //Test Constructor.
    public SplashWindow ( ) {
    	
    }
    
    /**
     * Default constructor to display the splash screen to the user.
     * @param isAboutScreen a <code>boolean</code> which is true if we are to display about screen.
     * @param ui a <code>UserInterface</code> object which controls interface processing in Statsres.
     */
    public SplashWindow ( boolean isAboutScreen, UserInterface ui, boolean testMode ) {
        
    	//Initialise user interface and set this as current frame.
        theInterface = ui;
        theInterface.setCurrentFrame(this);
        
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
    
    public void addHeaderInfo ( ) {
        
        //Set image icon.
        Image img = Toolkit.getDefaultToolkit().getImage(SplashWindow.class.getResource("/logosmall.png"));
        setIconImage(img);
        
        //Initialise GUI with resizable, title and decorate methods.
        this.setTitle ("Statsres");
        this.setResizable (true);
        this.setUndecorated(true);
    }
    
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
        theTitleLabel = new JLabel("Statsres");
        theTitleLabel.setFont(new Font(FONT_FAMILY, Font.BOLD, 25));
        titlePanel.add(theTitleLabel);
        centrePanel.add(titlePanel);
        
        //This presents "Loading... Please Wait" if using this as a splash screen.
        if ( !isAboutScreen ) {
            //Construct loading panel to add to the centre panel.
            JPanel loadingPanel = new JPanel();
            loadingPanel.setBackground(Color.WHITE);
            theLoadingLabel = new JLabel("Loading... Please Wait");
            theLoadingLabel.setFont(new Font(FONT_FAMILY, Font.BOLD+Font.ITALIC, 16));
            loadingPanel.add(theLoadingLabel);
            centrePanel.add(loadingPanel);
        } else {
            //Construct version panel to add to the centre panel. This presents "Version" if about screen.
            JPanel versionPanel = new JPanel();
            versionPanel.setBackground(Color.WHITE);
            theVersionLabel = new JLabel("Version " + VERSION_NUMBER);
            theVersionLabel.setFont(new Font(FONT_FAMILY, Font.BOLD+Font.ITALIC, 16));
            versionPanel.add(theVersionLabel);
            centrePanel.add(versionPanel);
        }
        
        //Construct copyright panel to add to the centre panel.
        JPanel copyrightPanel = new JPanel();
        copyrightPanel.setBackground(Color.WHITE);
        theCopyrightLabel = new JLabel("Original Author: David A J Lee");
        theCopyrightLabel.setFont(new Font(FONT_FAMILY, Font.ITALIC, 10) );
        copyrightPanel.add(theCopyrightLabel);
        centrePanel.add(copyrightPanel);
        return centrePanel;
    }
    
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
    
    public void setLocationBounds ( ) {
    	//Position the screen at the center of the screen.
        Toolkit tools = Toolkit.getDefaultToolkit();
        Dimension screenDim = tools.getScreenSize();
        Dimension displayDim = getPreferredSize();
        this.setLocation ( (int) (screenDim.width/2)-(displayDim.width/2), (int) (screenDim.height/2)-(displayDim.height/2));
    }
    
}
