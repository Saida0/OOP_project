package com.example.simulation_of_bangladesh_bank.Sara;

import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProcessInterbankFundTransfersController
{
    @javafx.fxml.FXML
    private TextField requestID1;
    @javafx.fxml.FXML
    private TextField senderID;
    @javafx.fxml.FXML
    private TextField receiverID;
    @javafx.fxml.FXML
    private TextField amountID2;
    @javafx.fxml.FXML
    private TextField amountID;
    @javafx.fxml.FXML
    private TableColumn<ProcessInterbankFundTransfers, String> requestColumn;
    @javafx.fxml.FXML
    private TableColumn<ProcessInterbankFundTransfers, String> amountColumn;
    @javafx.fxml.FXML
    private TableView<ProcessInterbankFundTransfers> tableID;
    @javafx.fxml.FXML
    private TableColumn<ProcessInterbankFundTransfers, String> senderColumn;
    @javafx.fxml.FXML
    private TableColumn<ProcessInterbankFundTransfers, String> receiverColumn;
    @javafx.fxml.FXML
    private TextField requestID3;
    @javafx.fxml.FXML
    private TextField requestID2;
    private List<ProcessInterbankFundTransfers> processInterbankFundTransfersArrayList = new ArrayList<>();
    private File file = new File( "data/ProcessInterbankFundTransfers.bin");

    @javafx.fxml.FXML
    public void initialize() {
        requestColumn.setCellValueFactory(new PropertyValueFactory<>( "request"));
        senderColumn.setCellValueFactory(new PropertyValueFactory<>( "sender"));
        receiverColumn.setCellValueFactory(new PropertyValueFactory<>( "receiver"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>( "amount"));

        processInterbankFundTransfersArrayList = BinaryFileHelper.readAllObjects(file);
        tableID.getItems().clear();
        tableID.getItems().addAll(processInterbankFundTransfersArrayList);
    }

    @javafx.fxml.FXML
    public void updateOnClick(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void deleteOnClick(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void saveOnClick(ActionEvent actionEvent) {
        ProcessInterbankFundTransfers processInterbankFundTransfers = new ProcessInterbankFundTransfers(
                requestID1.getText(),
                senderID.getText(),
                receiverID.getText(),
                amountID.getText()


        );

        processInterbankFundTransfersArrayList.add(processInterbankFundTransfers);
        BinaryFileHelper.writeAllObjects(file, processInterbankFundTransfersArrayList);
    }

    @javafx.fxml.FXML
    public void signOut(ActionEvent actionEvent) throws IOException {
        SceneSwitching.sceneSwitch(actionEvent, "Sara/PaymentSystemOperatorDash.fxml", "Sign Out");

    }
}