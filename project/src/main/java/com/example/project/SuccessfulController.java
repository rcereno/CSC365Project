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
    private MenuItem TopFoods;
    @FXML
    private MenuItem NumberUsersOnApp;

    Boolean QueryFoodByIngredientTF = false;
    Boolean UsersFavoriteFoodTF = false;
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
        UsersFavoriteFoodTF = false;
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
        UsersFavoriteFoodTF = false;
        QueryMenu.setText("Search by Ingredient Name. (Insert Ingredient Name)");
    }

    @FXML
    private void setQueryFavoriteFood(){
        QueryFoodByIngredientTF = false;
        UsersFavoriteFoodTF = true;
        QueryMenu.setText("Search a user’s favorite food by Name");
    }



    @FXML
    private void QueryResult2() throws SQLException {
        QueryFoodByIngredientTF = false;
        UsersFavoriteFoodTF = false;
        QueryMenu.setText("What are the usernames and passwords for all users?");
        ResultSet rs;
        Statement statement = connection.createStatement();
        rs = statement.executeQuery("SELECT UserID, password FROM User");
        StringBuilder ans = new StringBuilder();

        while (rs.next()){
            String Username = rs.getString(1);
            String Password = rs.getString(2);
            ans.append(Username).append(", ").append(Password).append("\n");
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
        if (UsersFavoriteFoodTF){
            if (!UserQueryInput.getText().isEmpty()){
                ResultSet rs;
                Statement statement = connection.createStatement();
                rs = statement.executeQuery("select F.likedFood " +
                        "from FavoriteFoods F, User U " +
                        "where F.userID = U.userID and U.name = '" + UserQueryInput.getText() + "'");
                StringBuilder ans = new StringBuilder();
                while (rs.next()){
                    String FoodName = rs.getString(1);
                    ans.append(FoodName).append("\n");
                }
                queryResults.setText(String.valueOf(ans));
            }
            else {
                queryResults.setText("Enter a user's Name.");
            }
        }
    }

    @FXML
    private void UsersOnApp() throws SQLException {
        QueryFoodByIngredientTF = false;
        UsersFavoriteFoodTF = false;
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

    @FXML
    private void MostLikedFoods() throws SQLException {
        QueryFoodByIngredientTF = false;
        UsersFavoriteFoodTF = false;
        QueryMenu.setText("What are the top 10 most liked Food items?");
        ResultSet rs;
        Statement statement = connection.createStatement();
        rs = statement.executeQuery("select likedFood, count(*) as totLikes " +
                "from FavoriteFoods FF " +
                "group by FF.likedFood " +
                "order by totLikes desc " +
                "limit 10");
        StringBuilder ans = new StringBuilder();

        while (rs.next()){
            String Food = rs.getString(1);
            ans.append(Food).append("\n");
        }
        queryResults.setText(String.valueOf(ans));
    }

    @FXML
    private void GetUsersNames() throws SQLException{
        QueryFoodByIngredientTF = false;
        UsersFavoriteFoodTF = false;
        QueryMenu.setText("What are the names of all the users?");
        ResultSet rs;
        Statement statement = connection.createStatement();
        rs = statement.executeQuery("select name " +
                "from User");
        StringBuilder ans = new StringBuilder();

        while (rs.next()){
            String Name = rs.getString(1);
            ans.append(Name).append("\n");
        }
        queryResults.setText(String.valueOf(ans));
    }

}

