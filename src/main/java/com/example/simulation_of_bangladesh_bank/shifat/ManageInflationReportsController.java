package com.example.simulation_of_bangladesh_bank.shifat;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class ManageInflationReportsController
{
    @javafx.fxml.FXML
    private TableView<ManageInflationReports> tableID;
    @javafx.fxml.FXML
    private TableColumn<ManageInflationReports, String> yearColumn;
    @javafx.fxml.FXML
    private TableColumn<ManageInflationReports, String> inflationColumn;
    @javafx.fxml.FXML
    private TextField inflationID1;
    @javafx.fxml.FXML
    private TextField inflationID2;
    @javafx.fxml.FXML
    private TableColumn<ManageInflationReports, String> reportColumn;
    @javafx.fxml.FXML
    private DatePicker yearID2;
    @javafx.fxml.FXML
    private DatePicker yearID1;
    @javafx.fxml.FXML
    private TextField reportID1;
    @javafx.fxml.FXML
    private TextField reportID2;
    @javafx.fxml.FXML
    private ComboBox<String> statusID;
    @javafx.fxml.FXML
    private TextField reportID3;
    @javafx.fxml.FXML
    private TableColumn<ManageInflationReports, String> statusColumn;
    @javafx.fxml.FXML
    private TextArea commentID;
    @javafx.fxml.FXML
    private TableColumn<ManageInflationReports, String> commentColumn;

    @javafx.fxml.FXML
    public void initialize() {

        reportColumn.setCellValueFactory(new PropertyValueFactory<>("reportID"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        inflationColumn.setCellValueFactory(new PropertyValueFactory<>("inflationRate"));
        commentColumn.setCellValueFactory(new PropertyValueFactory<>("comment"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    @javafx.fxml.FXML
    public void addOnClick(ActionEvent actionEvent) {
    }

    @Deprecated
    public void signOut(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.sceneSwitch(actionEvent, "shifat/GovernorDash.fxml", "Sign Out");

    }

    @javafx.fxml.FXML
    public void deleteOnClick(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void updateOnClicnk(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void signOutt(ActionEvent actionEvent) throws IOException  {
        SceneSwitcher.sceneSwitch(actionEvent, "shifat/GovernorDash.fxml", "Sign Out");

    }
}