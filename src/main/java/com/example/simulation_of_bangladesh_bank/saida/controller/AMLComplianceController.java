package com.example.simulation_of_bangladesh_bank.saida.controller;

import com.example.simulation_of_bangladesh_bank.saida.model.AMLMonitoring;
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
 * Controller for AML Compliance Supervision view.
 */
public class AMLComplianceController implements Initializable {

    @FXML
    private Label lblHighRisk;

    @FXML
    private Label lblMediumRisk;

    @FXML
    private Label lblUnderInvestigation;

    @FXML
    private Label lblResolved;

    @FXML
    private TextField txtTransactionID;

    @FXML
    private ComboBox<String> cmbRiskFlag;

    @FXML
    private DatePicker dpReviewDate;

    @FXML
    private TextArea txtInvestigationNotes;

    @FXML
    private Button btnLoadReports;

    @FXML
    private Button btnValidate;

    @FXML
    private Button btnRecord;

    @FXML
    private Button btnGenerate;

    @FXML
    private TableView<AMLMonitoring> tblAMLCases;

    @FXML
    private TableColumn<AMLMonitoring, String> colTransactionID;

    @FXML
    private TableColumn<AMLMonitoring, String> colRiskFlag;

    @FXML
    private TableColumn<AMLMonitoring, String> colAmount;

    @FXML
    private TableColumn<AMLMonitoring, String> colDate;

    @FXML
    private TableColumn<AMLMonitoring, String> colStatus;

    @FXML
    private TableColumn<AMLMonitoring, String> colNotes;

    private ObservableList<AMLMonitoring> amlCases = FXCollections.observableArrayList();
    private AMLMonitoring currentCase;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize risk flag options
        cmbRiskFlag.setItems(FXCollections.observableArrayList(
            "High",
            "Medium",
            "Low",
            "Under Investigation",
            "Pending Review",
            "Cleared"
        ));

        // Set default date
        dpReviewDate.setValue(LocalDate.now());
        
        // Initialize table columns
        setupTableColumns();
        
        // Load data from persistent storage
        loadAMLHistory();
        
        // Add table selection listener for auto-populate
        setupTableSelectionListener();
        
