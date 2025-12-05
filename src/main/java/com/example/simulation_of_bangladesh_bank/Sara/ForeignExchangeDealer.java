package com.example.simulation_of_bangladesh_bank.Sara;

import java.io.Serializable;
import java.time.LocalDate;

public class ForeignExchangeDealer implements Serializable{
    private String bankId;
    private String currencyCode;
    private String buyingRate;
    private String sellingRate;
    private LocalDate effectiveDate;

    public ForeignExchangeDealer(String bankId, String currencyCode, String buyingRate, String sellingRate, LocalDate effectiveDate) {
        this.bankId = bankId;
        this.currencyCode = currencyCode;
        this.buyingRate = buyingRate;
        this.sellingRate = sellingRate;
        this.effectiveDate = effectiveDate;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getBuyingRate() {
        return buyingRate;
    }

    public void setBuyingRate(String buyingRate) {
        this.buyingRate = buyingRate;
    }

    public String getSellingRate() {
        return sellingRate;
    }

    public void setSellingRate(String sellingRate) {
        this.sellingRate = sellingRate;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    @Override
    public String toString() {
        return "ForeignExchangeDealer{" +
                "bankId='" + bankId + '\'' +
                ", currencyCode='" + currencyCode + '\'' +
                ", buyingRate='" + buyingRate + '\'' +
                ", sellingRate='" + sellingRate + '\'' +
                ", effectiveDate=" + effectiveDate +
                '}';
    }
}
