module com.insurancecompany.insurancemanagementgroupproject2 {
    requires javafx.controls;
    requires javafx.fxml;

    exports com.insurancecompany.insurancemanagementgroupproject2.login to javafx.fxml;

    opens com.insurancecompany.insurancemanagementgroupproject2 to javafx.fxml;
    exports com.insurancecompany.insurancemanagementgroupproject2;
}