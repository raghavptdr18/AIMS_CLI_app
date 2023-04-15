package org.AIMS;
import java.sql.*;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class grades {

    public static boolean allGrades(String username) {

        // JDBC
        Connection conn = null;
        Statement stmt = null;

        try {
            conn=DBConnector.getConnection();
            stmt = conn.createStatement();
        }catch (Exception err)
        {
            err.printStackTrace();
        }

        // invalid username

        try {
            String query = "SELECT * FROM instructors WHERE username = '"+username+"'";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if(!resultSet.next())
            {
                System.out.println("Invalid User");
                return false;
            }
            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed Check");
        }


        try {
            String query = "SELECT grades.username, course_offerings.course_id , grades.grade FROM course_offerings, grades WHERE course_offerings.instructor = '"+username+"' AND course_offerings.course_id=grades.course_id";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    System.out.print(resultSet.getString(i) + " ");
                }
                System.out.println();
                conn.close();
            }
            resultSet.close();
            statement.close();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean allgradesadmin() {

        // JDBC
        Connection conn = null;
        Statement stmt = null;

        try {
            conn=DBConnector.getConnection();
            stmt = conn.createStatement();
        }catch (Exception err)
        {
            err.printStackTrace();
        }

        try {
            String query = "SELECT * FROM grades";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    System.out.print(resultSet.getString(i) + " ");
                }
                System.out.println();
                conn.close();
            }
            resultSet.close();
            statement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static boolean studentGrade(String username) {

        // JDBC
        Connection conn = null;
        Statement stmt = null;

        try {
            conn=DBConnector.getConnection();
            stmt = conn.createStatement();
        }catch (Exception err)
        {
            err.printStackTrace();
        }

        // invalid username

        try {
            String query = "SELECT * FROM users WHERE username = '"+username+"'";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if(!resultSet.next())
            {
                System.out.println("Invalid User");
                return false;
            }
            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed Check");
        }

        try {

            String query = "SELECT * FROM grades WHERE username= '" + username + "'";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    System.out.print(resultSet.getString(i) + " ");
                }
                System.out.println();
                conn.close();
            }
            resultSet.close();
            statement.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
