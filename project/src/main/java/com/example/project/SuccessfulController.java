package com.example.project;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.sql.*;

public class SuccessfulController {
    @FXML
    private MenuItem testMenu;
    @FXML
    private TextArea queryResults;
    @FXML
    private MenuItem UsernamePassword;

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
    private void QueryResult2() throws SQLException {
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
}

