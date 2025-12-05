package com.example.simulation_of_bangladesh_bank.Sara;

public class MaintainForeignReserveRecords {
    private String currencyID;
    private String amount;
    private String update;

    public MaintainForeignReserveRecords(String currencyID, String amount, String update) {
        this.currencyID = currencyID;
        this.amount = amount;
        this.update = update;
    }

    public String getCurrencyID() {
        return currencyID;
    }

    public void setCurrencyID(String currencyID) {
        this.currencyID = currencyID;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    @Override
    public String toString() {
        return "MaintainForeignReserveRecords{" +
                "currencyID='" + currencyID + '\'' +
                ", amount='" + amount + '\'' +
                ", update='" + update + '\'' +
                '}';
    }
}
