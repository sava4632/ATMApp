package atmapp.view;

import atmapp.controller.AuthController;
import atmapp.model.Account;
import atmapp.utils.SessionManager;
import atmapp.utils.UppercaseJTextField;
import atmapp.utils.Util;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import org.jdesktop.swingx.prompt.PromptSupport;

/**
 * This class represents the login view of the ATM application. Users can enter
 * their IBAN and code to access their account.
 *
 * @author Samuel
 */
public class LoginView extends JPanel {

    private Util utils = new Util();
    private AuthController controller;
    MainFrame mainFrame;

    public LoginView(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        // Configure the view
        setSize(850, 550);
        setBackground(Color.WHITE);
        setLayout(null);

        // Back Button for returning to the previous screen
        ImageIcon backImageIcon = utils.loadImage("/atmapp/assets/back.png");
        JButton backButton = new JButton(backImageIcon);
        backButton.setBounds(5, 5, 40, 40);
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        add(backButton);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmed = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to go back?", "Confirmation",
                        JOptionPane.YES_NO_OPTION);

                if (confirmed == JOptionPane.YES_OPTION) {
                    MainFrame mainFrame = (MainFrame) SwingUtilities.getWindowAncestor(LoginView.this);
                    mainFrame.switchPanel(new StartPanel(mainFrame));
                }
            }
        });

        // Logo and bank name
        JLabel logo = new JLabel(utils.loadImage("/atmapp/assets/logo.png"));
        logo.setBounds(55, 10, 60, 60);
        add(logo);

        JLabel logoText = new JLabel("SavaBank");
        logoText.setBounds(120, 10, 100, 60);
        logoText.setFont(new Font("Dialog", Font.BOLD, 18));
        add(logoText);

        // Welcome Text
        JLabel welcomeText = new JLabel("<html><b>Hello</b> again!</html>");
        welcomeText.setBounds(380, 20, 380, 180);
        welcomeText.setFont(new Font("Dialog", Font.BOLD, 50));
        welcomeText.setForeground(Color.decode("#566A2F"));
        add(welcomeText);

        // IBAN TextField with a placeholder
        JTextField ibanTextField = new JTextField("ES06 8491 5836 6827 5579 9690");
        PromptSupport.setPrompt("ESXX XXXX XXXX XXXX XXXX XXXX", ibanTextField);
        PromptSupport.setForeground(Color.decode("#566A2F"), ibanTextField);
        ibanTextField.setBounds(220, 200, 380, 70);
        ibanTextField.setFont(new Font("Dialog", Font.BOLD, 20));
        ibanTextField.setHorizontalAlignment(JTextField.CENTER);
        add(ibanTextField);

        // Uppercase filter for IBAN TextField
        DocumentFilter f = new UppercaseJTextField();
        AbstractDocument doc = (AbstractDocument) ibanTextField.getDocument();
        doc.setDocumentFilter(f);

        // Code TextField with placeholder and character limit
        JPasswordField codeTextField = new JPasswordField();
        codeTextField.setDocument(new PlainDocument() {
            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                if (getLength() + str.length() <= 4) {
                    super.insertString(offs, str, a);
                }
            }
        });
        PromptSupport.setPrompt("XXXX", codeTextField);
        PromptSupport.setForeground(Color.decode("#566A2F"), codeTextField);
        codeTextField.setBounds(220, 290, 380, 70);
        codeTextField.setFont(new Font("Dialog", Font.BOLD, 20));
        codeTextField.setHorizontalAlignment(JTextField.CENTER);
        add(codeTextField);

        // Login Button
        JButton loginButton = new JButton("Access");
        loginButton.setBounds(270, 400, 280, 30);
        loginButton.setBackground(Color.decode("#566A2F"));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Dialog", Font.BOLD, 15));
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String iban = ibanTextField.getText();
                String code = new String(codeTextField.getPassword());
                int isAuthenticated = validateCredentials(iban, code);
                if (isAuthenticated == 1) {
                    JOptionPane.showMessageDialog(mainFrame, "No account found with those credentials.");
                } else if (isAuthenticated == 2) {
                    JOptionPane.showMessageDialog(mainFrame, "The account is disabled.");
                } else {
                    // Get the authenticated user's data
                    List<Account> accounts = controller.loadAccounts();
                    Account authenticatedUser = null;
                    for (Account account : accounts) {
                        if (account.getIban().equals(iban) && account.getCode() == Long.parseLong(code) && account.isIsActive()) {
                            authenticatedUser = account;
                        }
                    }
                    // Save the session
                    SessionManager.saveSession(authenticatedUser);
                    System.out.println("New session is created: " + authenticatedUser.toString());

                    MainFrame mainFrame = (MainFrame) SwingUtilities.getWindowAncestor(LoginView.this);
                    mainFrame.switchPanel(new HomeView(mainFrame));
                }
            }

        });
        add(loginButton);
    }

    // Validate user credentials
    private int validateCredentials(String iban, String code) {
        System.out.println(iban + ":" + code);
        List<Account> accounts = controller.loadAccounts();
        for (Account account : accounts) {
            if (!code.isEmpty() && account.getCode() == Long.parseLong(code)) {
                if (account.getIban().equals(iban) && account.getCode() == Long.parseLong(code) && account.isIsActive()) {
                    return 0;
                }
                if (!account.isIsActive()) {
                    return 2;
                }
            }
        }
        return 1;
    }
}
