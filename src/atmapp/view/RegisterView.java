/**
 *
 */
package atmapp.view;

import atmapp.controller.AuthController;
import atmapp.model.Account;
import atmapp.model.Currency;
import atmapp.model.User;
import atmapp.utils.Util;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.UUID;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import org.jdesktop.swingx.prompt.PromptSupport; // Para poner los placeholder

/**
 * The RegisterView class is responsible for the user registration interface.
 */
public class RegisterView extends JPanel {

    private Util utils = new Util();
    private AuthController controller;
    MainFrame mainFrame;

    public RegisterView(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        //Configuración de la vista
        setSize(850, 550);
        setBackground(Color.WHITE);
        setLayout(null);

        // Back button to return to the previous view
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
                    MainFrame mainFrame = (MainFrame) SwingUtilities.getWindowAncestor(RegisterView.this);
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

        // IBAN field
        JTextField ibanField = new JTextField();
        ibanField.setEditable(false); // Este campo no será editable
        String newIban = controller.generateUniqueIban();
        ibanField.setText(newIban);
        ibanField.setBounds(450, 50, 280, 30);
        ibanField.setFont(new Font("Dialog", Font.BOLD, 15));
        ibanField.setFocusable(false);
        ibanField.setForeground(Color.decode("#566A2F"));
        ibanField.setHorizontalAlignment(JTextField.CENTER);
        ibanField.setBackground(Color.LIGHT_GRAY);
        add(ibanField);

        // NIF field
        JTextField nifField = new JTextField();
        PromptSupport.setPrompt("NIF", nifField); //Poner placeholder
        PromptSupport.setForeground(Color.decode("#566A2F"), nifField);
        nifField.setBounds(450, 100, 280, 30);
        nifField.setFont(new Font("Dialog", Font.BOLD, 15));
        add(nifField);

        // Name field
        JTextField nameField = new JTextField();
        PromptSupport.setPrompt("Full name", nameField); //Poner placeholder
        PromptSupport.setForeground(Color.decode("#566A2F"), nameField);
        nameField.setBounds(450, 150, 280, 30);
        nameField.setFont(new Font("Dialog", Font.BOLD, 15));
        add(nameField);

        // Age field
        JTextField ageField = new JTextField();
        ageField.setDocument(new PlainDocument() { //Limitar el numero maximo de carateres a 3
            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                if (getLength() + str.length() <= 3) {
                    super.insertString(offs, str, a);
                }
            }
        });
        PromptSupport.setPrompt("Age", ageField); //Poner placeholder
        PromptSupport.setForeground(Color.decode("#566A2F"), ageField);
        ageField.setBounds(450, 200, 50, 30);
        ageField.setFont(new Font("Dialog", Font.BOLD, 15));
        ageField.setHorizontalAlignment(JTextField.CENTER);
        add(ageField);

        // Phone field
        JTextField phoneField = new JTextField();
        phoneField.setDocument(new PlainDocument() { //Limitar el numero maximo de carateres a 9
            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                if (getLength() + str.length() <= 9) {
                    super.insertString(offs, str, a);
                }
            }
        });
        PromptSupport.setPrompt("Phone number", phoneField); //Poner placeholder
        PromptSupport.setForeground(Color.decode("#566A2F"), phoneField);
        phoneField.setBounds(550, 200, 180, 30);
        phoneField.setFont(new Font("Dialog", Font.BOLD, 15));
        add(phoneField);

        // Email field
        JTextField emailField = new JTextField();
        PromptSupport.setPrompt("Email", emailField); //Poner placeholder
        PromptSupport.setForeground(Color.decode("#566A2F"), emailField);
        emailField.setBounds(450, 250, 280, 30);
        emailField.setFont(new Font("Dialog", Font.BOLD, 15));
        add(emailField);

        // Code field
        JPasswordField codeField = new JPasswordField();
        codeField.setDocument(new PlainDocument() { //Limitar el numero maximo de carateres a 4
            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                if (getLength() + str.length() <= 4) {
                    super.insertString(offs, str, a);
                }
            }
        });
        PromptSupport.setPrompt("Code", codeField); //Poner placeholder
        PromptSupport.setForeground(Color.decode("#566A2F"), codeField);
        codeField.setBounds(450, 300, 120, 30); // Posicionar y dimensionar el campo
        codeField.setFont(new Font("Dialog", Font.BOLD, 15));
        codeField.setHorizontalAlignment(JTextField.CENTER);
        add(codeField);
        // Confirm Code field
        JPasswordField confirmCodeField = new JPasswordField();
        confirmCodeField.setDocument(new PlainDocument() { //Limitar el numero maximo de carateres a 4
            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                if (getLength() + str.length() <= 4) {
                    super.insertString(offs, str, a);
                }
            }
        });
        PromptSupport.setPrompt("Confirm code", confirmCodeField); //Poner placeholder
        PromptSupport.setForeground(Color.decode("#566A2F"), confirmCodeField);
        confirmCodeField.setBounds(610, 300, 120, 30); // Posicionar y dimensionar el campo
        confirmCodeField.setFont(new Font("Dialog", Font.BOLD, 15));
        confirmCodeField.setHorizontalAlignment(JTextField.CENTER);
        add(confirmCodeField);

        // Check conditions checkbox
        JCheckBox checkBoxConditions = new JCheckBox("I accept the terms and conditions");
        checkBoxConditions.setBounds(450, 350, 280, 30);
        checkBoxConditions.setFont(new Font("Dialog", Font.ITALIC, 15));
        add(checkBoxConditions);

        // User upload image
        JLabel userUploadImage = new JLabel(utils.loadImage("/atmapp/assets/userupload.png"));
        userUploadImage.setBounds(20, 30, 400, 400);
        add(userUploadImage);

        // Register button
        JButton registerButton = new JButton("Register");
        registerButton.setBounds(450, 400, 280, 30);  // Set the button's position and size
        registerButton.setBackground(Color.decode("#566A2F"));  // Set the button's background color
        registerButton.setForeground(Color.WHITE);  // Set the button's text color
        registerButton.setFont(new Font("Dialog", Font.BOLD, 15));  // Set the font for the button's text

