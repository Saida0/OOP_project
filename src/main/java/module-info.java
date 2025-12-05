module com.example.simulation_of_bangladesh_bank {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.simulation_of_bangladesh_bank to javafx.fxml;
    opens com.example.simulation_of_bangladesh_bank.shifat to javafx.fxml;
    opens com.example.simulation_of_bangladesh_bank.saida to javafx.fxml;
    opens com.example.simulation_of_bangladesh_bank.saida.controller to javafx.fxml;
    opens com.example.simulation_of_bangladesh_bank.saida.util to javafx.fxml;
    opens com.example.simulation_of_bangladesh_bank.saida.model to javafx.fxml;
    exports com.example.simulation_of_bangladesh_bank;
    exports com.example.simulation_of_bangladesh_bank.shifat;
    exports com.example.simulation_of_bangladesh_bank.saida;
    exports com.example.simulation_of_bangladesh_bank.saida.controller;
    exports com.example.simulation_of_bangladesh_bank.saida.util;
    exports com.example.simulation_of_bangladesh_bank.saida.model;
}