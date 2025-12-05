package com.example.simulation_of_bangladesh_bank.saida.controller;

import com.example.simulation_of_bangladesh_bank.saida.model.MonetaryStrategy;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.UUID;

/**
 * Controller for Monetary Policy Strategy view.
 */
public class MonetaryStrategyController implements Initializable {

    @FXML
    private Label lblFiscalYear;

    @FXML
    private Label lblCreditTarget;

    @FXML
    private Label lblProjectedGrowth;

    @FXML
    private Label lblStrategyStatus;

    @FXML
    private TextField txtStrategyID;

    @FXML
    private ComboBox<String> cmbFiscalYear;

    @FXML
    private TextField txtCreditTargets;

    @FXML
    private TextField txtProjectedGrowth;

    @FXML
    private TextField txtSectorAllocation;

    @FXML
    private Button btnLoadPrevious;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnValidate;

    @FXML
    private Button btnSubmit;

    @FXML
    private TableView<MonetaryStrategy> tblStrategies;

    @FXML
    private TableColumn<MonetaryStrategy, String> colStrategyID;

    @FXML
    private TableColumn<MonetaryStrategy, String> colFiscalYear;

    @FXML
    private TableColumn<MonetaryStrategy, String> colCreditTarget;

    @FXML
    private TableColumn<MonetaryStrategy, String> colSectorAllocation;

    @FXML
    private TableColumn<MonetaryStrategy, String> colGrowth;

    @FXML
    private TableColumn<MonetaryStrategy, String> colStatus;

    private ObservableList<MonetaryStrategy> strategies = FXCollections.observableArrayList();
    private MonetaryStrategy currentStrategy;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize fiscal year options
        int currentYear = LocalDate.now().getYear();
        cmbFiscalYear.setItems(FXCollections.observableArrayList(
            "FY " + currentYear + "-" + (currentYear + 1),
            "FY " + (currentYear + 1) + "-" + (currentYear + 2),
            "FY " + (currentYear - 1) + "-" + currentYear
        ));
        
        // Generate strategy ID
        generateStrategyID();
        
        // Initialize table columns
        setupTableColumns();
        
        // Load sample data
        loadSampleData();

        // Enable auto-populate of form when a strategy row is selected
        setupTableSelectionListener();
        
