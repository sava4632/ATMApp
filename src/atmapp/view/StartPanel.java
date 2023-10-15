package atmapp.view;

import atmapp.utils.ImageButton;
import atmapp.utils.Util;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * This class represents the initial panel displayed to users when the application starts.
 */
public class StartPanel extends JPanel {

    private Util utils = new Util();
    private MainFrame mainFrame;

    /**
     * Constructor for the StartPanel class.
     *
     * @param mainFrame The main application frame to which this panel belongs.
     */
    public StartPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setBackground(Color.WHITE); // Change this to your preferred background color
        setSize(850, 550);
        setLayout(null);

        // Logo and bank name
        JLabel logo = new JLabel(utils.loadImage("/atmapp/assets/logo.png"));
        logo.setBounds(10, 10, 60, 60);
        add(logo);

        JLabel logoText = new JLabel("SavaBank");
        logoText.setBounds(75, 10, 100, 60);
        logoText.setFont(new Font("Dialog", Font.BOLD, 18));
        add(logoText);

        // Welcome text
        JLabel welcomeText = new JLabel("<html><b>What would you</b> like to do?</html>");
        welcomeText.setBounds(380, 20, 380, 180);
        welcomeText.setFont(new Font("Dialog", Font.BOLD, 40));
        welcomeText.setForeground(Color.decode("#566A2F"));
        add(welcomeText);

        // User options
        ImageButton loginButton = new ImageButton("Access", "/atmapp/assets/login.png");
        loginButton.setBounds(90, 250, 300, 150); // Adjust these values as needed
        loginButton.setFont(new Font("Dialog", Font.BOLD, 25));
        loginButton.setBackground(Color.decode("#566A2F"));
        loginButton.setBorder(BorderFactory.createLineBorder(Color.decode("#566A2F"), 3));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        add(loginButton);

        // Add an action listener for the login button
        loginButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                MainFrame mainFrame = (MainFrame) SwingUtilities.getWindowAncestor(StartPanel.this);
                mainFrame.switchPanel(new LoginView(mainFrame));
            }
        });

        ImageButton registerButton = new ImageButton("Join Now", "/atmapp/assets/registro.png");
        registerButton.setBounds(450, 250, 300, 150); // Adjust these values as needed
        registerButton.setFont(new Font("Dialog", Font.BOLD, 25));
        registerButton.setBackground(Color.decode("#566A2F"));
        registerButton.setBorder(BorderFactory.createLineBorder(Color.decode("#566A2F"), 3));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        add(registerButton);

        // Add an action listener for the register button
        registerButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                MainFrame mainFrame = (MainFrame) SwingUtilities.getWindowAncestor(StartPanel.this);
                mainFrame.switchPanel(new RegisterView(mainFrame));
            }
        });
    }
}

