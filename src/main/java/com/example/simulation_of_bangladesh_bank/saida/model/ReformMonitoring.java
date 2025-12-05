package com.example.simulation_of_bangladesh_bank.saida.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;

/**
 * ReformMonitoring - Monitors economic reform program implementation
 * Used by Ministry of Finance Representative for reform tracking
 */
public class ReformMonitoring implements Serializable {
    private static final long serialVersionUID = 1L;
    private String reformId;
    private String reformName;
    private String reformCategory; // Fiscal/Monetary/Structural/Governance
    private String milestoneStatus; // Pending/InProgress/Achieved/Delayed
    private LocalDate targetDate;
    private LocalDate actualCompletionDate;
    private String issues;
    private String correctiveActions;
    private double progressPercentage;
    private List<String> milestones;
    private List<String> completedMilestones;
    private String responsibleAgency;
    private String donorConditionality;

    // Constructors
    public ReformMonitoring() {
        this.milestones = new ArrayList<>();
        this.completedMilestones = new ArrayList<>();
        this.milestoneStatus = "Pending";
        this.progressPercentage = 0;
    }

    public ReformMonitoring(String reformId, String reformName, String reformCategory,
                           LocalDate targetDate, String responsibleAgency) {
        this.reformId = reformId;
        this.reformName = reformName;
        this.reformCategory = reformCategory;
        this.targetDate = targetDate;
        this.responsibleAgency = responsibleAgency;
        this.milestones = new ArrayList<>();
        this.completedMilestones = new ArrayList<>();
        this.milestoneStatus = "Pending";
        this.progressPercentage = 0;
    }

    // Business Methods
    public void addMilestone(String milestone) {
        this.milestones.add(milestone);
        calculateProgress();
    }

    public void completeMilestone(String milestone) {
        if (milestones.contains(milestone) && !completedMilestones.contains(milestone)) {
            completedMilestones.add(milestone);
            calculateProgress();
        }
    }

    private void calculateProgress() {
        if (!milestones.isEmpty()) {
            this.progressPercentage = ((double) completedMilestones.size() / milestones.size()) * 100;
        }
        updateStatus();
    }

    private void updateStatus() {
        if (progressPercentage == 100) {
            this.milestoneStatus = "Achieved";
            this.actualCompletionDate = LocalDate.now();
        } else if (progressPercentage > 0) {
            LocalDate now = LocalDate.now();
            if (targetDate != null && now.isAfter(targetDate)) {
                this.milestoneStatus = "Delayed";
            } else {
                this.milestoneStatus = "InProgress";
            }
        }
    }

    public boolean validateMilestones() {
        if (milestones.isEmpty()) {
            return false;
        }
        
        LocalDate now = LocalDate.now();
        if (targetDate != null && now.isAfter(targetDate) && !milestoneStatus.equals("Achieved")) {
            return false;
        }
        
        return true;
    }

    public String generateReformReport() {
        StringBuilder report = new StringBuilder();
        report.append("╔══════════════════════════════════════════════════════════════╗\n");
        report.append("║           ECONOMIC REFORM PROGRESS REPORT                    ║\n");
        report.append("╠══════════════════════════════════════════════════════════════╣\n");
        report.append("║ Reform ID: ").append(String.format("%-49s", reformId != null ? reformId : "N/A")).append("║\n");
        report.append("║ Reform Name: ").append(String.format("%-47s", reformName != null ? reformName : "N/A")).append("║\n");
        report.append("║ Category: ").append(String.format("%-50s", reformCategory != null ? reformCategory : "N/A")).append("║\n");
        report.append("║ Responsible Agency: ").append(String.format("%-40s", responsibleAgency != null ? responsibleAgency : "N/A")).append("║\n");
        report.append("╠══════════════════════════════════════════════════════════════╣\n");
        report.append("║                     PROGRESS STATUS                          ║\n");
        report.append("╠══════════════════════════════════════════════════════════════╣\n");
        report.append("║ Status: ").append(String.format("%-52s", milestoneStatus)).append("║\n");
        report.append("║ Progress: ").append(String.format("%-49.1f%%", progressPercentage)).append("║\n");
        report.append("║ Target Date: ").append(String.format("%-47s", targetDate != null ? targetDate.toString() : "N/A")).append("║\n");
        if (actualCompletionDate != null) {
            report.append("║ Completion Date: ").append(String.format("%-43s", actualCompletionDate.toString())).append("║\n");
        }
        report.append("╠══════════════════════════════════════════════════════════════╣\n");
        report.append("║                      MILESTONES                              ║\n");
        report.append("╠══════════════════════════════════════════════════════════════╣\n");
        
        for (String milestone : milestones) {
            String status = completedMilestones.contains(milestone) ? "[✓]" : "[ ]";
            String displayMilestone = milestone.length() > 50 ? 
                                      milestone.substring(0, 47) + "..." : milestone;
            report.append("║ ").append(status).append(" ").append(String.format("%-56s", displayMilestone)).append("║\n");
        }
        
        report.append("╠══════════════════════════════════════════════════════════════╣\n");
        report.append("║ Completed: ").append(completedMilestones.size()).append("/").append(milestones.size());
        report.append(String.format("%49s", " ")).append("║\n");
        
        if (issues != null && !issues.isEmpty()) {
            report.append("╠══════════════════════════════════════════════════════════════╣\n");
            report.append("║ Issues: ").append(String.format("%-52s", issues.length() > 52 ? 
                         issues.substring(0, 49) + "..." : issues)).append("║\n");
        }
        
        if (donorConditionality != null && !donorConditionality.isEmpty()) {
            report.append("╠══════════════════════════════════════════════════════════════╣\n");
            report.append("║ Donor Conditionality: ").append(String.format("%-38s", 
                         donorConditionality.length() > 38 ? 
                         donorConditionality.substring(0, 35) + "..." : donorConditionality)).append("║\n");
        }
        
        report.append("╚══════════════════════════════════════════════════════════════╝\n");
        
        return report.toString();
    }

