package com.example.simulation_of_bangladesh_bank.saida.util;

import com.example.simulation_of_bangladesh_bank.saida.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DataInitializer - Creates realistic initial data for the Bangladesh Bank Simulation.
 * This data represents a realistic starting scenario for a commercial bank.
 */
public class DataInitializer {

    /**
     * Initializes all data files with realistic startup data if they don't exist.
     */
    public static void initializeAllData() {
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║        Bangladesh Bank Simulation - Data Initialization      ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        
        initializeSLRData();
        initializeLoanData();
        initializeInterbankData();
        initializeLiquidityData();
        initializeCARData();
        initializeAMLData();
        initializeBranchData();
        initializeMonetaryStrategyData();
        
        // Ministry of Finance data
        initializeRevenueData();
        initializeExpenditureData();
        initializePublicDebtData();
        initializeBudgetData();
        initializeForeignAidData();
        initializeReformData();
        initializeCashBalanceData();
        
        System.out.println("════════════════════════════════════════════════════════════════");
        System.out.println("Data initialization complete!");
    }

    // ==================== COMMERCIAL BANK MANAGER DATA ====================

    /**
     * Initialize SLR Compliance records.
     */
    public static void initializeSLRData() {
        if (DataManager.fileExists(DataManager.SLR_DATA_FILE)) {
            System.out.println("✓ SLR data already exists");
            return;
        }
        
        List<SLRCompliance> records = new ArrayList<>();
        
        // Historical SLR records for Sonali Bank
        SLRCompliance slr1 = new SLRCompliance();
        slr1.setComplianceId("SLR-2024-001");
        slr1.setBankId("Sonali Bank PLC");
        slr1.setCurrentDeposits(850000000000.0);  // 850 Billion BDT
        slr1.setReserveHoldings(115000000000.0);  // 115 Billion (13.5%)
        slr1.setSlrPercentage(13.0);
        slr1.setComplianceDate(LocalDate.now().minusMonths(3));
        slr1.calculateSLRRequirement();
        slr1.validateCompliance();
        records.add(slr1);

        SLRCompliance slr2 = new SLRCompliance();
        slr2.setComplianceId("SLR-2024-002");
        slr2.setBankId("Sonali Bank PLC");
        slr2.setCurrentDeposits(875000000000.0);  // 875 Billion BDT
        slr2.setReserveHoldings(118000000000.0);  // 118 Billion (13.5%)
        slr2.setSlrPercentage(13.0);
        slr2.setComplianceDate(LocalDate.now().minusMonths(2));
        slr2.calculateSLRRequirement();
        slr2.validateCompliance();
        records.add(slr2);

        SLRCompliance slr3 = new SLRCompliance();
        slr3.setComplianceId("SLR-2024-003");
        slr3.setBankId("Sonali Bank PLC");
        slr3.setCurrentDeposits(890000000000.0);  // 890 Billion BDT
        slr3.setReserveHoldings(120000000000.0);  // 120 Billion (13.5%)
        slr3.setSlrPercentage(13.0);
        slr3.setComplianceDate(LocalDate.now().minusMonths(1));
        slr3.calculateSLRRequirement();
        slr3.validateCompliance();
        records.add(slr3);

        DataManager.saveToFile(DataManager.SLR_DATA_FILE, records);
        System.out.println("✓ Initialized " + records.size() + " SLR compliance records");
    }

