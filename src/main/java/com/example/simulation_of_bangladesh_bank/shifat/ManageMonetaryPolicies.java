package com.example.simulation_of_bangladesh_bank.shifat;

import java.io.Serializable;
import java.time.LocalDate;

public class ManageMonetaryPolicies implements Serializable {
    @javafx.fxml.FXML
    private String policyId;
    private String policyName;
    private String description ;
    private LocalDate date;
    private String status;

    public ManageMonetaryPolicies(String policyId, String status, LocalDate date, String description, String policyName) {
        this.policyId = policyId;
        this.status = status;
        this.date = date;
        this.description = description;
        this.policyName = policyName;
    }

    public String getPolicyId() {
        return policyId;
    }

    public void setPolicyId(String policyId) {
        this.policyId = policyId;
    }

    public String getPolicyName() {
        return policyName;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ManageMonetaryPolicies{" +
                "policyId='" + policyId + '\'' +
                ", policyName='" + policyName + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", status='" + status + '\'' +
                '}';
    }
}
