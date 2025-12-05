package com.example.simulation_of_bangladesh_bank.shifat;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class ManageNational_InterestRatesController
{
    @javafx.fxml.FXML
    private TableColumn<ManageNational_InterestRates, String> rateTypeColumn;
    @javafx.fxml.FXML
    private TableColumn<ManageNational_InterestRates, String> percentageColumn;
    @javafx.fxml.FXML
    private TextField rateID3;
    @javafx.fxml.FXML
    private TextField percentageID2;
    @javafx.fxml.FXML
    private TextField percentageID1;
    @javafx.fxml.FXML
    private ComboBox<String> rateType1;
    @javafx.fxml.FXML
    private TextField rateID1;
    @javafx.fxml.FXML
    private ComboBox<String> rateType2;
    @javafx.fxml.FXML
    private TextField rateID2;
    @javafx.fxml.FXML
    private TableColumn<ManageNational_InterestRates, String> effectiveDateColumn;
    @javafx.fxml.FXML
    private TableColumn<ManageNational_InterestRates, String> rateColumn;
    @javafx.fxml.FXML
    private TableView<ManageNational_InterestRates> tableID;
    @javafx.fxml.FXML
    private DatePicker effectiveDate;

    @javafx.fxml.FXML
    public void initialize() {
        rateColumn.setCellValueFactory(new PropertyValueFactory<>("rateID"));
        rateTypeColumn.setCellValueFactory(new PropertyValueFactory<>("rateType"));
        percentageColumn.setCellValueFactory(new PropertyValueFactory<>("percentage"));
        effectiveDateColumn.setCellValueFactory(new PropertyValueFactory<>("effectiveDate"));
    }

    @javafx.fxml.FXML
    public void addOnClick(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void signOut(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.sceneSwitch(actionEvent, "shifat/GovernorDash.fxml", "Sign Out");

    }

    @javafx.fxml.FXML
    public void deleteOnClick(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void updateOnClicnk(ActionEvent actionEvent) {
    }
}