package atmapp.view;

import atmapp.controller.AuthController;
import atmapp.controller.HomeController;
import atmapp.model.Account;
import atmapp.model.Transaction;
import atmapp.utils.SessionManager;
import atmapp.utils.Util;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;

public class HomeView extends JPanel {

    private Util utils = new Util();
    private HomeController homeController = new HomeController();
    MainFrame mainFrame;
    private Account accountSession;
    private JPanel historyJPanel;
    private Map<String, Color> transactionTypeColors = new HashMap<>(); // For card colors
    private JLabel balanceDataJLabel;

    public HomeView(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        accountSession = SessionManager.loadSession(); // Get the user's session
        // Initialize colors for transaction types
        transactionTypeColors.put("Deposit", Color.decode("#008000"));
        transactionTypeColors.put("Withdraw", Color.decode("#FFA500"));

        // Configuration of the view
        setSize(850, 550);
        setBackground(Color.WHITE);
        setLayout(null);

        // Disconnect Button
        ImageIcon disconnectImageIcon = utils.loadImage("/atmapp/assets/disconnect.png");
        JButton disconnectButton = new JButton(disconnectImageIcon);
        disconnectButton.setBounds(775, 20, 40, 40);
        disconnectButton.setBorderPainted(false);
        disconnectButton.setContentAreaFilled(false);
        disconnectButton.setFocusPainted(false);
        add(disconnectButton);
        disconnectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmed = JOptionPane.showConfirmDialog(mainFrame,
                        "Are you sure you want to disconnect?", "Confirmation",
                        JOptionPane.YES_NO_OPTION);

                if (confirmed == JOptionPane.YES_OPTION) {
                    // Log out
                    SessionManager.clearSession();
                    System.out.println("Client disconnected");
                    MainFrame mainFrame = (MainFrame) SwingUtilities.getWindowAncestor(HomeView.this);
                    mainFrame.switchPanel(new StartPanel(mainFrame));
                }
            }
        });

        // Logo and bank name
        JLabel logo = new JLabel(utils.loadImage("/atmapp/assets/logo.png"));
        logo.setBounds(10, 10, 60, 60);
        add(logo);

        // User's full name
        JLabel fullNameJLabel = new JLabel(homeController.getUserByAccountSession().getFullname());
        fullNameJLabel.setBounds(600, 20, 200, 40);
        fullNameJLabel.setFont(new Font("Dialog", Font.BOLD, 20));
        fullNameJLabel.setForeground(Color.decode("#566A2F"));
        add(fullNameJLabel);

        JLabel logoText = new JLabel("SavaBank");
        logoText.setBounds(75, 10, 100, 60);
        logoText.setFont(new Font("Dialog", Font.BOLD, 18));
        add(logoText);

        // Balance
        JLabel balanceJLabel = new JLabel("Balance");
        balanceJLabel.setBounds(85, 80, 100, 80);
        balanceJLabel.setFont(new Font("Dialog", Font.BOLD, 25));
        balanceJLabel.setForeground(Color.BLACK);
        add(balanceJLabel);

        balanceDataJLabel = new JLabel();
        balanceDataJLabel.setBounds(65, 120, 350, 100);
        balanceDataJLabel.setFont(new Font("Dialog", Font.BOLD, 60));
        balanceDataJLabel.setForeground(Color.decode("#566A2F"));
        balanceDataJLabel.setHorizontalAlignment(JLabel.CENTER);
        add(balanceDataJLabel);

        JSeparator separator = new JSeparator();
        separator.setBounds(80, 200, 335, 2);
        add(separator);

        // Operation Buttons
        JButton withdrawButton = new JButton("Withdraw cash");
        withdrawButton.setBounds(120, 240, 250, 80);
        withdrawButton.setFont(new Font("Dialog", Font.BOLD, 18));
        withdrawButton.setBackground(Color.decode("#566A2F"));
        withdrawButton.setForeground(Color.WHITE);
        withdrawButton.setFocusPainted(false);
        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                WithdrawDialog withdrawView = new WithdrawDialog(mainFrame, HomeView.this);
                withdrawView.setVisible(true);
            }
        });
        add(withdrawButton);

        JButton depositButton = new JButton("Deposit money");
        depositButton.setBounds(120, 350, 250, 80);
        depositButton.setFont(new Font("Dialog", Font.BOLD, 18));
        depositButton.setBackground(Color.decode("#566A2F"));
        depositButton.setForeground(Color.WHITE);
        depositButton.setFocusPainted(false);
        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DepositDialog depositDialog = new DepositDialog(mainFrame, HomeView.this);
                depositDialog.setVisible(true);
            }
        });
        add(depositButton);

        // History
        JLabel history = new JLabel("Transaction history");
        history.setBounds(550, 80, 200, 80);
        history.setFont(new Font("Dialog", Font.PLAIN, 20));
        history.setForeground(Color.BLACK);
        add(history);

        JSeparator separator2 = new JSeparator();
        separator2.setBounds(530, 135, 210, 2);
        add(separator2);

        // History Panel
        historyJPanel = new JPanel();
        historyJPanel.setLayout(new BoxLayout(historyJPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPaneHistory = new JScrollPane(historyJPanel);
        scrollPaneHistory.setBounds(500, 150, 270, 300);
        add(scrollPaneHistory, BorderLayout.CENTER);
        UpdateView();
    }

    public void UpdateView() {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        // Update the balance in the corresponding JLabel
        accountSession = homeController.loadSession();
        double updatedBalance = accountSession.getBalanceInEuros();
        String formattedBalance = decimalFormat.format(updatedBalance);
        String balanceText = "<html>" + formattedBalance + " " + accountSession.getCurrency().getSymbol() + "</html>";
        balanceDataJLabel.setText(balanceText);

        accountSession = homeController.loadSession();
        List<Transaction> transactions = homeController.loadTransactionsByAccount();

        // Clear the history panel before adding new transactions
        historyJPanel.removeAll();

        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction transaction = transactions.get(i);
            JPanel transactionPanel = new JPanel();

            // Get the transaction type
            String transactionType = transaction.getType();

            // Get the color for the transaction type from the mapping
            Color color = transactionTypeColors.get(transactionType);

            // Set the border color
            transactionPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(color), "Transaction "));
            transactionPanel.setBackground(Color.WHITE);
            transactionPanel.setLayout(new BoxLayout(transactionPanel, BoxLayout.Y_AXIS));

            transactionPanel.add(new JLabel("Date: " + transaction.getDate()));

            String formattedAmount = decimalFormat.format(transaction.getAmount());
            transactionPanel.add(new JLabel("Amount: " + formattedAmount + " " + accountSession.getCurrency().getCode()));

            transactionPanel.add(new JLabel("Type: " + transaction.getType()));
            transactionPanel.add(new JLabel("Description: " + transaction.getDescription()));

            transactionPanel.add(Box.createVerticalStrut(10));

            JButton deleteButton = new JButton("remove");
            deleteButton.setBackground(Color.RED);
            deleteButton.setForeground(Color.WHITE);
            deleteButton.setFocusPainted(false);
            transactionPanel.add(deleteButton);

            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int confirmed = JOptionPane.showConfirmDialog(mainFrame,
                            "Are you sure you want to delete it?", "Confirmation",
                            JOptionPane.YES_NO_OPTION);

                    if (confirmed == JOptionPane.YES_OPTION) {
                        boolean isDeleted = homeController.deleteTransactionById(transaction.getId());

                        if (!isDeleted) {
                            JOptionPane.showMessageDialog(mainFrame, "Could not delete", "Error", JOptionPane.INFORMATION_MESSAGE);
                        }
                        UpdateView();
                    }
                }
            });

            transactionPanel.add(Box.createVerticalStrut(10));
            historyJPanel.add(transactionPanel);
        }
        validate();
        repaint();
    }
}
