package com.example.simulation_of_bangladesh_bank.saida.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * BudgetManagement - Manages national budget preparation and execution
 * Used by Ministry of Finance Representative for budget supervision
 */
public class BudgetManagement implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Additional fields
    private String quarter;
    private String budgetId;
    private String fiscalYear;
    private double totalAllocatedAmount;
    private double totalSpentAmount;
    private double totalRemainingAmount;
    private Map<String, Double> departmentAllocations;
    private Map<String, Double> departmentExpenditures;
    private String budgetStatus; // Draft/Approved/Executing/Completed
    private LocalDate preparationDate;
    private LocalDate approvalDate;
    private double budgetDeficit;
    private double revenueEstimate;
    
    // Simple properties for controller compatibility
    private String department;
    private double allocatedAmount;
    private double spentAmount;

    // Constructors
    public BudgetManagement() {
        this.departmentAllocations = new HashMap<>();
        this.departmentExpenditures = new HashMap<>();
        this.preparationDate = LocalDate.now();
        this.budgetStatus = "Draft";
    }

    public BudgetManagement(String budgetId, String fiscalYear, double totalAllocatedAmount,
                           double revenueEstimate) {
        this.budgetId = budgetId;
        this.fiscalYear = fiscalYear;
        this.totalAllocatedAmount = totalAllocatedAmount;
        this.revenueEstimate = revenueEstimate;
        this.totalRemainingAmount = totalAllocatedAmount;
        this.departmentAllocations = new HashMap<>();
        this.departmentExpenditures = new HashMap<>();
        this.preparationDate = LocalDate.now();
        this.budgetStatus = "Draft";
        calculateBudgetDeficit();
    }

    // Business Methods
    public void addDepartmentAllocation(String department, double amount) {
        this.departmentAllocations.put(department, amount);
        this.departmentExpenditures.putIfAbsent(department, 0.0);
    }

    public boolean validateAgainstFramework() {
        // Check if total allocations match total budget
        double totalAllocations = departmentAllocations.values().stream()
                .mapToDouble(Double::doubleValue).sum();
        
        if (Math.abs(totalAllocations - totalAllocatedAmount) > 0.01) {
            return false;
        }
        
        // Check fiscal deficit is within acceptable range (typically 5% of GDP)
        // For simulation, we'll assume budget deficit shouldn't exceed 30% of revenue
        if (budgetDeficit > revenueEstimate * 0.3) {
            return false;
        }
        
        return true;
    }

    private void calculateBudgetDeficit() {
        this.budgetDeficit = totalAllocatedAmount - revenueEstimate;
        if (budgetDeficit < 0) {
            budgetDeficit = 0; // Surplus case
        }
    }

    public String generateConsolidatedBudget() {
        StringBuilder budget = new StringBuilder();
        budget.append("╔══════════════════════════════════════════════════════════════╗\n");
        budget.append("║              CONSOLIDATED NATIONAL BUDGET                    ║\n");
        budget.append("╠══════════════════════════════════════════════════════════════╣\n");
        budget.append("║ Budget ID: ").append(String.format("%-49s", budgetId)).append("║\n");
        budget.append("║ Fiscal Year: ").append(String.format("%-47s", fiscalYear)).append("║\n");
        budget.append("║ Status: ").append(String.format("%-52s", budgetStatus)).append("║\n");
        budget.append("╠══════════════════════════════════════════════════════════════╣\n");
        budget.append("║                    BUDGET SUMMARY                            ║\n");
        budget.append("╠══════════════════════════════════════════════════════════════╣\n");
        budget.append(String.format("║ Total Allocation: BDT %-37.2f Crore ║\n", totalAllocatedAmount));
        budget.append(String.format("║ Revenue Estimate: BDT %-37.2f Crore ║\n", revenueEstimate));
        budget.append(String.format("║ Budget Deficit:   BDT %-37.2f Crore ║\n", budgetDeficit));
        budget.append("╠══════════════════════════════════════════════════════════════╣\n");
        budget.append("║               DEPARTMENT-WISE ALLOCATION                     ║\n");
        budget.append("╠══════════════════════════════════════════════════════════════╣\n");
        
        for (Map.Entry<String, Double> entry : departmentAllocations.entrySet()) {
            String dept = entry.getKey();
            Double alloc = entry.getValue();
            Double spent = departmentExpenditures.getOrDefault(dept, 0.0);
            budget.append(String.format("║ %-20s: Alloc: %.2f, Spent: %.2f        ║\n", 
                         dept, alloc, spent));
        }
        
        budget.append("╠══════════════════════════════════════════════════════════════╣\n");
        budget.append("║                    EXECUTION STATUS                          ║\n");
        budget.append("╠══════════════════════════════════════════════════════════════╣\n");
        budget.append(String.format("║ Total Spent:     BDT %-38.2f Crore ║\n", totalSpentAmount));
        budget.append(String.format("║ Total Remaining: BDT %-38.2f Crore ║\n", totalRemainingAmount));
        budget.append(String.format("║ Utilization Rate: %-40.2f%% ║\n", getUtilizationRate()));
        budget.append("╚══════════════════════════════════════════════════════════════╝\n");
        
        return budget.toString();
    }

    public String trackExecution(String department, double expenditure) {
        if (!departmentAllocations.containsKey(department)) {
            return "Department not found in budget allocation.";
        }
        
        double currentSpent = departmentExpenditures.getOrDefault(department, 0.0);
        double allocated = departmentAllocations.get(department);
        
        if (currentSpent + expenditure > allocated) {
            return "Error: Expenditure exceeds allocated budget for " + department;
        }
        
        departmentExpenditures.put(department, currentSpent + expenditure);
        this.totalSpentAmount += expenditure;
        this.totalRemainingAmount = totalAllocatedAmount - totalSpentAmount;
        
        return generateDepartmentReport(department);
    }

    private String generateDepartmentReport(String department) {
        double allocated = departmentAllocations.get(department);
        double spent = departmentExpenditures.get(department);
        double remaining = allocated - spent;
        double utilizationRate = (spent / allocated) * 100;
        
        StringBuilder report = new StringBuilder();
        report.append("===== DEPARTMENT BUDGET EXECUTION =====\n");
        report.append("Department: ").append(department).append("\n");
        report.append("Allocated: BDT ").append(String.format("%.2f", allocated)).append(" Crore\n");
        report.append("Spent: BDT ").append(String.format("%.2f", spent)).append(" Crore\n");
        report.append("Remaining: BDT ").append(String.format("%.2f", remaining)).append(" Crore\n");
        report.append("Utilization Rate: ").append(String.format("%.2f", utilizationRate)).append("%\n");
        report.append("=======================================\n");
        
        return report.toString();
    }

    public double getUtilizationRate() {
        if (totalAllocatedAmount > 0) {
            return (totalSpentAmount / totalAllocatedAmount) * 100;
        }
        return 0;
    }

    public void approveBudget() {
        this.budgetStatus = "Approved";
        this.approvalDate = LocalDate.now();
    }

    // Getters and Setters
    public String getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(String budgetId) {
        this.budgetId = budgetId;
    }

    public String getFiscalYear() {
        return fiscalYear;
    }

    public void setFiscalYear(String fiscalYear) {
        this.fiscalYear = fiscalYear;
    }

    public double getTotalAllocatedAmount() {
        return totalAllocatedAmount;
    }

    public void setTotalAllocatedAmount(double totalAllocatedAmount) {
        this.totalAllocatedAmount = totalAllocatedAmount;
        this.totalRemainingAmount = totalAllocatedAmount - totalSpentAmount;
        calculateBudgetDeficit();
    }

    public double getTotalSpentAmount() {
        return totalSpentAmount;
    }

    public double getTotalRemainingAmount() {
        return totalRemainingAmount;
    }

    public Map<String, Double> getDepartmentAllocations() {
        return departmentAllocations;
    }

    public Map<String, Double> getDepartmentExpenditures() {
        return departmentExpenditures;
    }

    public String getBudgetStatus() {
        return budgetStatus;
    }

    public void setBudgetStatus(String budgetStatus) {
        this.budgetStatus = budgetStatus;
    }

    public LocalDate getPreparationDate() {
        return preparationDate;
    }

    public LocalDate getApprovalDate() {
        return approvalDate;
    }

    public double getBudgetDeficit() {
        return budgetDeficit;
    }

    public double getRevenueEstimate() {
        return revenueEstimate;
    }

    public void setRevenueEstimate(double revenueEstimate) {
        this.revenueEstimate = revenueEstimate;
        calculateBudgetDeficit();
    }

    // Simple property getters/setters for controller compatibility
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public double getAllocatedAmount() {
        return allocatedAmount;
    }

    public void setAllocatedAmount(double allocatedAmount) {
        this.allocatedAmount = allocatedAmount;
    }

    public double getSpentAmount() {
        return spentAmount;
    }

    public void setSpentAmount(double spentAmount) {
        this.spentAmount = spentAmount;
    }

    public void trackExecution() {
        // No-op for compatibility
    }

    // Additional getters/setters
    public String getQuarter() { return quarter; }
    public void setQuarter(String quarter) { this.quarter = quarter; }

    @Override
    public String toString() {
        return "BudgetManagement{" +
                "budgetId='" + budgetId + '\'' +
                ", fiscalYear='" + fiscalYear + '\'' +
                ", totalAllocatedAmount=" + totalAllocatedAmount +
                ", budgetStatus='" + budgetStatus + '\'' +
                '}';
    }
}

