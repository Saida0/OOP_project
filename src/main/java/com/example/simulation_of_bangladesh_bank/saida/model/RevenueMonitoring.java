package com.example.simulation_of_bangladesh_bank.saida.model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * RevenueMonitoring - Monitors government revenue collection
 * Used by Ministry of Finance Representative to track tax and non-tax revenue
 */
public class RevenueMonitoring implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Additional fields
    private String revenueType;
    private double collectedAmount;
    private double targetAmount;
    private String region;
    private String revenueId;
    private double taxCollection;
    private double nonTaxRevenue;
    private double revenueTarget;
    private double actualRevenue;
    private LocalDate reportingPeriod;
    private String fiscalYear;
    private String quarter;
    private double shortfall;
    private double surplus;
    private String performanceStatus; // OnTrack/BelowTarget/AboveTarget

    // Constructors
    public RevenueMonitoring() {
        this.reportingPeriod = LocalDate.now();
    }

    public RevenueMonitoring(String revenueId, String fiscalYear, String quarter, 
                            double revenueTarget) {
        this.revenueId = revenueId;
        this.fiscalYear = fiscalYear;
        this.quarter = quarter;
        this.revenueTarget = revenueTarget;
        this.reportingPeriod = LocalDate.now();
    }

    // Business Methods
    public void loadRevenueData(double taxCollection, double nonTaxRevenue) {
        this.taxCollection = taxCollection;
        this.nonTaxRevenue = nonTaxRevenue;
        this.actualRevenue = taxCollection + nonTaxRevenue;
        calculatePerformance();
    }

    private void calculatePerformance() {
        double variance = actualRevenue - revenueTarget;
        
        if (variance >= 0) {
            this.surplus = variance;
            this.shortfall = 0;
            if (variance > revenueTarget * 0.05) {
                this.performanceStatus = "AboveTarget";
            } else {
                this.performanceStatus = "OnTrack";
            }
        } else {
            this.shortfall = Math.abs(variance);
            this.surplus = 0;
            this.performanceStatus = "BelowTarget";
        }
    }

    public boolean validateAgainstTarget() {
        return actualRevenue >= revenueTarget;
    }

    public String generatePerformanceReport() {
        StringBuilder report = new StringBuilder();
        report.append("===== REVENUE PERFORMANCE REPORT =====\n");
        report.append("Report ID: ").append(revenueId).append("\n");
        report.append("Fiscal Year: ").append(fiscalYear).append("\n");
        report.append("Quarter: ").append(quarter).append("\n");
        report.append("Reporting Date: ").append(reportingPeriod).append("\n\n");
        
        report.append("Revenue Collection:\n");
        report.append("  Tax Revenue: BDT ").append(String.format("%.2f", taxCollection)).append(" Crore\n");
        report.append("  Non-Tax Revenue: BDT ").append(String.format("%.2f", nonTaxRevenue)).append(" Crore\n");
        report.append("  Total Collection: BDT ").append(String.format("%.2f", actualRevenue)).append(" Crore\n\n");
        
        report.append("Target & Variance:\n");
        report.append("  Target: BDT ").append(String.format("%.2f", revenueTarget)).append(" Crore\n");
        report.append("  Variance: BDT ").append(String.format("%.2f", actualRevenue - revenueTarget)).append(" Crore\n");
        
        if (shortfall > 0) {
            report.append("  Shortfall: BDT ").append(String.format("%.2f", shortfall)).append(" Crore\n");
        } else if (surplus > 0) {
            report.append("  Surplus: BDT ").append(String.format("%.2f", surplus)).append(" Crore\n");
        }
        
        report.append("\nPerformance Status: ").append(performanceStatus).append("\n");
        report.append("Achievement Rate: ").append(String.format("%.2f", (actualRevenue / revenueTarget) * 100)).append("%\n");
        report.append("======================================\n");
        
        return report.toString();
    }

    public String identifyShortfalls() {
        if (shortfall <= 0) {
            return "No shortfall identified. Revenue collection is on or above target.";
        }
        
        StringBuilder analysis = new StringBuilder();
        analysis.append("===== SHORTFALL ANALYSIS =====\n");
        analysis.append("Total Shortfall: BDT ").append(String.format("%.2f", shortfall)).append(" Crore\n");
        analysis.append("Shortfall as % of Target: ").append(String.format("%.2f", (shortfall / revenueTarget) * 100)).append("%\n\n");
        
        analysis.append("Recommended Actions:\n");
        analysis.append("1. Review tax collection efficiency\n");
        analysis.append("2. Strengthen enforcement mechanisms\n");
        analysis.append("3. Expand tax base coverage\n");
        analysis.append("4. Improve compliance monitoring\n");
        analysis.append("==============================\n");
        
        return analysis.toString();
    }

    // Getters and Setters
    public String getRevenueId() {
        return revenueId;
    }

    public void setRevenueId(String revenueId) {
        this.revenueId = revenueId;
    }

    public double getTaxCollection() {
        return taxCollection;
    }

    public void setTaxCollection(double taxCollection) {
        this.taxCollection = taxCollection;
    }

    public double getNonTaxRevenue() {
        return nonTaxRevenue;
    }

    public void setNonTaxRevenue(double nonTaxRevenue) {
        this.nonTaxRevenue = nonTaxRevenue;
    }

    public double getRevenueTarget() {
        return revenueTarget;
    }

    public void setRevenueTarget(double revenueTarget) {
        this.revenueTarget = revenueTarget;
    }

    public double getActualRevenue() {
        return actualRevenue;
    }

    public LocalDate getReportingPeriod() {
        return reportingPeriod;
    }

    public void setReportingPeriod(LocalDate reportingPeriod) {
        this.reportingPeriod = reportingPeriod;
    }

    // String-based setter for controller compatibility.
    // Accepts human-readable period labels like "Q1 (Jul-Sep)" without forcing a date parse.
    public void setReportingPeriod(String reportingPeriodStr) {
        if (reportingPeriodStr == null || reportingPeriodStr.isEmpty()) {
            this.reportingPeriod = null;
            this.quarter = null;
            return;
        }
        // For generic labels (quarters/monthly/annual), store in the quarter field
        // instead of trying to parse as LocalDate.
        this.reportingPeriod = null;
        this.quarter = reportingPeriodStr;
    }

    public String getReportingPeriodAsString() {
        if (quarter != null && !quarter.isEmpty()) {
            return quarter;
        }
        return reportingPeriod != null ? reportingPeriod.toString() : "";
    }

    public String getFiscalYear() {
        return fiscalYear;
    }

    public void setFiscalYear(String fiscalYear) {
        this.fiscalYear = fiscalYear;
    }

    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    public double getShortfall() {
        return shortfall;
    }

    public double getSurplus() {
        return surplus;
    }

    public String getPerformanceStatus() {
        return performanceStatus;
    }

    // Additional getters/setters
    public String getRevenueType() { return revenueType; }
    public void setRevenueType(String revenueType) { this.revenueType = revenueType; }
    
    public double getCollectedAmount() { return collectedAmount; }
    public void setCollectedAmount(double collectedAmount) { this.collectedAmount = collectedAmount; }
    
    public double getTargetAmount() { return targetAmount; }
    public void setTargetAmount(double targetAmount) { this.targetAmount = targetAmount; }
    
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    

    @Override
    public String toString() {
        return "RevenueMonitoring{" +
                "revenueId='" + revenueId + '\'' +
                ", revenueType='" + revenueType + '\'' +
                ", collectedAmount=" + collectedAmount +
                ", targetAmount=" + targetAmount +
                '}';
    }
}