        // Update overview
        updateOverview();
    }

    /**
     * Sets up table columns with cell value factories.
     */
    private void setupTableColumns() {
        colStrategyID.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().getStrategyID()));
        
        colFiscalYear.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().getFiscalYear()));
        
        colCreditTarget.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().getCreditTargets() != null ? data.getValue().getCreditTargets() : "N/A"));
        
        colSectorAllocation.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().getSectorAllocationAsString()));
        
        colGrowth.setCellValueFactory(data -> 
            new SimpleStringProperty(String.format("%.1f%%", data.getValue().getProjectedGrowth())));
        
        colStatus.setCellValueFactory(data -> 
            new SimpleStringProperty("Submitted"));
        
        tblStrategies.setItems(strategies);
    }

    /**
     * Sets up table selection listener for auto-populating form fields.
     */
    private void setupTableSelectionListener() {
        tblStrategies.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateFormFromStrategy(newSelection);
            }
        });
    }

    /**
     * Populates form fields from selected monetary strategy.
     */
    private void populateFormFromStrategy(MonetaryStrategy strategy) {
        currentStrategy = strategy;

        // Populate form
        txtStrategyID.setText(strategy.getStrategyID());
        cmbFiscalYear.setValue(strategy.getFiscalYear());
        txtCreditTargets.setText(strategy.getCreditTargets() != null ? strategy.getCreditTargets() : "");
        txtSectorAllocation.setText(strategy.getSectorAllocationAsString());
        txtProjectedGrowth.setText(String.valueOf(strategy.getProjectedGrowth()));

        // Update overview labels
        lblFiscalYear.setText(strategy.getFiscalYear());
        lblCreditTarget.setText(strategy.getCreditTargets() != null ? strategy.getCreditTargets() : "N/A");
        lblProjectedGrowth.setText(String.format("%.1f%%", strategy.getProjectedGrowth()));
        lblStrategyStatus.setText("Submitted");
        lblStrategyStatus.setStyle("-fx-text-fill: #2196f3; -fx-font-weight: bold;");
    }

    /**
     * Loads sample strategies.
     */
    private void loadSampleData() {
        int year = LocalDate.now().getYear();
        
        MonetaryStrategy strategy1 = new MonetaryStrategy();
        strategy1.setStrategyID("MS-" + (year - 1) + "-001");
        strategy1.setFiscalYear("FY " + (year - 1) + "-" + year);
        strategy1.setCreditTargets("150000000000.0");
        strategy1.setSectorAllocationStr("Agriculture: 25%, Industry: 40%, SME: 20%, Others: 15%");
        strategy1.setProjectedGrowth(7.5);
        strategies.add(strategy1);
    }

    /**
     * Generates a new strategy ID.
     */
    private void generateStrategyID() {
        String id = "MS-" + LocalDate.now().getYear() + "-" + 
                    String.format("%03d", strategies.size() + 1);
        txtStrategyID.setText(id);
    }

    /**
     * Updates overview cards.
     */
    private void updateOverview() {
        if (!strategies.isEmpty()) {
            MonetaryStrategy latest = strategies.get(0);
            lblFiscalYear.setText(latest.getFiscalYear());
            lblCreditTarget.setText(latest.getCreditTargets() != null ? latest.getCreditTargets() : "N/A");
            lblProjectedGrowth.setText(String.format("%.1f%%", latest.getProjectedGrowth()));
            lblStrategyStatus.setText("Submitted");
        }
    }

    /**
     * Loads the previous strategy for reference.
     */
    @FXML
    public void loadPreviousStrategy(ActionEvent event) {
        if (strategies.isEmpty()) {
            showInfo("No previous strategy found");
            return;
        }

        MonetaryStrategy previous = strategies.get(0);
        previous.loadPreviousStrategy();
        
        // Pre-fill form with previous values (with adjustments)
        txtCreditTargets.setText(previous.getCreditTargets() != null ? previous.getCreditTargets() : "0");
        txtSectorAllocation.setText(previous.getSectorAllocationAsString());
        txtProjectedGrowth.setText(String.valueOf(previous.getProjectedGrowth()));
        
        showInfo("Previous strategy loaded.\n" +
                "Credit targets increased by 10% as baseline.\n" +
                "Please adjust values as needed.");
    }

    /**
     * Saves the new strategy as draft.
     */
    @FXML
    public void inputNewStrategy(ActionEvent event) {
        if (!validateInputs()) return;

        try {
            currentStrategy = createStrategyFromForm();
            currentStrategy.inputNewStrategy();
            
            lblStrategyStatus.setText("Draft");
            lblStrategyStatus.setStyle("-fx-text-fill: #ff9800; -fx-font-weight: bold;");
            
            showInfo("Strategy saved as draft.\n" +
                    "Please validate with Bangladesh Bank policy before submission.");
            
        } catch (NumberFormatException e) {
            showError("Please enter valid numeric values");
        }
    }

    /**
     * Validates strategy against BB monetary policy.
     */
    @FXML
    public void validateWithBBPolicy(ActionEvent event) {
        if (currentStrategy == null) {
            showError("Please save strategy first");
            return;
        }

        boolean isValid = currentStrategy.validateWithBBPolicy();
        
        if (isValid) {
            lblStrategyStatus.setText("Validated");
            lblStrategyStatus.setStyle("-fx-text-fill: #4caf50; -fx-font-weight: bold;");
            
            showInfo("✓ Strategy VALIDATED\n\n" +
                    "The strategy aligns with Bangladesh Bank monetary policy guidelines.\n" +
                    "You may now submit the strategy.");
        } else {
            lblStrategyStatus.setText("Needs Revision");
            lblStrategyStatus.setStyle("-fx-text-fill: #f44336; -fx-font-weight: bold;");
            
            showWarning("✗ Validation Issues Detected\n\n" +
                    "Please review the following:\n" +
                    "1. Credit growth should be within BB guidelines\n" +
                    "2. Sector allocation should meet priority sector requirements\n" +
                    "3. Projected growth should be realistic");
        }
    }

    /**
     * Submits the strategy to Bangladesh Bank.
     */
    @FXML
    public void submitStrategy(ActionEvent event) {
        if (currentStrategy == null) {
            showError("Please save and validate strategy first");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Submit Strategy");
        confirm.setHeaderText("Submit Monetary Strategy to Bangladesh Bank?");
        confirm.setContentText("This action cannot be undone. Please ensure all values are correct.");

        if (confirm.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            currentStrategy.submitStrategy();
            
            strategies.add(0, currentStrategy);
            updateOverview();
            
            lblStrategyStatus.setText("Submitted");
            lblStrategyStatus.setStyle("-fx-text-fill: #2196f3; -fx-font-weight: bold;");
            
            showInfo("Strategy submitted successfully to Bangladesh Bank.\n" +
                    "Strategy ID: " + currentStrategy.getStrategyID() + "\n" +
                    "Fiscal Year: " + currentStrategy.getFiscalYear());
            
            // Reset form
            generateStrategyID();
            clearForm();
        }
    }

    /**
     * Refreshes the table.
     */
    @FXML
    public void refreshTable(ActionEvent event) {
        tblStrategies.refresh();
        showInfo("Table refreshed");
    }

    /**
     * Creates a strategy from form inputs.
     */
    private MonetaryStrategy createStrategyFromForm() {
        MonetaryStrategy strategy = new MonetaryStrategy();
        strategy.setStrategyID(txtStrategyID.getText());
        strategy.setFiscalYear(cmbFiscalYear.getValue());
        strategy.setCreditTargets(txtCreditTargets.getText());
        strategy.setSectorAllocationStr(txtSectorAllocation.getText());
        strategy.setProjectedGrowth(Double.parseDouble(txtProjectedGrowth.getText()));
        return strategy;
    }

    /**
     * Validates form inputs.
     */
    private boolean validateInputs() {
        if (cmbFiscalYear.getValue() == null) {
            showError("Please select a fiscal year");
            return false;
        }
        if (txtCreditTargets.getText().isEmpty()) {
            showError("Please enter credit targets");
            return false;
        }
        if (txtSectorAllocation.getText().isEmpty()) {
            showError("Please enter sector allocation");
            return false;
        }
        if (txtProjectedGrowth.getText().isEmpty()) {
            showError("Please enter projected growth");
            return false;
        }
        return true;
    }

    /**
     * Clears the form.
     */
    private void clearForm() {
        cmbFiscalYear.setValue(null);
        txtCreditTargets.clear();
        txtSectorAllocation.clear();
        txtProjectedGrowth.clear();
        currentStrategy = null;
    }

    private double parseAmount(String text) {
        if (text == null || text.isEmpty()) return 0;
        String cleaned = text.replaceAll("[^0-9.]", "");
        return cleaned.isEmpty() ? 0 : Double.parseDouble(cleaned);
    }

    private String formatCurrency(double amount) {
        if (amount >= 1000000000) {
            return String.format("৳%.0fB", amount / 1000000000);
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

