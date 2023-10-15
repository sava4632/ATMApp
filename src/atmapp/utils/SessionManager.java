package atmapp.utils;

import atmapp.model.Account;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Utility class to manage user sessions.
 */
public class SessionManager {

    private static final String SESSION_FILE = "session.txt"; // Name of the session file

    /**
     * Loads the user's session from the session file.
     *
     * @return The user's account or null if the session file is not found or cannot be read.
     */
    public static Account loadSession() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SESSION_FILE))) {
            return (Account) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null; // Return null if the file is not found or cannot be read
        }
    }

    /**
     * Saves the user's session to the session file.
     *
     * @param userAccount The user's account to be saved in the session.
     */
    public static void saveSession(Account userAccount) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SESSION_FILE))) {
            oos.writeObject(userAccount);
        } catch (IOException e) {
            e.printStackTrace(); // Handle file write errors
        }
    }

    /**
     * Clears the user's session by deleting the session file.
     */
    public static void clearSession() {
        File sessionFile = new File(SESSION_FILE);
        if (sessionFile.exists()) {
            sessionFile.delete(); // Delete the session file if it exists
        }
    }
}

