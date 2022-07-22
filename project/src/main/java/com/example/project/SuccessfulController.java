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
            hi.append(s).append("\n\n");
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
            ans.append(Username).append(", ").append(Password).append("\n\n");
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
                    ans.append(FoodName).append("\n\n");
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
                    ans.append(FoodName).append("\n\n");
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
            ans.append(NumberOfUsers).append("\n\n");
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
            ans.append(Food).append("\n\n");
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
            ans.append(Name).append("\n\n");
        }
        queryResults.setText(String.valueOf(ans));
    }
    @FXML
    private void FavoriteMealPlan() throws SQLException{
        QueryFoodByIngredientTF = false;
        UsersFavoriteFoodTF = false;
        QueryMenu.setText("List of favorite foods eaten in MealPlan");
        ResultSet rs;
        Statement statement = connection.createStatement();
        rs = statement.executeQuery("select F.foodName, M.mealDate, F.ingredients, U.name " +
                "from MealPlanner M, Food F, FavoriteFoods FF, User U " +
                "where FF.userID = U.userID and FF.likedFood = M.foodName");
        StringBuilder ans = new StringBuilder();

        while (rs.next()){
            String foodName = rs.getString(1);
            String mealDate = rs.getString(2);
            String ingredients = rs.getString(3);
            String name = rs.getString(4);

            ans.append(foodName).append(", ").append(mealDate).append(", ").append(ingredients).append(", ").append(name).append(", ").append("\n\n");
        }
        queryResults.setText(String.valueOf(ans));
    }

    @FXML
    private void ManyFoodsUsersLiked() throws SQLException{
        QueryFoodByIngredientTF = false;
        UsersFavoriteFoodTF = false;
        QueryMenu.setText("How many foods all users have liked?");
        ResultSet rs;
        Statement statement = connection.createStatement();
        rs = statement.executeQuery("select U.userID, coalesce(count(likedFood), 0) as totLikes " +
                "from User U left join FavoriteFoods FF on (U.userID = FF.userID) " +
                "group by U.userID");
        StringBuilder ans = new StringBuilder();

        while (rs.next()){
            String UserID = rs.getString(1);
            String TotalLikes = rs.getString(2);

            ans.append(UserID).append(", ").append(TotalLikes).append("\n\n");
        }
        queryResults.setText(String.valueOf(ans));
    }

    @FXML
    private void SumCaloriesOnDate() throws SQLException{
        QueryFoodByIngredientTF = false;
        UsersFavoriteFoodTF = false;
        QueryMenu.setText("Sum of Calories for a User based on Exact Year + Month + Day");
        ResultSet rs;
        Statement statement = connection.createStatement();
        rs = statement.executeQuery("select M.userID, year(M.mealDate) as year, month(M.mealDate) as month, day(M.mealDate) as day, sum(F.calorieCount) as dailyCalories, U.calorieLimit " +
                "from Food F, MealPlanner M, User U " +
                "where F.foodName = M.foodName and U.userID = M.userID " +
                "group by M.userID, month(M.mealDate), year(M.mealDate), day(M.mealDate), U.calorieLimit;");
        StringBuilder ans = new StringBuilder();

        while (rs.next()){
            String UserID = rs.getString(1);
            String Year = rs.getString(2);
            String Month = rs.getString(3);
            String day = rs.getString(4);
            String dailyCalories = rs.getString(5);
            String calorieLimit = rs.getString(6);
            ans.append(UserID).append(", ").append(Year).append(", ").append(Month).append(", ").append(day).append(", ").append(dailyCalories).append(", ").append(calorieLimit).append("\n\n");
        }
        queryResults.setText(String.valueOf(ans));
    }
}

