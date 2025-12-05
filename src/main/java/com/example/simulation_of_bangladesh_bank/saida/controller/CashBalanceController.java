package com.example.simulation_of_bangladesh_bank.saida.controller;

import com.example.simulation_of_bangladesh_bank.saida.model.GovCashBalance;
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
import java.util.ResourceBundle;

/**
 * Controller for Government Cash Balance Management view.
 */
public class CashBalanceController implements Initializable {

    @FXML private Label lblCurrentBalance;
    @FXML private Label lblDailyRequirement;
    @FXML private Label lblProjectedBalance;
    @FXML private Label lblStatusIcon;
    @FXML private Label lblCashStatus;

    @FXML private TextField txtCashBalance;
    @FXML private TextField txtDailyRequirement;
    @FXML private TextField txtProjectedBalance;
    @FXML private DatePicker dpReportDate;

    @FXML private TableView<GovCashBalance> tblCashBalance;
    @FXML private TableColumn<GovCashBalance, String> colDate;
    @FXML private TableColumn<GovCashBalance, String> colBalance;
    @FXML private TableColumn<GovCashBalance, String> colDaily;
    @FXML private TableColumn<GovCashBalance, String> colProjected;
    @FXML private TableColumn<GovCashBalance, String> colDays;
    @FXML private TableColumn<GovCashBalance, String> colStatus;

