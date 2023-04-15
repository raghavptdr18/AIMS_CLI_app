package org.AIMS;

import java.io.IOException;
import java.sql.*;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class instCourse {
    public static void flow(String username) throws IOException {

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

        Scanner in = new Scanner(System.in);
        int option;

        System.out.println("1. View Course Catalog");
        System.out.println("2. Register/Deregister Course");
        System.out.println("3. View my Courses");
        System.out.println("PRESS 0 FOR GO BACK");
        System.out.println("Enter Option");
        option = in.nextInt();

        if (option == 1) {

            viewCourseCatalog();
            System.out.println("PRESS 0 FOR GO BACK");
            int back = in.nextInt();
            if (back == 0) instCourse.flow(username);
        }
        else if (option == 2) {
            // check for edit window
            int check = 0;
            check = checkWindow(check);

            if (check == 1) {
                System.out.println("1. Register Course");
                System.out.println("2. Deregister Course");
                System.out.println("PRESS 0 FOR GO BACK");
                System.out.println("Enter Option");
                int op = in.nextInt();

                if (op == 1) {

                    System.out.println("1. Enter course id");
                    String id = in.next();
                    System.out.println("2. Enter CGPA constraint");
                    Double constraint = in.nextDouble();

                    registerDeregister.registerDeregisterInstructor(op,username,id,constraint);
                    System.out.println("PRESS 0 FOR GO BACK");
                    int back = in.nextInt();
                    if (back == 0) instCourse.flow(username);

                } // register course
                else if (op == 2) {

                    System.out.println("1. Enter course id for Deregester");
                    String id = in.next();

                    registerDeregister.registerDeregisterInstructor(op,username,id,0.0);

                    System.out.println("PRESS 0 FOR GO BACK");
                    int back = in.nextInt();
                    if (back == 0) instCourse.flow(username);
                } // deregister course
                else if (op == 0) instCourse.flow(username);

                System.out.println("PRESS 0 FOR GO BACK");
                int back = in.nextInt();
                if (back == 0) instCourse.flow(username);

            } else {
                System.out.println("Window for register and deregister the course is closed");
            }
            System.out.println("PRESS 0 FOR GO BACK");
            int back = in.nextInt();
            if (back == 0) instCourse.flow(username);
        }
        else if (option == 3) {

            viewMyCourse(username);
            System.out.println("PRESS 0 FOR GO BACK");
            int back = in.nextInt();
            if (back == 0) instCourse.flow(username);
        } // view my courses
        else if (option == 0) instructor.afterLogin(username);
    }


    public static boolean viewCourseCatalog()
    {
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
            String query = "SELECT * FROM course_catalog_admin";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    System.out.print(resultSet.getString(i) + " ");
                }
                System.out.println();
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean viewMyCourse(String username) {
        // JDBC
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = DBConnector.getConnection();
            stmt = conn.createStatement();
        } catch (Exception err) {
            err.printStackTrace();
        }

        // invalid user

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
            String query = "SELECT * FROM course_offerings WHERE instructor = '" + username + "'";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    System.out.print(resultSet.getString(i) + " ");
                }
                System.out.println();
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static int checkWindow(int check) {
        // JDBC
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = DBConnector.getConnection();
            stmt = conn.createStatement();
        } catch (Exception err) {
            err.printStackTrace();
        }

        try {
            String query = "SELECT course_registration_instructor FROM flags";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet.next())
            {
                check = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return check;
    }

}