    /**
     * Initialize Loan Application records.
     */
    public static void initializeLoanData() {
        if (DataManager.fileExists(DataManager.LOANS_FILE)) {
            System.out.println("✓ Loan data already exists");
            return;
        }
        
        List<LoanApplication> loans = new ArrayList<>();
        
        // Active corporate loan applications
        LoanApplication loan1 = new LoanApplication();
        loan1.setApplicationId("LOAN-2024-001");
        loan1.setBorrowerId("CORP-001");
        loan1.setBorrowerName("Bangladesh Steel Industries Ltd.");
        loan1.setLoanType("Term Loan");
        loan1.setRequestedAmount(500000000.0);  // 500 Million BDT
        loan1.setInterestRate(9.5);
        loan1.setTenureMonths(60);
        loan1.setCollateralValue(750000000.0);
        loan1.setCollateralType("Industrial Property & Machinery");
        loan1.setApplicationDate(LocalDate.now().minusDays(15));
        loan1.setStatus("Under Review");
        loan1.setPurpose("Factory expansion and modernization");
        loans.add(loan1);

        LoanApplication loan2 = new LoanApplication();
        loan2.setApplicationId("LOAN-2024-002");
        loan2.setBorrowerId("CORP-002");
        loan2.setBorrowerName("Dhaka Textiles Corporation");
        loan2.setLoanType("Working Capital");
        loan2.setRequestedAmount(200000000.0);  // 200 Million BDT
        loan2.setInterestRate(10.0);
        loan2.setTenureMonths(12);
        loan2.setCollateralValue(300000000.0);
        loan2.setCollateralType("Inventory & Receivables");
        loan2.setApplicationDate(LocalDate.now().minusDays(10));
        loan2.setStatus("Pending");
        loan2.setPurpose("Raw material procurement");
        loans.add(loan2);

        LoanApplication loan3 = new LoanApplication();
        loan3.setApplicationId("LOAN-2024-003");
        loan3.setBorrowerId("CORP-003");
        loan3.setBorrowerName("Chittagong Shipbuilding Ltd.");
        loan3.setLoanType("Project Finance");
        loan3.setRequestedAmount(1500000000.0);  // 1.5 Billion BDT
        loan3.setInterestRate(8.75);
        loan3.setTenureMonths(120);
        loan3.setCollateralValue(2000000000.0);
        loan3.setCollateralType("Shipyard Assets & Government Guarantee");
        loan3.setApplicationDate(LocalDate.now().minusDays(30));
        loan3.setStatus("Approved");
        loan3.setPurpose("New vessel construction facility");
        loans.add(loan3);

        LoanApplication loan4 = new LoanApplication();
        loan4.setApplicationId("LOAN-2024-004");
        loan4.setBorrowerId("CORP-004");
        loan4.setBorrowerName("Sylhet Tea Estates Ltd.");
        loan4.setLoanType("Agricultural Loan");
        loan4.setRequestedAmount(80000000.0);  // 80 Million BDT
        loan4.setInterestRate(7.0);
        loan4.setTenureMonths(36);
        loan4.setCollateralValue(150000000.0);
        loan4.setCollateralType("Tea Garden Land & Equipment");
        loan4.setApplicationDate(LocalDate.now().minusDays(5));
        loan4.setStatus("Pending");
        loan4.setPurpose("Tea processing equipment upgrade");
        loans.add(loan4);

        DataManager.saveToFile(DataManager.LOANS_FILE, loans);
        System.out.println("✓ Initialized " + loans.size() + " loan applications");
    }

    /**
     * Initialize Interbank Operations records.
     */
    public static void initializeInterbankData() {
        if (DataManager.fileExists(DataManager.INTERBANK_FILE)) {
            System.out.println("✓ Interbank data already exists");
            return;
        }
        
        List<InterbankOperations> operations = new ArrayList<>();
        
        InterbankOperations op1 = new InterbankOperations();
        op1.setOperationId("IB-2024-001");
        op1.setBankId("Sonali Bank PLC");
        op1.setCounterpartyBank("Dutch Bangla Bank Ltd.");
        op1.setBorrowAmount(2000000000.0);  // 2 Billion borrowed
        op1.setLendingAmount(0);
        op1.setInterestRate(6.25);
        op1.setTransactionDate(LocalDate.now().minusDays(5));
        op1.setMaturityDate(LocalDate.now().plusDays(2));
        op1.setStatus("Active");
        operations.add(op1);

        InterbankOperations op2 = new InterbankOperations();
        op2.setOperationId("IB-2024-002");
        op2.setBankId("Sonali Bank PLC");
        op2.setCounterpartyBank("BRAC Bank Ltd.");
        op2.setBorrowAmount(0);
        op2.setLendingAmount(1500000000.0);  // 1.5 Billion lent
        op2.setInterestRate(6.5);
        op2.setTransactionDate(LocalDate.now().minusDays(3));
        op2.setMaturityDate(LocalDate.now().plusDays(4));
        op2.setStatus("Active");
        operations.add(op2);

        InterbankOperations op3 = new InterbankOperations();
        op3.setOperationId("IB-2024-003");
        op3.setBankId("Sonali Bank PLC");
        op3.setCounterpartyBank("Janata Bank Ltd.");
        op3.setBorrowAmount(1000000000.0);  // 1 Billion borrowed
        op3.setLendingAmount(0);
        op3.setInterestRate(6.0);
        op3.setTransactionDate(LocalDate.now().minusDays(10));
        op3.setMaturityDate(LocalDate.now().minusDays(3));
        op3.setStatus("Settled");
        operations.add(op3);

        DataManager.saveToFile(DataManager.INTERBANK_FILE, operations);
        System.out.println("✓ Initialized " + operations.size() + " interbank operations");
    }

