package com.example.project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class SuccessfulController {
    PreparedStatement ps;
    Connection connection;
    public SuccessfulController(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://ambari-node5.csc.calpoly.edu/eumorale?user=eumorale&password=027830952");
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}

