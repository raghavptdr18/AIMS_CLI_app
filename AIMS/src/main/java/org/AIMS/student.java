package org.AIMS;

import java.io.IOException;
import java.sql.*;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;

public class student {
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
            if (login.loginStudent(username, password)) {
                System.out.println("Login successful!");
                afterLogin(username);
                System.out.println("PRESS 0 FOR LOGOUT");
                int back = in.nextInt();
                if (back == 0) student.flow();

            } else {
                System.out.println("Login failed.");
                System.out.println("PRESS 0 FOR GO BACK");
                int back = in.nextInt();
                if (back == 0) student.flow();
            }
        } else if (option == 0)
            main.main(new String[0]);

    }


    public static boolean graduationCheck(String username) {

        Connection conn = null;
        Statement stmt = null;

        try {
            conn = DBConnector.getConnection();
            stmt = conn.createStatement();
        } catch (Exception err) {
            err.printStackTrace();
        }

        int sem = getsem(username);
        if (sem <= 8)
        {
            System.out.println("Not Graduated");
            return false;
        }

        Double cc = getcore(username);
        Double ce = getelective(username);

            if (cc >= 80 || ce >= 40) {
                System.out.println("Yes you are Graduated");
                return  true;
            }
            else
            {
                System.out.println("Not Graduated");
                return false;
            }
    }

    public static Double getcore(String username) {

        Connection conn = null;
        Statement stmt = null;

        try {
            conn = DBConnector.getConnection();
            stmt = conn.createStatement();
        } catch (Exception err) {
            err.printStackTrace();
        }

        Statement stmt1 = null;
        ResultSet rs1 = null;
        Double cc=0.0;
        try {

            stmt1 = conn.createStatement();
            String core = "SELECT SUM(cc.credit) " +
                    "FROM course_catalog_admin cc " +
                    "JOIN student_registration sr ON cc.course_id = sr.course_id " +
                    "WHERE sr.username = '" + username + "' AND cc.ce = 'c'";

            rs1 = stmt1.executeQuery(core);
            if (rs1.next()) {
                cc = rs1.getDouble(1);
                System.out.println(cc);
            }
            rs1.close();
            stmt1.close();

        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
        return cc;
    }

    public static Double getelective(String username) {

        Connection conn = null;
        Statement stmt = null;

        try {
            conn = DBConnector.getConnection();
            stmt = conn.createStatement();
        } catch (Exception err) {
            err.printStackTrace();
        }

        Statement stmt1 = null;
        ResultSet rs1 = null;
        Double cc=0.0;
        try {

            stmt1 = conn.createStatement();
            String elective = "SELECT SUM(cc.credit) " +
                    "FROM course_catalog_admin cc " +
                    "JOIN student_registration sr ON cc.course_id = sr.course_id " +
                    "WHERE sr.username = '" + username + "' AND cc.ce = 'e'";

            rs1 = stmt1.executeQuery(elective);
            if (rs1.next()) {
                cc = rs1.getDouble(1);
                System.out.println(cc);
            }
            rs1.close();
            stmt1.close();

        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
        return cc;
    }

    public static int getsem(String username) {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/aims", "postgres", "1234");
            stmt = conn.createStatement();
            String sql = "SELECT semester FROM user_details WHERE username = '" + username + "'";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {

                int sem = rs.getInt(1);
                return sem;
            }
            return -1;
        } catch (SQLException se) {
            se.printStackTrace();
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public static boolean updateProfile(String username , String joining_date , String contact) {

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

            String query = "SELECT * FROM user_details WHERE username= '" + username + "'";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) { // check if there is a row in the result set

                try {
                    String Query = "UPDATE user_details SET joining_date = ?, contact = ? WHERE username = ?";
                    PreparedStatement pstmt = conn.prepareStatement(Query);
                    pstmt.setString(1, joining_date);
                    pstmt.setString(2, contact);
                    pstmt.setString(3, username);
                    int rowsAffected = pstmt.executeUpdate();
                    System.out.println("Updated Succesfully");
                    return true;

                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("Failed Updation");
                    return false;
                }
            }
            resultSet.close();
            statement.close();
            conn.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean afterLogin(String username) throws IOException {

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
        System.out.println("1. Register / Deregister Course");
        System.out.println("2. view only thier grades");
        System.out.println("3. Compute Current CGPA");
        System.out.println("4. Update Profile");
        System.out.println("5. Graduation Check");
        System.out.println("Press 0 For LOGOUT");
        System.out.print("Enter an option: ");
        int op = in.nextInt();

        if (op == 1)
        {
            studentCourse.flow(username);
            System.out.println("PRESS 0 FOR GO BACK");
            int back = in.nextInt();
            if (back == 0) afterLogin(username);
        }
        else if (op == 2) {
            grades.studentGrade(username);
            System.out.println("PRESS 0 FOR GO BACK");
            int back = in.nextInt();
            if (back == 0) afterLogin(username);
        }
        else if (op == 3) {

            int sem = getsem(username);
            Double cg = cgpa.flow(username, sem);
            System.out.println("CGPA =");
            System.out.println(cg);
            System.out.println("PRESS 0 FOR GO BACK");
            int back = in.nextInt();
            if (back == 0) afterLogin(username);
        } // compute CGPA
        else if (op == 4) {

            System.out.println("1. Enter joining_date");
            String joining_date = in.next();
            System.out.println("2. Enter Contact");
            String contact = in.next();
            updateProfile(username,joining_date,contact);
            System.out.println("PRESS 0 FOR GO BACK");
            int back = in.nextInt();
            if (back == 0) afterLogin(username);
        } // Update profile
        else if(op==5)
        {
            // graduation check
            graduationCheck(username);
            System.out.println("PRESS 0 FOR GO BACK");
            int back = in.nextInt();
            if (back == 0) afterLogin(username);
        }
        else if(op==0)
        {
            main.main(new String[0]);
        }
        return true;
    }

}