    /**
     * Initialize Liquidity Management records.
     */
    public static void initializeLiquidityData() {
        if (DataManager.fileExists(DataManager.LIQUIDITY_FILE)) {
            System.out.println("✓ Liquidity data already exists");
            return;
        }
        
        List<LiquidityManagement> records = new ArrayList<>();
        
        LiquidityManagement liq1 = new LiquidityManagement();
        liq1.setLiquidityId("LIQ-2024-001");
        liq1.setBankId("Sonali Bank PLC");
        liq1.setCashInflows(15000000000.0);   // 15 Billion inflows
        liq1.setCashOutflows(12500000000.0);  // 12.5 Billion outflows
        liq1.setMinThreshold(2000000000.0);   // 2 Billion threshold
        liq1.setReportDate(LocalDate.now().minusDays(1));
        liq1.forecastTransactions();
        records.add(liq1);

        LiquidityManagement liq2 = new LiquidityManagement();
        liq2.setLiquidityId("LIQ-2024-002");
        liq2.setBankId("Sonali Bank PLC");
        liq2.setCashInflows(18000000000.0);   // 18 Billion inflows
        liq2.setCashOutflows(16000000000.0);  // 16 Billion outflows
        liq2.setMinThreshold(2000000000.0);
        liq2.setReportDate(LocalDate.now());
        liq2.forecastTransactions();
        records.add(liq2);

        DataManager.saveToFile(DataManager.LIQUIDITY_FILE, records);
        System.out.println("✓ Initialized " + records.size() + " liquidity records");
    }

    /**
     * Initialize CAR (Capital Adequacy Ratio) records.
     */
    public static void initializeCARData() {
        String filename = "car_data.bin";
        if (DataManager.fileExists(filename)) {
            System.out.println("✓ CAR data already exists");
            return;
        }
        
        List<CapitalAdequacy> records = new ArrayList<>();
        
        CapitalAdequacy car1 = new CapitalAdequacy();
        car1.setCapitalId("CAR-2024-Q3");
        car1.setBankId("Sonali Bank PLC");
        car1.setTier1Capital(95000000000.0);   // 95 Billion
        car1.setTier2Capital(25000000000.0);   // 25 Billion
        car1.setRiskWeightedAssets(850000000000.0);  // 850 Billion
        car1.setReportDate(LocalDate.now().minusMonths(1));
        car1.calculateCAR();
        records.add(car1);

        CapitalAdequacy car2 = new CapitalAdequacy();
        car2.setCapitalId("CAR-2024-Q4");
        car2.setBankId("Sonali Bank PLC");
        car2.setTier1Capital(98000000000.0);   // 98 Billion
        car2.setTier2Capital(26000000000.0);   // 26 Billion
        car2.setRiskWeightedAssets(875000000000.0);  // 875 Billion
        car2.setReportDate(LocalDate.now());
        car2.calculateCAR();
        records.add(car2);

        DataManager.saveToFile(filename, records);
        System.out.println("✓ Initialized " + records.size() + " CAR records");
    }