// Add an action listener to the button
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller = new AuthController();

                // Obtain the values from various input fields
                String iban = ibanField.getText();
                String nif = nifField.getText();
                String name = nameField.getText();
                String ageStr = ageField.getText();
                String phone = phoneField.getText();
                String code = new String(codeField.getPassword());
                String confirmCode = new String(confirmCodeField.getPassword());
                String email = emailField.getText();

                int codeValidationResult = validateCode(code, confirmCode);

                if (iban.isEmpty() || nif.isEmpty() || name.isEmpty() || ageStr.isEmpty() || phone.isEmpty() || code.isEmpty()
                        || confirmCode.isEmpty() || email.isEmpty()) {
                    // Show an error message if any field is empty
                    JOptionPane.showMessageDialog(mainFrame, "Please fill in all fields.");
                } else if (!checkBoxConditions.isSelected()) {
                    // Check if the user has accepted the terms and conditions
                    JOptionPane.showMessageDialog(mainFrame, "Please accept the policies");
                } else {
                    // Convert the age to an integer
                    int age = Integer.parseInt(ageStr);

                    // Validate age, NIF, phone, code, and email
                    if (age < 18) {
                        JOptionPane.showMessageDialog(mainFrame, "You must be at least 18 years old to register.");
                    } else if (validateNif(nif) != 0) {
                        if (validateNif(nif) == 1) {
                            JOptionPane.showMessageDialog(mainFrame, "The NIF entered is not valid.");
                        } else if (validateNif(nif) == 2) {
                            JOptionPane.showMessageDialog(mainFrame, "The NIF entered is not valid.");
                        }
                    } else if (!validatePhone(phone)) {
                        JOptionPane.showMessageDialog(mainFrame, "The phone number entered is not valid.");
                    } else if (codeValidationResult != 0) {
                        if (codeValidationResult == 1) {
                            JOptionPane.showMessageDialog(mainFrame, "The codes do not match.");
                        } else if (codeValidationResult == 2) {
                            JOptionPane.showMessageDialog(mainFrame, "The code must be exactly 4 characters.");
                        }
                    } else if (!validateEmail(email)) {
                        JOptionPane.showMessageDialog(mainFrame, "The email entered is not valid.");
                    } else {
                        // If all validations pass, proceed with user registration
                        registerUser(iban, nif, name, age, phone, code, email);
                    }
                }
            }
        });
        add(registerButton);
    }

    private void registerUser(String iban, String nif, String name, int age, String phone, String code, String email) {
        // Generate a unique user ID
        String idUser = utils.generateUniqueID(iban, UUID.randomUUID().toString());
        // Create a user with the obtained data
        User user = new User(idUser, nif, name, email, phone);
        // Add the user to the database/file
        if (controller.saveUser(user)) {
            // Create the user's account
            String idAccount = utils.generateUniqueID(iban, name);
            Currency currency = new Currency("EUR", "€", 0.95); // Moneda

            LocalDate currentDate = LocalDate.now();
            LocalDate expirationDate = currentDate.plus(Period.ofYears(7));

            long codeLong = Long.parseLong(code);
            Account account = new Account(idAccount, iban, idUser, name, codeLong,
                    currentDate, expirationDate, 0, currency, true);

            if (controller.saveAccount(account)) {
                JOptionPane.showMessageDialog(mainFrame, "Successfully created user");
                // Redirect the user
                MainFrame mainFrame = (MainFrame) SwingUtilities.getWindowAncestor(RegisterView.this);
                mainFrame.switchPanel(new StartPanel(mainFrame));
            } else {
                System.out.println("Error: Cannot create account");
            }
        } else {
            System.out.println("Error: Cannot create user");
        }
    }

    private boolean validateEmail(String email) {
        // Regular expression to validate a basic email address
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }

    private int validateCode(String code, String confirmCode) {
        // Check if both codes are equal
        if (!code.equals(confirmCode)) {
            return 1;
        }
        if (code.length() != 4) {
            return 2; // Formato incorrecto
        }
        return 0; // Código válido
    }

    private int validateNif(String nif) {
        // Check that the NIF has exactly 9 characters
        if (nif.length() != 9) {
            return 1;// Formato invalido
        }

        String numeros = nif.substring(0, 8);
        char letra = nif.charAt(8);
        if (!numeros.matches("\\d+") || !Character.isLetter(letra)) {
            return 1;
        }

        List<User> users = controller.loadUsers();
        for (User user : users) {
            if (user.getNif().equalsIgnoreCase(nif)) {
                return 2;
            }
        }
        return 0;
    }

    private boolean validatePhone(String phone) {
        // Check if the phone number has exactly 9 digits and is composed of digits only
        if (phone.length() != 9) {
            return false;
        }
        for (char c : phone.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

}
