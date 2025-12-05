package com.example.simulation_of_bangladesh_bank.saida.controller;

import com.example.simulation_of_bangladesh_bank.saida.model.User;
import com.example.simulation_of_bangladesh_bank.saida.util.DataManager;
import com.example.simulation_of_bangladesh_bank.saida.util.SceneSwitcher;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller for Admin Dashboard - User Management.
 */
public class AdminDashboardController implements Initializable {

    @FXML private Label lblAdminName;
    @FXML private Label lblTotalUsers;
    @FXML private Label lblBankManagers;
    @FXML private Label lblFinanceReps;
    @FXML private Label lblAdmins;
    
    @FXML private TableView<User> tblUsers;
    @FXML private TableColumn<User, String> colId;
    @FXML private TableColumn<User, String> colUsername;
    @FXML private TableColumn<User, String> colFullName;
    @FXML private TableColumn<User, String> colRole;
    @FXML private TableColumn<User, String> colDepartment;
    @FXML private TableColumn<User, String> colStatus;
    
    @FXML private Label lblFormTitle;
    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private TextField txtFullName;
    @FXML private ComboBox<String> cmbRole;
    @FXML private TextField txtEmail;
    @FXML private TextField txtPhone;
    @FXML private TextField txtDepartment;
    @FXML private Button btnSave;
    @FXML private Label lblMessage;
    @FXML private Button btnLogout;

    private ObservableList<User> users = FXCollections.observableArrayList();
    private User editingUser = null;
    private boolean isEditMode = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize role options
        cmbRole.setItems(FXCollections.observableArrayList(
            "Admin",
            "Commercial Bank Manager",
            "Ministry of Finance Representative"
        ));
        
        setupTableColumns();
        refreshUserList(null);
        
