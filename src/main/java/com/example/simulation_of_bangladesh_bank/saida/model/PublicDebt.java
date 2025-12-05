package com.example.simulation_of_bangladesh_bank.saida.model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * PublicDebt - Manages public debt monitoring and servicing
 * Used by Ministry of Finance Representative for debt management
 */
public class PublicDebt implements Serializable {
    private static final long serialVersionUID = 1L;
    private String debtId;
    private String debtType; // Domestic/External/Treasury
    private String creditor;
    private double outstandingDebt;
    private double principalAmount;
    private double interestRate;
    private double paymentAmount;
    private LocalDate nextPaymentDate;
    private LocalDate maturityDate;
    private String paymentFrequency; // Monthly/Quarterly/Annually
    private double accruedInterest;
    private String debtStatus; // Active/Paid/Defaulted
    private double availableCash;

    // Constructors
    public PublicDebt() {
        this.debtStatus = "Active";
    }

    public PublicDebt(String debtId, String debtType, String creditor, 
                     double principalAmount, double interestRate, LocalDate maturityDate) {
        this.debtId = debtId;
        this.debtType = debtType;
        this.creditor = creditor;
        this.principalAmount = principalAmount;
        this.outstandingDebt = principalAmount;
        this.interestRate = interestRate;
        this.maturityDate = maturityDate;
        this.debtStatus = "Active";
        calculateNextPayment();
    }

    // Business Methods
    private void calculateNextPayment() {
        this.accruedInterest = (outstandingDebt * interestRate) / 100;
        
        if (paymentFrequency == null) {
            this.paymentFrequency = "Quarterly";
        }
        
        this.nextPaymentDate = LocalDate.now().plusMonths(3);
        this.paymentAmount = accruedInterest / 4;
    }

    public String generateServiceSchedule() {
        StringBuilder schedule = new StringBuilder();
        schedule.append("===== DEBT SERVICE SCHEDULE =====\n");
        schedule.append("Debt ID: ").append(debtId).append("\n");
        schedule.append("Type: ").append(debtType).append("\n");
        schedule.append("Creditor: ").append(creditor).append("\n\n");
        
        schedule.append("Debt Details:\n");
        schedule.append("  Principal Amount: BDT ").append(String.format("%.2f", principalAmount)).append(" Crore\n");
        schedule.append("  Outstanding Debt: BDT ").append(String.format("%.2f", outstandingDebt)).append(" Crore\n");
        schedule.append("  Interest Rate: ").append(interestRate).append("% p.a.\n");
        schedule.append("  Accrued Interest: BDT ").append(String.format("%.2f", accruedInterest)).append(" Crore\n\n");
        
        schedule.append("Payment Schedule:\n");
        schedule.append("  Payment Frequency: ").append(paymentFrequency).append("\n");
        schedule.append("  Next Payment Date: ").append(nextPaymentDate).append("\n");
        schedule.append("  Payment Amount: BDT ").append(String.format("%.2f", paymentAmount)).append(" Crore\n");
        schedule.append("  Maturity Date: ").append(maturityDate).append("\n\n");
        
        schedule.append("Status: ").append(debtStatus).append("\n");
        schedule.append("=================================\n");
        
        return schedule.toString();
    }

    public boolean validateCashAvailability(double cashBalance) {
        this.availableCash = cashBalance;
        return cashBalance >= paymentAmount;
    }

    public String authorizePayment(double cashBalance) {
        if (!validateCashAvailability(cashBalance)) {
            return "Payment authorization failed. Insufficient cash balance.\n" +
                   "Required: BDT " + paymentAmount + " Crore\n" +
                   "Available: BDT " + cashBalance + " Crore";
        }
        
        StringBuilder authorization = new StringBuilder();
        authorization.append("===== DEBT PAYMENT AUTHORIZATION =====\n");
        authorization.append("Debt ID: ").append(debtId).append("\n");
        authorization.append("Creditor: ").append(creditor).append("\n");
        authorization.append("Payment Amount: BDT ").append(String.format("%.2f", paymentAmount)).append(" Crore\n");
        authorization.append("Payment Date: ").append(LocalDate.now()).append("\n");
        authorization.append("Status: AUTHORIZED\n");
        authorization.append("======================================\n");
        
        return authorization.toString();
    }

    public void authorizePayments() {
        this.debtStatus = "Payment Authorized";
    }

    public String updateDebtRecords(double paymentMade) {
        if (paymentMade > 0) {
            this.outstandingDebt -= (paymentMade - (paymentMade * interestRate / 100));
            if (outstandingDebt <= 0) {
                this.outstandingDebt = 0;
                this.debtStatus = "Paid";
            }
            calculateNextPayment();
        }
        
        StringBuilder update = new StringBuilder();
        update.append("===== DEBT RECORD UPDATE =====\n");
        update.append("Payment Made: BDT ").append(String.format("%.2f", paymentMade)).append(" Crore\n");
        update.append("New Outstanding Balance: BDT ").append(String.format("%.2f", outstandingDebt)).append(" Crore\n");
        update.append("Debt Status: ").append(debtStatus).append("\n");
        update.append("Next Payment Date: ").append(nextPaymentDate).append("\n");
        update.append("==============================\n");
        
        return update.toString();
    }

    public void updateDebtRecords() {
        calculateNextPayment();
    }

    public double getDebtToGDPRatio(double gdp) {
        if (gdp > 0) {
            return (outstandingDebt / gdp) * 100;
        }
        return 0;
    }

    public boolean isPaymentDue() {
        return nextPaymentDate != null && nextPaymentDate.isBefore(LocalDate.now());
    }

    // Getters and Setters
    public String getDebtId() {
        return debtId;
    }

    public void setDebtId(String debtId) {
        this.debtId = debtId;
    }

    public String getDebtType() {
        return debtType;
    }

    public void setDebtType(String debtType) {
        this.debtType = debtType;
    }

    public String getCreditor() {
        return creditor;
    }

    public void setCreditor(String creditor) {
        this.creditor = creditor;
    }

    public double getOutstandingDebt() {
        return outstandingDebt;
    }

    public void setOutstandingDebt(double outstandingDebt) {
        this.outstandingDebt = outstandingDebt;
    }

    public double getPrincipalAmount() {
        return principalAmount;
    }

    public void setPrincipalAmount(double principalAmount) {
        this.principalAmount = principalAmount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public LocalDate getNextPaymentDate() {
        return nextPaymentDate;
    }

    public void setNextPaymentDate(LocalDate nextPaymentDate) {
        this.nextPaymentDate = nextPaymentDate;
    }

    public LocalDate getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(LocalDate maturityDate) {
        this.maturityDate = maturityDate;
    }

    public String getPaymentFrequency() {
        return paymentFrequency;
    }

    public void setPaymentFrequency(String paymentFrequency) {
        this.paymentFrequency = paymentFrequency;
    }

    public double getAccruedInterest() {
        return accruedInterest;
    }

    public String getDebtStatus() {
        return debtStatus;
    }

    public void setDebtStatus(String debtStatus) {
        this.debtStatus = debtStatus;
    }

    @Override
    public String toString() {
        return "PublicDebt{" +
                "debtId='" + debtId + '\'' +
                ", debtType='" + debtType + '\'' +
                ", outstandingDebt=" + outstandingDebt +
                ", debtStatus='" + debtStatus + '\'' +
                '}';
    }
}
