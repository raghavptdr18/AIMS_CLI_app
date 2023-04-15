package org.AIMS;
import java.sql.*;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnector {

    private static final String URL = "jdbc:postgresql://localhost:5432/aims";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234";

    public static Connection getConnection() throws SQLException {
        try{

            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL,USER,PASSWORD);

        }catch (Exception e)
        {
            System.out.println(e);
        }
        return null;
    }

}