    /**
     * Initialize AML (Anti-Money Laundering) records.
     */
    public static void initializeAMLData() {
        if (DataManager.fileExists(DataManager.AML_CASES_FILE)) {
            System.out.println("✓ AML data already exists");
            return;
        }
        
        List<AMLMonitoring> cases = new ArrayList<>();
        
        AMLMonitoring aml1 = new AMLMonitoring();
        aml1.setCaseId("AML-2024-001");
        aml1.setTransactionId("TXN-20241115-001");
        aml1.setAccountNumber("0110-12345678");
        aml1.setTransactionAmount(50000000.0);  // 50 Million - suspicious
        aml1.setTransactionType("Wire Transfer");
        aml1.setRiskLevel("High");
        aml1.setFlaggedDate(LocalDate.now().minusDays(7));
        aml1.setStatus("Under Investigation");
        aml1.setDescription("Large international wire transfer to high-risk jurisdiction");
        cases.add(aml1);

        AMLMonitoring aml2 = new AMLMonitoring();
        aml2.setCaseId("AML-2024-002");
        aml2.setTransactionId("TXN-20241120-005");
        aml2.setAccountNumber("0110-87654321");
        aml2.setTransactionAmount(25000000.0);  // 25 Million
        aml2.setTransactionType("Cash Deposit");
        aml2.setRiskLevel("Medium");
        aml2.setFlaggedDate(LocalDate.now().minusDays(3));
        aml2.setStatus("Pending Review");
        aml2.setDescription("Structured cash deposits over multiple days");
        cases.add(aml2);

        AMLMonitoring aml3 = new AMLMonitoring();
        aml3.setCaseId("AML-2024-003");
        aml3.setTransactionId("TXN-20241110-012");
        aml3.setAccountNumber("0110-55556666");
        aml3.setTransactionAmount(75000000.0);  // 75 Million
        aml3.setTransactionType("Letter of Credit");
        aml3.setRiskLevel("High");
        aml3.setFlaggedDate(LocalDate.now().minusDays(14));
        aml3.setStatus("Cleared");
        aml3.setDescription("Trade-based transaction - verified legitimate");
        cases.add(aml3);

        DataManager.saveToFile(DataManager.AML_CASES_FILE, cases);
        System.out.println("✓ Initialized " + cases.size() + " AML cases");
    }

    /**
     * Initialize Branch Performance records.
     */
    public static void initializeBranchData() {
        if (DataManager.fileExists(DataManager.BRANCHES_FILE)) {
            System.out.println("✓ Branch data already exists");
            return;
        }
        
        List<BranchPerformance> branches = new ArrayList<>();
        
        String[] branchNames = {
            "Motijheel Corporate Branch", "Gulshan Branch", "Uttara Branch",
            "Chittagong Main Branch", "Sylhet Branch", "Khulna Branch",
            "Rajshahi Branch", "Dhanmondi Branch", "Mirpur Branch", "Banani Branch"
        };
        
        double[] deposits = {45.5, 38.2, 28.7, 42.1, 18.5, 22.3, 19.8, 31.4, 25.6, 35.9};  // Billions
        double[] loans = {32.1, 28.5, 18.2, 35.8, 12.4, 15.7, 14.2, 22.8, 17.3, 26.4};     // Billions
        double[] kpis = {92.5, 88.3, 85.7, 91.2, 78.4, 82.1, 79.8, 86.5, 81.2, 89.7};      // Scores
        
        for (int i = 0; i < branchNames.length; i++) {
            BranchPerformance branch = new BranchPerformance();
            branch.setBranchId("BR-" + String.format("%03d", i + 1));
            branch.setBranchName(branchNames[i]);
            branch.setTotalDeposits(deposits[i] * 1000000000);
            branch.setTotalLoans(loans[i] * 1000000000);
            branch.setKpiScore(kpis[i]);
            branch.setReportDate(LocalDate.now());
            branch.setEmployeeCount(45 + (i * 5));
            branch.setCustomerCount(12000 + (i * 1500));
            branches.add(branch);
        }

        DataManager.saveToFile(DataManager.BRANCHES_FILE, branches);
        System.out.println("✓ Initialized " + branches.size() + " branch records");
    }

    /**
     * Initialize Monetary Strategy records.
     */
    public static void initializeMonetaryStrategyData() {
        String filename = "monetary_strategy.bin";
        if (DataManager.fileExists(filename)) {
            System.out.println("✓ Monetary Strategy data already exists");
            return;
        }
        
        List<MonetaryStrategy> strategies = new ArrayList<>();
        
        MonetaryStrategy strategy = new MonetaryStrategy();
        strategy.setStrategyId("MS-2024-FY");
        strategy.setBankId("Sonali Bank PLC");
        strategy.setFiscalYear("2024-2025");
        strategy.setCreditTarget(150000000000.0);  // 150 Billion credit target
        strategy.setDepositTarget(200000000000.0); // 200 Billion deposit target
        strategy.setPriorityLending(45000000000.0); // 45 Billion priority sector
        strategy.setSubmissionDate(LocalDate.now().minusMonths(6));
        strategy.setStatus("Active");
        strategies.add(strategy);

        DataManager.saveToFile(filename, strategies);
        System.out.println("✓ Initialized " + strategies.size() + " monetary strategy records");
    }

    // ==================== MINISTRY OF FINANCE DATA ====================

