package com.example.simulation_of_bangladesh_bank.saida.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * InterbankOperations - Manages interbank borrowing and lending
 * Used by Commercial Bank Manager to coordinate money market operations
 */
public class InterbankOperations implements Serializable {
    private static final long serialVersionUID = 1L;
    private String operationId;
    private String bankId;
    private String counterpartyBank;
    private double borrowAmount;
    private double lendingAmount;
    private double interestRate;
    private double creditLimit;
    private double currentExposure;
    private LocalDate transactionDate;
    private LocalDate maturityDate;
    private String operationType; // Borrow/Lend/CallMoney
    private String status; // Active/Settled/Pending

    // Default credit limits for different bank tiers (in BDT)
    public static final double TIER_1_CREDIT_LIMIT = 5000000000.0;  // 5 Billion - Major banks
    public static final double TIER_2_CREDIT_LIMIT = 2000000000.0;  // 2 Billion - Medium banks
    public static final double TIER_3_CREDIT_LIMIT = 500000000.0;   // 500 Million - Smaller banks

    // Constructors
    public InterbankOperations() {
        this.transactionDate = LocalDate.now();
        this.status = "Pending";
        this.creditLimit = TIER_2_CREDIT_LIMIT; // Default credit limit
        this.currentExposure = 0;
    }

    public InterbankOperations(String operationId, String bankId, String counterpartyBank,
                               double creditLimit) {
        this.operationId = operationId;
        this.bankId = bankId;
        this.counterpartyBank = counterpartyBank;
        this.creditLimit = creditLimit;
        this.transactionDate = LocalDate.now();
        this.status = "Pending";
        this.currentExposure = 0;
    }

    // Business Methods
    public boolean borrow(double amount, double rate, int daysToMaturity) {
        if (validateCreditLimit(amount)) {
            this.borrowAmount = amount;
            this.interestRate = rate;
            this.operationType = "Borrow";
            this.currentExposure += amount;
            this.status = "Active";
            this.maturityDate = LocalDate.now().plusDays(daysToMaturity);
            return true;
        }
        return false;
    }

    public void borrow() {
        this.operationType = "Borrow";
        this.status = "Active";
        this.maturityDate = LocalDate.now().plusDays(7);
    }

    public boolean lend(double amount, double rate, int daysToMaturity) {
        this.lendingAmount = amount;
        this.interestRate = rate;
        this.operationType = "Lend";
        this.status = "Active";
        this.maturityDate = LocalDate.now().plusDays(daysToMaturity);
        return true;
    }

    public void lend() {
        this.operationType = "Lend";
        this.status = "Active";
        this.maturityDate = LocalDate.now().plusDays(7);
    }

    public boolean validateCreditLimit(double requestedAmount) {
        return (currentExposure + requestedAmount) <= creditLimit;
    }

    public boolean validateCreditLimit() {
        return currentExposure < creditLimit;
    }

    public double calculateInterestPayable() {
        if (operationType == null || transactionDate == null || maturityDate == null) return 0;
        
        long days = ChronoUnit.DAYS.between(transactionDate, maturityDate);
        double principal = operationType.equals("Borrow") ? borrowAmount : lendingAmount;
        return (principal * interestRate * days) / (365 * 100);
    }

    public String updateInterbankRecord() {
        StringBuilder record = new StringBuilder();
        record.append("===== INTERBANK TRANSACTION RECORD =====\n");
        record.append("Operation ID: ").append(operationId).append("\n");
        record.append("Bank ID: ").append(bankId).append("\n");
        record.append("Counterparty: ").append(counterpartyBank).append("\n");
        record.append("Operation Type: ").append(operationType).append("\n");
        record.append("Transaction Date: ").append(transactionDate).append("\n");
        record.append("Maturity Date: ").append(maturityDate).append("\n");
        
        if ("Borrow".equals(operationType)) {
            record.append("Borrowed Amount: BDT ").append(String.format("%.2f", borrowAmount)).append("\n");
        } else {
            record.append("Lent Amount: BDT ").append(String.format("%.2f", lendingAmount)).append("\n");
        }
        
        record.append("Interest Rate: ").append(interestRate).append("% p.a.\n");
        record.append("Interest Payable/Receivable: BDT ").append(String.format("%.2f", calculateInterestPayable())).append("\n");
        record.append("Status: ").append(status).append("\n");
        record.append("Current Exposure: BDT ").append(String.format("%.2f", currentExposure)).append("\n");
        record.append("Credit Limit: BDT ").append(String.format("%.2f", creditLimit)).append("\n");
        record.append("Available Limit: BDT ").append(String.format("%.2f", creditLimit - currentExposure)).append("\n");
        record.append("=========================================\n");
        
        return record.toString();
    }

    public String generateConfirmation() {
        StringBuilder confirmation = new StringBuilder();
        confirmation.append("===== INTERBANK TRANSACTION CONFIRMATION =====\n");
        confirmation.append("Reference: ").append(operationId).append("\n");
        confirmation.append("Date: ").append(transactionDate).append("\n\n");
        if (operationType != null) {
            confirmation.append("This confirms the following interbank ").append(operationType.toLowerCase()).append("ing transaction:\n\n");
            confirmation.append("Lender: ").append(operationType.equals("Lend") ? bankId : counterpartyBank).append("\n");
            confirmation.append("Borrower: ").append(operationType.equals("Borrow") ? bankId : counterpartyBank).append("\n");
            confirmation.append("Principal: BDT ").append(String.format("%.2f", operationType.equals("Borrow") ? borrowAmount : lendingAmount)).append("\n");
        }
        confirmation.append("Rate: ").append(interestRate).append("% p.a.\n");
        confirmation.append("Value Date: ").append(transactionDate).append("\n");
        confirmation.append("Maturity Date: ").append(maturityDate).append("\n\n");
        confirmation.append("Status: ").append(status).append("\n");
        confirmation.append("==============================================\n");
        
        return confirmation.toString();
    }

    public void settleTransaction() {
        this.status = "Settled";
    }

    // Getters and Setters
    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getCounterpartyBank() {
        return counterpartyBank;
    }

    public void setCounterpartyBank(String counterpartyBank) {
        this.counterpartyBank = counterpartyBank;
    }

    public double getBorrowAmount() {
        return borrowAmount;
    }

    public void setBorrowAmount(double borrowAmount) {
        this.borrowAmount = borrowAmount;
    }

    public double getLendingAmount() {
        return lendingAmount;
    }

    public void setLendingAmount(double lendingAmount) {
        this.lendingAmount = lendingAmount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public double getCurrentExposure() {
        return currentExposure;
    }

    public void setCurrentExposure(double currentExposure) {
        this.currentExposure = currentExposure;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public LocalDate getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(LocalDate maturityDate) {
        this.maturityDate = maturityDate;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "InterbankOperations{" +
                "operationId='" + operationId + '\'' +
                ", counterpartyBank='" + counterpartyBank + '\'' +
                ", operationType='" + operationType + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
