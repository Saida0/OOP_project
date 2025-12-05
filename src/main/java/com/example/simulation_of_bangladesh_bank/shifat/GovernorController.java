package com.example.simulation_of_bangladesh_bank.shifat;

import javafx.event.ActionEvent;

import java.io.IOException;

public class GovernorController
{
    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void inflationReports(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.sceneSwitch(actionEvent, "shifat/ManageInflationReports.fxml", "Sign Out");

    }

    @javafx.fxml.FXML
    public void interestRates(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.sceneSwitch(actionEvent, "shifat/ManageNational_InterestRates.fxml", "Manage Interest Rate");

    }

    @javafx.fxml.FXML
    public void loadApprovals(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.sceneSwitch(actionEvent, "shifat/ManageLoanApprovals.fxml", "Manage Loan");

    }

    @javafx.fxml.FXML
    public void foreignReservers(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.sceneSwitch(actionEvent, "shifat/ManageForeignCurrency.fxml", "Manage Forign Currency");

    }

    @javafx.fxml.FXML
    public void commercialBanks(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.sceneSwitch(actionEvent, "shifat/ManageCommercialBank.fxml", "Manage Commercial Bank");
    }

    @javafx.fxml.FXML
    public void signOut(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.sceneSwitch(actionEvent, "shifat/ManageCommercialBank.fxml", "Sign Out");

    }

    @javafx.fxml.FXML
    public void circularsOnClick(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.sceneSwitch(actionEvent, "shifat/ManageCircularsNotices.fxml", "Manage Circulars");

    }

    @javafx.fxml.FXML
    public void monetaryPolicies(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.sceneSwitch(actionEvent, "shifat/ManageMonetaryPolicies.fxml", "Manage Monetary Policies");

    }

    @javafx.fxml.FXML
    public void staffInfo(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.sceneSwitch(actionEvent, "shifat/ManageStaffInformation.fxml", "Manage Staff Info");

    }
}