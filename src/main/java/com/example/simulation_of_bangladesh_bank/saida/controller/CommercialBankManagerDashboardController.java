package com.example.simulation_of_bangladesh_bank.saida.controller;

import com.example.simulation_of_bangladesh_bank.saida.model.*;
import com.example.simulation_of_bangladesh_bank.saida.util.DataManager;
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
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller for Commercial Bank Manager Dashboard.
 * Handles navigation and dashboard functionality.
 */
public class CommercialBankManagerDashboardController implements Initializable {

    @FXML
    private Button btnDashboard;

    @FXML
    private Button btnSLR;

    @FXML
    private Button btnLoan;

    @FXML
    private Button btnLiquidity;

    @FXML
    private Button btnStrategy;

    @FXML
    private Button btnCAR;

    @FXML
    private Button btnAML;

    @FXML
    private Button btnInterbank;

    @FXML
    private Button btnBranch;

    @FXML
    private Button btnLogout;

    @FXML
    private Label lblPageTitle;

    @FXML
    private Label lblPageSubtitle;

    @FXML
    private Label lblUserName;

    @FXML
    private Label lblBankName;

    @FXML
    private ScrollPane contentScrollPane;

    @FXML
    private VBox contentArea;

    @FXML
    private Label lblSLRCount;

    @FXML
    private Label lblLoanCount;

    @FXML
    private Label lblCARCount;

    @FXML
    private Label lblAMLCount;

    @FXML
    private Label lblLiquidityStatus;

    @FXML
    private Label lblInterbankCount;

    @FXML
    private Label lblBranchCount;

    private String currentUserName;
    private String currentBankName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize dashboard with default values
        updateDashboardStats();
        highlightActiveButton(btnDashboard);
    }

    /**
     * Sets user information to display on the dashboard.
     */
    public void setUserInfo(String userName, String bankName) {
        this.currentUserName = userName;
        this.currentBankName = bankName;
        lblUserName.setText(userName);
        lblBankName.setText(bankName);
    }

    /**
     * Updates dashboard statistics from persistent data files.
     */
    private void updateDashboardStats() {
        // Load real data from bin files
        List<SLRCompliance> slrRecords = DataManager.loadFromFile(DataManager.SLR_DATA_FILE);
        List<LoanApplication> loanRecords = DataManager.loadFromFile(DataManager.LOANS_FILE);
        List<AMLMonitoring> amlRecords = DataManager.loadFromFile(DataManager.AML_CASES_FILE);
        List<InterbankOperations> interbankRecords = DataManager.loadFromFile(DataManager.INTERBANK_FILE);
        List<BranchPerformance> branchRecords = DataManager.loadFromFile(DataManager.BRANCHES_FILE);
        List<LiquidityManagement> liquidityRecords = DataManager.loadFromFile(DataManager.LIQUIDITY_FILE);
        List<CapitalAdequacy> carRecords = DataManager.loadFromFile("car_data.bin");
        
        // Update dashboard labels with actual counts
        lblSLRCount.setText(String.valueOf(slrRecords.size()));
        lblLoanCount.setText(String.valueOf(loanRecords.size()));
        lblCARCount.setText(String.valueOf(carRecords.size()));
        lblAMLCount.setText(String.valueOf(amlRecords.size()));
        
        // Calculate liquidity status
        if (!liquidityRecords.isEmpty()) {
            LiquidityManagement latest = liquidityRecords.get(0);
            double netPos = latest.getNetPosition();
            double threshold = latest.getMinThreshold();
            lblLiquidityStatus.setText(netPos >= threshold ? "Healthy" : "Alert");
        } else {
            lblLiquidityStatus.setText("No Data");
        }
        
        // Count active interbank operations
        long activeInterbank = interbankRecords.stream()
            .filter(op -> "Active".equals(op.getStatus()))
            .count();
        lblInterbankCount.setText(activeInterbank + " Active");
        
        // Branch count
        lblBranchCount.setText(branchRecords.size() + " Branches");
    }

    /**
     * Shows the main dashboard view.
     */
    @FXML
    public void showDashboard(ActionEvent event) {
        lblPageTitle.setText("Dashboard");
        lblPageSubtitle.setText("Welcome to Commercial Bank Manager Portal");
        highlightActiveButton(btnDashboard);
        loadDashboardContent();
    }

    /**
     * Shows the SLR Compliance view.
     */
    @FXML
    public void showSLRCompliance(ActionEvent event) {
        loadContentView("/com/example/simulation_of_bangladesh_bank/saida/fxml/SLRComplianceView.fxml", "SLR Compliance", "Manage Statutory Liquidity Reserve requirements", btnSLR);
    }

    /**
     * Shows the Loan Approval view.
     */
    @FXML
    public void showLoanApproval(ActionEvent event) {
        loadContentView("/com/example/simulation_of_bangladesh_bank/saida/fxml/LoanApprovalView.fxml", "Loan Approval", "Process corporate loan applications", btnLoan);
    }

    /**
     * Shows the Liquidity Management view.
     */
    @FXML
    public void showLiquidityManagement(ActionEvent event) {
        loadContentView("/com/example/simulation_of_bangladesh_bank/saida/fxml/LiquidityManagementView.fxml", "Liquidity Management", "Monitor daily cash flow and liquidity", btnLiquidity);
    }

    /**
     * Shows the Monetary Strategy view.
     */
    @FXML
    public void showMonetaryStrategy(ActionEvent event) {
        loadContentView("/com/example/simulation_of_bangladesh_bank/saida/fxml/MonetaryStrategyView.fxml", "Monetary Strategy", "Formulate monetary policy strategy", btnStrategy);
    }

    /**
     * Shows the CAR Monitoring view.
     */
    @FXML
    public void showCARMonitoring(ActionEvent event) {
        loadContentView("/com/example/simulation_of_bangladesh_bank/saida/fxml/CARMonitoringView.fxml", "CAR Monitoring", "Monitor Capital Adequacy Ratio", btnCAR);
    }

    /**
     * Shows the AML Compliance view.
     */
    @FXML
    public void showAMLCompliance(ActionEvent event) {
        loadContentView("/com/example/simulation_of_bangladesh_bank/saida/fxml/AMLComplianceView.fxml", "AML Compliance", "Anti-Money Laundering supervision", btnAML);
    }

    /**
     * Shows the Interbank Operations view.
     */
    @FXML
    public void showInterbankOps(ActionEvent event) {
        loadContentView("/com/example/simulation_of_bangladesh_bank/saida/fxml/InterbankOpsView.fxml", "Interbank Operations", "Coordinate interbank borrowing and lending", btnInterbank);
    }

    /**
     * Shows the Branch Performance view.
     */
    @FXML
    public void showBranchPerformance(ActionEvent event) {
        loadContentView("/com/example/simulation_of_bangladesh_bank/saida/fxml/BranchPerformanceView.fxml", "Branch Performance", "Monitor branch performance metrics", btnBranch);
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
        btnSLR.setStyle(defaultStyle);
        btnLoan.setStyle(defaultStyle);
        btnLiquidity.setStyle(defaultStyle);
        btnStrategy.setStyle(defaultStyle);
        btnCAR.setStyle(defaultStyle);
        btnAML.setStyle(defaultStyle);
        btnInterbank.setStyle(defaultStyle);
        btnBranch.setStyle(defaultStyle);

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

