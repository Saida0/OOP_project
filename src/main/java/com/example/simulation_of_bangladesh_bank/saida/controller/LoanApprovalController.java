package com.example.simulation_of_bangladesh_bank.saida.controller;

import com.example.simulation_of_bangladesh_bank.saida.model.LoanApplication;
import com.example.simulation_of_bangladesh_bank.saida.util.DataManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller for Corporate Loan Approval view.
 */
public class LoanApprovalController implements Initializable {

    @FXML
    private TextField txtApplicationID;

    @FXML
    private TextField txtLoanAmount;

    @FXML
    private ComboBox<String> cmbBusinessType;

    @FXML
    private TextField txtCollateralValue;

    @FXML
    private TextField txtInterestRate;

    @FXML
    private TextField txtLoanTerm;

    @FXML
    private Button btnAssessRisk;

    @FXML
    private Button btnApprove;

    @FXML
    private Button btnReject;

    @FXML
    private Button btnSanction;

    @FXML
    private Label lblRiskScore;

    @FXML
    private Label lblRiskCategory;

    @FXML
    private Label lblLoanStatus;

    @FXML
    private TableView<LoanApplication> tblLoans;

    @FXML
    private TableColumn<LoanApplication, String> colAppID;

    @FXML
    private TableColumn<LoanApplication, String> colAmount;

    @FXML
    private TableColumn<LoanApplication, String> colBusiness;

    @FXML
    private TableColumn<LoanApplication, String> colCollateral;

    @FXML
    private TableColumn<LoanApplication, String> colRisk;

    @FXML
    private TableColumn<LoanApplication, String> colStatus;

    @FXML
    private TableColumn<LoanApplication, String> colDate;

    private ObservableList<LoanApplication> loanApplications = FXCollections.observableArrayList();
    private LoanApplication currentApplication;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize business type options
        cmbBusinessType.setItems(FXCollections.observableArrayList(
            "Manufacturing",
            "Trading",
            "Agriculture",
            "Service Industry",
            "Construction",
            "IT & Technology",
            "Healthcare",
            "Education",
            "Real Estate",
            "Term Loan",
            "Working Capital",
            "Project Finance",
            "Agricultural Loan",
            "Others"
        ));

        // Generate default application ID
        generateNewApplicationID();
        
        // Set default interest rate
        txtInterestRate.setText("9.5");
        
        // Initialize table columns
        setupTableColumns();
        
        // Load data from persistent storage
        loadLoanHistory();
        
