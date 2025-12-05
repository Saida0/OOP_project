package com.example.simulation_of_bangladesh_bank.Sara;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ForeignExchangeDealerController
{
    @javafx.fxml.FXML
    private TableColumn<ForeignExchangeDealer, String> currencyColumn;
    @javafx.fxml.FXML
    private TextField buyingRate2;
    @javafx.fxml.FXML
    private TextField buyingRate1;
    @javafx.fxml.FXML
    private TableColumn<ForeignExchangeDealer, String> sellingColumn;
    @javafx.fxml.FXML
    private TableView<ForeignExchangeDealer> tableView;
    @javafx.fxml.FXML
    private TableColumn<ForeignExchangeDealer, String> buyngColumn;
    @javafx.fxml.FXML
    private DatePicker dateID1;
    @javafx.fxml.FXML
    private DatePicker dateID2;
    @javafx.fxml.FXML
    private TextField bankID1;
    @javafx.fxml.FXML
    private TextField bankID2;
    @javafx.fxml.FXML
    private TextField bankID3;
    @javafx.fxml.FXML
    private TableColumn<ForeignExchangeDealer, String> bankColumn;
    @javafx.fxml.FXML
    private TableColumn<ForeignExchangeDealer, String> dateColumn;
    @javafx.fxml.FXML
    private TextField sellingRate2;
    @javafx.fxml.FXML
    private ComboBox<String> currencyCode;
    @javafx.fxml.FXML
    private TextField sellingRate1;

    private List<ForeignExchangeDealer> foreignExchangeDealersArrayList = new ArrayList<>();
    private File file = new File( "data/ForeignExchangeDealer.bin");

    @javafx.fxml.FXML
    public void initialize() {

        bankColumn.setCellValueFactory(new PropertyValueFactory<>( "bankId"));
        currencyColumn.setCellValueFactory(new PropertyValueFactory<>( "currencyCode"));
        buyngColumn.setCellValueFactory(new PropertyValueFactory<>( "buyingRate"));
        sellingColumn.setCellValueFactory(new PropertyValueFactory<>( "sellingRate"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>( "effectiveDate"));

        currencyCode.getItems().addAll("USD", "BDT", "GBP", "MYR");


        foreignExchangeDealersArrayList = BinaryFileHelper.readAllObjects(file);
        tableView.getItems().clear();
        tableView.getItems().addAll(foreignExchangeDealersArrayList);
    }

    @javafx.fxml.FXML
    public void deleteOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void updateOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void saveOnAction(ActionEvent actionEvent) {

        ForeignExchangeDealer foreignExchangeDealer = new ForeignExchangeDealer(
                bankID1.getText(),
                currencyCode.getValue(),
                buyingRate1.getText(),
                sellingRate1.getText(),
                dateID1.getValue()


        );

        boolean add = foreignExchangeDealersArrayList.add(foreignExchangeDealer);
        BinaryFileHelper.writeAllObjects(file, foreignExchangeDealersArrayList);

        tableView.getItems().clear();
        tableView.getItems().addAll(foreignExchangeDealersArrayList);
    }

    @javafx.fxml.FXML
    public void signOutOnAction(ActionEvent actionEvent) throws IOException {
        SceneSwitching.sceneSwitch(actionEvent, "Sara/ForeignExchangeDealer_Dash.fxml", "Sign Out");
    }
}