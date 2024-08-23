package com.example.flower_shop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    static String user = "root";
    static String password = "novo_na4aloza17";
    static String url = "jdbc:mysql://localhost/flower";
    static String driver = "com.mysql.cj.jdbc.Driver";

    public static Connection connectDb(){

        Connection con = null;
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, user, password);
            }catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } catch (ClassNotFoundException e)  {
            throw new RuntimeException(e);
        }
        return con;
    }
}