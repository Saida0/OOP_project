package com.example.simulation_of_bangladesh_bank.saida.model;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;

/**
 * MinistryFinanceRep - User 8 in the Bangladesh Bank Simulation
 * Ministry of Finance Representative responsible for:
 * - Revenue Monitoring
 * - Expenditure Authorization
 * - Public Debt Management
 * - Fiscal-Monetary Coordination
 * - Foreign Aid Monitoring
 * - Budget Supervision
 * - Government Cash Management
 * - Economic Reform Monitoring
 */
public class MinistryFinanceRep {
    private String representativeId;
    private String department;
    private String representativeName;
    private String designation;
    private String email;
    private String phone;
    private LocalDate lastLogin;
    private boolean isLoggedIn;

    // Associated operations
    private List<RevenueMonitoring> revenueRecords;
    private List<ExpenditureControl> expenditureRecords;
    private List<PublicDebt> debtRecords;
    private List<FiscalCoordination> coordinationRecords;
    private List<ForeignAidMonitoring> aidRecords;
    private List<BudgetManagement> budgetRecords;
    private List<GovCashBalance> cashRecords;
    private List<ReformMonitoring> reformRecords;

    // Constructors
    public MinistryFinanceRep() {
        initializeLists();
    }

    public MinistryFinanceRep(String representativeId, String department, 
                              String representativeName, String designation) {
        this.representativeId = representativeId;
        this.department = department;
        this.representativeName = representativeName;
        this.designation = designation;
        initializeLists();
    }

    private void initializeLists() {
        this.revenueRecords = new ArrayList<>();
        this.expenditureRecords = new ArrayList<>();
        this.debtRecords = new ArrayList<>();
        this.coordinationRecords = new ArrayList<>();
        this.aidRecords = new ArrayList<>();
        this.budgetRecords = new ArrayList<>();
        this.cashRecords = new ArrayList<>();
        this.reformRecords = new ArrayList<>();
    }

    // ==================== Goal 1: Revenue Monitoring ====================
    public RevenueMonitoring monitorRevenueCollection(String fiscalYear, String quarter,
                                                      double revenueTarget, double taxCollection,
                                                      double nonTaxRevenue) {
        String revenueId = "REV-" + System.currentTimeMillis();
        RevenueMonitoring revenue = new RevenueMonitoring(revenueId, fiscalYear, quarter, revenueTarget);
        revenue.loadRevenueData(taxCollection, nonTaxRevenue);
        revenueRecords.add(revenue);
        return revenue;
    }

    public String getRevenuePerformance(String revenueId) {
        for (RevenueMonitoring rev : revenueRecords) {
            if (rev.getRevenueId().equals(revenueId)) {
                return rev.generatePerformanceReport();
            }
        }
        return "Revenue record not found.";
    }

    // ==================== Goal 2: Expenditure Authorization ====================
    public ExpenditureControl authorizeExpenditure(String department, String purpose,
                                                   double requestedAmount, double allocatedBudget,
                                                   String priority) {
        String expenditureId = "EXP-" + System.currentTimeMillis();
        ExpenditureControl expenditure = new ExpenditureControl(expenditureId, department, 
                                                                purpose, requestedAmount, allocatedBudget);
        expenditure.setPriority(priority);
        expenditureRecords.add(expenditure);
        return expenditure;
    }

    public String processExpenditureDecision(String expenditureId, boolean approve, String remarks) {
        for (ExpenditureControl exp : expenditureRecords) {
            if (exp.getExpenditureId().equals(expenditureId)) {
                if (approve) {
                    return exp.approveExpenditure(representativeName, remarks);
                } else {
                    return exp.rejectExpenditure(representativeName, remarks);
                }
            }
        }
        return "Expenditure record not found.";
    }

    // ==================== Goal 3: Public Debt Management ====================
    public PublicDebt managePublicDebt(String debtType, String creditor, double principalAmount,
                                       double interestRate, LocalDate maturityDate) {
        String debtId = "DEBT-" + System.currentTimeMillis();
        PublicDebt debt = new PublicDebt(debtId, debtType, creditor, principalAmount, 
                                         interestRate, maturityDate);
        debtRecords.add(debt);
        return debt;
    }

