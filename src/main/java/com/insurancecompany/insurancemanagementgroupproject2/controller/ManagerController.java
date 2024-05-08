package com.insurancecompany.insurancemanagementgroupproject2.controller;

import com.insurancecompany.insurancemanagementgroupproject2.DatabaseConnection;
import com.insurancecompany.insurancemanagementgroupproject2.model.Manager;
import com.insurancecompany.insurancemanagementgroupproject2.model.Surveyor;
import com.insurancecompany.insurancemanagementgroupproject2.model.User;

import java.sql.*;
import java.util.*;

public class ManagerController {

    private final DatabaseConnection databaseConnection;
    private final Connection connection;

    public ManagerController() {
        this.databaseConnection = new DatabaseConnection();
        this.connection = databaseConnection.getConnection();
    }

    public boolean createNewManager(String id, String full_name, String user_name, String password, String email, String phone_number, String address){
        try{
            String insertSurveyor = "INSERT INTO users VALUES (?,?,?,?, 3,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSurveyor);
            preparedStatement.setString(1,id);
            preparedStatement.setString(2,full_name);
            preparedStatement.setString(3,user_name);
            preparedStatement.setString(4,password);
            preparedStatement.setString(5,email);
            preparedStatement.setString(6,phone_number);
            preparedStatement.setString(7,address);
            preparedStatement.execute();
            System.out.println("Create new manager with id " +  id);
            return true;
        }catch (SQLException e){
            System.out.println("SQL exception from SurveyorController.createNewSurveyor: " + e);
            return false;
        }
    }
}
