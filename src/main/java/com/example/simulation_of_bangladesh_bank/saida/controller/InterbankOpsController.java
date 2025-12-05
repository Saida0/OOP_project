package com.example.simulation_of_bangladesh_bank.saida.controller;

import com.example.simulation_of_bangladesh_bank.saida.model.InterbankOperations;
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
 * Controller for Interbank Operations view.
 */
public class InterbankOpsController implements Initializable {

    @FXML
    private Label lblTotalBorrowed;

    @FXML
    private Label lblTotalLent;

    @FXML
    private Label lblNetPosition;

    @FXML
    private Label lblAvgRate;

    @FXML
    private ComboBox<String> cmbTransactionType;

    @FXML
    private ComboBox<String> cmbCounterparty;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtInterestRate;

    @FXML
    private TextField txtDuration;

    @FXML
    private Label lblCreditStatus;

    @FXML
    private Button btnValidateCredit;

    @FXML
    private Button btnBorrow;

    @FXML
    private Button btnLend;

    @FXML
    private Button btnUpdateRecord;

    @FXML
    private TableView<InterbankOperations> tblTransactions;

    @FXML
    private TableColumn<InterbankOperations, String> colDate;

    @FXML
    private TableColumn<InterbankOperations, String> colType;

    @FXML
    private TableColumn<InterbankOperations, String> colCounterparty;

    @FXML
    private TableColumn<InterbankOperations, String> colAmount;

    @FXML
    private TableColumn<InterbankOperations, String> colRate;

    @FXML
    private TableColumn<InterbankOperations, String> colDuration;

    @FXML
    private TableColumn<InterbankOperations, String> colStatus;

    private ObservableList<InterbankOperations> transactions = FXCollections.observableArrayList();
    private InterbankOperations currentTransaction;
    private boolean creditValidated = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize transaction type options
        cmbTransactionType.setItems(FXCollections.observableArrayList(
            "Borrow",
            "Lend"
        ));

        // Initialize counterparty banks
        cmbCounterparty.setItems(FXCollections.observableArrayList(
            "Sonali Bank Ltd.",
            "Janata Bank Ltd.",
            "Agrani Bank Ltd.",
            "Rupali Bank Ltd.",
            "BASIC Bank Ltd.",
            "Bangladesh Development Bank Ltd.",
            "Dutch Bangla Bank Ltd.",
            "BRAC Bank Ltd.",
            "Eastern Bank Ltd.",
            "Prime Bank Ltd."
        ));

        // Set default interest rate
        txtInterestRate.setText("6.5");
        txtDuration.setText("7");
        
        // Initialize table columns
        setupTableColumns();
        
        // Load data from persistent storage (or initialize with realistic startup data)
        loadTransactionHistory();
        
