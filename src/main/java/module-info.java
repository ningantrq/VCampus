module com.vcampus {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires javafx.swing;


    opens com.vcampus.controller.login to javafx.fxml;
    exports com.vcampus;
}