package com.example.simulation_of_bangladesh_bank.Sara;

import javafx.event.ActionEvent;

import java.io.IOException;

public class PaymentSystemOperatorDashController
{
    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void monitorTransaction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void managwRealTimeGross(ActionEvent actionEvent) throws IOException{
        SceneSwitching.sceneSwitch(actionEvent, "Sara/ManageRealTimeGrossSettlement.fxml", "Sign Out");

    }

    @javafx.fxml.FXML
    public void signOut(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void overseeautomated(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void processInterbankFundTransfers(ActionEvent actionEvent) throws IOException {
        SceneSwitching.sceneSwitch(actionEvent, "Sara/ProcessInterbankFundTransfers.fxml", "Sign Out");
    }
}