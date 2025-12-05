package com.example.simulation_of_bangladesh_bank.Sara;

import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MaintainForeignReserveRecordsController
{
    @javafx.fxml.FXML
    private TextField currencyID2;
    @javafx.fxml.FXML
    private TableColumn<MaintainForeignReserveRecords, String> updateColumn;
    @javafx.fxml.FXML
    private TextField updateID;
    @javafx.fxml.FXML
    private TextField currencyID3;
    @javafx.fxml.FXML
    private TableColumn<MaintainForeignReserveRecords, String> currencyColumn;
    @javafx.fxml.FXML
    private TextField currencyID1;
    @javafx.fxml.FXML
    private TableColumn<MaintainForeignReserveRecords, String> amountColumn;
    @javafx.fxml.FXML
    private TableView<MaintainForeignReserveRecords> tableID;
    @javafx.fxml.FXML
    private TextField ammountID1;
    @javafx.fxml.FXML
    private TextField ammountID2;
    private List<MaintainForeignReserveRecords> maintainForeignReserveRecordsArrayList = new ArrayList<>();
    private File file = new File( "data/MaintainForeignReserveRecords.bin");

    @javafx.fxml.FXML
    public void initialize() {
        currencyColumn.setCellValueFactory(new PropertyValueFactory<>("currencyID"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        updateColumn.setCellValueFactory(new PropertyValueFactory<>("update"));


        maintainForeignReserveRecordsArrayList = BinaryFileHelper.readAllObjects(file);
        tableID.getItems().clear();
        tableID.getItems().addAll(maintainForeignReserveRecordsArrayList);
    }

    @javafx.fxml.FXML
    public void updateOnClick(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void deleteOnClick(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void saveOnClick(ActionEvent actionEvent) {
        MaintainForeignReserveRecords maintainForeignReserveRecords  = new MaintainForeignReserveRecords(
                currencyID1.getText(),
                ammountID1.getText(),
                updateID.getText()


        );

        maintainForeignReserveRecordsArrayList.add(maintainForeignReserveRecords);
        BinaryFileHelper.writeAllObjects(file, maintainForeignReserveRecordsArrayList);
    }
}