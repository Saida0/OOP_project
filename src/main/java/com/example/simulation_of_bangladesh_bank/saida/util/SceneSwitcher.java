package com.example.simulation_of_bangladesh_bank.saida.util;

import com.example.simulation_of_bangladesh_bank.saida.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Utility class for switching between scenes and loading FXML views.
 * Similar to Shifat's SceneSwitcher, but enhanced for Saida's dashboard pattern.
 */
public class SceneSwitcher {

    /**
     * Switches to a new scene (full window replacement).
     * Similar to Shifat's sceneSwitch method.
     * 
     * @param actionEvent The action event from the button/control
     * @param fxmlFile The path to the FXML file (relative to resources)
     * @param title The title for the new window
     * @throws IOException if the FXML file cannot be loaded
     */
    public static void sceneSwitch(ActionEvent actionEvent, String fxmlFile, String title) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxmlFile));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Loads content into a ScrollPane (for dashboard content area).
     * Used by Saida's dashboard controllers to load views into the main content area.
     * 
     * @param scrollPane The ScrollPane to load content into
     * @param fxmlFile The path to the FXML file (relative to resources)
     * @return The loaded Parent node, or null if loading failed
     */
    public static Parent loadContentIntoScrollPane(ScrollPane scrollPane, String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxmlFile));
            Parent content = loader.load();
            scrollPane.setContent(content);
            return content;
        } catch (IOException e) {
            System.err.println("Error loading FXML file: " + fxmlFile);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Navigates back to the login screen.
     * 
     * @param actionEvent The action event from the button/control
     * @throws IOException if the login FXML file cannot be loaded
     */
    public static void navigateToLogin(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/com/example/simulation_of_bangladesh_bank/saida/fxml/Login.fxml"));
        Scene scene = new Scene(loader.load(), 900, 600);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Bangladesh Bank - Central Bank Simulation System");
        stage.setScene(scene);
        stage.setMaximized(false);
        stage.setWidth(900);
        stage.setHeight(600);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * Navigates back to the login screen (overloaded for non-ActionEvent usage).
     * 
     * @param node Any node from the current scene
     * @throws IOException if the login FXML file cannot be loaded
     */
    public static void navigateToLogin(Node node) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/com/example/simulation_of_bangladesh_bank/saida/fxml/Login.fxml"));
        Scene scene = new Scene(loader.load(), 900, 600);
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setTitle("Bangladesh Bank - Central Bank Simulation System");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}

