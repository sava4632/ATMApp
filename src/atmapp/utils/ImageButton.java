package atmapp.utils;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

/**
 * Custom button class that combines text and an image.
 * The image is displayed to the left of the text.
 */
public class ImageButton extends JButton {

    private ImageIcon imageIcon;
    private Util utils = new Util();

    /**
     * Constructor for creating a custom button with text and an image.
     *
     * @param text      The text that will appear to the right of the image.
     * @param imagePath The path to the image that will appear to the left of the text.
     */
    public ImageButton(String text, String imagePath) {
        super(text);
        imageIcon = utils.loadImage(imagePath);
        setIcon(imageIcon);
        setHorizontalTextPosition(SwingConstants.RIGHT);
        setVerticalTextPosition(SwingConstants.CENTER);
    }
}

