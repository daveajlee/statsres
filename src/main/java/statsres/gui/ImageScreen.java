package statsres.gui;

//Import java awt packages.
import java.awt.*;
import java.awt.event.*;
//Import java swing packages.
import javax.swing.*;
//Import java net package for URL support.
import java.net.URL;

/**
 * ImageScreen.java is the screen to display the graphical images throughout the Statsres program.
 * @author Dave Lee
 * @version 1.0
 */
public class ImageScreen extends Canvas implements MouseListener {
    
    private Image theImage;
    private JFrame theAssociateScreen;
    private int theLeftBorder;
    private int theTopBorder;
    
    /**
     * Default constructor which displays the image to the user.
     * @param fileName a <code>String</code> containing the fileName and location of the image.
     * @param lborder a <code>int</code> containing the size of the left border.
     * @param tborder a <code>int</code> containing the size of the right border.
     * @param assocScreen a <code>JFrame</code> containing the currently displayed screen - 
     * important for removing that screen when clicking on image.
     */
    public ImageScreen ( String fileName, int lborder, int tborder, JFrame assocScreen ) {
        //Initialise variables.
        theLeftBorder = lborder;
        theTopBorder = tborder;
        theAssociateScreen = assocScreen;
        addMouseListener(this);
        //Construct and display image.
        theImage = Toolkit.getDefaultToolkit().getImage(fileName);
        MediaTracker mediaTracker = new MediaTracker(this);
	mediaTracker.addImage(theImage, 0);
        try {
            mediaTracker.waitForID(0);
        }
	catch (InterruptedException ie) {
            System.out.println("Unable to load image!");
        }
    }
    
    /**
     * Second constructor which displays the image to the user using URLs.
     * @param fileName a <code>URL</code> containing the location of the image (for jar files).
     * @param lborder a <code>int</code> containing the size of the left border.
     * @param tborder a <code>int</code> containing the size of the right border.
     * @param assocScreen a <code>JFrame</code> containing the currently displayed screen - 
     * important for removing that screen when clicking on image.
     */
    public ImageScreen ( URL fileName, int lborder, int tborder, JFrame assocScreen ) {
        //Initialise variables.
        theLeftBorder = lborder;
        theTopBorder = tborder;
        theAssociateScreen = assocScreen;
        addMouseListener(this);
        //Construct and display image.
        theImage = Toolkit.getDefaultToolkit().getImage(fileName);
        MediaTracker mediaTracker = new MediaTracker(this);
	mediaTracker.addImage(theImage, 0);
        try
	{
            mediaTracker.waitForID(0);
        }
	catch (InterruptedException ie)
	{
            System.out.println("Unable to load image!");
        }
    }
    
    /**
     * Method to paint image on screen using Graphics object.
     * @param g a <code>Graphics</code> object to use when painting the image.
     */
    @Override
    public void paint ( Graphics g ) {
        g.drawImage(theImage,theLeftBorder,theTopBorder,null);
    }
    
    /**
     * When mouse clicked, close the associated JFrame object using dispose method.
     * @param e a <code>MouseEvent</code> object.
     */
    public void mouseClicked(MouseEvent e) {
        theAssociateScreen.dispose();
    }
    
    /**
     * When mouse pressed, do nothing.
     * @param e a <code>MouseEvent</code> object.
     */
    public void mousePressed(MouseEvent e) {}
    
    /**
     * When mouse released, do nothing.
     * @param e a <code>MouseEvent</code> object.
     */
    public void mouseReleased(MouseEvent e) {}
    
    /**
     * When mouse entered, do nothing.
     * @param e a <code>MouseEvent</code> object.
     */
    public void mouseEntered(MouseEvent e) {}
    
    /**
     * When mouse exited, do nothing.
     * @param e a <code>MouseEvent</code> object.
     */
    public void mouseExited(MouseEvent e) {}
    
}
