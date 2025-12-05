package com.example.simulation_of_bangladesh_bank.Sara;

import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GenerateDailyForexMarketSummaryController
{
    @javafx.fxml.FXML
    private TextField currencyID2;
    @javafx.fxml.FXML
    private TableColumn<GenerateDailyForexMarketSummary, String> currencyColumn;
    @javafx.fxml.FXML
    private TextField amountID;
    @javafx.fxml.FXML
    private TableColumn<GenerateDailyForexMarketSummary, String> rateColumn;
    @javafx.fxml.FXML
    private TableColumn<GenerateDailyForexMarketSummary, String> tradeColumn;
    @javafx.fxml.FXML
    private TableColumn<GenerateDailyForexMarketSummary, String> amountColumn;
    @javafx.fxml.FXML
    private TextField currencyID;
    @javafx.fxml.FXML
    private Text rateID;
    @javafx.fxml.FXML
    private ComboBox<String> tradeType;
    @javafx.fxml.FXML
    private TableView<GenerateDailyForexMarketSummary> tableID;
    private List<GenerateDailyForexMarketSummary> generateDailyForexMarketSummaryArrayList = new ArrayList<>();
    private File file = new File( "data/GenerateDailyForexMarketSummary.bin");

    @javafx.fxml.FXML
    public void initialize() {
        currencyColumn.setCellValueFactory(new PropertyValueFactory<>("CurrencyPairs"));
        tradeColumn.setCellValueFactory(new PropertyValueFactory<>("TransactionTpe"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        rateColumn.setCellValueFactory(new PropertyValueFactory<>("Amount"));


        generateDailyForexMarketSummaryArrayList = BinaryFileHelper.readAllObjects(file);
        tableID.getItems().clear();
        tableID.getItems().addAll(generateDailyForexMarketSummaryArrayList);
    }

    @javafx.fxml.FXML
    public void deleteOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void signOut(ActionEvent actionEvent) throws IOException {
        SceneSwitching.sceneSwitch(actionEvent, "Sara/ForeignExchangeDealer_Dash.fxml", "Sign Out");

    }

    @javafx.fxml.FXML
    public void saveOnAction(ActionEvent actionEvent) {
        GenerateDailyForexMarketSummary generateDailyForexMarketSummary = new GenerateDailyForexMarketSummary(
                currencyID.getText(),
                tradeType.getValue(),
                amountID.getText(),
                rateID.getText()


        );

        generateDailyForexMarketSummaryArrayList.add(generateDailyForexMarketSummary);
        BinaryFileHelper.writeAllObjects(file, generateDailyForexMarketSummaryArrayList);
    }
}