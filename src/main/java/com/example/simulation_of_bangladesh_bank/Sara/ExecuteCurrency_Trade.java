package com.example.simulation_of_bangladesh_bank.Sara;

public class ExecuteCurrency_Trade {

    String CurrencyPairs;
    String TransactionTpe;
    String Amount;
    String Rate;
    String CounterParty;


    public ExecuteCurrency_Trade(String currencyPairs, String transactionTpe, String amount, String rate, String counterParty) {
        CurrencyPairs = currencyPairs;
        TransactionTpe = transactionTpe;
        Amount = amount;
        Rate = rate;
        CounterParty = counterParty;
    }

    public String getCurrencyPairs() {
        return CurrencyPairs;
    }

    public void setCurrencyPairs(String currencyPairs) {
        CurrencyPairs = currencyPairs;
    }

    public String getTransactionTpe() {
        return TransactionTpe;
    }

    public void setTransactionTpe(String transactionTpe) {
        TransactionTpe = transactionTpe;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getRate() {
        return Rate;
    }

    public void setRate(String rate) {
        Rate = rate;
    }

    public String getCounterParty() {
        return CounterParty;
    }

    public void setCounterParty(String counterParty) {
        CounterParty = counterParty;
    }

    @Override
    public String toString() {
        return "ExecuteCurrency_Trade{" +
                "CurrencyPairs='" + CurrencyPairs + '\'' +
                ", TransactionTpe='" + TransactionTpe + '\'' +
                ", Amount='" + Amount + '\'' +
                ", Rate='" + Rate + '\'' +
                ", CounterParty='" + CounterParty + '\'' +
                '}';
    }
}