        // Add table selection listener to auto-populate form
        setupTableSelectionListener();
    }
    
    /**
     * Loads loan applications from persistent storage.
     */
    private void loadLoanHistory() {
        List<LoanApplication> savedLoans = DataManager.loadFromFile(DataManager.LOANS_FILE);
        
        if (savedLoans.isEmpty()) {
            System.out.println("No previous loan applications found.");
        } else {
            loanApplications.addAll(savedLoans);
            System.out.println("Loaded " + savedLoans.size() + " loan applications from history.");
        }
    }
    
    /**
     * Saves loan applications to persistent storage.
     */
    private void saveLoanHistory() {
        List<LoanApplication> toSave = new java.util.ArrayList<>(loanApplications);
        boolean success = DataManager.saveToFile(DataManager.LOANS_FILE, toSave);
        if (success) {
            System.out.println("Loan applications saved successfully.");
        } else {
            System.err.println("Failed to save loan applications!");
        }
    }
    
    /**
     * Sets up the table selection listener to auto-populate form fields.
     */
    private void setupTableSelectionListener() {
        tblLoans.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateFormFromLoan(newSelection);
            }
        });
    }
    
    /**
     * Populates the form fields from a selected loan application.
     */
    private void populateFormFromLoan(LoanApplication loan) {
        currentApplication = loan;
        
        // Populate basic fields
        String appId = loan.getApplicationId() != null ? loan.getApplicationId() : loan.getApplicationID();
        txtApplicationID.setText(appId != null ? appId : "");
        
        // Amount fields - use requestedAmount if available, otherwise loanAmount
        double amount = loan.getRequestedAmount() > 0 ? loan.getRequestedAmount() : loan.getLoanAmount();
        txtLoanAmount.setText(amount > 0 ? String.format("%.0f", amount) : "");
        
        // Business type - check both loanType and businessType
        String businessType = loan.getLoanType() != null ? loan.getLoanType() : loan.getBusinessType();
        if (businessType != null && !businessType.isEmpty()) {
            cmbBusinessType.setValue(businessType);
        }
        
        // Collateral value
        txtCollateralValue.setText(loan.getCollateralValue() > 0 ? String.format("%.0f", loan.getCollateralValue()) : "");
        
        // Interest rate
        txtInterestRate.setText(loan.getInterestRate() > 0 ? String.format("%.2f", loan.getInterestRate()) : "9.5");
        
        // Loan term
        txtLoanTerm.setText(loan.getTenureMonths() > 0 ? String.valueOf(loan.getTenureMonths()) : "");
        
        // Update status display
        String status = loan.getStatus();
        lblLoanStatus.setText(status != null ? status : "Pending");
        
        // Set status color
        if ("Approved".equals(status)) {
            lblLoanStatus.setStyle("-fx-text-fill: #4caf50; -fx-font-weight: bold;");
        } else if ("Rejected".equals(status)) {
            lblLoanStatus.setStyle("-fx-text-fill: #f44336; -fx-font-weight: bold;");
        } else if ("Under Review".equals(status)) {
            lblLoanStatus.setStyle("-fx-text-fill: #ff9800; -fx-font-weight: bold;");
        } else {
            lblLoanStatus.setStyle("-fx-text-fill: #2196f3; -fx-font-weight: bold;");
        }
        
        // Show borrower info if available
        if (loan.getBorrowerName() != null) {
            showInfo("Selected: " + loan.getBorrowerName() + "\n" +
                    "Purpose: " + (loan.getPurpose() != null ? loan.getPurpose() : "N/A") + "\n" +
                    "Collateral Type: " + (loan.getCollateralType() != null ? loan.getCollateralType() : "N/A"));
        }
    }

    /**
     * Sets up table columns with cell value factories.
     */
    private void setupTableColumns() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        
        colAppID.setCellValueFactory(data -> {
            String id = data.getValue().getApplicationId();
            if (id == null) id = data.getValue().getApplicationID();
            return new SimpleStringProperty(id != null ? id : "N/A");
        });
        
        colAmount.setCellValueFactory(data -> {
            double amount = data.getValue().getRequestedAmount();
            if (amount <= 0) amount = data.getValue().getLoanAmount();
            return new SimpleStringProperty(formatCurrency(amount));
        });
        
        colBusiness.setCellValueFactory(data -> {
            String type = data.getValue().getLoanType();
            if (type == null) type = data.getValue().getBusinessType();
            return new SimpleStringProperty(type != null ? type : "N/A");
        });
        
        colCollateral.setCellValueFactory(data -> 
            new SimpleStringProperty(formatCurrency(data.getValue().getCollateralValue())));
        
        colRisk.setCellValueFactory(data -> {
            String riskAssessment = data.getValue().assessRisk();
            return new SimpleStringProperty(riskAssessment != null ? riskAssessment : "Not Assessed");
        });
        
        colStatus.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().getStatus() != null ? data.getValue().getStatus() : "Pending"));
        
        colDate.setCellValueFactory(data -> {
            LocalDate date = data.getValue().getApplicationDate();
            return new SimpleStringProperty(date != null ? date.format(formatter) : LocalDate.now().format(formatter));
        });
        
        tblLoans.setItems(loanApplications);
    }

    /**
     * Generates a new application ID.
     */
    private void generateNewApplicationID() {
        String id = "LOAN-" + LocalDate.now().getYear() + "-" + 
                    String.format("%03d", loanApplications.size() + 1);
        txtApplicationID.setText(id);
    }

    /**
     * Assesses the risk of the loan application.
     */
    @FXML
    public void assessRisk(ActionEvent event) {
        try {
            if (!validateInputs()) return;

            currentApplication = createLoanApplication();
            int riskScore = currentApplication.assessRiskScore();

            lblRiskScore.setText(String.valueOf(riskScore));
            
            // Determine risk category
            String category;
            String color;
            if (riskScore >= 80) {
                category = "Low Risk";
                color = "#4caf50";
            } else if (riskScore >= 60) {
                category = "Medium Risk";
                color = "#ff9800";
            } else if (riskScore >= 40) {
                category = "High Risk";
                color = "#f44336";
            } else {
                category = "Very High Risk";
                color = "#d32f2f";
            }

            lblRiskCategory.setText(category);
            lblRiskCategory.setStyle("-fx-text-fill: " + color + "; -fx-font-weight: bold;");
            lblRiskScore.setStyle("-fx-text-fill: " + color + "; -fx-font-weight: bold;");

            showInfo("Risk assessment completed. Score: " + riskScore + " (" + category + ")");

        } catch (NumberFormatException e) {
            showError("Please enter valid numeric values");
        }
    }

    /**
     * Approves the loan application.
     */
    @FXML
    public void approveLoan(ActionEvent event) {
        if (currentApplication == null) {
            showError("Please select or assess a loan application first");
            return;
        }

        int riskScore = currentApplication.assessRiskScore();
        if (riskScore < 40) {
            showWarning("Cannot approve loan with very high risk score. Consider rejection.");
            return;
        }

        currentApplication.approveReject(true);
        currentApplication.setStatus("Approved");
        lblLoanStatus.setText("Approved");
        lblLoanStatus.setStyle("-fx-text-fill: #4caf50; -fx-font-weight: bold;");

        // If this is a new application, add it; otherwise just update
        if (!loanApplications.contains(currentApplication)) {
            loanApplications.add(0, currentApplication);
        }
        
        saveLoanHistory(); // Save to persistent storage
        tblLoans.refresh();
        
        showInfo("✅ Loan application approved successfully!\n\n" +
                "Application ID: " + (currentApplication.getApplicationId() != null ? 
                    currentApplication.getApplicationId() : currentApplication.getApplicationID()));
        
        generateNewApplicationID();
        clearForm();
    }

    /**
     * Rejects the loan application.
     */
    @FXML
    public void rejectLoan(ActionEvent event) {
        if (currentApplication == null) {
            showError("Please select a loan application to reject");
            return;
        }

        currentApplication.approveReject(false);
        currentApplication.setStatus("Rejected");
        lblLoanStatus.setText("Rejected");
        lblLoanStatus.setStyle("-fx-text-fill: #f44336; -fx-font-weight: bold;");

        // If this is a new application, add it; otherwise just update
        if (!loanApplications.contains(currentApplication)) {
            loanApplications.add(0, currentApplication);
        }
        
        saveLoanHistory(); // Save to persistent storage
        tblLoans.refresh();
        
        showInfo("Loan application rejected");
        
        generateNewApplicationID();
        clearForm();
    }

    /**
     * Generates a sanction letter for approved loans.
     */
    @FXML
    public void generateSanctionLetter(ActionEvent event) {
        if (currentApplication == null || !"Approved".equals(currentApplication.getStatus())) {
            showError("Please approve a loan application first");
            return;
        }

        String sanctionLetter = currentApplication.generateSanctionLetter();
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Loan Sanction Letter");
        alert.setHeaderText("Sanction Letter Generated");
        
        TextArea textArea = new TextArea(sanctionLetter);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setPrefHeight(400);
        
        alert.getDialogPane().setContent(textArea);
        alert.showAndWait();
    }

    /**
     * Refreshes the table data.
     */
    @FXML
    public void refreshTable(ActionEvent event) {
        tblLoans.refresh();
        showInfo("Table refreshed");
    }

    /**
     * Creates a loan application from form inputs.
     */
    private LoanApplication createLoanApplication() {
        LoanApplication loan = new LoanApplication();
        loan.setApplicationID(txtApplicationID.getText());
        loan.setLoanAmount(parseAmount(txtLoanAmount.getText()));
        loan.setBusinessType(cmbBusinessType.getValue());
        loan.setCollateralValue(parseAmount(txtCollateralValue.getText()));
        loan.setStatus("Pending");
        return loan;
    }

    /**
     * Validates form inputs.
     */
    private boolean validateInputs() {
        if (txtLoanAmount.getText().isEmpty()) {
            showError("Please enter loan amount");
            return false;
        }
        if (cmbBusinessType.getValue() == null) {
            showError("Please select business type");
            return false;
        }
        if (txtCollateralValue.getText().isEmpty()) {
            showError("Please enter collateral value");
            return false;
        }
        return true;
    }

    /**
     * Clears the form fields.
     */
    private void clearForm() {
        txtLoanAmount.clear();
        cmbBusinessType.setValue(null);
        txtCollateralValue.clear();
        txtLoanTerm.clear();
        lblRiskScore.setText("--");
        lblRiskCategory.setText("Not Assessed");
        lblLoanStatus.setText("Pending");
        lblRiskScore.setStyle("-fx-text-fill: #1a237e; -fx-font-weight: bold;");
        lblRiskCategory.setStyle("-fx-text-fill: #ff9800; -fx-font-weight: bold;");
        lblLoanStatus.setStyle("-fx-text-fill: #2196f3; -fx-font-weight: bold;");
        currentApplication = null;
    }

    private double parseAmount(String text) {
        String cleaned = text.replaceAll("[^0-9.]", "");
        return Double.parseDouble(cleaned);
    }

    private String formatCurrency(double amount) {
        if (amount >= 10000000) {
            return String.format("৳%.2fCr", amount / 10000000);
        } else if (amount >= 100000) {
            return String.format("৳%.2fL", amount / 100000);
        } else {
            return String.format("৳%.0f", amount);
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

