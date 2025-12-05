package com.example.simulation_of_bangladesh_bank.saida.controller;

import com.example.simulation_of_bangladesh_bank.saida.model.CapitalAdequacy;
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
 * Controller for Capital Adequacy Ratio (CAR) Monitoring view.
 */
public class CARMonitoringController implements Initializable {

    @FXML
    private Label lblCurrentCAR;

    @FXML
    private Label lblCARStatus;

    @FXML
    private Label lblRWA;

    @FXML
    private Label lblCapitalBase;

    @FXML
    private ProgressBar progressCAR;

    @FXML
    private TextField txtRWA;

    @FXML
    private TextField txtCapitalBase;

    @FXML
    private DatePicker dpReportDate;

    @FXML
    private Button btnCalculate;

    @FXML
    private Button btnValidateBasel;

    @FXML
    private Button btnGenerate;

    @FXML
    private Button btnSubmit;

    @FXML
    private TableView<CapitalAdequacy> tblCARHistory;

    @FXML
    private TableColumn<CapitalAdequacy, String> colDate;

    @FXML
    private TableColumn<CapitalAdequacy, String> colRWA;

    @FXML
    private TableColumn<CapitalAdequacy, String> colCapital;

    @FXML
    private TableColumn<CapitalAdequacy, String> colCAR;

    @FXML
    private TableColumn<CapitalAdequacy, String> colCompliance;

    @FXML
    private TableColumn<CapitalAdequacy, String> colStatus;

    private ObservableList<CapitalAdequacy> carRecords = FXCollections.observableArrayList();
    private CapitalAdequacy currentCAR;

    private static final double BASEL_III_MINIMUM = 10.0; // 10% minimum CAR
    private static final String CAR_DATA_FILE = "car_data.bin";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set default date
        dpReportDate.setValue(LocalDate.now());
        
        // Initialize table columns
        setupTableColumns();
        
        // Load data from persistent storage
        loadCARHistory();
        
        // Add table selection listener for auto-populate
        setupTableSelectionListener();
        
