package com.example.simulation_of_bangladesh_bank.shifat;

import java.io.Serializable;
import java.time.LocalDate;

public class ManageAnnualPerformanceReports implements Serializable {
    String report;
    String bankName;
    LocalDate date;
    String remark;

    public ManageAnnualPerformanceReports(String report, String bankName, LocalDate date, String remark) {
        this.report = report;
        this.bankName = bankName;
        this.date = date;
        this.remark = remark;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ManageAnnualPerformanceReports{" +
                "report='" + report + '\'' +
                ", bankName='" + bankName + '\'' +
                ", date=" + date +
                ", remark='" + remark + '\'' +
                '}';
    }
}