    public String processDebtPayment(String debtId, double cashBalance) {
        for (PublicDebt debt : debtRecords) {
            if (debt.getDebtId().equals(debtId)) {
                String authorization = debt.authorizePayment(cashBalance);
                if (authorization.contains("AUTHORIZED")) {
                    return authorization + "\n" + debt.updateDebtRecords(debt.getPaymentAmount());
                }
                return authorization;
            }
        }
        return "Debt record not found.";
    }

    // ==================== Goal 4: Fiscal-Monetary Coordination ====================
    public FiscalCoordination coordinatePolicyWithBB(String fiscalYear, String fiscalPolicyStance,
                                                     String monetaryPolicyStatus, double targetInflation,
                                                     double gdpGrowthTarget) {
        String coordinationId = "COORD-" + System.currentTimeMillis();
        FiscalCoordination coordination = new FiscalCoordination(coordinationId, fiscalYear, 
                                                                 fiscalPolicyStance);
        coordination.loadMonetaryPolicy(monetaryPolicyStatus, targetInflation, gdpGrowthTarget);
        coordinationRecords.add(coordination);
        return coordination;
    }

    public String generateCoordinationMeeting(String coordinationId, double fiscalDeficit, 
                                               double actualInflation) {
        for (FiscalCoordination coord : coordinationRecords) {
            if (coord.getCoordinationId().equals(coordinationId)) {
                coord.setFiscalParameters(fiscalDeficit, actualInflation);
                return coord.generateCoordinationAgenda();
            }
        }
        return "Coordination record not found.";
    }

    // ==================== Goal 5: Foreign Aid Monitoring ====================
    public ForeignAidMonitoring monitorForeignAid(String projectName, String donorAgency,
                                                  double aidAmount, String projectSector,
                                                  double disbursedAmount, double utilizedAmount,
                                                  String implementingAgency) {
        String aidId = "AID-" + System.currentTimeMillis();
        ForeignAidMonitoring aid = new ForeignAidMonitoring(aidId, projectName, donorAgency, 
                                                            aidAmount, projectSector);
        aid.loadAidData(disbursedAmount, utilizedAmount, implementingAgency);
        aidRecords.add(aid);
        return aid;
    }

    public String getAidUtilizationReport(String aidId) {
        for (ForeignAidMonitoring aid : aidRecords) {
            if (aid.getAidId().equals(aidId)) {
                return aid.generateAidReport();
            }
        }
        return "Aid record not found.";
    }

    // ==================== Goal 6: Budget Supervision ====================
    public BudgetManagement superviseNationalBudget(String fiscalYear, double totalAllocation,
                                                    double revenueEstimate) {
        String budgetId = "BUD-" + System.currentTimeMillis();
        BudgetManagement budget = new BudgetManagement(budgetId, fiscalYear, totalAllocation, 
                                                       revenueEstimate);
        budgetRecords.add(budget);
        return budget;
    }

    public String allocateDepartmentBudget(String budgetId, String department, double amount) {
        for (BudgetManagement budget : budgetRecords) {
            if (budget.getBudgetId().equals(budgetId)) {
                budget.addDepartmentAllocation(department, amount);
                return "Allocated BDT " + amount + " Crore to " + department;
            }
        }
        return "Budget record not found.";
    }

    public String trackBudgetExecution(String budgetId, String department, double expenditure) {
        for (BudgetManagement budget : budgetRecords) {
            if (budget.getBudgetId().equals(budgetId)) {
                return budget.trackExecution(department, expenditure);
            }
        }
        return "Budget record not found.";
    }

    // ==================== Goal 7: Government Cash Management ====================
    public GovCashBalance manageGovernmentCash(double cashBalance, double minimumBalance,
                                               double dailyRequirement) {
        String balanceId = "CASH-" + System.currentTimeMillis();
        GovCashBalance cash = new GovCashBalance(balanceId, cashBalance, minimumBalance);
        cash.loadCashData(0, 0, dailyRequirement);
        cashRecords.add(cash);
        return cash;
    }

