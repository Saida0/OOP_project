package com.example.simulation_of_bangladesh_bank.saida.controller;

import com.example.simulation_of_bangladesh_bank.saida.model.BranchPerformance;
import com.example.simulation_of_bangladesh_bank.saida.util.DataManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller for Branch Performance Monitoring view.
 */
public class BranchPerformanceController implements Initializable {

    @FXML
    private Label lblTotalBranches;

    @FXML
    private Label lblTopPerformers;

    @FXML
    private Label lblWeakBranches;

    @FXML
    private Label lblAvgKPI;

    @FXML
    private TextField txtBranchID;

    @FXML
    private ComboBox<String> cmbRegion;

    @FXML
    private TextField txtKPIScore;

    @FXML
    private TextArea txtRemarks;

    @FXML
    private Button btnLoadData;

    @FXML
    private Button btnRanking;

    @FXML
    private Button btnIdentifyWeak;

    @FXML
    private Button btnRecommend;

    @FXML
    private TableView<BranchPerformance> tblBranches;

    @FXML
    private TableColumn<BranchPerformance, String> colRank;

    @FXML
    private TableColumn<BranchPerformance, String> colBranchID;

    @FXML
    private TableColumn<BranchPerformance, String> colRegion;

    @FXML
    private TableColumn<BranchPerformance, String> colKPI;

    @FXML
    private TableColumn<BranchPerformance, String> colStatus;

    @FXML
    private TableColumn<BranchPerformance, String> colRemarks;

    private ObservableList<BranchPerformance> branches = FXCollections.observableArrayList();
    private BranchPerformance currentBranch;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize region options
        cmbRegion.setItems(FXCollections.observableArrayList(
            "Dhaka Division",
            "Chittagong Division",
            "Rajshahi Division",
            "Khulna Division",
            "Barisal Division",
            "Sylhet Division",
            "Rangpur Division",
            "Mymensingh Division"
        ));
        
        // Initialize table columns
        setupTableColumns();
        
        // Load data from persistent storage
        loadBranchHistory();
        
        // Add table selection listener for auto-populate
        setupTableSelectionListener();
        
