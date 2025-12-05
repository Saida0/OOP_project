package com.example.simulation_of_bangladesh_bank.shifat;

import java.io.Serializable;
import java.time.LocalDate;

public class ManagePenaltyRecords implements Serializable {
    String penaltyID;
    String bankName;
    String fineAmount;
    String reason;
    LocalDate dateIssued;

    public ManagePenaltyRecords(String penaltyID, String bankName, String fineAmount, String reason, LocalDate dateIssued) {
        this.penaltyID = penaltyID;
        this.bankName = bankName;
        this.fineAmount = fineAmount;
        this.reason = reason;
        this.dateIssued = dateIssued;
    }

    public String getPenaltyID() {
        return penaltyID;
    }

    public void setPenaltyID(String penaltyID) {
        this.penaltyID = penaltyID;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(String fineAmount) {
        this.fineAmount = fineAmount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDate getDateIssued() {
        return dateIssued;
    }

    public void setDateIssued(LocalDate dateIssued) {
        this.dateIssued = dateIssued;
    }

    @Override
    public String toString() {
        return "ManagePenaltyRecords{" +
                "penaltyID='" + penaltyID + '\'' +
                ", bankName='" + bankName + '\'' +
                ", fineAmount='" + fineAmount + '\'' +
                ", reason='" + reason + '\'' +
                ", dateIssued=" + dateIssued +
                '}';
    }
}
