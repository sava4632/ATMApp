package atmapp.controller;

import atmapp.model.Account;
import atmapp.model.Transaction;
import atmapp.model.User;
import atmapp.utils.SessionManager;
import atmapp.utils.Util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The `HomeController` class handles the core logic of the ATM application, including
 * user sessions, transactions, and account management.
 *
 * @author Samuel
 */
public class HomeController {

    private static AuthController authController = new AuthController();
    private Account accountSession;
    private static Util utils = new Util();
    private SessionManager sessionManager = new SessionManager();

    public HomeController() {
        accountSession = loadSession();
    }

    /**
     * Get the User associated with the current account session.
     *
     * @return The user corresponding to the account session.
     */
    public User getUserByAccountSession() {
        User user = null;
        List<User> users = authController.loadUsers();
        for (User u : users) {
            if (accountSession.getHolderId().equals(u.getId())) {
                user = u;
            }
        }
        return user;
    }

    /**
     * Save a transaction to the transactions list.
     *
     * @param transaction The transaction to be saved.
     * @return True if the transaction is successfully saved, false otherwise.
     */
    public boolean saveTransaction(Transaction transaction) {
        try {
            List<Transaction> transactions = loadTransactions();
            transactions.add(transaction);

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("transactions.txt"));
            oos.writeObject(transactions);
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }

    /**
     * Load all transactions from the transactions file.
     *
     * @return A list of all transactions.
     */
    public List<Transaction> loadTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("transactions.txt"));
            List<Transaction> loadedTransactions = (List<Transaction>) ois.readObject();
            transactions.addAll(loadedTransactions);
        } catch (EOFException e) {
            // Empty file
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return transactions;
    }

    /**
     * Load transactions for the current account session.
     *
     * @return A list of transactions associated with the account session.
     */
    public List<Transaction> loadTransactionsByAccount() {
        List<Transaction> allTransactions = loadTransactions();
        List<Transaction> meTransactions = new ArrayList<>();
        if (allTransactions != null) {
            for (Transaction transaction : allTransactions) {
                if (transaction.getAccountId().equals(accountSession.getId())) {
                    meTransactions.add(transaction);
                }
            }
        } else {
            return null;
        }
        return meTransactions;
    }

    /**
     * Load the current user's account session.
     *
     * @return The account associated with the current session.
     */
    public Account loadSession() {
        Account account = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("session.txt"));
            account = (Account) ois.readObject();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return account;
    }

    /**
     * Delete a transaction by its ID.
     *
     * @param id The ID of the transaction to be deleted.
     * @return True if the transaction is successfully deleted, false otherwise.
     */
    public boolean deleteTransactionById(String id) {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("transactions.txt"));
            List<Transaction> transactions = (List<Transaction>) ois.readObject();
            ois.close();

            Iterator<Transaction> iterator = transactions.iterator();
            while (iterator.hasNext()) {
                Transaction transaction = iterator.next();
                if (transaction.getId().equals(id)) {
                    iterator.remove();
                }
            }

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("transactions.txt"));
            oos.writeObject(transactions);
            oos.close();

            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }
        return false;
    }

    /**
     * Subtract an amount from the current account balance.
     *
     * @param amount The amount to be subtracted.
     * @return True if the balance is successfully updated, false otherwise.
     */
    public boolean SubtractAmount(double amount) {
        accountSession = loadSession();
        List<Account> allAccounts = authController.loadAccounts();
        Account accountUpdated = new Account();
        for (Account a : allAccounts) {
            if (a.getId().equals(accountSession.getId())) {
                a.setBalance(accountSession.getBalance() - amount);
                accountUpdated = a;
                break;
            }
        }
        boolean isUpdated = saveListOfAccounts(allAccounts);
        if (!isUpdated) {
            return false;
        } else {
            sessionManager.saveSession(accountUpdated);
            accountSession = loadSession();
            return true;
        }
    }

    /**
     * Add an amount to the current account balance.
     *
     * @param amount The amount to be added.
     * @return True if the balance is successfully updated, false otherwise.
     */
    public boolean AddQuantity(double amount) {
        accountSession = loadSession();
        List<Account> allAccounts = authController.loadAccounts();
        Account accountUpdated = new Account();
        for (Account a : allAccounts) {
            if (a.getId().equals(accountSession.getId())) {
                a.setBalance(accountSession.getBalance() + amount);
                accountUpdated = a;
                break;
            }
        }
        boolean isUpdated = saveListOfAccounts(allAccounts);
        if (!isUpdated) {
            return false;
        } else {
            sessionManager.saveSession(accountUpdated);
            accountSession = loadSession();
            return true;
        }
    }

    /**
     * Save the updated list of accounts to the accounts file.
     *
     * @param accountsUpdated The list of accounts to be saved.
     * @return True if the accounts list is successfully updated, false otherwise.
     */
    private boolean saveListOfAccounts(List<Account> accountsUpdated) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("accounts.csv"))){
            List<String> lines = accountsUpdated.stream()
                    .map(Account::toString)
                    .collect(Collectors.toList());
            Files.write(Paths.get("accounts.csv"), lines);
            return true;
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
}
