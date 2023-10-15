package atmapp.view;

import atmapp.controller.HomeController;
import atmapp.model.Account;
import atmapp.model.Transaction;
import atmapp.utils.Util;
import org.jdesktop.swingx.prompt.PromptSupport;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.util.UUID;
import javax.swing.*;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author Samuel
 */
public class WithdrawDialog extends JDialog {

    private Util utils = new Util();
    private HomeView homeView;
    private HomeController homeController = new HomeController();

    public WithdrawDialog(JFrame mainFrame, HomeView homeView) {
        super(mainFrame, "New Withdraw", true); // 'true' makes the window modal
        this.homeView = homeView;
        setSize(400, 450);
        setLocationRelativeTo(mainFrame); // Center the window with respect to the main frame
        setLayout(null);

        // Disable decoration (title and buttons)
        setUndecorated(true);

        // Confirm if the user wants to exit the window
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                ImageIcon icon = utils.loadImage("/atmapp/assets/salir.png"); // Replace with the path to your icon
                Object[] options = { "Yes", "No" };
                int confirmed = JOptionPane.showOptionDialog(WithdrawDialog.this,
                        "Are you sure you want to exit?", "Confirmation",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                        icon, options, options[1]);

                if (confirmed == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });

        // Add components to the contentPane of the dialog to set the content
        Container contentPane = getContentPane();
        contentPane.setBackground(Color.LIGHT_GRAY);

        // Title
        JLabel title = new JLabel("New Withdraw");
        title.setBounds(115, 10, 200, 60);
        title.setFont(new Font("Dialog", Font.BOLD, 25));
        contentPane.add(title);

        JSeparator separator2 = new JSeparator();
        separator2.setBounds(95, 60, 210, 2);
        contentPane.add(separator2);

        // Amount to withdraw
        JTextField withdrawAmountField = new JTextField();
        PromptSupport.setPrompt("Amount", withdrawAmountField);
        PromptSupport.setForeground(Color.decode("#566A2F"), withdrawAmountField);
        withdrawAmountField.setBounds(100, 100, 200, 50);
        withdrawAmountField.setFont(new Font("Dialog", Font.BOLD, 30));
        withdrawAmountField.setHorizontalAlignment(JTextField.CENTER);
        contentPane.add(withdrawAmountField);

        // Code
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
        codeTextField.setBounds(100, 180, 200, 50);
        codeTextField.setFont(new Font("Dialog", Font.BOLD, 30));
        codeTextField.setHorizontalAlignment(JTextField.CENTER);
        contentPane.add(codeTextField);

        // Description Area
        JTextArea descArea = new JTextArea();
        descArea.setDocument(new PlainDocument() {
            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                if (getLength() + str.length() <= 15) {
                    super.insertString(offs, str, a);
                }
            }
        });
        PromptSupport.setPrompt("DESCRIPTION", descArea);
        PromptSupport.setForeground(Color.decode("#566A2F"), descArea);
        descArea.setBounds(100, 250, 200, 80);
        descArea.setFont(new Font("Dialog", Font.BOLD, 20));
        contentPane.add(descArea);

        JButton cancel = new JButton("Cancel");
        cancel.setBounds(100, 350, 90, 30);
        cancel.setFont(new Font("Dialog", Font.BOLD, 15));
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        contentPane.add(cancel);

        JButton submit = new JButton("Submit");
        submit.setBounds(210, 350, 90, 30);
        submit.setFont(new Font("Dialog", Font.BOLD, 15));
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String amountText = withdrawAmountField.getText();
                try {
                    // Remove any currency format and parse the input as a decimal number
                    amountText = amountText.replace("$", "").replace(",", "");
                    double amount = Double.parseDouble(amountText);

                    // Get the account that has the active session
                    Account account = homeController.loadSession();

                    // Get the code entered by the user
                    String userCode = new String(codeTextField.getPassword());

                    // Compare the user code with the code of the account
                    if (userCode.equals(Long.toString(account.getCode()))) {
                        // Update the money in the account
                        boolean isUpdated = homeController.SubtractAmount(amount);

                        if (!isUpdated) {
                            JOptionPane.showMessageDialog(homeView, "The transaction could not be completed", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            Account accountUpdated = homeController.loadSession();

                            // Create the transaction
                            Transaction newTransaction = new Transaction(UUID.randomUUID().toString(), accountUpdated.getId(),
                                    LocalDate.now(), amount, "Withdraw", descArea.getText());

                            homeController.saveTransaction(newTransaction);
                            homeView.UpdateView(); // Update transactions in the view

                            JOptionPane.showMessageDialog(homeView, "Transaction completed successfully", "Information", JOptionPane.INFORMATION_MESSAGE);
                            dispose();
                        }
                    } else {
                        // Invalid code
                        JOptionPane.showMessageDialog(homeView, "Invalid code", "Error", JOptionPane.ERROR_MESSAGE);
                        codeTextField.setText(""); // Clear the field's content
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(homeView, "Invalid amount", "Error", JOptionPane.ERROR_MESSAGE);
                    withdrawAmountField.setText(""); // Clear the field's content
                }
            }
        });

        contentPane.add(submit);
    }
}

