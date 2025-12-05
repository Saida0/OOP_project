package com.example.simulation_of_bangladesh_bank.saida.controller;

import com.example.simulation_of_bangladesh_bank.saida.model.User;
import com.example.simulation_of_bangladesh_bank.saida.util.DataManager;
import com.example.simulation_of_bangladesh_bank.saida.util.SceneSwitcher;
import javafx.collections.FXCollections;
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
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller for the Login view.
 * Handles user authentication and navigation to appropriate dashboards.
 */
public class LoginController implements Initializable {

    @FXML
    private ComboBox<String> userTypeComboBox;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button loginButton;

    private User authenticatedUser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize user type options (including all team members' roles)
        userTypeComboBox.setItems(FXCollections.observableArrayList(
            "Admin",
            "Commercial Bank Manager",
            "Ministry of Finance Representative",
            "Governor",
            "Director of Banking Regulation"
        ));
        
        // Clear error label initially
        errorLabel.setText("");
    }

    /**
     * Handles the login button click event.
     * Validates credentials and navigates to the appropriate dashboard.
     */
    @FXML
    public void handleLogin(ActionEvent event) {
        String userType = userTypeComboBox.getValue();
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        // Validate inputs
        if (userType == null || userType.isEmpty()) {
            showError("Please select a user type");
            return;
        }

        if (username.isEmpty()) {
            showError("Please enter your username");
            return;
        }

        if (password.isEmpty()) {
            showError("Please enter your password");
            return;
        }

        // Authenticate against binary file
        Optional<User> userOpt = DataManager.authenticateUser(username, password);
        
        if (userOpt.isPresent()) {
            authenticatedUser = userOpt.get();
            
            // Verify user role matches selected type
            if (!authenticatedUser.getRole().equals(userType)) {
                showError("User role does not match selected type.\nYour role: " + authenticatedUser.getRole());
                return;
            }
            
            navigateToDashboard(userType, authenticatedUser);
        } else {
            showError("Invalid username or password");
        }
    }

    /**
     * Navigates to the appropriate dashboard based on user type.
     */
    private void navigateToDashboard(String userType, User user) {
        try {
            String fxmlPath;
            switch (userType) {
                case "Admin":
                    fxmlPath = "/com/example/simulation_of_bangladesh_bank/saida/fxml/AdminDashboard.fxml";
                    break;
                case "Commercial Bank Manager":
                    fxmlPath = "/com/example/simulation_of_bangladesh_bank/saida/fxml/CommercialBankManagerDashboard.fxml";
                    break;
                case "Ministry of Finance Representative":
                    fxmlPath = "/com/example/simulation_of_bangladesh_bank/saida/fxml/MinistryFinanceRepDashboard.fxml";
                    break;
                case "Governor":
                    fxmlPath = "/com/example/simulation_of_bangladesh_bank/shifat/GovernorDash.fxml";
                    break;
                case "Director of Banking Regulation":
                    fxmlPath = "/com/example/simulation_of_bangladesh_bank/shifat/DirectorofBankingRegulation.fxml";
                    break;
                default:
                    fxmlPath = "/com/example/simulation_of_bangladesh_bank/saida/fxml/MinistryFinanceRepDashboard.fxml";
                    break;
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Pass user info to dashboard controller (for Saida's dashboards)
            Object controller = loader.getController();
            if (controller instanceof AdminDashboardController) {
                ((AdminDashboardController) controller).setUserInfo(
                    user.getFullName(), user.getDepartment());
            } else if (controller instanceof CommercialBankManagerDashboardController) {
                ((CommercialBankManagerDashboardController) controller).setUserInfo(
                    user.getFullName(), user.getDepartment());
            } else if (controller instanceof MinistryFinanceRepDashboardController) {
                ((MinistryFinanceRepDashboardController) controller).setUserInfo(
                    user.getFullName(), user.getDepartment());
            }
            // Note: Shifat's controllers don't have setUserInfo method, so we skip them

            // Get current stage and set new scene
            Stage stage = (Stage) loginButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Bangladesh Bank - " + userType + " (" + user.getFullName() + ")");
            stage.setMaximized(true);
            stage.show();

        } catch (IOException e) {
            showError("Error loading dashboard: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Displays an error message to the user.
     */
    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setStyle("-fx-text-fill: #d32f2f;");
    }

    /**
     * Clears all input fields.
     */
    public void clearFields() {
        userTypeComboBox.setValue(null);
        usernameField.clear();
        passwordField.clear();
        errorLabel.setText("");
    }
}

