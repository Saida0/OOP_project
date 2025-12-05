package com.example.simulation_of_bangladesh_bank.shifat;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class ManageAnnualPerformanceReportsController
{
    @javafx.fxml.FXML
    private TableColumn<ManageAnnualPerformanceReports, String> yearColumn;
    @javafx.fxml.FXML
    private TextField reportID1;
    @javafx.fxml.FXML
    private TextField reportID2;
    @javafx.fxml.FXML
    private TextField reportID3;
    @javafx.fxml.FXML
    private TableView<ManageAnnualPerformanceReports> tableID;
    @javafx.fxml.FXML
    private TableColumn<ManageAnnualPerformanceReports, String> bankColumn;
    @javafx.fxml.FXML
    private TableColumn<ManageAnnualPerformanceReports, String> reportColumn;
    @javafx.fxml.FXML
    private TextField bankName2;
    @javafx.fxml.FXML
    private TableColumn<ManageAnnualPerformanceReports, String> remarkColumn;
    @javafx.fxml.FXML
    private TextField bankName1;
    @javafx.fxml.FXML
    private TextArea remarkID;
    @javafx.fxml.FXML
    private DatePicker yearID;

    @javafx.fxml.FXML
    public void initialize() {

        reportColumn.setCellValueFactory(new PropertyValueFactory<>("report"));
        bankColumn.setCellValueFactory(new PropertyValueFactory<>("bankName"));
        remarkColumn.setCellValueFactory(new PropertyValueFactory<>("remark"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    @javafx.fxml.FXML
    public void addOnClick(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void signOut(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.sceneSwitch(actionEvent, "shifat/DirectorofBankingRegulation.fxml", "Sign Out");

    }

    @javafx.fxml.FXML
    public void deleteOnClick(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void updateOnClicnk(ActionEvent actionEvent) {
    }
}