    /**
     * Initialize Revenue Monitoring records.
     */
    public static void initializeRevenueData() {
        if (DataManager.fileExists(DataManager.REVENUE_FILE)) {
            System.out.println("✓ Revenue data already exists");
            return;
        }
        
        List<RevenueMonitoring> records = new ArrayList<>();
        
        RevenueMonitoring rev1 = new RevenueMonitoring();
        rev1.setRevenueId("REV-2024-11");
        rev1.setRevenueType("Income Tax");
        rev1.setCollectedAmount(85000000000.0);   // 85 Billion
        rev1.setTargetAmount(100000000000.0);     // 100 Billion target
        rev1.setReportingPeriod(LocalDate.now().minusMonths(1));
        rev1.setRegion("National");
        records.add(rev1);

        RevenueMonitoring rev2 = new RevenueMonitoring();
        rev2.setRevenueId("REV-2024-VAT");
        rev2.setRevenueType("Value Added Tax");
        rev2.setCollectedAmount(120000000000.0);  // 120 Billion
        rev2.setTargetAmount(130000000000.0);     // 130 Billion target
        rev2.setReportingPeriod(LocalDate.now().minusMonths(1));
        rev2.setRegion("National");
        records.add(rev2);

        RevenueMonitoring rev3 = new RevenueMonitoring();
        rev3.setRevenueId("REV-2024-CUSTOMS");
        rev3.setRevenueType("Customs Duty");
        rev3.setCollectedAmount(45000000000.0);   // 45 Billion
        rev3.setTargetAmount(50000000000.0);      // 50 Billion target
        rev3.setReportingPeriod(LocalDate.now().minusMonths(1));
        rev3.setRegion("National");
        records.add(rev3);

        DataManager.saveToFile(DataManager.REVENUE_FILE, records);
        System.out.println("✓ Initialized " + records.size() + " revenue records");
    }

    /**
     * Initialize Expenditure Control records.
     */
    public static void initializeExpenditureData() {
        if (DataManager.fileExists(DataManager.EXPENDITURE_FILE)) {
            System.out.println("✓ Expenditure data already exists");
            return;
        }
        
        List<ExpenditureControl> records = new ArrayList<>();
        
        ExpenditureControl exp1 = new ExpenditureControl();
        exp1.setExpenditureId("EXP-2024-001");
        exp1.setMinistry("Ministry of Education");
        exp1.setBudgetHead("Primary Education Development");
        exp1.setRequestedAmount(25000000000.0);   // 25 Billion
        exp1.setApprovedAmount(22000000000.0);    // 22 Billion approved
        exp1.setDisbursedAmount(18000000000.0);   // 18 Billion disbursed
        exp1.setRequestDate(LocalDate.now().minusMonths(2));
        exp1.setStatus("Partially Disbursed");
        records.add(exp1);

        ExpenditureControl exp2 = new ExpenditureControl();
        exp2.setExpenditureId("EXP-2024-002");
        exp2.setMinistry("Ministry of Health");
        exp2.setBudgetHead("Hospital Equipment Procurement");
        exp2.setRequestedAmount(15000000000.0);   // 15 Billion
        exp2.setApprovedAmount(15000000000.0);    // 15 Billion approved
        exp2.setDisbursedAmount(8000000000.0);    // 8 Billion disbursed
        exp2.setRequestDate(LocalDate.now().minusMonths(1));
        exp2.setStatus("In Progress");
        records.add(exp2);

        ExpenditureControl exp3 = new ExpenditureControl();
        exp3.setExpenditureId("EXP-2024-003");
        exp3.setMinistry("Ministry of Roads");
        exp3.setBudgetHead("Highway Construction");
        exp3.setRequestedAmount(50000000000.0);   // 50 Billion
        exp3.setApprovedAmount(0);
        exp3.setDisbursedAmount(0);
        exp3.setRequestDate(LocalDate.now().minusDays(7));
        exp3.setStatus("Pending Approval");
        records.add(exp3);

        DataManager.saveToFile(DataManager.EXPENDITURE_FILE, records);
        System.out.println("✓ Initialized " + records.size() + " expenditure records");
    }