    public String identifyDelays() {
        StringBuilder analysis = new StringBuilder();
        analysis.append("===== DELAY ANALYSIS =====\n");
        analysis.append("Reform: ").append(reformName).append("\n\n");
        
        if (milestoneStatus.equals("Delayed")) {
            analysis.append("STATUS: DELAYED\n\n");
            
            int pendingMilestones = milestones.size() - completedMilestones.size();
            analysis.append("Pending Milestones: ").append(pendingMilestones).append("\n\n");
            
            analysis.append("Pending Items:\n");
            for (String milestone : milestones) {
                if (!completedMilestones.contains(milestone)) {
                    analysis.append("- ").append(milestone).append("\n");
                }
            }
        } else {
            analysis.append("STATUS: ON TRACK\n");
            analysis.append("No significant delays identified.\n");
        }
        
        analysis.append("==========================\n");
        return analysis.toString();
    }

    public String recommendCorrectiveActions() {
        StringBuilder recommendations = new StringBuilder();
        recommendations.append("===== CORRECTIVE ACTION RECOMMENDATIONS =====\n");
        recommendations.append("Reform: ").append(reformName).append("\n");
        recommendations.append("Current Status: ").append(milestoneStatus).append("\n\n");
        
        if (milestoneStatus.equals("Delayed")) {
            recommendations.append("HIGH PRIORITY ACTIONS:\n");
            recommendations.append("1. Convene emergency stakeholder meeting\n");
            recommendations.append("2. Reallocate resources to pending milestones\n");
            recommendations.append("3. Request deadline extension from donors\n");
            recommendations.append("4. Identify and remove implementation bottlenecks\n");
            recommendations.append("5. Assign dedicated task force for acceleration\n");
        } else if (milestoneStatus.equals("InProgress")) {
            recommendations.append("MONITORING ACTIONS:\n");
            recommendations.append("1. Continue regular progress monitoring\n");
            recommendations.append("2. Ensure resource availability\n");
            recommendations.append("3. Address minor issues proactively\n");
        } else if (milestoneStatus.equals("Pending")) {
            recommendations.append("INITIATION ACTIONS:\n");
            recommendations.append("1. Finalize implementation plan\n");
            recommendations.append("2. Secure necessary approvals\n");
            recommendations.append("3. Allocate budget and resources\n");
            recommendations.append("4. Begin stakeholder engagement\n");
        }
        
        this.correctiveActions = recommendations.toString();
        recommendations.append("=============================================\n");
        return recommendations.toString();
    }

    // Getters and Setters
    public String getReformId() {
        return reformId;
    }

    public void setReformId(String reformId) {
        this.reformId = reformId;
    }

    public String getReformName() {
        return reformName;
    }

    public void setReformName(String reformName) {
        this.reformName = reformName;
    }

    public String getReformCategory() {
        return reformCategory;
    }

    public void setReformCategory(String reformCategory) {
        this.reformCategory = reformCategory;
    }

    public String getMilestoneStatus() {
        return milestoneStatus;
    }

    public void setMilestoneStatus(String milestoneStatus) {
        this.milestoneStatus = milestoneStatus;
    }

    public LocalDate getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(LocalDate targetDate) {
        this.targetDate = targetDate;
    }

    public LocalDate getActualCompletionDate() {
        return actualCompletionDate;
    }

    public String getIssues() {
        return issues;
    }

    public void setIssues(String issues) {
        this.issues = issues;
    }

    public String getCorrectiveActions() {
        return correctiveActions;
    }

    public double getProgressPercentage() {
        return progressPercentage;
    }

    public List<String> getMilestones() {
        return milestones;
    }

    public List<String> getCompletedMilestones() {
        return completedMilestones;
    }

    public String getResponsibleAgency() {
        return responsibleAgency;
    }

    public void setResponsibleAgency(String responsibleAgency) {
        this.responsibleAgency = responsibleAgency;
    }

    public String getDonorConditionality() {
        return donorConditionality;
    }

    public void setDonorConditionality(String donorConditionality) {
        this.donorConditionality = donorConditionality;
    }

    // Additional setters
    public void setProgressPercentage(double progressPercentage) { 
        this.progressPercentage = progressPercentage; 
    }

    @Override
    public String toString() {
        return "ReformMonitoring{" +
                "reformId='" + reformId + '\'' +
                ", reformName='" + reformName + '\'' +
                ", milestoneStatus='" + milestoneStatus + '\'' +
                ", progressPercentage=" + progressPercentage +
                '}';
    }
}
