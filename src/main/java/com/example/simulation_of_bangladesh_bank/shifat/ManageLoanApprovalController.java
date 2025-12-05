package com.example.simulation_of_bangladesh_bank.shifat;

import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class ManageLoanApprovalController
{
    @javafx.fxml.FXML
    private TextField nameID1;
    @javafx.fxml.FXML
    private TextField nameID2;
    @javafx.fxml.FXML
    private TableColumn<ManageLoanApproval, String> nameColumn;
    @javafx.fxml.FXML
    private TableColumn<ManageLoanApproval, String> statusColumn;
    @javafx.fxml.FXML
    private TableView<ManageLoanApproval> tableID;
    @javafx.fxml.FXML
    private ComboBox<String> statusID1;
    @javafx.fxml.FXML
    private ComboBox<String> statusID2;
    @javafx.fxml.FXML
    private TableColumn<ManageLoanApproval, String> amountColumn;
    @javafx.fxml.FXML
    private TableColumn<ManageLoanApproval, String> loanColumn;
    @javafx.fxml.FXML
    private TextField purposeID1;
    @javafx.fxml.FXML
    private TextField loanID1;
    @javafx.fxml.FXML
    private TextField amountID1;
    @javafx.fxml.FXML
    private TextField loanID2;
    @javafx.fxml.FXML
    private ComboBox<String> applicationDate1;
    @javafx.fxml.FXML
    private TableColumn<ManageLoanApproval, String> purposeColumn;
    @javafx.fxml.FXML
    private TextField loanID3;
    @javafx.fxml.FXML
    private TextField loanID4;
    @javafx.fxml.FXML
    private TableColumn<ManageLoanApproval, String> applicationDateColumn;

    @javafx.fxml.FXML
    public void initialize() {

        loanColumn.setCellValueFactory(new PropertyValueFactory<>("loanId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("bankName"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amountRequested"));
        purposeColumn.setCellValueFactory(new PropertyValueFactory<>("purpose"));
        applicationDateColumn.setCellValueFactory(new PropertyValueFactory<>("applicationDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    @javafx.fxml.FXML
    public void searchOnClick(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void rejectOnClick(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void approveOnClick(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void saveOnClick(ActionEvent actionEvent) {
    }

    @Deprecated
    public void signOut1(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void updateOnClicnk(ActionEvent actionEvent) {
    }

    @Deprecated
    public void addOnClick(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void signOut(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.sceneSwitch(actionEvent, "shifat/GovernorDash.fxml", "Sign Out");

    }

    @Deprecated
    public void deleteOnClick(ActionEvent actionEvent) {
    }
}