    public String updateCashPosition(String balanceId, double amount, String transactionType) {
        for (GovCashBalance cash : cashRecords) {
            if (cash.getBalanceId().equals(balanceId)) {
                return cash.updateCashRecords(amount, transactionType);
            }
        }
        return "Cash record not found.";
    }

    public String getCashManagementStrategy(String balanceId) {
        for (GovCashBalance cash : cashRecords) {
            if (cash.getBalanceId().equals(balanceId)) {
                return cash.generateCashManagementStrategy();
            }
        }
        return "Cash record not found.";
    }

    // ==================== Goal 8: Economic Reform Monitoring ====================
    public ReformMonitoring monitorEconomicReforms(String reformName, String reformCategory,
                                                   LocalDate targetDate, String responsibleAgency) {
        String reformId = "REFORM-" + System.currentTimeMillis();
        ReformMonitoring reform = new ReformMonitoring(reformId, reformName, reformCategory, 
                                                       targetDate, responsibleAgency);
        reformRecords.add(reform);
        return reform;
    }

    public String addReformMilestone(String reformId, String milestone) {
        for (ReformMonitoring reform : reformRecords) {
            if (reform.getReformId().equals(reformId)) {
                reform.addMilestone(milestone);
                return "Milestone added: " + milestone;
            }
        }
        return "Reform record not found.";
    }

    public String completeMilestone(String reformId, String milestone) {
        for (ReformMonitoring reform : reformRecords) {
            if (reform.getReformId().equals(reformId)) {
                reform.completeMilestone(milestone);
                return reform.generateReformReport();
            }
        }
        return "Reform record not found.";
    }

    public String getReformRecommendations(String reformId) {
        for (ReformMonitoring reform : reformRecords) {
            if (reform.getReformId().equals(reformId)) {
                return reform.recommendCorrectiveActions();
            }
        }
        return "Reform record not found.";
    }

    // ==================== Dashboard Summary ====================
    public String generateDashboardSummary() {
        StringBuilder dashboard = new StringBuilder();
        dashboard.append("╔══════════════════════════════════════════════════════════════╗\n");
        dashboard.append("║       MINISTRY OF FINANCE REPRESENTATIVE DASHBOARD           ║\n");
        dashboard.append("╠══════════════════════════════════════════════════════════════╣\n");
        dashboard.append("║ Representative: ").append(String.format("%-44s", representativeName)).append("║\n");
        dashboard.append("║ Designation: ").append(String.format("%-47s", designation)).append("║\n");
        dashboard.append("║ Department: ").append(String.format("%-48s", department)).append("║\n");
        dashboard.append("╠══════════════════════════════════════════════════════════════╣\n");
        dashboard.append("║                    OPERATIONAL SUMMARY                       ║\n");
        dashboard.append("╠══════════════════════════════════════════════════════════════╣\n");
        dashboard.append("║ Revenue Records: ").append(String.format("%-43d", revenueRecords.size())).append("║\n");
        dashboard.append("║ Expenditure Requests: ").append(String.format("%-38d", expenditureRecords.size())).append("║\n");
        dashboard.append("║ Public Debt Records: ").append(String.format("%-39d", debtRecords.size())).append("║\n");
        dashboard.append("║ Policy Coordinations: ").append(String.format("%-38d", coordinationRecords.size())).append("║\n");
        dashboard.append("║ Foreign Aid Projects: ").append(String.format("%-38d", aidRecords.size())).append("║\n");
        dashboard.append("║ Budget Records: ").append(String.format("%-44d", budgetRecords.size())).append("║\n");
        dashboard.append("║ Cash Balance Records: ").append(String.format("%-38d", cashRecords.size())).append("║\n");
        dashboard.append("║ Reform Programs: ").append(String.format("%-43d", reformRecords.size())).append("║\n");
        dashboard.append("╠══════════════════════════════════════════════════════════════╣\n");
        
        // Pending items summary
        long pendingExpenditures = expenditureRecords.stream()
                .filter(e -> e.getApprovalStatus().equals("Pending")).count();
        long delayedReforms = reformRecords.stream()
                .filter(r -> r.getMilestoneStatus().equals("Delayed")).count();
        
        dashboard.append("║                    ATTENTION REQUIRED                        ║\n");
        dashboard.append("╠══════════════════════════════════════════════════════════════╣\n");
        dashboard.append("║ Pending Expenditure Approvals: ").append(String.format("%-28d", pendingExpenditures)).append("║\n");
        dashboard.append("║ Delayed Reform Programs: ").append(String.format("%-35d", delayedReforms)).append("║\n");
        dashboard.append("╚══════════════════════════════════════════════════════════════╝\n");
        
        return dashboard.toString();
    }

