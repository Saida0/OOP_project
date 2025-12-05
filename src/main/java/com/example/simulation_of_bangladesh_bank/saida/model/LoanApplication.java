package com.example.simulation_of_bangladesh_bank.saida.model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * LoanApplication - Manages corporate loan applications
 * Used by Commercial Bank Manager for loan approval workflow
 */
public class LoanApplication implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Additional fields for the initializer
    private String borrowerId;
    private String borrowerName;
    private String loanType;
    private double requestedAmount;
    private String collateralType;
    private String purpose;
    private String applicationId;
    private String bankId;
    private String applicantName;
    private String businessType;
    private double loanAmount;
    private double collateralValue;
    private double interestRate;
    private int tenureMonths;
    private double creditScore;
    private double debtToIncomeRatio;
    private LocalDate applicationDate;
    private LocalDate approvalDate;
    private String status; // Pending/Approved/Rejected/UnderReview
    private String remarks;
    private String sanctionLetterNumber;

    // Constructors
    public LoanApplication() {
        this.applicationDate = LocalDate.now();
        this.status = "Pending";
    }

    public LoanApplication(String applicationId, String bankId, String applicantName,
                          String businessType, double loanAmount, double collateralValue) {
        this.applicationId = applicationId;
        this.bankId = bankId;
        this.applicantName = applicantName;
        this.businessType = businessType;
        this.loanAmount = loanAmount;
        this.collateralValue = collateralValue;
        this.applicationDate = LocalDate.now();
        this.status = "Pending";
    }

    // Business Methods
    public String assessRisk() {
        StringBuilder assessment = new StringBuilder();
        assessment.append("===== RISK ASSESSMENT REPORT =====\n");
        assessment.append("Application ID: ").append(applicationId).append("\n");
        assessment.append("Applicant: ").append(applicantName).append("\n");
        
        // Credit Score Assessment
        String creditRating;
        if (creditScore >= 750) {
            creditRating = "Excellent";
        } else if (creditScore >= 650) {
            creditRating = "Good";
        } else if (creditScore >= 550) {
            creditRating = "Fair";
        } else {
            creditRating = "Poor";
        }
        assessment.append("Credit Score: ").append(creditScore).append(" (").append(creditRating).append(")\n");
        
        // Debt-to-Income Ratio Assessment
        String dtiRating;
        if (debtToIncomeRatio <= 35) {
            dtiRating = "Healthy";
        } else if (debtToIncomeRatio <= 50) {
            dtiRating = "Manageable";
        } else {
            dtiRating = "High Risk";
        }
        assessment.append("Debt-to-Income Ratio: ").append(debtToIncomeRatio).append("% (").append(dtiRating).append(")\n");
        
        // Collateral Coverage
        double collateralCoverage = (collateralValue / loanAmount) * 100;
        assessment.append("Collateral Coverage: ").append(String.format("%.2f", collateralCoverage)).append("%\n");
        
        // Overall Risk
        String overallRisk = calculateOverallRisk(creditRating, dtiRating, collateralCoverage);
        assessment.append("Overall Risk Level: ").append(overallRisk).append("\n");
        assessment.append("==================================\n");
        
        return assessment.toString();
    }

    private String calculateOverallRisk(String creditRating, String dtiRating, double collateralCoverage) {
        int riskScore = 0;
        
        if (creditRating.equals("Excellent")) riskScore += 3;
        else if (creditRating.equals("Good")) riskScore += 2;
        else if (creditRating.equals("Fair")) riskScore += 1;
        
        if (dtiRating.equals("Healthy")) riskScore += 3;
        else if (dtiRating.equals("Manageable")) riskScore += 2;
        else riskScore += 0;
        
        if (collateralCoverage >= 150) riskScore += 3;
        else if (collateralCoverage >= 100) riskScore += 2;
        else if (collateralCoverage >= 75) riskScore += 1;
        
        if (riskScore >= 7) return "Low Risk";
        else if (riskScore >= 4) return "Medium Risk";
        else return "High Risk";
    }

    public boolean approveLoan(double approvedInterestRate, int approvedTenure, String sanctionNumber) {
        if (this.status.equals("Pending") || this.status.equals("UnderReview")) {
            this.status = "Approved";
            this.interestRate = approvedInterestRate;
            this.tenureMonths = approvedTenure;
            this.sanctionLetterNumber = sanctionNumber;
            this.approvalDate = LocalDate.now();
            return true;
        }
        return false;
    }

    public boolean rejectLoan(String rejectionReason) {
        if (this.status.equals("Pending") || this.status.equals("UnderReview")) {
            this.status = "Rejected";
            this.remarks = rejectionReason;
            return true;
        }
        return false;
    }

    public String generateSanctionLetter() {
        if (!this.status.equals("Approved")) {
            return "Loan not approved. Cannot generate sanction letter.";
        }
        
        StringBuilder letter = new StringBuilder();
        letter.append("===== LOAN SANCTION LETTER =====\n");
        letter.append("Sanction Letter No: ").append(sanctionLetterNumber).append("\n");
        letter.append("Date: ").append(approvalDate).append("\n\n");
        letter.append("To: ").append(applicantName).append("\n");
        letter.append("Business Type: ").append(businessType).append("\n\n");
        letter.append("We are pleased to inform you that your loan application has been approved.\n\n");
        letter.append("Loan Details:\n");
        letter.append("- Application ID: ").append(applicationId).append("\n");
        letter.append("- Sanctioned Amount: BDT ").append(String.format("%.2f", loanAmount)).append("\n");
        letter.append("- Interest Rate: ").append(interestRate).append("% per annum\n");
        letter.append("- Tenure: ").append(tenureMonths).append(" months\n");
        letter.append("- Collateral Value: BDT ").append(String.format("%.2f", collateralValue)).append("\n\n");
        letter.append("Terms and conditions apply.\n");
        letter.append("================================\n");
        
        return letter.toString();
    }

    // Controller-compatible methods
    public int assessRiskScore() {
        int score = 50; // Base score
        double collateralRatio = loanAmount > 0 ? (collateralValue / loanAmount) * 100 : 0;
        if (collateralRatio >= 150) score += 30;
        else if (collateralRatio >= 100) score += 20;
        else if (collateralRatio >= 75) score += 10;
        
        if (creditScore >= 750) score += 20;
        else if (creditScore >= 650) score += 10;
        
        return Math.min(100, score);
    }

    public void approveReject(boolean approve) {
        if (approve) {
            this.status = "Approved";
            this.approvalDate = LocalDate.now();
        } else {
            this.status = "Rejected";
        }
    }

    // Getters and Setters (with ID uppercase aliases for compatibility)
    public String getApplicationID() {
        return applicationId;
    }

    public void setApplicationID(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public double getCollateralValue() {
        return collateralValue;
    }

    public void setCollateralValue(double collateralValue) {
        this.collateralValue = collateralValue;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public int getTenureMonths() {
        return tenureMonths;
    }

    public void setTenureMonths(int tenureMonths) {
        this.tenureMonths = tenureMonths;
    }

    public double getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(double creditScore) {
        this.creditScore = creditScore;
    }

    public double getDebtToIncomeRatio() {
        return debtToIncomeRatio;
    }

    public void setDebtToIncomeRatio(double debtToIncomeRatio) {
        this.debtToIncomeRatio = debtToIncomeRatio;
    }

    public LocalDate getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(LocalDate applicationDate) {
        this.applicationDate = applicationDate;
    }

    public LocalDate getApprovalDate() {
        return approvalDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getSanctionLetterNumber() {
        return sanctionLetterNumber;
    }

    // Additional getters and setters for new fields
    public String getBorrowerId() { return borrowerId; }
    public void setBorrowerId(String borrowerId) { this.borrowerId = borrowerId; }
    
    public String getBorrowerName() { return borrowerName; }
    public void setBorrowerName(String borrowerName) { this.borrowerName = borrowerName; }
    
    public String getLoanType() { return loanType; }
    public void setLoanType(String loanType) { this.loanType = loanType; }
    
    public double getRequestedAmount() { return requestedAmount; }
    public void setRequestedAmount(double requestedAmount) { this.requestedAmount = requestedAmount; }
    
    public String getCollateralType() { return collateralType; }
    public void setCollateralType(String collateralType) { this.collateralType = collateralType; }
    
    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }

    @Override
    public String toString() {
        return "LoanApplication{" +
                "applicationId='" + applicationId + '\'' +
                ", borrowerName='" + borrowerName + '\'' +
                ", requestedAmount=" + requestedAmount +
                ", status='" + status + '\'' +
                '}';
    }
}

