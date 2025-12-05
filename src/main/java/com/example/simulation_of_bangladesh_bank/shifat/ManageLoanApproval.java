package com.example.simulation_of_bangladesh_bank.shifat;

import java.io.Serializable;
import java.time.LocalDate;

public class ManageLoanApproval implements Serializable {
    @javafx.fxml.FXML
    private String loanId;
    private String bankName;
    private int amountRequested;
    private String purpose ;
    private LocalDate applicationDate;
    private String status;

    public ManageLoanApproval(String status, LocalDate applicationDate, String purpose, int amountRequested, String bankName, String loanId) {
        this.status = status;
        this.applicationDate = applicationDate;
        this.purpose = purpose;
        this.amountRequested = amountRequested;
        this.bankName = bankName;
        this.loanId = loanId;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public int getAmountRequested() {
        return amountRequested;
    }

    public void setAmountRequested(int amountRequested) {
        this.amountRequested = amountRequested;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public LocalDate getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(LocalDate applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ManageLoan{" +
                "loanId='" + loanId + '\'' +
                ", bankName='" + bankName + '\'' +
                ", amountRequested=" + amountRequested +
                ", purpose='" + purpose + '\'' +
                ", applicationDate=" + applicationDate +
                ", status='" + status + '\'' +
                '}';
    }
}
