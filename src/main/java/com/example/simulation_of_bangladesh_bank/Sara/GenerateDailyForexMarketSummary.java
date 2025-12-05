package com.example.simulation_of_bangladesh_bank.Sara;

public class GenerateDailyForexMarketSummary {
    private String currencyId;
    private String tradeType;
    private String amount;
    private String rate;

    public GenerateDailyForexMarketSummary(String currencyId, String tradeType, String amount, String rate) {
        this.currencyId = currencyId;
        this.tradeType = tradeType;
        this.amount = amount;
        this.rate = rate;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "GenerateDailyForexMarketSummary{" +
                "currencyId='" + currencyId + '\'' +
                ", tradeType='" + tradeType + '\'' +
                ", amount='" + amount + '\'' +
                ", rate='" + rate + '\'' +
                '}';
    }
}
