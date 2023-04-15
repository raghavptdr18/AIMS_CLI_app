package org.AIMS;

import java.io.IOException;
import java.sql.*;
import java.util.Scanner;
import java.sql.Connection;

public class instructor {
    public static void flow() throws IOException {

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

        System.out.println("1. Login");
        System.out.println("PRESS 0 FOR GO BACK");
        System.out.print("Enter an option: ");
        option = in.nextInt();
        if (option == 1) {
            System.out.print("Enter username: ");
            String username = in.next();
            System.out.print("Enter password: ");
            String password = in.next();
            if (login.loginInstructor(username, password)) {
                System.out.println("Login successful!");
                afterLogin(username);
                System.out.println("PRESS 0 FOR GO BACK");
                int back = in.nextInt();
                if (back == 0) instructor.flow();
            } else {
                System.out.println("Login failed.");
                System.out.println("PRESS 0 FOR GO BACK");
                int back = in.nextInt();
                if (back == 0) instructor.flow();
            }
        }else if (option == 0)
            main.main(new String[0]);
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
            String query = "SELECT upload_grade FROM flags";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                check = resultSet.getInt(1);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return check;
    }

    public static boolean afterLogin(String username) throws IOException {
        // JDBC
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = DBConnector.getConnection();
            stmt = conn.createStatement();
        } catch (Exception err) {
            err.printStackTrace();
        }

        System.out.println("1. Register/Deregister Course");
        System.out.println("2. View Grades");
        System.out.println("3. Update Grades via .csv file");
        System.out.println("Press 0 For go Logout");
        System.out.print("Enter an option: ");
        int op;
        Scanner in = new Scanner(System.in);
        op = in.nextInt();
        if (op == 1) {
            instCourse.flow(username);
            System.out.println("PRESS 0 FOR GO BACK");
            int back = in.nextInt();
            if (back == 0) afterLogin(username);
        }
        else if (op == 2) {
            grades.allGrades(username);
            System.out.println("PRESS 0 FOR GO BACK");
            int back = in.nextInt();
            if (back == 0) afterLogin(username);
        }
        else if(op==3){

            int check = 0;
            check = checkWindow(check);
            if(check==1) updateGrade.flow();
            else System.out.println("Window for register and deregister the course is closed");

            System.out.println("PRESS 0 FOR GO BACK");
            int back = in.nextInt();
            if (back == 0) afterLogin(username);
        }
        else
        {
            main.main(new String[0]);
        }

        return true;
    }

}
