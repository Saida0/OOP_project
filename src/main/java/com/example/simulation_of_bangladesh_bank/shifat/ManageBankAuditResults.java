package com.example.simulation_of_bangladesh_bank.shifat;

import java.io.Serializable;
import java.time.LocalDate;

public class ManageBankAuditResults implements Serializable {
    @javafx.fxml.FXML
    private String auditID;
    private LocalDate date;
    private String bankName;
    private String auditorName;
    private String summary;

    public ManageBankAuditResults(LocalDate date, String auditID, String bankName, String auditorName, String summary) {
        this.date = date;
        this.auditID = auditID;
        this.bankName = bankName;
        this.auditorName = auditorName;
        this.summary = summary;
    }

    public String getAuditID() {
        return auditID;
    }

    public void setAuditID(String auditID) {
        this.auditID = auditID;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAuditorName() {
        return auditorName;
    }

    public void setAuditorName(String auditorName) {
        this.auditorName = auditorName;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public String toString() {
        return "ManageBankAuditResults{" +
                "auditID='" + auditID + '\'' +
                ", date=" + date +
                ", bankName='" + bankName + '\'' +
                ", auditorName='" + auditorName + '\'' +
                ", summary='" + summary + '\'' +
                '}';
    }
}
