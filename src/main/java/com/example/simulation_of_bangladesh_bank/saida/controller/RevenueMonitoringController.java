package com.example.simulation_of_bangladesh_bank.saida.controller;

import com.example.simulation_of_bangladesh_bank.saida.model.RevenueMonitoring;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for Revenue Monitoring view.
 */
public class RevenueMonitoringController implements Initializable {

    @FXML private Label lblTarget;
    @FXML private Label lblProgress;
    @FXML private Label lblTaxCollection;
    @FXML private Label lblNonTaxRevenue;
    @FXML private Label lblShortfall;
    @FXML private ProgressBar progressRevenue;
    
    @FXML private TextField txtTaxCollection;
    @FXML private TextField txtNonTaxRevenue;
    @FXML private TextField txtRevenueTarget;
    @FXML private ComboBox<String> cmbPeriod;
    
    @FXML private Button btnValidate;
    @FXML private Button btnReport;
    @FXML private Button btnShortfall;
    
    @FXML private TableView<RevenueMonitoring> tblRevenue;
    @FXML private TableColumn<RevenueMonitoring, String> colPeriod;
    @FXML private TableColumn<RevenueMonitoring, String> colTax;
    @FXML private TableColumn<RevenueMonitoring, String> colNonTax;
    @FXML private TableColumn<RevenueMonitoring, String> colTarget;
    @FXML private TableColumn<RevenueMonitoring, String> colAchieved;
    @FXML private TableColumn<RevenueMonitoring, String> colStatus;

    private ObservableList<RevenueMonitoring> records = FXCollections.observableArrayList();
    private RevenueMonitoring currentRecord;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmbPeriod.setItems(FXCollections.observableArrayList(
            "Q1 (Jul-Sep)", "Q2 (Oct-Dec)", "Q3 (Jan-Mar)", "Q4 (Apr-Jun)", 
            "Monthly", "Annual"
        ));
        
