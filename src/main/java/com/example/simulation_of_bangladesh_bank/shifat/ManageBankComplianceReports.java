package com.example.simulation_of_bangladesh_bank.shifat;

import java.io.Serializable;
import java.time.LocalDate;

public class ManageBankComplianceReports implements Serializable {
    private String reportID;
    private LocalDate submissionDate;
    private String bankName;
    private String summary;
    private String status;

    public ManageBankComplianceReports(String status, String summary, String bankName, LocalDate submissionDate, String reportID) {
        this.status = status;
        this.summary = summary;
        this.bankName = bankName;
        this.submissionDate = submissionDate;
        this.reportID = reportID;
    }

    public String getReportID() {
        return reportID;
    }

    public void setReportID(String reportID) {
        this.reportID = reportID;
    }

    public LocalDate getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDate submissionDate) {
        this.submissionDate = submissionDate;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ManageBankComplianceReports{" +
                "reportID='" + reportID + '\'' +
                ", submissionDate=" + submissionDate +
                ", bankName='" + bankName + '\'' +
                ", summary='" + summary + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
