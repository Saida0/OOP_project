package com.example.simulation_of_bangladesh_bank.shifat;

import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class ManagePenaltyRecordsController
{
    @javafx.fxml.FXML
    private TableColumn<ManagePenaltyRecords, String> penaltyColumn;
    @javafx.fxml.FXML
    private TableColumn<ManagePenaltyRecords, String> reasonColumn;
    @javafx.fxml.FXML
    private TableColumn<ManagePenaltyRecords, String> amountColumn;
    @javafx.fxml.FXML
    private TextField penaltyID1;
    @javafx.fxml.FXML
    private TextField bankName2;
    @javafx.fxml.FXML
    private TextField penaltyID3;
    @javafx.fxml.FXML
    private TextField bankName1;
    @javafx.fxml.FXML
    private TextField penaltyID2;
    @javafx.fxml.FXML
    private TextField amountID1;
    @javafx.fxml.FXML
    private TableColumn<ManagePenaltyRecords, String> nameColumn;
    @javafx.fxml.FXML
    private TextField reasonID;
    @javafx.fxml.FXML
    private DatePicker dateID;
    @javafx.fxml.FXML
    private TableView<ManagePenaltyRecords> tableID;
    @javafx.fxml.FXML
    private TableColumn<ManagePenaltyRecords, String> dateColumn;

    @javafx.fxml.FXML
    public void initialize() {
        penaltyColumn.setCellValueFactory(new PropertyValueFactory<>("penaltyID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("bankName"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("fineAmount"));
        reasonColumn.setCellValueFactory(new PropertyValueFactory<>("reason"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateIssued"));
    }

    @javafx.fxml.FXML
    public void addOnClick(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void searchOnClick(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void signOut(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.sceneSwitch(actionEvent, "shifat/DirectorofBankingRegulation.fxml", "Sign Out");

    }

    @javafx.fxml.FXML
    public void updateOnClicnk(ActionEvent actionEvent) {
    }
}