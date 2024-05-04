<<<<<<< HEAD
module com.insurancecompany.insurancemanagementgroupproject2 {
=======

module com.insurancecompany.insurancemanagementgroupproject2 {

>>>>>>> 7e948976a48c67aaa8e044101863f042f0bf6148
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires org.postgresql.jdbc;


<<<<<<< HEAD
=======

>>>>>>> 7e948976a48c67aaa8e044101863f042f0bf6148
    opens com.insurancecompany.insurancemanagementgroupproject2 to javafx.fxml;
    exports com.insurancecompany.insurancemanagementgroupproject2;
    exports com.insurancecompany.insurancemanagementgroupproject2.controller;
    opens com.insurancecompany.insurancemanagementgroupproject2.controller to javafx.fxml;
<<<<<<< HEAD
=======

>>>>>>> 7e948976a48c67aaa8e044101863f042f0bf6148
}