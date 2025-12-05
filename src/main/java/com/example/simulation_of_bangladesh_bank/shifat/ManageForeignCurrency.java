package com.example.simulation_of_bangladesh_bank.shifat;

import java.io.Serializable;
import java.time.LocalDate;

public class ManageForeignCurrency implements Serializable {
    @javafx.fxml.FXML
    private String reserveID;
    private String amount;
    private String currencyType;
    private LocalDate date;

    public ManageForeignCurrency(String reserveID, LocalDate date, String currencyType, String amount) {
        this.reserveID = reserveID;
        this.date = date;
        this.currencyType = currencyType;
        this.amount = amount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getReserveID() {
        return reserveID;
    }

    public void setReserveID(String reserveID) {
        this.reserveID = reserveID;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ManageForeignCurrency{" +
                "reserveID='" + reserveID + '\'' +
                ", amount='" + amount + '\'' +
                ", currencyType='" + currencyType + '\'' +
                ", date=" + date +
                '}';
    }
}
