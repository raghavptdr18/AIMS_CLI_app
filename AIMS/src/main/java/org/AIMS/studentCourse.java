package org.AIMS;

import java.io.IOException;
import java.sql.*;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class studentCourse {
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



        System.out.println("1. View Course Offerings");
        System.out.println("2. Register/Deregister Course");
        System.out.println("3. view my Courses");
        System.out.println("PRESS 0 FOR GO BACK");
        System.out.println("Enter Option");
        option = in.nextInt();

        if (option == 1) {

            int sem = getsem(username);
            viewCourseOfferings(username,sem);

            System.out.println("PRESS 0 FOR GO BACK");
            int back = in.nextInt();
            if (back == 0)
                studentCourse.flow(username);

        }
        else if (option == 2) {

            // check window for edit 

            int check = 0;

            check = checkWindow(check);

            if(check==1){
            
            System.out.println("1. Register Course");
            System.out.println("2. Deregister Course");
            System.out.println("Enter Option");

            int op = in.nextInt();

            if (op == 1) {

                System.out.println("1. Enter course id");
                String id = in.next();
                registerDeregister.registerDeregisterStudent(1,id,username);

            } // register course
            else if (op == 2) {

                System.out.println("1. Enter course id for Deregester");
                String id = in.next();
                registerDeregister.registerDeregisterStudent(2,id,username);
            } // deregister course
        }
        else 
        {
            System.out.println("Window for register and deregister the course is closed");
        }

            System.out.println("PRESS 0 FOR GO BACK");
            int back = in.nextInt();
            if (back == 0) studentCourse.flow(username);

        }
        else if (option == 3) {

            int sem = getsem(username);
            viewMyCourse(username,sem);
            System.out.println("PRESS 0 FOR GO BACK");
            int back = in.nextInt();
            if (back == 0) studentCourse.flow(username);
        } // view my courses
        else if (option == 0) student.afterLogin(username);
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

    public static int getbatch(String username) {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/aims", "postgres", "1234");
            stmt = conn.createStatement();
            String sql = "SELECT batch FROM user_details WHERE username = '" + username + "'";
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

    public static double getcurrentsemCredits(String username) {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/aims", "postgres", "1234");
            stmt = conn.createStatement();
            String sql = "SELECT current_sem_credits FROM user_details WHERE username = '" + username + "'";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {

                Double credits = rs.getDouble(1);
                return credits;
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

    public static double getccc(String id) {
        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/aims", "postgres", "1234");
            stmt = conn.createStatement();
            if (conn != null) {
                System.out.println("connection OK");
            } else {
                System.out.println("connection FAILED");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        try {

            String sql = "SELECT credit FROM course_catalog_admin WHERE course_id = ?";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, id);

                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            double ccc = (double) rs.getInt(1);
                            System.out.println("Current course credits: " + ccc);
                            return ccc;
                        } else {
                            System.out.println("No results found");
                            return 0;
                        }
                    }
            
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }

    public static boolean requisite_check(String id , String username) {

        // id => course want to register
        // rid => prerequisite id

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

        // getting requesite id

        String rid ="-1";

        try {
            String sql = "SELECT prerequisites FROM course_catalog_admin WHERE course_id= ?";

            PreparedStatement pstmt = conn.prepareStatement(sql) ;

            pstmt.setString(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    rid = rs.getString(1);
                }
                rs.close();
            }

            pstmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println(rid);

        if(rid.equals("-1")) return true;
        // check if student done this requesite id course
        if(!rid.equals("-1")){
            try {
                String sql = "SELECT * FROM student_registration WHERE course_id= '"+rid+"' AND username= '"+username+"' AND stat='completed' ";

                PreparedStatement pstmt = conn.prepareStatement(sql) ;

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return true;
                    }
                    rs.close();
                }

                pstmt.close();
                conn.close();
                return false;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        else return true;

    }

    public static boolean cgCheck(String username, String id , int sem) {

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

        Double cgpa1=(double) 0;
        Double cgpaCourse=(double) 0;

        // getting student cgpa

        cgpa1 = cgpa.flow(username,sem);

        // getting course cgpa constraint

        try {
            String query = "SELECT cgpa_constraint FROM course_offerings WHERE course_id = '" + id + "'";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) { // check if there is a row in the result set
                cgpaCourse=resultSet.getDouble(1);
            }
            resultSet.close();
            statement.close();
//            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed Updation");
        }

        if(cgpa1>=cgpaCourse) return true;
        else return false;

    }

    public static double creditlimit(String username, int sem) {

        if(sem==1 || sem==2) return 18;

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
            String sql = "SELECT SUM(course_catalog_admin.credit) FROM student_registration, course_catalog_admin WHERE student_registration.course_id = course_catalog_admin.course_id AND student_registration.username = ? AND (student_registration.semester = ? OR student_registration.semester = ?)";

            PreparedStatement pstmt = conn.prepareStatement(sql) ;
            pstmt.setString(1, username);
            pstmt.setInt(2, sem-1);
            pstmt.setInt(3, sem-2);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    double sum = rs.getDouble(1);
                    System.out.println("Sum: " + sum);
                    return (sum/2)*(1.25);
                }
                rs.close();
            }
            pstmt.close();
            conn.close();
            return -1;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static boolean viewCourseOfferings(String username, int sem) {

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
        int batch = getbatch(username);
        try {
            String query = "SELECT course_catalog_admin.course_id, course_offerings.instructor, course_catalog_admin.credit, course_offerings.cgpa_constraint, course_catalog_admin.ce, course_catalog_admin.prerequisites FROM course_catalog_admin, course_offerings WHERE course_catalog_admin.course_id = course_offerings.course_id AND course_catalog_admin.batch = '" + batch + "' AND course_catalog_admin.semester = '" + sem + "'"; // view only that sem and batch
            // courses
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    System.out.print(resultSet.getString(i) + " ");
                }
                System.out.println();
                conn.close();
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static boolean viewMyCourse(String username, int sem) {

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
            String query = "SELECT * FROM student_registration WHERE username = '" + username + "'";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    System.out.print(resultSet.getString(i) + " ");
                }
                System.out.println();
                conn.close();
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
    public static int checkWindow(int check) {

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
            String query = "SELECT course_registration_student FROM flags";
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

    public static boolean checkAvailability(String username , String id) {

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

        int batch = getbatch(username);
        int sem = getsem(username);

        try {
            String query = "SELECT * FROM course_offerings , course_catalog_admin WHERE course_catalog_admin.course_id = course_offerings.course_id AND course_catalog_admin.batch = '"+batch+"' AND course_catalog_admin.semester= '"+sem+"' AND course_offerings.course_id = '"+id+"' ";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if(!resultSet.next())
            {
                return false;
            }
            else return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
