package atmapp;

import atmapp.view.MainFrame;
import javax.swing.SwingUtilities;

/**
 * This is the entry point for the ATM application.
 */
public class LauncherApp {

    /**
     * The main method that initializes and displays the application's user interface.
     *
     * @param args The command line arguments (not used in this application).
     */
    public static void main(String[] args) {
        // Ensure that Swing-related operations run on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Show the graphical user interface (GUI) of the application by creating a MainFrame instance
                new MainFrame().setVisible(true);
            }
        });
    }
}

