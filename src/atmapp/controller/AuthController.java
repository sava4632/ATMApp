package atmapp.controller;

import atmapp.model.Account;
import atmapp.model.Currency;
import atmapp.model.User;
import static atmapp.utils.Util.generateIban;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The AuthController class handles user authentication and registration logic.
 */
public class AuthController {
    /**
     * Saves a new user to the user data file.
     *
     * @param user The user object to be saved.
     * @return True if the user is successfully saved; false if an error occurs.
     */
    public static boolean saveUser(User user) {
        // Load the existing list of users
        List<User> users = loadUsers();

        if (users == null) {
            users = new ArrayList<>(); // Initialize the list if it's null
        }
        users.add(user);

        try {
            // Convert user objects to strings and save them to the file
            List<String> lines = users.stream()
                    .map(User::toString)
                    .collect(Collectors.toList());
            Files.write(Paths.get("users.csv"), lines);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * Loads the list of users from the user data file.
     *
     * @return A list of User objects, or null if there is an error.
     */
    public static List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get("users.csv"));
            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String id = parts[0];
                    String nif = parts[1];
                    String name = parts[2];
                    String email = parts[3];
                    String phone = parts[4];

                    User user = new User(id, nif, name, email, phone);
                    users.add(user);
                } else {
                    // Handle the case where the line does not have the expected format
                    System.out.println("Error: Line with incorrect format - " + line);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return users;
    }

    /**
     * Saves a new account to the account data file.
     *
     * @param account The account object to be saved.
     * @return True if the account is successfully saved; false if an error occurs.
     */
    public static boolean saveAccount(Account account) {
        List<Account> accounts = loadAccounts();
        accounts.add(account);
        try {
            List<String> lines = accounts.stream()
                    .map(Account::toString) // Ensure you have an appropriate toString method in your Account class
                    .collect(Collectors.toList());
            Files.write(Paths.get("accounts.csv"), lines);
            return true;
        } catch (IOException i) {
            i.printStackTrace();
        }
        return false;
    }

    /**
     * Loads the list of accounts from the account data file.
     *
     * @return A list of Account objects, or null if there is an error.
     */
    public static List<Account> loadAccounts() {
        List<Account> accounts = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get("accounts.csv"));
            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length == 12) {
                    String id = parts[0];
                    String iban = parts[1];
                    String holderId = parts[2];
                    String holderName = parts[3];
                    long code = Long.parseLong(parts[4]);
                    LocalDate creationDate = LocalDate.parse(parts[5]);
                    LocalDate expirationDate = LocalDate.parse(parts[6]);
                    double balance = Double.parseDouble(parts[7]);
                    Currency currency = new Currency(parts[8], parts[9], Double.parseDouble(parts[10]));
                    boolean isActive = Boolean.parseBoolean(parts[11]);

                    Account account = new Account(id, iban, holderId, holderName, code, creationDate, expirationDate, balance, currency, isActive);
                    accounts.add(account);
                } else {
                    // Handle the case where the line does not have the expected format
                    System.out.println("Error: Line with incorrect format - " + line);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return accounts;
    }

    /**
     * Generates a unique IBAN (International Bank Account Number) for a new account.
     *
     * @return A unique IBAN.
     */
    public static String generateUniqueIban() {
        String iban;
        do {
            iban = generateIban();
        } while (isIbanInUse(iban));
        return iban;
    }

    /**
     * Checks if an IBAN is already in use by an existing account.
     *
     * @param iban The IBAN to be checked.
     * @return True if the IBAN is in use; false otherwise.
     */
    public static boolean isIbanInUse(String iban) {
        boolean isInUse = false;
        // Get all registered accounts
        List<Account> accounts = loadAccounts();
        for (Account account : accounts) {
            if (account.getIban().equals(iban)) {
                return true; // The account is already in use
            }
        }
        return isInUse;
    }
}

