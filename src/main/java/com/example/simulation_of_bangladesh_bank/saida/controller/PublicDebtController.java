package com.example.simulation_of_bangladesh_bank.saida.controller;

import com.example.simulation_of_bangladesh_bank.saida.model.PublicDebt;
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
 * Controller for Public Debt Management view.
 */
public class PublicDebtController implements Initializable {

    @FXML private Label lblOutstandingDebt;
    @FXML private Label lblNextPayment;
    @FXML private Label lblPaymentAmount;
    @FXML private Label lblDebtTypes;

    @FXML private ComboBox<String> cmbDebtType;
    @FXML private TextField txtOutstandingDebt;
    @FXML private TextField txtPaymentAmount;
    @FXML private DatePicker dpPaymentDate;

    @FXML private TableView<PublicDebt> tblDebt;
    @FXML private TableColumn<PublicDebt, String> colDebtType;
    @FXML private TableColumn<PublicDebt, String> colOutstanding;
    @FXML private TableColumn<PublicDebt, String> colPaymentDate;
    @FXML private TableColumn<PublicDebt, String> colPaymentAmt;
    @FXML private TableColumn<PublicDebt, String> colStatus;
    @FXML private TableColumn<PublicDebt, String> colRemaining;

    private ObservableList<PublicDebt> debts = FXCollections.observableArrayList();
    private PublicDebt currentDebt;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmbDebtType.setItems(FXCollections.observableArrayList(
            "Domestic Debt - Treasury Bills", "Domestic Debt - Treasury Bonds",
            "External Debt - Bilateral", "External Debt - Multilateral",
            "External Debt - Commercial", "Savings Certificates"
        ));
        dpPaymentDate.setValue(LocalDate.now().plusMonths(1));
        setupTableColumns();
        loadSampleData();
        setupTableSelectionListener();
        updateOverview();
    }

    private void setupTableColumns() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        colDebtType.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDebtType()));
        colOutstanding.setCellValueFactory(data -> new SimpleStringProperty(formatCurrency(data.getValue().getOutstandingDebt())));
        colPaymentDate.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNextPaymentDate().format(formatter)));
        colPaymentAmt.setCellValueFactory(data -> new SimpleStringProperty(formatCurrency(data.getValue().getPaymentAmount())));
        colStatus.setCellValueFactory(data -> {
            LocalDate payDate = data.getValue().getNextPaymentDate();
            if (payDate.isBefore(LocalDate.now())) return new SimpleStringProperty("Overdue");
            else if (payDate.isBefore(LocalDate.now().plusDays(30))) return new SimpleStringProperty("Due Soon");
            else return new SimpleStringProperty("Scheduled");
        });
        colRemaining.setCellValueFactory(data -> 
            new SimpleStringProperty(formatCurrency(data.getValue().getOutstandingDebt() - data.getValue().getPaymentAmount())));
        tblDebt.setItems(debts);
    }

    private void loadSampleData() {
        PublicDebt d1 = new PublicDebt();
        d1.setDebtType("External Debt - Multilateral");
        d1.setOutstandingDebt(50000000000.0);
        d1.setPaymentAmount(2500000000.0);
        d1.setNextPaymentDate(LocalDate.now().plusDays(15));
        debts.add(d1);

        PublicDebt d2 = new PublicDebt();
        d2.setDebtType("Domestic Debt - Treasury Bonds");
        d2.setOutstandingDebt(80000000000.0);
        d2.setPaymentAmount(4000000000.0);
        d2.setNextPaymentDate(LocalDate.now().plusMonths(2));
        debts.add(d2);
    }

    /**
     * When a debt row is selected, populate the debt form fields.
     */
    private void setupTableSelectionListener() {
        tblDebt.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                populateFormFromDebt(newSel);
            }
        });
    }

    private void populateFormFromDebt(PublicDebt debt) {
        currentDebt = debt;
        cmbDebtType.setValue(debt.getDebtType());
        txtOutstandingDebt.setText(String.valueOf(debt.getOutstandingDebt()));
        txtPaymentAmount.setText(String.valueOf(debt.getPaymentAmount()));
        dpPaymentDate.setValue(debt.getNextPaymentDate());
    }

    private void updateOverview() {
        double totalDebt = debts.stream().mapToDouble(PublicDebt::getOutstandingDebt).sum();
        lblOutstandingDebt.setText(formatCurrency(totalDebt));
        lblDebtTypes.setText(String.valueOf(debts.size()));
        
        if (!debts.isEmpty()) {
            PublicDebt next = debts.stream()
                .min((a, b) -> a.getNextPaymentDate().compareTo(b.getNextPaymentDate()))
                .orElse(debts.get(0));
            lblNextPayment.setText(next.getNextPaymentDate().format(DateTimeFormatter.ofPattern("dd MMM yyyy")));
            lblPaymentAmount.setText(formatCurrency(next.getPaymentAmount()));
        }
    }

    @FXML
    public void generateServiceSchedule(ActionEvent event) {
        StringBuilder schedule = new StringBuilder("=== DEBT SERVICE SCHEDULE ===\n\n");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        
        debts.stream()
            .sorted((a, b) -> a.getNextPaymentDate().compareTo(b.getNextPaymentDate()))
            .forEach(d -> {
                schedule.append(String.format("%s\n", d.getDebtType()));
                schedule.append(String.format("  Payment Date: %s\n", d.getNextPaymentDate().format(formatter)));
                schedule.append(String.format("  Amount Due: %s\n\n", formatCurrency(d.getPaymentAmount())));
            });
        
        showReport("Debt Service Schedule", schedule.toString());
    }

    @FXML
    public void authorizePayments(ActionEvent event) {
        if (currentDebt == null) currentDebt = createDebtFromForm();
        currentDebt.authorizePayments();
        
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Authorize Payment");
        confirm.setHeaderText("Authorize Debt Payment?");
        confirm.setContentText("Amount: " + formatCurrency(currentDebt.getPaymentAmount()) + 
            "\nDebt Type: " + currentDebt.getDebtType());
        
        if (confirm.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            showInfo("Payment AUTHORIZED\nAmount: " + formatCurrency(currentDebt.getPaymentAmount()));
            debts.add(0, currentDebt);
            updateOverview();
            clearForm();
        }
    }

    @FXML
    public void updateDebtRecords(ActionEvent event) {
        if (currentDebt == null) currentDebt = createDebtFromForm();
        currentDebt.updateDebtRecords();
        debts.add(0, currentDebt);
        updateOverview();
        tblDebt.refresh();
        showInfo("Debt records updated successfully");
        clearForm();
    }

    @FXML
    public void refreshTable(ActionEvent event) {
        updateOverview();
        tblDebt.refresh();
    }

    private PublicDebt createDebtFromForm() {
        PublicDebt d = new PublicDebt();
        d.setDebtType(cmbDebtType.getValue());
        d.setOutstandingDebt(parseAmount(txtOutstandingDebt.getText()));
        d.setPaymentAmount(parseAmount(txtPaymentAmount.getText()));
        d.setNextPaymentDate(dpPaymentDate.getValue());
        return d;
    }

    private void clearForm() {
        cmbDebtType.setValue(null);
        txtOutstandingDebt.clear();
        txtPaymentAmount.clear();
        dpPaymentDate.setValue(LocalDate.now().plusMonths(1));
        currentDebt = null;
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

