module com.example.loginregisterfeature {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires org.postgresql.jdbc;


    opens com.example.icms to javafx.fxml;
    exports com.example.icms;
    exports com.example.icms.controller;
    opens com.example.icms.controller to javafx.fxml;
}