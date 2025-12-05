package com.example.simulation_of_bangladesh_bank.saida.model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * ExpenditureControl - Manages government expenditure authorization and control
 * Used by Ministry of Finance Representative for expenditure management
 */
public class ExpenditureControl implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Additional fields
    private String ministry;
    private String budgetHead;
    private double approvedAmount;
    private double disbursedAmount;
    private String status;
    private String expenditureId;
    private String department;
    private String purpose;
    private double requestedAmount;
    private double allocatedBudget;
    private double spentAmount;
    private double remainingBudget;
    private String approvalStatus; // Pending/Approved/Rejected/PartiallyApproved
    private String priority; // High/Medium/Low
    private LocalDate requestDate;
    private LocalDate approvalDate;
    private String approverName;
    private String remarks;

    // Constructors
    public ExpenditureControl() {
        this.requestDate = LocalDate.now();
        this.approvalStatus = "Pending";
    }

    public ExpenditureControl(String expenditureId, String department, String purpose,
                             double requestedAmount, double allocatedBudget) {
        this.expenditureId = expenditureId;
        this.department = department;
        this.purpose = purpose;
        this.requestedAmount = requestedAmount;
        this.allocatedBudget = allocatedBudget;
        this.remainingBudget = allocatedBudget - spentAmount;
        this.requestDate = LocalDate.now();
        this.approvalStatus = "Pending";
    }

    // Business Methods
    public boolean validateBudget() {
        this.remainingBudget = allocatedBudget - spentAmount;
        return requestedAmount <= remainingBudget;
    }

    // No-arg overload for controller compatibility
    public void approveExpenditure() {
        approveExpenditure("System", "Auto-approved");
    }

    public String approveExpenditure(String approver, String remarks) {
        if (!validateBudget()) {
            this.approvalStatus = "Rejected";
            this.remarks = "Insufficient budget. Available: BDT " + remainingBudget;
            return "Expenditure rejected due to insufficient budget.";
        }
        
        this.approvalStatus = "Approved";
        this.approverName = approver;
        this.approvalDate = LocalDate.now();
        this.remarks = remarks;
        this.spentAmount += requestedAmount;
        this.remainingBudget = allocatedBudget - spentAmount;
        
        return generateExpenditureReport();
    }

    public String partialApproval(String approver, double approvedAmount, String remarks) {
        if (approvedAmount > remainingBudget) {
            approvedAmount = remainingBudget;
        }
        
        this.approvalStatus = "PartiallyApproved";
        this.approverName = approver;
        this.approvalDate = LocalDate.now();
        this.remarks = "Partially approved. Requested: " + requestedAmount + 
                      ", Approved: " + approvedAmount + ". " + remarks;
        this.spentAmount += approvedAmount;
        this.remainingBudget = allocatedBudget - spentAmount;
        
        return generateExpenditureReport();
    }

    public String rejectExpenditure(String approver, String reason) {
        this.approvalStatus = "Rejected";
        this.approverName = approver;
        this.approvalDate = LocalDate.now();
        this.remarks = reason;
        
        return "Expenditure request rejected. Reason: " + reason;
    }

    public String generateExpenditureReport() {
        StringBuilder report = new StringBuilder();
        report.append("===== EXPENDITURE AUTHORIZATION REPORT =====\n");
        report.append("Expenditure ID: ").append(expenditureId).append("\n");
        report.append("Department: ").append(department).append("\n");
        report.append("Purpose: ").append(purpose).append("\n");
        report.append("Priority: ").append(priority != null ? priority : "Not Set").append("\n\n");
        
        report.append("Financial Details:\n");
        report.append("  Allocated Budget: BDT ").append(String.format("%.2f", allocatedBudget)).append("\n");
        report.append("  Requested Amount: BDT ").append(String.format("%.2f", requestedAmount)).append("\n");
        report.append("  Previously Spent: BDT ").append(String.format("%.2f", spentAmount - requestedAmount)).append("\n");
        report.append("  Remaining Budget: BDT ").append(String.format("%.2f", remainingBudget)).append("\n\n");
        
        report.append("Approval Details:\n");
        report.append("  Status: ").append(approvalStatus).append("\n");
        report.append("  Request Date: ").append(requestDate).append("\n");
        if (approvalDate != null) {
            report.append("  Approval Date: ").append(approvalDate).append("\n");
            report.append("  Approved By: ").append(approverName).append("\n");
        }
        
        if (remarks != null && !remarks.isEmpty()) {
            report.append("\nRemarks: ").append(remarks).append("\n");
        }
        
        report.append("=============================================\n");
        return report.toString();
    }

    public double getBudgetUtilizationRate() {
        if (allocatedBudget > 0) {
            return (spentAmount / allocatedBudget) * 100;
        }
        return 0;
    }

    // Getters and Setters
    public String getExpenditureId() {
        return expenditureId;
    }

    public void setExpenditureId(String expenditureId) {
        this.expenditureId = expenditureId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public double getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(double requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public double getAllocatedBudget() {
        return allocatedBudget;
    }

    public void setAllocatedBudget(double allocatedBudget) {
        this.allocatedBudget = allocatedBudget;
        this.remainingBudget = allocatedBudget - spentAmount;
    }

    public double getSpentAmount() {
        return spentAmount;
    }

    public void setSpentAmount(double spentAmount) {
        this.spentAmount = spentAmount;
        this.remainingBudget = allocatedBudget - spentAmount;
    }

    public double getRemainingBudget() {
        return remainingBudget;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public LocalDate getApprovalDate() {
        return approvalDate;
    }

    public String getApproverName() {
        return approverName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    // Additional getters/setters
    public String getMinistry() { return ministry; }
    public void setMinistry(String ministry) { this.ministry = ministry; }
    
    public String getBudgetHead() { return budgetHead; }
    public void setBudgetHead(String budgetHead) { this.budgetHead = budgetHead; }
    
    public double getApprovedAmount() { return approvedAmount; }
    public void setApprovedAmount(double approvedAmount) { this.approvedAmount = approvedAmount; }
    
    public double getDisbursedAmount() { return disbursedAmount; }
    public void setDisbursedAmount(double disbursedAmount) { this.disbursedAmount = disbursedAmount; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public void setRequestDate(LocalDate requestDate) { this.requestDate = requestDate; }

    @Override
    public String toString() {
        return "ExpenditureControl{" +
                "expenditureId='" + expenditureId + '\'' +
                ", ministry='" + ministry + '\'' +
                ", requestedAmount=" + requestedAmount +
                ", status='" + status + '\'' +
                '}';
    }
}

