package com.example.simulation_of_bangladesh_bank.shifat;

import javafx.event.ActionEvent;

import java.io.IOException;

public class DirectorofBankingRegulationController
{
    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void ManageLoanRequests(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.sceneSwitch(actionEvent, "shifat/ManageLoanRequests.fxml", "Manage Bank");

    }

    @javafx.fxml.FXML
    public void ManageBankComplianceReports(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.sceneSwitch(actionEvent, "shifat/ManageBankComplianceReports.fxml", "Manage Bank");

    }

    @javafx.fxml.FXML
    public void ManageAnnualPerformanceReports(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.sceneSwitch(actionEvent, "shifat/ManageAnnualPerformanceReports.fxml", "Manage Bank");

    }

    @javafx.fxml.FXML
    public void SuperviseCommercialBanks(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.sceneSwitch(actionEvent, "shifat/SuperviseCommercialBanks.fxml", "Manage Bank");

    }

    @javafx.fxml.FXML
    public void ManageBankAuditResults(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.sceneSwitch(actionEvent, "shifat/ManageBankAuditResults.fxml", "Manage Bank");

    }

    @javafx.fxml.FXML
    public void signOut(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.sceneSwitch(actionEvent, "shifat/ManageInflationReports.fxml", "Manage Bank");

    }

    @javafx.fxml.FXML
    public void ManageircularsforBanks(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.sceneSwitch(actionEvent, "shifat/ManageCircularsForBanks.fxml", "Manage Bank");

    }

    @javafx.fxml.FXML
    public void ManageInspectionSchedules(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.sceneSwitch(actionEvent, "shifat/ManageInspectionSchedules.fxml", "Manage Bank");

    }

    @javafx.fxml.FXML
    public void ManagePenaltyRecords(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.sceneSwitch(actionEvent, "shifat/ManagePenaltyRecords.fxml", "Manage Bank");

    }
}