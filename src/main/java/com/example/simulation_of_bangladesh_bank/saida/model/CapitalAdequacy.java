package com.example.simulation_of_bangladesh_bank.saida.model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * CapitalAdequacy - Monitors Capital Adequacy Ratio (CAR) based on Basel III
 * Used by Commercial Bank Manager to ensure regulatory capital compliance
 */
public class CapitalAdequacy implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Additional field
    private String capitalId;
    private String carId;
    private String bankId;
    private double riskWeightedAssets;
    private double tier1Capital;
    private double tier2Capital;
    private double capitalBase;
    private double carValue;
    private double minimumCAR; // Basel III minimum is 10.5% in Bangladesh
    private LocalDate reportDate;
    private String status; // Compliant/Non-Compliant/Warning

    // Constants for Basel III requirements
    public static final double BASEL_III_MIN_CAR = 10.0;
    public static final double CONSERVATION_BUFFER = 2.5;
    public static final double MIN_TIER1_RATIO = 6.0;

    // Constructors
    public CapitalAdequacy() {
        this.reportDate = LocalDate.now();
        this.minimumCAR = BASEL_III_MIN_CAR + CONSERVATION_BUFFER;
        this.status = "Pending";
    }

    public CapitalAdequacy(String carId, String bankId, double riskWeightedAssets,
                          double tier1Capital, double tier2Capital) {
        this.carId = carId;
        this.bankId = bankId;
        this.riskWeightedAssets = riskWeightedAssets;
        this.tier1Capital = tier1Capital;
        this.tier2Capital = tier2Capital;
        this.minimumCAR = BASEL_III_MIN_CAR + CONSERVATION_BUFFER;
        this.reportDate = LocalDate.now();
        calculateCAR();
    }

    // Business Methods
    public double calculateCAR() {
        this.capitalBase = tier1Capital + tier2Capital;
        if (riskWeightedAssets > 0) {
            this.carValue = (capitalBase / riskWeightedAssets) * 100;
        } else {
            this.carValue = 0;
        }
        validateBaselIII();
        return this.carValue;
    }

    public double calculateTier1Ratio() {
        if (riskWeightedAssets > 0) {
            return (tier1Capital / riskWeightedAssets) * 100;
        }
        return 0;
    }

    public boolean validateBaselIII() {
        double tier1Ratio = calculateTier1Ratio();
        
        if (carValue >= minimumCAR && tier1Ratio >= MIN_TIER1_RATIO) {
            this.status = "Compliant";
            return true;
        } else if (carValue >= BASEL_III_MIN_CAR && tier1Ratio >= MIN_TIER1_RATIO) {
            this.status = "Warning"; // Meeting minimum but below conservation buffer
            return true;
        } else {
            this.status = "Non-Compliant";
            return false;
        }
    }

    public String generateCARReport() {
        StringBuilder report = new StringBuilder();
        report.append("===== CAPITAL ADEQUACY REPORT =====\n");
        report.append("Report ID: ").append(carId).append("\n");
        report.append("Bank ID: ").append(bankId).append("\n");
        report.append("Report Date: ").append(reportDate).append("\n\n");
        
        report.append("Capital Components:\n");
        report.append("  Tier 1 Capital: BDT ").append(String.format("%.2f", tier1Capital)).append("\n");
        report.append("  Tier 2 Capital: BDT ").append(String.format("%.2f", tier2Capital)).append("\n");
        report.append("  Total Capital Base: BDT ").append(String.format("%.2f", capitalBase)).append("\n\n");
        
        report.append("Risk-Weighted Assets: BDT ").append(String.format("%.2f", riskWeightedAssets)).append("\n\n");
        
        report.append("Ratios:\n");
        report.append("  Tier 1 Ratio: ").append(String.format("%.2f", calculateTier1Ratio())).append("%\n");
        report.append("  Capital Adequacy Ratio (CAR): ").append(String.format("%.2f", carValue)).append("%\n\n");
        
        report.append("Basel III Requirements:\n");
        report.append("  Minimum CAR: ").append(BASEL_III_MIN_CAR).append("%\n");
        report.append("  Conservation Buffer: ").append(CONSERVATION_BUFFER).append("%\n");
        report.append("  Total Requirement: ").append(minimumCAR).append("%\n");
        report.append("  Minimum Tier 1 Ratio: ").append(MIN_TIER1_RATIO).append("%\n\n");
        
        report.append("Compliance Status: ").append(status).append("\n");
        
        if (!status.equals("Compliant")) {
            report.append("\nCapital Enhancement Required:\n");
            double requiredCapital = (minimumCAR * riskWeightedAssets / 100) - capitalBase;
            if (requiredCapital > 0) {
                report.append("  Additional Capital Needed: BDT ").append(String.format("%.2f", requiredCapital)).append("\n");
            }
        }
        
        report.append("===================================\n");
        return report.toString();
    }

    public String submitCARReport() {
        return "CAR Report " + carId + " submitted to Bangladesh Bank successfully on " + LocalDate.now();
    }

    public double getCapitalShortfall() {
        double requiredCapital = (minimumCAR * riskWeightedAssets) / 100;
        if (capitalBase < requiredCapital) {
            return requiredCapital - capitalBase;
        }
        return 0;
    }

    // Getters and Setters
    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public double getRiskWeightedAssets() {
        return riskWeightedAssets;
    }

    public void setRiskWeightedAssets(double riskWeightedAssets) {
        this.riskWeightedAssets = riskWeightedAssets;
        calculateCAR();
    }

    public double getTier1Capital() {
        return tier1Capital;
    }

    public void setTier1Capital(double tier1Capital) {
        this.tier1Capital = tier1Capital;
        calculateCAR();
    }

    public double getTier2Capital() {
        return tier2Capital;
    }

    public void setTier2Capital(double tier2Capital) {
        this.tier2Capital = tier2Capital;
        calculateCAR();
    }

    public double getCapitalBase() {
        return capitalBase;
    }

    public void setCapitalBase(double capitalBase) {
        this.capitalBase = capitalBase;
        this.tier1Capital = capitalBase * 0.7;
        this.tier2Capital = capitalBase * 0.3;
        calculateCAR();
    }

    public double getCarValue() {
        return carValue;
    }

    public double getMinimumCAR() {
        return minimumCAR;
    }

    public LocalDate getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }

    public String getStatus() {
        return status;
    }

    // Additional getters/setters
    public String getCapitalId() { return capitalId; }
    public void setCapitalId(String capitalId) { this.capitalId = capitalId; }

    @Override
    public String toString() {
        return "CapitalAdequacy{" +
                "capitalId='" + capitalId + '\'' +
                ", bankId='" + bankId + '\'' +
                ", carValue=" + carValue +
                ", status='" + status + '\'' +
                '}';
    }
}

