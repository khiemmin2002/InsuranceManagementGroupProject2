package com.insurancecompany.insurancemanagementgroupproject2.controller;
/**
 * @author team 5
 */
import com.insurancecompany.insurancemanagementgroupproject2.DatabaseConnection;
import com.insurancecompany.insurancemanagementgroupproject2.model.Surveyor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SurveyorController extends Thread{
    private final DatabaseConnection databaseConnection;
    private final Connection connection;
    // Constructor to SurveyorController
    public SurveyorController(DatabaseConnection databaseConnection, Connection connection) {
         this.databaseConnection = databaseConnection;
        this.connection = connection;
    }
    // Method to fetch surveyor information from user table from database
    public List<Surveyor> fetchSurveyor(){
        List<Surveyor> surveyorList = new ArrayList<Surveyor>();
        try {
            String getSurveyorQuery = "SELECT * FROM users WHERE role_id = 3";
            Statement statement = connection.createStatement();
            ResultSet queryResult = statement.executeQuery(getSurveyorQuery);
            while (queryResult.next()){
                Surveyor provider = new Surveyor(
                        queryResult.getString("id"),
                        queryResult.getString("full_name"),
                        queryResult.getString("user_name"),
                        queryResult.getString("password"),
                        queryResult.getString("email"),
                        queryResult.getString("phone_number"),
                        queryResult.getString("address"),
                        queryResult.getInt("role_id")
                );
                surveyorList.add(provider);
            }
            System.out.println("Fetch data from database.users successfully!");
        }catch (SQLException e){
            System.out.println("SQL exception: " + e);
        }
        return surveyorList;
    }
    // Method to create new surveyor inside user database table
    public boolean createNewSurveyor(String id, String full_name, String user_name, String password, String email, String phone_number, String address){
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
            System.out.println("Create new surveyor with id " +  id);
            return true;
        }catch (SQLException e){
            System.out.println("SQL exception from SurveyorController.createNewSurveyor: " + e);
            return false;
        }
    }
    // Method to update user information for surveyor existing
    public boolean updateSurveyorInformation(String id,String full_name, String email, String phone_number, String address){
        try{
            String updateSurveyor = "UPDATE users SET full_name = ?, email = ?, phone_number = ?, address = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateSurveyor);
            preparedStatement.setString(1,full_name);
            preparedStatement.setString(2,email);
            preparedStatement.setString(3,phone_number);
            preparedStatement.setString(4,address);
            preparedStatement.setString(5,id);
            preparedStatement.execute();
            System.out.println("Updated surveyor at id " + id);
            return true;
        }catch (SQLException e){
            System.out.println("SQL exception from SurveyourController.updateSurveyor: " + e);
            return false;
        }
    }
    // Method to remove a surveyor from user database table
    public boolean removeSurveyor(String id){
        try{
            String updateSurveyor = "DELETE FROM users WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateSurveyor);
            preparedStatement.setString(1,id);
            preparedStatement.execute();
            System.out.println("Delete surveyor of id " + id);
            return true;
        }catch (SQLException e){
            System.out.println("SQL exception from SurveyourController.updateSurveyor: " + e);
            return false;
        }
    }
}
