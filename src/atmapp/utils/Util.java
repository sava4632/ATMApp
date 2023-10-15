package atmapp.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Util {

    /**
     * Load an image from a resource path and return it as an ImageIcon.
     *
     * @param resourcePath The path to the image resource.
     * @return An ImageIcon representing the loaded image.
     */
    public ImageIcon loadImage(String resourcePath) {
        try {
            // Load the image as a resource from the classpath
            java.net.URL imgUrl = getClass().getResource(resourcePath);

            if (imgUrl != null) {
                // Read the image from the resource
                BufferedImage image = ImageIO.read(imgUrl);

                // Return an icon that can be rendered by a component
                return new ImageIcon(image);
            } else {
                System.out.println("Could not find resources: " + resourcePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Generate a random IBAN for accounts.
     *
     * @return A string representing the generated IBAN.
     */
    public static String generateIban() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder("ES");

        // Generate the two control digits (XX)
        for (int i = 0; i < 2; i++) {
            sb.append(random.nextInt(10));
        }

        sb.append(' ');

        // Generate the 20 digits of the account number
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                sb.append(random.nextInt(10));
            }
            if (i < 4) {
                sb.append(' ');
            }
        }

        return sb.toString();
    }

    /**
     * Generate a unique ID based on concatenating two strings and calculating a hash code.
     *
     * @param c1 The first string to concatenate.
     * @param c2 The second string to concatenate.
     * @return A 4-digit unique ID.
     */
    public String generateUniqueID(String c1, String c2) {
        // Concatenate NIF and IBAN to create a unique string
        String uniqueString = c1 + c2;

        // Calculate a hash value for the unique string
        int hashCode = uniqueString.hashCode();

        // Ensure that the hashCode is always positive
        if (hashCode < 0) {
            hashCode *= -1;
        }

        // Convert the hashCode to a 4-digit string
        String uniqueID = String.format("%04d", hashCode);

        return uniqueID;
    }
}

