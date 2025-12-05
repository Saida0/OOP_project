package com.example.simulation_of_bangladesh_bank.saida.controller;

import com.example.simulation_of_bangladesh_bank.saida.model.ForeignAidMonitoring;
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
 * Controller for Foreign Aid Monitoring view.
 */
public class ForeignAidController implements Initializable {

    @FXML private Label lblTotalAid;
    @FXML private Label lblActiveProjects;
    @FXML private Label lblUtilization;
    @FXML private Label lblBottlenecks;
    @FXML private ProgressBar progressUtilization;

    @FXML private TextField txtProjectName;
    @FXML private ComboBox<String> cmbDonorAgency;
    @FXML private TextField txtAidAmount;
    @FXML private ComboBox<String> cmbUtilizationStatus;
    @FXML private TextField txtAmountUtilized;

    @FXML private TableView<ForeignAidMonitoring> tblAidProjects;
    @FXML private TableColumn<ForeignAidMonitoring, String> colProject;
    @FXML private TableColumn<ForeignAidMonitoring, String> colDonor;
    @FXML private TableColumn<ForeignAidMonitoring, String> colAmount;
    @FXML private TableColumn<ForeignAidMonitoring, String> colUtilized;
    @FXML private TableColumn<ForeignAidMonitoring, String> colPercent;
    @FXML private TableColumn<ForeignAidMonitoring, String> colStatus;

