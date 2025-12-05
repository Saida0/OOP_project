package com.example.simulation_of_bangladesh_bank.shifat;

import java.io.Serializable;
import java.time.LocalDate;

public class ManageInflationReports implements Serializable {
    @javafx.fxml.FXML
    private String reportID;
    private LocalDate year;
    private String inflationRate;
    private String comment;
    private String status;

    public ManageInflationReports(String reportID, LocalDate year, String inflationRate, String comment, String status) {
        this.reportID = reportID;
        this.year = year;
        this.inflationRate = inflationRate;
        this.comment = comment;
        this.status = status;
    }

    public String getReportID() {
        return reportID;
    }

    public void setReportID(String reportID) {
        this.reportID = reportID;
    }

    public LocalDate getYear() {
        return year;
    }

    public void setYear(LocalDate year) {
        this.year = year;
    }

    public String getInflationRate() {
        return inflationRate;
    }

    public void setInflationRate(String inflationRate) {
        this.inflationRate = inflationRate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ManageInflationReports{" +
                "reportID='" + reportID + '\'' +
                ", year=" + year +
                ", inflationRate='" + inflationRate + '\'' +
                ", comment='" + comment + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
