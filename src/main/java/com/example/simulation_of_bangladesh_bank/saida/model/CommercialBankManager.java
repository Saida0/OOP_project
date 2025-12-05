package com.example.simulation_of_bangladesh_bank.saida.model;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;

/**
 * CommercialBankManager - User 7 in the Bangladesh Bank Simulation
 * Responsible for managing commercial bank operations including:
 * - SLR Compliance
 * - Corporate Loan Approvals
 * - Liquidity Management
 * - Monetary Strategy
 * - Capital Adequacy Ratio (CAR) Monitoring
 * - AML Compliance
 * - Interbank Operations
 * - Branch Performance Monitoring
 */
public class CommercialBankManager {
    private String managerId;
    private String bankName;
    private String bankId;
    private String managerName;
    private String email;
    private String phone;
    private LocalDate lastLogin;
    private boolean isLoggedIn;
    
    // Associated operations
    private List<SLRCompliance> slrRecords;
    private List<LoanApplication> loanApplications;
    private List<LiquidityManagement> liquidityRecords;
    private List<CapitalAdequacy> carRecords;
    private List<AMLMonitoring> amlRecords;
    private List<InterbankOperations> interbankRecords;
    private List<BranchPerformance> branchPerformances;
    private List<MonetaryStrategy> strategies;

    // Constructors
    public CommercialBankManager() {
        initializeLists();
    }

    public CommercialBankManager(String managerId, String bankName, String bankId, String managerName) {
        this.managerId = managerId;
        this.bankName = bankName;
        this.bankId = bankId;
        this.managerName = managerName;
        initializeLists();
    }

    private void initializeLists() {
        this.slrRecords = new ArrayList<>();
        this.loanApplications = new ArrayList<>();
        this.liquidityRecords = new ArrayList<>();
        this.carRecords = new ArrayList<>();
        this.amlRecords = new ArrayList<>();
        this.interbankRecords = new ArrayList<>();
        this.branchPerformances = new ArrayList<>();
        this.strategies = new ArrayList<>();
    }

    // ==================== Goal 1: SLR Compliance ====================
    public SLRCompliance manageSLRCompliance(double currentDeposits, double reserveHoldings, 
                                              double slrPercentage) {
        String complianceId = "SLR-" + System.currentTimeMillis();
        SLRCompliance slr = new SLRCompliance(complianceId, bankId, currentDeposits, 
                                              reserveHoldings, slrPercentage);
        slr.validateCompliance();
        slrRecords.add(slr);
        return slr;
    }

    public String getSLRComplianceCertificate(String complianceId) {
        for (SLRCompliance slr : slrRecords) {
            if (slr.getComplianceId().equals(complianceId)) {
                return slr.generateCertificate();
            }
        }
        return "SLR record not found.";
    }

    // ==================== Goal 2: Corporate Loan Approval ====================
    public LoanApplication approveCorporateLoan(String applicantName, String businessType,
                                                double loanAmount, double collateralValue,
                                                double creditScore, double debtToIncomeRatio) {
        String applicationId = "LOAN-" + System.currentTimeMillis();
        LoanApplication loan = new LoanApplication(applicationId, bankId, applicantName, 
                                                   businessType, loanAmount, collateralValue);
        loan.setCreditScore(creditScore);
        loan.setDebtToIncomeRatio(debtToIncomeRatio);
        loanApplications.add(loan);
        return loan;
    }

    public String processLoanDecision(String applicationId, boolean approve, 
                                       double interestRate, int tenure, String remarks) {
        for (LoanApplication loan : loanApplications) {
            if (loan.getApplicationId().equals(applicationId)) {
                if (approve) {
                    String sanctionNumber = "SL-" + System.currentTimeMillis();
                    loan.approveLoan(interestRate, tenure, sanctionNumber);
                    return loan.generateSanctionLetter();
                } else {
                    loan.rejectLoan(remarks);
                    return "Loan application rejected. Reason: " + remarks;
                }
            }
        }
        return "Loan application not found.";
    }

