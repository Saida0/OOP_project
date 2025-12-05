package com.example.simulation_of_bangladesh_bank.saida.controller;

import com.example.simulation_of_bangladesh_bank.saida.model.LiquidityManagement;
import com.example.simulation_of_bangladesh_bank.saida.util.DataManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller for Liquidity Management view.
 */
public class LiquidityManagementController implements Initializable {

    @FXML
    private Label lblCashInflows;

    @FXML
    private Label lblCashOutflows;

    @FXML
    private Label lblNetPosition;

    @FXML
    private Label lblMinThreshold;

    @FXML
    private TextField txtInflows;

    @FXML
    private TextField txtOutflows;

    @FXML
    private TextField txtThreshold;

    @FXML
    private Button btnForecast;

    @FXML
    private Button btnValidate;

    @FXML
    private Button btnAdjust;

    @FXML
    private HBox alertBox;

    @FXML
    private Label lblAlertIcon;

    @FXML
    private Label lblAlertTitle;

    @FXML
    private Label lblAlertMessage;

    @FXML
    private TableView<LiquidityManagement> tblTransactions;

    @FXML
    private TableColumn<LiquidityManagement, String> colDate;

    @FXML
    private TableColumn<LiquidityManagement, String> colType;

    @FXML
    private TableColumn<LiquidityManagement, String> colInflow;

    @FXML
    private TableColumn<LiquidityManagement, String> colOutflow;

    @FXML
    private TableColumn<LiquidityManagement, String> colNet;

    @FXML
    private TableColumn<LiquidityManagement, String> colStatus;

    private ObservableList<LiquidityManagement> transactions = FXCollections.observableArrayList();
    private LiquidityManagement currentLiquidity;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set default threshold
        txtThreshold.setText("500000000");
        
        // Initialize table columns
        setupTableColumns();
        
        // Load data from persistent storage
        loadLiquidityHistory();
        
        // Enable auto-populate of form when a row is selected
        setupTableSelectionListener();
        