    /**
     * Initialize Public Debt records.
     */
    public static void initializePublicDebtData() {
        if (DataManager.fileExists(DataManager.DEBT_FILE)) {
            System.out.println("✓ Public Debt data already exists");
            return;
        }
        
        List<PublicDebt> records = new ArrayList<>();
        
        PublicDebt debt1 = new PublicDebt();
        debt1.setDebtId("DEBT-WB-2024");
        debt1.setDebtType("External - Multilateral");
        debt1.setCreditor("World Bank");
        debt1.setPrincipalAmount(5000000000.0);     // 5 Billion USD equivalent
        debt1.setOutstandingDebt(4200000000.0);     // 4.2 Billion outstanding
        debt1.setInterestRate(2.5);
        debt1.setMaturityDate(LocalDate.now().plusYears(15));
        debt1.setDebtStatus("Active");
        records.add(debt1);

        PublicDebt debt2 = new PublicDebt();
        debt2.setDebtId("DEBT-ADB-2024");
        debt2.setDebtType("External - Multilateral");
        debt2.setCreditor("Asian Development Bank");
        debt2.setPrincipalAmount(3000000000.0);     // 3 Billion
        debt2.setOutstandingDebt(2800000000.0);     // 2.8 Billion outstanding
        debt2.setInterestRate(3.0);
        debt2.setMaturityDate(LocalDate.now().plusYears(12));
        debt2.setDebtStatus("Active");
        records.add(debt2);

        PublicDebt debt3 = new PublicDebt();
        debt3.setDebtId("DEBT-TBILL-2024");
        debt3.setDebtType("Domestic - Treasury Bill");
        debt3.setCreditor("Bangladesh Bank");
        debt3.setPrincipalAmount(200000000000.0);   // 200 Billion BDT
        debt3.setOutstandingDebt(200000000000.0);
        debt3.setInterestRate(7.5);
        debt3.setMaturityDate(LocalDate.now().plusMonths(6));
        debt3.setDebtStatus("Active");
        records.add(debt3);

        DataManager.saveToFile(DataManager.DEBT_FILE, records);
        System.out.println("✓ Initialized " + records.size() + " public debt records");
    }

    /**
     * Initialize Budget Management records.
     */
    public static void initializeBudgetData() {
        if (DataManager.fileExists(DataManager.BUDGET_FILE)) {
            System.out.println("✓ Budget data already exists");
            return;
        }
        
        List<BudgetManagement> records = new ArrayList<>();
        
        String[] departments = {"Education", "Health", "Defense", "Infrastructure", "Social Welfare"};
        double[] allocations = {450, 280, 350, 520, 180};  // Billions
        double[] spent = {320, 195, 280, 380, 125};        // Billions
        
        for (int i = 0; i < departments.length; i++) {
            BudgetManagement budget = new BudgetManagement();
            budget.setBudgetId("BUD-2024-" + String.format("%02d", i + 1));
            budget.setDepartment(departments[i]);
            budget.setAllocatedAmount(allocations[i] * 1000000000);
            budget.setSpentAmount(spent[i] * 1000000000);
            budget.setFiscalYear("2024-2025");
            budget.setQuarter("Q2");
            records.add(budget);
        }

        DataManager.saveToFile(DataManager.BUDGET_FILE, records);
        System.out.println("✓ Initialized " + records.size() + " budget records");
    }

    /**
     * Initialize Foreign Aid records.
     */
    public static void initializeForeignAidData() {
        String filename = "foreign_aid.bin";
        if (DataManager.fileExists(filename)) {
            System.out.println("✓ Foreign Aid data already exists");
            return;
        }
        
        List<ForeignAidMonitoring> records = new ArrayList<>();
        
        ForeignAidMonitoring aid1 = new ForeignAidMonitoring();
        aid1.setAidId("AID-JICA-2024");
        aid1.setDonorAgency("Japan International Cooperation Agency");
        aid1.setProjectName("Dhaka Mass Rapid Transit Development");
        aid1.setCommittedAmount(2500000000.0);    // 2.5 Billion USD
        aid1.setDisbursedAmount(1200000000.0);   // 1.2 Billion disbursed
        aid1.setUtilizationStatus("On Track");
        aid1.setAgreementDate(LocalDate.now().minusYears(3));
        aid1.setCompletionDate(LocalDate.now().plusYears(2));
        records.add(aid1);

        ForeignAidMonitoring aid2 = new ForeignAidMonitoring();
        aid2.setAidId("AID-DFID-2024");
        aid2.setDonorAgency("UK Foreign Office");
        aid2.setProjectName("Climate Resilience Program");
        aid2.setCommittedAmount(500000000.0);     // 500 Million
        aid2.setDisbursedAmount(350000000.0);    // 350 Million disbursed
        aid2.setUtilizationStatus("On Track");
        aid2.setAgreementDate(LocalDate.now().minusYears(2));
        aid2.setCompletionDate(LocalDate.now().plusYears(1));
        records.add(aid2);

        DataManager.saveToFile(filename, records);
        System.out.println("✓ Initialized " + records.size() + " foreign aid records");
    }