        // Add selection listener
        tblUsers.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateFormWithUser(newSelection);
            }
        });
    }

    /**
     * Sets the admin user info.
     */
    public void setUserInfo(String name, String department) {
        lblAdminName.setText(name);
    }

    /**
     * Sets up table columns.
     */
    private void setupTableColumns() {
        colId.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getId()));
        colUsername.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUsername()));
        colFullName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFullName()));
        colRole.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRole()));
        colDepartment.setCellValueFactory(data -> new SimpleStringProperty(
            data.getValue().getDepartment() != null ? data.getValue().getDepartment() : "N/A"));
        colStatus.setCellValueFactory(data -> new SimpleStringProperty(
            data.getValue().isActive() ? "Active" : "Inactive"));
        
        tblUsers.setItems(users);
    }

    /**
     * Refreshes the user list from file.
     */
    @FXML
    public void refreshUserList(ActionEvent event) {
        users.clear();
        List<User> loadedUsers = DataManager.loadUsers();
        users.addAll(loadedUsers);
        updateStats();
        
        if (event != null) {
            showMessage("User list refreshed", false);
        }
    }

    /**
     * Updates the statistics cards.
     */
    private void updateStats() {
        long total = users.size();
        long bankManagers = users.stream().filter(u -> "Commercial Bank Manager".equals(u.getRole())).count();
        long financeReps = users.stream().filter(u -> "Ministry of Finance Representative".equals(u.getRole())).count();
        long admins = users.stream().filter(u -> "Admin".equals(u.getRole())).count();
        
        lblTotalUsers.setText(String.valueOf(total));
        lblBankManagers.setText(String.valueOf(bankManagers));
        lblFinanceReps.setText(String.valueOf(financeReps));
        lblAdmins.setText(String.valueOf(admins));
    }

    /**
     * Handles Add User button.
     */
    @FXML
    public void handleAddUser(ActionEvent event) {
        clearForm(null);
        isEditMode = false;
        editingUser = null;
        lblFormTitle.setText("➕ Add New User");
        btnSave.setText("💾 Save User");
        txtUsername.setDisable(false);
    }

    /**
     * Handles Edit User button.
     */
    @FXML
    public void handleEditUser(ActionEvent event) {
        User selected = tblUsers.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a user to edit.");
            return;
        }
        
        isEditMode = true;
        editingUser = selected;
        lblFormTitle.setText("Edit User: " + selected.getUsername());
        btnSave.setText("Update User");
        txtUsername.setDisable(true); // Can't change username
        populateFormWithUser(selected);
    }

    /**
     * Populates form with user data.
     */
    private void populateFormWithUser(User user) {
        txtUsername.setText(user.getUsername());
        txtPassword.setText(""); // Don't show password
        txtFullName.setText(user.getFullName());
        cmbRole.setValue(user.getRole());
        txtEmail.setText(user.getEmail() != null ? user.getEmail() : "");
        txtPhone.setText(user.getPhone() != null ? user.getPhone() : "");
        txtDepartment.setText(user.getDepartment() != null ? user.getDepartment() : "");
    }

    /**
     * Handles Save/Update User button.
     */
    @FXML
    public void handleSaveUser(ActionEvent event) {
        // Validate inputs
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText();
        String fullName = txtFullName.getText().trim();
        String role = cmbRole.getValue();
        String email = txtEmail.getText().trim();
        String phone = txtPhone.getText().trim();
        String department = txtDepartment.getText().trim();
        
        if (username.isEmpty()) {
            showMessage("Username is required", true);
            return;
        }
        if (!isEditMode && password.isEmpty()) {
            showMessage("Password is required for new users", true);
            return;
        }
        if (fullName.isEmpty()) {
            showMessage("Full name is required", true);
            return;
        }
        if (role == null) {
            showMessage("Please select a role", true);
            return;
        }
        
        List<User> allUsers = DataManager.loadUsers();
        
        if (isEditMode && editingUser != null) {
            // Update existing user
            for (User u : allUsers) {
                if (u.getUsername().equalsIgnoreCase(editingUser.getUsername())) {
                    u.setFullName(fullName);
                    u.setRole(role);
                    u.setEmail(email);
                    u.setPhone(phone);
                    u.setDepartment(department);
                    if (!password.isEmpty()) {
                        u.setPassword(password);
                    }
                    break;
                }
            }
            
            if (DataManager.saveUsers(allUsers)) {
                showMessage("✓ User updated successfully!", false);
                refreshUserList(null);
                clearForm(null);
            } else {
                showMessage("✗ Failed to update user", true);
            }
        } else {
            // Check if username already exists
            if (allUsers.stream().anyMatch(u -> u.getUsername().equalsIgnoreCase(username))) {
                showMessage("Username already exists!", true);
                return;
            }
            
            // Create new user
            String newId = "USR" + String.format("%03d", allUsers.size() + 1);
            User newUser = new User(newId, username, password, fullName, role, email, phone, department);
            allUsers.add(newUser);
            
            if (DataManager.saveUsers(allUsers)) {
                showMessage("✓ User added successfully!", false);
                refreshUserList(null);
                clearForm(null);
            } else {
                showMessage("✗ Failed to add user", true);
            }
        }
    }

    /**
     * Handles Reset Password button.
     */
    @FXML
    public void handleResetPassword(ActionEvent event) {
        User selected = tblUsers.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a user to reset password.");
            return;
        }
        
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Reset Password");
        dialog.setHeaderText("Reset password for: " + selected.getUsername());
        dialog.setContentText("Enter new password:");
        
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent() && !result.get().trim().isEmpty()) {
            String newPassword = result.get().trim();
            
            List<User> allUsers = DataManager.loadUsers();
            for (User u : allUsers) {
                if (u.getUsername().equalsIgnoreCase(selected.getUsername())) {
                    u.setPassword(newPassword);
                    break;
                }
            }
            
            if (DataManager.saveUsers(allUsers)) {
                showAlert(Alert.AlertType.INFORMATION, "Success", 
                    "Password reset successfully for " + selected.getUsername());
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to reset password");
            }
        }
    }

    /**
     * Handles Delete User button.
     */
    @FXML
    public void handleDeleteUser(ActionEvent event) {
        User selected = tblUsers.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a user to delete.");
            return;
        }
        
        // Prevent deleting last admin
        long adminCount = users.stream().filter(u -> "Admin".equals(u.getRole())).count();
        if ("Admin".equals(selected.getRole()) && adminCount <= 1) {
            showAlert(Alert.AlertType.ERROR, "Cannot Delete", 
                "Cannot delete the last admin user. System requires at least one admin.");
            return;
        }
        
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Delete");
        confirm.setHeaderText("Delete user: " + selected.getUsername() + "?");
        confirm.setContentText("This action cannot be undone. Are you sure?");
        
        if (confirm.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            List<User> allUsers = DataManager.loadUsers();
            allUsers.removeIf(u -> u.getUsername().equalsIgnoreCase(selected.getUsername()));
            
            if (DataManager.saveUsers(allUsers)) {
                showMessage("✓ User deleted successfully!", false);
                refreshUserList(null);
                clearForm(null);
            } else {
                showMessage("✗ Failed to delete user", true);
            }
        }
    }

    /**
     * Clears the form.
     */
    @FXML
    public void handleClearForm(ActionEvent event) {
        clearForm(event);
    }

    private void clearForm(ActionEvent event) {
        txtUsername.clear();
        txtPassword.clear();
        txtFullName.clear();
        cmbRole.setValue(null);
        txtEmail.clear();
        txtPhone.clear();
        txtDepartment.clear();
        lblMessage.setText("");
        
        isEditMode = false;
        editingUser = null;
        lblFormTitle.setText("➕ Add New User");
        btnSave.setText("💾 Save User");
        txtUsername.setDisable(false);
        
        tblUsers.getSelectionModel().clearSelection();
    }

    /**
     * Handles Logout button.
     */
    @FXML
    public void handleLogout(ActionEvent event) {
        try {
            SceneSwitcher.navigateToLogin(event);
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to logout: " + e.getMessage());
        }
    }

    private void showMessage(String message, boolean isError) {
        lblMessage.setText(message);
        lblMessage.setStyle(isError ? "-fx-text-fill: #f44336;" : "-fx-text-fill: #4caf50;");
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