        // Update overview
        updateOverview();
    }
    
    /**
     * Loads liquidity history from persistent storage.
     */
    private void loadLiquidityHistory() {
        List<LiquidityManagement> savedTransactions = DataManager.loadFromFile(DataManager.LIQUIDITY_FILE);
        
        if (savedTransactions.isEmpty()) {
            System.out.println("No previous liquidity history found. Starting fresh.");
        } else {
            transactions.addAll(savedTransactions);
            System.out.println("Loaded " + savedTransactions.size() + " liquidity records from history.");
        }
    }
    
    /**
     * Saves liquidity records to persistent storage.
     */
    private void saveLiquidityHistory() {
        List<LiquidityManagement> toSave = new java.util.ArrayList<>(transactions);
        boolean success = DataManager.saveToFile(DataManager.LIQUIDITY_FILE, toSave);
        if (success) {
            System.out.println("Liquidity records saved successfully.");
        } else {
            System.err.println("Failed to save liquidity records!");
        }
    }

    /**
     * Sets up table columns with cell value factories.
     */
    private void setupTableColumns() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        
        colDate.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().getReportDate() != null
                ? data.getValue().getReportDate().format(formatter)
                : LocalDate.now().format(formatter)));
        
        colType.setCellValueFactory(data -> 
            new SimpleStringProperty("Daily"));
        
        colInflow.setCellValueFactory(data -> 
            new SimpleStringProperty(formatCurrency(data.getValue().getCashInflows())));
        
        colOutflow.setCellValueFactory(data -> 
            new SimpleStringProperty(formatCurrency(data.getValue().getCashOutflows())));
        
        colNet.setCellValueFactory(data -> 
            new SimpleStringProperty(formatCurrency(data.getValue().getNetPosition())));
        
        colStatus.setCellValueFactory(data -> {
            boolean isHealthy = data.getValue().validateCashPosition();
            return new SimpleStringProperty(isHealthy ? "Healthy" : "Alert");
        });
        
        tblTransactions.setItems(transactions);
    }

    /**
     * Sets up table selection listener for auto-populating form fields.
     */
    private void setupTableSelectionListener() {
        tblTransactions.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateFormFromRecord(newSelection);
            }
        });
    }

    /**
     * Populates input fields and alert box from the selected liquidity record.
     */
    private void populateFormFromRecord(LiquidityManagement record) {
        currentLiquidity = record;

        // Populate text fields
        txtInflows.setText(String.format("%.0f", record.getCashInflows()));
        txtOutflows.setText(String.format("%.0f", record.getCashOutflows()));
        txtThreshold.setText(String.format("%.0f", record.getMinThreshold()));

        // Update overview based on the selected record
        double net = record.getNetPosition();
        double threshold = record.getMinThreshold();
        lblCashInflows.setText(formatCurrency(record.getCashInflows()));
        lblCashOutflows.setText(formatCurrency(record.getCashOutflows()));
        lblNetPosition.setText(formatCurrency(net));
        lblMinThreshold.setText(formatCurrency(threshold));

        if (net >= threshold) {
            lblNetPosition.setStyle("-fx-text-fill: #4caf50; -fx-font-weight: bold;");
        } else if (net >= 0) {
            lblNetPosition.setStyle("-fx-text-fill: #ff9800; -fx-font-weight: bold;");
        } else {
            lblNetPosition.setStyle("-fx-text-fill: #f44336; -fx-font-weight: bold;");
        }

        // Update alert box status
        boolean healthy = record.validateCashPosition();
        setAlertStatus(healthy,
                healthy ? "Liquidity Status: Healthy" : "Liquidity Status: Alert",
                healthy
                        ? "Cash position is above minimum threshold"
                        : "Cash position is below minimum threshold! Immediate action required.");
    }


    /**
     * Updates the overview cards.
     */
    private void updateOverview() {
        double totalInflows = transactions.stream()
            .mapToDouble(LiquidityManagement::getCashInflows).sum();
        double totalOutflows = transactions.stream()
            .mapToDouble(LiquidityManagement::getCashOutflows).sum();
        double netPosition = totalInflows - totalOutflows;
        double threshold = parseAmount(txtThreshold.getText());

        lblCashInflows.setText(formatCurrency(totalInflows));
        lblCashOutflows.setText(formatCurrency(totalOutflows));
        lblNetPosition.setText(formatCurrency(netPosition));
        lblMinThreshold.setText(formatCurrency(threshold));

        // Update net position color
        if (netPosition >= threshold) {
            lblNetPosition.setStyle("-fx-text-fill: #4caf50; -fx-font-weight: bold;");
        } else if (netPosition >= 0) {
            lblNetPosition.setStyle("-fx-text-fill: #ff9800; -fx-font-weight: bold;");
        } else {
            lblNetPosition.setStyle("-fx-text-fill: #f44336; -fx-font-weight: bold;");
        }
    }

    /**
     * Forecasts transactions based on inputs.
     */
    @FXML
    public void forecastTransactions(ActionEvent event) {
        try {
            double inflows = parseAmount(txtInflows.getText());
            double outflows = parseAmount(txtOutflows.getText());
            double threshold = parseAmount(txtThreshold.getText());

            if (inflows <= 0 && outflows <= 0) {
                showError("Please enter cash inflows and/or outflows");
                return;
            }

            currentLiquidity = new LiquidityManagement();
            currentLiquidity.setLiquidityId("LIQ-" + System.currentTimeMillis());
            currentLiquidity.setCashInflows(inflows);
            currentLiquidity.setCashOutflows(outflows);
            currentLiquidity.setMinThreshold(threshold);
            currentLiquidity.forecastTransactions();

            transactions.add(0, currentLiquidity);
            saveLiquidityHistory(); // Save to persistent storage
            updateOverview();
            
            showInfo("✅ Transaction forecast completed & saved!\n\n" +
                    "Inflows: " + formatCurrency(inflows) + "\n" +
                    "Outflows: " + formatCurrency(outflows) + "\n" +
                    "Net Position: " + formatCurrency(currentLiquidity.getNetPosition()));

        } catch (NumberFormatException e) {
            showError("Please enter valid numeric values");
        }
    }

    /**
     * Validates the current cash position.
     */
    @FXML
    public void validatePosition(ActionEvent event) {
        if (currentLiquidity == null) {
            showError("Please forecast transactions first");
            return;
        }

        boolean isHealthy = currentLiquidity.validateCashPosition();
        
        if (isHealthy) {
            setAlertStatus(true, "Liquidity Status: Healthy", 
                "Cash position is above minimum threshold");
            showInfo("Cash position validated - Status: Healthy");
        } else {
            setAlertStatus(false, "Liquidity Status: Alert", 
                "Cash position is below minimum threshold! Immediate action required.");
            showWarning("Cash position is below minimum threshold!");
        }
    }

    /**
     * Executes liquidity adjustment.
     */
    @FXML
    public void executeLiquidityAdjustment(ActionEvent event) {
        if (currentLiquidity == null) {
            showError("Please forecast and validate position first");
            return;
        }

        currentLiquidity.executeLiquidityAdjustment();
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Liquidity Adjustment");
        alert.setHeaderText("Execute Liquidity Adjustment?");
        alert.setContentText("This will trigger the following actions:\n" +
            "1. Review interbank borrowing options\n" +
            "2. Adjust repo/reverse repo positions\n" +
            "3. Notify treasury department");

        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            showInfo("Liquidity adjustment executed successfully.\n" +
                    "Treasury department has been notified.");
        }
    }

    /**
     * Refreshes the table.
     */
    @FXML
    public void refreshTable(ActionEvent event) {
        updateOverview();
        tblTransactions.refresh();
        showInfo("Table refreshed");
    }

    /**
     * Updates the alert box status.
     */
    private void setAlertStatus(boolean isHealthy, String title, String message) {
        if (isHealthy) {
            alertBox.setStyle("-fx-background-color: #e8f5e9; -fx-background-radius: 12;");
            lblAlertIcon.setText("✓");
            lblAlertIcon.setStyle("-fx-text-fill: #4caf50;");
            lblAlertTitle.setStyle("-fx-text-fill: #2e7d32; -fx-font-weight: bold;");
            lblAlertMessage.setStyle("-fx-text-fill: #4caf50;");
        } else {
            alertBox.setStyle("-fx-background-color: #ffebee; -fx-background-radius: 12;");
            lblAlertIcon.setText("⚠");
            lblAlertIcon.setStyle("-fx-text-fill: #f44336;");
            lblAlertTitle.setStyle("-fx-text-fill: #c62828; -fx-font-weight: bold;");
            lblAlertMessage.setStyle("-fx-text-fill: #f44336;");
        }
        lblAlertTitle.setText(title);
        lblAlertMessage.setText(message);
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

