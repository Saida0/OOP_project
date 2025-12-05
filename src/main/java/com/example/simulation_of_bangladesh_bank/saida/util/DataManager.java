package com.example.simulation_of_bangladesh_bank.saida.util;

import com.example.simulation_of_bangladesh_bank.saida.model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * DataManager - Utility class for binary file operations.
 * Handles reading and writing data to .bin files.
 */
public class DataManager {
    
    // Data directory path (relative to project root)
    private static final String DATA_DIR = "data";
    
    // File names
    public static final String USERS_FILE = "users.bin";
    public static final String SLR_DATA_FILE = "slr_data.bin";
    public static final String LOANS_FILE = "loans.bin";
    public static final String AML_CASES_FILE = "aml_cases.bin";
    public static final String BRANCHES_FILE = "branches.bin";
    public static final String INTERBANK_FILE = "interbank_operations.bin";
    public static final String LIQUIDITY_FILE = "liquidity_management.bin";
    public static final String REVENUE_FILE = "revenue.bin";
    public static final String EXPENDITURE_FILE = "expenditure.bin";
    public static final String DEBT_FILE = "public_debt.bin";
    public static final String BUDGET_FILE = "budget.bin";
    public static final String REFORM_FILE = "reforms.bin";

    /**
     * Gets the full path to a data file.
     */
    public static String getFilePath(String filename) {
        return DATA_DIR + File.separator + filename;
    }

    /**
     * Ensures the data directory exists.
     */
    public static void ensureDataDirectory() {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    /**
     * Saves a list of objects to a binary file.
     */
    public static <T extends Serializable> boolean saveToFile(String filename, List<T> data) {
        ensureDataDirectory();
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(getFilePath(filename)))) {
            oos.writeObject(data);
            return true;
        } catch (IOException e) {
            System.err.println("Error saving to file " + filename + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Loads a list of objects from a binary file.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> List<T> loadFromFile(String filename) {
        File file = new File(getFilePath(filename));
        if (!file.exists()) {
            return new ArrayList<>();
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(file))) {
            return (List<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading from file " + filename + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Saves a single object to a binary file.
     */
    public static <T extends Serializable> boolean saveObjectToFile(String filename, T data) {
        ensureDataDirectory();
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(getFilePath(filename)))) {
            oos.writeObject(data);
            return true;
        } catch (IOException e) {
            System.err.println("Error saving object to file " + filename + ": " + e.getMessage());
            return false;
        }
    }

    /**
     * Loads a single object from a binary file.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T loadObjectFromFile(String filename) {
        File file = new File(getFilePath(filename));
        if (!file.exists()) {
            return null;
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(file))) {
            return (T) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading object from file " + filename + ": " + e.getMessage());
            return null;
        }
    }

    // ==================== USER MANAGEMENT ====================

    /**
     * Saves users to binary file.
     */
    public static boolean saveUsers(List<User> users) {
        return saveToFile(USERS_FILE, users);
    }

    /**
     * Loads users from binary file.
     */
    public static List<User> loadUsers() {
        return loadFromFile(USERS_FILE);
    }

    /**
     * Finds a user by username.
     */
    public static Optional<User> findUserByUsername(String username) {
        List<User> users = loadUsers();
        return users.stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username))
                .findFirst();
    }

    /**
     * Authenticates a user by username and password.
     */
    public static Optional<User> authenticateUser(String username, String password) {
        Optional<User> userOpt = findUserByUsername(username);
        if (userOpt.isPresent() && userOpt.get().authenticate(password)) {
            // Update last login time
            User user = userOpt.get();
            user.recordLogin();
            
            // Save updated user data
            List<User> users = loadUsers();
            users.stream()
                    .filter(u -> u.getUsername().equalsIgnoreCase(username))
                    .findFirst()
                    .ifPresent(u -> u.setLastLogin(user.getLastLogin()));
            saveUsers(users);
            
            return Optional.of(user);
        }
        return Optional.empty();
    }