    /**
     * Initialize Economic Reform records.
     */
    public static void initializeReformData() {
        if (DataManager.fileExists(DataManager.REFORM_FILE)) {
            System.out.println("✓ Reform data already exists");
            return;
        }
        
        List<ReformMonitoring> records = new ArrayList<>();
        
        ReformMonitoring reform1 = new ReformMonitoring();
        reform1.setReformId("REFORM-2024-001");
        reform1.setReformName("Digital Banking Transformation");
        reform1.setReformCategory("Financial Sector");
        reform1.setTargetDate(LocalDate.now().plusYears(1));
        reform1.setResponsibleAgency("Bangladesh Bank");
        reform1.setProgressPercentage(45);
        reform1.setMilestoneStatus("In Progress");
        records.add(reform1);

        ReformMonitoring reform2 = new ReformMonitoring();
        reform2.setReformId("REFORM-2024-002");
        reform2.setReformName("Tax Administration Modernization");
        reform2.setReformCategory("Revenue");
        reform2.setTargetDate(LocalDate.now().plusMonths(8));
        reform2.setResponsibleAgency("National Board of Revenue");
        reform2.setProgressPercentage(65);
        reform2.setMilestoneStatus("On Track");
        records.add(reform2);

        DataManager.saveToFile(DataManager.REFORM_FILE, records);
        System.out.println("✓ Initialized " + records.size() + " reform records");
    }

    /**
     * Initialize Government Cash Balance records.
     */
    public static void initializeCashBalanceData() {
        String filename = "cash_balance.bin";
        if (DataManager.fileExists(filename)) {
            System.out.println("✓ Cash Balance data already exists");
            return;
        }
        
        List<GovCashBalance> records = new ArrayList<>();
        
        GovCashBalance cash1 = new GovCashBalance();
        cash1.setBalanceId("CASH-2024-11");
        cash1.setAccountType("Consolidated Fund");
        cash1.setOpeningBalance(150000000000.0);   // 150 Billion
        cash1.setTotalReceipts(85000000000.0);     // 85 Billion receipts
        cash1.setTotalPayments(72000000000.0);     // 72 Billion payments
        cash1.setClosingBalance(163000000000.0);   // 163 Billion closing
        cash1.setReportDate(LocalDate.now().minusDays(1));
        records.add(cash1);

        GovCashBalance cash2 = new GovCashBalance();
        cash2.setBalanceId("CASH-2024-12");
        cash2.setAccountType("Consolidated Fund");
        cash2.setOpeningBalance(163000000000.0);   // Yesterday's closing
        cash2.setTotalReceipts(45000000000.0);     // Today's receipts
        cash2.setTotalPayments(38000000000.0);     // Today's payments
        cash2.setClosingBalance(170000000000.0);   // Today's closing
        cash2.setReportDate(LocalDate.now());
        records.add(cash2);

        DataManager.saveToFile(filename, records);
        System.out.println("✓ Initialized " + records.size() + " cash balance records");
    }

    /**
     * Resets all data files and reinitializes with fresh data.
     */
    public static void resetAllData() {
        System.out.println("⚠️ Resetting all data files...");
        
        // Delete all data files
        String[] files = {
            DataManager.SLR_DATA_FILE, DataManager.LOANS_FILE, DataManager.INTERBANK_FILE,
            DataManager.LIQUIDITY_FILE, "car_data.bin", DataManager.AML_CASES_FILE,
            DataManager.BRANCHES_FILE, "monetary_strategy.bin", DataManager.REVENUE_FILE,
            DataManager.EXPENDITURE_FILE, DataManager.DEBT_FILE, DataManager.BUDGET_FILE,
            "foreign_aid.bin", DataManager.REFORM_FILE, "cash_balance.bin"
        };
        
        for (String file : files) {
            DataManager.deleteFile(file);
        }
        
        // Reinitialize all data
        initializeAllData();
    }
}

