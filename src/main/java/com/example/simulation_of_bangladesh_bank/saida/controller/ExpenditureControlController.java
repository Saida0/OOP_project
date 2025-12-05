package com.example.simulation_of_bangladesh_bank.saida.controller;

import com.example.simulation_of_bangladesh_bank.saida.model.ExpenditureControl;
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
 * Controller for Expenditure Control view.
 */
public class ExpenditureControlController implements Initializable {

    @FXML private Label lblPending;
    @FXML private Label lblApproved;
    @FXML private Label lblRejected;
    @FXML private Label lblDisbursed;
    @FXML private Label lblBudgetStatus;

    @FXML private TextField txtRequestID;
    @FXML private ComboBox<String> cmbDepartment;
    @FXML private TextField txtAmount;
    @FXML private TextArea txtPurpose;

    @FXML private TableView<ExpenditureControl> tblExpenditure;
    @FXML private TableColumn<ExpenditureControl, String> colRequestID;
    @FXML private TableColumn<ExpenditureControl, String> colDepartment;
    @FXML private TableColumn<ExpenditureControl, String> colAmount;
    @FXML private TableColumn<ExpenditureControl, String> colPurpose;
    @FXML private TableColumn<ExpenditureControl, String> colStatus;
    @FXML private TableColumn<ExpenditureControl, String> colDate;

    private ObservableList<ExpenditureControl> requests = FXCollections.observableArrayList();
    private ExpenditureControl currentRequest;
    private int requestCounter = 1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmbDepartment.setItems(FXCollections.observableArrayList(
            "Ministry of Education", "Ministry of Health", "Ministry of Agriculture",
            "Ministry of Transport", "Ministry of Defense", "Ministry of ICT",
            "Ministry of Power", "Ministry of Water Resources", "Others"
        ));
        
        generateRequestID();
        setupTableColumns();
        loadSampleData();
        setupTableSelectionListener();
        updateStatistics();
    }

    private void setupTableColumns() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        colRequestID.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDepartment().substring(0, 3).toUpperCase() + "-" + requestCounter++));
        colDepartment.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDepartment()));
        colAmount.setCellValueFactory(data -> new SimpleStringProperty(formatCurrency(data.getValue().getRequestedAmount())));
        colPurpose.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPurpose()));
        colStatus.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getApprovalStatus()));
        colDate.setCellValueFactory(data -> new SimpleStringProperty(LocalDate.now().format(formatter)));
        tblExpenditure.setItems(requests);
    }

    private void loadSampleData() {
        ExpenditureControl e1 = new ExpenditureControl();
        e1.setDepartment("Ministry of Education");
        e1.setRequestedAmount(5000000000.0);
        e1.setPurpose("School infrastructure development");
        e1.setApprovalStatus("Pending");
        requests.add(e1);

        ExpenditureControl e2 = new ExpenditureControl();
        e2.setDepartment("Ministry of Health");
        e2.setRequestedAmount(3000000000.0);
        e2.setPurpose("Medical equipment procurement");
        e2.setApprovalStatus("Approved");
        requests.add(e2);
    }

    /**
     * When a row is selected, populate the expenditure request form.
     */
    private void setupTableSelectionListener() {
        tblExpenditure.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                populateFormFromRequest(newSel);
            }
        });
    }

    private void populateFormFromRequest(ExpenditureControl req) {
        currentRequest = req;
        // ID shown as stored/generated earlier
        txtRequestID.setText(txtRequestID.getText()); // keep existing ID pattern
        cmbDepartment.setValue(req.getDepartment());
        txtAmount.setText(String.valueOf(req.getRequestedAmount()));
        txtPurpose.setText(req.getPurpose());
        lblBudgetStatus.setText("Not Validated");
    }

    private void generateRequestID() {
        txtRequestID.setText("EXP-" + LocalDate.now().getYear() + "-" + String.format("%04d", requests.size() + 1));
    }

    private void updateStatistics() {
        long pending = requests.stream().filter(r -> "Pending".equals(r.getApprovalStatus())).count();
        long approved = requests.stream().filter(r -> "Approved".equals(r.getApprovalStatus())).count();
        long rejected = requests.stream().filter(r -> "Rejected".equals(r.getApprovalStatus())).count();
        double disbursed = requests.stream().filter(r -> "Approved".equals(r.getApprovalStatus()))
            .mapToDouble(ExpenditureControl::getRequestedAmount).sum();

        lblPending.setText(String.valueOf(pending));
        lblApproved.setText(String.valueOf(approved));
        lblRejected.setText(String.valueOf(rejected));
        lblDisbursed.setText(formatCurrency(disbursed));
    }

    @FXML
    public void validateBudget(ActionEvent event) {
        try {
            currentRequest = createRequestFromForm();
            boolean valid = currentRequest.validateBudget();
            
            lblBudgetStatus.setText(valid ? "✓ Within Budget" : "✗ Exceeds Budget");
            lblBudgetStatus.setStyle("-fx-text-fill: " + (valid ? "#4caf50" : "#f44336") + "; -fx-font-weight: bold;");
            showInfo(valid ? "Budget validated - funds available" : "Budget validation failed - insufficient funds");
        } catch (Exception e) {
            showError("Please fill all required fields");
        }
    }

    @FXML
    public void approveExpenditure(ActionEvent event) {
        if (currentRequest == null) currentRequest = createRequestFromForm();
        currentRequest.approveExpenditure();
        currentRequest.setApprovalStatus("Approved");
        requests.add(0, currentRequest);
        updateStatistics();
        showInfo("Expenditure APPROVED\nAmount: " + formatCurrency(currentRequest.getRequestedAmount()));
        clearForm();
    }

    @FXML
    public void rejectExpenditure(ActionEvent event) {
        if (currentRequest == null) currentRequest = createRequestFromForm();
        currentRequest.setApprovalStatus("Rejected");
        requests.add(0, currentRequest);
        updateStatistics();
        showInfo("Expenditure REJECTED");
        clearForm();
    }

    @FXML
    public void generateExpenditureReport(ActionEvent event) {
        if (currentRequest == null && !requests.isEmpty()) currentRequest = requests.get(0);
        if (currentRequest != null) {
            String report = currentRequest.generateExpenditureReport();
            showReport("Expenditure Report", report);
        }
    }

    @FXML
    public void refreshTable(ActionEvent event) {
        updateStatistics();
        tblExpenditure.refresh();
    }

    private ExpenditureControl createRequestFromForm() {
        ExpenditureControl e = new ExpenditureControl();
        e.setDepartment(cmbDepartment.getValue());
        e.setRequestedAmount(parseAmount(txtAmount.getText()));
        e.setPurpose(txtPurpose.getText());
        e.setApprovalStatus("Pending");
        return e;
    }

    private void clearForm() {
        generateRequestID();
        cmbDepartment.setValue(null);
        txtAmount.clear();
        txtPurpose.clear();
        lblBudgetStatus.setText("Not Validated");
        currentRequest = null;
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

