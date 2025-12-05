package com.example.simulation_of_bangladesh_bank.shifat;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ManageCircularsNoticesController
{
    @javafx.fxml.FXML
    private TableColumn<ManageCircularsNotices, String> issueDateColumn;
    @javafx.fxml.FXML
    private TableColumn<ManageCircularsNotices, String> circularColumn;
    @javafx.fxml.FXML
    private TableColumn<ManageCircularsNotices, String> expireDateColumn;
    @javafx.fxml.FXML
    private TableColumn<ManageCircularsNotices, String> titleColumn;
    @javafx.fxml.FXML
    private TableColumn<ManageCircularsNotices, String> statusColumn;
    @javafx.fxml.FXML
    private TextField circularID2;
    @javafx.fxml.FXML
    private TextField titleID2;
    @javafx.fxml.FXML
    private TextField titleID1;
    @javafx.fxml.FXML
    private TextField circularID3;
    @javafx.fxml.FXML
    private TableView<ManageCircularsNotices> tableID;
    @javafx.fxml.FXML
    private TextField circularID1;
    @javafx.fxml.FXML
    private DatePicker expireDate;
    @javafx.fxml.FXML
    private DatePicker issueDate;
    @javafx.fxml.FXML
    private ComboBox<String> statusID1;
    @javafx.fxml.FXML
    private ComboBox<String> statusID2;
    private List<ManageCircularsNotices> manageCircularsNoticesArrayList = new ArrayList<>();
    private File file = new File( "data/ManageCircularsNotices.bin");

    @javafx.fxml.FXML
    public void initialize() {

        circularColumn.setCellValueFactory(new PropertyValueFactory<>("circularID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        issueDateColumn.setCellValueFactory(new PropertyValueFactory<>("issueDate"));
        expireDateColumn.setCellValueFactory(new PropertyValueFactory<>("expireDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        manageCircularsNoticesArrayList = BinaryFileHelper.readAllObjects(file);
        tableID.getItems().clear();
        tableID.getItems().addAll(manageCircularsNoticesArrayList);

    }

    @javafx.fxml.FXML
    public void publishOnClick(ActionEvent actionEvent) {
        ManageCircularsNotices manageCircularsNotices = new ManageCircularsNotices(
                circularID1.getText(),
                titleID1.getText(),
                issueDate.getValue(),
                expireDate.getValue(),
                statusID1.getValue()
    );

        manageCircularsNoticesArrayList.add(manageCircularsNotices);
        BinaryFileHelper.writeAllObjects(file, manageCircularsNoticesArrayList);
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