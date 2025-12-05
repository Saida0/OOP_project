package com.example.simulation_of_bangladesh_bank.saida.controller;

import com.example.simulation_of_bangladesh_bank.saida.model.BudgetManagement;
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

/**
 * Controller for Budget Management view.
 */
public class BudgetManagementController implements Initializable {

    @FXML private Label lblTotalAllocated;
    @FXML private Label lblTotalSpent;
    @FXML private Label lblExecutionRate;
    @FXML private Label lblRemaining;
    @FXML private ProgressBar progressExecution;

    @FXML private ComboBox<String> cmbFiscalYear;
    @FXML private ComboBox<String> cmbDepartment;
    @FXML private TextField txtAllocated;
    @FXML private TextField txtSpent;

    @FXML private TableView<BudgetManagement> tblBudget;
    @FXML private TableColumn<BudgetManagement, String> colDepartment;
    @FXML private TableColumn<BudgetManagement, String> colFiscalYear;
    @FXML private TableColumn<BudgetManagement, String> colAllocated;
    @FXML private TableColumn<BudgetManagement, String> colSpent;
    @FXML private TableColumn<BudgetManagement, String> colExecution;
    @FXML private TableColumn<BudgetManagement, String> colStatus;

    private ObservableList<BudgetManagement> budgets = FXCollections.observableArrayList();
    private BudgetManagement currentBudget;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int year = LocalDate.now().getYear();
        cmbFiscalYear.setItems(FXCollections.observableArrayList(
            "FY " + year + "-" + (year + 1),
            "FY " + (year - 1) + "-" + year
        ));
        cmbDepartment.setItems(FXCollections.observableArrayList(
            "Education", "Health", "Defense", "Infrastructure", "Agriculture",
            "Social Welfare", "Public Administration", "Others"
        ));
        setupTableColumns();
        loadSampleData();
        setupTableSelectionListener();
        updateOverview();
    }

    private void setupTableColumns() {
        colDepartment.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDepartment()));
        colFiscalYear.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFiscalYear()));
        colAllocated.setCellValueFactory(data -> new SimpleStringProperty(formatCurrency(data.getValue().getAllocatedAmount())));
        colSpent.setCellValueFactory(data -> new SimpleStringProperty(formatCurrency(data.getValue().getSpentAmount())));
        colExecution.setCellValueFactory(data -> {
            double rate = (data.getValue().getSpentAmount() / data.getValue().getAllocatedAmount()) * 100;
            return new SimpleStringProperty(String.format("%.1f%%", rate));
        });
        colStatus.setCellValueFactory(data -> {
            double rate = (data.getValue().getSpentAmount() / data.getValue().getAllocatedAmount()) * 100;
            if (rate >= 80) return new SimpleStringProperty("On Track");
            else if (rate >= 50) return new SimpleStringProperty("Moderate");
            else return new SimpleStringProperty("Low");
        });
        tblBudget.setItems(budgets);
    }

    private void loadSampleData() {
        int year = LocalDate.now().getYear();
        String fy = "FY " + year + "-" + (year + 1);
        
        addBudget("Education", fy, 80000000000.0, 45000000000.0);
        addBudget("Health", fy, 35000000000.0, 20000000000.0);
        addBudget("Infrastructure", fy, 120000000000.0, 75000000000.0);
    }

    /**
     * When a budget row is selected, populate the budget form and header metrics.
     */
    private void setupTableSelectionListener() {
        tblBudget.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                populateFormFromBudget(newSel);
            }
        });
    }

    private void populateFormFromBudget(BudgetManagement b) {
        currentBudget = b;
        cmbDepartment.setValue(b.getDepartment());
        cmbFiscalYear.setValue(b.getFiscalYear());
        txtAllocated.setText(String.valueOf(b.getAllocatedAmount()));
        txtSpent.setText(String.valueOf(b.getSpentAmount()));

        // Update overview based on this single budget
        double remaining = b.getAllocatedAmount() - b.getSpentAmount();
        double rate = b.getAllocatedAmount() > 0 ? (b.getSpentAmount() / b.getAllocatedAmount()) : 0;
        lblTotalAllocated.setText(formatCurrency(b.getAllocatedAmount()));
        lblTotalSpent.setText(formatCurrency(b.getSpentAmount()));
        lblRemaining.setText(formatCurrency(remaining));
        lblExecutionRate.setText(String.format("%.1f%%", rate * 100));
        progressExecution.setProgress(rate);
    }

    private void addBudget(String dept, String fy, double allocated, double spent) {
        BudgetManagement b = new BudgetManagement();
        b.setDepartment(dept);
        b.setFiscalYear(fy);
        b.setAllocatedAmount(allocated);
        b.setSpentAmount(spent);
        budgets.add(b);
    }

    private void updateOverview() {
        double totalAllocated = budgets.stream().mapToDouble(BudgetManagement::getAllocatedAmount).sum();
        double totalSpent = budgets.stream().mapToDouble(BudgetManagement::getSpentAmount).sum();
        double remaining = totalAllocated - totalSpent;
        double rate = totalAllocated > 0 ? (totalSpent / totalAllocated) : 0;

        lblTotalAllocated.setText(formatCurrency(totalAllocated));
        lblTotalSpent.setText(formatCurrency(totalSpent));
        lblRemaining.setText(formatCurrency(remaining));
        lblExecutionRate.setText(String.format("%.1f%%", rate * 100));
        progressExecution.setProgress(rate);
    }

    @FXML
    public void validateAgainstFramework(ActionEvent event) {
        if (currentBudget == null) currentBudget = createBudgetFromForm();
        boolean valid = currentBudget.validateAgainstFramework();
        showInfo(valid ? "✓ Budget allocation is within fiscal framework" : 
                        "⚠ Budget allocation exceeds framework limits");
    }

    @FXML
    public void generateConsolidatedBudget(ActionEvent event) {
        StringBuilder report = new StringBuilder("=== CONSOLIDATED BUDGET REPORT ===\n\n");
        report.append("Fiscal Year: ").append(cmbFiscalYear.getValue() != null ? cmbFiscalYear.getValue() : "Current").append("\n\n");
        
        budgets.forEach(b -> {
            double rate = (b.getSpentAmount() / b.getAllocatedAmount()) * 100;
            report.append(b.getDepartment()).append("\n");
            report.append("  Allocated: ").append(formatCurrency(b.getAllocatedAmount())).append("\n");
            report.append("  Spent: ").append(formatCurrency(b.getSpentAmount())).append("\n");
            report.append("  Execution: ").append(String.format("%.1f%%", rate)).append("\n\n");
        });
        
        if (!budgets.isEmpty()) budgets.get(0).generateConsolidatedBudget();
        showReport("Consolidated Budget", report.toString());
    }

    @FXML
    public void trackExecution(ActionEvent event) {
        if (currentBudget == null) currentBudget = createBudgetFromForm();
        currentBudget.trackExecution();
        
        double rate = (currentBudget.getSpentAmount() / currentBudget.getAllocatedAmount()) * 100;
        String status = rate >= 80 ? "On Track" : rate >= 50 ? "Moderate" : "Low Execution";
        
        showInfo("Budget Execution Tracking\n\n" +
                "Department: " + currentBudget.getDepartment() + "\n" +
                "Execution Rate: " + String.format("%.1f%%", rate) + "\n" +
                "Status: " + status);
    }

    @FXML
    public void refreshTable(ActionEvent event) {
        if (currentBudget != null) {
            budgets.add(0, currentBudget);
            currentBudget = null;
        }
        updateOverview();
        tblBudget.refresh();
    }

    private BudgetManagement createBudgetFromForm() {
        BudgetManagement b = new BudgetManagement();
        b.setDepartment(cmbDepartment.getValue());
        b.setFiscalYear(cmbFiscalYear.getValue());
        b.setAllocatedAmount(parseAmount(txtAllocated.getText()));
        b.setSpentAmount(parseAmount(txtSpent.getText()));
        return b;
    }

    private double parseAmount(String text) {
        if (text == null || text.isEmpty()) return 0;
        return Double.parseDouble(text.replaceAll("[^0-9.]", ""));
    }

    private String formatCurrency(double amount) {
        if (amount >= 1000000000) return String.format("৳%.1fB", amount / 1000000000);
        else if (amount >= 1000000) return String.format("৳%.1fM", amount / 1000000);
        else return String.format("৳%.0f", amount);
    }

    private void showReport(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        TextArea ta = new TextArea(content);
        ta.setEditable(false);
        ta.setWrapText(true);
        ta.setPrefHeight(350);
        alert.getDialogPane().setContent(ta);
        alert.showAndWait();
    }

    private void showInfo(String msg) { new Alert(Alert.AlertType.INFORMATION, msg).showAndWait(); }
}

