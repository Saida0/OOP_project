package com.example.simulation_of_bangladesh_bank.Sara;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExecuteCurrencyTrade {
    @javafx.fxml.FXML
    private TableColumn <ExecuteCurrency_Trade, String>currencyTableColumn;
    @javafx.fxml.FXML
    private TableColumn<ExecuteCurrency_Trade, String> amountTableColumn;
    @javafx.fxml.FXML
    private RadioButton buyRadioButton;
    @javafx.fxml.FXML
    private TextField counterPartyTextField;
    @javafx.fxml.FXML
    private TextField amountTextField;
    @javafx.fxml.FXML
    private TableColumn<ExecuteCurrency_Trade,String> transactionTypeTableColumn;
    @javafx.fxml.FXML
    private TextField rateTextField;
    @javafx.fxml.FXML
    private RadioButton sellRadioButton;
    @javafx.fxml.FXML
    private TableView <ExecuteCurrency_Trade> currencyTableView;
    @javafx.fxml.FXML
    private VBox currencyVBox;
    @javafx.fxml.FXML
    private ComboBox<String> currencyPairComboBox;


    private List<ExecuteCurrency_Trade> executeCurrency_tradeArrayList = new ArrayList<>();
    private File file = new File( "data/ExecuteCurrency_Trade.bin");

    @javafx.fxml.FXML
    public void initialize() {
        currencyTableColumn.setCellValueFactory(new PropertyValueFactory<>("CurrencyPairs"));
        transactionTypeTableColumn.setCellValueFactory(new PropertyValueFactory<>("TransactionTpe"));
        amountTableColumn.setCellValueFactory(new PropertyValueFactory<>("Amount"));


        executeCurrency_tradeArrayList = BinaryFileHelper.readAllObjects(file);
        currencyTableView.getItems().clear();
        currencyTableView.getItems().addAll(executeCurrency_tradeArrayList);



    }


    @javafx.fxml.FXML
    public void clearFormOrderOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void backOnAction(ActionEvent actionEvent) throws IOException {
        SceneSwitching.sceneSwitch(actionEvent, "Sara/ForeignExchangeDealer_Dash.fxml", "Back");
    }

    @javafx.fxml.FXML
    public void updateOrderOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void newOrderOnAction(ActionEvent actionEvent) {
        ExecuteCurrency_Trade ExecuteCurrency_Trade = new ExecuteCurrency_Trade(
                currencyPairComboBox.getValue(),
                sellRadioButton.getId(),
                amountTextField.getText(),
                rateTextField.getText(),
                counterPartyTextField.getText()



        );

        executeCurrency_tradeArrayList.add(ExecuteCurrency_Trade);
        BinaryFileHelper.writeAllObjects(file, executeCurrency_tradeArrayList);
    }

    @javafx.fxml.FXML
    public void cancelOrderOnAction(ActionEvent actionEvent) {
    }

    public List<ExecuteCurrency_Trade> getExecuteCurrencyTrade() {
        return executeCurrency_tradeArrayList;
    }

    public void setExecuteCurrencyTrade(List<ExecuteCurrency_Trade> executeCurrencyTrade) {
        this.executeCurrency_tradeArrayList = executeCurrencyTrade;
    }
}
