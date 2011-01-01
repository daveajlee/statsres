package statsres.gui;

//Import the Java GUI packages.
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//Import statsres main package.
import statsres.main.*;

/**
 * WaitingScreen.java is the screen to display the please wait message during processing.
 * @author Dave Lee
 * @version 1.1
 */
public class WaitingScreen extends JFrame {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 8872811946893980953L;
	private JLabel thePleaseWaitLabel;
    private JButton theCancelButton;
    private StatsresProg theProg;
    
    /**
     * Default constructor to display the waiting screen to the user.
     * @param ui a <code>UserInterface</code> object which controls interface processing in Statsres.
     */
    public WaitingScreen ( StatsresProg sp ) {
        
        //Initialise user interface and program variables.
        theProg = sp;
        
        //Set image icon.
        Image img = Toolkit.getDefaultToolkit().getImage(WaitingScreen.class.getResource("/logosmall.png"));
        setIconImage(img);
        
        //Initialise GUI with resizable, title and decorate methods.
        this.setTitle ("Statsres - Please Wait!");
        this.setResizable (false);
        this.setUndecorated(false);
        
        //Get a container to add things to.
        Container c = this.getContentPane();
        
        //Construct centre panel with box layout to display all components.
        JPanel centrePanel = new JPanel();
        centrePanel.setLayout ( new BoxLayout ( centrePanel, BoxLayout.PAGE_AXIS ) );
        centrePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.black,1), BorderFactory.createEmptyBorder(5,5,5,5)));
        centrePanel.add(Box.createRigidArea(new Dimension(0,10))); //Spacer.
        centrePanel.setBackground(Color.WHITE);
        
        //Construct wait panel to add to the centre panel.
        JPanel waitPanel = new JPanel();
        waitPanel.setBackground(Color.WHITE);
        thePleaseWaitLabel = new JLabel("Please Wait...");
        thePleaseWaitLabel.setFont(new Font("Arial", Font.ITALIC, 20));
        waitPanel.add(thePleaseWaitLabel);
        centrePanel.add(waitPanel);
        
        //Construct button panel to add to the centre panel.
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        theCancelButton = new JButton("Cancel");
        theCancelButton.addActionListener(new ActionListener() {
            public void actionPerformed ( ActionEvent e ) {
                theProg.stopProcessing();
            }
        });
        buttonPanel.add(theCancelButton);
        centrePanel.add(buttonPanel);
        
        c.add(centrePanel, BorderLayout.CENTER);
        
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
