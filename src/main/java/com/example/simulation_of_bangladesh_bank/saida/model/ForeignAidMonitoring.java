package com.example.simulation_of_bangladesh_bank.saida.model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * ForeignAidMonitoring - Monitors foreign aid and loan utilization
 * Used by Ministry of Finance Representative for aid management
 */
public class ForeignAidMonitoring implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Additional fields
    private double committedAmount;
    private LocalDate completionDate;
    private String aidId;
    private String projectName;
    private String donorAgency;
    private double aidAmount;
    private double disbursedAmount;
    private double utilizedAmount;
    private String utilizationStatus; // OnTrack/Delayed/Completed/Suspended
    private double utilizationRate;
    private LocalDate agreementDate;
    private LocalDate projectEndDate;
    private String projectSector;
    private String implementingAgency;
    private String bottlenecks;
    private String remarks;

    // Constructors
    public ForeignAidMonitoring() {
        this.utilizationStatus = "OnTrack";
    }

    public ForeignAidMonitoring(String aidId, String projectName, String donorAgency,
                                double aidAmount, String projectSector) {
        this.aidId = aidId;
        this.projectName = projectName;
        this.donorAgency = donorAgency;
        this.aidAmount = aidAmount;
        this.projectSector = projectSector;
        this.utilizationStatus = "OnTrack";
        this.agreementDate = LocalDate.now();
    }

    // Business Methods
    public void loadAidData(double disbursedAmount, double utilizedAmount, 
                           String implementingAgency) {
        this.disbursedAmount = disbursedAmount;
        this.utilizedAmount = utilizedAmount;
        this.implementingAgency = implementingAgency;
        calculateUtilizationRate();
    }

    private void calculateUtilizationRate() {
        if (disbursedAmount > 0) {
            this.utilizationRate = (utilizedAmount / disbursedAmount) * 100;
        } else {
            this.utilizationRate = 0;
        }
        assessUtilizationStatus();
    }

    private void assessUtilizationStatus() {
        if (utilizedAmount >= aidAmount) {
            this.utilizationStatus = "Completed";
        } else if (utilizationRate >= 80) {
            this.utilizationStatus = "OnTrack";
        } else if (utilizationRate >= 50) {
            this.utilizationStatus = "Delayed";
        } else {
            this.utilizationStatus = "CriticallyDelayed";
        }
    }

    public boolean validateAidUse() {
        // Validate that utilized amount doesn't exceed disbursed
        if (utilizedAmount > disbursedAmount) {
            return false;
        }
        // Validate that disbursed doesn't exceed total aid
        if (disbursedAmount > aidAmount) {
            return false;
        }
        return true;
    }

    public String generateAidReport() {
        StringBuilder report = new StringBuilder();
        report.append("===== FOREIGN AID UTILIZATION REPORT =====\n");
        report.append("Aid ID: ").append(aidId).append("\n");
        report.append("Project Name: ").append(projectName).append("\n");
        report.append("Donor Agency: ").append(donorAgency).append("\n");
        report.append("Sector: ").append(projectSector).append("\n");
        report.append("Implementing Agency: ").append(implementingAgency != null ? implementingAgency : "N/A").append("\n\n");
        
        report.append("Financial Status:\n");
        report.append("  Total Aid Amount: USD ").append(String.format("%.2f", aidAmount)).append(" Million\n");
        report.append("  Disbursed Amount: USD ").append(String.format("%.2f", disbursedAmount)).append(" Million\n");
        report.append("  Utilized Amount: USD ").append(String.format("%.2f", utilizedAmount)).append(" Million\n");
        report.append("  Remaining: USD ").append(String.format("%.2f", aidAmount - utilizedAmount)).append(" Million\n\n");
        
        report.append("Performance Metrics:\n");
        report.append("  Disbursement Rate: ").append(String.format("%.2f", (disbursedAmount / aidAmount) * 100)).append("%\n");
        report.append("  Utilization Rate: ").append(String.format("%.2f", utilizationRate)).append("%\n");
        report.append("  Status: ").append(utilizationStatus).append("\n\n");
        
        report.append("Timeline:\n");
        report.append("  Agreement Date: ").append(agreementDate).append("\n");
        report.append("  Project End Date: ").append(projectEndDate != null ? projectEndDate : "Not Set").append("\n\n");
        
        if (bottlenecks != null && !bottlenecks.isEmpty()) {
            report.append("Identified Bottlenecks:\n");
            report.append("  ").append(bottlenecks).append("\n\n");
        }
        
        if (remarks != null && !remarks.isEmpty()) {
            report.append("Remarks: ").append(remarks).append("\n");
        }
        
        report.append("==========================================\n");
        return report.toString();
    }

    public String identifyBottlenecks() {
        StringBuilder issues = new StringBuilder();
        issues.append("===== BOTTLENECK ANALYSIS =====\n");
        issues.append("Project: ").append(projectName).append("\n\n");
        
        if (utilizationRate < 50) {
            issues.append("CRITICAL ISSUES:\n");
            issues.append("1. Low utilization rate indicates severe implementation challenges\n");
            issues.append("2. Risk of aid cancellation or reduction by donor\n");
            issues.append("3. Project objectives may not be met within timeline\n\n");
        }
        
        issues.append("Common Bottlenecks Identified:\n");
        issues.append("- Land acquisition delays\n");
        issues.append("- Procurement process challenges\n");
        issues.append("- Capacity constraints in implementing agency\n");
        issues.append("- Regulatory approvals pending\n");
        issues.append("- Contractor performance issues\n\n");
        
        issues.append("Recommended Actions:\n");
        issues.append("1. High-level intervention for land acquisition\n");
        issues.append("2. Fast-track procurement procedures\n");
        issues.append("3. Capacity building for implementing agency\n");
        issues.append("4. Regular donor consultation meetings\n");
        issues.append("===============================\n");
        
        this.bottlenecks = issues.toString();
        return issues.toString();
    }

    public double getUnusedAid() {
        return aidAmount - utilizedAmount;
    }

    // Getters and Setters
    public String getAidId() {
        return aidId;
    }

    public void setAidId(String aidId) {
        this.aidId = aidId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDonorAgency() {
        return donorAgency;
    }

    public void setDonorAgency(String donorAgency) {
        this.donorAgency = donorAgency;
    }

    public double getAidAmount() {
        return aidAmount;
    }

    public void setAidAmount(double aidAmount) {
        this.aidAmount = aidAmount;
    }

    public double getDisbursedAmount() {
        return disbursedAmount;
    }

    public void setDisbursedAmount(double disbursedAmount) {
        this.disbursedAmount = disbursedAmount;
        calculateUtilizationRate();
    }

    public double getUtilizedAmount() {
        return utilizedAmount;
    }

    public void setUtilizedAmount(double utilizedAmount) {
        this.utilizedAmount = utilizedAmount;
        calculateUtilizationRate();
    }

    public String getUtilizationStatus() {
        return utilizationStatus;
    }

    public void setUtilizationStatus(String utilizationStatus) {
        this.utilizationStatus = utilizationStatus;
    }

    public double getUtilizationRate() {
        return utilizationRate;
    }

    public LocalDate getAgreementDate() {
        return agreementDate;
    }

    public void setAgreementDate(LocalDate agreementDate) {
        this.agreementDate = agreementDate;
    }

    public LocalDate getProjectEndDate() {
        return projectEndDate;
    }

    public void setProjectEndDate(LocalDate projectEndDate) {
        this.projectEndDate = projectEndDate;
    }

    public String getProjectSector() {
        return projectSector;
    }

    public void setProjectSector(String projectSector) {
        this.projectSector = projectSector;
    }

    public String getImplementingAgency() {
        return implementingAgency;
    }

    public void setImplementingAgency(String implementingAgency) {
        this.implementingAgency = implementingAgency;
    }

    public String getBottlenecks() {
        return bottlenecks;
    }

    public void setBottlenecks(String bottlenecks) {
        this.bottlenecks = bottlenecks;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    // Additional getters/setters
    public double getCommittedAmount() { return committedAmount; }
    public void setCommittedAmount(double committedAmount) { this.committedAmount = committedAmount; }
    
    public LocalDate getCompletionDate() { return completionDate; }
    public void setCompletionDate(LocalDate completionDate) { this.completionDate = completionDate; }
    

    @Override
    public String toString() {
        return "ForeignAidMonitoring{" +
                "aidId='" + aidId + '\'' +
                ", projectName='" + projectName + '\'' +
                ", donorAgency='" + donorAgency + '\'' +
                ", utilizationStatus='" + utilizationStatus + '\'' +
                '}';
    }
}

