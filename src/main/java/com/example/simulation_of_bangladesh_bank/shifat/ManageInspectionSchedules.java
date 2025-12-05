package com.example.simulation_of_bangladesh_bank.shifat;

import java.io.Serializable;
import java.time.LocalDate;

public class ManageInspectionSchedules implements Serializable {
    String inspectionID;
    String bankName;
    LocalDate date;
    String inspectorName;
    String status;

    public ManageInspectionSchedules(String inspectionID, String bankName, LocalDate date, String inspectorName, String status) {
        this.inspectionID = inspectionID;
        this.bankName = bankName;
        this.date = date;
        this.inspectorName = inspectorName;
        this.status = status;
    }

    public String getInspectionID() {
        return inspectionID;
    }

    public void setInspectionID(String inspectionID) {
        this.inspectionID = inspectionID;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getInspectorName() {
        return inspectorName;
    }

    public void setInspectorName(String inspectorName) {
        this.inspectorName = inspectorName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ManageInspectionSchedules{" +
                "inspectionID='" + inspectionID + '\'' +
                ", bankName='" + bankName + '\'' +
                ", date=" + date +
                ", inspectorName='" + inspectorName + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
