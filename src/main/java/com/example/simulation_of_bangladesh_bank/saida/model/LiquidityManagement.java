package com.example.simulation_of_bangladesh_bank.saida.model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * LiquidityManagement - Manages daily cash flow and liquidity positions
 * Used by Commercial Bank Manager to oversee daily liquidity operations
 */
public class LiquidityManagement implements Serializable {
    private static final long serialVersionUID = 1L;
    private String liquidityId;
    private String bankId;
    private double cashInflows;
    private double cashOutflows;
    private double netPosition;
    private double minThreshold;
    private double projectedInflows;
    private double projectedOutflows;
    private LocalDate reportDate;
    private String liquidityStatus; // Surplus/Deficit/Balanced
    private String actionTaken; // None/Borrowed/Lent

    // Constructors
    public LiquidityManagement() {
        this.reportDate = LocalDate.now();
        this.liquidityStatus = "Balanced";
        this.actionTaken = "None";
    }

    public LiquidityManagement(String liquidityId, String bankId, double cashInflows,
                               double cashOutflows, double minThreshold) {
        this.liquidityId = liquidityId;
        this.bankId = bankId;
        this.cashInflows = cashInflows;
        this.cashOutflows = cashOutflows;
        this.minThreshold = minThreshold;
        this.reportDate = LocalDate.now();
        calculateNetPosition();
    }

    // Business Methods
    public double calculateNetPosition() {
        this.netPosition = cashInflows - cashOutflows;
        updateLiquidityStatus();
        return this.netPosition;
    }

    private void updateLiquidityStatus() {
        if (netPosition > minThreshold * 1.2) {
            this.liquidityStatus = "Surplus";
        } else if (netPosition < minThreshold) {
            this.liquidityStatus = "Deficit";
        } else {
            this.liquidityStatus = "Balanced";
        }
    }

    public double forecastTransactions() {
        double projectedNet = projectedInflows - projectedOutflows;
        return netPosition + projectedNet;
    }

    public boolean validateCashPosition() {
        return netPosition >= minThreshold;
    }

    // No-arg overload for controller compatibility
    public void executeLiquidityAdjustment() {
        calculateNetPosition();
    }

    public String executeLiquidityAdjustment(double amount, String adjustmentType) {
        StringBuilder result = new StringBuilder();
        result.append("===== LIQUIDITY ADJUSTMENT =====\n");
        result.append("Date: ").append(LocalDate.now()).append("\n");
        result.append("Bank ID: ").append(bankId).append("\n");
        result.append("Current Net Position: BDT ").append(String.format("%.2f", netPosition)).append("\n");
        
        if (adjustmentType.equalsIgnoreCase("borrow")) {
            this.cashInflows += amount;
            this.actionTaken = "Borrowed";
            result.append("Action: Borrowed BDT ").append(String.format("%.2f", amount)).append(" from interbank market\n");
        } else if (adjustmentType.equalsIgnoreCase("lend")) {
            if (amount <= (netPosition - minThreshold)) {
                this.cashOutflows += amount;
                this.actionTaken = "Lent";
                result.append("Action: Lent BDT ").append(String.format("%.2f", amount)).append(" to interbank market\n");
            } else {
                result.append("Error: Cannot lend. Would fall below minimum threshold.\n");
                return result.toString();
            }
        }
        
        calculateNetPosition();
        result.append("New Net Position: BDT ").append(String.format("%.2f", netPosition)).append("\n");
        result.append("Status: ").append(liquidityStatus).append("\n");
        result.append("================================\n");
        
        return result.toString();
    }

    public String generateLiquidityReport() {
        StringBuilder report = new StringBuilder();
        report.append("===== DAILY LIQUIDITY REPORT =====\n");
        report.append("Report ID: ").append(liquidityId).append("\n");
        report.append("Bank ID: ").append(bankId).append("\n");
        report.append("Date: ").append(reportDate).append("\n\n");
        
        report.append("Current Position:\n");
        report.append("  Cash Inflows: BDT ").append(String.format("%.2f", cashInflows)).append("\n");
        report.append("  Cash Outflows: BDT ").append(String.format("%.2f", cashOutflows)).append("\n");
        report.append("  Net Position: BDT ").append(String.format("%.2f", netPosition)).append("\n");
        report.append("  Minimum Threshold: BDT ").append(String.format("%.2f", minThreshold)).append("\n\n");
        
        report.append("Projected (Next 24 Hours):\n");
        report.append("  Projected Inflows: BDT ").append(String.format("%.2f", projectedInflows)).append("\n");
        report.append("  Projected Outflows: BDT ").append(String.format("%.2f", projectedOutflows)).append("\n");
        report.append("  Forecasted Position: BDT ").append(String.format("%.2f", forecastTransactions())).append("\n\n");
        
        report.append("Status: ").append(liquidityStatus).append("\n");
        report.append("Action Taken: ").append(actionTaken).append("\n");
        report.append("==================================\n");
        
        return report.toString();
    }

    public double getSurplusAmount() {
        if (netPosition > minThreshold) {
            return netPosition - minThreshold;
        }
        return 0;
    }

    public double getDeficitAmount() {
        if (netPosition < minThreshold) {
            return minThreshold - netPosition;
        }
        return 0;
    }

    // Getters and Setters
    public String getLiquidityId() {
        return liquidityId;
    }

    public void setLiquidityId(String liquidityId) {
        this.liquidityId = liquidityId;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public double getCashInflows() {
        return cashInflows;
    }

    public void setCashInflows(double cashInflows) {
        this.cashInflows = cashInflows;
        calculateNetPosition();
    }

    public double getCashOutflows() {
        return cashOutflows;
    }

    public void setCashOutflows(double cashOutflows) {
        this.cashOutflows = cashOutflows;
        calculateNetPosition();
    }

    public double getNetPosition() {
        return netPosition;
    }

    public double getMinThreshold() {
        return minThreshold;
    }

    public void setMinThreshold(double minThreshold) {
        this.minThreshold = minThreshold;
        updateLiquidityStatus();
    }

    public double getProjectedInflows() {
        return projectedInflows;
    }

    public void setProjectedInflows(double projectedInflows) {
        this.projectedInflows = projectedInflows;
    }

    public double getProjectedOutflows() {
        return projectedOutflows;
    }

    public void setProjectedOutflows(double projectedOutflows) {
        this.projectedOutflows = projectedOutflows;
    }

    public LocalDate getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }

    public String getLiquidityStatus() {
        return liquidityStatus;
    }

    public String getActionTaken() {
        return actionTaken;
    }

    @Override
    public String toString() {
        return "LiquidityManagement{" +
                "liquidityId='" + liquidityId + '\'' +
                ", bankId='" + bankId + '\'' +
                ", netPosition=" + netPosition +
                ", liquidityStatus='" + liquidityStatus + '\'' +
                '}';
    }
}

