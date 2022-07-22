package com.example.project;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.sql.*;

public class SuccessfulController {
    @FXML
    private MenuButton QueryMenu;
    @FXML
    private MenuItem testMenu;
    @FXML
    private TextArea queryResults;
    @FXML
    private MenuItem UsernamePassword;
    @FXML
    private MenuItem QueryFoodByIngredient;
    @FXML
    private TextField UserQueryInput;
    @FXML
    private MenuItem Query1;
    @FXML
    private TextField foodName;
    @FXML
    private TextArea ingredients;
    @FXML
    private TextArea recipeSteps;
    @FXML
    private MenuButton difficulty;
    @FXML
    private MenuItem difficultyItem;
    @FXML
    private TextField calorieCount;
    @FXML
    private MenuButton mealType;
    @FXML
    private MenuItem mealItem;

    @FXML
    private MenuItem NumberUsersOnApp;

    Boolean QueryFoodByIngredientTF = false;
    PreparedStatement ps;
    Connection connection;
    public SuccessfulController() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://ambari-node5.csc.calpoly.edu/eumorale?user=eumorale&password=027830952");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void HandleMouse(MouseEvent event) {
        EventHandler<ActionEvent> event1 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                String t = ((MenuItem) e.getSource()).getText();
                System.out.println(t);
            }
        };
    }

    @FXML
    private void testQuery(ActionEvent event){
        QueryFoodByIngredientTF = false;
        QueryMenu.setText("Test");
        StringBuilder hi= new StringBuilder();
        int i = 0;
        while (i < 10){
            i++;
            String s = String.valueOf(i);
            hi.append(s).append("\n");
        }
        queryResults.setText(String.valueOf(hi));
    }

    @FXML
    private void setQuery2True(){
        QueryFoodByIngredientTF = true;
        QueryMenu.setText("Search by Ingredient Name. (Insert Ingredient Name)");
    }

    @FXML
    private void QueryResult2() throws SQLException {
        QueryFoodByIngredientTF = false;
        QueryMenu.setText("What are the usernames and passwords for all users?");
        ResultSet rs;
        Statement statement = connection.createStatement();
        rs = statement.executeQuery("SELECT UserID, password FROM User");
        StringBuilder ans = new StringBuilder();

        while (rs.next()){
            String Username = rs.getString(1);
            String Password = rs.getString(2);
            ans.append(Username).append(",").append(Password).append("\n");
        }
        queryResults.setText(String.valueOf(ans));
    }

    @FXML
    private void QueryByInput() throws SQLException{
        if (QueryFoodByIngredientTF){
            if (!UserQueryInput.getText().isEmpty()){
                ResultSet rs;
                Statement statement = connection.createStatement();
                rs = statement.executeQuery("select foodName" +
                        " from Food F" +
                        " where F.ingredients " +
                        " LIKE '%" + UserQueryInput.getText() + "%'");
                StringBuilder ans = new StringBuilder();
                while (rs.next()){
                    String FoodName = rs.getString(1);
                    ans.append(FoodName).append("\n");
                }
                queryResults.setText(String.valueOf(ans));
            }
            else {
                queryResults.setText("Enter an ingredient.");
            }
        }
    }

    @FXML
    private void UsersOnApp() throws SQLException {
        QueryFoodByIngredientTF = false;
        QueryMenu.setText("How many users are using this App?");
        ResultSet rs;
        Statement statement = connection.createStatement();
        rs = statement.executeQuery("select count(*) as totUsers from User U");
        StringBuilder ans = new StringBuilder();

        while (rs.next()){
            String NumberOfUsers = rs.getString(1);
            ans.append(NumberOfUsers).append("\n");
        }
        queryResults.setText(String.valueOf(ans));
    }
}