        // Update overview
        updateOverview();
    }
    
    /**
     * Loads CAR records from persistent storage.
     */
    private void loadCARHistory() {
        List<CapitalAdequacy> savedRecords = DataManager.loadFromFile(CAR_DATA_FILE);
        
        if (savedRecords.isEmpty()) {
            System.out.println("No previous CAR records found.");
        } else {
            carRecords.addAll(savedRecords);
            System.out.println("Loaded " + savedRecords.size() + " CAR records from history.");
        }
    }
    
    /**
     * Saves CAR records to persistent storage.
     */
    private void saveCARHistory() {
        List<CapitalAdequacy> toSave = new java.util.ArrayList<>(carRecords);
        boolean success = DataManager.saveToFile(CAR_DATA_FILE, toSave);
        if (success) {
            System.out.println("CAR records saved successfully.");
        } else {
            System.err.println("Failed to save CAR records!");
        }
    }
    
    /**
     * Sets up table selection listener for auto-populating form fields.
     */
    private void setupTableSelectionListener() {
        tblCARHistory.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateFormFromCAR(newSelection);
            }
        });
    }
    
    /**
     * Populates form fields from selected CAR record.
     */
    private void populateFormFromCAR(CapitalAdequacy car) {
        currentCAR = car;
        
        // Populate Risk Weighted Assets
        txtRWA.setText(String.format("%.0f", car.getRiskWeightedAssets()));
        
        // Populate Capital Base (Tier 1 + Tier 2)
        double capitalBase = car.getCapitalBase();
        if (capitalBase <= 0) {
            capitalBase = car.getTier1Capital() + car.getTier2Capital();
        }
        txtCapitalBase.setText(String.format("%.0f", capitalBase));
        
        // Populate Report Date
        if (car.getReportDate() != null) {
            dpReportDate.setValue(car.getReportDate());
        }
        
        // Update display labels
        lblCurrentCAR.setText(String.format("%.2f%%", car.getCarValue()));
        lblRWA.setText(formatCurrency(car.getRiskWeightedAssets()));
        lblCapitalBase.setText(formatCurrency(capitalBase));
        
        // Update progress bar
        double progress = Math.min(car.getCarValue() / 20.0, 1.0);
        progressCAR.setProgress(progress);
        updateCARStatus(car.getCarValue());
        
        // Show info about the selected record
        String capitalId = car.getCapitalId() != null ? car.getCapitalId() : "N/A";
        showInfo("Selected CAR Record:\n\n" +
                "ID: " + capitalId + "\n" +
                "Date: " + (car.getReportDate() != null ? car.getReportDate() : "N/A") + "\n" +
                "Tier 1 Capital: " + formatCurrency(car.getTier1Capital()) + "\n" +
                "Tier 2 Capital: " + formatCurrency(car.getTier2Capital()) + "\n" +
                "Risk Weighted Assets: " + formatCurrency(car.getRiskWeightedAssets()) + "\n" +
                "CAR: " + String.format("%.2f%%", car.getCarValue()) + "\n" +
                "Status: " + car.getStatus());
    }

    /**
     * Sets up table columns with cell value factories.
     */
    private void setupTableColumns() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        
        colDate.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().getReportDate().format(formatter)));
        
        colRWA.setCellValueFactory(data -> 
            new SimpleStringProperty(formatCurrency(data.getValue().getRiskWeightedAssets())));
        
        colCapital.setCellValueFactory(data -> 
            new SimpleStringProperty(formatCurrency(data.getValue().getCapitalBase())));
        
        colCAR.setCellValueFactory(data -> 
            new SimpleStringProperty(String.format("%.2f%%", data.getValue().getCarValue())));
        
        colCompliance.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().validateBaselIII() ? "Compliant" : "Non-Compliant"));
        
        colStatus.setCellValueFactory(data -> {
            double car = data.getValue().getCarValue();
            if (car >= BASEL_III_MINIMUM + 2) return new SimpleStringProperty("Strong");
            else if (car >= BASEL_III_MINIMUM) return new SimpleStringProperty("Adequate");
            else return new SimpleStringProperty("Below Minimum");
        });
        
        tblCARHistory.setItems(carRecords);
    }


    /**
     * Updates overview cards.
     */
    private void updateOverview() {
        if (!carRecords.isEmpty()) {
            CapitalAdequacy latest = carRecords.get(0);
            lblCurrentCAR.setText(String.format("%.2f%%", latest.getCarValue()));
            lblRWA.setText(formatCurrency(latest.getRiskWeightedAssets()));
            lblCapitalBase.setText(formatCurrency(latest.getCapitalBase()));
            
            // Update progress bar (normalized to 0-1 scale, max 20%)
            double progress = Math.min(latest.getCarValue() / 20.0, 1.0);
            progressCAR.setProgress(progress);
            
            // Update status
            updateCARStatus(latest.getCarValue());
        }
    }

    /**
     * Updates the CAR status display.
     */
    private void updateCARStatus(double carValue) {
        if (carValue >= BASEL_III_MINIMUM + 2) {
            lblCARStatus.setText("Strong");
            lblCARStatus.setStyle("-fx-text-fill: #4caf50;");
            progressCAR.setStyle("-fx-accent: #4caf50;");
        } else if (carValue >= BASEL_III_MINIMUM) {
            lblCARStatus.setText("Adequate");
            lblCARStatus.setStyle("-fx-text-fill: #ff9800;");
            progressCAR.setStyle("-fx-accent: #ff9800;");
        } else {
            lblCARStatus.setText("Below Minimum");
            lblCARStatus.setStyle("-fx-text-fill: #f44336;");
            progressCAR.setStyle("-fx-accent: #f44336;");
        }
    }

    /**
     * Calculates the CAR based on inputs.
     */
    @FXML
    public void calculateCAR(ActionEvent event) {
        try {
            double rwa = parseAmount(txtRWA.getText());
            double capital = parseAmount(txtCapitalBase.getText());
            LocalDate date = dpReportDate.getValue();

            if (rwa <= 0 || capital <= 0) {
                showError("Please enter valid amounts for RWA and Capital Base");
                return;
            }

            currentCAR = new CapitalAdequacy();
            currentCAR.setRiskWeightedAssets(rwa);
            currentCAR.setCapitalBase(capital);
            currentCAR.setReportDate(date);
            currentCAR.calculateCAR();

            // Update display
            lblCurrentCAR.setText(String.format("%.2f%%", currentCAR.getCarValue()));
            lblRWA.setText(formatCurrency(rwa));
            lblCapitalBase.setText(formatCurrency(capital));
            
            double progress = Math.min(currentCAR.getCarValue() / 20.0, 1.0);
            progressCAR.setProgress(progress);
            updateCARStatus(currentCAR.getCarValue());

            showInfo("CAR calculated: " + String.format("%.2f%%", currentCAR.getCarValue()));

        } catch (NumberFormatException e) {
            showError("Please enter valid numeric values");
        }
    }

    /**
     * Validates against Basel III requirements.
     */
    @FXML
    public void validateBaselIII(ActionEvent event) {
        if (currentCAR == null) {
            showError("Please calculate CAR first");
            return;
        }

        boolean isCompliant = currentCAR.validateBaselIII();
        
        if (isCompliant) {
            showInfo("✓ Bank is COMPLIANT with Basel III requirements.\n" +
                    "Current CAR: " + String.format("%.2f%%", currentCAR.getCarValue()) + "\n" +
                    "Minimum Required: " + BASEL_III_MINIMUM + "%");
        } else {
            double shortfall = BASEL_III_MINIMUM - currentCAR.getCarValue();
            showWarning("✗ Bank is NOT COMPLIANT with Basel III requirements.\n" +
                    "Current CAR: " + String.format("%.2f%%", currentCAR.getCarValue()) + "\n" +
                    "Minimum Required: " + BASEL_III_MINIMUM + "%\n" +
                    "Shortfall: " + String.format("%.2f%%", shortfall));
        }
    }

    /**
     * Generates a CAR report.
     */
    @FXML
    public void generateCARReport(ActionEvent event) {
        if (currentCAR == null) {
            showError("Please calculate CAR first");
            return;
        }

        String report = currentCAR.generateCARReport();
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("CAR Report");
        alert.setHeaderText("Capital Adequacy Ratio Report");
        
        TextArea textArea = new TextArea(report);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setPrefHeight(350);
        
        alert.getDialogPane().setContent(textArea);
        alert.showAndWait();
    }

    /**
     * Submits the CAR report to Bangladesh Bank.
     */
    @FXML
    public void submitCARReport(ActionEvent event) {
        if (currentCAR == null) {
            showError("Please calculate and generate CAR report first");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Submit CAR Report");
        confirm.setHeaderText("Submit to Bangladesh Bank?");
        confirm.setContentText("This will submit the CAR report for regulatory review.");

        if (confirm.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            // Generate ID if not present
            if (currentCAR.getCapitalId() == null) {
                currentCAR.setCapitalId("CAR-" + System.currentTimeMillis());
            }
            
            currentCAR.submitCARReport();
            
            // Add to list if new, otherwise just update
            if (!carRecords.contains(currentCAR)) {
                carRecords.add(0, currentCAR);
            }
            
            // Save to persistent storage
            saveCARHistory();
            tblCARHistory.refresh();
            
            showInfo("✅ CAR Report submitted successfully to Bangladesh Bank!\n\n" +
                    "Report ID: " + currentCAR.getCapitalId() + "\n" +
                    "Reference Date: " + currentCAR.getReportDate() + "\n" +
                    "CAR Value: " + String.format("%.2f%%", currentCAR.getCarValue()));
            
            // Clear for next entry
            currentCAR = null;
            txtRWA.clear();
            txtCapitalBase.clear();
            dpReportDate.setValue(LocalDate.now());
        }
    }

    /**
     * Refreshes the table.
     */
    @FXML
    public void refreshTable(ActionEvent event) {
        updateOverview();
        tblCARHistory.refresh();
        showInfo("Table refreshed");
    }

    private double parseAmount(String text) {
        if (text == null || text.isEmpty()) return 0;
        String cleaned = text.replaceAll("[^0-9.]", "");
        return cleaned.isEmpty() ? 0 : Double.parseDouble(cleaned);
    }

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

