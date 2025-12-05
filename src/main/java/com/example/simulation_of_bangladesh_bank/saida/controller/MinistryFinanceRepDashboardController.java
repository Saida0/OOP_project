package com.example.simulation_of_bangladesh_bank.saida.controller;

import com.example.simulation_of_bangladesh_bank.saida.util.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for Ministry of Finance Representative Dashboard.
 * Handles navigation and dashboard functionality.
 */
public class MinistryFinanceRepDashboardController implements Initializable {

    @FXML
    private Button btnDashboard;

    @FXML
    private Button btnRevenue;

    @FXML
    private Button btnExpenditure;

    @FXML
    private Button btnDebt;

    @FXML
    private Button btnCoordination;

    @FXML
    private Button btnAid;

    @FXML
    private Button btnBudget;

    @FXML
    private Button btnCash;

    @FXML
    private Button btnReform;

    @FXML
    private Button btnLogout;

    @FXML
    private Label lblPageTitle;

    @FXML
    private Label lblPageSubtitle;

    @FXML
    private Label lblUserName;

    @FXML
    private Label lblDepartment;

    @FXML
    private ScrollPane contentScrollPane;

    @FXML
    private VBox contentArea;

    @FXML
    private Label lblRevenueTotal;

    @FXML
    private Label lblExpenditureTotal;

    @FXML
    private Label lblDebtTotal;

    @FXML
    private Label lblCashBalance;

    @FXML
    private Label lblAidStatus;

    @FXML
    private Label lblReformCount;

    @FXML
    private Label lblPendingApprovals;

    private String currentUserName;
    private String currentDepartment;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize dashboard with default values
        updateDashboardStats();
        highlightActiveButton(btnDashboard);
    }

    /**
     * Sets user information to display on the dashboard.
     */
    public void setUserInfo(String userName, String department) {
        this.currentUserName = userName;
        this.currentDepartment = department;
        lblUserName.setText(userName);
        lblDepartment.setText(department);
    }

    /**
     * Updates dashboard statistics.
     */
    private void updateDashboardStats() {
        // In a real application, these would come from the database
        lblRevenueTotal.setText("৳450B");
        lblExpenditureTotal.setText("৳380B");
        lblDebtTotal.setText("৳120B");
        lblCashBalance.setText("৳85B");
        lblAidStatus.setText("On Track");
        lblReformCount.setText("8 Active");
        lblPendingApprovals.setText("12 Items");
    }

    /**
     * Shows the main dashboard view.
     */
    @FXML
    public void showDashboard(ActionEvent event) {
        lblPageTitle.setText("Dashboard");
        lblPageSubtitle.setText("Welcome to Ministry of Finance Portal");
        highlightActiveButton(btnDashboard);
        loadDashboardContent();
    }

    /**
     * Shows the Revenue Monitoring view.
     */
    @FXML
    public void showRevenueMonitoring(ActionEvent event) {
        loadContentView("/com/example/simulation_of_bangladesh_bank/saida/fxml/RevenueMonitoringView.fxml", "Revenue Monitoring", "Monitor government revenue collection", btnRevenue);
    }

    /**
     * Shows the Expenditure Control view.
     */
    @FXML
    public void showExpenditureControl(ActionEvent event) {
        loadContentView("/com/example/simulation_of_bangladesh_bank/saida/fxml/ExpenditureControlView.fxml", "Expenditure Control", "Authorize government expenditure", btnExpenditure);
    }

    /**
     * Shows the Public Debt view.
     */
    @FXML
    public void showPublicDebt(ActionEvent event) {
        loadContentView("/com/example/simulation_of_bangladesh_bank/saida/fxml/PublicDebtView.fxml", "Public Debt Management", "Monitor and service public debt", btnDebt);
    }

    /**
     * Shows the Fiscal Coordination view.
     */
    @FXML
    public void showFiscalCoordination(ActionEvent event) {
        loadContentView("/com/example/simulation_of_bangladesh_bank/saida/fxml/FiscalCoordinationView.fxml", "Policy Coordination", "Coordinate fiscal-monetary policy with BB", btnCoordination);
    }

    /**
     * Shows the Foreign Aid view.
     */
    @FXML
    public void showForeignAid(ActionEvent event) {
        loadContentView("/com/example/simulation_of_bangladesh_bank/saida/fxml/ForeignAidView.fxml", "Foreign Aid Monitoring", "Monitor foreign aid utilization", btnAid);
    }

    /**
     * Shows the Budget Management view.
     */
    @FXML
    public void showBudgetManagement(ActionEvent event) {
        loadContentView("/com/example/simulation_of_bangladesh_bank/saida/fxml/BudgetManagementView.fxml", "Budget Management", "Supervise national budget execution", btnBudget);
    }

    /**
     * Shows the Cash Balance view.
     */
    @FXML
    public void showCashBalance(ActionEvent event) {
        loadContentView("/com/example/simulation_of_bangladesh_bank/saida/fxml/CashBalanceView.fxml", "Cash Balance", "Manage government cash balance", btnCash);
    }

    /**
     * Shows the Reform Monitoring view.
     */
    @FXML
    public void showReformMonitoring(ActionEvent event) {
        loadContentView("/com/example/simulation_of_bangladesh_bank/saida/fxml/ReformMonitoringView.fxml", "Reform Programs", "Monitor economic reform programs", btnReform);
    }

    /**
     * Handles user logout.
     */
    @FXML
    public void handleLogout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout Confirmation");
        alert.setHeaderText("Are you sure you want to logout?");
        alert.setContentText("You will be returned to the login screen.");

        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            try {
                SceneSwitcher.navigateToLogin(event);
            } catch (IOException e) {
                showError("Error returning to login: " + e.getMessage());
            }
        }
    }

    /**
     * Loads a content view into the main content area.
     * Uses SceneSwitcher utility for consistency with team coding standards.
     */
    private void loadContentView(String fxmlPath, String title, String subtitle, Button activeButton) {
        Parent content = SceneSwitcher.loadContentIntoScrollPane(contentScrollPane, fxmlPath);
        if (content != null) {
            lblPageTitle.setText(title);
            lblPageSubtitle.setText(subtitle);
            highlightActiveButton(activeButton);
        } else {
            showError("Error loading view: " + fxmlPath);
        }
    }

    /**
     * Loads the default dashboard content.
     */
    private void loadDashboardContent() {
        contentScrollPane.setContent(contentArea);
        updateDashboardStats();
    }

    /**
     * Highlights the active navigation button.
     */
    private void highlightActiveButton(Button activeButton) {
        // Reset all buttons to default style
        String defaultStyle = "-fx-background-color: transparent; -fx-text-fill: #000000; -fx-background-radius: 8; -fx-cursor: hand;";
        String activeStyle = "-fx-background-color: #e3f2fd; -fx-text-fill: #0d47a1; -fx-background-radius: 8; -fx-cursor: hand;";

        btnDashboard.setStyle(defaultStyle);
        btnRevenue.setStyle(defaultStyle);
        btnExpenditure.setStyle(defaultStyle);
        btnDebt.setStyle(defaultStyle);
        btnCoordination.setStyle(defaultStyle);
        btnAid.setStyle(defaultStyle);
        btnBudget.setStyle(defaultStyle);
        btnCash.setStyle(defaultStyle);
        btnReform.setStyle(defaultStyle);

        // Highlight active button
        if (activeButton != null) {
            activeButton.setStyle(activeStyle);
        }
    }


    /**
     * Shows an error alert.
     */
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

