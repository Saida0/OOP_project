package com.example.simulation_of_bangladesh_bank.saida.util;

import com.example.simulation_of_bangladesh_bank.saida.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * UserManagement - Command-line utility for managing users.
 * Run this class directly to add, delete, or modify users.
 * 
 * Usage: Run the main method of this class
 */
public class UserManagement {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║        BANGLADESH BANK - USER MANAGEMENT UTILITY             ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        
        DataManager.ensureDataDirectory();
        
        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1":
                    listAllUsers();
                    break;
                case "2":
                    addNewUser();
                    break;
                case "3":
                    modifyUser();
                    break;
                case "4":
                    deleteUser();
                    break;
                case "5":
                    changePassword();
                    break;
                case "6":
                    resetToDefaults();
                    break;
                case "0":
                    running = false;
                    System.out.println("\nExiting User Management. Goodbye!");
                    break;
                default:
                    System.out.println("\n⚠ Invalid option. Please try again.");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n┌────────────────────────────────────────┐");
        System.out.println("│           USER MANAGEMENT MENU         │");
        System.out.println("├────────────────────────────────────────┤");
        System.out.println("│  1. List All Users                     │");
        System.out.println("│  2. Add New User                       │");
        System.out.println("│  3. Modify User                        │");
        System.out.println("│  4. Delete User                        │");
        System.out.println("│  5. Change Password                    │");
        System.out.println("│  6. Reset to Default Users             │");
        System.out.println("│  0. Exit                               │");
        System.out.println("└────────────────────────────────────────┘");
        System.out.print("Enter your choice: ");
    }

    /**
     * Lists all users in the system.
     */
    public static void listAllUsers() {
        List<User> users = DataManager.loadUsers();
        
        if (users.isEmpty()) {
            System.out.println("\n⚠ No users found in the system.");
            return;
        }
        
        System.out.println("\n╔══════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                              REGISTERED USERS                                    ║");
        System.out.println("╠══════════════════════════════════════════════════════════════════════════════════╣");
        System.out.printf("║ %-6s │ %-15s │ %-20s │ %-35s ║%n", "ID", "Username", "Full Name", "Role");
        System.out.println("╠══════════════════════════════════════════════════════════════════════════════════╣");
        
        for (User user : users) {
            System.out.printf("║ %-6s │ %-15s │ %-20s │ %-35s ║%n",
                    user.getId(),
                    user.getUsername(),
                    truncate(user.getFullName(), 20),
                    truncate(user.getRole(), 35));
        }
        System.out.println("╚══════════════════════════════════════════════════════════════════════════════════╝");
        System.out.println("Total Users: " + users.size());
    }

    /**
     * Adds a new user to the system.
     */
    public static void addNewUser() {
        System.out.println("\n═══════════ ADD NEW USER ═══════════");
        
        List<User> users = DataManager.loadUsers();
        
        // Generate new ID
        String newId = "USR" + String.format("%03d", users.size() + 1);
        System.out.println("Generated User ID: " + newId);
        
        System.out.print("Enter Username: ");
        String username = scanner.nextLine().trim();
        
        // Check if username exists
        if (users.stream().anyMatch(u -> u.getUsername().equalsIgnoreCase(username))) {
            System.out.println("⚠ Username already exists!");
            return;
        }
        
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        
        System.out.print("Enter Full Name: ");
        String fullName = scanner.nextLine().trim();
        
        System.out.println("\nSelect Role:");
        System.out.println("  1. Commercial Bank Manager");
        System.out.println("  2. Ministry of Finance Representative");
        System.out.print("Enter choice (1 or 2): ");
        String roleChoice = scanner.nextLine().trim();
        
        String role;
        if ("1".equals(roleChoice)) {
            role = "Commercial Bank Manager";
        } else if ("2".equals(roleChoice)) {
            role = "Ministry of Finance Representative";
        } else {
            System.out.println("⚠ Invalid role selection!");
            return;
        }
        
        System.out.print("Enter Email: ");
        String email = scanner.nextLine().trim();
        
        System.out.print("Enter Phone: ");
        String phone = scanner.nextLine().trim();
        
        System.out.print("Enter Department/Organization: ");
        String department = scanner.nextLine().trim();
        
        // Create and add user
        User newUser = new User(newId, username, password, fullName, role, email, phone, department);
        users.add(newUser);
        
        if (DataManager.saveUsers(users)) {
            System.out.println("\n✓ User added successfully!");
            System.out.println("  Username: " + username);
            System.out.println("  Password: " + password);
            System.out.println("  Role: " + role);
        } else {
            System.out.println("\n✗ Failed to save user!");
        }
    }

    /**
     * Modifies an existing user.
     */
    public static void modifyUser() {
        System.out.println("\n═══════════ MODIFY USER ═══════════");
        listAllUsers();
        
        System.out.print("\nEnter Username to modify: ");
        String username = scanner.nextLine().trim();
        
        List<User> users = DataManager.loadUsers();
        Optional<User> userOpt = users.stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username))
                .findFirst();
        
        if (userOpt.isEmpty()) {
            System.out.println("⚠ User not found!");
            return;
        }
        
        User user = userOpt.get();
        System.out.println("\nCurrent Details:");
        System.out.println("  Full Name: " + user.getFullName());
        System.out.println("  Email: " + user.getEmail());
        System.out.println("  Phone: " + user.getPhone());
        System.out.println("  Department: " + user.getDepartment());
        System.out.println("  Role: " + user.getRole());
        
        System.out.println("\n(Press Enter to keep current value)");
        
        System.out.print("New Full Name [" + user.getFullName() + "]: ");
        String newName = scanner.nextLine().trim();
        if (!newName.isEmpty()) user.setFullName(newName);
        
        System.out.print("New Email [" + user.getEmail() + "]: ");
        String newEmail = scanner.nextLine().trim();
        if (!newEmail.isEmpty()) user.setEmail(newEmail);
        
        System.out.print("New Phone [" + user.getPhone() + "]: ");
        String newPhone = scanner.nextLine().trim();
        if (!newPhone.isEmpty()) user.setPhone(newPhone);
        
        System.out.print("New Department [" + user.getDepartment() + "]: ");
        String newDept = scanner.nextLine().trim();
        if (!newDept.isEmpty()) user.setDepartment(newDept);
        
        if (DataManager.saveUsers(users)) {
            System.out.println("\n✓ User modified successfully!");
        } else {
            System.out.println("\n✗ Failed to save changes!");
        }
    }

    /**
     * Deletes a user from the system.
     */
    public static void deleteUser() {
        System.out.println("\n═══════════ DELETE USER ═══════════");
        listAllUsers();
        
        System.out.print("\nEnter Username to delete: ");
        String username = scanner.nextLine().trim();
        
        List<User> users = DataManager.loadUsers();
        
        Optional<User> userOpt = users.stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username))
                .findFirst();
        
        if (userOpt.isEmpty()) {
            System.out.println("⚠ User not found!");
            return;
        }
        
        User user = userOpt.get();
        System.out.println("\nUser to delete:");
        System.out.println("  Username: " + user.getUsername());
        System.out.println("  Full Name: " + user.getFullName());
        System.out.println("  Role: " + user.getRole());
        
        System.out.print("\nAre you sure you want to delete this user? (yes/no): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        
        if ("yes".equals(confirm)) {
            users.remove(user);
            if (DataManager.saveUsers(users)) {
                System.out.println("\n✓ User deleted successfully!");
            } else {
                System.out.println("\n✗ Failed to delete user!");
            }
        } else {
            System.out.println("\nDeletion cancelled.");
        }
    }

    /**
     * Changes a user's password.
     */
    public static void changePassword() {
        System.out.println("\n═══════════ CHANGE PASSWORD ═══════════");
        
        System.out.print("Enter Username: ");
        String username = scanner.nextLine().trim();
        
        List<User> users = DataManager.loadUsers();
        Optional<User> userOpt = users.stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username))
                .findFirst();
        
        if (userOpt.isEmpty()) {
            System.out.println("⚠ User not found!");
            return;
        }
        
        User user = userOpt.get();
        
        System.out.print("Enter Current Password: ");
        String currentPassword = scanner.nextLine();
        
        if (!user.authenticate(currentPassword)) {
            System.out.println("⚠ Current password is incorrect!");
            return;
        }
        
        System.out.print("Enter New Password: ");
        String newPassword = scanner.nextLine();
        
        System.out.print("Confirm New Password: ");
        String confirmPassword = scanner.nextLine();
        
        if (!newPassword.equals(confirmPassword)) {
            System.out.println("⚠ Passwords do not match!");
            return;
        }
        
        user.setPassword(newPassword);
        
        if (DataManager.saveUsers(users)) {
            System.out.println("\n✓ Password changed successfully!");
        } else {
            System.out.println("\n✗ Failed to change password!");
        }
    }

    /**
     * Resets to default users (deletes all and recreates defaults).
     */
    public static void resetToDefaults() {
        System.out.println("\n═══════════ RESET TO DEFAULTS ═══════════");
        System.out.println("⚠ WARNING: This will delete ALL existing users and create default users.");
        System.out.print("Are you sure? (yes/no): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        
        if ("yes".equals(confirm)) {
            // Delete existing file
            DataManager.deleteFile(DataManager.USERS_FILE);
            
            // Reinitialize defaults
            DataManager.initializeDefaultUsers();
            
            System.out.println("\n✓ Reset complete! Default users restored.");
            listAllUsers();
        } else {
            System.out.println("\nReset cancelled.");
        }
    }

    /**
     * Programmatic method to add a user (can be called from other classes).
     */
    public static boolean addUser(String username, String password, String fullName, 
                                   String role, String email, String phone, String department) {
        List<User> users = DataManager.loadUsers();
        
        // Check if username exists
        if (users.stream().anyMatch(u -> u.getUsername().equalsIgnoreCase(username))) {
            return false;
        }
        
        String newId = "USR" + String.format("%03d", users.size() + 1);
        User newUser = new User(newId, username, password, fullName, role, email, phone, department);
        users.add(newUser);
        
        return DataManager.saveUsers(users);
    }

    /**
     * Programmatic method to delete a user.
     */
    public static boolean deleteUser(String username) {
        List<User> users = DataManager.loadUsers();
        boolean removed = users.removeIf(u -> u.getUsername().equalsIgnoreCase(username));
        
        if (removed) {
            return DataManager.saveUsers(users);
        }
        return false;
    }

    /**
     * Programmatic method to update password.
     */
    public static boolean updatePassword(String username, String oldPassword, String newPassword) {
        List<User> users = DataManager.loadUsers();
        Optional<User> userOpt = users.stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username))
                .findFirst();
        
        if (userOpt.isPresent() && userOpt.get().authenticate(oldPassword)) {
            userOpt.get().setPassword(newPassword);
            return DataManager.saveUsers(users);
        }
        return false;
    }

    private static String truncate(String str, int length) {
        if (str == null) return "";
        return str.length() > length ? str.substring(0, length - 3) + "..." : str;
    }
}