    // ==================== Goal 3: Liquidity Management ====================
    public LiquidityManagement overseeLiquidityManagement(double cashInflows, double cashOutflows,
                                                          double minThreshold) {
        String liquidityId = "LIQ-" + System.currentTimeMillis();
        LiquidityManagement liquidity = new LiquidityManagement(liquidityId, bankId, 
                                                                cashInflows, cashOutflows, minThreshold);
        liquidityRecords.add(liquidity);
        return liquidity;
    }

    public String executeLiquidityAction(String liquidityId, double amount, String action) {
        for (LiquidityManagement liq : liquidityRecords) {
            if (liq.getLiquidityId().equals(liquidityId)) {
                return liq.executeLiquidityAdjustment(amount, action);
            }
        }
        return "Liquidity record not found.";
    }

    // ==================== Goal 4: Monetary Strategy ====================
    public MonetaryStrategy formulateMonetaryStrategy(String fiscalYear, String creditTargets,
                                                      double projectedGrowth, double targetInterestRate,
                                                      double targetLoanGrowth, double targetDepositGrowth) {
        String strategyId = "STR-" + System.currentTimeMillis();
        MonetaryStrategy strategy = new MonetaryStrategy(strategyId, bankId, fiscalYear);
        strategy.inputNewStrategy(creditTargets, projectedGrowth, targetInterestRate, 
                                  targetLoanGrowth, targetDepositGrowth);
        strategies.add(strategy);
        return strategy;
    }

    public String submitStrategyToBB(String strategyId) {
        for (MonetaryStrategy strategy : strategies) {
            if (strategy.getStrategyId().equals(strategyId)) {
                return strategy.submitStrategy();
            }
        }
        return "Strategy not found.";
    }

    // ==================== Goal 5: CAR Monitoring ====================
    public CapitalAdequacy monitorCAR(double riskWeightedAssets, double tier1Capital, 
                                      double tier2Capital) {
        String carId = "CAR-" + System.currentTimeMillis();
        CapitalAdequacy car = new CapitalAdequacy(carId, bankId, riskWeightedAssets, 
                                                  tier1Capital, tier2Capital);
        carRecords.add(car);
        return car;
    }

    public String submitCARReport(String carId) {
        for (CapitalAdequacy car : carRecords) {
            if (car.getCarId().equals(carId)) {
                return car.generateCARReport() + "\n" + car.submitCARReport();
            }
        }
        return "CAR record not found.";
    }

    // ==================== Goal 6: AML Compliance ====================
    public AMLMonitoring superviseAMLCompliance(String transactionId, double transactionAmount,
                                                String transactionType, String accountHolder) {
        String monitoringId = "AML-" + System.currentTimeMillis();
        AMLMonitoring aml = new AMLMonitoring(monitoringId, bankId, transactionId, 
                                              transactionAmount, transactionType);
        aml.setAccountHolder(accountHolder);
        aml.validateAMLRedFlags();
        amlRecords.add(aml);
        return aml;
    }

    public String processAMLInvestigation(String monitoringId, String notes, String status) {
        for (AMLMonitoring aml : amlRecords) {
            if (aml.getMonitoringId().equals(monitoringId)) {
                aml.recordInvestigation(notes, status);
                if (aml.requiresEscalation()) {
                    return aml.generateAMLReport() + "\n" + aml.submitToRegulatory();
                }
                return aml.generateAMLReport();
            }
        }
        return "AML record not found.";
    }

    // ==================== Goal 7: Interbank Operations ====================
    public InterbankOperations coordinateInterbankOps(String counterpartyBank, double creditLimit) {
        String operationId = "IBO-" + System.currentTimeMillis();
        InterbankOperations interbank = new InterbankOperations(operationId, bankId, 
                                                                counterpartyBank, creditLimit);
        interbankRecords.add(interbank);
        return interbank;
    }

    public String executeInterbankTransaction(String operationId, String type, 
                                               double amount, double rate, int days) {
        for (InterbankOperations ibo : interbankRecords) {
            if (ibo.getOperationId().equals(operationId)) {
                boolean success;
                if (type.equalsIgnoreCase("borrow")) {
                    success = ibo.borrow(amount, rate, days);
                } else {
                    success = ibo.lend(amount, rate, days);
                }
                if (success) {
                    return ibo.generateConfirmation();
                }
                return "Transaction failed. Credit limit exceeded or invalid parameters.";
            }
        }
        return "Interbank operation not found.";
    }

