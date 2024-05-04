<<<<<<< HEAD
module com.example.loginregisterfeature {
=======
module com.insurancecompany.insurancemanagementgroupproject2 {
>>>>>>> 077082379a5d6c362018e6cbc37b96355c96706b
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires org.postgresql.jdbc;


<<<<<<< HEAD
    opens com.example.icms to javafx.fxml;
    exports com.example.icms;
    exports com.example.icms.controller;
    opens com.example.icms.controller to javafx.fxml;
=======
    opens com.insurancecompany.insurancemanagementgroupproject2 to javafx.fxml;
    exports com.insurancecompany.insurancemanagementgroupproject2;
    exports com.insurancecompany.insurancemanagementgroupproject2.controller;
    opens com.insurancecompany.insurancemanagementgroupproject2.controller to javafx.fxml;
>>>>>>> 077082379a5d6c362018e6cbc37b96355c96706b
}