        // Update overview
        updateOverview();
    }
    
    /**
     * Loads transaction history from persistent storage.
     * If no history exists, initializes with realistic startup scenario.
     */
    private void loadTransactionHistory() {
        List<InterbankOperations> savedTransactions = DataManager.loadFromFile(DataManager.INTERBANK_FILE);
        
        if (savedTransactions.isEmpty()) {
            // No saved data - this is a fresh start, no dummy data
            System.out.println("No previous interbank transaction history found. Starting fresh.");
        } else {
            transactions.addAll(savedTransactions);
            System.out.println("Loaded " + savedTransactions.size() + " interbank transactions from history.");
        }
    }
    
    /**
     * Saves current transactions to persistent storage.
     */
    private void saveTransactionHistory() {
        List<InterbankOperations> toSave = new java.util.ArrayList<>(transactions);
        boolean success = DataManager.saveToFile(DataManager.INTERBANK_FILE, toSave);
        if (success) {
            System.out.println("Interbank transactions saved successfully.");
        } else {
            System.err.println("Failed to save interbank transactions!");
        }
    }

    /**
     * Sets up table columns.
     */
    private void setupTableColumns() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        
        colDate.setCellValueFactory(data -> {
            LocalDate txnDate = data.getValue().getTransactionDate();
            return new SimpleStringProperty(txnDate != null ? txnDate.format(formatter) : "N/A");
        });
        
        colType.setCellValueFactory(data -> {
            if (data.getValue().getBorrowAmount() > 0) return new SimpleStringProperty("Borrow");
            else return new SimpleStringProperty("Lend");
        });
        
        colCounterparty.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().getCounterpartyBank()));
        
        colAmount.setCellValueFactory(data -> {
            double amount = Math.max(data.getValue().getBorrowAmount(), data.getValue().getLendingAmount());
            return new SimpleStringProperty(formatCurrency(amount));
        });
        
        colRate.setCellValueFactory(data -> 
            new SimpleStringProperty(String.format("%.2f%%", data.getValue().getInterestRate())));
        
        colDuration.setCellValueFactory(data -> {
            LocalDate txnDate = data.getValue().getTransactionDate();
            LocalDate matDate = data.getValue().getMaturityDate();
            if (txnDate != null && matDate != null) {
                long days = java.time.temporal.ChronoUnit.DAYS.between(txnDate, matDate);
                return new SimpleStringProperty(days + " days");
            }
            return new SimpleStringProperty("N/A");
        });
        
        colStatus.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().getStatus() != null ? data.getValue().getStatus() : "Active"));
        
        tblTransactions.setItems(transactions);
    }


    /**
     * Updates overview statistics.
     */
    private void updateOverview() {
        double totalBorrowed = transactions.stream()
            .mapToDouble(InterbankOperations::getBorrowAmount).sum();
        double totalLent = transactions.stream()
            .mapToDouble(InterbankOperations::getLendingAmount).sum();
        double netPosition = totalLent - totalBorrowed;
        double avgRate = transactions.stream()
            .mapToDouble(InterbankOperations::getInterestRate).average().orElse(0);

        lblTotalBorrowed.setText(formatCurrency(totalBorrowed));
        lblTotalLent.setText(formatCurrency(totalLent));
        lblNetPosition.setText(formatCurrency(netPosition));
        lblAvgRate.setText(String.format("%.2f%%", avgRate));

        // Update net position color
        if (netPosition > 0) {
            lblNetPosition.setStyle("-fx-text-fill: #4caf50; -fx-font-weight: bold;");
        } else if (netPosition < 0) {
            lblNetPosition.setStyle("-fx-text-fill: #f44336; -fx-font-weight: bold;");
        } else {
            lblNetPosition.setStyle("-fx-text-fill: #2196f3; -fx-font-weight: bold;");
        }
    }

    /**
     * Gets the credit limit based on bank tier.
     * Tier 1 (Major State Banks): 5 Billion
     * Tier 2 (Large Private Banks): 2 Billion  
     * Tier 3 (Other Banks): 500 Million
     */
    private double getCreditLimitForBank(String bankName) {
        // Tier 1 - Major state-owned banks
        if (bankName.contains("Sonali") || bankName.contains("Janata") || 
            bankName.contains("Agrani") || bankName.contains("Rupali")) {
            return InterbankOperations.TIER_1_CREDIT_LIMIT; // 5 Billion
        }
        // Tier 2 - Large private banks
        else if (bankName.contains("Dutch Bangla") || bankName.contains("BRAC") || 
                 bankName.contains("Eastern") || bankName.contains("Prime")) {
            return InterbankOperations.TIER_2_CREDIT_LIMIT; // 2 Billion
        }
        // Tier 3 - Other banks
        else {
            return InterbankOperations.TIER_3_CREDIT_LIMIT; // 500 Million
        }
    }

    /**
     * Validates credit limit for the counterparty.
     */
    @FXML
    public void validateCreditLimit(ActionEvent event) {
        if (cmbCounterparty.getValue() == null) {
            showError("Please select a counterparty bank");
            return;
        }
        if (txtAmount.getText().isEmpty()) {
            showError("Please enter transaction amount");
            return;
        }

        try {
            double requestedAmount = parseAmount(txtAmount.getText());
            String counterparty = cmbCounterparty.getValue();
            double creditLimit = getCreditLimitForBank(counterparty);
            
            // Calculate current exposure to this counterparty from existing transactions
            double existingExposure = transactions.stream()
                .filter(t -> counterparty.equals(t.getCounterpartyBank()))
                .mapToDouble(t -> t.getBorrowAmount() + t.getLendingAmount())
                .sum();
            
            currentTransaction = new InterbankOperations();
            currentTransaction.setCounterpartyBank(counterparty);
            currentTransaction.setCreditLimit(creditLimit);
            currentTransaction.setCurrentExposure(existingExposure);
            
            boolean isValid = (existingExposure + requestedAmount) <= creditLimit;
            creditValidated = isValid;
            
            if (isValid) {
                lblCreditStatus.setText("✓ Credit Limit OK");
                lblCreditStatus.setStyle("-fx-text-fill: #4caf50; -fx-font-weight: bold;");
                showInfo("Credit limit validated successfully!\n\n" +
                        "Counterparty: " + counterparty + "\n" +
                        "Credit Limit: " + formatCurrency(creditLimit) + "\n" +
                        "Existing Exposure: " + formatCurrency(existingExposure) + "\n" +
                        "Requested Amount: " + formatCurrency(requestedAmount) + "\n" +
                        "Available: " + formatCurrency(creditLimit - existingExposure) + "\n\n" +
                        "✓ Transaction can proceed.");
            } else {
                lblCreditStatus.setText("✗ Exceeds Limit");
                lblCreditStatus.setStyle("-fx-text-fill: #f44336; -fx-font-weight: bold;");
                showWarning("Transaction exceeds credit limit!\n\n" +
                        "Counterparty: " + counterparty + "\n" +
                        "Credit Limit: " + formatCurrency(creditLimit) + "\n" +
                        "Existing Exposure: " + formatCurrency(existingExposure) + "\n" +
                        "Requested Amount: " + formatCurrency(requestedAmount) + "\n" +
                        "Available: " + formatCurrency(creditLimit - existingExposure) + "\n\n" +
                        "Please reduce amount or choose another counterparty.");
            }
        } catch (NumberFormatException e) {
            showError("Please enter a valid numeric amount");
        }
    }

    /**
     * Executes a borrow transaction.
     */
    @FXML
    public void executeBorrow(ActionEvent event) {
        if (!validateTransaction("Borrow")) return;

        try {
            double amount = parseAmount(txtAmount.getText());
            double rate = Double.parseDouble(txtInterestRate.getText());
            int duration = Integer.parseInt(txtDuration.getText());
            
            InterbankOperations borrowOp = new InterbankOperations();
            borrowOp.setOperationId("IB-BRW-" + System.currentTimeMillis());
            borrowOp.setBorrowAmount(amount);
            borrowOp.setLendingAmount(0);
            borrowOp.setInterestRate(rate);
            borrowOp.setCounterpartyBank(cmbCounterparty.getValue());
            borrowOp.setMaturityDate(LocalDate.now().plusDays(duration));
            borrowOp.borrow();

            transactions.add(0, borrowOp);
            saveTransactionHistory(); // Save to persistent storage
            updateOverview();
            
            showInfo("✅ Borrow transaction executed & saved!\n\n" +
                    "Transaction ID: " + borrowOp.getOperationId() + "\n" +
                    "Amount: " + formatCurrency(amount) + "\n" +
                    "Rate: " + rate + "%\n" +
                    "Duration: " + duration + " days\n" +
                    "Counterparty: " + cmbCounterparty.getValue() + "\n" +
                    "Maturity: " + borrowOp.getMaturityDate());
            
            clearForm();
            
        } catch (NumberFormatException e) {
            showError("Please enter valid numeric values");
        }
    }

    /**
     * Executes a lend transaction.
     */
    @FXML
    public void executeLend(ActionEvent event) {
        if (!validateTransaction("Lend")) return;

        try {
            double amount = parseAmount(txtAmount.getText());
            double rate = Double.parseDouble(txtInterestRate.getText());
            int duration = Integer.parseInt(txtDuration.getText());
            
            InterbankOperations lendOp = new InterbankOperations();
            lendOp.setOperationId("IB-LND-" + System.currentTimeMillis());
            lendOp.setBorrowAmount(0);
            lendOp.setLendingAmount(amount);
            lendOp.setInterestRate(rate);
            lendOp.setCounterpartyBank(cmbCounterparty.getValue());
            lendOp.setMaturityDate(LocalDate.now().plusDays(duration));
            lendOp.lend();

            transactions.add(0, lendOp);
            saveTransactionHistory(); // Save to persistent storage
            updateOverview();
            
            showInfo("✅ Lend transaction executed & saved!\n\n" +
                    "Transaction ID: " + lendOp.getOperationId() + "\n" +
                    "Amount: " + formatCurrency(amount) + "\n" +
                    "Rate: " + rate + "%\n" +
                    "Duration: " + duration + " days\n" +
                    "Counterparty: " + cmbCounterparty.getValue() + "\n" +
                    "Maturity: " + lendOp.getMaturityDate());
            
            clearForm();
            
        } catch (NumberFormatException e) {
            showError("Please enter valid numeric values");
        }
    }

    /**
     * Updates interbank records.
     */
    @FXML
    public void updateInterbankRecord(ActionEvent event) {
        if (currentTransaction != null) {
            currentTransaction.updateInterbankRecord();
        }
        
        updateOverview();
        tblTransactions.refresh();
        
        showInfo("Interbank records updated successfully.\n" +
                "Total transactions: " + transactions.size());
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
     * Validates transaction inputs.
     */
    private boolean validateTransaction(String type) {
        if (cmbCounterparty.getValue() == null) {
            showError("Please select a counterparty bank");
            return false;
        }
        if (txtAmount.getText().isEmpty()) {
            showError("Please enter transaction amount");
            return false;
        }
        if (!creditValidated) {
            showWarning("Please validate credit limit first");
            return false;
        }
        return true;
    }

    /**
     * Clears the form.
     */
    private void clearForm() {
        cmbTransactionType.setValue(null);
        cmbCounterparty.setValue(null);
        txtAmount.clear();
        txtInterestRate.setText("6.5");
        txtDuration.setText("7");
        lblCreditStatus.setText("Not Checked");
        lblCreditStatus.setStyle("-fx-text-fill: #666666;");
        creditValidated = false;
        currentTransaction = null;
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
            return String.format("৳%.0fM", amount / 1000000);
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