    // ==================== Comprehensive Reports ====================
    public String generateFiscalSummaryReport() {
        StringBuilder report = new StringBuilder();
        report.append("═══════════════════════════════════════════════════════════════\n");
        report.append("              COMPREHENSIVE FISCAL SUMMARY REPORT               \n");
        report.append("═══════════════════════════════════════════════════════════════\n\n");
        
        // Revenue Summary
        report.append("REVENUE COLLECTION SUMMARY:\n");
        report.append("───────────────────────────────────────────────────────────────\n");
        double totalRevenue = revenueRecords.stream()
                .mapToDouble(RevenueMonitoring::getActualRevenue).sum();
        report.append("Total Revenue Collected: BDT ").append(String.format("%.2f", totalRevenue)).append(" Crore\n\n");
        
        // Expenditure Summary
        report.append("EXPENDITURE SUMMARY:\n");
        report.append("───────────────────────────────────────────────────────────────\n");
        double totalExpenditure = expenditureRecords.stream()
                .filter(e -> e.getApprovalStatus().equals("Approved"))
                .mapToDouble(ExpenditureControl::getRequestedAmount).sum();
        report.append("Total Approved Expenditure: BDT ").append(String.format("%.2f", totalExpenditure)).append(" Crore\n\n");
        
        // Debt Summary
        report.append("PUBLIC DEBT SUMMARY:\n");
        report.append("───────────────────────────────────────────────────────────────\n");
        double totalDebt = debtRecords.stream()
                .mapToDouble(PublicDebt::getOutstandingDebt).sum();
        report.append("Total Outstanding Debt: BDT ").append(String.format("%.2f", totalDebt)).append(" Crore\n\n");
        
        // Foreign Aid Summary
        report.append("FOREIGN AID SUMMARY:\n");
        report.append("───────────────────────────────────────────────────────────────\n");
        double totalAid = aidRecords.stream()
                .mapToDouble(ForeignAidMonitoring::getAidAmount).sum();
        double utilizedAid = aidRecords.stream()
                .mapToDouble(ForeignAidMonitoring::getUtilizedAmount).sum();
        report.append("Total Aid Commitment: USD ").append(String.format("%.2f", totalAid)).append(" Million\n");
        report.append("Total Aid Utilized: USD ").append(String.format("%.2f", utilizedAid)).append(" Million\n\n");
        
        report.append("═══════════════════════════════════════════════════════════════\n");
        
        return report.toString();
    }

    // Getters and Setters
    public String getRepresentativeId() {
        return representativeId;
    }

    public void setRepresentativeId(String representativeId) {
        this.representativeId = representativeId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getRepresentativeName() {
        return representativeName;
    }

    public void setRepresentativeName(String representativeName) {
        this.representativeName = representativeName;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
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

    public List<RevenueMonitoring> getRevenueRecords() {
        return revenueRecords;
    }

    public List<ExpenditureControl> getExpenditureRecords() {
        return expenditureRecords;
    }

    public List<PublicDebt> getDebtRecords() {
        return debtRecords;
    }

    public List<FiscalCoordination> getCoordinationRecords() {
        return coordinationRecords;
    }

    public List<ForeignAidMonitoring> getAidRecords() {
        return aidRecords;
    }

    public List<BudgetManagement> getBudgetRecords() {
        return budgetRecords;
    }

    public List<GovCashBalance> getCashRecords() {
        return cashRecords;
    }

    public List<ReformMonitoring> getReformRecords() {
        return reformRecords;
    }

    @Override
    public String toString() {
        return "MinistryFinanceRep{" +
                "representativeId='" + representativeId + '\'' +
                ", representativeName='" + representativeName + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}

