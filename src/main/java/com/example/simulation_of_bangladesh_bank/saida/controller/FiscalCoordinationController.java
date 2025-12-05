package com.example.simulation_of_bangladesh_bank.saida.controller;

import com.example.simulation_of_bangladesh_bank.saida.model.FiscalCoordination;
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
 * Controller for Fiscal-Monetary Policy Coordination view.
 */
public class FiscalCoordinationController implements Initializable {

    @FXML private Label lblFiscalStance;
    @FXML private Label lblMonetaryStatus;
    @FXML private Label lblCoordinationStatus;

    @FXML private ComboBox<String> cmbFiscalStance;
    @FXML private ComboBox<String> cmbMonetaryStatus;
    @FXML private DatePicker dpCoordinationDate;
    @FXML private TextArea txtAgenda;

    @FXML private TableView<FiscalCoordination> tblCoordination;
    @FXML private TableColumn<FiscalCoordination, String> colDate;
    @FXML private TableColumn<FiscalCoordination, String> colFiscal;
    @FXML private TableColumn<FiscalCoordination, String> colMonetary;
    @FXML private TableColumn<FiscalCoordination, String> colStatus;
    @FXML private TableColumn<FiscalCoordination, String> colNotes;

    private ObservableList<FiscalCoordination> records = FXCollections.observableArrayList();
    private FiscalCoordination currentRecord;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmbFiscalStance.setItems(FXCollections.observableArrayList(
            "Expansionary", "Contractionary", "Neutral", "Counter-cyclical"
        ));
        cmbMonetaryStatus.setItems(FXCollections.observableArrayList(
            "Accommodative", "Tightening", "Neutral", "Easing"
        ));
        dpCoordinationDate.setValue(LocalDate.now());
        setupTableColumns();
        loadSampleData();
        setupTableSelectionListener();
    }

    private void setupTableColumns() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        colDate.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCoordinationDate().format(formatter)));
        colFiscal.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFiscalPolicyStance()));
        colMonetary.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getMonetaryPolicyStatus()));
        colStatus.setCellValueFactory(data -> {
            String fiscal = data.getValue().getFiscalPolicyStance();
            String monetary = data.getValue().getMonetaryPolicyStatus();
            boolean aligned = (fiscal.contains("Expansion") && monetary.contains("Accommod")) ||
                            (fiscal.contains("Contract") && monetary.contains("Tight")) ||
                            fiscal.contains("Neutral");
            return new SimpleStringProperty(aligned ? "Aligned" : "Divergent");
        });
        colNotes.setCellValueFactory(data -> new SimpleStringProperty("Policy coordination meeting"));
        tblCoordination.setItems(records);
    }

    private void loadSampleData() {
        FiscalCoordination fc1 = new FiscalCoordination();
        fc1.setFiscalPolicyStance("Expansionary");
        fc1.setMonetaryPolicyStatus("Accommodative");
        fc1.setCoordinationDate(LocalDate.now().minusMonths(1));
        records.add(fc1);
    }

    /**
     * When a coordination record is selected, populate the top controls and labels.
     */
    private void setupTableSelectionListener() {
        tblCoordination.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                populateFormFromRecord(newSel);
            }
        });
    }

    private void populateFormFromRecord(FiscalCoordination fc) {
        currentRecord = fc;
        dpCoordinationDate.setValue(fc.getCoordinationDate());
        cmbFiscalStance.setValue(fc.getFiscalPolicyStance());
        cmbMonetaryStatus.setValue(fc.getMonetaryPolicyStatus());

        // Update header labels to reflect this coordination snapshot
        lblFiscalStance.setText(fc.getFiscalPolicyStance());
        boolean aligned = (fc.getFiscalPolicyStance().contains("Expansion") && fc.getMonetaryPolicyStatus().contains("Accommod")) ||
                          (fc.getFiscalPolicyStance().contains("Contract") && fc.getMonetaryPolicyStatus().contains("Tight")) ||
                          fc.getFiscalPolicyStance().contains("Neutral") ||
                          fc.getMonetaryPolicyStatus().contains("Neutral");
        lblCoordinationStatus.setText(aligned ? "Aligned" : "Divergent");
        lblCoordinationStatus.setStyle("-fx-text-fill: " + (aligned ? "#4caf50" : "#f44336") + "; -fx-font-weight: bold;");
        lblMonetaryStatus.setText(fc.getMonetaryPolicyStatus());
    }

    @FXML
    public void loadMonetaryPolicy(ActionEvent event) {
        currentRecord = new FiscalCoordination();
        currentRecord.loadMonetaryPolicy();
        
        lblMonetaryStatus.setText("Accommodative");
        lblMonetaryStatus.setStyle("-fx-text-fill: #1a237e; -fx-font-weight: bold;");
        cmbMonetaryStatus.setValue("Accommodative");
        
        showInfo("Bangladesh Bank Monetary Policy Loaded\n\n" +
                "Current Stance: Accommodative\n" +
                "Policy Rate: 6.0%\n" +
                "Reserve Requirements: CRR 4%, SLR 13%\n" +
                "Credit Growth Target: 14.8%");
    }

    @FXML
    public void matchFiscalPolicy(ActionEvent event) {
        if (cmbFiscalStance.getValue() == null || cmbMonetaryStatus.getValue() == null) {
            showError("Please select both fiscal and monetary policy stances");
            return;
        }

        currentRecord = createRecordFromForm();
        currentRecord.matchFiscalPolicy();
        
        String fiscal = cmbFiscalStance.getValue();
        String monetary = cmbMonetaryStatus.getValue();
        
        boolean aligned = (fiscal.contains("Expansion") && monetary.contains("Accommod")) ||
                        (fiscal.contains("Contract") && monetary.contains("Tight")) ||
                        fiscal.contains("Neutral") || monetary.contains("Neutral");
        
        lblFiscalStance.setText(fiscal);
        lblCoordinationStatus.setText(aligned ? "Aligned" : "Divergent");
        lblCoordinationStatus.setStyle("-fx-text-fill: " + (aligned ? "#4caf50" : "#f44336") + "; -fx-font-weight: bold;");
        
        if (aligned) {
            showInfo("✓ Policies are ALIGNED\n\n" +
                    "Fiscal: " + fiscal + "\n" +
                    "Monetary: " + monetary + "\n\n" +
                    "Coordination is consistent with economic objectives.");
        } else {
            showWarning("⚠ Policy DIVERGENCE detected\n\n" +
                    "Fiscal: " + fiscal + "\n" +
                    "Monetary: " + monetary + "\n\n" +
                    "Recommend coordination meeting to align policies.");
        }
    }

    @FXML
    public void generateCoordinationAgenda(ActionEvent event) {
        if (currentRecord == null) currentRecord = createRecordFromForm();
        currentRecord.generateCoordinationAgenda();
        
        StringBuilder agenda = new StringBuilder("=== COORDINATION MEETING AGENDA ===\n\n");
        agenda.append("Date: ").append(dpCoordinationDate.getValue().format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))).append("\n\n");
        agenda.append("1. Review of Current Economic Situation\n");
        agenda.append("2. Fiscal Policy Update - Ministry of Finance\n");
        agenda.append("3. Monetary Policy Update - Bangladesh Bank\n");
        agenda.append("4. Policy Alignment Assessment\n");
        agenda.append("5. Coordination Issues Discussion\n");
        agenda.append("6. Action Items and Next Steps\n\n");
        
        if (!txtAgenda.getText().isEmpty()) {
            agenda.append("Additional Notes:\n").append(txtAgenda.getText());
        }
        
        showReport("Coordination Agenda", agenda.toString());
        
        records.add(0, currentRecord);
        tblCoordination.refresh();
    }

    @FXML
    public void refreshTable(ActionEvent event) {
        tblCoordination.refresh();
    }

    private FiscalCoordination createRecordFromForm() {
        FiscalCoordination fc = new FiscalCoordination();
        fc.setFiscalPolicyStance(cmbFiscalStance.getValue());
        fc.setMonetaryPolicyStatus(cmbMonetaryStatus.getValue());
        fc.setCoordinationDate(dpCoordinationDate.getValue());
        return fc;
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

    private void showError(String msg) { new Alert(Alert.AlertType.ERROR, msg).showAndWait(); }
    private void showWarning(String msg) { new Alert(Alert.AlertType.WARNING, msg).showAndWait(); }
    private void showInfo(String msg) { new Alert(Alert.AlertType.INFORMATION, msg).showAndWait(); }
}

