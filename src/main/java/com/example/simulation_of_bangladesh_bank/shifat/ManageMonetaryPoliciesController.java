package com.example.simulation_of_bangladesh_bank.shifat;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class ManageMonetaryPoliciesController
{
    @javafx.fxml.FXML
    private TextField policyID1;
    @javafx.fxml.FXML
    private TextField policyName1;
    @javafx.fxml.FXML
    private ComboBox<String> StatusID2;
    @javafx.fxml.FXML
    private ComboBox<String> StatusID1;
    @javafx.fxml.FXML
    private TextField policyName2;
    @javafx.fxml.FXML
    private TextField policyID2;
    @javafx.fxml.FXML
    private TextField dateOfImplementation2;
    @javafx.fxml.FXML
    private TableColumn<ManageMonetaryPolicies, String> policyName3;
    @javafx.fxml.FXML
    private TextField policyID3;
    @javafx.fxml.FXML
    private TextField dateOfImplementation1;
    @javafx.fxml.FXML
    private TableColumn<ManageMonetaryPolicies, String> dateOfImplementation3;
    @javafx.fxml.FXML
    private TextField descriptionID2;
    @javafx.fxml.FXML
    private TextField descriptionID1;
    @javafx.fxml.FXML
    private TableView<ManageMonetaryPolicies> tableview;
    @javafx.fxml.FXML
    private TableColumn<ManageMonetaryPolicies, String> statusID3;
    @javafx.fxml.FXML
    private TableColumn<ManageMonetaryPolicies, String> policyColumn;

    @javafx.fxml.FXML
    public void initialize() {
        policyColumn.setCellValueFactory(new PropertyValueFactory<>("policyId"));
        policyName3.setCellValueFactory(new PropertyValueFactory<>("policyName"));
        dateOfImplementation3.setCellValueFactory(new PropertyValueFactory<>("date"));
        statusID3.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    @javafx.fxml.FXML
    public void signoutOnAction(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.sceneSwitch(actionEvent, "shifat/GovernorDash.fxml", "Sign Out");

    }

    @javafx.fxml.FXML
    public void updateOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void saveOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void deleteOnAction(ActionEvent actionEvent) {
    }
}