package com.example.simulation_of_bangladesh_bank.shifat;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class ManageBankComplianceReportsController
{
    @javafx.fxml.FXML
    private TableColumn<ManageBankComplianceReports, String> summaryColumn;
    @javafx.fxml.FXML
    private TableColumn<ManageBankComplianceReports, String> reportColumn;
    @javafx.fxml.FXML
    private TextField bankName2;
    @javafx.fxml.FXML
    private TextField bankName1;
    @javafx.fxml.FXML
    private DatePicker dateID1;
    @javafx.fxml.FXML
    private TableColumn<ManageBankComplianceReports, String> submissionDateColumn;
    @javafx.fxml.FXML
    private DatePicker dateID2;
    @javafx.fxml.FXML
    private TextField reportID1;
    @javafx.fxml.FXML
    private TextField reportID2;
    @javafx.fxml.FXML
    private ComboBox<String> statusID;
    @javafx.fxml.FXML
    private TextField reportID3;
    @javafx.fxml.FXML
    private TableColumn<ManageBankComplianceReports, String> statusColumn;
    @javafx.fxml.FXML
    private TableView<ManageBankComplianceReports> tableID;
    @javafx.fxml.FXML
    private TableColumn<ManageBankComplianceReports, String> bankColumn;
    @javafx.fxml.FXML
    private TextArea summaryID;

    @javafx.fxml.FXML
    public void initialize() {

        reportColumn.setCellValueFactory(new PropertyValueFactory<>("reportID"));
        submissionDateColumn.setCellValueFactory(new PropertyValueFactory<>("submissionDate"));
        bankColumn.setCellValueFactory(new PropertyValueFactory<>("bankName"));
        summaryColumn.setCellValueFactory(new PropertyValueFactory<>("summary"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
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