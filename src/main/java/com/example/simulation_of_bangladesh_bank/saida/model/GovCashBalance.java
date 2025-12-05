package com.example.simulation_of_bangladesh_bank.saida.model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * GovCashBalance - Manages government cash balance with Bangladesh Bank
 * Used by Ministry of Finance Representative for treasury management
 */
public class GovCashBalance implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Additional fields
    private double openingBalance;
    private double closingBalance;
    private double totalReceipts;
    private double totalPayments;
    private String balanceId;
    private double cashBalance;
    private double dailyRequirement;
    private double projectedBalance;
    private double minimumBalance;
    private LocalDate reportDate;
    private double inflows;
    private double outflows;
    private String balanceStatus; // Adequate/Warning/Critical
    private String accountType; // TSA (Treasury Single Account)

    // Constructors
    public GovCashBalance() {
        this.reportDate = LocalDate.now();
        this.accountType = "TSA";
    }

    public GovCashBalance(String balanceId, double cashBalance, double minimumBalance) {
        this.balanceId = balanceId;
        this.cashBalance = cashBalance;
        this.minimumBalance = minimumBalance;
        this.reportDate = LocalDate.now();
        this.accountType = "TSA";
        assessBalanceStatus();
    }

    // Business Methods
    public void loadCashData(double inflows, double outflows, double dailyRequirement) {
        this.inflows = inflows;
        this.outflows = outflows;
        this.dailyRequirement = dailyRequirement;
        calculateProjectedBalance();
        assessBalanceStatus();
    }

    private void calculateProjectedBalance() {
        this.projectedBalance = cashBalance + inflows - outflows;
    }

    private void assessBalanceStatus() {
        if (cashBalance >= minimumBalance * 1.5) {
            this.balanceStatus = "Adequate";
        } else if (cashBalance >= minimumBalance) {
            this.balanceStatus = "Warning";
        } else {
            this.balanceStatus = "Critical";
        }
    }

    public boolean validateCashNeeds() {
        return cashBalance >= dailyRequirement;
    }

    public String updateCashRecords(double amount, String transactionType) {
        if (transactionType.equalsIgnoreCase("credit") || 
            transactionType.equalsIgnoreCase("inflow")) {
            this.cashBalance += amount;
            this.inflows += amount;
        } else if (transactionType.equalsIgnoreCase("debit") || 
                   transactionType.equalsIgnoreCase("outflow")) {
            if (amount > cashBalance) {
                return "Error: Insufficient cash balance for this transaction.";
            }
            this.cashBalance -= amount;
            this.outflows += amount;
        }
        
        calculateProjectedBalance();
        assessBalanceStatus();
        
        StringBuilder update = new StringBuilder();
        update.append("===== CASH RECORD UPDATE =====\n");
        update.append("Transaction: ").append(transactionType.toUpperCase()).append("\n");
        update.append("Amount: BDT ").append(String.format("%.2f", amount)).append(" Crore\n");
        update.append("New Balance: BDT ").append(String.format("%.2f", cashBalance)).append(" Crore\n");
        update.append("Status: ").append(balanceStatus).append("\n");
        update.append("==============================\n");
        
        return update.toString();
    }

    public String authorizeTransfer(String targetAccount, double amount, String purpose) {
        if (amount > cashBalance) {
            return "Transfer failed: Insufficient balance.";
        }
        
        StringBuilder authorization = new StringBuilder();
        authorization.append("===== FUND TRANSFER AUTHORIZATION =====\n");
        authorization.append("From: Government Treasury Single Account\n");
        authorization.append("To: ").append(targetAccount).append("\n");
        authorization.append("Amount: BDT ").append(String.format("%.2f", amount)).append(" Crore\n");
        authorization.append("Purpose: ").append(purpose).append("\n");
        authorization.append("Date: ").append(LocalDate.now()).append("\n");
        authorization.append("Status: AUTHORIZED\n");
        authorization.append("=======================================\n");
        
        return authorization.toString();
    }

    public String generateCashReport() {
        StringBuilder report = new StringBuilder();
        report.append("===== GOVERNMENT CASH BALANCE REPORT =====\n");
        report.append("Balance ID: ").append(balanceId).append("\n");
        report.append("Account Type: ").append(accountType).append("\n");
        report.append("Report Date: ").append(reportDate).append("\n\n");
        
        report.append("Current Position:\n");
        report.append("  Cash Balance: BDT ").append(String.format("%.2f", cashBalance)).append(" Crore\n");
        report.append("  Minimum Balance: BDT ").append(String.format("%.2f", minimumBalance)).append(" Crore\n");
        report.append("  Daily Requirement: BDT ").append(String.format("%.2f", dailyRequirement)).append(" Crore\n\n");
        
        report.append("Today's Movements:\n");
        report.append("  Inflows: BDT ").append(String.format("%.2f", inflows)).append(" Crore\n");
        report.append("  Outflows: BDT ").append(String.format("%.2f", outflows)).append(" Crore\n");
        report.append("  Net Movement: BDT ").append(String.format("%.2f", inflows - outflows)).append(" Crore\n\n");
        
        report.append("Projections:\n");
        report.append("  Projected Balance: BDT ").append(String.format("%.2f", projectedBalance)).append(" Crore\n");
        report.append("  Days Coverage: ").append(getDaysCoverage()).append(" days\n\n");
        
        report.append("Status: ").append(balanceStatus).append("\n");
        
        if (balanceStatus.equals("Critical")) {
            report.append("\n⚠ WARNING: Cash balance is below minimum threshold!\n");
            report.append("Immediate action required to replenish funds.\n");
        } else if (balanceStatus.equals("Warning")) {
            report.append("\n⚠ NOTICE: Cash balance approaching minimum threshold.\n");
            report.append("Monitor closely and plan for fund inflows.\n");
        }
        
        report.append("==========================================\n");
        return report.toString();
    }

    public int getDaysCoverage() {
        if (dailyRequirement > 0) {
            return (int) (cashBalance / dailyRequirement);
        }
        return 0;
    }

    public String generateCashManagementStrategy() {
        StringBuilder strategy = new StringBuilder();
        strategy.append("===== CASH MANAGEMENT STRATEGY =====\n\n");
        
        if (balanceStatus.equals("Adequate")) {
            strategy.append("Current Status: COMFORTABLE\n\n");
            strategy.append("Recommendations:\n");
            strategy.append("1. Consider short-term investments for excess funds\n");
            strategy.append("2. Maintain current collection efficiency\n");
            strategy.append("3. Review upcoming large expenditures\n");
        } else if (balanceStatus.equals("Warning")) {
            strategy.append("Current Status: REQUIRES ATTENTION\n\n");
            strategy.append("Recommendations:\n");
            strategy.append("1. Accelerate revenue collection\n");
            strategy.append("2. Prioritize essential expenditures\n");
            strategy.append("3. Defer non-critical payments\n");
            strategy.append("4. Consider short-term borrowing options\n");
        } else {
            strategy.append("Current Status: CRITICAL - IMMEDIATE ACTION NEEDED\n\n");
            strategy.append("Emergency Measures:\n");
            strategy.append("1. Freeze all non-essential expenditures\n");
            strategy.append("2. Emergency borrowing from Bangladesh Bank\n");
            strategy.append("3. Issue treasury bills for immediate liquidity\n");
            strategy.append("4. Notify all ministries of cash constraints\n");
        }
        
        strategy.append("\n=====================================\n");
        return strategy.toString();
    }

    // Getters and Setters
    public String getBalanceId() {
        return balanceId;
    }

    public void setBalanceId(String balanceId) {
        this.balanceId = balanceId;
    }

    public double getCashBalance() {
        return cashBalance;
    }

    public void setCashBalance(double cashBalance) {
        this.cashBalance = cashBalance;
        assessBalanceStatus();
    }

    public double getDailyRequirement() {
        return dailyRequirement;
    }

    public void setDailyRequirement(double dailyRequirement) {
        this.dailyRequirement = dailyRequirement;
    }

    public double getProjectedBalance() {
        return projectedBalance;
    }

    public void setProjectedBalance(double projectedBalance) {
        this.projectedBalance = projectedBalance;
    }

    // No-arg overload for controller compatibility
    public void updateCashRecords() {
        calculateProjectedBalance();
        assessBalanceStatus();
    }

    public double getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(double minimumBalance) {
        this.minimumBalance = minimumBalance;
        assessBalanceStatus();
    }

    public LocalDate getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }

    public double getInflows() {
        return inflows;
    }

    public double getOutflows() {
        return outflows;
    }

    public String getBalanceStatus() {
        return balanceStatus;
    }

    public String getAccountType() {
        return accountType;
    }

    // Additional getters/setters
    public double getOpeningBalance() { return openingBalance; }
    public void setOpeningBalance(double openingBalance) { this.openingBalance = openingBalance; }
    
    public double getClosingBalance() { return closingBalance; }
    public void setClosingBalance(double closingBalance) { this.closingBalance = closingBalance; }
    
    public double getTotalReceipts() { return totalReceipts; }
    public void setTotalReceipts(double totalReceipts) { this.totalReceipts = totalReceipts; }
    
    public double getTotalPayments() { return totalPayments; }
    public void setTotalPayments(double totalPayments) { this.totalPayments = totalPayments; }
    
    public void setAccountType(String accountType) { this.accountType = accountType; }

    @Override
    public String toString() {
        return "GovCashBalance{" +
                "balanceId='" + balanceId + '\'' +
                ", openingBalance=" + openingBalance +
                ", closingBalance=" + closingBalance +
                ", balanceStatus='" + balanceStatus + '\'' +
                '}';
    }
}

