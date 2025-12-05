package com.example.simulation_of_bangladesh_bank.Sara;

import javafx.event.ActionEvent;

import java.io.IOException;

public class ForeignExchangeDealer_DashController
{
    @javafx.fxml.FXML
    public void initialize() {


    }

    @Deprecated
    public void managedailyexchangeOnAction(ActionEvent actionEvent) throws IOException {
        SceneSwitching.sceneSwitch(actionEvent, "Sara/ForeignExchangeDealer.fxml", "Foreign Exchange Dealer");
    }

    @javafx.fxml.FXML
    public void manageDailyExchangeRateUpdates(ActionEvent actionEvent){
    }

    @javafx.fxml.FXML
    public void verifyCompliancewithForexPolicies(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void maintainForeignReserveRecords(ActionEvent actionEvent) throws IOException{
        SceneSwitching.sceneSwitch(actionEvent, "Sara/MaintainForeignReserveRecords.fxml", "Sign Out");

    }

    @javafx.fxml.FXML
    public void generateDailyForexMarketSummary(ActionEvent actionEvent) throws IOException{
        SceneSwitching.sceneSwitch(actionEvent, "Sara/GenerateDailyForexMarketSummary.fxml", "Sign Out");

    }

    @javafx.fxml.FXML
    public void signOut(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void executeCurrencyBuy_SellOrders(ActionEvent actionEvent) throws IOException{
        SceneSwitching.sceneSwitch(actionEvent, "Sara/executeCurrencyBuy_SellOrders.fxml", "Sign Out");

    }

    @javafx.fxml.FXML
    public void forecastForeignCurrencyDemand(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void monitorExchangeRateVolatility(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void recordCrossBorderRemittanceTransactions(ActionEvent actionEvent) {
    }
}