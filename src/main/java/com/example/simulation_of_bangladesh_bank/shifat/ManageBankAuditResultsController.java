package com.example.simulation_of_bangladesh_bank.shifat;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class ManageBankAuditResultsController
{
    @javafx.fxml.FXML
    private TableColumn<ManageBankAuditResults, String> auditDateColumn;
    @javafx.fxml.FXML
    private TableColumn<ManageBankAuditResults, String> auditorNameColumn;
    @javafx.fxml.FXML
    private TableColumn<ManageBankAuditResults, String> summaryColumn;
    @javafx.fxml.FXML
    private TextField bankName2;
    @javafx.fxml.FXML
    private TextField bankName1;
    @javafx.fxml.FXML
    private DatePicker dateID1;
    @javafx.fxml.FXML
    private TextField auditID3;
    @javafx.fxml.FXML
    private TextField auditID2;
    @javafx.fxml.FXML
    private TableColumn<ManageBankAuditResults, String> auditColumn;
    @javafx.fxml.FXML
    private TextField auditID1;
    @javafx.fxml.FXML
    private TableView<ManageBankAuditResults> tableID;
    @javafx.fxml.FXML
    private TableColumn<ManageBankAuditResults, String> bankColumn;
    @javafx.fxml.FXML
    private TextArea summaryID;
    @javafx.fxml.FXML
    private TextField auditorName;

    @javafx.fxml.FXML
    public void initialize() {

        auditColumn.setCellValueFactory(new PropertyValueFactory<>("auditID"));
        auditDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        bankColumn.setCellValueFactory(new PropertyValueFactory<>("bankName"));
        auditorNameColumn.setCellValueFactory(new PropertyValueFactory<>("auditorName"));
        summaryColumn.setCellValueFactory(new PropertyValueFactory<>("summary"));
    }

    @javafx.fxml.FXML
    public void addOnClick(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void deleteOnClick(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void updateOnClicnk(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void signOutt(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.sceneSwitch(actionEvent, "shifat/DirectorofBankingRegulation.fxml", "Sign Out");

    }
}