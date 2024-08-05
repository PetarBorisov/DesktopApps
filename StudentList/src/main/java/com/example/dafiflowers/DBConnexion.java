package com.example.dafiflowers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnexion {
    static String user = System.getenv("DB_USER");
    static String password = System.getenv("DB_PASSWORD");
    static String url = System.getenv("DB_URL");
    static String driver = "com.mysql.cj.jdbc.Driver";

    public static Connection getCon(){

        Connection con = null;
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, user,password);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }catch (ClassNotFoundException e)  {
            throw new RuntimeException(e);

        }
        return con;
    }
}