    /**
     * Adds a new user.
     */
    public static boolean addUser(User user) {
        List<User> users = loadUsers();
        
        // Check if username already exists
        if (users.stream().anyMatch(u -> u.getUsername().equalsIgnoreCase(user.getUsername()))) {
            return false; // Username already exists
        }
        
        users.add(user);
        return saveUsers(users);
    }

    /**
     * Checks if users file exists and has data.
     */
    public static boolean hasUsers() {
        File file = new File(getFilePath(USERS_FILE));
        return file.exists() && file.length() > 0;
    }

    /**
     * Initializes default users if none exist.
     */
    public static void initializeDefaultUsers() {
        if (!hasUsers()) {
            List<User> defaultUsers = new ArrayList<>();
            
            // User 1: Admin
            User admin = new User(
                "USR001",
                "admin",
                "admin123",
                "System Administrator",
                "Admin",
                "admin@bb.gov.bd",
                "+880-2-9530001",
                "Bangladesh Bank IT Division"
            );
            defaultUsers.add(admin);
            
            // User 2: Commercial Bank Manager
            User bankManager = new User(
                "USR002",
                "bankmanager",
                "bank123",
                "Md. Karim Ahmed",
                "Commercial Bank Manager",
                "karim.ahmed@samplebank.com.bd",
                "+880-1711-123456",
                "Sonali Bank PLC"
            );
            defaultUsers.add(bankManager);
            
            // User 3: Ministry of Finance Representative
            User financeRep = new User(
                "USR003",
                "financerep",
                "finance123",
                "Fatima Rahman",
                "Ministry of Finance Representative",
                "fatima.rahman@mof.gov.bd",
                "+880-1811-654321",
                "Finance Division, Ministry of Finance"
            );
            defaultUsers.add(financeRep);
            
            // User 4: Governor (Shifat's role)
            User governor = new User(
                "USR004",
                "governor",
                "governor123",
                "Dr. Fazle Kabir",
                "Governor",
                "governor@bb.org.bd",
                "+880-2-9530001",
                "Bangladesh Bank - Governor's Office"
            );
            defaultUsers.add(governor);
            
            // User 5: Director of Banking Regulation (Shifat's role)
            User dbr = new User(
                "USR005",
                "dbr",
                "dbr123",
                "Md. Shifat Ahmed",
                "Director of Banking Regulation",
                "dbr@bb.org.bd",
                "+880-2-9530002",
                "Bangladesh Bank - Banking Regulation Department"
            );
            defaultUsers.add(dbr);
            
            saveUsers(defaultUsers);
            System.out.println("Default users initialized successfully.");
            System.out.println("════════════════════════════════════════");
            System.out.println("User 1 - ADMIN:");
            System.out.println("  Username: admin");
            System.out.println("  Password: admin123");
            System.out.println("────────────────────────────────────────");
            System.out.println("User 2 - Commercial Bank Manager:");
            System.out.println("  Username: bankmanager");
            System.out.println("  Password: bank123");
            System.out.println("────────────────────────────────────────");
            System.out.println("User 3 - Ministry of Finance Rep:");
            System.out.println("  Username: financerep");
            System.out.println("  Password: finance123");
            System.out.println("────────────────────────────────────────");
            System.out.println("User 4 - Governor (Shifat):");
            System.out.println("  Username: governor");
            System.out.println("  Password: governor123");
            System.out.println("────────────────────────────────────────");
            System.out.println("User 5 - Director of Banking Regulation (Shifat):");
            System.out.println("  Username: dbr");
            System.out.println("  Password: dbr123");
            System.out.println("════════════════════════════════════════");
        }
    }

    /**
     * Checks if a file exists.
     */
    public static boolean fileExists(String filename) {
        return new File(getFilePath(filename)).exists();
    }

    /**
     * Deletes a file.
     */
    public static boolean deleteFile(String filename) {
        File file = new File(getFilePath(filename));
        return file.delete();
    }
}