    // ==================== Goal 8: Branch Performance ====================
    public BranchPerformance monitorBranchPerformance(String branchId, String branchName, 
                                                      String region, double depositGrowth,
                                                      double loanGrowth, double npaRatio,
                                                      double customerSatisfaction) {
        String performanceId = "BP-" + System.currentTimeMillis();
        BranchPerformance performance = new BranchPerformance(performanceId, bankId, 
                                                              branchId, branchName, region);
        performance.loadBranchData(depositGrowth, loanGrowth, npaRatio, customerSatisfaction);
        branchPerformances.add(performance);
        return performance;
    }

    public String getBranchImprovementRecommendations(String performanceId) {
        for (BranchPerformance bp : branchPerformances) {
            if (bp.getPerformanceId().equals(performanceId)) {
                if (bp.identifyWeakBranch()) {
                    return bp.recommendImprovements();
                }
                return "Branch is performing well. No immediate improvements needed.";
            }
        }
        return "Branch performance record not found.";
    }

    // ==================== Dashboard Summary ====================
    public String generateDashboardSummary() {
        StringBuilder dashboard = new StringBuilder();
        dashboard.append("╔══════════════════════════════════════════════════════════════╗\n");
        dashboard.append("║         COMMERCIAL BANK MANAGER DASHBOARD                    ║\n");
        dashboard.append("╠══════════════════════════════════════════════════════════════╣\n");
        dashboard.append("║ Manager: ").append(String.format("%-51s", managerName)).append("║\n");
        dashboard.append("║ Bank: ").append(String.format("%-54s", bankName)).append("║\n");
        dashboard.append("║ Bank ID: ").append(String.format("%-51s", bankId)).append("║\n");
        dashboard.append("╠══════════════════════════════════════════════════════════════╣\n");
        dashboard.append("║                    OPERATIONAL SUMMARY                       ║\n");
        dashboard.append("╠══════════════════════════════════════════════════════════════╣\n");
        dashboard.append("║ SLR Records: ").append(String.format("%-47d", slrRecords.size())).append("║\n");
        dashboard.append("║ Loan Applications: ").append(String.format("%-41d", loanApplications.size())).append("║\n");
        dashboard.append("║ Liquidity Records: ").append(String.format("%-41d", liquidityRecords.size())).append("║\n");
        dashboard.append("║ CAR Records: ").append(String.format("%-47d", carRecords.size())).append("║\n");
        dashboard.append("║ AML Monitoring Cases: ").append(String.format("%-38d", amlRecords.size())).append("║\n");
        dashboard.append("║ Interbank Operations: ").append(String.format("%-38d", interbankRecords.size())).append("║\n");
        dashboard.append("║ Branch Evaluations: ").append(String.format("%-40d", branchPerformances.size())).append("║\n");
        dashboard.append("║ Monetary Strategies: ").append(String.format("%-39d", strategies.size())).append("║\n");
        dashboard.append("╚══════════════════════════════════════════════════════════════╝\n");
        
        return dashboard.toString();
    }

    // Getters and Setters
    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDate lastLogin) {
        this.lastLogin = lastLogin;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
        if (loggedIn) {
            this.lastLogin = LocalDate.now();
        }
    }

    public List<SLRCompliance> getSlrRecords() {
        return slrRecords;
    }

    public List<LoanApplication> getLoanApplications() {
        return loanApplications;
    }

    public List<LiquidityManagement> getLiquidityRecords() {
        return liquidityRecords;
    }

    public List<CapitalAdequacy> getCarRecords() {
        return carRecords;
    }

    public List<AMLMonitoring> getAmlRecords() {
        return amlRecords;
    }

    public List<InterbankOperations> getInterbankRecords() {
        return interbankRecords;
    }

    public List<BranchPerformance> getBranchPerformances() {
        return branchPerformances;
    }

    public List<MonetaryStrategy> getStrategies() {
        return strategies;
    }

    @Override
    public String toString() {
        return "CommercialBankManager{" +
                "managerId='" + managerId + '\'' +
                ", bankName='" + bankName + '\'' +
                ", managerName='" + managerName + '\'' +
                '}';
    }
}