        setupTableColumns();
        loadSampleData();
        setupTableSelectionListener();
        updateOverview();
    }

    private void setupTableColumns() {
        colPeriod.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().getReportingPeriodAsString()));
        colTax.setCellValueFactory(data -> 
            new SimpleStringProperty(formatCurrency(data.getValue().getTaxCollection())));
        colNonTax.setCellValueFactory(data -> 
            new SimpleStringProperty(formatCurrency(data.getValue().getNonTaxRevenue())));
        colTarget.setCellValueFactory(data -> 
            new SimpleStringProperty(formatCurrency(data.getValue().getRevenueTarget())));
        colAchieved.setCellValueFactory(data -> {
            double total = data.getValue().getTaxCollection() + data.getValue().getNonTaxRevenue();
            double percent = (total / data.getValue().getRevenueTarget()) * 100;
            return new SimpleStringProperty(String.format("%.1f%%", percent));
        });
        colStatus.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().validateAgainstTarget() ? "On Track" : "Behind"));
        tblRevenue.setItems(records);
    }

    private void loadSampleData() {
        RevenueMonitoring r1 = new RevenueMonitoring();
        r1.setTaxCollection(120000000000.0);
        r1.setNonTaxRevenue(30000000000.0);
        r1.setRevenueTarget(180000000000.0);
        r1.setReportingPeriod("Q1 (Jul-Sep)");
        records.add(r1);
    }

    /**
     * When a row is selected in the revenue table, populate the form and top KPIs.
     */
    private void setupTableSelectionListener() {
        tblRevenue.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                populateFormFromRecord(newSel);
            }
        });
    }

    private void populateFormFromRecord(RevenueMonitoring record) {
        currentRecord = record;

        // Populate form fields
        txtTaxCollection.setText(String.valueOf(record.getTaxCollection()));
        txtNonTaxRevenue.setText(String.valueOf(record.getNonTaxRevenue()));
        txtRevenueTarget.setText(String.valueOf(record.getRevenueTarget()));
        if (record.getReportingPeriodAsString() != null && !record.getReportingPeriodAsString().isEmpty()) {
            cmbPeriod.setValue(record.getReportingPeriodAsString());
        }

        // Update overview KPIs based on this record
        double total = record.getTaxCollection() + record.getNonTaxRevenue();
        double target = record.getRevenueTarget();
        double shortfall = Math.max(0, target - total);
        double progress = target > 0 ? (total / target) : 0;

        lblTaxCollection.setText(formatCurrency(record.getTaxCollection()));
        lblNonTaxRevenue.setText(formatCurrency(record.getNonTaxRevenue()));
        lblTarget.setText(formatCurrency(target));
        lblShortfall.setText(formatCurrency(shortfall));
        lblProgress.setText(String.format("%.1f%% Achieved", progress * 100));
        progressRevenue.setProgress(Math.min(progress, 1.0));
    }

    private void updateOverview() {
        double totalTax = records.stream().mapToDouble(RevenueMonitoring::getTaxCollection).sum();
        double totalNonTax = records.stream().mapToDouble(RevenueMonitoring::getNonTaxRevenue).sum();
        double totalTarget = records.stream().mapToDouble(RevenueMonitoring::getRevenueTarget).sum();
        double total = totalTax + totalNonTax;
        double shortfall = Math.max(0, totalTarget - total);
        double progress = totalTarget > 0 ? (total / totalTarget) : 0;

        lblTaxCollection.setText(formatCurrency(totalTax));
        lblNonTaxRevenue.setText(formatCurrency(totalNonTax));
        lblTarget.setText(formatCurrency(totalTarget));
        lblShortfall.setText(formatCurrency(shortfall));
        lblProgress.setText(String.format("%.1f%% Achieved", progress * 100));
        progressRevenue.setProgress(Math.min(progress, 1.0));
    }

    @FXML
    public void validateAgainstTarget(ActionEvent event) {
        try {
            currentRecord = createRecordFromForm();
            boolean onTrack = currentRecord.validateAgainstTarget();
            
            if (onTrack) {
                showInfo("✓ Revenue collection is ON TRACK\n" +
                        "Current: " + formatCurrency(currentRecord.getTaxCollection() + currentRecord.getNonTaxRevenue()) +
                        "\nTarget: " + formatCurrency(currentRecord.getRevenueTarget()));
            } else {
                showWarning("✗ Revenue collection is BEHIND TARGET\n" +
                        "Shortfall: " + formatCurrency(currentRecord.getRevenueTarget() - 
                            currentRecord.getTaxCollection() - currentRecord.getNonTaxRevenue()));
            }
        } catch (Exception e) {
            showError("Please enter valid values");
        }
    }

    @FXML
    public void generatePerformanceReport(ActionEvent event) {
        if (currentRecord == null) currentRecord = createRecordFromForm();
        String report = currentRecord.generatePerformanceReport();
        showReport("Revenue Performance Report", report);
    }

    @FXML
    public void identifyShortfalls(ActionEvent event) {
        if (currentRecord == null) currentRecord = createRecordFromForm();
        currentRecord.identifyShortfalls();
        
        double total = currentRecord.getTaxCollection() + currentRecord.getNonTaxRevenue();
        double shortfall = currentRecord.getRevenueTarget() - total;
        
        if (shortfall > 0) {
            showWarning("SHORTFALL IDENTIFIED\n\n" +
                    "Amount: " + formatCurrency(shortfall) + "\n" +
                    "Percentage: " + String.format("%.1f%%", (shortfall/currentRecord.getRevenueTarget())*100) +
                    "\n\nRecommended Actions:\n" +
                    "1. Intensify tax collection efforts\n" +
                    "2. Review non-performing sectors\n" +
                    "3. Increase compliance monitoring");
        } else {
            showInfo("No shortfall - Revenue collection exceeds target!");
        }
    }

    @FXML
    public void refreshTable(ActionEvent event) {
        if (currentRecord != null) {
            records.add(0, currentRecord);
            currentRecord = null;
        }
        updateOverview();
        tblRevenue.refresh();
    }

    private RevenueMonitoring createRecordFromForm() {
        RevenueMonitoring r = new RevenueMonitoring();
        r.setTaxCollection(parseAmount(txtTaxCollection.getText()));
        r.setNonTaxRevenue(parseAmount(txtNonTaxRevenue.getText()));
        r.setRevenueTarget(parseAmount(txtRevenueTarget.getText()));
        r.setReportingPeriod(cmbPeriod.getValue() != null ? cmbPeriod.getValue() : "Monthly");
        return r;
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
        ta.setPrefHeight(300);
        alert.getDialogPane().setContent(ta);
        alert.showAndWait();
    }

    private void showError(String msg) { new Alert(Alert.AlertType.ERROR, msg).showAndWait(); }
    private void showWarning(String msg) { new Alert(Alert.AlertType.WARNING, msg).showAndWait(); }
    private void showInfo(String msg) { new Alert(Alert.AlertType.INFORMATION, msg).showAndWait(); }
}

