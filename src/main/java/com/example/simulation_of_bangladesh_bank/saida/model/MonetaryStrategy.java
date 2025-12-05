package com.example.simulation_of_bangladesh_bank.saida.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * MonetaryStrategy - Annual monetary policy implementation strategy
 * Used by Commercial Bank Manager to formulate strategy for Bangladesh Bank
 */
public class MonetaryStrategy implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Additional fields
    private double creditTarget;
    private double depositTarget;
    private double priorityLending;
    private String status;
    private String strategyId;
    private String bankId;
    private String fiscalYear;
    private String creditTargets;
    private Map<String, Double> sectorAllocation; // Sector name -> percentage
    private double projectedGrowth;
    private double targetInterestRate;
    private double targetLoanGrowth;
    private double targetDepositGrowth;
    private String strategyStatus; // Draft/Submitted/Approved/Rejected
    private LocalDate creationDate;
    private LocalDate submissionDate;
    private String previousYearPerformance;
    private String remarks;

    // Constructors
    public MonetaryStrategy() {
        this.creationDate = LocalDate.now();
        this.strategyStatus = "Draft";
        this.sectorAllocation = new HashMap<>();
    }

    public MonetaryStrategy(String strategyId, String bankId, String fiscalYear) {
        this.strategyId = strategyId;
        this.bankId = bankId;
        this.fiscalYear = fiscalYear;
        this.creationDate = LocalDate.now();
        this.strategyStatus = "Draft";
        this.sectorAllocation = new HashMap<>();
    }

    // Business Methods
    public void loadPreviousStrategy(String previousPerformance, double lastYearGrowth) {
        this.previousYearPerformance = previousPerformance;
        // Set baseline targets based on previous performance
        this.projectedGrowth = lastYearGrowth * 1.1; // 10% improvement target
    }

    public void inputNewStrategy(String creditTargets, double projectedGrowth,
                                 double targetInterestRate, double targetLoanGrowth,
                                 double targetDepositGrowth) {
        this.creditTargets = creditTargets;
        this.projectedGrowth = projectedGrowth;
        this.targetInterestRate = targetInterestRate;
        this.targetLoanGrowth = targetLoanGrowth;
        this.targetDepositGrowth = targetDepositGrowth;
    }

    public void setSectorAllocation(String sector, double percentage) {
        this.sectorAllocation.put(sector, percentage);
    }

    public boolean validateWithBBPolicy(double bbInflationTarget, double bbGrowthTarget,
                                        double bbInterestRateRange) {
        StringBuilder validationResult = new StringBuilder();
        boolean isValid = true;
        
        // Check if projected growth aligns with BB target
        if (Math.abs(projectedGrowth - bbGrowthTarget) > 2) {
            validationResult.append("Growth projection deviates significantly from BB target.\n");
            isValid = false;
        }
        
        // Check if interest rate is within BB range
        if (Math.abs(targetInterestRate - bbInterestRateRange) > 1) {
            validationResult.append("Interest rate target outside BB acceptable range.\n");
            isValid = false;
        }
        
        // Check sector allocation totals 100%
        double totalAllocation = sectorAllocation.values().stream().mapToDouble(Double::doubleValue).sum();
        if (Math.abs(totalAllocation - 100) > 0.1) {
            validationResult.append("Sector allocation does not sum to 100%.\n");
            isValid = false;
        }
        
        if (isValid) {
            this.remarks = "Strategy validated successfully with BB policy guidelines.";
        } else {
            this.remarks = validationResult.toString();
        }
        
        return isValid;
    }

    public String submitStrategy() {
        if (this.strategyStatus.equals("Draft")) {
            this.strategyStatus = "Submitted";
            this.submissionDate = LocalDate.now();
            return generateStrategyDocument();
        }
        return "Strategy already submitted or cannot be submitted in current status.";
    }

    public String generateStrategyDocument() {
        StringBuilder document = new StringBuilder();
        document.append("=======================================================\n");
        document.append("     ANNUAL MONETARY POLICY IMPLEMENTATION STRATEGY     \n");
        document.append("=======================================================\n\n");
        
        document.append("Strategy ID: ").append(strategyId).append("\n");
        document.append("Bank ID: ").append(bankId).append("\n");
        document.append("Fiscal Year: ").append(fiscalYear).append("\n");
        document.append("Submission Date: ").append(submissionDate != null ? submissionDate : "Not Submitted").append("\n");
        document.append("Status: ").append(strategyStatus).append("\n\n");
        
        document.append("-------------------------------------------------------\n");
        document.append("1. EXECUTIVE SUMMARY\n");
        document.append("-------------------------------------------------------\n");
        document.append("Credit Targets: ").append(creditTargets).append("\n");
        document.append("Projected Growth Rate: ").append(String.format("%.2f", projectedGrowth)).append("%\n\n");
        
        document.append("-------------------------------------------------------\n");
        document.append("2. KEY PERFORMANCE TARGETS\n");
        document.append("-------------------------------------------------------\n");
        document.append("  Target Loan Growth: ").append(String.format("%.2f", targetLoanGrowth)).append("%\n");
        document.append("  Target Deposit Growth: ").append(String.format("%.2f", targetDepositGrowth)).append("%\n");
        document.append("  Target Interest Rate: ").append(String.format("%.2f", targetInterestRate)).append("%\n\n");
        
        document.append("-------------------------------------------------------\n");
        document.append("3. SECTOR-WISE CREDIT ALLOCATION\n");
        document.append("-------------------------------------------------------\n");
        for (Map.Entry<String, Double> entry : sectorAllocation.entrySet()) {
            document.append("  ").append(entry.getKey()).append(": ")
                    .append(String.format("%.2f", entry.getValue())).append("%\n");
        }
        document.append("\n");
        
        document.append("-------------------------------------------------------\n");
        document.append("4. PREVIOUS YEAR PERFORMANCE\n");
        document.append("-------------------------------------------------------\n");
        document.append(previousYearPerformance != null ? previousYearPerformance : "N/A").append("\n\n");
        
        if (remarks != null && !remarks.isEmpty()) {
            document.append("-------------------------------------------------------\n");
            document.append("5. REMARKS\n");
            document.append("-------------------------------------------------------\n");
            document.append(remarks).append("\n\n");
        }
        
        document.append("=======================================================\n");
        document.append("                  END OF STRATEGY DOCUMENT              \n");
        document.append("=======================================================\n");
        
        return document.toString();
    }

    public String calculateProjectedKPIs() {
        StringBuilder kpis = new StringBuilder();
        kpis.append("Projected Key Performance Indicators:\n");
        kpis.append("  Expected Loan Portfolio Growth: BDT ")
            .append(String.format("%.2f", targetLoanGrowth)).append(" Crore\n");
        kpis.append("  Expected Deposit Growth: BDT ")
            .append(String.format("%.2f", targetDepositGrowth)).append(" Crore\n");
        kpis.append("  Net Interest Margin Target: ")
            .append(String.format("%.2f", targetInterestRate * 0.3)).append("%\n");
        return kpis.toString();
    }

    // Getters and Setters
    public String getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(String strategyId) {
        this.strategyId = strategyId;
    }

    // Alias methods for controller compatibility
    public String getStrategyID() {
        return strategyId;
    }

    public void setStrategyID(String strategyId) {
        this.strategyId = strategyId;
    }

    // No-arg overloads for controller compatibility
    public void loadPreviousStrategy() {
        this.previousYearPerformance = "Previous year performance loaded";
    }

    public void inputNewStrategy() {
        // No-op - use individual setters
    }

    public boolean validateWithBBPolicy() {
        return validateWithBBPolicy(6.0, 7.0, 9.0);
    }

    public void setCreditTargets(double target) {
        this.creditTargets = String.valueOf(target);
    }

    public String getSectorAllocationAsString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Double> entry : sectorAllocation.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("%, ");
        }
        return sb.toString();
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getFiscalYear() {
        return fiscalYear;
    }

    public void setFiscalYear(String fiscalYear) {
        this.fiscalYear = fiscalYear;
    }

    public String getCreditTargets() {
        return creditTargets;
    }

    // Double getter for controller compatibility
    public double getCreditTargetsAsDouble() {
        try {
            return creditTargets != null ? Double.parseDouble(creditTargets) : 0.0;
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public void setCreditTargets(String creditTargets) {
        this.creditTargets = creditTargets;
    }

    // Overloaded method for controller compatibility (String only)
    public void setSectorAllocationStr(String allocation) {
        this.creditTargets = allocation;
    }

    public String getSectorAllocationStr() {
        return getSectorAllocationAsString();
    }

    public Map<String, Double> getSectorAllocation() {
        return sectorAllocation;
    }

    public double getProjectedGrowth() {
        return projectedGrowth;
    }

    public void setProjectedGrowth(double projectedGrowth) {
        this.projectedGrowth = projectedGrowth;
    }

    public double getTargetInterestRate() {
        return targetInterestRate;
    }

    public void setTargetInterestRate(double targetInterestRate) {
        this.targetInterestRate = targetInterestRate;
    }

    public double getTargetLoanGrowth() {
        return targetLoanGrowth;
    }

    public void setTargetLoanGrowth(double targetLoanGrowth) {
        this.targetLoanGrowth = targetLoanGrowth;
    }

    public double getTargetDepositGrowth() {
        return targetDepositGrowth;
    }

    public void setTargetDepositGrowth(double targetDepositGrowth) {
        this.targetDepositGrowth = targetDepositGrowth;
    }

    public String getStrategyStatus() {
        return strategyStatus;
    }

    public void setStrategyStatus(String strategyStatus) {
        this.strategyStatus = strategyStatus;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public LocalDate getSubmissionDate() {
        return submissionDate;
    }

    public String getPreviousYearPerformance() {
        return previousYearPerformance;
    }

    public void setPreviousYearPerformance(String previousYearPerformance) {
        this.previousYearPerformance = previousYearPerformance;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    // Additional getters/setters
    public double getCreditTarget() { return creditTarget; }
    public void setCreditTarget(double creditTarget) { this.creditTarget = creditTarget; }
    
    public double getDepositTarget() { return depositTarget; }
    public void setDepositTarget(double depositTarget) { this.depositTarget = depositTarget; }
    
    public double getPriorityLending() { return priorityLending; }
    public void setPriorityLending(double priorityLending) { this.priorityLending = priorityLending; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public void setSubmissionDate(LocalDate submissionDate) { this.submissionDate = submissionDate; }

    @Override
    public String toString() {
        return "MonetaryStrategy{" +
                "strategyId='" + strategyId + '\'' +
                ", fiscalYear='" + fiscalYear + '\'' +
                ", creditTarget=" + creditTarget +
                ", status='" + status + '\'' +
                '}';
    }
}

