package com.example.simulation_of_bangladesh_bank.shifat;

import java.io.Serializable;
import java.time.LocalDate;

public class ManageNational_InterestRates implements Serializable {
    @javafx.fxml.FXML
    private String rateID;
    private String rateType;
    private String percentage ;
    private LocalDate effectiveDate;

    public ManageNational_InterestRates(String rateID, String rateType, String percentage, LocalDate effectiveDate) {
        this.rateID = rateID;
        this.rateType = rateType;
        this.percentage = percentage;
        this.effectiveDate = effectiveDate;
    }

    public String getRateID() {
        return rateID;
    }

    public void setRateID(String rateID) {
        this.rateID = rateID;
    }

    public String getRateType() {
        return rateType;
    }

    public void setRateType(String rateType) {
        this.rateType = rateType;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    @Override
    public String toString() {
        return "ManageNational_InterestRates{" +
                "rateID='" + rateID + '\'' +
                ", rateType='" + rateType + '\'' +
                ", percentage='" + percentage + '\'' +
                ", effectiveDate=" + effectiveDate +
                '}';
    }
}
