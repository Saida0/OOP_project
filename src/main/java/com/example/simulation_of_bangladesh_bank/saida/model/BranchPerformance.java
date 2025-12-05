package com.example.simulation_of_bangladesh_bank.saida.model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * BranchPerformance - Monitors and evaluates branch-level performance
 * Used by Commercial Bank Manager for branch performance reporting
 */
public class BranchPerformance implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Additional fields
    private double totalDeposits;
    private double totalLoans;
    private LocalDate reportDate;
    private int employeeCount;
    private int customerCount;
    private String performanceId;
    private String bankId;
    private String branchId;
    private String branchName;
    private String region;
    private double kpiScore;
    private double depositGrowth;
    private double loanGrowth;
    private double npaRatio; // Non-Performing Assets ratio
    private double customerSatisfactionScore;
    private int ranking;
    private String performanceRating; // Excellent/Good/Average/Poor
    private String remarks;
    private LocalDate evaluationDate;

    // Constructors
    public BranchPerformance() {
        this.evaluationDate = LocalDate.now();
    }

    public BranchPerformance(String performanceId, String bankId, String branchId, 
                            String branchName, String region) {
        this.performanceId = performanceId;
        this.bankId = bankId;
        this.branchId = branchId;
        this.branchName = branchName;
        this.region = region;
        this.evaluationDate = LocalDate.now();
    }

    // Business Methods
    public void loadBranchData(double depositGrowth, double loanGrowth, 
                               double npaRatio, double customerSatisfaction) {
        this.depositGrowth = depositGrowth;
        this.loanGrowth = loanGrowth;
        this.npaRatio = npaRatio;
        this.customerSatisfactionScore = customerSatisfaction;
        calculateKPIScore();
    }

    private void calculateKPIScore() {
        // Weighted KPI calculation
        // Deposit Growth: 25%, Loan Growth: 25%, NPA (inverse): 30%, Customer Satisfaction: 20%
        double depositScore = Math.min(depositGrowth * 2, 25); // Max 25 points
        double loanScore = Math.min(loanGrowth * 2, 25); // Max 25 points
        double npaScore = Math.max(30 - (npaRatio * 10), 0); // Lower NPA = higher score
        double satisfactionScore = (customerSatisfactionScore / 5) * 20; // Scale to 20 points
        
        this.kpiScore = depositScore + loanScore + npaScore + satisfactionScore;
        assignPerformanceRating();
    }

    private void assignPerformanceRating() {
        if (kpiScore >= 85) {
            this.performanceRating = "Excellent";
        } else if (kpiScore >= 70) {
            this.performanceRating = "Good";
        } else if (kpiScore >= 50) {
            this.performanceRating = "Average";
        } else {
            this.performanceRating = "Poor";
        }
    }

    public String generateRanking(int totalBranches) {
        // Simple ranking based on KPI score percentile
        if (kpiScore >= 85) {
            this.ranking = (int) (totalBranches * 0.1); // Top 10%
        } else if (kpiScore >= 70) {
            this.ranking = (int) (totalBranches * 0.3); // Top 30%
        } else if (kpiScore >= 50) {
            this.ranking = (int) (totalBranches * 0.6); // Top 60%
        } else {
            this.ranking = (int) (totalBranches * 0.9); // Bottom 10%
        }
        
        return "Branch " + branchName + " ranked #" + ranking + " out of " + totalBranches;
    }

    public boolean identifyWeakBranch() {
        return performanceRating.equals("Poor") || performanceRating.equals("Average");
    }

    public String recommendImprovements() {
        StringBuilder recommendations = new StringBuilder();
        recommendations.append("===== IMPROVEMENT RECOMMENDATIONS =====\n");
        recommendations.append("Branch: ").append(branchName).append("\n");
        recommendations.append("Current Rating: ").append(performanceRating).append("\n\n");
        
        if (depositGrowth < 5) {
            recommendations.append("1. DEPOSIT GROWTH:\n");
            recommendations.append("   - Launch deposit mobilization campaigns\n");
            recommendations.append("   - Introduce attractive savings schemes\n");
            recommendations.append("   - Focus on corporate deposits\n\n");
        }
        
        if (loanGrowth < 5) {
            recommendations.append("2. LOAN PORTFOLIO:\n");
            recommendations.append("   - Increase SME lending focus\n");
            recommendations.append("   - Streamline loan processing\n");
            recommendations.append("   - Competitive interest rates\n\n");
        }
        
        if (npaRatio > 3) {
            recommendations.append("3. NPA MANAGEMENT:\n");
            recommendations.append("   - Strengthen credit appraisal\n");
            recommendations.append("   - Aggressive recovery measures\n");
            recommendations.append("   - Early warning system implementation\n\n");
        }
        
        if (customerSatisfactionScore < 4) {
            recommendations.append("4. CUSTOMER SERVICE:\n");
            recommendations.append("   - Staff training programs\n");
            recommendations.append("   - Reduce service turnaround time\n");
            recommendations.append("   - Digital service enhancement\n\n");
        }
        
        recommendations.append("=======================================\n");
        return recommendations.toString();
    }

    public String generatePerformanceReport() {
        StringBuilder report = new StringBuilder();
        report.append("===== BRANCH PERFORMANCE REPORT =====\n");
        report.append("Report ID: ").append(performanceId).append("\n");
        report.append("Evaluation Date: ").append(evaluationDate).append("\n\n");
        
        report.append("Branch Information:\n");
        report.append("  Branch ID: ").append(branchId).append("\n");
        report.append("  Branch Name: ").append(branchName).append("\n");
        report.append("  Region: ").append(region).append("\n");
        report.append("  Bank ID: ").append(bankId).append("\n\n");
        
        report.append("Performance Metrics:\n");
        report.append("  Deposit Growth: ").append(String.format("%.2f", depositGrowth)).append("%\n");
        report.append("  Loan Growth: ").append(String.format("%.2f", loanGrowth)).append("%\n");
        report.append("  NPA Ratio: ").append(String.format("%.2f", npaRatio)).append("%\n");
        report.append("  Customer Satisfaction: ").append(String.format("%.1f", customerSatisfactionScore)).append("/5\n\n");
        
        report.append("Overall Assessment:\n");
        report.append("  KPI Score: ").append(String.format("%.2f", kpiScore)).append("/100\n");
        report.append("  Ranking: #").append(ranking).append("\n");
        report.append("  Performance Rating: ").append(performanceRating).append("\n");
        
        if (remarks != null && !remarks.isEmpty()) {
            report.append("\nRemarks: ").append(remarks).append("\n");
        }
        
        report.append("=====================================\n");
        return report.toString();
    }

    // Getters and Setters
    public String getPerformanceId() {
        return performanceId;
    }

    public void setPerformanceId(String performanceId) {
        this.performanceId = performanceId;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    // Alias methods for controller compatibility
    public String getBranchID() {
        return branchId;
    }

    public void setBranchID(String branchId) {
        this.branchId = branchId;
    }

    public void setKpiScore(double kpiScore) {
        this.kpiScore = kpiScore;
        assignPerformanceRating();
    }

    public static java.util.List<BranchPerformance> identifyWeakBranches() {
        return new java.util.ArrayList<>();
    }

    public void loadBranchData() {
        calculateKPIScore();
    }

    public String generateRanking() {
        return generateRanking(100);
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public double getKpiScore() {
        return kpiScore;
    }

    public double getDepositGrowth() {
        return depositGrowth;
    }

    public void setDepositGrowth(double depositGrowth) {
        this.depositGrowth = depositGrowth;
    }

    public double getLoanGrowth() {
        return loanGrowth;
    }

    public void setLoanGrowth(double loanGrowth) {
        this.loanGrowth = loanGrowth;
    }

    public double getNpaRatio() {
        return npaRatio;
    }

    public void setNpaRatio(double npaRatio) {
        this.npaRatio = npaRatio;
    }

    public double getCustomerSatisfactionScore() {
        return customerSatisfactionScore;
    }

    public void setCustomerSatisfactionScore(double customerSatisfactionScore) {
        this.customerSatisfactionScore = customerSatisfactionScore;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public String getPerformanceRating() {
        return performanceRating;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public LocalDate getEvaluationDate() {
        return evaluationDate;
    }

    public void setEvaluationDate(LocalDate evaluationDate) {
        this.evaluationDate = evaluationDate;
    }

    // Additional getters/setters
    public double getTotalDeposits() { return totalDeposits; }
    public void setTotalDeposits(double totalDeposits) { this.totalDeposits = totalDeposits; }
    
    public double getTotalLoans() { return totalLoans; }
    public void setTotalLoans(double totalLoans) { this.totalLoans = totalLoans; }
    
    public LocalDate getReportDate() { return reportDate; }
    public void setReportDate(LocalDate reportDate) { this.reportDate = reportDate; }
    
    public int getEmployeeCount() { return employeeCount; }
    public void setEmployeeCount(int employeeCount) { this.employeeCount = employeeCount; }
    
    public int getCustomerCount() { return customerCount; }
    public void setCustomerCount(int customerCount) { this.customerCount = customerCount; }

    @Override
    public String toString() {
        return "BranchPerformance{" +
                "branchId='" + branchId + '\'' +
                ", branchName='" + branchName + '\'' +
                ", totalDeposits=" + totalDeposits +
                ", kpiScore=" + kpiScore +
                '}';
    }
}

