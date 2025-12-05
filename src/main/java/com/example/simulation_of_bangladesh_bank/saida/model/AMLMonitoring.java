package com.example.simulation_of_bangladesh_bank.saida.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;

/**
 * AMLMonitoring - Anti-Money Laundering compliance monitoring
 * Used by Commercial Bank Manager to supervise AML compliance
 */
public class AMLMonitoring implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Additional fields
    private String caseId;
    private String accountNumber;
    private String riskLevel;
    private LocalDate flaggedDate;
    private String status;
    private String description;
    private String monitoringId;
    private String bankId;
    private String transactionId;
    private String riskFlag; // Low/Medium/High/Critical
    private String investigationNotes;
    private LocalDate reviewDate;
    private String investigationStatus; // Pending/UnderReview/Cleared/Escalated
    private double transactionAmount;
    private String transactionType;
    private String accountHolder;
    private List<String> redFlags;

    // Constructors
    public AMLMonitoring() {
        this.reviewDate = LocalDate.now();
        this.investigationStatus = "Pending";
        this.redFlags = new ArrayList<>();
    }

    public AMLMonitoring(String monitoringId, String bankId, String transactionId,
                        double transactionAmount, String transactionType) {
        this.monitoringId = monitoringId;
        this.bankId = bankId;
        this.transactionId = transactionId;
        this.transactionAmount = transactionAmount;
        this.transactionType = transactionType;
        this.reviewDate = LocalDate.now();
        this.investigationStatus = "Pending";
        this.redFlags = new ArrayList<>();
        assessRiskLevel();
    }

    // Business Methods
    public void loadSuspiciousReports(List<String> reports) {
        // Simulate loading suspicious transaction reports
        for (String report : reports) {
            if (report.contains("large_cash") || report.contains("structured")) {
                this.redFlags.add("Structured Transaction Pattern");
            }
            if (report.contains("high_risk_country")) {
                this.redFlags.add("High Risk Geographic Origin");
            }
            if (report.contains("rapid_movement")) {
                this.redFlags.add("Rapid Fund Movement");
            }
        }
        assessRiskLevel();
    }

    public boolean validateAMLRedFlags() {
        // AML Red Flag Detection
        boolean hasRedFlags = false;
        
        // Check for large cash transactions (> 10 lakh BDT)
        if (transactionAmount > 1000000) {
            redFlags.add("Large Cash Transaction (> 10 Lakh BDT)");
            hasRedFlags = true;
        }
        
        // Check for round-figure transactions
        if (transactionAmount % 100000 == 0 && transactionAmount >= 500000) {
            redFlags.add("Suspicious Round Figure Transaction");
            hasRedFlags = true;
        }
        
        // Check transaction type
        if (transactionType != null && 
            (transactionType.equalsIgnoreCase("wire_international") || 
             transactionType.equalsIgnoreCase("cash_deposit_multiple"))) {
            redFlags.add("High-Risk Transaction Type: " + transactionType);
            hasRedFlags = true;
        }
        
        assessRiskLevel();
        return hasRedFlags;
    }

    private void assessRiskLevel() {
        int flagCount = redFlags.size();
        
        if (flagCount == 0) {
            this.riskFlag = "Low";
        } else if (flagCount == 1) {
            this.riskFlag = "Medium";
        } else if (flagCount == 2) {
            this.riskFlag = "High";
        } else {
            this.riskFlag = "Critical";
        }
    }

    public String generateAMLReport() {
        StringBuilder report = new StringBuilder();
        report.append("===== AML MONITORING REPORT =====\n");
        report.append("Monitoring ID: ").append(monitoringId).append("\n");
        report.append("Bank ID: ").append(bankId).append("\n");
        report.append("Review Date: ").append(reviewDate).append("\n\n");
        
        report.append("Transaction Details:\n");
        report.append("  Transaction ID: ").append(transactionId).append("\n");
        report.append("  Amount: BDT ").append(String.format("%.2f", transactionAmount)).append("\n");
        report.append("  Type: ").append(transactionType).append("\n");
        report.append("  Account Holder: ").append(accountHolder != null ? accountHolder : "N/A").append("\n\n");
        
        report.append("Risk Assessment:\n");
        report.append("  Risk Level: ").append(riskFlag).append("\n");
        report.append("  Red Flags Identified: ").append(redFlags.size()).append("\n");
        
        if (!redFlags.isEmpty()) {
            report.append("\nRed Flags:\n");
            for (int i = 0; i < redFlags.size(); i++) {
                report.append("  ").append(i + 1).append(". ").append(redFlags.get(i)).append("\n");
            }
        }
        
        report.append("\nInvestigation Status: ").append(investigationStatus).append("\n");
        if (investigationNotes != null && !investigationNotes.isEmpty()) {
            report.append("Investigation Notes: ").append(investigationNotes).append("\n");
        }
        
        report.append("=================================\n");
        return report.toString();
    }

    public void recordInvestigation(String notes, String newStatus) {
        this.investigationNotes = notes;
        this.investigationStatus = newStatus;
        this.reviewDate = LocalDate.now();
    }

    public boolean requiresEscalation() {
        return riskFlag.equals("High") || riskFlag.equals("Critical");
    }

    public String submitToRegulatory() {
        if (requiresEscalation()) {
            this.investigationStatus = "Escalated";
            return "AML Report " + monitoringId + " submitted to Bangladesh Financial Intelligence Unit (BFIU)";
        }
        return "Escalation not required for current risk level";
    }

    // Getters and Setters
    public String getMonitoringId() {
        return monitoringId;
    }

    public void setMonitoringId(String monitoringId) {
        this.monitoringId = monitoringId;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    // Alias methods for controller compatibility
    public String getTransactionID() {
        return transactionId;
    }

    public void setTransactionID(String transactionId) {
        this.transactionId = transactionId;
    }

    // No-arg overloads for controller compatibility
    public void loadSuspiciousReports() {
        assessRiskLevel();
    }

    public void recordInvestigation() {
        this.reviewDate = LocalDate.now();
    }

    public String getRiskFlag() {
        return riskFlag;
    }

    public void setRiskFlag(String riskFlag) {
        this.riskFlag = riskFlag;
    }

    public String getInvestigationNotes() {
        return investigationNotes;
    }

    public void setInvestigationNotes(String investigationNotes) {
        this.investigationNotes = investigationNotes;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getInvestigationStatus() {
        return investigationStatus;
    }

    public void setInvestigationStatus(String investigationStatus) {
        this.investigationStatus = investigationStatus;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    public List<String> getRedFlags() {
        return redFlags;
    }

    public void addRedFlag(String flag) {
        this.redFlags.add(flag);
        assessRiskLevel();
    }

    // Additional getters and setters
    public String getCaseId() { return caseId; }
    public void setCaseId(String caseId) { this.caseId = caseId; }
    
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    
    public String getRiskLevel() { return riskLevel; }
    public void setRiskLevel(String riskLevel) { this.riskLevel = riskLevel; }
    
    public LocalDate getFlaggedDate() { return flaggedDate; }
    public void setFlaggedDate(LocalDate flaggedDate) { this.flaggedDate = flaggedDate; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return "AMLMonitoring{" +
                "caseId='" + caseId + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", riskLevel='" + riskLevel + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

