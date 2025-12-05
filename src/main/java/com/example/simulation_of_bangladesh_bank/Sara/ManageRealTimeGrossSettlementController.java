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

public class ManageRealTimeGrossSettlementController
{
    @javafx.fxml.FXML
    private TextField rtgsID2;
    @javafx.fxml.FXML
    private TextField rtgsID;
    @javafx.fxml.FXML
    private TextField senderID;
    @javafx.fxml.FXML
    private TextField receiverID;
    @javafx.fxml.FXML
    private TableColumn<ManageRealTimeGrossSettlement, String> rtgsColumn;
    @javafx.fxml.FXML
    private TextField statusID;
    @javafx.fxml.FXML
    private TableColumn<ManageRealTimeGrossSettlement, String> statusColumn;
    @javafx.fxml.FXML
    private TableView<ManageRealTimeGrossSettlement> tableID;
    @javafx.fxml.FXML
    private TableColumn<ManageRealTimeGrossSettlement, String> senderColumn;
    @javafx.fxml.FXML
    private TableColumn<ManageRealTimeGrossSettlementController, String> receiverColumn;
    private List<ManageRealTimeGrossSettlement> manageRealTimeGrossSettlementControllerArrayList = new ArrayList<>();
    private File file = new File( "data/ManageRealTimeGrossSettlement.bin");

    @javafx.fxml.FXML
    public void initialize() {
        rtgsColumn.setCellValueFactory(new PropertyValueFactory<>("rtgsID"));
        senderColumn.setCellValueFactory(new PropertyValueFactory<>("sender"));
        receiverColumn.setCellValueFactory(new PropertyValueFactory<>("receiver"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));



        manageRealTimeGrossSettlementControllerArrayList = BinaryFileHelper.readAllObjects(file);
        tableID.getItems().clear();
        tableID.getItems().addAll(manageRealTimeGrossSettlementControllerArrayList);
    }

    @javafx.fxml.FXML
    public void deleteOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void signOut(ActionEvent actionEvent) throws IOException {
        SceneSwitching.sceneSwitch(actionEvent, "Sara/PaymentSystemOperatorDash.fxml", "Sign Out");

    }

    @javafx.fxml.FXML
    public void saveOnAction(ActionEvent actionEvent) {
        ManageRealTimeGrossSettlement manageRealTimeGrossSettlement  = new ManageRealTimeGrossSettlement(
                rtgsID.getText(),
                senderID.getText(),
                receiverID.getText(),
                statusID.getText()


        );

        manageRealTimeGrossSettlementControllerArrayList.add(manageRealTimeGrossSettlement);
        BinaryFileHelper.writeAllObjects(file, manageRealTimeGrossSettlementControllerArrayList);
    }
    }
