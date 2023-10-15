package atmapp.view;

import atmapp.utils.SessionManager;
import atmapp.utils.Util;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

/**
 * This class represents the main frame of the ATM application.
 */
public class MainFrame extends JFrame {

    private JPanel currentPanel; // The initial panel of the application
    private Util utils = new Util();

    /**
     * Constructor for the MainFrame class, which sets up the application's main window.
     */
    public MainFrame() {
        // Configure the JFrame
        setTitle("ATM Machine");
        setSize(850, 550);
        setLocationRelativeTo(null); // Center the window on the screen
        setResizable(false); // Disable window resizing

        // Load the icon from a file in resources
        ImageIcon icon = new ImageIcon(MainFrame.class.getResource("/atmapp/assets/logo.png")); // Change the name and location based on your project
        setIconImage(icon.getImage()); // Set the window's icon

        // Confirm that the user wants to exit the application
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                ImageIcon icon = utils.loadImage("/atmapp/assets/salir.png"); // Replace this with the path to your icon
                Object[] options = {"Yes", "No"};
                int confirmed = JOptionPane.showOptionDialog(MainFrame.this,
                        "Are you sure you want to go out?", "Confirmation",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                        icon, options, options[1]);

                if (confirmed == JOptionPane.YES_OPTION) {
                    // Close the session
                    SessionManager.clearSession();
                    dispose();
                }
            }
        });

        setLayout(null);

        // Initial panel of the application
        currentPanel = new StartPanel(this);
        currentPanel.setBounds(0, 0, 850, 550);
        add(currentPanel);
    }

    /**
     * Method to change the loaded panel in the MainFrame.
     *
     * @param newPanel The new panel/view.
     */
    public void switchPanel(JPanel newPanel) {
        remove(currentPanel);
        currentPanel = newPanel;
        add(currentPanel);
        revalidate();
        repaint();
    }
}