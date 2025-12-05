package com.example.simulation_of_bangladesh_bank.saida.model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * SLRCompliance - Manages Statutory Liquidity Reserve requirements
 * Used by Commercial Bank Manager to ensure banks maintain required reserves
 */
public class SLRCompliance implements Serializable {
    private static final long serialVersionUID = 1L;
    private String complianceId;
    private String bankId;
    private double currentDeposits;
    private double reserveHoldings;
    private double requiredSLR;
    private double slrPercentage = 13.0; // Default Bangladesh Bank SLR rate
    private LocalDate complianceDate;
    private String status; // Compliant/Non-Compliant/Pending

    // Constructors
    public SLRCompliance() {
        this.complianceDate = LocalDate.now();
        this.status = "Pending";
    }

    public SLRCompliance(String complianceId, String bankId, double currentDeposits, 
                         double reserveHoldings, double slrPercentage) {
        this.complianceId = complianceId;
        this.bankId = bankId;
        this.currentDeposits = currentDeposits;
        this.reserveHoldings = reserveHoldings;
        this.slrPercentage = slrPercentage;
        this.complianceDate = LocalDate.now();
        calculateSLRRequirement();
    }

    // Business Methods
    public double calculateSLRRequirement() {
        this.requiredSLR = (currentDeposits * slrPercentage) / 100;
        return this.requiredSLR;
    }

    public boolean validateCompliance() {
        if (reserveHoldings >= requiredSLR) {
            this.status = "Compliant";
            return true;
        } else {
            this.status = "Non-Compliant";
            return false;
        }
    }

    public String generateCertificate() {
        StringBuilder certificate = new StringBuilder();
        certificate.append("===== SLR COMPLIANCE CERTIFICATE =====\n");
        certificate.append("Compliance ID: ").append(complianceId).append("\n");
        certificate.append("Bank ID: ").append(bankId).append("\n");
        certificate.append("Date: ").append(complianceDate).append("\n");
        certificate.append("Current Deposits: BDT ").append(String.format("%.2f", currentDeposits)).append("\n");
        certificate.append("Required SLR (").append(slrPercentage).append("%): BDT ")
                   .append(String.format("%.2f", requiredSLR)).append("\n");
        certificate.append("Reserve Holdings: BDT ").append(String.format("%.2f", reserveHoldings)).append("\n");
        certificate.append("Status: ").append(status).append("\n");
        certificate.append("======================================\n");
        return certificate.toString();
    }

    public double getDeficitAmount() {
        if (reserveHoldings < requiredSLR) {
            return requiredSLR - reserveHoldings;
        }
        return 0;
    }

    // Getters and Setters
    public String getComplianceId() {
        return complianceId;
    }

    public void setComplianceId(String complianceId) {
        this.complianceId = complianceId;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public double getCurrentDeposits() {
        return currentDeposits;
    }

    public void setCurrentDeposits(double currentDeposits) {
        this.currentDeposits = currentDeposits;
        calculateSLRRequirement();
    }

    public double getReserveHoldings() {
        return reserveHoldings;
    }

    public void setReserveHoldings(double reserveHoldings) {
        this.reserveHoldings = reserveHoldings;
    }

    public double getRequiredSLR() {
        return requiredSLR;
    }

    public double getSlrPercentage() {
        return slrPercentage;
    }

    public void setSlrPercentage(double slrPercentage) {
        this.slrPercentage = slrPercentage;
        calculateSLRRequirement();
    }

    public LocalDate getComplianceDate() {
        return complianceDate;
    }

    public void setComplianceDate(LocalDate complianceDate) {
        this.complianceDate = complianceDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SLRCompliance{" +
                "complianceId='" + complianceId + '\'' +
                ", bankId='" + bankId + '\'' +
                ", requiredSLR=" + requiredSLR +
                ", reserveHoldings=" + reserveHoldings +
                ", status='" + status + '\'' +
                '}';
    }
}

