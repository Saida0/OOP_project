package com.example.simulation_of_bangladesh_bank.shifat;

import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class SuperviseCommercialBanksController
{
    @javafx.fxml.FXML
    private TableColumn<SuperviseCommercialBanks, String> licenseColumn;
    @javafx.fxml.FXML
    private TableColumn<SuperviseCommercialBanks, String> typeColumn;
    @javafx.fxml.FXML
    private TextField nameID2;
    @javafx.fxml.FXML
    private TextField bankId;
    @javafx.fxml.FXML
    private TextField bankId2;
    @javafx.fxml.FXML
    private TableColumn<SuperviseCommercialBanks, String> nameColumn;
    @javafx.fxml.FXML
    private ComboBox<String> statusID;
    @javafx.fxml.FXML
    private TextField bankId3;
    @javafx.fxml.FXML
    private TextField bankId4;
    @javafx.fxml.FXML
    private TableColumn<SuperviseCommercialBanks, String> statusColumn;
    @javafx.fxml.FXML
    private TableView<SuperviseCommercialBanks> tableID;
    @javafx.fxml.FXML
    private TableColumn<SuperviseCommercialBanks, String> bankColumn;
    @javafx.fxml.FXML
    private TextField nameID;
    @javafx.fxml.FXML
    private ComboBox<String> typeID;
    @javafx.fxml.FXML
    private ComboBox<String> typeID2;
    @javafx.fxml.FXML
    private TextField licenseID;
    @javafx.fxml.FXML
    private ComboBox<String> statusID2;

    @javafx.fxml.FXML
    public void initialize() {
        bankColumn.setCellValueFactory(new PropertyValueFactory<>("bankId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        licenseColumn.setCellValueFactory(new PropertyValueFactory<>("licensenumber"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

    }

    @javafx.fxml.FXML
    public void addOnClicnk(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void signOut(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.sceneSwitch(actionEvent, "shifat/DirectorofBankingRegulation.fxml", "Sign Out");

    }

    @javafx.fxml.FXML
    public void deleteOnClick(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void searchOnClickk(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void updateOnClicnk(ActionEvent actionEvent) {
    }
}