package com.example.simulation_of_bangladesh_bank.saida.controller;

import com.example.simulation_of_bangladesh_bank.saida.model.SLRCompliance;
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
 * Controller for SLR Compliance Management view.
 */
public class SLRComplianceController implements Initializable {

    @FXML
    private TextField txtCurrentDeposits;

    @FXML
    private TextField txtReserveHoldings;

    @FXML
    private TextField txtSLRRate;

    @FXML
    private DatePicker dpComplianceDate;

    @FXML
    private Button btnCalculate;

    @FXML
    private Button btnValidate;

    @FXML
    private Button btnGenerateCert;

    @FXML
    private Label lblRequiredSLR;

    @FXML
    private Label lblActualSLR;

    @FXML
    private Label lblComplianceStatus;

    @FXML
    private TableView<SLRCompliance> tblSLRHistory;

    @FXML
    private TableColumn<SLRCompliance, String> colDate;

    @FXML
    private TableColumn<SLRCompliance, String> colDeposits;

    @FXML
    private TableColumn<SLRCompliance, String> colReserves;

    @FXML
    private TableColumn<SLRCompliance, String> colRequired;

    @FXML
    private TableColumn<SLRCompliance, String> colActual;

    @FXML
    private TableColumn<SLRCompliance, String> colStatus;

    private ObservableList<SLRCompliance> slrRecords = FXCollections.observableArrayList();
    private SLRCompliance currentSLR;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set default date
        dpComplianceDate.setValue(LocalDate.now());
        
        // Set default SLR rate (Bangladesh Bank mandated rate)
        txtSLRRate.setText("13.0");
        
        // Initialize table columns
        setupTableColumns();
        