        // Update statistics
        updateStatistics();
    }
    
    /**
     * Loads branch data from persistent storage.
     */
    private void loadBranchHistory() {
        List<BranchPerformance> savedBranches = DataManager.loadFromFile(DataManager.BRANCHES_FILE);
        
        if (savedBranches.isEmpty()) {
            System.out.println("No previous branch data found.");
        } else {
            branches.addAll(savedBranches);
            // Sort by KPI descending
            branches.sort((a, b) -> Double.compare(b.getKpiScore(), a.getKpiScore()));
            System.out.println("Loaded " + savedBranches.size() + " branch records from history.");
        }
    }
    
    /**
     * Saves branch data to persistent storage.
     */
    private void saveBranchHistory() {
        List<BranchPerformance> toSave = new java.util.ArrayList<>(branches);
        boolean success = DataManager.saveToFile(DataManager.BRANCHES_FILE, toSave);
        if (success) {
            System.out.println("Branch data saved successfully.");
        } else {
            System.err.println("Failed to save branch data!");
        }
    }
    
    /**
     * Sets up table selection listener for auto-populating form fields.
     */
    private void setupTableSelectionListener() {
        tblBranches.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateFormFromBranch(newSelection);
            }
        });
    }
    
    /**
     * Populates form fields from selected branch.
     */
    private void populateFormFromBranch(BranchPerformance branch) {
        currentBranch = branch;
        
        // Populate Branch ID
        txtBranchID.setText(branch.getBranchID() != null ? branch.getBranchID() : "");
        
        // Populate Region
        String region = branch.getRegion();
        if (region != null && !region.isEmpty()) {
            // Map to division if needed
            if (!region.contains("Division")) {
                region = mapToRegion(region);
            }
            cmbRegion.setValue(region);
        }
        
        // Populate KPI Score
        txtKPIScore.setText(String.format("%.1f", branch.getKpiScore()));
        
        // Populate Remarks
        txtRemarks.setText(branch.getRemarks() != null ? branch.getRemarks() : "");
        
        // Show branch details
        String branchName = branch.getBranchName() != null ? branch.getBranchName() : branch.getBranchID();
        double deposits = branch.getTotalDeposits();
        double loans = branch.getTotalLoans();
        int employees = branch.getEmployeeCount();
        int customers = branch.getCustomerCount();
        
        showInfo("Selected Branch:\n\n" +
                "Branch: " + branchName + "\n" +
                "ID: " + branch.getBranchID() + "\n" +
                "Region: " + (branch.getRegion() != null ? branch.getRegion() : "N/A") + "\n" +
                "KPI Score: " + String.format("%.1f", branch.getKpiScore()) + "\n" +
                "Total Deposits: ৳" + formatCurrency(deposits) + "\n" +
                "Total Loans: ৳" + formatCurrency(loans) + "\n" +
                "Employees: " + employees + "\n" +
                "Customers: " + customers);
    }
    
    /**
     * Maps city/area name to division.
     */
    private String mapToRegion(String area) {
        if (area == null) return "Dhaka Division";
        area = area.toLowerCase();
        if (area.contains("dhaka") || area.contains("motijheel") || area.contains("gulshan") || 
            area.contains("uttara") || area.contains("dhanmondi") || area.contains("mirpur") || 
            area.contains("banani")) {
            return "Dhaka Division";
        } else if (area.contains("chittagong") || area.contains("chattogram")) {
            return "Chittagong Division";
        } else if (area.contains("sylhet")) {
            return "Sylhet Division";
        } else if (area.contains("khulna")) {
            return "Khulna Division";
        } else if (area.contains("rajshahi")) {
            return "Rajshahi Division";
        }
        return "Dhaka Division";
    }
    
    /**
     * Formats currency for display.
     */
    private String formatCurrency(double amount) {
        if (amount >= 1000000000) {
            return String.format("%.2fB", amount / 1000000000);
        } else if (amount >= 1000000) {
            return String.format("%.2fM", amount / 1000000);
        } else if (amount >= 1000) {
            return String.format("%.2fK", amount / 1000);
        }
        return String.format("%.0f", amount);
    }

    /**
     * Sets up table columns.
     */
    private void setupTableColumns() {
        colRank.setCellValueFactory(data -> {
            int rank = branches.indexOf(data.getValue()) + 1;
            return new SimpleStringProperty(String.valueOf(rank));
        });
        
        colBranchID.setCellValueFactory(data -> {
            String name = data.getValue().getBranchName();
            if (name != null && !name.isEmpty()) {
                return new SimpleStringProperty(name);
            }
            return new SimpleStringProperty(data.getValue().getBranchID());
        });
        
        colRegion.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().getRegion() != null ? data.getValue().getRegion() : "N/A"));
        
        colKPI.setCellValueFactory(data -> 
            new SimpleStringProperty(String.format("%.1f", data.getValue().getKpiScore())));
        
        colStatus.setCellValueFactory(data -> {
            double kpi = data.getValue().getKpiScore();
            if (kpi >= 90) return new SimpleStringProperty("Excellent");
            else if (kpi >= 80) return new SimpleStringProperty("Very Good");
            else if (kpi >= 70) return new SimpleStringProperty("Good");
            else if (kpi >= 60) return new SimpleStringProperty("Average");
            else return new SimpleStringProperty("Needs Improvement");
        });
        
        colRemarks.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().getRemarks() != null ? data.getValue().getRemarks() : ""));
        
        tblBranches.setItems(branches);
    }

    /**
     * Updates statistics cards.
     */
    private void updateStatistics() {
        lblTotalBranches.setText(String.valueOf(branches.size()));
        
        long topPerformers = branches.stream()
            .filter(b -> b.getKpiScore() >= 80).count();
        lblTopPerformers.setText(String.valueOf(topPerformers));
        
        long weakBranches = branches.stream()
            .filter(b -> b.getKpiScore() < 50).count();
        lblWeakBranches.setText(String.valueOf(weakBranches));
        
        double avgKPI = branches.stream()
            .mapToDouble(BranchPerformance::getKpiScore).average().orElse(0);
        lblAvgKPI.setText(String.format("%.1f", avgKPI));
    }

    /**
     * Loads branch data.
     */
    @FXML
    public void loadBranchData(ActionEvent event) {
        String branchID = txtBranchID.getText().trim();
        
        if (!branchID.isEmpty()) {
            // Search for specific branch
            BranchPerformance found = branches.stream()
                .filter(b -> b.getBranchID().equalsIgnoreCase(branchID))
                .findFirst().orElse(null);
            
            if (found != null) {
                found.loadBranchData();
                txtKPIScore.setText(String.format("%.1f", found.getKpiScore()));
                cmbRegion.setValue(found.getRegion());
                txtRemarks.setText(found.getRemarks());
                showInfo("Branch data loaded: " + branchID);
            } else {
                showWarning("Branch not found: " + branchID);
            }
        } else {
            // Refresh all data
            branches.forEach(BranchPerformance::loadBranchData);
            updateStatistics();
            showInfo("All branch data refreshed");
        }
    }

    /**
     * Generates branch ranking.
     */
    @FXML
    public void generateRanking(ActionEvent event) {
        // Sort branches by KPI score descending
        branches.sort((a, b) -> Double.compare(b.getKpiScore(), a.getKpiScore()));
        
        // Call generateRanking on model
        if (!branches.isEmpty()) {
            branches.get(0).generateRanking();
        }
        
        tblBranches.refresh();
        updateStatistics();
        
        StringBuilder ranking = new StringBuilder("=== BRANCH RANKING ===\n\n");
        for (int i = 0; i < Math.min(5, branches.size()); i++) {
            BranchPerformance b = branches.get(i);
            ranking.append(String.format("%d. %s (%s) - KPI: %.1f\n", 
                i + 1, b.getBranchID(), b.getRegion(), b.getKpiScore()));
        }
        
        showInfo("Branch ranking generated.\n\nTop 5 Branches:\n" + ranking);
    }

    /**
     * Identifies weak performing branches.
     */
    @FXML
    public void identifyWeakBranches(ActionEvent event) {
        if (!branches.isEmpty()) {
            branches.get(0).identifyWeakBranches();
        }
        
        StringBuilder weakList = new StringBuilder("=== BRANCHES NEEDING IMPROVEMENT ===\n\n");
        weakList.append("(KPI Score < 50)\n\n");
        
        long count = 0;
        for (BranchPerformance b : branches) {
            if (b.getKpiScore() < 50) {
                weakList.append(String.format("• %s (%s) - KPI: %.1f\n  Remarks: %s\n\n",
                    b.getBranchID(), b.getRegion(), b.getKpiScore(), b.getRemarks()));
                count++;
            }
        }
        
        if (count == 0) {
            weakList.append("No branches below minimum KPI threshold.\n");
        }
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Weak Branches");
        alert.setHeaderText("Branches Requiring Attention: " + count);
        
        TextArea textArea = new TextArea(weakList.toString());
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setPrefHeight(300);
        
        alert.getDialogPane().setContent(textArea);
        alert.showAndWait();
    }

    /**
     * Recommends improvements for branches.
     */
    @FXML
    public void recommendImprovements(ActionEvent event) {
        String branchID = txtBranchID.getText().trim();
        BranchPerformance target = null;
        
        if (!branchID.isEmpty()) {
            target = branches.stream()
                .filter(b -> b.getBranchID().equalsIgnoreCase(branchID))
                .findFirst().orElse(null);
        }
        
        if (target == null && !branches.isEmpty()) {
            // Get lowest performing branch
            target = branches.stream()
                .min(Comparator.comparingDouble(BranchPerformance::getKpiScore))
                .orElse(branches.get(branches.size() - 1));
        }
        
        if (target != null) {
            target.recommendImprovements();
            
            StringBuilder recommendations = new StringBuilder();
            recommendations.append("=== IMPROVEMENT RECOMMENDATIONS ===\n\n");
            recommendations.append("Branch: ").append(target.getBranchID()).append("\n");
            recommendations.append("Region: ").append(target.getRegion()).append("\n");
            recommendations.append("Current KPI: ").append(String.format("%.1f", target.getKpiScore())).append("\n\n");
            
            if (target.getKpiScore() < 50) {
                recommendations.append("Priority Actions:\n");
                recommendations.append("1. Conduct immediate performance review\n");
                recommendations.append("2. Implement recovery action plan\n");
                recommendations.append("3. Assign performance mentor\n");
                recommendations.append("4. Weekly progress monitoring\n\n");
            } else if (target.getKpiScore() < 70) {
                recommendations.append("Improvement Actions:\n");
                recommendations.append("1. Focus on customer acquisition\n");
                recommendations.append("2. Improve loan recovery rate\n");
                recommendations.append("3. Enhance digital banking adoption\n");
                recommendations.append("4. Monthly performance review\n\n");
            } else {
                recommendations.append("Maintenance Actions:\n");
                recommendations.append("1. Continue current strategies\n");
                recommendations.append("2. Focus on customer retention\n");
                recommendations.append("3. Explore new market opportunities\n");
                recommendations.append("4. Knowledge sharing with other branches\n\n");
            }
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Recommendations");
            alert.setHeaderText("Improvement Recommendations");
            
            TextArea textArea = new TextArea(recommendations.toString());
            textArea.setEditable(false);
            textArea.setWrapText(true);
            textArea.setPrefHeight(350);
            
            alert.getDialogPane().setContent(textArea);
            alert.showAndWait();
        } else {
            showError("No branch data available");
        }
    }

    /**
     * Refreshes the table.
     */
    @FXML
    public void refreshTable(ActionEvent event) {
        tblBranches.refresh();
        updateStatistics();
        showInfo("Table refreshed");
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

