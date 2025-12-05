package com.example.simulation_of_bangladesh_bank.saida.controller;

import com.example.simulation_of_bangladesh_bank.saida.model.ReformMonitoring;
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
 * Controller for Economic Reform Programs Monitoring view.
 */
public class ReformMonitoringController implements Initializable {

    @FXML private Label lblActiveReforms;
    @FXML private Label lblOverallProgress;
    @FXML private Label lblCompleted;
    @FXML private Label lblIssues;
    @FXML private ProgressBar progressOverall;

    @FXML private TextField txtReformName;
    @FXML private ComboBox<String> cmbMilestoneStatus;
    @FXML private DatePicker dpTargetDate;
    @FXML private TextArea txtIssues;

    @FXML private TableView<ReformMonitoring> tblReforms;
    @FXML private TableColumn<ReformMonitoring, String> colReformName;
    @FXML private TableColumn<ReformMonitoring, String> colStatus;
    @FXML private TableColumn<ReformMonitoring, String> colTargetDate;
    @FXML private TableColumn<ReformMonitoring, String> colProgress;
    @FXML private TableColumn<ReformMonitoring, String> colIssues;

    private ObservableList<ReformMonitoring> reforms = FXCollections.observableArrayList();
    private ReformMonitoring currentReform;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmbMilestoneStatus.setItems(FXCollections.observableArrayList(
            "On Track", "Slightly Delayed", "Significantly Delayed", 
            "At Risk", "Completed", "Not Started"
        ));
        dpTargetDate.setValue(LocalDate.now().plusMonths(6));
        setupTableColumns();
        loadSampleData();
        setupTableSelectionListener();
        updateOverview();
    }

    private void setupTableColumns() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        colReformName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getReformName()));
        colStatus.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMilestoneStatus()));
        colTargetDate.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTargetDate().format(formatter)));
        colProgress.setCellValueFactory(data -> {
            String status = data.getValue().getMilestoneStatus();
            if ("Completed".equals(status)) return new SimpleStringProperty("100%");
            else if ("On Track".equals(status)) return new SimpleStringProperty("75%");
            else if ("Slightly Delayed".equals(status)) return new SimpleStringProperty("50%");
            else return new SimpleStringProperty("25%");
        });
        colIssues.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getIssues()));
        tblReforms.setItems(reforms);
    }

    private void loadSampleData() {
        addReform("VAT Automation System", "On Track", LocalDate.now().plusMonths(3), "Minor technical challenges");
        addReform("Public Procurement Reform", "Slightly Delayed", LocalDate.now().plusMonths(6), "Capacity building needed");
        addReform("Financial Sector Strengthening", "Completed", LocalDate.now().minusMonths(1), "None");
    }

    /**
     * When a reform row is selected, populate the form and header summary.
     */
    private void setupTableSelectionListener() {
        tblReforms.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                populateFormFromReform(newSel);
            }
        });
    }

    private void populateFormFromReform(ReformMonitoring r) {
        currentReform = r;
        txtReformName.setText(r.getReformName());
        cmbMilestoneStatus.setValue(r.getMilestoneStatus());
        dpTargetDate.setValue(r.getTargetDate());
        txtIssues.setText(r.getIssues());

        // Update overview counters relative to this selected reform
        long active = "Completed".equals(r.getMilestoneStatus()) ? 0 : 1;
        long completed = "Completed".equals(r.getMilestoneStatus()) ? 1 : 0;
        long withIssues = ("Significantly Delayed".equals(r.getMilestoneStatus()) || "At Risk".equals(r.getMilestoneStatus())) ? 1 : 0;
        double progress = "Completed".equals(r.getMilestoneStatus()) ? 1.0 :
                          "On Track".equals(r.getMilestoneStatus()) ? 0.75 :
                          "Slightly Delayed".equals(r.getMilestoneStatus()) ? 0.5 : 0.25;

        lblActiveReforms.setText(String.valueOf(active));
        lblCompleted.setText(String.valueOf(completed));
        lblIssues.setText(String.valueOf(withIssues));
        lblOverallProgress.setText(String.format("%.0f%%", progress * 100));
        progressOverall.setProgress(progress);
    }

    private void addReform(String name, String status, LocalDate target, String issues) {
        ReformMonitoring r = new ReformMonitoring();
        r.setReformName(name);
        r.setMilestoneStatus(status);
        r.setTargetDate(target);
        r.setIssues(issues);
        reforms.add(r);
    }

    private void updateOverview() {
        long active = reforms.stream().filter(r -> !"Completed".equals(r.getMilestoneStatus())).count();
        long completed = reforms.stream().filter(r -> "Completed".equals(r.getMilestoneStatus())).count();
        long withIssues = reforms.stream().filter(r -> 
            "Significantly Delayed".equals(r.getMilestoneStatus()) || "At Risk".equals(r.getMilestoneStatus())).count();
        
        double progress = reforms.isEmpty() ? 0 : (double) completed / reforms.size();
        
        lblActiveReforms.setText(String.valueOf(active));
        lblCompleted.setText(String.valueOf(completed));
        lblIssues.setText(String.valueOf(withIssues));
        lblOverallProgress.setText(String.format("%.0f%%", progress * 100));
        progressOverall.setProgress(progress);
    }

    @FXML
    public void validateMilestones(ActionEvent event) {
        if (currentReform == null) currentReform = createReformFromForm();
        boolean valid = currentReform.validateMilestones();
        
        String status = currentReform.getMilestoneStatus();
        String message = "Milestone Validation\n\n" +
                "Reform: " + currentReform.getReformName() + "\n" +
                "Status: " + status + "\n" +
                "Target Date: " + currentReform.getTargetDate().format(DateTimeFormatter.ofPattern("dd MMM yyyy")) + "\n\n";
        
        if ("On Track".equals(status) || "Completed".equals(status)) {
            message += "✓ Milestones are on schedule";
        } else {
            message += "⚠ Action required to meet targets";
        }
        
        showInfo(message);
    }

    @FXML
    public void generateReformReport(ActionEvent event) {
        StringBuilder report = new StringBuilder("=== ECONOMIC REFORM PROGRESS REPORT ===\n\n");
        report.append("Report Date: ").append(LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))).append("\n\n");
        
        reforms.forEach(r -> {
            report.append("Reform: ").append(r.getReformName()).append("\n");
            report.append("Status: ").append(r.getMilestoneStatus()).append("\n");
            report.append("Target: ").append(r.getTargetDate().format(DateTimeFormatter.ofPattern("dd MMM yyyy"))).append("\n");
            report.append("Issues: ").append(r.getIssues()).append("\n\n");
        });
        
        if (!reforms.isEmpty()) reforms.get(0).generateReformReport();
        showReport("Reform Progress Report", report.toString());
    }

    @FXML
    public void recommendCorrectiveActions(ActionEvent event) {
        StringBuilder recommendations = new StringBuilder("=== CORRECTIVE ACTIONS ===\n\n");
        
        reforms.stream()
            .filter(r -> !"Completed".equals(r.getMilestoneStatus()) && !"On Track".equals(r.getMilestoneStatus()))
            .forEach(r -> {
                r.recommendCorrectiveActions();
                recommendations.append("Reform: ").append(r.getReformName()).append("\n");
                recommendations.append("Current Status: ").append(r.getMilestoneStatus()).append("\n");
                recommendations.append("Recommendations:\n");
                recommendations.append("  1. Accelerate implementation timeline\n");
                recommendations.append("  2. Increase resource allocation\n");
                recommendations.append("  3. Address identified bottlenecks\n");
                recommendations.append("  4. Strengthen coordination mechanisms\n\n");
            });
        
        if (recommendations.toString().equals("=== CORRECTIVE ACTIONS ===\n\n")) {
            recommendations.append("All reforms are on track. No corrective actions needed.\n");
        }
        
        showReport("Corrective Actions", recommendations.toString());
    }

    @FXML
    public void refreshTable(ActionEvent event) {
        if (currentReform != null && !txtReformName.getText().isEmpty()) {
            reforms.add(0, currentReform);
            clearForm();
        }
        updateOverview();
        tblReforms.refresh();
    }

    private ReformMonitoring createReformFromForm() {
        ReformMonitoring r = new ReformMonitoring();
        r.setReformName(txtReformName.getText());
        r.setMilestoneStatus(cmbMilestoneStatus.getValue());
        r.setTargetDate(dpTargetDate.getValue());
        r.setIssues(txtIssues.getText());
        return r;
    }

    private void clearForm() {
        txtReformName.clear();
        cmbMilestoneStatus.setValue(null);
        dpTargetDate.setValue(LocalDate.now().plusMonths(6));
        txtIssues.clear();
        currentReform = null;
    }

    private void showReport(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        TextArea ta = new TextArea(content);
        ta.setEditable(false);
        ta.setWrapText(true);
        ta.setPrefHeight(400);
        alert.getDialogPane().setContent(ta);
        alert.showAndWait();
    }

    private void showInfo(String msg) { new Alert(Alert.AlertType.INFORMATION, msg).showAndWait(); }
}

