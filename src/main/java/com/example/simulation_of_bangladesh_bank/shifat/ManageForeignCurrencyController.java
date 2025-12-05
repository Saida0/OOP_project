package com.example.simulation_of_bangladesh_bank.shifat;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class ManageForeignCurrencyController
{
    @javafx.fxml.FXML
    private ComboBox<String> currencyType1;
    @javafx.fxml.FXML
    private ComboBox<String> currencyType2;
    @javafx.fxml.FXML
    private TableColumn<ManageForeignCurrency, String> amountColumn;
    @javafx.fxml.FXML
    private TableColumn<ManageForeignCurrency, String> reserveColumn;
    @javafx.fxml.FXML
    private TableColumn<ManageForeignCurrency, String> updatedDateColumn;
    @javafx.fxml.FXML
    private TextField amountID1;
    @javafx.fxml.FXML
    private DatePicker lastUpdatedDate;
    @javafx.fxml.FXML
    private TextField amountID2;
    @javafx.fxml.FXML
    private TableView<ManageForeignCurrency> tableID;
    @javafx.fxml.FXML
    private TextField reserveID3;
    @javafx.fxml.FXML
    private TextField reserveID2;
    @javafx.fxml.FXML
    private TextField reserveID1;
    @javafx.fxml.FXML
    private TableColumn<ManageForeignCurrency, String> currencyTypeColumn;

    @javafx.fxml.FXML
    public void initialize() {
        reserveColumn.setCellValueFactory(new PropertyValueFactory<>("reserveID"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        currencyTypeColumn.setCellValueFactory(new PropertyValueFactory<>("currencyType"));
        updatedDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    @javafx.fxml.FXML
    public void signOut(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.sceneSwitch(actionEvent, "shifat/GovernorDash.fxml", "Sign Out");

    }

    @javafx.fxml.FXML
    public void deleteOnClick(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void saveOnClick(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void updateOnClicnk(ActionEvent actionEvent) {
    }
}