    private ObservableList<ForeignAidMonitoring> projects = FXCollections.observableArrayList();
    private ForeignAidMonitoring currentProject;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmbDonorAgency.setItems(FXCollections.observableArrayList(
            "World Bank", "Asian Development Bank", "IMF", "JICA (Japan)",
            "USAID", "DFID (UK)", "GIZ (Germany)", "KOICA (Korea)", "Others"
        ));
        cmbUtilizationStatus.setItems(FXCollections.observableArrayList(
            "On Track", "Delayed", "At Risk", "Completed", "Not Started"
        ));
        setupTableColumns();
        loadSampleData();
        setupTableSelectionListener();
        updateOverview();
    }

    private void setupTableColumns() {
        colProject.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProjectName()));
        colDonor.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDonorAgency()));
        colAmount.setCellValueFactory(data -> new SimpleStringProperty("$" + formatAmount(data.getValue().getAidAmount())));
        colUtilized.setCellValueFactory(data -> {
            double utilized = data.getValue().getAidAmount() * 0.6; // Simulated
            return new SimpleStringProperty("$" + formatAmount(utilized));
        });
        colPercent.setCellValueFactory(data -> new SimpleStringProperty("60%"));
        colStatus.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUtilizationStatus()));
        tblAidProjects.setItems(projects);
    }

    private void loadSampleData() {
        ForeignAidMonitoring p1 = new ForeignAidMonitoring();
        p1.setProjectName("Padma Bridge Rail Link");
        p1.setDonorAgency("Asian Development Bank");
        p1.setAidAmount(2500000000.0);
        p1.setUtilizationStatus("On Track");
        projects.add(p1);

        ForeignAidMonitoring p2 = new ForeignAidMonitoring();
        p2.setProjectName("Primary Education Development");
        p2.setDonorAgency("World Bank");
        p2.setAidAmount(800000000.0);
        p2.setUtilizationStatus("Delayed");
        projects.add(p2);
    }

    /**
     * When an aid project is selected, populate the form and overview KPIs.
     */
    private void setupTableSelectionListener() {
        tblAidProjects.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                populateFormFromProject(newSel);
            }
        });
    }

    private void populateFormFromProject(ForeignAidMonitoring p) {
        currentProject = p;
        txtProjectName.setText(p.getProjectName());
        cmbDonorAgency.setValue(p.getDonorAgency());
        txtAidAmount.setText(String.valueOf(p.getAidAmount()));
        cmbUtilizationStatus.setValue(p.getUtilizationStatus());

        // Update overview metrics based on this project
        lblTotalAid.setText("USD " + formatAmount(p.getAidAmount()));
        lblActiveProjects.setText("1");
        boolean bottleneck = "Delayed".equals(p.getUtilizationStatus()) || "At Risk".equals(p.getUtilizationStatus());
        lblBottlenecks.setText(bottleneck ? "1" : "0");
        double utilizationRatio = p.getAidAmount() > 0 ? 0.6 : 0; // placeholder until real field exists
        lblUtilization.setText(String.format("%.0f%%", utilizationRatio * 100));
        progressUtilization.setProgress(utilizationRatio);
    }

    private void updateOverview() {
        double totalAid = projects.stream().mapToDouble(ForeignAidMonitoring::getAidAmount).sum();
        long active = projects.stream().filter(p -> !"Completed".equals(p.getUtilizationStatus())).count();
        long bottlenecks = projects.stream().filter(p -> "Delayed".equals(p.getUtilizationStatus()) || "At Risk".equals(p.getUtilizationStatus())).count();
        
        lblTotalAid.setText("$" + formatAmount(totalAid));
        lblActiveProjects.setText(String.valueOf(active));
        lblBottlenecks.setText(String.valueOf(bottlenecks));
        lblUtilization.setText("60%");
        progressUtilization.setProgress(0.6);
    }

    @FXML
    public void validateAidUse(ActionEvent event) {
        if (currentProject == null) currentProject = createProjectFromForm();
        boolean valid = currentProject.validateAidUse();
        showInfo(valid ? "✓ Aid utilization is within acceptable parameters" : 
                        "✗ Aid utilization needs attention");
    }

    @FXML
    public void generateAidReport(ActionEvent event) {
        if (currentProject == null && !projects.isEmpty()) currentProject = projects.get(0);
        if (currentProject != null) {
            String report = currentProject.generateAidReport();
            showReport("Foreign Aid Report", report);
        }
    }

    @FXML
    public void identifyBottlenecks(ActionEvent event) {
        StringBuilder bottleneckReport = new StringBuilder("=== BOTTLENECK ANALYSIS ===\n\n");
        
        projects.stream()
            .filter(p -> "Delayed".equals(p.getUtilizationStatus()) || "At Risk".equals(p.getUtilizationStatus()))
            .forEach(p -> {
                p.identifyBottlenecks();
                bottleneckReport.append("Project: ").append(p.getProjectName()).append("\n");
                bottleneckReport.append("Donor: ").append(p.getDonorAgency()).append("\n");
                bottleneckReport.append("Status: ").append(p.getUtilizationStatus()).append("\n");
                bottleneckReport.append("Issues: Land acquisition delays, procurement challenges\n\n");
            });
        
        if (bottleneckReport.toString().equals("=== BOTTLENECK ANALYSIS ===\n\n")) {
            bottleneckReport.append("No significant bottlenecks identified.\n");
        }
        
        showReport("Bottleneck Analysis", bottleneckReport.toString());
    }

    @FXML
    public void refreshTable(ActionEvent event) {
        if (currentProject != null) {
            projects.add(0, currentProject);
            currentProject = null;
        }
        updateOverview();
        tblAidProjects.refresh();
    }

    private ForeignAidMonitoring createProjectFromForm() {
        ForeignAidMonitoring p = new ForeignAidMonitoring();
        p.setProjectName(txtProjectName.getText());
        p.setDonorAgency(cmbDonorAgency.getValue());
        p.setAidAmount(parseAmount(txtAidAmount.getText()));
        p.setUtilizationStatus(cmbUtilizationStatus.getValue());
        return p;
    }

    private double parseAmount(String text) {
        if (text == null || text.isEmpty()) return 0;
        return Double.parseDouble(text.replaceAll("[^0-9.]", ""));
    }

    private String formatAmount(double amount) {
        if (amount >= 1000000000) return String.format("%.2fB", amount / 1000000000);
        else if (amount >= 1000000) return String.format("%.2fM", amount / 1000000);
        else return String.format("%.0f", amount);
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

