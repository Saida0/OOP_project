package com.example.simulation_of_bangladesh_bank.saida;

import com.example.simulation_of_bangladesh_bank.saida.util.DataManager;
import com.example.simulation_of_bangladesh_bank.saida.util.DataInitializer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main Application class for Bangladesh Bank Simulation System.
 * Entry point for the JavaFX application.
 */
public class Main extends Application {

    @Override
    public void init() throws Exception {
        // Initialize data directory and default users before UI loads
        System.out.println("Initializing Bangladesh Bank Simulation System...");
        DataManager.ensureDataDirectory();
        DataManager.initializeDefaultUsers();
        
        // Initialize all application data with realistic startup scenario
        DataInitializer.initializeAllData();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the login screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/simulation_of_bangladesh_bank/saida/fxml/Login.fxml"));
        Parent root = loader.load();
        
        // Create the scene
        Scene scene = new Scene(root, 900, 600);
        
        // Configure the stage
        primaryStage.setTitle("Bangladesh Bank - Central Bank Simulation System");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.centerOnScreen();
        
        // Show the stage
        primaryStage.show();
    }

    /**
     * Main method - entry point of the application.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}