        // Update statistics
        updateStatistics();
    }
    
    /**
     * Loads AML cases from persistent storage.
     */
    private void loadAMLHistory() {
        List<AMLMonitoring> savedCases = DataManager.loadFromFile(DataManager.AML_CASES_FILE);
        
        if (savedCases.isEmpty()) {
            System.out.println("No previous AML cases found.");
        } else {
            amlCases.addAll(savedCases);
            System.out.println("Loaded " + savedCases.size() + " AML cases from history.");
        }
    }
    
    /**
     * Saves AML cases to persistent storage.
     */
    private void saveAMLHistory() {
        List<AMLMonitoring> toSave = new java.util.ArrayList<>(amlCases);
        boolean success = DataManager.saveToFile(DataManager.AML_CASES_FILE, toSave);
        if (success) {
            System.out.println("AML cases saved successfully.");
        } else {
            System.err.println("Failed to save AML cases!");
        }
    }
    
    /**
     * Sets up table selection listener for auto-populating form fields.
     */
    private void setupTableSelectionListener() {
        tblAMLCases.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateFormFromAML(newSelection);
            }
        });
    }
    
    /**
     * Populates form fields from selected AML case.
     */
    private void populateFormFromAML(AMLMonitoring amlCase) {
        currentCase = amlCase;
        
        // Populate Transaction ID
        String txnId = amlCase.getTransactionId() != null ? amlCase.getTransactionId() : amlCase.getTransactionID();
        txtTransactionID.setText(txnId != null ? txnId : "");
        
        // Populate Risk Flag
        String riskLevel = amlCase.getRiskLevel() != null ? amlCase.getRiskLevel() : amlCase.getRiskFlag();
        if (riskLevel != null) {
            cmbRiskFlag.setValue(riskLevel);
        }
        
        // Populate Review Date
        LocalDate reviewDate = amlCase.getFlaggedDate() != null ? amlCase.getFlaggedDate() : amlCase.getReviewDate();
        if (reviewDate != null) {
            dpReviewDate.setValue(reviewDate);
        }
        
        // Populate Investigation Notes
        String notes = amlCase.getDescription() != null ? amlCase.getDescription() : amlCase.getInvestigationNotes();
        txtInvestigationNotes.setText(notes != null ? notes : "");
        
        // Show case details
        String caseId = amlCase.getCaseId() != null ? amlCase.getCaseId() : "N/A";
        String accountNo = amlCase.getAccountNumber() != null ? amlCase.getAccountNumber() : "N/A";
        String status = amlCase.getStatus() != null ? amlCase.getStatus() : "N/A";
        double amount = amlCase.getTransactionAmount();
        String txnType = amlCase.getTransactionType() != null ? amlCase.getTransactionType() : "N/A";
        
        showInfo("Selected AML Case:\n\n" +
                "Case ID: " + caseId + "\n" +
                "Transaction ID: " + txnId + "\n" +
                "Account: " + accountNo + "\n" +
                "Amount: ৳" + String.format("%,.0f", amount) + "\n" +
                "Type: " + txnType + "\n" +
                "Risk Level: " + riskLevel + "\n" +
                "Status: " + status + "\n" +
                "Description: " + (notes != null ? notes : "N/A"));
    }

    /**
     * Sets up table columns with cell value factories.
     */
    private void setupTableColumns() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        
        colTransactionID.setCellValueFactory(data -> {
            String txnId = data.getValue().getTransactionId();
            if (txnId == null) txnId = data.getValue().getTransactionID();
            return new SimpleStringProperty(txnId != null ? txnId : "N/A");
        });
        
        colRiskFlag.setCellValueFactory(data -> {
            String risk = data.getValue().getRiskLevel();
            if (risk == null) risk = data.getValue().getRiskFlag();
            return new SimpleStringProperty(risk != null ? risk : "N/A");
        });
        
        colAmount.setCellValueFactory(data -> 
            new SimpleStringProperty("৳" + String.format("%,.0f", data.getValue().getTransactionAmount())));
        
        colDate.setCellValueFactory(data -> {
            LocalDate date = data.getValue().getFlaggedDate();
            if (date == null) date = data.getValue().getReviewDate();
            return new SimpleStringProperty(date != null ? date.format(formatter) : "N/A");
        });
        
        colStatus.setCellValueFactory(data -> {
            String status = data.getValue().getStatus();
            if (status != null) return new SimpleStringProperty(status);
            String flag = data.getValue().getRiskLevel();
            if (flag == null) flag = data.getValue().getRiskFlag();
            if ("High".equals(flag)) return new SimpleStringProperty("Pending");
            else if ("Under Investigation".equals(flag)) return new SimpleStringProperty("Investigating");
            else if ("Cleared".equals(flag)) return new SimpleStringProperty("Resolved");
            else return new SimpleStringProperty("Under Review");
        });
        
        colNotes.setCellValueFactory(data -> {
            String desc = data.getValue().getDescription();
            if (desc == null) desc = data.getValue().getInvestigationNotes();
            return new SimpleStringProperty(desc != null ? desc : "");
        });
        
        tblAMLCases.setItems(amlCases);
    }

    /**
     * Updates the statistics cards.
     */
    private void updateStatistics() {
        long highRisk = amlCases.stream()
            .filter(c -> {
                String risk = c.getRiskLevel() != null ? c.getRiskLevel() : c.getRiskFlag();
                return "High".equals(risk) || "High Risk".equals(risk);
            }).count();
        long mediumRisk = amlCases.stream()
            .filter(c -> {
                String risk = c.getRiskLevel() != null ? c.getRiskLevel() : c.getRiskFlag();
                return "Medium".equals(risk) || "Medium Risk".equals(risk);
            }).count();
        long investigating = amlCases.stream()
            .filter(c -> {
                String status = c.getStatus();
                String risk = c.getRiskLevel() != null ? c.getRiskLevel() : c.getRiskFlag();
                return "Under Investigation".equals(status) || "Under Investigation".equals(risk);
            }).count();
        long resolved = amlCases.stream()
            .filter(c -> {
                String status = c.getStatus();
                String risk = c.getRiskLevel() != null ? c.getRiskLevel() : c.getRiskFlag();
                return "Cleared".equals(status) || "Cleared".equals(risk);
            }).count();

        lblHighRisk.setText(String.valueOf(highRisk));
        lblMediumRisk.setText(String.valueOf(mediumRisk));
        lblUnderInvestigation.setText(String.valueOf(investigating));
        lblResolved.setText(String.valueOf(resolved));
    }

    /**
     * Loads suspicious activity reports.
     */
    @FXML
    public void loadSuspiciousReports(ActionEvent event) {
        // Simulate loading new reports
        AMLMonitoring newCase = new AMLMonitoring();
        newCase.loadSuspiciousReports();
        newCase.setTransactionID("TXN-2024-" + String.format("%05d", (int)(Math.random() * 99999)));
        newCase.setRiskFlag("High Risk");
        newCase.setReviewDate(LocalDate.now());
        newCase.setInvestigationNotes("New suspicious activity detected - awaiting review");
        
        amlCases.add(0, newCase);
        updateStatistics();
        
        showInfo("Suspicious activity reports loaded successfully.\n" +
                "New cases added to the review queue.");
    }

    /**
     * Validates AML red flags for the current case.
     */
    @FXML
    public void validateAMLRedFlags(ActionEvent event) {
        String transactionID = txtTransactionID.getText().trim();
        
        if (transactionID.isEmpty()) {
            showError("Please enter a Transaction ID");
            return;
        }

        // Create or find current case
        currentCase = new AMLMonitoring();
        currentCase.setTransactionID(transactionID);
        currentCase.setRiskFlag(cmbRiskFlag.getValue());
        currentCase.setReviewDate(dpReviewDate.getValue());
        
        boolean hasRedFlags = currentCase.validateAMLRedFlags();
        
        if (hasRedFlags) {
            showWarning("⚠ RED FLAGS DETECTED\n\n" +
                    "Transaction ID: " + transactionID + "\n" +
                    "Risk Level: " + cmbRiskFlag.getValue() + "\n\n" +
                    "Recommended Actions:\n" +
                    "1. Escalate to Compliance Officer\n" +
                    "2. Request additional documentation\n" +
                    "3. Consider filing SAR");
        } else {
            showInfo("No significant red flags detected for this transaction.\n" +
                    "Continue standard monitoring procedures.");
        }
    }

    /**
     * Records the investigation details.
     */
    @FXML
    public void recordInvestigation(ActionEvent event) {
        String transactionID = txtTransactionID.getText().trim();
        String riskFlag = cmbRiskFlag.getValue();
        String notes = txtInvestigationNotes.getText().trim();

        if (transactionID.isEmpty()) {
            showError("Please enter a Transaction ID");
            return;
        }
        if (riskFlag == null) {
            showError("Please select a Risk Flag");
            return;
        }
        if (notes.isEmpty()) {
            showError("Please enter investigation notes");
            return;
        }

        // Update existing case or create new one
        if (currentCase == null) {
            currentCase = new AMLMonitoring();
            currentCase.setCaseId("AML-" + System.currentTimeMillis());
        }
        
        currentCase.setTransactionId(transactionID);
        currentCase.setTransactionID(transactionID);
        currentCase.setRiskLevel(riskFlag);
        currentCase.setRiskFlag(riskFlag);
        currentCase.setFlaggedDate(dpReviewDate.getValue());
        currentCase.setReviewDate(dpReviewDate.getValue());
        currentCase.setDescription(notes);
        currentCase.setInvestigationNotes(notes);
        currentCase.setStatus("Under Investigation");
        currentCase.recordInvestigation();

        // Add if new, otherwise just update
        if (!amlCases.contains(currentCase)) {
            amlCases.add(0, currentCase);
        }
        
        // Save to persistent storage
        saveAMLHistory();
        updateStatistics();
        tblAMLCases.refresh();
        
        showInfo("✅ Investigation recorded & saved!\n\n" +
                "Case ID: " + currentCase.getCaseId() + "\n" +
                "Transaction ID: " + transactionID + "\n" +
                "Risk Level: " + riskFlag);
        
        // Clear form
        clearForm();
    }

    /**
     * Generates an AML compliance report.
     */
    @FXML
    public void generateAMLReport(ActionEvent event) {
        if (currentCase == null && amlCases.isEmpty()) {
            showError("No AML cases available for report generation");
            return;
        }

        AMLMonitoring reportCase = currentCase != null ? currentCase : amlCases.get(0);
        String report = reportCase.generateAMLReport();
        
        // Add summary statistics to report
        StringBuilder fullReport = new StringBuilder(report);
        fullReport.append("\n\n=== SUMMARY STATISTICS ===\n");
        fullReport.append("High Risk Cases: ").append(lblHighRisk.getText()).append("\n");
        fullReport.append("Medium Risk Cases: ").append(lblMediumRisk.getText()).append("\n");
        fullReport.append("Under Investigation: ").append(lblUnderInvestigation.getText()).append("\n");
        fullReport.append("Resolved Cases: ").append(lblResolved.getText()).append("\n");
        fullReport.append("Total Cases: ").append(amlCases.size()).append("\n");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("AML Compliance Report");
        alert.setHeaderText("Anti-Money Laundering Report");
        
        TextArea textArea = new TextArea(fullReport.toString());
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setPrefHeight(400);
        
        alert.getDialogPane().setContent(textArea);
        alert.showAndWait();
    }

    /**
     * Refreshes the table.
     */
    @FXML
    public void refreshTable(ActionEvent event) {
        updateStatistics();
        tblAMLCases.refresh();
        showInfo("Table refreshed");
    }

    /**
     * Clears the input form.
     */
    private void clearForm() {
        txtTransactionID.clear();
        cmbRiskFlag.setValue(null);
        dpReviewDate.setValue(LocalDate.now());
        txtInvestigationNotes.clear();
        currentCase = null;
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