        // Load data from persistent storage
        loadSLRHistory();
    }
    
    /**
     * Loads SLR compliance history from persistent storage.
     */
    private void loadSLRHistory() {
        List<SLRCompliance> savedRecords = DataManager.loadFromFile(DataManager.SLR_DATA_FILE);
        
        if (savedRecords.isEmpty()) {
            System.out.println("No previous SLR compliance history found. Starting fresh.");
        } else {
            slrRecords.addAll(savedRecords);
            System.out.println("Loaded " + savedRecords.size() + " SLR compliance records from history.");
        }
    }
    
    /**
     * Saves SLR compliance records to persistent storage.
     */
    private void saveSLRHistory() {
        List<SLRCompliance> toSave = new java.util.ArrayList<>(slrRecords);
        boolean success = DataManager.saveToFile(DataManager.SLR_DATA_FILE, toSave);
        if (success) {
            System.out.println("SLR compliance records saved successfully.");
        } else {
            System.err.println("Failed to save SLR compliance records!");
        }
    }

    /**
     * Sets up the table columns with cell value factories.
     */
    private void setupTableColumns() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        
        colDate.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().getComplianceDate().format(formatter)));
        
        colDeposits.setCellValueFactory(data -> 
            new SimpleStringProperty(formatCurrency(data.getValue().getCurrentDeposits())));
        
        colReserves.setCellValueFactory(data -> 
            new SimpleStringProperty(formatCurrency(data.getValue().getReserveHoldings())));
        
        colRequired.setCellValueFactory(data -> 
            new SimpleStringProperty(formatCurrency(data.getValue().getRequiredSLR())));
        
        colActual.setCellValueFactory(data -> {
            double actual = (data.getValue().getReserveHoldings() / data.getValue().getCurrentDeposits()) * 100;
            return new SimpleStringProperty(String.format("%.2f%%", actual));
        });
        
        colStatus.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().validateCompliance() ? "Compliant" : "Non-Compliant"));
        
        tblSLRHistory.setItems(slrRecords);
    }


    /**
     * Calculates the SLR requirement based on input values.
     */
    @FXML
    public void calculateSLR(ActionEvent event) {
        try {
            double deposits = parseAmount(txtCurrentDeposits.getText());
            double reserves = parseAmount(txtReserveHoldings.getText());
            double rate = Double.parseDouble(txtSLRRate.getText());
            LocalDate date = dpComplianceDate.getValue();

            // Validation
            if (deposits <= 0) {
                showError("Please enter a valid deposit amount");
                return;
            }
            if (reserves < 0) {
                showError("Reserve holdings cannot be negative");
                return;
            }
            if (rate <= 0 || rate > 100) {
                showError("SLR rate must be between 0 and 100");
                return;
            }
            
            // CRITICAL: Reserve holdings cannot exceed total deposits!
            // This is a fundamental banking principle
            if (reserves > deposits) {
                showError("❌ Invalid Data!\n\n" +
                        "Reserve Holdings (৳" + String.format("%.2f", reserves) + ") cannot exceed " +
                        "Total Deposits (৳" + String.format("%.2f", deposits) + ").\n\n" +
                        "Reserves are a PORTION of deposits that must be kept liquid.\n" +
                        "Please check your input values.");
                return;
            }
            
            // Warn if values seem unrealistically small for a bank
            if (deposits < 1000000) { // Less than 1 million
                showWarning("⚠️ Low deposit amount detected!\n\n" +
                        "Deposit amount: ৳" + String.format("%.2f", deposits) + "\n\n" +
                        "For realistic banking simulation, deposits are typically in billions.\n" +
                        "Example: 50,000,000,000 (50 Billion BDT)\n\n" +
                        "Continue anyway?");
            }

            // Create SLR compliance object with all values
            currentSLR = new SLRCompliance();
            currentSLR.setSlrPercentage(rate);  // SET THE RATE FIRST! (fixes the bug)
            currentSLR.setCurrentDeposits(deposits);
            currentSLR.setReserveHoldings(reserves);
            currentSLR.setComplianceDate(date);
            currentSLR.calculateSLRRequirement();

            // Update display labels - use values from the model for consistency
            double requiredSLR = currentSLR.getRequiredSLR();
            double actualPercentage = (reserves / deposits) * 100;

            lblRequiredSLR.setText(formatCurrency(requiredSLR));
            lblActualSLR.setText(formatCurrency(reserves) + " (" + String.format("%.2f%%", actualPercentage) + ")");

            // Clear previous compliance status
            lblComplianceStatus.setText("");
            lblComplianceStatus.setStyle("");

            showInfo("SLR calculated successfully. Required: " + formatCurrency(requiredSLR));

        } catch (NumberFormatException e) {
            showError("Please enter valid numeric values");
        }
    }

    /**
     * Validates compliance against SLR requirements.
     */
    @FXML
    public void validateCompliance(ActionEvent event) {
        if (currentSLR == null) {
            showError("Please calculate SLR first");
            return;
        }

        double required = currentSLR.getRequiredSLR();
        double actual = currentSLR.getReserveHoldings();
        boolean isCompliant = currentSLR.validateCompliance();
        
        // Debug info for verification
        System.out.println("=== SLR Validation Debug ===");
        System.out.println("Deposits: " + formatCurrency(currentSLR.getCurrentDeposits()));
        System.out.println("SLR Rate: " + currentSLR.getSlrPercentage() + "%");
        System.out.println("Required SLR: " + formatCurrency(required));
        System.out.println("Actual Holdings: " + formatCurrency(actual));
        System.out.println("Is Compliant: " + isCompliant);
        System.out.println("============================");
        
        if (isCompliant) {
            double surplus = actual - required;
            lblComplianceStatus.setText("✓ COMPLIANT (Surplus: " + formatCurrency(surplus) + ")");
            lblComplianceStatus.setStyle("-fx-text-fill: #4caf50; -fx-font-weight: bold; -fx-font-size: 14px;");
            showInfo("Bank is COMPLIANT with SLR requirements.\n\n" +
                    "Required: " + formatCurrency(required) + "\n" +
                    "Actual: " + formatCurrency(actual) + "\n" +
                    "Surplus: " + formatCurrency(surplus));
        } else {
            double shortfall = required - actual;
            lblComplianceStatus.setText("✗ NON-COMPLIANT (Deficit: " + formatCurrency(shortfall) + ")");
            lblComplianceStatus.setStyle("-fx-text-fill: #f44336; -fx-font-weight: bold; -fx-font-size: 14px;");
            
            showWarning("⚠️ Bank is NOT COMPLIANT with SLR requirements!\n\n" +
                    "Required SLR: " + formatCurrency(required) + "\n" +
                    "Actual Holdings: " + formatCurrency(actual) + "\n" +
                    "SHORTFALL: " + formatCurrency(shortfall) + "\n\n" +
                    "Action Required: Increase reserve holdings by at least " + formatCurrency(shortfall) + 
                    " to meet compliance.");
        }
    }

    /**
     * Generates a compliance certificate.
     */
    @FXML
    public void generateCertificate(ActionEvent event) {
        if (currentSLR == null) {
            showError("Please calculate and validate SLR first");
            return;
        }

        // Generate unique compliance ID
        currentSLR.setComplianceId("SLR-" + System.currentTimeMillis());
        currentSLR.setBankId("Sonali Bank PLC"); // Can be made dynamic
        
        String certificate = currentSLR.generateCertificate();
        
        // Add to history and save to persistent storage
        slrRecords.add(0, currentSLR);
        saveSLRHistory();
        
        // Show certificate in dialog
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("SLR Compliance Certificate");
        alert.setHeaderText("✅ Certificate Generated & Saved!");
        
        TextArea textArea = new TextArea(certificate);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setPrefHeight(300);
        
        alert.getDialogPane().setContent(textArea);
        alert.showAndWait();
        
        // Reset current SLR for next entry
        currentSLR = null;
    }

    /**
     * Refreshes the table data.
     */
    @FXML
    public void refreshTable(ActionEvent event) {
        tblSLRHistory.refresh();
        showInfo("Table refreshed");
    }

    /**
     * Parses amount string removing commas and currency symbols.
     */
    private double parseAmount(String text) {
        String cleaned = text.replaceAll("[^0-9.]", "");
        return Double.parseDouble(cleaned);
    }

    /**
     * Formats a number as currency.
     */
    private String formatCurrency(double amount) {
        if (amount >= 1000000000) {
            return String.format("৳%.2fB", amount / 1000000000);
        } else if (amount >= 1000000) {
            return String.format("৳%.2fM", amount / 1000000);
        } else {
            return String.format("৳%.2f", amount);
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

