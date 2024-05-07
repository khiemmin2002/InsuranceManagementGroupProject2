module com.insurancecompany.insurancemanagementgroupproject2 {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires org.postgresql.jdbc;

    opens com.insurancecompany.insurancemanagementgroupproject2 to javafx.fxml;
    exports com.insurancecompany.insurancemanagementgroupproject2;
    exports com.insurancecompany.insurancemanagementgroupproject2.controller;
    opens com.insurancecompany.insurancemanagementgroupproject2.controller to javafx.fxml;
    opens com.insurancecompany.insurancemanagementgroupproject2.model to javafx.base;

}