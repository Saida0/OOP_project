package com.example.simulation_of_bangladesh_bank.saida.model;

import java.time.LocalDate;

/**
 * FiscalCoordination - Coordinates fiscal policy with Bangladesh Bank monetary policy
 * Used by Ministry of Finance Representative for policy coordination
 */
public class FiscalCoordination {
    private String coordinationId;
    private String fiscalPolicyStance; // Expansionary/Contractionary/Neutral
    private String monetaryPolicyStatus; // Accommodative/Restrictive/Neutral
    private LocalDate coordinationDate;
    private String fiscalYear;
    private double targetInflation;
    private double actualInflation;
    private double fiscalDeficit;
    private double gdpGrowthTarget;
    private String policyAlignment; // Aligned/Misaligned/PartiallyAligned
    private String coordinationStatus; // Scheduled/Completed/Pending
    private String meetingAgenda;
    private String decisions;

    // Constructors
    public FiscalCoordination() {
        this.coordinationDate = LocalDate.now();
        this.coordinationStatus = "Pending";
    }

    public FiscalCoordination(String coordinationId, String fiscalYear, 
                             String fiscalPolicyStance) {
        this.coordinationId = coordinationId;
        this.fiscalYear = fiscalYear;
        this.fiscalPolicyStance = fiscalPolicyStance;
        this.coordinationDate = LocalDate.now();
        this.coordinationStatus = "Pending";
    }

    // Business Methods
    // No-arg overload for controller compatibility
    public void loadMonetaryPolicy() {
        assessPolicyAlignment();
    }

    public void loadMonetaryPolicy(String monetaryStatus, double targetInflation, 
                                   double gdpGrowthTarget) {
        this.monetaryPolicyStatus = monetaryStatus;
        this.targetInflation = targetInflation;
        this.gdpGrowthTarget = gdpGrowthTarget;
        assessPolicyAlignment();
    }

    public void setFiscalParameters(double fiscalDeficit, double actualInflation) {
        this.fiscalDeficit = fiscalDeficit;
        this.actualInflation = actualInflation;
        assessPolicyAlignment();
    }

    private void assessPolicyAlignment() {
        if (monetaryPolicyStatus == null || fiscalPolicyStance == null) {
            this.policyAlignment = "Pending Assessment";
            return;
        }
        
        // Check if fiscal and monetary policies are complementary
        boolean aligned = false;
        
        if (fiscalPolicyStance.equals("Expansionary") && 
            monetaryPolicyStatus.equals("Accommodative")) {
            aligned = true;
        } else if (fiscalPolicyStance.equals("Contractionary") && 
                   monetaryPolicyStatus.equals("Restrictive")) {
            aligned = true;
        } else if (fiscalPolicyStance.equals("Neutral") && 
                   monetaryPolicyStatus.equals("Neutral")) {
            aligned = true;
        }
        
        // Check inflation alignment
        boolean inflationAligned = Math.abs(actualInflation - targetInflation) <= 2;
        
        if (aligned && inflationAligned) {
            this.policyAlignment = "Aligned";
        } else if (aligned || inflationAligned) {
            this.policyAlignment = "PartiallyAligned";
        } else {
            this.policyAlignment = "Misaligned";
        }
    }

    public boolean matchFiscalPolicy() {
        return policyAlignment != null && policyAlignment.equals("Aligned");
    }

    public String generateCoordinationAgenda() {
        StringBuilder agenda = new StringBuilder();
        agenda.append("╔══════════════════════════════════════════════════════════════╗\n");
        agenda.append("║     FISCAL-MONETARY POLICY COORDINATION MEETING AGENDA       ║\n");
        agenda.append("╠══════════════════════════════════════════════════════════════╣\n");
        agenda.append("║ Coordination ID: ").append(String.format("%-43s", coordinationId)).append("║\n");
        agenda.append("║ Fiscal Year: ").append(String.format("%-47s", fiscalYear)).append("║\n");
        agenda.append("║ Date: ").append(String.format("%-53s", coordinationDate)).append("║\n");
        agenda.append("╠══════════════════════════════════════════════════════════════╣\n");
        agenda.append("║                       POLICY STATUS                          ║\n");
        agenda.append("╠══════════════════════════════════════════════════════════════╣\n");
        agenda.append("║ Fiscal Policy Stance: ").append(String.format("%-38s", fiscalPolicyStance)).append("║\n");
        agenda.append("║ Monetary Policy Status: ").append(String.format("%-36s", monetaryPolicyStatus)).append("║\n");
        agenda.append("║ Policy Alignment: ").append(String.format("%-42s", policyAlignment)).append("║\n");
        agenda.append("╠══════════════════════════════════════════════════════════════╣\n");
        agenda.append("║                    ECONOMIC INDICATORS                       ║\n");
        agenda.append("╠══════════════════════════════════════════════════════════════╣\n");
        agenda.append("║ Target Inflation: ").append(String.format("%-41.2f%%", targetInflation)).append("║\n");
        agenda.append("║ Actual Inflation: ").append(String.format("%-41.2f%%", actualInflation)).append("║\n");
        agenda.append("║ GDP Growth Target: ").append(String.format("%-40.2f%%", gdpGrowthTarget)).append("║\n");
        agenda.append("║ Fiscal Deficit (% of GDP): ").append(String.format("%-31.2f%%", fiscalDeficit)).append("║\n");
        agenda.append("╠══════════════════════════════════════════════════════════════╣\n");
        agenda.append("║                     AGENDA ITEMS                             ║\n");
        agenda.append("╠══════════════════════════════════════════════════════════════╣\n");
        agenda.append("║ 1. Review current economic conditions                        ║\n");
        agenda.append("║ 2. Assess fiscal-monetary policy coordination                ║\n");
        agenda.append("║ 3. Discuss inflation management strategies                   ║\n");
        agenda.append("║ 4. Government borrowing requirements                         ║\n");
        agenda.append("║ 5. Interest rate implications                                ║\n");
        agenda.append("║ 6. Action items and next steps                               ║\n");
        agenda.append("╚══════════════════════════════════════════════════════════════╝\n");
        
        this.meetingAgenda = agenda.toString();
        return agenda.toString();
    }

