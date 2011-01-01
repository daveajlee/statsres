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
    
    private ImageScreen theStatsresLogo;
    private JLabel theTitleLabel;
    private JLabel theLoadingLabel;
    private JLabel theVersionLabel;
    private JLabel theCopyrightLabel;
    private UserInterface theInterface;
    
    /**
     * Default constructor to display the splash screen to the user.
     * @param isAboutScreen a <code>boolean</code> which is true if we are to display about screen.
     * @param ui a <code>UserInterface</code> object which controls interface processing in Statsres.
     */
    public SplashWindow ( boolean isAboutScreen, UserInterface ui ) {
        
        //Initialise user interface and set this as current frame.
        theInterface = ui;
        theInterface.setCurrentFrame(this);
        
        //Set image icon.
        Image img = Toolkit.getDefaultToolkit().getImage(SplashWindow.class.getResource("/logosmall.png"));
        setIconImage(img);
        
        //Initialise GUI with resizable, title and decorate methods.
        this.setTitle ("Statsres");
        this.setResizable (true);
        this.setUndecorated(true);
        
        //Get a container to add things to.
        Container c = this.getContentPane();
        
        //Construct centre panel with box layout to display all components.
        JPanel centrePanel = new JPanel();
        centrePanel.setLayout ( new BoxLayout ( centrePanel, BoxLayout.PAGE_AXIS ) );
        centrePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.black,1), BorderFactory.createEmptyBorder(5,5,5,5)));
        centrePanel.add(Box.createRigidArea(new Dimension(0,10))); //Spacer.
        centrePanel.setBackground(Color.WHITE);
        
        //Construct logo panel to add to the centre panel.
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(Color.WHITE);
        theStatsresLogo = new ImageScreen(SplashWindow.class.getResource("/logo.png"),75,0,SplashWindow.this);
        theStatsresLogo.setSize(274,118);
        theStatsresLogo.setBackground(Color.WHITE);
        logoPanel.add(theStatsresLogo);
        centrePanel.add(logoPanel);
        
        //Construct title panel to add to the centre panel.
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.WHITE);
        theTitleLabel = new JLabel("Statsres");
        theTitleLabel.setFont(new Font("Arial", Font.BOLD, 25));
        titlePanel.add(theTitleLabel);
        centrePanel.add(titlePanel);
        
        //This presents "Loading... Please Wait" if using this as a splash screen.
        if ( !isAboutScreen ) {
            //Construct loading panel to add to the centre panel.
            JPanel loadingPanel = new JPanel();
            loadingPanel.setBackground(Color.WHITE);
            theLoadingLabel = new JLabel("Loading... Please Wait");
            theLoadingLabel.setFont(new Font("Arial", Font.BOLD+Font.ITALIC, 16));
            loadingPanel.add(theLoadingLabel);
            centrePanel.add(loadingPanel);
        }
        //This presents "Version + version number" if using this as about screen.
        else {
            //Construct version panel to add to the centre panel.
            JPanel versionPanel = new JPanel();
            versionPanel.setBackground(Color.WHITE);
            theVersionLabel = new JLabel("Version " + theInterface.getVersion());
            theVersionLabel.setFont(new Font("Arial", Font.BOLD+Font.ITALIC, 16));
            versionPanel.add(theVersionLabel);
            centrePanel.add(versionPanel);
        }
        
        //Construct copyright panel to add to the centre panel.
        JPanel copyrightPanel = new JPanel();
        copyrightPanel.setBackground(Color.WHITE);
        theCopyrightLabel = new JLabel("Original Author: David A J Lee");
        theCopyrightLabel.setFont(new Font("Arial", Font.ITALIC, 10) );
        copyrightPanel.add(theCopyrightLabel);
        centrePanel.add(copyrightPanel);
        
        c.add(centrePanel, BorderLayout.CENTER);
        
        //Mouse listener if this is the about screen.
        if ( isAboutScreen ) {
            c.addMouseListener ( new MouseListener () {
                public void mouseClicked(MouseEvent e) {
                    dispose();
                }
                public void mousePressed(MouseEvent e) {}
                public void mouseReleased(MouseEvent e) {}
                public void mouseEntered(MouseEvent e) {}
                public void mouseExited(MouseEvent e) {}
            });
        }
        
        //Position the screen at the center of the screen.
        Toolkit tools = Toolkit.getDefaultToolkit();
        Dimension screenDim = tools.getScreenSize();
        Dimension displayDim = getPreferredSize();
        this.setLocation ( (int) (screenDim.width/2)-(displayDim.width/2), (int) (screenDim.height/2)-(displayDim.height/2));
        
        //Display the front screen to the user.
        this.pack ();
        this.setVisible (true);
        this.setSize ( getPreferredSize() );
        
    }
    
}
