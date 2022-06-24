package com.moutasim.registrationsystem.DBConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnector {
    private static final String DATABASE_URL = "jdbc:mysql://192.168.1.41:3307/registration";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "TestServer";

    public static Connection connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
            System.out.println("Connected");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return connection;
    }


}