    public void documentDecisions(String decisions) {
        this.decisions = decisions;
        this.coordinationStatus = "Completed";
    }

    public String updatePolicyTracking() {
        StringBuilder tracking = new StringBuilder();
        tracking.append("===== POLICY COORDINATION TRACKING =====\n");
        tracking.append("Coordination ID: ").append(coordinationId).append("\n");
        tracking.append("Status: ").append(coordinationStatus).append("\n");
        tracking.append("Policy Alignment: ").append(policyAlignment).append("\n\n");
        
        if (decisions != null && !decisions.isEmpty()) {
            tracking.append("Decisions Made:\n");
            tracking.append(decisions).append("\n\n");
        }
        
        tracking.append("Recommendations:\n");
        if (policyAlignment.equals("Misaligned")) {
            tracking.append("- Urgent policy recalibration needed\n");
            tracking.append("- Schedule emergency coordination meeting\n");
        } else if (policyAlignment.equals("PartiallyAligned")) {
            tracking.append("- Fine-tune policy parameters\n");
            tracking.append("- Monitor inflation closely\n");
        } else {
            tracking.append("- Continue current policy trajectory\n");
            tracking.append("- Regular monitoring recommended\n");
        }
        
        tracking.append("========================================\n");
        return tracking.toString();
    }

    // Getters and Setters
    public String getCoordinationId() {
        return coordinationId;
    }

    public void setCoordinationId(String coordinationId) {
        this.coordinationId = coordinationId;
    }

    public String getFiscalPolicyStance() {
        return fiscalPolicyStance;
    }

    public void setFiscalPolicyStance(String fiscalPolicyStance) {
        this.fiscalPolicyStance = fiscalPolicyStance;
        assessPolicyAlignment();
    }

    public String getMonetaryPolicyStatus() {
        return monetaryPolicyStatus;
    }

    public void setMonetaryPolicyStatus(String monetaryPolicyStatus) {
        this.monetaryPolicyStatus = monetaryPolicyStatus;
        assessPolicyAlignment();
    }

    public LocalDate getCoordinationDate() {
        return coordinationDate;
    }

    public void setCoordinationDate(LocalDate coordinationDate) {
        this.coordinationDate = coordinationDate;
    }

    public String getFiscalYear() {
        return fiscalYear;
    }

    public void setFiscalYear(String fiscalYear) {
        this.fiscalYear = fiscalYear;
    }

    public double getTargetInflation() {
        return targetInflation;
    }

    public void setTargetInflation(double targetInflation) {
        this.targetInflation = targetInflation;
    }

    public double getActualInflation() {
        return actualInflation;
    }

    public void setActualInflation(double actualInflation) {
        this.actualInflation = actualInflation;
    }

    public double getFiscalDeficit() {
        return fiscalDeficit;
    }

    public void setFiscalDeficit(double fiscalDeficit) {
        this.fiscalDeficit = fiscalDeficit;
    }

    public double getGdpGrowthTarget() {
        return gdpGrowthTarget;
    }

    public void setGdpGrowthTarget(double gdpGrowthTarget) {
        this.gdpGrowthTarget = gdpGrowthTarget;
    }

    public String getPolicyAlignment() {
        return policyAlignment;
    }

    public String getCoordinationStatus() {
        return coordinationStatus;
    }

    public void setCoordinationStatus(String coordinationStatus) {
        this.coordinationStatus = coordinationStatus;
    }

    public String getDecisions() {
        return decisions;
    }

    @Override
    public String toString() {
        return "FiscalCoordination{" +
                "coordinationId='" + coordinationId + '\'' +
                ", fiscalPolicyStance='" + fiscalPolicyStance + '\'' +
                ", monetaryPolicyStatus='" + monetaryPolicyStatus + '\'' +
                ", policyAlignment='" + policyAlignment + '\'' +
                '}';
    }
}

