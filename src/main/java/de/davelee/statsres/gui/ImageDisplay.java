package de.davelee.statsres.gui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

/**
 * Class to display an image in the Statsres program.
 * @author Dave Lee
 */
public class ImageDisplay extends Canvas {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Image image;
    private final int leftBorder;
    private final int topBorder;
    private final static Logger logger = LoggerFactory.getLogger(ImageDisplay.class);
    
    /**
     * Create a new image display.
     * @param fileName a <code>String</code> with the location of the file.
     * @param leftBorder a <code>int</code> with the position of left border.
     * @param topBorder a <code>int</code> with the position of top border.
     */
    public ImageDisplay(String fileName, int leftBorder, int topBorder ) {
        //Initialise variables.
        this.leftBorder = leftBorder;
        this.topBorder = topBorder;
        //Construct and display image.
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        image = toolkit.getImage(ImageDisplay.class.getResource("/" + fileName));        
        //theImage = toolkit.getImage(fileName);
        MediaTracker mediaTracker = new MediaTracker(this);
        mediaTracker.addImage(image, 0);
        try {
            mediaTracker.waitForID(0);
        }
	    catch (InterruptedException ie) {
            logger.error("Picture could not be read ", ie);
        }
    }
    
    /**
     * Paint the graphics part of this screen.
     * @param g a <code>Graphics</code> object.
     */
    public void paint ( Graphics g ) {
        g.drawImage(image,leftBorder,topBorder,null);
    }
    
    /**
     * Repaint the graphics part of this screen.
     */
    public void repaint () {
        Graphics g = getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(-1000,-1000,2000,2000);
        paint ( g );
    }

}