    private ObservableList<GovCashBalance> records = FXCollections.observableArrayList();
    private GovCashBalance currentRecord;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dpReportDate.setValue(LocalDate.now());
        setupTableColumns();
        loadSampleData();
        setupTableSelectionListener();
        updateOverview();
    }

    private void setupTableColumns() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        colDate.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getReportDate().format(formatter)));
        colBalance.setCellValueFactory(data -> new SimpleStringProperty(formatCurrency(data.getValue().getCashBalance())));
        colDaily.setCellValueFactory(data -> new SimpleStringProperty(formatCurrency(data.getValue().getDailyRequirement())));
        colProjected.setCellValueFactory(data -> new SimpleStringProperty(formatCurrency(data.getValue().getProjectedBalance())));
        colDays.setCellValueFactory(data -> {
            double days = data.getValue().getCashBalance() / data.getValue().getDailyRequirement();
            return new SimpleStringProperty(String.format("%.0f days", days));
        });
        colStatus.setCellValueFactory(data -> {
            double days = data.getValue().getCashBalance() / data.getValue().getDailyRequirement();
            return new SimpleStringProperty(days >= 7 ? "Healthy" : days >= 3 ? "Adequate" : "Critical");
        });
        tblCashBalance.setItems(records);
    }

    private void loadSampleData() {
        GovCashBalance c1 = new GovCashBalance();
        c1.setCashBalance(85000000000.0);
        c1.setDailyRequirement(5000000000.0);
        c1.setProjectedBalance(80000000000.0);
        c1.setReportDate(LocalDate.now());
        records.add(c1);
    }

    /**
     * When a cash balance record is selected, populate the form and status section.
     */
    private void setupTableSelectionListener() {
        tblCashBalance.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                populateFormFromRecord(newSel);
            }
        });
    }

    private void populateFormFromRecord(GovCashBalance c) {
        currentRecord = c;
        dpReportDate.setValue(c.getReportDate());
        txtCashBalance.setText(String.valueOf(c.getCashBalance()));
        txtDailyRequirement.setText(String.valueOf(c.getDailyRequirement()));
        txtProjectedBalance.setText(String.valueOf(c.getProjectedBalance()));

        lblCurrentBalance.setText(formatCurrency(c.getCashBalance()));
        lblDailyRequirement.setText(formatCurrency(c.getDailyRequirement()));
        lblProjectedBalance.setText(formatCurrency(c.getProjectedBalance()));

        double days = c.getCashBalance() / c.getDailyRequirement();
        updateStatus(days);
    }

    private void updateOverview() {
        if (!records.isEmpty()) {
            GovCashBalance latest = records.get(0);
            lblCurrentBalance.setText(formatCurrency(latest.getCashBalance()));
            lblDailyRequirement.setText(formatCurrency(latest.getDailyRequirement()));
            lblProjectedBalance.setText(formatCurrency(latest.getProjectedBalance()));
            
            double days = latest.getCashBalance() / latest.getDailyRequirement();
            updateStatus(days);
        }
    }

    private void updateStatus(double daysCover) {
        if (daysCover >= 7) {
            lblStatusIcon.setText("✓");
            lblStatusIcon.setStyle("-fx-text-fill: #4caf50;");
            lblCashStatus.setText("Cash position is healthy (" + String.format("%.0f", daysCover) + " days cover)");
            lblCashStatus.setStyle("-fx-text-fill: #2e7d32;");
        } else if (daysCover >= 3) {
            lblStatusIcon.setText("⚠");
            lblStatusIcon.setStyle("-fx-text-fill: #ff9800;");
            lblCashStatus.setText("Cash position is adequate (" + String.format("%.0f", daysCover) + " days cover)");
            lblCashStatus.setStyle("-fx-text-fill: #f57c00;");
        } else {
            lblStatusIcon.setText("✗");
            lblStatusIcon.setStyle("-fx-text-fill: #f44336;");
            lblCashStatus.setText("Cash position is CRITICAL (" + String.format("%.0f", daysCover) + " days cover)");
            lblCashStatus.setStyle("-fx-text-fill: #c62828;");
        }
    }

    @FXML
    public void validateCashNeeds(ActionEvent event) {
        try {
            currentRecord = createRecordFromForm();
            boolean valid = currentRecord.validateCashNeeds();
            
            double daysCover = currentRecord.getCashBalance() / currentRecord.getDailyRequirement();
            updateStatus(daysCover);
            
            showInfo("Cash Needs Validation\n\n" +
                    "Current Balance: " + formatCurrency(currentRecord.getCashBalance()) + "\n" +
                    "Daily Requirement: " + formatCurrency(currentRecord.getDailyRequirement()) + "\n" +
                    "Days Cover: " + String.format("%.1f days", daysCover) + "\n" +
                    "Status: " + (valid ? "ADEQUATE" : "ACTION REQUIRED"));
        } catch (Exception e) {
            showError("Please enter valid values");
        }
    }

    @FXML
    public void updateCashRecords(ActionEvent event) {
        if (currentRecord == null) currentRecord = createRecordFromForm();
        currentRecord.updateCashRecords();
        records.add(0, currentRecord);
        updateOverview();
        tblCashBalance.refresh();
        showInfo("Cash records updated successfully");
        clearForm();
    }

    @FXML
    public void generateCashReport(ActionEvent event) {
        if (currentRecord == null && !records.isEmpty()) currentRecord = records.get(0);
        if (currentRecord != null) {
            String report = currentRecord.generateCashReport();
            showReport("Cash Balance Report", report);
        }
    }

    @FXML
    public void refreshTable(ActionEvent event) {
        updateOverview();
        tblCashBalance.refresh();
    }

    private GovCashBalance createRecordFromForm() {
        GovCashBalance c = new GovCashBalance();
        c.setCashBalance(parseAmount(txtCashBalance.getText()));
        c.setDailyRequirement(parseAmount(txtDailyRequirement.getText()));
        c.setProjectedBalance(parseAmount(txtProjectedBalance.getText()));
        c.setReportDate(dpReportDate.getValue());
        return c;
    }

    private void clearForm() {
        txtCashBalance.clear();
        txtDailyRequirement.clear();
        txtProjectedBalance.clear();
        dpReportDate.setValue(LocalDate.now());
        currentRecord = null;
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
    private void showInfo(String msg) { new Alert(Alert.AlertType.INFORMATION, msg).showAndWait(); }
}

